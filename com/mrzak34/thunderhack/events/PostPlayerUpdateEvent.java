/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class PostPlayerUpdateEvent extends Event {
/*    */   private int iterations;
/*    */   
/*    */   public int getIterations() {
/* 11 */     return this.iterations;
/*    */   }
/*    */   
/*    */   public void setIterations(int in) {
/* 15 */     this.iterations = in;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\PostPlayerUpdateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */