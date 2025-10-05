/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class Render3DEvent extends Event {
/*    */   private final float partialTicks;
/*    */   
/*    */   public Render3DEvent(float partialTicks) {
/*  9 */     this.partialTicks = partialTicks;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 13 */     return this.partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\Render3DEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */