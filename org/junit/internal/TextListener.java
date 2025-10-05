/*    */ package org.junit.internal;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.List;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.Result;
/*    */ import org.junit.runner.notification.Failure;
/*    */ import org.junit.runner.notification.RunListener;
/*    */ 
/*    */ public class TextListener
/*    */   extends RunListener
/*    */ {
/*    */   private final PrintStream fWriter;
/*    */   
/*    */   public TextListener(JUnitSystem system) {
/* 17 */     this(system.out());
/*    */   }
/*    */   
/*    */   public TextListener(PrintStream writer) {
/* 21 */     this.fWriter = writer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void testRunFinished(Result result) {
/* 26 */     printHeader(result.getRunTime());
/* 27 */     printFailures(result);
/* 28 */     printFooter(result);
/*    */   }
/*    */ 
/*    */   
/*    */   public void testStarted(Description description) {
/* 33 */     this.fWriter.append('.');
/*    */   }
/*    */ 
/*    */   
/*    */   public void testFailure(Failure failure) {
/* 38 */     this.fWriter.append('E');
/*    */   }
/*    */ 
/*    */   
/*    */   public void testIgnored(Description description) {
/* 43 */     this.fWriter.append('I');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private PrintStream getWriter() {
/* 51 */     return this.fWriter;
/*    */   }
/*    */   
/*    */   protected void printHeader(long runTime) {
/* 55 */     getWriter().println();
/* 56 */     getWriter().println("Time: " + elapsedTimeAsString(runTime));
/*    */   }
/*    */   
/*    */   protected void printFailures(Result result) {
/* 60 */     List<Failure> failures = result.getFailures();
/* 61 */     if (failures.size() == 0)
/*    */       return; 
/* 63 */     if (failures.size() == 1) {
/* 64 */       getWriter().println("There was " + failures.size() + " failure:");
/*    */     } else {
/* 66 */       getWriter().println("There were " + failures.size() + " failures:");
/* 67 */     }  int i = 1;
/* 68 */     for (Failure each : failures)
/* 69 */       printFailure(each, "" + i++); 
/*    */   }
/*    */   
/*    */   protected void printFailure(Failure each, String prefix) {
/* 73 */     getWriter().println(prefix + ") " + each.getTestHeader());
/* 74 */     getWriter().print(each.getTrace());
/*    */   }
/*    */   
/*    */   protected void printFooter(Result result) {
/* 78 */     if (result.wasSuccessful()) {
/* 79 */       getWriter().println();
/* 80 */       getWriter().print("OK");
/* 81 */       getWriter().println(" (" + result.getRunCount() + " test" + ((result.getRunCount() == 1) ? "" : "s") + ")");
/*    */     } else {
/*    */       
/* 84 */       getWriter().println();
/* 85 */       getWriter().println("FAILURES!!!");
/* 86 */       getWriter().println("Tests run: " + result.getRunCount() + ",  Failures: " + result.getFailureCount());
/*    */     } 
/* 88 */     getWriter().println();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String elapsedTimeAsString(long runTime) {
/* 96 */     return NumberFormat.getInstance().format(runTime / 1000.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\TextListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */