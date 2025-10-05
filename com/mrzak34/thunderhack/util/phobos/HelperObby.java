/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class HelperObby {
/*  23 */   float MD = 4.0F; private final AutoCrystal module;
/*     */   
/*     */   public HelperObby(AutoCrystal module) {
/*  26 */     this.module = module;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PositionData findBestObbyData(Map<BlockPos, PositionData> obbyData, List<EntityPlayer> players, List<EntityPlayer> friends, List<Entity> entities, EntityPlayer target, boolean newVer) {
/*  35 */     double maxY = 0.0D;
/*  36 */     List<EntityPlayer> filteredPlayers = new LinkedList<>();
/*  37 */     for (EntityPlayer player : players) {
/*  38 */       if (player == null || 
/*  39 */         EntityUtil.isDead((Entity)player) || player.field_70163_u > Util.mc.field_71439_g.field_70163_u + 18.0D || player
/*     */         
/*  41 */         .func_70068_e((Entity)Util.mc.field_71439_g) > 
/*  42 */         MathUtil.square(((Float)this.module.targetRange.getValue()).floatValue())) {
/*     */         continue;
/*     */       }
/*     */       
/*  46 */       filteredPlayers.add(player);
/*  47 */       if (player.field_70163_u > maxY) {
/*  48 */         maxY = player.field_70163_u;
/*     */       }
/*     */     } 
/*     */     
/*  52 */     int fastObby = ((Integer)this.module.fastObby.getValue()).intValue();
/*  53 */     if (fastObby != 0) {
/*     */       Set<BlockPos> positions;
/*  55 */       if (target != null) {
/*  56 */         positions = new HashSet<>((int)((4 * fastObby) / 0.75D) + 1);
/*  57 */         addPositions(positions, target, fastObby);
/*     */       } else {
/*     */         
/*  60 */         positions = new HashSet<>((int)((filteredPlayers.size() * 4 * fastObby) / 0.75D + 1.0D));
/*     */         
/*  62 */         for (EntityPlayer player : filteredPlayers) {
/*  63 */           addPositions(positions, player, fastObby);
/*     */         }
/*     */       } 
/*     */       
/*  67 */       obbyData.keySet().retainAll(positions);
/*     */     } 
/*     */     
/*  70 */     int maxPath = ((Integer)this.module.helpingBlocks.getValue()).intValue();
/*  71 */     int shortest = maxPath;
/*  72 */     float maxDamage = 0.0F;
/*  73 */     float maxSelfDamage = 0.0F;
/*  74 */     PositionData bestData = null;
/*     */     
/*  76 */     for (PositionData positionData : obbyData.values()) {
/*  77 */       if (positionData.isBlocked()) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/*  82 */       BlockPos pos = positionData.getPos();
/*  83 */       if (pos.func_177956_o() >= maxY) {
/*     */         continue;
/*     */       }
/*     */       
/*  87 */       float self = Float.MAX_VALUE;
/*  88 */       boolean preSelf = ((Boolean)this.module.obbyPreSelf.getValue()).booleanValue();
/*  89 */       IBlockStateHelper helper = new BlockStateHelper(new HashMap<>());
/*  90 */       if (preSelf) {
/*  91 */         helper.addBlockState(pos, Blocks.field_150343_Z.func_176223_P());
/*  92 */         self = this.module.damageHelper.getObbyDamage(pos, helper);
/*  93 */         if (checkSelfDamage(self)) {
/*     */           continue;
/*     */         }
/*     */         
/*  97 */         positionData.setSelfDamage(self);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 102 */       BlockPos[] ignore = new BlockPos[newVer ? 1 : 2];
/* 103 */       ignore[0] = pos.func_177984_a();
/* 104 */       if (!newVer) {
/* 105 */         ignore[1] = pos.func_177981_b(2);
/*     */       }
/*     */       
/* 108 */       if (((Boolean)this.module.interact.getValue()).booleanValue()) {
/* 109 */         AutoCrystal.RayTraceMode mode = (AutoCrystal.RayTraceMode)this.module.obbyTrace.getValue();
/* 110 */         for (EnumFacing facing : EnumFacing.values()) {
/* 111 */           BlockPos offset = pos.func_177972_a(facing);
/* 112 */           if (BlockUtils.getDistanceSq(offset) < 
/* 113 */             MathUtil.square(((Float)this.module.placeRange.getValue()).floatValue())) {
/*     */ 
/*     */ 
/*     */             
/* 117 */             IBlockState state = Util.mc.field_71441_e.func_180495_p(offset);
/* 118 */             if (!state.func_185904_a().func_76222_j() || state
/* 119 */               .func_185904_a().func_76224_d()) {
/*     */ 
/*     */ 
/*     */               
/* 123 */               Ray ray = RayTraceFactory.rayTrace(positionData
/* 124 */                   .getFrom(), offset, facing
/*     */                   
/* 126 */                   .func_176734_d(), (IBlockAccess)Util.mc.field_71441_e, Blocks.field_150343_Z
/*     */                   
/* 128 */                   .func_176223_P(), (mode == AutoCrystal.RayTraceMode.Smart) ? -1.0D : 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 133 */               if (ray.isLegit() || mode != AutoCrystal.RayTraceMode.Smart) {
/*     */ 
/*     */ 
/*     */                 
/* 137 */                 if (((Boolean)this.module.inside.getValue()).booleanValue() && state
/* 138 */                   .func_185904_a().func_76224_d()) {
/* 139 */                   (ray.getResult())
/* 140 */                     .field_178784_b = (ray.getResult()).field_178784_b.func_176734_d();
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 145 */                   ray = new Ray(ray.getResult(), ray.getRotations(), ray.getPos().func_177972_a(ray.getFacing()), ray.getFacing().func_176734_d(), ray.getVector());
/*     */                 } 
/*     */                 
/* 148 */                 positionData.setValid(true);
/* 149 */                 positionData.setPath(new Ray[] { ray }); break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 154 */       }  if (!positionData.isValid()) {
/* 155 */         PathFinder.findPath(positionData, ((Float)this.module.placeRange
/*     */             
/* 157 */             .getValue()).floatValue(), entities, (AutoCrystal.RayTraceMode)this.module.obbyTrace
/*     */             
/* 159 */             .getValue(), helper, Blocks.field_150343_Z
/*     */             
/* 161 */             .func_176223_P(), PathFinder.CHECK, ignore);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 166 */       if (!positionData.isValid() || positionData
/* 167 */         .getPath() == null || (positionData
/* 168 */         .getPath()).length > maxPath) {
/*     */         continue;
/*     */       }
/*     */       
/* 172 */       for (Ray ray : positionData.getPath()) {
/* 173 */         helper.addBlockState(ray.getPos().func_177972_a(ray.getFacing()), Blocks.field_150343_Z
/* 174 */             .func_176223_P());
/*     */       }
/*     */       
/* 177 */       if (!preSelf) {
/*     */         
/* 179 */         self = this.module.damageHelper.getObbyDamage(pos, helper);
/* 180 */         if (checkSelfDamage(self)) {
/*     */           continue;
/*     */         }
/*     */         
/* 184 */         positionData.setSelfDamage(self);
/*     */       } 
/*     */       
/* 187 */       if (this.module.shouldCalcFuckinBitch(AutoCrystal.AntiFriendPop.Place)) {
/* 188 */         boolean poppingFriend = false;
/* 189 */         for (EntityPlayer friend : friends) {
/*     */           
/* 191 */           float f = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)friend, helper);
/* 192 */           if (f > EntityUtil.getHealth((Entity)friend)) {
/* 193 */             poppingFriend = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 198 */         if (poppingFriend) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 203 */       float damage = 0.0F;
/* 204 */       if (target != null) {
/* 205 */         positionData.setTarget(target);
/* 206 */         damage = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)target, helper);
/* 207 */         if (damage < ((Float)this.module.minDamage.getValue()).floatValue()) {
/*     */           continue;
/*     */         }
/*     */       } else {
/* 211 */         for (EntityPlayer p : filteredPlayers) {
/* 212 */           float d = this.module.damageHelper.getObbyDamage(pos, (EntityLivingBase)p, helper);
/* 213 */           if (d < ((Float)this.module.minDamage.getValue()).floatValue() || d < damage) {
/*     */             continue;
/*     */           }
/*     */           
/* 217 */           damage = d;
/* 218 */           positionData.setTarget(p);
/*     */         } 
/*     */       } 
/*     */       
/* 222 */       if (damage < ((Float)this.module.minDamage.getValue()).floatValue()) {
/*     */         continue;
/*     */       }
/*     */       
/* 226 */       positionData.setDamage(damage);
/* 227 */       int length = (positionData.getPath()).length;
/* 228 */       if (bestData == null) {
/* 229 */         bestData = positionData;
/* 230 */         maxDamage = damage;
/* 231 */         maxSelfDamage = self;
/* 232 */         shortest = length;
/*     */         
/*     */         continue;
/*     */       } 
/* 236 */       boolean betterLen = (length - ((Integer)this.module.maxDiff.getValue()).intValue() < shortest);
/*     */       
/* 238 */       boolean betterDmg = (damage + ((Double)this.module.maxDmgDiff.getValue()).doubleValue() > maxDamage && damage - ((Double)this.module.maxDmgDiff.getValue()).doubleValue() >= ((Float)this.module.minDamage.getValue()).floatValue());
/*     */       
/* 240 */       if ((betterLen && damage > maxDamage) || (betterDmg && length < shortest) || (betterDmg && length == shortest && self < maxSelfDamage)) {
/*     */ 
/*     */         
/* 243 */         bestData = positionData;
/* 244 */         if (length < shortest) {
/* 245 */           shortest = length;
/*     */         }
/*     */         
/* 248 */         if (damage > maxDamage) {
/* 249 */           maxDamage = damage;
/*     */         }
/*     */         
/* 252 */         if (self < maxSelfDamage) {
/* 253 */           maxSelfDamage = self;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     return bestData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPositions(Set<BlockPos> positions, EntityPlayer player, int fastObby) {
/* 264 */     BlockPos down = player.func_180425_c().func_177977_b();
/* 265 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 266 */       BlockPos offset = down;
/* 267 */       for (int i = 0; i < fastObby; i++) {
/* 268 */         offset = offset.func_177972_a(facing);
/* 269 */         positions.add(offset);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkSelfDamage(float self) {
/* 280 */     if (self > EntityUtil.getHealth((Entity)Util.mc.field_71439_g) - 1.0D) {
/* 281 */       if (((Boolean)this.module.obbySafety.getValue()).booleanValue());
/*     */ 
/*     */ 
/*     */       
/* 285 */       return true;
/*     */     } 
/*     */     
/* 288 */     return (self > ((Float)this.module.maxSelfPlace.getValue()).floatValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperObby.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */