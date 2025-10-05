/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IItemTool;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.CombatRules;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DamageUtil
/*     */ {
/*     */   public static boolean canBreakWeakness(boolean checkStack) {
/*  32 */     if (!Util.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
/*  33 */       return true;
/*     */     }
/*     */     
/*  36 */     int strengthAmp = 0;
/*     */     
/*  38 */     PotionEffect effect = Util.mc.field_71439_g.func_70660_b(MobEffects.field_76420_g);
/*     */     
/*  40 */     if (effect != null) {
/*  41 */       strengthAmp = effect.func_76458_c();
/*     */     }
/*     */     
/*  44 */     if (strengthAmp >= 1) {
/*  45 */       return true;
/*     */     }
/*     */     
/*  48 */     return (checkStack && canBreakWeakness(Util.mc.field_71439_g.func_184614_ca()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canBreakWeakness(ItemStack stack) {
/*  53 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemSword) {
/*  54 */       return true;
/*     */     }
/*     */     
/*  57 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemTool) {
/*     */       
/*  59 */       IItemTool tool = (IItemTool)stack.func_77973_b();
/*  60 */       return (tool.getAttackDamage() > 4.0F);
/*     */     } 
/*     */     
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public static int findAntiWeakness() {
/*  67 */     int slot = -1;
/*  68 */     for (int i = 8; i > -1; i--) {
/*  69 */       if (canBreakWeakness(Util.mc.field_71439_g.field_71071_by
/*  70 */           .func_70301_a(i))) {
/*  71 */         slot = i;
/*  72 */         if (Util.mc.field_71439_g.field_71071_by.field_70461_c == i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     return slot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDamage(ItemStack stack) {
/*  88 */     return stack.func_77958_k() - stack.func_77952_i();
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
/*     */   public static float calculate(double x, double y, double z, AxisAlignedBB bb, EntityLivingBase base) {
/* 103 */     return calculate(x, y, z, bb, base, false);
/*     */   }
/*     */   
/*     */   public static float calculate(double x, double y, double z, AxisAlignedBB bb, EntityLivingBase base, boolean terrainCalc) {
/* 107 */     return calculate(x, y, z, bb, base, (IBlockAccess)Util.mc.field_71441_e, terrainCalc);
/*     */   }
/*     */   
/*     */   public static float calculate(double x, double y, double z, AxisAlignedBB bb, EntityLivingBase base, IBlockAccess world, boolean terrainCalc) {
/* 111 */     return calculate(x, y, z, bb, base, world, terrainCalc, false);
/*     */   }
/*     */   
/*     */   public static float calculate(double x, double y, double z, AxisAlignedBB bb, EntityLivingBase base, IBlockAccess world, boolean terrainCalc, boolean anvils) {
/* 115 */     return calculate(x, y, z, bb, base, world, terrainCalc, anvils, 6.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float calculate(double x, double y, double z, AxisAlignedBB bb, EntityLivingBase base, IBlockAccess world, boolean terrainCalc, boolean anvils, float power) {
/* 120 */     float f = 12.0F;
/* 121 */     double d5 = base.func_70011_f(x, y, z) / 12.0D;
/* 122 */     if (d5 > 1.0D) {
/* 123 */       return 0.0F;
/*     */     }
/* 125 */     double d6 = base.field_70170_p.func_72842_a(new Vec3d(x, y, z), base.func_174813_aQ());
/* 126 */     d5 = (1.0D - d5) * d6;
/* 127 */     f = (int)((d5 * d5 + d5) / 2.0D * 7.0D * 12.0D + 1.0D);
/* 128 */     f = getDifDamage(f);
/* 129 */     DamageSource dmsrc = DamageSource.func_94539_a(new Explosion((World)Util.mc.field_71441_e, (Entity)Util.mc.field_71439_g, x, y, z, 6.0F, false, true));
/* 130 */     f = CombatRules.func_189427_a(f, base.func_70658_aO(), (float)base.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/* 131 */     int n = EnchantmentHelper.func_77508_a(base.func_184193_aE(), dmsrc);
/* 132 */     if (n > 0) {
/* 133 */       f = CombatRules.func_188401_b(f, n);
/*     */     }
/* 135 */     if (base.func_70660_b(MobEffects.field_76429_m) != null) {
/* 136 */       f = f * (25 - (base.func_70660_b(MobEffects.field_76429_m).func_76458_c() + 1) * 5) / 25.0F;
/*     */     }
/* 138 */     f = Math.max(f, 0.0F);
/* 139 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getDifDamage(float f) {
/* 144 */     EnumDifficulty enumDifficulty = Util.mc.field_71441_e.func_175659_aa();
/* 145 */     if (enumDifficulty == EnumDifficulty.PEACEFUL) {
/* 146 */       f = 0.0F;
/* 147 */       return 0.0F;
/*     */     } 
/* 149 */     if (enumDifficulty == EnumDifficulty.EASY) {
/* 150 */       f = Math.min(f / 2.0F + 1.0F, f);
/* 151 */       return f;
/*     */     } 
/* 153 */     if (enumDifficulty == EnumDifficulty.HARD) {
/* 154 */       f = f * 3.0F / 2.0F;
/*     */     }
/* 156 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getBlockDensity(Vec3d vec, AxisAlignedBB bb, IBlockAccess world, boolean ignoreWebs, boolean ignoreBeds, boolean terrainCalc, boolean anvils) {
/* 166 */     double x = 1.0D / ((bb.field_72336_d - bb.field_72340_a) * 2.0D + 1.0D);
/* 167 */     double y = 1.0D / ((bb.field_72337_e - bb.field_72338_b) * 2.0D + 1.0D);
/* 168 */     double z = 1.0D / ((bb.field_72334_f - bb.field_72339_c) * 2.0D + 1.0D);
/* 169 */     double xFloor = (1.0D - Math.floor(1.0D / x) * x) / 2.0D;
/* 170 */     double zFloor = (1.0D - Math.floor(1.0D / z) * z) / 2.0D;
/*     */     
/* 172 */     if (x >= 0.0D && y >= 0.0D && z >= 0.0D) {
/* 173 */       int air = 0;
/* 174 */       int traced = 0;
/*     */       float a;
/* 176 */       for (a = 0.0F; a <= 1.0F; a = (float)(a + x)) {
/* 177 */         float b; for (b = 0.0F; b <= 1.0F; b = (float)(b + y)) {
/* 178 */           float c; for (c = 0.0F; c <= 1.0F; c = (float)(c + z)) {
/* 179 */             double xOff = bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * a;
/* 180 */             double yOff = bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * b;
/* 181 */             double zOff = bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * c;
/*     */             
/* 183 */             RayTraceResult result = rayTraceBlocks(new Vec3d(xOff + xFloor, yOff, zOff + zFloor), vec, world, false, false, false, ignoreWebs, ignoreBeds, terrainCalc, anvils);
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
/* 195 */             if (result == null) {
/* 196 */               air++;
/*     */             }
/*     */             
/* 199 */             traced++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 204 */       return air / traced;
/*     */     } 
/* 206 */     return 0.0F;
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
/*     */   public static RayTraceResult rayTraceBlocks(Vec3d start, Vec3d end, IBlockAccess world, boolean stopOnLiquid, boolean ignoreNoBox, boolean lastUncollidableBlock, boolean ignoreWebs, boolean ignoreBeds, boolean terrainCalc, boolean anvils) {
/* 234 */     return RayTracer.trace((World)Util.mc.field_71441_e, world, start, end, stopOnLiquid, ignoreNoBox, lastUncollidableBlock, (b, p) -> 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 242 */         (((!terrainCalc || b.func_149638_a((Entity)Util.mc.field_71439_g) >= 100.0F || p.func_177954_c(end.field_72450_a, end.field_72448_b, end.field_72449_c) > 36.0D) && (!ignoreBeds || !(b instanceof net.minecraft.block.BlockBed)) && (!ignoreWebs || !(b instanceof net.minecraft.block.BlockWeb))) || (anvils && b instanceof net.minecraft.block.BlockAnvil)));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\DamageUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */