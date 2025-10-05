/*     */ package org.junit.internal.runners;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import org.junit.internal.AssumptionViolatedException;
/*     */ import org.junit.runner.Description;
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
/*     */ public class MethodRoadie
/*     */ {
/*     */   private final Object fTest;
/*     */   private final RunNotifier fNotifier;
/*     */   private final Description fDescription;
/*     */   private TestMethod fTestMethod;
/*     */   
/*     */   public MethodRoadie(Object test, TestMethod method, RunNotifier notifier, Description description) {
/*  32 */     this.fTest = test;
/*  33 */     this.fNotifier = notifier;
/*  34 */     this.fDescription = description;
/*  35 */     this.fTestMethod = method;
/*     */   }
/*     */   
/*     */   public void run() {
/*  39 */     if (this.fTestMethod.isIgnored()) {
/*  40 */       this.fNotifier.fireTestIgnored(this.fDescription);
/*     */       return;
/*     */     } 
/*  43 */     this.fNotifier.fireTestStarted(this.fDescription);
/*     */     
/*  45 */     try { long timeout = this.fTestMethod.getTimeout();
/*  46 */       if (timeout > 0L) {
/*  47 */         runWithTimeout(timeout);
/*     */       } else {
/*  49 */         runTest();
/*     */       }  }
/*  51 */     finally { this.fNotifier.fireTestFinished(this.fDescription); }
/*     */   
/*     */   }
/*     */   
/*     */   private void runWithTimeout(final long timeout) {
/*  56 */     runBeforesThenTestThenAfters(new Runnable()
/*     */         {
/*     */           public void run() {
/*  59 */             ExecutorService service = Executors.newSingleThreadExecutor();
/*  60 */             Callable<Object> callable = new Callable() {
/*     */                 public Object call() throws Exception {
/*  62 */                   MethodRoadie.this.runTestMethod();
/*  63 */                   return null;
/*     */                 }
/*     */               };
/*  66 */             Future<Object> result = service.submit(callable);
/*  67 */             service.shutdown();
/*     */             try {
/*  69 */               boolean terminated = service.awaitTermination(timeout, TimeUnit.MILLISECONDS);
/*     */               
/*  71 */               if (!terminated)
/*  72 */                 service.shutdownNow(); 
/*  73 */               result.get(0L, TimeUnit.MILLISECONDS);
/*  74 */             } catch (TimeoutException e) {
/*  75 */               MethodRoadie.this.addFailure(new Exception(String.format("test timed out after %d milliseconds", new Object[] { Long.valueOf(this.val$timeout) })));
/*  76 */             } catch (Exception e) {
/*  77 */               MethodRoadie.this.addFailure(e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void runTest() {
/*  84 */     runBeforesThenTestThenAfters(new Runnable() {
/*     */           public void run() {
/*  86 */             MethodRoadie.this.runTestMethod();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void runBeforesThenTestThenAfters(Runnable test) {
/*     */     
/*  93 */     try { runBefores();
/*  94 */       test.run(); }
/*  95 */     catch (FailedBefore e) {  }
/*  96 */     catch (Exception e)
/*  97 */     { throw new RuntimeException("test should never throw an exception to this level"); }
/*     */     finally
/*  99 */     { runAfters(); }
/*     */   
/*     */   }
/*     */   
/*     */   protected void runTestMethod() {
/*     */     try {
/* 105 */       this.fTestMethod.invoke(this.fTest);
/* 106 */       if (this.fTestMethod.expectsException())
/* 107 */         addFailure(new AssertionError("Expected exception: " + this.fTestMethod.getExpectedException().getName())); 
/* 108 */     } catch (InvocationTargetException e) {
/* 109 */       Throwable actual = e.getTargetException();
/* 110 */       if (actual instanceof AssumptionViolatedException)
/*     */         return; 
/* 112 */       if (!this.fTestMethod.expectsException()) {
/* 113 */         addFailure(actual);
/* 114 */       } else if (this.fTestMethod.isUnexpected(actual)) {
/* 115 */         String message = "Unexpected exception, expected<" + this.fTestMethod.getExpectedException().getName() + "> but was<" + actual.getClass().getName() + ">";
/*     */         
/* 117 */         addFailure(new Exception(message, actual));
/*     */       } 
/* 119 */     } catch (Throwable e) {
/* 120 */       addFailure(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runBefores() throws FailedBefore {
/*     */     try {
/*     */       try {
/* 127 */         List<Method> befores = this.fTestMethod.getBefores();
/* 128 */         for (Method before : befores)
/* 129 */           before.invoke(this.fTest, new Object[0]); 
/* 130 */       } catch (InvocationTargetException e) {
/* 131 */         throw e.getTargetException();
/*     */       } 
/* 133 */     } catch (AssumptionViolatedException e) {
/* 134 */       throw new FailedBefore();
/* 135 */     } catch (Throwable e) {
/* 136 */       addFailure(e);
/* 137 */       throw new FailedBefore();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void runAfters() {
/* 142 */     List<Method> afters = this.fTestMethod.getAfters();
/* 143 */     for (Method after : afters) {
/*     */       try {
/* 145 */         after.invoke(this.fTest, new Object[0]);
/* 146 */       } catch (InvocationTargetException e) {
/* 147 */         addFailure(e.getTargetException());
/* 148 */       } catch (Throwable e) {
/* 149 */         addFailure(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected void addFailure(Throwable e) {
/* 154 */     this.fNotifier.fireTestFailure(new Failure(this.fDescription, e));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\runners\MethodRoadie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */