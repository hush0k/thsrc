/*     */ package com.mrzak34.thunderhack.util.math;
/*     */ 
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IBlock;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.CombatRules;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class DamageUtil
/*     */   implements Util
/*     */ {
/*     */   public static int getItemDamage(ItemStack stack) {
/*  28 */     return stack.func_77958_k() - stack.func_77952_i();
/*     */   }
/*     */ 
/*     */   
/*     */   public static float calculateDamage(double posX, double posY, double posZ, Entity entity, boolean ignoreTerrain) {
/*  33 */     float finalDamage = 1.0F;
/*     */     try {
/*  35 */       float doubleExplosionSize = 12.0F;
/*  36 */       double distancedSize = entity.func_70011_f(posX, posY, posZ) / doubleExplosionSize;
/*     */ 
/*     */       
/*  39 */       double blockDensity = ignoreTerrain ? ignoreTerrainDecntiy(new Vec3d(posX, posY, posZ), entity.func_174813_aQ(), (World)mc.field_71441_e) : entity.field_70170_p.func_72842_a(new Vec3d(posX, posY, posZ), entity.func_174813_aQ());
/*  40 */       double v = (1.0D - distancedSize) * blockDensity;
/*  41 */       float damage = (int)((v * v + v) / 2.0D * 7.0D * doubleExplosionSize + 1.0D);
/*     */       
/*  43 */       if (entity instanceof EntityLivingBase) {
/*  44 */         finalDamage = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)mc.field_71441_e, null, posX, posY, posZ, 6.0F, false, true));
/*     */       }
/*  46 */     } catch (NullPointerException nullPointerException) {}
/*     */ 
/*     */     
/*  49 */     return finalDamage;
/*     */   }
/*     */   
/*     */   public static float ignoreTerrainDecntiy(Vec3d vec, AxisAlignedBB bb, World world) {
/*  53 */     double d0 = 1.0D / ((bb.field_72336_d - bb.field_72340_a) * 2.0D + 1.0D);
/*  54 */     double d1 = 1.0D / ((bb.field_72337_e - bb.field_72338_b) * 2.0D + 1.0D);
/*  55 */     double d2 = 1.0D / ((bb.field_72334_f - bb.field_72339_c) * 2.0D + 1.0D);
/*  56 */     double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
/*  57 */     double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
/*     */     
/*  59 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/*  60 */       int j2 = 0;
/*  61 */       int k2 = 0;
/*     */       float f;
/*  63 */       for (f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/*  64 */         float f1; for (f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/*  65 */           float f2; for (f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/*  66 */             double d5 = bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * f;
/*  67 */             double d6 = bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * f1;
/*  68 */             double d7 = bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * f2;
/*     */             
/*     */             RayTraceResult result;
/*  71 */             if ((result = world.func_72933_a(new Vec3d(d5 + d3, d6, d7 + d4), vec)) == null) {
/*  72 */               j2++;
/*     */             } else {
/*  74 */               Block blockHit = BlockUtils.getBlock(result.func_178782_a());
/*  75 */               if (((IBlock)blockHit).getBlockResistance() < 600.0F) {
/*  76 */                 j2++;
/*     */               }
/*     */             } 
/*  79 */             k2++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  84 */       return j2 / k2;
/*     */     } 
/*  86 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getBlastReduction(EntityLivingBase entity, float damageI, Explosion explosion) {
/*  93 */     float damage = damageI;
/*  94 */     if (entity instanceof EntityPlayer) {
/*  95 */       EntityPlayer ep = (EntityPlayer)entity;
/*  96 */       DamageSource ds = DamageSource.func_94539_a(explosion);
/*  97 */       damage = CombatRules.func_189427_a(damage, ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/*  98 */       int k = 0;
/*     */       try {
/* 100 */         k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
/* 101 */       } catch (Exception exception) {}
/*     */       
/* 103 */       float f = MathHelper.func_76131_a(k, 0.0F, 20.0F);
/* 104 */       damage *= 1.0F - f / 25.0F;
/* 105 */       if (entity.func_70644_a(MobEffects.field_76429_m)) {
/* 106 */         damage -= damage / 4.0F;
/*     */       }
/* 108 */       damage = Math.max(damage, 0.0F);
/* 109 */       return damage;
/*     */     } 
/* 111 */     damage = CombatRules.func_189427_a(damage, entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/* 112 */     return damage;
/*     */   }
/*     */   
/*     */   public static float getDamageMultiplied(float damage) {
/* 116 */     int diff = mc.field_71441_e.func_175659_aa().func_151525_a();
/* 117 */     return damage * ((diff == 0) ? 0.0F : ((diff == 2) ? 1.0F : ((diff == 1) ? 0.5F : 1.5F)));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\math\DamageUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */