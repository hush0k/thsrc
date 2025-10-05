/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Modifier;
/*    */ import junit.framework.Test;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SuiteMethod
/*    */   extends JUnit38ClassRunner
/*    */ {
/*    */   public SuiteMethod(Class<?> klass) throws Throwable {
/* 23 */     super(testFromSuiteMethod(klass));
/*    */   }
/*    */   
/*    */   public static Test testFromSuiteMethod(Class<?> klass) throws Throwable {
/* 27 */     Method suiteMethod = null;
/* 28 */     Test suite = null;
/*    */     try {
/* 30 */       suiteMethod = klass.getMethod("suite", new Class[0]);
/* 31 */       if (!Modifier.isStatic(suiteMethod.getModifiers())) {
/* 32 */         throw new Exception(klass.getName() + ".suite() must be static");
/*    */       }
/* 34 */       suite = (Test)suiteMethod.invoke(null, new Object[0]);
/* 35 */     } catch (InvocationTargetException e) {
/* 36 */       throw e.getCause();
/*    */     } 
/* 38 */     return suite;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\SuiteMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */