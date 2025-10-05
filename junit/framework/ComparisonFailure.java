/*    */ package junit.framework;
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
/*    */ public class ComparisonFailure
/*    */   extends AssertionFailedError
/*    */ {
/*    */   private static final int MAX_CONTEXT_LENGTH = 20;
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String fExpected;
/*    */   private String fActual;
/*    */   
/*    */   public ComparisonFailure(String message, String expected, String actual) {
/* 22 */     super(message);
/* 23 */     this.fExpected = expected;
/* 24 */     this.fActual = actual;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 35 */     return (new ComparisonCompactor(20, this.fExpected, this.fActual)).compact(super.getMessage());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getActual() {
/* 43 */     return this.fActual;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getExpected() {
/* 50 */     return this.fExpected;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\ComparisonFailure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */