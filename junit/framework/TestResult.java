/*     */ package junit.framework;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestResult
/*     */ {
/*  25 */   protected List<TestFailure> fFailures = new ArrayList<TestFailure>();
/*  26 */   protected List<TestFailure> fErrors = new ArrayList<TestFailure>();
/*  27 */   protected List<TestListener> fListeners = new ArrayList<TestListener>();
/*  28 */   protected int fRunTests = 0;
/*     */ 
/*     */   
/*     */   private boolean fStop = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addError(Test test, Throwable t) {
/*  36 */     this.fErrors.add(new TestFailure(test, t));
/*  37 */     for (TestListener each : cloneListeners()) {
/*  38 */       each.addError(test, t);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addFailure(Test test, AssertionFailedError t) {
/*  45 */     this.fFailures.add(new TestFailure(test, t));
/*  46 */     for (TestListener each : cloneListeners()) {
/*  47 */       each.addFailure(test, t);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void addListener(TestListener listener) {
/*  53 */     this.fListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeListener(TestListener listener) {
/*  59 */     this.fListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized List<TestListener> cloneListeners() {
/*  65 */     List<TestListener> result = new ArrayList<TestListener>();
/*  66 */     result.addAll(this.fListeners);
/*  67 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endTest(Test test) {
/*  73 */     for (TestListener each : cloneListeners()) {
/*  74 */       each.endTest(test);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int errorCount() {
/*  80 */     return this.fErrors.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Enumeration<TestFailure> errors() {
/*  86 */     return Collections.enumeration(this.fErrors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int failureCount() {
/*  94 */     return this.fFailures.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Enumeration<TestFailure> failures() {
/* 100 */     return Collections.enumeration(this.fFailures);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void run(final TestCase test) {
/* 107 */     startTest(test);
/* 108 */     Protectable p = new Protectable() {
/*     */         public void protect() throws Throwable {
/* 110 */           test.runBare();
/*     */         }
/*     */       };
/* 113 */     runProtected(test, p);
/*     */     
/* 115 */     endTest(test);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int runCount() {
/* 121 */     return this.fRunTests;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runProtected(Test test, Protectable p) {
/*     */     try {
/* 128 */       p.protect();
/*     */     }
/* 130 */     catch (AssertionFailedError e) {
/* 131 */       addFailure(test, e);
/*     */     }
/* 133 */     catch (ThreadDeath e) {
/* 134 */       throw e;
/*     */     }
/* 136 */     catch (Throwable e) {
/* 137 */       addError(test, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean shouldStop() {
/* 144 */     return this.fStop;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTest(Test test) {
/* 150 */     int count = test.countTestCases();
/* 151 */     synchronized (this) {
/* 152 */       this.fRunTests += count;
/*     */     } 
/* 154 */     for (TestListener each : cloneListeners()) {
/* 155 */       each.startTest(test);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void stop() {
/* 161 */     this.fStop = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean wasSuccessful() {
/* 167 */     return (failureCount() == 0 && errorCount() == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\framework\TestResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */