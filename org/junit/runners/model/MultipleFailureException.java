/*    */ package org.junit.runners.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultipleFailureException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final List<Throwable> fErrors;
/*    */   
/*    */   public MultipleFailureException(List<Throwable> errors) {
/* 18 */     this.fErrors = new ArrayList<Throwable>(errors);
/*    */   }
/*    */   
/*    */   public List<Throwable> getFailures() {
/* 22 */     return Collections.unmodifiableList(this.fErrors);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 27 */     StringBuilder sb = new StringBuilder(String.format("There were %d errors:", new Object[] { Integer.valueOf(this.fErrors.size()) }));
/*    */     
/* 29 */     for (Throwable e : this.fErrors) {
/* 30 */       sb.append(String.format("\n  %s(%s)", new Object[] { e.getClass().getName(), e.getMessage() }));
/*    */     } 
/* 32 */     return sb.toString();
/*    */   }
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
/*    */   public static void assertEmpty(List<Throwable> errors) throws Throwable {
/* 46 */     if (errors.isEmpty())
/*    */       return; 
/* 48 */     if (errors.size() == 1) {
/* 49 */       throw (Throwable)errors.get(0);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     throw new org.junit.internal.runners.model.MultipleFailureException(errors);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\MultipleFailureException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */