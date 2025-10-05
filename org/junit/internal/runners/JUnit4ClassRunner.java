/*     */ package org.junit.internal.runners;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class JUnit4ClassRunner
/*     */   extends Runner
/*     */   implements Filterable, Sortable
/*     */ {
/*     */   private final List<Method> fTestMethods;
/*     */   private TestClass fTestClass;
/*     */   
/*     */   public JUnit4ClassRunner(Class<?> klass) throws InitializationError {
/*  35 */     this.fTestClass = new TestClass(klass);
/*  36 */     this.fTestMethods = getTestMethods();
/*  37 */     validate();
/*     */   }
/*     */   
/*     */   protected List<Method> getTestMethods() {
/*  41 */     return this.fTestClass.getTestMethods();
/*     */   }
/*     */   
/*     */   protected void validate() throws InitializationError {
/*  45 */     MethodValidator methodValidator = new MethodValidator(this.fTestClass);
/*  46 */     methodValidator.validateMethodsForDefaultRunner();
/*  47 */     methodValidator.assertValid();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(final RunNotifier notifier) {
/*  52 */     (new ClassRoadie(notifier, this.fTestClass, getDescription(), new Runnable() {
/*     */           public void run() {
/*  54 */             JUnit4ClassRunner.this.runMethods(notifier);
/*     */           }
/*     */         })).runProtected();
/*     */   }
/*     */   
/*     */   protected void runMethods(RunNotifier notifier) {
/*  60 */     for (Method method : this.fTestMethods) {
/*  61 */       invokeTestMethod(method, notifier);
/*     */     }
/*     */   }
/*     */   
/*     */   public Description getDescription() {
/*  66 */     Description spec = Description.createSuiteDescription(getName(), classAnnotations());
/*  67 */     List<Method> testMethods = this.fTestMethods;
/*  68 */     for (Method method : testMethods)
/*  69 */       spec.addChild(methodDescription(method)); 
/*  70 */     return spec;
/*     */   }
/*     */   
/*     */   protected Annotation[] classAnnotations() {
/*  74 */     return this.fTestClass.getJavaClass().getAnnotations();
/*     */   }
/*     */   
/*     */   protected String getName() {
/*  78 */     return getTestClass().getName();
/*     */   }
/*     */   
/*     */   protected Object createTest() throws Exception {
/*  82 */     return getTestClass().getConstructor().newInstance(new Object[0]);
/*     */   }
/*     */   protected void invokeTestMethod(Method method, RunNotifier notifier) {
/*     */     Object test;
/*  86 */     Description description = methodDescription(method);
/*     */     
/*     */     try {
/*  89 */       test = createTest();
/*  90 */     } catch (InvocationTargetException e) {
/*  91 */       testAborted(notifier, description, e.getCause());
/*     */       return;
/*  93 */     } catch (Exception e) {
/*  94 */       testAborted(notifier, description, e);
/*     */       return;
/*     */     } 
/*  97 */     TestMethod testMethod = wrapMethod(method);
/*  98 */     (new MethodRoadie(test, testMethod, notifier, description)).run();
/*     */   }
/*     */ 
/*     */   
/*     */   private void testAborted(RunNotifier notifier, Description description, Throwable e) {
/* 103 */     notifier.fireTestStarted(description);
/* 104 */     notifier.fireTestFailure(new Failure(description, e));
/* 105 */     notifier.fireTestFinished(description);
/*     */   }
/*     */   
/*     */   protected TestMethod wrapMethod(Method method) {
/* 109 */     return new TestMethod(method, this.fTestClass);
/*     */   }
/*     */   
/*     */   protected String testName(Method method) {
/* 113 */     return method.getName();
/*     */   }
/*     */   
/*     */   protected Description methodDescription(Method method) {
/* 117 */     return Description.createTestDescription(getTestClass().getJavaClass(), testName(method), testAnnotations(method));
/*     */   }
/*     */   
/*     */   protected Annotation[] testAnnotations(Method method) {
/* 121 */     return method.getAnnotations();
/*     */   }
/*     */   
/*     */   public void filter(Filter filter) throws NoTestsRemainException {
/* 125 */     for (Iterator<Method> iter = this.fTestMethods.iterator(); iter.hasNext(); ) {
/* 126 */       Method method = iter.next();
/* 127 */       if (!filter.shouldRun(methodDescription(method)))
/* 128 */         iter.remove(); 
/*     */     } 
/* 130 */     if (this.fTestMethods.isEmpty())
/* 131 */       throw new NoTestsRemainException(); 
/*     */   }
/*     */   
/*     */   public void sort(final Sorter sorter) {
/* 135 */     Collections.sort(this.fTestMethods, new Comparator<Method>() {
/*     */           public int compare(Method o1, Method o2) {
/* 137 */             return sorter.compare(JUnit4ClassRunner.this.methodDescription(o1), JUnit4ClassRunner.this.methodDescription(o2));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected TestClass getTestClass() {
/* 143 */     return this.fTestClass;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\JUnit4ClassRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */