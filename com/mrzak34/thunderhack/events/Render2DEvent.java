/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class Render2DEvent
/*    */   extends Event {
/*    */   public float partialTicks;
/*    */   public ScaledResolution scaledResolution;
/*    */   
/*    */   public Render2DEvent(float partialTicks, ScaledResolution scaledResolution) {
/* 12 */     this.partialTicks = partialTicks;
/* 13 */     this.scaledResolution = scaledResolution;
/*    */   }
/*    */   
/*    */   public void setScaledResolution(ScaledResolution scaledResolution) {
/* 17 */     this.scaledResolution = scaledResolution;
/*    */   }
/*    */   
/*    */   public double getScreenWidth() {
/* 21 */     return this.scaledResolution.func_78327_c();
/*    */   }
/*    */   
/*    */   public double getScreenHeight() {
/* 25 */     return this.scaledResolution.func_78324_d();
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 29 */     return this.partialTicks;
/*    */   }
/*    */   
/*    */   public void setPartialTicks(float partialTicks) {
/* 33 */     this.partialTicks = partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\Render2DEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */