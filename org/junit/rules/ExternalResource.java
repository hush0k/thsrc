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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ExternalResource
/*    */   implements TestRule
/*    */ {
/*    */   public Statement apply(Statement base, Description description) {
/* 37 */     return statement(base);
/*    */   }
/*    */   
/*    */   private Statement statement(final Statement base) {
/* 41 */     return new Statement()
/*    */       {
/*    */         public void evaluate() throws Throwable {
/* 44 */           ExternalResource.this.before();
/*    */           try {
/* 46 */             base.evaluate();
/*    */           } finally {
/* 48 */             ExternalResource.this.after();
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected void before() throws Throwable {}
/*    */   
/*    */   protected void after() {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\ExternalResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */