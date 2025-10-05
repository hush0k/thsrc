/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketPlayerPosLook;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.notification.Notification;
/*     */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class LongJump
/*     */   extends Module {
/*     */   public double Field1990;
/*     */   public double Field1991;
/*  26 */   public int Field1992 = 0;
/*  27 */   public int Field1993 = 0;
/*     */   public boolean jumped = false;
/*     */   public double speedXZ;
/*     */   public double distance;
/*  31 */   public int stage = 0;
/*  32 */   public int ticks = 2;
/*     */   boolean flag = false;
/*  34 */   private final Setting<ModeEn> Mode = register(new Setting("Mode", ModeEn.FunnyGame));
/*  35 */   public Setting<Boolean> usetimer = register(new Setting("Timer", Boolean.valueOf(true), v -> (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)));
/*  36 */   public Setting<Boolean> usver = register(new Setting("JumpBoost", Boolean.valueOf(false), v -> (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)));
/*  37 */   public Setting<Boolean> ongr = register(new Setting("groundSpoof", Boolean.valueOf(false), v -> (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)));
/*  38 */   public Setting<Boolean> ongr2 = register(new Setting("groundSpoofVal", Boolean.valueOf(false), v -> (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)));
/*  39 */   public Setting<Float> speed2 = register(new Setting("Speed", Float.valueOf(1.44F), Float.valueOf(0.0F), Float.valueOf(3.0F), v -> (this.Mode.getValue() == ModeEn.MatrixCustom)));
/*  40 */   public Setting<Float> jumpTimer = register(new Setting("JumpTimer", Float.valueOf(0.6F), Float.valueOf(0.1F), Float.valueOf(2.0F), v -> (this.Mode.getValue() == ModeEn.MatrixCustom)));
/*  41 */   public Setting<Float> spd = register(new Setting("Speed2", Float.valueOf(1.49F), Float.valueOf(0.1F), Float.valueOf(2.0F), v -> (this.Mode.getValue() == ModeEn.MatrixCustom)));
/*  42 */   public Setting<Boolean> dmgkick = register(new Setting("DmgKickProtection", Boolean.valueOf(true), v -> (this.Mode.getValue() == ModeEn.MatrixCustom)));
/*  43 */   public Setting<Boolean> noGround = register(new Setting("Ground", Boolean.valueOf(true), v -> (this.Mode.getValue() == ModeEn.MatrixCustom)));
/*  44 */   public Setting<Boolean> YSpoof = register(new Setting("YSpoof", Boolean.valueOf(true), v -> (this.Mode.getValue() == ModeEn.MatrixCustom)));
/*  45 */   private final Setting<Float> timr = register(new Setting("TimerSpeed", Float.valueOf(1.0F), Float.valueOf(0.5F), Float.valueOf(3.0F), v -> (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)));
/*  46 */   private final Setting<Float> speed = register(new Setting("Speed", Float.valueOf(16.7F), Float.valueOf(5.0F), Float.valueOf(30.0F), v -> (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)));
/*  47 */   private int boostMotion = 0;
/*  48 */   private float startY = 0.0F;
/*     */   public LongJump() {
/*  50 */     super("LongJump", "Догонять попусков-на ez", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static void strafe(float speed) {
/*  54 */     if (!MovementUtil.isMoving())
/*  55 */       return;  double yaw = direction();
/*  56 */     mc.field_71439_g.field_70159_w = -Math.sin(yaw) * speed;
/*  57 */     mc.field_71439_g.field_70179_y = Math.cos(yaw) * speed;
/*     */   }
/*     */   
/*     */   static double direction() {
/*  61 */     double rotationYaw = mc.field_71439_g.field_70177_z;
/*  62 */     if (mc.field_71439_g.field_191988_bg < 0.0F) rotationYaw += 180.0D; 
/*  63 */     double forward = 1.0D;
/*  64 */     if (mc.field_71439_g.field_191988_bg < 0.0F)
/*  65 */     { forward = -0.5D; }
/*  66 */     else if (mc.field_71439_g.field_191988_bg > 0.0F) { forward = 0.5D; }
/*     */     
/*  68 */     if (mc.field_71439_g.field_70702_br > 0.0F) rotationYaw -= 90.0D * forward; 
/*  69 */     if (mc.field_71439_g.field_70702_br < 0.0F) rotationYaw += 90.0D * forward; 
/*  70 */     return Math.toRadians(rotationYaw);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMove(EventMove f4p2) {
/*  75 */     if (this.Mode.getValue() == ModeEn.Default) {
/*  76 */       DefaultOnMove(f4p2);
/*  77 */     } else if (this.Mode.getValue() == ModeEn.FunnyGame) {
/*  78 */       FunnyGameOnMove(f4p2);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketRecive(PacketEvent.Receive e) {
/*  84 */     if (mc.field_71441_e != null && mc.field_71439_g != null && (this.Mode.getValue() == ModeEn.Default || this.Mode.getValue() == ModeEn.FunnyGame)) {
/*  85 */       if (e.getPacket() instanceof SPacketPlayerPosLook) {
/*  86 */         toggle();
/*     */       }
/*  88 */     } else if ((this.Mode.getValue() == ModeEn.NexusGrief || this.Mode.getValue() == ModeEn.MatrixCustom) && 
/*  89 */       mc.field_71462_r == null && e.getPacket() instanceof SPacketPlayerPosLook) {
/*  90 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)e.getPacket();
/*  91 */       ((ISPacketPlayerPosLook)packet).setPitch(mc.field_71439_g.field_70125_A);
/*  92 */       ((ISPacketPlayerPosLook)packet).setYaw(mc.field_71439_g.field_70177_z);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  99 */     if (mc.field_71441_e != null && mc.field_71439_g != null && this.Mode.getValue() == ModeEn.Default) {
/* 100 */       if (mc.field_71439_g.field_70122_E && this.jumped) {
/* 101 */         toggle();
/*     */       }
/* 103 */     } else if (this.Mode.getValue() == ModeEn.MatrixCustom) {
/* 104 */       if (mc.field_71439_g.field_70737_aN > 0 && ((Boolean)this.dmgkick.getValue()).booleanValue()) {
/* 105 */         NotificationManager.publicity("Kick Protection", 2, Notification.Type.ERROR);
/* 106 */         toggle();
/*     */       } 
/*     */       
/* 109 */       if (mc.field_71439_g.field_70122_E) {
/* 110 */         this.flag = true;
/*     */         
/*     */         return;
/*     */       } 
/* 114 */       if (this.boostMotion == 0) {
/* 115 */         double yaw = Math.toRadians(mc.field_71439_g.field_70177_z);
/*     */         
/* 117 */         if (!((Boolean)this.noGround.getValue()).booleanValue())
/* 118 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true)); 
/* 119 */         if (((Boolean)this.YSpoof.getValue()).booleanValue()) {
/* 120 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t + -Math.sin(yaw) * ((Float)this.spd.getValue()).floatValue(), mc.field_71439_g.field_70163_u + 0.41999998688697815D, mc.field_71439_g.field_70161_v + Math.cos(yaw) * ((Float)this.spd.getValue()).floatValue(), false));
/*     */         } else {
/* 122 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t + -Math.sin(yaw) * ((Float)this.spd.getValue()).floatValue(), mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + Math.cos(yaw) * ((Float)this.spd.getValue()).floatValue(), false));
/*     */         } 
/* 124 */         this.boostMotion = 1;
/* 125 */         Thunderhack.TICK_TIMER = ((Float)this.jumpTimer.getValue()).floatValue();
/* 126 */         this.flag = false;
/* 127 */       } else if (this.boostMotion == 2) {
/* 128 */         strafe(((Float)this.speed2.getValue()).floatValue());
/* 129 */         mc.field_71439_g.field_70181_x = 0.41999998688697815D;
/* 130 */         this.boostMotion = 3;
/* 131 */       } else if (this.boostMotion < 5) {
/* 132 */         this.boostMotion++;
/*     */       } else {
/* 134 */         Thunderhack.TICK_TIMER = 1.0F;
/* 135 */         if (this.flag)
/* 136 */           this.boostMotion = 0; 
/*     */       } 
/* 138 */     } else if (this.Mode.getValue() == ModeEn.NexusGrief) {
/* 139 */       if (mc.field_71439_g.field_70737_aN > 0) {
/* 140 */         NotificationManager.publicity("Kick Protection", 2, Notification.Type.ERROR);
/* 141 */         toggle();
/*     */       } 
/* 143 */       if (mc.field_71439_g.field_70122_E) {
/* 144 */         this.flag = true;
/*     */         
/*     */         return;
/*     */       } 
/* 148 */       if (this.boostMotion == 0) {
/* 149 */         double yaw = Math.toRadians(mc.field_71439_g.field_70177_z);
/* 150 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t + -Math.sin(yaw) * 1.5D, mc.field_71439_g.field_70163_u + 0.41999998688697815D, mc.field_71439_g.field_70161_v + Math.cos(yaw) * 1.5D, false));
/* 151 */         this.boostMotion = 1;
/* 152 */         Thunderhack.TICK_TIMER = 0.6F;
/* 153 */         this.flag = false;
/* 154 */       } else if (this.boostMotion == 2) {
/* 155 */         strafe(1.44F);
/* 156 */         mc.field_71439_g.field_70181_x = 0.41999998688697815D;
/* 157 */         this.boostMotion = 3;
/* 158 */       } else if (this.boostMotion < 5) {
/* 159 */         this.boostMotion++;
/*     */       } else {
/* 161 */         Thunderhack.TICK_TIMER = 1.0F;
/* 162 */         if (this.flag) {
/* 163 */           this.boostMotion = 0;
/*     */         }
/*     */       } 
/* 166 */     } else if (this.Mode.getValue() == ModeEn.FunnyGame) {
/* 167 */       if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*     */         return;
/*     */       }
/* 170 */       if (mc.field_71439_g.field_70122_E && this.jumped) {
/* 171 */         Thunderhack.TICK_TIMER = 1.0F;
/* 172 */         toggle();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 179 */     this.boostMotion = 0;
/* 180 */     this.startY = (float)mc.field_71439_g.field_70163_u;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 186 */     this.Field1990 = 0.0D;
/* 187 */     this.Field1991 = 0.0D;
/* 188 */     this.Field1992 = 0;
/* 189 */     this.Field1993 = 0;
/* 190 */     Thunderhack.TICK_TIMER = 1.0F;
/* 191 */     this.speedXZ = 0.0D;
/* 192 */     this.distance = 0.0D;
/* 193 */     this.stage = 0;
/* 194 */     this.ticks = 2;
/* 195 */     this.jumped = false;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPost(EventPostSync f4u2) {
/* 200 */     if (this.Mode.getValue() == ModeEn.Default) {
/* 201 */       DefaultOnPreMotion(f4u2);
/* 202 */     } else if (this.Mode.getValue() == ModeEn.FunnyGame) {
/* 203 */       FGPostMotion(f4u2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void DefaultOnPreMotion(EventPostSync f4u2) {
/* 208 */     double d = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 209 */     double d2 = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/* 210 */     this.Field1991 = Math.sqrt(d * d + d2 * d2);
/* 211 */     if (((Boolean)this.ongr2.getValue()).booleanValue())
/* 212 */       mc.field_71439_g.field_70122_E = ((Boolean)this.ongr.getValue()).booleanValue(); 
/*     */   }
/*     */   
/*     */   public void DefaultOnMove(EventMove f4p2) {
/* 216 */     if (!mc.field_71439_g.field_70123_F && this.Field1993 <= 0 && (mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F)) {
/* 217 */       if (((Boolean)this.usetimer.getValue()).booleanValue()) {
/* 218 */         Thunderhack.TICK_TIMER = ((Float)this.timr.getValue()).floatValue();
/*     */       } else {
/* 220 */         Thunderhack.TICK_TIMER = 1.0F;
/*     */       } 
/*     */       
/* 223 */       if (this.Field1992 == 1 && mc.field_71439_g.field_70124_G) {
/* 224 */         this.Field1990 = 1.0D + getBaseMoveSpeed() - 0.05D;
/* 225 */       } else if (this.Field1992 == 2 && mc.field_71439_g.field_70124_G) {
/* 226 */         mc.field_71439_g.field_70181_x = 0.415D;
/* 227 */         f4p2.set_y(0.415D);
/* 228 */         this.jumped = true;
/* 229 */         this.Field1990 *= (((Float)this.speed.getValue()).floatValue() / 10.0F);
/* 230 */       } else if (this.Field1992 == 3) {
/* 231 */         double d = 0.66D * (this.Field1991 - getBaseMoveSpeed());
/* 232 */         this.Field1990 = this.Field1991 - d;
/*     */       } else {
/* 234 */         this.Field1990 = this.Field1991 - this.Field1991 / 159.0D;
/* 235 */         if (mc.field_71439_g.field_70124_G && this.Field1992 > 3) {
/* 236 */           this.Field1993 = 10;
/* 237 */           this.Field1992 = 1;
/*     */         } 
/*     */       } 
/*     */       
/* 241 */       this.Field1990 = Math.max(this.Field1990, getBaseMoveSpeed());
/* 242 */       Method744(f4p2, this.Field1990);
/* 243 */       f4p2.setCanceled(true);
/* 244 */       this.Field1992++;
/*     */     } else {
/* 246 */       if (this.Field1993 > 0) {
/* 247 */         this.Field1993--;
/*     */       }
/*     */       
/* 250 */       this.Field1992 = 0;
/* 251 */       this.Field1990 = 0.0D;
/* 252 */       this.Field1991 = 0.0D;
/* 253 */       f4p2.set_z(0.0D);
/* 254 */       f4p2.set_x(0.0D);
/* 255 */       f4p2.setCanceled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void Method744(EventMove event, double d) {
/* 261 */     MovementInput movementInput = mc.field_71439_g.field_71158_b;
/* 262 */     double d2 = movementInput.field_192832_b;
/* 263 */     double d3 = movementInput.field_78902_a;
/* 264 */     float f = mc.field_71439_g.field_70177_z;
/* 265 */     if (d2 == 0.0D && d3 == 0.0D) {
/* 266 */       event.set_x(0.0D);
/* 267 */       event.set_z(0.0D);
/*     */     } else {
/* 269 */       if (d2 != 0.0D) {
/* 270 */         if (d3 > 0.0D) {
/* 271 */           f += ((d2 > 0.0D) ? -45 : 45);
/* 272 */         } else if (d3 < 0.0D) {
/* 273 */           f += ((d2 > 0.0D) ? 45 : -45);
/*     */         } 
/*     */         
/* 276 */         d3 = 0.0D;
/* 277 */         if (d2 > 0.0D) {
/* 278 */           d2 = 1.0D;
/* 279 */         } else if (d2 < 0.0D) {
/* 280 */           d2 = -1.0D;
/*     */         } 
/*     */       } 
/* 283 */       event.set_x(d2 * d * Math.cos(Math.toRadians((f + 90.0F))) + d3 * d * Math.sin(Math.toRadians((f + 90.0F))));
/* 284 */       event.set_z(d2 * d * Math.sin(Math.toRadians((f + 90.0F))) - d3 * d * Math.cos(Math.toRadians((f + 90.0F))));
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed() {
/* 289 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/* 290 */       return 0.2873D;
/*     */     }
/*     */ 
/*     */     
/* 294 */     double d = 0.2873D;
/* 295 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
/* 296 */       int n = mc.field_71439_g.func_70660_b(MobEffects.field_76424_c).func_76458_c();
/* 297 */       d *= 1.0D + 0.2D * (n + 1);
/*     */     } 
/* 299 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j) && ((Boolean)this.usver.getValue()).booleanValue()) {
/* 300 */       int n = mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c();
/* 301 */       d /= 1.0D + 0.2D * (n + 1);
/*     */     } 
/* 303 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void FunnyGameOnMove(EventMove f4p2) {
/* 311 */     if (mc.field_71439_g.field_70123_F || !isMovingClient()) {
/* 312 */       this.stage = 0;
/* 313 */       this.ticks = 2;
/* 314 */       f4p2.set_z(0.0D);
/* 315 */       f4p2.set_x(0.0D);
/* 316 */       f4p2.setCanceled(true);
/*     */       return;
/*     */     } 
/* 319 */     if (((Boolean)this.usetimer.getValue()).booleanValue()) {
/* 320 */       Thunderhack.TICK_TIMER = ((Float)this.speed2.getValue()).floatValue();
/*     */     }
/* 322 */     if (this.ticks > 0 && isMovingClient()) {
/* 323 */       this.speedXZ = 0.09D;
/* 324 */       this.ticks--;
/* 325 */     } else if (this.stage == 1 && mc.field_71439_g.field_70124_G && isMovingClient()) {
/* 326 */       this.speedXZ = 1.0D + getBaseMoveSpeed() - 0.05D;
/* 327 */     } else if (this.stage == 2 && mc.field_71439_g.field_70124_G && isMovingClient()) {
/* 328 */       mc.field_71439_g.field_70181_x = 0.415D + isJumpBoost();
/* 329 */       f4p2.set_y(0.415D + isJumpBoost());
/* 330 */       this.speedXZ *= (((Float)this.speed.getValue()).floatValue() / 10.0F);
/* 331 */       this.jumped = true;
/* 332 */     } else if (this.stage == 3) {
/* 333 */       double d = 0.66D * (this.distance - getBaseMoveSpeed());
/* 334 */       this.speedXZ = this.distance - d;
/*     */     } else {
/* 336 */       this.speedXZ = this.distance - this.distance / 159.0D;
/*     */     } 
/*     */ 
/*     */     
/* 340 */     f4p2.setCanceled(true);
/* 341 */     Method744(f4p2, this.speedXZ);
/*     */ 
/*     */     
/* 344 */     List list = mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, mc.field_71439_g.field_70181_x, 0.0D));
/* 345 */     List list2 = mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -0.4D, 0.0D));
/* 346 */     if (!mc.field_71439_g.field_70124_G && (
/* 347 */       list.size() > 0 || 
/* 348 */       list2.size() > 0))
/*     */     {
/* 350 */       if (this.stage > 10) {
/* 351 */         if (this.stage >= 98) {
/* 352 */           mc.field_71439_g.field_70181_x = -0.4D;
/* 353 */           f4p2.set_y(-0.4D);
/* 354 */           this.stage = 0;
/* 355 */           this.ticks = 5;
/*     */         } else {
/* 357 */           mc.field_71439_g.field_70181_x = -0.001D;
/* 358 */           f4p2.set_y(-0.001D);
/*     */         } 
/*     */       }
/*     */     }
/* 362 */     if (this.ticks <= 0 && isMovingClient()) {
/* 363 */       this.stage++;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isMovingClient() {
/* 368 */     return (mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F);
/*     */   }
/*     */   
/*     */   public double isJumpBoost() {
/* 372 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
/* 373 */       return 0.2D;
/*     */     }
/* 375 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void FGPostMotion(EventPostSync f4u2) {
/* 380 */     if (this.startY > mc.field_71439_g.field_70163_u) {
/* 381 */       Thunderhack.TICK_TIMER = 1.0F;
/* 382 */       toggle();
/*     */     } 
/* 384 */     double d = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 385 */     double d2 = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/* 386 */     this.distance = Math.sqrt(d * d + d2 * d2);
/*     */   }
/*     */   
/*     */   public enum ModeEn
/*     */   {
/* 391 */     FunnyGame,
/* 392 */     Default,
/* 393 */     NexusGrief,
/* 394 */     MatrixCustom;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\LongJump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */