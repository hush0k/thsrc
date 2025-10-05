/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class MouseEvent extends Event {
/*    */   private final int button;
/*    */   private final boolean state;
/*    */   
/*    */   public MouseEvent(int button, boolean state) {
/* 12 */     this.button = button;
/* 13 */     this.state = state;
/*    */   }
/*    */   
/*    */   public boolean getState() {
/* 17 */     return this.state;
/*    */   }
/*    */   
/*    */   public int getButton() {
/* 21 */     return this.button;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\MouseEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */