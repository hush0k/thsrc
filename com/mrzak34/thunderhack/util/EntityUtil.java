/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class EntityUtil
/*     */   implements Util {
/*     */   public static boolean isDead(Entity entity) {
/*  19 */     return (entity.field_70128_L || ((IEntity)entity)
/*  20 */       .isPseudoDeadT() || (entity instanceof EntityLivingBase && ((EntityLivingBase)entity)
/*     */       
/*  22 */       .func_110143_aJ() <= 0.0F));
/*     */   }
/*     */   
/*     */   public static void attackEntity(Entity entity, boolean packet, boolean swingArm) {
/*  26 */     if (packet) {
/*  27 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity));
/*     */     } else {
/*  29 */       mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, entity);
/*     */     } 
/*  31 */     if (swingArm) {
/*  32 */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Vec3d interpolateEntity(Entity entity, float time) {
/*  37 */     return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * time);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSafe(Entity entity, int height, boolean floor) {
/*  44 */     return (getUnsafeBlocks(entity, height, floor).size() == 0);
/*     */   }
/*     */   
/*     */   public static boolean isSafe(Entity entity) {
/*  48 */     return isSafe(entity, 0, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Vec3d> getUnsafeBlocks(Entity entity, int height, boolean floor) {
/*  53 */     return getUnsafeBlocksFromVec3d(entity.func_174791_d(), height, floor);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Vec3d> getUnsafeBlocksFromVec3d(Vec3d pos, int height, boolean floor) {
/*  58 */     ArrayList<Vec3d> vec3ds = new ArrayList<>();
/*  59 */     for (Vec3d vector : getOffsets(height, floor)) {
/*  60 */       BlockPos targetPos = (new BlockPos(pos)).func_177963_a(vector.field_72450_a, vector.field_72448_b, vector.field_72449_c);
/*  61 */       Block block = mc.field_71441_e.func_180495_p(targetPos).func_177230_c();
/*  62 */       if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockFire || block instanceof net.minecraft.block.BlockDeadBush || block instanceof net.minecraft.block.BlockSnow)
/*     */       {
/*  64 */         vec3ds.add(vector); } 
/*     */     } 
/*  66 */     return vec3ds;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Vec3d> getOffsetList(int y, boolean floor) {
/*  71 */     ArrayList<Vec3d> offsets = new ArrayList<>();
/*  72 */     offsets.add(new Vec3d(-1.0D, y, 0.0D));
/*  73 */     offsets.add(new Vec3d(1.0D, y, 0.0D));
/*  74 */     offsets.add(new Vec3d(0.0D, y, -1.0D));
/*  75 */     offsets.add(new Vec3d(0.0D, y, 1.0D));
/*  76 */     if (floor) {
/*  77 */       offsets.add(new Vec3d(0.0D, (y - 1), 0.0D));
/*     */     }
/*  79 */     return offsets;
/*     */   }
/*     */   
/*     */   public static Vec3d[] getOffsets(int y, boolean floor) {
/*  83 */     List<Vec3d> offsets = getOffsetList(y, floor);
/*  84 */     Vec3d[] array = new Vec3d[offsets.size()];
/*  85 */     return offsets.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static boolean isLiving(Entity entity) {
/*  89 */     return entity instanceof EntityLivingBase;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getHealth(Entity entity) {
/*  94 */     if (isLiving(entity)) {
/*  95 */       EntityLivingBase livingBase = (EntityLivingBase)entity;
/*  96 */       return livingBase.func_110143_aJ() + livingBase.func_110139_bj();
/*     */     } 
/*  98 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static float getHealth(Entity entity, boolean absorption) {
/* 102 */     if (isLiving(entity)) {
/* 103 */       EntityLivingBase livingBase = (EntityLivingBase)entity;
/* 104 */       return livingBase.func_110143_aJ() + (absorption ? livingBase.func_110139_bj() : 0.0F);
/*     */     } 
/* 106 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
/* 110 */     double diffX = entityLiving.field_70165_t - mc.field_71439_g.field_70165_t;
/* 111 */     double diffZ = entityLiving.field_70161_v - mc.field_71439_g.field_70161_v;
/* 112 */     float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
/* 113 */     double difference = angleDifference(yaw, mc.field_71439_g.field_70177_z);
/* 114 */     return (difference <= scope);
/*     */   }
/*     */   
/*     */   public static double angleDifference(float oldYaw, float newYaw) {
/* 118 */     float yaw = Math.abs(oldYaw - newYaw) % 360.0F;
/* 119 */     if (yaw > 180.0F) {
/* 120 */       yaw = 360.0F - yaw;
/*     */     }
/* 122 */     return yaw;
/*     */   }
/*     */   
/*     */   public static boolean canEntityBeSeen(Entity entityIn) {
/* 126 */     return (mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70165_t + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(entityIn.field_70165_t, entityIn.field_70163_u + entityIn.func_70047_e(), entityIn.field_70161_v), false, true, false) == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\EntityUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */