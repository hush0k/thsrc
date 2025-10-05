/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import org.junit.internal.runners.SuiteMethod;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SuiteMethodBuilder
/*    */   extends RunnerBuilder
/*    */ {
/*    */   public Runner runnerForClass(Class<?> each) throws Throwable {
/* 13 */     if (hasSuiteMethod(each))
/* 14 */       return (Runner)new SuiteMethod(each); 
/* 15 */     return null;
/*    */   }
/*    */   
/*    */   public boolean hasSuiteMethod(Class<?> testClass) {
/*    */     try {
/* 20 */       testClass.getMethod("suite", new Class[0]);
/* 21 */     } catch (NoSuchMethodException e) {
/* 22 */       return false;
/*    */     } 
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\builders\SuiteMethodBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */