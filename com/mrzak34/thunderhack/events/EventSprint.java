/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EventSprint extends Event {
/*    */   private boolean sprintState;
/*    */   
/*    */   public EventSprint(boolean sprintState) {
/*  9 */     this.sprintState = sprintState;
/*    */   }
/*    */   
/*    */   public boolean getSprintState() {
/* 13 */     return this.sprintState;
/*    */   }
/*    */   
/*    */   public void setSprintState(boolean sprintState) {
/* 17 */     this.sprintState = sprintState;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventSprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */