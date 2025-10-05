/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.manager.RotationManager;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.math.PhobosRotationUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotationSmoother
/*     */ {
/*     */   private final RotationManager manager;
/*     */   private int rotationTicks;
/*     */   private boolean rotating;
/*     */   
/*     */   public RotationSmoother(RotationManager manager) {
/*  20 */     this.manager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z, double fromX, double fromY, double fromZ, float fromHeight) {
/*  30 */     double xDiff = x - fromX;
/*  31 */     double yDiff = y - fromY + fromHeight;
/*  32 */     double zDiff = z - fromZ;
/*  33 */     double dist = MathHelper.func_76133_a(xDiff * xDiff + zDiff * zDiff);
/*     */     
/*  35 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/*  36 */     float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/*     */     
/*  38 */     float prevYaw = Thunderhack.rotationManager.getServerYaw();
/*  39 */     float diff = yaw - prevYaw;
/*     */     
/*  41 */     if (diff < -180.0F || diff > 180.0F) {
/*  42 */       float round = Math.round(Math.abs(diff / 360.0F));
/*  43 */       diff = (diff < 0.0F) ? (diff + 360.0F * round) : (diff - 360.0F * round);
/*     */     } 
/*     */     
/*  46 */     return new float[] { prevYaw + diff, pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] faceSmoothly(double curYaw, double curPitch, double intendedYaw, double intendedPitch, double yawSpeed, double pitchSpeed) {
/*  55 */     float yaw = PhobosRotationUtil.updateRotation((float)curYaw, (float)intendedYaw, (float)yawSpeed);
/*     */ 
/*     */ 
/*     */     
/*  59 */     float pitch = PhobosRotationUtil.updateRotation((float)curPitch, (float)intendedPitch, (float)pitchSpeed);
/*     */ 
/*     */ 
/*     */     
/*  63 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static double angle(float[] rotation1, float[] rotation2) {
/*  67 */     Vec3d r1Vec = PhobosRotationUtil.getVec3d(rotation1[0], rotation1[1]);
/*  68 */     Vec3d r2Vec = PhobosRotationUtil.getVec3d(rotation2[0], rotation2[1]);
/*  69 */     return MathUtil.angle(r1Vec, r2Vec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getRotations(Entity from, Entity entity, double height, double maxAngle) {
/*  76 */     return getRotations(entity, from.field_70165_t, from.field_70163_u, from.field_70161_v, from
/*     */ 
/*     */ 
/*     */         
/*  80 */         .func_70047_e(), height, maxAngle);
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
/*     */   public float[] getRotations(Entity entity, double fromX, double fromY, double fromZ, float eyeHeight, double height, double maxAngle) {
/*  92 */     float[] rotations = getRotations(entity.field_70165_t, entity.field_70163_u + entity
/*     */         
/*  94 */         .func_70047_e() * height, entity.field_70161_v, fromX, fromY, fromZ, eyeHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     return smoothen(rotations, maxAngle);
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] smoothen(float[] rotations, double maxAngle) {
/* 106 */     float[] server = { this.manager.getServerYaw(), this.manager.getServerPitch() };
/* 107 */     return smoothen(server, rotations, maxAngle);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] smoothen(float[] server, float[] rotations, double maxAngle) {
/* 113 */     if (maxAngle >= 180.0D || maxAngle <= 0.0D || 
/*     */       
/* 115 */       angle(server, rotations) <= maxAngle) {
/* 116 */       this.rotating = false;
/* 117 */       return rotations;
/*     */     } 
/*     */     
/* 120 */     this.rotationTicks = 0;
/* 121 */     this.rotating = true;
/* 122 */     return faceSmoothly(server[0], server[1], rotations[0], rotations[1], maxAngle, maxAngle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementRotationTicks() {
/* 131 */     this.rotationTicks++;
/*     */   }
/*     */   
/*     */   public int getRotationTicks() {
/* 135 */     return this.rotationTicks;
/*     */   }
/*     */   
/*     */   public boolean isRotating() {
/* 139 */     return this.rotating;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\RotationSmoother.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */