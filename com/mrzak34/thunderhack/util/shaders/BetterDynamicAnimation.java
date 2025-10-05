/*    */ package com.mrzak34.thunderhack.util.shaders;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.math.DynamicAnimation;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ public class BetterDynamicAnimation
/*    */ {
/*    */   private final int maxTicks;
/*    */   private double value;
/*    */   
/*    */   public BetterDynamicAnimation(int maxTicks) {
/* 14 */     this.maxTicks = maxTicks;
/*    */   }
/*    */   private double dstValue; private int prevStep; private int step;
/*    */   public BetterDynamicAnimation() {
/* 18 */     this(5);
/*    */   }
/*    */   
/*    */   public void update() {
/* 22 */     this.prevStep = this.step;
/* 23 */     this.step = MathHelper.func_76125_a(this.step + 1, 0, this.maxTicks);
/*    */   }
/*    */   
/*    */   public void setValue(double value) {
/* 27 */     if (value != this.dstValue) {
/* 28 */       this.prevStep = 0;
/* 29 */       this.step = 0;
/* 30 */       this.value = this.dstValue;
/* 31 */       this.dstValue = value;
/*    */     } 
/*    */   }
/*    */   
/*    */   public double getAnimationD() {
/* 36 */     float pt = Minecraft.func_71410_x().func_184121_ak();
/* 37 */     double delta = this.dstValue - this.value;
/* 38 */     double animation = DynamicAnimation.createAnimation((this.prevStep + (this.step - this.prevStep) * pt) / this.maxTicks);
/* 39 */     return this.value + delta * animation;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\BetterDynamicAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */