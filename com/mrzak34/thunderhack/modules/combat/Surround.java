/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.surround.BlockPosWithFacing;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Surround extends Module {
/*     */   public final Setting<Boolean> multiThread;
/*     */   private final Setting<Integer> delay;
/*     */   private final Setting<EventMode> eventMode;
/*     */   private final Setting<Mode> mainMode;
/*     */   private final Setting<BlockMode> blockMode;
/*     */   private final Setting<Switch> switchMode;
/*     */   private final Setting<SwitchWhen> switchWhen;
/*     */   private final Setting<CBTimings> cBTimingsMode;
/*     */   private final Setting<CbMode> cbMode;
/*     */   private final Setting<CBRotateMode> cBRotateMode;
/*     */   private final Setting<Rotate> rotateMode;
/*     */   private final Setting<detectEntityMode> detectEntity;
/*     */   private final Setting<ToggleMode> toggleMode;
/*     */   public Setting<Boolean> syncronized;
/*     */   public Setting<Boolean> allEntities;
/*     */   public Setting<Boolean> extension;
/*     */   public Setting<Boolean> safeDynamic;
/*     */   public Setting<Boolean> rangeCheck;
/*     */   public Setting<Boolean> smartBlock;
/*     */   public Setting<Boolean> safeEChest;
/*     */   public Setting<Boolean> center;
/*     */   public Setting<Boolean> smartCenter;
/*     */   public Setting<Boolean> smartHelping;
/*     */   public Setting<Boolean> fightCA;
/*     */   public Setting<Boolean> detectSound;
/*     */   public Setting<Boolean> onEntityDestruction;
/*  49 */   private String really = " TheKisDevs & LavaHack Development owns you, and I am sorry, because it is uncrackable <3"; public Setting<Boolean> antiCity; public Setting<Boolean> manipulateWorld; public Setting<Boolean> postReceive; public Setting<Boolean> packet; public Setting<Boolean> feetBlocks; public Setting<Boolean> down; public Setting<Boolean> inAir; public Setting<Boolean> airMotion; public Setting<Boolean> crystalBreaker; public Setting<Boolean> cBRotate; public Setting<Boolean> cBPacket; public Setting<Boolean> cientSide; public final Setting<Boolean> cbTerrain; public final Setting<Boolean> cbNoSuicide; private final Setting<Float> heightLimit; private final Setting<Float> cBSequentialDelay; private final Setting<Float> cBRange; private final Setting<Integer> cBDelay; private final Setting<Float> placeRange; private final Setting<Float> toggleHeight; private ModeUtil modeUtil;
/*     */   private Timer breakTimer;
/*     */   private boolean haveBlock;
/*     */   private Function blockState;
/*     */   private double pos_Y;
/*     */   
/*     */   public Surround() {
/*  56 */     super("Surround", "окружает тебя обсой", "surrounds you", Module.Category.COMBAT);
/*     */     
/*  58 */     this.multiThread = register(new Setting("Multi Thread", Boolean.valueOf(false)));
/*  59 */     this.delay = register(new Setting("Delay", Integer.valueOf(15), Integer.valueOf(0), Integer.valueOf(100)));
/*     */     
/*  61 */     this.eventMode = register(new Setting("Event Mode", EventMode.SyncEvent));
/*     */ 
/*     */     
/*  64 */     this.mainMode = register(new Setting("Mode", Mode.Normal));
/*     */ 
/*     */     
/*  67 */     this.blockMode = register(new Setting("Block", BlockMode.Obsidian));
/*     */ 
/*     */     
/*  70 */     this.switchMode = register(new Setting("Switch", Switch.Silent));
/*     */ 
/*     */     
/*  73 */     this.switchWhen = register(new Setting("SwitchWhen", SwitchWhen.Place));
/*     */ 
/*     */     
/*  76 */     this.cBTimingsMode = register(new Setting("CB Timings", CBTimings.Adaptive));
/*     */ 
/*     */     
/*  79 */     this.cbMode = register(new Setting("CbMode", CbMode.SurroundBlocks));
/*     */ 
/*     */     
/*  82 */     this.cBRotateMode = register(new Setting("CBRotateMode", CBRotateMode.Packet));
/*     */ 
/*     */     
/*  85 */     this.rotateMode = register(new Setting("Rotate", Rotate.Packet));
/*     */ 
/*     */     
/*  88 */     this.detectEntity = register(new Setting("DetectEntity", detectEntityMode.RemoveEntity));
/*     */ 
/*     */     
/*  91 */     this.toggleMode = register(new Setting("Toggle", ToggleMode.OnComplete));
/*     */ 
/*     */     
/*  94 */     this.syncronized = register(new Setting("Syncronized", Boolean.valueOf(false)));
/*  95 */     this.allEntities = register(new Setting("AllEntities", Boolean.valueOf(false)));
/*  96 */     this.extension = register(new Setting("Extension", Boolean.valueOf(false)));
/*  97 */     this.safeDynamic = register(new Setting("Safe Dynamic", Boolean.valueOf(false)));
/*  98 */     this.rangeCheck = register(new Setting("RangeCheck", Boolean.valueOf(false)));
/*  99 */     this.smartBlock = register(new Setting("Smart Block", Boolean.valueOf(false)));
/* 100 */     this.safeEChest = register(new Setting("Safe E Chest", Boolean.valueOf(false)));
/* 101 */     this.center = register(new Setting("Center", Boolean.valueOf(true)));
/* 102 */     this.smartCenter = register(new Setting("SmartCenter", Boolean.valueOf(false)));
/* 103 */     this.smartHelping = register(new Setting("SmartHelping", Boolean.valueOf(false)));
/* 104 */     this.fightCA = register(new Setting("FightCA", Boolean.valueOf(true)));
/* 105 */     this.detectSound = register(new Setting("DetectSound", Boolean.valueOf(true)));
/* 106 */     this.onEntityDestruction = register(new Setting("OnEntityDestruction", Boolean.valueOf(false)));
/* 107 */     this.antiCity = register(new Setting("AntiCity", Boolean.valueOf(false)));
/* 108 */     this.manipulateWorld = register(new Setting("ManipulateWorld", Boolean.valueOf(false)));
/* 109 */     this.postReceive = register(new Setting("PostReceive", Boolean.valueOf(false)));
/* 110 */     this.packet = register(new Setting("Packet", Boolean.valueOf(false)));
/* 111 */     this.feetBlocks = register(new Setting("FeetBlocks", Boolean.valueOf(false)));
/* 112 */     this.down = register(new Setting("Down", Boolean.valueOf(false)));
/* 113 */     this.inAir = register(new Setting("InAir", Boolean.valueOf(false)));
/* 114 */     this.airMotion = register(new Setting("InAirMotionStop", Boolean.valueOf(false)));
/* 115 */     this.crystalBreaker = register(new Setting("CrystalBreaker", Boolean.valueOf(true)));
/* 116 */     this.cBRotate = register(new Setting("CBRotate", Boolean.valueOf(false)));
/* 117 */     this.cBPacket = register(new Setting("CBPacket", Boolean.valueOf(false)));
/* 118 */     this.cientSide = register(new Setting("ClientSide", Boolean.valueOf(false)));
/* 119 */     this.cbTerrain = register(new Setting("CbTerrain", Boolean.valueOf(true)));
/* 120 */     this.cbNoSuicide = register(new Setting("CbNoSuicide", Boolean.valueOf(true)));
/* 121 */     this.heightLimit = register(new Setting("HeightLimit", Float.valueOf(256.0F), Float.valueOf(0.0F), Float.valueOf(256.0F)));
/* 122 */     this.cBSequentialDelay = register(new Setting("CBSequentialDelay", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/* 123 */     this.cBRange = register(new Setting("CBRange", Float.valueOf(3.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/* 124 */     this.cBDelay = register(new Setting("CBDelay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500)));
/* 125 */     this.placeRange = register(new Setting("PlaceRange", Float.valueOf(5.0F), Float.valueOf(2.0F), Float.valueOf(10.0F)));
/* 126 */     this.toggleHeight = register(new Setting("ToggleHeight", Float.valueOf(0.4F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*     */     
/* 128 */     this.modeUtil = new ModeUtil();
/* 129 */     this.breakTimer = new Timer();
/* 130 */     this.haveBlock = false;
/* 131 */     this.blockState = Surround::getBlockState; } public enum EventMode {
/*     */     Tick, Update, SyncEvent; } public enum Mode {
/*     */     High, AntiFacePlace, Dynamic, Cubic, Safe, SemiSafe, Strict, Normal; } public enum BlockMode {
/*     */     Obsidian, EnderChest; } public enum Switch {
/* 135 */     None, Vanilla, Packet, Silent; } public enum SwitchWhen { Place, RunSurround; } public enum CBTimings { Sequential, Adaptive; } public enum CbMode { SurroundBlocks, Area; } public enum CBRotateMode { Client, Packet, Both; } public enum Rotate { None, Packet, Silent; } public enum detectEntityMode { Off, RemoveEntity, SetDead, Both; } public enum ToggleMode { Never, OffGround, OnComplete, Combo, PositiveYChange, YChange; } private static final ScheduledExecutorService THREAD = ThreadUtil.newDaemonScheduledExecutor("SURROUND");
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onTick(TickEvent tickEvent) {
/* 141 */     if (this.eventMode.getValue() != EventMode.Tick) {
/*     */       return;
/*     */     }
/* 144 */     if (((Boolean)this.syncronized.getValue()).booleanValue()) {
/* 145 */       doSynchronized();
/*     */       return;
/*     */     } 
/* 148 */     doNonSynchronized();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 153 */     if (fullNullCheck())
/* 154 */       return;  if (this.eventMode.getValue() != EventMode.Update)
/* 155 */       return;  if (((Boolean)this.syncronized.getValue()).booleanValue()) {
/* 156 */       doSynchronized();
/*     */     } else {
/* 158 */       doNonSynchronized();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onSync(EventSync e) {
/* 164 */     if (this.eventMode.getValue() != EventMode.SyncEvent) {
/*     */       return;
/*     */     }
/* 167 */     if (((Boolean)this.syncronized.getValue()).booleanValue()) {
/* 168 */       doSynchronized();
/*     */       return;
/*     */     } 
/* 171 */     doNonSynchronized();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 176 */     this.breakTimer.reset();
/* 177 */     if (fullNullCheck())
/* 178 */       return;  this.pos_Y = mc.field_71439_g.field_70163_u;
/* 179 */     if (((Boolean)this.center.getValue()).booleanValue() && !setCenter() && 
/* 180 */       this.toggleMode.getValue() != ToggleMode.Never)
/* 181 */       disable(); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive2(PacketEvent.Receive event) {
/* 186 */     if (!((Boolean)this.onEntityDestruction.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 189 */     if (!(event.getPacket() instanceof SPacketDestroyEntities)) {
/*     */       return;
/*     */     }
/* 192 */     SPacketDestroyEntities sPacketDestroyEntities = (SPacketDestroyEntities)event.getPacket();
/* 193 */     int[] nArray = sPacketDestroyEntities.func_149098_c();
/* 194 */     for (int n2 : nArray) {
/* 195 */       mc.field_71441_e.func_73028_b(n2);
/*     */     }
/* 197 */     if (((Boolean)this.syncronized.getValue()).booleanValue()) {
/* 198 */       doSynchronized();
/*     */       return;
/*     */     } 
/* 201 */     doNonSynchronized();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceivePost(PacketEvent.ReceivePost e) {
/* 206 */     if (!((Boolean)this.postReceive.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 209 */     doAntiCity(new PacketEvent.Receive(e.getPacket()));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceivePre(PacketEvent.Receive e) {
/* 214 */     if (((Boolean)this.postReceive.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 217 */     doAntiCity(e);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSpawnCrystal(EventEntitySpawn event) {
/* 222 */     if (!((Boolean)this.fightCA.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 225 */     if (this.detectEntity.getValue() == detectEntityMode.Off) {
/*     */       return;
/*     */     }
/* 228 */     Entity entity = event.getEntity();
/* 229 */     List<BlockPos> list = this.modeUtil.getBlockPositions((Mode)this.mainMode.getValue());
/* 230 */     if (!checkIntersections(entity.func_174813_aQ(), list)) {
/*     */       return;
/*     */     }
/* 233 */     if (this.detectEntity.getValue() == detectEntityMode.SetDead || this.detectEntity.getValue() == detectEntityMode.Off) {
/* 234 */       entity.func_70106_y();
/*     */     }
/* 236 */     if (this.detectEntity.getValue() == detectEntityMode.RemoveEntity || this.detectEntity.getValue() == detectEntityMode.Both) {
/* 237 */       mc.field_71441_e.func_72900_e(entity);
/*     */     }
/* 239 */     if (((Boolean)this.syncronized.getValue()).booleanValue()) {
/* 240 */       doSynchronized();
/*     */       return;
/*     */     } 
/* 243 */     doNonSynchronized();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 248 */     if (event.getPacket() instanceof SPacketSoundEffect) {
/* 249 */       SPacketSoundEffect sPacketSoundEffect1 = (SPacketSoundEffect)event.getPacket();
/* 250 */       if (sPacketSoundEffect1.func_186978_a() == SoundEvents.field_187845_fY) {
/* 251 */         event.setCanceled(true);
/*     */       }
/*     */     } 
/* 254 */     if (!((Boolean)this.fightCA.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 257 */     if (!((Boolean)this.detectSound.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 260 */     if (!(event.getPacket() instanceof SPacketSoundEffect)) {
/*     */       return;
/*     */     }
/* 263 */     SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect)event.getPacket();
/* 264 */     if (sPacketSoundEffect.func_186978_a() != SoundEvents.field_187539_bB) {
/*     */       return;
/*     */     }
/* 267 */     Vec3d vec3d = new Vec3d(sPacketSoundEffect.func_149207_d(), sPacketSoundEffect.func_149211_e(), sPacketSoundEffect.func_149210_f());
/* 268 */     if (!doesCrystalWantToFuckUs(vec3d, this.modeUtil.getBlockPositions((Mode)this.mainMode.getValue()))) {
/*     */       return;
/*     */     }
/* 271 */     if (((Boolean)this.syncronized.getValue()).booleanValue()) {
/* 272 */       doSynchronized();
/*     */       return;
/*     */     } 
/* 275 */     doNonSynchronized();
/*     */   }
/*     */ 
/*     */   
/*     */   private void doNonSynchronized() {
/* 280 */     if (((Boolean)this.multiThread.getValue()).booleanValue()) {
/* 281 */       THREAD.schedule(this::handleSurround, ((Integer)this.delay.getValue()).intValue(), TimeUnit.MILLISECONDS);
/*     */     } else {
/* 283 */       handleSurround();
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized void doSynchronized() {
/* 288 */     if (((Boolean)this.multiThread.getValue()).booleanValue()) {
/* 289 */       THREAD.schedule(this::handleSurround, ((Integer)this.delay.getValue()).intValue(), TimeUnit.MILLISECONDS);
/*     */     } else {
/* 291 */       handleSurround();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleSurround() {
/* 296 */     if (fullNullCheck())
/* 297 */       return;  if (mc.field_71439_g.field_70173_aa < 60)
/* 298 */       return;  if (!((Boolean)this.inAir.getValue()).booleanValue() || mc.field_71439_g.field_70122_E) {
/* 299 */       if (((Boolean)this.inAir.getValue()).booleanValue() && ((Boolean)this.airMotion.getValue()).booleanValue() && !mc.field_71439_g.field_70122_E) {
/* 300 */         mc.field_71439_g.field_70159_w = 0.0D;
/* 301 */         mc.field_71439_g.field_70179_y = 0.0D;
/*     */       } 
/* 303 */       int n2 = mc.field_71439_g.field_71071_by.field_70461_c;
/* 304 */       int n = getSlotWithBestBlock();
/* 305 */       if (n == -1) {
/*     */         return;
/*     */       }
/* 308 */       if (this.switchWhen.getValue() == SwitchWhen.RunSurround) {
/* 309 */         SwitchMethod((Switch)this.switchMode.getValue(), n, false);
/*     */       }
/* 311 */       List<BlockPos> positions = this.modeUtil.getBlockPositions((Mode)this.mainMode.getValue());
/* 312 */       doPlace(positions);
/* 313 */       if (((Boolean)this.crystalBreaker.getValue()).booleanValue()) {
/* 314 */         doCrystalBreaker(positions);
/*     */       }
/* 316 */       if (this.switchWhen.getValue() == SwitchWhen.RunSurround) {
/* 317 */         SwitchMethod((Switch)this.switchMode.getValue(), n2, true);
/*     */       }
/*     */     } 
/* 320 */     if (mc.field_71439_g.field_70163_u > this.pos_Y + ((Float)this.toggleHeight.getValue()).floatValue() && this.toggleMode.getValue() == ToggleMode.PositiveYChange) {
/* 321 */       disable();
/*     */     }
/* 323 */     if (mc.field_71439_g.field_70163_u != this.pos_Y && this.toggleMode.getValue() == ToggleMode.YChange) {
/* 324 */       disable();
/*     */     }
/* 326 */     if ((mc.field_71439_g.field_70163_u != this.pos_Y || !mc.field_71439_g.field_70122_E) && this.toggleMode.getValue() == ToggleMode.Combo) {
/* 327 */       disable();
/*     */     }
/* 329 */     if (this.toggleMode.getValue() == ToggleMode.OnComplete) {
/* 330 */       disable();
/*     */     }
/* 332 */     if (!mc.field_71439_g.field_70122_E && this.toggleMode.getValue() == ToggleMode.OffGround) {
/* 333 */       disable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 339 */     this.blockState = Surround::getBlockState;
/* 340 */     this.breakTimer.reset();
/*     */   }
/*     */   
/*     */   private void doCrystalBreaker(List<BlockPos> list) {
/* 344 */     if (!this.breakTimer.passedMs(((Integer)this.cBDelay.getValue()).intValue())) {
/*     */       return;
/*     */     }
/* 347 */     float[] fArray = new float[2];
/* 348 */     fArray[0] = mc.field_71439_g.field_70177_z;
/* 349 */     fArray[1] = mc.field_71439_g.field_70125_A;
/* 350 */     HashSet<EntityEnderCrystal> hashSet = new HashSet<>(64);
/* 351 */     if (this.cbMode.getValue() == CbMode.Area) {
/* 352 */       double d = ((Float)this.cBRange.getValue()).floatValue();
/* 353 */       double d2 = mc.field_71439_g.field_70165_t - d;
/* 354 */       double d3 = mc.field_71439_g.field_70163_u - d;
/* 355 */       double d4 = mc.field_71439_g.field_70161_v - d;
/* 356 */       double d5 = mc.field_71439_g.field_70165_t + d;
/* 357 */       double d6 = mc.field_71439_g.field_70163_u + d;
/* 358 */       double d7 = mc.field_71439_g.field_70161_v + d;
/* 359 */       AxisAlignedBB axisAlignedBB = new AxisAlignedBB(d2, d3, d4, d5, d6, d7);
/* 360 */       for (EntityEnderCrystal entityEnderCrystal : mc.field_71441_e.func_72872_a(EntityEnderCrystal.class, axisAlignedBB)) {
/* 361 */         if (!canBreakCrystal(entityEnderCrystal)) {
/*     */           return;
/*     */         }
/* 364 */         breakCrystal(entityEnderCrystal, fArray);
/*     */       } 
/*     */       return;
/*     */     } 
/* 368 */     Iterator<BlockPos> iterator = list.iterator();
/* 369 */     while (iterator.hasNext()) {
/* 370 */       BlockPos blockPos = iterator.next();
/* 371 */       Iterator<EntityEnderCrystal> iterator2 = mc.field_71441_e.func_72872_a(EntityEnderCrystal.class, new AxisAlignedBB(blockPos)).iterator();
/*     */       
/* 373 */       while (iterator2.hasNext()) {
/* 374 */         EntityEnderCrystal entityEnderCrystal = iterator2.next();
/* 375 */         if (hashSet.contains(entityEnderCrystal) || !canBreakCrystal(entityEnderCrystal))
/* 376 */           continue;  breakCrystal(entityEnderCrystal, fArray);
/* 377 */         hashSet.add(entityEnderCrystal);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canBreakCrystal(EntityEnderCrystal entityEnderCrystal) {
/* 385 */     if (this.cBTimingsMode.getValue() == CBTimings.Sequential && entityEnderCrystal.field_70173_aa < ((Float)this.cBSequentialDelay.getValue()).floatValue()) {
/* 386 */       return false;
/*     */     }
/* 388 */     if (!((Boolean)this.cbNoSuicide.getValue()).booleanValue()) {
/* 389 */       return true;
/*     */     }
/* 391 */     float f = DamageUtil.calculateDamage(entityEnderCrystal.field_70165_t, entityEnderCrystal.field_70163_u, entityEnderCrystal.field_70161_v, (Entity)mc.field_71439_g, ((Boolean)this.cbTerrain.getValue()).booleanValue());
/* 392 */     return (f < mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj());
/*     */   }
/*     */   
/*     */   private void breakCrystal(EntityEnderCrystal entityEnderCrystal, float[] fArray) {
/* 396 */     if (((Boolean)this.cBRotate.getValue()).booleanValue()) {
/* 397 */       float[] fArray2 = SilentRotationUtil.calcAngle(entityEnderCrystal.func_174791_d());
/* 398 */       rotateToCrystal(fArray2);
/*     */     } 
/* 400 */     if (((Boolean)this.cBPacket.getValue()).booleanValue()) {
/* 401 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity((Entity)entityEnderCrystal));
/*     */     } else {
/* 403 */       mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, (Entity)entityEnderCrystal);
/*     */     } 
/* 405 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 406 */     if (((Boolean)this.cientSide.getValue()).booleanValue()) {
/* 407 */       mc.field_71441_e.func_73028_b(entityEnderCrystal.func_145782_y());
/*     */     }
/* 409 */     if (!((Boolean)this.cBRotate.getValue()).booleanValue())
/* 410 */       return;  rotateToCrystal(fArray);
/*     */   }
/*     */   
/*     */   private void rotateToCrystal(float[] fArray) {
/* 414 */     if (this.cBRotateMode.getValue() != CBRotateMode.Client) {
/* 415 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(fArray[0], fArray[1], mc.field_71439_g.field_70122_E));
/*     */     }
/* 417 */     if (this.cBRotateMode.getValue() != CBRotateMode.Client && 
/* 418 */       this.cBRotateMode.getValue() != CBRotateMode.Both)
/*     */       return; 
/* 420 */     mc.field_71439_g.field_70177_z = fArray[0];
/* 421 */     mc.field_71439_g.field_70125_A = fArray[1];
/*     */   }
/*     */   
/*     */   private boolean setCenter() {
/* 425 */     if (!((Boolean)this.smartCenter.getValue()).booleanValue()) {
/* 426 */       setCenterPos(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v));
/* 427 */       return true;
/*     */     } 
/* 429 */     BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/* 430 */     if (checkBlockPos(blockPos)) {
/* 431 */       blockPos = getCenterBlockPos(blockPos);
/*     */     }
/* 433 */     if (blockPos == null) {
/* 434 */       return false;
/*     */     }
/* 436 */     setCenterPos(blockPos);
/* 437 */     return true;
/*     */   }
/*     */   
/*     */   private void setCenterPos(BlockPos blockPos) {
/* 441 */     Vec3d vec3d = new Vec3d(blockPos.func_177958_n() + 0.5D, mc.field_71439_g.field_70163_u, blockPos.func_177952_p() + 0.5D);
/* 442 */     mc.field_71439_g.field_70159_w = 0.0D;
/* 443 */     mc.field_71439_g.field_70179_y = 0.0D;
/* 444 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, true));
/* 445 */     mc.field_71439_g.func_70107_b(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void doPlace(List<BlockPos> list) {
/* 451 */     int n = getSlotWithBestBlock();
/* 452 */     if (n == -1) {
/*     */       return;
/*     */     }
/* 455 */     int n2 = mc.field_71439_g.field_71071_by.field_70461_c;
/* 456 */     if (this.switchMode.getValue() == Switch.None) {
/* 457 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(n2);
/* 458 */       Item item = itemStack.func_77973_b();
/* 459 */       if (!(item instanceof ItemBlock)) {
/*     */         return;
/*     */       }
/* 462 */       Block block2 = ((ItemBlock)item).func_179223_d();
/* 463 */       if (block2 != getBlockByMode()) {
/*     */         return;
/*     */       }
/*     */     } 
/* 467 */     for (BlockPos o : list) {
/* 468 */       if (o.func_177956_o() > ((Float)this.heightLimit.getValue()).floatValue() || !checkBlockPos(o) || getInterferingEntities(o) || (((Boolean)this.rangeCheck.getValue()).booleanValue() && mc.field_71439_g.func_174818_b(o) > ((Float)this.placeRange.getValue()).floatValue()))
/*     */         continue; 
/* 470 */       SwitchMethod((Switch)this.switchMode.getValue(), n, false);
/* 471 */       PlaceMethod(o, EnumHand.MAIN_HAND, (Rotate)this.rotateMode.getValue(), ((Boolean)this.packet.getValue()).booleanValue());
/* 472 */       SwitchMethod((Switch)this.switchMode.getValue(), n2, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void PlaceMethod(BlockPos blockPos, EnumHand enumHand, Rotate rotate, boolean bl) {
/* 477 */     EnumFacing enumFacing = getFacing(blockPos);
/* 478 */     if (enumFacing == null) {
/*     */       return;
/*     */     }
/* 481 */     BlockPos blockPos2 = blockPos.func_177972_a(enumFacing);
/* 482 */     EnumFacing enumFacing2 = enumFacing.func_176734_d();
/* 483 */     Vec3d vec3d = (new Vec3d((Vec3i)blockPos2)).func_178787_e((new Vec3d(0.5D, 0.5D, 0.5D)).func_178787_e((new Vec3d(enumFacing2.func_176730_m())).func_186678_a(0.5D)));
/* 484 */     boolean sneak = mc.field_71441_e.func_180495_p(blockPos2).func_177230_c().func_180639_a((World)mc.field_71441_e, blockPos2, mc.field_71441_e.func_180495_p(blockPos2), (EntityPlayer)mc.field_71439_g, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
/* 485 */     if (sneak) {
/* 486 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */     }
/* 488 */     float[] angle = getNeededRotations2(blockPos2);
/*     */     
/* 490 */     if (rotate == Rotate.Packet) {
/* 491 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], mc.field_71439_g.field_70122_E));
/*     */     }
/* 493 */     if (rotate == Rotate.Silent) {
/* 494 */       mc.field_71439_g.field_70177_z = angle[0];
/* 495 */       mc.field_71439_g.field_70125_A = angle[1];
/*     */     } 
/*     */     
/* 498 */     placeMethod(blockPos2, vec3d, enumHand, enumFacing2, bl);
/* 499 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 500 */     if (sneak) {
/* 501 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */     }
/* 503 */     if (rotate == Rotate.Packet) {
/* 504 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], mc.field_71439_g.field_70122_E));
/*     */     }
/*     */   }
/*     */   
/*     */   public static float[] getNeededRotations2(BlockPos bp) {
/* 509 */     Vec3d eyesPos = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/* 510 */     double diffX = bp.func_177958_n() - eyesPos.field_72450_a;
/* 511 */     double diffY = bp.func_177956_o() - eyesPos.field_72448_b;
/* 512 */     double diffZ = bp.func_177952_p() - eyesPos.field_72449_c;
/* 513 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/* 514 */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/* 515 */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/* 516 */     return new float[] { mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z), mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A) };
/*     */   }
/*     */   
/*     */   public static void placeMethod(BlockPos blockPos, Vec3d vec3d, EnumHand enumHand, EnumFacing enumFacing, boolean bl) {
/* 520 */     if (bl) {
/* 521 */       float f = (float)(vec3d.field_72450_a - blockPos.func_177958_n());
/* 522 */       float f2 = (float)(vec3d.field_72448_b - blockPos.func_177956_o());
/* 523 */       float f3 = (float)(vec3d.field_72449_c - blockPos.func_177952_p());
/* 524 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos, enumFacing, enumHand, f, f2, f3));
/*     */     } else {
/* 526 */       mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, blockPos, enumFacing, vec3d, enumHand);
/*     */     } 
/* 528 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 529 */     ((IMinecraft)mc).setRightClickDelayTimer(4);
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacing(BlockPos blockPos) {
/* 533 */     Iterator<EnumFacing> iterator = getFacings(blockPos).iterator();
/* 534 */     if (!iterator.hasNext()) return null; 
/* 535 */     return iterator.next();
/*     */   }
/*     */   
/*     */   private void SwitchMethod(Switch mode, int slot, boolean update_controller) {
/* 539 */     if (mc.field_71439_g.field_71071_by.field_70461_c == slot) {
/*     */       return;
/*     */     }
/* 542 */     if (mode == Switch.Packet) {
/* 543 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 544 */     } else if (mode == Switch.Silent) {
/* 545 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 546 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/* 547 */     } else if (mode == Switch.Vanilla && 
/* 548 */       !update_controller) {
/* 549 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 550 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/*     */     } 
/*     */     
/* 553 */     if (update_controller) mc.field_71442_b.func_78765_e(); 
/*     */   }
/*     */   
/*     */   private void doAntiCity(PacketEvent.Receive event) {
/* 557 */     if (!((Boolean)this.antiCity.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/* 560 */     if (!(event.getPacket() instanceof SPacketBlockChange)) {
/*     */       return;
/*     */     }
/* 563 */     SPacketBlockChange sPacketBlockChange = (SPacketBlockChange)event.getPacket();
/* 564 */     BlockPos blockPos = sPacketBlockChange.func_179827_b();
/* 565 */     if (!sPacketBlockChange.func_180728_a().func_177230_c().func_176200_f((IBlockAccess)mc.field_71441_e, blockPos)) {
/*     */       return;
/*     */     }
/* 568 */     List<BlockPos> list = this.modeUtil.getBlockPositions((Mode)this.mainMode.getValue());
/* 569 */     if (!list.contains(blockPos)) {
/*     */       return;
/*     */     }
/* 572 */     if (((Boolean)this.manipulateWorld.getValue()).booleanValue()) {
/* 573 */       this.blockState = (arg_0 -> getBlockStateAS(list, (BlockPos)arg_0));
/*     */     }
/* 575 */     if (((Boolean)this.syncronized.getValue()).booleanValue()) {
/* 576 */       doSynchronized();
/*     */     } else {
/* 578 */       doNonSynchronized();
/*     */     } 
/* 580 */     this.blockState = Surround::getBlockState;
/*     */   }
/*     */   
/*     */   private static IBlockState getBlockStateAS(List<BlockPos> list, BlockPos blockPos) {
/* 584 */     if (list.contains(blockPos)) return Blocks.field_150350_a.func_176223_P(); 
/* 585 */     return mc.field_71441_e.func_180495_p(blockPos);
/*     */   }
/*     */   
/*     */   private boolean checkIntersections(AxisAlignedBB axisAlignedBB, List<BlockPos> list) {
/* 589 */     Iterator<BlockPos> iterator = list.iterator();
/*     */     while (true) {
/* 591 */       if (!iterator.hasNext()) return false; 
/* 592 */       if ((new AxisAlignedBB(iterator.next())).func_72326_a(axisAlignedBB))
/* 593 */         return true; 
/*     */     } 
/*     */   }
/*     */   private boolean getInterferingEntities(BlockPos blockPos) {
/* 597 */     AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos);
/* 598 */     for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, axisAlignedBB)) {
/* 599 */       if (!(entity instanceof net.minecraft.entity.item.EntityItem) && 
/* 600 */         !(entity instanceof net.minecraft.entity.item.EntityXPOrb)) return true; 
/*     */     } 
/* 602 */     return false;
/*     */   }
/*     */   
/*     */   private int getSlotWithBestBlock() {
/* 606 */     int obby_slot = InventoryUtil.findHotbarBlock(Blocks.field_150343_Z);
/* 607 */     int echest_slot = InventoryUtil.findHotbarBlock(Blocks.field_150477_bB);
/* 608 */     if (this.blockMode.getValue() == BlockMode.Obsidian) {
/* 609 */       this.haveBlock = (((Boolean)this.smartBlock.getValue()).booleanValue() && obby_slot == -1);
/* 610 */       return obby_slot;
/*     */     } 
/* 612 */     this.haveBlock = (!((Boolean)this.smartBlock.getValue()).booleanValue() && echest_slot != -1);
/* 613 */     return echest_slot;
/*     */   }
/*     */   
/*     */   private Block getBlockByMode() {
/* 617 */     if (this.blockMode.getValue() != BlockMode.Obsidian) return Blocks.field_150477_bB; 
/* 618 */     return Blocks.field_150343_Z;
/*     */   }
/*     */   
/*     */   private BlockPos getCenterBlockPos(BlockPos blockPos) {
/* 622 */     ArrayList<BlockPos> arrayList = new ArrayList<>();
/* 623 */     if (((IBlockState)this.blockState.apply(blockPos.func_177978_c().func_177977_b())).func_185904_a().func_76220_a()) {
/* 624 */       arrayList.add(blockPos.func_177978_c());
/*     */     }
/* 626 */     if (((IBlockState)this.blockState.apply(blockPos.func_177974_f().func_177977_b())).func_185904_a().func_76220_a()) {
/* 627 */       arrayList.add(blockPos.func_177974_f());
/*     */     }
/* 629 */     if (((IBlockState)this.blockState.apply(blockPos.func_177968_d().func_177977_b())).func_185904_a().func_76220_a()) {
/* 630 */       arrayList.add(blockPos.func_177968_d());
/*     */     }
/* 632 */     if (!((IBlockState)this.blockState.apply(blockPos.func_177976_e().func_177977_b())).func_185904_a().func_76220_a()) return arrayList.stream().min(Comparator.comparingDouble(Surround::getDistanceToBlock)).orElse(null); 
/* 633 */     arrayList.add(blockPos.func_177976_e());
/* 634 */     return arrayList.stream().min(Comparator.comparingDouble(Surround::getDistanceToBlock)).orElse(null);
/*     */   }
/*     */   
/*     */   public List<BlockPos> getDynamicPositions() {
/* 638 */     if (!((Boolean)this.extension.getValue()).booleanValue()) return getDynamicPositionWOE(); 
/* 639 */     return getDynamicPositionWE();
/*     */   }
/*     */   
/*     */   private List<BlockPos> getDynamicPositionWOE() {
/* 643 */     List<BlockPos> list = checkEntities((Entity)mc.field_71439_g, mc.field_71439_g.field_70163_u);
/* 644 */     ArrayList<BlockPos> arrayList = new ArrayList<>(16);
/* 645 */     if (((Boolean)this.feetBlocks.getValue()).booleanValue()) {
/* 646 */       arrayList.addAll(checkHitBoxes((Entity)mc.field_71439_g, mc.field_71439_g.field_70163_u, -1));
/*     */     }
/* 648 */     for (BlockPos o : list) {
/* 649 */       List<BlockPos> list2 = getSmartHelpingPositions(o);
/* 650 */       arrayList.addAll(list2);
/* 651 */       arrayList.add(o);
/*     */     } 
/* 653 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<BlockPos> getDynamicPositionWE() {
/* 658 */     List<BlockPos> list2 = getDynamicPositionWOE();
/* 659 */     List<Entity> list3 = new ArrayList<>();
/* 660 */     for (BlockPos bp : list2) {
/* 661 */       List<Entity> list = mc.field_71441_e.func_72872_a(((Boolean)this.allEntities.getValue()).booleanValue() ? Entity.class : EntityPlayer.class, new AxisAlignedBB(bp));
/* 662 */       if (list3.isEmpty()) {
/* 663 */         list3 = mc.field_71441_e.func_72872_a(EntityPlayer.class, new AxisAlignedBB(bp.func_177977_b()));
/*     */       }
/* 665 */       list3.addAll(list);
/*     */     } 
/* 667 */     ArrayList<BlockPos> arrayList = new ArrayList<>(list2);
/*     */     
/* 669 */     for (Entity value : list3) {
/*     */       
/* 671 */       if (value.equals(mc.field_71439_g))
/* 672 */         continue;  List<BlockPos> list4 = checkEntities(value, mc.field_71439_g.field_70163_u);
/* 673 */       ArrayList<BlockPos> arrayList2 = new ArrayList<>(16);
/* 674 */       if (((Boolean)this.feetBlocks.getValue()).booleanValue()) {
/* 675 */         arrayList2.addAll(checkHitBoxes(value, mc.field_71439_g.field_70163_u, -1));
/*     */       }
/* 677 */       for (BlockPos bp : list4) {
/* 678 */         List<BlockPos> object3 = getSmartHelpingPositions(bp);
/* 679 */         arrayList2.addAll(object3);
/* 680 */         arrayList2.add(bp);
/*     */       } 
/* 682 */       ArrayList<Entity> arrayList3 = new ArrayList<>(list3);
/* 683 */       arrayList3.add(mc.field_71439_g);
/*     */       
/* 685 */       for (Entity entity : arrayList3) {
/* 686 */         List<BlockPos> list5 = checkHitBoxes(entity, mc.field_71439_g.field_70163_u, 0);
/* 687 */         for (BlockPos blockPos : arrayList2) {
/* 688 */           if (!list5.contains(blockPos))
/* 689 */             continue;  list5.add(blockPos);
/*     */         } 
/*     */       } 
/* 692 */       arrayList2.removeAll(list4);
/* 693 */       arrayList.addAll(arrayList2);
/*     */     } 
/* 695 */     return arrayList;
/*     */   }
/*     */   
/*     */   private List<BlockPos> getSmartHelpingPositions(BlockPos blockPos) {
/* 699 */     if (!((Boolean)this.smartHelping.getValue()).booleanValue()) return Collections.singletonList(blockPos.func_177977_b()); 
/* 700 */     if (getFacings(blockPos).isEmpty()) return Collections.singletonList(blockPos.func_177977_b()); 
/* 701 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   private List<BlockPos> checkEntities(Entity entity, double d) {
/* 705 */     List<BlockPos> list = checkHitBoxes(entity, d, 0);
/* 706 */     ArrayList<BlockPos> arrayList = new ArrayList<>(16);
/* 707 */     for (BlockPos blockPos : list) {
/* 708 */       BlockPos blockPos2 = blockPos.func_177978_c();
/* 709 */       BlockPos blockPos3 = blockPos.func_177974_f();
/* 710 */       BlockPos blockPos4 = blockPos.func_177968_d();
/* 711 */       BlockPos blockPos5 = blockPos.func_177976_e();
/* 712 */       if (!list.contains(blockPos2)) {
/* 713 */         arrayList.add(blockPos2);
/*     */       }
/* 715 */       if (!list.contains(blockPos3)) {
/* 716 */         arrayList.add(blockPos3);
/*     */       }
/* 718 */       if (!list.contains(blockPos4)) {
/* 719 */         arrayList.add(blockPos4);
/*     */       }
/* 721 */       if (!list.contains(blockPos5)) {
/* 722 */         arrayList.add(blockPos5);
/*     */       }
/* 724 */       if (!((Boolean)this.safeDynamic.getValue()).booleanValue() && (!((Boolean)this.safeEChest.getValue()).booleanValue() || !this.haveBlock))
/* 725 */         continue;  BlockPos blockPos6 = blockPos.func_177978_c().func_177976_e();
/* 726 */       BlockPos blockPos7 = blockPos.func_177978_c().func_177974_f();
/* 727 */       BlockPos blockPos8 = blockPos.func_177968_d().func_177974_f();
/* 728 */       BlockPos blockPos9 = blockPos.func_177968_d().func_177976_e();
/* 729 */       if (!list.contains(blockPos6)) {
/* 730 */         arrayList.add(blockPos6);
/*     */       }
/* 732 */       if (!list.contains(blockPos7)) {
/* 733 */         arrayList.add(blockPos7);
/*     */       }
/* 735 */       if (!list.contains(blockPos8)) {
/* 736 */         arrayList.add(blockPos8);
/*     */       }
/* 738 */       if (list.contains(blockPos9))
/* 739 */         continue;  arrayList.add(blockPos9);
/*     */     } 
/* 741 */     return arrayList;
/*     */   }
/*     */   
/*     */   public List<BlockPos> checkHitBoxes(Entity entity, double d, int n) {
/* 745 */     ArrayList<BlockPos> arrayList = new ArrayList<>(16);
/* 746 */     AxisAlignedBB axisAlignedBB = entity.func_174813_aQ();
/* 747 */     double d2 = (axisAlignedBB.field_72336_d - axisAlignedBB.field_72340_a) / 2.0D;
/* 748 */     double d3 = (axisAlignedBB.field_72334_f - axisAlignedBB.field_72339_c) / 2.0D;
/* 749 */     Vec3d vec3d = new Vec3d(entity.field_70165_t + d2, d + n, entity.field_70161_v + d3);
/* 750 */     Vec3d vec3d2 = new Vec3d(entity.field_70165_t + d2, d + n, entity.field_70161_v - d3);
/* 751 */     Vec3d vec3d3 = new Vec3d(entity.field_70165_t - d2, d + n, entity.field_70161_v + d3);
/* 752 */     Vec3d vec3d4 = new Vec3d(entity.field_70165_t - d2, d + n, entity.field_70161_v - d3);
/* 753 */     addBlockToList(vec3d, arrayList);
/* 754 */     addBlockToList(vec3d2, arrayList);
/* 755 */     addBlockToList(vec3d3, arrayList);
/* 756 */     addBlockToList(vec3d4, arrayList);
/* 757 */     return arrayList;
/*     */   }
/*     */   
/*     */   public List<BlockPos> getAntiFacePlacePositions() {
/* 761 */     ArrayList<BlockPos> arrayList = new ArrayList<>(16);
/* 762 */     arrayList.addAll(this.modeUtil.getBlockPositions(Mode.Normal));
/* 763 */     BlockPos blockPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/* 764 */     List<BlockPosWithFacing> list = getNeighbours(blockPos.func_177984_a());
/* 765 */     for (BlockPosWithFacing pos : list) {
/* 766 */       if (getBlock(pos.getPosition().func_177984_a()) == Blocks.field_150350_a) {
/* 767 */         arrayList.add(pos.getPosition());
/*     */         continue;
/*     */       } 
/* 770 */       if (getBlock(pos.getPosition().func_177972_a(pos.getFacing())) != Blocks.field_150350_a)
/* 771 */         continue;  arrayList.add(pos.getPosition());
/*     */     } 
/* 773 */     return arrayList;
/*     */   }
/*     */   
/*     */   private List<BlockPosWithFacing> getNeighbours(BlockPos blockPos) {
/* 777 */     ArrayList<BlockPosWithFacing> arrayList = new ArrayList<>(16);
/* 778 */     arrayList.add(new BlockPosWithFacing(blockPos.func_177978_c(), EnumFacing.NORTH));
/* 779 */     arrayList.add(new BlockPosWithFacing(blockPos.func_177974_f(), EnumFacing.EAST));
/* 780 */     arrayList.add(new BlockPosWithFacing(blockPos.func_177968_d(), EnumFacing.SOUTH));
/* 781 */     arrayList.add(new BlockPosWithFacing(blockPos.func_177976_e(), EnumFacing.WEST));
/* 782 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static List<EnumFacing> getFacings(BlockPos blockPos) {
/* 786 */     ArrayList<EnumFacing> arrayList = new ArrayList<>();
/* 787 */     if (mc.field_71441_e == null) return arrayList; 
/* 788 */     if (blockPos == null) {
/* 789 */       return arrayList;
/*     */     }
/* 791 */     EnumFacing[] enumFacingArray = EnumFacing.values();
/* 792 */     int n = enumFacingArray.length;
/* 793 */     int n2 = 0;
/* 794 */     while (n2 < n) {
/* 795 */       EnumFacing enumFacing = enumFacingArray[n2];
/* 796 */       BlockPos blockPos2 = blockPos.func_177972_a(enumFacing);
/* 797 */       IBlockState iBlockState = mc.field_71441_e.func_180495_p(blockPos2);
/* 798 */       if (iBlockState != null && iBlockState.func_177230_c().func_176209_a(iBlockState, false) && !iBlockState.func_185904_a().func_76222_j()) {
/* 799 */         arrayList.add(enumFacing);
/*     */       }
/* 801 */       n2++;
/*     */     } 
/* 803 */     return arrayList;
/*     */   }
/*     */   
/*     */   private void addBlockToList(Vec3d vec3d, List<BlockPos> list) {
/* 807 */     BlockPos blockPos = new BlockPos(vec3d);
/* 808 */     if (!checkBlockPos(blockPos))
/* 809 */       return;  if (list.contains(blockPos))
/* 810 */       return;  list.add(blockPos);
/*     */   }
/*     */   
/*     */   private boolean checkBlockPos(BlockPos blockPos) {
/* 814 */     return (blockPos != null && mc.field_71441_e != null && ((IBlockState)this.blockState.apply(blockPos)).func_185904_a().func_76222_j());
/*     */   }
/*     */   
/*     */   private Block getBlock(BlockPos blockPos) {
/* 818 */     return ((IBlockState)this.blockState.apply(blockPos)).func_177230_c();
/*     */   }
/*     */   
/*     */   private boolean doesCrystalWantToFuckUs(Vec3d vec3d, List<BlockPos> list) {
/* 822 */     Iterator<BlockPos> iterator = list.iterator();
/*     */     while (true) {
/* 824 */       if (!iterator.hasNext()) return false; 
/* 825 */       if ((new AxisAlignedBB(iterator.next())).func_72318_a(vec3d))
/* 826 */         return true; 
/*     */     } 
/*     */   }
/*     */   private static double getDistanceToBlock(BlockPos blockPos) {
/* 830 */     return mc.field_71439_g.func_70011_f(blockPos.func_177958_n() + 0.5D, blockPos.func_177956_o(), blockPos.func_177952_p() + 0.5D);
/*     */   }
/*     */   
/*     */   private static IBlockState getBlockState(Object blockPos) {
/* 834 */     return mc.field_71441_e.func_180495_p((BlockPos)blockPos);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\Surround.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */