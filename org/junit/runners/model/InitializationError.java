/*    */ package org.junit.runners.model;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InitializationError
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final List<Throwable> fErrors;
/*    */   
/*    */   public InitializationError(List<Throwable> errors) {
/* 18 */     this.fErrors = errors;
/*    */   }
/*    */   
/*    */   public InitializationError(Throwable error) {
/* 22 */     this(Arrays.asList(new Throwable[] { error }));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InitializationError(String string) {
/* 30 */     this(new Exception(string));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Throwable> getCauses() {
/* 37 */     return this.fErrors;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\model\InitializationError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */