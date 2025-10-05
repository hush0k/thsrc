/*     */ package org.junit.runner.notification;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.junit.runner.Description;
/*     */ import org.junit.runner.Result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RunNotifier
/*     */ {
/*  20 */   private final List<RunListener> fListeners = Collections.synchronizedList(new ArrayList<RunListener>());
/*     */ 
/*     */   
/*     */   private boolean fPleaseStop = false;
/*     */ 
/*     */   
/*     */   public void addListener(RunListener listener) {
/*  27 */     this.fListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(RunListener listener) {
/*  33 */     this.fListeners.remove(listener);
/*     */   }
/*     */   
/*     */   private abstract class SafeNotifier {
/*     */     void run() {
/*  38 */       synchronized (RunNotifier.this.fListeners) {
/*  39 */         for (Iterator<RunListener> all = RunNotifier.this.fListeners.iterator(); all.hasNext();) {
/*     */           try {
/*  41 */             notifyListener(all.next());
/*  42 */           } catch (Exception e) {
/*  43 */             all.remove();
/*  44 */             RunNotifier.this.fireTestFailure(new Failure(Description.TEST_MECHANISM, e));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SafeNotifier() {}
/*     */     
/*     */     protected abstract void notifyListener(RunListener param1RunListener) throws Exception;
/*     */   }
/*     */   
/*     */   public void fireTestRunStarted(final Description description) {
/*  56 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/*  59 */           each.testRunStarted(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestRunFinished(final Result result) {
/*  68 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/*  71 */           each.testRunFinished(result);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestStarted(final Description description) throws StoppedByUserException {
/*  82 */     if (this.fPleaseStop)
/*  83 */       throw new StoppedByUserException(); 
/*  84 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/*  87 */           each.testStarted(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestFailure(final Failure failure) {
/*  97 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 100 */           each.testFailure(failure);
/*     */         }
/*     */       }).run();
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
/*     */   public void fireTestAssumptionFailed(final Failure failure) {
/* 114 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 117 */           each.testAssumptionFailure(failure);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestIgnored(final Description description) {
/* 127 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 130 */           each.testIgnored(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireTestFinished(final Description description) {
/* 142 */     (new SafeNotifier()
/*     */       {
/*     */         protected void notifyListener(RunListener each) throws Exception {
/* 145 */           each.testFinished(description);
/*     */         }
/*     */       }).run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pleaseStop() {
/* 157 */     this.fPleaseStop = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFirstListener(RunListener listener) {
/* 164 */     this.fListeners.add(0, listener);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\notification\RunNotifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */