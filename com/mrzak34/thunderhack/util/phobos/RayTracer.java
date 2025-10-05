/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.mrzak34.thunderhack.manager.PositionManager;
/*     */ import com.mrzak34.thunderhack.manager.RotationManager;
/*     */ import com.mrzak34.thunderhack.util.RotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.List;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class RayTracer
/*     */ {
/*     */   private static final Predicate<Entity> PREDICATE;
/*     */   
/*     */   static {
/*  31 */     PREDICATE = Predicates.and(EntitySelectors.field_180132_d, e -> 
/*  32 */         (e != null && e.func_70067_L()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTraceEntities(World world, Entity from, double range, PositionManager position, RotationManager rotation, Predicate<Entity> entityCheck, Entity... additional) {
/*  41 */     return rayTraceEntities(world, from, range, position
/*     */ 
/*     */         
/*  44 */         .getX(), position
/*  45 */         .getY(), position
/*  46 */         .getZ(), rotation
/*  47 */         .getServerYaw(), rotation
/*  48 */         .getServerPitch(), position
/*  49 */         .getBB(), entityCheck, additional);
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
/*     */   public static RayTraceResult rayTraceEntities(World world, Entity from, double range, double posX, double posY, double posZ, float yaw, float pitch, AxisAlignedBB fromBB, Predicate<Entity> entityCheck, Entity... additional) {
/*     */     List<Entity> entities;
/*  66 */     Vec3d eyePos = new Vec3d(posX, posY + from.func_70047_e(), posZ);
/*     */     
/*  68 */     Vec3d rot = RotationUtil.getVec3d(yaw, pitch);
/*     */     
/*  70 */     Vec3d intercept = eyePos.func_72441_c(rot.field_72450_a * range, rot.field_72448_b * range, rot.field_72449_c * range);
/*     */     
/*  72 */     Entity pointedEntity = null;
/*  73 */     Vec3d hitVec = null;
/*  74 */     double distance = range;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     AxisAlignedBB within = fromBB.func_72321_a(rot.field_72450_a * range, rot.field_72448_b * range, rot.field_72449_c * range).func_72314_b(1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/*  84 */     Predicate<Entity> predicate = (entityCheck == null) ? PREDICATE : Predicates.and(PREDICATE, entityCheck);
/*     */     
/*  86 */     if (Util.mc.func_152345_ab()) {
/*  87 */       entities = world.func_175674_a(from, within, predicate);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       entities = (List<Entity>)Util.mc.field_71441_e.field_72996_f.stream().filter(e -> (e != null && e.func_174813_aQ().func_72326_a(within) && predicate.test(e))).collect(Collectors.toList());
/*     */     } 
/*     */     
/* 100 */     for (Entity entity : additional) {
/* 101 */       if (entity != null && entity
/* 102 */         .func_174813_aQ().func_72326_a(within)) {
/* 103 */         entities.add(entity);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     for (Entity entity : entities) {
/*     */       
/* 109 */       AxisAlignedBB bb = entity.func_174813_aQ().func_186662_g(entity.func_70111_Y());
/*     */       
/* 111 */       RayTraceResult result = bb.func_72327_a(eyePos, intercept);
/*     */       
/* 113 */       if (bb.func_72318_a(eyePos)) {
/* 114 */         if (distance >= 0.0D) {
/* 115 */           pointedEntity = entity;
/* 116 */           hitVec = (result == null) ? eyePos : result.field_72307_f;
/* 117 */           distance = 0.0D;
/*     */         }  continue;
/* 119 */       }  if (result != null) {
/* 120 */         double hitDistance = eyePos.func_72438_d(result.field_72307_f);
/*     */         
/* 122 */         if (hitDistance < distance || distance == 0.0D) {
/* 123 */           if (entity.func_184208_bv() == from
/* 124 */             .func_184208_bv()) {
/*     */ 
/*     */             
/* 127 */             if (distance == 0.0D) {
/* 128 */               pointedEntity = entity;
/* 129 */               hitVec = result.field_72307_f;
/*     */             }  continue;
/*     */           } 
/* 132 */           pointedEntity = entity;
/* 133 */           hitVec = result.field_72307_f;
/* 134 */           distance = hitDistance;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 140 */     if (pointedEntity != null && hitVec != null) {
/* 141 */       return new RayTraceResult(pointedEntity, hitVec);
/*     */     }
/*     */     
/* 144 */     return null;
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
/*     */   public static RayTraceResult trace(World world, IBlockAccess access, Vec3d start, Vec3d end, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/* 158 */     return trace(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, null);
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
/*     */   public static RayTraceResult trace(World world, Vec3d start, Vec3d end, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock, BiPredicate<Block, BlockPos> blockChecker) {
/* 179 */     return trace(world, (IBlockAccess)world, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult trace(World world, IBlockAccess access, Vec3d start, Vec3d end, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock, BiPredicate<Block, BlockPos> blockChecker) {
/* 211 */     return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, (blockChecker == null) ? null : ((b, p, ef) -> blockChecker.test(b, p)));
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
/*     */   public static RayTraceResult traceTri(World world, IBlockAccess access, Vec3d start, Vec3d end, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock, TriPredicate<Block, BlockPos, EnumFacing> blockChecker) {
/* 231 */     return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker, null);
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
/*     */   public static RayTraceResult traceTri(World world, IBlockAccess access, Vec3d start, Vec3d end, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock, TriPredicate<Block, BlockPos, EnumFacing> blockChecker, TriPredicate<Block, BlockPos, EnumFacing> collideCheck) {
/* 251 */     return traceTri(world, access, start, end, stopOnLiquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock, blockChecker, collideCheck, CollisionFunction.DEFAULT);
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
/*     */   public static RayTraceResult traceTri(World world, IBlockAccess access, Vec3d start, Vec3d end, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock, TriPredicate<Block, BlockPos, EnumFacing> blockChecker, TriPredicate<Block, BlockPos, EnumFacing> collideCheck, CollisionFunction crt) {
/* 273 */     if (!Double.isNaN(start.field_72450_a) && 
/* 274 */       !Double.isNaN(start.field_72448_b) && 
/* 275 */       !Double.isNaN(start.field_72449_c)) {
/* 276 */       if (!Double.isNaN(end.field_72450_a) && 
/* 277 */         !Double.isNaN(end.field_72448_b) && 
/* 278 */         !Double.isNaN(end.field_72449_c)) {
/* 279 */         int feX = MathHelper.func_76128_c(end.field_72450_a);
/* 280 */         int feY = MathHelper.func_76128_c(end.field_72448_b);
/* 281 */         int feZ = MathHelper.func_76128_c(end.field_72449_c);
/* 282 */         int fsX = MathHelper.func_76128_c(start.field_72450_a);
/* 283 */         int fsY = MathHelper.func_76128_c(start.field_72448_b);
/* 284 */         int fsZ = MathHelper.func_76128_c(start.field_72449_c);
/* 285 */         BlockPos pos = new BlockPos(fsX, fsY, fsZ);
/* 286 */         IBlockState state = access.func_180495_p(pos);
/* 287 */         Block block = state.func_177230_c();
/*     */         
/* 289 */         if ((!ignoreBlockWithoutBoundingBox || state
/* 290 */           .func_185890_d(access, pos) != Block.field_185506_k) && (block
/*     */           
/* 292 */           .func_176209_a(state, stopOnLiquid) || (collideCheck != null && collideCheck
/*     */           
/* 294 */           .test(block, pos, null))) && (blockChecker == null || blockChecker
/*     */           
/* 296 */           .test(block, pos, null))) {
/*     */           
/* 298 */           RayTraceResult raytraceresult = crt.collisionRayTrace(state, world, pos, start, end);
/*     */           
/* 300 */           if (raytraceresult != null) {
/* 301 */             return raytraceresult;
/*     */           }
/*     */         } 
/*     */         
/* 305 */         RayTraceResult result = null;
/* 306 */         int steps = 200;
/*     */         
/* 308 */         while (steps-- >= 0) {
/* 309 */           EnumFacing enumfacing; if (Double.isNaN(start.field_72450_a) || 
/* 310 */             Double.isNaN(start.field_72448_b) || 
/* 311 */             Double.isNaN(start.field_72449_c)) {
/* 312 */             return null;
/*     */           }
/*     */           
/* 315 */           if (fsX == feX && fsY == feY && fsZ == feZ) {
/* 316 */             return returnLastUncollidableBlock ? result : null;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 321 */           boolean xEq = true;
/* 322 */           boolean yEq = true;
/* 323 */           boolean zEq = true;
/* 324 */           double x = 999.0D;
/* 325 */           double y = 999.0D;
/* 326 */           double z = 999.0D;
/*     */           
/* 328 */           if (feX > fsX) {
/* 329 */             x = fsX + 1.0D;
/* 330 */           } else if (feX < fsX) {
/* 331 */             x = fsX + 0.0D;
/*     */           } else {
/* 333 */             xEq = false;
/*     */           } 
/*     */           
/* 336 */           if (feY > fsY) {
/* 337 */             y = fsY + 1.0D;
/* 338 */           } else if (feY < fsY) {
/* 339 */             y = fsY + 0.0D;
/*     */           } else {
/* 341 */             yEq = false;
/*     */           } 
/*     */           
/* 344 */           if (feZ > fsZ) {
/* 345 */             z = fsZ + 1.0D;
/* 346 */           } else if (feZ < fsZ) {
/* 347 */             z = fsZ + 0.0D;
/*     */           } else {
/* 349 */             zEq = false;
/*     */           } 
/*     */           
/* 352 */           double xOff = 999.0D;
/* 353 */           double yOff = 999.0D;
/* 354 */           double zOff = 999.0D;
/* 355 */           double diffX = end.field_72450_a - start.field_72450_a;
/* 356 */           double diffY = end.field_72448_b - start.field_72448_b;
/* 357 */           double diffZ = end.field_72449_c - start.field_72449_c;
/*     */           
/* 359 */           if (xEq) {
/* 360 */             xOff = (x - start.field_72450_a) / diffX;
/*     */           }
/*     */           
/* 363 */           if (yEq) {
/* 364 */             yOff = (y - start.field_72448_b) / diffY;
/*     */           }
/*     */           
/* 367 */           if (zEq) {
/* 368 */             zOff = (z - start.field_72449_c) / diffZ;
/*     */           }
/*     */           
/* 371 */           if (xOff == -0.0D) {
/* 372 */             xOff = -1.0E-4D;
/*     */           }
/*     */           
/* 375 */           if (yOff == -0.0D) {
/* 376 */             yOff = -1.0E-4D;
/*     */           }
/*     */           
/* 379 */           if (zOff == -0.0D) {
/* 380 */             zOff = -1.0E-4D;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 385 */           if (xOff < yOff && xOff < zOff) {
/* 386 */             enumfacing = (feX > fsX) ? EnumFacing.WEST : EnumFacing.EAST;
/*     */ 
/*     */ 
/*     */             
/* 390 */             start = new Vec3d(x, start.field_72448_b + diffY * xOff, start.field_72449_c + diffZ * xOff);
/*     */           
/*     */           }
/* 393 */           else if (yOff < zOff) {
/* 394 */             enumfacing = (feY > fsY) ? EnumFacing.DOWN : EnumFacing.UP;
/*     */ 
/*     */ 
/*     */             
/* 398 */             start = new Vec3d(start.field_72450_a + diffX * yOff, y, start.field_72449_c + diffZ * yOff);
/*     */           }
/*     */           else {
/*     */             
/* 402 */             enumfacing = (feZ > fsZ) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/*     */ 
/*     */ 
/*     */             
/* 406 */             start = new Vec3d(start.field_72450_a + diffX * zOff, start.field_72448_b + diffY * zOff, z);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 411 */           fsX = MathHelper.func_76128_c(start.field_72450_a) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
/*     */           
/* 413 */           fsY = MathHelper.func_76128_c(start.field_72448_b) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
/*     */           
/* 415 */           fsZ = MathHelper.func_76128_c(start.field_72449_c) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
/*     */ 
/*     */           
/* 418 */           pos = new BlockPos(fsX, fsY, fsZ);
/* 419 */           IBlockState state1 = access.func_180495_p(pos);
/* 420 */           Block block1 = state1.func_177230_c();
/*     */           
/* 422 */           if (!ignoreBlockWithoutBoundingBox || state1
/* 423 */             .func_185904_a() == Material.field_151567_E || state1
/* 424 */             .func_185890_d(access, pos) != Block.field_185506_k) {
/*     */             
/* 426 */             if ((block1.func_176209_a(state1, stopOnLiquid) || (collideCheck != null && collideCheck
/*     */               
/* 428 */               .test(block1, pos, enumfacing))) && (blockChecker == null || blockChecker
/*     */               
/* 430 */               .test(block1, pos, enumfacing))) {
/*     */               
/* 432 */               RayTraceResult raytraceresult1 = crt.collisionRayTrace(state1, world, pos, start, end);
/*     */ 
/*     */               
/* 435 */               if (raytraceresult1 != null)
/* 436 */                 return raytraceresult1; 
/*     */               continue;
/*     */             } 
/* 439 */             result = new RayTraceResult(RayTraceResult.Type.MISS, start, enumfacing, pos);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 448 */         return returnLastUncollidableBlock ? result : null;
/*     */       } 
/* 450 */       return null;
/*     */     } 
/*     */     
/* 453 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\RayTracer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */