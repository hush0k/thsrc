/*     */ package junit.textui;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Enumeration;
/*     */ import junit.framework.AssertionFailedError;
/*     */ import junit.framework.Test;
/*     */ import junit.framework.TestFailure;
/*     */ import junit.framework.TestListener;
/*     */ import junit.framework.TestResult;
/*     */ import junit.runner.BaseTestRunner;
/*     */ 
/*     */ public class ResultPrinter
/*     */   implements TestListener
/*     */ {
/*     */   PrintStream fWriter;
/*  17 */   int fColumn = 0;
/*     */   
/*     */   public ResultPrinter(PrintStream writer) {
/*  20 */     this.fWriter = writer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void print(TestResult result, long runTime) {
/*  27 */     printHeader(runTime);
/*  28 */     printErrors(result);
/*  29 */     printFailures(result);
/*  30 */     printFooter(result);
/*     */   }
/*     */   
/*     */   void printWaitPrompt() {
/*  34 */     getWriter().println();
/*  35 */     getWriter().println("<RETURN> to continue");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void printHeader(long runTime) {
/*  42 */     getWriter().println();
/*  43 */     getWriter().println("Time: " + elapsedTimeAsString(runTime));
/*     */   }
/*     */   
/*     */   protected void printErrors(TestResult result) {
/*  47 */     printDefects(result.errors(), result.errorCount(), "error");
/*     */   }
/*     */   
/*     */   protected void printFailures(TestResult result) {
/*  51 */     printDefects(result.failures(), result.failureCount(), "failure");
/*     */   }
/*     */   
/*     */   protected void printDefects(Enumeration<TestFailure> booBoos, int count, String type) {
/*  55 */     if (count == 0)
/*  56 */       return;  if (count == 1) {
/*  57 */       getWriter().println("There was " + count + " " + type + ":");
/*     */     } else {
/*  59 */       getWriter().println("There were " + count + " " + type + "s:");
/*  60 */     }  for (int i = 1; booBoos.hasMoreElements(); i++) {
/*  61 */       printDefect(booBoos.nextElement(), i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void printDefect(TestFailure booBoo, int count) {
/*  66 */     printDefectHeader(booBoo, count);
/*  67 */     printDefectTrace(booBoo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void printDefectHeader(TestFailure booBoo, int count) {
/*  73 */     getWriter().print(count + ") " + booBoo.failedTest());
/*     */   }
/*     */   
/*     */   protected void printDefectTrace(TestFailure booBoo) {
/*  77 */     getWriter().print(BaseTestRunner.getFilteredTrace(booBoo.trace()));
/*     */   }
/*     */   
/*     */   protected void printFooter(TestResult result) {
/*  81 */     if (result.wasSuccessful()) {
/*  82 */       getWriter().println();
/*  83 */       getWriter().print("OK");
/*  84 */       getWriter().println(" (" + result.runCount() + " test" + ((result.runCount() == 1) ? "" : "s") + ")");
/*     */     } else {
/*     */       
/*  87 */       getWriter().println();
/*  88 */       getWriter().println("FAILURES!!!");
/*  89 */       getWriter().println("Tests run: " + result.runCount() + ",  Failures: " + result.failureCount() + ",  Errors: " + result.errorCount());
/*     */     } 
/*     */ 
/*     */     
/*  93 */     getWriter().println();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String elapsedTimeAsString(long runTime) {
/* 102 */     return NumberFormat.getInstance().format(runTime / 1000.0D);
/*     */   }
/*     */   
/*     */   public PrintStream getWriter() {
/* 106 */     return this.fWriter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addError(Test test, Throwable t) {
/* 112 */     getWriter().print("E");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFailure(Test test, AssertionFailedError t) {
/* 119 */     getWriter().print("F");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endTest(Test test) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTest(Test test) {
/* 132 */     getWriter().print(".");
/* 133 */     if (this.fColumn++ >= 40) {
/* 134 */       getWriter().println();
/* 135 */       this.fColumn = 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\junit\textui\ResultPrinter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */