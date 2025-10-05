/*    */ package org.junit.internal.runners.statements;
/*    */ 
/*    */ import org.junit.runners.model.FrameworkMethod;
/*    */ import org.junit.runners.model.Statement;
/*    */ 
/*    */ 
/*    */ public class InvokeMethod
/*    */   extends Statement
/*    */ {
/*    */   private final FrameworkMethod fTestMethod;
/*    */   private Object fTarget;
/*    */   
/*    */   public InvokeMethod(FrameworkMethod testMethod, Object target) {
/* 14 */     this.fTestMethod = testMethod;
/* 15 */     this.fTarget = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evaluate() throws Throwable {
/* 20 */     this.fTestMethod.invokeExplosively(this.fTarget, new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\statements\InvokeMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */