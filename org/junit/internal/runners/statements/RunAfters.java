/*    */ package org.junit.internal.runners.statements;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.junit.runners.model.FrameworkMethod;
/*    */ import org.junit.runners.model.MultipleFailureException;
/*    */ import org.junit.runners.model.Statement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RunAfters
/*    */   extends Statement
/*    */ {
/*    */   private final Statement fNext;
/*    */   private final Object fTarget;
/*    */   private final List<FrameworkMethod> fAfters;
/*    */   
/*    */   public RunAfters(Statement next, List<FrameworkMethod> afters, Object target) {
/* 21 */     this.fNext = next;
/* 22 */     this.fAfters = afters;
/* 23 */     this.fTarget = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public void evaluate() throws Throwable {
/* 28 */     List<Throwable> errors = new ArrayList<Throwable>();
/*    */     try {
/* 30 */       this.fNext.evaluate();
/* 31 */     } catch (Throwable e) {
/* 32 */       errors.add(e);
/*    */     } finally {
/* 34 */       for (FrameworkMethod each : this.fAfters) {
/*    */         try {
/* 36 */           each.invokeExplosively(this.fTarget, new Object[0]);
/* 37 */         } catch (Throwable e) {
/* 38 */           errors.add(e);
/*    */         } 
/*    */       } 
/* 41 */     }  MultipleFailureException.assertEmpty(errors);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\statements\RunAfters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */