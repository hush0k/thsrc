/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class MatrixMove
/*    */   extends Event {
/*    */   private final boolean toGround;
/*    */   private final AxisAlignedBB aabbFrom;
/*    */   private final double fromX;
/*    */   private final double fromY;
/*    */   private final double fromZ;
/*    */   private double motionX;
/*    */   private double motionY;
/*    */   private double motionZ;
/*    */   
/*    */   public MatrixMove(double fromX, double fromY, double fromZ, double motionX, double motionY, double motionZ, boolean toGround, AxisAlignedBB aabbFrom) {
/* 20 */     this.fromX = fromX;
/* 21 */     this.fromY = fromY;
/* 22 */     this.fromZ = fromZ;
/* 23 */     this.motionX = motionX;
/* 24 */     this.motionY = motionY;
/* 25 */     this.motionZ = motionZ;
/* 26 */     this.toGround = toGround;
/* 27 */     this.aabbFrom = aabbFrom;
/*    */   }
/*    */   
/*    */   public double getFromX() {
/* 31 */     return this.fromX;
/*    */   }
/*    */   
/*    */   public double getFromZ() {
/* 35 */     return this.fromZ;
/*    */   }
/*    */   
/*    */   public double getMotionX() {
/* 39 */     return this.motionX;
/*    */   }
/*    */   
/*    */   public void setMotionX(double motionX) {
/* 43 */     this.motionX = motionX;
/*    */   }
/*    */   
/*    */   public double getMotionY() {
/* 47 */     return this.motionY;
/*    */   }
/*    */   
/*    */   public void setMotionY(double motionY) {
/* 51 */     this.motionY = motionY;
/*    */   }
/*    */   
/*    */   public double getMotionZ() {
/* 55 */     return this.motionZ;
/*    */   }
/*    */   
/*    */   public void setMotionZ(double motionZ) {
/* 59 */     this.motionZ = motionZ;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getAABBFrom() {
/* 63 */     return this.aabbFrom;
/*    */   }
/*    */   
/*    */   public boolean toGround() {
/* 67 */     return this.toGround;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\MatrixMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */