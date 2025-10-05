/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.Deque;
/*    */ 
/*    */ public abstract class PositionHistoryChecker
/*    */ {
/*    */   protected boolean checkOldLook = true;
/*  8 */   protected int ticksToCheck = 10;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract boolean check(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, int paramInt3);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkFlyingQueue(double x, double y, double z, float oldYaw, float oldPitch, int blockX, int blockY, int blockZ, PositionHistoryHelper history) {
/* 19 */     if (this.checkOldLook && 
/* 20 */       check(x, y, z, oldYaw, oldPitch, blockX, blockY, blockZ)) {
/* 21 */       return true;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 27 */     Deque<PositionHistoryHelper.RotationHistory> queue = history.getPackets();
/* 28 */     if (queue.size() == 0) {
/* 29 */       return false;
/*    */     }
/*    */     
/* 32 */     int checked = 0;
/* 33 */     for (PositionHistoryHelper.RotationHistory data : queue) {
/* 34 */       if (data == null) {
/*    */         continue;
/*    */       }
/*    */       
/* 38 */       if (++checked > 10) {
/*    */         break;
/*    */       }
/*    */       
/* 42 */       if (!data.hasLook) {
/*    */         continue;
/*    */       }
/*    */       
/* 46 */       float yaw = data.yaw;
/* 47 */       float pitch = data.pitch;
/* 48 */       if (yaw == oldYaw && pitch == oldPitch) {
/*    */         continue;
/*    */       }
/*    */       
/* 52 */       if (check(x, y, z, yaw, pitch, blockX, blockY, blockZ)) {
/* 53 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\PositionHistoryChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */