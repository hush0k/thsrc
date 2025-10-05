/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ public abstract class AbstractGeoCache implements GeoCache {
/*    */   private final Vec3i[] cache;
/*    */   private final int[] indices;
/*    */   
/*    */   public AbstractGeoCache(int expectedSize, int indicesSize) {
/* 11 */     this.cache = new Vec3i[expectedSize];
/* 12 */     this.indices = new int[indicesSize];
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void fill(Vec3i[] paramArrayOfVec3i, int[] paramArrayOfint);
/*    */   
/*    */   public void cache() {
/* 19 */     Vec3i dummy = new Vec3i(2147483647, 0, 0);
/* 20 */     this.cache[this.cache.length - 1] = dummy;
/* 21 */     fill(this.cache, this.indices);
/* 22 */     if (this.cache[this.cache.length - 1] == dummy) {
/* 23 */       throw new IllegalStateException("Cache was not filled!");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRadius(double radius) {
/* 29 */     return this.indices[MathUtil.clamp((int)Math.ceil(radius), 0, this.indices.length)];
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3i get(int index) {
/* 34 */     return this.cache[index];
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3i[] array() {
/* 39 */     return this.cache;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\AbstractGeoCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */