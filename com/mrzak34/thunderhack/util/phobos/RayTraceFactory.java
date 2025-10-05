/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.modules.combat.Burrow;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.RotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RayTraceFactory
/*     */ {
/*  26 */   private static final EnumFacing[] T = new EnumFacing[] { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN };
/*  27 */   private static final EnumFacing[] B = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.UP };
/*  28 */   private static final EnumFacing[] S = new EnumFacing[] { EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN };
/*     */   
/*     */   private RayTraceFactory() {
/*  31 */     throw new AssertionError();
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
/*     */   public static Ray fullTrace(Entity from, IBlockAccess world, BlockPos pos, double resolution) {
/*  50 */     Ray dumbRay = null;
/*  51 */     double closest = Double.MAX_VALUE;
/*  52 */     for (EnumFacing facing : getOptimalFacings(from, pos)) {
/*  53 */       BlockPos offset = pos.func_177972_a(facing);
/*  54 */       IBlockState state = world.func_180495_p(offset);
/*  55 */       if (!state.func_185904_a().func_76222_j()) {
/*     */ 
/*     */ 
/*     */         
/*  59 */         Ray ray = rayTrace(from, offset, facing
/*  60 */             .func_176734_d(), world, state, resolution);
/*  61 */         if (ray.isLegit()) {
/*  62 */           return ray;
/*     */         }
/*     */         
/*  65 */         double dist = BlockUtils.getDistanceSq(from, offset);
/*  66 */         if (dumbRay == null || dist < closest) {
/*  67 */           closest = dist;
/*  68 */           dumbRay = ray;
/*     */         } 
/*     */       } 
/*     */     } 
/*  72 */     return dumbRay;
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
/*     */   public static Ray rayTrace(Entity from, BlockPos on, EnumFacing facing, IBlockAccess access, IBlockState state, double res) {
/*  97 */     Vec3d start = Burrow.getEyePos(from);
/*  98 */     AxisAlignedBB bb = state.func_185900_c(access, on);
/*  99 */     if (res >= 1.0D) {
/* 100 */       float[] r = rots(on, facing, from, access, state);
/* 101 */       Vec3d look = RotationUtil.getVec3d(r[0], r[1]);
/* 102 */       double d = Util.mc.field_71442_b.func_78757_d();
/* 103 */       Vec3d rotations = start.func_72441_c(look.field_72450_a * d, look.field_72448_b * d, look.field_72449_c * d);
/* 104 */       RayTraceResult result = RayTracer.trace((World)Util.mc.field_71441_e, access, start, rotations, false, false, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       if (result == null || result.field_178784_b != facing || 
/*     */         
/* 113 */         !on.equals(result.func_178782_a())) {
/* 114 */         return dumbRay(on, facing, r);
/*     */       }
/*     */       
/* 117 */       return (new Ray(result, r, on, facing, null)).setLegit(true);
/*     */     } 
/* 119 */     Vec3i dirVec = facing.func_176730_m();
/*     */ 
/*     */     
/* 122 */     double dirX = (dirVec.func_177958_n() < 0) ? bb.field_72340_a : (dirVec.func_177958_n() * bb.field_72336_d);
/*     */ 
/*     */     
/* 125 */     double dirY = (dirVec.func_177956_o() < 0) ? bb.field_72338_b : (dirVec.func_177956_o() * bb.field_72337_e);
/*     */ 
/*     */     
/* 128 */     double dirZ = (dirVec.func_177952_p() < 0) ? bb.field_72339_c : (dirVec.func_177952_p() * bb.field_72334_f);
/*     */ 
/*     */     
/* 131 */     double minX = on.func_177958_n() + dirX + ((dirVec.func_177958_n() == 0) ? bb.field_72340_a : 0.0D);
/*     */     
/* 133 */     double minY = on.func_177956_o() + dirY + ((dirVec.func_177956_o() == 0) ? bb.field_72338_b : 0.0D);
/*     */     
/* 135 */     double minZ = on.func_177952_p() + dirZ + ((dirVec.func_177952_p() == 0) ? bb.field_72339_c : 0.0D);
/*     */ 
/*     */     
/* 138 */     double maxX = on.func_177958_n() + dirX + ((dirVec.func_177958_n() == 0) ? bb.field_72336_d : 0.0D);
/*     */     
/* 140 */     double maxY = on.func_177956_o() + dirY + ((dirVec.func_177956_o() == 0) ? bb.field_72337_e : 0.0D);
/*     */     
/* 142 */     double maxZ = on.func_177952_p() + dirZ + ((dirVec.func_177952_p() == 0) ? bb.field_72334_f : 0.0D);
/*     */     
/* 144 */     boolean xEq = (Double.compare(minX, maxX) == 0);
/* 145 */     boolean yEq = (Double.compare(minY, maxY) == 0);
/* 146 */     boolean zEq = (Double.compare(minZ, maxZ) == 0);
/*     */ 
/*     */     
/* 149 */     if (xEq) {
/* 150 */       minX -= dirVec.func_177958_n() * 5.0E-4D;
/* 151 */       maxX = minX;
/*     */     } 
/*     */     
/* 154 */     if (yEq) {
/* 155 */       minY -= dirVec.func_177956_o() * 5.0E-4D;
/* 156 */       maxY = minY;
/*     */     } 
/*     */     
/* 159 */     if (zEq) {
/* 160 */       minZ -= dirVec.func_177952_p() * 5.0E-4D;
/* 161 */       maxZ = minZ;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     double endX = Math.max(minX, maxX) - (xEq ? 0.0D : 5.0E-4D);
/* 168 */     double endY = Math.max(minY, maxY) - (yEq ? 0.0D : 5.0E-4D);
/* 169 */     double endZ = Math.max(minZ, maxZ) - (zEq ? 0.0D : 5.0E-4D);
/*     */     
/* 171 */     if (res <= 0.0D) {
/*     */       
/* 173 */       double staX = Math.min(minX, maxX) + (xEq ? 0.0D : 5.0E-4D);
/* 174 */       double staY = Math.min(minY, maxY) + (yEq ? 0.0D : 5.0E-4D);
/* 175 */       double staZ = Math.min(minZ, maxZ) + (zEq ? 0.0D : 5.0E-4D);
/*     */ 
/*     */ 
/*     */       
/* 179 */       Set<Vec3d> vectors = new HashSet<>();
/* 180 */       vectors.add(new Vec3d(staX, staY, staZ));
/* 181 */       vectors.add(new Vec3d(staX, staY, endZ));
/* 182 */       vectors.add(new Vec3d(staX, endY, staZ));
/* 183 */       vectors.add(new Vec3d(staX, endY, endZ));
/* 184 */       vectors.add(new Vec3d(endX, staY, staZ));
/* 185 */       vectors.add(new Vec3d(endX, staY, endZ));
/* 186 */       vectors.add(new Vec3d(endX, endY, staZ));
/* 187 */       vectors.add(new Vec3d(endX, endY, endZ));
/*     */       
/* 189 */       double d1 = (endX - staX) / 2.0D + staX;
/* 190 */       double y = (endY - staY) / 2.0D + staY;
/* 191 */       double z = (endZ - staZ) / 2.0D + staZ;
/*     */       
/* 193 */       vectors.add(new Vec3d(d1, y, z));
/*     */       
/* 195 */       for (Vec3d vec : vectors) {
/* 196 */         RayTraceResult ray = RayTracer.trace((World)Util.mc.field_71441_e, access, start, vec, false, false, true);
/*     */ 
/*     */         
/* 199 */         if (ray != null && on
/* 200 */           .equals(ray.func_178782_a()) && facing == ray.field_178784_b)
/*     */         {
/* 202 */           return (new Ray(ray, rots(from, vec), on, facing, vec))
/* 203 */             .setLegit(true);
/*     */         }
/*     */       } 
/*     */       
/* 207 */       return dumbRay(on, facing, 
/* 208 */           rots(on, facing, from, access, state));
/*     */     } 
/*     */     
/*     */     double x;
/* 212 */     for (x = Math.min(minX, maxX); x <= endX; x += res) {
/* 213 */       double y; for (y = Math.min(minY, maxY); y <= endY; y += res) {
/* 214 */         double z; for (z = Math.min(minZ, maxZ); z <= endZ; z += res) {
/* 215 */           Vec3d vector = new Vec3d(x, y, z);
/* 216 */           RayTraceResult ray = RayTracer.trace((World)Util.mc.field_71441_e, access, start, vector, false, false, true);
/*     */           
/* 218 */           if (ray != null && facing == ray.field_178784_b && on
/*     */             
/* 220 */             .equals(ray.func_178782_a())) {
/* 221 */             return (new Ray(ray, rots(from, vector), on, facing, vector))
/* 222 */               .setLegit(true);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 229 */     return dumbRay(on, facing, rots(on, facing, from, access, state));
/*     */   }
/*     */   
/*     */   public static Ray dumbRay(BlockPos on, EnumFacing offset, float[] rotations) {
/* 233 */     return newRay(new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(0.5D, 1.0D, 0.5D), EnumFacing.UP, BlockPos.field_177992_a), on, offset, rotations);
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
/*     */   public static Ray newRay(RayTraceResult result, BlockPos on, EnumFacing offset, float[] rotations) {
/* 246 */     return new Ray(result, rotations, on, offset, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static float[] rots(Entity from, Vec3d vec3d) {
/* 252 */     return RotationUtil.getRotations(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, from);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] rots(BlockPos pos, EnumFacing facing, Entity from, IBlockAccess world, IBlockState state) {
/* 260 */     return RotationUtil.getRotations(pos, facing, from, world, state);
/*     */   }
/*     */   
/*     */   private static EnumFacing[] getOptimalFacings(Entity player, BlockPos pos) {
/* 264 */     if (pos.func_177956_o() > player.field_70163_u + 2.0D)
/* 265 */       return T; 
/* 266 */     if (pos.func_177956_o() < player.field_70163_u) {
/* 267 */       return B;
/*     */     }
/*     */     
/* 270 */     return S;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\RayTraceFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */