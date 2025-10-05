/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class PathFinder {
/*     */   public static final double MAX_RANGE = 7.0D;
/*     */   public static final GeoCache CACHE;
/*     */   public static final TriPredicate<BlockPos, Pathable, Entity> CHECK;
/*     */   
/*     */   static {
/*  21 */     CHECK = ((b, d, e) -> {
/*     */         if (e == null || !e.field_70156_m || e.field_70128_L || !intersects(e.func_174813_aQ(), b)) {
/*     */           return true;
/*     */         }
/*     */         if (d != null && e instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/*     */           d.getBlockingEntities().add(new BlockingEntity(e, b));
/*     */           return false;
/*     */         } 
/*     */         return false;
/*     */       });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final EnumFacing[] SN = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH };
/*  40 */   private static final EnumFacing[] EW = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST };
/*  41 */   private static final EnumFacing[] UD = new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
/*  42 */   private static final boolean[][] CHECKS = new boolean[][] { { true, false }, { false, true }, { true, true }, { false, false } };
/*     */ 
/*     */   
/*     */   static {
/*  46 */     CACHE = new PathCache(1365, 8, 7.0D);
/*  47 */     CACHE.cache();
/*     */   }
/*     */   
/*     */   private PathFinder() {
/*  51 */     throw new AssertionError();
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
/*     */   public static void efficient(Pathable p, double pr, List<Entity> es, AutoCrystal.RayTraceMode mode, IBlockStateHelper world, IBlockState setState, TriPredicate<BlockPos, Pathable, Entity> check, Iterable<BlockPos> limited, BlockPos... ignore) {
/*  63 */     fastPath(p, pr, es, mode, world, setState, check, limited, ignore);
/*  64 */     if (!p.isValid()) {
/*  65 */       findPath(p, pr, es, mode, world, setState, check, ignore);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean intersects(AxisAlignedBB bb, BlockPos pos) {
/*  70 */     return bb.func_186668_a(pos.func_177958_n(), pos
/*  71 */         .func_177956_o(), pos
/*  72 */         .func_177952_p(), (pos
/*  73 */         .func_177958_n() + 1), (pos
/*  74 */         .func_177956_o() + 1), (pos
/*  75 */         .func_177952_p() + 1));
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
/*     */   public static void fastPath(Pathable p, double pr, List<Entity> es, AutoCrystal.RayTraceMode m, IBlockStateHelper world, IBlockState s, TriPredicate<BlockPos, Pathable, Entity> c, Iterable<BlockPos> tc, BlockPos... ignore) {
/*  87 */     if (pr > 7.0D) {
/*  88 */       throw new IllegalArgumentException("Range " + pr + " was bigger than MAX_RANGE: " + 7.0D);
/*     */     }
/*     */ 
/*     */     
/*  92 */     Set<BlockPos> ignored = Sets.newHashSet((Object[])ignore);
/*  93 */     for (BlockPos pos : tc) {
/*  94 */       if (checkPos(pos, p, ignored, pr, es, m, world, s, c)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void findPath(Pathable pathable, double pr, List<Entity> entities, AutoCrystal.RayTraceMode mode, IBlockStateHelper world, IBlockState setState, TriPredicate<BlockPos, Pathable, Entity> check, BlockPos... ignore) {
/* 108 */     if (pr > 7.0D) {
/* 109 */       throw new IllegalArgumentException("Range " + pr + " was bigger than MAX_RANGE: " + 7.0D);
/*     */     }
/*     */ 
/*     */     
/* 113 */     Set<BlockPos> ignored = Sets.newHashSet((Object[])ignore);
/* 114 */     int maxRadius = CACHE.getRadius(pr);
/* 115 */     Vec3i[] offsets = CACHE.array();
/* 116 */     for (int i = 1; i < maxRadius && 
/* 117 */       !checkPos(pathable.getPos().func_177971_a(offsets[i]), pathable, ignored, pr, entities, mode, world, setState, check); i++);
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
/*     */   private static boolean checkPos(BlockPos pos, Pathable pathable, Set<BlockPos> ignored, double pr, List<Entity> entities, AutoCrystal.RayTraceMode mode, IBlockStateHelper world, IBlockState setState, TriPredicate<BlockPos, Pathable, Entity> c) {
/* 140 */     IBlockState state = world.func_180495_p(pos);
/* 141 */     if (state.func_185904_a().func_76222_j()) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     if (pathable.getFrom().func_174818_b(pos) > MathUtil.square(pr) || ignored
/* 146 */       .contains(pos)) {
/* 147 */       return false;
/*     */     }
/*     */     
/* 150 */     int xDiff = pathable.getPos().func_177958_n() - pos.func_177958_n();
/* 151 */     int yDiff = pathable.getPos().func_177956_o() - pos.func_177956_o();
/* 152 */     int zDiff = pathable.getPos().func_177952_p() - pos.func_177952_p();
/*     */     
/* 154 */     for (int i = 0; i < CHECKS.length; i++) {
/* 155 */       boolean[] check = CHECKS[i];
/* 156 */       EnumFacing[] facings = produceOffsets(check[0], check[1], xDiff, yDiff, zDiff);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 161 */       if (facings.length <= pathable.getMaxLength()) {
/*     */ 
/*     */ 
/*     */         
/* 165 */         int index = 0;
/* 166 */         boolean valid = true;
/* 167 */         BlockPos current = pos;
/* 168 */         Ray[] path = new Ray[facings.length];
/* 169 */         for (EnumFacing facing : facings) {
/* 170 */           BlockPos offset = current.func_177972_a(facing);
/* 171 */           if (check(offset, pr, ignored, pathable, entities, c)) {
/* 172 */             valid = false;
/*     */             
/*     */             break;
/*     */           } 
/* 176 */           Ray ray = RayTraceFactory.rayTrace(pathable.getFrom(), current, facing, world, setState, (mode == AutoCrystal.RayTraceMode.Smart) ? -1.0D : 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 184 */           if (!ray.isLegit() && mode == AutoCrystal.RayTraceMode.Smart) {
/* 185 */             valid = false;
/*     */             
/*     */             break;
/*     */           } 
/* 189 */           path[index++] = ray;
/* 190 */           current = offset;
/*     */         } 
/*     */         
/* 193 */         if (valid) {
/* 194 */           pathable.setPath(path);
/* 195 */           pathable.setValid(true);
/* 196 */           return true;
/*     */         } 
/*     */         
/* 199 */         if (facings.length == 1 || (facings.length < 4 && i > 0)) {
/*     */           break;
/*     */         }
/*     */         
/* 203 */         pathable.getBlockingEntities().clear();
/*     */       } 
/*     */     } 
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean check(BlockPos pos, double pr, Set<BlockPos> ignored, Pathable pathable, List<Entity> entities, TriPredicate<BlockPos, Pathable, Entity> c) {
/* 215 */     return (pathable.getFrom().func_174818_b(pos) > MathUtil.square(pr) || ignored
/* 216 */       .contains(pos) || 
/* 217 */       checkEntities(pathable, pos, entities, c));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkEntities(Pathable data, BlockPos pos, List<Entity> entities, TriPredicate<BlockPos, Pathable, Entity> check) {
/* 225 */     for (Entity entity : entities) {
/* 226 */       if (check.test(pos, data, entity)) {
/*     */         continue;
/*     */       }
/*     */       
/* 230 */       return true;
/*     */     } 
/*     */     
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFacing[] produceOffsets(boolean yFirst, boolean xFirst, int xDiff, int yDiff, int zDiff) {
/* 243 */     EnumFacing[] result = new EnumFacing[Math.abs(xDiff) + Math.abs(yDiff) + Math.abs(zDiff)];
/* 244 */     int index = 0;
/* 245 */     if (yFirst) {
/* 246 */       index = apply(result, yDiff, index, UD);
/* 247 */       if (xFirst) {
/* 248 */         index = apply(result, xDiff, index, EW);
/* 249 */         apply(result, zDiff, index, SN);
/*     */       } else {
/* 251 */         index = apply(result, zDiff, index, SN);
/* 252 */         apply(result, xDiff, index, EW);
/*     */       } 
/*     */     } else {
/* 255 */       if (xFirst) {
/* 256 */         index = apply(result, xDiff, index, EW);
/* 257 */         index = apply(result, zDiff, index, SN);
/*     */       } else {
/* 259 */         index = apply(result, zDiff, index, SN);
/* 260 */         index = apply(result, xDiff, index, EW);
/*     */       } 
/*     */       
/* 263 */       apply(result, yDiff, index, UD);
/*     */     } 
/*     */     
/* 266 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int apply(EnumFacing[] array, int diff, int start, EnumFacing[] facings) {
/* 273 */     int i = 0;
/* 274 */     for (; i < Math.abs(diff); i++) {
/* 275 */       array[i + start] = (diff > 0) ? facings[0] : facings[1];
/*     */     }
/*     */     
/* 278 */     return start + i;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\PathFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */