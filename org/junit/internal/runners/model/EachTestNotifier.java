/*    */ package org.junit.internal.runners.model;
/*    */ 
/*    */ import org.junit.internal.AssumptionViolatedException;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.notification.Failure;
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ import org.junit.runners.model.MultipleFailureException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EachTestNotifier
/*    */ {
/*    */   private final RunNotifier fNotifier;
/*    */   private final Description fDescription;
/*    */   
/*    */   public EachTestNotifier(RunNotifier notifier, Description description) {
/* 18 */     this.fNotifier = notifier;
/* 19 */     this.fDescription = description;
/*    */   }
/*    */   
/*    */   public void addFailure(Throwable targetException) {
/* 23 */     if (targetException instanceof MultipleFailureException) {
/* 24 */       addMultipleFailureException((MultipleFailureException)targetException);
/*    */     } else {
/* 26 */       this.fNotifier.fireTestFailure(new Failure(this.fDescription, targetException));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void addMultipleFailureException(MultipleFailureException mfe) {
/* 32 */     for (Throwable each : mfe.getFailures())
/* 33 */       addFailure(each); 
/*    */   }
/*    */   
/*    */   public void addFailedAssumption(AssumptionViolatedException e) {
/* 37 */     this.fNotifier.fireTestAssumptionFailed(new Failure(this.fDescription, (Throwable)e));
/*    */   }
/*    */   
/*    */   public void fireTestFinished() {
/* 41 */     this.fNotifier.fireTestFinished(this.fDescription);
/*    */   }
/*    */   
/*    */   public void fireTestStarted() {
/* 45 */     this.fNotifier.fireTestStarted(this.fDescription);
/*    */   }
/*    */   
/*    */   public void fireTestIgnored() {
/* 49 */     this.fNotifier.fireTestIgnored(this.fDescription);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\model\EachTestNotifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */