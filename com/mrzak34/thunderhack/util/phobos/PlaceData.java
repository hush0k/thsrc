/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class PlaceData {
/*  10 */   private final Map<EntityPlayer, ForceData> force = new HashMap<>();
/*  11 */   private final Map<EntityPlayer, List<PositionData>> corr = new HashMap<>();
/*  12 */   private final Map<BlockPos, PositionData> obby = new HashMap<>();
/*  13 */   private final Map<BlockPos, PositionData> liquidObby = new HashMap<>();
/*     */   
/*  15 */   private final List<PositionData> liquid = new ArrayList<>();
/*  16 */   private final Set<PositionData> data = new TreeSet<>();
/*  17 */   private final Set<AntiTotemData> antiTotem = new TreeSet<>();
/*  18 */   private final Set<PositionData> shieldData = new TreeSet<>();
/*  19 */   private final Set<PositionData> raytraceData = new TreeSet<>();
/*     */   private final float minDamage;
/*     */   private EntityPlayer shieldPlayer;
/*     */   private float highestSelfDamage;
/*     */   private EntityPlayer target;
/*     */   
/*     */   public PlaceData(float minDamage) {
/*  26 */     this.minDamage = minDamage;
/*     */   }
/*     */   
/*     */   public EntityPlayer getShieldPlayer() {
/*  30 */     if (this.shieldPlayer == null) {
/*  31 */       this.shieldPlayer = new ShieldPlayer((World)(Minecraft.func_71410_x()).field_71441_e);
/*     */     }
/*     */     
/*  34 */     return this.shieldPlayer;
/*     */   }
/*     */   
/*     */   public void addAntiTotem(AntiTotemData data) {
/*  38 */     this.antiTotem.add(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCorrespondingData(EntityPlayer player, PositionData data) {
/*  43 */     List<PositionData> list = this.corr.computeIfAbsent(player, v -> new ArrayList());
/*     */     
/*  45 */     list.add(data);
/*     */   }
/*     */   
/*     */   public void confirmHighDamageForce(EntityPlayer player) {
/*  49 */     ForceData data = this.force.computeIfAbsent(player, v -> new ForceData());
/*  50 */     data.setPossibleHighDamage(true);
/*     */   }
/*     */   
/*     */   public void confirmPossibleAntiTotem(EntityPlayer player) {
/*  54 */     ForceData data = this.force.computeIfAbsent(player, v -> new ForceData());
/*  55 */     data.setPossibleAntiTotem(true);
/*     */   }
/*     */   
/*     */   public void addForceData(EntityPlayer player, ForcePosition forceIn) {
/*  59 */     ForceData data = this.force.computeIfAbsent(player, v -> new ForceData());
/*  60 */     data.getForceData().add(forceIn);
/*     */   }
/*     */   
/*     */   public void addAllCorrespondingData() {
/*  64 */     for (AntiTotemData antiTotemData : this.antiTotem) {
/*  65 */       for (EntityPlayer player : antiTotemData.getAntiTotems()) {
/*  66 */         List<PositionData> corresponding = this.corr.get(player);
/*  67 */         if (corresponding != null) {
/*  68 */           corresponding.forEach(antiTotemData::addCorrespondingData);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getMinDamage() {
/*  75 */     return this.minDamage;
/*     */   }
/*     */   
/*     */   public EntityPlayer getTarget() {
/*  79 */     return this.target;
/*     */   }
/*     */   
/*     */   public void setTarget(EntityPlayer target) {
/*  83 */     this.target = target;
/*     */   }
/*     */   
/*     */   public Set<AntiTotemData> getAntiTotem() {
/*  87 */     return this.antiTotem;
/*     */   }
/*     */   
/*     */   public Set<PositionData> getData() {
/*  91 */     return this.data;
/*     */   }
/*     */   
/*     */   public Map<BlockPos, PositionData> getAllObbyData() {
/*  95 */     return this.obby;
/*     */   }
/*     */   
/*     */   public Map<EntityPlayer, ForceData> getForceData() {
/*  99 */     return this.force;
/*     */   }
/*     */   
/*     */   public List<PositionData> getLiquid() {
/* 103 */     return this.liquid;
/*     */   }
/*     */   
/*     */   public Map<BlockPos, PositionData> getLiquidObby() {
/* 107 */     return this.liquidObby;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     StringBuilder builder = new StringBuilder();
/* 113 */     builder.append("PlaceData:\n");
/* 114 */     for (PositionData data : this.data) {
/* 115 */       builder.append("Position: ").append(data.getPos()).append("\n");
/*     */     }
/*     */     
/* 118 */     return builder.toString();
/*     */   }
/*     */   
/*     */   public float getHighestSelfDamage() {
/* 122 */     return this.highestSelfDamage;
/*     */   }
/*     */   
/*     */   public void setHighestSelfDamage(float highestSelfDamage) {
/* 126 */     this.highestSelfDamage = highestSelfDamage;
/*     */   }
/*     */   
/*     */   public Set<PositionData> getShieldData() {
/* 130 */     return this.shieldData;
/*     */   }
/*     */   
/*     */   public Set<PositionData> getRaytraceData() {
/* 134 */     return this.raytraceData;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\PlaceData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */