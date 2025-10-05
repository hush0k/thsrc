/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.function.BiPredicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class RotationUtil {
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing) {
/*  21 */     return getRotations(pos, facing, (Entity)Util.mc.field_71439_g);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing, Entity from) {
/*  25 */     return getRotations(pos, facing, from, (IBlockAccess)Util.mc.field_71441_e, Util.mc.field_71441_e.func_180495_p(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing, Entity from, IBlockAccess world, IBlockState state) {
/*  33 */     AxisAlignedBB bb = state.func_185900_c(world, pos);
/*     */     
/*  35 */     double x = pos.func_177958_n() + (bb.field_72340_a + bb.field_72336_d) / 2.0D;
/*  36 */     double y = pos.func_177956_o() + (bb.field_72338_b + bb.field_72337_e) / 2.0D;
/*  37 */     double z = pos.func_177952_p() + (bb.field_72339_c + bb.field_72334_f) / 2.0D;
/*     */     
/*  39 */     if (facing != null) {
/*  40 */       x += facing.func_176730_m().func_177958_n() * (bb.field_72340_a + bb.field_72336_d) / 2.0D;
/*  41 */       y += facing.func_176730_m().func_177956_o() * (bb.field_72338_b + bb.field_72337_e) / 2.0D;
/*  42 */       z += facing.func_176730_m().func_177952_p() * (bb.field_72339_c + bb.field_72334_f) / 2.0D;
/*     */     } 
/*     */     
/*  45 */     return getRotations(x, y, z, from);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotationsToTopMiddle(BlockPos pos) {
/*  53 */     return getRotations(pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getRotationsToTopMiddleUp(BlockPos pos) {
/*  58 */     return getRotations(pos.func_177958_n() + 0.5D, (pos.func_177956_o() + 1), pos.func_177952_p() + 0.5D);
/*     */   }
/*     */   
/*     */   public static float[] getRotationsMaxYaw(BlockPos pos, float max, float current) {
/*  62 */     return new float[] {
/*     */         
/*  64 */         updateRotation(current, getRotationsToTopMiddle(pos)[0], max), 
/*  65 */         getRotationsToTopMiddle(pos)[1]
/*     */       };
/*     */   }
/*     */   
/*     */   public static float[] getRotations(Entity entity, double height) {
/*  70 */     return getRotations(entity.field_70165_t, entity.field_70163_u + entity
/*  71 */         .func_70047_e() * height, entity.field_70161_v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(Entity entity) {
/*  81 */     return getRotations(entity.field_70165_t, entity.field_70163_u + entity
/*  82 */         .func_70047_e(), entity.field_70161_v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotationsMaxYaw(Entity entity, float max, float current) {
/*  89 */     return new float[] { MathHelper.func_76142_g(
/*  90 */           updateRotation(current, getRotations(entity)[0], max)), 
/*  91 */         getRotations(entity)[1] };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(Vec3d vec3d) {
/* 101 */     return getRotations(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z) {
/* 105 */     return getRotations(x, y, z, (Entity)Util.mc.field_71439_g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z, Entity f) {
/* 120 */     return getRotations(x, y, z, f.field_70165_t, f.field_70163_u, f.field_70161_v, f.func_70047_e());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z, double fromX, double fromY, double fromZ, float fromHeight) {
/* 130 */     double xDiff = x - fromX;
/* 131 */     double yDiff = y - fromY + fromHeight;
/* 132 */     double zDiff = z - fromZ;
/* 133 */     double dist = MathHelper.func_76133_a(xDiff * xDiff + zDiff * zDiff);
/*     */     
/* 135 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/* 136 */     float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/*     */     
/* 138 */     float prevYaw = Thunderhack.rotationManager.getServerYaw();
/* 139 */     float diff = yaw - prevYaw;
/*     */     
/* 141 */     if (diff < -180.0F || diff > 180.0F) {
/* 142 */       float round = Math.round(Math.abs(diff / 360.0F));
/* 143 */       diff = (diff < 0.0F) ? (diff + 360.0F * round) : (diff - 360.0F * round);
/*     */     } 
/*     */     
/* 146 */     return new float[] { prevYaw + diff, pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean inFov(Entity entity) {
/* 154 */     return inFov(entity.field_70165_t, entity.field_70163_u + (entity
/* 155 */         .func_70047_e() / 2.0F), entity.field_70161_v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean inFov(BlockPos pos) {
/* 164 */     return inFov(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean inFov(double x, double y, double z) {
/* 192 */     return (getAngle(x, y, z) < (Util.mc.field_71474_y.field_74334_X / 2.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getAngle(Entity entity, double yOffset) {
/* 204 */     Vec3d vec3d = MathUtil.fromTo(interpolatedEyePos(), entity.field_70165_t, entity.field_70163_u + yOffset, entity.field_70161_v);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     return MathUtil.angle(vec3d, interpolatedEyeVec());
/*     */   }
/*     */   
/*     */   public static Vec3d interpolatedEyeVec() {
/* 213 */     return Util.mc.field_71439_g.func_70676_i(Util.mc.func_184121_ak());
/*     */   }
/*     */   
/*     */   public static Vec3d interpolatedEyePos() {
/* 217 */     return Util.mc.field_71439_g.func_174824_e(Util.mc.func_184121_ak());
/*     */   }
/*     */   
/*     */   public static double getAngle(double x, double y, double z) {
/* 221 */     Vec3d vec3d = MathUtil.fromTo(interpolatedEyePos(), x, y, z);
/*     */ 
/*     */     
/* 224 */     return MathUtil.angle(vec3d, interpolatedEyeVec());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d getVec3d(float yaw, float pitch) {
/* 235 */     float vx = -MathHelper.func_76126_a(MathUtil.rad(yaw)) * MathHelper.func_76134_b(MathUtil.rad(pitch));
/* 236 */     float vz = MathHelper.func_76134_b(MathUtil.rad(yaw)) * MathHelper.func_76134_b(MathUtil.rad(pitch));
/* 237 */     float vy = -MathHelper.func_76126_a(MathUtil.rad(pitch));
/* 238 */     return new Vec3d(vx, vy, vz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isLegit(BlockPos pos) {
/* 243 */     return isLegit(pos, null);
/*     */   }
/*     */   
/*     */   public static boolean isLegit(BlockPos pos, EnumFacing facing) {
/* 247 */     return isLegit(pos, facing, (IBlockAccess)Util.mc.field_71441_e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLegit(BlockPos pos, EnumFacing facing, IBlockAccess world) {
/* 253 */     RayTraceResult ray = rayTraceTo(pos, world);
/*     */     
/* 255 */     return (ray != null && ray
/* 256 */       .func_178782_a() != null && ray
/* 257 */       .func_178782_a().equals(pos) && (facing == null || ray.field_178784_b == facing));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTraceTo(BlockPos pos, IBlockAccess world) {
/* 263 */     return rayTraceTo(pos, world, (b, p) -> p.equals(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTraceTo(BlockPos pos, IBlockAccess world, BiPredicate<Block, BlockPos> check) {
/* 269 */     return rayTraceWithYP(pos, world, Thunderhack.rotationManager
/* 270 */         .getServerYaw(), Thunderhack.rotationManager
/* 271 */         .getServerPitch(), check);
/*     */   }
/*     */   
/*     */   public static RayTraceResult rayTraceWithYP(BlockPos pos, IBlockAccess world, float yaw, float pitch, BiPredicate<Block, BlockPos> check) {
/* 275 */     EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/* 276 */     Vec3d start = Thunderhack.positionManager.getVec().func_72441_c(0.0D, entityPlayerSP.func_70047_e(), 0.0D);
/* 277 */     Vec3d look = getVec3d(yaw, pitch);
/* 278 */     double d = entityPlayerSP.func_70011_f(pos.func_177958_n() + 0.5D, pos
/* 279 */         .func_177956_o() + 0.5D, pos
/* 280 */         .func_177952_p() + 0.5D) + 1.0D;
/*     */     
/* 282 */     Vec3d end = start.func_72441_c(look.field_72450_a * d, look.field_72448_b * d, look.field_72449_c * d);
/*     */     
/* 284 */     return RayTracer.trace((World)Util.mc.field_71441_e, world, start, end, true, false, true, check);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] faceSmoothly(double curYaw, double curPitch, double intendedYaw, double intendedPitch, double yawSpeed, double pitchSpeed) {
/* 301 */     float yaw = updateRotation((float)curYaw, (float)intendedYaw, (float)yawSpeed);
/*     */ 
/*     */ 
/*     */     
/* 305 */     float pitch = updateRotation((float)curPitch, (float)intendedPitch, (float)pitchSpeed);
/*     */ 
/*     */ 
/*     */     
/* 309 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static double angle(float[] rotation1, float[] rotation2) {
/* 313 */     Vec3d r1Vec = getVec3d(rotation1[0], rotation1[1]);
/* 314 */     Vec3d r2Vec = getVec3d(rotation2[0], rotation2[1]);
/* 315 */     return MathUtil.angle(r1Vec, r2Vec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float updateRotation(float current, float intended, float factor) {
/* 329 */     float updated = MathHelper.func_76142_g(intended - current);
/*     */     
/* 331 */     if (updated > factor) {
/* 332 */       updated = factor;
/*     */     }
/*     */     
/* 335 */     if (updated < -factor) {
/* 336 */       updated = -factor;
/*     */     }
/*     */     
/* 339 */     return current + updated;
/*     */   }
/*     */   
/*     */   public static int getDirection4D() {
/* 343 */     return MathHelper.func_76128_c((Util.mc.field_71439_g.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */