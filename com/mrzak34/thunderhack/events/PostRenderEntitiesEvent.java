/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class PostRenderEntitiesEvent extends Event {
/*    */   private final float partialTicks;
/*    */   private final int pass;
/*    */   
/*    */   public PostRenderEntitiesEvent(float partialTicks, int pass) {
/* 10 */     this.partialTicks = partialTicks;
/* 11 */     this.pass = pass;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 15 */     return this.partialTicks;
/*    */   }
/*    */   
/*    */   public int getPass() {
/* 19 */     return this.pass;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\PostRenderEntitiesEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */