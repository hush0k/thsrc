/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ public abstract class AbstractSphere
/*    */   extends AbstractGeoCache {
/*    */   private final double r;
/*    */   
/*    */   public AbstractSphere(int expectedSize, int indicesSize, double radius) {
/* 12 */     super(expectedSize, indicesSize);
/* 13 */     this.r = radius;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract Collection<BlockPos> sorter(BlockPos paramBlockPos);
/*    */   
/*    */   protected void fill(Vec3i[] cache, int[] indices) {
/* 20 */     BlockPos pos = BlockPos.field_177992_a;
/* 21 */     Collection<BlockPos> positions = sorter(pos);
/* 22 */     double rSquare = this.r * this.r;
/* 23 */     for (int x = pos.func_177958_n() - (int)this.r; x <= pos.func_177958_n() + this.r; x++) {
/* 24 */       for (int z = pos.func_177952_p() - (int)this.r; z <= pos.func_177952_p() + this.r; z++) {
/* 25 */         for (int y = pos.func_177956_o() - (int)this.r; y < pos.func_177956_o() + this.r; y++) {
/*    */ 
/*    */           
/* 28 */           double dist = ((pos.func_177958_n() - x) * (pos.func_177958_n() - x) + (pos.func_177952_p() - z) * (pos.func_177952_p() - z) + (pos.func_177956_o() - y) * (pos.func_177956_o() - y));
/*    */           
/* 30 */           if (dist < rSquare) {
/* 31 */             positions.add(new BlockPos(x, y, z));
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     if (positions.size() != cache.length) {
/* 38 */       throw new IllegalStateException("Unexpected Size for Sphere: " + positions
/* 39 */           .size() + ", expected " + cache.length + "!");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     int i = 0;
/* 46 */     int currentDistance = 0;
/* 47 */     for (BlockPos off : positions) {
/* 48 */       if (Math.sqrt(pos.func_177951_i((Vec3i)off)) > currentDistance) {
/* 49 */         indices[currentDistance++] = i;
/*    */       }
/*    */       
/* 52 */       cache[i++] = (Vec3i)off;
/*    */     } 
/*    */     
/* 55 */     if (currentDistance != indices.length - 1) {
/* 56 */       throw new IllegalStateException("Sphere Indices not initialized!");
/*    */     }
/*    */     
/* 59 */     indices[indices.length - 1] = cache.length;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\AbstractSphere.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */