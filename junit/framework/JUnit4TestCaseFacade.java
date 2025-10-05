/*    */ package junit.framework;
/*    */ 
/*    */ import org.junit.runner.Describable;
/*    */ import org.junit.runner.Description;
/*    */ 
/*    */ 
/*    */ public class JUnit4TestCaseFacade
/*    */   implements Test, Describable
/*    */ {
/*    */   private final Description fDescription;
/*    */   
/*    */   JUnit4TestCaseFacade(Description description) {
/* 13 */     this.fDescription = description;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 18 */     return getDescription().toString();
/*    */   }
/*    */   
/*    */   public int countTestCases() {
/* 22 */     return 1;
/*    */   }
/*    */   
/*    */   public void run(TestResult result) {
/* 26 */     throw new RuntimeException("This test stub created only for informational purposes.");
/*    */   }
/*    */ 
/*    */   
/*    */   public Description getDescription() {
/* 31 */     return this.fDescription;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\JUnit4TestCaseFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */