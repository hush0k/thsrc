/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ 
/*    */ public abstract class Finishable implements Runnable {
/*    */   private final AtomicBoolean finished;
/*    */   
/*    */   public Finishable() {
/*  9 */     this(new AtomicBoolean());
/*    */   }
/*    */   
/*    */   public Finishable(AtomicBoolean finished) {
/* 13 */     this.finished = finished;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 19 */       execute();
/*    */     } finally {
/* 21 */       setFinished(true);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void execute();
/*    */   
/*    */   public boolean isFinished() {
/* 28 */     return this.finished.get();
/*    */   }
/*    */   
/*    */   public void setFinished(boolean finished) {
/* 32 */     this.finished.set(finished);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Finishable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */