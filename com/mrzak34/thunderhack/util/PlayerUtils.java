/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerUtils
/*     */ {
/*  21 */   private static final Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */   
/*     */   public static boolean isPlayerMoving() {
/*  25 */     return (mc.field_71474_y.field_74351_w.func_151470_d() || mc.field_71474_y.field_74368_y.func_151470_d() || mc.field_71474_y.field_74366_z.func_151470_d() || mc.field_71474_y.field_74370_x.func_151470_d());
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getDistance(BlockPos pos) {
/*  30 */     return mc.field_71439_g.func_70011_f(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getDistance(EntityPlayer pos) {
/*  35 */     return mc.field_71439_g.func_70032_d((Entity)pos);
/*     */   }
/*     */   
/*     */   public static EntityPlayer getLookingPlayer(double range) {
/*  39 */     List<EntityPlayer> players = new ArrayList<>(mc.field_71441_e.field_73010_i);
/*  40 */     for (int i = 0; i < players.size(); i++) {
/*     */       
/*  42 */       if (getDistance(players.get(i)) > range)
/*  43 */         players.remove(i); 
/*     */     } 
/*  45 */     players.remove(mc.field_71439_g);
/*  46 */     EntityPlayer target = null;
/*  47 */     Vec3d positionEyes = mc.field_71439_g.func_174824_e(mc.func_184121_ak());
/*  48 */     Vec3d rotationEyes = mc.field_71439_g.func_70676_i(mc.func_184121_ak());
/*  49 */     int precision = 2;
/*  50 */     for (int j = 0; j < (int)range; j++) {
/*  51 */       for (int k = precision; k > 0; k--) {
/*  52 */         for (EntityPlayer targetTemp : players) {
/*  53 */           AxisAlignedBB playerBox = targetTemp.func_174813_aQ();
/*  54 */           double xArray = positionEyes.field_72450_a + rotationEyes.field_72450_a * j + rotationEyes.field_72450_a / k;
/*  55 */           double yArray = positionEyes.field_72448_b + rotationEyes.field_72448_b * j + rotationEyes.field_72448_b / k;
/*  56 */           double zArray = positionEyes.field_72449_c + rotationEyes.field_72449_c * j + rotationEyes.field_72449_c / k;
/*  57 */           if (playerBox.field_72337_e >= yArray && playerBox.field_72338_b <= yArray && playerBox.field_72336_d >= xArray && playerBox.field_72340_a <= xArray && playerBox.field_72334_f >= zArray && playerBox.field_72339_c <= zArray) {
/*  58 */             target = targetTemp;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayer getNearestPlayer(double range) {
/*  69 */     return mc.field_71441_e.field_73010_i.stream().filter(p -> (mc.field_71439_g.func_70032_d((Entity)p) <= range))
/*  70 */       .filter(p -> (mc.field_71439_g.func_145782_y() != p.func_145782_y()))
/*  71 */       .min(Comparator.comparing(p -> Float.valueOf(mc.field_71439_g.func_70032_d((Entity)p))))
/*  72 */       .orElse(null);
/*     */   }
/*     */   
/*     */   public static double[] directionSpeed(double speed) {
/*  76 */     float forward = mc.field_71439_g.field_71158_b.field_192832_b;
/*  77 */     float side = mc.field_71439_g.field_71158_b.field_78902_a;
/*  78 */     float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
/*     */     
/*  80 */     if (forward != 0.0F) {
/*  81 */       if (side > 0.0F) {
/*  82 */         yaw += ((forward > 0.0F) ? -45 : 45);
/*  83 */       } else if (side < 0.0F) {
/*  84 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/*  86 */       side = 0.0F;
/*  87 */       if (forward > 0.0F) {
/*  88 */         forward = 1.0F;
/*  89 */       } else if (forward < 0.0F) {
/*  90 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/*  95 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/*  96 */     double posX = forward * speed * cos + side * speed * sin;
/*  97 */     double posZ = forward * speed * sin - side * speed * cos;
/*  98 */     return new double[] { posX, posZ };
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPlayerAboveVoid() {
/* 103 */     boolean aboveVoid = false;
/* 104 */     if (mc.field_71439_g.field_70163_u <= 0.0D) return true; 
/* 105 */     for (int i = 1; i < mc.field_71439_g.field_70163_u; i++) {
/* 106 */       BlockPos pos = new BlockPos(mc.field_71439_g.field_70165_t, i, mc.field_71439_g.field_70161_v);
/* 107 */       if (mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockAir) {
/* 108 */         aboveVoid = true;
/*     */       } else {
/* 110 */         aboveVoid = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 115 */     return aboveVoid;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
/* 120 */     double dirx = me.field_70165_t - px;
/* 121 */     double diry = me.field_70163_u - py;
/* 122 */     double dirz = me.field_70161_v - pz;
/*     */     
/* 124 */     double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
/*     */     
/* 126 */     dirx /= len;
/* 127 */     diry /= len;
/* 128 */     dirz /= len;
/*     */     
/* 130 */     double pitch = Math.asin(diry);
/* 131 */     double yaw = Math.atan2(dirz, dirx);
/*     */ 
/*     */     
/* 134 */     pitch = pitch * 180.0D / Math.PI;
/* 135 */     yaw = yaw * 180.0D / Math.PI;
/*     */     
/* 137 */     yaw += 90.0D;
/*     */     
/* 139 */     return new double[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static boolean isKeyDown(int i) {
/* 143 */     if (i != 0 && i < 256) {
/* 144 */       return (i < 0) ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i);
/*     */     }
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPos getPlayerPos() {
/* 151 */     return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void centerPlayer(Vec3d centeredBlock) {
/* 156 */     double xDeviation = Math.abs(centeredBlock.field_72450_a - mc.field_71439_g.field_70165_t);
/* 157 */     double zDeviation = Math.abs(centeredBlock.field_72449_c - mc.field_71439_g.field_70161_v);
/*     */     
/* 159 */     if (xDeviation <= 0.1D && zDeviation <= 0.1D) {
/* 160 */       double newX = -2.0D;
/* 161 */       double newZ = -2.0D;
/* 162 */       int xRel = (mc.field_71439_g.field_70165_t < 0.0D) ? -1 : 1;
/* 163 */       int zRel = (mc.field_71439_g.field_70161_v < 0.0D) ? -1 : 1;
/* 164 */       if (BlockUtils.getBlock(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v) instanceof net.minecraft.block.BlockAir) {
/* 165 */         if (Math.abs(mc.field_71439_g.field_70165_t % 1.0D) * 100.0D <= 30.0D) {
/* 166 */           newX = Math.round(mc.field_71439_g.field_70165_t - 0.3D * xRel) + 0.5D * -xRel;
/* 167 */         } else if (Math.abs(mc.field_71439_g.field_70165_t % 1.0D) * 100.0D >= 70.0D) {
/* 168 */           newX = Math.round(mc.field_71439_g.field_70165_t + 0.3D * xRel) - 0.5D * -xRel;
/*     */         } 
/* 170 */         if (Math.abs(mc.field_71439_g.field_70161_v % 1.0D) * 100.0D <= 30.0D) {
/* 171 */           newZ = Math.round(mc.field_71439_g.field_70161_v - 0.3D * zRel) + 0.5D * -zRel;
/* 172 */         } else if (Math.abs(mc.field_71439_g.field_70161_v % 1.0D) * 100.0D >= 70.0D) {
/* 173 */           newZ = Math.round(mc.field_71439_g.field_70161_v + 0.3D * zRel) - 0.5D * -zRel;
/*     */         } 
/*     */       } 
/*     */       
/* 177 */       if (newX == -2.0D) {
/* 178 */         if (mc.field_71439_g.field_70165_t > Math.round(mc.field_71439_g.field_70165_t)) {
/* 179 */           newX = Math.round(mc.field_71439_g.field_70165_t) + 0.5D;
/*     */         
/*     */         }
/* 182 */         else if (mc.field_71439_g.field_70165_t < Math.round(mc.field_71439_g.field_70165_t)) {
/* 183 */           newX = Math.round(mc.field_71439_g.field_70165_t) - 0.5D;
/*     */         } else {
/* 185 */           newX = mc.field_71439_g.field_70165_t;
/*     */         } 
/*     */       }
/* 188 */       if (newZ == -2.0D) {
/* 189 */         if (mc.field_71439_g.field_70161_v > Math.round(mc.field_71439_g.field_70161_v)) {
/* 190 */           newZ = Math.round(mc.field_71439_g.field_70161_v) + 0.5D;
/* 191 */         } else if (mc.field_71439_g.field_70161_v < Math.round(mc.field_71439_g.field_70161_v)) {
/* 192 */           newZ = Math.round(mc.field_71439_g.field_70161_v) - 0.5D;
/*     */         } else {
/* 194 */           newZ = mc.field_71439_g.field_70161_v;
/*     */         } 
/*     */       }
/* 197 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(newX, mc.field_71439_g.field_70163_u, newZ, true));
/* 198 */       mc.field_71439_g.func_70107_b(newX, mc.field_71439_g.field_70163_u, newZ);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\PlayerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */