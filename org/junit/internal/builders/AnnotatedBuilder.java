/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import org.junit.runner.RunWith;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.InitializationError;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnnotatedBuilder
/*    */   extends RunnerBuilder
/*    */ {
/*    */   private static final String CONSTRUCTOR_ERROR_FORMAT = "Custom runner class %s should have a public constructor with signature %s(Class testClass)";
/*    */   private RunnerBuilder fSuiteBuilder;
/*    */   
/*    */   public AnnotatedBuilder(RunnerBuilder suiteBuilder) {
/* 17 */     this.fSuiteBuilder = suiteBuilder;
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner runnerForClass(Class<?> testClass) throws Exception {
/* 22 */     RunWith annotation = testClass.<RunWith>getAnnotation(RunWith.class);
/* 23 */     if (annotation != null)
/* 24 */       return buildRunner(annotation.value(), testClass); 
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner buildRunner(Class<? extends Runner> runnerClass, Class<?> testClass) throws Exception {
/*    */     try {
/* 31 */       return runnerClass.getConstructor(new Class[] { Class.class }).newInstance(new Object[] { testClass });
/*    */     }
/* 33 */     catch (NoSuchMethodException e) {
/*    */       try {
/* 35 */         return runnerClass.getConstructor(new Class[] { Class.class, RunnerBuilder.class }).newInstance(new Object[] { testClass, this.fSuiteBuilder });
/*    */       
/*    */       }
/* 38 */       catch (NoSuchMethodException e2) {
/* 39 */         String simpleName = runnerClass.getSimpleName();
/* 40 */         throw new InitializationError(String.format("Custom runner class %s should have a public constructor with signature %s(Class testClass)", new Object[] { simpleName, simpleName }));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\builders\AnnotatedBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */