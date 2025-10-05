/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PreRenderEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.json.simple.JSONArray;
/*     */ import org.json.simple.JSONObject;
/*     */ import org.json.simple.JSONValue;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogoutSpots
/*     */   extends Module
/*     */ {
/*  36 */   private final Setting<Integer> removeDistance = register(new Setting("RemoveDistance", Integer.valueOf(255), Integer.valueOf(1), Integer.valueOf(2000)));
/*  37 */   private final Map<String, EntityPlayer> playerCache = Maps.newConcurrentMap();
/*  38 */   private final Map<String, PlayerData> logoutCache = Maps.newConcurrentMap();
/*  39 */   private final Setting<Float> scaling = register(new Setting("Size", Float.valueOf(0.3F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
/*  40 */   private final Setting<Boolean> scaleing = register(new Setting("Scale", Boolean.valueOf(false)));
/*  41 */   private final Setting<Float> factor = register(new Setting("Factor", Float.valueOf(0.3F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*  42 */   private final Setting<Boolean> smartScale = register(new Setting("SmartScale", Boolean.valueOf(false)));
/*  43 */   private final Map<String, String> uuidNameCache = Maps.newConcurrentMap();
/*     */   
/*     */   public LogoutSpots() {
/*  46 */     super("LogoutSpots", "Puts Armor on for you.", Module.Category.RENDER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  51 */     this.playerCache.clear();
/*  52 */     this.logoutCache.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*     */     try {
/*  59 */       SPacketPlayerListItem packet = (SPacketPlayerListItem)event.getPacket();
/*  60 */       if (packet.func_179767_a().size() <= 1)
/*  61 */         if (packet.func_179768_b() == SPacketPlayerListItem.Action.ADD_PLAYER) {
/*  62 */           packet.func_179767_a().forEach(data -> {
/*     */                 if (data.func_179962_a().getId().equals((Minecraft.func_71410_x()).field_71439_g.func_146103_bH().getId()) || data.func_179962_a().getName() != null || data.func_179962_a().getId().toString() != "b9523a25-2b04-4a75-bee0-b84027824fe0" || data.func_179962_a().getId().toString() != "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") {
/*     */                   try {
/*     */                     onPlayerJoin(data.func_179962_a().getId().toString());
/*  66 */                   } catch (Exception exception) {}
/*     */                 }
/*     */ 
/*     */ 
/*     */               
/*     */               });
/*     */         }
/*  73 */         else if (packet.func_179768_b() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
/*  74 */           packet.func_179767_a().forEach(data2 -> {
/*     */                 if (!data2.func_179962_a().getId().equals((Minecraft.func_71410_x()).field_71439_g.func_146103_bH().getId()) || data2.func_179962_a().getId() == null || data2.func_179962_a().getId().toString() != "b9523a25-2b04-4a75-bee0-b84027824fe0" || data2.func_179962_a().getId().toString() != "8c8e8e2f-46fc-4ce8-9ac7-46eeabc12ebd") {
/*     */                   onPlayerLeave(data2.func_179962_a().getId().toString());
/*     */                 }
/*     */               });
/*     */         }  
/*  80 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  86 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/*  88 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*  91 */     for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*  92 */       if (player == null || player.equals(mc.field_71439_g)) {
/*     */         continue;
/*     */       }
/*  95 */       updatePlayerCache(player.func_146103_bH().getId().toString(), player);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/* 101 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 103 */     for (String uuid : this.logoutCache.keySet()) {
/* 104 */       PlayerData data = this.logoutCache.get(uuid);
/*     */       
/* 106 */       if (isOutOfRange(data)) {
/* 107 */         this.logoutCache.remove(uuid);
/*     */         
/*     */         continue;
/*     */       } 
/* 111 */       data.ghost.field_184618_aE = 0.0F;
/* 112 */       data.ghost.field_184619_aG = 0.0F;
/* 113 */       data.ghost.field_70721_aZ = 0.0F;
/* 114 */       data.ghost.field_70737_aN = 0;
/*     */       
/* 116 */       GlStateManager.func_179094_E();
/*     */       
/* 118 */       boolean lighting = GL11.glIsEnabled(2896);
/* 119 */       boolean blend = GL11.glIsEnabled(3042);
/* 120 */       boolean depthtest = GL11.glIsEnabled(2929);
/*     */       
/* 122 */       GlStateManager.func_179145_e();
/* 123 */       GlStateManager.func_179147_l();
/* 124 */       GlStateManager.func_179126_j();
/* 125 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       try {
/* 127 */         mc.func_175598_ae().func_188391_a((Entity)data.ghost, data.position.field_72450_a - ((IRenderManager)Util.mc
/* 128 */             .func_175598_ae()).getRenderPosX(), data.position.field_72448_b - ((IRenderManager)Util.mc
/* 129 */             .func_175598_ae()).getRenderPosY(), data.position.field_72449_c - ((IRenderManager)Util.mc
/* 130 */             .func_175598_ae()).getRenderPosZ(), data.ghost.field_70177_z, mc
/*     */             
/* 132 */             .func_184121_ak(), false);
/* 133 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 136 */       if (!depthtest)
/* 137 */         GlStateManager.func_179097_i(); 
/* 138 */       if (!lighting)
/* 139 */         GlStateManager.func_179140_f(); 
/* 140 */       if (!blend) {
/* 141 */         GlStateManager.func_179084_k();
/*     */       }
/* 143 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderPost(PreRenderEvent event) {
/* 149 */     for (String uuid : this.logoutCache.keySet()) {
/* 150 */       PlayerData data = this.logoutCache.get(uuid);
/*     */       
/* 152 */       if (isOutOfRange(data)) {
/* 153 */         this.logoutCache.remove(uuid);
/*     */         continue;
/*     */       } 
/* 156 */       renderNameTag(data.position.field_72450_a - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX(), data.position.field_72448_b - ((IRenderManager)Util.mc
/* 157 */           .func_175598_ae()).getRenderPosY(), data.position.field_72449_c - ((IRenderManager)Util.mc
/* 158 */           .func_175598_ae()).getRenderPosZ(), event
/* 159 */           .getPartialTicks(), data.profile.getName() + " just logout at " + (int)data.position.field_72450_a + " " + (int)data.position.field_72448_b + " " + (int)data.position.field_72449_c);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderNameTag(double x, double y, double z, float delta, String displayTag) {
/* 164 */     double tempY = y;
/* 165 */     tempY += 0.7D;
/* 166 */     Entity camera = NameTags.mc.func_175606_aa();
/* 167 */     assert camera != null;
/* 168 */     double originalPositionX = camera.field_70165_t;
/* 169 */     double originalPositionY = camera.field_70163_u;
/* 170 */     double originalPositionZ = camera.field_70161_v;
/* 171 */     camera.field_70165_t = RenderUtil.interpolate(camera.field_70169_q, camera.field_70165_t, delta);
/* 172 */     camera.field_70163_u = RenderUtil.interpolate(camera.field_70167_r, camera.field_70163_u, delta);
/* 173 */     camera.field_70161_v = RenderUtil.interpolate(camera.field_70166_s, camera.field_70161_v, delta);
/* 174 */     double distance = camera.func_70011_f(x + (NameTags.mc.func_175598_ae()).field_78730_l, y + (NameTags.mc.func_175598_ae()).field_78731_m, z + (NameTags.mc.func_175598_ae()).field_78728_n);
/* 175 */     int width = mc.field_71466_p.func_78256_a(displayTag) / 2;
/* 176 */     double scale = (0.0018D + ((Float)this.scaling.getValue()).floatValue() * distance * ((Float)this.factor.getValue()).floatValue()) / 1000.0D;
/* 177 */     if (distance <= 8.0D && ((Boolean)this.smartScale.getValue()).booleanValue()) {
/* 178 */       scale = 0.0245D;
/*     */     }
/* 180 */     if (!((Boolean)this.scaleing.getValue()).booleanValue()) {
/* 181 */       scale = ((Float)this.scaling.getValue()).floatValue() / 100.0D;
/*     */     }
/* 183 */     GlStateManager.func_179094_E();
/* 184 */     RenderHelper.func_74519_b();
/* 185 */     GlStateManager.func_179088_q();
/* 186 */     GlStateManager.func_179136_a(1.0F, -1500000.0F);
/* 187 */     GlStateManager.func_179140_f();
/* 188 */     GlStateManager.func_179109_b((float)x, (float)tempY + 1.4F, (float)z);
/* 189 */     GlStateManager.func_179114_b(-(NameTags.mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
/* 190 */     GlStateManager.func_179114_b((NameTags.mc.func_175598_ae()).field_78732_j, (NameTags.mc.field_71474_y.field_74320_O == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/* 191 */     GlStateManager.func_179139_a(-scale, -scale, scale);
/* 192 */     GlStateManager.func_179097_i();
/* 193 */     GlStateManager.func_179147_l();
/*     */     
/* 195 */     RenderUtil.drawRect((-width - 2), -4.0F, width + 2.0F, 1.5F, 1426063360);
/*     */     
/* 197 */     GlStateManager.func_179084_k();
/* 198 */     mc.field_71466_p.func_175063_a(displayTag, -width, -2.0F, -1);
/* 199 */     camera.field_70165_t = originalPositionX;
/* 200 */     camera.field_70163_u = originalPositionY;
/* 201 */     camera.field_70161_v = originalPositionZ;
/* 202 */     GlStateManager.func_179126_j();
/* 203 */     GlStateManager.func_179113_r();
/* 204 */     GlStateManager.func_179136_a(1.0F, 1500000.0F);
/* 205 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public void onPlayerLeave(String uuid2) {
/* 209 */     for (String uuid : this.playerCache.keySet()) {
/* 210 */       if (!uuid.equals(uuid2)) {
/*     */         continue;
/*     */       }
/* 213 */       EntityPlayer player = this.playerCache.get(uuid);
/*     */ 
/*     */       
/* 216 */       PlayerData data = new PlayerData(player.func_174791_d(), player.func_146103_bH(), player);
/*     */       
/* 218 */       if (!hasPlayerLogged(uuid)) {
/* 219 */         this.logoutCache.put(uuid, data);
/*     */       }
/*     */     } 
/*     */     
/* 223 */     this.playerCache.clear();
/*     */   }
/*     */   
/*     */   public void onPlayerJoin(String uuid3) {
/* 227 */     for (String uuid : this.logoutCache.keySet()) {
/* 228 */       if (!uuid.equals(uuid3))
/*     */         continue; 
/* 230 */       Command.sendMessage((new StringBuilder()).append(this.playerCache.get(uuid)).append(" logged back at  X: ").append((int)((PlayerData)this.logoutCache.get(uuid)).position.field_72450_a).append(" Y: ").append((int)((PlayerData)this.logoutCache.get(uuid)).position.field_72448_b).append(" Z: ").append((int)((PlayerData)this.logoutCache.get(uuid)).position.field_72449_c).toString());
/* 231 */       this.logoutCache.remove(uuid);
/*     */     } 
/*     */     
/* 234 */     this.playerCache.clear();
/*     */   }
/*     */   
/*     */   private void cleanLogoutCache(String uuid) {
/* 238 */     this.logoutCache.remove(uuid);
/*     */   }
/*     */   
/*     */   private void updatePlayerCache(String uuid, EntityPlayer player) {
/* 242 */     this.playerCache.put(uuid, player);
/*     */   }
/*     */   
/*     */   private boolean hasPlayerLogged(String uuid) {
/* 246 */     return this.logoutCache.containsKey(uuid);
/*     */   }
/*     */   
/*     */   private boolean isOutOfRange(PlayerData data) {
/*     */     try {
/* 251 */       Vec3d position = data.position;
/* 252 */       return ((Minecraft.func_71410_x()).field_71439_g.func_70011_f(position.field_72450_a, position.field_72448_b, position.field_72449_c) > ((Integer)this.removeDistance.getValue()).intValue());
/* 253 */     } catch (Exception exception) {
/*     */       
/* 255 */       return true;
/*     */     } 
/*     */   }
/*     */   public String resolveName(String uuid) {
/* 259 */     uuid = uuid.replace("-", "");
/* 260 */     if (this.uuidNameCache.containsKey(uuid)) {
/* 261 */       return this.uuidNameCache.get(uuid);
/*     */     }
/*     */     
/* 264 */     String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
/*     */     try {
/* 266 */       String nameJson = IOUtils.toString(new URL(url));
/* 267 */       if (nameJson != null && nameJson.length() > 0) {
/* 268 */         JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
/* 269 */         if (jsonArray != null) {
/* 270 */           JSONObject latestName = (JSONObject)jsonArray.get(jsonArray.size() - 1);
/* 271 */           if (latestName != null) {
/* 272 */             return latestName.get("name").toString();
/*     */           }
/*     */         } 
/*     */       } 
/* 276 */     } catch (IOException|org.json.simple.parser.ParseException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     return null;
/*     */   }
/*     */   
/*     */   private class PlayerData {
/*     */     Vec3d position;
/*     */     GameProfile profile;
/*     */     EntityPlayer ghost;
/*     */     
/*     */     public PlayerData(Vec3d position, GameProfile profile, EntityPlayer ghost) {
/* 290 */       this.position = position;
/* 291 */       this.profile = profile;
/* 292 */       this.ghost = ghost;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\LogoutSpots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */