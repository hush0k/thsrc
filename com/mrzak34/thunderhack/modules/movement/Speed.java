/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.MatrixMove;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.PyroSpeed;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.SPacketExplosion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class Speed
/*     */   extends Module
/*     */ {
/*     */   public double distance;
/*  35 */   public int Field2015 = 4;
/*     */   public int FunnyGameStage;
/*     */   public boolean flip;
/*  38 */   int velocity = 0;
/*  39 */   int boostticks = 0;
/*     */   boolean isBoosting = false;
/*  41 */   private final Setting<mode> Mode = register(new Setting("Mode", mode.Default));
/*  42 */   public Setting<Integer> bticks = register(new Setting("boostTicks", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(40), v -> (this.Mode.getValue() == mode.Default)));
/*  43 */   public Setting<Boolean> strafeBoost = register(new Setting("StrafeBoost", Boolean.valueOf(false), v -> (this.Mode.getValue() == mode.Default)));
/*  44 */   public Setting<Float> reduction = register(new Setting("reduction ", Float.valueOf(2.0F), Float.valueOf(1.0F), Float.valueOf(10.0F), v -> (this.Mode.getValue() == mode.Default)));
/*  45 */   public Setting<Boolean> usver = register(new Setting("calcJumpBoost", Boolean.valueOf(false), v -> (this.Mode.getValue() == mode.Default)));
/*  46 */   public double defaultBaseSpeed = getBaseMoveSpeed();
/*  47 */   private final Setting<Boolean> autoWalk = register(new Setting("AutoWalk", Boolean.valueOf(false)));
/*  48 */   private final Setting<Boolean> uav = register(new Setting("UseAllVelocity", Boolean.valueOf(false), v -> (this.Mode.getValue() == mode.Default)));
/*     */   private boolean nexus_flip = false;
/*  50 */   private double strictBaseSpeed = 0.2873D;
/*     */   private int strictCounter;
/*  52 */   private int strictStage = 4;
/*  53 */   private int ticksPassed = 0;
/*     */   private int strictTicks;
/*  55 */   private double maxVelocity = 0.0D;
/*  56 */   private final Timer velocityTimer = new Timer();
/*     */   public Speed() {
/*  58 */     super("Speed", "спиды", Module.Category.MOVEMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  63 */     this.defaultBaseSpeed = getBaseMoveSpeed();
/*  64 */     this.Field2015 = 4;
/*  65 */     this.distance = 0.0D;
/*  66 */     this.FunnyGameStage = 0;
/*  67 */     mc.field_71439_g.field_70747_aH = 0.02F;
/*  68 */     Thunderhack.TICK_TIMER = 1.0F;
/*  69 */     this.velocity = 0;
/*  70 */     this.strictTicks = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  75 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*  76 */       toggle();
/*     */       return;
/*     */     } 
/*  79 */     this.strictTicks = 0;
/*  80 */     this.maxVelocity = 0.0D;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPre(EventSync event) {
/*  85 */     if (this.Mode.getValue() == mode.MatrixJumpBoost && 
/*  86 */       MovementUtil.isMoving() && !mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72321_a(0.5D, 0.0D, 0.5D).func_72317_d(0.0D, -1.0D, 0.0D)).isEmpty() && !this.nexus_flip) {
/*  87 */       mc.field_71439_g.field_70122_E = true;
/*  88 */       mc.field_71439_g.func_70664_aZ();
/*  89 */       mc.field_71439_g.field_70747_aH = 0.026523F;
/*     */     } 
/*     */     
/*  92 */     if (mc.field_71439_g.field_70143_R > 0.0F) {
/*  93 */       this.nexus_flip = true;
/*     */     }
/*  95 */     if (!mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -0.2D, 0.0D)).isEmpty() && this.nexus_flip) {
/*  96 */       this.nexus_flip = false;
/*     */     }
/*     */ 
/*     */     
/* 100 */     if (((Boolean)this.strafeBoost.getValue()).booleanValue() && this.isBoosting) {
/*     */       return;
/*     */     }
/* 103 */     if (this.Mode.getValue() == mode.Grief) {
/*     */       return;
/*     */     }
/*     */     
/* 107 */     double d2 = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 108 */     double d3 = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/* 109 */     double d4 = d2 * d2 + d3 * d3;
/* 110 */     this.distance = Math.sqrt(d4);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 116 */     if (fullNullCheck())
/* 117 */       return;  if (event.getPacket() instanceof SPacketEntityVelocity && (
/* 118 */       (SPacketEntityVelocity)event.getPacket()).func_149412_c() == mc.field_71439_g.func_145782_y()) {
/* 119 */       SPacketEntityVelocity pack = (SPacketEntityVelocity)event.getPacket();
/* 120 */       int vX = pack.func_149411_d();
/* 121 */       int vZ = pack.func_149409_f();
/* 122 */       if (vX < 0) vX *= -1; 
/* 123 */       if (vZ < 0) vZ *= -1;
/*     */       
/* 125 */       if (vX + vZ < 3000 && !((Boolean)this.uav.getValue()).booleanValue())
/* 126 */         return;  this.velocity = vX + vZ;
/*     */       
/* 128 */       this.boostticks = ((Integer)this.bticks.getValue()).intValue();
/*     */     } 
/*     */     
/* 131 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/* 132 */       Thunderhack.TICK_TIMER = 1.0F;
/* 133 */       this.strictBaseSpeed = 0.2873D;
/* 134 */       this.strictStage = 4;
/* 135 */       this.strictCounter = 0;
/* 136 */       this.strictTicks = 0;
/* 137 */       this.maxVelocity = 0.0D;
/* 138 */       toggle();
/* 139 */     } else if (event.getPacket() instanceof SPacketExplosion) {
/* 140 */       SPacketExplosion velocity = (SPacketExplosion)event.getPacket();
/* 141 */       this.maxVelocity = Math.sqrt((velocity.func_149149_c() * velocity.func_149149_c() + velocity.func_149147_e() * velocity.func_149147_e()));
/* 142 */       this.velocityTimer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 148 */     if (((Boolean)this.autoWalk.getValue()).booleanValue()) {
/* 149 */       ((IKeyBinding)mc.field_71474_y.field_74351_w).setPressed(true);
/*     */     }
/* 151 */     if (this.Mode.getValue() == mode.Grief) {
/* 152 */       if (!MovementUtil.isMoving()) {
/*     */         return;
/*     */       }
/* 155 */       if (mc.field_71439_g.field_70122_E) {
/* 156 */         mc.field_71439_g.func_70664_aZ();
/* 157 */         Thunderhack.TICK_TIMER = 0.94F;
/*     */       } 
/* 159 */       if (mc.field_71439_g.field_70143_R > 0.7D && mc.field_71439_g.field_70143_R < 1.3D) {
/* 160 */         Thunderhack.TICK_TIMER = 1.8F;
/*     */       }
/*     */     } 
/* 163 */     if (this.Mode.getValue() == mode.ReallyWorld) {
/* 164 */       if (!MovementUtil.isMoving())
/*     */         return; 
/* 166 */       if (mc.field_71439_g.field_70122_E) {
/* 167 */         mc.field_71439_g.func_70664_aZ();
/*     */       }
/* 169 */       if (mc.field_71439_g.field_70143_R <= 0.22D) {
/* 170 */         Thunderhack.TICK_TIMER = 3.5F;
/* 171 */         mc.field_71439_g.field_70747_aH = 0.026523F;
/* 172 */       } else if (mc.field_71439_g.field_70143_R < 1.25D) {
/* 173 */         Thunderhack.TICK_TIMER = 0.47F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getBaseMoveSpeed() {
/* 179 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/* 180 */       return 0.2873D;
/*     */     }
/*     */ 
/*     */     
/* 184 */     double d = 0.2873D;
/* 185 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
/* 186 */       int n = ((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.field_71439_g.func_70660_b(MobEffects.field_76424_c))).func_76458_c();
/* 187 */       d *= 1.0D + 0.2D * (n + 1);
/*     */     } 
/* 189 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j) && ((Boolean)this.usver.getValue()).booleanValue()) {
/* 190 */       int n = ((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.field_71439_g.func_70660_b(MobEffects.field_76430_j))).func_76458_c();
/* 191 */       d /= 1.0D + 0.2D * (n + 1);
/*     */     } 
/* 193 */     if (((Boolean)this.strafeBoost.getValue()).booleanValue() && this.velocity > 0 && this.boostticks > 0) {
/* 194 */       d += (this.velocity / 8000.0F / ((Float)this.reduction.getValue()).floatValue());
/* 195 */       this.boostticks--;
/*     */     } 
/* 197 */     if (this.boostticks == 1) {
/* 198 */       this.velocity = 0;
/*     */     }
/* 200 */     return d;
/*     */   }
/*     */   
/*     */   public double isJumpBoost() {
/* 204 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
/* 205 */       return 0.2D;
/*     */     }
/* 207 */     return 0.0D; } @SubscribeEvent
/*     */   public void onMove(EventMove event) { float forward;
/*     */     float strafe;
/*     */     float yaw;
/*     */     double cos;
/*     */     double sin;
/* 213 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/* 214 */       return;  switch ((mode)this.Mode.getValue()) {
/*     */       case StrafeStrict:
/* 216 */         this.strictCounter++;
/* 217 */         this.strictCounter %= 5;
/*     */         
/* 219 */         if (this.strictCounter != 0) {
/* 220 */           Thunderhack.TICK_TIMER = 1.0F;
/* 221 */         } else if (PlayerUtils.isPlayerMoving()) {
/* 222 */           Thunderhack.TICK_TIMER = 1.3F;
/* 223 */           mc.field_71439_g.field_70159_w *= 1.0199999809265137D;
/* 224 */           mc.field_71439_g.field_70179_y *= 1.0199999809265137D;
/*     */         } 
/*     */         
/* 227 */         if (mc.field_71439_g.field_70122_E && PlayerUtils.isPlayerMoving()) {
/* 228 */           this.strictStage = 2;
/*     */         }
/*     */         
/* 231 */         if (round(mc.field_71439_g.field_70163_u - (int)mc.field_71439_g.field_70163_u) == round(0.138D)) {
/* 232 */           mc.field_71439_g.field_70181_x -= 0.08D;
/* 233 */           event.setY(event.get_y() - 0.09316090325960147D);
/* 234 */           mc.field_71439_g.field_70163_u -= 0.09316090325960147D;
/*     */         } 
/*     */         
/* 237 */         if (this.strictStage == 1 && (mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F)) {
/* 238 */           this.strictStage = 2;
/* 239 */           this.strictBaseSpeed = 1.38D * getBaseMotionSpeed() - 0.01D;
/* 240 */         } else if (this.strictStage == 2) {
/* 241 */           this.strictStage = 3;
/* 242 */           mc.field_71439_g.field_70181_x = 0.399399995803833D;
/* 243 */           event.setY(0.399399995803833D);
/* 244 */           this.strictBaseSpeed *= 2.149D;
/* 245 */         } else if (this.strictStage == 3) {
/* 246 */           this.strictStage = 4;
/* 247 */           double adjustedMotion = 0.66D * (this.distance - getBaseMotionSpeed());
/* 248 */           this.strictBaseSpeed = this.distance - adjustedMotion;
/*     */         } else {
/* 250 */           if (mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, mc.field_71439_g.field_70181_x, 0.0D)).size() > 0 || mc.field_71439_g.field_70124_G)
/* 251 */             this.strictStage = 1; 
/* 252 */           this.strictBaseSpeed = this.distance - this.distance / 159.0D;
/*     */         } 
/*     */         
/* 255 */         this.strictBaseSpeed = Math.max(this.strictBaseSpeed, getBaseMotionSpeed());
/*     */         
/* 257 */         if (this.maxVelocity > 0.0D && ((Boolean)this.strafeBoost.getValue()).booleanValue() && !this.velocityTimer.passedMs(75L) && !mc.field_71439_g.field_70123_F) {
/* 258 */           this.strictBaseSpeed = Math.max(this.strictBaseSpeed, this.maxVelocity);
/*     */         } else {
/* 260 */           this.strictBaseSpeed = Math.min(this.strictBaseSpeed, (this.ticksPassed > 25) ? 0.449D : 0.433D);
/*     */         } 
/*     */         
/* 263 */         forward = mc.field_71439_g.field_71158_b.field_192832_b;
/*     */         
/* 265 */         strafe = mc.field_71439_g.field_71158_b.field_78902_a;
/*     */         
/* 267 */         yaw = mc.field_71439_g.field_70177_z;
/*     */         
/* 269 */         this.ticksPassed++;
/*     */         
/* 271 */         if (this.ticksPassed > 50)
/* 272 */           this.ticksPassed = 0; 
/* 273 */         if (forward == 0.0F && strafe == 0.0F) {
/* 274 */           event.setX(0.0D);
/* 275 */           event.setZ(0.0D);
/* 276 */         } else if (forward != 0.0F) {
/* 277 */           if (strafe >= 1.0F) {
/* 278 */             yaw += ((forward > 0.0F) ? -45 : 45);
/* 279 */             strafe = 0.0F;
/* 280 */           } else if (strafe <= -1.0F) {
/* 281 */             yaw += ((forward > 0.0F) ? 45 : -45);
/* 282 */             strafe = 0.0F;
/*     */           } 
/* 284 */           if (forward > 0.0F) {
/* 285 */             forward = 1.0F;
/* 286 */           } else if (forward < 0.0F) {
/* 287 */             forward = -1.0F;
/*     */           } 
/*     */         } 
/*     */         
/* 291 */         strafe = MathUtil.clamp(strafe, -1.0F, 1.0F);
/*     */         
/* 293 */         cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 294 */         sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/*     */         
/* 296 */         event.setX(forward * this.strictBaseSpeed * cos + strafe * this.strictBaseSpeed * sin);
/* 297 */         event.setZ(forward * this.strictBaseSpeed * sin - strafe * this.strictBaseSpeed * cos);
/*     */         
/* 299 */         if (forward == 0.0F && strafe == 0.0F) {
/* 300 */           event.setX(0.0D);
/* 301 */           event.setZ(0.0D);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case FunnyGameNew:
/* 307 */         if (MovementUtil.isMoving()) {
/* 308 */           Thunderhack.TICK_TIMER = 1.088F;
/* 309 */           if (this.strictStage == 1) {
/* 310 */             this.strictBaseSpeed = 1.35D * getBaseMoveSpeed() - 0.01D;
/* 311 */           } else if (this.strictStage == 2) {
/* 312 */             double jumpSpeed = 0.3999999463558197D;
/* 313 */             if (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j)) {
/* 314 */               double amplifier = mc.field_71439_g.func_70660_b(MobEffects.field_76430_j).func_76458_c();
/* 315 */               jumpSpeed += (amplifier + 1.0D) * 0.1D;
/*     */             } 
/*     */             
/* 318 */             mc.field_71439_g.field_70181_x = jumpSpeed;
/* 319 */             event.setY(jumpSpeed);
/* 320 */             double acceleration = 2.149D;
/* 321 */             this.strictBaseSpeed *= acceleration;
/* 322 */           } else if (this.strictStage == 3) {
/* 323 */             double scaledstrictBaseSpeed = 0.66D * (this.distance - getBaseMoveSpeed());
/* 324 */             this.strictBaseSpeed = this.distance - scaledstrictBaseSpeed;
/*     */           } else {
/* 326 */             if ((mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, mc.field_71439_g.field_70181_x, 0.0D)).size() > 0 || mc.field_71439_g.field_70124_G) && this.strictStage > 0) {
/* 327 */               this.strictStage = MovementUtil.isMoving() ? 1 : 0;
/*     */             }
/* 329 */             this.strictBaseSpeed = this.distance - this.distance / 159.0D;
/*     */           } 
/*     */           
/* 332 */           this.strictBaseSpeed = Math.max(this.strictBaseSpeed, getBaseMoveSpeed());
/*     */           
/* 334 */           double baseStrictSpeed = 0.465D;
/* 335 */           double baseRestrictedSpeed = 0.44D;
/*     */           
/* 337 */           if (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
/* 338 */             double amplifier = mc.field_71439_g.func_70660_b(MobEffects.field_76424_c).func_76458_c();
/* 339 */             baseStrictSpeed *= 1.0D + 0.2D * (amplifier + 1.0D);
/* 340 */             baseRestrictedSpeed *= 1.0D + 0.2D * (amplifier + 1.0D);
/*     */           } 
/*     */           
/* 343 */           if (mc.field_71439_g.func_70644_a(MobEffects.field_76421_d)) {
/* 344 */             double amplifier = mc.field_71439_g.func_70660_b(MobEffects.field_76421_d).func_76458_c();
/* 345 */             baseStrictSpeed /= 1.0D + 0.2D * (amplifier + 1.0D);
/* 346 */             baseRestrictedSpeed /= 1.0D + 0.2D * (amplifier + 1.0D);
/*     */           } 
/*     */ 
/*     */           
/* 350 */           this.strictBaseSpeed = Math.min(this.strictBaseSpeed, (this.strictTicks > 25) ? baseStrictSpeed : baseRestrictedSpeed);
/*     */           
/* 352 */           this.strictTicks++;
/*     */           
/* 354 */           if (this.strictTicks > 50) {
/* 355 */             this.strictTicks = 0;
/*     */           }
/*     */           
/* 358 */           float f1 = mc.field_71439_g.field_71158_b.field_192832_b;
/* 359 */           float f2 = mc.field_71439_g.field_71158_b.field_78902_a;
/* 360 */           float f3 = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
/*     */           
/* 362 */           if (!MovementUtil.isMoving()) {
/* 363 */             event.setX(0.0D);
/* 364 */             event.setZ(0.0D);
/*     */           
/*     */           }
/* 367 */           else if (f1 != 0.0F) {
/* 368 */             if (f2 >= 1.0F) {
/* 369 */               f3 += ((f1 > 0.0F) ? -45 : 45);
/* 370 */               f2 = 0.0F;
/*     */             
/*     */             }
/* 373 */             else if (f2 <= -1.0F) {
/* 374 */               f3 += ((f1 > 0.0F) ? 45 : -45);
/* 375 */               f2 = 0.0F;
/*     */             } 
/*     */             
/* 378 */             if (f1 > 0.0F) {
/* 379 */               f1 = 1.0F;
/*     */             
/*     */             }
/* 382 */             else if (f1 < 0.0F) {
/* 383 */               f1 = -1.0F;
/*     */             } 
/*     */           } 
/*     */           
/* 387 */           double d1 = Math.cos(Math.toRadians(f3));
/* 388 */           double d2 = -Math.sin(Math.toRadians(f3));
/*     */           
/* 390 */           event.setX(f1 * this.strictBaseSpeed * d2 + f2 * this.strictBaseSpeed * d1);
/* 391 */           event.setZ(f1 * this.strictBaseSpeed * d1 - f2 * this.strictBaseSpeed * d2);
/* 392 */           this.strictStage++;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case Default:
/* 398 */         if (event.isCanceled()) {
/*     */           return;
/*     */         }
/* 401 */         if (!PyroSpeed.isMovingClient() || mc.field_71439_g.field_70143_R > 5.0F) {
/*     */           return;
/*     */         }
/* 404 */         if (mc.field_71439_g.field_70123_F) {
/* 405 */           double d; if (mc.field_71439_g.field_70122_E && (d = PyroSpeed.Method5402(1.0D)) == 1.0D) {
/* 406 */             this.FunnyGameStage++;
/*     */           }
/* 408 */           if (this.FunnyGameStage > 0) {
/* 409 */             int n2; int n3; float f; switch (this.FunnyGameStage) {
/*     */               case 1:
/* 411 */                 event.setCanceled(true);
/*     */                 
/* 413 */                 event.set_y(0.41999998688698D);
/* 414 */                 n2 = this.FunnyGameStage;
/* 415 */                 this.FunnyGameStage = n2 + 1;
/*     */                 return;
/*     */               
/*     */               case 2:
/* 419 */                 event.setCanceled(true);
/*     */                 
/* 421 */                 event.set_y(0.33319999363422D);
/* 422 */                 n3 = this.FunnyGameStage;
/* 423 */                 this.FunnyGameStage = n3 + 1;
/*     */                 return;
/*     */               
/*     */               case 3:
/* 427 */                 f = (float)PyroSpeed.Method718();
/* 428 */                 event.set_y(0.24813599859094704D);
/* 429 */                 event.set_x(-MathHelper.func_76126_a(f) * 0.2D);
/* 430 */                 event.set_z(MathHelper.func_76134_b(f) * 0.2D);
/* 431 */                 this.FunnyGameStage = 0;
/* 432 */                 mc.field_71439_g.field_70181_x = 0.0D;
/* 433 */                 event.setCanceled(true);
/*     */                 return;
/*     */             } 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 440 */         this.FunnyGameStage = 0;
/* 441 */         if (this.Field2015 == 1 && (mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F)) {
/* 442 */           this.defaultBaseSpeed = 1.35D * getBaseMoveSpeed() - 0.01D;
/* 443 */         } else if (this.Field2015 == 2 && mc.field_71439_g.field_70124_G) {
/* 444 */           double d = 0.4D;
/* 445 */           double d2 = d;
/* 446 */           mc.field_71439_g.field_70181_x = d2 + isJumpBoost();
/* 447 */           double d3 = d;
/* 448 */           event.set_y(d3 + isJumpBoost());
/* 449 */           this.flip = !this.flip;
/* 450 */           this.defaultBaseSpeed *= this.flip ? 1.6835D : 1.395D;
/* 451 */         } else if (this.Field2015 == 3) {
/* 452 */           double d = 0.66D * (this.distance - getBaseMoveSpeed());
/* 453 */           this.defaultBaseSpeed = this.distance - d;
/*     */         } else {
/* 455 */           List<AxisAlignedBB> list = mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, mc.field_71439_g.field_70181_x, 0.0D));
/* 456 */           if ((list.size() > 0 || mc.field_71439_g.field_70124_G) && this.Field2015 > 0) {
/* 457 */             this.Field2015 = 1;
/*     */           }
/* 459 */           this.defaultBaseSpeed = this.distance - this.distance / 159.0D;
/*     */         } 
/* 461 */         event.setCanceled(true);
/* 462 */         this.defaultBaseSpeed = Math.max(this.defaultBaseSpeed, getBaseMoveSpeed());
/* 463 */         PyroSpeed.Method744(event, this.defaultBaseSpeed);
/* 464 */         this.Field2015++;
/*     */         break;
/*     */     } 
/*     */     
/* 468 */     if (this.Mode.getValue() == mode.StrafeStrict || this.Mode.getValue() == mode.FunnyGameNew) {
/* 469 */       event.setCanceled(true);
/*     */     } }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMove(MatrixMove move) {
/* 475 */     if (this.Mode.getValue() != mode.Matrix) {
/*     */       return;
/*     */     }
/* 478 */     if (!mc.field_71439_g.field_70122_E && move.toGround()) {
/* 479 */       move.setMotionX(move.getMotionX() * 2.0D);
/* 480 */       move.setMotionZ(move.getMotionZ() * 2.0D);
/* 481 */       mc.field_71439_g.field_70159_w *= 2.0D;
/* 482 */       mc.field_71439_g.field_70179_y *= 2.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getBaseMotionSpeed() {
/* 488 */     double baseSpeed = 0.2873D;
/* 489 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76424_c)) {
/* 490 */       int amplifier = ((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.field_71439_g.func_70660_b(MobEffects.field_76424_c))).func_76458_c();
/* 491 */       baseSpeed *= 1.0D + 0.2D * (amplifier + 1.0D);
/*     */     } 
/* 493 */     return baseSpeed;
/*     */   }
/*     */   
/*     */   private double round(double value) {
/* 497 */     BigDecimal bd = new BigDecimal(value);
/* 498 */     bd = bd.setScale(3, RoundingMode.HALF_UP);
/* 499 */     return bd.doubleValue();
/*     */   }
/*     */   
/*     */   public enum mode {
/* 503 */     Default, Grief, StrafeStrict, ReallyWorld, Matrix, MatrixJumpBoost, FunnyGameNew;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Speed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */