/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class DamageBlockEvent
/*    */   extends Event
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   private EnumFacing enumFacing;
/*    */   
/*    */   public DamageBlockEvent(BlockPos blockPos, EnumFacing enumFacing) {
/* 16 */     this.blockPos = blockPos;
/* 17 */     this.enumFacing = enumFacing;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 21 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public void setBlockPos(BlockPos blockPos) {
/* 25 */     this.blockPos = blockPos;
/*    */   }
/*    */   
/*    */   public EnumFacing getEnumFacing() {
/* 29 */     return this.enumFacing;
/*    */   }
/*    */   
/*    */   public void setEnumFacing(EnumFacing enumFacing) {
/* 33 */     this.enumFacing = enumFacing;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\DamageBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */