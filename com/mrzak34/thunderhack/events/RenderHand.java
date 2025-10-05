/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class RenderHand extends Event {
/*    */   private final float ticks;
/*    */   
/*    */   public RenderHand(float ticks) {
/*  9 */     this.ticks = ticks;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 13 */     return this.ticks;
/*    */   }
/*    */   
/*    */   public static class PostOutline
/*    */     extends RenderHand {
/*    */     public PostOutline(float ticks) {
/* 19 */       super(ticks);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class PreOutline extends RenderHand {
/*    */     public PreOutline(float ticks) {
/* 25 */       super(ticks);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class PostFill extends RenderHand {
/*    */     public PostFill(float ticks) {
/* 31 */       super(ticks);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class PreFill extends RenderHand {
/*    */     public PreFill(float ticks) {
/* 37 */       super(ticks);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class PostBoth extends RenderHand {
/*    */     public PostBoth(float ticks) {
/* 43 */       super(ticks);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class PreBoth extends RenderHand {
/*    */     public PreBoth(float ticks) {
/* 49 */       super(ticks);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\RenderHand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */