/*    */ package org.junit.rules;
/*    */ 
/*    */ import org.junit.internal.runners.statements.FailOnTimeout;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runners.model.Statement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Timeout
/*    */   implements TestRule
/*    */ {
/*    */   private final int fMillis;
/*    */   
/*    */   public Timeout(int millis) {
/* 43 */     this.fMillis = millis;
/*    */   }
/*    */   
/*    */   public Statement apply(Statement base, Description description) {
/* 47 */     return (Statement)new FailOnTimeout(base, this.fMillis);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\Timeout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */