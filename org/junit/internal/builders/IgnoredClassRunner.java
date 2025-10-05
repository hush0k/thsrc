/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ 
/*    */ 
/*    */ public class IgnoredClassRunner
/*    */   extends Runner
/*    */ {
/*    */   private final Class<?> fTestClass;
/*    */   
/*    */   public IgnoredClassRunner(Class<?> testClass) {
/* 14 */     this.fTestClass = testClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(RunNotifier notifier) {
/* 19 */     notifier.fireTestIgnored(getDescription());
/*    */   }
/*    */ 
/*    */   
/*    */   public Description getDescription() {
/* 24 */     return Description.createSuiteDescription(this.fTestClass);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\builders\IgnoredClassRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */