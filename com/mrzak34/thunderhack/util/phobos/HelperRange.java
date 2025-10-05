/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class HelperRange {
/*     */   private final AutoCrystal module;
/*     */   
/*     */   public HelperRange(AutoCrystal module) {
/*  16 */     this.module = module;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCrystalInRange(Entity crystal) {
/*  21 */     return isCrystalInRange(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, 0);
/*     */   }
/*     */   
/*     */   public boolean isCrystalInRangeOfLastPosition(Entity crystal) {
/*  25 */     return isCrystalInRange(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, Thunderhack.positionManager
/*  26 */         .getX(), Thunderhack.positionManager
/*  27 */         .getY(), Thunderhack.positionManager
/*  28 */         .getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCrystalInRange(double crystalX, double crystalY, double crystalZ, int ticks) {
/*  33 */     if (((Boolean)this.module.smartBreakTrace.getValue()).booleanValue() && 
/*  34 */       isOutsideBreakTrace(crystalX, crystalY, crystalZ, ticks)) {
/*  35 */       return false;
/*     */     }
/*     */     
/*  38 */     if (((Boolean)this.module.ncpRange.getValue()).booleanValue()) {
/*  39 */       EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/*  40 */       double breakerX = ((Entity)entityPlayerSP).field_70165_t + ((Entity)entityPlayerSP).field_70159_w * ticks;
/*  41 */       double breakerY = ((Entity)entityPlayerSP).field_70163_u + ((Entity)entityPlayerSP).field_70181_x * ticks;
/*  42 */       double breakerZ = ((Entity)entityPlayerSP).field_70161_v + ((Entity)entityPlayerSP).field_70179_y * ticks;
/*  43 */       return SmartRangeUtil.isInStrictBreakRange(crystalX, crystalY, crystalZ, 
/*     */           
/*  45 */           MathUtil.square(((Float)this.module.breakRange.getValue()).floatValue()), breakerX, breakerY, breakerZ);
/*     */     } 
/*     */ 
/*     */     
/*  49 */     return SmartRangeUtil.isInSmartRange(crystalX, crystalY, crystalZ, (Entity)Util.mc.field_71439_g, 
/*     */         
/*  51 */         MathUtil.square(((Float)this.module.breakRange.getValue()).floatValue()), ticks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCrystalInRange(double crystalX, double crystalY, double crystalZ, double breakerX, double breakerY, double breakerZ) {
/*  57 */     if (((Boolean)this.module.smartBreakTrace.getValue()).booleanValue() && 
/*  58 */       !isCrystalInBreakTrace(crystalX, crystalY, crystalZ, breakerX, breakerY, breakerZ))
/*     */     {
/*  60 */       return false;
/*     */     }
/*     */     
/*  63 */     if (((Boolean)this.module.ncpRange.getValue()).booleanValue()) {
/*  64 */       return SmartRangeUtil.isInStrictBreakRange(crystalX, crystalY, crystalZ, 
/*     */           
/*  66 */           MathUtil.square(((Float)this.module.breakRange.getValue()).floatValue()), breakerX, breakerY, breakerZ);
/*     */     }
/*     */ 
/*     */     
/*  70 */     return 
/*     */       
/*  72 */       (distanceSq(crystalX, crystalY, crystalZ, breakerX, breakerY, breakerZ) < MathUtil.square(((Float)this.module.breakRange.getValue()).floatValue()));
/*     */   }
/*     */   
/*     */   public static double distanceSq(double x, double y, double z, Entity entity) {
/*  76 */     return distanceSq(x, y, z, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public static double distanceSq(double x, double y, double z, double x1, double y1, double z1) {
/*  80 */     double xDist = x - x1;
/*  81 */     double yDist = y - y1;
/*  82 */     double zDist = z - z1;
/*  83 */     return xDist * xDist + yDist * yDist + zDist * zDist;
/*     */   }
/*     */   
/*     */   public boolean isCrystalOutsideNegativeRange(BlockPos pos) {
/*  87 */     int negativeTicks = ((Integer)this.module.negativeTicks.getValue()).intValue();
/*  88 */     if (negativeTicks == 0) {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     if (((Boolean)this.module.negativeBreakTrace.getValue()).booleanValue() && 
/*  93 */       isOutsideBreakTrace(pos, negativeTicks)) {
/*  94 */       return true;
/*     */     }
/*     */     
/*  97 */     if (((Boolean)this.module.ncpRange.getValue()).booleanValue()) {
/*  98 */       EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/*  99 */       double breakerX = ((Entity)entityPlayerSP).field_70165_t + ((Entity)entityPlayerSP).field_70159_w * negativeTicks;
/* 100 */       double breakerY = ((Entity)entityPlayerSP).field_70163_u + ((Entity)entityPlayerSP).field_70181_x * negativeTicks;
/* 101 */       double breakerZ = ((Entity)entityPlayerSP).field_70161_v + ((Entity)entityPlayerSP).field_70179_y * negativeTicks;
/* 102 */       return !SmartRangeUtil.isInStrictBreakRange((pos
/* 103 */           .func_177958_n() + 0.5F), (pos.func_177956_o() + 1), pos.func_177952_p() + 0.5D, 
/* 104 */           MathUtil.square(((Float)this.module.breakRange.getValue()).floatValue()), breakerX, breakerY, breakerZ);
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return !SmartRangeUtil.isInSmartRange(pos, (Entity)Util.mc.field_71439_g, 
/*     */         
/* 110 */         MathUtil.square(((Float)this.module.breakRange.getValue()).floatValue()), negativeTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutsideBreakTrace(BlockPos pos, int ticks) {
/* 115 */     return isOutsideBreakTrace((pos
/* 116 */         .func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F), ticks);
/*     */   }
/*     */   
/*     */   public boolean isOutsideBreakTrace(double x, double y, double z, int ticks) {
/* 120 */     EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/* 121 */     double breakerX = ((Entity)entityPlayerSP).field_70165_t + ((Entity)entityPlayerSP).field_70159_w * ticks;
/* 122 */     double breakerY = ((Entity)entityPlayerSP).field_70163_u + ((Entity)entityPlayerSP).field_70181_x * ticks;
/* 123 */     double breakerZ = ((Entity)entityPlayerSP).field_70161_v + ((Entity)entityPlayerSP).field_70179_y * ticks;
/* 124 */     return !isCrystalInBreakTrace(x, y, z, breakerX, breakerY, breakerZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCrystalInBreakTrace(double crystalX, double crystalY, double crystalZ, double breakerX, double breakerY, double breakerZ) {
/* 131 */     if (distanceSq(crystalX, crystalY, crystalZ, breakerX, breakerY, breakerZ) >= 
/*     */       
/* 133 */       MathUtil.square(((Float)this.module.breakTrace.getValue()).floatValue())) { if (Util.mc.field_71441_e
/* 134 */         .func_147447_a(new Vec3d(breakerX, breakerY + Util.mc.field_71439_g
/*     */             
/* 136 */             .func_70047_e(), breakerZ), new Vec3d(crystalX, crystalY + 1.7D, crystalZ), false, true, false) == null);
/*     */       return false; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */