/*    */ package com.mrzak34.thunderhack.util.math;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicAnimation
/*    */ {
/*    */   private final double speed;
/*    */   private double startValue;
/*    */   private double targetValue;
/*    */   
/*    */   public DynamicAnimation(double speed) {
/* 15 */     this.speed = 0.15000000596046448D + speed;
/*    */   }
/*    */   private double outValue; private double step; private double prevStep; private double delta;
/*    */   public DynamicAnimation() {
/* 19 */     this(0.0D);
/*    */   }
/*    */   
/*    */   public static double createAnimation(double value) {
/* 23 */     return Math.sqrt(1.0D - Math.pow(value - 1.0D, 2.0D));
/*    */   }
/*    */   
/*    */   public void update() {
/* 27 */     this.prevStep = this.step;
/* 28 */     this.step = MathHelper.func_151237_a(this.step + this.speed, 0.0D, 1.0D);
/* 29 */     this.outValue = this.startValue + this.delta * createAnimation(this.step);
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 33 */     return this.startValue + this.delta * createAnimation(this.prevStep + (this.step - this.prevStep) * 
/* 34 */         Minecraft.func_71410_x().func_184121_ak());
/*    */   }
/*    */   
/*    */   public void setValue(double value) {
/* 38 */     if (value == this.targetValue)
/*    */       return; 
/* 40 */     this.targetValue = value;
/* 41 */     this.startValue = this.outValue;
/* 42 */     this.prevStep = 0.0D;
/* 43 */     this.step = 0.0D;
/* 44 */     this.delta = this.targetValue - this.startValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\math\DynamicAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */