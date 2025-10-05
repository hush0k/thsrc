/*     */ package org.junit.runner;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import junit.framework.Test;
/*     */ import junit.runner.Version;
/*     */ import org.junit.internal.JUnitSystem;
/*     */ import org.junit.internal.RealSystem;
/*     */ import org.junit.internal.TextListener;
/*     */ import org.junit.internal.runners.JUnit38ClassRunner;
/*     */ import org.junit.runner.notification.Failure;
/*     */ import org.junit.runner.notification.RunListener;
/*     */ import org.junit.runner.notification.RunNotifier;
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
/*     */ public class JUnitCore
/*     */ {
/*  34 */   private RunNotifier fNotifier = new RunNotifier();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String... args) {
/*  45 */     runMainAndExit((JUnitSystem)new RealSystem(), args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void runMainAndExit(JUnitSystem system, String... args) {
/*  53 */     Result result = (new JUnitCore()).runMain(system, args);
/*  54 */     system.exit(result.wasSuccessful() ? 0 : 1);
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
/*     */   public static Result runClasses(Computer computer, Class<?>... classes) {
/*  66 */     return (new JUnitCore()).run(computer, classes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Result runClasses(Class<?>... classes) {
/*  76 */     return (new JUnitCore()).run(defaultComputer(), classes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result runMain(JUnitSystem system, String... args) {
/*  84 */     system.out().println("JUnit version " + Version.id());
/*  85 */     List<Class<?>> classes = new ArrayList<Class<?>>();
/*  86 */     List<Failure> missingClasses = new ArrayList<Failure>();
/*  87 */     for (String each : args) {
/*     */       try {
/*  89 */         classes.add(Class.forName(each));
/*  90 */       } catch (ClassNotFoundException e) {
/*  91 */         system.out().println("Could not find class: " + each);
/*  92 */         Description description = Description.createSuiteDescription(each, new java.lang.annotation.Annotation[0]);
/*  93 */         Failure failure = new Failure(description, e);
/*  94 */         missingClasses.add(failure);
/*     */       } 
/*  96 */     }  TextListener textListener = new TextListener(system);
/*  97 */     addListener((RunListener)textListener);
/*  98 */     Result result = run((Class[])classes.<Class<?>[]>toArray((Class<?>[][])new Class[0]));
/*  99 */     for (Failure each : missingClasses)
/* 100 */       result.getFailures().add(each); 
/* 101 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 108 */     return Version.id();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result run(Class<?>... classes) {
/* 117 */     return run(Request.classes(defaultComputer(), classes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result run(Computer computer, Class<?>... classes) {
/* 127 */     return run(Request.classes(computer, classes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result run(Request request) {
/* 136 */     return run(request.getRunner());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result run(Test test) {
/* 145 */     return run((Runner)new JUnit38ClassRunner(test));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result run(Runner runner) {
/* 152 */     Result result = new Result();
/* 153 */     RunListener listener = result.createListener();
/* 154 */     this.fNotifier.addFirstListener(listener);
/*     */     try {
/* 156 */       this.fNotifier.fireTestRunStarted(runner.getDescription());
/* 157 */       runner.run(this.fNotifier);
/* 158 */       this.fNotifier.fireTestRunFinished(result);
/*     */     } finally {
/* 160 */       removeListener(listener);
/*     */     } 
/* 162 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(RunListener listener) {
/* 171 */     this.fNotifier.addListener(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(RunListener listener) {
/* 179 */     this.fNotifier.removeListener(listener);
/*     */   }
/*     */   
/*     */   static Computer defaultComputer() {
/* 183 */     return new Computer();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\JUnitCore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */