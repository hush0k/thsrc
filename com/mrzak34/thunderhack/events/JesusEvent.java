/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class JesusEvent extends Event {
/*    */   private BlockPos pos;
/*    */   private AxisAlignedBB boundingBox;
/*    */   
/*    */   public JesusEvent(BlockPos pos) {
/* 14 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 18 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void setPos(BlockPos pos) {
/* 22 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getBoundingBox() {
/* 26 */     return this.boundingBox;
/*    */   }
/*    */   
/*    */   public void setBoundingBox(AxisAlignedBB boundingBox) {
/* 30 */     this.boundingBox = boundingBox;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\JesusEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */