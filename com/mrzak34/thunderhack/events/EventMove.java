/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class EventMove
/*    */   extends Event {
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   private MoverType move_type;
/*    */   
/*    */   public EventMove(MoverType type, double x, double y, double z) {
/* 16 */     this.move_type = type;
/* 17 */     this.x = x;
/* 18 */     this.y = y;
/* 19 */     this.z = z;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 23 */     this.y = y;
/*    */   }
/*    */   
/*    */   public void setZ(double z) {
/* 27 */     this.z = z;
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 31 */     this.x = x;
/*    */   }
/*    */   
/*    */   public MoverType get_move_type() {
/* 35 */     return this.move_type;
/*    */   }
/*    */   
/*    */   public void set_move_type(MoverType type) {
/* 39 */     this.move_type = type;
/*    */   }
/*    */   
/*    */   public double get_x() {
/* 43 */     return this.x;
/*    */   }
/*    */   
/*    */   public void set_x(double x) {
/* 47 */     this.x = x;
/*    */   }
/*    */   
/*    */   public double get_y() {
/* 51 */     return this.y;
/*    */   }
/*    */   
/*    */   public void set_y(double y) {
/* 55 */     this.y = y;
/*    */   }
/*    */   
/*    */   public double get_z() {
/* 59 */     return this.z;
/*    */   }
/*    */   
/*    */   public void set_z(double z) {
/* 63 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */