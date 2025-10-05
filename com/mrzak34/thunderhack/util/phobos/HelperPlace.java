/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ public class HelperPlace
/*     */ {
/*     */   private final AutoCrystal module;
/*     */   
/*     */   public HelperPlace(AutoCrystal module) {
/*  27 */     this.module = module;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlaceData getData(List<EntityPlayer> general, List<EntityPlayer> players, List<EntityPlayer> enemies, List<EntityPlayer> friends, List<Entity> entities, float minDamage, Set<BlockPos> blackList, double maxY) {
/*  38 */     PlaceData data = new PlaceData(minDamage);
/*  39 */     EntityPlayer target = this.module.isSuicideModule() ? (EntityPlayer)Util.mc.field_71439_g : this.module.getTTRG(players, enemies, (Float)this.module.targetRange.getValue());
/*     */     
/*  41 */     if (target == null && this.module.targetMode.getValue() != AutoCrystal.Target.Damage) {
/*  42 */       return data;
/*     */     }
/*     */     
/*  45 */     data.setTarget(target);
/*  46 */     evaluate(data, general, friends, entities, blackList, maxY);
/*  47 */     data.addAllCorrespondingData();
/*  48 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void evaluate(PlaceData data, List<EntityPlayer> players, List<EntityPlayer> friends, List<Entity> entities, Set<BlockPos> blackList, double maxY) {
/*  56 */     boolean obby = (((Boolean)this.module.obsidian.getValue()).booleanValue() && this.module.obbyTimer.passedMs(((Integer)this.module.obbyDelay.getValue()).intValue()) && (InventoryUtil.isHolding(Blocks.field_150343_Z) || (((Boolean)this.module.obbySwitch.getValue()).booleanValue() && InventoryUtil.findHotbarBlock(Blocks.field_150343_Z) != -1)));
/*     */     
/*  58 */     switch ((AutoCrystal.PreCalc)this.module.preCalc.getValue()) {
/*     */       case Place:
/*  60 */         for (EntityPlayer player : players) {
/*  61 */           preCalc(data, player, obby, entities, friends, blackList);
/*     */         }
/*     */       case Break:
/*  64 */         if (data.getTarget() == null) {
/*  65 */           if (data.getData().isEmpty()) {
/*     */             break;
/*     */           }
/*     */         } else {
/*  69 */           preCalc(data, data.getTarget(), obby, entities, friends, blackList);
/*     */         } 
/*     */ 
/*     */         
/*  73 */         for (PositionData positionData : data.getData()) {
/*  74 */           if (positionData.getMaxDamage() > data
/*  75 */             .getMinDamage() && positionData
/*  76 */             .getMaxDamage() > ((Float)this.module.preCalcDamage
/*  77 */             .getValue()).floatValue()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  86 */     BlockPos middle = Util.mc.field_71439_g.func_180425_c();
/*     */     
/*  88 */     int maxRadius = Sphere.getRadius(((Float)this.module.placeRange.getValue()).floatValue());
/*  89 */     for (int i = 1; i < maxRadius; i++) {
/*  90 */       calc(middle.func_177971_a(Sphere.get(i)), data, players, friends, entities, obby, blackList, maxY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void preCalc(PlaceData data, EntityPlayer player, boolean obby, List<Entity> entities, List<EntityPlayer> friends, Set<BlockPos> blackList) {
/*     */     MotionTracker extrapolationEntity;
/* 101 */     switch ((AutoCrystal.ExtrapolationType)this.module.preCalcExtra.getValue()) {
/*     */ 
/*     */       
/*     */       case Place:
/* 105 */         extrapolationEntity = (((Integer)this.module.extrapol.getValue()).intValue() == 0) ? null : this.module.extrapolationHelper.getTrackerFromEntity((Entity)player);
/*     */         break;
/*     */ 
/*     */       
/*     */       case Break:
/* 110 */         extrapolationEntity = (((Integer)this.module.bExtrapol.getValue()).intValue() == 0) ? null : this.module.extrapolationHelper.getBreakTrackerFromEntity((Entity)player);
/*     */         break;
/*     */ 
/*     */       
/*     */       case Block:
/* 115 */         extrapolationEntity = (((Integer)this.module.blockExtrapol.getValue()).intValue() == 0) ? null : this.module.extrapolationHelper.getBlockTracker((Entity)player);
/*     */         break;
/*     */       default:
/* 118 */         extrapolationEntity = null;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     BlockPos pos = (extrapolationEntity == null || !extrapolationEntity.active) ? player.func_180425_c().func_177977_b() : extrapolationEntity.func_180425_c().func_177977_b();
/*     */     
/* 127 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 128 */       PositionData pData = selfCalc(data, pos.func_177972_a(facing), entities, friends, obby, blackList);
/*     */       
/* 130 */       if (pData != null)
/*     */       {
/*     */ 
/*     */         
/* 134 */         checkPlayer(data, player, pData);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PositionData selfCalc(PlaceData placeData, BlockPos pos, List<Entity> entities, List<EntityPlayer> friends, boolean obby, Set<BlockPos> blackList) {
/* 144 */     if (blackList.contains(pos)) {
/* 145 */       return null;
/*     */     }
/*     */     
/* 148 */     PositionData data = PositionData.create(pos, obby, (this.module.rotate
/*     */ 
/*     */         
/* 151 */         .getValue() != AutoCrystal.ACRotate.None && this.module.rotate
/* 152 */         .getValue() != AutoCrystal.ACRotate.Break) ? 0 : ((Integer)this.module.helpingBlocks
/*     */         
/* 154 */         .getValue()).intValue(), ((Boolean)this.module.newVer
/* 155 */         .getValue()).booleanValue(), ((Boolean)this.module.newVerEntities
/* 156 */         .getValue()).booleanValue(), this.module
/* 157 */         .getDeathTime(), entities, ((Boolean)this.module.lava
/*     */         
/* 159 */         .getValue()).booleanValue(), ((Boolean)this.module.water
/* 160 */         .getValue()).booleanValue(), ((Boolean)this.module.ignoreLavaItems
/* 161 */         .getValue()).booleanValue(), this.module);
/*     */     
/* 163 */     if (data.isBlocked() && !((Boolean)this.module.fallBack.getValue()).booleanValue()) {
/* 164 */       return null;
/*     */     }
/*     */     
/* 167 */     if (data.isLiquid()) {
/* 168 */       if (!data.isLiquidValid() || (((Boolean)this.module.liquidRayTrace
/*     */ 
/*     */         
/* 171 */         .getValue()).booleanValue() && ((((Boolean)this.module.newVer
/* 172 */         .getValue()).booleanValue() && data
/* 173 */         .getPos().func_177956_o() >= Util.mc.field_71439_g.field_70163_u + 2.0D) || (
/*     */         
/* 175 */         !((Boolean)this.module.newVer.getValue()).booleanValue() && data
/* 176 */         .getPos().func_177956_o() >= Util.mc.field_71439_g.field_70163_u + 1.0D))) || 
/*     */         
/* 178 */         BlockUtils.getDistanceSq(pos.func_177984_a()) >= 
/* 179 */         MathUtil.square(((Float)this.module.placeRange.getValue()).floatValue()) || 
/* 180 */         BlockUtils.getDistanceSq(pos.func_177981_b(2)) >= 
/* 181 */         MathUtil.square(((Float)this.module.placeRange.getValue()).floatValue())) {
/* 182 */         return null;
/*     */       }
/*     */       
/* 185 */       if (data.usesObby()) {
/* 186 */         if (data.isObbyValid()) {
/* 187 */           placeData.getLiquidObby().put(data.getPos(), data);
/*     */         }
/*     */         
/* 190 */         return null;
/*     */       } 
/*     */       
/* 193 */       placeData.getLiquid().add(data);
/* 194 */       return null;
/* 195 */     }  if (data.usesObby()) {
/* 196 */       if (data.isObbyValid()) {
/* 197 */         placeData.getAllObbyData().put(data.getPos(), data);
/*     */       }
/*     */       
/* 200 */       return null;
/*     */     } 
/*     */     
/* 203 */     if (!data.isValid()) {
/* 204 */       return null;
/*     */     }
/*     */     
/* 207 */     return validate(placeData, data, friends);
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionData validate(PlaceData placeData, PositionData data, List<EntityPlayer> friends) {
/* 212 */     if (BlockUtils.getDistanceSq(data.getPos()) >= 
/* 213 */       MathUtil.square(((Float)this.module.placeTrace.getValue()).floatValue()) && 
/* 214 */       noPlaceTrace(data.getPos())) {
/* 215 */       if (((Boolean)this.module.rayTraceBypass.getValue()).booleanValue() && ((Boolean)this.module.forceBypass
/* 216 */         .getValue()).booleanValue() && 
/* 217 */         !data.isLiquid() && 
/* 218 */         !data.usesObby()) {
/* 219 */         data.setRaytraceBypass(true);
/*     */       } else {
/* 221 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 225 */     float selfDamage = this.module.damageHelper.getDamage(data.getPos());
/* 226 */     if (selfDamage > placeData.getHighestSelfDamage()) {
/* 227 */       placeData.setHighestSelfDamage(selfDamage);
/*     */     }
/*     */     
/* 230 */     if (selfDamage > Util.mc.field_71439_g.func_110143_aJ() - 1.0D)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       if (!((Boolean)this.module.suicide.getValue()).booleanValue()) {
/* 237 */         return null;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (selfDamage > ((Float)this.module.maxSelfPlace.getValue()).floatValue() && 
/* 248 */       !((Boolean)this.module.override.getValue()).booleanValue()) {
/* 249 */       return null;
/*     */     }
/*     */     
/* 252 */     if (checkFriends(data, friends)) {
/* 253 */       return null;
/*     */     }
/*     */     
/* 256 */     data.setSelfDamage(selfDamage);
/* 257 */     return data;
/*     */   }
/*     */   
/*     */   private boolean noPlaceTrace(BlockPos pos) {
/* 261 */     if (this.module.isNotCheckingRotations() || (((Boolean)this.module.rayTraceBypass.getValue()).booleanValue() && !Visible.INSTANCE.check(pos, ((Integer)this.module.bypassTicks.getValue()).intValue()))) {
/* 262 */       return false;
/*     */     }
/*     */     
/* 265 */     if (((Boolean)this.module.smartTrace.getValue()).booleanValue()) {
/* 266 */       for (EnumFacing facing : EnumFacing.values()) {
/* 267 */         Ray ray = RayTraceFactory.rayTrace((Entity)Util.mc.field_71439_g, pos, facing, (IBlockAccess)Util.mc.field_71441_e, Blocks.field_150343_Z
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 272 */             .func_176223_P(), ((Double)this.module.traceWidth
/* 273 */             .getValue()).doubleValue());
/* 274 */         if (ray.isLegit()) {
/* 275 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 279 */       return true;
/*     */     } 
/*     */     
/* 282 */     if (((Boolean)this.module.ignoreNonFull.getValue()).booleanValue()) {
/* 283 */       for (EnumFacing facing : EnumFacing.values()) {
/* 284 */         Ray ray = RayTraceFactory.rayTrace((Entity)Util.mc.field_71439_g, pos, facing, (IBlockAccess)Util.mc.field_71441_e, Blocks.field_150343_Z
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 289 */             .func_176223_P(), ((Double)this.module.traceWidth
/* 290 */             .getValue()).doubleValue());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 295 */         if (!Util.mc.field_71441_e.func_180495_p(ray.getResult().func_178782_a()).func_177230_c().func_149730_j(Util.mc.field_71441_e.func_180495_p(ray
/* 296 */               .getResult().func_178782_a()))) {
/* 297 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 302 */     return !RayTraceUtil.raytracePlaceCheck((Entity)Util.mc.field_71439_g, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calc(BlockPos pos, PlaceData data, List<EntityPlayer> players, List<EntityPlayer> friends, List<Entity> entities, boolean obby, Set<BlockPos> blackList, double maxY) {
/* 313 */     if (placeCheck(pos, maxY) || (data
/* 314 */       .getTarget() != null && data
/* 315 */       .getTarget().func_174818_b(pos) > 
/* 316 */       MathUtil.square(((Float)this.module.range.getValue()).floatValue()))) {
/*     */       return;
/*     */     }
/*     */     
/* 320 */     PositionData positionData = selfCalc(data, pos, entities, friends, obby, blackList);
/*     */ 
/*     */     
/* 323 */     if (positionData == null) {
/*     */       return;
/*     */     }
/*     */     
/* 327 */     calcPositionData(data, positionData, players);
/*     */   }
/*     */ 
/*     */   
/*     */   public void calcPositionData(PlaceData data, PositionData positionData, List<EntityPlayer> players) {
/* 332 */     boolean isAntiTotem = false;
/* 333 */     if (data.getTarget() == null) {
/* 334 */       for (EntityPlayer player : players) {
/* 335 */         isAntiTotem = (checkPlayer(data, player, positionData) || isAntiTotem);
/*     */       }
/*     */     } else {
/*     */       
/* 339 */       isAntiTotem = checkPlayer(data, data.getTarget(), positionData);
/*     */     } 
/*     */     
/* 342 */     if (positionData.isRaytraceBypass() && ((((Boolean)this.module.rayBypassFacePlace
/* 343 */       .getValue()).booleanValue() && positionData
/* 344 */       .getFacePlacer() != null) || positionData
/* 345 */       .getMaxDamage() > data.getMinDamage())) {
/* 346 */       data.getRaytraceData().add(positionData);
/*     */       
/*     */       return;
/*     */     } 
/* 350 */     if (positionData.isForce()) {
/* 351 */       ForcePosition forcePosition = new ForcePosition(positionData, this.module);
/* 352 */       for (EntityPlayer forced : positionData.getForced()) {
/* 353 */         data.addForceData(forced, forcePosition);
/*     */       }
/*     */     } 
/*     */     
/* 357 */     if (isAntiTotem) {
/* 358 */       data.addAntiTotem(new AntiTotemData(positionData, this.module));
/*     */     }
/*     */     
/* 361 */     if (positionData.getFacePlacer() != null || positionData.getMaxDamage() > data.getMinDamage()) {
/* 362 */       data.getData().add(positionData);
/* 363 */     } else if (((Boolean)this.module.shield.getValue()).booleanValue() && 
/* 364 */       !positionData.usesObby() && 
/* 365 */       !positionData.isLiquid() && positionData
/* 366 */       .isValid() && positionData
/* 367 */       .getSelfDamage() <= ((Float)this.module.shieldSelfDamage
/* 368 */       .getValue()).floatValue()) {
/* 369 */       if (((Boolean)this.module.shieldPrioritizeHealth.getValue()).booleanValue()) {
/* 370 */         positionData.setDamage(0.0F);
/*     */       }
/*     */       
/* 373 */       positionData.setTarget(data.getShieldPlayer());
/* 374 */       data.getShieldData().add(positionData);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean placeCheck(BlockPos pos, double maxY) {
/* 379 */     if (pos.func_177956_o() < 0 || (pos
/* 380 */       .func_177956_o() - 1) >= maxY || 
/* 381 */       BlockUtils.getDistanceSq(pos) >= 
/* 382 */       MathUtil.square(((Float)this.module.placeRange.getValue()).floatValue())) {
/* 383 */       return true;
/*     */     }
/*     */     
/* 386 */     if (this.module.isOutsideBreakRange(pos, this.module) || this.module.rangeHelper
/* 387 */       .isCrystalOutsideNegativeRange(pos)) {
/* 388 */       return true;
/*     */     }
/*     */     
/* 391 */     if (distanceSq((pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F), (Entity)Util.mc.field_71439_g) > 
/* 392 */       MathUtil.square(((Float)this.module.pbTrace.getValue()).floatValue())) {
/* 393 */       return !RayTraceUtil.canBeSeen(new Vec3d((pos
/* 394 */             .func_177958_n() + 0.5F), (pos
/* 395 */             .func_177956_o() + 1) + 1.7D, (pos
/* 396 */             .func_177952_p() + 0.5F)), (Entity)Util.mc.field_71439_g);
/*     */     }
/*     */ 
/*     */     
/* 400 */     return false;
/*     */   }
/*     */   
/*     */   public static double distanceSq(double x, double y, double z, Entity entity) {
/* 404 */     return distanceSq(x, y, z, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public static double distanceSq(double x, double y, double z, double x1, double y1, double z1) {
/* 408 */     double xDist = x - x1;
/* 409 */     double yDist = y - y1;
/* 410 */     double zDist = z - z1;
/* 411 */     return xDist * xDist + yDist * yDist + zDist * zDist;
/*     */   }
/*     */   
/*     */   private boolean checkFriends(PositionData data, List<EntityPlayer> friends) {
/* 415 */     if (!this.module.shouldCalcFuckinBitch(AutoCrystal.AntiFriendPop.Place)) {
/* 416 */       return false;
/*     */     }
/*     */     
/* 419 */     for (EntityPlayer friend : friends) {
/* 420 */       if (friend != null && 
/* 421 */         !EntityUtil.isDead((Entity)friend) && this.module.damageHelper
/* 422 */         .getDamage(data.getPos(), (EntityLivingBase)friend) > friend
/* 423 */         .func_110143_aJ() - 0.5F) {
/* 424 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 428 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkPlayer(PlaceData data, EntityPlayer player, PositionData positionData) {
/* 434 */     BlockPos pos = positionData.getPos();
/* 435 */     if (data.getTarget() == null && player
/* 436 */       .func_174818_b(pos) > 
/* 437 */       MathUtil.square(((Float)this.module.range.getValue()).floatValue())) {
/* 438 */       return false;
/*     */     }
/*     */     
/* 441 */     boolean result = false;
/* 442 */     float health = player.func_110143_aJ();
/* 443 */     float damage = this.module.damageHelper.getDamage(pos, (EntityLivingBase)player);
/* 444 */     if (((Boolean)this.module.antiTotem.getValue()).booleanValue() && 
/* 445 */       !positionData.usesObby() && 
/* 446 */       !positionData.isLiquid() && 
/* 447 */       !positionData.isRaytraceBypass()) {
/* 448 */       if (this.module.antiTotemHelper.isDoublePoppable(player)) {
/* 449 */         if (damage > ((Float)this.module.popDamage.getValue()).floatValue()) {
/* 450 */           data.addCorrespondingData(player, positionData);
/* 451 */         } else if (damage < health + ((Float)this.module.maxTotemOffset.getValue()).floatValue() && damage > health + ((Float)this.module.minTotemOffset
/* 452 */           .getValue()).floatValue()) {
/* 453 */           positionData.addAntiTotem(player);
/* 454 */           result = true;
/*     */         } 
/* 456 */       } else if (((Boolean)this.module.forceAntiTotem.getValue()).booleanValue() && Thunderhack.combatManager
/* 457 */         .lastPop((Entity)player) > 500L) {
/* 458 */         if (damage > ((Float)this.module.popDamage.getValue()).floatValue()) {
/* 459 */           data.confirmHighDamageForce(player);
/*     */         }
/*     */         
/* 462 */         if (damage > 0.0F && damage < ((Float)this.module.totemHealth
/* 463 */           .getValue()).floatValue() + ((Float)this.module.maxTotemOffset
/* 464 */           .getValue()).floatValue()) {
/* 465 */           data.confirmPossibleAntiTotem(player);
/*     */         }
/*     */         
/* 468 */         float force = health - damage;
/* 469 */         if (force > 0.0F && force < ((Float)this.module.totemHealth.getValue()).floatValue()) {
/* 470 */           positionData.addForcePlayer(player);
/* 471 */           if (force < positionData.getMinDiff()) {
/* 472 */             positionData.setMinDiff(force);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 478 */     if (damage > ((Float)this.module.minFaceDmg.getValue()).floatValue() && (
/* 479 */       health < ((Float)this.module.facePlace.getValue()).floatValue() || ((IEntityLivingBase)player).getLowestDurability() <= ((Float)this.module.armorPlace.getValue()).floatValue())) {
/* 480 */       positionData.setFacePlacer(player);
/*     */     }
/*     */ 
/*     */     
/* 484 */     if (damage > positionData.getMaxDamage()) {
/* 485 */       positionData.setDamage(damage);
/* 486 */       positionData.setTarget(player);
/*     */     } 
/*     */     
/* 489 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */