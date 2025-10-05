/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.concurrent.Future;
/*    */ 
/*    */ 
/*    */ public class ThreadManager
/*    */   implements GlobalExecutor
/*    */ {
/*    */   public Future<?> submit(SafeRunnable runnable) {
/* 10 */     return submitRunnable(runnable);
/*    */   }
/*    */   
/*    */   public Future<?> submitRunnable(Runnable runnable) {
/* 14 */     return EXECUTOR.submit(runnable);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void shutDown() {
/* 21 */     EXECUTOR.shutdown();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ThreadManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */