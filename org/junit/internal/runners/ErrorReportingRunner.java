/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runner.notification.Failure;
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ import org.junit.runners.model.InitializationError;
/*    */ 
/*    */ 
/*    */ public class ErrorReportingRunner
/*    */   extends Runner
/*    */ {
/*    */   private final List<Throwable> fCauses;
/*    */   private final Class<?> fTestClass;
/*    */   
/*    */   public ErrorReportingRunner(Class<?> testClass, Throwable cause) {
/* 19 */     this.fTestClass = testClass;
/* 20 */     this.fCauses = getCauses(cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public Description getDescription() {
/* 25 */     Description description = Description.createSuiteDescription(this.fTestClass);
/* 26 */     for (Throwable each : this.fCauses)
/* 27 */       description.addChild(describeCause(each)); 
/* 28 */     return description;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(RunNotifier notifier) {
/* 33 */     for (Throwable each : this.fCauses) {
/* 34 */       runCause(each, notifier);
/*    */     }
/*    */   }
/*    */   
/*    */   private List<Throwable> getCauses(Throwable cause) {
/* 39 */     if (cause instanceof java.lang.reflect.InvocationTargetException)
/* 40 */       return getCauses(cause.getCause()); 
/* 41 */     if (cause instanceof InitializationError)
/* 42 */       return ((InitializationError)cause).getCauses(); 
/* 43 */     if (cause instanceof InitializationError) {
/* 44 */       return ((InitializationError)cause).getCauses();
/*    */     }
/* 46 */     return Arrays.asList(new Throwable[] { cause });
/*    */   }
/*    */   
/*    */   private Description describeCause(Throwable child) {
/* 50 */     return Description.createTestDescription(this.fTestClass, "initializationError");
/*    */   }
/*    */ 
/*    */   
/*    */   private void runCause(Throwable child, RunNotifier notifier) {
/* 55 */     Description description = describeCause(child);
/* 56 */     notifier.fireTestStarted(description);
/* 57 */     notifier.fireTestFailure(new Failure(description, child));
/* 58 */     notifier.fireTestFinished(description);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\ErrorReportingRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */