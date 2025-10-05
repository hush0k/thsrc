/*    */ package org.junit.internal;
/*    */ 
/*    */ import org.hamcrest.Description;
/*    */ import org.hamcrest.Matcher;
/*    */ import org.hamcrest.SelfDescribing;
/*    */ import org.hamcrest.StringDescription;
/*    */ 
/*    */ public class AssumptionViolatedException
/*    */   extends RuntimeException
/*    */   implements SelfDescribing {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final Object fValue;
/*    */   private final Matcher<?> fMatcher;
/*    */   
/*    */   public AssumptionViolatedException(Object value, Matcher<?> matcher) {
/* 16 */     super((value instanceof Throwable) ? (Throwable)value : null);
/* 17 */     this.fValue = value;
/* 18 */     this.fMatcher = matcher;
/*    */   }
/*    */   
/*    */   public AssumptionViolatedException(String assumption) {
/* 22 */     this(assumption, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 27 */     return StringDescription.asString(this);
/*    */   }
/*    */   
/*    */   public void describeTo(Description description) {
/* 31 */     if (this.fMatcher != null) {
/* 32 */       description.appendText("got: ");
/* 33 */       description.appendValue(this.fValue);
/* 34 */       description.appendText(", expected: ");
/* 35 */       description.appendDescriptionOf((SelfDescribing)this.fMatcher);
/*    */     } else {
/* 37 */       description.appendText("failed assumption: " + this.fValue);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\AssumptionViolatedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */