/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketPlayer;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Flight
/*     */   extends Module {
/*     */   public boolean pendingFlagApplyPacket = false;
/*  17 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Vanilla));
/*  18 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(0.1F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.mode.getValue() == Mode.Vanilla)));
/*  19 */   public Setting<Float> speedValue = register(new Setting("Speed", Float.valueOf(1.69F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.mode.getValue() == Mode.MatrixJump)));
/*  20 */   public Setting<Float> vspeedValue = register(new Setting("Vertical", Float.valueOf(0.78F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.mode.getValue() == Mode.MatrixJump)));
/*  21 */   public Setting<Boolean> spoofValue = register(new Setting("Ground", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.MatrixJump)));
/*  22 */   public Setting<Boolean> aboba = register(new Setting("AutoToggle", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.MatrixJump)));
/*  23 */   private double lastMotionX = 0.0D;
/*  24 */   private double lastMotionY = 0.0D;
/*  25 */   private double lastMotionZ = 0.0D;
/*     */ 
/*     */   
/*     */   public Flight() {
/*  29 */     super("Flight", "Makes you fly.", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayer(EventSync event) {
/*  34 */     if (this.mode.getValue() == Mode.Vanilla) {
/*     */       
/*  36 */       mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
/*  37 */       mc.field_71439_g.field_70747_aH = ((Float)this.speed.getValue()).floatValue();
/*  38 */       double[] dir = MathUtil.directionSpeed(((Float)this.speed.getValue()).floatValue());
/*  39 */       if (mc.field_71439_g.field_71158_b.field_78902_a != 0.0F || mc.field_71439_g.field_71158_b.field_192832_b != 0.0F) {
/*  40 */         mc.field_71439_g.field_70159_w = dir[0];
/*  41 */         mc.field_71439_g.field_70179_y = dir[1];
/*     */       } else {
/*  43 */         mc.field_71439_g.field_70159_w = 0.0D;
/*  44 */         mc.field_71439_g.field_70179_y = 0.0D;
/*     */       } 
/*  46 */       if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/*  47 */         mc.field_71439_g.field_70181_x += ((Float)this.speed.getValue()).floatValue();
/*     */       }
/*  49 */       if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/*  50 */         mc.field_71439_g.field_70181_x -= ((Float)this.speed.getValue()).floatValue();
/*     */       }
/*  52 */     } else if (this.mode.getValue() == Mode.AirJump && 
/*  53 */       MovementUtil.isMoving() && !mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72321_a(0.5D, 0.0D, 0.5D).func_72317_d(0.0D, -1.0D, 0.0D)).isEmpty()) {
/*  54 */       mc.field_71439_g.field_70122_E = true;
/*  55 */       mc.field_71439_g.func_70664_aZ();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  63 */     if (this.mode.getValue() != Mode.MatrixJump) {
/*     */       return;
/*     */     }
/*  66 */     mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/*     */     
/*  68 */     mc.field_71439_g.field_70159_w = 0.0D;
/*  69 */     mc.field_71439_g.field_70181_x = 0.0D;
/*  70 */     mc.field_71439_g.field_70179_y = 0.0D;
/*  71 */     if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/*  72 */       mc.field_71439_g.field_70181_x += ((Float)this.vspeedValue.getValue()).floatValue();
/*     */     }
/*  74 */     if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/*  75 */       mc.field_71439_g.field_70181_x -= ((Float)this.vspeedValue.getValue()).floatValue();
/*     */     }
/*  77 */     LongJump.strafe(((Float)this.speedValue.getValue()).floatValue());
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  82 */     if (this.mode.getValue() != Mode.MatrixJump) {
/*     */       return;
/*     */     }
/*  85 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*  88 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/*  89 */       this.pendingFlagApplyPacket = true;
/*  90 */       this.lastMotionX = mc.field_71439_g.field_70159_w;
/*  91 */       this.lastMotionY = mc.field_71439_g.field_70181_x;
/*  92 */       this.lastMotionZ = mc.field_71439_g.field_70179_y;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send e) {
/*  98 */     if (this.mode.getValue() == Mode.MatrixJump) {
/*     */       
/* 100 */       if (e.getPacket() instanceof CPacketPlayer.PositionRotation && 
/* 101 */         this.pendingFlagApplyPacket) {
/* 102 */         mc.field_71439_g.field_70159_w = this.lastMotionX;
/* 103 */         mc.field_71439_g.field_70181_x = this.lastMotionY;
/* 104 */         mc.field_71439_g.field_70179_y = this.lastMotionZ;
/* 105 */         this.pendingFlagApplyPacket = false;
/* 106 */         if (((Boolean)this.aboba.getValue()).booleanValue()) {
/* 107 */           toggle();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 112 */       if (e.getPacket() instanceof CPacketPlayer) {
/* 113 */         CPacketPlayer packet = (CPacketPlayer)e.getPacket();
/* 114 */         if (((Boolean)this.spoofValue.getValue()).booleanValue()) {
/* 115 */           ((ICPacketPlayer)packet).setOnGround(true);
/*     */         }
/*     */       } 
/* 118 */     } else if (this.mode.getValue() == Mode.AirJump) {
/* 119 */       if (fullNullCheck()) {
/*     */         return;
/*     */       }
/* 122 */       if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook)
/* 123 */         toggle(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum Mode
/*     */   {
/* 129 */     Vanilla, MatrixJump, AirJump;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Flight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */