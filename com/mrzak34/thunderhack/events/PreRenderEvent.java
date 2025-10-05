/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class PreRenderEvent
/*    */   extends Event
/*    */ {
/*    */   private final float partialTicks;
/*    */   
/*    */   public PreRenderEvent(float partialTicks) {
/* 11 */     this.partialTicks = partialTicks;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 15 */     return this.partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\PreRenderEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */