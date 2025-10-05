/*    */ package org.junit.internal.runners;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.List;
/*    */ import org.junit.internal.AssumptionViolatedException;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.notification.Failure;
/*    */ import org.junit.runner.notification.RunNotifier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ClassRoadie
/*    */ {
/*    */   private RunNotifier fNotifier;
/*    */   private TestClass fTestClass;
/*    */   private Description fDescription;
/*    */   private final Runnable fRunnable;
/*    */   
/*    */   public ClassRoadie(RunNotifier notifier, TestClass testClass, Description description, Runnable runnable) {
/* 27 */     this.fNotifier = notifier;
/* 28 */     this.fTestClass = testClass;
/* 29 */     this.fDescription = description;
/* 30 */     this.fRunnable = runnable;
/*    */   }
/*    */   
/*    */   protected void runUnprotected() {
/* 34 */     this.fRunnable.run();
/*    */   }
/*    */   
/*    */   protected void addFailure(Throwable targetException) {
/* 38 */     this.fNotifier.fireTestFailure(new Failure(this.fDescription, targetException));
/*    */   }
/*    */   
/*    */   public void runProtected() {
/*    */     
/* 43 */     try { runBefores();
/* 44 */       runUnprotected(); }
/* 45 */     catch (FailedBefore e) {  }
/*    */     finally
/* 47 */     { runAfters(); }
/*    */   
/*    */   }
/*    */   
/*    */   private void runBefores() throws FailedBefore {
/*    */     try {
/*    */       try {
/* 54 */         List<Method> befores = this.fTestClass.getBefores();
/* 55 */         for (Method before : befores)
/* 56 */           before.invoke(null, new Object[0]); 
/* 57 */       } catch (InvocationTargetException e) {
/* 58 */         throw e.getTargetException();
/*    */       } 
/* 60 */     } catch (AssumptionViolatedException e) {
/* 61 */       throw new FailedBefore();
/* 62 */     } catch (Throwable e) {
/* 63 */       addFailure(e);
/* 64 */       throw new FailedBefore();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void runAfters() {
/* 69 */     List<Method> afters = this.fTestClass.getAfters();
/* 70 */     for (Method after : afters) {
/*    */       try {
/* 72 */         after.invoke(null, new Object[0]);
/* 73 */       } catch (InvocationTargetException e) {
/* 74 */         addFailure(e.getTargetException());
/* 75 */       } catch (Throwable e) {
/* 76 */         addFailure(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\ClassRoadie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */