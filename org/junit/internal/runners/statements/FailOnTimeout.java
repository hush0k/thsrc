/*    */ package org.junit.internal.runners.statements;
/*    */ 
/*    */ import org.junit.runners.model.Statement;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FailOnTimeout
/*    */   extends Statement
/*    */ {
/*    */   private final Statement fOriginalStatement;
/*    */   private final long fTimeout;
/*    */   
/*    */   public FailOnTimeout(Statement originalStatement, long timeout) {
/* 14 */     this.fOriginalStatement = originalStatement;
/* 15 */     this.fTimeout = timeout;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evaluate() throws Throwable {
/* 20 */     StatementThread thread = evaluateStatement();
/* 21 */     if (!thread.fFinished)
/* 22 */       throwExceptionForUnfinishedThread(thread); 
/*    */   }
/*    */   
/*    */   private StatementThread evaluateStatement() throws InterruptedException {
/* 26 */     StatementThread thread = new StatementThread(this.fOriginalStatement);
/* 27 */     thread.start();
/* 28 */     thread.join(this.fTimeout);
/* 29 */     thread.interrupt();
/* 30 */     return thread;
/*    */   }
/*    */ 
/*    */   
/*    */   private void throwExceptionForUnfinishedThread(StatementThread thread) throws Throwable {
/* 35 */     if (thread.fExceptionThrownByOriginalStatement != null) {
/* 36 */       throw thread.fExceptionThrownByOriginalStatement;
/*    */     }
/* 38 */     throwTimeoutException(thread);
/*    */   }
/*    */   
/*    */   private void throwTimeoutException(StatementThread thread) throws Exception {
/* 42 */     Exception exception = new Exception(String.format("test timed out after %d milliseconds", new Object[] { Long.valueOf(this.fTimeout) }));
/*    */     
/* 44 */     exception.setStackTrace(thread.getStackTrace());
/* 45 */     throw exception;
/*    */   }
/*    */   
/*    */   private static class StatementThread
/*    */     extends Thread
/*    */   {
/*    */     private final Statement fStatement;
/*    */     private boolean fFinished = false;
/* 53 */     private Throwable fExceptionThrownByOriginalStatement = null;
/*    */     
/*    */     public StatementThread(Statement statement) {
/* 56 */       this.fStatement = statement;
/*    */     }
/*    */ 
/*    */     
/*    */     public void run() {
/*    */       try {
/* 62 */         this.fStatement.evaluate();
/* 63 */         this.fFinished = true;
/* 64 */       } catch (InterruptedException e) {
/*    */       
/* 66 */       } catch (Throwable e) {
/* 67 */         this.fExceptionThrownByOriginalStatement = e;
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\statements\FailOnTimeout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */