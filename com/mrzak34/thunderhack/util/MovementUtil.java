/*     */ package com.mrzak34.thunderhack.util;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class MovementUtil
/*     */   implements Util {
/*     */   public static boolean isMoving() {
/*  13 */     return (mc.field_71439_g.field_191988_bg != 0.0D || mc.field_71439_g.field_70702_br != 0.0D);
/*     */   }
/*     */   
/*     */   public static void strafe(EventMove event, double speed) {
/*  17 */     if (isMoving()) {
/*  18 */       double[] strafe = strafe(speed);
/*  19 */       event.set_x(strafe[0]);
/*  20 */       event.set_z(strafe[1]);
/*     */     } else {
/*  22 */       event.set_x(0.0D);
/*  23 */       event.set_z(0.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double getSpeed() {
/*  28 */     return Math.hypot(mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70179_y);
/*     */   }
/*     */   
/*     */   public static double[] strafe(double speed) {
/*  32 */     return strafe((Entity)mc.field_71439_g, speed);
/*     */   }
/*     */   
/*     */   public static double[] strafe(Entity entity, double speed) {
/*  36 */     return strafe(entity, mc.field_71439_g.field_71158_b, speed);
/*     */   }
/*     */   
/*     */   public static double[] strafe(Entity entity, MovementInput movementInput, double speed) {
/*  40 */     float moveForward = movementInput.field_192832_b;
/*  41 */     float moveStrafe = movementInput.field_78902_a;
/*     */ 
/*     */     
/*  44 */     float rotationYaw = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * mc.func_184121_ak();
/*     */     
/*  46 */     if (moveForward != 0.0F) {
/*  47 */       if (moveStrafe > 0.0F) {
/*  48 */         rotationYaw += ((moveForward > 0.0F) ? -45 : 45);
/*  49 */       } else if (moveStrafe < 0.0F) {
/*  50 */         rotationYaw += ((moveForward > 0.0F) ? 45 : -45);
/*     */       } 
/*  52 */       moveStrafe = 0.0F;
/*  53 */       if (moveForward > 0.0F) {
/*  54 */         moveForward = 1.0F;
/*  55 */       } else if (moveForward < 0.0F) {
/*  56 */         moveForward = -1.0F;
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     double posX = moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
/*  61 */     double posZ = moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
/*  62 */     return new double[] { posX, posZ };
/*     */   }
/*     */ 
/*     */   
/*     */   public static double[] forward(double d) {
/*  67 */     float f = (Minecraft.func_71410_x()).field_71439_g.field_71158_b.field_192832_b;
/*  68 */     float f2 = (Minecraft.func_71410_x()).field_71439_g.field_71158_b.field_78902_a;
/*  69 */     float f3 = (Minecraft.func_71410_x()).field_71439_g.field_70126_B + ((Minecraft.func_71410_x()).field_71439_g.field_70177_z - (Minecraft.func_71410_x()).field_71439_g.field_70126_B) * Minecraft.func_71410_x().func_184121_ak();
/*  70 */     if (f != 0.0F) {
/*  71 */       if (f2 > 0.0F) {
/*  72 */         f3 += ((f > 0.0F) ? -45 : 45);
/*  73 */       } else if (f2 < 0.0F) {
/*  74 */         f3 += ((f > 0.0F) ? 45 : -45);
/*     */       } 
/*  76 */       f2 = 0.0F;
/*  77 */       if (f > 0.0F) {
/*  78 */         f = 1.0F;
/*  79 */       } else if (f < 0.0F) {
/*  80 */         f = -1.0F;
/*     */       } 
/*     */     } 
/*  83 */     double d2 = Math.sin(Math.toRadians((f3 + 90.0F)));
/*  84 */     double d3 = Math.cos(Math.toRadians((f3 + 90.0F)));
/*  85 */     double d4 = f * d * d3 + f2 * d * d2;
/*  86 */     double d5 = f * d * d2 - f2 * d * d3;
/*  87 */     return new double[] { d4, d5 };
/*     */   }
/*     */   
/*     */   public static boolean isMoving(EntityLivingBase entityLivingBase) {
/*  91 */     return (entityLivingBase.field_191988_bg != 0.0F || entityLivingBase.field_70702_br != 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setMotion(double speed) {
/*  96 */     double forward = mc.field_71439_g.field_71158_b.field_192832_b;
/*  97 */     double strafe = mc.field_71439_g.field_71158_b.field_78902_a;
/*  98 */     float yaw = mc.field_71439_g.field_70177_z;
/*  99 */     if (forward == 0.0D && strafe == 0.0D) {
/* 100 */       mc.field_71439_g.field_70159_w = 0.0D;
/* 101 */       mc.field_71439_g.field_70179_y = 0.0D;
/*     */     } else {
/* 103 */       if (forward != 0.0D) {
/* 104 */         if (strafe > 0.0D) {
/* 105 */           yaw += ((forward > 0.0D) ? -45 : 45);
/* 106 */         } else if (strafe < 0.0D) {
/* 107 */           yaw += ((forward > 0.0D) ? 45 : -45);
/*     */         } 
/* 109 */         strafe = 0.0D;
/* 110 */         if (forward > 0.0D) {
/* 111 */           forward = 1.0D;
/* 112 */         } else if (forward < 0.0D) {
/* 113 */           forward = -1.0D;
/*     */         } 
/*     */       } 
/* 116 */       double sin = MathHelper.func_76126_a((float)Math.toRadians((yaw + 90.0F)));
/* 117 */       double cos = MathHelper.func_76134_b((float)Math.toRadians((yaw + 90.0F)));
/* 118 */       mc.field_71439_g.field_70159_w = forward * speed * cos + strafe * speed * sin;
/* 119 */       mc.field_71439_g.field_70179_y = forward * speed * sin - strafe * speed * cos;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\MovementUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */