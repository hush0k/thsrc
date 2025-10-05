/*     */ package com.mrzak34.thunderhack.util.rotations;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ public class RayTracingUtils
/*     */ {
/*     */   public static ArrayList<Vec3d> getHitBoxPointsJitter(Vec3d position, float fakeBoxScale) {
/*  22 */     float head_height = 1.6F + interpolateRandom(-0.4F, 0.2F);
/*  23 */     float chest_height = 0.8F + interpolateRandom(-0.2F, 0.2F);
/*  24 */     float leggs_height = 0.225F + interpolateRandom(-0.1F, 0.1F);
/*     */     
/*  26 */     Vec3d head1 = position.func_72441_c(-fakeBoxScale, head_height, fakeBoxScale);
/*  27 */     Vec3d head2 = position.func_72441_c(0.0D, head_height, fakeBoxScale);
/*  28 */     Vec3d head3 = position.func_72441_c(fakeBoxScale, head_height, fakeBoxScale);
/*  29 */     Vec3d head4 = position.func_72441_c(-fakeBoxScale, head_height, 0.0D);
/*  30 */     Vec3d head5 = position.func_72441_c(fakeBoxScale, head_height, 0.0D);
/*  31 */     Vec3d head6 = position.func_72441_c(-fakeBoxScale, head_height, -fakeBoxScale);
/*  32 */     Vec3d head7 = position.func_72441_c(0.0D, head_height, -fakeBoxScale);
/*  33 */     Vec3d head8 = position.func_72441_c(fakeBoxScale, head_height, -fakeBoxScale);
/*     */     
/*  35 */     Vec3d chest1 = position.func_72441_c(-fakeBoxScale, chest_height, fakeBoxScale);
/*  36 */     Vec3d chest2 = position.func_72441_c(0.0D, chest_height, fakeBoxScale);
/*  37 */     Vec3d chest3 = position.func_72441_c(fakeBoxScale, chest_height, fakeBoxScale);
/*  38 */     Vec3d chest4 = position.func_72441_c(-fakeBoxScale, chest_height, 0.0D);
/*  39 */     Vec3d chest5 = position.func_72441_c(fakeBoxScale, chest_height, 0.0D);
/*  40 */     Vec3d chest6 = position.func_72441_c(-fakeBoxScale, chest_height, -fakeBoxScale);
/*  41 */     Vec3d chest7 = position.func_72441_c(0.0D, chest_height, -fakeBoxScale);
/*  42 */     Vec3d chest8 = position.func_72441_c(fakeBoxScale, chest_height, -fakeBoxScale);
/*     */     
/*  44 */     Vec3d legs1 = position.func_72441_c(-fakeBoxScale, leggs_height, fakeBoxScale);
/*  45 */     Vec3d legs2 = position.func_72441_c(0.0D, leggs_height, fakeBoxScale);
/*  46 */     Vec3d legs3 = position.func_72441_c(fakeBoxScale, leggs_height, fakeBoxScale);
/*  47 */     Vec3d legs4 = position.func_72441_c(-fakeBoxScale, leggs_height, 0.0D);
/*  48 */     Vec3d legs5 = position.func_72441_c(fakeBoxScale, leggs_height, 0.0D);
/*  49 */     Vec3d legs6 = position.func_72441_c(-fakeBoxScale, leggs_height, -fakeBoxScale);
/*  50 */     Vec3d legs7 = position.func_72441_c(0.0D, leggs_height, -fakeBoxScale);
/*  51 */     Vec3d legs8 = position.func_72441_c(fakeBoxScale, leggs_height, -fakeBoxScale);
/*     */     
/*  53 */     return new ArrayList<>(Arrays.asList(new Vec3d[] { head1, head2, head3, head4, head5, head6, head7, head8, chest1, chest2, chest3, chest4, chest5, chest6, chest7, chest8, legs1, legs2, legs3, legs4, legs5, legs6, legs7, legs8 }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Vec3d> getHitBoxPointsNonJitter(Vec3d position, float fakeBoxScale) {
/*  62 */     float head_height = 1.6F;
/*  63 */     float chest_height = 0.8F;
/*  64 */     float leggs_height = 0.225F;
/*     */     
/*  66 */     Vec3d head1 = position.func_72441_c(-fakeBoxScale, head_height, fakeBoxScale);
/*  67 */     Vec3d head2 = position.func_72441_c(0.0D, head_height, fakeBoxScale);
/*  68 */     Vec3d head3 = position.func_72441_c(fakeBoxScale, head_height, fakeBoxScale);
/*  69 */     Vec3d head4 = position.func_72441_c(-fakeBoxScale, head_height, 0.0D);
/*  70 */     Vec3d head5 = position.func_72441_c(fakeBoxScale, head_height, 0.0D);
/*  71 */     Vec3d head6 = position.func_72441_c(-fakeBoxScale, head_height, -fakeBoxScale);
/*  72 */     Vec3d head7 = position.func_72441_c(0.0D, head_height, -fakeBoxScale);
/*  73 */     Vec3d head8 = position.func_72441_c(fakeBoxScale, head_height, -fakeBoxScale);
/*     */     
/*  75 */     Vec3d chest1 = position.func_72441_c(-fakeBoxScale, chest_height, fakeBoxScale);
/*  76 */     Vec3d chest2 = position.func_72441_c(0.0D, chest_height, fakeBoxScale);
/*  77 */     Vec3d chest3 = position.func_72441_c(fakeBoxScale, chest_height, fakeBoxScale);
/*  78 */     Vec3d chest4 = position.func_72441_c(-fakeBoxScale, chest_height, 0.0D);
/*  79 */     Vec3d chest5 = position.func_72441_c(fakeBoxScale, chest_height, 0.0D);
/*  80 */     Vec3d chest6 = position.func_72441_c(-fakeBoxScale, chest_height, -fakeBoxScale);
/*  81 */     Vec3d chest7 = position.func_72441_c(0.0D, chest_height, -fakeBoxScale);
/*  82 */     Vec3d chest8 = position.func_72441_c(fakeBoxScale, chest_height, -fakeBoxScale);
/*     */     
/*  84 */     Vec3d legs1 = position.func_72441_c(-fakeBoxScale, leggs_height, fakeBoxScale);
/*  85 */     Vec3d legs2 = position.func_72441_c(0.0D, leggs_height, fakeBoxScale);
/*  86 */     Vec3d legs3 = position.func_72441_c(fakeBoxScale, leggs_height, fakeBoxScale);
/*  87 */     Vec3d legs4 = position.func_72441_c(-fakeBoxScale, leggs_height, 0.0D);
/*  88 */     Vec3d legs5 = position.func_72441_c(fakeBoxScale, leggs_height, 0.0D);
/*  89 */     Vec3d legs6 = position.func_72441_c(-fakeBoxScale, leggs_height, -fakeBoxScale);
/*  90 */     Vec3d legs7 = position.func_72441_c(0.0D, leggs_height, -fakeBoxScale);
/*  91 */     Vec3d legs8 = position.func_72441_c(fakeBoxScale, leggs_height, -fakeBoxScale);
/*     */     
/*  93 */     return new ArrayList<>(Arrays.asList(new Vec3d[] { head1, head2, head3, head4, head5, head6, head7, head8, chest1, chest2, chest3, chest4, chest5, chest6, chest7, chest8, legs1, legs2, legs3, legs4, legs5, legs6, legs7, legs8 }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Vec3d> getHitBoxPointsOldJitter(Vec3d position, float fakeBoxScale) {
/* 102 */     float head_height = 1.6F + interpolateRandom(-0.8F, 0.2F);
/* 103 */     float chest_height = 0.8F + interpolateRandom(-0.6F, 0.2F);
/* 104 */     float leggs_height = 0.15F + interpolateRandom(-0.1F, 0.1F);
/*     */     
/* 106 */     Vec3d head1 = position.func_72441_c(0.0D, head_height, 0.0D);
/* 107 */     Vec3d chest1 = position.func_72441_c(0.0D, chest_height, 0.0D);
/* 108 */     Vec3d legs1 = position.func_72441_c(0.0D, leggs_height, 0.0D);
/*     */     
/* 110 */     return new ArrayList<>(Arrays.asList(new Vec3d[] { head1, chest1, legs1 }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Vec3d> getHitBoxPointsOld(Vec3d position, float fakeBoxScale) {
/* 119 */     float head_height = 1.6F;
/* 120 */     float chest_height = 0.8F;
/* 121 */     float leggs_height = 0.15F;
/*     */     
/* 123 */     Vec3d head1 = position.func_72441_c(0.0D, head_height, 0.0D);
/* 124 */     Vec3d chest1 = position.func_72441_c(0.0D, chest_height, 0.0D);
/* 125 */     Vec3d legs1 = position.func_72441_c(0.0D, leggs_height, 0.0D);
/*     */ 
/*     */     
/* 128 */     return new ArrayList<>(Arrays.asList(new Vec3d[] { head1, chest1, legs1 }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float interpolateRandom(float var0, float var1) {
/* 137 */     return (float)(var0 + (var1 - var0) * Math.random());
/*     */   }
/*     */ 
/*     */   
/*     */   public static Entity getPointedEntity(Vec2f rot, double dst, boolean walls, Entity target) {
/* 142 */     double d0 = dst;
/* 143 */     RayTraceResult objectMouseOver = rayTrace(d0, rot.field_189982_i, rot.field_189983_j, walls);
/* 144 */     Vec3d vec3d = Util.mc.field_71439_g.func_174824_e(1.0F);
/* 145 */     boolean flag = false;
/* 146 */     double d1 = d0;
/* 147 */     if (objectMouseOver != null) {
/* 148 */       d1 = objectMouseOver.field_72307_f.func_72438_d(vec3d);
/*     */     }
/* 150 */     Vec3d vec3d1 = getLook(rot.field_189982_i, rot.field_189983_j);
/* 151 */     Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * d0, vec3d1.field_72448_b * d0, vec3d1.field_72449_c * d0);
/* 152 */     Entity pointedEntity = null;
/* 153 */     Vec3d vec3d3 = null;
/* 154 */     double d2 = d1;
/* 155 */     Entity entity1 = target;
/* 156 */     AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72321_a(entity1.func_70111_Y(), entity1.func_70111_Y(), entity1.func_70111_Y());
/* 157 */     RayTraceResult raytraceresult = axisalignedbb.func_72327_a(vec3d, vec3d2);
/* 158 */     if (axisalignedbb.func_72318_a(vec3d)) {
/* 159 */       if (d2 >= 0.0D) {
/* 160 */         pointedEntity = entity1;
/* 161 */         vec3d3 = (raytraceresult == null) ? vec3d : raytraceresult.field_72307_f;
/* 162 */         d2 = 0.0D;
/*     */       } 
/* 164 */     } else if (raytraceresult != null) {
/* 165 */       double d3 = vec3d.func_72438_d(raytraceresult.field_72307_f);
/*     */       
/* 167 */       if (d3 < d2 || d2 == 0.0D) {
/* 168 */         boolean flag1 = false;
/* 169 */         if (!flag1 && entity1.func_184208_bv() == Util.mc.field_71439_g.func_184208_bv()) {
/* 170 */           if (d2 == 0.0D) {
/* 171 */             pointedEntity = entity1;
/* 172 */             vec3d3 = raytraceresult.field_72307_f;
/*     */           } 
/*     */         } else {
/* 175 */           pointedEntity = entity1;
/* 176 */           vec3d3 = raytraceresult.field_72307_f;
/* 177 */           d2 = d3;
/*     */         } 
/*     */       } 
/*     */     } 
/* 181 */     if (pointedEntity != null && flag && vec3d.func_72438_d(vec3d3) > dst) {
/* 182 */       pointedEntity = null;
/* 183 */       objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, null, new BlockPos(vec3d3));
/*     */     } 
/* 185 */     if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
/* 186 */       objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);
/*     */     }
/* 188 */     return (objectMouseOver != null) ? ((objectMouseOver.field_72308_g instanceof Entity) ? objectMouseOver.field_72308_g : null) : null;
/*     */   }
/*     */   
/*     */   public static RayTraceResult rayTrace(double blockReachDistance, float yaw, float pitch, boolean walls) {
/* 192 */     if (!walls) {
/* 193 */       return null;
/*     */     }
/* 195 */     Vec3d vec3d = Util.mc.field_71439_g.func_174824_e(1.0F);
/* 196 */     Vec3d vec3d1 = getLook(yaw, pitch);
/* 197 */     Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * blockReachDistance, vec3d1.field_72448_b * blockReachDistance, vec3d1.field_72449_c * blockReachDistance);
/* 198 */     return Util.mc.field_71441_e.func_147447_a(vec3d, vec3d2, true, true, true);
/*     */   }
/*     */   
/*     */   static Vec3d getVectorForRotation(float pitch, float yaw) {
/* 202 */     float f = MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F);
/* 203 */     float f1 = MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F);
/* 204 */     float f2 = -MathHelper.func_76134_b(-pitch * 0.017453292F);
/* 205 */     float f3 = MathHelper.func_76126_a(-pitch * 0.017453292F);
/* 206 */     return new Vec3d((f1 * f2), f3, (f * f2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity getMouseOver(Entity target, float yaw, float pitch, double distance, boolean ignoreWalls) {
/* 212 */     Entity entity = Util.mc.func_175606_aa();
/* 213 */     if (entity != null && Util.mc.field_71441_e != null) {
/* 214 */       RayTraceResult objectMouseOver = ignoreWalls ? null : rayTrace(distance, yaw, pitch);
/* 215 */       Vec3d vec3d = entity.func_174824_e(1.0F);
/* 216 */       boolean flag = false;
/* 217 */       double d1 = distance;
/* 218 */       if (distance > 3.0D) {
/* 219 */         flag = true;
/*     */       }
/* 221 */       if (objectMouseOver != null) {
/* 222 */         d1 = objectMouseOver.field_72307_f.func_72438_d(vec3d);
/*     */       }
/* 224 */       Vec3d vec3d1 = getVectorForRotation(pitch, yaw);
/* 225 */       Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * distance, vec3d1.field_72448_b * distance, vec3d1.field_72449_c * distance);
/* 226 */       Entity pointedEntity = null;
/* 227 */       Vec3d vec3d3 = null;
/* 228 */       double d2 = d1;
/* 229 */       AxisAlignedBB axisalignedbb = target.func_174813_aQ().func_72321_a(target.func_70111_Y(), target.func_70111_Y(), target.func_70111_Y());
/* 230 */       RayTraceResult raytraceresult = axisalignedbb.func_72327_a(vec3d, vec3d2);
/* 231 */       if (axisalignedbb.func_72318_a(vec3d)) {
/* 232 */         if (d2 >= 0.0D) {
/* 233 */           pointedEntity = target;
/* 234 */           vec3d3 = (raytraceresult == null) ? vec3d : raytraceresult.field_72307_f;
/* 235 */           d2 = 0.0D;
/*     */         } 
/* 237 */       } else if (raytraceresult != null) {
/* 238 */         double d3 = vec3d.func_72438_d(raytraceresult.field_72307_f);
/*     */         
/* 240 */         if (d3 < d2 || d2 == 0.0D) {
/* 241 */           boolean flag1 = false;
/*     */           
/* 243 */           if (!flag1 && target.func_184208_bv() == entity.func_184208_bv()) {
/* 244 */             if (d2 == 0.0D) {
/* 245 */               pointedEntity = target;
/* 246 */               vec3d3 = raytraceresult.field_72307_f;
/*     */             } 
/*     */           } else {
/* 249 */             pointedEntity = target;
/* 250 */             vec3d3 = raytraceresult.field_72307_f;
/* 251 */             d2 = d3;
/*     */           } 
/*     */         } 
/*     */       } 
/* 255 */       if (pointedEntity != null && flag && vec3d.func_72438_d(vec3d3) > distance) {
/* 256 */         pointedEntity = null;
/* 257 */         objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, null, new BlockPos(vec3d3));
/*     */       } 
/*     */       
/* 260 */       if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
/* 261 */         objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);
/*     */       }
/* 263 */       if (objectMouseOver == null)
/* 264 */         return null; 
/* 265 */       return objectMouseOver.field_72308_g;
/*     */     } 
/* 267 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTrace(double blockReachDistance, float yaw, float pitch) {
/* 272 */     Vec3d vec3d = (Minecraft.func_71410_x()).field_71439_g.func_174824_e(1.0F);
/* 273 */     Vec3d vec3d1 = getVectorForRotation(pitch, yaw);
/* 274 */     Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * blockReachDistance, vec3d1.field_72448_b * blockReachDistance, vec3d1.field_72449_c * blockReachDistance);
/* 275 */     return (Minecraft.func_71410_x()).field_71441_e.func_147447_a(vec3d, vec3d2, true, true, true);
/*     */   }
/*     */   
/*     */   static Vec3d getLook(float yaw, float pitch) {
/* 279 */     return getVectorForRotation(pitch, yaw);
/*     */   }
/*     */   
/*     */   public static ArrayList<Vec3d> getHitBoxPoints(Vec3d position, float fakeBoxScale) {
/* 283 */     Setting<Aura.RayTracingMode> mode = ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).rayTracing;
/* 284 */     switch ((Aura.RayTracingMode)mode.getValue()) {
/*     */       case New:
/* 286 */         return getHitBoxPointsNonJitter(position, fakeBoxScale);
/*     */       case Old:
/* 288 */         return getHitBoxPointsOld(position, fakeBoxScale);
/*     */       case OldJitter:
/* 290 */         return getHitBoxPointsOldJitter(position, fakeBoxScale);
/*     */       case NewJitter:
/* 292 */         return getHitBoxPointsJitter(position, fakeBoxScale);
/*     */     } 
/* 294 */     return getHitBoxPointsNonJitter(position, fakeBoxScale);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d getVecTarget(Entity target, double distance) {
/* 299 */     Vec3d vec = target.func_174791_d().func_178787_e(new Vec3d(0.0D, MathHelper.func_151237_a(target.func_70047_e() * Util.mc.field_71439_g.func_70032_d(target) / (distance + target.field_70130_N), 0.2D, Util.mc.field_71439_g.func_70047_e()), 0.0D));
/* 300 */     if (!isHitBoxVisible(vec)) {
/* 301 */       double i; for (i = target.field_70130_N * 0.05D; i <= target.field_70130_N * 0.95D; i += target.field_70130_N * 0.9D / 8.0D) {
/* 302 */         double j; for (j = target.field_70130_N * 0.05D; j <= target.field_70130_N * 0.95D; j += target.field_70130_N * 0.9D / 8.0D) {
/* 303 */           double k; for (k = 0.0D; k <= target.field_70131_O; k += (target.field_70131_O / 8.0F)) {
/* 304 */             if (isHitBoxVisible((new Vec3d(i, k, j)).func_178787_e(target.func_174791_d().func_178787_e(new Vec3d((-target.field_70130_N / 2.0F), 0.0D, (-target.field_70130_N / 2.0F)))))) {
/* 305 */               vec = (new Vec3d(i, k, j)).func_178787_e(target.func_174791_d().func_178787_e(new Vec3d((-target.field_70130_N / 2.0F), 0.0D, (-target.field_70130_N / 2.0F))));
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 312 */     if (getDistanceFromHead(vec) > distance * distance) {
/* 313 */       return null;
/*     */     }
/* 315 */     return vec;
/*     */   }
/*     */   
/*     */   public static boolean isHitBoxVisible(Vec3d vec3d) {
/* 319 */     Vec3d eyesPos = new Vec3d(Util.mc.field_71439_g.field_70165_t, (Util.mc.field_71439_g.func_174813_aQ()).field_72338_b + Util.mc.field_71439_g.func_70047_e(), Util.mc.field_71439_g.field_70161_v);
/* 320 */     return (Util.mc.field_71441_e.func_147447_a(eyesPos, vec3d, false, true, false) == null);
/*     */   }
/*     */   
/*     */   public static float getDistanceFromHead(Vec3d d1) {
/* 324 */     double x = d1.field_72450_a - Util.mc.field_71439_g.field_70165_t;
/* 325 */     double y = d1.field_72448_b - (Util.mc.field_71439_g.func_174824_e(1.0F)).field_72448_b;
/* 326 */     double z = d1.field_72449_c - Util.mc.field_71439_g.field_70161_v;
/* 327 */     return (float)(Math.pow(x, 2.0D) + Math.pow(y, 2.0D) + Math.pow(z, 2.0D));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\rotations\RayTracingUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */