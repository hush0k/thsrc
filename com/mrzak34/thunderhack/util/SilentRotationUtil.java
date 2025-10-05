/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SilentRotationUtil
/*     */ {
/*     */   public static void lookAtVector(Vec3d vec) {
/*  14 */     float[] angle = calcAngle(Util.mc.field_71439_g.func_174824_e(Util.mc.func_184121_ak()), vec);
/*  15 */     setPlayerRotations(angle[0], angle[1]);
/*  16 */     Util.mc.field_71439_g.field_70761_aq = angle[0];
/*  17 */     Util.mc.field_71439_g.field_70759_as = angle[0];
/*     */   }
/*     */   
/*     */   public static void lookAtVec3d(Vec3d vec3d) {
/*  21 */     float[] angle = calculateAngle(Util.mc.field_71439_g.func_174824_e(Util.mc.func_184121_ak()), new Vec3d(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c));
/*  22 */     Util.mc.field_71439_g.field_70125_A = angle[1];
/*  23 */     Util.mc.field_71439_g.field_70177_z = angle[0];
/*     */   }
/*     */   
/*     */   public static void lookAtXYZ(double x, double y, double z) {
/*  27 */     Vec3d vec3d = new Vec3d(x, y, z);
/*  28 */     lookAtVec3d(vec3d);
/*     */   }
/*     */   
/*     */   public static void lookAtEntity(Entity entity) {
/*  32 */     float[] angle = calcAngle(Util.mc.field_71439_g.func_174824_e(Util.mc.func_184121_ak()), entity.func_174824_e(Util.mc.func_184121_ak()));
/*  33 */     lookAtAngles(angle[0], angle[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void lookAtAngles(float yaw, float pitch) {
/*  38 */     setPlayerRotations(yaw, pitch);
/*     */     
/*  40 */     Util.mc.field_71439_g.field_70759_as = yaw;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void lookAtBlock(BlockPos blockPos) {
/*  46 */     float[] angle = calcAngle(Util.mc.field_71439_g.func_174824_e(Util.mc.func_184121_ak()), new Vec3d((Vec3i)blockPos));
/*  47 */     setPlayerRotations(angle[0], angle[1]);
/*  48 */     Util.mc.field_71439_g.field_70761_aq = angle[0];
/*  49 */     Util.mc.field_71439_g.field_70759_as = angle[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setPlayerRotations(float yaw, float pitch) {
/*  54 */     Util.mc.field_71439_g.field_70177_z = yaw;
/*  55 */     Util.mc.field_71439_g.field_70759_as = yaw;
/*  56 */     Util.mc.field_71439_g.field_70125_A = pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] calcAngle(Vec3d to) {
/*  61 */     if (to == null) {
/*  62 */       return null;
/*     */     }
/*  64 */     double difX = to.field_72450_a - (Util.mc.field_71439_g.func_174824_e(1.0F)).field_72450_a;
/*  65 */     double difY = (to.field_72448_b - (Util.mc.field_71439_g.func_174824_e(1.0F)).field_72448_b) * -1.0D;
/*  66 */     double difZ = to.field_72449_c - (Util.mc.field_71439_g.func_174824_e(1.0F)).field_72449_c;
/*  67 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/*  68 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] calcAngle(BlockPos to) {
/*  73 */     if (to == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     double difX = to.func_177958_n() - (Util.mc.field_71439_g.func_174824_e(1.0F)).field_72450_a;
/*  77 */     double difY = (to.func_177956_o() - (Util.mc.field_71439_g.func_174824_e(1.0F)).field_72448_b) * -1.0D;
/*  78 */     double difZ = to.func_177952_p() - (Util.mc.field_71439_g.func_174824_e(1.0F)).field_72449_c;
/*  79 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/*  80 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
/*     */   }
/*     */   
/*     */   public static float[] calcAngle(Vec3d from, Vec3d to) {
/*  84 */     double difX = to.field_72450_a - from.field_72450_a;
/*  85 */     double difY = (to.field_72448_b - from.field_72448_b) * -1.0D;
/*  86 */     double difZ = to.field_72449_c - from.field_72449_c;
/*  87 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/*  88 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] calculateAngle(Vec3d from, Vec3d to) {
/*  93 */     double difX = to.field_72450_a - from.field_72450_a;
/*  94 */     double difY = (to.field_72448_b - from.field_72448_b) * -1.0D;
/*  95 */     double difZ = to.field_72449_c - from.field_72449_c;
/*  96 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/*  97 */     float yD = (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D);
/*  98 */     float pD = (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist)));
/*  99 */     if (pD > 90.0F) {
/* 100 */       pD = 90.0F;
/* 101 */     } else if (pD < -90.0F) {
/* 102 */       pD = -90.0F;
/*     */     } 
/* 104 */     return new float[] { yD, pD };
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\SilentRotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */