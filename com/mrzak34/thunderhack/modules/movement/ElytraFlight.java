/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.ElytraEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class ElytraFlight extends Module {
/*  32 */   public static ElytraFlight INSTANCE = new ElytraFlight();
/*     */   private static boolean hasElytra = false;
/*  34 */   private final Setting<Boolean> instantFly = register(new Setting("InstantFly", Boolean.valueOf(true)));
/*  35 */   public Setting<Boolean> cruiseControl = register(new Setting("CruiseControl", Boolean.valueOf(false)));
/*  36 */   public Setting<Float> factor = register(new Setting("Factor", Float.valueOf(1.5F), Float.valueOf(0.1F), Float.valueOf(50.0F)));
/*  37 */   public Setting<Float> upFactor = register(new Setting("UpFactor", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  38 */   public Setting<Float> downFactor = register(new Setting("DownFactor", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*     */   public int lastItem;
/*     */   boolean matrixTakeOff = false;
/*  41 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.BOOST));
/*  42 */   public Setting<Boolean> stopMotion = register(new Setting("StopMotion", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.BOOST)));
/*  43 */   public Setting<Boolean> freeze = register(new Setting("Freeze", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.BOOST)));
/*  44 */   public Setting<Float> minUpSpeed = register(new Setting("MinUpSpeed", Float.valueOf(0.5F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.mode.getValue() == Mode.BOOST && ((Boolean)this.cruiseControl.getValue()).booleanValue())));
/*  45 */   public Setting<Boolean> forceHeight = register(new Setting("ForceHeight", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.FIREWORK || (this.mode.getValue() == Mode.BOOST && ((Boolean)this.cruiseControl.getValue()).booleanValue()))));
/*  46 */   private final Setting<Integer> manualHeight = register(new Setting("Height", Integer.valueOf(121), Integer.valueOf(1), Integer.valueOf(256), v -> ((this.mode.getValue() == Mode.FIREWORK || (this.mode.getValue() == Mode.BOOST && ((Boolean)this.cruiseControl.getValue()).booleanValue())) && ((Boolean)this.forceHeight.getValue()).booleanValue())));
/*  47 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(10.0F), v -> (this.mode.getValue() == Mode.CONTROL)));
/*  48 */   private final Setting<Float> sneakDownSpeed = register(new Setting("DownSpeed", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(10.0F), v -> (this.mode.getValue() == Mode.CONTROL)));
/*  49 */   private final Setting<Boolean> boostTimer = register(new Setting("Timer", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.BOOST)));
/*  50 */   public Setting<Boolean> speedLimit = register(new Setting("SpeedLimit", Boolean.valueOf(true), v -> (this.mode.getValue() != Mode.FIREWORK)));
/*  51 */   public Setting<Float> maxSpeed = register(new Setting("MaxSpeed", Float.valueOf(2.5F), Float.valueOf(0.1F), Float.valueOf(10.0F), v -> (((Boolean)this.speedLimit.getValue()).booleanValue() && this.mode.getValue() != Mode.FIREWORK)));
/*  52 */   public Setting<Boolean> noDrag = new Setting("NoDrag", Boolean.valueOf(false), v -> (this.mode.getValue() != Mode.FIREWORK));
/*  53 */   private final Setting<Boolean> groundSafety = register(new Setting("GroundSafety", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.FIREWORK)));
/*  54 */   private final Setting<Float> triggerHeight = register(new Setting("TriggerHeight", Float.valueOf(0.3F), Float.valueOf(0.05F), Float.valueOf(1.0F), v -> (this.mode.getValue() == Mode.FIREWORK && ((Boolean)this.groundSafety.getValue()).booleanValue())));
/*  55 */   private final Setting<Float> packetDelay = register(new Setting("Limit", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> (this.mode.getValue() == Mode.BOOST)));
/*  56 */   private final Setting<Float> staticDelay = register(new Setting("Delay", Float.valueOf(5.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.mode.getValue() == Mode.BOOST)));
/*  57 */   private final Setting<Float> timeout = register(new Setting("Timeout", Float.valueOf(0.5F), Float.valueOf(0.1F), Float.valueOf(1.0F), v -> (this.mode.getValue() == Mode.BOOST)));
/*  58 */   private final Setting<Boolean> autoSwitch = register(new Setting("AutoSwitch", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.FIREWORK)));
/*  59 */   private final Setting<Integer> minSpeed = register(new Setting("MinSpeed", Integer.valueOf(20), Integer.valueOf(1), Integer.valueOf(50), v -> (this.mode.getValue() == Mode.FIREWORK)));
/*     */   private double height;
/*  61 */   private final Timer instantFlyTimer = new Timer();
/*  62 */   private final Timer staticTimer = new Timer();
/*  63 */   private final Timer rocketTimer = new Timer();
/*  64 */   private final Timer strictTimer = new Timer();
/*     */   
/*     */   private boolean hasTouchedGround = false;
/*     */   
/*     */   public ElytraFlight() {
/*  69 */     super("ElytraFlight", "бусты для 2б", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static int getElly() {
/*  73 */     for (ItemStack stack : mc.field_71439_g.func_184193_aE()) {
/*  74 */       if (stack.func_77973_b() == Items.field_185160_cR) {
/*  75 */         return -2;
/*     */       }
/*     */     } 
/*  78 */     int slot = -1;
/*  79 */     for (int i = 0; i < 36; i++) {
/*  80 */       ItemStack s = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  81 */       if (s.func_77973_b() == Items.field_185160_cR) {
/*  82 */         slot = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*  86 */     if (slot < 9 && slot != -1) {
/*  87 */       slot += 36;
/*     */     }
/*  89 */     return slot;
/*     */   }
/*     */   
/*     */   public static double[] directionSpeed(double speed) {
/*  93 */     float forward = mc.field_71439_g.field_71158_b.field_192832_b;
/*  94 */     float side = mc.field_71439_g.field_71158_b.field_78902_a;
/*  95 */     float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
/*     */     
/*  97 */     if (forward != 0.0F) {
/*  98 */       if (side > 0.0F) {
/*  99 */         yaw += ((forward > 0.0F) ? -45 : 45);
/* 100 */       } else if (side < 0.0F) {
/* 101 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/* 103 */       side = 0.0F;
/* 104 */       if (forward > 0.0F) {
/* 105 */         forward = 1.0F;
/* 106 */       } else if (forward < 0.0F) {
/* 107 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 112 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 113 */     double posX = forward * speed * cos + side * speed * sin;
/* 114 */     double posZ = forward * speed * sin - side * speed * cos;
/* 115 */     return new double[] { posX, posZ };
/*     */   }
/*     */   
/*     */   public void onEnable() {
/* 119 */     if (mc.field_71439_g != null) {
/* 120 */       this.height = mc.field_71439_g.field_70163_u;
/* 121 */       if (!mc.field_71439_g.func_184812_l_()) mc.field_71439_g.field_71075_bZ.field_75101_c = false; 
/* 122 */       mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/*     */     } 
/* 124 */     hasElytra = false;
/* 125 */     if (mc.field_71439_g == null)
/* 126 */       return;  int elytra = getElly();
/* 127 */     if (this.mode.getValue() == Mode.MatrixFirework && 
/* 128 */       elytra != -1) {
/* 129 */       this.lastItem = elytra;
/* 130 */       mc.field_71442_b.func_187098_a(0, elytra, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 131 */       mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 132 */       mc.field_71442_b.func_187098_a(0, this.lastItem, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 138 */     if (mc.field_71439_g != null) {
/* 139 */       if (!mc.field_71439_g.func_184812_l_()) mc.field_71439_g.field_71075_bZ.field_75101_c = false; 
/* 140 */       mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/*     */     } 
/* 142 */     Thunderhack.TICK_TIMER = 1.0F;
/* 143 */     hasElytra = false;
/* 144 */     if (mc.field_71439_g == null)
/* 145 */       return;  if (this.mode.getValue() == Mode.MatrixFirework) {
/* 146 */       int elytra = getElly();
/*     */       
/* 148 */       if (elytra != -1 && 
/* 149 */         elytra == -2) {
/* 150 */         mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 151 */         mc.field_71442_b.func_187098_a(0, this.lastItem, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 152 */         mc.field_71442_b.func_187098_a(0, 6, 1, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 160 */     if (fullNullCheck())
/*     */       return; 
/* 162 */     if (this.mode.getValue() == Mode.MatrixFirework) {
/* 163 */       matrixFireworks();
/*     */       
/*     */       return;
/*     */     } 
/* 167 */     if (mc.field_71439_g.field_70122_E) {
/* 168 */       this.hasTouchedGround = true;
/*     */     }
/*     */     
/* 171 */     if (!((Boolean)this.cruiseControl.getValue()).booleanValue()) {
/* 172 */       this.height = mc.field_71439_g.field_70163_u;
/*     */     }
/*     */     
/* 175 */     for (ItemStack is : mc.field_71439_g.func_184193_aE()) {
/* 176 */       if (is.func_77973_b() instanceof net.minecraft.item.ItemElytra) {
/* 177 */         hasElytra = true;
/*     */         break;
/*     */       } 
/* 180 */       hasElytra = false;
/*     */     } 
/*     */ 
/*     */     
/* 184 */     if (this.strictTimer.passedMs(1500L) && !this.strictTimer.passedMs(2000L)) {
/* 185 */       Thunderhack.TICK_TIMER = 1.0F;
/*     */     }
/*     */     
/* 188 */     if (!mc.field_71439_g.func_184613_cA()) {
/* 189 */       if (this.hasTouchedGround && ((Boolean)this.boostTimer.getValue()).booleanValue() && !mc.field_71439_g.field_70122_E) {
/* 190 */         Thunderhack.TICK_TIMER = 0.3F;
/*     */       }
/* 192 */       if (!mc.field_71439_g.field_70122_E && ((Boolean)this.instantFly.getValue()).booleanValue() && mc.field_71439_g.field_70181_x < 0.0D) {
/* 193 */         if (!this.instantFlyTimer.passedMs((long)(1000.0F * ((Float)this.timeout.getValue()).floatValue())))
/*     */           return; 
/* 195 */         this.instantFlyTimer.reset();
/* 196 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 197 */         this.hasTouchedGround = false;
/* 198 */         this.strictTimer.reset();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 204 */     if (!mc.field_71439_g.func_184613_cA())
/*     */       return; 
/* 206 */     if (this.mode.getValue() != Mode.FIREWORK)
/*     */       return; 
/* 208 */     if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/* 209 */       this.height += ((Float)this.upFactor.getValue()).floatValue() * 0.5D;
/* 210 */     } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/* 211 */       this.height -= ((Float)this.downFactor.getValue()).floatValue() * 0.5D;
/*     */     } 
/*     */     
/* 214 */     if (((Boolean)this.forceHeight.getValue()).booleanValue()) {
/* 215 */       this.height = ((Integer)this.manualHeight.getValue()).intValue();
/*     */     }
/*     */     
/* 218 */     Vec3d motionVector = new Vec3d(mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70179_y);
/* 219 */     double bps = motionVector.func_72433_c() * 20.0D;
/*     */     
/* 221 */     double horizSpeed = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/* 222 */     double horizPct = MathHelper.func_151237_a(horizSpeed / 1.7D, 0.0D, 1.0D);
/* 223 */     double heightPct = 1.0D - Math.sqrt(horizPct);
/* 224 */     double minAngle = 0.6D;
/*     */     
/* 226 */     if (horizPct >= 0.5D || mc.field_71439_g.field_70163_u > this.height + 1.0D) {
/* 227 */       double pitch = -((45.0D - minAngle) * heightPct + minAngle);
/*     */       
/* 229 */       double diff = (this.height + 1.0D - mc.field_71439_g.field_70163_u) * 2.0D;
/* 230 */       double heightDiffPct = MathHelper.func_151237_a(Math.abs(diff), 0.0D, 1.0D);
/* 231 */       double pDist = -Math.toDegrees(Math.atan2(Math.abs(diff), horizSpeed * 30.0D)) * Math.signum(diff);
/*     */       
/* 233 */       double adjustment = (pDist - pitch) * heightDiffPct;
/*     */       
/* 235 */       mc.field_71439_g.field_70125_A = (float)pitch;
/* 236 */       mc.field_71439_g.field_70125_A += (float)adjustment;
/* 237 */       mc.field_71439_g.field_70127_C = mc.field_71439_g.field_70125_A;
/*     */     } 
/*     */     
/* 240 */     if (this.rocketTimer.passedMs((long)(1000.0F * ((Float)this.factor.getValue()).floatValue()))) {
/* 241 */       double heightDiff = this.height - mc.field_71439_g.field_70163_u;
/* 242 */       boolean shouldBoost = ((heightDiff > 0.25D && heightDiff < 1.0D) || bps < ((Integer)this.minSpeed.getValue()).intValue());
/*     */       
/* 244 */       if (((Boolean)this.groundSafety.getValue()).booleanValue()) {
/* 245 */         Block bottomBlock = mc.field_71441_e.func_180495_p((new BlockPos((Entity)mc.field_71439_g)).func_177977_b()).func_177230_c();
/* 246 */         if (bottomBlock != Blocks.field_150350_a && !(bottomBlock instanceof net.minecraft.block.BlockLiquid) && 
/* 247 */           (mc.field_71439_g.func_174813_aQ()).field_72338_b - Math.floor((mc.field_71439_g.func_174813_aQ()).field_72338_b) > ((Float)this.triggerHeight.getValue()).floatValue()) {
/* 248 */           shouldBoost = true;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 253 */       if (((Boolean)this.autoSwitch.getValue()).booleanValue() && shouldBoost && mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151152_bP) {
/* 254 */         for (int l = 0; l < 9; l++) {
/* 255 */           if (mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151152_bP) {
/* 256 */             mc.field_71439_g.field_71071_by.field_70461_c = l;
/* 257 */             mc.field_71442_b.func_78765_e();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/* 263 */       if (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151152_bP && shouldBoost) {
/* 264 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 265 */         this.rocketTimer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onElytra(ElytraEvent event) {
/* 274 */     if (mc.field_71441_e == null || mc.field_71439_g == null || !hasElytra || !mc.field_71439_g.func_184613_cA())
/*     */       return; 
/* 276 */     if (this.mode.getValue() == Mode.FIREWORK)
/* 277 */       return;  if (this.mode.getValue() == Mode.MatrixFirework)
/*     */       return; 
/* 279 */     if ((event.getEntity() == mc.field_71439_g && mc.field_71439_g.func_70613_aW()) || (mc.field_71439_g.func_184186_bw() && !mc.field_71439_g.func_70090_H()) || (mc.field_71439_g != null && mc.field_71439_g.field_71075_bZ.field_75100_b && !mc.field_71439_g.func_180799_ab()) || (mc.field_71439_g.field_71075_bZ.field_75100_b && mc.field_71439_g.func_184613_cA())) {
/*     */       
/* 281 */       event.setCanceled(true);
/*     */       
/* 283 */       if (this.mode.getValue() != Mode.BOOST) {
/*     */         
/* 285 */         Vec3d lookVec = mc.field_71439_g.func_70040_Z();
/*     */         
/* 287 */         float pitch = mc.field_71439_g.field_70125_A * 0.017453292F;
/*     */         
/* 289 */         double lookDist = Math.sqrt(lookVec.field_72450_a * lookVec.field_72450_a + lookVec.field_72449_c * lookVec.field_72449_c);
/* 290 */         double motionDist = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/* 291 */         double lookVecDist = lookVec.func_72433_c();
/*     */         
/* 293 */         float cosPitch = MathHelper.func_76134_b(pitch);
/* 294 */         cosPitch = (float)(cosPitch * cosPitch * Math.min(1.0D, lookVecDist / 0.4D));
/*     */ 
/*     */         
/* 297 */         if (this.mode.getValue() != Mode.CONTROL) {
/* 298 */           mc.field_71439_g.field_70181_x += -0.08D + cosPitch * 0.06D / ((Float)this.downFactor.getValue()).floatValue();
/*     */         }
/*     */ 
/*     */         
/* 302 */         if (this.mode.getValue() == Mode.CONTROL) {
/*     */           
/* 304 */           if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/* 305 */             mc.field_71439_g.field_70181_x = -((Float)this.sneakDownSpeed.getValue()).floatValue();
/* 306 */           } else if (!mc.field_71474_y.field_74314_A.func_151470_d()) {
/* 307 */             mc.field_71439_g.field_70181_x = -3.0E-14D * ((Float)this.downFactor.getValue()).floatValue();
/*     */           } 
/* 309 */         } else if (this.mode.getValue() != Mode.CONTROL && mc.field_71439_g.field_70181_x < 0.0D && lookDist > 0.0D) {
/*     */           
/* 311 */           double downSpeed = mc.field_71439_g.field_70181_x * -0.1D * cosPitch;
/* 312 */           mc.field_71439_g.field_70181_x += downSpeed;
/* 313 */           mc.field_71439_g.field_70159_w += lookVec.field_72450_a * downSpeed / lookDist * ((Float)this.factor.getValue()).floatValue();
/* 314 */           mc.field_71439_g.field_70179_y += lookVec.field_72449_c * downSpeed / lookDist * ((Float)this.factor.getValue()).floatValue();
/*     */         } 
/*     */ 
/*     */         
/* 318 */         if (pitch < 0.0F && this.mode.getValue() != Mode.CONTROL) {
/*     */           
/* 320 */           double rawUpSpeed = motionDist * -MathHelper.func_76126_a(pitch) * 0.04D;
/* 321 */           mc.field_71439_g.field_70181_x += rawUpSpeed * 3.2D * ((Float)this.upFactor.getValue()).floatValue();
/* 322 */           mc.field_71439_g.field_70159_w -= lookVec.field_72450_a * rawUpSpeed / lookDist;
/* 323 */           mc.field_71439_g.field_70179_y -= lookVec.field_72449_c * rawUpSpeed / lookDist;
/* 324 */         } else if (this.mode.getValue() == Mode.CONTROL && mc.field_71474_y.field_74314_A.func_151470_d()) {
/*     */           
/* 326 */           if (motionDist > (((Float)this.upFactor.getValue()).floatValue() / ((Float)this.upFactor.getMax()).floatValue())) {
/* 327 */             double rawUpSpeed = motionDist * 0.01325D;
/* 328 */             mc.field_71439_g.field_70181_x += rawUpSpeed * 3.2D;
/* 329 */             mc.field_71439_g.field_70159_w -= lookVec.field_72450_a * rawUpSpeed / lookDist;
/* 330 */             mc.field_71439_g.field_70179_y -= lookVec.field_72449_c * rawUpSpeed / lookDist;
/*     */           } else {
/* 332 */             double[] dir = directionSpeed(((Float)this.speed.getValue()).floatValue());
/* 333 */             mc.field_71439_g.field_70159_w = dir[0];
/* 334 */             mc.field_71439_g.field_70179_y = dir[1];
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 339 */         if (lookDist > 0.0D) {
/* 340 */           mc.field_71439_g.field_70159_w += (lookVec.field_72450_a / lookDist * motionDist - mc.field_71439_g.field_70159_w) * 0.1D;
/* 341 */           mc.field_71439_g.field_70179_y += (lookVec.field_72449_c / lookDist * motionDist - mc.field_71439_g.field_70179_y) * 0.1D;
/*     */         } 
/*     */         
/* 344 */         if (this.mode.getValue() == Mode.CONTROL && !mc.field_71474_y.field_74314_A.func_151470_d()) {
/*     */           
/* 346 */           double[] dir = directionSpeed(((Float)this.speed.getValue()).floatValue());
/* 347 */           mc.field_71439_g.field_70159_w = dir[0];
/* 348 */           mc.field_71439_g.field_70179_y = dir[1];
/*     */         } 
/*     */         
/* 351 */         if (!((Boolean)this.noDrag.getValue()).booleanValue()) {
/* 352 */           mc.field_71439_g.field_70159_w *= 0.9900000095367432D;
/* 353 */           mc.field_71439_g.field_70181_x *= 0.9800000190734863D;
/* 354 */           mc.field_71439_g.field_70179_y *= 0.9900000095367432D;
/*     */         } 
/*     */ 
/*     */         
/* 358 */         double finalDist = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/*     */         
/* 360 */         if (((Boolean)this.speedLimit.getValue()).booleanValue() && finalDist > ((Float)this.maxSpeed.getValue()).floatValue()) {
/* 361 */           mc.field_71439_g.field_70159_w *= ((Float)this.maxSpeed.getValue()).floatValue() / finalDist;
/* 362 */           mc.field_71439_g.field_70179_y *= ((Float)this.maxSpeed.getValue()).floatValue() / finalDist;
/*     */         } 
/*     */         
/* 365 */         mc.field_71439_g.func_70091_d(MoverType.SELF, mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70179_y);
/*     */       } else {
/* 367 */         float moveForward = mc.field_71439_g.field_71158_b.field_192832_b;
/*     */         
/* 369 */         if (((Boolean)this.cruiseControl.getValue()).booleanValue()) {
/* 370 */           if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/* 371 */             this.height += ((Float)this.upFactor.getValue()).floatValue() * 0.5D;
/* 372 */           } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/* 373 */             this.height -= ((Float)this.downFactor.getValue()).floatValue() * 0.5D;
/*     */           } 
/*     */           
/* 376 */           if (((Boolean)this.forceHeight.getValue()).booleanValue()) {
/* 377 */             this.height = ((Integer)this.manualHeight.getValue()).intValue();
/*     */           }
/*     */           
/* 380 */           double horizSpeed = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/* 381 */           double horizPct = MathHelper.func_151237_a(horizSpeed / 1.7D, 0.0D, 1.0D);
/* 382 */           double heightPct = 1.0D - Math.sqrt(horizPct);
/* 383 */           double minAngle = 0.6D;
/*     */           
/* 385 */           if (horizSpeed >= ((Float)this.minUpSpeed.getValue()).floatValue() && this.instantFlyTimer.passedMs((long)(2000.0F * ((Float)this.packetDelay.getValue()).floatValue()))) {
/* 386 */             double pitch = -((45.0D - minAngle) * heightPct + minAngle);
/*     */             
/* 388 */             double diff = (this.height + 1.0D - mc.field_71439_g.field_70163_u) * 2.0D;
/* 389 */             double heightDiffPct = MathHelper.func_151237_a(Math.abs(diff), 0.0D, 1.0D);
/* 390 */             double pDist = -Math.toDegrees(Math.atan2(Math.abs(diff), horizSpeed * 30.0D)) * Math.signum(diff);
/*     */             
/* 392 */             double adjustment = (pDist - pitch) * heightDiffPct;
/*     */             
/* 394 */             mc.field_71439_g.field_70125_A = (float)pitch;
/* 395 */             mc.field_71439_g.field_70125_A += (float)adjustment;
/* 396 */             mc.field_71439_g.field_70127_C = mc.field_71439_g.field_70125_A;
/*     */           } else {
/* 398 */             mc.field_71439_g.field_70125_A = 0.25F;
/* 399 */             mc.field_71439_g.field_70127_C = 0.25F;
/* 400 */             moveForward = 1.0F;
/*     */           } 
/*     */         } 
/*     */         
/* 404 */         Vec3d vec3d = mc.field_71439_g.func_70040_Z();
/*     */         
/* 406 */         float f = mc.field_71439_g.field_70125_A * 0.017453292F;
/*     */         
/* 408 */         double d6 = Math.sqrt(vec3d.field_72450_a * vec3d.field_72450_a + vec3d.field_72449_c * vec3d.field_72449_c);
/* 409 */         double d8 = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/* 410 */         double d1 = vec3d.func_72433_c();
/* 411 */         float f4 = MathHelper.func_76134_b(f);
/* 412 */         f4 = (float)(f4 * f4 * Math.min(1.0D, d1 / 0.4D));
/* 413 */         mc.field_71439_g.field_70181_x += -0.08D + f4 * 0.06D;
/*     */         
/* 415 */         if (mc.field_71439_g.field_70181_x < 0.0D && d6 > 0.0D) {
/* 416 */           double d2 = mc.field_71439_g.field_70181_x * -0.1D * f4;
/* 417 */           mc.field_71439_g.field_70181_x += d2;
/* 418 */           mc.field_71439_g.field_70159_w += vec3d.field_72450_a * d2 / d6;
/* 419 */           mc.field_71439_g.field_70179_y += vec3d.field_72449_c * d2 / d6;
/*     */         } 
/*     */         
/* 422 */         if (f < 0.0F) {
/* 423 */           double d10 = d8 * -MathHelper.func_76126_a(f) * 0.04D;
/* 424 */           mc.field_71439_g.field_70181_x += d10 * 3.2D;
/* 425 */           mc.field_71439_g.field_70159_w -= vec3d.field_72450_a * d10 / d6;
/* 426 */           mc.field_71439_g.field_70179_y -= vec3d.field_72449_c * d10 / d6;
/*     */         } 
/*     */         
/* 429 */         if (d6 > 0.0D) {
/* 430 */           mc.field_71439_g.field_70159_w += (vec3d.field_72450_a / d6 * d8 - mc.field_71439_g.field_70159_w) * 0.1D;
/* 431 */           mc.field_71439_g.field_70179_y += (vec3d.field_72449_c / d6 * d8 - mc.field_71439_g.field_70179_y) * 0.1D;
/*     */         } 
/*     */         
/* 434 */         if (!((Boolean)this.noDrag.getValue()).booleanValue()) {
/* 435 */           mc.field_71439_g.field_70159_w *= 0.9900000095367432D;
/* 436 */           mc.field_71439_g.field_70181_x *= 0.9800000190734863D;
/* 437 */           mc.field_71439_g.field_70179_y *= 0.9900000095367432D;
/*     */         } 
/*     */         
/* 440 */         float yaw = mc.field_71439_g.field_70177_z * 0.017453292F;
/*     */         
/* 442 */         if (f > 0.0F && mc.field_71439_g.field_70181_x < 0.0D) {
/* 443 */           if (moveForward != 0.0F && this.instantFlyTimer.passedMs((long)(2000.0F * ((Float)this.packetDelay.getValue()).floatValue())) && this.staticTimer.passedMs((long)(1000.0F * ((Float)this.staticDelay.getValue()).floatValue()))) {
/* 444 */             if (((Boolean)this.stopMotion.getValue()).booleanValue()) {
/* 445 */               mc.field_71439_g.field_70159_w = 0.0D;
/* 446 */               mc.field_71439_g.field_70179_y = 0.0D;
/*     */             } 
/* 448 */             this.instantFlyTimer.reset();
/* 449 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 450 */           } else if (!this.instantFlyTimer.passedMs((long)(2000.0F * ((Float)this.packetDelay.getValue()).floatValue()))) {
/* 451 */             mc.field_71439_g.field_70159_w -= moveForward * Math.sin(yaw) * ((Float)this.factor.getValue()).floatValue() / 20.0D;
/* 452 */             mc.field_71439_g.field_70179_y += moveForward * Math.cos(yaw) * ((Float)this.factor.getValue()).floatValue() / 20.0D;
/* 453 */             this.staticTimer.reset();
/*     */           } 
/*     */         }
/*     */         
/* 457 */         double finalDist = Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y);
/*     */         
/* 459 */         if (((Boolean)this.speedLimit.getValue()).booleanValue() && finalDist > ((Float)this.maxSpeed.getValue()).floatValue()) {
/* 460 */           mc.field_71439_g.field_70159_w *= ((Float)this.maxSpeed.getValue()).floatValue() / finalDist;
/* 461 */           mc.field_71439_g.field_70179_y *= ((Float)this.maxSpeed.getValue()).floatValue() / finalDist;
/*     */         } 
/*     */         
/* 464 */         if (((Boolean)this.freeze.getValue()).booleanValue() && Keyboard.isKeyDown(56)) {
/* 465 */           mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
/*     */         }
/*     */         
/* 468 */         mc.field_71439_g.func_70091_d(MoverType.SELF, mc.field_71439_g.field_70159_w, mc.field_71439_g.field_70181_x, mc.field_71439_g.field_70179_y);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void matrixFireworks() {
/* 474 */     if (InventoryUtil.getFireWorks() == -1) {
/* 475 */       Command.sendMessage("Нет фейерверков!");
/* 476 */       toggle();
/*     */       return;
/*     */     } 
/* 479 */     int elytra = InventoryUtil.getElytra();
/* 480 */     if (elytra == -1) {
/* 481 */       Command.sendMessage("Нет элитр!");
/* 482 */       toggle();
/*     */       return;
/*     */     } 
/* 485 */     if (mc.field_71439_g.func_70090_H()) {
/*     */       return;
/*     */     }
/* 488 */     if (mc.field_71439_g.field_70122_E) {
/* 489 */       mc.field_71439_g.func_70664_aZ();
/* 490 */       this.matrixTakeOff = true;
/* 491 */     } else if (this.matrixTakeOff && mc.field_71439_g.field_70143_R > 0.05D) {
/* 492 */       mc.field_71439_g.field_70181_x = 0.0D;
/* 493 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_FALL_FLYING));
/* 494 */       if (InventoryUtil.getFireWorks() >= 0) {
/* 495 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(InventoryUtil.getFireWorks()));
/* 496 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 497 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */       } 
/* 499 */       this.matrixTakeOff = false;
/* 500 */     } else if (((IEntityPlayerSP)mc.field_71439_g).wasFallFlying()) {
/* 501 */       if (mc.field_71439_g.field_70173_aa % 25 == 0 && 
/* 502 */         InventoryUtil.getFireWorks() >= 0) {
/* 503 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(InventoryUtil.getFireWorks()));
/* 504 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 505 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */       } 
/*     */       
/* 508 */       mc.field_71439_g.field_70181_x = -0.009999999776482582D;
/* 509 */       MovementUtil.setMotion(MovementUtil.getSpeed());
/* 510 */       if (!mc.field_71439_g.func_70093_af() && ((IKeyBinding)mc.field_71474_y.field_74314_A).isPressed()) {
/* 511 */         mc.field_71439_g.field_70181_x = 0.800000011920929D;
/*     */       }
/* 513 */       if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/* 514 */         mc.field_71439_g.field_70181_x = -0.800000011920929D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private enum Mode
/*     */   {
/* 522 */     BOOST, CONTROL, FIREWORK, MatrixFirework;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\ElytraFlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */