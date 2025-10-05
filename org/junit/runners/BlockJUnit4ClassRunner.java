/*     */ package org.junit.runners;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.junit.After;
/*     */ import org.junit.Before;
/*     */ import org.junit.Ignore;
/*     */ import org.junit.Rule;
/*     */ import org.junit.Test;
/*     */ import org.junit.internal.runners.model.ReflectiveCallable;
/*     */ import org.junit.internal.runners.rules.RuleFieldValidator;
/*     */ import org.junit.internal.runners.statements.ExpectException;
/*     */ import org.junit.internal.runners.statements.Fail;
/*     */ import org.junit.internal.runners.statements.FailOnTimeout;
/*     */ import org.junit.internal.runners.statements.InvokeMethod;
/*     */ import org.junit.internal.runners.statements.RunAfters;
/*     */ import org.junit.internal.runners.statements.RunBefores;
/*     */ import org.junit.rules.MethodRule;
/*     */ import org.junit.rules.RunRules;
/*     */ import org.junit.rules.TestRule;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.notification.RunNotifier;
/*     */ import org.junit.runners.model.FrameworkMethod;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.Statement;
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
/*     */ public class BlockJUnit4ClassRunner
/*     */   extends ParentRunner<FrameworkMethod>
/*     */ {
/*     */   public BlockJUnit4ClassRunner(Class<?> klass) throws InitializationError {
/*  55 */     super(klass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runChild(FrameworkMethod method, RunNotifier notifier) {
/*  64 */     Description description = describeChild(method);
/*  65 */     if (method.getAnnotation(Ignore.class) != null) {
/*  66 */       notifier.fireTestIgnored(description);
/*     */     } else {
/*  68 */       runLeaf(methodBlock(method), description, notifier);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Description describeChild(FrameworkMethod method) {
/*  74 */     return Description.createTestDescription(getTestClass().getJavaClass(), testName(method), method.getAnnotations());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<FrameworkMethod> getChildren() {
/*  80 */     return computeTestMethods();
/*     */   }
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
/*     */   protected List<FrameworkMethod> computeTestMethods() {
/*  93 */     return getTestClass().getAnnotatedMethods(Test.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collectInitializationErrors(List<Throwable> errors) {
/*  98 */     super.collectInitializationErrors(errors);
/*     */     
/* 100 */     validateNoNonStaticInnerClass(errors);
/* 101 */     validateConstructor(errors);
/* 102 */     validateInstanceMethods(errors);
/* 103 */     validateFields(errors);
/*     */   }
/*     */   
/*     */   protected void validateNoNonStaticInnerClass(List<Throwable> errors) {
/* 107 */     if (getTestClass().isANonStaticInnerClass()) {
/* 108 */       String gripe = "The inner class " + getTestClass().getName() + " is not static.";
/*     */       
/* 110 */       errors.add(new Exception(gripe));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateConstructor(List<Throwable> errors) {
/* 120 */     validateOnlyOneConstructor(errors);
/* 121 */     validateZeroArgConstructor(errors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateOnlyOneConstructor(List<Throwable> errors) {
/* 129 */     if (!hasOneConstructor()) {
/* 130 */       String gripe = "Test class should have exactly one public constructor";
/* 131 */       errors.add(new Exception(gripe));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateZeroArgConstructor(List<Throwable> errors) {
/* 140 */     if (!getTestClass().isANonStaticInnerClass() && hasOneConstructor() && (getTestClass().getOnlyConstructor().getParameterTypes()).length != 0) {
/*     */ 
/*     */       
/* 143 */       String gripe = "Test class should have exactly one public zero-argument constructor";
/* 144 */       errors.add(new Exception(gripe));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasOneConstructor() {
/* 149 */     return ((getTestClass().getJavaClass().getConstructors()).length == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void validateInstanceMethods(List<Throwable> errors) {
/* 161 */     validatePublicVoidNoArgMethods((Class)After.class, false, errors);
/* 162 */     validatePublicVoidNoArgMethods((Class)Before.class, false, errors);
/* 163 */     validateTestMethods(errors);
/*     */     
/* 165 */     if (computeTestMethods().size() == 0)
/* 166 */       errors.add(new Exception("No runnable methods")); 
/*     */   }
/*     */   
/*     */   private void validateFields(List<Throwable> errors) {
/* 170 */     RuleFieldValidator.RULE_VALIDATOR.validate(getTestClass(), errors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateTestMethods(List<Throwable> errors) {
/* 178 */     validatePublicVoidNoArgMethods((Class)Test.class, false, errors);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object createTest() throws Exception {
/* 187 */     return getTestClass().getOnlyConstructor().newInstance(new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String testName(FrameworkMethod method) {
/* 195 */     return method.getName();
/*     */   }
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
/*     */   
/*     */   protected Statement methodBlock(FrameworkMethod method) {
/*     */     Object test;
/*     */     try {
/* 233 */       test = (new ReflectiveCallable()
/*     */         {
/*     */           protected Object runReflectiveCall() throws Throwable {
/* 236 */             return BlockJUnit4ClassRunner.this.createTest();
/*     */           }
/*     */         }).run();
/* 239 */     } catch (Throwable e) {
/* 240 */       return (Statement)new Fail(e);
/*     */     } 
/*     */     
/* 243 */     Statement statement = methodInvoker(method, test);
/* 244 */     statement = possiblyExpectingExceptions(method, test, statement);
/* 245 */     statement = withPotentialTimeout(method, test, statement);
/* 246 */     statement = withBefores(method, test, statement);
/* 247 */     statement = withAfters(method, test, statement);
/* 248 */     statement = withRules(method, test, statement);
/* 249 */     return statement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Statement methodInvoker(FrameworkMethod method, Object test) {
/* 260 */     return (Statement)new InvokeMethod(method, test);
/*     */   }
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
/*     */   @Deprecated
/*     */   protected Statement possiblyExpectingExceptions(FrameworkMethod method, Object test, Statement next) {
/* 274 */     Test annotation = (Test)method.getAnnotation(Test.class);
/* 275 */     return expectsException(annotation) ? (Statement)new ExpectException(next, getExpectedException(annotation)) : next;
/*     */   }
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
/*     */   @Deprecated
/*     */   protected Statement withPotentialTimeout(FrameworkMethod method, Object test, Statement next) {
/* 289 */     long timeout = getTimeout((Test)method.getAnnotation(Test.class));
/* 290 */     return (timeout > 0L) ? (Statement)new FailOnTimeout(next, timeout) : next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
/* 303 */     List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(Before.class);
/*     */     
/* 305 */     return befores.isEmpty() ? statement : (Statement)new RunBefores(statement, befores, target);
/*     */   }
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
/*     */   @Deprecated
/*     */   protected Statement withAfters(FrameworkMethod method, Object target, Statement statement) {
/* 321 */     List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(After.class);
/*     */     
/* 323 */     return afters.isEmpty() ? statement : (Statement)new RunAfters(statement, afters, target);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Statement withRules(FrameworkMethod method, Object target, Statement statement) {
/* 329 */     Statement result = statement;
/* 330 */     result = withMethodRules(method, target, result);
/* 331 */     result = withTestRules(method, target, result);
/* 332 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Statement withMethodRules(FrameworkMethod method, Object target, Statement result) {
/* 338 */     List<TestRule> testRules = getTestRules(target);
/* 339 */     for (MethodRule each : getMethodRules(target)) {
/* 340 */       if (!testRules.contains(each))
/* 341 */         result = each.apply(result, method, target); 
/* 342 */     }  return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<MethodRule> getMethodRules(Object target) {
/* 347 */     return rules(target);
/*     */   }
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
/*     */   @Deprecated
/*     */   protected List<MethodRule> rules(Object target) {
/* 361 */     return getTestClass().getAnnotatedFieldValues(target, Rule.class, MethodRule.class);
/*     */   }
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
/*     */   private Statement withTestRules(FrameworkMethod method, Object target, Statement statement) {
/* 375 */     List<TestRule> testRules = getTestRules(target);
/* 376 */     return testRules.isEmpty() ? statement : (Statement)new RunRules(statement, testRules, describeChild(method));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<TestRule> getTestRules(Object target) {
/* 387 */     return getTestClass().getAnnotatedFieldValues(target, Rule.class, TestRule.class);
/*     */   }
/*     */ 
/*     */   
/*     */   private Class<? extends Throwable> getExpectedException(Test annotation) {
/* 392 */     if (annotation == null || annotation.expected() == Test.None.class) {
/* 393 */       return null;
/*     */     }
/* 395 */     return annotation.expected();
/*     */   }
/*     */   
/*     */   private boolean expectsException(Test annotation) {
/* 399 */     return (getExpectedException(annotation) != null);
/*     */   }
/*     */   
/*     */   private long getTimeout(Test annotation) {
/* 403 */     if (annotation == null)
/* 404 */       return 0L; 
/* 405 */     return annotation.timeout();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\BlockJUnit4ClassRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */