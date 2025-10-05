/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.movement.PacketFly;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.CrystalUtils;
/*     */ import com.mrzak34.thunderhack.util.InteractionUtil;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
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
/*     */ public class CevBreaker extends Module {
/*  44 */   public static ConcurrentHashMap<BlockPos, Long> shiftedBlocks = new ConcurrentHashMap<>();
/*  45 */   public final Setting<ColorSetting> Color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  46 */   public final Setting<ColorSetting> Color2 = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  47 */   private final Setting<Integer> pickTickSwitch = register(new Setting("Pick Switch Destroy", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(20)));
/*  48 */   private final Setting<Float> placeRange = register(new Setting("TargetRange", Float.valueOf(4.5F), Float.valueOf(1.0F), Float.valueOf(16.0F)));
/*     */   public boolean startBreak = false;
/*     */   boolean broke = false;
/*  51 */   Timer renderTimer = new Timer();
/*  52 */   private final Setting<Mode> mode = register(new Setting("BreakMode", Mode.TripleP));
/*  53 */   private final Setting<Integer> crysDelay = register(new Setting("CrysDelay", Integer.valueOf(200), Integer.valueOf(1), Integer.valueOf(1000)));
/*  54 */   private final Setting<Integer> atttt = register(new Setting("AttackDelay", Integer.valueOf(200), Integer.valueOf(1), Integer.valueOf(1000)));
/*  55 */   private final Setting<Integer> pausedelay = register(new Setting("PauseDelay", Integer.valueOf(300), Integer.valueOf(1), Integer.valueOf(1000)));
/*  56 */   private final Setting<Integer> actionShift = register(new Setting("ActionShift", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(8)));
/*  57 */   private final Setting<Integer> actionInterval = register(new Setting("ActionInterval", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10)));
/*  58 */   private final Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  59 */   private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
/*  60 */   private final Setting<Boolean> p1 = register(new Setting("PacketCrystal", Boolean.valueOf(true)));
/*  61 */   private final Setting<Boolean> MStrict = register(new Setting("ModeStrict", Boolean.valueOf(true)));
/*  62 */   private final Setting<Boolean> strictdirection = register(new Setting("StrictDirection", Boolean.valueOf(true)));
/*  63 */   private int tick = 99;
/*     */   private int oldslot;
/*  65 */   private int wait = 50;
/*  66 */   private BlockPos lastBlock = null;
/*  67 */   private BlockPos continueBlock = null;
/*     */   private boolean pickStillBol = false;
/*     */   private EnumFacing direction;
/*  70 */   private final Timer attackTimer = new Timer();
/*  71 */   private final Timer cryTimer = new Timer();
/*     */   private int itemSlot;
/*     */   private BlockPos renderPos;
/*  74 */   private int tickCounter = 0;
/*  75 */   private BlockPos playerPos = null;
/*  76 */   private BlockPos toppos = null;
/*     */   private InteractionUtil.Placement placement;
/*     */   private InteractionUtil.Placement lastPlacement;
/*  79 */   private final Timer lastPlacementTimer = new Timer();
/*  80 */   private final Timer pausetimer = new Timer();
/*     */   public CevBreaker() {
/*  82 */     super("CevBreaker", "CevBreaker", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   public static EntityEnderCrystal searchCrystal(BlockPos blockPos) {
/*  86 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/*  87 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/*     */     
/*  89 */     for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost))) {
/*  90 */       if (entity instanceof EntityEnderCrystal) {
/*  91 */         return (EntityEnderCrystal)entity;
/*     */       }
/*     */     } 
/*  94 */     for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2))) {
/*  95 */       if (entity instanceof EntityEnderCrystal) {
/*  96 */         return (EntityEnderCrystal)entity;
/*     */       }
/*     */     } 
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public static int getPicSlot() {
/* 103 */     int pic = -1;
/*     */     
/* 105 */     if (Util.mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151046_w) {
/* 106 */       pic = Util.mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/* 108 */     if (pic == -1) {
/* 109 */       for (int l = 0; l < 9; l++) {
/* 110 */         if (Util.mc.field_71439_g.field_71071_by.func_70301_a(l).func_77973_b() == Items.field_151046_w) {
/* 111 */           pic = l;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 117 */     return pic;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 123 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/* 124 */       toggle();
/*     */       return;
/*     */     } 
/* 127 */     this.startBreak = false;
/* 128 */     this.renderPos = null;
/* 129 */     this.playerPos = null;
/* 130 */     this.placement = null;
/* 131 */     this.lastPlacement = null;
/* 132 */     this.tickCounter = ((Integer)this.actionInterval.getValue()).intValue();
/* 133 */     this.lastBlock = null;
/* 134 */     this.tick = 99;
/* 135 */     this.wait = 50;
/* 136 */     this.continueBlock = null;
/* 137 */     this.pickStillBol = false;
/* 138 */     this.direction = null;
/* 139 */     this.broke = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 144 */     this.continueBlock = null;
/* 145 */     this.lastBlock = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 150 */     if (this.mode.getValue() == Mode.DoubleP && 
/* 151 */       this.continueBlock != null) {
/* 152 */       if (BlockUtils.getBlockgs(this.continueBlock) instanceof net.minecraft.block.BlockAir) {
/* 153 */         this.broke = true;
/*     */       }
/* 155 */       if (!(BlockUtils.getBlockgs(this.continueBlock) instanceof net.minecraft.block.BlockAir) && this.broke) {
/* 156 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 157 */         mc.field_71442_b.func_180512_c(this.continueBlock, EnumFacing.UP);
/* 158 */         this.broke = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 163 */     if (this.tick != 99 && 
/* 164 */       this.tick++ >= this.wait) {
/* 165 */       if (this.oldslot != -1) {
/* 166 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.oldslot));
/* 167 */         mc.field_71442_b.func_78765_e();
/* 168 */         if (this.lastBlock != null && this.direction != null)
/* 169 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.lastBlock, this.direction)); 
/* 170 */         this.oldslot = -1;
/*     */       } 
/* 172 */       if (!this.pickStillBol) {
/* 173 */         this.wait = 12;
/* 174 */         this.tick = 0;
/* 175 */         this.oldslot = InventoryUtil.getPicatHotbar();
/* 176 */         this.pickStillBol = true;
/*     */       } else {
/* 178 */         this.tick = 99;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBreakPacket() {
/* 185 */     if (this.mode.getValue() == Mode.Vanilla)
/* 186 */       return;  if (mc.field_71441_e == null || mc.field_71439_g == null)
/* 187 */       return;  if (this.toppos == null)
/*     */       return; 
/* 189 */     if (this.mode.getValue() == Mode.DoubleP) {
/* 190 */       this.continueBlock = this.toppos;
/*     */     }
/* 192 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 193 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.toppos, handlePlaceRotation(this.toppos)));
/* 194 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.toppos, handlePlaceRotation(this.toppos)));
/* 195 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.toppos, handlePlaceRotation(this.toppos)));
/*     */     
/* 197 */     this.lastBlock = this.toppos;
/* 198 */     this.direction = handlePlaceRotation(this.toppos);
/* 199 */     this.oldslot = InventoryUtil.getPicatHotbar();
/* 200 */     this.tick = 0;
/* 201 */     this.wait = ((Integer)this.pickTickSwitch.getValue()).intValue() + 50;
/* 202 */     this.pickStillBol = false;
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 206 */     if (this.renderPos != null && !this.renderTimer.passedMs(500L)) {
/* 207 */       RenderUtil.drawBlockOutline(this.renderPos, ((ColorSetting)this.Color2.getValue()).getColorObject(), 0.3F, true, 0);
/*     */     }
/* 209 */     if (this.lastBlock != null) {
/* 210 */       RenderUtil.drawBlockOutline(this.lastBlock, new Color(175, 175, 255), 2.0F, false, 0);
/*     */       
/* 212 */       this; this; this; float prognum = this.tick / ((Integer)this.pickTickSwitch.getValue()).intValue() * 100.0F / 50.0F * mc.field_71441_e.func_180495_p(this.lastBlock).func_177230_c().func_176195_g(mc.field_71441_e.func_180495_p(this.lastBlock), (World)mc.field_71441_e, this.lastBlock);
/*     */ 
/*     */       
/* 215 */       GlStateManager.func_179094_E();
/*     */       try {
/* 217 */         RenderUtil.glBillboardDistanceScaled(this.lastBlock.func_177958_n() + 0.5F, this.lastBlock.func_177956_o() + 0.5F, this.lastBlock.func_177952_p() + 0.5F, (EntityPlayer)mc.field_71439_g, 1.0F);
/* 218 */       } catch (Exception exception) {}
/*     */       
/* 220 */       GlStateManager.func_179097_i();
/* 221 */       GlStateManager.func_179140_f();
/* 222 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 223 */       mc.field_71466_p.func_175063_a(String.valueOf(prognum), (int)-(mc.field_71466_p.func_78256_a(String.valueOf(prognum)) / 2.0D), -4.0F, -1);
/* 224 */       GlStateManager.func_179145_e();
/* 225 */       GlStateManager.func_179126_j();
/* 226 */       GlStateManager.func_179121_F();
/*     */     } 
/* 228 */     if (this.toppos != null) {
/* 229 */       EntityEnderCrystal ent = searchCrystal(this.toppos);
/* 230 */       if (ent != null && this.attackTimer.passedMs(((Integer)this.atttt.getValue()).intValue())) {
/* 231 */         RenderUtil.drawBoxESP(this.toppos, new Color(2472706), false, new Color(3145472), 0.5F, true, true, 170, false, 0);
/*     */       } else {
/* 233 */         RenderUtil.drawBoxESP(this.toppos, new Color(12255746), false, new Color(16711680), 0.5F, true, true, 170, false, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPre(EventSync event) {
/* 241 */     if (this.playerPos != null) {
/* 242 */       if (canPlaceCrystal(this.playerPos.func_177984_a().func_177984_a()) && this.cryTimer.passedMs(((Integer)this.crysDelay.getValue()).intValue()) && searchCrystal(this.playerPos.func_177984_a().func_177984_a()) == null) {
/* 243 */         placeCrystal(this.playerPos.func_177984_a().func_177984_a(), handlePlaceRotation(this.playerPos.func_177984_a().func_177984_a()));
/* 244 */         setPickSlot();
/* 245 */         this.cryTimer.reset();
/* 246 */       } else if (canBreakCrystal(this.playerPos.func_177984_a().func_177984_a())) {
/* 247 */         EntityEnderCrystal ent = searchCrystal(this.playerPos.func_177984_a().func_177984_a());
/* 248 */         if (ent != null && this.attackTimer.passedMs(((Integer)this.atttt.getValue()).intValue())) {
/* 249 */           mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, (Entity)ent);
/* 250 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 251 */           this.startBreak = true;
/* 252 */           this.attackTimer.reset();
/* 253 */           this.pausetimer.reset();
/*     */         } 
/*     */       } 
/*     */     }
/* 257 */     if (this.toppos != null) {
/* 258 */       if (this.mode.getValue() == Mode.Vanilla) {
/* 259 */         EntityEnderCrystal ent = searchCrystal(this.toppos);
/* 260 */         if (ent == null) {
/* 261 */           placeCrystal(this.toppos, handlePlaceRotation(this.toppos));
/*     */         } else {
/* 263 */           setPickSlot();
/* 264 */           if (mc.field_71441_e.func_180495_p(this.toppos).func_177230_c() != Blocks.field_150350_a) {
/* 265 */             mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 266 */             mc.field_71442_b.func_180512_c(this.toppos, handlePlaceRotation(this.toppos));
/*     */           }
/*     */         
/*     */         } 
/* 270 */       } else if (!this.startBreak) {
/* 271 */         onBreakPacket();
/* 272 */         this.startBreak = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 277 */     if (this.placement != null) {
/* 278 */       this.lastPlacement = this.placement;
/* 279 */       this.lastPlacementTimer.reset();
/*     */     } 
/* 281 */     this.placement = null;
/* 282 */     this.playerPos = null;
/*     */     
/* 284 */     int ping = CrystalUtils.ping();
/*     */     
/* 286 */     shiftedBlocks.forEach((pos, time) -> {
/*     */           if (System.currentTimeMillis() - time.longValue() > (ping + 100)) {
/*     */             shiftedBlocks.remove(pos);
/*     */           }
/*     */         });
/*     */     
/* 292 */     if (event.isCanceled())
/*     */       return; 
/* 294 */     if (((Boolean)this.strict.getValue()).booleanValue() && (!mc.field_71439_g.field_70122_E || !mc.field_71439_g.field_70124_G))
/*     */       return; 
/* 296 */     if (((PacketFly)Thunderhack.moduleManager.getModuleByClass(PacketFly.class)).isEnabled())
/*     */       return; 
/* 298 */     if (this.tickCounter < ((Integer)this.actionInterval.getValue()).intValue()) {
/* 299 */       this.tickCounter++;
/*     */     }
/*     */     
/* 302 */     int slot = getBlockSlot();
/* 303 */     if (slot == -1) {
/* 304 */       Command.sendMessage("No Obby Found!");
/* 305 */       toggle();
/*     */       return;
/*     */     } 
/* 308 */     this.itemSlot = slot;
/*     */     
/* 310 */     EntityPlayer nearestPlayer = getNearestTarget();
/*     */     
/* 312 */     if (nearestPlayer == null)
/*     */       return; 
/* 314 */     if (this.tickCounter < ((Integer)this.actionInterval.getValue()).intValue()) {
/* 315 */       if (this.lastPlacement != null && !this.lastPlacementTimer.passedMs(650L)) {
/* 316 */         mc.field_71439_g.field_70125_A = this.lastPlacement.getPitch();
/* 317 */         mc.field_71439_g.field_70177_z = this.lastPlacement.getYaw();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 322 */     this.playerPos = new BlockPos(nearestPlayer.field_70165_t, nearestPlayer.field_70163_u, nearestPlayer.field_70161_v);
/*     */     
/* 324 */     BlockPos firstPos = getNextPos(this.playerPos);
/*     */     
/* 326 */     if (firstPos != null) {
/* 327 */       this.placement = InteractionUtil.preparePlacement(firstPos, ((Boolean)this.rotate.getValue()).booleanValue(), event);
/* 328 */       if (this.placement != null) {
/* 329 */         shiftedBlocks.put(firstPos, Long.valueOf(System.currentTimeMillis()));
/* 330 */         this.tickCounter = 0;
/* 331 */         this.renderPos = firstPos;
/* 332 */         this.renderTimer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPost(EventPostSync event) {
/* 340 */     if (!this.pausetimer.passedMs(((Integer)this.pausedelay.getValue()).intValue())) {
/*     */       return;
/*     */     }
/* 343 */     if (this.placement != null && this.playerPos != null && this.itemSlot != -1) {
/* 344 */       boolean changeItem = (mc.field_71439_g.field_71071_by.field_70461_c != this.itemSlot);
/* 345 */       int startingItem = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */       
/* 347 */       if (changeItem) {
/* 348 */         mc.field_71439_g.field_71071_by.field_70461_c = this.itemSlot;
/* 349 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.itemSlot));
/*     */       } 
/*     */       
/* 352 */       boolean isSprinting = mc.field_71439_g.func_70051_ag();
/* 353 */       boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(this.placement.getNeighbour());
/*     */       
/* 355 */       if (isSprinting) {
/* 356 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */       }
/*     */       
/* 359 */       if (shouldSneak) {
/* 360 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */       }
/*     */       
/* 363 */       InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
/*     */       
/* 365 */       int extraBlocks = 0;
/* 366 */       while (extraBlocks < ((Integer)this.actionShift.getValue()).intValue() - 1) {
/* 367 */         BlockPos nextPos = getNextPos(this.playerPos);
/* 368 */         if (nextPos != null) {
/* 369 */           InteractionUtil.Placement nextPlacement = InteractionUtil.preparePlacement(nextPos, ((Boolean)this.rotate.getValue()).booleanValue(), true);
/* 370 */           if (nextPlacement != null) {
/* 371 */             this.placement = nextPlacement;
/* 372 */             shiftedBlocks.put(nextPos, Long.valueOf(System.currentTimeMillis()));
/* 373 */             InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
/* 374 */             this.renderPos = nextPos;
/* 375 */             this.renderTimer.reset();
/* 376 */             extraBlocks++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 384 */       this.cryTimer.reset();
/* 385 */       if (shouldSneak) {
/* 386 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */       }
/*     */       
/* 389 */       if (isSprinting) {
/* 390 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
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
/*     */   private boolean canPlaceBlock(BlockPos pos, boolean strictDirection) {
/* 402 */     return (InteractionUtil.canPlaceBlock(pos, strictDirection) && !shiftedBlocks.containsKey(pos));
/*     */   }
/*     */   
/*     */   private BlockPos getNextPos(BlockPos playerPos) {
/* 406 */     for (EnumFacing enumFacing : EnumFacing.field_176754_o) {
/* 407 */       BlockPos furthestBlock = null;
/* 408 */       double furthestDistance = 0.0D;
/* 409 */       if (canPlaceBlock(playerPos.func_177972_a(enumFacing).func_177977_b(), true)) {
/* 410 */         BlockPos tempBlock = playerPos.func_177972_a(enumFacing).func_177977_b();
/* 411 */         double tempDistance = mc.field_71439_g.func_70011_f(tempBlock.func_177958_n() + 0.5D, tempBlock.func_177956_o() + 0.5D, tempBlock.func_177952_p() + 0.5D);
/* 412 */         if (tempDistance >= furthestDistance) {
/* 413 */           furthestBlock = tempBlock;
/* 414 */           furthestDistance = tempDistance;
/*     */         } 
/*     */       } 
/* 417 */       if (furthestBlock != null) return furthestBlock;
/*     */     
/*     */     } 
/* 420 */     for (EnumFacing enumFacing : EnumFacing.field_176754_o) {
/* 421 */       BlockPos furthestBlock = null;
/* 422 */       double furthestDistance = 0.0D;
/* 423 */       if (canPlaceBlock(playerPos.func_177972_a(enumFacing), false)) {
/* 424 */         BlockPos tempBlock = playerPos.func_177972_a(enumFacing);
/* 425 */         double tempDistance = mc.field_71439_g.func_70011_f(tempBlock.func_177958_n() + 0.5D, tempBlock.func_177956_o() + 0.5D, tempBlock.func_177952_p() + 0.5D);
/* 426 */         if (tempDistance >= furthestDistance) {
/* 427 */           furthestBlock = tempBlock;
/* 428 */           furthestDistance = tempDistance;
/*     */         } 
/*     */       } 
/* 431 */       if (furthestBlock != null) return furthestBlock;
/*     */     
/*     */     } 
/* 434 */     for (EnumFacing enumFacing : EnumFacing.field_176754_o) {
/* 435 */       BlockPos furthestBlock = null;
/* 436 */       double furthestDistance = 0.0D;
/* 437 */       if (canPlaceBlock(playerPos.func_177984_a().func_177972_a(enumFacing), false)) {
/* 438 */         BlockPos tempBlock = playerPos.func_177984_a().func_177972_a(enumFacing);
/* 439 */         double tempDistance = mc.field_71439_g.func_70011_f(tempBlock.func_177958_n() + 0.5D, tempBlock.func_177956_o() + 0.5D, tempBlock.func_177952_p() + 0.5D);
/* 440 */         if (tempDistance >= furthestDistance) {
/* 441 */           furthestBlock = tempBlock;
/* 442 */           furthestDistance = tempDistance;
/*     */         } 
/*     */       } 
/* 445 */       if (furthestBlock != null) return furthestBlock;
/*     */     
/*     */     } 
/* 448 */     Block baseBlock = mc.field_71441_e.func_180495_p(playerPos.func_177984_a().func_177984_a()).func_177230_c();
/* 449 */     if (baseBlock instanceof net.minecraft.block.BlockAir || baseBlock instanceof net.minecraft.block.BlockLiquid) {
/* 450 */       if (canPlaceBlock(playerPos.func_177984_a().func_177984_a(), false)) {
/* 451 */         this.toppos = playerPos.func_177984_a().func_177984_a();
/* 452 */         return playerPos.func_177984_a().func_177984_a();
/*     */       } 
/* 454 */       BlockPos offsetPos = playerPos.func_177984_a().func_177984_a().func_177972_a(EnumFacing.func_176731_b(MathHelper.func_76128_c((mc.field_71439_g.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3));
/* 455 */       if (canPlaceBlock(offsetPos, false)) {
/* 456 */         return offsetPos;
/*     */       }
/*     */     } 
/*     */     
/* 460 */     return null;
/*     */   }
/*     */   
/*     */   private boolean canPlaceCrystal(BlockPos blockPos) {
/* 464 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/* 465 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/*     */     try {
/* 467 */       if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
/* 468 */         return false;
/*     */       }
/* 470 */       if (mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a || mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a) {
/* 471 */         return false;
/*     */       }
/* 473 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost))) {
/* 474 */         if (entity instanceof EntityEnderCrystal)
/* 475 */           continue;  return false;
/*     */       } 
/* 477 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2))) {
/* 478 */         if (entity instanceof EntityEnderCrystal)
/* 479 */           continue;  return false;
/*     */       } 
/* 481 */     } catch (Exception ignored) {
/* 482 */       return false;
/*     */     } 
/* 484 */     return true;
/*     */   }
/*     */   
/*     */   private boolean canBreakCrystal(BlockPos blockPos) {
/*     */     try {
/* 489 */       if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
/* 490 */         return true;
/*     */       }
/* 492 */     } catch (Exception ignored) {
/* 493 */       return false;
/*     */     } 
/* 495 */     return false;
/*     */   }
/*     */   
/*     */   public boolean setCrystalSlot() {
/* 499 */     int crystalSlot = CrystalUtils.getCrystalSlot();
/* 500 */     if (crystalSlot == -1)
/* 501 */       return false; 
/* 502 */     if (mc.field_71439_g.field_71071_by.field_70461_c != crystalSlot) {
/* 503 */       mc.field_71439_g.field_71071_by.field_70461_c = crystalSlot;
/* 504 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(crystalSlot));
/*     */     } 
/* 506 */     return true;
/*     */   }
/*     */   
/*     */   public boolean setPickSlot() {
/* 510 */     int pickslot = getPicSlot();
/* 511 */     if (pickslot == -1)
/* 512 */       return false; 
/* 513 */     if (mc.field_71439_g.field_71071_by.field_70461_c != pickslot) {
/* 514 */       mc.field_71439_g.field_71071_by.field_70461_c = pickslot;
/* 515 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(pickslot));
/*     */     } 
/* 517 */     return true;
/*     */   }
/*     */   
/*     */   public boolean placeCrystal(BlockPos pos, EnumFacing facing) {
/* 521 */     if (pos != null) {
/* 522 */       if (!setCrystalSlot()) return false; 
/* 523 */       if (mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_185158_cP)
/* 524 */         return false; 
/* 525 */       BlockUtils.rightClickBlock(pos, mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D), EnumHand.MAIN_HAND, facing, true);
/* 526 */       return true;
/*     */     } 
/* 528 */     return false;
/*     */   }
/*     */   
/*     */   private int getBlockSlot() {
/* 532 */     int slot = -1;
/* 533 */     for (int i = 0; i < 9; i++) {
/* 534 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 535 */       if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
/* 536 */         Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
/* 537 */         if (block instanceof net.minecraft.block.BlockObsidian) {
/* 538 */           slot = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 544 */     return slot;
/*     */   }
/*     */   
/*     */   private EntityPlayer getNearestTarget() {
/* 548 */     Stream<EntityPlayer> stream = mc.field_71441_e.field_73010_i.stream();
/* 549 */     return stream
/* 550 */       .filter(e -> (e != mc.field_71439_g && e != mc.func_175606_aa()))
/* 551 */       .filter(e -> !Thunderhack.friendManager.isFriend(e.func_70005_c_()))
/* 552 */       .filter(e -> (mc.field_71439_g.func_70032_d((Entity)e) < Math.max(((Float)this.placeRange.getValue()).floatValue() - 1.0F, 1.0F)))
/* 553 */       .filter(this::isValidBase)
/* 554 */       .min(Comparator.comparing(e -> Float.valueOf(mc.field_71439_g.func_70032_d((Entity)e))))
/* 555 */       .orElse(null);
/*     */   }
/*     */   
/*     */   private boolean isValidBase(EntityPlayer player) {
/* 559 */     BlockPos basePos = (new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v)).func_177977_b();
/*     */     
/* 561 */     Block baseBlock = mc.field_71441_e.func_180495_p(basePos).func_177230_c();
/*     */     
/* 563 */     return (!(baseBlock instanceof net.minecraft.block.BlockAir) && !(baseBlock instanceof net.minecraft.block.BlockLiquid));
/*     */   }
/*     */   
/*     */   public EnumFacing handlePlaceRotation(BlockPos pos) {
/* 567 */     if (pos == null || mc.field_71439_g == null) {
/* 568 */       return null;
/*     */     }
/* 570 */     EnumFacing facing = null;
/* 571 */     Vec3d placeVec = null;
/* 572 */     double[] placeRotation = null;
/*     */     
/* 574 */     double increment = 0.45D;
/* 575 */     double start = 0.05D;
/* 576 */     double end = 0.95D;
/*     */     
/* 578 */     Vec3d eyesPos = new Vec3d(mc.field_71439_g.field_70165_t, (mc.field_71439_g.func_174813_aQ()).field_72338_b + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/*     */     double xS;
/* 580 */     for (xS = start; xS <= end; xS += increment) {
/* 581 */       double yS; for (yS = start; yS <= end; yS += increment) {
/* 582 */         double zS; for (zS = start; zS <= end; zS += increment) {
/* 583 */           Vec3d posVec = (new Vec3d((Vec3i)pos)).func_72441_c(xS, yS, zS);
/*     */           
/* 585 */           double distToPosVec = eyesPos.func_72438_d(posVec);
/* 586 */           double diffX = posVec.field_72450_a - eyesPos.field_72450_a;
/* 587 */           double diffY = posVec.field_72448_b - eyesPos.field_72448_b;
/* 588 */           double diffZ = posVec.field_72449_c - eyesPos.field_72449_c;
/* 589 */           double diffXZ = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
/*     */           
/* 591 */           double[] tempPlaceRotation = { MathHelper.func_76142_g((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), MathHelper.func_76142_g((float)-Math.toDegrees(Math.atan2(diffY, diffXZ))) };
/*     */           
/* 593 */           float yawCos = MathHelper.func_76134_b((float)(-tempPlaceRotation[0] * 0.01745329238474369D - 3.1415927410125732D));
/* 594 */           float yawSin = MathHelper.func_76126_a((float)(-tempPlaceRotation[0] * 0.01745329238474369D - 3.1415927410125732D));
/* 595 */           float pitchCos = -MathHelper.func_76134_b((float)(-tempPlaceRotation[1] * 0.01745329238474369D));
/* 596 */           float pitchSin = MathHelper.func_76126_a((float)(-tempPlaceRotation[1] * 0.01745329238474369D));
/*     */           
/* 598 */           Vec3d rotationVec = new Vec3d((yawSin * pitchCos), pitchSin, (yawCos * pitchCos));
/* 599 */           Vec3d eyesRotationVec = eyesPos.func_72441_c(rotationVec.field_72450_a * distToPosVec, rotationVec.field_72448_b * distToPosVec, rotationVec.field_72449_c * distToPosVec);
/*     */           
/* 601 */           RayTraceResult rayTraceResult = mc.field_71441_e.func_147447_a(eyesPos, eyesRotationVec, false, true, false);
/* 602 */           if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK && rayTraceResult.func_178782_a().equals(pos)) {
/* 603 */             Vec3d currVec = posVec;
/* 604 */             double[] currRotation = tempPlaceRotation;
/*     */             
/* 606 */             if (((Boolean)this.strictdirection.getValue()).booleanValue()) {
/* 607 */               if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) || facing == null)) {
/* 608 */                 if (mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D).func_72438_d(currVec) < mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D).func_72438_d(placeVec)) {
/* 609 */                   placeVec = currVec;
/* 610 */                   placeRotation = currRotation;
/* 611 */                   if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 612 */                     facing = rayTraceResult.field_178784_b;
/*     */                   }
/*     */                 } 
/*     */               } else {
/* 616 */                 placeVec = currVec;
/* 617 */                 placeRotation = currRotation;
/* 618 */                 if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 619 */                   facing = rayTraceResult.field_178784_b;
/*     */                 }
/*     */               }
/*     */             
/* 623 */             } else if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) || facing == null)) {
/* 624 */               if (Math.hypot(((currRotation[0] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedYaw()) % 360.0D + 540.0D) % 360.0D - 180.0D, currRotation[1] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPitch()) < 
/* 625 */                 Math.hypot(((placeRotation[0] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedYaw()) % 360.0D + 540.0D) % 360.0D - 180.0D, placeRotation[1] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPitch())) {
/* 626 */                 placeVec = currVec;
/* 627 */                 placeRotation = currRotation;
/* 628 */                 if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 629 */                   facing = rayTraceResult.field_178784_b;
/*     */                 }
/*     */               } 
/*     */             } else {
/* 633 */               placeVec = currVec;
/* 634 */               placeRotation = currRotation;
/* 635 */               if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 636 */                 facing = rayTraceResult.field_178784_b;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 644 */     if (((Boolean)this.MStrict.getValue()).booleanValue()) {
/* 645 */       if (placeRotation != null && facing != null) {
/* 646 */         return facing;
/*     */       }
/* 648 */       for (xS = start; xS <= end; xS += increment) {
/* 649 */         double yS; for (yS = start; yS <= end; yS += increment) {
/* 650 */           double zS; for (zS = start; zS <= end; zS += increment) {
/* 651 */             Vec3d posVec = (new Vec3d((Vec3i)pos)).func_72441_c(xS, yS, zS);
/*     */             
/* 653 */             double distToPosVec = eyesPos.func_72438_d(posVec);
/* 654 */             double diffX = posVec.field_72450_a - eyesPos.field_72450_a;
/* 655 */             double diffY = posVec.field_72448_b - eyesPos.field_72448_b;
/* 656 */             double diffZ = posVec.field_72449_c - eyesPos.field_72449_c;
/* 657 */             double diffXZ = MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
/*     */             
/* 659 */             double[] tempPlaceRotation = { MathHelper.func_76142_g((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F), MathHelper.func_76142_g((float)-Math.toDegrees(Math.atan2(diffY, diffXZ))) };
/*     */             
/* 661 */             float yawCos = MathHelper.func_76134_b((float)(-tempPlaceRotation[0] * 0.01745329238474369D - 3.1415927410125732D));
/* 662 */             float yawSin = MathHelper.func_76126_a((float)(-tempPlaceRotation[0] * 0.01745329238474369D - 3.1415927410125732D));
/* 663 */             float pitchCos = -MathHelper.func_76134_b((float)(-tempPlaceRotation[1] * 0.01745329238474369D));
/* 664 */             float pitchSin = MathHelper.func_76126_a((float)(-tempPlaceRotation[1] * 0.01745329238474369D));
/*     */             
/* 666 */             Vec3d rotationVec = new Vec3d((yawSin * pitchCos), pitchSin, (yawCos * pitchCos));
/* 667 */             Vec3d eyesRotationVec = eyesPos.func_72441_c(rotationVec.field_72450_a * distToPosVec, rotationVec.field_72448_b * distToPosVec, rotationVec.field_72449_c * distToPosVec);
/*     */             
/* 669 */             RayTraceResult rayTraceResult = mc.field_71441_e.func_147447_a(eyesPos, eyesRotationVec, false, true, true);
/* 670 */             if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 671 */               Vec3d currVec = posVec;
/* 672 */               double[] currRotation = tempPlaceRotation;
/*     */               
/* 674 */               if (((Boolean)this.strictdirection.getValue()).booleanValue()) {
/* 675 */                 if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) || facing == null)) {
/* 676 */                   if (mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D).func_72438_d(currVec) < mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D).func_72438_d(placeVec)) {
/* 677 */                     placeVec = currVec;
/* 678 */                     placeRotation = currRotation;
/* 679 */                     if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 680 */                       facing = rayTraceResult.field_178784_b;
/*     */                     }
/*     */                   } 
/*     */                 } else {
/* 684 */                   placeVec = currVec;
/* 685 */                   placeRotation = currRotation;
/* 686 */                   if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 687 */                     facing = rayTraceResult.field_178784_b;
/*     */                   }
/*     */                 }
/*     */               
/* 691 */               } else if (placeVec != null && placeRotation != null && ((rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) || facing == null)) {
/* 692 */                 if (Math.hypot(((currRotation[0] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedYaw()) % 360.0D + 540.0D) % 360.0D - 180.0D, currRotation[1] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPitch()) < 
/* 693 */                   Math.hypot(((placeRotation[0] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedYaw()) % 360.0D + 540.0D) % 360.0D - 180.0D, placeRotation[1] - ((IEntityPlayerSP)mc.field_71439_g).getLastReportedPitch())) {
/* 694 */                   placeVec = currVec;
/* 695 */                   placeRotation = currRotation;
/* 696 */                   if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 697 */                     facing = rayTraceResult.field_178784_b;
/*     */                   }
/*     */                 } 
/*     */               } else {
/* 701 */                 placeVec = currVec;
/* 702 */                 placeRotation = currRotation;
/* 703 */                 if (rayTraceResult != null && rayTraceResult.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 704 */                   facing = rayTraceResult.field_178784_b;
/*     */                 }
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 714 */     } else if (facing != null) {
/* 715 */       return facing;
/*     */     } 
/*     */ 
/*     */     
/* 719 */     if (pos.func_177956_o() > mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e()) {
/* 720 */       return EnumFacing.DOWN;
/*     */     }
/* 722 */     return EnumFacing.UP;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 726 */     Packet, DoubleP, TripleP, Vanilla, StrictFast;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\CevBreaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */