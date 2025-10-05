/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class ChorusEvent
/*    */   extends Event {
/*    */   private final double chorusX;
/*    */   private final double chorusY;
/*    */   private final double chorusZ;
/*    */   
/*    */   public ChorusEvent(double x, double y, double z) {
/* 12 */     this.chorusX = x;
/* 13 */     this.chorusY = y;
/* 14 */     this.chorusZ = z;
/*    */   }
/*    */   
/*    */   public double getChorusX() {
/* 18 */     return this.chorusX;
/*    */   }
/*    */   
/*    */   public double getChorusY() {
/* 22 */     return this.chorusY;
/*    */   }
/*    */   
/*    */   public double getChorusZ() {
/* 26 */     return this.chorusZ;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\ChorusEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */