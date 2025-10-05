/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.server.SPacketExplosion;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Burrow extends Module {
/*  40 */   public static final Set<Block> BAD_BLOCKS = Sets.newHashSet((Object[])new Block[] { Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, (Block)Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final Set<Block> SHULKERS = Sets.newHashSet((Object[])new Block[] { Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   protected final Timer scaleTimer = new Timer();
/*  58 */   protected final Timer timer = new Timer();
/*  59 */   public Setting<Float> vClip = register(new Setting("V-Clip", Float.valueOf(-9.0F), Float.valueOf(-256.0F), Float.valueOf(256.0F)));
/*  60 */   public Setting<Float> minDown = register(new Setting("Min-Down", Float.valueOf(3.0F), Float.valueOf(0.0F), Float.valueOf(1337.0F)));
/*  61 */   public Setting<Float> maxDown = register(new Setting("Max-Down", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(1337.0F)));
/*  62 */   public Setting<Float> minUp = register(new Setting("Min-Up", Float.valueOf(3.0F), Float.valueOf(0.0F), Float.valueOf(1337.0F)));
/*  63 */   public Setting<Float> maxUp = register(new Setting("Max-Up", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(1337.0F)));
/*  64 */   public Setting<Float> scaleFactor = register(new Setting("Scale-Factor", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(10.0F)));
/*  65 */   public Setting<Integer> scaleDelay = register(new Setting("Scale-Delay", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(1000)));
/*  66 */   public Setting<Integer> cooldown = register(new Setting("Cooldown", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500)));
/*  67 */   public Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(1000)));
/*  68 */   public Setting<Boolean> scaleDown = register(new Setting("Scale-Down", Boolean.valueOf(false)));
/*  69 */   public Setting<Boolean> scaleVelocity = register(new Setting("Scale-Velocity", Boolean.valueOf(false)));
/*  70 */   public Setting<Boolean> scaleExplosion = register(new Setting("Scale-Explosion", Boolean.valueOf(false)));
/*  71 */   public Setting<Boolean> attackBefore = register(new Setting("Attack-Before", Boolean.valueOf(false)));
/*  72 */   public Setting<Boolean> antiWeakness = register(new Setting("antiWeakness", Boolean.valueOf(false)));
/*  73 */   public Setting<Boolean> attack = register(new Setting("Attack", Boolean.valueOf(false)));
/*  74 */   public Setting<Boolean> deltaY = register(new Setting("Delta-Y", Boolean.valueOf(true)));
/*  75 */   public Setting<Boolean> placeDisable = register(new Setting("PlaceDisable", Boolean.valueOf(false)));
/*  76 */   public Setting<Boolean> wait = register(new Setting("Wait", Boolean.valueOf(true)));
/*  77 */   public Setting<Boolean> highBlock = register(new Setting("HighBlock", Boolean.valueOf(false)));
/*  78 */   public Setting<Boolean> evade = register(new Setting("Evade", Boolean.valueOf(false)));
/*  79 */   public Setting<Boolean> noVoid = register(new Setting("NoVoid", Boolean.valueOf(false)));
/*  80 */   public Setting<Boolean> conflict = register(new Setting("Conflict", Boolean.valueOf(true)));
/*  81 */   public Setting<Boolean> onGround = register(new Setting("OnGround", Boolean.valueOf(true)));
/*  82 */   public Setting<Boolean> allowUp = register(new Setting("Allow-Up", Boolean.valueOf(false)));
/*  83 */   public Setting<Boolean> beacon = register(new Setting("Beacon", Boolean.valueOf(false)));
/*  84 */   public Setting<Boolean> echest = register(new Setting("E-Chest", Boolean.valueOf(false)));
/*  85 */   public Setting<Boolean> anvil = register(new Setting("Anvil", Boolean.valueOf(false)));
/*  86 */   public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
/*  87 */   public Setting<Boolean> discrete = register(new Setting("Discrete", Boolean.valueOf(true)));
/*  88 */   public Setting<Boolean> air = register(new Setting("Air", Boolean.valueOf(false)));
/*  89 */   public Setting<Boolean> fallback = register(new Setting("Fallback", Boolean.valueOf(true)));
/*  90 */   public Setting<Boolean> skipZero = register(new Setting("SkipZero", Boolean.valueOf(true)));
/*     */   protected double motionY;
/*     */   protected BlockPos startPos;
/*     */   private volatile double last_x;
/*     */   private volatile double last_y;
/*     */   private volatile double last_z;
/*  96 */   private final Setting<OffsetMode> offsetMode = register(new Setting("Mode", OffsetMode.Smart));
/*     */   public Burrow() {
/*  98 */     super("Burrow", "Ставит в тебя блок", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   public static void send(Packet<?> packet) {
/* 102 */     NetHandlerPlayClient connection = mc.func_147114_u();
/* 103 */     if (connection != null) {
/* 104 */       connection.func_147297_a(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void swingPacket(EnumHand hand) {
/* 109 */     ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(mc
/* 110 */         .func_147114_u())).func_147297_a((Packet)new CPacketAnimation(hand));
/*     */   }
/*     */   
/*     */   public static void switchToHotbarSlot(int slot, boolean silent) {
/* 114 */     if (mc.field_71439_g.field_71071_by.field_70461_c == slot || slot < 0) {
/*     */       return;
/*     */     }
/* 117 */     if (silent) {
/* 118 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 119 */       mc.field_71442_b.func_78765_e();
/*     */     } else {
/* 121 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 122 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/* 123 */       mc.field_71442_b.func_78765_e();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void swing(int slot) {
/* 128 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(
/* 129 */           getHand(slot)));
/*     */   }
/*     */   
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing, Entity from) {
/* 133 */     return getRotations(pos, facing, from, (IBlockAccess)mc.field_71441_e, mc.field_71441_e.func_180495_p(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(BlockPos pos, EnumFacing facing, Entity from, IBlockAccess world, IBlockState state) {
/* 141 */     AxisAlignedBB bb = state.func_185900_c(world, pos);
/*     */     
/* 143 */     double x = pos.func_177958_n() + (bb.field_72340_a + bb.field_72336_d) / 2.0D;
/* 144 */     double y = pos.func_177956_o() + (bb.field_72338_b + bb.field_72337_e) / 2.0D;
/* 145 */     double z = pos.func_177952_p() + (bb.field_72339_c + bb.field_72334_f) / 2.0D;
/*     */     
/* 147 */     if (facing != null) {
/* 148 */       x += facing.func_176730_m().func_177958_n() * (bb.field_72340_a + bb.field_72336_d) / 2.0D;
/* 149 */       y += facing.func_176730_m().func_177956_o() * (bb.field_72338_b + bb.field_72337_e) / 2.0D;
/* 150 */       z += facing.func_176730_m().func_177952_p() * (bb.field_72339_c + bb.field_72334_f) / 2.0D;
/*     */     } 
/*     */     
/* 153 */     return getRotations(x, y, z, from);
/*     */   }
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z, Entity f) {
/* 157 */     return getRotations(x, y, z, f.field_70165_t, f.field_70163_u, f.field_70161_v, f.func_70047_e());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotations(double x, double y, double z, double fromX, double fromY, double fromZ, float fromHeight) {
/* 167 */     double xDiff = x - fromX;
/* 168 */     double yDiff = y - fromY + fromHeight;
/* 169 */     double zDiff = z - fromZ;
/* 170 */     double dist = MathHelper.func_76133_a(xDiff * xDiff + zDiff * zDiff);
/*     */     
/* 172 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/* 173 */     float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/* 174 */     float prevYaw = mc.field_71439_g.field_70177_z;
/* 175 */     float diff = yaw - prevYaw;
/*     */     
/* 177 */     if (diff < -180.0F || diff > 180.0F) {
/* 178 */       float round = Math.round(Math.abs(diff / 360.0F));
/* 179 */       diff = (diff < 0.0F) ? (diff + 360.0F * round) : (diff - 360.0F * round);
/*     */     } 
/*     */     
/* 182 */     return new float[] { prevYaw + diff, pitch };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void doRotation(float yaw, float pitch, boolean onGround) {
/* 188 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)rotation(yaw, pitch, onGround));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static CPacketPlayer rotation(float yaw, float pitch, boolean onGround) {
/* 194 */     return (CPacketPlayer)new CPacketPlayer.Rotation(yaw, pitch, onGround);
/*     */   }
/*     */   
/*     */   public static void doY(Entity entity, double y, boolean onGround) {
/* 198 */     doPosition(entity.field_70165_t, y, entity.field_70161_v, onGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void doPosition(double x, double y, double z, boolean onGround) {
/* 205 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)position(x, y, z, onGround));
/*     */   }
/*     */   
/*     */   public static CPacketPlayer position(double x, double y, double z) {
/* 209 */     return position(x, y, z, mc.field_71439_g.field_70122_E);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CPacketPlayer position(double x, double y, double z, boolean onGround) {
/* 216 */     return (CPacketPlayer)new CPacketPlayer.Position(x, y, z, onGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void doPosRot(double x, double y, double z, float yaw, float pitch, boolean onGround) {
/* 225 */     mc.field_71439_g.field_71174_a.func_147297_a(
/* 226 */         (Packet)positionRotation(x, y, z, yaw, pitch, onGround));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CPacketPlayer positionRotation(double x, double y, double z, float yaw, float pitch, boolean onGround) {
/* 235 */     return (CPacketPlayer)new CPacketPlayer.PositionRotation(x, y, z, yaw, pitch, onGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void place(BlockPos on, EnumFacing facing, int slot, float x, float y, float z) {
/*     */     try {
/* 245 */       place(on, facing, getHand(slot), x, y, z);
/* 246 */     } catch (Exception e) {
/* 247 */       Command.sendMessage("Failed to place the block");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static EnumHand getHand(int slot) {
/* 252 */     return (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void place(BlockPos on, EnumFacing facing, EnumHand hand, float x, float y, float z) {
/*     */     try {
/* 262 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(on, facing, hand, x, y, z));
/*     */     }
/* 264 */     catch (Exception exception) {
/* 265 */       Command.sendMessage("Failed to place the block");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static BlockPos getPosition(Entity entity) {
/* 270 */     return getPosition(entity, 0.0D);
/*     */   }
/*     */   
/*     */   public static BlockPos getPosition(Entity entity, double yOffset) {
/* 274 */     double y = entity.field_70163_u + yOffset;
/* 275 */     if (entity.field_70163_u - Math.floor(entity.field_70163_u) > 0.5D) {
/* 276 */       y = Math.ceil(entity.field_70163_u);
/*     */     }
/*     */     
/* 279 */     return new BlockPos(entity.field_70165_t, y, entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public static boolean canBreakWeakness(boolean checkStack) {
/* 283 */     if (!mc.field_71439_g.func_70644_a(MobEffects.field_76437_t)) {
/* 284 */       return true;
/*     */     }
/*     */     
/* 287 */     int strengthAmp = 0;
/*     */     
/* 289 */     PotionEffect effect = mc.field_71439_g.func_70660_b(MobEffects.field_76420_g);
/*     */     
/* 291 */     if (effect != null) {
/* 292 */       strengthAmp = effect.func_76458_c();
/*     */     }
/*     */     
/* 295 */     if (strengthAmp >= 1) {
/* 296 */       return true;
/*     */     }
/*     */     
/* 299 */     return (checkStack && canBreakWeakness(mc.field_71439_g.func_184614_ca()));
/*     */   }
/*     */   
/*     */   public static boolean canBreakWeakness(ItemStack stack) {
/* 303 */     return stack.func_77973_b() instanceof net.minecraft.item.ItemSword;
/*     */   }
/*     */   
/*     */   public static boolean shouldSneak(BlockPos pos, boolean manager) {
/* 307 */     return shouldSneak(pos, (IBlockAccess)mc.field_71441_e, manager);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean shouldSneak(BlockPos pos, IBlockAccess provider, boolean manager) {
/* 313 */     return shouldSneak(provider.func_180495_p(pos).func_177230_c(), manager);
/*     */   }
/*     */   
/*     */   public static boolean shouldSneak(Block block, boolean manager) {
/* 317 */     if (manager && mc.field_71439_g.func_70093_af()) {
/* 318 */       return false;
/*     */     }
/*     */     
/* 321 */     return (BAD_BLOCKS.contains(block) || SHULKERS.contains(block));
/*     */   }
/*     */   
/*     */   public static float[] hitVecToPlaceVec(BlockPos pos, Vec3d hitVec) {
/* 325 */     float x = (float)(hitVec.field_72450_a - pos.func_177958_n());
/* 326 */     float y = (float)(hitVec.field_72448_b - pos.func_177956_o());
/* 327 */     float z = (float)(hitVec.field_72449_c - pos.func_177952_p());
/*     */     
/* 329 */     return new float[] { x, y, z };
/*     */   }
/*     */   
/*     */   public static RayTraceResult getRayTraceResultWithEntity(float yaw, float pitch, Entity from) {
/* 333 */     return getRayTraceResult(yaw, pitch, mc.field_71442_b.func_78757_d(), from);
/*     */   }
/*     */   
/*     */   public static RayTraceResult getRayTraceResult(float yaw, float pitch, float d, Entity from) {
/* 337 */     Vec3d vec3d = getEyePos(from);
/* 338 */     Vec3d lookVec = getVec3d(yaw, pitch);
/* 339 */     Vec3d rotations = vec3d.func_72441_c(lookVec.field_72450_a * d, lookVec.field_72448_b * d, lookVec.field_72449_c * d);
/* 340 */     return Optional.<RayTraceResult>ofNullable(mc.field_71441_e
/* 341 */         .func_147447_a(vec3d, rotations, false, false, false))
/* 342 */       .orElseGet(() -> new RayTraceResult(RayTraceResult.Type.MISS, new Vec3d(0.5D, 1.0D, 0.5D), EnumFacing.UP, BlockPos.field_177992_a));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d getVec3d(float yaw, float pitch) {
/* 348 */     float vx = -MathHelper.func_76126_a(rad(yaw)) * MathHelper.func_76134_b(rad(pitch));
/* 349 */     float vz = MathHelper.func_76134_b(rad(yaw)) * MathHelper.func_76134_b(rad(pitch));
/* 350 */     float vy = -MathHelper.func_76126_a(rad(pitch));
/* 351 */     return new Vec3d(vx, vy, vz);
/*     */   }
/*     */   
/*     */   public static float rad(float angle) {
/* 355 */     return (float)(angle * Math.PI / 180.0D);
/*     */   }
/*     */   
/*     */   public static Vec3d getEyePos(Entity entity) {
/* 359 */     return new Vec3d(entity.field_70165_t, getEyeHeight(entity), entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public static double getEyeHeight() {
/* 363 */     return getEyeHeight((Entity)mc.field_71439_g);
/*     */   }
/*     */   
/*     */   public static double getEyeHeight(Entity entity) {
/* 367 */     return entity.field_70163_u + entity.func_70047_e();
/*     */   }
/*     */   
/*     */   public static int findAntiWeakness() {
/* 371 */     int slot = -1;
/* 372 */     for (int i = 8; i > -1; i--) {
/* 373 */       if (canBreakWeakness(mc.field_71439_g.field_71071_by
/* 374 */           .func_70301_a(i))) {
/* 375 */         slot = i;
/* 376 */         if (mc.field_71439_g.field_71071_by.field_70461_c == i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     return slot;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 387 */     this.timer.reset();
/* 388 */     if (mc.field_71441_e == null || mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/* 391 */     this.startPos = getPlayerPos();
/*     */   }
/*     */   
/*     */   protected void attack(Packet<?> attacking, int slot) {
/* 395 */     if (slot != -1) {
/* 396 */       switchToHotbarSlot(slot, true);
/*     */     }
/*     */     
/* 399 */     send(attacking);
/* 400 */     swing(EnumHand.MAIN_HAND);
/*     */   }
/*     */   
/*     */   public void swing(EnumHand hand) {
/* 404 */     swingPacket(hand);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 409 */     if (event.getPacket() instanceof SPacketExplosion && (
/* 410 */       (Boolean)this.scaleExplosion.getValue()).booleanValue()) {
/* 411 */       this.motionY = ((SPacketExplosion)event.getPacket()).func_149144_d();
/* 412 */       this.scaleTimer.reset();
/*     */     } 
/*     */     
/* 415 */     if (event.getPacket() instanceof SPacketExplosion) {
/* 416 */       if (((Boolean)this.scaleVelocity.getValue()).booleanValue()) {
/*     */         return;
/*     */       }
/*     */       
/* 420 */       EntityPlayerSP playerSP = mc.field_71439_g;
/* 421 */       if (playerSP != null) {
/* 422 */         this.motionY = ((SPacketExplosion)event.getPacket()).func_149144_d() / 8000.0D;
/* 423 */         this.scaleTimer.reset();
/*     */       } 
/*     */     } 
/* 426 */     if (event.getPacket() instanceof SPacketPlayerPosLook) {
/* 427 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
/* 428 */       double x = packet.func_148932_c();
/* 429 */       double y = packet.func_148928_d();
/* 430 */       double z = packet.func_148933_e();
/*     */       
/* 432 */       if (packet.func_179834_f()
/* 433 */         .contains(SPacketPlayerPosLook.EnumFlags.X)) {
/* 434 */         x += mc.field_71439_g.field_70165_t;
/*     */       }
/*     */       
/* 437 */       if (packet.func_179834_f()
/* 438 */         .contains(SPacketPlayerPosLook.EnumFlags.Y)) {
/* 439 */         y += mc.field_71439_g.field_70163_u;
/*     */       }
/*     */       
/* 442 */       if (packet.func_179834_f()
/* 443 */         .contains(SPacketPlayerPosLook.EnumFlags.Z)) {
/* 444 */         z += mc.field_71439_g.field_70161_v;
/*     */       }
/*     */       
/* 447 */       this.last_x = MathHelper.func_151237_a(x, -3.0E7D, 3.0E7D);
/* 448 */       this.last_y = y;
/* 449 */       this.last_z = MathHelper.func_151237_a(z, -3.0E7D, 3.0E7D);
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
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 464 */     if (((Boolean)this.wait.getValue()).booleanValue()) {
/* 465 */       BlockPos currentPos = getPlayerPos();
/* 466 */       if (!currentPos.equals(this.startPos)) {
/* 467 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 472 */     if (isInsideBlock()) {
/*     */       return;
/*     */     }
/*     */     
/* 476 */     EntityPlayerSP entityPlayerSP1 = mc.field_71439_g;
/*     */     
/* 478 */     BlockPos pos = getPosition((Entity)entityPlayerSP1);
/* 479 */     if (!mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
/* 480 */       if (!((Boolean)this.wait.getValue()).booleanValue()) {
/* 481 */         disable();
/*     */       }
/*     */       return;
/*     */     } 
/* 485 */     BlockPos posHead = getPosition((Entity)entityPlayerSP1).func_177984_a().func_177984_a();
/* 486 */     if (!mc.field_71441_e.func_180495_p(posHead).func_185904_a().func_76222_j() && ((Boolean)this.wait.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 490 */     CPacketUseEntity attacking = null;
/* 491 */     boolean crystals = false;
/* 492 */     float currentDmg = Float.MAX_VALUE;
/* 493 */     for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(pos))) {
/* 494 */       if (entity != null && !mc.field_71439_g.equals(entity) && entity.field_70156_m) {
/* 495 */         if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)this.attack.getValue()).booleanValue()) {
/* 496 */           EntityUtil.attackEntity(entity, true, true);
/* 497 */           crystals = true;
/*     */           continue;
/*     */         } 
/* 500 */         if (!((Boolean)this.wait.getValue()).booleanValue()) {
/* 501 */           disable();
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/* 506 */     int weaknessSlot = -1;
/* 507 */     if (crystals) {
/* 508 */       if (attacking == null) {
/* 509 */         if (!((Boolean)this.wait.getValue()).booleanValue()) {
/* 510 */           disable();
/*     */         }
/*     */         return;
/*     */       } 
/* 514 */       if (!canBreakWeakness(true) && (
/* 515 */         !((Boolean)this.antiWeakness.getValue()).booleanValue() || ((Integer)this.cooldown
/* 516 */         .getValue()).intValue() != 0 || (
/* 517 */         weaknessSlot = findAntiWeakness()) == -1)) {
/* 518 */         if (!((Boolean)this.wait.getValue()).booleanValue()) {
/* 519 */           disable();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 525 */     if (!((Boolean)this.allowUp.getValue()).booleanValue()) {
/* 526 */       BlockPos upUp = pos.func_177981_b(2);
/* 527 */       IBlockState upState = mc.field_71441_e.func_180495_p(upUp);
/* 528 */       if (upState.func_185904_a().func_76230_c()) {
/*     */         
/* 530 */         if (!((Boolean)this.wait.getValue()).booleanValue()) {
/* 531 */           disable();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 544 */     int slot = ((Boolean)this.anvil.getValue()).booleanValue() ? InventoryUtil.findHotbarBlock(Blocks.field_150467_bQ) : (((Boolean)this.beacon.getValue()).booleanValue() ? InventoryUtil.findHotbarBlock((Block)Blocks.field_150461_bJ) : ((((Boolean)this.echest.getValue()).booleanValue() || mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150477_bB) ? InventoryUtil.findHotbarBlock(Blocks.field_150477_bB) : InventoryUtil.findHotbarBlock(BlockObsidian.class)));
/* 545 */     if (slot == -1) {
/* 546 */       Command.sendMessage("No Block found!");
/*     */       
/*     */       return;
/*     */     } 
/* 550 */     EnumFacing f = BlockUtils.getFacing(pos);
/* 551 */     if (f == null) {
/* 552 */       if (!((Boolean)this.wait.getValue()).booleanValue()) {
/* 553 */         disable();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 559 */     double y = applyScale(getY((Entity)entityPlayerSP1, (OffsetMode)this.offsetMode.getValue()));
/* 560 */     if (Double.isNaN(y)) {
/*     */       return;
/*     */     }
/*     */     
/* 564 */     BlockPos on = pos.func_177972_a(f);
/*     */     
/* 566 */     float[] r = getRotations(on, f.func_176734_d(), (Entity)entityPlayerSP1);
/*     */     
/* 568 */     RayTraceResult result = getRayTraceResultWithEntity(r[0], r[1], (Entity)entityPlayerSP1);
/*     */     
/* 570 */     float[] vec = hitVecToPlaceVec(on, result.field_72307_f);
/* 571 */     boolean sneaking = !shouldSneak(on, true);
/*     */     
/* 573 */     EntityPlayerSP entityPlayerSP2 = entityPlayerSP1;
/* 574 */     int finalWeaknessSlot = weaknessSlot;
/* 575 */     CPacketUseEntity finalAttacking = attacking;
/* 576 */     if (singlePlayerCheck(pos)) {
/* 577 */       if (!((Boolean)this.wait.getValue()).booleanValue() || ((Boolean)this.placeDisable.getValue()).booleanValue()) {
/* 578 */         disable();
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 583 */     int lastSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 584 */     if (((Boolean)this.attackBefore.getValue()).booleanValue() && finalAttacking != null) {
/* 585 */       attack((Packet<?>)finalAttacking, finalWeaknessSlot);
/*     */     }
/*     */     
/* 588 */     if (((Boolean)this.conflict.getValue()).booleanValue() || ((Boolean)this.rotate.getValue()).booleanValue()) {
/* 589 */       if (((Boolean)this.rotate.getValue()).booleanValue()) {
/* 590 */         if (entityPlayerSP2.func_174791_d().equals(getVec())) {
/* 591 */           doRotation(r[0], r[1], true);
/*     */         } else {
/* 593 */           doPosRot(((EntityPlayer)entityPlayerSP2).field_70165_t, ((EntityPlayer)entityPlayerSP2).field_70163_u, ((EntityPlayer)entityPlayerSP2).field_70161_v, r[0], r[1], true);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 601 */         doPosition(((EntityPlayer)entityPlayerSP2).field_70165_t, ((EntityPlayer)entityPlayerSP2).field_70163_u, ((EntityPlayer)entityPlayerSP2).field_70161_v, true);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 608 */     doY((Entity)entityPlayerSP2, ((EntityPlayer)entityPlayerSP2).field_70163_u + 0.42D, ((Boolean)this.onGround
/* 609 */         .getValue()).booleanValue());
/* 610 */     doY((Entity)entityPlayerSP2, ((EntityPlayer)entityPlayerSP2).field_70163_u + 0.75D, ((Boolean)this.onGround
/* 611 */         .getValue()).booleanValue());
/* 612 */     doY((Entity)entityPlayerSP2, ((EntityPlayer)entityPlayerSP2).field_70163_u + 1.01D, ((Boolean)this.onGround
/* 613 */         .getValue()).booleanValue());
/* 614 */     doY((Entity)entityPlayerSP2, ((EntityPlayer)entityPlayerSP2).field_70163_u + 1.16D, ((Boolean)this.onGround
/* 615 */         .getValue()).booleanValue());
/*     */     
/* 617 */     InventoryUtil.switchToHotbarSlot(slot, false);
/*     */     
/* 619 */     if (!sneaking) {
/* 620 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 625 */     place(on, f.func_176734_d(), slot, vec[0], vec[1], vec[2]);
/*     */     
/* 627 */     if (((Boolean)this.highBlock.getValue()).booleanValue()) {
/* 628 */       doY((Entity)entityPlayerSP2, ((EntityPlayer)entityPlayerSP2).field_70163_u + 1.67D, ((Boolean)this.onGround
/* 629 */           .getValue()).booleanValue());
/* 630 */       doY((Entity)entityPlayerSP2, ((EntityPlayer)entityPlayerSP2).field_70163_u + 2.01D, ((Boolean)this.onGround
/* 631 */           .getValue()).booleanValue());
/* 632 */       doY((Entity)entityPlayerSP2, ((EntityPlayer)entityPlayerSP2).field_70163_u + 2.42D, ((Boolean)this.onGround
/* 633 */           .getValue()).booleanValue());
/* 634 */       BlockPos highPos = pos.func_177984_a();
/* 635 */       EnumFacing face = EnumFacing.DOWN;
/* 636 */       place(highPos.func_177972_a(face), face.func_176734_d(), slot, vec[0], vec[1], vec[2]);
/*     */     } 
/*     */     
/* 639 */     swing(slot);
/*     */     
/* 641 */     InventoryUtil.switchToHotbarSlot(lastSlot, false);
/*     */ 
/*     */     
/* 644 */     if (!sneaking) {
/* 645 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 650 */     doY((Entity)entityPlayerSP1, y, false);
/* 651 */     this.timer.reset();
/* 652 */     if (!((Boolean)this.wait.getValue()).booleanValue() || ((Boolean)this.placeDisable.getValue()).booleanValue())
/* 653 */       disable(); 
/*     */   }
/*     */   
/*     */   public Vec3d getVec() {
/* 657 */     return new Vec3d(this.last_x, this.last_y, this.last_z);
/*     */   }
/*     */   
/*     */   protected double getY(Entity entity, OffsetMode mode) {
/* 661 */     if (mode == OffsetMode.Constant) {
/* 662 */       double y = entity.field_70163_u + ((Float)this.vClip.getValue()).floatValue();
/* 663 */       if (((Boolean)this.evade.getValue()).booleanValue() && Math.abs(y) < 1.0D) {
/* 664 */         y = -1.0D;
/*     */       }
/*     */       
/* 667 */       return y;
/*     */     } 
/*     */     
/* 670 */     double d = getY(entity, ((Float)this.minDown.getValue()).floatValue(), ((Float)this.maxDown.getValue()).floatValue(), true);
/* 671 */     if (Double.isNaN(d)) {
/* 672 */       d = getY(entity, -((Float)this.minUp.getValue()).floatValue(), -((Float)this.maxUp.getValue()).floatValue(), false);
/* 673 */       if (Double.isNaN(d) && (
/* 674 */         (Boolean)this.fallback.getValue()).booleanValue()) {
/* 675 */         return getY(entity, OffsetMode.Constant);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 680 */     return d;
/*     */   }
/*     */   
/*     */   protected double getY(Entity entity, double min, double max, boolean add) {
/* 684 */     if ((min > max && add) || (max > min && !add)) {
/* 685 */       return Double.NaN;
/*     */     }
/*     */     
/* 688 */     double x = entity.field_70165_t;
/* 689 */     double y = entity.field_70163_u;
/* 690 */     double z = entity.field_70161_v;
/*     */     
/* 692 */     boolean air = false;
/* 693 */     double lastOff = 0.0D;
/* 694 */     BlockPos last = null;
/* 695 */     double off = min;
/* 696 */     for (; add ? (off < max) : (off > max); 
/* 697 */       off = add ? ++off : --off) {
/* 698 */       BlockPos pos = new BlockPos(x, y - off, z);
/* 699 */       if (!((Boolean)this.noVoid.getValue()).booleanValue() || pos.func_177956_o() >= 0)
/*     */       {
/*     */ 
/*     */         
/* 703 */         if (((Boolean)this.skipZero.getValue()).booleanValue() && Math.abs(y) < 1.0D) {
/* 704 */           air = false;
/* 705 */           last = pos;
/* 706 */           lastOff = y - off;
/*     */         }
/*     */         else {
/*     */           
/* 710 */           IBlockState state = mc.field_71441_e.func_180495_p(pos);
/* 711 */           if ((!((Boolean)this.air.getValue()).booleanValue() && !state.func_185904_a().func_76230_c()) || state
/* 712 */             .func_177230_c() == Blocks.field_150350_a) {
/* 713 */             if (air) {
/* 714 */               if (add) {
/* 715 */                 return ((Boolean)this.discrete.getValue()).booleanValue() ? pos.func_177956_o() : (y - off);
/*     */               }
/* 717 */               return ((Boolean)this.discrete.getValue()).booleanValue() ? last.func_177956_o() : lastOff;
/*     */             } 
/*     */ 
/*     */             
/* 721 */             air = true;
/*     */           } else {
/* 723 */             air = false;
/*     */           } 
/*     */           
/* 726 */           last = pos;
/* 727 */           lastOff = y - off;
/*     */         }  } 
/*     */     } 
/* 730 */     return Double.NaN;
/*     */   }
/*     */   
/*     */   protected double applyScale(double value) {
/* 734 */     if ((value < mc.field_71439_g.field_70163_u && !((Boolean)this.scaleDown.getValue()).booleanValue()) || (
/* 735 */       !((Boolean)this.scaleExplosion.getValue()).booleanValue() && !((Boolean)this.scaleVelocity.getValue()).booleanValue()) || this.scaleTimer
/* 736 */       .passedMs(((Integer)this.scaleDelay.getValue()).intValue()) || this.motionY == 0.0D)
/*     */     {
/* 738 */       return value;
/*     */     }
/*     */     
/* 741 */     if (value < mc.field_71439_g.field_70163_u) {
/* 742 */       value -= this.motionY * ((Float)this.scaleFactor.getValue()).floatValue();
/*     */     } else {
/* 744 */       value += this.motionY * ((Float)this.scaleFactor.getValue()).floatValue();
/*     */     } 
/*     */ 
/*     */     
/* 748 */     return ((Boolean)this.discrete.getValue()).booleanValue() ? Math.floor(value) : value;
/*     */   }
/*     */   
/*     */   protected BlockPos getPlayerPos() {
/* 752 */     return (((Boolean)this.deltaY.getValue()).booleanValue() && Math.abs(mc.field_71439_g.field_70181_x) > 0.1D) ? new BlockPos((Entity)mc.field_71439_g) : 
/*     */       
/* 754 */       getPosition((Entity)mc.field_71439_g);
/*     */   }
/*     */   
/*     */   protected boolean isInsideBlock() {
/* 758 */     double x = mc.field_71439_g.field_70165_t;
/* 759 */     double y = mc.field_71439_g.field_70163_u + 0.2D;
/* 760 */     double z = mc.field_71439_g.field_70161_v;
/*     */     
/* 762 */     return (mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_185904_a().func_76230_c() || !mc.field_71439_g.field_70124_G);
/*     */   }
/*     */   
/*     */   protected boolean singlePlayerCheck(BlockPos pos) {
/* 766 */     if (mc.func_71356_B()) {
/*     */ 
/*     */ 
/*     */       
/* 770 */       EntityPlayerMP entityPlayerMP = mc.func_71401_C().func_184103_al().func_177451_a(mc.field_71439_g.func_110124_au());
/*     */       
/* 772 */       if (entityPlayerMP == null) {
/* 773 */         disable();
/* 774 */         return true;
/*     */       } 
/*     */       
/* 777 */       entityPlayerMP.func_130014_f_().func_175656_a(pos, ((Boolean)this.echest.getValue()).booleanValue() ? Blocks.field_150477_bB
/* 778 */           .func_176223_P() : Blocks.field_150343_Z
/* 779 */           .func_176223_P());
/*     */       
/* 781 */       mc.field_71441_e.func_175656_a(pos, ((Boolean)this.echest.getValue()).booleanValue() ? Blocks.field_150477_bB
/* 782 */           .func_176223_P() : Blocks.field_150343_Z
/* 783 */           .func_176223_P());
/*     */       
/* 785 */       return true;
/*     */     } 
/*     */     
/* 788 */     return false;
/*     */   }
/*     */   
/*     */   public enum OffsetMode {
/* 792 */     Constant,
/* 793 */     Smart;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\Burrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */