/*    */ package org.junit.rules;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.Callable;
/*    */ import org.hamcrest.Matcher;
/*    */ import org.junit.Assert;
/*    */ import org.junit.runners.model.MultipleFailureException;
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
/*    */ public class ErrorCollector
/*    */   extends Verifier
/*    */ {
/* 36 */   private List<Throwable> errors = new ArrayList<Throwable>();
/*    */ 
/*    */   
/*    */   protected void verify() throws Throwable {
/* 40 */     MultipleFailureException.assertEmpty(this.errors);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addError(Throwable error) {
/* 47 */     this.errors.add(error);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> void checkThat(T value, Matcher<T> matcher) {
/* 55 */     checkThat("", value, matcher);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> void checkThat(final String reason, final T value, final Matcher<T> matcher) {
/* 64 */     checkSucceeds(new Callable() {
/*    */           public Object call() throws Exception {
/* 66 */             Assert.assertThat(reason, value, matcher);
/* 67 */             return value;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object checkSucceeds(Callable<Object> callable) {
/*    */     try {
/* 79 */       return callable.call();
/* 80 */     } catch (Throwable e) {
/* 81 */       addError(e);
/* 82 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\ErrorCollector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */