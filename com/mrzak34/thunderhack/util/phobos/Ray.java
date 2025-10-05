/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Ray
/*    */ {
/*    */   private final RayTraceResult result;
/*    */   private final EnumFacing facing;
/*    */   private final BlockPos pos;
/*    */   private final Vec3d vector;
/*    */   private float[] rotations;
/*    */   private boolean legit;
/*    */   
/*    */   public Ray(RayTraceResult result, float[] rotations, BlockPos pos, EnumFacing facing, Vec3d vector) {
/* 28 */     this.result = result;
/* 29 */     this.rotations = rotations;
/* 30 */     this.pos = pos;
/* 31 */     this.facing = facing;
/* 32 */     this.vector = vector;
/*    */   }
/*    */   
/*    */   public RayTraceResult getResult() {
/* 36 */     return this.result;
/*    */   }
/*    */   
/*    */   public void updateRotations(Entity entity) {
/* 40 */     if (this.vector != null)
/*    */     {
/* 42 */       this.rotations = RayTraceFactory.rots(entity, this.vector);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] getRotations() {
/* 48 */     return this.rotations;
/*    */   }
/*    */   
/*    */   public EnumFacing getFacing() {
/* 52 */     return this.facing;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 56 */     return this.pos;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLegit() {
/* 63 */     return this.legit;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Ray setLegit(boolean legit) {
/* 73 */     this.legit = legit;
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   public Vec3d getVector() {
/* 78 */     return this.vector;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Ray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */