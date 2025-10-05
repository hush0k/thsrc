/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.junit.After;
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
/*    */ 
/*    */ @Deprecated
/*    */ public class MethodValidator
/*    */ {
/* 24 */   private final List<Throwable> fErrors = new ArrayList<Throwable>();
/*    */   
/*    */   private TestClass fTestClass;
/*    */   
/*    */   public MethodValidator(TestClass testClass) {
/* 29 */     this.fTestClass = testClass;
/*    */   }
/*    */   
/*    */   public void validateInstanceMethods() {
/* 33 */     validateTestMethods((Class)After.class, false);
/* 34 */     validateTestMethods((Class)Before.class, false);
/* 35 */     validateTestMethods((Class)Test.class, false);
/*    */     
/* 37 */     List<Method> methods = this.fTestClass.getAnnotatedMethods((Class)Test.class);
/* 38 */     if (methods.size() == 0)
/* 39 */       this.fErrors.add(new Exception("No runnable methods")); 
/*    */   }
/*    */   
/*    */   public void validateStaticMethods() {
/* 43 */     validateTestMethods((Class)BeforeClass.class, true);
/* 44 */     validateTestMethods((Class)AfterClass.class, true);
/*    */   }
/*    */   
/*    */   public List<Throwable> validateMethodsForDefaultRunner() {
/* 48 */     validateNoArgConstructor();
/* 49 */     validateStaticMethods();
/* 50 */     validateInstanceMethods();
/* 51 */     return this.fErrors;
/*    */   }
/*    */   
/*    */   public void assertValid() throws InitializationError {
/* 55 */     if (!this.fErrors.isEmpty())
/* 56 */       throw new InitializationError(this.fErrors); 
/*    */   }
/*    */   
/*    */   public void validateNoArgConstructor() {
/*    */     try {
/* 61 */       this.fTestClass.getConstructor();
/* 62 */     } catch (Exception e) {
/* 63 */       this.fErrors.add(new Exception("Test class should have public zero-argument constructor", e));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void validateTestMethods(Class<? extends Annotation> annotation, boolean isStatic) {
/* 69 */     List<Method> methods = this.fTestClass.getAnnotatedMethods(annotation);
/*    */     
/* 71 */     for (Method each : methods) {
/* 72 */       if (Modifier.isStatic(each.getModifiers()) != isStatic) {
/* 73 */         String state = isStatic ? "should" : "should not";
/* 74 */         this.fErrors.add(new Exception("Method " + each.getName() + "() " + state + " be static"));
/*    */       } 
/*    */       
/* 77 */       if (!Modifier.isPublic(each.getDeclaringClass().getModifiers())) {
/* 78 */         this.fErrors.add(new Exception("Class " + each.getDeclaringClass().getName() + " should be public"));
/*    */       }
/* 80 */       if (!Modifier.isPublic(each.getModifiers())) {
/* 81 */         this.fErrors.add(new Exception("Method " + each.getName() + " should be public"));
/*    */       }
/* 83 */       if (each.getReturnType() != void.class) {
/* 84 */         this.fErrors.add(new Exception("Method " + each.getName() + " should be void"));
/*    */       }
/* 86 */       if ((each.getParameterTypes()).length != 0)
/* 87 */         this.fErrors.add(new Exception("Method " + each.getName() + " should have no parameters")); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\MethodValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */