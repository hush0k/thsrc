/*    */ package org.junit.rules;
/*    */ 
/*    */ import org.junit.internal.AssumptionViolatedException;
/*    */ import org.junit.runners.model.FrameworkMethod;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class TestWatchman
/*    */   implements MethodRule
/*    */ {
/*    */   public Statement apply(final Statement base, final FrameworkMethod method, Object target) {
/* 48 */     return new Statement()
/*    */       {
/*    */         public void evaluate() throws Throwable {
/* 51 */           TestWatchman.this.starting(method);
/*    */           try {
/* 53 */             base.evaluate();
/* 54 */             TestWatchman.this.succeeded(method);
/* 55 */           } catch (AssumptionViolatedException e) {
/* 56 */             throw e;
/* 57 */           } catch (Throwable t) {
/* 58 */             TestWatchman.this.failed(t, method);
/* 59 */             throw t;
/*    */           } finally {
/* 61 */             TestWatchman.this.finished(method);
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public void succeeded(FrameworkMethod method) {}
/*    */   
/*    */   public void failed(Throwable e, FrameworkMethod method) {}
/*    */   
/*    */   public void starting(FrameworkMethod method) {}
/*    */   
/*    */   public void finished(FrameworkMethod method) {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\TestWatchman.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */