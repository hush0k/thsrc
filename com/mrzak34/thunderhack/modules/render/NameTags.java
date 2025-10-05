/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.RotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import java.awt.Color;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameTags
/*     */   extends Module
/*     */ {
/*  37 */   private final Setting<Boolean> health = register(new Setting("Health", Boolean.valueOf(true)));
/*  38 */   private final Setting<Boolean> armor = register(new Setting("Armor", Boolean.valueOf(true)));
/*  39 */   private final Setting<Float> scaling = register(new Setting("Size", Float.valueOf(0.3F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
/*  40 */   private final Setting<Boolean> invisibles = register(new Setting("Invisibles", Boolean.valueOf(false)));
/*  41 */   private final Setting<Boolean> ping = register(new Setting("Ping", Boolean.valueOf(true)));
/*  42 */   private final Setting<Boolean> totemPops = register(new Setting("TotemPops", Boolean.valueOf(true)));
/*  43 */   private final Setting<Boolean> gamemode = register(new Setting("Gamemode", Boolean.valueOf(false)));
/*  44 */   private final Setting<Boolean> entityID = register(new Setting("ID", Boolean.valueOf(false)));
/*  45 */   private final Setting<Boolean> rect = register(new Setting("Rectangle", Boolean.valueOf(true)));
/*  46 */   private final Setting<Boolean> outline = register(new Setting("Outline", Boolean.FALSE, v -> ((Boolean)this.rect.getValue()).booleanValue()));
/*  47 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/*  48 */   private final Setting<Boolean> sneak = register(new Setting("SneakColor", Boolean.valueOf(false)));
/*  49 */   private final Setting<Boolean> heldStackName = register(new Setting("StackName", Boolean.valueOf(false)));
/*  50 */   private final Setting<Boolean> onlyFov = register(new Setting("OnlyFov", Boolean.valueOf(false)));
/*  51 */   private final Setting<Boolean> scaleing = register(new Setting("Scale", Boolean.valueOf(false)));
/*  52 */   private final Setting<Float> factor = register(new Setting("Factor", Float.valueOf(0.3F), Float.valueOf(0.1F), Float.valueOf(1.0F), v -> ((Boolean)this.scaleing.getValue()).booleanValue()));
/*  53 */   private final Setting<Boolean> smartScale = register(new Setting("SmartScale", Boolean.valueOf(false), v -> ((Boolean)this.scaleing.getValue()).booleanValue()));
/*  54 */   private final Setting<Boolean> ench = register(new Setting("Enchantments", Boolean.valueOf(false)));
/*  55 */   private final Setting<ColorSetting> mainColor = register(new Setting("MainColor", new ColorSetting((new Color(-1157627897, true)).getRGB())));
/*  56 */   private final Setting<ColorSetting> outlineColor = register(new Setting("OutlineColor", new ColorSetting((new Color(-1144535097, true)).getRGB())));
/*  57 */   private final Setting<ColorSetting> textColor = register(new Setting("TextColor", new ColorSetting((new Color(-1144535097, true)).getRGB())));
/*  58 */   private final Setting<ColorSetting> friendColor = register(new Setting("FriendColor", new ColorSetting((new Color(-1693361408, true)).getRGB())));
/*  59 */   private final Setting<ColorSetting> friendtextColor = register(new Setting("FriendText", new ColorSetting((new Color(-1150039040, true)).getRGB())));
/*  60 */   private final Setting<ColorSetting> invisibleText = register(new Setting("InvisText", new ColorSetting((new Color(-1157548383, true)).getRGB())));
/*  61 */   private final Setting<ColorSetting> shiftColor = register(new Setting("ShiftText", new ColorSetting((new Color(-1147600692, true)).getRGB())));
/*  62 */   public Setting<mode> Mode = register(new Setting("RectMode", mode.Shadow));
/*     */   public NameTags() {
/*  64 */     super("Nametags", "Better Nametags", Module.Category.RENDER);
/*     */ 
/*     */     
/*  67 */     this.players = new CopyOnWriteArrayList<>();
/*     */   }
/*     */   private CopyOnWriteArrayList<EntityPlayer> players;
/*     */   public void onUpdate() {
/*  71 */     this.players.clear();
/*  72 */     this.players.addAll(mc.field_71441_e.field_73010_i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/*  77 */     if (!fullNullCheck()) {
/*  78 */       for (EntityPlayer player : this.players) {
/*  79 */         if (player == null || player.equals(mc.field_71439_g) || !player.func_70089_S() || (player.func_82150_aj() && !((Boolean)this.invisibles.getValue()).booleanValue()) || (((Boolean)this.onlyFov.getValue()).booleanValue() && !RotationUtil.isInFov((Entity)player)))
/*     */           continue; 
/*  81 */         double x = interpolate(player.field_70142_S, player.field_70165_t, event.getPartialTicks()) - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX();
/*  82 */         double y = interpolate(player.field_70137_T, player.field_70163_u, event.getPartialTicks()) - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY();
/*  83 */         double z = interpolate(player.field_70136_U, player.field_70161_v, event.getPartialTicks()) - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ();
/*  84 */         renderNameTag(player, x, y, z, event.getPartialTicks());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawRect(float x, float y, float w, float h, int color) {
/*  90 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/*  91 */     float red = (color >> 16 & 0xFF) / 255.0F;
/*  92 */     float green = (color >> 8 & 0xFF) / 255.0F;
/*  93 */     float blue = (color & 0xFF) / 255.0F;
/*  94 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  95 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  96 */     GlStateManager.func_179147_l();
/*  97 */     GlStateManager.func_179090_x();
/*  98 */     GlStateManager.func_187441_d(((Float)this.lineWidth.getValue()).floatValue());
/*  99 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 100 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
/* 101 */     bufferbuilder.func_181662_b(x, h, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 102 */     bufferbuilder.func_181662_b(w, h, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 103 */     bufferbuilder.func_181662_b(w, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 104 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 105 */     tessellator.func_78381_a();
/* 106 */     GlStateManager.func_179098_w();
/* 107 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public void drawOutlineRect(float x, float y, float w, float h, int color) {
/* 111 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 112 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 113 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 114 */     float blue = (color & 0xFF) / 255.0F;
/* 115 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 116 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 117 */     GlStateManager.func_179147_l();
/* 118 */     GlStateManager.func_179090_x();
/* 119 */     GlStateManager.func_187441_d(((Float)this.lineWidth.getValue()).floatValue());
/* 120 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 121 */     bufferbuilder.func_181668_a(2, DefaultVertexFormats.field_181706_f);
/* 122 */     bufferbuilder.func_181662_b(x, h, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 123 */     bufferbuilder.func_181662_b(w, h, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 124 */     bufferbuilder.func_181662_b(w, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 125 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 126 */     tessellator.func_78381_a();
/* 127 */     GlStateManager.func_179098_w();
/* 128 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
/* 132 */     double tempY = y;
/* 133 */     tempY += player.func_70093_af() ? 0.5D : 0.7D;
/* 134 */     Entity camera = mc.func_175606_aa();
/* 135 */     assert camera != null;
/* 136 */     double originalPositionX = camera.field_70165_t;
/* 137 */     double originalPositionY = camera.field_70163_u;
/* 138 */     double originalPositionZ = camera.field_70161_v;
/* 139 */     camera.field_70165_t = interpolate(camera.field_70169_q, camera.field_70165_t, delta);
/* 140 */     camera.field_70163_u = interpolate(camera.field_70167_r, camera.field_70163_u, delta);
/* 141 */     camera.field_70161_v = interpolate(camera.field_70166_s, camera.field_70161_v, delta);
/* 142 */     String displayTag = getDisplayTag(player);
/* 143 */     double distance = camera.func_70011_f(x + (mc.func_175598_ae()).field_78730_l, y + (mc.func_175598_ae()).field_78731_m, z + (mc.func_175598_ae()).field_78728_n);
/* 144 */     int width = Util.fr.func_78256_a(displayTag) / 2;
/* 145 */     double scale = (0.0018D + ((Float)this.scaling.getValue()).floatValue() * distance * ((Float)this.factor.getValue()).floatValue()) / 1000.0D;
/* 146 */     if (distance <= 8.0D && ((Boolean)this.smartScale.getValue()).booleanValue()) {
/* 147 */       scale = 0.0245D;
/*     */     }
/* 149 */     if (!((Boolean)this.scaleing.getValue()).booleanValue()) {
/* 150 */       scale = ((Float)this.scaling.getValue()).floatValue() / 100.0D;
/*     */     }
/* 152 */     GlStateManager.func_179094_E();
/* 153 */     RenderHelper.func_74519_b();
/* 154 */     GlStateManager.func_179088_q();
/* 155 */     GlStateManager.func_179136_a(1.0F, -1500000.0F);
/* 156 */     GlStateManager.func_179140_f();
/* 157 */     GlStateManager.func_179109_b((float)x, (float)tempY + 1.4F, (float)z);
/* 158 */     GlStateManager.func_179114_b(-(mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
/* 159 */     GlStateManager.func_179114_b((mc.func_175598_ae()).field_78732_j, (mc.field_71474_y.field_74320_O == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/* 160 */     GlStateManager.func_179139_a(-scale, -scale, scale);
/* 161 */     GlStateManager.func_179147_l();
/* 162 */     GlStateManager.func_179147_l();
/*     */     
/* 164 */     if (((Boolean)this.rect.getValue()).booleanValue()) {
/* 165 */       drawRect((-width - 2), -(Util.fr.field_78288_b + 1), width + 2.0F, 1.5F, Thunderhack.friendManager.isFriend(player) ? ((ColorSetting)this.friendColor.getValue()).getColor() : ((ColorSetting)this.mainColor.getValue()).getColor());
/* 166 */       if (((Boolean)this.outline.getValue()).booleanValue()) {
/* 167 */         drawOutlineRect((-width - 2), -(mc.field_71466_p.field_78288_b + 1), width + 2.0F, 1.5F, ((ColorSetting)this.outlineColor.getValue()).getColor());
/*     */       }
/*     */     } 
/* 170 */     GlStateManager.func_179084_k();
/* 171 */     ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
/* 172 */     if (((Boolean)this.heldStackName.getValue()).booleanValue() && !renderMainHand.func_190926_b() && renderMainHand.func_77973_b() != Items.field_190931_a) {
/* 173 */       String stackName = renderMainHand.func_82833_r();
/* 174 */       int stackNameWidth = Util.fr.func_78256_a(stackName) / 2;
/* 175 */       GL11.glPushMatrix();
/* 176 */       GL11.glScalef(0.75F, 0.75F, 0.0F);
/* 177 */       Util.fr.func_175063_a(stackName, -stackNameWidth, -(getBiggestArmorTag(player) + 20.0F), -1);
/* 178 */       GL11.glScalef(1.5F, 1.5F, 1.0F);
/* 179 */       GL11.glPopMatrix();
/*     */     } 
/* 181 */     if (((Boolean)this.armor.getValue()).booleanValue()) {
/* 182 */       GlStateManager.func_179094_E();
/* 183 */       int xOffset = -8;
/* 184 */       for (ItemStack stack : player.field_71071_by.field_70460_b) {
/* 185 */         if (stack == null)
/* 186 */           continue;  xOffset -= 8;
/*     */       } 
/* 188 */       xOffset -= 8;
/* 189 */       ItemStack renderOffhand = player.func_184592_cb().func_77946_l();
/* 190 */       renderItemStack(renderOffhand, xOffset, -26);
/* 191 */       xOffset += 16;
/* 192 */       for (ItemStack stack : player.field_71071_by.field_70460_b) {
/* 193 */         if (stack == null)
/* 194 */           continue;  ItemStack armourStack = stack.func_77946_l();
/* 195 */         renderItemStack(armourStack, xOffset, -26);
/* 196 */         xOffset += 16;
/*     */       } 
/* 198 */       renderItemStack(renderMainHand, xOffset, -26);
/* 199 */       GlStateManager.func_179121_F();
/*     */     } 
/* 201 */     Util.fr.func_175063_a(displayTag, -width, -(Util.fr.field_78288_b - 1), getDisplayColour(player));
/* 202 */     camera.field_70165_t = originalPositionX;
/* 203 */     camera.field_70163_u = originalPositionY;
/* 204 */     camera.field_70161_v = originalPositionZ;
/* 205 */     GlStateManager.func_179084_k();
/* 206 */     GlStateManager.func_179113_r();
/* 207 */     GlStateManager.func_179136_a(1.0F, 1500000.0F);
/* 208 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   private void renderItemStack(ItemStack stack, int x, int y) {
/* 212 */     GlStateManager.func_179094_E();
/*     */     
/* 214 */     GlStateManager.func_179086_m(256);
/* 215 */     RenderHelper.func_74519_b();
/* 216 */     (mc.func_175599_af()).field_77023_b = -150.0F;
/* 217 */     GlStateManager.func_179118_c();
/* 218 */     GlStateManager.func_179129_p();
/* 219 */     mc.func_175599_af().func_180450_b(stack, x, y);
/* 220 */     mc.func_175599_af().func_175030_a(mc.field_71466_p, stack, x, y);
/* 221 */     (mc.func_175599_af()).field_77023_b = 0.0F;
/* 222 */     RenderHelper.func_74518_a();
/* 223 */     GlStateManager.func_179089_o();
/* 224 */     GlStateManager.func_179141_d();
/* 225 */     GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
/*     */     
/* 227 */     if (((Boolean)this.ench.getValue()).booleanValue())
/* 228 */       renderEnchantmentText(stack, x, y); 
/* 229 */     GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
/* 230 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   private void renderEnchantmentText(ItemStack stack, int x, int y) {
/* 234 */     int enchantmentY = y - 8;
/* 235 */     if (stack.func_77973_b() == Items.field_151153_ao && stack.func_77962_s()) {
/* 236 */       Util.fr.func_175063_a("god", (x * 2), enchantmentY, -3977919);
/* 237 */       enchantmentY -= 8;
/*     */     } 
/* 239 */     NBTTagList enchants = stack.func_77986_q();
/* 240 */     for (int index = 0; index < enchants.func_74745_c(); index++) {
/* 241 */       short id = enchants.func_150305_b(index).func_74765_d("id");
/* 242 */       short level = enchants.func_150305_b(index).func_74765_d("lvl");
/* 243 */       Enchantment enc = Enchantment.func_185262_c(id);
/* 244 */       if (enc != null) {
/* 245 */         String encName = enc.func_190936_d() ? (TextFormatting.RED + enc.func_77316_c(level).substring(11).substring(0, 1).toLowerCase()) : enc.func_77316_c(level).substring(0, 1).toLowerCase();
/* 246 */         encName = encName + level;
/* 247 */         Util.fr.func_175063_a(encName, (x * 2), enchantmentY, -1);
/* 248 */         enchantmentY -= 8;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getBiggestArmorTag(EntityPlayer player) {
/* 257 */     float enchantmentY = 0.0F;
/* 258 */     boolean arm = false;
/* 259 */     for (ItemStack stack : player.field_71071_by.field_70460_b) {
/* 260 */       float encY = 0.0F;
/* 261 */       if (stack != null) {
/* 262 */         NBTTagList enchants = stack.func_77986_q();
/* 263 */         for (int index = 0; index < enchants.func_74745_c(); index++) {
/* 264 */           short id = enchants.func_150305_b(index).func_74765_d("id");
/* 265 */           Enchantment enc = Enchantment.func_185262_c(id);
/* 266 */           if (enc != null) {
/* 267 */             encY += 8.0F;
/* 268 */             arm = true;
/*     */           } 
/*     */         } 
/* 271 */       }  if (encY <= enchantmentY)
/* 272 */         continue;  enchantmentY = encY;
/*     */     } 
/* 274 */     ItemStack renderMainHand = player.func_184614_ca().func_77946_l();
/* 275 */     if (renderMainHand.func_77962_s()) {
/* 276 */       float encY = 0.0F;
/* 277 */       NBTTagList enchants = renderMainHand.func_77986_q();
/* 278 */       for (int index2 = 0; index2 < enchants.func_74745_c(); index2++) {
/* 279 */         short id = enchants.func_150305_b(index2).func_74765_d("id");
/* 280 */         Enchantment enc2 = Enchantment.func_185262_c(id);
/* 281 */         if (enc2 != null) {
/* 282 */           encY += 8.0F;
/* 283 */           arm = true;
/*     */         } 
/* 285 */       }  if (encY > enchantmentY)
/* 286 */         enchantmentY = encY; 
/*     */     } 
/*     */     ItemStack renderOffHand;
/* 289 */     if ((renderOffHand = player.func_184592_cb().func_77946_l()).func_77962_s()) {
/* 290 */       float encY = 0.0F;
/* 291 */       NBTTagList enchants = renderOffHand.func_77986_q();
/* 292 */       for (int index = 0; index < enchants.func_74745_c(); index++) {
/* 293 */         short id = enchants.func_150305_b(index).func_74765_d("id");
/* 294 */         Enchantment enc = Enchantment.func_185262_c(id);
/* 295 */         if (enc != null) {
/* 296 */           encY += 8.0F;
/* 297 */           arm = true;
/*     */         } 
/* 299 */       }  if (encY > enchantmentY) {
/* 300 */         enchantmentY = encY;
/*     */       }
/*     */     } 
/* 303 */     return (arm ? false : 20) + enchantmentY;
/*     */   }
/*     */   
/*     */   private String getDisplayTag(EntityPlayer player) {
/* 307 */     String name = player.func_145748_c_().func_150254_d();
/* 308 */     if (name.contains(mc.func_110432_I().func_111285_a())) {
/* 309 */       name = "You";
/*     */     }
/* 311 */     if (!((Boolean)this.health.getValue()).booleanValue()) {
/* 312 */       return name;
/*     */     }
/* 314 */     float health = EntityUtil.getHealth((Entity)player);
/* 315 */     String color = (health > 18.0F) ? "§a" : ((health > 16.0F) ? "§2" : ((health > 12.0F) ? "§e" : ((health > 8.0F) ? "§6" : ((health > 5.0F) ? "§c" : "§4"))));
/* 316 */     String pingStr = "";
/* 317 */     if (((Boolean)this.ping.getValue()).booleanValue()) {
/*     */       try {
/* 319 */         int responseTime = ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc.func_147114_u())).func_175102_a(player.func_110124_au()).func_178853_c();
/* 320 */         pingStr = pingStr + responseTime + "ms ";
/* 321 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 325 */     String popStr = " ";
/* 326 */     if (((Boolean)this.totemPops.getValue()).booleanValue()) {
/* 327 */       popStr = popStr + Thunderhack.combatManager.getPops((Entity)player);
/*     */     }
/* 329 */     String idString = "";
/* 330 */     if (((Boolean)this.entityID.getValue()).booleanValue()) {
/* 331 */       idString = idString + "ID: " + player.func_145782_y() + " ";
/*     */     }
/* 333 */     String gameModeStr = "";
/* 334 */     if (((Boolean)this.gamemode.getValue()).booleanValue()) {
/* 335 */       gameModeStr = player.func_184812_l_() ? (gameModeStr + "[C] ") : ((player.func_175149_v() || player.func_82150_aj()) ? (gameModeStr + "[I] ") : (gameModeStr + "[S] "));
/*     */     }
/* 337 */     name = (Math.floor(health) == health) ? (name + color + " " + ((health > 0.0F) ? (String)Integer.valueOf((int)Math.floor(health)) : "dead")) : (name + color + " " + ((health > 0.0F) ? (String)Integer.valueOf((int)health) : "dead"));
/* 338 */     return pingStr + idString + gameModeStr + name + popStr;
/*     */   }
/*     */   
/*     */   private int getDisplayColour(EntityPlayer player) {
/* 342 */     int colour = ((ColorSetting)this.textColor.getValue()).getColor();
/* 343 */     if (Thunderhack.friendManager.isFriend(player)) {
/* 344 */       return ((ColorSetting)this.friendtextColor.getValue()).getColor();
/*     */     }
/* 346 */     if (player.func_82150_aj()) {
/* 347 */       colour = ((ColorSetting)this.invisibleText.getValue()).getColor();
/* 348 */     } else if (player.func_70093_af() && ((Boolean)this.sneak.getValue()).booleanValue()) {
/* 349 */       colour = ((ColorSetting)this.shiftColor.getValue()).getColor();
/*     */     } 
/* 351 */     return colour;
/*     */   }
/*     */   
/*     */   private double interpolate(double previous, double current, float delta) {
/* 355 */     return previous + (current - previous) * delta;
/*     */   }
/*     */   
/*     */   private void renderEnchantmentText2(int x) {
/* 359 */     int enchantmentY = -78;
/* 360 */     FontRender.drawString3("ILLEGAL", (float)(x * 3.1D), enchantmentY, PaletteHelper.rainbow(300, 1.0F, 1.0F).getRGB());
/*     */   }
/*     */   
/*     */   public enum mode {
/* 364 */     Default, ShaderSmooth, Shadow;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\NameTags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */