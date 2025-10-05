/*    */ package org.junit.internal.runners.statements;
/*    */ 
/*    */ import org.junit.internal.AssumptionViolatedException;
/*    */ import org.junit.runners.model.Statement;
/*    */ 
/*    */ 
/*    */ public class ExpectException
/*    */   extends Statement
/*    */ {
/*    */   private Statement fNext;
/*    */   private final Class<? extends Throwable> fExpected;
/*    */   
/*    */   public ExpectException(Statement next, Class<? extends Throwable> expected) {
/* 14 */     this.fNext = next;
/* 15 */     this.fExpected = expected;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evaluate() throws Exception {
/* 20 */     boolean complete = false;
/*    */     try {
/* 22 */       this.fNext.evaluate();
/* 23 */       complete = true;
/* 24 */     } catch (AssumptionViolatedException e) {
/* 25 */       throw e;
/* 26 */     } catch (Throwable e) {
/* 27 */       if (!this.fExpected.isAssignableFrom(e.getClass())) {
/* 28 */         String message = "Unexpected exception, expected<" + this.fExpected.getName() + "> but was<" + e.getClass().getName() + ">";
/*    */ 
/*    */         
/* 31 */         throw new Exception(message, e);
/*    */       } 
/*    */     } 
/* 34 */     if (complete)
/* 35 */       throw new AssertionError("Expected exception: " + this.fExpected.getName()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\statements\ExpectException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */