/*    */ package junit.extensions;
/*    */ 
/*    */ import junit.framework.Assert;
/*    */ import junit.framework.Test;
/*    */ import junit.framework.TestResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TestDecorator
/*    */   extends Assert
/*    */   implements Test
/*    */ {
/*    */   protected Test fTest;
/*    */   
/*    */   public TestDecorator(Test test) {
/* 17 */     this.fTest = test;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void basicRun(TestResult result) {
/* 24 */     this.fTest.run(result);
/*    */   }
/*    */   
/*    */   public int countTestCases() {
/* 28 */     return this.fTest.countTestCases();
/*    */   }
/*    */   
/*    */   public void run(TestResult result) {
/* 32 */     basicRun(result);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return this.fTest.toString();
/*    */   }
/*    */   
/*    */   public Test getTest() {
/* 41 */     return this.fTest;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\extensions\TestDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */