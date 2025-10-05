/*    */ package org.junit.internal.runners.statements;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.junit.runners.model.FrameworkMethod;
/*    */ import org.junit.runners.model.Statement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RunBefores
/*    */   extends Statement
/*    */ {
/*    */   private final Statement fNext;
/*    */   private final Object fTarget;
/*    */   private final List<FrameworkMethod> fBefores;
/*    */   
/*    */   public RunBefores(Statement next, List<FrameworkMethod> befores, Object target) {
/* 19 */     this.fNext = next;
/* 20 */     this.fBefores = befores;
/* 21 */     this.fTarget = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evaluate() throws Throwable {
/* 26 */     for (FrameworkMethod before : this.fBefores)
/* 27 */       before.invokeExplosively(this.fTarget, new Object[0]); 
/* 28 */     this.fNext.evaluate();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\statements\RunBefores.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */