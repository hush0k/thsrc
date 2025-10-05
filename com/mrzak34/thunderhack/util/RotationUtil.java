/*     */ package com.mrzak34.thunderhack.util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class RotationUtil implements Util {
/*     */   public static EntityPlayer getRotationPlayer() {
/*  18 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*  19 */     return (entityPlayerSP == null) ? (EntityPlayer)mc.field_71439_g : (EntityPlayer)entityPlayerSP;
/*     */   }
/*     */   
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing) {
/*  23 */     return getRotations(pos, facing, (Entity)getRotationPlayer());
/*     */   }
/*     */   
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing, Entity from) {
/*  27 */     return getRotations(pos, facing, from, (IBlockAccess)mc.field_71441_e, mc.field_71441_e.func_180495_p(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing, Entity from, IBlockAccess world, IBlockState state) {
/*  35 */     AxisAlignedBB bb = state.func_185900_c(world, pos);
/*     */     
/*  37 */     double x = pos.func_177958_n() + (bb.field_72340_a + bb.field_72336_d) / 2.0D;
/*  38 */     double y = pos.func_177956_o() + (bb.field_72338_b + bb.field_72337_e) / 2.0D;
/*  39 */     double z = pos.func_177952_p() + (bb.field_72339_c + bb.field_72334_f) / 2.0D;
/*     */     
/*  41 */     if (facing != null) {
/*  42 */       x += facing.func_176730_m().func_177958_n() * (bb.field_72340_a + bb.field_72336_d) / 2.0D;
/*  43 */       y += facing.func_176730_m().func_177956_o() * (bb.field_72338_b + bb.field_72337_e) / 2.0D;
/*  44 */       z += facing.func_176730_m().func_177952_p() * (bb.field_72339_c + bb.field_72334_f) / 2.0D;
/*     */     } 
/*     */     
/*  47 */     return getRotations(x, y, z, from);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z, Entity f) {
/*  51 */     return getRotations(x, y, z, f.field_70165_t, f.field_70163_u, f.field_70161_v, f.func_70047_e());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z, double fromX, double fromY, double fromZ, float fromHeight) {
/*  61 */     double xDiff = x - fromX;
/*  62 */     double yDiff = y - fromY + fromHeight;
/*  63 */     double zDiff = z - fromZ;
/*  64 */     double dist = MathHelper.func_76133_a(xDiff * xDiff + zDiff * zDiff);
/*     */     
/*  66 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/*  67 */     float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/*     */     
/*  69 */     float prevYaw = mc.field_71439_g.field_70177_z;
/*  70 */     float diff = yaw - prevYaw;
/*     */     
/*  72 */     if (diff < -180.0F || diff > 180.0F) {
/*  73 */       float round = Math.round(Math.abs(diff / 360.0F));
/*  74 */       diff = (diff < 0.0F) ? (diff + 360.0F * round) : (diff - 360.0F * round);
/*     */     } 
/*     */     
/*  77 */     return new float[] { prevYaw + diff, pitch };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec2f getRotationTo(Vec3d posTo) {
/*  82 */     EntityPlayerSP player = mc.field_71439_g;
/*  83 */     return (player != null) ? getRotationTo(player.func_174824_e(1.0F), posTo) : Vec2f.field_189974_a;
/*     */   }
/*     */   
/*     */   public static Vec2f getRotationTo(Vec3d posFrom, Vec3d posTo) {
/*  87 */     return getRotationFromVec(posTo.func_178788_d(posFrom));
/*     */   }
/*     */   
/*     */   public static Vec2f getRotationFromVec(Vec3d vec) {
/*  91 */     double lengthXZ = Math.hypot(vec.field_72450_a, vec.field_72449_c);
/*  92 */     double yaw = normalizeAngle(Math.toDegrees(Math.atan2(vec.field_72449_c, vec.field_72450_a)) - 90.0D);
/*  93 */     double pitch = normalizeAngle(Math.toDegrees(-Math.atan2(vec.field_72448_b, lengthXZ)));
/*     */     
/*  95 */     return new Vec2f((float)yaw, (float)pitch);
/*     */   }
/*     */   
/*     */   public static double normalizeAngle(double angle) {
/*  99 */     angle %= 360.0D;
/*     */     
/* 101 */     if (angle >= 180.0D) {
/* 102 */       angle -= 360.0D;
/*     */     }
/*     */     
/* 105 */     if (angle < -180.0D) {
/* 106 */       angle += 360.0D;
/*     */     }
/*     */     
/* 109 */     return angle;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] getNeededRotations(Entity entityLivingBase) {
/* 114 */     double d = entityLivingBase.field_70165_t - Util.mc.field_71439_g.field_70165_t;
/* 115 */     double d2 = entityLivingBase.field_70161_v - Util.mc.field_71439_g.field_70161_v;
/* 116 */     double d3 = entityLivingBase.field_70163_u - (Util.mc.field_71439_g.func_174813_aQ()).field_72338_b + (Util.mc.field_71439_g.func_174813_aQ()).field_72337_e - ((Minecraft.func_71410_x()).field_71439_g.func_174813_aQ()).field_72338_b;
/* 117 */     double d4 = MathHelper.func_76133_a(d * d + d2 * d2);
/* 118 */     float f = (float)(MathHelper.func_181159_b(d2, d) * 180.0D / Math.PI) - 90.0F;
/* 119 */     float f2 = (float)-(MathHelper.func_181159_b(d3, d4) * 180.0D / Math.PI);
/* 120 */     return new float[] { f, f2 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static double angle(Vec3d vec3d, Vec3d other) {
/* 125 */     double lengthSq = vec3d.func_72433_c() * other.func_72433_c();
/*     */     
/* 127 */     if (lengthSq < 1.0E-4D) {
/* 128 */       return 0.0D;
/*     */     }
/*     */     
/* 131 */     double dot = vec3d.func_72430_b(other);
/* 132 */     double arg = dot / lengthSq;
/*     */     
/* 134 */     if (arg > 1.0D)
/* 135 */       return 0.0D; 
/* 136 */     if (arg < -1.0D) {
/* 137 */       return 180.0D;
/*     */     }
/*     */     
/* 140 */     return Math.acos(arg) * 180.0D / Math.PI;
/*     */   }
/*     */   
/*     */   public static double angle(float[] rotation1, float[] rotation2) {
/* 144 */     Vec3d r1Vec = getVec3d(rotation1[0], rotation1[1]);
/* 145 */     Vec3d r2Vec = getVec3d(rotation2[0], rotation2[1]);
/* 146 */     return angle(r1Vec, r2Vec);
/*     */   }
/*     */   
/*     */   public static Vec3d getVec3d(float yaw, float pitch) {
/* 150 */     float vx = -MathHelper.func_76126_a(MathUtil.rad(yaw)) * MathHelper.func_76134_b(MathUtil.rad(pitch));
/* 151 */     float vz = MathHelper.func_76134_b(MathUtil.rad(yaw)) * MathHelper.func_76134_b(MathUtil.rad(pitch));
/* 152 */     float vy = -MathHelper.func_76126_a(MathUtil.rad(pitch));
/* 153 */     return new Vec3d(vx, vy, vz);
/*     */   }
/*     */   
/*     */   public static int getDirection4D() {
/* 157 */     return MathHelper.func_76128_c((mc.field_71439_g.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */   }
/*     */   
/*     */   public static boolean isInFov(Entity entity) {
/* 161 */     return (entity != null && (mc.field_71439_g.func_70068_e(entity) < 4.0D || isInFov(entity.func_174791_d(), mc.field_71439_g.func_174791_d())));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isInFov(Vec3d vec3d, Vec3d other) {
/* 166 */     if (mc.field_71439_g.field_70125_A > 30.0F) {
/* 167 */       if (other.field_72448_b > mc.field_71439_g.field_70163_u) {
/* 168 */         return true;
/*     */       }
/* 170 */     } else if (mc.field_71439_g.field_70125_A < -30.0F && other.field_72448_b < mc.field_71439_g.field_70163_u) {
/* 171 */       return true;
/*     */     } 
/* 173 */     float angle = calcAngleNoY(vec3d, other)[0] - transformYaw();
/* 174 */     if (angle < -270.0F) {
/* 175 */       return true;
/*     */     }
/* 177 */     float fov = mc.field_71474_y.field_74334_X / 2.0F;
/* 178 */     return (angle < fov + 10.0F && angle > -fov - 10.0F);
/*     */   }
/*     */   
/*     */   public static float[] calcAngleNoY(Vec3d from, Vec3d to) {
/* 182 */     double difX = to.field_72450_a - from.field_72450_a;
/* 183 */     double difZ = to.field_72449_c - from.field_72449_c;
/* 184 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D) };
/*     */   }
/*     */   
/*     */   public static float transformYaw() {
/* 188 */     float yaw = mc.field_71439_g.field_70177_z % 360.0F;
/* 189 */     if (mc.field_71439_g.field_70177_z > 0.0F) {
/* 190 */       if (yaw > 180.0F) {
/* 191 */         yaw = -180.0F + yaw - 180.0F;
/*     */       }
/* 193 */     } else if (yaw < -180.0F) {
/* 194 */       yaw = 180.0F + yaw + 180.0F;
/*     */     } 
/* 196 */     if (yaw < 0.0F) {
/* 197 */       return 180.0F + yaw;
/*     */     }
/* 199 */     return -180.0F + yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] calcAngle(Vec3d from, Vec3d to) {
/* 204 */     double difX = to.field_72450_a - from.field_72450_a;
/* 205 */     double difY = (to.field_72448_b - from.field_72448_b) * -1.0D;
/* 206 */     double difZ = to.field_72449_c - from.field_72449_c;
/* 207 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/* 208 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */