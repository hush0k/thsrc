/*    */ package org.junit.internal.requests;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import org.junit.runner.Description;
/*    */ import org.junit.runner.Request;
/*    */ import org.junit.runner.Runner;
/*    */ import org.junit.runner.manipulation.Sorter;
/*    */ 
/*    */ public class SortingRequest
/*    */   extends Request {
/*    */   private final Request fRequest;
/*    */   private final Comparator<Description> fComparator;
/*    */   
/*    */   public SortingRequest(Request request, Comparator<Description> comparator) {
/* 15 */     this.fRequest = request;
/* 16 */     this.fComparator = comparator;
/*    */   }
/*    */ 
/*    */   
/*    */   public Runner getRunner() {
/* 21 */     Runner runner = this.fRequest.getRunner();
/* 22 */     (new Sorter(this.fComparator)).apply(runner);
/* 23 */     return runner;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\requests\SortingRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */