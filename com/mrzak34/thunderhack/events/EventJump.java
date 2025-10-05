/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class EventJump extends Event {
/*    */   private float yaw;
/*    */   
/*    */   public EventJump(float yaw) {
/* 11 */     this.yaw = yaw;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 15 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public void setYaw(float yaw) {
/* 19 */     this.yaw = yaw;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */