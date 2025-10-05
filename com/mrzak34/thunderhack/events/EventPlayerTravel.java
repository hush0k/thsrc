/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class EventPlayerTravel extends Event {
/*    */   public float Strafe;
/*    */   
/*    */   public float getStrafe() {
/* 11 */     return this.Strafe;
/*    */   }
/*    */   public float Vertical; public float Forward;
/*    */   public float getVertical() {
/* 15 */     return this.Vertical;
/*    */   }
/*    */   
/*    */   public float getForward() {
/* 19 */     return this.Forward;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventPlayerTravel(float p_Strafe, float p_Vertical, float p_Forward) {
/* 26 */     this.Strafe = p_Strafe;
/* 27 */     this.Vertical = p_Vertical;
/* 28 */     this.Forward = p_Forward;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventPlayerTravel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */