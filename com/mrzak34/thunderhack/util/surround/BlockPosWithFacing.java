/*    */ package com.mrzak34.thunderhack.util.surround;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class BlockPosWithFacing
/*    */ {
/*    */   private final BlockPos bp;
/*    */   private final EnumFacing facing;
/*    */   
/*    */   public BlockPosWithFacing(BlockPos blockPos, EnumFacing enumFacing) {
/* 12 */     this.bp = blockPos;
/* 13 */     this.facing = enumFacing;
/*    */   }
/*    */   
/*    */   public BlockPos getPosition() {
/* 17 */     return this.bp;
/*    */   }
/*    */   
/*    */   public EnumFacing getFacing() {
/* 21 */     return this.facing;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\surround\BlockPosWithFacing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */