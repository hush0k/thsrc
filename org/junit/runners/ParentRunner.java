/*     */ package org.junit.runners;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.junit.AfterClass;
/*     */ import org.junit.BeforeClass;
/*     */ import org.junit.ClassRule;
/*     */ import org.junit.internal.AssumptionViolatedException;
/*     */ import org.junit.internal.runners.model.EachTestNotifier;
/*     */ import org.junit.internal.runners.rules.RuleFieldValidator;
/*     */ import org.junit.internal.runners.statements.RunAfters;
/*     */ import org.junit.internal.runners.statements.RunBefores;
/*     */ import org.junit.rules.RunRules;
/*     */ import org.junit.rules.TestRule;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.Runner;
/*     */ import org.junit.runner.manipulation.Filter;
/*     */ import org.junit.runner.manipulation.Filterable;
/*     */ import org.junit.runner.manipulation.NoTestsRemainException;
/*     */ import org.junit.runner.manipulation.Sortable;
/*     */ import org.junit.runner.manipulation.Sorter;
/*     */ import org.junit.runner.notification.RunNotifier;
/*     */ import org.junit.runner.notification.StoppedByUserException;
/*     */ import org.junit.runners.model.FrameworkMethod;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.RunnerScheduler;
/*     */ import org.junit.runners.model.Statement;
/*     */ import org.junit.runners.model.TestClass;
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
/*     */ public abstract class ParentRunner<T>
/*     */   extends Runner
/*     */   implements Filterable, Sortable
/*     */ {
/*     */   private final TestClass fTestClass;
/*  54 */   private Sorter fSorter = Sorter.NULL;
/*     */   
/*  56 */   private List<T> fFilteredChildren = null;
/*     */   
/*  58 */   private RunnerScheduler fScheduler = new RunnerScheduler() {
/*     */       public void schedule(Runnable childStatement) {
/*  60 */         childStatement.run();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void finished() {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ParentRunner(Class<?> testClass) throws InitializationError {
/*  73 */     this.fTestClass = new TestClass(testClass);
/*  74 */     validate();
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
/*     */   
/*     */   protected void collectInitializationErrors(List<Throwable> errors) {
/* 111 */     validatePublicVoidNoArgMethods((Class)BeforeClass.class, true, errors);
/* 112 */     validatePublicVoidNoArgMethods((Class)AfterClass.class, true, errors);
/* 113 */     validateClassRules(errors);
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
/*     */   protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
/* 128 */     List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
/*     */     
/* 130 */     for (FrameworkMethod eachTestMethod : methods)
/* 131 */       eachTestMethod.validatePublicVoidNoArg(isStatic, errors); 
/*     */   }
/*     */   
/*     */   private void validateClassRules(List<Throwable> errors) {
/* 135 */     RuleFieldValidator.CLASS_RULE_VALIDATOR.validate(getTestClass(), errors);
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
/*     */   protected Statement classBlock(RunNotifier notifier) {
/* 156 */     Statement statement = childrenInvoker(notifier);
/* 157 */     statement = withBeforeClasses(statement);
/* 158 */     statement = withAfterClasses(statement);
/* 159 */     statement = withClassRules(statement);
/* 160 */     return statement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Statement withBeforeClasses(Statement statement) {
/* 169 */     List<FrameworkMethod> befores = this.fTestClass.getAnnotatedMethods(BeforeClass.class);
/*     */     
/* 171 */     return befores.isEmpty() ? statement : (Statement)new RunBefores(statement, befores, null);
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
/*     */   protected Statement withAfterClasses(Statement statement) {
/* 183 */     List<FrameworkMethod> afters = this.fTestClass.getAnnotatedMethods(AfterClass.class);
/*     */     
/* 185 */     return afters.isEmpty() ? statement : (Statement)new RunAfters(statement, afters, null);
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
/*     */   private Statement withClassRules(Statement statement) {
/* 200 */     List<TestRule> classRules = classRules();
/* 201 */     return classRules.isEmpty() ? statement : (Statement)new RunRules(statement, classRules, getDescription());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<TestRule> classRules() {
/* 210 */     return this.fTestClass.getAnnotatedFieldValues(null, ClassRule.class, TestRule.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Statement childrenInvoker(final RunNotifier notifier) {
/* 219 */     return new Statement()
/*     */       {
/*     */         public void evaluate() {
/* 222 */           ParentRunner.this.runChildren(notifier);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private void runChildren(final RunNotifier notifier) {
/* 228 */     for (T each : getFilteredChildren()) {
/* 229 */       this.fScheduler.schedule(new Runnable() {
/*     */             public void run() {
/* 231 */               ParentRunner.this.runChild(each, notifier); }
/*     */           });
/*     */     } 
/* 234 */     this.fScheduler.finished();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getName() {
/* 241 */     return this.fTestClass.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final TestClass getTestClass() {
/* 252 */     return this.fTestClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void runLeaf(Statement statement, Description description, RunNotifier notifier) {
/* 260 */     EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
/* 261 */     eachNotifier.fireTestStarted();
/*     */     try {
/* 263 */       statement.evaluate();
/* 264 */     } catch (AssumptionViolatedException e) {
/* 265 */       eachNotifier.addFailedAssumption(e);
/* 266 */     } catch (Throwable e) {
/* 267 */       eachNotifier.addFailure(e);
/*     */     } finally {
/* 269 */       eachNotifier.fireTestFinished();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Annotation[] getRunnerAnnotations() {
/* 278 */     return this.fTestClass.getAnnotations();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Description getDescription() {
/* 287 */     Description description = Description.createSuiteDescription(getName(), getRunnerAnnotations());
/*     */     
/* 289 */     for (T child : getFilteredChildren())
/* 290 */       description.addChild(describeChild(child)); 
/* 291 */     return description;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(RunNotifier notifier) {
/* 296 */     EachTestNotifier testNotifier = new EachTestNotifier(notifier, getDescription());
/*     */     
/*     */     try {
/* 299 */       Statement statement = classBlock(notifier);
/* 300 */       statement.evaluate();
/* 301 */     } catch (AssumptionViolatedException e) {
/* 302 */       testNotifier.fireTestIgnored();
/* 303 */     } catch (StoppedByUserException e) {
/* 304 */       throw e;
/* 305 */     } catch (Throwable e) {
/* 306 */       testNotifier.addFailure(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void filter(Filter filter) throws NoTestsRemainException {
/* 315 */     for (Iterator<T> iter = getFilteredChildren().iterator(); iter.hasNext(); ) {
/* 316 */       T each = iter.next();
/* 317 */       if (shouldRun(filter, each)) {
/*     */         try {
/* 319 */           filter.apply(each);
/* 320 */         } catch (NoTestsRemainException e) {
/* 321 */           iter.remove();
/*     */         }  continue;
/*     */       } 
/* 324 */       iter.remove();
/*     */     } 
/* 326 */     if (getFilteredChildren().isEmpty()) {
/* 327 */       throw new NoTestsRemainException();
/*     */     }
/*     */   }
/*     */   
/*     */   public void sort(Sorter sorter) {
/* 332 */     this.fSorter = sorter;
/* 333 */     for (T each : getFilteredChildren())
/* 334 */       sortChild(each); 
/* 335 */     Collections.sort(getFilteredChildren(), comparator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validate() throws InitializationError {
/* 343 */     List<Throwable> errors = new ArrayList<Throwable>();
/* 344 */     collectInitializationErrors(errors);
/* 345 */     if (!errors.isEmpty())
/* 346 */       throw new InitializationError(errors); 
/*     */   }
/*     */   
/*     */   private List<T> getFilteredChildren() {
/* 350 */     if (this.fFilteredChildren == null)
/* 351 */       this.fFilteredChildren = new ArrayList<T>(getChildren()); 
/* 352 */     return this.fFilteredChildren;
/*     */   }
/*     */   
/*     */   private void sortChild(T child) {
/* 356 */     this.fSorter.apply(child);
/*     */   }
/*     */   
/*     */   private boolean shouldRun(Filter filter, T each) {
/* 360 */     return filter.shouldRun(describeChild(each));
/*     */   }
/*     */   
/*     */   private Comparator<? super T> comparator() {
/* 364 */     return new Comparator<T>() {
/*     */         public int compare(T o1, T o2) {
/* 366 */           return ParentRunner.this.fSorter.compare(ParentRunner.this.describeChild(o1), ParentRunner.this.describeChild(o2));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScheduler(RunnerScheduler scheduler) {
/* 376 */     this.fScheduler = scheduler;
/*     */   }
/*     */   
/*     */   protected abstract List<T> getChildren();
/*     */   
/*     */   protected abstract Description describeChild(T paramT);
/*     */   
/*     */   protected abstract void runChild(T paramT, RunNotifier paramRunNotifier);
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\ParentRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */