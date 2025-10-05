/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class BlockingEntity {
/*    */   private final Entity entity;
/*    */   private final BlockPos pos;
/*    */   
/*    */   public BlockingEntity(Entity entity, BlockPos pos) {
/* 11 */     this.entity = entity;
/* 12 */     this.pos = pos;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 16 */     return this.entity;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockedPos() {
/* 20 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\BlockingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */