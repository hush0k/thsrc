/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ public class ForcePosition
/*     */   extends PositionData
/*     */ {
/*     */   private final PositionData data;
/*     */   
/*     */   public ForcePosition(PositionData data, AutoCrystal module) {
/*  13 */     super(data.getPos(), data.getMaxLength(), module);
/*  14 */     this.data = data;
/*     */   }
/*     */   
/*     */   public PositionData getData() {
/*  18 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean usesObby() {
/*  23 */     return this.data.usesObby();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxDamage() {
/*  28 */     return this.data.getMaxDamage();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(float damage) {
/*  33 */     this.data.setDamage(damage);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSelfDamage() {
/*  38 */     return this.data.getSelfDamage();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelfDamage(float selfDamage) {
/*  43 */     this.data.setSelfDamage(selfDamage);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getTarget() {
/*  48 */     return this.data.getTarget();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(EntityPlayer target) {
/*  53 */     this.data.setTarget(target);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getFacePlacer() {
/*  58 */     return this.data.getFacePlacer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacePlacer(EntityPlayer facePlace) {
/*  63 */     this.data.setFacePlacer(facePlace);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EntityPlayer> getAntiTotems() {
/*  68 */     return this.data.getAntiTotems();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAntiTotem(EntityPlayer player) {
/*  73 */     this.data.addAntiTotem(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlocked() {
/*  78 */     return this.data.isBlocked();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinDiff() {
/*  83 */     return this.data.getMinDiff();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMinDiff(float minDiff) {
/*  88 */     this.data.setMinDiff(minDiff);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isForce() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addForcePlayer(EntityPlayer player) {
/*  98 */     this.data.addForcePlayer(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLiquid() {
/* 103 */     return this.data.isLiquid();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(PositionData o) {
/* 108 */     if (o instanceof ForcePosition) {
/* 109 */       int c = Float.compare(getMinDiff(), o.getMinDiff());
/* 110 */       if (c != 0) {
/* 111 */         return c;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return super.compareTo(o);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ForcePosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */