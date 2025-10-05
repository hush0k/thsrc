/*     */ package com.mrzak34.thunderhack.modules.funnygame;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketPlayer;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class GroundBoost
/*     */   extends Module
/*     */ {
/*  22 */   public Setting<Integer> ticks = register(new Setting("RbandDelay", Integer.valueOf(2), Integer.valueOf(2), Integer.valueOf(40)));
/*  23 */   public Setting<Boolean> autoSprint = register(new Setting("AutoSprint", Boolean.valueOf(true)));
/*  24 */   public Setting<Integer> spddd = register(new Setting("Speed", Integer.valueOf(5000), Integer.valueOf(50), Integer.valueOf(5000)));
/*  25 */   public Setting<Boolean> usver = register(new Setting("use", Boolean.valueOf(false)));
/*     */   boolean hasact = false;
/*  27 */   private int rhh = 0;
/*  28 */   private int stage = 0;
/*  29 */   private double moveSpeed = 0.0D;
/*  30 */   private double distance = 0.0D;
/*  31 */   private float startY = 0.0F;
/*     */   public GroundBoost() {
/*  33 */     super("GroundBoost", "Лютейшие спиды-(каппучино+плоскость)", Module.Category.FUNNYGAME);gg
/*     */   }
/*     */   
/*     */   public static boolean isBoxColliding() {
/*  37 */     return (Util.mc.field_71441_e.func_184144_a((Entity)Util.mc.field_71439_g, Util.mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, 0.21D, 0.0D)).size() > 0);
/*     */   }
/*     */   
/*     */   public static double getJumpSpeed() {
/*  41 */     double defaultSpeed = 0.0D;
/*  42 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
/*  43 */       int amplifier = mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c();
/*  44 */       defaultSpeed += (amplifier + 1) * 0.1D;
/*     */     } 
/*     */     
/*  47 */     return defaultSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  52 */     if (this.hasact) {
/*  53 */       mc.field_71474_y.field_74336_f = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*     */     try {
/*  60 */       if (mc.field_71474_y.field_74336_f) {
/*  61 */         this.hasact = true;
/*  62 */         mc.field_71474_y.field_74336_f = false;
/*     */       } 
/*  64 */       this.stage = 2;
/*  65 */       this.distance = 0.0D;
/*  66 */       this.moveSpeed = getBaseMoveSpeed();
/*     */       
/*  68 */       Thunderhack.TICK_TIMER = 1.0F;
/*  69 */       if (((Boolean)this.autoSprint.getValue()).booleanValue() && mc.field_71439_g != null)
/*  70 */         mc.field_71439_g.func_70031_b(false); 
/*  71 */       this.startY = (float)mc.field_71439_g.field_70163_u;
/*  72 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  79 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/*  80 */       this.rhh = ((Integer)this.ticks.getValue()).intValue();
/*  81 */       this.stage = 2;
/*  82 */       this.distance = 0.0D;
/*  83 */       this.moveSpeed = getBaseMoveSpeed();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send e) {
/*  89 */     if (e.getPacket() instanceof CPacketPlayer) {
/*  90 */       if (this.rhh > 0) {
/*     */         return;
/*     */       }
/*  93 */       CPacketPlayer packet = (CPacketPlayer)e.getPacket();
/*  94 */       if (this.stage == 3)
/*  95 */         ((ICPacketPlayer)packet).setY(packet.func_186996_b(0.0D) + (isBoxColliding() ? 0.2D : 0.4D) + getJumpSpeed()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayer(EventSync e) {
/* 101 */     if (this.startY > mc.field_71439_g.field_70163_u) {
/* 102 */       this.startY = 0.0F;
/* 103 */       toggle();
/*     */     } 
/* 105 */     double d3 = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 106 */     double d4 = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/*     */     
/* 108 */     this.distance = Math.sqrt(d3 * d3 + d4 * d4);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMoveEvent(EventMove event) {
/* 113 */     if (fullNullCheck())
/* 114 */       return;  if (mc.field_71439_g.func_184613_cA() || mc.field_71439_g.field_70143_R >= 4.0F)
/* 115 */       return;  if (mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab())
/*     */       return; 
/* 117 */     if (this.rhh > 0) {
/* 118 */       this.rhh--;
/*     */       
/*     */       return;
/*     */     } 
/* 122 */     if (((Boolean)this.autoSprint.getValue()).booleanValue()) {
/* 123 */       mc.field_71439_g.func_70031_b(true);
/*     */     }
/*     */     
/* 126 */     if (!mc.field_71439_g.field_70123_F || checkMove()) {
/* 127 */       if (mc.field_71439_g.field_70122_E) {
/* 128 */         if (this.stage == 2) {
/* 129 */           if (this.rhh > 0)
/* 130 */             this.moveSpeed = getBaseMoveSpeed(); 
/* 131 */           this.moveSpeed *= (((Integer)this.spddd.getValue()).intValue() / 1000.0F);
/* 132 */           this.stage = 3;
/* 133 */         } else if (this.stage == 3) {
/* 134 */           double var = 0.66D * (this.distance - getBaseMoveSpeed());
/* 135 */           this.moveSpeed = this.distance - var;
/* 136 */           this.stage = 2;
/*     */         } 
/*     */       }
/* 139 */       this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
/* 140 */       setVanilaSpeed(event, this.moveSpeed);
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed() {
/* 145 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/* 146 */       return 0.2873D;
/*     */     }
/*     */ 
/*     */     
/* 150 */     double d = 0.2873D;
/* 151 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
/* 152 */       int n = mc.field_71439_g.func_70660_b(MobEffects.field_76424_c).func_76458_c();
/* 153 */       d *= 1.0D + 0.2D * (n + 1);
/*     */     } 
/* 155 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j) && ((Boolean)this.usver.getValue()).booleanValue()) {
/* 156 */       int n = mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c();
/* 157 */       d /= 1.0D + 0.2D * (n + 1);
/*     */     } 
/* 159 */     return d;
/*     */   }
/*     */   
/*     */   public float[] setYaw(float yaw, double niggers) {
/* 163 */     float moveForward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 164 */     float moveStrafe = mc.field_71439_g.field_71158_b.field_78902_a;
/* 165 */     float rotationYaw = yaw;
/*     */     
/* 167 */     if (moveForward == 0.0F && moveStrafe == 0.0F) {
/* 168 */       float[] arrayOfFloat = new float[2];
/* 169 */       arrayOfFloat[0] = 0.0F;
/* 170 */       arrayOfFloat[1] = 0.0F;
/* 171 */       return arrayOfFloat;
/* 172 */     }  if (moveForward != 0.0F) {
/* 173 */       if (moveStrafe >= 1.0F) {
/* 174 */         rotationYaw += (moveForward > 0.0F) ? -45.0F : 45.0F;
/* 175 */         moveStrafe = 0.0F;
/* 176 */       } else if (moveStrafe <= -1.0F) {
/* 177 */         rotationYaw += (moveForward > 0.0F) ? 45.0F : -45.0F;
/* 178 */         moveStrafe = 0.0F;
/*     */       } 
/*     */       
/* 181 */       if (moveForward > 0.0F) {
/* 182 */         moveForward = 1.0F;
/* 183 */       } else if (moveForward < 0.0F) {
/* 184 */         moveForward = -1.0F;
/*     */       } 
/*     */     } 
/* 187 */     moveStrafe = MathUtil.clamp(moveStrafe, -1.0F, 1.0F);
/*     */     
/* 189 */     double motionX = Math.cos(Math.toRadians((rotationYaw + 90.0F)));
/* 190 */     double motionZ = Math.sin(Math.toRadians((rotationYaw + 90.0F)));
/*     */     
/* 192 */     double newX = moveForward * niggers * motionX + moveStrafe * niggers * motionZ;
/* 193 */     double newZ = moveForward * niggers * motionZ - moveStrafe * niggers * motionX;
/*     */     
/* 195 */     float[] ret = new float[2];
/* 196 */     ret[0] = (float)newX;
/* 197 */     ret[1] = (float)newZ;
/* 198 */     return ret;
/*     */   }
/*     */   
/*     */   public float[] getYaw(double niggers) {
/* 202 */     float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
/* 203 */     return setYaw(yaw, niggers);
/*     */   }
/*     */   
/*     */   public double round(double value, int places) {
/* 207 */     BigDecimal b = (new BigDecimal(value)).setScale(places, RoundingMode.HALF_UP);
/* 208 */     return b.doubleValue();
/*     */   }
/*     */   
/*     */   public boolean checkMove() {
/* 212 */     return (mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F);
/*     */   }
/*     */   
/*     */   public void setVanilaSpeed(EventMove event, double speed) {
/* 216 */     float moveForward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 217 */     float moveStrafe = mc.field_71439_g.field_71158_b.field_78902_a;
/* 218 */     float rotationYaw = mc.field_71439_g.field_70177_z;
/*     */     
/* 220 */     if (moveForward == 0.0F && moveStrafe == 0.0F) {
/* 221 */       event.set_x(0.0D);
/* 222 */       event.set_z(0.0D);
/*     */       return;
/*     */     } 
/* 225 */     if (moveForward != 0.0F) {
/* 226 */       if (moveStrafe >= 1.0F) {
/* 227 */         rotationYaw += (moveForward > 0.0F) ? -45.0F : 45.0F;
/* 228 */         moveStrafe = 0.0F;
/* 229 */       } else if (moveStrafe <= -1.0F) {
/* 230 */         rotationYaw += (moveForward > 0.0F) ? 45.0F : -45.0F;
/* 231 */         moveStrafe = 0.0F;
/*     */       } 
/*     */       
/* 234 */       if (moveForward > 0.0F) {
/* 235 */         moveForward = 1.0F;
/* 236 */       } else if (moveForward < 0.0F) {
/* 237 */         moveForward = -1.0F;
/*     */       } 
/*     */     } 
/* 240 */     moveStrafe = MathUtil.clamp(moveStrafe, -1.0F, 1.0F);
/*     */ 
/*     */     
/* 243 */     double motionX = Math.cos(Math.toRadians((rotationYaw + 90.0F)));
/* 244 */     double motionZ = Math.sin(Math.toRadians((rotationYaw + 90.0F)));
/*     */     
/* 246 */     double newX = moveForward * speed * motionX + moveStrafe * speed * motionZ;
/* 247 */     double newZ = moveForward * speed * motionZ - moveStrafe * speed * motionX;
/*     */     
/* 249 */     event.set_x(newX);
/* 250 */     event.set_z(newZ);
/* 251 */     event.setCanceled(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\GroundBoost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */