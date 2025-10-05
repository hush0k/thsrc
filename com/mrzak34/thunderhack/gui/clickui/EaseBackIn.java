/*    */ package com.mrzak34.thunderhack.gui.clickui;
/*    */ 
/*    */ import com.mrzak34.thunderhack.notification.Animation;
/*    */ import com.mrzak34.thunderhack.notification.Direction;
/*    */ 
/*    */ public class EaseBackIn extends Animation {
/*    */   private final float easeAmount;
/*    */   
/*    */   public EaseBackIn(int ms, double endPoint, float easeAmount) {
/* 10 */     super(ms, endPoint);
/* 11 */     this.easeAmount = easeAmount;
/*    */   }
/*    */   
/*    */   public EaseBackIn(int ms, double endPoint, float easeAmount, Direction direction) {
/* 15 */     super(ms, endPoint, direction);
/* 16 */     this.easeAmount = easeAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean correctOutput() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getEquation(double x) {
/* 26 */     double x1 = x / this.duration;
/* 27 */     float shrink = this.easeAmount + 1.0F;
/* 28 */     return Math.max(0.0D, 1.0D + shrink * Math.pow(x1 - 1.0D, 3.0D) + this.easeAmount * Math.pow(x1 - 1.0D, 2.0D));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\EaseBackIn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */