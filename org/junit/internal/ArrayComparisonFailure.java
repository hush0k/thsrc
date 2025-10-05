/*    */ package org.junit.internal;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayComparisonFailure
/*    */   extends AssertionError
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private List<Integer> fIndices = new ArrayList<Integer>();
/*    */ 
/*    */ 
/*    */   
/*    */   private final String fMessage;
/*    */ 
/*    */   
/*    */   private final AssertionError fCause;
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayComparisonFailure(String message, AssertionError cause, int index) {
/* 28 */     this.fMessage = message;
/* 29 */     this.fCause = cause;
/* 30 */     addDimension(index);
/*    */   }
/*    */   
/*    */   public void addDimension(int index) {
/* 34 */     this.fIndices.add(0, Integer.valueOf(index));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 39 */     StringBuilder builder = new StringBuilder();
/* 40 */     if (this.fMessage != null)
/* 41 */       builder.append(this.fMessage); 
/* 42 */     builder.append("arrays first differed at element ");
/* 43 */     for (Iterator<Integer> i$ = this.fIndices.iterator(); i$.hasNext(); ) { int each = ((Integer)i$.next()).intValue();
/* 44 */       builder.append("[");
/* 45 */       builder.append(each);
/* 46 */       builder.append("]"); }
/*    */     
/* 48 */     builder.append("; ");
/* 49 */     builder.append(this.fCause.getMessage());
/* 50 */     return builder.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return getMessage();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\internal\ArrayComparisonFailure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */