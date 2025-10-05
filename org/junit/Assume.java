/*    */ package org.junit;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.hamcrest.CoreMatchers;
/*    */ import org.hamcrest.Matcher;
/*    */ import org.junit.internal.AssumptionViolatedException;
/*    */ import org.junit.internal.matchers.Each;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Assume
/*    */ {
/*    */   public static void assumeTrue(boolean b) {
/* 39 */     assumeThat(Boolean.valueOf(b), CoreMatchers.is(Boolean.valueOf(true)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void assumeNotNull(Object... objects) {
/* 47 */     assumeThat(Arrays.asList(objects), Each.each(CoreMatchers.notNullValue()));
/*    */   }
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
/*    */   public static <T> void assumeThat(T actual, Matcher<T> matcher) {
/* 69 */     if (!matcher.matches(actual)) {
/* 70 */       throw new AssumptionViolatedException(actual, matcher);
/*    */     }
/*    */   }
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
/*    */   public static void assumeNoException(Throwable t) {
/* 92 */     assumeThat(t, CoreMatchers.nullValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\Assume.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */