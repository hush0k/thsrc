/*    */ package com.mrzak34.thunderhack.util.math;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PhobosRotationUtil
/*    */ {
/*    */   public static Vec3d getVec3d(float yaw, float pitch) {
/* 11 */     float vx = -MathHelper.func_76126_a(MathUtil.rad(yaw)) * MathHelper.func_76134_b(MathUtil.rad(pitch));
/* 12 */     float vz = MathHelper.func_76134_b(MathUtil.rad(yaw)) * MathHelper.func_76134_b(MathUtil.rad(pitch));
/* 13 */     float vy = -MathHelper.func_76126_a(MathUtil.rad(pitch));
/* 14 */     return new Vec3d(vx, vy, vz);
/*    */   }
/*    */ 
/*    */   
/*    */   public static double angle(float[] rotation1, float[] rotation2) {
/* 19 */     Vec3d r1Vec = getVec3d(rotation1[0], rotation1[1]);
/* 20 */     Vec3d r2Vec = getVec3d(rotation2[0], rotation2[1]);
/* 21 */     return MathUtil.angle(r1Vec, r2Vec);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float updateRotation(float current, float intended, float factor) {
/* 28 */     float updated = MathHelper.func_76142_g(intended - current);
/*    */     
/* 30 */     if (updated > factor) {
/* 31 */       updated = factor;
/*    */     }
/*    */     
/* 34 */     if (updated < -factor) {
/* 35 */       updated = -factor;
/*    */     }
/*    */     
/* 38 */     return current + updated;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\math\PhobosRotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */