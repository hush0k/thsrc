/*    */ package org.junit.rules;
/*    */ 
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
/*    */ public class Verifier
/*    */   implements TestRule
/*    */ {
/*    */   public Statement apply(final Statement base, Description description) {
/* 30 */     return new Statement()
/*    */       {
/*    */         public void evaluate() throws Throwable {
/* 33 */           base.evaluate();
/* 34 */           Verifier.this.verify();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected void verify() throws Throwable {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\Verifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */