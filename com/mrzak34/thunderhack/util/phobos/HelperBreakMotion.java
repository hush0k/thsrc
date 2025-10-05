/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ public class HelperBreakMotion
/*     */   extends AbstractBreakHelper<CrystalDataMotion>
/*     */ {
/*     */   public HelperBreakMotion(AutoCrystal module) {
/*  19 */     super(module);
/*     */   }
/*     */ 
/*     */   
/*     */   public BreakData<CrystalDataMotion> newData(Collection<CrystalDataMotion> data) {
/*  24 */     return new BreakData<>(data);
/*     */   }
/*     */ 
/*     */   
/*     */   protected CrystalDataMotion newCrystalData(Entity crystal) {
/*  29 */     return new CrystalDataMotion(crystal);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValid(Entity crystal, CrystalDataMotion data) {
/*  34 */     double distance = Thunderhack.positionManager.getDistanceSq(crystal);
/*  35 */     if (!this.module.rangeHelper.isCrystalInRangeOfLastPosition(crystal) || (distance >= MathUtil.square(((Float)this.module.breakTrace.getValue()).floatValue()) && !Thunderhack.positionManager.canEntityBeSeen(crystal))) {
/*  36 */       data.invalidateTiming(CrystalDataMotion.Timing.PRE);
/*     */     }
/*     */     
/*  39 */     EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/*  40 */     distance = entityPlayerSP.func_70068_e(crystal);
/*  41 */     if (!this.module.rangeHelper.isCrystalInRange(crystal) || (distance >= MathUtil.square(((Float)this.module.breakTrace.getValue()).floatValue()) && !entityPlayerSP.func_70685_l(crystal))) {
/*  42 */       data.invalidateTiming(CrystalDataMotion.Timing.POST);
/*     */     }
/*  44 */     return (data.getTiming() != CrystalDataMotion.Timing.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean calcSelf(BreakData<CrystalDataMotion> breakData, Entity crystal, CrystalDataMotion data) {
/*     */     float preDamage, postDamage;
/*  51 */     boolean breakCase = true;
/*  52 */     boolean incrementedCount = false;
/*  53 */     switch (data.getTiming()) {
/*     */       case BOTH:
/*  55 */         breakCase = false;
/*     */       case PRE:
/*  57 */         preDamage = this.module.damageHelper.getDamage(crystal);
/*  58 */         if (preDamage <= ((Float)this.module.shieldSelfDamage.getValue()).floatValue()) {
/*  59 */           breakData.setShieldCount(breakData.getShieldCount() + 1);
/*  60 */           incrementedCount = true;
/*     */         } 
/*     */         
/*  63 */         data.setSelfDmg(preDamage);
/*  64 */         if (preDamage > EntityUtil.getHealth((Entity)Util.mc.field_71439_g) - 1.0F && 
/*  65 */           !((Boolean)this.module.suicide.getValue()).booleanValue()) {
/*  66 */           data.invalidateTiming(CrystalDataMotion.Timing.PRE);
/*     */         }
/*     */ 
/*     */         
/*  70 */         if (breakCase) {
/*     */           break;
/*     */         }
/*     */       case POST:
/*  74 */         postDamage = this.module.damageHelper.getDamage(crystal, Util.mc.field_71439_g
/*     */             
/*  76 */             .func_174813_aQ());
/*  77 */         if (!incrementedCount && postDamage <= ((Float)this.module.shieldSelfDamage
/*  78 */           .getValue()).floatValue()) {
/*  79 */           breakData.setShieldCount(breakData.getShieldCount() + 1);
/*     */         }
/*     */         
/*  82 */         data.setPostSelf(postDamage);
/*  83 */         if (postDamage > EntityUtil.getHealth((Entity)Util.mc.field_71439_g) - 1.0F)
/*     */         {
/*  85 */           if (!((Boolean)this.module.suicide.getValue()).booleanValue()) {
/*  86 */             data.invalidateTiming(CrystalDataMotion.Timing.POST);
/*     */           }
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     return (data.getTiming() == CrystalDataMotion.Timing.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calcCrystal(BreakData<CrystalDataMotion> data, CrystalDataMotion crystalData, Entity crystal, List<EntityPlayer> players) {
/* 108 */     boolean highPreSelf = (crystalData.getSelfDmg() > ((Float)this.module.maxSelfBreak.getValue()).floatValue());
/*     */     
/* 110 */     boolean highPostSelf = (crystalData.getPostSelf() > ((Float)this.module.maxSelfBreak.getValue()).floatValue());
/*     */     
/* 112 */     if (!((Boolean)this.module.suicide.getValue()).booleanValue() && 
/* 113 */       !((Boolean)this.module.overrideBreak.getValue()).booleanValue() && highPreSelf && highPostSelf) {
/*     */ 
/*     */       
/* 116 */       crystalData.invalidateTiming(CrystalDataMotion.Timing.PRE);
/* 117 */       crystalData.invalidateTiming(CrystalDataMotion.Timing.POST);
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     float damage = 0.0F;
/* 122 */     boolean killing = false;
/* 123 */     for (EntityPlayer player : players) {
/* 124 */       if (player.func_70068_e(crystal) > 144.0D) {
/*     */         continue;
/*     */       }
/*     */       
/* 128 */       float playerDamage = this.module.damageHelper.getDamage(crystal, (EntityLivingBase)player);
/* 129 */       if (playerDamage > crystalData.getDamage()) {
/* 130 */         crystalData.setDamage(playerDamage);
/*     */       }
/*     */       
/* 133 */       if (playerDamage > EntityUtil.getHealth((Entity)player) + 1.0F) {
/* 134 */         highPreSelf = false;
/* 135 */         highPostSelf = false;
/* 136 */         killing = true;
/*     */       } 
/*     */       
/* 139 */       if (playerDamage > damage) {
/* 140 */         damage = playerDamage;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     if (((Boolean)this.module.antiTotem.getValue()).booleanValue() && 
/* 145 */       !EntityUtil.isDead(crystal) && crystal
/* 146 */       .func_180425_c()
/* 147 */       .func_177977_b()
/* 148 */       .equals(this.module.antiTotemHelper.getTargetPos())) {
/* 149 */       data.setAntiTotem(crystal);
/*     */     }
/*     */     
/* 152 */     if (highPreSelf) {
/* 153 */       crystalData.invalidateTiming(CrystalDataMotion.Timing.PRE);
/*     */     }
/*     */     
/* 156 */     if (highPostSelf) {
/* 157 */       crystalData.invalidateTiming(CrystalDataMotion.Timing.POST);
/*     */     }
/*     */     
/* 160 */     if ((crystalData.getTiming() != CrystalDataMotion.Timing.NONE && (!((Boolean)this.module.efficient.getValue()).booleanValue() || damage > crystalData.getSelfDmg())) || killing)
/* 161 */       data.register(crystalData); 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperBreakMotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */