/*    */ package org.junit.rules;
/*    */ 
/*    */ import org.junit.runner.Description;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TestName
/*    */   extends TestWatcher
/*    */ {
/*    */   private String fName;
/*    */   
/*    */   protected void starting(Description d) {
/* 30 */     this.fName = d.getMethodName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMethodName() {
/* 37 */     return this.fName;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\rules\TestName.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */