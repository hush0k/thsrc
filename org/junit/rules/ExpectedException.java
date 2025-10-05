/*     */ package org.junit.rules;
/*     */ 
/*     */ import org.hamcrest.CoreMatchers;
/*     */ import org.hamcrest.Description;
/*     */ import org.hamcrest.Matcher;
/*     */ import org.hamcrest.SelfDescribing;
/*     */ import org.hamcrest.StringDescription;
/*     */ import org.junit.Assert;
/*     */ import org.junit.internal.matchers.TypeSafeMatcher;
/*     */ import org.junit.matchers.JUnitMatchers;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runners.model.Statement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpectedException
/*     */   implements TestRule
/*     */ {
/*     */   public static ExpectedException none() {
/*  50 */     return new ExpectedException();
/*     */   }
/*     */   
/*  53 */   private Matcher<Object> fMatcher = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement apply(Statement base, Description description) {
/*  61 */     return new ExpectedExceptionStatement(base);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expect(Matcher<?> matcher) {
/*  70 */     if (this.fMatcher == null) {
/*  71 */       this.fMatcher = (Matcher)matcher;
/*     */     } else {
/*  73 */       this.fMatcher = (Matcher<Object>)JUnitMatchers.both(this.fMatcher).and(matcher);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expect(Class<? extends Throwable> type) {
/*  81 */     expect(CoreMatchers.instanceOf(type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expectMessage(String substring) {
/*  89 */     expectMessage(JUnitMatchers.containsString(substring));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void expectMessage(Matcher<String> matcher) {
/*  97 */     expect(hasMessage(matcher));
/*     */   }
/*     */   
/*     */   private class ExpectedExceptionStatement extends Statement {
/*     */     private final Statement fNext;
/*     */     
/*     */     public ExpectedExceptionStatement(Statement base) {
/* 104 */       this.fNext = base;
/*     */     }
/*     */ 
/*     */     
/*     */     public void evaluate() throws Throwable {
/*     */       try {
/* 110 */         this.fNext.evaluate();
/* 111 */       } catch (Throwable e) {
/* 112 */         if (ExpectedException.this.fMatcher == null)
/* 113 */           throw e; 
/* 114 */         Assert.assertThat(e, ExpectedException.this.fMatcher);
/*     */         return;
/*     */       } 
/* 117 */       if (ExpectedException.this.fMatcher != null) {
/* 118 */         throw new AssertionError("Expected test to throw " + StringDescription.toString(ExpectedException.this.fMatcher));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private Matcher<Throwable> hasMessage(final Matcher<String> matcher) {
/* 124 */     return (Matcher<Throwable>)new TypeSafeMatcher<Throwable>() {
/*     */         public void describeTo(Description description) {
/* 126 */           description.appendText("exception with message ");
/* 127 */           description.appendDescriptionOf((SelfDescribing)matcher);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean matchesSafely(Throwable item) {
/* 132 */           return matcher.matches(item.getMessage());
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\ExpectedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */