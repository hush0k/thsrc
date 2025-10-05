/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class KeyEvent
/*    */   extends Event {
/*    */   private final int key;
/*    */   
/*    */   public KeyEvent(int key) {
/* 10 */     this.key = key;
/*    */   }
/*    */   
/*    */   public int getKey() {
/* 14 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\KeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */