/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AimAssist
/*     */   extends Module
/*     */ {
/*     */   public static EntityLivingBase target;
/*     */   public static int deltaX;
/*     */   public static int deltaY;
/*  23 */   public Setting<Boolean> players = register(new Setting("Players", Boolean.valueOf(true)));
/*  24 */   public Setting<Float> strength = register(new Setting("Strength", Float.valueOf(40.0F), Float.valueOf(1.0F), Float.valueOf(50.0F)));
/*  25 */   public Setting<Float> range = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(0.1F), Float.valueOf(10.0F)));
/*  26 */   public Setting<Boolean> dead = register(new Setting("Dead", Boolean.valueOf(false)));
/*  27 */   public Setting<Boolean> invisibles = register(new Setting("Invisibles", Boolean.valueOf(false)));
/*  28 */   public Setting<Boolean> teams = register(new Setting("Teams", Boolean.valueOf(true)));
/*  29 */   public Setting<Boolean> nonPlayers = register(new Setting("NonPlayerslayers", Boolean.valueOf(true)));
/*  30 */   public Setting<Boolean> vertical = register(new Setting("Vertical", Boolean.valueOf(true)));
/*  31 */   public Setting<Boolean> onlyClick = register(new Setting("Clicking", Boolean.valueOf(true)));
/*  32 */   public Setting<Float> fov = register(new Setting("FOV", Float.valueOf(180.0F), Float.valueOf(5.0F), Float.valueOf(180.0F)));
/*  33 */   private final Setting<sortEn> sort = register(new Setting("TargetMode", sortEn.Distance));
/*     */   
/*     */   public AimAssist() {
/*  36 */     super("AimAssist", "AimAssist", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
/*  40 */     double diffZ = entityLiving.field_70161_v - mc.field_71439_g.field_70161_v;
/*  41 */     double diffX = entityLiving.field_70165_t - mc.field_71439_g.field_70165_t;
/*  42 */     float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
/*  43 */     double difference = angleDifference(yaw, mc.field_71439_g.field_70177_z);
/*  44 */     return (difference <= scope);
/*     */   }
/*     */   
/*     */   public static double angleDifference(double a, double b) {
/*  48 */     float yaw360 = (float)(Math.abs(a - b) % 360.0D);
/*  49 */     if (yaw360 > 180.0F) {
/*  50 */       yaw360 = 360.0F - yaw360;
/*     */     }
/*  52 */     return yaw360;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPreMotion(EventSync event) {
/*  58 */     target = getClosest(((Float)this.range.getValue()).floatValue());
/*     */     
/*  60 */     float s = ((Float)this.strength.getMax()).floatValue() - ((Float)this.strength.getValue()).floatValue() + 1.0F;
/*     */     
/*  62 */     if (target == null || !mc.field_71439_g.func_70685_l((Entity)target)) {
/*  63 */       deltaX = deltaY = 0;
/*     */       
/*     */       return;
/*     */     } 
/*  67 */     float[] rotations = getRotations();
/*     */     
/*  69 */     float targetYaw = (float)(rotations[0] + Math.random());
/*  70 */     float targetPitch = (float)(rotations[1] + Math.random());
/*     */     
/*  72 */     float niggaYaw = (targetYaw - mc.field_71439_g.field_70177_z) / Math.max(2.0F, s);
/*  73 */     float niggaPitch = (targetPitch - mc.field_71439_g.field_70125_A) / Math.max(2.0F, s);
/*     */     
/*  75 */     float f = mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
/*  76 */     float gcd = f * f * f * 8.0F;
/*     */     
/*  78 */     deltaX = Math.round(niggaYaw / gcd);
/*     */     
/*  80 */     if (((Boolean)this.vertical.getValue()).booleanValue()) { deltaY = Math.round(niggaPitch / gcd); }
/*  81 */     else { deltaY = 0; }
/*     */   
/*     */   }
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  86 */     if (target == null) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     float f = mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
/*  91 */     float gcd = f * f * f * 8.0F;
/*     */     
/*  93 */     int i = mc.field_71474_y.field_74338_d ? -1 : 1;
/*  94 */     this; this; float f2 = (mc.field_71417_B.field_74377_a + deltaX - mc.field_71417_B.field_74377_a) * gcd;
/*  95 */     this; this; float f3 = ((Boolean)this.vertical.getValue()).booleanValue() ? ((mc.field_71417_B.field_74375_b - deltaY - mc.field_71417_B.field_74375_b) * gcd) : 0.0F;
/*     */     
/*  97 */     if (!((Boolean)this.onlyClick.getValue()).booleanValue() || (Mouse.isButtonDown(0) && mc.field_71462_r == null)) {
/*  98 */       mc.field_71439_g.field_70177_z += f2;
/*  99 */       mc.field_71439_g.field_70125_A += f3 * i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 105 */     deltaX = 0;
/* 106 */     deltaY = 0;
/*     */   }
/*     */   
/*     */   private EntityLivingBase getClosest(double range) {
/* 110 */     if (mc.field_71441_e == null) return null;
/*     */     
/* 112 */     double dist = range;
/* 113 */     EntityLivingBase target = null;
/*     */     
/* 115 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/*     */       
/* 117 */       if (entity instanceof EntityLivingBase) {
/* 118 */         EntityLivingBase livingBase = (EntityLivingBase)entity;
/*     */         
/* 120 */         if (canAttack(livingBase)) {
/* 121 */           double currentDist = mc.field_71439_g.func_70032_d((Entity)livingBase);
/*     */           
/* 123 */           if (currentDist <= dist) {
/* 124 */             dist = currentDist;
/* 125 */             target = livingBase;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     return target;
/*     */   }
/*     */   
/*     */   private boolean canAttack(EntityLivingBase player) {
/* 135 */     if (player instanceof net.minecraft.entity.player.EntityPlayer && !((Boolean)this.players.getValue()).booleanValue()) {
/* 136 */       return false;
/*     */     }
/*     */     
/* 139 */     if (!canSeeEntityAtFov((Entity)player, ((Float)this.fov.getValue()).floatValue() * 2.0F)) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     if ((player instanceof net.minecraft.entity.passive.EntityAnimal || player instanceof net.minecraft.entity.monster.EntityMob || player instanceof net.minecraft.entity.passive.EntityVillager) && 
/* 144 */       !((Boolean)this.nonPlayers.getValue()).booleanValue()) {
/* 145 */       return false;
/*     */     }
/*     */     
/* 148 */     if (player.func_82150_aj() && !((Boolean)this.invisibles.getValue()).booleanValue()) {
/* 149 */       return false;
/*     */     }
/* 151 */     if (player.field_70128_L && !((Boolean)this.dead.getValue()).booleanValue()) {
/* 152 */       return false;
/*     */     }
/* 154 */     if (player.func_184191_r((Entity)mc.field_71439_g) && ((Boolean)this.teams.getValue()).booleanValue()) {
/* 155 */       return false;
/*     */     }
/* 157 */     if (player.field_70173_aa < 2) {
/* 158 */       return false;
/*     */     }
/* 160 */     if (Thunderhack.friendManager.isFriend(player.func_70005_c_())) {
/* 161 */       return false;
/*     */     }
/* 163 */     return (mc.field_71439_g != player);
/*     */   }
/*     */   
/*     */   private float[] getRotations() {
/* 167 */     double var4 = target.field_70165_t - target.field_70142_S - target.field_70165_t + 0.01D - mc.field_71439_g.field_70165_t;
/* 168 */     double var6 = target.field_70161_v - target.field_70136_U - target.field_70161_v - mc.field_71439_g.field_70161_v;
/* 169 */     double var8 = target.field_70163_u - target.field_70137_T - target.field_70163_u + 0.4D + target.func_70047_e() / 1.3D - mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e();
/*     */     
/* 171 */     double var14 = MathHelper.func_76133_a(var4 * var4 + var6 * var6);
/*     */     
/* 173 */     float yaw = (float)(Math.atan2(var6, var4) * 180.0D / Math.PI) - 90.0F;
/* 174 */     float pitch = (float)-(Math.atan2(var8, var14) * 180.0D / Math.PI);
/*     */     
/* 176 */     yaw = mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z);
/* 177 */     pitch = mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A);
/* 178 */     float[] rotations = { yaw, pitch };
/* 179 */     float[] lastRotations = { yaw, pitch };
/* 180 */     float[] fixedRotations = getFixedRotation(rotations, lastRotations);
/* 181 */     yaw = fixedRotations[0];
/* 182 */     pitch = fixedRotations[1];
/* 183 */     pitch = MathHelper.func_76131_a(pitch, -90.0F, 90.0F);
/* 184 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public float[] getFixedRotation(float[] rotations, float[] lastRotations) {
/* 188 */     Minecraft mc = Minecraft.func_71410_x();
/*     */     
/* 190 */     float yaw = rotations[0];
/* 191 */     float pitch = rotations[1];
/*     */     
/* 193 */     float lastYaw = lastRotations[0];
/* 194 */     float lastPitch = lastRotations[1];
/*     */     
/* 196 */     float f = mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
/* 197 */     float gcd = f * f * f * 1.2F;
/*     */     
/* 199 */     float deltaYaw = yaw - lastYaw;
/* 200 */     float deltaPitch = pitch + lastPitch;
/*     */     
/* 202 */     float fixedDeltaYaw = deltaYaw - deltaYaw % gcd;
/* 203 */     float fixedDeltaPitch = deltaPitch - deltaPitch % gcd;
/*     */     
/* 205 */     float fixedYaw = lastYaw + fixedDeltaYaw;
/* 206 */     float fixedPitch = lastPitch + fixedDeltaPitch;
/*     */     
/* 208 */     return new float[] { fixedYaw, fixedPitch };
/*     */   }
/*     */   
/*     */   public enum sortEn
/*     */   {
/* 213 */     Distance, HigherArmor, BlockingStatus, LowestArmor, Health, Angle, HurtTime;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AimAssist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */