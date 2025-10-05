/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PositionData
/*     */   extends BasePath
/*     */   implements Util, Comparable<PositionData>
/*     */ {
/*     */   private final AutoCrystal module;
/*  28 */   private final List<EntityPlayer> forced = new ArrayList<>();
/*     */   
/*     */   private final Set<EntityPlayer> antiTotems;
/*     */   
/*     */   private EntityPlayer target;
/*     */   
/*     */   private EntityPlayer facePlace;
/*     */   
/*     */   private IBlockState state;
/*     */   private float selfDamage;
/*     */   private float damage;
/*     */   private boolean obby;
/*     */   private boolean obbyValid;
/*     */   private boolean blocked;
/*     */   private boolean liquidValid;
/*     */   private boolean liquid;
/*     */   private float minDiff;
/*     */   private boolean raytraceBypass;
/*     */   
/*     */   public PositionData(BlockPos pos, int blocks, AutoCrystal module) {
/*  48 */     this(pos, blocks, module, new HashSet<>());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PositionData(BlockPos pos, int blocks, AutoCrystal module, Set<EntityPlayer> antiTotems) {
/*  54 */     super((Entity)mc.field_71439_g, pos, blocks);
/*  55 */     this.module = module;
/*  56 */     this.antiTotems = antiTotems;
/*  57 */     this.minDiff = Float.MAX_VALUE;
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
/*     */   public static PositionData create(BlockPos pos, boolean obby, int helpingBlocks, boolean newVer, boolean newVerEntities, int deathTime, List<Entity> entities, boolean lava, boolean water, boolean lavaItems, AutoCrystal module) {
/*  71 */     PositionData data = new PositionData(pos, helpingBlocks, module);
/*  72 */     data.state = mc.field_71441_e.func_180495_p(pos);
/*  73 */     if (data.state.func_177230_c() != Blocks.field_150357_h && data.state
/*  74 */       .func_177230_c() != Blocks.field_150343_Z) {
/*  75 */       if (!obby || 
/*  76 */         !data.state.func_185904_a().func_76222_j() || 
/*  77 */         checkEntities(data, pos, entities, 0, true, true, false)) {
/*  78 */         return data;
/*     */       }
/*     */       
/*  81 */       data.obby = true;
/*     */     } 
/*     */     
/*  84 */     BlockPos up = pos.func_177984_a();
/*  85 */     IBlockState upState = mc.field_71441_e.func_180495_p(up);
/*  86 */     if (upState.func_177230_c() != Blocks.field_150350_a) {
/*  87 */       if (checkLiquid(upState.func_177230_c(), water, lava)) {
/*  88 */         data.liquid = true;
/*     */       } else {
/*  90 */         return data;
/*     */       } 
/*     */     }
/*     */     
/*     */     IBlockState upUpState;
/*  95 */     if (!newVer && (
/*  96 */       upUpState = mc.field_71441_e.func_180495_p(up.func_177984_a()))
/*  97 */       .func_177230_c() != Blocks.field_150350_a) {
/*  98 */       if (checkLiquid(upUpState.func_177230_c(), water, lava)) {
/*  99 */         data.liquid = true;
/*     */       } else {
/* 101 */         return data;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 106 */     boolean checkLavaItems = (lavaItems && upState.func_185904_a() == Material.field_151587_i);
/* 107 */     if (checkEntities(data, up, entities, deathTime, false, false, checkLavaItems) || (!newVerEntities && 
/*     */       
/* 109 */       checkEntities(data, up.func_177984_a(), entities, deathTime, false, false, checkLavaItems)))
/*     */     {
/* 111 */       return data;
/*     */     }
/*     */     
/* 114 */     if (data.obby) {
/* 115 */       if (data.liquid) {
/* 116 */         data.liquidValid = true;
/*     */       }
/*     */       
/* 119 */       data.obbyValid = true;
/* 120 */       return data;
/*     */     } 
/*     */     
/* 123 */     if (data.liquid) {
/* 124 */       data.liquidValid = true;
/* 125 */       return data;
/*     */     } 
/*     */     
/* 128 */     data.setValid(true);
/* 129 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkEntities(PositionData data, BlockPos pos, List<Entity> entities, int deathTime, boolean dead, boolean spawning, boolean lavaItems) {
/* 139 */     AxisAlignedBB bb = new AxisAlignedBB(pos);
/* 140 */     for (Entity entity : entities) {
/* 141 */       if (entity == null || (spawning && !entity.field_70156_m) || (dead && 
/*     */         
/* 143 */         EntityUtil.isDead(entity)) || 
/* 144 */         !data.module.bbBlockingHelper.blocksBlock(bb, entity)) {
/*     */         continue;
/*     */       }
/*     */       
/* 148 */       if (lavaItems && entity instanceof net.minecraft.entity.item.EntityItem)
/*     */         continue; 
/* 150 */       if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/* 151 */         if (!dead) {
/* 152 */           boolean crystalIsDead = entity.field_70128_L;
/* 153 */           boolean crystalIsPseudoDead = ((IEntity)entity).isPseudoDeadT();
/* 154 */           if (crystalIsDead || crystalIsPseudoDead) {
/* 155 */             if ((crystalIsDead && Thunderhack.setDeadManager.passedDeathTime(entity, deathTime)) || (crystalIsPseudoDead && ((IEntity)entity)
/* 156 */               .getPseudoTimeT().passedMs(deathTime))) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/* 161 */             return true;
/*     */           } 
/*     */           
/* 164 */           data.blocked = true;
/*     */         } 
/*     */ 
/*     */         
/* 168 */         data.getBlockingEntities().add(new BlockingEntity(entity, pos));
/*     */         
/*     */         continue;
/*     */       } 
/* 172 */       return true;
/*     */     } 
/*     */     
/* 175 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean checkLiquid(Block block, boolean water, boolean lava) {
/* 179 */     return ((water && (block == Blocks.field_150355_j || block == Blocks.field_150358_i)) || (lava && (block == Blocks.field_150353_l || block == Blocks.field_150356_k)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesObby() {
/* 189 */     return this.obby;
/*     */   }
/*     */   
/*     */   public boolean isObbyValid() {
/* 193 */     return this.obbyValid;
/*     */   }
/*     */   
/*     */   public float getMaxDamage() {
/* 197 */     return this.damage;
/*     */   }
/*     */   
/*     */   public void setDamage(float damage) {
/* 201 */     this.damage = damage;
/*     */   }
/*     */   
/*     */   public float getSelfDamage() {
/* 205 */     return this.selfDamage;
/*     */   }
/*     */   
/*     */   public void setSelfDamage(float selfDamage) {
/* 209 */     this.selfDamage = selfDamage;
/*     */   }
/*     */   
/*     */   public EntityPlayer getTarget() {
/* 213 */     return this.target;
/*     */   }
/*     */   
/*     */   public void setTarget(EntityPlayer target) {
/* 217 */     this.target = target;
/*     */   }
/*     */   
/*     */   public EntityPlayer getFacePlacer() {
/* 221 */     return this.facePlace;
/*     */   }
/*     */   
/*     */   public void setFacePlacer(EntityPlayer facePlace) {
/* 225 */     this.facePlace = facePlace;
/*     */   }
/*     */   
/*     */   public Set<EntityPlayer> getAntiTotems() {
/* 229 */     return this.antiTotems;
/*     */   }
/*     */   
/*     */   public void addAntiTotem(EntityPlayer player) {
/* 233 */     this.antiTotems.add(player);
/*     */   }
/*     */   
/*     */   public boolean isBlocked() {
/* 237 */     return this.blocked;
/*     */   }
/*     */   
/*     */   public float getMinDiff() {
/* 241 */     return this.minDiff;
/*     */   }
/*     */   
/*     */   public void setMinDiff(float minDiff) {
/* 245 */     this.minDiff = minDiff;
/*     */   }
/*     */   
/*     */   public boolean isForce() {
/* 249 */     return !this.forced.isEmpty();
/*     */   }
/*     */   
/*     */   public void addForcePlayer(EntityPlayer player) {
/* 253 */     this.forced.add(player);
/*     */   }
/*     */   
/*     */   public List<EntityPlayer> getForced() {
/* 257 */     return this.forced;
/*     */   }
/*     */   
/*     */   public boolean isLiquidValid() {
/* 261 */     return this.liquidValid;
/*     */   }
/*     */   
/*     */   public boolean isLiquid() {
/* 265 */     return this.liquid;
/*     */   }
/*     */   
/*     */   public float getHealth() {
/* 269 */     EntityPlayer entityPlayer = getTarget();
/* 270 */     return (entityPlayer == null) ? 36.0F : EntityUtil.getHealth((Entity)entityPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(PositionData o) {
/* 276 */     if (((Boolean)this.module.useSafetyFactor.getValue()).booleanValue()) {
/*     */       
/* 278 */       double thisFactor = this.damage * ((Double)this.module.safetyFactor.getValue()).doubleValue() - this.selfDamage * ((Double)this.module.selfFactor.getValue()).doubleValue();
/*     */       
/* 280 */       double otherFactor = o.damage * ((Double)this.module.safetyFactor.getValue()).doubleValue() - o.selfDamage * ((Double)this.module.selfFactor.getValue()).doubleValue();
/*     */       
/* 282 */       if (thisFactor != otherFactor) {
/* 283 */         return Double.compare(otherFactor, thisFactor);
/*     */       }
/*     */     } 
/*     */     
/* 287 */     if (Math.abs(o.damage - this.damage) < ((Double)this.module.compareDiff.getValue()).doubleValue() && (
/* 288 */       !((Boolean)this.module.facePlaceCompare.getValue()).booleanValue() || this.damage >= ((Float)this.module.minDamage
/* 289 */       .getValue()).floatValue())) {
/* 290 */       if (usesObby() && o.usesObby())
/*     */       {
/* 292 */         return Integer.compare((getPath()).length, (o.getPath()).length) + 
/* 293 */           Float.compare(this.selfDamage, o.selfDamage);
/*     */       }
/*     */       
/* 296 */       return Float.compare(this.selfDamage, o.getSelfDamage());
/*     */     } 
/*     */     
/* 299 */     return Float.compare(o.damage, this.damage);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 304 */     if (o instanceof PositionData) {
/* 305 */       return ((PositionData)o).getPos().equals(getPos());
/*     */     }
/*     */     
/* 308 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 313 */     return getPos().hashCode();
/*     */   }
/*     */   
/*     */   public boolean isRaytraceBypass() {
/* 317 */     return this.raytraceBypass;
/*     */   }
/*     */   
/*     */   public void setRaytraceBypass(boolean raytraceBypass) {
/* 321 */     this.raytraceBypass = raytraceBypass;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\PositionData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */