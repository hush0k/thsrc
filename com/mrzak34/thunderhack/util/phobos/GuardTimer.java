/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ 
/*    */ public class GuardTimer implements DiscreteTimer {
/*  6 */   private final Timer guard = new Timer();
/*    */   private final long interval;
/*    */   private final long guardDelay;
/*    */   private long delay;
/*    */   private long time;
/*    */   
/*    */   public GuardTimer() {
/* 13 */     this(1000L);
/*    */   }
/*    */   
/*    */   public GuardTimer(long guardDelay) {
/* 17 */     this(guardDelay, 10L);
/*    */   }
/*    */   
/*    */   public GuardTimer(long guardDelay, long interval) {
/* 21 */     this.guardDelay = guardDelay;
/* 22 */     this.interval = interval;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getTime() {
/* 27 */     return System.currentTimeMillis() - this.time;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTime(long time) {
/* 32 */     this.time = time;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean passed(long ms) {
/* 37 */     return (ms == 0L || ms < this.interval || System.currentTimeMillis() - this.time >= ms);
/*    */   }
/*    */ 
/*    */   
/*    */   public DiscreteTimer reset(long ms) {
/* 42 */     if (ms <= this.interval || this.delay != ms || this.guard.passedMs(this.guardDelay)) {
/* 43 */       this.delay = ms;
/* 44 */       reset();
/*    */     } else {
/* 46 */       this.time = ms + this.time;
/*    */     } 
/*    */     
/* 49 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 54 */     this.time = System.currentTimeMillis();
/* 55 */     this.guard.reset();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\GuardTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */