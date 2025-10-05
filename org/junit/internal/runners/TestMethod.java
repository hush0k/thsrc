/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.List;
/*    */ import org.junit.After;
/*    */ import org.junit.Before;
/*    */ import org.junit.Ignore;
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
/*    */ public class TestMethod
/*    */ {
/*    */   private final Method fMethod;
/*    */   private TestClass fTestClass;
/*    */   
/*    */   public TestMethod(Method method, TestClass testClass) {
/* 25 */     this.fMethod = method;
/* 26 */     this.fTestClass = testClass;
/*    */   }
/*    */   
/*    */   public boolean isIgnored() {
/* 30 */     return (this.fMethod.getAnnotation(Ignore.class) != null);
/*    */   }
/*    */   
/*    */   public long getTimeout() {
/* 34 */     Test annotation = this.fMethod.<Test>getAnnotation(Test.class);
/* 35 */     if (annotation == null)
/* 36 */       return 0L; 
/* 37 */     long timeout = annotation.timeout();
/* 38 */     return timeout;
/*    */   }
/*    */   
/*    */   protected Class<? extends Throwable> getExpectedException() {
/* 42 */     Test annotation = this.fMethod.<Test>getAnnotation(Test.class);
/* 43 */     if (annotation == null || annotation.expected() == Test.None.class) {
/* 44 */       return null;
/*    */     }
/* 46 */     return annotation.expected();
/*    */   }
/*    */   
/*    */   boolean isUnexpected(Throwable exception) {
/* 50 */     return !getExpectedException().isAssignableFrom(exception.getClass());
/*    */   }
/*    */   
/*    */   boolean expectsException() {
/* 54 */     return (getExpectedException() != null);
/*    */   }
/*    */   
/*    */   List<Method> getBefores() {
/* 58 */     return this.fTestClass.getAnnotatedMethods((Class)Before.class);
/*    */   }
/*    */   
/*    */   List<Method> getAfters() {
/* 62 */     return this.fTestClass.getAnnotatedMethods((Class)After.class);
/*    */   }
/*    */   
/*    */   public void invoke(Object test) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
/* 66 */     this.fMethod.invoke(test, new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\TestMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */