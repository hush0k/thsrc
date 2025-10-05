/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ public class HelperUtil
/*     */ {
/*     */   public static AutoCrystal.BreakValidity isValid(AutoCrystal module, Entity crystal) {
/*  20 */     return isValid(module, crystal, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AutoCrystal.BreakValidity isValid(AutoCrystal module, Entity crystal, boolean lastPos) {
/*  25 */     if (((Integer)module.existed.getValue()).intValue() != 0 && (
/*  26 */       System.currentTimeMillis() - ((IEntity)crystal)
/*  27 */       .getTimeStampT()) + (
/*  28 */       ((Boolean)module.pingExisted.getValue()).booleanValue() ? (Thunderhack.serverManager
/*  29 */       .getPing() / 2.0D) : 0.0D) < ((Integer)module.existed
/*     */       
/*  31 */       .getValue()).intValue()) {
/*  32 */       return AutoCrystal.BreakValidity.INVALID;
/*     */     }
/*     */     
/*  35 */     if ((lastPos && !module.rangeHelper.isCrystalInRangeOfLastPosition(crystal)) || (!lastPos && 
/*  36 */       !module.rangeHelper.isCrystalInRange(crystal))) {
/*  37 */       return AutoCrystal.BreakValidity.INVALID;
/*     */     }
/*     */     
/*  40 */     if (((lastPos && Thunderhack.positionManager.getDistanceSq(crystal) >= 
/*  41 */       MathUtil.square(((Float)module.breakTrace.getValue()).floatValue())) || (!lastPos && Util.mc.field_71439_g
/*  42 */       .func_70068_e(crystal) >= 
/*  43 */       MathUtil.square(((Float)module.breakTrace.getValue()).floatValue()))) && ((
/*  44 */       lastPos && !Thunderhack.positionManager.canEntityBeSeen(crystal)) || (!lastPos && 
/*  45 */       !RayTraceUtil.canBeSeen(new Vec3d(crystal.field_70165_t, crystal.field_70163_u + 1.7D, crystal.field_70161_v), (Entity)Util.mc.field_71439_g))))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  50 */       return AutoCrystal.BreakValidity.INVALID;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  55 */     if (module.noRotateNigga(AutoCrystal.ACRotate.Break) || module
/*  56 */       .isNotCheckingRotations() || (
/*  57 */       CalculationMotion.isLegit(crystal, new Entity[] { crystal }) && AutoCrystal.POSITION_HISTORY
/*     */       
/*  59 */       .arePreviousRotationsLegit(crystal, ((Integer)module.rotationTicks
/*     */         
/*  61 */         .getValue()).intValue(), true)))
/*     */     {
/*  63 */       return AutoCrystal.BreakValidity.VALID;
/*     */     }
/*     */     
/*  66 */     return AutoCrystal.BreakValidity.ROTATIONS;
/*     */   }
/*     */   
/*     */   public static void simulateExplosion(AutoCrystal module, double x, double y, double z) {
/*  70 */     List<Entity> entities = Util.mc.field_71441_e.field_72996_f;
/*  71 */     if (entities == null) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     for (Entity entity : entities) {
/*  76 */       if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && entity
/*  77 */         .func_70092_e(x, y, z) < 144.0D) {
/*  78 */         if (((Boolean)module.pseudoSetDead.getValue()).booleanValue()) {
/*  79 */           ((IEntity)entity).setPseudoDeadT(true); continue;
/*     */         } 
/*  81 */         Thunderhack.setDeadManager.setDead(entity);
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
/*     */ 
/*     */   
/*     */   public static boolean validChange(BlockPos pos, List<EntityPlayer> players) {
/*  96 */     for (EntityPlayer player : players) {
/*  97 */       if (player == null || player
/*  98 */         .equals(Util.mc.field_71439_g) || player.field_70128_L || Thunderhack.friendManager
/*     */         
/* 100 */         .isFriend(player)) {
/*     */         continue;
/*     */       }
/*     */       
/* 104 */       if (player.func_174831_c(pos) <= 4.0D && player.field_70163_u >= pos
/* 105 */         .func_177956_o()) {
/* 106 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean valid(Entity entity, double range, double trace) {
/* 114 */     EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/* 115 */     double d = entity.func_70068_e((Entity)entityPlayerSP);
/* 116 */     if (d >= MathUtil.square(range)) {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     if (d >= trace) {
/* 121 */       return RayTraceUtil.canBeSeen(entity, (EntityLivingBase)entityPlayerSP);
/*     */     }
/*     */     
/* 124 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */