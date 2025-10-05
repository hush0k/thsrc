/*    */ package junit.framework;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.junit.Ignore;
/*    */ import org.junit.runner.Describable;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.Request;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runner.manipulation.Filter;
/*    */ import org.junit.runner.manipulation.Filterable;
/*    */ import org.junit.runner.manipulation.NoTestsRemainException;
/*    */ import org.junit.runner.manipulation.Sortable;
/*    */ import org.junit.runner.manipulation.Sorter;
/*    */ 
/*    */ 
/*    */ public class JUnit4TestAdapter
/*    */   implements Test, Filterable, Sortable, Describable
/*    */ {
/*    */   private final Class<?> fNewTestClass;
/*    */   private final Runner fRunner;
/*    */   private final JUnit4TestAdapterCache fCache;
/*    */   
/*    */   public JUnit4TestAdapter(Class<?> newTestClass) {
/* 24 */     this(newTestClass, JUnit4TestAdapterCache.getDefault());
/*    */   }
/*    */ 
/*    */   
/*    */   public JUnit4TestAdapter(Class<?> newTestClass, JUnit4TestAdapterCache cache) {
/* 29 */     this.fCache = cache;
/* 30 */     this.fNewTestClass = newTestClass;
/* 31 */     this.fRunner = Request.classWithoutSuiteMethod(newTestClass).getRunner();
/*    */   }
/*    */   
/*    */   public int countTestCases() {
/* 35 */     return this.fRunner.testCount();
/*    */   }
/*    */   
/*    */   public void run(TestResult result) {
/* 39 */     this.fRunner.run(this.fCache.getNotifier(result, this));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Test> getTests() {
/* 44 */     return this.fCache.asTestList(getDescription());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> getTestClass() {
/* 49 */     return this.fNewTestClass;
/*    */   }
/*    */   
/*    */   public Description getDescription() {
/* 53 */     Description description = this.fRunner.getDescription();
/* 54 */     return removeIgnored(description);
/*    */   }
/*    */   
/*    */   private Description removeIgnored(Description description) {
/* 58 */     if (isIgnored(description))
/* 59 */       return Description.EMPTY; 
/* 60 */     Description result = description.childlessCopy();
/* 61 */     for (Description each : description.getChildren()) {
/* 62 */       Description child = removeIgnored(each);
/* 63 */       if (!child.isEmpty())
/* 64 */         result.addChild(child); 
/*    */     } 
/* 66 */     return result;
/*    */   }
/*    */   
/*    */   private boolean isIgnored(Description description) {
/* 70 */     return (description.getAnnotation(Ignore.class) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return this.fNewTestClass.getName();
/*    */   }
/*    */   
/*    */   public void filter(Filter filter) throws NoTestsRemainException {
/* 79 */     filter.apply(this.fRunner);
/*    */   }
/*    */   
/*    */   public void sort(Sorter sorter) {
/* 83 */     sorter.apply(this.fRunner);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\JUnit4TestAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */