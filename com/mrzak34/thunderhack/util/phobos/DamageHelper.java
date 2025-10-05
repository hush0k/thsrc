/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DamageHelper
/*     */ {
/*     */   private final Setting<Boolean> terrainCalc;
/*     */   private final Setting<Integer> bExtrapolation;
/*     */   private final Setting<Integer> pExtrapolation;
/*     */   private final Setting<Boolean> selfExtrapolation;
/*     */   private final Setting<Boolean> obbyTerrain;
/*     */   private final ExtrapolationHelper positionHelper;
/*     */   private final AutoCrystal module;
/*     */   
/*     */   public DamageHelper(AutoCrystal module, ExtrapolationHelper positionHelper, Setting<Boolean> terrainCalc, Setting<Integer> extrapolation, Setting<Integer> bExtrapolation, Setting<Boolean> selfExtrapolation, Setting<Boolean> obbyTerrain) {
/*  30 */     this.module = module;
/*  31 */     this.positionHelper = positionHelper;
/*  32 */     this.terrainCalc = terrainCalc;
/*  33 */     this.pExtrapolation = extrapolation;
/*  34 */     this.bExtrapolation = bExtrapolation;
/*  35 */     this.selfExtrapolation = selfExtrapolation;
/*  36 */     this.obbyTerrain = obbyTerrain;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDamage(Entity crystal) {
/*  41 */     if (this.module.isSuicideModule()) {
/*  42 */       return 0.0F;
/*     */     }
/*     */     
/*  45 */     return DamageUtil.calculate(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, Thunderhack.positionManager
/*     */ 
/*     */         
/*  48 */         .getBB(), (EntityLivingBase)Util.mc.field_71439_g);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDamage(Entity crystal, AxisAlignedBB bb) {
/*  53 */     if (this.module.isSuicideModule()) {
/*  54 */       return 0.0F;
/*     */     }
/*     */     
/*  57 */     return DamageUtil.calculate(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, bb, (EntityLivingBase)Util.mc.field_71439_g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamage(Entity crystal, EntityLivingBase base) {
/*  66 */     return getDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, base, (IBlockAccess)Util.mc.field_71441_e, ((Integer)this.bExtrapolation
/*  67 */         .getValue()).intValue(), ((Boolean)this.module.avgBreakExtra
/*  68 */         .getValue()).booleanValue(), false, false, ((Boolean)this.terrainCalc
/*  69 */         .getValue()).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDamage(BlockPos pos) {
/*  74 */     if (this.module.isSuicideModule()) {
/*  75 */       return 0.0F;
/*     */     }
/*     */     
/*  78 */     return getDamage(pos, (EntityLivingBase)Util.mc.field_71439_g, (IBlockAccess)Util.mc.field_71441_e, ((Integer)this.pExtrapolation
/*  79 */         .getValue()).intValue(), ((Boolean)this.module.avgPlaceDamage
/*  80 */         .getValue()).booleanValue(), true, ((Boolean)this.terrainCalc
/*  81 */         .getValue()).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDamage(BlockPos pos, EntityLivingBase base) {
/*  86 */     return getDamage(pos, base, (IBlockAccess)Util.mc.field_71441_e, ((Integer)this.pExtrapolation
/*  87 */         .getValue()).intValue(), ((Boolean)this.module.avgPlaceDamage
/*  88 */         .getValue()).booleanValue(), false, ((Boolean)this.terrainCalc
/*  89 */         .getValue()).booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getObbyDamage(BlockPos pos, IBlockAccess world) {
/*  95 */     if (this.module.isSuicideModule()) {
/*  96 */       return 0.0F;
/*     */     }
/*     */     
/*  99 */     return getDamage(pos, (EntityLivingBase)Util.mc.field_71439_g, world, ((Integer)this.pExtrapolation
/* 100 */         .getValue()).intValue(), ((Boolean)this.module.avgPlaceDamage
/* 101 */         .getValue()).booleanValue(), true, ((Boolean)this.obbyTerrain
/* 102 */         .getValue()).booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getObbyDamage(BlockPos pos, EntityLivingBase base, IBlockAccess world) {
/* 109 */     return getDamage(pos, base, world, ((Integer)this.pExtrapolation.getValue()).intValue(), ((Boolean)this.module.avgPlaceDamage
/* 110 */         .getValue()).booleanValue(), false, ((Boolean)this.obbyTerrain
/* 111 */         .getValue()).booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getDamage(BlockPos pos, EntityLivingBase base, IBlockAccess world, int ticks, boolean avg, boolean self, boolean terrain) {
/* 118 */     return getDamage((pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F), base, world, ticks, avg, self, true, terrain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getDamage(double x, double y, double z, EntityLivingBase base, IBlockAccess world, int ticks, boolean avg, boolean self, boolean place, boolean terrain) {
/* 127 */     if (ticks != 0 && (!self || ((Boolean)this.selfExtrapolation
/* 128 */       .getValue()).booleanValue())) {
/*     */       MotionTracker tracker;
/*     */       
/* 131 */       if ((tracker = (MotionTracker)(place ? this.positionHelper.getTrackerFromEntity((Entity)base) : this.positionHelper.getBreakTrackerFromEntity((Entity)base))) != null && tracker.active) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         float dmg = DamageUtil.calculate(x, y, z, tracker
/* 138 */             .func_174813_aQ(), base, world, terrain);
/*     */         
/* 140 */         if (avg) {
/*     */ 
/*     */           
/* 143 */           double extraWeight = place ? ((Double)this.module.placeExtraWeight.getValue()).doubleValue() : ((Double)this.module.breakExtraWeight.getValue()).doubleValue();
/*     */ 
/*     */           
/* 146 */           double normWeight = place ? ((Double)this.module.placeNormalWeight.getValue()).doubleValue() : ((Double)this.module.breakNormalWeight.getValue()).doubleValue();
/*     */           
/* 148 */           float normDmg = DamageUtil.calculate(x, y, z, base
/* 149 */               .func_174813_aQ(), base, world, terrain);
/*     */           
/* 151 */           return (float)((normDmg * normWeight + dmg * extraWeight) / 2.0D);
/*     */         } 
/*     */         
/* 154 */         return dmg;
/*     */       } 
/*     */     } 
/*     */     return DamageUtil.calculate(x, y, z, base.func_174813_aQ(), base, world, terrain);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\DamageHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */