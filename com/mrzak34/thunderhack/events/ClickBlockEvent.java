/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Cancelable
/*    */ public class ClickBlockEvent extends Event {
/*    */   private final BlockPos pos;
/*    */   private final EnumFacing facing;
/*    */   
/*    */   public ClickBlockEvent(BlockPos pos, EnumFacing facing) {
/* 16 */     this.pos = pos;
/* 17 */     this.facing = facing;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 21 */     return this.pos;
/*    */   }
/*    */   
/*    */   public EnumFacing getFacing() {
/* 25 */     return this.facing;
/*    */   }
/*    */   
/*    */   public static class Right extends ClickBlockEvent {
/*    */     private final Vec3d vec;
/*    */     private final EnumHand hand;
/*    */     
/*    */     public Right(BlockPos pos, EnumFacing facing, Vec3d vec, EnumHand hand) {
/* 33 */       super(pos, facing);
/* 34 */       this.vec = vec;
/* 35 */       this.hand = hand;
/*    */     }
/*    */     
/*    */     public EnumHand getHand() {
/* 39 */       return this.hand;
/*    */     }
/*    */     
/*    */     public Vec3d getVec() {
/* 43 */       return this.vec;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\ClickBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */