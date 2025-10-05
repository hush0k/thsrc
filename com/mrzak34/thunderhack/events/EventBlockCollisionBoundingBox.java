/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class EventBlockCollisionBoundingBox
/*    */   extends Event {
/*    */   private final BlockPos _pos;
/*    */   private AxisAlignedBB _boundingBox;
/*    */   
/*    */   public EventBlockCollisionBoundingBox(BlockPos pos) {
/* 15 */     this._pos = pos;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 19 */     return this._pos;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getBoundingBox() {
/* 23 */     return this._boundingBox;
/*    */   }
/*    */   
/*    */   public void setBoundingBox(AxisAlignedBB boundingBox) {
/* 27 */     this._boundingBox = boundingBox;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventBlockCollisionBoundingBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */