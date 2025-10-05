/*    */ package org.junit.internal.runners.model;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ReflectiveCallable
/*    */ {
/*    */   public Object run() throws Throwable {
/*    */     try {
/* 15 */       return runReflectiveCall();
/* 16 */     } catch (InvocationTargetException e) {
/* 17 */       throw e.getTargetException();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract Object runReflectiveCall() throws Throwable;
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\model\ReflectiveCallable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */