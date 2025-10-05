/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class InitializationError
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final List<Throwable> fErrors;
/*    */   
/*    */   public InitializationError(List<Throwable> errors) {
/* 16 */     this.fErrors = errors;
/*    */   }
/*    */   
/*    */   public InitializationError(Throwable... errors) {
/* 20 */     this(Arrays.asList(errors));
/*    */   }
/*    */   
/*    */   public InitializationError(String string) {
/* 24 */     this(new Throwable[] { new Exception(string) });
/*    */   }
/*    */   
/*    */   public List<Throwable> getCauses() {
/* 28 */     return this.fErrors;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\InitializationError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */