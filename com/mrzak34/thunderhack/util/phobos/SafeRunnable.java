/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface SafeRunnable
/*    */   extends Runnable {
/*    */   void runSafely() throws Throwable;
/*    */   
/*    */   default void run() {
/*    */     try {
/* 10 */       runSafely();
/* 11 */     } catch (Throwable t) {
/* 12 */       handle(t);
/*    */     } 
/*    */   }
/*    */   
/*    */   default void handle(Throwable t) {
/* 17 */     t.printStackTrace();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\SafeRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */