/*    */ package org.junit.internal.runners.statements;
/*    */ 
/*    */ import org.junit.runners.model.Statement;
/*    */ 
/*    */ public class Fail
/*    */   extends Statement {
/*    */   private final Throwable fError;
/*    */   
/*    */   public Fail(Throwable e) {
/* 10 */     this.fError = e;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evaluate() throws Throwable {
/* 15 */     throw this.fError;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\statements\Fail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */