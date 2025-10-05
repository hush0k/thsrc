/*    */ package org.junit.experimental;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.Future;
/*    */ import org.junit.runner.Computer;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runners.ParentRunner;
/*    */ import org.junit.runners.model.InitializationError;
/*    */ import org.junit.runners.model.RunnerBuilder;
/*    */ import org.junit.runners.model.RunnerScheduler;
/*    */ 
/*    */ public class ParallelComputer
/*    */   extends Computer
/*    */ {
/*    */   private final boolean fClasses;
/*    */   private final boolean fMethods;
/*    */   
/*    */   public ParallelComputer(boolean classes, boolean methods) {
/* 23 */     this.fClasses = classes;
/* 24 */     this.fMethods = methods;
/*    */   }
/*    */   
/*    */   public static Computer classes() {
/* 28 */     return new ParallelComputer(true, false);
/*    */   }
/*    */   
/*    */   public static Computer methods() {
/* 32 */     return new ParallelComputer(false, true);
/*    */   }
/*    */   
/*    */   private static <T> Runner parallelize(Runner runner) {
/* 36 */     if (runner instanceof ParentRunner)
/* 37 */       ((ParentRunner)runner).setScheduler(new RunnerScheduler() {
/* 38 */             private final List<Future<Object>> fResults = new ArrayList<Future<Object>>();
/*    */             
/* 40 */             private final ExecutorService fService = Executors.newCachedThreadPool();
/*    */ 
/*    */             
/*    */             public void schedule(final Runnable childStatement) {
/* 44 */               this.fResults.add(this.fService.submit(new Callable() {
/*    */                       public Object call() throws Exception {
/* 46 */                         childStatement.run();
/* 47 */                         return null;
/*    */                       }
/*    */                     }));
/*    */             }
/*    */             
/*    */             public void finished() {
/* 53 */               for (Future<Object> each : this.fResults) {
/*    */                 try {
/* 55 */                   each.get();
/* 56 */                 } catch (Exception e) {
/* 57 */                   e.printStackTrace();
/*    */                 } 
/*    */               } 
/*    */             }
/*    */           }); 
/* 62 */     return runner;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Runner getSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
/* 68 */     Runner suite = super.getSuite(builder, classes);
/* 69 */     return this.fClasses ? parallelize(suite) : suite;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Runner getRunner(RunnerBuilder builder, Class<?> testClass) throws Throwable {
/* 75 */     Runner runner = super.getRunner(builder, testClass);
/* 76 */     return this.fMethods ? parallelize(runner) : runner;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\ParallelComputer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */