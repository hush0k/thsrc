/*    */ package org.junit.rules;
/*    */ 
/*    */ import org.junit.internal.AssumptionViolatedException;
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
/*    */ 
/*    */ public abstract class TestWatcher
/*    */   implements TestRule
/*    */ {
/*    */   public Statement apply(final Statement base, final Description description) {
/* 42 */     return new Statement()
/*    */       {
/*    */         public void evaluate() throws Throwable {
/* 45 */           TestWatcher.this.starting(description);
/*    */           try {
/* 47 */             base.evaluate();
/* 48 */             TestWatcher.this.succeeded(description);
/* 49 */           } catch (AssumptionViolatedException e) {
/* 50 */             throw e;
/* 51 */           } catch (Throwable t) {
/* 52 */             TestWatcher.this.failed(t, description);
/* 53 */             throw t;
/*    */           } finally {
/* 55 */             TestWatcher.this.finished(description);
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   protected void succeeded(Description description) {}
/*    */   
/*    */   protected void failed(Throwable e, Description description) {}
/*    */   
/*    */   protected void starting(Description description) {}
/*    */   
/*    */   protected void finished(Description description) {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\TestWatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */