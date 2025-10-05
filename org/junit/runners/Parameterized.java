/*     */ package org.junit.runners;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.junit.runner.Runner;
/*     */ import org.junit.runner.notification.RunNotifier;
/*     */ import org.junit.runners.model.FrameworkMethod;
/*     */ import org.junit.runners.model.InitializationError;
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
/*     */ 
/*     */ public class Parameterized
/*     */   extends Suite
/*     */ {
/*     */   private class TestClassRunnerForParameters
/*     */     extends BlockJUnit4ClassRunner
/*     */   {
/*     */     private final int fParameterSetNumber;
/*     */     private final List<Object[]> fParameterList;
/*     */     
/*     */     TestClassRunnerForParameters(Class<?> type, List<Object[]> parameterList, int i) throws InitializationError {
/*  79 */       super(type);
/*  80 */       this.fParameterList = parameterList;
/*  81 */       this.fParameterSetNumber = i;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object createTest() throws Exception {
/*  86 */       return getTestClass().getOnlyConstructor().newInstance(computeParams());
/*     */     }
/*     */ 
/*     */     
/*     */     private Object[] computeParams() throws Exception {
/*     */       try {
/*  92 */         return this.fParameterList.get(this.fParameterSetNumber);
/*  93 */       } catch (ClassCastException e) {
/*  94 */         throw new Exception(String.format("%s.%s() must return a Collection of arrays.", new Object[] { getTestClass().getName(), Parameterized.access$000(this.this$0, getTestClass()).getName() }));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected String getName() {
/* 103 */       return String.format("[%s]", new Object[] { Integer.valueOf(this.fParameterSetNumber) });
/*     */     }
/*     */ 
/*     */     
/*     */     protected String testName(FrameworkMethod method) {
/* 108 */       return String.format("%s[%s]", new Object[] { method.getName(), Integer.valueOf(this.fParameterSetNumber) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void validateConstructor(List<Throwable> errors) {
/* 114 */       validateOnlyOneConstructor(errors);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Statement classBlock(RunNotifier notifier) {
/* 119 */       return childrenInvoker(notifier);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Annotation[] getRunnerAnnotations() {
/* 124 */       return new Annotation[0];
/*     */     }
/*     */   }
/*     */   
/* 128 */   private final ArrayList<Runner> runners = new ArrayList<Runner>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Parameterized(Class<?> klass) throws Throwable {
/* 134 */     super(klass, Collections.emptyList());
/* 135 */     List<Object[]> parametersList = getParametersList(getTestClass());
/* 136 */     for (int i = 0; i < parametersList.size(); i++) {
/* 137 */       this.runners.add(new TestClassRunnerForParameters(getTestClass().getJavaClass(), parametersList, i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<Runner> getChildren() {
/* 143 */     return this.runners;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Object[]> getParametersList(TestClass klass) throws Throwable {
/* 149 */     return (List<Object[]>)getParametersMethod(klass).invokeExplosively(null, new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private FrameworkMethod getParametersMethod(TestClass testClass) throws Exception {
/* 155 */     List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Parameters.class);
/*     */     
/* 157 */     for (FrameworkMethod each : methods) {
/* 158 */       int modifiers = each.getMethod().getModifiers();
/* 159 */       if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
/* 160 */         return each;
/*     */       }
/*     */     } 
/* 163 */     throw new Exception("No public static parameters method on class " + testClass.getName());
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.METHOD})
/*     */   public static @interface Parameters {}
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runners\Parameterized.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */