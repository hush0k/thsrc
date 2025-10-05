/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import org.junit.Ignore;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IgnoredBuilder
/*    */   extends RunnerBuilder
/*    */ {
/*    */   public Runner runnerForClass(Class<?> testClass) {
/* 13 */     if (testClass.getAnnotation(Ignore.class) != null)
/* 14 */       return new IgnoredClassRunner(testClass); 
/* 15 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\builders\IgnoredBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */