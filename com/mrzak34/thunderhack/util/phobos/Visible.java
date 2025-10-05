/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class Visible {
/*  11 */   public static final Visible INSTANCE = new Visible();
/*  12 */   private final NcpInteractTrace rayTracing = new NcpInteractTrace();
/*  13 */   private final RayChecker checker = new RayChecker();
/*     */   
/*     */   public Visible() {
/*  16 */     this.rayTracing.setMaxSteps(60);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSameBlock(int x1, int y1, int z1, double x2, double y2, double z2) {
/*  21 */     return (x1 == floor(x2) && z1 == 
/*  22 */       floor(z2) && y1 == 
/*  23 */       floor(y2));
/*     */   }
/*     */   
/*     */   public static int floor(double num) {
/*  27 */     int floor = (int)num;
/*  28 */     return (floor == num) ? floor : (floor - 
/*     */       
/*  30 */       (int)(Double.doubleToRawLongBits(num) >>> 63L));
/*     */   }
/*     */   
/*     */   public boolean check(BlockPos pos) {
/*  34 */     return check(pos, 10);
/*     */   }
/*     */   
/*     */   public boolean check(BlockPos pos, int ticks) {
/*  38 */     this.checker.ticksToCheck = ticks;
/*  39 */     EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/*  40 */     return check(((Entity)entityPlayerSP).field_70165_t, ((Entity)entityPlayerSP).field_70163_u, ((Entity)entityPlayerSP).field_70161_v, ((Entity)entityPlayerSP).field_70177_z, ((Entity)entityPlayerSP).field_70125_A, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean check(double x, double y, double z, float yaw, float pitch, BlockPos pos) {
/*     */     boolean collides;
/*  47 */     int blockX = pos.func_177958_n();
/*  48 */     int blockY = pos.func_177956_o();
/*  49 */     int blockZ = pos.func_177952_p();
/*  50 */     double eyeY = y + Util.mc.field_71439_g.func_70047_e();
/*  51 */     if (isSameBlock(blockX, blockY, blockZ, x, eyeY, z)) {
/*  52 */       collides = false;
/*     */     } else {
/*  54 */       collides = !this.checker.checkFlyingQueue(x, eyeY, z, yaw, pitch, blockX, blockY, blockZ, AutoCrystal.POSITION_HISTORY);
/*     */     } 
/*     */     
/*  57 */     return collides;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkRayTracing(double eyeX, double eyeY, double eyeZ, double dirX, double dirY, double dirZ, int blockX, int blockY, int blockZ) {
/*     */     boolean collides;
/*  63 */     int eyeBlockX = floor(eyeX);
/*  64 */     int eyeBlockY = floor(eyeY);
/*  65 */     int eyeBlockZ = floor(eyeZ);
/*  66 */     int bdX = blockX - eyeBlockX;
/*  67 */     int bdY = blockY - eyeBlockY;
/*  68 */     int bdZ = blockZ - eyeBlockZ;
/*     */     
/*  70 */     double tMinX = getMinTime(eyeX, eyeBlockX, dirX, bdX);
/*  71 */     double tMinY = getMinTime(eyeY, eyeBlockY, dirY, bdY);
/*  72 */     double tMinZ = getMinTime(eyeZ, eyeBlockZ, dirZ, bdZ);
/*  73 */     double tMaxX = getMaxTime(eyeX, eyeBlockX, dirX, tMinX);
/*  74 */     double tMaxY = getMaxTime(eyeY, eyeBlockY, dirY, tMinY);
/*  75 */     double tMaxZ = getMaxTime(eyeZ, eyeBlockZ, dirZ, tMinZ);
/*     */     
/*  77 */     double tCollide = Math.max(0.0D, Math.max(tMinX, Math.max(tMinY, tMinZ)));
/*  78 */     double collideX = toBlock(eyeX + dirX * tCollide, blockX);
/*  79 */     double collideY = toBlock(eyeY + dirY * tCollide, blockY);
/*  80 */     double collideZ = toBlock(eyeZ + dirZ * tCollide, blockZ);
/*     */     
/*  82 */     if ((tMinX > tMaxY && tMinX > tMaxZ) || (tMinY > tMaxX && tMinY > tMaxZ) || (tMinZ > tMaxX && tMaxZ > tMaxY)) {
/*     */ 
/*     */       
/*  85 */       collideX = postCorrect(blockX, bdX, collideX);
/*  86 */       collideY = postCorrect(blockY, bdY, collideY);
/*  87 */       collideZ = postCorrect(blockZ, bdZ, collideZ);
/*     */     } 
/*     */     
/*  90 */     if (tMinX == tCollide) {
/*  91 */       collideX = Math.round(collideX);
/*     */     }
/*     */     
/*  94 */     if (tMinY == tCollide) {
/*  95 */       collideY = Math.round(collideY);
/*     */     }
/*     */     
/*  98 */     if (tMinZ == tCollide) {
/*  99 */       collideZ = Math.round(collideZ);
/*     */     }
/*     */     
/* 102 */     this.rayTracing.set(eyeX, eyeY, eyeZ, collideX, collideY, collideZ, blockX, blockY, blockZ);
/* 103 */     this.rayTracing.loop();
/*     */ 
/*     */     
/* 106 */     if (this.rayTracing.collides) {
/* 107 */       collides = true;
/*     */     } else {
/* 109 */       collides = (this.rayTracing.getStepsDone() > this.rayTracing.getMaxSteps());
/*     */     } 
/*     */     
/* 112 */     return collides;
/*     */   }
/*     */   
/*     */   private double postCorrect(int blockC, int bdC, double collideC) {
/* 116 */     int ref = (bdC < 0) ? (blockC + 1) : blockC;
/* 117 */     if (floor(collideC) == ref) {
/* 118 */       return collideC;
/*     */     }
/* 120 */     return ref;
/*     */   }
/*     */ 
/*     */   
/*     */   private double getMinTime(double eye, int eyeBlock, double dir, int blockDiff) {
/* 125 */     if (blockDiff == 0) {
/* 126 */       return 0.0D;
/*     */     }
/*     */     
/* 129 */     double eyeOffset = Math.abs(eye - eyeBlock);
/* 130 */     return (((dir < 0.0D) ? eyeOffset : (1.0D - eyeOffset)) + (
/* 131 */       Math.abs(blockDiff) - 1)) / Math.abs(dir);
/*     */   }
/*     */   
/*     */   private double getMaxTime(double eye, int eyeBlock, double dir, double tMin) {
/* 135 */     if (dir == 0.0D) {
/* 136 */       return Double.MAX_VALUE;
/*     */     }
/*     */     
/* 139 */     if (tMin == 0.0D) {
/* 140 */       double eyeOffset = Math.abs(eye - eyeBlock);
/* 141 */       return ((dir < 0.0D) ? eyeOffset : (1.0D - eyeOffset)) / Math.abs(dir);
/*     */     } 
/*     */     
/* 144 */     return tMin + 1.0D / Math.abs(dir);
/*     */   }
/*     */   
/*     */   private double toBlock(double coord, int block) {
/* 148 */     int blockDiff = block - floor(coord);
/* 149 */     if (blockDiff == 0) {
/* 150 */       return coord;
/*     */     }
/* 152 */     return Math.round(coord);
/*     */   }
/*     */   
/*     */   private class RayChecker
/*     */     extends PositionHistoryChecker {
/*     */     protected boolean check(double x, double y, double z, float yaw, float pitch, int blockX, int blockY, int blockZ) {
/* 158 */       Vec3d direction = RotationUtil.getVec3d(yaw, pitch);
/* 159 */       return !Visible.this.checkRayTracing(x, y, z, direction.field_72450_a, direction.field_72448_b, direction.field_72449_c, blockX, blockY, blockZ);
/*     */     }
/*     */     private RayChecker() {}
/*     */     public boolean checkFlyingQueue(double x, double y, double z, float oldYaw, float oldPitch, int blockX, int blockY, int blockZ, PositionHistoryHelper history) {
/* 163 */       return super.checkFlyingQueue(x, y, z, oldYaw, oldPitch, blockX, blockY, blockZ, history);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Visible.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */