/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class StepEvent
/*    */   extends Event
/*    */ {
/*    */   private final AxisAlignedBB axisAlignedBB;
/*    */   private float height;
/*    */   
/*    */   public StepEvent(AxisAlignedBB axisAlignedBB, float height) {
/* 22 */     this.axisAlignedBB = axisAlignedBB;
/* 23 */     this.height = height;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getAxisAlignedBB() {
/* 27 */     return this.axisAlignedBB;
/*    */   }
/*    */   
/*    */   public float getHeight() {
/* 31 */     return this.height;
/*    */   }
/*    */   
/*    */   public void setHeight(float in) {
/* 35 */     this.height = in;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\StepEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */