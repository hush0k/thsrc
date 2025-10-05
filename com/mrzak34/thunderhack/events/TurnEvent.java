/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class TurnEvent extends Event {
/*    */   private final float yaw;
/*    */   private final float pitch;
/*    */   
/*    */   public TurnEvent(float yaw, float pitch) {
/* 12 */     this.yaw = yaw;
/* 13 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 17 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 21 */     return this.pitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\TurnEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */