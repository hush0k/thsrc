/*    */ package com.mrzak34.thunderhack.util.shaders;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class BetterAnimation {
/*    */   private int prevTick;
/*    */   private int tick;
/*    */   private final int maxTick;
/*    */   
/*    */   public BetterAnimation(int maxTick) {
/* 12 */     this.maxTick = maxTick;
/*    */   }
/*    */   
/*    */   public BetterAnimation() {
/* 16 */     this(10);
/*    */   }
/*    */   
/*    */   public static double dropAnimation(double value) {
/* 20 */     double c1 = 1.70158D;
/* 21 */     double c3 = 2.70158D;
/* 22 */     return 1.0D + c3 * Math.pow(value - 1.0D, 3.0D) + c1 * Math.pow(value - 1.0D, 2.0D);
/*    */   }
/*    */   
/*    */   public void update(boolean update) {
/* 26 */     this.prevTick = this.tick;
/* 27 */     this.tick = MathHelper.func_76125_a(this.tick + (update ? 1 : -1), 0, this.maxTick);
/*    */   }
/*    */   
/*    */   public double getAnimationd() {
/* 31 */     return dropAnimation(((this.prevTick + (this.tick - this.prevTick) * Minecraft.func_71410_x().func_184121_ak()) / this.maxTick));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\shaders\BetterAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */