/*    */ package org.junit.internal.matchers;
/*    */ 
/*    */ import org.hamcrest.BaseMatcher;
/*    */ import org.hamcrest.CoreMatchers;
/*    */ import org.hamcrest.Description;
/*    */ import org.hamcrest.Matcher;
/*    */ import org.hamcrest.SelfDescribing;
/*    */ 
/*    */ public class CombinableMatcher<T>
/*    */   extends BaseMatcher<T> {
/*    */   private final Matcher<? extends T> fMatcher;
/*    */   
/*    */   public CombinableMatcher(Matcher<? extends T> matcher) {
/* 14 */     this.fMatcher = matcher;
/*    */   }
/*    */   
/*    */   public boolean matches(Object item) {
/* 18 */     return this.fMatcher.matches(item);
/*    */   }
/*    */   
/*    */   public void describeTo(Description description) {
/* 22 */     description.appendDescriptionOf((SelfDescribing)this.fMatcher);
/*    */   }
/*    */ 
/*    */   
/*    */   public CombinableMatcher<T> and(Matcher<? extends T> matcher) {
/* 27 */     return new CombinableMatcher(CoreMatchers.allOf(new Matcher[] { matcher, this.fMatcher }));
/*    */   }
/*    */ 
/*    */   
/*    */   public CombinableMatcher<T> or(Matcher<? extends T> matcher) {
/* 32 */     return new CombinableMatcher(CoreMatchers.anyOf(new Matcher[] { matcher, this.fMatcher }));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\matchers\CombinableMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */