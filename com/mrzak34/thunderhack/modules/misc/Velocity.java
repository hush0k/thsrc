/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PushEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketEntityVelocity;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketExplosion;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.SPacketExplosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Velocity
/*     */   extends Module
/*     */ {
/*  25 */   public Setting<Boolean> onlyAura = register(new Setting("OnlyAura", Boolean.valueOf(false)));
/*  26 */   public Setting<Boolean> ice = register(new Setting("Ice", Boolean.valueOf(false)));
/*  27 */   public Setting<Boolean> autoDisable = register(new Setting("DisableOnVerify", Boolean.valueOf(false)));
/*  28 */   private final Setting<modeEn> mode = register(new Setting("Mode", modeEn.Matrix));
/*  29 */   public Setting<Float> horizontal = register(new Setting("Horizontal", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(100.0F), v -> (this.mode.getValue() == modeEn.Custom)));
/*  30 */   public Setting<Float> vertical = register(new Setting("Vertical", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(100.0F), v -> (this.mode.getValue() == modeEn.Custom)));
/*     */   
/*     */   private boolean flag;
/*     */   
/*     */   public Velocity() {
/*  35 */     super("Velocity", "акэбэшка", Module.Category.MOVEMENT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  41 */     if (((Boolean)this.ice.getValue()).booleanValue()) {
/*  42 */       Blocks.field_150432_aD.field_149765_K = 0.6F;
/*  43 */       Blocks.field_150403_cj.field_149765_K = 0.6F;
/*  44 */       Blocks.field_185778_de.field_149765_K = 0.6F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  50 */     Blocks.field_150432_aD.field_149765_K = 0.98F;
/*  51 */     Blocks.field_150403_cj.field_149765_K = 0.98F;
/*  52 */     Blocks.field_185778_de.field_149765_K = 0.98F;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceived(PacketEvent.Receive event) {
/*  58 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*     */     
/*  62 */     if (event.getPacket() instanceof SPacketChat && ((Boolean)this.autoDisable.getValue()).booleanValue()) {
/*  63 */       String text = ((SPacketChat)event.getPacket()).func_148915_c().func_150254_d();
/*  64 */       if (text.contains("Тебя проверяют на чит АКБ, ник хелпера - "))
/*  65 */         toggle(); 
/*     */     } 
/*     */     Entity entity;
/*     */     SPacketEntityStatus packet;
/*  69 */     if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).func_149160_c() == 31 && entity = packet.func_149161_a((World)mc.field_71441_e) instanceof EntityFishHook) {
/*  70 */       EntityFishHook fishHook = (EntityFishHook)entity;
/*  71 */       if (fishHook.field_146043_c == mc.field_71439_g) {
/*  72 */         event.setCanceled(true);
/*     */       }
/*     */     } 
/*     */     
/*  76 */     if (event.getPacket() instanceof SPacketExplosion) {
/*  77 */       SPacketExplosion velocity_ = (SPacketExplosion)event.getPacket();
/*  78 */       if (this.mode.getValue() == modeEn.Custom) {
/*  79 */         ((ISPacketExplosion)velocity_).setMotionX(((ISPacketExplosion)velocity_).getMotionX() * ((Float)this.horizontal.getValue()).floatValue() / 100.0F);
/*  80 */         ((ISPacketExplosion)velocity_).setMotionZ(((ISPacketExplosion)velocity_).getMotionZ() * ((Float)this.horizontal.getValue()).floatValue() / 100.0F);
/*  81 */         ((ISPacketExplosion)velocity_).setMotionY(((ISPacketExplosion)velocity_).getMotionY() * ((Float)this.vertical.getValue()).floatValue() / 100.0F);
/*  82 */       } else if (this.mode.getValue() == modeEn.Cancel) {
/*  83 */         ((ISPacketExplosion)velocity_).setMotionX(0.0F);
/*  84 */         ((ISPacketExplosion)velocity_).setMotionY(0.0F);
/*  85 */         ((ISPacketExplosion)velocity_).setMotionZ(0.0F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  90 */     if (((Boolean)this.onlyAura.getValue()).booleanValue() && ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).isDisabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     if (this.mode.getValue() == modeEn.Cancel && event.getPacket() instanceof SPacketEntityVelocity) {
/*  95 */       SPacketEntityVelocity pac = (SPacketEntityVelocity)event.getPacket();
/*  96 */       if (pac.func_149412_c() == mc.field_71439_g.func_145782_y()) {
/*  97 */         event.setCanceled(true);
/*     */         return;
/*     */       } 
/*     */     } 
/* 101 */     if (this.mode.getValue() == modeEn.Custom) {
/*     */       SPacketEntityVelocity velocity;
/* 103 */       if (event.getPacket() instanceof SPacketEntityVelocity && (velocity = (SPacketEntityVelocity)event.getPacket()).func_149412_c() == mc.field_71439_g.func_145782_y()) {
/* 104 */         ((ISPacketEntityVelocity)velocity).setMotionX((int)(velocity.func_149411_d() * ((Float)this.horizontal.getValue()).floatValue() / 100.0F));
/* 105 */         ((ISPacketEntityVelocity)velocity).setMotionY((int)(velocity.func_149410_e() * ((Float)this.vertical.getValue()).floatValue() / 100.0F));
/* 106 */         ((ISPacketEntityVelocity)velocity).setMotionZ((int)(velocity.func_149409_f() * ((Float)this.horizontal.getValue()).floatValue() / 100.0F));
/*     */       } 
/*     */     } 
/* 109 */     if (this.mode.getValue() == modeEn.Matrix) {
/* 110 */       if (event.getPacket() instanceof SPacketEntityStatus) {
/* 111 */         SPacketEntityStatus var9 = (SPacketEntityStatus)event.getPacket();
/* 112 */         if (var9.func_149160_c() == 2 && var9.func_149161_a((World)mc.field_71441_e) == mc.field_71439_g) {
/* 113 */           this.flag = true;
/*     */         }
/*     */       } 
/*     */       
/* 117 */       if (event.getPacket() instanceof SPacketEntityVelocity) {
/* 118 */         SPacketEntityVelocity var4 = (SPacketEntityVelocity)event.getPacket();
/* 119 */         if (var4.func_149412_c() == mc.field_71439_g.func_145782_y()) {
/* 120 */           if (!this.flag) {
/* 121 */             event.setCanceled(true);
/*     */           } else {
/* 123 */             this.flag = false;
/* 124 */             ((ISPacketEntityVelocity)var4).setMotionX((int)(var4.func_149411_d() * -0.1D));
/* 125 */             ((ISPacketEntityVelocity)var4).setMotionZ((int)(var4.func_149409_f() * -0.1D));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPreMotion(EventSync var1) {
/* 134 */     if (this.mode.getValue() == modeEn.Matrix && 
/* 135 */       mc.field_71439_g.field_70737_aN > 0 && !mc.field_71439_g.field_70122_E) {
/* 136 */       double var3 = (mc.field_71439_g.field_70177_z * 0.017453292F);
/* 137 */       double var5 = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/* 138 */       mc.field_71439_g.field_70159_w = -Math.sin(var3) * var5;
/* 139 */       mc.field_71439_g.field_70179_y = Math.cos(var3) * var5;
/* 140 */       mc.field_71439_g.func_70031_b((mc.field_71439_g.field_70173_aa % 2 != 0));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPush(PushEvent event) {
/* 147 */     event.setCanceled(true);
/*     */   }
/*     */   
/*     */   public enum modeEn
/*     */   {
/* 152 */     Matrix, Cancel, Custom;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\Velocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */