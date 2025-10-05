/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.CrystalUtils;
/*     */ import com.mrzak34.thunderhack.util.InteractionUtil;
/*     */ import com.mrzak34.thunderhack.util.SilentRotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.BlockRenderUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class PistonAura extends Module {
/*  44 */   private static PistonAura INSTANCE = new PistonAura();
/*  45 */   public final Setting<ColorSetting> arrowColor = register(new Setting("Arrow Color", new ColorSetting(-2009289807)));
/*  46 */   public final Setting<ColorSetting> outlineColorCurrent = register(new Setting("Outline Color", new ColorSetting(-1323314462)));
/*  47 */   public final Setting<ColorSetting> colorFull = register(new Setting("Full Color", new ColorSetting(-2011093215)));
/*  48 */   public final Setting<ColorSetting> outlineColorFull = register(new Setting("OutlineColorF", new ColorSetting(-2009289807)));
/*  49 */   public final Setting<ColorSetting> colorCurrent = register(new Setting("ColorCurrent", new ColorSetting(-1323314462)));
/*  50 */   public Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true)));
/*     */   public BlockPos facePos;
/*     */   public EnumFacing faceOffset;
/*     */   public BlockPos crystalPos;
/*     */   public BlockPos pistonNeighbour;
/*     */   public EnumFacing pistonOffset;
/*  56 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.DAMAGE));
/*  57 */   private final Setting<Boolean> smart = register(new Setting("Smart", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.PUSH)));
/*  58 */   private final Setting<Boolean> triggerable = register(new Setting("DisablePush", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.PUSH)));
/*  59 */   private final Setting<Boolean> disableWhenNone = register(new Setting("DisableNone", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.DAMAGE)));
/*  60 */   private final Setting<Integer> targetRange = register(new Setting("TargetRange", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(6)));
/*  61 */   private final Setting<Integer> breakDelay = register(new Setting("Delay", Integer.valueOf(25), Integer.valueOf(0), Integer.valueOf(100)));
/*  62 */   private final Setting<Integer> actionShift = register(new Setting("ActionShift", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(8)));
/*  63 */   private final Setting<Integer> actionInterval = register(new Setting("ActionInterval", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10)));
/*  64 */   private final Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  65 */   private final Setting<Boolean> raytrace = register(new Setting("RayTrace", Boolean.valueOf(false)));
/*  66 */   private final Setting<Boolean> antiSuicide = register(new Setting("AntiSuicide", Boolean.valueOf(false)));
/*  67 */   private final Setting<Boolean> mine = register(new Setting("Mine", Boolean.valueOf(false)));
/*  68 */   private final Setting<Boolean> protocol = register(new Setting("Protocol", Boolean.valueOf(false)));
/*  69 */   private final Setting<Boolean> ccrys = register(new Setting("WaitCrystal", Boolean.valueOf(true)));
/*  70 */   private final Setting<Boolean> renderCurrent = register(new Setting("Current", Boolean.valueOf(true), v -> ((Boolean)this.render.getValue()).booleanValue()));
/*  71 */   private final Setting<Boolean> renderFull = register(new Setting("Full", Boolean.valueOf(true), v -> ((Boolean)this.render.getValue()).booleanValue()));
/*  72 */   private final Setting<Boolean> arrow = register(new Setting("Arrow", Boolean.valueOf(true), v -> ((Boolean)this.render.getValue()).booleanValue()));
/*  73 */   private final Setting<Boolean> bottomArrow = register(new Setting("Bottom", Boolean.valueOf(true), v -> ((Boolean)this.render.getValue()).booleanValue()));
/*  74 */   private final Setting<Boolean> topArrow = register(new Setting("Top", Boolean.valueOf(true), v -> ((Boolean)this.render.getValue()).booleanValue()));
/*  75 */   private Stage stage = Stage.SEARCHING;
/*     */   private BlockPos torchPos;
/*  77 */   private final Timer torchTimer = new Timer();
/*     */   private boolean skipPiston;
/*  79 */   private final Timer delayTimer = new Timer();
/*     */   private int delayTime;
/*  81 */   private final Timer renderTimer = new Timer();
/*  82 */   private Runnable postAction = null;
/*  83 */   private int tickCounter = 0;
/*  84 */   private BlockPos placedPiston = null;
/*  85 */   private final Timer placedPistonTimer = new Timer();
/*  86 */   private final Timer actionTimer = new Timer();
/*     */   
/*     */   public PistonAura() {
/*  89 */     super("PistonAura", "Поршни вталкивают-кристал в чела-(Охуенная хуйня)", Module.Category.COMBAT);
/*  90 */     setInstance();
/*     */   }
/*     */   
/*     */   public static PistonAura getInstance() {
/*  94 */     if (INSTANCE == null) {
/*  95 */       INSTANCE = new PistonAura();
/*     */     }
/*  97 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static int getPistonSlot() {
/* 101 */     int slot = -1;
/* 102 */     for (int i = 0; i < 9; i++) {
/* 103 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 104 */       if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
/* 105 */         Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
/* 106 */         if (block instanceof net.minecraft.block.BlockPistonBase) {
/* 107 */           slot = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 113 */     return slot;
/*     */   }
/*     */   
/*     */   public static void invokeSync(float yaw, float pitch) {
/* 117 */     boolean flag = mc.field_71439_g.func_70051_ag();
/*     */     
/* 119 */     if (flag != ((IEntityPlayerSP)mc.field_71439_g).getServerSprintState()) {
/* 120 */       if (flag) {
/* 121 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */       } else {
/* 123 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */       } 
/*     */       
/* 126 */       ((IEntityPlayerSP)mc.field_71439_g).setServerSprintState(flag);
/*     */     } 
/*     */     
/* 129 */     boolean flag1 = mc.field_71439_g.func_70093_af();
/*     */     
/* 131 */     if (flag1 != ((IEntityPlayerSP)mc.field_71439_g).getServerSneakState()) {
/* 132 */       if (flag1) {
/* 133 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */       } else {
/* 135 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */       } 
/*     */       
/* 138 */       ((IEntityPlayerSP)mc.field_71439_g).setServerSneakState(flag1);
/*     */     } 
/*     */     
/* 141 */     if (mc.field_71439_g == mc.func_175606_aa()) {
/* 142 */       AxisAlignedBB axisalignedbb = mc.field_71439_g.func_174813_aQ();
/* 143 */       double dX = mc.field_71439_g.field_70165_t - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPosX();
/* 144 */       double dY = axisalignedbb.field_72338_b - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPosY();
/* 145 */       double dZ = mc.field_71439_g.field_70161_v - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPosZ();
/* 146 */       double dYaw = (yaw - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedYaw());
/* 147 */       double dPitch = (pitch - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPitch());
/* 148 */       ((IEntityPlayerSP)mc.field_71439_g).setPositionUpdateTicks(((IEntityPlayerSP)mc.field_71439_g).getPositionUpdateTicks() + 1);
/* 149 */       boolean positionChanged = (dX * dX + dY * dY + dZ * dZ > 9.0E-4D || ((IEntityPlayerSP)mc.field_71439_g).getPositionUpdateTicks() >= 20);
/* 150 */       boolean rotationChanged = (dYaw != 0.0D || dPitch != 0.0D);
/*     */       
/* 152 */       if (mc.field_71439_g.func_184218_aH()) {
/* 153 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(mc.field_71439_g.field_70159_w, -999.0D, mc.field_71439_g.field_70179_y, yaw, pitch, mc.field_71439_g.field_70122_E));
/* 154 */         positionChanged = false;
/* 155 */       } else if (positionChanged && rotationChanged) {
/* 156 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.PositionRotation(mc.field_71439_g.field_70165_t, axisalignedbb.field_72338_b, mc.field_71439_g.field_70161_v, yaw, pitch, mc.field_71439_g.field_70122_E));
/* 157 */       } else if (positionChanged) {
/* 158 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, axisalignedbb.field_72338_b, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
/* 159 */       } else if (rotationChanged) {
/* 160 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(yaw, pitch, mc.field_71439_g.field_70122_E));
/* 161 */       } else if (((IEntityPlayerSP)mc.field_71439_g).getPrevOnGround() != mc.field_71439_g.field_70122_E) {
/* 162 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer(mc.field_71439_g.field_70122_E));
/*     */       } 
/*     */       
/* 165 */       if (positionChanged) {
/* 166 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedPosX(mc.field_71439_g.field_70165_t);
/* 167 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedPosY(axisalignedbb.field_72338_b);
/* 168 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedPosZ(mc.field_71439_g.field_70161_v);
/* 169 */         ((IEntityPlayerSP)mc.field_71439_g).setPositionUpdateTicks(0);
/*     */       } 
/*     */       
/* 172 */       if (rotationChanged) {
/* 173 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedYaw(yaw);
/* 174 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedPitch(pitch);
/*     */       } 
/*     */       
/* 177 */       ((IEntityPlayerSP)mc.field_71439_g).setPrevOnGround(mc.field_71439_g.field_70122_E);
/* 178 */       ((IEntityPlayerSP)mc.field_71439_g).setAutoJumpEnabled(mc.field_71474_y.field_189989_R);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setInstance() {
/* 183 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public void onEnable() {
/* 187 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/* 188 */       return;  this.stage = Stage.SEARCHING;
/* 189 */     this.facePos = null;
/* 190 */     this.faceOffset = null;
/* 191 */     this.crystalPos = null;
/* 192 */     this.pistonNeighbour = null;
/* 193 */     this.pistonOffset = null;
/* 194 */     this.delayTime = 0;
/* 195 */     this.tickCounter = 0;
/* 196 */     this.postAction = null;
/* 197 */     this.torchPos = null;
/* 198 */     this.skipPiston = false;
/* 199 */     this.placedPiston = null;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onReceivePacket(PacketEvent.Receive event) {
/* 204 */     if (event.getPacket() instanceof SPacketBlockChange && this.torchPos != null && (
/* 205 */       (SPacketBlockChange)event.getPacket()).func_179827_b().equals(this.torchPos) && ((SPacketBlockChange)event.getPacket()).func_180728_a().func_177230_c() instanceof net.minecraft.block.BlockAir) {
/* 206 */       this.torchPos = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleAction(boolean extra) {
/* 212 */     if (this.actionTimer.passedMs(1000L) && ((Boolean)this.disableWhenNone.getValue()).booleanValue()) {
/* 213 */       toggle();
/*     */     }
/* 215 */     if (!this.delayTimer.passedMs(this.delayTime))
/* 216 */       return;  if (((Boolean)this.strict.getValue()).booleanValue() && Math.sqrt(mc.field_71439_g.field_70159_w * mc.field_71439_g.field_70159_w + mc.field_71439_g.field_70179_y * mc.field_71439_g.field_70179_y) > 9.0E-4D)
/*     */       return; 
/* 218 */     if (this.mode.getValue() == Mode.DAMAGE) {
/* 219 */       List<EntityPlayer> candidates; int itemSlot; Entity nearestCrystal; Iterator<EntityPlayer> iterator; Optional<BlockUtils.ClickLocation> posCL; switch (this.stage) {
/*     */         case NORTH:
/* 221 */           candidates = getTargets();
/* 222 */           for (iterator = candidates.iterator(); iterator.hasNext(); ) { EntityPlayer candidate = iterator.next();
/* 223 */             if (evaluateTarget(candidate)) {
/* 224 */               int i = getPistonSlot();
/* 225 */               if (i == -1) {
/* 226 */                 Command.sendMessage("No pistons found!");
/* 227 */                 toggle();
/*     */                 
/*     */                 return;
/*     */               } 
/* 231 */               if (this.skipPiston) {
/* 232 */                 this.stage = Stage.CRYSTAL;
/* 233 */                 this.skipPiston = false;
/*     */                 
/*     */                 return;
/*     */               } 
/* 237 */               boolean changeItem = (mc.field_71439_g.field_71071_by.field_70461_c != i);
/* 238 */               boolean isSprinting = mc.field_71439_g.func_70051_ag();
/* 239 */               boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(this.pistonNeighbour);
/*     */ 
/*     */ 
/*     */               
/* 243 */               Vec3d vec = (new Vec3d((Vec3i)this.pistonNeighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(this.pistonOffset.func_176730_m())).func_186678_a(0.5D));
/*     */               
/* 245 */               if (extra) {
/* 246 */                 float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), vec);
/* 247 */                 invokeSync(angle[0], angle[1]);
/*     */               } else {
/* 249 */                 SilentRotationUtil.lookAtVec3d(vec);
/*     */               } 
/*     */               
/* 252 */               this.postAction = (() -> {
/*     */                   this.renderTimer.reset();
/*     */                   if (changeItem) {
/*     */                     mc.field_71439_g.field_71071_by.field_70461_c = itemSlot;
/*     */                     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(itemSlot));
/*     */                   } 
/*     */                   if (isSprinting) {
/*     */                     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */                   }
/*     */                   if (shouldSneak) {
/*     */                     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */                   }
/*     */                   mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, this.pistonNeighbour, this.pistonOffset, vec, EnumHand.MAIN_HAND);
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*     */                   if (shouldSneak) {
/*     */                     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */                   }
/*     */                   if (isSprinting) {
/*     */                     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */                   }
/*     */                   this.stage = Stage.CRYSTAL;
/*     */                 });
/*     */               return;
/*     */             }  }
/*     */           
/*     */           break;
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
/*     */         case SOUTH:
/* 289 */           if (this.torchPos != null && 
/* 290 */             mc.field_71441_e.func_180495_p(this.torchPos).func_177230_c() == Blocks.field_150350_a) {
/* 291 */             this.torchPos = null;
/*     */           }
/*     */           
/* 294 */           if (this.torchPos != null) {
/* 295 */             if (this.torchTimer.passedMs(1000L)) {
/* 296 */               RayTraceResult result = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(this.torchPos.func_177958_n() + 0.5D, this.torchPos.func_177956_o() + 0.5D, this.torchPos.func_177952_p() + 0.5D));
/* 297 */               EnumFacing f = (result == null || result.field_178784_b == null) ? EnumFacing.UP : result.field_178784_b;
/*     */ 
/*     */ 
/*     */               
/* 301 */               Vec3d vec = (new Vec3d((Vec3i)this.torchPos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(f.func_176730_m())).func_186678_a(0.5D));
/*     */               
/* 303 */               if (extra) {
/* 304 */                 float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), vec);
/* 305 */                 invokeSync(angle[0], angle[1]);
/*     */               } else {
/* 307 */                 SilentRotationUtil.lookAtVec3d(vec);
/*     */               } 
/*     */               
/* 310 */               this.postAction = (() -> {
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.torchPos, f));
/*     */                   
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.torchPos, f));
/*     */                   this.torchTimer.reset();
/*     */                 });
/*     */             } 
/*     */             return;
/*     */           } 
/* 319 */           if (!isOffhand()) {
/* 320 */             int crystalSlot = CrystalUtils.getCrystalSlot();
/*     */             
/* 322 */             if (crystalSlot == -1) {
/* 323 */               Command.sendMessage("No crystals found!");
/* 324 */               toggle();
/*     */               
/*     */               return;
/*     */             } 
/* 328 */             if (mc.field_71439_g.field_71071_by.field_70461_c != crystalSlot) {
/* 329 */               mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
/* 330 */               mc.field_71442_b.func_78765_e();
/*     */             } 
/*     */           } 
/*     */           
/* 334 */           if (this.crystalPos == null) {
/* 335 */             this.stage = Stage.SEARCHING;
/*     */             
/*     */             return;
/*     */           } 
/* 339 */           if (extra) {
/* 340 */             float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d(this.crystalPos.func_177958_n() + 0.5D, this.crystalPos.func_177956_o() + 0.5D, this.crystalPos.func_177952_p() + 0.5D));
/* 341 */             invokeSync(angle[0], angle[1]);
/*     */           } else {
/* 343 */             SilentRotationUtil.lookAtVec3d(new Vec3d(this.crystalPos.func_177958_n() + 0.5D, this.crystalPos.func_177956_o() + 0.5D, this.crystalPos.func_177952_p() + 0.5D));
/*     */           } 
/*     */           
/* 346 */           this.postAction = (() -> {
/*     */               this.renderTimer.reset();
/*     */               RayTraceResult result = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(this.crystalPos.func_177958_n() + 0.5D, this.crystalPos.func_177956_o() - 0.5D, this.crystalPos.func_177952_p() + 0.5D));
/* 349 */               EnumFacing f = (result == null || result.field_178784_b == null) ? EnumFacing.UP : result.field_178784_b;
/*     */               BlockUtils.rightClickBlock(this.crystalPos, mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D), isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, f, true);
/*     */               this.stage = Stage.REDSTONE;
/*     */               this.torchTimer.reset();
/*     */             });
/*     */           return;
/*     */ 
/*     */         
/*     */         case EAST:
/* 358 */           if (this.facePos == null) {
/* 359 */             this.stage = Stage.SEARCHING;
/*     */             
/*     */             return;
/*     */           } 
/* 363 */           itemSlot = getRedstoneBlockSlot();
/*     */           
/* 365 */           if (itemSlot == -1) {
/* 366 */             Command.sendMessage("No redstone found!");
/* 367 */             toggle();
/*     */             
/*     */             return;
/*     */           } 
/* 371 */           posCL = BlockUtils.generateClickLocation(this.facePos.func_177967_a(this.faceOffset, 3), false, (((ItemBlock)mc.field_71439_g.field_71071_by.func_70301_a(getRedstoneBlockSlot()).func_77973_b()).func_179223_d() == Blocks.field_150429_aA));
/*     */           
/* 373 */           if (!posCL.isPresent() && ((ItemBlock)mc.field_71439_g.field_71071_by.func_70301_a(getRedstoneBlockSlot()).func_77973_b()).func_179223_d() == Blocks.field_150429_aA)
/* 374 */             for (EnumFacing torchFacing : EnumFacing.field_176754_o) {
/* 375 */               if (!torchFacing.equals(this.faceOffset) && !torchFacing.equals(this.faceOffset.func_176734_d())) {
/*     */                 
/* 377 */                 posCL = BlockUtils.generateClickLocation(this.facePos.func_177967_a(this.faceOffset, 2).func_177972_a(torchFacing), false, (((ItemBlock)mc.field_71439_g.field_71071_by.func_70301_a(getRedstoneBlockSlot()).func_77973_b()).func_179223_d() == Blocks.field_150429_aA));
/* 378 */                 if (posCL.isPresent())
/*     */                   break; 
/*     */               } 
/*     */             }  
/* 382 */           if (posCL.isPresent()) {
/* 383 */             boolean changeItem = (mc.field_71439_g.field_71071_by.field_70461_c != itemSlot);
/* 384 */             boolean isSprinting = mc.field_71439_g.func_70051_ag();
/* 385 */             boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(((BlockUtils.ClickLocation)posCL.get()).neighbour);
/*     */ 
/*     */ 
/*     */             
/* 389 */             Vec3d vec = (new Vec3d((Vec3i)((BlockUtils.ClickLocation)posCL.get()).neighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(((BlockUtils.ClickLocation)posCL.get()).opposite.func_176730_m())).func_186678_a(0.5D));
/*     */             
/* 391 */             if (extra) {
/* 392 */               float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), vec);
/* 393 */               invokeSync(angle[0], angle[1]);
/*     */             } else {
/* 395 */               SilentRotationUtil.lookAtVec3d(vec);
/*     */             } 
/*     */             
/* 398 */             Optional<BlockUtils.ClickLocation> finalCL = posCL;
/*     */             
/* 400 */             this.postAction = (() -> {
/*     */                 this.delayTimer.reset();
/*     */                 
/*     */                 this.renderTimer.reset();
/*     */                 
/*     */                 this.delayTime = ((Integer)this.breakDelay.getValue()).intValue() * 10;
/*     */                 
/*     */                 if (changeItem) {
/*     */                   mc.field_71439_g.field_71071_by.field_70461_c = itemSlot;
/*     */                   
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(itemSlot));
/*     */                 } 
/*     */                 
/*     */                 if (isSprinting) {
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */                 }
/*     */                 
/*     */                 if (shouldSneak) {
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */                 }
/*     */                 
/*     */                 mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, ((BlockUtils.ClickLocation)finalCL.get()).neighbour, ((BlockUtils.ClickLocation)finalCL.get()).opposite, vec, EnumHand.MAIN_HAND);
/*     */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*     */                 if (shouldSneak) {
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */                 }
/*     */                 if (isSprinting) {
/*     */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */                 }
/*     */                 this.stage = Stage.BREAKING;
/*     */               });
/*     */             return;
/*     */           } 
/* 433 */           this.stage = Stage.BREAKING;
/*     */           return;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case WEST:
/* 441 */           nearestCrystal = mc.field_71441_e.field_72996_f.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> (mc.field_71439_g.func_70032_d(e) <= (((Integer)this.targetRange.getValue()).intValue() + 4))).min(Comparator.comparing(c -> Float.valueOf(mc.field_71439_g.func_70032_d(c)))).orElse(null);
/*     */           
/* 443 */           if (nearestCrystal != null) {
/* 444 */             if (((Boolean)this.antiSuicide.getValue()).booleanValue() && CrystalUtils.calculateDamage((EntityEnderCrystal)nearestCrystal, (Entity)mc.field_71439_g) >= mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj())
/*     */               return; 
/* 446 */             this.delayTimer.reset();
/* 447 */             this.renderTimer.reset();
/* 448 */             this.delayTime = ((Integer)this.breakDelay.getValue()).intValue() * 10;
/* 449 */             if (extra) {
/* 450 */               float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), nearestCrystal.func_174791_d());
/* 451 */               invokeSync(angle[0], angle[1]);
/*     */             } else {
/* 453 */               SilentRotationUtil.lookAtVec3d(nearestCrystal.func_174791_d());
/*     */             } 
/* 455 */             this.postAction = (() -> {
/*     */                 mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, nearestCrystal); mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(isOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
/*     */               });
/*     */             break;
/*     */           } 
/* 460 */           if (extra) {
/* 461 */             float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d(this.facePos.func_177958_n() + 0.5D, this.facePos.func_177956_o(), this.facePos.func_177952_p() + 0.5D));
/* 462 */             invokeSync(angle[0], angle[1]); break;
/*     */           } 
/* 464 */           SilentRotationUtil.lookAtXYZ(this.facePos.func_177958_n() + 0.5D, this.facePos.func_177956_o(), this.facePos.func_177952_p() + 0.5D);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } else {
/* 470 */       this.stage = Stage.SEARCHING;
/* 471 */       int pistonSlot = getPistonSlot();
/* 472 */       if (pistonSlot == -1) {
/* 473 */         Command.sendMessage("No pistons found!");
/* 474 */         toggle();
/*     */         
/*     */         return;
/*     */       } 
/* 478 */       int redstoneBlockSlot = getRedstoneBlockSlot();
/*     */       
/* 480 */       if (redstoneBlockSlot == -1) {
/* 481 */         Command.sendMessage("No redstone found!");
/* 482 */         toggle();
/*     */         
/*     */         return;
/*     */       } 
/* 486 */       List<EntityPlayer> candidates = getTargets();
/*     */       
/* 488 */       for (Iterator<EntityPlayer> iterator = candidates.iterator(); iterator.hasNext(); ) { EntityPlayer candidate = iterator.next();
/* 489 */         if (((Boolean)this.smart.getValue()).booleanValue() && 
/* 490 */           !BlockUtils.isHole(new BlockPos((Entity)candidate)) && mc.field_71441_e.func_180495_p(new BlockPos((Entity)candidate)).func_177230_c() == Blocks.field_150350_a) {
/*     */           continue;
/*     */         }
/* 493 */         BlockPos candidatePos = (new BlockPos((Entity)candidate)).func_177984_a();
/* 494 */         if (((Boolean)this.antiSuicide.getValue()).booleanValue() && candidatePos.equals(new BlockPos((Entity)mc.field_71439_g)))
/* 495 */           continue;  for (EnumFacing faceTryOffset : EnumFacing.field_176754_o) {
/* 496 */           if (mc.field_71441_e.func_180495_p(candidatePos.func_177972_a(faceTryOffset)).func_177230_c() instanceof net.minecraft.block.BlockPistonBase || (!this.placedPistonTimer.passed((CrystalUtils.ping() + 150)) && candidatePos.func_177972_a(faceTryOffset).equals(this.placedPiston))) {
/* 497 */             if (mc.field_71441_e.func_180495_p(candidatePos.func_177972_a(faceTryOffset)).func_177230_c() instanceof net.minecraft.block.BlockPistonBase) {
/* 498 */               EnumFacing enumfacing = (EnumFacing)mc.field_71441_e.func_180495_p(candidatePos.func_177972_a(faceTryOffset)).func_177229_b((IProperty)BlockDirectional.field_176387_N);
/* 499 */               if (!enumfacing.equals(faceTryOffset.func_176734_d()))
/*     */                 continue; 
/* 501 */             }  if (mc.field_71441_e.func_180495_p(candidatePos.func_177967_a(faceTryOffset, 2)).func_177230_c() != Blocks.field_150451_bX) { if (mc.field_71441_e.func_180495_p(candidatePos.func_177967_a(faceTryOffset, 2)).func_177230_c() == Blocks.field_150429_aA) {
/*     */                 // Byte code: goto -> 2460
/*     */               }
/* 504 */               if (InteractionUtil.canPlaceBlock(candidatePos.func_177967_a(faceTryOffset, 2), ((Boolean)this.raytrace.getValue()).booleanValue())) {
/* 505 */                 InteractionUtil.Placement placement = InteractionUtil.preparePlacement(candidatePos.func_177967_a(faceTryOffset, 2), true, extra, ((Boolean)this.raytrace.getValue()).booleanValue());
/* 506 */                 if (placement != null) {
/* 507 */                   this.postAction = (() -> {
/*     */                       boolean changeItem = (mc.field_71439_g.field_71071_by.field_70461_c != redstoneBlockSlot);
/*     */                       
/*     */                       int startingItem = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */                       if (changeItem) {
/*     */                         mc.field_71439_g.field_71071_by.field_70461_c = redstoneBlockSlot;
/*     */                         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(redstoneBlockSlot));
/*     */                       } 
/*     */                       InteractionUtil.placeBlockSafely(placement, EnumHand.MAIN_HAND, true);
/*     */                       this.delayTimer.reset();
/*     */                       this.delayTime = CrystalUtils.ping() + 150;
/*     */                       if (changeItem) {
/*     */                         mc.field_71439_g.field_71071_by.field_70461_c = startingItem;
/*     */                         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(startingItem));
/*     */                       } 
/*     */                       if (((Boolean)this.triggerable.getValue()).booleanValue()) {
/*     */                         toggle();
/*     */                       }
/*     */                     });
/*     */                   return;
/*     */                 } 
/*     */                 // Byte code: goto -> 2460
/*     */               } 
/*     */               // Byte code: goto -> 2460 }
/*     */             
/*     */             // Byte code: goto -> 2460
/*     */           } 
/*     */           continue;
/*     */         } 
/* 536 */         for (EnumFacing faceTryOffset : EnumFacing.field_176754_o) {
/* 537 */           if (InteractionUtil.canPlaceBlock(candidatePos.func_177972_a(faceTryOffset), ((Boolean)this.raytrace.getValue()).booleanValue()) && (((Boolean)this.raytrace.getValue()).booleanValue() ? InteractionUtil.canPlaceBlock(candidatePos.func_177967_a(faceTryOffset, 2), true) : (mc.field_71441_e.func_180495_p(candidatePos.func_177967_a(faceTryOffset, 2)).func_177230_c() == Blocks.field_150350_a))) {
/* 538 */             float[] rots = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(1.0F), new Vec3d(candidatePos.func_177972_a(faceTryOffset).func_177958_n() + 0.5D, candidatePos.func_177972_a(faceTryOffset).func_177956_o() + 1.0D, candidatePos.func_177972_a(faceTryOffset).func_177952_p() + 0.5D));
/* 539 */             EnumFacing facing = EnumFacing.func_176733_a(rots[0]);
/* 540 */             if (Math.abs(rots[1]) <= 55.0F && 
/* 541 */               facing == faceTryOffset) {
/* 542 */               InteractionUtil.Placement placement = InteractionUtil.preparePlacement(candidatePos.func_177972_a(faceTryOffset), true, extra, ((Boolean)this.raytrace.getValue()).booleanValue());
/* 543 */               if (placement != null) {
/* 544 */                 this.postAction = (() -> {
/*     */                     boolean changeItem = (mc.field_71439_g.field_71071_by.field_70461_c != pistonSlot);
/*     */                     int startingItem = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */                     if (changeItem) {
/*     */                       mc.field_71439_g.field_71071_by.field_70461_c = pistonSlot;
/*     */                       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(pistonSlot));
/*     */                     } 
/*     */                     InteractionUtil.placeBlockSafely(placement, EnumHand.MAIN_HAND, true);
/*     */                     this.placedPiston = candidatePos.func_177972_a(faceTryOffset);
/*     */                     this.placedPistonTimer.reset();
/*     */                     if (changeItem) {
/*     */                       mc.field_71439_g.field_71071_by.field_70461_c = startingItem;
/*     */                       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(startingItem));
/*     */                     } 
/*     */                   });
/*     */                 return;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }  }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntitySync(EventSync event) {
/* 572 */     if (this.tickCounter < ((Integer)this.actionInterval.getValue()).intValue()) {
/* 573 */       this.tickCounter++;
/*     */     }
/* 575 */     if (event.isCanceled() || !InteractionUtil.canPlaceNormally())
/*     */       return; 
/* 577 */     if (this.stage == Stage.BREAKING) {
/* 578 */       float[] angle = SilentRotationUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d(this.facePos.func_177958_n() + 0.5D, this.facePos.func_177956_o(), this.facePos.func_177952_p() + 0.5D));
/* 579 */       mc.field_71439_g.field_70177_z = angle[0];
/* 580 */       mc.field_71439_g.field_70125_A = angle[1];
/*     */     } 
/*     */     
/* 583 */     if (this.tickCounter < ((Integer)this.actionInterval.getValue()).intValue()) {
/*     */       return;
/*     */     }
/* 586 */     handleAction(false);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void postEntitySync(EventPostSync event) {
/* 591 */     if (this.postAction != null) {
/* 592 */       this.actionTimer.reset();
/* 593 */       this.tickCounter = 0;
/* 594 */       this.postAction.run();
/* 595 */       this.postAction = null;
/* 596 */       int extraBlocks = 0;
/* 597 */       while (extraBlocks < ((Integer)this.actionShift.getValue()).intValue() - 1) {
/* 598 */         handleAction(true);
/* 599 */         if (this.postAction != null) {
/* 600 */           this.postAction.run();
/* 601 */           this.postAction = null;
/*     */         } else {
/*     */           return;
/*     */         } 
/* 605 */         extraBlocks++;
/*     */       } 
/*     */     } 
/* 608 */     this.postAction = null;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 613 */     if (event.getPacket() instanceof SPacketSoundEffect) {
/* 614 */       SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
/* 615 */       if (this.crystalPos == null)
/* 616 */         return;  if (packet.func_186977_b() == SoundCategory.BLOCKS && packet.func_186978_a() == SoundEvents.field_187539_bB && 
/* 617 */         this.crystalPos.func_177984_a().func_185332_f((int)packet.func_149207_d(), (int)packet.func_149211_e(), (int)packet.func_149210_f()) <= 2.0D) {
/* 618 */         this.stage = Stage.SEARCHING;
/* 619 */         this.delayTime = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/* 627 */     if (this.facePos == null || this.faceOffset == null)
/* 628 */       return;  if (this.renderTimer.passedMs(1000L))
/* 629 */       return;  if (((Boolean)this.renderCurrent.getValue()).booleanValue()) {
/* 630 */       BlockPos renderBlock = null;
/*     */       
/* 632 */       switch (this.stage) {
/*     */         case NORTH:
/* 634 */           renderBlock = this.facePos.func_177977_b().func_177967_a(this.faceOffset, 2);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/*     */         case WEST:
/* 639 */           renderBlock = this.facePos.func_177977_b().func_177967_a(this.faceOffset, 1);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 643 */           renderBlock = this.facePos.func_177977_b().func_177967_a(this.faceOffset, 3);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 648 */       if (renderBlock != null) {
/* 649 */         AxisAlignedBB axisAlignedBB = mc.field_71441_e.func_180495_p(renderBlock).func_185900_c((IBlockAccess)mc.field_71441_e, renderBlock).func_186670_a(renderBlock);
/*     */         
/* 651 */         axisAlignedBB = axisAlignedBB.func_72317_d(-((IRenderManager)mc.func_175598_ae()).getRenderPosX(), -((IRenderManager)mc.func_175598_ae()).getRenderPosY(), -((IRenderManager)mc.func_175598_ae()).getRenderPosZ());
/*     */         
/* 653 */         BlockRenderUtil.prepareGL();
/* 654 */         BlockRenderUtil.drawFill(axisAlignedBB, ((ColorSetting)this.colorCurrent.getValue()).getColor());
/* 655 */         BlockRenderUtil.releaseGL();
/*     */         
/* 657 */         BlockRenderUtil.prepareGL();
/* 658 */         BlockRenderUtil.drawOutline(axisAlignedBB, ((ColorSetting)this.outlineColorCurrent.getValue()).getColor(), 1.5F);
/* 659 */         BlockRenderUtil.releaseGL();
/*     */       } 
/*     */     } 
/* 662 */     if (((Boolean)this.renderFull.getValue()).booleanValue()) {
/* 663 */       AxisAlignedBB axisAlignedBB = null;
/*     */       
/* 665 */       switch (this.faceOffset) {
/*     */         case NORTH:
/* 667 */           axisAlignedBB = (new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -3.0D)).func_186670_a(this.facePos.func_177977_b());
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 671 */           axisAlignedBB = (new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 3.0D)).func_186670_a(this.facePos.func_177977_b());
/*     */           break;
/*     */         
/*     */         case EAST:
/* 675 */           axisAlignedBB = (new AxisAlignedBB(0.0D, 0.0D, 0.0D, 3.0D, 1.0D, 1.0D)).func_186670_a(this.facePos.func_177977_b());
/*     */           break;
/*     */         
/*     */         case WEST:
/* 679 */           axisAlignedBB = (new AxisAlignedBB(0.0D, 0.0D, 0.0D, -3.0D, 1.0D, 1.0D)).func_186670_a(this.facePos.func_177977_b());
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 684 */       if (axisAlignedBB != null) {
/*     */         
/* 686 */         axisAlignedBB = axisAlignedBB.func_72317_d(-((IRenderManager)mc.func_175598_ae()).getRenderPosX(), -((IRenderManager)mc.func_175598_ae()).getRenderPosY(), -((IRenderManager)mc.func_175598_ae()).getRenderPosZ());
/*     */         
/* 688 */         BlockRenderUtil.prepareGL();
/* 689 */         BlockRenderUtil.drawFill(axisAlignedBB, ((ColorSetting)this.colorFull.getValue()).getColor());
/* 690 */         BlockRenderUtil.releaseGL();
/*     */         
/* 692 */         BlockRenderUtil.prepareGL();
/* 693 */         BlockRenderUtil.drawOutline(axisAlignedBB, ((ColorSetting)this.outlineColorFull.getValue()).getColor(), 1.5F);
/* 694 */         BlockRenderUtil.releaseGL();
/*     */       } 
/*     */     } 
/* 697 */     if (((Boolean)this.arrow.getValue()).booleanValue()) {
/* 698 */       Vec3d firstVec = null;
/* 699 */       Vec3d secondVec = null;
/* 700 */       Vec3d thirdVec = null;
/*     */       
/* 702 */       BlockPos offsetPos = this.facePos.func_177967_a(this.faceOffset, 2);
/*     */       
/* 704 */       Vec3d properPos = new Vec3d(offsetPos.func_177958_n() + 0.5D - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), (offsetPos.func_177956_o() + 1) - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), offsetPos.func_177952_p() + 0.5D - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ());
/*     */       
/* 706 */       switch (this.faceOffset) {
/*     */         case NORTH:
/* 708 */           firstVec = new Vec3d(properPos.field_72450_a - 0.5D, properPos.field_72448_b, properPos.field_72449_c - 0.5D);
/* 709 */           secondVec = new Vec3d(properPos.field_72450_a, properPos.field_72448_b, properPos.field_72449_c + 0.5D);
/* 710 */           thirdVec = new Vec3d(properPos.field_72450_a + 0.5D, properPos.field_72448_b, properPos.field_72449_c - 0.5D);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 714 */           firstVec = new Vec3d(properPos.field_72450_a - 0.5D, properPos.field_72448_b, properPos.field_72449_c + 0.5D);
/* 715 */           secondVec = new Vec3d(properPos.field_72450_a, properPos.field_72448_b, properPos.field_72449_c - 0.5D);
/* 716 */           thirdVec = new Vec3d(properPos.field_72450_a + 0.5D, properPos.field_72448_b, properPos.field_72449_c + 0.5D);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 720 */           firstVec = new Vec3d(properPos.field_72450_a + 0.5D, properPos.field_72448_b, properPos.field_72449_c - 0.5D);
/* 721 */           secondVec = new Vec3d(properPos.field_72450_a - 0.5D, properPos.field_72448_b, properPos.field_72449_c);
/* 722 */           thirdVec = new Vec3d(properPos.field_72450_a + 0.5D, properPos.field_72448_b, properPos.field_72449_c + 0.5D);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 726 */           firstVec = new Vec3d(properPos.field_72450_a - 0.5D, properPos.field_72448_b, properPos.field_72449_c - 0.5D);
/* 727 */           secondVec = new Vec3d(properPos.field_72450_a + 0.5D, properPos.field_72448_b, properPos.field_72449_c);
/* 728 */           thirdVec = new Vec3d(properPos.field_72450_a - 0.5D, properPos.field_72448_b, properPos.field_72449_c + 0.5D);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 733 */       if (firstVec != null) {
/* 734 */         BlockRenderUtil.prepareGL();
/* 735 */         GL11.glPushMatrix();
/*     */         
/* 737 */         GL11.glEnable(3042);
/*     */         
/* 739 */         GL11.glBlendFunc(770, 771);
/*     */         
/* 741 */         GL11.glDisable(2896);
/* 742 */         GL11.glDisable(3553);
/* 743 */         GL11.glEnable(2848);
/* 744 */         GL11.glDisable(2929);
/* 745 */         GL11.glDepthMask(false);
/*     */         
/* 747 */         GL11.glLineWidth(5.0F);
/*     */         
/* 749 */         GL11.glColor4f((((ColorSetting)this.arrowColor.getValue()).getColor() >> 16 & 0xFF) / 255.0F, (((ColorSetting)this.arrowColor.getValue()).getColor() >> 8 & 0xFF) / 255.0F, (((ColorSetting)this.arrowColor.getValue()).getColor() & 0xFF) / 255.0F, (((ColorSetting)this.arrowColor.getValue()).getColor() >> 24 & 0xFF) / 255.0F);
/*     */         
/* 751 */         if (((Boolean)this.topArrow.getValue()).booleanValue()) {
/* 752 */           GL11.glBegin(1);
/* 753 */           GL11.glVertex3d(firstVec.field_72450_a, firstVec.field_72448_b, firstVec.field_72449_c);
/* 754 */           GL11.glVertex3d(secondVec.field_72450_a, secondVec.field_72448_b, secondVec.field_72449_c);
/* 755 */           GL11.glEnd();
/*     */           
/* 757 */           GL11.glBegin(1);
/* 758 */           GL11.glVertex3d(thirdVec.field_72450_a, thirdVec.field_72448_b, thirdVec.field_72449_c);
/* 759 */           GL11.glVertex3d(secondVec.field_72450_a, secondVec.field_72448_b, secondVec.field_72449_c);
/* 760 */           GL11.glEnd();
/*     */         } 
/*     */         
/* 763 */         if (((Boolean)this.bottomArrow.getValue()).booleanValue()) {
/* 764 */           GL11.glBegin(1);
/* 765 */           GL11.glVertex3d(firstVec.field_72450_a, firstVec.field_72448_b - 1.0D, firstVec.field_72449_c);
/* 766 */           GL11.glVertex3d(secondVec.field_72450_a, secondVec.field_72448_b - 1.0D, secondVec.field_72449_c);
/* 767 */           GL11.glEnd();
/*     */           
/* 769 */           GL11.glBegin(1);
/* 770 */           GL11.glVertex3d(thirdVec.field_72450_a, thirdVec.field_72448_b - 1.0D, thirdVec.field_72449_c);
/* 771 */           GL11.glVertex3d(secondVec.field_72450_a, secondVec.field_72448_b - 1.0D, secondVec.field_72449_c);
/* 772 */           GL11.glEnd();
/*     */         } 
/*     */         
/* 775 */         GL11.glLineWidth(1.0F);
/*     */         
/* 777 */         GL11.glDisable(2848);
/* 778 */         GL11.glEnable(3553);
/* 779 */         GL11.glEnable(2896);
/* 780 */         GL11.glEnable(2929);
/*     */         
/* 782 */         GL11.glDepthMask(true);
/*     */         
/* 784 */         GL11.glDisable(3042);
/* 785 */         GL11.glPopMatrix();
/*     */         
/* 787 */         BlockRenderUtil.releaseGL();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isOffhand() {
/* 793 */     return (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP);
/*     */   }
/*     */   
/*     */   private boolean evaluateTarget(EntityPlayer candidate) {
/* 797 */     if (getRedstoneBlockSlot() == -1) {
/* 798 */       Command.sendMessage("No redstone found!");
/* 799 */       toggle();
/* 800 */       return false;
/*     */     } 
/* 802 */     BlockPos tempFacePos = (new BlockPos((Entity)candidate)).func_177984_a();
/* 803 */     if (evaluateTarget(tempFacePos)) {
/* 804 */       return true;
/*     */     }
/* 806 */     tempFacePos = (new BlockPos((Entity)candidate)).func_177984_a().func_177984_a();
/* 807 */     return evaluateTarget(tempFacePos);
/*     */   }
/*     */   
/*     */   public boolean canPlaceCrystal(BlockPos blockPos) {
/* 811 */     if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e
/* 812 */       .func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) return false;
/*     */     
/* 814 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/*     */     
/* 816 */     if (mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150332_K) {
/* 817 */       return false;
/*     */     }
/* 819 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/*     */     
/* 821 */     if (!((Boolean)this.protocol.getValue()).booleanValue() && 
/* 822 */       mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a) {
/* 823 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 827 */     return mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost, boost2.func_177982_a(1, 1, 1))).isEmpty();
/*     */   }
/*     */   
/*     */   public boolean evaluateTarget(BlockPos tempFacePos) {
/* 831 */     if (!isAir(tempFacePos) && !((Boolean)this.mine.getValue()).booleanValue()) return false; 
/* 832 */     for (EnumFacing faceTryOffset : EnumFacing.field_176754_o) {
/* 833 */       this.torchPos = null;
/* 834 */       this.skipPiston = false;
/* 835 */       if (!canPlaceCrystal(tempFacePos.func_177972_a(faceTryOffset).func_177977_b()))
/*     */         continue; 
/* 837 */       if (getRedstoneBlockSlot() == -1) {
/* 838 */         return false;
/*     */       }
/*     */       
/* 841 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(getRedstoneBlockSlot());
/* 842 */       Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
/* 843 */       if (block == Blocks.field_150451_bX) {
/* 844 */         if (!isAir(tempFacePos.func_177967_a(faceTryOffset, 3))) {
/* 845 */           if (((Boolean)this.mine.getValue()).booleanValue() && (mc.field_71441_e.func_180495_p(tempFacePos.func_177967_a(faceTryOffset, 3)).func_177230_c() == Blocks.field_150429_aA || mc.field_71441_e.func_180495_p(tempFacePos.func_177967_a(faceTryOffset, 3)).func_177230_c() == Blocks.field_150451_bX)) {
/* 846 */             this.torchPos = tempFacePos.func_177967_a(faceTryOffset, 3);
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */         }
/*     */       } else {
/* 852 */         Optional<BlockUtils.ClickLocation> optional = BlockUtils.generateClickLocation(tempFacePos.func_177967_a(faceTryOffset, 3), false, true);
/*     */         
/* 854 */         if (!optional.isPresent() && ((Boolean)this.mine.getValue()).booleanValue() && (mc.field_71441_e.func_180495_p(tempFacePos.func_177967_a(faceTryOffset, 3)).func_177230_c() == Blocks.field_150429_aA || mc.field_71441_e.func_180495_p(tempFacePos.func_177967_a(faceTryOffset, 3)).func_177230_c() == Blocks.field_150451_bX)) {
/* 855 */           this.torchPos = tempFacePos.func_177967_a(faceTryOffset, 3);
/*     */         }
/*     */         
/* 858 */         if (!optional.isPresent() && this.torchPos == null && ((ItemBlock)mc.field_71439_g.field_71071_by.func_70301_a(getRedstoneBlockSlot()).func_77973_b()).func_179223_d() == Blocks.field_150429_aA) {
/* 859 */           for (EnumFacing torchFacing : EnumFacing.field_176754_o) {
/* 860 */             if (!torchFacing.equals(faceTryOffset) && !torchFacing.equals(faceTryOffset.func_176734_d())) {
/*     */               
/* 862 */               optional = BlockUtils.generateClickLocation(tempFacePos.func_177967_a(faceTryOffset, 2).func_177972_a(torchFacing), false, true);
/* 863 */               if (optional.isPresent())
/*     */                 break; 
/* 865 */               if (((Boolean)this.mine.getValue()).booleanValue() && mc.field_71441_e.func_180495_p(tempFacePos.func_177967_a(faceTryOffset, 2).func_177972_a(torchFacing)).func_177230_c() == Blocks.field_150429_aA) {
/* 866 */                 this.torchPos = tempFacePos.func_177967_a(faceTryOffset, 2).func_177972_a(torchFacing);
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/* 872 */         if (!optional.isPresent() && this.torchPos == null) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 877 */       Optional<BlockUtils.ClickLocation> posCL = BlockUtils.generateClickLocation(tempFacePos.func_177967_a(faceTryOffset, 2));
/* 878 */       this.skipPiston = (((Boolean)this.mine.getValue()).booleanValue() && mc.field_71441_e.func_180495_p(tempFacePos.func_177967_a(faceTryOffset, 2)).func_177230_c() instanceof net.minecraft.block.BlockPistonBase);
/* 879 */       if (posCL.isPresent() || this.skipPiston) {
/* 880 */         if (!this.skipPiston) {
/* 881 */           BlockPos currentPos = ((BlockUtils.ClickLocation)posCL.get()).neighbour;
/* 882 */           EnumFacing currentFace = ((BlockUtils.ClickLocation)posCL.get()).opposite;
/* 883 */           double[] yawPitch = BlockUtils.calculateLookAt(currentPos.func_177958_n(), currentPos.func_177956_o(), currentPos.func_177952_p(), currentFace, (EntityPlayer)mc.field_71439_g);
/* 884 */           EnumFacing facing = EnumFacing.func_176733_a(yawPitch[0]);
/* 885 */           if (Math.abs(yawPitch[1]) > 55.0D || 
/* 886 */             facing != faceTryOffset)
/*     */             continue; 
/* 888 */           if (((Boolean)this.raytrace.getValue()).booleanValue() && 
/* 889 */             !rayTrace(((BlockUtils.ClickLocation)posCL.get()).neighbour))
/*     */             continue; 
/* 891 */           this.pistonNeighbour = currentPos;
/* 892 */           this.pistonOffset = currentFace;
/*     */         } 
/*     */         
/* 895 */         this.facePos = tempFacePos;
/* 896 */         this.faceOffset = faceTryOffset;
/* 897 */         this.crystalPos = tempFacePos.func_177972_a(faceTryOffset).func_177977_b();
/* 898 */         return true;
/*     */       }  continue;
/*     */     } 
/* 901 */     return false;
/*     */   }
/*     */   
/*     */   private boolean rayTrace(BlockPos pos) {
/* 905 */     for (double xS = 0.1D; xS < 0.9D; xS += 0.1D) {
/* 906 */       double yS; for (yS = 0.1D; yS < 0.9D; yS += 0.1D) {
/* 907 */         double zS; for (zS = 0.1D; zS < 0.9D; zS += 0.1D) {
/* 908 */           Vec3d eyesPos = new Vec3d(mc.field_71439_g.field_70165_t, (mc.field_71439_g.func_174813_aQ()).field_72338_b + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/* 909 */           Vec3d posVec = (new Vec3d((Vec3i)pos)).func_72441_c(xS, yS, zS);
/*     */           
/* 911 */           double distToPosVec = eyesPos.func_72438_d(posVec);
/* 912 */           double diffX = posVec.field_72450_a - eyesPos.field_72450_a;
/* 913 */           double diffY = posVec.field_72448_b - eyesPos.field_72448_b;
/* 914 */           double diffZ = posVec.field_72449_c - eyesPos.field_72449_c;
/* 915 */           double diffXZ = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
/*     */           
/* 917 */           double[] tempPlaceRotation = { MathHelper.func_76142_g((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), MathHelper.func_76142_g((float)-Math.toDegrees(Math.atan2(diffY, diffXZ))) };
/*     */           
/* 919 */           float yawCos = MathHelper.func_76134_b((float)(-tempPlaceRotation[0] * 0.01745329238474369D - 3.1415927410125732D));
/* 920 */           float yawSin = MathHelper.func_76126_a((float)(-tempPlaceRotation[0] * 0.01745329238474369D - 3.1415927410125732D));
/* 921 */           float pitchCos = -MathHelper.func_76134_b((float)(-tempPlaceRotation[1] * 0.01745329238474369D));
/* 922 */           float pitchSin = MathHelper.func_76126_a((float)(-tempPlaceRotation[1] * 0.01745329238474369D));
/*     */           
/* 924 */           Vec3d rotationVec = new Vec3d((yawSin * pitchCos), pitchSin, (yawCos * pitchCos));
/* 925 */           Vec3d eyesRotationVec = eyesPos.func_72441_c(rotationVec.field_72450_a * distToPosVec, rotationVec.field_72448_b * distToPosVec, rotationVec.field_72449_c * distToPosVec);
/*     */ 
/*     */           
/* 928 */           RayTraceResult rayTraceResult = mc.field_71441_e.func_147447_a(eyesPos, eyesRotationVec, false, false, false);
/* 929 */           if (rayTraceResult != null && 
/* 930 */             rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK && 
/* 931 */             rayTraceResult.func_178782_a().equals(pos)) {
/* 932 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 939 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isAir(BlockPos pos) {
/* 943 */     return mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockAir;
/*     */   }
/*     */   
/*     */   private List<EntityPlayer> getTargets() {
/* 947 */     return (List<EntityPlayer>)mc.field_71441_e.field_73010_i.stream()
/* 948 */       .filter(entityPlayer -> !Thunderhack.friendManager.isFriend(entityPlayer.func_70005_c_()))
/* 949 */       .filter(entityPlayer -> (entityPlayer != mc.field_71439_g))
/* 950 */       .filter(e -> (mc.field_71439_g.func_70032_d((Entity)e) < ((Integer)this.targetRange.getValue()).intValue()))
/* 951 */       .sorted(Comparator.comparing(e -> Float.valueOf(mc.field_71439_g.func_70032_d((Entity)e))))
/* 952 */       .collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   private int getRedstoneBlockSlot() {
/* 956 */     int slot = -1;
/* 957 */     for (int i = 0; i < 9; i++) {
/* 958 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 959 */       if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
/* 960 */         Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
/* 961 */         if (block == Blocks.field_150451_bX || block == Blocks.field_150429_aA) {
/* 962 */           slot = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 968 */     return slot;
/*     */   }
/*     */   
/*     */   private enum Mode {
/* 972 */     DAMAGE, PUSH;
/*     */   }
/*     */   
/*     */   private enum Stage
/*     */   {
/* 977 */     SEARCHING,
/* 978 */     CRYSTAL,
/* 979 */     REDSTONE,
/* 980 */     BREAKING,
/* 981 */     EXPLOSION;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\PistonAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */