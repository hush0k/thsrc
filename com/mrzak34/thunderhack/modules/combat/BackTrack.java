/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventEntityMove;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PreRenderEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.phobos.ThreadUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.client.CPacketConfirmTransaction;
/*     */ import net.minecraft.network.play.client.CPacketKeepAlive;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class BackTrack
/*     */   extends Module {
/*  28 */   private final Setting<RenderMode> renderMode = register(new Setting("RenderMode", RenderMode.Chams));
/*  29 */   private final Setting<ColorSetting> color1 = register(new Setting("Color", new ColorSetting(-2009289807)));
/*  30 */   private final Setting<ColorSetting> color2 = register(new Setting("HighLightColor", new ColorSetting(-2009289807)));
/*  31 */   private final Setting<Integer> btticks = register(new Setting("TrackTicks", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(15)));
/*  32 */   private final Setting<Boolean> hlaura = register(new Setting("HighLightAura", Boolean.valueOf(true)));
/*  33 */   private final Setting<Boolean> holdPackets = register(new Setting("ServerSync", Boolean.valueOf(true)));
/*     */   long skip_packet_ka;
/*     */   long skip_packet_ct;
/*     */   long skip_packet_cwt;
/*     */   
/*     */   public BackTrack() {
/*  39 */     super("BackTrack", "откатывает позицию-врагов", "rolls back the-position of enemies", Module.Category.COMBAT);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPreRenderEvent(PreRenderEvent event) {
/*  45 */     synchronized (this) {
/*  46 */       for (EntityPlayer entity : mc.field_71441_e.field_73010_i) {
/*  47 */         if (entity == mc.field_71439_g) {
/*     */           continue;
/*     */         }
/*  50 */         if (((IEntity)entity).getPosition_history().size() > 0) {
/*  51 */           for (int i = 0; i < ((IEntity)entity).getPosition_history().size(); i++) {
/*  52 */             GlStateManager.func_179094_E();
/*  53 */             if (Aura.bestBtBox != ((IEntity)entity).getPosition_history().get(i) && ((Boolean)this.hlaura.getValue()).booleanValue()) {
/*  54 */               if (this.renderMode.getValue() == RenderMode.Box) {
/*  55 */                 RenderUtil.drawBoundingBox(((IEntity)entity).getPosition_history().get(i), 1.0D, ((ColorSetting)this.color1.getValue()).getColorObject());
/*  56 */               } else if (this.renderMode.getValue() == RenderMode.Chams) {
/*  57 */                 RenderUtil.renderEntity(((IEntity)entity)
/*  58 */                     .getPosition_history().get(i), 
/*  59 */                     (ModelBase)(((IEntity)entity).getPosition_history().get(i)).modelPlayer, 
/*  60 */                     (((IEntity)entity).getPosition_history().get(i)).limbSwing, 
/*  61 */                     (((IEntity)entity).getPosition_history().get(i)).limbSwingAmount, 
/*  62 */                     (((IEntity)entity).getPosition_history().get(i)).Yaw, 
/*  63 */                     (((IEntity)entity).getPosition_history().get(i)).Pitch, 
/*  64 */                     (EntityLivingBase)(((IEntity)entity).getPosition_history().get(i)).ent, ((ColorSetting)this.color1
/*  65 */                     .getValue()).getColorObject());
/*  66 */               } else if (this.renderMode.getValue() == RenderMode.Ghost) {
/*     */                 
/*  68 */                 GlStateManager.func_179094_E();
/*     */                 
/*  70 */                 boolean lighting = GL11.glIsEnabled(2896);
/*  71 */                 boolean blend = GL11.glIsEnabled(3042);
/*  72 */                 boolean depthtest = GL11.glIsEnabled(2929);
/*     */                 
/*  74 */                 GlStateManager.func_179145_e();
/*  75 */                 GlStateManager.func_179147_l();
/*  76 */                 GlStateManager.func_179126_j();
/*  77 */                 GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */                 try {
/*  79 */                   mc.func_175598_ae().func_188391_a((Entity)entity, 
/*  80 */                       ((Box)((IEntity)entity).getPosition_history().get(i)).position.field_72450_a - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX(), 
/*  81 */                       ((Box)((IEntity)entity).getPosition_history().get(i)).position.field_72448_b - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY(), 
/*  82 */                       ((Box)((IEntity)entity).getPosition_history().get(i)).position.field_72449_c - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ(), 
/*     */                       
/*  84 */                       (((IEntity)entity).getPosition_history().get(i)).Yaw, mc.func_184121_ak(), false);
/*  85 */                 } catch (Exception exception) {}
/*     */ 
/*     */                 
/*  88 */                 if (!depthtest)
/*  89 */                   GlStateManager.func_179097_i(); 
/*  90 */                 if (!lighting)
/*  91 */                   GlStateManager.func_179140_f(); 
/*  92 */                 if (!blend) {
/*  93 */                   GlStateManager.func_179084_k();
/*     */                 }
/*  95 */                 GlStateManager.func_179121_F();
/*     */               }
/*     */             
/*  98 */             } else if (this.renderMode.getValue() == RenderMode.Box) {
/*  99 */               RenderUtil.drawBoundingBox(((IEntity)entity).getPosition_history().get(i), 1.0D, ((ColorSetting)this.color2.getValue()).getColorObject());
/* 100 */             } else if (this.renderMode.getValue() == RenderMode.Chams) {
/* 101 */               RenderUtil.renderEntity(((IEntity)entity)
/* 102 */                   .getPosition_history().get(i), 
/* 103 */                   (ModelBase)(((IEntity)entity).getPosition_history().get(i)).modelPlayer, 
/* 104 */                   (((IEntity)entity).getPosition_history().get(i)).limbSwing, 
/* 105 */                   (((IEntity)entity).getPosition_history().get(i)).limbSwingAmount, 
/* 106 */                   (((IEntity)entity).getPosition_history().get(i)).Yaw, 
/* 107 */                   (((IEntity)entity).getPosition_history().get(i)).Pitch, 
/* 108 */                   (EntityLivingBase)(((IEntity)entity).getPosition_history().get(i)).ent, ((ColorSetting)this.color2
/* 109 */                   .getValue()).getColorObject());
/*     */             }
/* 111 */             else if (this.renderMode.getValue() == RenderMode.Ghost) {
/*     */               
/* 113 */               GlStateManager.func_179094_E();
/*     */               
/* 115 */               boolean lighting = GL11.glIsEnabled(2896);
/* 116 */               boolean blend = GL11.glIsEnabled(3042);
/* 117 */               boolean depthtest = GL11.glIsEnabled(2929);
/*     */               
/* 119 */               GlStateManager.func_179145_e();
/* 120 */               GlStateManager.func_179147_l();
/* 121 */               GlStateManager.func_179126_j();
/* 122 */               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.1F);
/*     */               try {
/* 124 */                 mc.func_175598_ae().func_188391_a((Entity)entity, 
/* 125 */                     ((Box)((IEntity)entity).getPosition_history().get(i)).position.field_72450_a, 
/* 126 */                     ((Box)((IEntity)entity).getPosition_history().get(i)).position.field_72448_b, 
/* 127 */                     ((Box)((IEntity)entity).getPosition_history().get(i)).position.field_72449_c, 
/*     */                     
/* 129 */                     (((IEntity)entity).getPosition_history().get(i)).Yaw, mc.func_184121_ak(), false);
/* 130 */               } catch (Exception exception) {}
/*     */ 
/*     */               
/* 133 */               if (!depthtest)
/* 134 */                 GlStateManager.func_179097_i(); 
/* 135 */               if (!lighting)
/* 136 */                 GlStateManager.func_179140_f(); 
/* 137 */               if (!blend) {
/* 138 */                 GlStateManager.func_179084_k();
/*     */               }
/* 140 */               GlStateManager.func_179121_F();
/*     */             } 
/*     */             
/* 143 */             GlStateManager.func_179121_F();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 152 */     if (!((Boolean)this.holdPackets.getValue()).booleanValue() || fullNullCheck())
/* 153 */       return;  if (event.getPacket() instanceof CPacketKeepAlive) {
/* 154 */       if (((CPacketKeepAlive)event.getPacket()).func_149460_c() == this.skip_packet_ka) {
/*     */         return;
/*     */       }
/* 157 */       event.setCanceled(true);
/* 158 */       ThreadUtil.run(() -> { this.skip_packet_ka = ((CPacketKeepAlive)event.getPacket()).func_149460_c(); mc.field_71439_g.field_71174_a.func_147297_a(event.getPacket()); }((Integer)this.btticks
/*     */ 
/*     */           
/* 161 */           .getValue()).intValue() * 50L);
/*     */     } 
/*     */     
/* 164 */     if (event.getPacket() instanceof CPacketConfirmTransaction) {
/* 165 */       if (((CPacketConfirmTransaction)event.getPacket()).func_149533_d() == this.skip_packet_ct) {
/*     */         return;
/*     */       }
/* 168 */       if (((CPacketConfirmTransaction)event.getPacket()).func_149532_c() == this.skip_packet_cwt) {
/*     */         return;
/*     */       }
/* 171 */       event.setCanceled(true);
/* 172 */       ThreadUtil.run(() -> { this.skip_packet_ct = ((CPacketConfirmTransaction)event.getPacket()).func_149533_d(); this.skip_packet_cwt = ((CPacketConfirmTransaction)event.getPacket()).func_149532_c(); mc.field_71439_g.field_71174_a.func_147297_a(event.getPacket()); }((Integer)this.btticks
/*     */ 
/*     */ 
/*     */           
/* 176 */           .getValue()).intValue() * 50L);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityMove(EventEntityMove e) {
/* 182 */     if (e.ctx() == mc.field_71439_g) {
/*     */       return;
/*     */     }
/* 185 */     if (e.ctx() instanceof EntityPlayer && 
/* 186 */       e.ctx() != null) {
/* 187 */       EntityPlayer a = (EntityPlayer)e.ctx();
/* 188 */       ((IEntity)a).getPosition_history().add(new Box(e.ctx().func_174791_d(), ((Integer)this.btticks.getValue()).intValue(), a.field_184619_aG, a.field_70721_aZ, a.field_70177_z, a.field_70125_A, (EntityPlayer)e.ctx()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 195 */     for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/* 196 */       ((IEntity)player).getPosition_history().removeIf(Box::update);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum RenderMode
/*     */   {
/* 202 */     Box, Chams, Ghost, None;
/*     */   }
/*     */   
/*     */   public static class Box
/*     */   {
/*     */     private final ModelPlayer modelPlayer;
/*     */     private final Vec3d position;
/*     */     private final float limbSwing;
/*     */     private final float limbSwingAmount;
/*     */     private final float Yaw;
/*     */     private final float Pitch;
/*     */     private final EntityPlayer ent;
/*     */     private int ticks;
/*     */     
/*     */     public Box(Vec3d position, int ticks, float limbswing, float limbSwingAmount, float Yaw, float Pitch, EntityPlayer ent) {
/* 217 */       this.position = position;
/* 218 */       this.ticks = ticks;
/* 219 */       this.modelPlayer = new ModelPlayer(0.0F, false);
/* 220 */       this.limbSwing = limbswing;
/* 221 */       this.limbSwingAmount = limbSwingAmount;
/* 222 */       this.Pitch = Pitch;
/* 223 */       this.Yaw = Yaw;
/* 224 */       this.ent = ent;
/*     */     }
/*     */     public ModelPlayer getModelPlayer() {
/* 227 */       return this.modelPlayer;
/*     */     } public EntityPlayer getEnt() {
/* 229 */       return this.ent;
/*     */     }
/*     */     public int getTicks() {
/* 232 */       return this.ticks;
/*     */     }
/*     */     
/*     */     public boolean update() {
/* 236 */       return (this.ticks-- <= 0);
/*     */     }
/*     */     
/*     */     public Vec3d getPosition() {
/* 240 */       return this.position;
/*     */     }
/*     */     
/*     */     public float getLimbSwing() {
/* 244 */       return this.limbSwing;
/*     */     }
/*     */     
/*     */     public float getLimbSwingAmount() {
/* 248 */       return this.limbSwingAmount;
/*     */     }
/*     */     
/*     */     public float getYaw() {
/* 252 */       return this.Yaw;
/*     */     }
/*     */     
/*     */     public float getPitch() {
/* 256 */       return this.Pitch;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\BackTrack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */