/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntityPolarBear;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EntityType
/*     */ {
/*  18 */   Animal(new Color(0, 200, 0, 255)),
/*  19 */   Monster(new Color(200, 60, 60, 255)),
/*  20 */   Player(new Color(255, 255, 255, 255)),
/*  21 */   Boss(new Color(40, 0, 255, 255)),
/*  22 */   Vehicle(new Color(200, 100, 0, 255)),
/*  23 */   Other(new Color(200, 100, 200, 255)),
/*  24 */   Entity(new Color(255, 255, 0, 255));
/*     */   
/*     */   private final Color color;
/*     */   
/*     */   EntityType(Color color) {
/*  29 */     this.color = color;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Supplier<EntityType> getEntityType(Entity entity) {
/*  34 */     if (entity instanceof EntityWolf) {
/*  35 */       return () -> isAngryWolf((EntityWolf)entity) ? Monster : Animal;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  40 */     if (entity instanceof EntityEnderman) {
/*  41 */       return () -> isAngryEnderMan((EntityEnderman)entity) ? Monster : Entity;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  46 */     if (entity instanceof EntityPolarBear) {
/*  47 */       return () -> isAngryPolarBear((EntityPolarBear)entity) ? Monster : Animal;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  52 */     if (entity instanceof EntityPigZombie) {
/*  53 */       return () -> (entity.field_70125_A == 0.0F && ((EntityPigZombie)entity).func_142015_aE() <= 0) ? Monster : Entity;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (entity instanceof EntityIronGolem) {
/*  60 */       return () -> isAngryGolem((EntityIronGolem)entity) ? Monster : Entity;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  65 */     if (entity instanceof net.minecraft.entity.passive.EntityVillager) {
/*  66 */       return () -> Entity;
/*     */     }
/*     */     
/*  69 */     if (entity instanceof EntityRabbit) {
/*  70 */       return () -> isFriendlyRabbit((EntityRabbit)entity) ? Animal : Monster;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (isAnimal(entity)) {
/*  76 */       return () -> Animal;
/*     */     }
/*     */     
/*  79 */     if (isMonster(entity)) {
/*  80 */       return () -> Monster;
/*     */     }
/*     */     
/*  83 */     if (isPlayer(entity)) {
/*  84 */       return () -> Player;
/*     */     }
/*     */     
/*  87 */     if (isVehicle(entity)) {
/*  88 */       return () -> Vehicle;
/*     */     }
/*     */     
/*  91 */     if (isBoss(entity)) {
/*  92 */       return () -> Boss;
/*     */     }
/*     */     
/*  95 */     if (isOther(entity)) {
/*  96 */       return () -> Other;
/*     */     }
/*     */     
/*  99 */     return () -> Entity;
/*     */   }
/*     */   
/*     */   public static boolean isPlayer(Entity entity) {
/* 103 */     return entity instanceof net.minecraft.entity.player.EntityPlayer;
/*     */   }
/*     */   
/*     */   public static boolean isAnimal(Entity entity) {
/* 107 */     return (entity instanceof net.minecraft.entity.passive.EntityPig || entity instanceof net.minecraft.entity.passive.EntityParrot || entity instanceof net.minecraft.entity.passive.EntityCow || entity instanceof net.minecraft.entity.passive.EntitySheep || entity instanceof net.minecraft.entity.passive.EntityChicken || entity instanceof net.minecraft.entity.passive.EntitySquid || entity instanceof net.minecraft.entity.passive.EntityBat || entity instanceof net.minecraft.entity.passive.EntityVillager || entity instanceof net.minecraft.entity.passive.EntityOcelot || entity instanceof net.minecraft.entity.passive.EntityHorse || entity instanceof net.minecraft.entity.passive.EntityLlama || entity instanceof net.minecraft.entity.passive.EntityMule || entity instanceof net.minecraft.entity.passive.EntityDonkey || entity instanceof net.minecraft.entity.passive.EntitySkeletonHorse || entity instanceof net.minecraft.entity.passive.EntityZombieHorse || entity instanceof net.minecraft.entity.monster.EntitySnowman || (entity instanceof EntityRabbit && 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       isFriendlyRabbit((EntityRabbit)entity)));
/*     */   }
/*     */   
/*     */   public static boolean isMonster(Entity entity) {
/* 128 */     return (entity instanceof net.minecraft.entity.monster.EntityCreeper || entity instanceof net.minecraft.entity.monster.EntityIllusionIllager || entity instanceof net.minecraft.entity.monster.EntitySkeleton || (entity instanceof net.minecraft.entity.monster.EntityZombie && !(entity instanceof EntityPigZombie)) || entity instanceof net.minecraft.entity.monster.EntityBlaze || entity instanceof net.minecraft.entity.monster.EntitySpider || entity instanceof net.minecraft.entity.monster.EntityWitch || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntitySilverfish || entity instanceof net.minecraft.entity.monster.EntityGuardian || entity instanceof net.minecraft.entity.monster.EntityEndermite || entity instanceof net.minecraft.entity.monster.EntityGhast || entity instanceof net.minecraft.entity.monster.EntityEvoker || entity instanceof net.minecraft.entity.monster.EntityShulker || entity instanceof net.minecraft.entity.monster.EntityWitherSkeleton || entity instanceof net.minecraft.entity.monster.EntityStray || entity instanceof net.minecraft.entity.monster.EntityVex || entity instanceof net.minecraft.entity.monster.EntityVindicator || (entity instanceof EntityPolarBear && 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       !isAngryPolarBear((EntityPolarBear)entity)) || (entity instanceof EntityWolf && 
/*     */       
/* 150 */       !isAngryWolf((EntityWolf)entity)) || (entity instanceof EntityPigZombie && 
/*     */       
/* 152 */       !isAngryPigMan(entity)) || (entity instanceof EntityEnderman && 
/*     */       
/* 154 */       !isAngryEnderMan((EntityEnderman)entity)) || (entity instanceof EntityRabbit && 
/*     */       
/* 156 */       !isFriendlyRabbit((EntityRabbit)entity)) || (entity instanceof EntityIronGolem && 
/*     */       
/* 158 */       !isAngryGolem((EntityIronGolem)entity)));
/*     */   }
/*     */   
/*     */   public static boolean isBoss(Entity entity) {
/* 162 */     return (entity instanceof net.minecraft.entity.boss.EntityDragon || entity instanceof net.minecraft.entity.boss.EntityWither || entity instanceof net.minecraft.entity.monster.EntityGiantZombie);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOther(Entity entity) {
/* 168 */     return (entity instanceof net.minecraft.entity.item.EntityEnderCrystal || entity instanceof net.minecraft.entity.projectile.EntityEvokerFangs || entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.item.EntityFallingBlock || entity instanceof net.minecraft.entity.projectile.EntityFireball || entity instanceof net.minecraft.entity.item.EntityEnderEye || entity instanceof net.minecraft.entity.item.EntityEnderPearl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVehicle(Entity entity) {
/* 178 */     return (entity instanceof net.minecraft.entity.item.EntityBoat || entity instanceof net.minecraft.entity.item.EntityMinecart);
/*     */   }
/*     */   
/*     */   public static boolean isAngryEnderMan(EntityEnderman enderman) {
/* 182 */     return enderman.func_70823_r();
/*     */   }
/*     */   
/*     */   public static boolean isAngryPigMan(Entity entity) {
/* 186 */     return (entity instanceof EntityPigZombie && entity.field_70125_A == 0.0F && ((EntityPigZombie)entity)
/*     */       
/* 188 */       .func_142015_aE() <= 0);
/*     */   }
/*     */   
/*     */   public static boolean isAngryGolem(EntityIronGolem ironGolem) {
/* 192 */     return (ironGolem.field_70125_A == 0.0F);
/*     */   }
/*     */   
/*     */   public static boolean isAngryWolf(EntityWolf wolf) {
/* 196 */     return wolf.func_70919_bu();
/*     */   }
/*     */   
/*     */   public static boolean isAngryPolarBear(EntityPolarBear polarBear) {
/* 200 */     return (polarBear.field_70125_A == 0.0F && polarBear
/* 201 */       .func_142015_aE() <= 0);
/*     */   }
/*     */   
/*     */   public static boolean isFriendlyRabbit(EntityRabbit rabbit) {
/* 205 */     return (rabbit.func_175531_cl() != 99);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 212 */     return this.color;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\EntityType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */