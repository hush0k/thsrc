/*    */ package org.junit.internal.requests;
/*    */ 
/*    */ import org.junit.internal.runners.ErrorReportingRunner;
/*    */ import org.junit.runner.Request;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runner.manipulation.Filter;
/*    */ import org.junit.runner.manipulation.NoTestsRemainException;
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
/*    */ public final class FilterRequest
/*    */   extends Request
/*    */ {
/*    */   private final Request fRequest;
/*    */   private final Filter fFilter;
/*    */   
/*    */   public FilterRequest(Request classRequest, Filter filter) {
/* 26 */     this.fRequest = classRequest;
/* 27 */     this.fFilter = filter;
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner getRunner() {
/*    */     try {
/* 33 */       Runner runner = this.fRequest.getRunner();
/* 34 */       this.fFilter.apply(runner);
/* 35 */       return runner;
/* 36 */     } catch (NoTestsRemainException e) {
/* 37 */       return (Runner)new ErrorReportingRunner(Filter.class, new Exception(String.format("No tests found matching %s from %s", new Object[] { this.fFilter.describe(), this.fRequest.toString() })));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\requests\FilterRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */