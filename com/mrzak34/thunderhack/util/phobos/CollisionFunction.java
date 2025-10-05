/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockProperties;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface CollisionFunction
/*    */ {
/* 19 */   public static final CollisionFunction DEFAULT = IBlockProperties::func_185910_a;
/*    */   
/*    */   RayTraceResult collisionRayTrace(IBlockState paramIBlockState, World paramWorld, BlockPos paramBlockPos, Vec3d paramVec3d1, Vec3d paramVec3d2);
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CollisionFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */