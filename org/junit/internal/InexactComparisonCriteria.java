/*    */ package org.junit.internal;
/*    */ 
/*    */ import org.junit.Assert;
/*    */ 
/*    */ public class InexactComparisonCriteria extends ComparisonCriteria {
/*    */   public double fDelta;
/*    */   
/*    */   public InexactComparisonCriteria(double delta) {
/*  9 */     this.fDelta = delta;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void assertElementsEqual(Object expected, Object actual) {
/* 14 */     if (expected instanceof Double) {
/* 15 */       Assert.assertEquals(((Double)expected).doubleValue(), ((Double)actual).doubleValue(), this.fDelta);
/*    */     } else {
/* 17 */       Assert.assertEquals(((Float)expected).floatValue(), ((Float)actual).floatValue(), this.fDelta);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\InexactComparisonCriteria.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */