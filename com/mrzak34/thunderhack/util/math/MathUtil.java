/*     */ package com.mrzak34.thunderhack.util.math;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MathUtil
/*     */   implements Util
/*     */ {
/*     */   public static double random(double min, double max) {
/*  15 */     return Math.random() * (max - min) + min;
/*     */   }
/*     */   
/*     */   public static float random(float min, float max) {
/*  19 */     return (float)(Math.random() * (max - min) + min);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float round2(double value) {
/*  24 */     BigDecimal bd = new BigDecimal(value);
/*  25 */     bd = bd.setScale(2, RoundingMode.HALF_UP);
/*  26 */     return bd.floatValue();
/*     */   }
/*     */   
/*     */   public static double angle(Vec3d vec3d, Vec3d other) {
/*  30 */     double lengthSq = vec3d.func_72433_c() * other.func_72433_c();
/*     */     
/*  32 */     if (lengthSq < 1.0E-4D) {
/*  33 */       return 0.0D;
/*     */     }
/*     */     
/*  36 */     double dot = vec3d.func_72430_b(other);
/*  37 */     double arg = dot / lengthSq;
/*     */     
/*  39 */     if (arg > 1.0D)
/*  40 */       return 0.0D; 
/*  41 */     if (arg < -1.0D) {
/*  42 */       return 180.0D;
/*     */     }
/*     */     
/*  45 */     return Math.acos(arg) * 180.0D / Math.PI;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d fromTo(Vec3d from, double x, double y, double z) {
/*  50 */     return fromTo(from.field_72450_a, from.field_72448_b, from.field_72449_c, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float lerp(float f, float st, float en) {
/*  55 */     return st + f * (en - st);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d fromTo(double x, double y, double z, double x2, double y2, double z2) {
/*  60 */     return new Vec3d(x2 - x, y2 - y, z2 - z);
/*     */   }
/*     */   
/*     */   public static float rad(float angle) {
/*  64 */     return (float)(angle * Math.PI / 180.0D);
/*     */   }
/*     */   
/*     */   public static int clamp(int num, int min, int max) {
/*  68 */     return (num < min) ? min : Math.min(num, max);
/*     */   }
/*     */   
/*     */   public static float clamp(float num, float min, float max) {
/*  72 */     return (num < min) ? min : Math.min(num, max);
/*     */   }
/*     */   
/*     */   public static double clamp(double num, double min, double max) {
/*  76 */     return (num < min) ? min : Math.min(num, max);
/*     */   }
/*     */   
/*     */   public static float sin(float value) {
/*  80 */     return MathHelper.func_76126_a(value);
/*     */   }
/*     */   
/*     */   public static float cos(float value) {
/*  84 */     return MathHelper.func_76134_b(value);
/*     */   }
/*     */   
/*     */   public static float wrapDegrees(float value) {
/*  88 */     return MathHelper.func_76142_g(value);
/*     */   }
/*     */   
/*     */   public static double wrapDegrees(double value) {
/*  92 */     return MathHelper.func_76138_g(value);
/*     */   }
/*     */   
/*     */   public static double square(double input) {
/*  96 */     return input * input;
/*     */   }
/*     */   
/*     */   public static double round(double value, int places) {
/* 100 */     if (places < 0) {
/* 101 */       throw new IllegalArgumentException();
/*     */     }
/* 103 */     BigDecimal bd = BigDecimal.valueOf(value);
/* 104 */     bd = bd.setScale(places, RoundingMode.FLOOR);
/* 105 */     return bd.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d direction(float yaw) {
/* 111 */     return new Vec3d(Math.cos(degToRad((yaw + 90.0F))), 0.0D, Math.sin(degToRad((yaw + 90.0F))));
/*     */   }
/*     */   
/*     */   public static float round(float value, int places) {
/* 115 */     if (places < 0) {
/* 116 */       throw new IllegalArgumentException();
/*     */     }
/* 118 */     BigDecimal bd = BigDecimal.valueOf(value);
/* 119 */     bd = bd.setScale(places, RoundingMode.HALF_UP);
/* 120 */     return bd.floatValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static double degToRad(double deg) {
/* 125 */     return deg * 0.01745329238474369D;
/*     */   }
/*     */   
/*     */   public static double[] directionSpeed(double speed) {
/* 129 */     float forward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 130 */     float side = mc.field_71439_g.field_71158_b.field_78902_a;
/* 131 */     float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
/* 132 */     if (forward != 0.0F) {
/* 133 */       if (side > 0.0F) {
/* 134 */         yaw += ((forward > 0.0F) ? -45 : 45);
/* 135 */       } else if (side < 0.0F) {
/* 136 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/* 138 */       side = 0.0F;
/* 139 */       if (forward > 0.0F) {
/* 140 */         forward = 1.0F;
/* 141 */       } else if (forward < 0.0F) {
/* 142 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     side = clamp(side, -1.0F, 1.0F);
/*     */ 
/*     */     
/* 149 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 150 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 151 */     double posX = forward * speed * cos + side * speed * sin;
/* 152 */     double posZ = forward * speed * sin - side * speed * cos;
/* 153 */     return new double[] { posX, posZ };
/*     */   }
/*     */   
/*     */   public static float[] calcAngle(Vec3d from, Vec3d to) {
/* 157 */     double difX = to.field_72450_a - from.field_72450_a;
/* 158 */     double difY = (to.field_72448_b - from.field_72448_b) * -1.0D;
/* 159 */     double difZ = to.field_72449_c - from.field_72449_c;
/* 160 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/* 161 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
/*     */   }
/*     */   
/*     */   public static double roundToClosest(double num, double low, double high) {
/* 165 */     double d1 = num - low;
/* 166 */     double d2 = high - num;
/*     */     
/* 168 */     if (d2 > d1) {
/* 169 */       return low;
/*     */     }
/* 171 */     return high;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\math\MathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */