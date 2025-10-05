/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class DestroyBlockEvent
/*    */   extends Event {
/*    */   private BlockPos blockPos;
/*    */   
/*    */   public DestroyBlockEvent(BlockPos blockPos) {
/* 11 */     this.blockPos = blockPos;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 15 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public void setBlockPos(BlockPos blockPos) {
/* 19 */     this.blockPos = blockPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\DestroyBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */