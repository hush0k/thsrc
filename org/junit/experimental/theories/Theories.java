/*     */ package org.junit.experimental.theories;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.junit.Assert;
/*     */ import org.junit.experimental.theories.internal.Assignments;
/*     */ import org.junit.experimental.theories.internal.ParameterizedAssertionError;
/*     */ import org.junit.internal.AssumptionViolatedException;
/*     */ import org.junit.runners.BlockJUnit4ClassRunner;
/*     */ import org.junit.runners.model.FrameworkMethod;
/*     */ import org.junit.runners.model.InitializationError;
/*     */ import org.junit.runners.model.Statement;
/*     */ import org.junit.runners.model.TestClass;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Theories
/*     */   extends BlockJUnit4ClassRunner
/*     */ {
/*     */   public Theories(Class<?> klass) throws InitializationError {
/*  25 */     super(klass);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collectInitializationErrors(List<Throwable> errors) {
/*  30 */     super.collectInitializationErrors(errors);
/*  31 */     validateDataPointFields(errors);
/*     */   }
/*     */   
/*     */   private void validateDataPointFields(List<Throwable> errors) {
/*  35 */     Field[] fields = getTestClass().getJavaClass().getDeclaredFields();
/*     */     
/*  37 */     for (Field each : fields) {
/*  38 */       if (each.getAnnotation(DataPoint.class) != null && !Modifier.isStatic(each.getModifiers()))
/*  39 */         errors.add(new Error("DataPoint field " + each.getName() + " must be static")); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void validateConstructor(List<Throwable> errors) {
/*  44 */     validateOnlyOneConstructor(errors);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validateTestMethods(List<Throwable> errors) {
/*  49 */     for (FrameworkMethod each : computeTestMethods()) {
/*  50 */       if (each.getAnnotation(Theory.class) != null) {
/*  51 */         each.validatePublicVoid(false, errors); continue;
/*     */       } 
/*  53 */       each.validatePublicVoidNoArg(false, errors);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List<FrameworkMethod> computeTestMethods() {
/*  58 */     List<FrameworkMethod> testMethods = super.computeTestMethods();
/*  59 */     List<FrameworkMethod> theoryMethods = getTestClass().getAnnotatedMethods(Theory.class);
/*  60 */     testMethods.removeAll(theoryMethods);
/*  61 */     testMethods.addAll(theoryMethods);
/*  62 */     return testMethods;
/*     */   }
/*     */ 
/*     */   
/*     */   public Statement methodBlock(FrameworkMethod method) {
/*  67 */     return new TheoryAnchor(method, getTestClass());
/*     */   }
/*     */   
/*     */   public static class TheoryAnchor extends Statement {
/*  71 */     private int successes = 0;
/*     */     
/*     */     private FrameworkMethod fTestMethod;
/*     */     
/*     */     private TestClass fTestClass;
/*  76 */     private List<AssumptionViolatedException> fInvalidParameters = new ArrayList<AssumptionViolatedException>();
/*     */     
/*     */     public TheoryAnchor(FrameworkMethod method, TestClass testClass) {
/*  79 */       this.fTestMethod = method;
/*  80 */       this.fTestClass = testClass;
/*     */     }
/*     */     
/*     */     private TestClass getTestClass() {
/*  84 */       return this.fTestClass;
/*     */     }
/*     */ 
/*     */     
/*     */     public void evaluate() throws Throwable {
/*  89 */       runWithAssignment(Assignments.allUnassigned(this.fTestMethod.getMethod(), getTestClass()));
/*     */ 
/*     */       
/*  92 */       if (this.successes == 0) {
/*  93 */         Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: " + this.fInvalidParameters);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void runWithAssignment(Assignments parameterAssignment) throws Throwable {
/* 100 */       if (!parameterAssignment.isComplete()) {
/* 101 */         runWithIncompleteAssignment(parameterAssignment);
/*     */       } else {
/* 103 */         runWithCompleteAssignment(parameterAssignment);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void runWithIncompleteAssignment(Assignments incomplete) throws InstantiationException, IllegalAccessException, Throwable {
/* 110 */       for (PotentialAssignment source : incomplete.potentialsForNextUnassigned())
/*     */       {
/* 112 */         runWithAssignment(incomplete.assignNext(source));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void runWithCompleteAssignment(final Assignments complete) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Throwable {
/* 119 */       (new BlockJUnit4ClassRunner(getTestClass().getJavaClass())
/*     */         {
/*     */           protected void collectInitializationErrors(List<Throwable> errors) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public Statement methodBlock(FrameworkMethod method) {
/* 128 */             final Statement statement = super.methodBlock(method);
/* 129 */             return new Statement()
/*     */               {
/*     */                 public void evaluate() throws Throwable {
/*     */                   try {
/* 133 */                     statement.evaluate();
/* 134 */                     Theories.TheoryAnchor.this.handleDataPointSuccess();
/* 135 */                   } catch (AssumptionViolatedException e) {
/* 136 */                     Theories.TheoryAnchor.this.handleAssumptionViolation(e);
/* 137 */                   } catch (Throwable e) {
/* 138 */                     Theories.TheoryAnchor.this.reportParameterizedError(e, complete.getArgumentStrings(Theories.TheoryAnchor.this.nullsOk()));
/*     */                   } 
/*     */                 }
/*     */               };
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           protected Statement methodInvoker(FrameworkMethod method, Object test) {
/* 148 */             return Theories.TheoryAnchor.this.methodCompletesWithParameters(method, complete, test);
/*     */           }
/*     */ 
/*     */           
/*     */           public Object createTest() throws Exception {
/* 153 */             return getTestClass().getOnlyConstructor().newInstance(complete.getConstructorArguments(Theories.TheoryAnchor.this.nullsOk()));
/*     */           }
/*     */         }).methodBlock(this.fTestMethod).evaluate();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Statement methodCompletesWithParameters(final FrameworkMethod method, final Assignments complete, final Object freshInstance) {
/* 161 */       return new Statement()
/*     */         {
/*     */           public void evaluate() throws Throwable {
/*     */             try {
/* 165 */               Object[] values = complete.getMethodArguments(Theories.TheoryAnchor.this.nullsOk());
/*     */               
/* 167 */               method.invokeExplosively(freshInstance, values);
/* 168 */             } catch (CouldNotGenerateValueException e) {}
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void handleAssumptionViolation(AssumptionViolatedException e) {
/* 176 */       this.fInvalidParameters.add(e);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void reportParameterizedError(Throwable e, Object... params) throws Throwable {
/* 181 */       if (params.length == 0)
/* 182 */         throw e; 
/* 183 */       throw new ParameterizedAssertionError(e, this.fTestMethod.getName(), params);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean nullsOk() {
/* 188 */       Theory annotation = this.fTestMethod.getMethod().<Theory>getAnnotation(Theory.class);
/*     */       
/* 190 */       if (annotation == null)
/* 191 */         return false; 
/* 192 */       return annotation.nullsAccepted();
/*     */     }
/*     */     
/*     */     protected void handleDataPointSuccess() {
/* 196 */       this.successes++;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\theories\Theories.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */