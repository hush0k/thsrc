/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.junit.AfterClass;
/*    */ import org.junit.Before;
/*    */ import org.junit.BeforeClass;
/*    */ import org.junit.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class TestClass
/*    */ {
/*    */   private final Class<?> fClass;
/*    */   
/*    */   public TestClass(Class<?> klass) {
/* 26 */     this.fClass = klass;
/*    */   }
/*    */   
/*    */   public List<Method> getTestMethods() {
/* 30 */     return getAnnotatedMethods((Class)Test.class);
/*    */   }
/*    */   
/*    */   List<Method> getBefores() {
/* 34 */     return getAnnotatedMethods((Class)BeforeClass.class);
/*    */   }
/*    */   
/*    */   List<Method> getAfters() {
/* 38 */     return getAnnotatedMethods((Class)AfterClass.class);
/*    */   }
/*    */   
/*    */   public List<Method> getAnnotatedMethods(Class<? extends Annotation> annotationClass) {
/* 42 */     List<Method> results = new ArrayList<Method>();
/* 43 */     for (Class<?> eachClass : getSuperClasses(this.fClass)) {
/* 44 */       Method[] methods = eachClass.getDeclaredMethods();
/* 45 */       for (Method eachMethod : methods) {
/* 46 */         Annotation annotation = eachMethod.getAnnotation((Class)annotationClass);
/* 47 */         if (annotation != null && !isShadowed(eachMethod, results))
/* 48 */           results.add(eachMethod); 
/*    */       } 
/*    */     } 
/* 51 */     if (runsTopToBottom(annotationClass))
/* 52 */       Collections.reverse(results); 
/* 53 */     return results;
/*    */   }
/*    */   
/*    */   private boolean runsTopToBottom(Class<? extends Annotation> annotation) {
/* 57 */     return (annotation.equals(Before.class) || annotation.equals(BeforeClass.class));
/*    */   }
/*    */   
/*    */   private boolean isShadowed(Method method, List<Method> results) {
/* 61 */     for (Method each : results) {
/* 62 */       if (isShadowed(method, each))
/* 63 */         return true; 
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   private boolean isShadowed(Method current, Method previous) {
/* 69 */     if (!previous.getName().equals(current.getName()))
/* 70 */       return false; 
/* 71 */     if ((previous.getParameterTypes()).length != (current.getParameterTypes()).length)
/* 72 */       return false; 
/* 73 */     for (int i = 0; i < (previous.getParameterTypes()).length; i++) {
/* 74 */       if (!previous.getParameterTypes()[i].equals(current.getParameterTypes()[i]))
/* 75 */         return false; 
/*    */     } 
/* 77 */     return true;
/*    */   }
/*    */   
/*    */   private List<Class<?>> getSuperClasses(Class<?> testClass) {
/* 81 */     ArrayList<Class<?>> results = new ArrayList<Class<?>>();
/* 82 */     Class<?> current = testClass;
/* 83 */     while (current != null) {
/* 84 */       results.add(current);
/* 85 */       current = current.getSuperclass();
/*    */     } 
/* 87 */     return results;
/*    */   }
/*    */   
/*    */   public Constructor<?> getConstructor() throws SecurityException, NoSuchMethodException {
/* 91 */     return this.fClass.getConstructor(new Class[0]);
/*    */   }
/*    */   
/*    */   public Class<?> getJavaClass() {
/* 95 */     return this.fClass;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 99 */     return this.fClass.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\TestClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */