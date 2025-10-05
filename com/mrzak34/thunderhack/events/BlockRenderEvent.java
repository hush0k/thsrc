/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class BlockRenderEvent extends Event {
/*    */   private final Block block;
/*    */   private final BlockPos pos;
/*    */   
/*    */   public BlockRenderEvent(Block block, BlockPos pos) {
/* 14 */     this.block = block;
/* 15 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 19 */     return this.block;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 23 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\BlockRenderEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */