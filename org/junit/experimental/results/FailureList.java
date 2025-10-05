/*    */ package org.junit.experimental.results;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.junit.runner.Result;
/*    */ import org.junit.runner.notification.Failure;
/*    */ import org.junit.runner.notification.RunListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class FailureList
/*    */ {
/*    */   private final List<Failure> failures;
/*    */   
/*    */   public FailureList(List<Failure> failures) {
/* 16 */     this.failures = failures;
/*    */   }
/*    */   
/*    */   public Result result() {
/* 20 */     Result result = new Result();
/* 21 */     RunListener listener = result.createListener();
/* 22 */     for (Failure failure : this.failures) {
/*    */       try {
/* 24 */         listener.testFailure(failure);
/* 25 */       } catch (Exception e) {
/* 26 */         throw new RuntimeException("I can't believe this happened");
/*    */       } 
/*    */     } 
/* 29 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\results\FailureList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */