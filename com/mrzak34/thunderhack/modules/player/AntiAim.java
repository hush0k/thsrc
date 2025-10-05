/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ 
/*    */ public class AntiAim extends Module {
/*    */   private final Setting<Mode> pitchMode;
/*    */   private final Setting<Mode> yawMode;
/*    */   public Setting<Integer> Speed;
/*    */   public Setting<Integer> yawDelta;
/*    */   public Setting<Integer> pitchDelta;
/*    */   
/*    */   public AntiAim() {
/* 14 */     super("AntiAim", "утро 1 января", "can break CA predict", Module.Category.PLAYER);
/*    */ 
/*    */     
/* 17 */     this.pitchMode = register(new Setting("PitchMode", Mode.None));
/* 18 */     this.yawMode = register(new Setting("YawMode", Mode.None));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 23 */     this.Speed = register(new Setting("Speed", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(45)));
/* 24 */     this.yawDelta = register(new Setting("YawDelta", Integer.valueOf(60), Integer.valueOf(-360), Integer.valueOf(360)));
/* 25 */     this.pitchDelta = register(new Setting("PitchDelta", Integer.valueOf(10), Integer.valueOf(-90), Integer.valueOf(90)));
/* 26 */     this.bodySync = register(new Setting("BodySync", Boolean.valueOf(true)));
/* 27 */     this.allowInteract = register(new Setting("AllowInteract", Boolean.valueOf(true)));
/*    */   }
/*    */   public final Setting<Boolean> bodySync; public final Setting<Boolean> allowInteract; private float rotationYaw; private float rotationPitch; private float pitch_sinus_step; private float yaw_sinus_step;
/*    */   public enum Mode {
/*    */     None, RandomAngle, Spin, Sinus, Fixed, Static; }
/*    */   @SubscribeEvent(priority = EventPriority.HIGH)
/*    */   public void onSync(EventSync e) {
/* 34 */     if (((Boolean)this.allowInteract.getValue()).booleanValue() && (mc.field_71474_y.field_74312_F.func_151470_d() || mc.field_71474_y.field_74313_G.func_151470_d()))
/* 35 */       return;  if (this.yawMode.getValue() != Mode.None) {
/* 36 */       mc.field_71439_g.field_70177_z = this.rotationYaw;
/* 37 */       if (((Boolean)this.bodySync.getValue()).booleanValue())
/* 38 */         mc.field_71439_g.field_70761_aq = this.rotationYaw; 
/*    */     } 
/* 40 */     if (this.pitchMode.getValue() != Mode.None)
/* 41 */       mc.field_71439_g.field_70125_A = this.rotationPitch; 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onCalc(PlayerUpdateEvent e) {
/* 46 */     if (this.pitchMode.getValue() == Mode.RandomAngle && 
/* 47 */       mc.field_71439_g.field_70173_aa % ((Integer)this.Speed.getValue()).intValue() == 0) {
/* 48 */       this.rotationPitch = MathUtil.random(90.0F, -90.0F);
/*    */     }
/* 50 */     if (this.yawMode.getValue() == Mode.RandomAngle && 
/* 51 */       mc.field_71439_g.field_70173_aa % ((Integer)this.Speed.getValue()).intValue() == 0) {
/* 52 */       this.rotationYaw = MathUtil.random(0.0F, 360.0F);
/*    */     }
/* 54 */     if (this.yawMode.getValue() == Mode.Spin && 
/* 55 */       mc.field_71439_g.field_70173_aa % ((Integer)this.Speed.getValue()).intValue() == 0) {
/* 56 */       this.rotationYaw += ((Integer)this.yawDelta.getValue()).intValue();
/* 57 */       if (this.rotationYaw > 360.0F) this.rotationYaw = 0.0F; 
/* 58 */       if (this.rotationYaw < 0.0F) this.rotationYaw = 360.0F;
/*    */     
/*    */     } 
/* 61 */     if (this.pitchMode.getValue() == Mode.Spin && 
/* 62 */       mc.field_71439_g.field_70173_aa % ((Integer)this.Speed.getValue()).intValue() == 0) {
/* 63 */       this.rotationPitch += ((Integer)this.pitchDelta.getValue()).intValue();
/* 64 */       if (this.rotationPitch > 90.0F) this.rotationPitch = -90.0F; 
/* 65 */       if (this.rotationPitch < -90.0F) this.rotationPitch = 90.0F;
/*    */     
/*    */     } 
/* 68 */     if (this.pitchMode.getValue() == Mode.Sinus) {
/* 69 */       this.pitch_sinus_step += ((Integer)this.Speed.getValue()).intValue() / 10.0F;
/* 70 */       this.rotationPitch = (float)(mc.field_71439_g.field_70125_A + ((Integer)this.pitchDelta.getValue()).intValue() * Math.sin(this.pitch_sinus_step));
/* 71 */       this.rotationPitch = MathUtil.clamp(this.rotationPitch, -90.0F, 90.0F);
/*    */     } 
/*    */     
/* 74 */     if (this.yawMode.getValue() == Mode.Sinus) {
/* 75 */       this.yaw_sinus_step += ((Integer)this.Speed.getValue()).intValue() / 10.0F;
/* 76 */       this.rotationYaw = (float)(mc.field_71439_g.field_70177_z + ((Integer)this.yawDelta.getValue()).intValue() * Math.sin(this.yaw_sinus_step));
/*    */     } 
/*    */     
/* 79 */     if (this.pitchMode.getValue() == Mode.Fixed) {
/* 80 */       this.rotationPitch = ((Integer)this.pitchDelta.getValue()).intValue();
/*    */     }
/* 82 */     if (this.yawMode.getValue() == Mode.Fixed) {
/* 83 */       this.rotationYaw = ((Integer)this.yawDelta.getValue()).intValue();
/*    */     }
/* 85 */     if (this.pitchMode.getValue() == Mode.Static) {
/* 86 */       this.rotationPitch = mc.field_71439_g.field_70125_A + ((Integer)this.pitchDelta.getValue()).intValue();
/* 87 */       this.rotationPitch = MathUtil.clamp(this.rotationPitch, -90.0F, 90.0F);
/*    */     } 
/* 89 */     if (this.yawMode.getValue() == Mode.Static)
/* 90 */       this.rotationYaw = mc.field_71439_g.field_70177_z % 360.0F + ((Integer)this.yawDelta.getValue()).intValue(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\AntiAim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */