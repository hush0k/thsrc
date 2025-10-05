/*    */ package junit.extensions;
/*    */ 
/*    */ import junit.framework.Test;
/*    */ import junit.framework.TestResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RepeatedTest
/*    */   extends TestDecorator
/*    */ {
/*    */   private int fTimesRepeat;
/*    */   
/*    */   public RepeatedTest(Test test, int repeat) {
/* 14 */     super(test);
/* 15 */     if (repeat < 0)
/* 16 */       throw new IllegalArgumentException("Repetition count must be >= 0"); 
/* 17 */     this.fTimesRepeat = repeat;
/*    */   }
/*    */ 
/*    */   
/*    */   public int countTestCases() {
/* 22 */     return super.countTestCases() * this.fTimesRepeat;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(TestResult result) {
/* 27 */     for (int i = 0; i < this.fTimesRepeat && 
/* 28 */       !result.shouldStop(); i++)
/*    */     {
/* 30 */       super.run(result);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return super.toString() + "(repeated)";
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\extensions\RepeatedTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */