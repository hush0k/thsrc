/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ public class TimeStamp {
/*    */   private final long timeStamp;
/*    */   private boolean valid = true;
/*    */   
/*    */   public TimeStamp() {
/*  8 */     this.timeStamp = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public long getTimeStamp() {
/* 12 */     return this.timeStamp;
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 16 */     return this.valid;
/*    */   }
/*    */   
/*    */   public void setValid(boolean valid) {
/* 20 */     this.valid = valid;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\TimeStamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */