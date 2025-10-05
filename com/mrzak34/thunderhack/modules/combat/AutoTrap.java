/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.movement.PacketFly;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.setting.SubBind;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.CrystalUtils;
/*     */ import com.mrzak34.thunderhack.util.InteractionUtil;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.util.Comparator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class AutoTrap
/*     */   extends Module
/*     */ {
/*  40 */   public static ConcurrentHashMap<BlockPos, Long> shiftedBlocks = new ConcurrentHashMap<>();
/*  41 */   public final Setting<ColorSetting> Color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  42 */   private final Setting<Float> placeRange = register(new Setting("TargetRange", Float.valueOf(4.5F), Float.valueOf(1.0F), Float.valueOf(16.0F)));
/*  43 */   private final Setting<Boolean> top = register(new Setting("Top", Boolean.valueOf(true)));
/*  44 */   private final Setting<Boolean> piston = register(new Setting("Piston", Boolean.valueOf(false)));
/*  45 */   private final Setting<SubBind> self = register(new Setting("Self", new SubBind(0)));
/*  46 */   Timer renderTimer = new Timer();
/*  47 */   private final Setting<Integer> actionShift = register(new Setting("ActionShift", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(8)));
/*  48 */   private final Setting<Integer> actionInterval = register(new Setting("ActionInterval", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10)));
/*  49 */   private final Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  50 */   private final Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
/*  51 */   private final Setting<Boolean> toggelable = register(new Setting("DisableWhenDone", Boolean.valueOf(false)));
/*     */   private int itemSlot;
/*     */   private BlockPos renderPos;
/*  54 */   private int tickCounter = 0;
/*  55 */   private BlockPos playerPos = null;
/*     */   private InteractionUtil.Placement placement;
/*     */   private InteractionUtil.Placement lastPlacement;
/*  58 */   private final Timer lastPlacementTimer = new Timer();
/*     */   
/*     */   public AutoTrap() {
/*  61 */     super("AutoTrap", "Трапит ньюфагов", Module.Category.COMBAT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  67 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*  68 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     this.renderPos = null;
/*  73 */     this.playerPos = null;
/*  74 */     this.placement = null;
/*  75 */     this.lastPlacement = null;
/*  76 */     this.tickCounter = ((Integer)this.actionInterval.getValue()).intValue();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPre(EventSync event) {
/*  81 */     if (this.placement != null) {
/*  82 */       this.lastPlacement = this.placement;
/*  83 */       this.lastPlacementTimer.reset();
/*     */     } 
/*  85 */     this.placement = null;
/*  86 */     this.playerPos = null;
/*     */     
/*  88 */     int ping = CrystalUtils.ping();
/*     */     
/*  90 */     shiftedBlocks.forEach((pos, time) -> {
/*     */           if (System.currentTimeMillis() - time.longValue() > (ping + 100)) {
/*     */             shiftedBlocks.remove(pos);
/*     */           }
/*     */         });
/*     */     
/*  96 */     if (event.isCanceled() || !InteractionUtil.canPlaceNormally(((Boolean)this.rotate.getValue()).booleanValue()))
/*     */       return; 
/*  98 */     if (((Boolean)this.strict.getValue()).booleanValue() && (!mc.field_71439_g.field_70122_E || !mc.field_71439_g.field_70124_G))
/*     */       return; 
/* 100 */     if (((PacketFly)Thunderhack.moduleManager.getModuleByClass(PacketFly.class)).isEnabled())
/*     */       return; 
/* 102 */     if (this.tickCounter < ((Integer)this.actionInterval.getValue()).intValue()) {
/* 103 */       this.tickCounter++;
/*     */     }
/*     */     
/* 106 */     int slot = getBlockSlot();
/* 107 */     if (slot == -1) {
/* 108 */       Command.sendMessage("No Obby Found!");
/* 109 */       toggle();
/*     */       return;
/*     */     } 
/* 112 */     this.itemSlot = slot;
/*     */     
/* 114 */     EntityPlayer nearestPlayer = getNearestTarget();
/*     */     
/* 116 */     if (nearestPlayer == null)
/*     */       return; 
/* 118 */     if (this.tickCounter < ((Integer)this.actionInterval.getValue()).intValue()) {
/* 119 */       if (this.lastPlacement != null && !this.lastPlacementTimer.passedMs(650L)) {
/* 120 */         mc.field_71439_g.field_70125_A = this.lastPlacement.getPitch();
/* 121 */         mc.field_71439_g.field_70177_z = this.lastPlacement.getYaw();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     this.playerPos = new BlockPos(nearestPlayer.field_70165_t, nearestPlayer.field_70163_u, nearestPlayer.field_70161_v);
/*     */     
/* 128 */     BlockPos firstPos = getNextPos(this.playerPos);
/*     */     
/* 130 */     if (firstPos != null) {
/* 131 */       this.placement = InteractionUtil.preparePlacement(firstPos, ((Boolean)this.rotate.getValue()).booleanValue(), event);
/* 132 */       if (this.placement != null) {
/* 133 */         shiftedBlocks.put(firstPos, Long.valueOf(System.currentTimeMillis()));
/* 134 */         this.tickCounter = 0;
/* 135 */         this.renderPos = firstPos;
/* 136 */         this.renderTimer.reset();
/*     */       }
/*     */     
/* 139 */     } else if (((Boolean)this.toggelable.getValue()).booleanValue()) {
/* 140 */       toggle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerPost(EventPostSync event) {
/* 148 */     if (this.placement != null && this.playerPos != null && this.itemSlot != -1) {
/* 149 */       boolean changeItem = (mc.field_71439_g.field_71071_by.field_70461_c != this.itemSlot);
/* 150 */       int startingItem = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */       
/* 152 */       if (changeItem) {
/* 153 */         mc.field_71439_g.field_71071_by.field_70461_c = this.itemSlot;
/* 154 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.itemSlot));
/*     */       } 
/*     */       
/* 157 */       boolean isSprinting = mc.field_71439_g.func_70051_ag();
/* 158 */       boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(this.placement.getNeighbour());
/*     */       
/* 160 */       if (isSprinting) {
/* 161 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */       }
/*     */       
/* 164 */       if (shouldSneak) {
/* 165 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */       }
/*     */       
/* 168 */       InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
/*     */       
/* 170 */       int extraBlocks = 0;
/* 171 */       while (extraBlocks < ((Integer)this.actionShift.getValue()).intValue() - 1) {
/* 172 */         BlockPos nextPos = getNextPos(this.playerPos);
/* 173 */         if (nextPos != null) {
/* 174 */           InteractionUtil.Placement nextPlacement = InteractionUtil.preparePlacement(nextPos, ((Boolean)this.rotate.getValue()).booleanValue(), true);
/* 175 */           if (nextPlacement != null) {
/* 176 */             this.placement = nextPlacement;
/* 177 */             shiftedBlocks.put(nextPos, Long.valueOf(System.currentTimeMillis()));
/* 178 */             InteractionUtil.placeBlock(this.placement, EnumHand.MAIN_HAND, true);
/* 179 */             this.renderPos = nextPos;
/* 180 */             this.renderTimer.reset();
/* 181 */             extraBlocks++;
/*     */             continue;
/*     */           } 
/*     */           break;
/*     */         } 
/* 186 */         if (((Boolean)this.toggelable.getValue()).booleanValue()) {
/* 187 */           toggle();
/* 188 */           if (changeItem) {
/* 189 */             mc.field_71439_g.field_71071_by.field_70461_c = startingItem;
/* 190 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(startingItem));
/*     */           } 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 198 */       if (shouldSneak) {
/* 199 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */       }
/*     */       
/* 202 */       if (isSprinting) {
/* 203 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */       }
/*     */       
/* 206 */       if (changeItem) {
/* 207 */         mc.field_71439_g.field_71071_by.field_70461_c = startingItem;
/* 208 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(startingItem));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canPlaceBlock(BlockPos pos, boolean strictDirection) {
/* 214 */     return (InteractionUtil.canPlaceBlock(pos, strictDirection) && !shiftedBlocks.containsKey(pos));
/*     */   }
/*     */   
/*     */   private boolean pistonCheck(BlockPos facePos, EnumFacing facing) {
/* 218 */     PistonAura pistonAura = (PistonAura)Thunderhack.moduleManager.getModuleByClass(PistonAura.class);
/* 219 */     if (pistonAura.facePos != null) {
/* 220 */       return !pistonAura.faceOffset.equals(facing);
/*     */     }
/* 222 */     pistonAura.evaluateTarget(facePos);
/* 223 */     if (pistonAura.facePos != null) {
/* 224 */       if (pistonAura.faceOffset.equals(facing)) {
/* 225 */         resetPA(pistonAura);
/* 226 */         return false;
/*     */       } 
/* 228 */       resetPA(pistonAura);
/*     */     } 
/*     */     
/* 231 */     return true;
/*     */   }
/*     */   
/*     */   private void resetPA(PistonAura pistonAura) {
/* 235 */     pistonAura.facePos = null;
/* 236 */     pistonAura.faceOffset = null;
/* 237 */     pistonAura.pistonOffset = null;
/* 238 */     pistonAura.pistonNeighbour = null;
/* 239 */     pistonAura.crystalPos = null;
/*     */   }
/*     */   
/*     */   private BlockPos getNextPos(BlockPos playerPos) {
/* 243 */     for (EnumFacing enumFacing : EnumFacing.field_176754_o) {
/* 244 */       BlockPos furthestBlock = null;
/* 245 */       double furthestDistance = 0.0D;
/* 246 */       if (canPlaceBlock(playerPos.func_177972_a(enumFacing).func_177977_b(), true)) {
/* 247 */         BlockPos tempBlock = playerPos.func_177972_a(enumFacing).func_177977_b();
/* 248 */         double tempDistance = mc.field_71439_g.func_70011_f(tempBlock.func_177958_n() + 0.5D, tempBlock.func_177956_o() + 0.5D, tempBlock.func_177952_p() + 0.5D);
/* 249 */         if (tempDistance >= furthestDistance) {
/* 250 */           furthestBlock = tempBlock;
/* 251 */           furthestDistance = tempDistance;
/*     */         } 
/*     */       } 
/* 254 */       if (furthestBlock != null) return furthestBlock;
/*     */     
/*     */     } 
/* 257 */     for (EnumFacing enumFacing : EnumFacing.field_176754_o) {
/* 258 */       BlockPos furthestBlock = null;
/* 259 */       double furthestDistance = 0.0D;
/* 260 */       if (canPlaceBlock(playerPos.func_177972_a(enumFacing), false)) {
/* 261 */         BlockPos tempBlock = playerPos.func_177972_a(enumFacing);
/* 262 */         double tempDistance = mc.field_71439_g.func_70011_f(tempBlock.func_177958_n() + 0.5D, tempBlock.func_177956_o() + 0.5D, tempBlock.func_177952_p() + 0.5D);
/* 263 */         if (tempDistance >= furthestDistance) {
/* 264 */           furthestBlock = tempBlock;
/* 265 */           furthestDistance = tempDistance;
/*     */         } 
/*     */       } 
/* 268 */       if (furthestBlock != null) return furthestBlock;
/*     */     
/*     */     } 
/* 271 */     for (EnumFacing enumFacing : EnumFacing.field_176754_o) {
/* 272 */       BlockPos furthestBlock = null;
/* 273 */       double furthestDistance = 0.0D;
/* 274 */       if (canPlaceBlock(playerPos.func_177984_a().func_177972_a(enumFacing), false) && (
/* 275 */         !((Boolean)this.piston.getValue()).booleanValue() || pistonCheck(playerPos.func_177984_a(), enumFacing))) {
/* 276 */         BlockPos tempBlock = playerPos.func_177984_a().func_177972_a(enumFacing);
/* 277 */         double tempDistance = mc.field_71439_g.func_70011_f(tempBlock.func_177958_n() + 0.5D, tempBlock.func_177956_o() + 0.5D, tempBlock.func_177952_p() + 0.5D);
/* 278 */         if (tempDistance >= furthestDistance) {
/* 279 */           furthestBlock = tempBlock;
/* 280 */           furthestDistance = tempDistance;
/*     */         } 
/*     */       } 
/*     */       
/* 284 */       if (furthestBlock != null) return furthestBlock;
/*     */     
/*     */     } 
/* 287 */     if (((Boolean)this.top.getValue()).booleanValue()) {
/* 288 */       Block baseBlock = mc.field_71441_e.func_180495_p(playerPos.func_177984_a().func_177984_a()).func_177230_c();
/* 289 */       if (baseBlock instanceof net.minecraft.block.BlockAir || baseBlock instanceof net.minecraft.block.BlockLiquid) {
/* 290 */         if (canPlaceBlock(playerPos.func_177984_a().func_177984_a(), false)) {
/* 291 */           return playerPos.func_177984_a().func_177984_a();
/*     */         }
/* 293 */         BlockPos offsetPos = playerPos.func_177984_a().func_177984_a().func_177972_a(EnumFacing.func_176731_b(MathHelper.func_76128_c((mc.field_71439_g.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3));
/* 294 */         if (canPlaceBlock(offsetPos, false)) {
/* 295 */           return offsetPos;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 301 */     return null;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/* 306 */     if (mc.field_71441_e == null || mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/* 310 */     if (this.renderPos != null && !this.renderTimer.passedMs(500L)) {
/* 311 */       RenderUtil.drawBlockOutline(this.renderPos, ((ColorSetting)this.Color.getValue()).getColorObject(), 0.3F, true, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getBlockSlot() {
/* 316 */     int slot = -1;
/* 317 */     for (int i = 0; i < 9; i++) {
/* 318 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 319 */       if (stack != ItemStack.field_190927_a && stack.func_77973_b() instanceof ItemBlock) {
/* 320 */         Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
/* 321 */         if (block instanceof net.minecraft.block.BlockObsidian) {
/* 322 */           slot = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 328 */     return slot;
/*     */   }
/*     */   
/*     */   private EntityPlayer getNearestTarget() {
/* 332 */     Stream<EntityPlayer> stream = mc.field_71441_e.field_73010_i.stream();
/* 333 */     return (EntityPlayer)stream
/* 334 */       .filter(e -> (e != mc.field_71439_g && e != mc.func_175606_aa()))
/* 335 */       .filter(e -> !Thunderhack.friendManager.isFriend(e.func_70005_c_()))
/* 336 */       .filter(e -> (mc.field_71439_g.func_70032_d((Entity)e) < Math.max(((Float)this.placeRange.getValue()).floatValue() - 1.0F, 1.0F)))
/* 337 */       .filter(this::isValidBase)
/* 338 */       .min(Comparator.comparing(e -> Float.valueOf(mc.field_71439_g.func_70032_d((Entity)e))))
/* 339 */       .orElse(PlayerUtils.isKeyDown(((SubBind)this.self.getValue()).getKey()) ? mc.field_71439_g : null);
/*     */   }
/*     */   
/*     */   private boolean isValidBase(EntityPlayer player) {
/* 343 */     BlockPos basePos = (new BlockPos(player.field_70165_t, player.field_70163_u, player.field_70161_v)).func_177977_b();
/*     */     
/* 345 */     Block baseBlock = mc.field_71441_e.func_180495_p(basePos).func_177230_c();
/*     */     
/* 347 */     return (!(baseBlock instanceof net.minecraft.block.BlockAir) && !(baseBlock instanceof net.minecraft.block.BlockLiquid));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AutoTrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */