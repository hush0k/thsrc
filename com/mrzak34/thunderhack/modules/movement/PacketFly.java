/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.events.PushEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketPlayerPosLook;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.setting.SubBind;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.TimeVec3d;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketFly
/*     */   extends Module
/*     */ {
/*  34 */   private static final Random random = new Random();
/*  35 */   public Setting<Float> speed = register(new Setting("Speed", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/*  36 */   public Setting<Boolean> boost = register(new Setting("Boost", Boolean.valueOf(false)));
/*  37 */   public Setting<Boolean> jitter = register(new Setting("Jitter", Boolean.valueOf(false)));
/*  38 */   public Setting<Boolean> constrict = register(new Setting("Constrict", Boolean.valueOf(false)));
/*  39 */   public Setting<Boolean> noPhaseSlow = register(new Setting("NoPhaseSlow", Boolean.valueOf(false)));
/*  40 */   public Setting<Boolean> multiAxis = register(new Setting("MultiAxis", Boolean.valueOf(false)));
/*  41 */   public Setting<Boolean> bounds = register(new Setting("Bounds", Boolean.valueOf(false)));
/*  42 */   public Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(true)));
/*  43 */   double speedX = 0.0D;
/*  44 */   double speedY = 0.0D;
/*  45 */   double speedZ = 0.0D;
/*  46 */   private final Setting<Type> type = register(new Setting("Type", Type.FAST));
/*  47 */   public Setting<SubBind> facrotize = register(new Setting("Snap", new SubBind(0), v -> (this.type.getValue() == Type.FACTOR)));
/*  48 */   public Setting<Float> motion = register(new Setting("Distance", Float.valueOf(5.0F), Float.valueOf(1.0F), Float.valueOf(20.0F), v -> (this.type.getValue() == Type.FACTOR)));
/*  49 */   public Setting<Float> factor = register(new Setting("Factor", Float.valueOf(1.0F), Float.valueOf(1.0F), Float.valueOf(10.0F), v -> (this.type.getValue() == Type.FACTOR || this.type.getValue() == Type.DESYNC)));
/*  50 */   private final Setting<Mode> packetMode = register(new Setting("PacketMode", Mode.UP));
/*  51 */   private final Setting<Phase> phase = register(new Setting("Phase", Phase.NCP));
/*  52 */   private final Setting<AntiKick> antiKickMode = register(new Setting("AntiKick", AntiKick.NORMAL));
/*  53 */   private final Setting<Limit> limit = register(new Setting("Limit", Limit.NONE));
/*     */   private int teleportId;
/*     */   private CPacketPlayer.Position startingOutOfBoundsPos;
/*  56 */   private final ArrayList<CPacketPlayer> packets = new ArrayList<>();
/*  57 */   private final Map<Integer, TimeVec3d> posLooks = new ConcurrentHashMap<>();
/*  58 */   private int antiKickTicks = 0;
/*  59 */   private int vDelay = 0;
/*  60 */   private int hDelay = 0;
/*     */   private boolean limitStrict = false;
/*  62 */   private int limitTicks = 0;
/*  63 */   private int jitterTicks = 0;
/*     */   private boolean oddJitter = false;
/*  65 */   private float postYaw = -400.0F;
/*  66 */   private float postPitch = -400.0F;
/*     */   
/*  68 */   private int factorCounter = 0;
/*     */   
/*  70 */   private final Timer intervalTimer = new Timer();
/*     */   
/*     */   public PacketFly() {
/*  73 */     super("PacketFly", "летать на пакетах-из пятерочки", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static double randomLimitedVertical() {
/*  77 */     int randomValue = random.nextInt(22);
/*  78 */     randomValue += 70;
/*  79 */     if (random.nextBoolean()) {
/*  80 */       return randomValue;
/*     */     }
/*  82 */     return -randomValue;
/*     */   }
/*     */   
/*     */   public static double randomLimitedHorizontal() {
/*  86 */     int randomValue = random.nextInt(10);
/*  87 */     if (random.nextBoolean()) {
/*  88 */       return randomValue;
/*     */     }
/*  90 */     return -randomValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  96 */     if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiDisconnected || mc.field_71462_r instanceof net.minecraft.client.gui.GuiMainMenu || mc.field_71462_r instanceof net.minecraft.client.gui.GuiMultiplayer || mc.field_71462_r instanceof net.minecraft.client.gui.GuiDownloadTerrain)
/*     */     {
/*  98 */       toggle();
/*     */     }
/*     */     
/* 101 */     if (((Boolean)this.boost.getValue()).booleanValue()) {
/* 102 */       Thunderhack.TICK_TIMER = 1.088F;
/*     */     } else {
/* 104 */       Thunderhack.TICK_TIMER = 1.0F;
/*     */     } 
/*     */   }
/*     */   @SubscribeEvent
/*     */   public void onPlayerUpdate(PlayerUpdateEvent event) {
/*     */     float rawFactor;
/*     */     int factorInt, i;
/* 111 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/* 112 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     if (mc.field_71439_g.field_70173_aa % 20 == 0) {
/* 117 */       cleanPosLooks();
/*     */     }
/*     */     
/* 120 */     mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
/*     */     
/* 122 */     if (this.teleportId <= 0 && this.type.getValue() != Type.SETBACK) {
/*     */       
/* 124 */       this.startingOutOfBoundsPos = new CPacketPlayer.Position(randomHorizontal(), 1.0D, randomHorizontal(), mc.field_71439_g.field_70122_E);
/* 125 */       this.packets.add(this.startingOutOfBoundsPos);
/* 126 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)this.startingOutOfBoundsPos);
/*     */       
/*     */       return;
/*     */     } 
/* 130 */     boolean phasing = checkCollisionBox();
/*     */     
/* 132 */     this.speedX = 0.0D;
/* 133 */     this.speedY = 0.0D;
/* 134 */     this.speedZ = 0.0D;
/*     */     
/* 136 */     if (mc.field_71474_y.field_74314_A.func_151470_d() && (this.hDelay < 1 || (((Boolean)this.multiAxis.getValue()).booleanValue() && phasing))) {
/* 137 */       if (mc.field_71439_g.field_70173_aa % ((this.type.getValue() == Type.SETBACK || this.type.getValue() == Type.SLOW || this.limit.getValue() == Limit.STRICT) ? 10 : 20) == 0) {
/* 138 */         this.speedY = (this.antiKickMode.getValue() != AntiKick.NONE) ? -0.032D : 0.062D;
/*     */       } else {
/* 140 */         this.speedY = 0.062D;
/*     */       } 
/* 142 */       this.antiKickTicks = 0;
/* 143 */       this.vDelay = 5;
/* 144 */     } else if (mc.field_71474_y.field_74311_E.func_151470_d() && (this.hDelay < 1 || (((Boolean)this.multiAxis.getValue()).booleanValue() && phasing))) {
/* 145 */       this.speedY = -0.062D;
/* 146 */       this.antiKickTicks = 0;
/* 147 */       this.vDelay = 5;
/*     */     } 
/*     */     
/* 150 */     if ((((Boolean)this.multiAxis.getValue()).booleanValue() && phasing) || !mc.field_71474_y.field_74311_E.func_151470_d() || !mc.field_71474_y.field_74314_A.func_151470_d()) {
/* 151 */       if (PlayerUtils.isPlayerMoving()) {
/* 152 */         double[] dir = PlayerUtils.directionSpeed(((phasing && this.phase.getValue() == Phase.NCP) ? (((Boolean)this.noPhaseSlow.getValue()).booleanValue() ? (((Boolean)this.multiAxis.getValue()).booleanValue() ? 0.0465D : 0.062D) : 0.031D) : 0.26D) * ((Float)this.speed.getValue()).floatValue());
/* 153 */         if ((dir[0] != 0.0D || dir[1] != 0.0D) && (this.vDelay < 1 || (((Boolean)this.multiAxis.getValue()).booleanValue() && phasing))) {
/* 154 */           this.speedX = dir[0];
/* 155 */           this.speedZ = dir[1];
/* 156 */           this.hDelay = 5;
/*     */         } 
/*     */       } 
/*     */       
/* 160 */       if (this.antiKickMode.getValue() != AntiKick.NONE && (this.limit.getValue() == Limit.NONE || this.limitTicks != 0)) {
/* 161 */         if (this.antiKickTicks < ((this.packetMode.getValue() == Mode.BYPASS && !((Boolean)this.bounds.getValue()).booleanValue()) ? 1 : 3)) {
/* 162 */           this.antiKickTicks++;
/*     */         } else {
/* 164 */           this.antiKickTicks = 0;
/* 165 */           if (this.antiKickMode.getValue() != AntiKick.LIMITED || !phasing) {
/* 166 */             this.speedY = (this.antiKickMode.getValue() == AntiKick.STRICT) ? -0.08D : -0.04D;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 172 */     if (phasing && ((
/* 173 */       this.phase.getValue() == Phase.NCP && mc.field_71439_g.field_191988_bg != 0.0D) || (mc.field_71439_g.field_70702_br != 0.0D && this.speedY != 0.0D))) {
/* 174 */       this.speedY /= 2.5D;
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (this.limit.getValue() != Limit.NONE) {
/* 179 */       if (this.limitTicks == 0) {
/* 180 */         this.speedX = 0.0D;
/* 181 */         this.speedY = 0.0D;
/* 182 */         this.speedZ = 0.0D;
/* 183 */       } else if (this.limitTicks == 2 && ((Boolean)this.jitter.getValue()).booleanValue()) {
/* 184 */         if (this.oddJitter) {
/* 185 */           this.speedX = 0.0D;
/* 186 */           this.speedY = 0.0D;
/* 187 */           this.speedZ = 0.0D;
/*     */         } 
/* 189 */         this.oddJitter = !this.oddJitter;
/*     */       } 
/* 191 */     } else if (((Boolean)this.jitter.getValue()).booleanValue() && this.jitterTicks == 7) {
/* 192 */       this.speedX = 0.0D;
/* 193 */       this.speedY = 0.0D;
/* 194 */       this.speedZ = 0.0D;
/*     */     } 
/*     */     
/* 197 */     switch ((Type)this.type.getValue()) {
/*     */       case UP:
/* 199 */         mc.field_71439_g.func_70016_h(this.speedX, this.speedY, this.speedZ);
/* 200 */         sendPackets(this.speedX, this.speedY, this.speedZ, (Mode)this.packetMode.getValue(), true, false);
/*     */         break;
/*     */       case PRESERVE:
/* 203 */         sendPackets(this.speedX, this.speedY, this.speedZ, (Mode)this.packetMode.getValue(), true, false);
/*     */         break;
/*     */       case LIMITJITTER:
/* 206 */         mc.field_71439_g.func_70016_h(this.speedX, this.speedY, this.speedZ);
/* 207 */         sendPackets(this.speedX, this.speedY, this.speedZ, (Mode)this.packetMode.getValue(), false, false);
/*     */         break;
/*     */       case BYPASS:
/*     */       case OBSCURE:
/* 211 */         rawFactor = ((Float)this.factor.getValue()).floatValue();
/* 212 */         if (PlayerUtils.isKeyDown(((SubBind)this.facrotize.getValue()).getKey()) && this.intervalTimer.passedMs(3500L)) {
/* 213 */           this.intervalTimer.reset();
/* 214 */           rawFactor = ((Float)this.motion.getValue()).floatValue();
/*     */         } 
/* 216 */         factorInt = (int)Math.floor(rawFactor);
/* 217 */         this.factorCounter++;
/* 218 */         if (this.factorCounter > (int)(20.0D / (rawFactor - factorInt) * 20.0D)) {
/* 219 */           factorInt++;
/* 220 */           this.factorCounter = 0;
/*     */         } 
/* 222 */         for (i = 1; i <= factorInt; i++) {
/* 223 */           mc.field_71439_g.func_70016_h(this.speedX * i, this.speedY * i, this.speedZ * i);
/* 224 */           sendPackets(this.speedX * i, this.speedY * i, this.speedZ * i, (Mode)this.packetMode.getValue(), true, false);
/*     */         } 
/* 226 */         this.speedX = mc.field_71439_g.field_70159_w;
/* 227 */         this.speedY = mc.field_71439_g.field_70181_x;
/* 228 */         this.speedZ = mc.field_71439_g.field_70179_y;
/*     */         break;
/*     */     } 
/*     */     
/* 232 */     this.vDelay--;
/* 233 */     this.hDelay--;
/*     */     
/* 235 */     if (((Boolean)this.constrict.getValue()).booleanValue() && (this.limit.getValue() == Limit.NONE || this.limitTicks > 1)) {
/* 236 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
/*     */     }
/*     */     
/* 239 */     this.limitTicks++;
/* 240 */     this.jitterTicks++;
/*     */     
/* 242 */     if (this.limitTicks > ((this.limit.getValue() == Limit.STRICT) ? (this.limitStrict ? 1 : 2) : 3)) {
/* 243 */       this.limitTicks = 0;
/* 244 */       this.limitStrict = !this.limitStrict;
/*     */     } 
/*     */     
/* 247 */     if (this.jitterTicks > 7) {
/* 248 */       this.jitterTicks = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendPackets(double x, double y, double z, Mode mode, boolean sendConfirmTeleport, boolean sendExtraCT) {
/* 253 */     Vec3d nextPos = new Vec3d(mc.field_71439_g.field_70165_t + x, mc.field_71439_g.field_70163_u + y, mc.field_71439_g.field_70161_v + z);
/* 254 */     Vec3d bounds = getBoundsVec(x, y, z, mode);
/*     */     
/* 256 */     CPacketPlayer.Position position1 = new CPacketPlayer.Position(nextPos.field_72450_a, nextPos.field_72448_b, nextPos.field_72449_c, mc.field_71439_g.field_70122_E);
/* 257 */     this.packets.add(position1);
/* 258 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)position1);
/*     */     
/* 260 */     if (this.limit.getValue() != Limit.NONE && this.limitTicks == 0)
/*     */       return; 
/* 262 */     CPacketPlayer.Position position2 = new CPacketPlayer.Position(bounds.field_72450_a, bounds.field_72448_b, bounds.field_72449_c, mc.field_71439_g.field_70122_E);
/* 263 */     this.packets.add(position2);
/* 264 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)position2);
/*     */     
/* 266 */     if (sendConfirmTeleport) {
/* 267 */       this.teleportId++;
/*     */       
/* 269 */       if (sendExtraCT) {
/* 270 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketConfirmTeleport(this.teleportId - 1));
/*     */       }
/*     */       
/* 273 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketConfirmTeleport(this.teleportId));
/*     */       
/* 275 */       this.posLooks.put(Integer.valueOf(this.teleportId), new TimeVec3d(nextPos.field_72450_a, nextPos.field_72448_b, nextPos.field_72449_c, System.currentTimeMillis()));
/*     */       
/* 277 */       if (sendExtraCT) {
/* 278 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketConfirmTeleport(this.teleportId + 1));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vec3d getBoundsVec(double x, double y, double z, Mode mode) {
/* 292 */     switch (mode) {
/*     */       case UP:
/* 294 */         return new Vec3d(mc.field_71439_g.field_70165_t + x, ((Boolean)this.bounds.getValue()).booleanValue() ? (((Boolean)this.strict.getValue()).booleanValue() ? 'ÿ' : 'Ā') : (mc.field_71439_g.field_70163_u + 420.0D), mc.field_71439_g.field_70161_v + z);
/*     */       case PRESERVE:
/* 296 */         return new Vec3d(((Boolean)this.bounds.getValue()).booleanValue() ? (mc.field_71439_g.field_70165_t + randomHorizontal()) : randomHorizontal(), ((Boolean)this.strict.getValue()).booleanValue() ? Math.max(mc.field_71439_g.field_70163_u, 2.0D) : mc.field_71439_g.field_70163_u, ((Boolean)this.bounds.getValue()).booleanValue() ? (mc.field_71439_g.field_70161_v + randomHorizontal()) : randomHorizontal());
/*     */       case LIMITJITTER:
/* 298 */         return new Vec3d(mc.field_71439_g.field_70165_t + (((Boolean)this.strict.getValue()).booleanValue() ? x : randomLimitedHorizontal()), mc.field_71439_g.field_70163_u + randomLimitedVertical(), mc.field_71439_g.field_70161_v + (((Boolean)this.strict.getValue()).booleanValue() ? z : randomLimitedHorizontal()));
/*     */       case BYPASS:
/* 300 */         if (((Boolean)this.bounds.getValue()).booleanValue()) {
/* 301 */           double rawY = y * 510.0D;
/* 302 */           return new Vec3d(mc.field_71439_g.field_70165_t + x, mc.field_71439_g.field_70163_u + ((rawY > ((mc.field_71439_g.field_71093_bK == -1) ? 127 : 'ÿ')) ? -rawY : ((rawY < 1.0D) ? -rawY : rawY)), mc.field_71439_g.field_70161_v + z);
/*     */         } 
/* 304 */         return new Vec3d(mc.field_71439_g.field_70165_t + ((x == 0.0D) ? (random.nextBoolean() ? -10 : 10) : (x * 38.0D)), mc.field_71439_g.field_70163_u + y, mc.field_71439_g.field_70165_t + ((z == 0.0D) ? (random.nextBoolean() ? -10 : 10) : (z * 38.0D)));
/*     */       
/*     */       case OBSCURE:
/* 307 */         return new Vec3d(mc.field_71439_g.field_70165_t + randomHorizontal(), Math.max(1.5D, Math.min(mc.field_71439_g.field_70163_u + y, 253.5D)), mc.field_71439_g.field_70161_v + randomHorizontal());
/*     */     } 
/* 309 */     return new Vec3d(mc.field_71439_g.field_70165_t + x, ((Boolean)this.bounds.getValue()).booleanValue() ? (((Boolean)this.strict.getValue()).booleanValue() ? true : false) : (mc.field_71439_g.field_70163_u - 1337.0D), mc.field_71439_g.field_70161_v + z);
/*     */   }
/*     */ 
/*     */   
/*     */   public double randomHorizontal() {
/* 314 */     int randomValue = random.nextInt(((Boolean)this.bounds.getValue()).booleanValue() ? 80 : ((this.packetMode.getValue() == Mode.OBSCURE) ? ((mc.field_71439_g.field_70173_aa % 2 == 0) ? 480 : 100) : 29000000)) + (((Boolean)this.bounds.getValue()).booleanValue() ? 5 : 500);
/* 315 */     if (random.nextBoolean()) {
/* 316 */       return randomValue;
/*     */     }
/* 318 */     return -randomValue;
/*     */   }
/*     */   
/*     */   private void cleanPosLooks() {
/* 322 */     this.posLooks.forEach((tp, timeVec3d) -> {
/*     */           if (System.currentTimeMillis() - timeVec3d.getTime() > TimeUnit.SECONDS.toMillis(30L)) {
/*     */             this.posLooks.remove(tp);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 331 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/* 332 */       toggle();
/*     */       return;
/*     */     } 
/* 335 */     this.packets.clear();
/* 336 */     this.posLooks.clear();
/* 337 */     this.teleportId = 0;
/* 338 */     this.vDelay = 0;
/* 339 */     this.hDelay = 0;
/* 340 */     this.postYaw = -400.0F;
/* 341 */     this.postPitch = -400.0F;
/* 342 */     this.antiKickTicks = 0;
/* 343 */     this.limitTicks = 0;
/* 344 */     this.jitterTicks = 0;
/* 345 */     this.speedX = 0.0D;
/* 346 */     this.speedY = 0.0D;
/* 347 */     this.speedZ = 0.0D;
/* 348 */     this.oddJitter = false;
/* 349 */     this.startingOutOfBoundsPos = null;
/* 350 */     this.startingOutOfBoundsPos = new CPacketPlayer.Position(randomHorizontal(), 1.0D, randomHorizontal(), mc.field_71439_g.field_70122_E);
/* 351 */     this.packets.add(this.startingOutOfBoundsPos);
/* 352 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)this.startingOutOfBoundsPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 357 */     if (mc.field_71439_g != null) {
/* 358 */       mc.field_71439_g.func_70016_h(0.0D, 0.0D, 0.0D);
/*     */     }
/*     */     
/* 361 */     Thunderhack.TICK_TIMER = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onReceive(PacketEvent.Receive event) {
/* 368 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/*     */     
/* 372 */     if (event.getPacket() instanceof SPacketPlayerPosLook) {
/* 373 */       if (!(mc.field_71462_r instanceof net.minecraft.client.gui.GuiDownloadTerrain)) {
/* 374 */         SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
/* 375 */         if (mc.field_71439_g.func_70089_S()) {
/* 376 */           if (this.teleportId <= 0) {
/* 377 */             this.teleportId = ((SPacketPlayerPosLook)event.getPacket()).func_186965_f();
/*     */           }
/* 379 */           else if (mc.field_71441_e.func_175668_a(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v), false) && this.type
/* 380 */             .getValue() != Type.SETBACK) {
/* 381 */             if (this.type.getValue() == Type.DESYNC) {
/* 382 */               this.posLooks.remove(Integer.valueOf(packet.func_186965_f()));
/* 383 */               event.setCanceled(true);
/* 384 */               if (this.type.getValue() == Type.SLOW)
/* 385 */                 mc.field_71439_g.func_70107_b(packet.func_148932_c(), packet.func_148928_d(), packet.func_148933_e()); 
/*     */               return;
/*     */             } 
/* 388 */             if (this.posLooks.containsKey(Integer.valueOf(packet.func_186965_f()))) {
/* 389 */               TimeVec3d vec = this.posLooks.get(Integer.valueOf(packet.func_186965_f()));
/* 390 */               if (vec.field_72450_a == packet.func_148932_c() && vec.field_72448_b == packet.func_148928_d() && vec.field_72449_c == packet.func_148933_e()) {
/* 391 */                 this.posLooks.remove(Integer.valueOf(packet.func_186965_f()));
/* 392 */                 event.setCanceled(true);
/* 393 */                 if (this.type.getValue() == Type.SLOW) {
/* 394 */                   mc.field_71439_g.func_70107_b(packet.func_148932_c(), packet.func_148928_d(), packet.func_148933_e());
/*     */                 }
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/* 402 */         ((ISPacketPlayerPosLook)packet).setYaw(mc.field_71439_g.field_70177_z);
/* 403 */         ((ISPacketPlayerPosLook)packet).setPitch(mc.field_71439_g.field_70125_A);
/* 404 */         packet.func_179834_f().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
/* 405 */         packet.func_179834_f().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
/* 406 */         this.teleportId = packet.func_186965_f();
/*     */       } else {
/* 408 */         this.teleportId = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMove(EventMove event) {
/* 416 */     if (this.type.getValue() != Type.SETBACK && this.teleportId <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 420 */     if (this.type.getValue() != Type.SLOW) {
/* 421 */       event.set_x(this.speedX);
/* 422 */       event.set_y(this.speedY);
/* 423 */       event.set_z(this.speedZ);
/*     */     } 
/*     */     
/* 426 */     if ((this.phase.getValue() != Phase.NONE && this.phase.getValue() == Phase.VANILLA) || checkCollisionBox()) {
/* 427 */       mc.field_71439_g.field_70145_X = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean checkCollisionBox() {
/* 432 */     if (!mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72321_a(0.0D, 0.0D, 0.0D)).isEmpty()) {
/* 433 */       return true;
/*     */     }
/* 435 */     return !mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, 2.0D, 0.0D).func_191195_a(0.0D, 1.99D, 0.0D)).isEmpty();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSend(PacketEvent.Send event) {
/* 440 */     if (event.getPacket() instanceof CPacketPlayer && !(event.getPacket() instanceof CPacketPlayer.Position)) {
/* 441 */       event.setCanceled(true);
/*     */     }
/* 443 */     if (event.getPacket() instanceof CPacketPlayer) {
/* 444 */       CPacketPlayer packet = (CPacketPlayer)event.getPacket();
/* 445 */       if (this.packets.contains(packet)) {
/* 446 */         this.packets.remove(packet);
/*     */         return;
/*     */       } 
/* 449 */       event.setCanceled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPush(PushEvent event) {
/* 455 */     event.setCanceled(true);
/*     */   }
/*     */   
/*     */   public enum Limit {
/* 459 */     NONE, STRONG, STRICT;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 463 */     UP, PRESERVE, DOWN, LIMITJITTER, BYPASS, OBSCURE;
/*     */   }
/*     */   
/*     */   public enum Type {
/* 467 */     FACTOR, SETBACK, FAST, SLOW, DESYNC;
/*     */   }
/*     */   
/*     */   public enum Phase {
/* 471 */     NONE, VANILLA, NCP;
/*     */   }
/*     */   
/*     */   private enum AntiKick {
/* 475 */     NONE, NORMAL, LIMITED, STRICT;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\PacketFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */