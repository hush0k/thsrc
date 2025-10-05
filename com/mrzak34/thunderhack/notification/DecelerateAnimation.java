/*    */ package com.mrzak34.thunderhack.notification;
/*    */ 
/*    */ public class DecelerateAnimation
/*    */   extends Animation {
/*    */   public DecelerateAnimation(int ms, double endPoint) {
/*  6 */     super(ms, endPoint);
/*    */   }
/*    */   
/*    */   public DecelerateAnimation(int ms, double endPoint, Direction direction) {
/* 10 */     super(ms, endPoint, direction);
/*    */   }
/*    */   
/*    */   protected double getEquation(double x) {
/* 14 */     double x1 = x / this.duration;
/* 15 */     return 1.0D - (x1 - 1.0D) * (x1 - 1.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\notification\DecelerateAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */