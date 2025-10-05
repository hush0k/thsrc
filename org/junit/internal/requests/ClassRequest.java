/*    */ package org.junit.internal.requests;
/*    */ 
/*    */ import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
/*    */ import org.junit.runner.Request;
/*    */ import org.junit.runner.Runner;
/*    */ 
/*    */ public class ClassRequest
/*    */   extends Request
/*    */ {
/*    */   private final Class<?> fTestClass;
/*    */   private boolean fCanUseSuiteMethod;
/*    */   
/*    */   public ClassRequest(Class<?> testClass, boolean canUseSuiteMethod) {
/* 14 */     this.fTestClass = testClass;
/* 15 */     this.fCanUseSuiteMethod = canUseSuiteMethod;
/*    */   }
/*    */   
/*    */   public ClassRequest(Class<?> testClass) {
/* 19 */     this(testClass, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner getRunner() {
/* 24 */     return (new AllDefaultPossibilitiesBuilder(this.fCanUseSuiteMethod)).safeRunnerForClass(this.fTestClass);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\requests\ClassRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */