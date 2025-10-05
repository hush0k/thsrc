/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSprint;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.MatrixMove;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.manager.EventManager;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Strafe extends Module {
/*     */   public static double oldSpeed;
/*     */   public static double contextFriction;
/*     */   public static boolean needSwap;
/*  35 */   public static float jumpTicks = 0.0F; public static boolean prevSprint; public static boolean needSprintState; public static int counter; public static int noSlowTicks;
/*  36 */   public Setting<Float> setSpeed = register(new Setting("speed", Float.valueOf(1.3F), Float.valueOf(0.0F), Float.valueOf(2.0F)));
/*     */   boolean skip = false;
/*  38 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Matrix));
/*  39 */   public Setting<Boolean> elytra = register(new Setting("ElytraBoost", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.Matrix)));
/*  40 */   public Setting<Boolean> onlyDown = register(new Setting("OnlyDown", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.SunriseFast)));
/*  41 */   private final Setting<Float> maxSpeed = register(new Setting("MaxSpeed", Float.valueOf(0.9F), Float.valueOf(0.0F), Float.valueOf(2.0F), v -> (this.mode.getValue() == Mode.SunriseFast)));
/*  42 */   private float waterTicks = 0.0F;
/*  43 */   private final Timer fixTimer = new Timer();
/*  44 */   private final Timer elytraDelay = new Timer();
/*  45 */   private final Timer startDelay = new Timer();
/*     */   public Strafe() {
/*  47 */     super("Strafe", "testMove", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static float getDirection() {
/*  51 */     float rotationYaw = mc.field_71439_g.field_70177_z;
/*     */     
/*  53 */     float strafeFactor = 0.0F;
/*     */     
/*  55 */     if (mc.field_71439_g.field_71158_b.field_192832_b > 0.0F)
/*  56 */       strafeFactor = 1.0F; 
/*  57 */     if (mc.field_71439_g.field_71158_b.field_192832_b < 0.0F) {
/*  58 */       strafeFactor = -1.0F;
/*     */     }
/*  60 */     if (strafeFactor == 0.0F) {
/*  61 */       if (mc.field_71439_g.field_71158_b.field_78902_a > 0.0F) {
/*  62 */         rotationYaw -= 90.0F;
/*     */       }
/*  64 */       if (mc.field_71439_g.field_71158_b.field_78902_a < 0.0F)
/*  65 */         rotationYaw += 90.0F; 
/*     */     } else {
/*  67 */       if (mc.field_71439_g.field_71158_b.field_78902_a > 0.0F) {
/*  68 */         rotationYaw -= 45.0F * strafeFactor;
/*     */       }
/*  70 */       if (mc.field_71439_g.field_71158_b.field_78902_a < 0.0F) {
/*  71 */         rotationYaw += 45.0F * strafeFactor;
/*     */       }
/*     */     } 
/*  74 */     if (strafeFactor < 0.0F) {
/*  75 */       rotationYaw -= 180.0F;
/*     */     }
/*  77 */     return (float)Math.toRadians(rotationYaw);
/*     */   }
/*     */   
/*     */   public static void setStrafe(double motion) {
/*  81 */     if (!MovementUtil.isMoving())
/*  82 */       return;  double radians = getDirection();
/*  83 */     mc.field_71439_g.field_70159_w = -Math.sin(radians) * motion;
/*  84 */     mc.field_71439_g.field_70179_y = Math.cos(radians) * motion;
/*     */   }
/*     */   
/*     */   public static float getMotion() {
/*  88 */     return (float)Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/*     */   }
/*     */   
/*     */   public static void setMotion(double motion) {
/*  92 */     double forward = mc.field_71439_g.field_71158_b.field_192832_b;
/*  93 */     double strafe = mc.field_71439_g.field_71158_b.field_78902_a;
/*  94 */     float yaw = mc.field_71439_g.field_70177_z;
/*  95 */     if (forward == 0.0D && strafe == 0.0D) {
/*  96 */       mc.field_71439_g.field_70159_w = 0.0D;
/*  97 */       mc.field_71439_g.field_70179_y = 0.0D;
/*  98 */       oldSpeed = 0.0D;
/*     */     } else {
/* 100 */       if (forward != 0.0D) {
/* 101 */         if (strafe > 0.0D) {
/* 102 */           yaw += ((forward > 0.0D) ? -45 : 45);
/* 103 */         } else if (strafe < 0.0D) {
/* 104 */           yaw += ((forward > 0.0D) ? 45 : -45);
/*     */         } 
/* 106 */         strafe = 0.0D;
/* 107 */         if (forward > 0.0D) {
/* 108 */           forward = 1.0D;
/* 109 */         } else if (forward < 0.0D) {
/* 110 */           forward = -1.0D;
/*     */         } 
/*     */       } 
/* 113 */       double cosinus = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 114 */       double sinus = Math.sin(Math.toRadians((yaw + 90.0F)));
/*     */       
/* 116 */       mc.field_71439_g.field_70159_w = forward * motion * cosinus + strafe * motion * sinus;
/* 117 */       mc.field_71439_g.field_70179_y = forward * motion * sinus - strafe * motion * cosinus;
/*     */     } 
/*     */   }
/*     */   public static double calculateSpeed(MatrixMove move) {
/*     */     float n8;
/* 122 */     Minecraft mc = Minecraft.func_71410_x();
/* 123 */     boolean fromGround = mc.field_71439_g.field_70122_E;
/* 124 */     boolean toGround = move.toGround();
/* 125 */     boolean jump = (move.getMotionY() > 0.0D);
/* 126 */     float speedAttributes = getAIMoveSpeed((EntityPlayer)mc.field_71439_g);
/* 127 */     float frictionFactor = getFrictionFactor((EntityPlayer)mc.field_71439_g, move);
/* 128 */     float n6 = (mc.field_71439_g.func_70644_a(MobEffects.field_76430_j) && mc.field_71439_g.func_184587_cr()) ? 0.88F : (float)((oldSpeed > 0.32D && mc.field_71439_g.func_184587_cr()) ? 0.88D : 0.9100000262260437D);
/* 129 */     if (fromGround) {
/* 130 */       n6 = frictionFactor;
/*     */     }
/* 132 */     float n7 = (float)(0.16277135908603668D / Math.pow(n6, 3.01D));
/*     */     
/* 134 */     if (fromGround) {
/* 135 */       n8 = speedAttributes * n7;
/* 136 */       if (jump) {
/* 137 */         n8 += 0.2F;
/*     */       }
/*     */     } else {
/* 140 */       n8 = 0.0255F;
/*     */     } 
/* 142 */     boolean noslow = false;
/* 143 */     double max2 = oldSpeed + n8;
/* 144 */     double max = 0.0D;
/* 145 */     if (mc.field_71439_g.func_184587_cr() && !jump) {
/* 146 */       double n10 = oldSpeed + n8 * 0.25D;
/* 147 */       double motionY2 = move.getMotionY();
/* 148 */       if (motionY2 != 0.0D && Math.abs(motionY2) < 0.08D) {
/* 149 */         n10 += 0.055D;
/*     */       }
/* 151 */       if (max2 > (max = Math.max(0.043D, n10))) {
/* 152 */         noslow = true;
/* 153 */         noSlowTicks++;
/*     */       } else {
/* 155 */         noSlowTicks = Math.max(noSlowTicks - 1, 0);
/*     */       } 
/*     */     } else {
/* 158 */       noSlowTicks = 0;
/*     */     } 
/* 160 */     if (noSlowTicks > 3) {
/* 161 */       max2 = max - 0.019D;
/*     */     } else {
/* 163 */       max2 = Math.max(noslow ? 0.0D : 0.25D, max2) - ((counter++ % 2 == 0) ? 0.001D : 0.002D);
/*     */     } 
/* 165 */     contextFriction = n6;
/* 166 */     if (!toGround && !fromGround) {
/* 167 */       needSwap = true;
/*     */     } else {
/* 169 */       prevSprint = false;
/*     */     } 
/* 171 */     if (!fromGround && !toGround) {
/* 172 */       needSprintState = !((IEntityPlayerSP)mc.field_71439_g).getServerSprintState();
/*     */     }
/* 174 */     if (toGround && fromGround) {
/* 175 */       needSprintState = false;
/*     */     }
/* 177 */     return max2;
/*     */   }
/*     */   
/*     */   public static float getAIMoveSpeed(EntityPlayer contextPlayer) {
/* 181 */     boolean prevSprinting = contextPlayer.func_70051_ag();
/* 182 */     contextPlayer.func_70031_b(false);
/* 183 */     float speed = contextPlayer.func_70689_ay() * 1.3F;
/* 184 */     contextPlayer.func_70031_b(prevSprinting);
/* 185 */     return speed;
/*     */   }
/*     */   
/*     */   private static float getFrictionFactor(EntityPlayer contextPlayer, MatrixMove move) {
/* 189 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185345_c(move.getFromX(), (move.getAABBFrom()).field_72338_b - 1.0D, move.getFromZ());
/* 190 */     return (contextPlayer.field_70170_p.func_180495_p((BlockPos)blockpos$pooledmutableblockpos).func_177230_c()).field_149765_K * 0.91F;
/*     */   }
/*     */   
/*     */   public static int findNullSlot() {
/* 194 */     for (int i = 0; i < 36; i++) {
/* 195 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 196 */       if (stack.func_77973_b() instanceof net.minecraft.item.ItemAir) {
/* 197 */         if (i < 9) {
/* 198 */           i += 36;
/*     */         }
/* 200 */         return i;
/*     */       } 
/*     */     } 
/* 203 */     return 999;
/*     */   }
/*     */   
/*     */   public static void strafe(float speed) {
/* 207 */     if (!MovementUtil.isMoving()) {
/*     */       return;
/*     */     }
/* 210 */     double yaw = getDirection();
/* 211 */     mc.field_71439_g.field_70159_w = -Math.sin(yaw) * speed;
/* 212 */     mc.field_71439_g.field_70179_y = Math.cos(yaw) * speed;
/*     */   }
/*     */   
/*     */   public static void disabler(int elytra) {
/* 216 */     if (elytra != -2) {
/* 217 */       mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 218 */       mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */     } 
/* 220 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 221 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 222 */     if (elytra != -2) {
/* 223 */       mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 224 */       mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerUpdate(PlayerUpdateEvent e) {
/* 230 */     if (this.mode.getValue() == Mode.Matrix) {
/* 231 */       if (!((Boolean)this.elytra.getValue()).booleanValue())
/* 232 */         return;  int elytra = getHotbarSlotOfItem();
/*     */       
/* 234 */       if (mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab() || this.waterTicks > 0.0F || elytra == -1 || ((IEntity)mc.field_71439_g).isInWeb())
/*     */         return; 
/* 236 */       if (mc.field_71439_g.field_70143_R != 0.0F && mc.field_71439_g.field_70143_R < 0.1D && mc.field_71439_g.field_70181_x < -0.1D) {
/* 237 */         if (elytra != -2) {
/* 238 */           mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 239 */           mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */         } 
/*     */         
/* 242 */         mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 243 */         mc.func_147114_u().func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/*     */         
/* 245 */         if (elytra != -2) {
/* 246 */           mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 247 */           mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */         } 
/*     */       } 
/*     */     } 
/* 251 */     if (jumpTicks > 0.0F) {
/* 252 */       jumpTicks--;
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMove(MatrixMove event) {
/* 258 */     if (this.mode.getValue() == Mode.Matrix) {
/* 259 */       int elytraSlot = getHotbarSlotOfItem();
/*     */       
/* 261 */       if (((Boolean)this.elytra.getValue()).booleanValue() && elytraSlot != -1 && 
/* 262 */         MovementUtil.isMoving() && !mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70143_R >= 0.15D && event.toGround()) {
/* 263 */         setMotion(((Float)this.setSpeed.getValue()).floatValue());
/* 264 */         oldSpeed = ((Float)this.setSpeed.getValue()).floatValue() / 1.06D;
/*     */       } 
/*     */ 
/*     */       
/* 268 */       if (mc.field_71439_g.func_70090_H()) {
/* 269 */         this.waterTicks = 10.0F;
/*     */       } else {
/* 271 */         this.waterTicks--;
/*     */       } 
/*     */       
/* 274 */       if (strafes()) {
/* 275 */         double forward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 276 */         double strafe = mc.field_71439_g.field_71158_b.field_78902_a;
/* 277 */         float yaw = mc.field_71439_g.field_70177_z;
/* 278 */         if (forward == 0.0D && strafe == 0.0D) {
/* 279 */           oldSpeed = 0.0D;
/* 280 */           event.setMotionX(0.0D);
/* 281 */           event.setMotionZ(0.0D);
/*     */         } else {
/*     */           
/* 284 */           if (forward != 0.0D) {
/* 285 */             if (strafe > 0.0D) {
/* 286 */               yaw += ((forward > 0.0D) ? -45 : 45);
/* 287 */             } else if (strafe < 0.0D) {
/* 288 */               yaw += ((forward > 0.0D) ? 45 : -45);
/*     */             } 
/*     */             
/* 291 */             strafe = 0.0D;
/* 292 */             if (forward > 0.0D) {
/* 293 */               forward = 1.0D;
/* 294 */             } else if (forward < 0.0D) {
/* 295 */               forward = -1.0D;
/*     */             } 
/*     */           } 
/* 298 */           double speed = calculateSpeed(event);
/* 299 */           double cos = Math.cos(Math.toRadians((yaw + 90.0F))), sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 300 */           event.setMotionX(forward * speed * cos + strafe * speed * sin);
/* 301 */           event.setMotionZ(forward * speed * sin - strafe * speed * cos);
/* 302 */           event.setCanceled(true);
/*     */         } 
/*     */       } else {
/* 305 */         oldSpeed = 0.0D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void updateValues(EventSync e) {
/* 312 */     double distTraveledLastTickX = mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70169_q;
/* 313 */     double distTraveledLastTickZ = mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70166_s;
/* 314 */     oldSpeed = Math.sqrt(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ) * contextFriction;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/* 319 */     if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/* 320 */       oldSpeed = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 326 */     oldSpeed = 0.0D;
/* 327 */     this.startDelay.reset();
/* 328 */     this.skip = true;
/*     */   }
/*     */   
/*     */   public boolean strafes() {
/* 332 */     if (mc.field_71439_g == null)
/* 333 */       return false; 
/* 334 */     if (mc.field_71439_g.func_70093_af()) {
/* 335 */       return false;
/*     */     }
/* 337 */     if (mc.field_71439_g.func_180799_ab()) {
/* 338 */       return false;
/*     */     }
/* 340 */     if (((RusherScaffold)Thunderhack.moduleManager.getModuleByClass(RusherScaffold.class)).isEnabled()) {
/* 341 */       return false;
/*     */     }
/* 343 */     if (mc.field_71439_g.func_70090_H() || this.waterTicks > 0.0F) {
/* 344 */       return false;
/*     */     }
/* 346 */     return !mc.field_71439_g.field_71075_bZ.field_75100_b;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void actionEvent(EventSprint eventAction) {
/* 351 */     if (this.mode.getValue() == Mode.SunriseFast) {
/*     */       return;
/*     */     }
/* 354 */     if (strafes() && 
/* 355 */       EventManager.serversprint != needSprintState) {
/* 356 */       eventAction.setSprintState(!EventManager.serversprint);
/*     */     }
/*     */     
/* 359 */     if (needSwap) {
/* 360 */       eventAction.setSprintState(!((IEntityPlayerSP)mc.field_71439_g).getServerSprintState());
/* 361 */       needSwap = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getHotbarSlotOfItem() {
/* 366 */     for (ItemStack stack : mc.field_71439_g.func_184193_aE()) {
/* 367 */       if (stack.func_77973_b() == Items.field_185160_cR) {
/* 368 */         return -2;
/*     */       }
/*     */     } 
/* 371 */     int slot = -1;
/* 372 */     for (int i = 0; i < 36; i++) {
/* 373 */       ItemStack s = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 374 */       if (s.func_77973_b() == Items.field_185160_cR) {
/* 375 */         slot = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 379 */     if (slot < 9 && slot != -1) {
/* 380 */       slot += 36;
/*     */     }
/* 382 */     return slot;
/*     */   }
/*     */   
/*     */   public void fixElytra() {
/* 386 */     ItemStack stack = mc.field_71439_g.field_71071_by.func_70445_o();
/* 387 */     if (stack != null && stack.func_77973_b() instanceof ItemArmor && this.fixTimer.passed(300L)) {
/* 388 */       ItemArmor ia = (ItemArmor)stack.func_77973_b();
/* 389 */       if (ia.field_77881_a == EntityEquipmentSlot.CHEST && mc.field_71439_g.field_71071_by.func_70440_f(2).func_77973_b() == Items.field_185160_cR) {
/* 390 */         mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 391 */         int nullSlot = findNullSlot();
/* 392 */         boolean needDrop = (nullSlot == 999);
/* 393 */         if (needDrop) {
/* 394 */           nullSlot = 9;
/*     */         }
/* 396 */         mc.field_71442_b.func_187098_a(0, nullSlot, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 397 */         if (needDrop) {
/* 398 */           mc.field_71442_b.func_187098_a(0, -999, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */         }
/* 400 */         this.fixTimer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdate(PlayerUpdateEvent event) {
/* 407 */     if (this.mode.getValue() == Mode.ElytraMiniJump) {
/* 408 */       if (mc.field_71439_g.field_70122_E) {
/* 409 */         mc.field_71439_g.func_70664_aZ();
/*     */         return;
/*     */       } 
/* 412 */       if (!mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -0.9D, 0.0D)).isEmpty() && this.elytraDelay.passedMs(250L) && this.startDelay.passedMs(500L)) {
/* 413 */         int elytra = InventoryUtil.getElytra();
/* 414 */         if (elytra == -1) {
/* 415 */           toggle();
/*     */         } else {
/* 417 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/* 418 */           disabler(elytra);
/*     */         } 
/* 420 */         mc.field_71439_g.field_70181_x = 0.0D;
/* 421 */         if (MovementUtil.isMoving()) {
/* 422 */           LegitStrafe.setSpeed(((Float)this.setSpeed.getValue()).floatValue());
/*     */         }
/* 424 */         this.elytraDelay.reset();
/*     */       } 
/*     */     } 
/* 427 */     if (this.mode.getValue() == Mode.SunriseFast) {
/* 428 */       if (mc.field_71439_g.field_70173_aa % 6 == 0) {
/* 429 */         int elytra = InventoryUtil.getElytra();
/* 430 */         if (elytra == -1) {
/* 431 */           toggle();
/*     */         } else {
/* 433 */           disabler(elytra);
/*     */         } 
/*     */       } 
/* 436 */       if (!this.skip) {
/* 437 */         if (mc.field_71439_g.field_70122_E && !((IKeyBinding)mc.field_71474_y.field_74314_A).isPressed()) {
/* 438 */           mc.field_71439_g.func_70664_aZ();
/* 439 */           if (jumpTicks != 0.0F) {
/* 440 */             strafe(0.2F);
/*     */             return;
/*     */           } 
/* 443 */           jumpTicks = 11.0F;
/* 444 */           strafe((float)(MovementUtil.getSpeed() * ((Float)this.setSpeed.getValue()).floatValue()));
/*     */         } 
/* 446 */         if (!mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -0.84D, 0.0D)).isEmpty() && (!((Boolean)this.onlyDown.getValue()).booleanValue() || mc.field_71439_g.field_70143_R > 0.05D)) {
/* 447 */           setMotion(Math.min(MovementUtil.getSpeed() * ((Float)this.setSpeed.getValue()).floatValue(), ((Float)this.maxSpeed.getValue()).floatValue()));
/*     */         }
/*     */       } else {
/* 450 */         if (mc.field_71439_g.field_70122_E)
/* 451 */           mc.field_71439_g.func_70664_aZ(); 
/* 452 */         if (mc.field_71439_g.field_70143_R > 0.05D) {
/* 453 */           this.skip = false;
/*     */         }
/*     */       } 
/*     */     } 
/* 457 */     fixElytra();
/*     */   }
/*     */   
/*     */   private enum Mode
/*     */   {
/* 462 */     Matrix, ElytraMiniJump, SunriseFast;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Strafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */