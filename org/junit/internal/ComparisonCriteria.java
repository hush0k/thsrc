/*    */ package org.junit.internal;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import org.junit.Assert;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ComparisonCriteria
/*    */ {
/*    */   public void arrayEquals(String message, Object expecteds, Object actuals) throws ArrayComparisonFailure {
/* 30 */     if (expecteds == actuals)
/*    */       return; 
/* 32 */     String header = (message == null) ? "" : (message + ": ");
/*    */     
/* 34 */     int expectedsLength = assertArraysAreSameLength(expecteds, actuals, header);
/*    */ 
/*    */     
/* 37 */     for (int i = 0; i < expectedsLength; i++) {
/* 38 */       Object expected = Array.get(expecteds, i);
/* 39 */       Object actual = Array.get(actuals, i);
/*    */       
/* 41 */       if (isArray(expected) && isArray(actual)) {
/*    */         try {
/* 43 */           arrayEquals(message, expected, actual);
/* 44 */         } catch (ArrayComparisonFailure e) {
/* 45 */           e.addDimension(i);
/* 46 */           throw e;
/*    */         } 
/*    */       } else {
/*    */         try {
/* 50 */           assertElementsEqual(expected, actual);
/* 51 */         } catch (AssertionError e) {
/* 52 */           throw new ArrayComparisonFailure(header, e, i);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   private boolean isArray(Object expected) {
/* 58 */     return (expected != null && expected.getClass().isArray());
/*    */   }
/*    */ 
/*    */   
/*    */   private int assertArraysAreSameLength(Object expecteds, Object actuals, String header) {
/* 63 */     if (expecteds == null)
/* 64 */       Assert.fail(header + "expected array was null"); 
/* 65 */     if (actuals == null)
/* 66 */       Assert.fail(header + "actual array was null"); 
/* 67 */     int actualsLength = Array.getLength(actuals);
/* 68 */     int expectedsLength = Array.getLength(expecteds);
/* 69 */     if (actualsLength != expectedsLength) {
/* 70 */       Assert.fail(header + "array lengths differed, expected.length=" + expectedsLength + " actual.length=" + actualsLength);
/*    */     }
/* 72 */     return expectedsLength;
/*    */   }
/*    */   
/*    */   protected abstract void assertElementsEqual(Object paramObject1, Object paramObject2);
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\ComparisonCriteria.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */