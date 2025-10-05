/*    */ package junit.framework;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
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
/*    */ public class TestFailure
/*    */ {
/*    */   protected Test fFailedTest;
/*    */   protected Throwable fThrownException;
/*    */   
/*    */   public TestFailure(Test failedTest, Throwable thrownException) {
/* 21 */     this.fFailedTest = failedTest;
/* 22 */     this.fThrownException = thrownException;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Test failedTest() {
/* 28 */     return this.fFailedTest;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable thrownException() {
/* 34 */     return this.fThrownException;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     StringBuffer buffer = new StringBuffer();
/* 42 */     buffer.append(this.fFailedTest + ": " + this.fThrownException.getMessage());
/* 43 */     return buffer.toString();
/*    */   }
/*    */   public String trace() {
/* 46 */     StringWriter stringWriter = new StringWriter();
/* 47 */     PrintWriter writer = new PrintWriter(stringWriter);
/* 48 */     thrownException().printStackTrace(writer);
/* 49 */     StringBuffer buffer = stringWriter.getBuffer();
/* 50 */     return buffer.toString();
/*    */   }
/*    */   public String exceptionMessage() {
/* 53 */     return thrownException().getMessage();
/*    */   }
/*    */   public boolean isFailure() {
/* 56 */     return thrownException() instanceof AssertionFailedError;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\TestFailure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */