/*    */ package org.junit.runner;
/*    */ 
/*    */ import org.junit.runner.notification.RunNotifier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Runner
/*    */   implements Describable
/*    */ {
/*    */   public abstract Description getDescription();
/*    */   
/*    */   public abstract void run(RunNotifier paramRunNotifier);
/*    */   
/*    */   public int testCount() {
/* 38 */     return getDescription().testCount();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\org\junit\runner\Runner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */