/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.combat.Burrow;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RayTraceUtil
/*    */ {
/*    */   public static float[] hitVecToPlaceVec(BlockPos pos, Vec3d hitVec) {
/* 32 */     float x = (float)(hitVec.field_72450_a - pos.func_177958_n());
/* 33 */     float y = (float)(hitVec.field_72448_b - pos.func_177956_o());
/* 34 */     float z = (float)(hitVec.field_72449_c - pos.func_177952_p());
/*    */     
/* 36 */     return new float[] { x, y, z };
/*    */   }
/*    */   
/*    */   public static RayTraceResult getRayTraceResult(float yaw, float pitch) {
/* 40 */     return getRayTraceResult(yaw, pitch, Util.mc.field_71442_b.func_78757_d());
/*    */   }
/*    */ 
/*    */   
/*    */   public static RayTraceResult getRayTraceResult(float yaw, float pitch, float distance) {
/* 45 */     return getRayTraceResult(yaw, pitch, distance, (Entity)Util.mc.field_71439_g);
/*    */   }
/*    */   
/*    */   public static RayTraceResult getRayTraceResult(float yaw, float pitch, float d, Entity from) {
/* 49 */     Vec3d vec3d = Burrow.getEyePos(from);
/* 50 */     Vec3d lookVec = Burrow.getVec3d(yaw, pitch);
/* 51 */     Vec3d rotations = vec3d.func_72441_c(lookVec.field_72450_a * d, lookVec.field_72448_b * d, lookVec.field_72449_c * d);
/* 52 */     return Optional.<RayTraceResult>ofNullable(Util.mc.field_71441_e
/* 53 */         .func_147447_a(vec3d, rotations, false, false, false))
/* 54 */       .orElseGet(() -> new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(0.5D, 1.0D, 0.5D), EnumFacing.UP, BlockPos.field_177992_a));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean canBeSeen(double x, double y, double z, Entity by) {
/* 60 */     return canBeSeen(new Vec3d(x, y, z), by.field_70165_t, by.field_70163_u, by.field_70161_v, by.func_70047_e());
/*    */   }
/*    */   
/*    */   public static boolean canBeSeen(Vec3d toSee, Entity by) {
/* 64 */     return canBeSeen(toSee, by.field_70165_t, by.field_70163_u, by.field_70161_v, by.func_70047_e());
/*    */   }
/*    */   
/*    */   public static boolean canBeSeen(Vec3d toSee, double x, double y, double z, float eyeHeight) {
/* 68 */     Vec3d start = new Vec3d(x, y + eyeHeight, z);
/* 69 */     return (Util.mc.field_71441_e.func_147447_a(start, toSee, false, true, false) == null);
/*    */   }
/*    */   
/*    */   public static boolean canBeSeen(Entity toSee, EntityLivingBase by) {
/* 73 */     return by.func_70685_l(toSee);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean raytracePlaceCheck(Entity entity, BlockPos pos) {
/* 78 */     return (getFacing(entity, pos, false) != null);
/*    */   }
/*    */   
/*    */   public static EnumFacing getFacing(Entity entity, BlockPos pos, boolean verticals) {
/* 82 */     for (EnumFacing facing : EnumFacing.values()) {
/* 83 */       RayTraceResult result = Util.mc.field_71441_e.func_147447_a(Burrow.getEyePos(entity), new Vec3d(pos.func_177958_n() + 0.5D + facing.func_176730_m().func_177958_n() * 1.0D / 2.0D, pos.func_177956_o() + 0.5D + facing.func_176730_m().func_177956_o() * 1.0D / 2.0D, pos.func_177952_p() + 0.5D + facing.func_176730_m().func_177952_p() * 1.0D / 2.0D), false, true, false);
/* 84 */       if (result != null && result.field_72313_a == RayTraceResult.Type.BLOCK && result.func_178782_a().equals(pos)) {
/* 85 */         return facing;
/*    */       }
/*    */     } 
/*    */     
/* 89 */     if (verticals) {
/* 90 */       if (pos.func_177956_o() > Util.mc.field_71439_g.field_70163_u + Util.mc.field_71439_g.func_70047_e()) {
/* 91 */         return EnumFacing.DOWN;
/*    */       }
/*    */       
/* 94 */       return EnumFacing.UP;
/*    */     } 
/*    */     
/* 97 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\RayTraceUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */