/*    */ package junit.framework;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.notification.Failure;
/*    */ import org.junit.runner.notification.RunListener;
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JUnit4TestAdapterCache
/*    */   extends HashMap<Description, Test>
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   private static final JUnit4TestAdapterCache fInstance = new JUnit4TestAdapterCache();
/*    */   
/*    */   public static JUnit4TestAdapterCache getDefault() {
/* 21 */     return fInstance;
/*    */   }
/*    */   
/*    */   public Test asTest(Description description) {
/* 25 */     if (description.isSuite()) {
/* 26 */       return createTest(description);
/*    */     }
/* 28 */     if (!containsKey(description))
/* 29 */       put(description, createTest(description)); 
/* 30 */     return get(description);
/*    */   }
/*    */ 
/*    */   
/*    */   Test createTest(Description description) {
/* 35 */     if (description.isTest()) {
/* 36 */       return new JUnit4TestCaseFacade(description);
/*    */     }
/* 38 */     TestSuite suite = new TestSuite(description.getDisplayName());
/* 39 */     for (Description child : description.getChildren())
/* 40 */       suite.addTest(asTest(child)); 
/* 41 */     return suite;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RunNotifier getNotifier(final TestResult result, JUnit4TestAdapter adapter) {
/* 47 */     RunNotifier notifier = new RunNotifier();
/* 48 */     notifier.addListener(new RunListener()
/*    */         {
/*    */           public void testFailure(Failure failure) throws Exception {
/* 51 */             result.addError(JUnit4TestAdapterCache.this.asTest(failure.getDescription()), failure.getException());
/*    */           }
/*    */ 
/*    */ 
/*    */           
/*    */           public void testFinished(Description description) throws Exception {
/* 57 */             result.endTest(JUnit4TestAdapterCache.this.asTest(description));
/*    */           }
/*    */ 
/*    */ 
/*    */           
/*    */           public void testStarted(Description description) throws Exception {
/* 63 */             result.startTest(JUnit4TestAdapterCache.this.asTest(description));
/*    */           }
/*    */         });
/* 66 */     return notifier;
/*    */   }
/*    */   
/*    */   public List<Test> asTestList(Description description) {
/* 70 */     if (description.isTest()) {
/* 71 */       return Arrays.asList(new Test[] { asTest(description) });
/*    */     }
/* 73 */     List<Test> returnThis = new ArrayList<Test>();
/* 74 */     for (Description child : description.getChildren()) {
/* 75 */       returnThis.add(asTest(child));
/*    */     }
/* 77 */     return returnThis;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\JUnit4TestAdapterCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */