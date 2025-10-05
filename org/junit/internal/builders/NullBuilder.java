/*    */ package org.junit.internal.builders;
/*    */ 
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullBuilder
/*    */   extends RunnerBuilder
/*    */ {
/*    */   public Runner runnerForClass(Class<?> each) throws Throwable {
/* 12 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\builders\NullBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */