/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class KeyboardEvent extends Event {
/*    */   private final boolean eventState;
/*    */   private final char character;
/*    */   private final int key;
/*    */   
/*    */   public KeyboardEvent(boolean eventState, int key, char character) {
/* 13 */     this.eventState = eventState;
/* 14 */     this.key = key;
/* 15 */     this.character = character;
/*    */   }
/*    */   
/*    */   public boolean getEventState() {
/* 19 */     return this.eventState;
/*    */   }
/*    */   
/*    */   public int getKey() {
/* 23 */     return this.key;
/*    */   }
/*    */   
/*    */   public char getCharacter() {
/* 27 */     return this.character;
/*    */   }
/*    */   
/*    */   public static class Post {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\KeyboardEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */