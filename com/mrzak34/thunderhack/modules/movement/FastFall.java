/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PostPlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class FastFall extends Module {
/*  14 */   private final Timer rubberbandTimer = new Timer();
/*  15 */   private final Timer strictTimer = new Timer();
/*  16 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(1.0F), Float.valueOf(3.0F), Float.valueOf(5.0F)));
/*  17 */   public Setting<Float> shiftTicks = register(new Setting("Height", Float.valueOf(2.0F), Float.valueOf(1.0F), Float.valueOf(2.5F)));
/*  18 */   public Setting<Float> height = register(new Setting("Height", Float.valueOf(0.0F), Float.valueOf(2.0F), Float.valueOf(10.0F)));
/*  19 */   public Setting<Boolean> webs = register(new Setting("Webs", Boolean.valueOf(false)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.MOTION));
/*     */   
/*     */   private boolean previousOnGround;
/*     */   
/*     */   public FastFall() {
/*  30 */     super("FastFall", "FastFall", Module.Category.MOVEMENT);
/*     */   }
/*     */   private int ticks; private boolean stop;
/*     */   
/*     */   public void onTick() {
/*  35 */     this.previousOnGround = mc.field_71439_g.field_70122_E;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  40 */     if (Jesus.isInLiquid() || mc.field_71439_g.func_191953_am() || mc.field_71439_g.field_71075_bZ.field_75100_b || mc.field_71439_g.func_184613_cA() || mc.field_71439_g.func_70617_f_()) {
/*     */       return;
/*     */     }
/*  43 */     if (((IEntity)mc.field_71439_g).isInWeb() && !((Boolean)this.webs.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*  46 */     if (mc.field_71474_y.field_74314_A.func_151470_d() || mc.field_71474_y.field_74311_E.func_151470_d()) {
/*     */       return;
/*     */     }
/*  49 */     if (!this.rubberbandTimer.passedMs(1000L)) {
/*     */       return;
/*     */     }
/*  52 */     if (mc.field_71439_g.field_70122_E && (
/*  53 */       (Mode)this.mode.getValue()).equals(Mode.MOTION)) {
/*  54 */       for (double fallHeight = 0.0D; fallHeight < ((Float)this.height.getValue()).floatValue() + 0.5D; fallHeight += 0.01D) {
/*  55 */         if (!mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -fallHeight, 0.0D)).isEmpty()) {
/*  56 */           mc.field_71439_g.field_70181_x = -((Float)this.speed.getValue()).floatValue();
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostPlayerUpdate(PostPlayerUpdateEvent event) {
/*  66 */     if (Jesus.isInLiquid() || mc.field_71439_g.func_191953_am() || mc.field_71439_g.field_71075_bZ.field_75100_b || mc.field_71439_g.func_184613_cA() || mc.field_71439_g.func_70617_f_()) {
/*     */       return;
/*     */     }
/*  69 */     if (((IEntity)mc.field_71439_g).isInWeb() && !((Boolean)this.webs.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*  72 */     if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/*     */       return;
/*     */     }
/*  75 */     if (!this.rubberbandTimer.passedMs(1000L)) {
/*     */       return;
/*     */     }
/*  78 */     if (((Mode)this.mode.getValue()).equals(Mode.PACKET)) {
/*  79 */       event.setCanceled(true);
/*  80 */       if (mc.field_71439_g.field_70181_x < 0.0D && this.previousOnGround && !mc.field_71439_g.field_70122_E) {
/*  81 */         for (double fallHeight = 0.0D; fallHeight < ((Float)this.height.getValue()).floatValue() + 0.5D; fallHeight += 0.01D) {
/*  82 */           if (!mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -fallHeight, 0.0D)).isEmpty() && 
/*  83 */             this.strictTimer.passedMs(1000L)) {
/*  84 */             mc.field_71439_g.field_70159_w = 0.0D;
/*  85 */             mc.field_71439_g.field_70179_y = 0.0D;
/*  86 */             event.setIterations(((Float)this.shiftTicks.getValue()).intValue());
/*  87 */             this.stop = true;
/*  88 */             this.ticks = 0;
/*  89 */             this.strictTimer.reset();
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMotion(EventMove event) {
/* 100 */     if (((Mode)this.mode.getValue()).equals(Mode.PACKET) && this.stop) {
/* 101 */       event.setCanceled(true);
/* 102 */       event.setX(0.0D);
/* 103 */       event.setZ(0.0D);
/* 104 */       mc.field_71439_g.field_70159_w = 0.0D;
/* 105 */       mc.field_71439_g.field_70179_y = 0.0D;
/* 106 */       this.ticks++;
/* 107 */       if (this.ticks > ((Float)this.shiftTicks.getValue()).floatValue()) {
/* 108 */         this.stop = false;
/* 109 */         this.ticks = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 117 */     if (fullNullCheck())
/* 118 */       return;  if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook)
/* 119 */       this.rubberbandTimer.reset(); 
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 124 */     MOTION,
/* 125 */     PACKET;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\FastFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */