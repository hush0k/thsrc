/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ public class HelperBreak
/*     */   extends AbstractBreakHelper<CrystalData>
/*     */ {
/*     */   public HelperBreak(AutoCrystal module) {
/*  19 */     super(module);
/*     */   }
/*     */ 
/*     */   
/*     */   public BreakData<CrystalData> newData(Collection<CrystalData> data) {
/*  24 */     return new BreakData<>(data);
/*     */   }
/*     */ 
/*     */   
/*     */   protected CrystalData newCrystalData(Entity crystal) {
/*  29 */     return new CrystalData(crystal);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValid(Entity crystal, CrystalData data) {
/*  34 */     double distance = Thunderhack.positionManager.getDistanceSq(crystal);
/*     */     
/*  36 */     if (distance > MathUtil.square(((Float)this.module.breakTrace.getValue()).floatValue()) && !Thunderhack.positionManager.canEntityBeSeen(crystal)) {
/*  37 */       return false;
/*     */     }
/*  39 */     return this.module.rangeHelper.isCrystalInRangeOfLastPosition(crystal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean calcSelf(BreakData<CrystalData> breakData, Entity crystal, CrystalData data) {
/*  46 */     float selfDamage = this.module.damageHelper.getDamage(crystal);
/*  47 */     data.setSelfDmg(selfDamage);
/*  48 */     if (selfDamage <= ((Float)this.module.shieldSelfDamage.getValue()).floatValue()) {
/*  49 */       breakData.setShieldCount(breakData.getShieldCount() + 1);
/*     */     }
/*     */     
/*  52 */     if (selfDamage > EntityUtil.getHealth((Entity)Util.mc.field_71439_g) - 1.0F)
/*     */     {
/*  54 */       if (!((Boolean)this.module.suicide.getValue()).booleanValue()) {
/*  55 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcCrystal(BreakData<CrystalData> data, CrystalData crystalData, Entity crystal, List<EntityPlayer> players) {
/*  75 */     boolean highSelf = (crystalData.getSelfDmg() > ((Float)this.module.maxSelfBreak.getValue()).floatValue());
/*     */     
/*  77 */     if (!((Boolean)this.module.suicide.getValue()).booleanValue() && 
/*  78 */       !((Boolean)this.module.overrideBreak.getValue()).booleanValue() && highSelf) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  83 */     float damage = 0.0F;
/*  84 */     boolean killing = false;
/*  85 */     for (EntityPlayer player : players) {
/*  86 */       if (player.func_70068_e(crystal) > 144.0D) {
/*     */         continue;
/*     */       }
/*     */       
/*  90 */       float playerDamage = this.module.damageHelper.getDamage(crystal, (EntityLivingBase)player);
/*  91 */       if (playerDamage > crystalData.getDamage()) {
/*  92 */         crystalData.setDamage(playerDamage);
/*     */       }
/*     */       
/*  95 */       if (playerDamage > EntityUtil.getHealth((Entity)player) + 1.0F) {
/*  96 */         killing = true;
/*  97 */         highSelf = false;
/*     */       } 
/*     */       
/* 100 */       if (playerDamage > damage) {
/* 101 */         damage = playerDamage;
/*     */       }
/*     */     } 
/*     */     
/* 105 */     if (((Boolean)this.module.antiTotem.getValue()).booleanValue() && 
/* 106 */       !EntityUtil.isDead(crystal) && crystal
/* 107 */       .func_180425_c()
/* 108 */       .func_177977_b()
/* 109 */       .equals(this.module.antiTotemHelper.getTargetPos())) {
/* 110 */       data.setAntiTotem(crystal);
/*     */     }
/*     */     
/* 113 */     if (!highSelf && (!((Boolean)this.module.efficient.getValue()).booleanValue() || damage > crystalData.getSelfDmg() || killing))
/* 114 */       data.register(crystalData); 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperBreak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */