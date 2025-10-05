/*     */ package com.mrzak34.thunderhack.util;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.util.CombatRules;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CrystalUtils {
/*  28 */   public static Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*  30 */   private static final List<Block> valid = Arrays.asList(new Block[] { Blocks.field_150343_Z, Blocks.field_150357_h, Blocks.field_150477_bB, Blocks.field_150467_bQ });
/*     */ 
/*     */   
/*     */   public static float getBlastReduction(EntityLivingBase entity, float damageInput, Explosion explosion) {
/*  34 */     float damage = damageInput;
/*  35 */     if (entity instanceof EntityPlayer) {
/*  36 */       EntityPlayer ep = (EntityPlayer)entity;
/*  37 */       DamageSource ds = DamageSource.func_94539_a(explosion);
/*  38 */       damage = CombatRules.func_189427_a(damage, ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/*  39 */       int k = 0;
/*     */       try {
/*  41 */         k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
/*  42 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/*  45 */       float f = MathHelper.func_76131_a(k, 0.0F, 20.0F);
/*  46 */       damage *= 1.0F - f / 25.0F;
/*     */       
/*  48 */       if (entity.func_70644_a(MobEffects.field_76429_m)) {
/*  49 */         damage -= damage / 4.0F;
/*     */       }
/*     */       
/*  52 */       damage = Math.max(damage, 0.0F);
/*  53 */       return damage;
/*     */     } 
/*  55 */     damage = CombatRules.func_189427_a(damage, entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/*  56 */     return damage;
/*     */   }
/*     */   
/*     */   public static float getDamageMultiplied(float damage) {
/*  60 */     int diff = mc.field_71441_e.func_175659_aa().func_151525_a();
/*  61 */     return damage * ((diff == 0) ? 0.0F : ((diff == 2) ? 1.0F : ((diff == 1) ? 0.5F : 1.5F)));
/*     */   }
/*     */   
/*     */   public static Vec3d getEntityPosVec(Entity entity, int ticks) {
/*  65 */     return entity.func_174791_d().func_178787_e(getMotionVec(entity, ticks));
/*     */   }
/*     */   
/*     */   public static Vec3d getMotionVec(Entity entity, int ticks) {
/*  69 */     double dX = entity.field_70165_t - entity.field_70169_q;
/*  70 */     double dZ = entity.field_70161_v - entity.field_70166_s;
/*  71 */     double entityMotionPosX = 0.0D;
/*  72 */     double entityMotionPosZ = 0.0D;
/*     */     
/*  74 */     for (int i = 1; i <= ticks && 
/*  75 */       mc.field_71441_e.func_180495_p(new BlockPos(entity.field_70165_t + dX * i, entity.field_70163_u, entity.field_70161_v + dZ * i)).func_177230_c() instanceof net.minecraft.block.BlockAir; i++) {
/*  76 */       entityMotionPosX = dX * i;
/*  77 */       entityMotionPosZ = dZ * i;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     return new Vec3d(entityMotionPosX, 0.0D, entityMotionPosZ);
/*     */   }
/*     */   
/*     */   public static int ping() {
/*  87 */     if (mc.func_147114_u() == null)
/*  88 */       return 150; 
/*  89 */     if (mc.field_71439_g == null) {
/*  90 */       return 150;
/*     */     }
/*     */     try {
/*  93 */       return mc.func_147114_u().func_175102_a(mc.field_71439_g.func_110124_au()).func_178853_c();
/*  94 */     } catch (NullPointerException nullPointerException) {
/*     */       
/*  96 */       return 150;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getCrystalSlot() {
/* 101 */     int crystalSlot = -1;
/*     */     
/* 103 */     if (Util.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_185158_cP) {
/* 104 */       crystalSlot = Util.mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/*     */ 
/*     */     
/* 108 */     if (crystalSlot == -1) {
/* 109 */       for (int l = 0; l < 9; l++) {
/* 110 */         if (Util.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_185158_cP) {
/* 111 */           crystalSlot = l;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 117 */     return crystalSlot;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canPlaceCrystal(BlockPos blockPos) {
/* 122 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/* 123 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/*     */     try {
/* 125 */       if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
/* 126 */         return false;
/*     */       }
/*     */       
/* 129 */       if (mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a || mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a) {
/* 130 */         return false;
/*     */       }
/*     */       
/* 133 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost))) {
/* 134 */         if (!(entity instanceof EntityEnderCrystal)) {
/* 135 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 139 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2))) {
/* 140 */         if (!(entity instanceof EntityEnderCrystal)) {
/* 141 */           return false;
/*     */         }
/*     */       } 
/* 144 */     } catch (Exception ignored) {
/* 145 */       return false;
/*     */     } 
/*     */     
/* 148 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static RayTraceResult rayTraceBlocks(Vec3d start, Vec3d end) {
/* 153 */     return rayTraceBlocks(start, end, false, false, false);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static RayTraceResult rayTraceBlocks(Vec3d vec31, Vec3d vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/* 158 */     if (!Double.isNaN(vec31.field_72450_a) && !Double.isNaN(vec31.field_72448_b) && !Double.isNaN(vec31.field_72449_c)) {
/* 159 */       if (!Double.isNaN(vec32.field_72450_a) && !Double.isNaN(vec32.field_72448_b) && !Double.isNaN(vec32.field_72449_c)) {
/* 160 */         int i = MathHelper.func_76128_c(vec32.field_72450_a);
/* 161 */         int j = MathHelper.func_76128_c(vec32.field_72448_b);
/* 162 */         int k = MathHelper.func_76128_c(vec32.field_72449_c);
/* 163 */         int l = MathHelper.func_76128_c(vec31.field_72450_a);
/* 164 */         int i1 = MathHelper.func_76128_c(vec31.field_72448_b);
/* 165 */         int j1 = MathHelper.func_76128_c(vec31.field_72449_c);
/* 166 */         BlockPos blockpos = new BlockPos(l, i1, j1);
/* 167 */         IBlockState iblockstate = mc.field_71441_e.func_180495_p(blockpos);
/* 168 */         Block block = iblockstate.func_177230_c();
/*     */         
/* 170 */         if (!valid.contains(block)) {
/* 171 */           block = Blocks.field_150350_a;
/* 172 */           iblockstate = Blocks.field_150350_a.func_176194_O().func_177621_b();
/*     */         } 
/*     */         
/* 175 */         if ((!ignoreBlockWithoutBoundingBox || iblockstate.func_185890_d((IBlockAccess)mc.field_71441_e, blockpos) != Block.field_185506_k) && block.func_176209_a(iblockstate, stopOnLiquid)) {
/* 176 */           RayTraceResult raytraceresult = iblockstate.func_185910_a((World)mc.field_71441_e, blockpos, vec31, vec32);
/*     */           
/* 178 */           if (raytraceresult != null) {
/* 179 */             return raytraceresult;
/*     */           }
/*     */         } 
/*     */         
/* 183 */         RayTraceResult raytraceresult2 = null;
/* 184 */         int k1 = 200;
/*     */         
/* 186 */         while (k1-- >= 0) {
/* 187 */           EnumFacing enumfacing; if (Double.isNaN(vec31.field_72450_a) || Double.isNaN(vec31.field_72448_b) || Double.isNaN(vec31.field_72449_c)) {
/* 188 */             return null;
/*     */           }
/*     */           
/* 191 */           if (l == i && i1 == j && j1 == k) {
/* 192 */             return returnLastUncollidableBlock ? raytraceresult2 : null;
/*     */           }
/*     */           
/* 195 */           boolean flag2 = true;
/* 196 */           boolean flag = true;
/* 197 */           boolean flag1 = true;
/* 198 */           double d0 = 999.0D;
/* 199 */           double d1 = 999.0D;
/* 200 */           double d2 = 999.0D;
/*     */           
/* 202 */           if (i > l) {
/* 203 */             d0 = l + 1.0D;
/* 204 */           } else if (i < l) {
/* 205 */             d0 = l + 0.0D;
/*     */           } else {
/* 207 */             flag2 = false;
/*     */           } 
/*     */           
/* 210 */           if (j > i1) {
/* 211 */             d1 = i1 + 1.0D;
/* 212 */           } else if (j < i1) {
/* 213 */             d1 = i1 + 0.0D;
/*     */           } else {
/* 215 */             flag = false;
/*     */           } 
/*     */           
/* 218 */           if (k > j1) {
/* 219 */             d2 = j1 + 1.0D;
/* 220 */           } else if (k < j1) {
/* 221 */             d2 = j1 + 0.0D;
/*     */           } else {
/* 223 */             flag1 = false;
/*     */           } 
/*     */           
/* 226 */           double d3 = 999.0D;
/* 227 */           double d4 = 999.0D;
/* 228 */           double d5 = 999.0D;
/* 229 */           double d6 = vec32.field_72450_a - vec31.field_72450_a;
/* 230 */           double d7 = vec32.field_72448_b - vec31.field_72448_b;
/* 231 */           double d8 = vec32.field_72449_c - vec31.field_72449_c;
/*     */           
/* 233 */           if (flag2) {
/* 234 */             d3 = (d0 - vec31.field_72450_a) / d6;
/*     */           }
/*     */           
/* 237 */           if (flag) {
/* 238 */             d4 = (d1 - vec31.field_72448_b) / d7;
/*     */           }
/*     */           
/* 241 */           if (flag1) {
/* 242 */             d5 = (d2 - vec31.field_72449_c) / d8;
/*     */           }
/*     */           
/* 245 */           if (d3 == -0.0D) {
/* 246 */             d3 = -1.0E-4D;
/*     */           }
/*     */           
/* 249 */           if (d4 == -0.0D) {
/* 250 */             d4 = -1.0E-4D;
/*     */           }
/*     */           
/* 253 */           if (d5 == -0.0D) {
/* 254 */             d5 = -1.0E-4D;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 259 */           if (d3 < d4 && d3 < d5) {
/* 260 */             enumfacing = (i > l) ? EnumFacing.WEST : EnumFacing.EAST;
/* 261 */             vec31 = new Vec3d(d0, vec31.field_72448_b + d7 * d3, vec31.field_72449_c + d8 * d3);
/* 262 */           } else if (d4 < d5) {
/* 263 */             enumfacing = (j > i1) ? EnumFacing.DOWN : EnumFacing.UP;
/* 264 */             vec31 = new Vec3d(vec31.field_72450_a + d6 * d4, d1, vec31.field_72449_c + d8 * d4);
/*     */           } else {
/* 266 */             enumfacing = (k > j1) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/* 267 */             vec31 = new Vec3d(vec31.field_72450_a + d6 * d5, vec31.field_72448_b + d7 * d5, d2);
/*     */           } 
/*     */           
/* 270 */           l = MathHelper.func_76128_c(vec31.field_72450_a) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
/* 271 */           i1 = MathHelper.func_76128_c(vec31.field_72448_b) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
/* 272 */           j1 = MathHelper.func_76128_c(vec31.field_72449_c) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
/* 273 */           blockpos = new BlockPos(l, i1, j1);
/* 274 */           IBlockState iblockstate1 = mc.field_71441_e.func_180495_p(blockpos);
/* 275 */           Block block1 = iblockstate1.func_177230_c();
/*     */           
/* 277 */           if (!valid.contains(block1)) {
/* 278 */             block1 = Blocks.field_150350_a;
/* 279 */             iblockstate1 = Blocks.field_150350_a.func_176194_O().func_177621_b();
/*     */           } 
/*     */           
/* 282 */           if (!ignoreBlockWithoutBoundingBox || iblockstate1.func_185904_a() == Material.field_151567_E || iblockstate1.func_185890_d((IBlockAccess)mc.field_71441_e, blockpos) != Block.field_185506_k) {
/* 283 */             if (block1.func_176209_a(iblockstate1, stopOnLiquid)) {
/* 284 */               RayTraceResult raytraceresult1 = iblockstate1.func_185910_a((World)mc.field_71441_e, blockpos, vec31, vec32);
/*     */               
/* 286 */               if (raytraceresult1 != null)
/* 287 */                 return raytraceresult1; 
/*     */               continue;
/*     */             } 
/* 290 */             raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 295 */         return returnLastUncollidableBlock ? raytraceresult2 : null;
/*     */       } 
/* 297 */       return null;
/*     */     } 
/*     */     
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float calculateDamage2(BlockPos pos, Entity entity) {
/* 305 */     return calculateDamage(pos.func_177958_n(), (pos.func_177956_o() + 1), pos.func_177952_p(), entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
/* 310 */     float doubleExplosionSize = 12.0F;
/*     */     
/* 312 */     Vec3d entityPosVec = getEntityPosVec(entity, 3);
/* 313 */     double distancedsize = entityPosVec.func_72438_d(new Vec3d(posX, posY, posZ)) / doubleExplosionSize;
/* 314 */     Vec3d vec3d = new Vec3d(posX, posY, posZ);
/* 315 */     double blockDensity = 0.0D;
/*     */     try {
/* 317 */       blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ().func_191194_a(getMotionVec(entity, 3)));
/* 318 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 321 */     double v = (1.0D - distancedsize) * blockDensity;
/* 322 */     float damage = (int)((v * v + v) / 2.0D * 7.0D * doubleExplosionSize + 1.0D);
/* 323 */     double finald = 1.0D;
/* 324 */     if (entity instanceof EntityLivingBase) {
/* 325 */       finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)mc.field_71441_e, (Entity)mc.field_71439_g, posX, posY, posZ, 6.0F, false, true));
/*     */     }
/* 327 */     return (float)finald;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
/* 332 */     return calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, entity);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\CrystalUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */