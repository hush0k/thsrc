/*     */ package org.junit.internal.runners;
/*     */ 
/*     */ import junit.extensions.TestDecorator;
/*     */ import junit.framework.AssertionFailedError;
/*     */ import junit.framework.Test;
/*     */ import junit.framework.TestCase;
/*     */ import junit.framework.TestListener;
/*     */ import junit.framework.TestResult;
/*     */ import junit.framework.TestSuite;
/*     */ import org.junit.runner.Describable;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.Runner;
/*     */ import org.junit.runner.manipulation.Filter;
/*     */ import org.junit.runner.manipulation.Filterable;
/*     */ import org.junit.runner.manipulation.NoTestsRemainException;
/*     */ import org.junit.runner.manipulation.Sortable;
/*     */ import org.junit.runner.manipulation.Sorter;
/*     */ import org.junit.runner.notification.Failure;
/*     */ import org.junit.runner.notification.RunNotifier;
/*     */ 
/*     */ public class JUnit38ClassRunner
/*     */   extends Runner implements Filterable, Sortable {
/*     */   private Test fTest;
/*     */   
/*     */   private final class OldTestClassAdaptingListener implements TestListener {
/*     */     private OldTestClassAdaptingListener(RunNotifier notifier) {
/*  27 */       this.fNotifier = notifier;
/*     */     }
/*     */     private final RunNotifier fNotifier;
/*     */     public void endTest(Test test) {
/*  31 */       this.fNotifier.fireTestFinished(asDescription(test));
/*     */     }
/*     */     
/*     */     public void startTest(Test test) {
/*  35 */       this.fNotifier.fireTestStarted(asDescription(test));
/*     */     }
/*     */ 
/*     */     
/*     */     public void addError(Test test, Throwable t) {
/*  40 */       Failure failure = new Failure(asDescription(test), t);
/*  41 */       this.fNotifier.fireTestFailure(failure);
/*     */     }
/*     */     
/*     */     private Description asDescription(Test test) {
/*  45 */       if (test instanceof Describable) {
/*  46 */         Describable facade = (Describable)test;
/*  47 */         return facade.getDescription();
/*     */       } 
/*  49 */       return Description.createTestDescription(getEffectiveClass(test), getName(test));
/*     */     }
/*     */     
/*     */     private Class<? extends Test> getEffectiveClass(Test test) {
/*  53 */       return (Class)test.getClass();
/*     */     }
/*     */     
/*     */     private String getName(Test test) {
/*  57 */       if (test instanceof TestCase) {
/*  58 */         return ((TestCase)test).getName();
/*     */       }
/*  60 */       return test.toString();
/*     */     }
/*     */     
/*     */     public void addFailure(Test test, AssertionFailedError t) {
/*  64 */       addError(test, (Throwable)t);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JUnit38ClassRunner(Class<?> klass) {
/*  71 */     this((Test)new TestSuite(klass.asSubclass(TestCase.class)));
/*     */   }
/*     */ 
/*     */   
/*     */   public JUnit38ClassRunner(Test test) {
/*  76 */     setTest(test);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(RunNotifier notifier) {
/*  81 */     TestResult result = new TestResult();
/*  82 */     result.addListener(createAdaptingListener(notifier));
/*  83 */     getTest().run(result);
/*     */   }
/*     */   
/*     */   public TestListener createAdaptingListener(RunNotifier notifier) {
/*  87 */     return new OldTestClassAdaptingListener(notifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public Description getDescription() {
/*  92 */     return makeDescription(getTest());
/*     */   }
/*     */   
/*     */   private static Description makeDescription(Test test) {
/*  96 */     if (test instanceof TestCase) {
/*  97 */       TestCase tc = (TestCase)test;
/*  98 */       return Description.createTestDescription(tc.getClass(), tc.getName());
/*  99 */     }  if (test instanceof TestSuite) {
/* 100 */       TestSuite ts = (TestSuite)test;
/* 101 */       String name = (ts.getName() == null) ? createSuiteDescription(ts) : ts.getName();
/* 102 */       Description description = Description.createSuiteDescription(name, new java.lang.annotation.Annotation[0]);
/* 103 */       int n = ts.testCount();
/* 104 */       for (int i = 0; i < n; i++) {
/* 105 */         Description made = makeDescription(ts.testAt(i));
/* 106 */         description.addChild(made);
/*     */       } 
/* 108 */       return description;
/* 109 */     }  if (test instanceof Describable) {
/* 110 */       Describable adapter = (Describable)test;
/* 111 */       return adapter.getDescription();
/* 112 */     }  if (test instanceof TestDecorator) {
/* 113 */       TestDecorator decorator = (TestDecorator)test;
/* 114 */       return makeDescription(decorator.getTest());
/*     */     } 
/*     */     
/* 117 */     return Description.createSuiteDescription(test.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String createSuiteDescription(TestSuite ts) {
/* 122 */     int count = ts.countTestCases();
/* 123 */     String example = (count == 0) ? "" : String.format(" [example: %s]", new Object[] { ts.testAt(0) });
/* 124 */     return String.format("TestSuite with %s tests%s", new Object[] { Integer.valueOf(count), example });
/*     */   }
/*     */   
/*     */   public void filter(Filter filter) throws NoTestsRemainException {
/* 128 */     if (getTest() instanceof Filterable) {
/* 129 */       Filterable adapter = (Filterable)getTest();
/* 130 */       adapter.filter(filter);
/* 131 */     } else if (getTest() instanceof TestSuite) {
/* 132 */       TestSuite suite = (TestSuite)getTest();
/* 133 */       TestSuite filtered = new TestSuite(suite.getName());
/* 134 */       int n = suite.testCount();
/* 135 */       for (int i = 0; i < n; i++) {
/* 136 */         Test test = suite.testAt(i);
/* 137 */         if (filter.shouldRun(makeDescription(test)))
/* 138 */           filtered.addTest(test); 
/*     */       } 
/* 140 */       setTest((Test)filtered);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sort(Sorter sorter) {
/* 145 */     if (getTest() instanceof Sortable) {
/* 146 */       Sortable adapter = (Sortable)getTest();
/* 147 */       adapter.sort(sorter);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setTest(Test test) {
/* 152 */     this.fTest = test;
/*     */   }
/*     */   
/*     */   private Test getTest() {
/* 156 */     return this.fTest;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\JUnit38ClassRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */