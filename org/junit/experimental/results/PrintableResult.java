/*    */ package org.junit.experimental.results;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ import org.junit.internal.TextListener;
/*    */ import org.junit.runner.JUnitCore;
/*    */ import org.junit.runner.Request;
/*    */ import org.junit.runner.Result;
/*    */ import org.junit.runner.notification.Failure;
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
/*    */ public class PrintableResult
/*    */ {
/*    */   private Result result;
/*    */   
/*    */   public static PrintableResult testResult(Class<?> type) {
/* 27 */     return testResult(Request.aClass(type));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PrintableResult testResult(Request request) {
/* 34 */     return new PrintableResult((new JUnitCore()).run(request));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrintableResult(List<Failure> failures) {
/* 43 */     this((new FailureList(failures)).result());
/*    */   }
/*    */   
/*    */   private PrintableResult(Result result) {
/* 47 */     this.result = result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 53 */     (new TextListener(new PrintStream(stream))).testRunFinished(this.result);
/* 54 */     return stream.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int failureCount() {
/* 61 */     return this.result.getFailures().size();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\experimental\results\PrintableResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */