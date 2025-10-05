/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class EventMoveDirection
/*    */   extends Event {
/*    */   private boolean post;
/*    */   
/*    */   public EventMoveDirection(boolean post) {
/* 12 */     this.post = post;
/*    */   }
/*    */   
/*    */   public boolean isPost() {
/* 16 */     return this.post;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventMoveDirection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */