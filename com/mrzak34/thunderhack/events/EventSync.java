/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class EventSync
/*    */   extends Event {
/*    */   float yaw;
/*    */   float pitch;
/*    */   boolean onGround;
/*    */   
/*    */   public EventSync(float yaw, float pitch, boolean onGround) {
/* 14 */     this.yaw = yaw;
/* 15 */     this.pitch = pitch;
/* 16 */     this.onGround = onGround;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 20 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 24 */     return this.pitch;
/*    */   }
/*    */   public boolean isOnGround() {
/* 27 */     return this.onGround;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */