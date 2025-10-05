/*     */ package org.junit.runner;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.junit.runner.notification.Failure;
/*     */ import org.junit.runner.notification.RunListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Result
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  19 */   private AtomicInteger fCount = new AtomicInteger();
/*  20 */   private AtomicInteger fIgnoreCount = new AtomicInteger();
/*  21 */   private final List<Failure> fFailures = Collections.synchronizedList(new ArrayList<Failure>());
/*  22 */   private long fRunTime = 0L;
/*     */ 
/*     */   
/*     */   private long fStartTime;
/*     */ 
/*     */   
/*     */   public int getRunCount() {
/*  29 */     return this.fCount.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFailureCount() {
/*  36 */     return this.fFailures.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getRunTime() {
/*  43 */     return this.fRunTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Failure> getFailures() {
/*  50 */     return this.fFailures;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIgnoreCount() {
/*  57 */     return this.fIgnoreCount.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean wasSuccessful() {
/*  64 */     return (getFailureCount() == 0);
/*     */   }
/*     */   
/*     */   private class Listener
/*     */     extends RunListener {
/*     */     public void testRunStarted(Description description) throws Exception {
/*  70 */       Result.this.fStartTime = System.currentTimeMillis();
/*     */     }
/*     */     private Listener() {}
/*     */     
/*     */     public void testRunFinished(Result result) throws Exception {
/*  75 */       long endTime = System.currentTimeMillis();
/*  76 */       Result.this.fRunTime += endTime - Result.this.fStartTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public void testFinished(Description description) throws Exception {
/*  81 */       Result.this.fCount.getAndIncrement();
/*     */     }
/*     */ 
/*     */     
/*     */     public void testFailure(Failure failure) throws Exception {
/*  86 */       Result.this.fFailures.add(failure);
/*     */     }
/*     */ 
/*     */     
/*     */     public void testIgnored(Description description) throws Exception {
/*  91 */       Result.this.fIgnoreCount.getAndIncrement();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void testAssumptionFailure(Failure failure) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RunListener createListener() {
/* 104 */     return new Listener();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\Result.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */