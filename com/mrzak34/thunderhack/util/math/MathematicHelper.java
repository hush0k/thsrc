/*    */ package com.mrzak34.thunderhack.util.math;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.RoundingMode;
/*    */ 
/*    */ public class MathematicHelper
/*    */ {
/*    */   public static BigDecimal round(float f, int times) {
/*  9 */     BigDecimal bd = new BigDecimal(Float.toString(f));
/* 10 */     bd = bd.setScale(times, RoundingMode.HALF_UP);
/* 11 */     return bd;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float abs(float num) {
/* 16 */     return (num < 0.0F) ? (0.0F - num) : num;
/*    */   }
/*    */   
/*    */   public static double round(double num, double increment) {
/* 20 */     double v = Math.round(num / increment) * increment;
/* 21 */     BigDecimal bd = new BigDecimal(v);
/* 22 */     bd = bd.setScale(3, RoundingMode.HALF_UP);
/* 23 */     return bd.doubleValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static float clamp(float val, float min, float max) {
/* 28 */     if (val <= min) {
/* 29 */       val = min;
/*    */     }
/* 31 */     if (val >= max) {
/* 32 */       val = max;
/*    */     }
/* 34 */     return val;
/*    */   }
/*    */   
/*    */   public static float randomizeFloat(float startInclusive, float endInclusive) {
/* 38 */     if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0F) {
/* 39 */       return startInclusive;
/*    */     }
/* 41 */     return (float)(startInclusive + (endInclusive - startInclusive) * Math.random());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\math\MathematicHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */