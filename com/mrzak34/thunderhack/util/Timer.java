/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.phobos.Passable;
/*    */ 
/*    */ public class Timer
/*    */   implements Passable {
/*  7 */   private long time = -1L;
/*    */   
/*    */   public boolean passedS(double s) {
/* 10 */     return (getMs(System.nanoTime() - this.time) >= (long)(s * 1000.0D));
/*    */   }
/*    */   
/*    */   public boolean passedM(double m) {
/* 14 */     return (getMs(System.nanoTime() - this.time) >= (long)(m * 1000.0D * 60.0D));
/*    */   }
/*    */   
/*    */   public boolean passedDms(double dms) {
/* 18 */     return (getMs(System.nanoTime() - this.time) >= (long)(dms * 10.0D));
/*    */   }
/*    */   
/*    */   public boolean passedDs(double ds) {
/* 22 */     return (getMs(System.nanoTime() - this.time) >= (long)(ds * 100.0D));
/*    */   }
/*    */   
/*    */   public boolean passedMs(long ms) {
/* 26 */     return (getMs(System.nanoTime() - this.time) >= ms);
/*    */   }
/*    */   
/*    */   public boolean passedNS(long ns) {
/* 30 */     return (System.nanoTime() - this.time >= ns);
/*    */   }
/*    */   
/*    */   public void setMs(long ms) {
/* 34 */     this.time = System.nanoTime() - ms * 1000000L;
/*    */   }
/*    */   
/*    */   public long getPassedTimeMs() {
/* 38 */     return getMs(System.nanoTime() - this.time);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 42 */     this.time = System.nanoTime();
/*    */   }
/*    */   
/*    */   public long getMs(long time) {
/* 46 */     return time / 1000000L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean passed(long delay, boolean reset) {
/* 56 */     if (reset) reset(); 
/* 57 */     return (System.currentTimeMillis() - this.time >= delay);
/*    */   }
/*    */   
/*    */   public long getTimeMs() {
/* 61 */     return getMs(System.nanoTime() - this.time);
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 65 */     return System.nanoTime() - this.time;
/*    */   }
/*    */   
/*    */   public void adjust(int by) {
/* 69 */     this.time += by;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean passed(long delay) {
/* 74 */     return (getMs(System.nanoTime() - this.time) >= delay);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */