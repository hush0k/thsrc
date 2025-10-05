/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class BasePath
/*    */   implements Pathable {
/* 10 */   private final List<BlockingEntity> blocking = new ArrayList<>();
/*    */   
/*    */   private final int maxLength;
/*    */   private final BlockPos pos;
/*    */   private final Entity from;
/*    */   private boolean valid;
/*    */   private Ray[] path;
/*    */   
/*    */   public BasePath(Entity from, BlockPos pos, int maxLength) {
/* 19 */     this.from = from;
/* 20 */     this.pos = pos;
/* 21 */     this.maxLength = maxLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPos() {
/* 26 */     return this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getFrom() {
/* 31 */     return this.from;
/*    */   }
/*    */ 
/*    */   
/*    */   public Ray[] getPath() {
/* 36 */     return this.path;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPath(Ray... path) {
/* 41 */     this.path = path;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxLength() {
/* 46 */     return this.maxLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValid() {
/* 51 */     return this.valid;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValid(boolean valid) {
/* 56 */     this.valid = valid;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BlockingEntity> getBlockingEntities() {
/* 61 */     return this.blocking;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\BasePath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */