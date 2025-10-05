/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.GameZaloopEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.PlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.manager.EventManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.movement.GuiMove;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.CrystalUtils;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoTotem extends Module {
/*  47 */   public Setting<ModeEn> mode = register(new Setting("Mode", ModeEn.SemiStrict));
/*  48 */   public Setting<Boolean> totem = register(new Setting("Totem", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  49 */   public Setting<Boolean> gapple = register(new Setting("SwordGap", Boolean.valueOf(false), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  50 */   public Setting<Boolean> rightClick = register(new Setting("RightClickGap", Boolean.valueOf(false), v -> (((Boolean)this.gapple.getValue()).booleanValue() && this.mode.getValue() == ModeEn.SemiStrict)));
/*  51 */   public Setting<Boolean> crystal = register(new Setting("Crystal", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  52 */   public Setting<Boolean> StopSprint = register(new Setting("StopSprint", Boolean.valueOf(false), v -> (this.mode.getValue() != ModeEn.Strict)));
/*  53 */   public Setting<Float> delay = register(new Setting("Delay", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  54 */   public Setting<Boolean> hotbarTotem = register(new Setting("HotbarTotem", Boolean.valueOf(false), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  55 */   public Setting<Float> totemHealthThreshold = register(new Setting("TotemHealth", Float.valueOf(5.0F), Float.valueOf(0.0F), Float.valueOf(36.0F), v -> (this.mode.getValue() != ModeEn.Strict)));
/*  56 */   public Setting<CrystalCheck> crystalCheck = register(new Setting("CrystalCheck", CrystalCheck.DAMAGE, v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  57 */   public Setting<Float> crystalRange = register(new Setting("CrystalRange", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(15.0F), v -> (this.crystalCheck.getValue() != CrystalCheck.NONE && this.mode.getValue() == ModeEn.SemiStrict)));
/*  58 */   public Setting<Boolean> extraSafe = register(new Setting("ExtraCheck", Boolean.valueOf(false), v -> (this.crystalCheck.getValue() != CrystalCheck.NONE && this.mode.getValue() == ModeEn.SemiStrict)));
/*  59 */   public Setting<Boolean> fallCheck = register(new Setting("FallCheck", Boolean.valueOf(true), v -> (this.mode.getValue() != ModeEn.Strict)));
/*  60 */   public Setting<Boolean> totemOnElytra = register(new Setting("TotemOnElytra", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  61 */   public Setting<Boolean> clearAfter = register(new Setting("SwapBack", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  62 */   public Setting<Boolean> hard = register(new Setting("AlwaysDefault", Boolean.valueOf(false), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  63 */   public Setting<Boolean> notFromHotbar = register(new Setting("NotFromHotbar", Boolean.valueOf(false), v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*  64 */   public Setting<Default> defaultItem = register(new Setting("DefaultItem", Default.TOTEM, v -> (this.mode.getValue() == ModeEn.SemiStrict)));
/*     */ 
/*     */ 
/*     */   
/*  68 */   public Setting<Boolean> absorptionHP = register(new Setting("absorptionHP", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Matrix)));
/*  69 */   public Setting<Boolean> checkTNT = register(new Setting("CheckTNT", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Matrix)));
/*  70 */   public Setting<Boolean> checkObsidian = register(new Setting("CheckObsidian", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Matrix)));
/*     */ 
/*     */ 
/*     */   
/*  74 */   public Setting<Boolean> deathVerbose = register(new Setting("Death Verbose", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Strict)));
/*  75 */   public Setting<OffHand> offhand = register(new Setting("OffHand", OffHand.Totem, v -> (this.mode.getValue() == ModeEn.Strict)));
/*  76 */   public Setting<Float> healthF = register(new Setting("Health", Float.valueOf(16.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.mode.getValue() == ModeEn.Strict)));
/*  77 */   public Setting<Boolean> lethal = register(new Setting("Lethal", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Strict)));
/*  78 */   public Setting<Integer> GappleSlot = register(new Setting("GAppleSlot", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(8), v -> (this.mode.getValue() == ModeEn.Strict)));
/*  79 */   public Setting<Boolean> offhandoverride = register(new Setting("OffHandOverride", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Strict)));
/*  80 */   public Setting<Boolean> crapple = register(new Setting("Crapple", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Strict)));
/*  81 */   public Setting<Boolean> funnyGame = register(new Setting("FunnyGameBypass", Boolean.valueOf(true), v -> (this.mode.getValue() == ModeEn.Strict)));
/*     */ 
/*     */   
/*  84 */   public static long packet_latency_timer = 0L;
/*  85 */   public static int last_packet_time = 0;
/*  86 */   private final Queue<Integer> clickQueue = new LinkedList<>();
/*  87 */   private final Timer stop_spam = new Timer();
/*  88 */   private final Timer timer = new Timer();
/*  89 */   private int swapBack = -1;
/*     */ 
/*     */   
/*     */   public AutoTotem() {
/*  93 */     super("AutoTotem", "AutoTotem", Module.Category.COMBAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getItemSlot(Item item, boolean gappleCheck) {
/*  98 */     for (int i = 0; i < 36; i++) {
/*  99 */       ItemStack itemStackInSlot = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 100 */       if (!gappleCheck) {
/* 101 */         if (item == itemStackInSlot.func_77973_b()) {
/* 102 */           if (i < 9) i += 36; 
/* 103 */           return i;
/*     */         }
/*     */       
/* 106 */       } else if (item == itemStackInSlot.func_77973_b() && (!item.func_77613_e(itemStackInSlot).equals(EnumRarity.RARE) || noGapples())) {
/* 107 */         if (i < 9) i += 36; 
/* 108 */         return i;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean noGapples() {
/* 117 */     for (int i = 0; i < 36; i++) {
/* 118 */       ItemStack itemStackInSlot = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 119 */       if (Items.field_151153_ao == itemStackInSlot.func_77973_b() && !Items.field_151153_ao.func_77613_e(itemStackInSlot).equals(EnumRarity.RARE)) {
/* 120 */         return false;
/*     */       }
/*     */     } 
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<BlockPos> getSphere(BlockPos blockPos, float n, int n2, boolean b, boolean b2, int n3) {
/* 128 */     ArrayList<BlockPos> list = new ArrayList<>();
/* 129 */     int x = blockPos.func_177958_n();
/* 130 */     int y = blockPos.func_177956_o();
/* 131 */     int z = blockPos.func_177952_p();
/* 132 */     for (int n4 = x - (int)n; n4 <= x + n; n4++) {
/* 133 */       for (int n5 = z - (int)n; n5 <= z + n; ) {
/* 134 */         int n6 = b2 ? (y - (int)n) : y; for (;; n5++) { if (n6 < (b2 ? (y + n) : (y + n2))) {
/* 135 */             double n7 = ((x - n4) * (x - n4) + (z - n5) * (z - n5) + (b2 ? ((y - n6) * (y - n6)) : 0));
/* 136 */             if (n7 < (n * n) && (!b || n7 >= ((n - 1.0F) * (n - 1.0F))))
/* 137 */               list.add(new BlockPos(n4, n6 + n3, n5));  n6++; continue;
/*     */           }  }
/*     */       
/*     */       } 
/*     */     } 
/* 142 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getDistanceOfEntityToBlock(Entity entity, BlockPos blockPos) {
/* 149 */     return getDistance(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p());
/*     */   }
/*     */   
/*     */   public static double getDistance(double n, double n2, double n3, double n4, double n5, double n6) {
/* 153 */     double n7 = n - n4;
/* 154 */     double n8 = n2 - n5;
/* 155 */     double n9 = n3 - n6;
/* 156 */     return MathHelper.func_76133_a(n7 * n7 + n8 * n8 + n9 * n9);
/*     */   }
/*     */   
/*     */   public static int findNearestCurrentItem() {
/* 160 */     int currentItem = mc.field_71439_g.field_71071_by.field_70461_c;
/* 161 */     if (currentItem == 8) {
/* 162 */       return 7;
/*     */     }
/* 164 */     if (currentItem == 0) {
/* 165 */       return 1;
/*     */     }
/* 167 */     return currentItem - 1;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerUpdate(PlayerUpdateEvent e) {
/* 172 */     if (this.mode.getValue() != ModeEn.Matrix)
/* 173 */       return;  int totemSlot = getItemSlot(Items.field_190929_cY, false);
/* 174 */     if (totemSlot < 9 && totemSlot != -1) {
/* 175 */       totemSlot += 36;
/*     */     }
/* 177 */     float hp = mc.field_71439_g.func_110143_aJ();
/* 178 */     if (((Boolean)this.absorptionHP.getValue()).booleanValue()) {
/* 179 */       hp = EntityUtil.getHealth((Entity)mc.field_71439_g);
/*     */     }
/* 181 */     int prevCurrentItem = mc.field_71439_g.field_71071_by.field_70461_c;
/* 182 */     int currentItem = findNearestCurrentItem();
/* 183 */     boolean totemCheck = (((Float)this.totemHealthThreshold.getValue()).floatValue() >= hp || crystalCheck() || (((Boolean)this.fallCheck.getValue()).booleanValue() && (EntityUtil.getHealth((Entity)mc.field_71439_g) - (mc.field_71439_g.field_70143_R - 3.0F) / 2.0F + 3.5F) < 0.5D && !mc.field_71439_g.func_184613_cA()) || checkTNT() || checkObsidian());
/* 184 */     boolean totemInHand = (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY);
/* 185 */     if (totemCheck) {
/* 186 */       if (totemSlot >= 0 && !totemInHand) {
/* 187 */         mc.field_71442_b.func_187098_a(0, totemSlot, currentItem, ClickType.SWAP, (EntityPlayer)mc.field_71439_g);
/* 188 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(currentItem));
/* 189 */         mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
/* 190 */         ItemStack itemstack = mc.field_71439_g.func_184586_b(EnumHand.OFF_HAND);
/* 191 */         mc.field_71439_g.func_184611_a(EnumHand.OFF_HAND, mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND));
/* 192 */         mc.field_71439_g.func_184611_a(EnumHand.MAIN_HAND, itemstack);
/* 193 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.field_177992_a, EnumFacing.DOWN));
/* 194 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(prevCurrentItem));
/* 195 */         mc.field_71439_g.field_71071_by.field_70461_c = prevCurrentItem;
/* 196 */         mc.field_71442_b.func_187098_a(0, totemSlot, currentItem, ClickType.SWAP, (EntityPlayer)mc.field_71439_g);
/* 197 */         if (this.swapBack == -1)
/* 198 */           this.swapBack = totemSlot; 
/*     */         return;
/*     */       } 
/* 201 */       if (totemInHand) {
/*     */         return;
/*     */       }
/*     */     } 
/* 205 */     if (this.swapBack >= 0) {
/* 206 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(currentItem));
/* 207 */       mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
/* 208 */       ItemStack itemstack = mc.field_71439_g.func_184586_b(EnumHand.OFF_HAND);
/* 209 */       mc.field_71439_g.func_184611_a(EnumHand.OFF_HAND, mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND));
/* 210 */       mc.field_71439_g.func_184611_a(EnumHand.MAIN_HAND, itemstack);
/* 211 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.field_177992_a, EnumFacing.DOWN));
/* 212 */       mc.field_71442_b.func_187098_a(0, this.swapBack, currentItem, ClickType.SWAP, (EntityPlayer)mc.field_71439_g);
/* 213 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.field_177992_a, EnumFacing.DOWN));
/* 214 */       itemstack = mc.field_71439_g.func_184586_b(EnumHand.OFF_HAND);
/* 215 */       mc.field_71439_g.func_184611_a(EnumHand.OFF_HAND, mc.field_71439_g.func_184586_b(EnumHand.MAIN_HAND));
/* 216 */       mc.field_71439_g.func_184611_a(EnumHand.MAIN_HAND, itemstack);
/* 217 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(prevCurrentItem));
/* 218 */       mc.field_71439_g.field_71071_by.field_70461_c = prevCurrentItem;
/* 219 */       this.swapBack = -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLoop(GameZaloopEvent event) {
/* 239 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/* 240 */       return;  if (this.mode.getValue() != ModeEn.SemiStrict)
/*     */       return; 
/* 242 */     if (!(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer)) {
/* 243 */       if (!this.clickQueue.isEmpty()) {
/* 244 */         if (!this.timer.passedMs((long)(((Float)this.delay.getValue()).floatValue() * 100.0F)))
/* 245 */           return;  int slot = ((Integer)this.clickQueue.poll()).intValue();
/*     */         try {
/* 247 */           this.timer.reset();
/* 248 */           if (EventManager.serversprint && ((Boolean)this.StopSprint.getValue()).booleanValue())
/* 249 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING)); 
/* 250 */           mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 251 */         } catch (Exception e) {
/* 252 */           e.printStackTrace();
/*     */         } 
/*     */       } else {
/*     */         
/* 256 */         if (!mc.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
/* 257 */           for (int index = 44; index >= 9; index--) {
/* 258 */             if (mc.field_71439_g.field_71069_bz.func_75139_a(index).func_75211_c().func_190926_b()) {
/* 259 */               if (EventManager.serversprint && ((Boolean)this.StopSprint.getValue()).booleanValue())
/* 260 */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING)); 
/* 261 */               mc.field_71442_b.func_187098_a(0, index, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         }
/* 267 */         if (((Boolean)this.totem.getValue()).booleanValue()) {
/* 268 */           if (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() <= ((Float)this.totemHealthThreshold.getValue()).floatValue() || (((Boolean)this.totemOnElytra
/* 269 */             .getValue()).booleanValue() && mc.field_71439_g.func_184613_cA()) || (((Boolean)this.fallCheck
/* 270 */             .getValue()).booleanValue() && (EntityUtil.getHealth((Entity)mc.field_71439_g) - (mc.field_71439_g.field_70143_R - 3.0F) / 2.0F + 3.5F) < 0.5D && !mc.field_71439_g.func_184613_cA())) {
/*     */             
/* 272 */             putItemIntoOffhand(Items.field_190929_cY); return;
/*     */           } 
/* 274 */           if (this.crystalCheck.getValue() == CrystalCheck.RANGE) {
/*     */ 
/*     */ 
/*     */             
/* 278 */             EntityEnderCrystal crystal = mc.field_71441_e.field_72996_f.stream().filter(e -> (e instanceof EntityEnderCrystal && mc.field_71439_g.func_70032_d(e) <= ((Float)this.crystalRange.getValue()).floatValue())).min(Comparator.comparing(c -> Float.valueOf(mc.field_71439_g.func_70032_d(c)))).orElse(null);
/*     */             
/* 280 */             if (crystal != null) {
/* 281 */               putItemIntoOffhand(Items.field_190929_cY);
/*     */               return;
/*     */             } 
/* 284 */           } else if (this.crystalCheck.getValue() == CrystalCheck.DAMAGE) {
/* 285 */             float damage = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 290 */             List<Entity> crystalsInRange = (List<Entity>)mc.field_71441_e.field_72996_f.stream().filter(e -> e instanceof EntityEnderCrystal).filter(e -> (mc.field_71439_g.func_70032_d(e) <= ((Float)this.crystalRange.getValue()).floatValue())).collect(Collectors.toList());
/*     */             
/* 292 */             for (Entity entity : crystalsInRange) {
/* 293 */               damage += CrystalUtils.calculateDamage((EntityEnderCrystal)entity, (Entity)mc.field_71439_g);
/*     */             }
/*     */             
/* 296 */             if (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - damage <= ((Float)this.totemHealthThreshold.getValue()).floatValue()) {
/* 297 */               putItemIntoOffhand(Items.field_190929_cY);
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/* 302 */           if (((Boolean)this.extraSafe.getValue()).booleanValue() && 
/* 303 */             crystalCheck()) {
/* 304 */             putItemIntoOffhand(Items.field_190929_cY);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */         
/* 310 */         if (((Boolean)this.gapple.getValue()).booleanValue() && isSword(mc.field_71439_g.func_184614_ca().func_77973_b())) {
/* 311 */           if (((Boolean)this.rightClick.getValue()).booleanValue() && !mc.field_71474_y.field_74313_G.func_151470_d()) {
/* 312 */             if (((Boolean)this.clearAfter.getValue()).booleanValue()) {
/* 313 */               putItemIntoOffhand(((Default)this.defaultItem.getValue()).item);
/*     */             }
/*     */             return;
/*     */           } 
/* 317 */           putItemIntoOffhand(Items.field_151153_ao);
/*     */           
/*     */           return;
/*     */         } 
/* 321 */         if (((Boolean)this.crystal.getValue()).booleanValue()) {
/* 322 */           if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).isEnabled()) {
/* 323 */             putItemIntoOffhand(Items.field_185158_cP); return;
/*     */           } 
/* 325 */           if (((Boolean)this.clearAfter.getValue()).booleanValue()) {
/* 326 */             putItemIntoOffhand(((Default)this.defaultItem.getValue()).item);
/*     */             return;
/*     */           } 
/*     */         } 
/* 330 */         if (((Boolean)this.hard.getValue()).booleanValue()) {
/* 331 */           if ((((Default)this.defaultItem.getValue()).item == Items.field_185159_cQ && mc.field_71439_g.func_184811_cZ().func_185141_a(Items.field_185159_cQ)) || (((Default)this.defaultItem.getValue()).item == Items.field_185159_cQ && findItemSlot(Items.field_185159_cQ) == -1 && mc.field_71439_g.func_184592_cb().func_77973_b() != Items.field_185159_cQ)) {
/* 332 */             putItemIntoOffhand(Items.field_151153_ao);
/*     */           } else {
/* 334 */             putItemIntoOffhand(((Default)this.defaultItem.getValue()).item);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isSword(Item item) {
/* 342 */     return (item == Items.field_151048_u || item == Items.field_151040_l || item == Items.field_151010_B || item == Items.field_151052_q || item == Items.field_151041_m);
/*     */   }
/*     */   
/*     */   private int findItemSlot(Item item) {
/* 346 */     int itemSlot = -1;
/* 347 */     for (int i = ((Boolean)this.notFromHotbar.getValue()).booleanValue() ? 9 : 0; i < 36; i++) {
/*     */       
/* 349 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*     */       
/* 351 */       if (stack != null && stack.func_77973_b() == item) {
/* 352 */         itemSlot = i;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 357 */     return itemSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void putItemIntoOffhand(Item item) {
/* 367 */     if (mc.field_71439_g.func_184592_cb().func_77973_b() == item)
/* 368 */       return;  int slot = findItemSlot(item);
/* 369 */     if (((Boolean)this.hotbarTotem.getValue()).booleanValue() && item == Items.field_190929_cY) {
/* 370 */       for (int i = 0; i < 9; i++) {
/* 371 */         ItemStack stack = (ItemStack)mc.field_71439_g.field_71071_by.field_70462_a.get(i);
/* 372 */         if (stack.func_77973_b() == Items.field_190929_cY) {
/* 373 */           if (mc.field_71439_g.field_71071_by.field_70461_c != i) {
/* 374 */             mc.field_71439_g.field_71071_by.field_70461_c = i;
/*     */           }
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/* 380 */     if (slot != -1) {
/* 381 */       if (((Float)this.delay.getValue()).floatValue() > 0.0F) {
/* 382 */         if (this.timer.passedMs((long)(((Float)this.delay.getValue()).floatValue() * 100.0F))) {
/* 383 */           if (EventManager.serversprint && ((Boolean)this.StopSprint.getValue()).booleanValue())
/* 384 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING)); 
/* 385 */           mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 386 */           this.timer.reset();
/*     */         } else {
/* 388 */           this.clickQueue.add(Integer.valueOf((slot < 9) ? (slot + 36) : slot));
/*     */         } 
/*     */         
/* 391 */         this.clickQueue.add(Integer.valueOf(45));
/* 392 */         this.clickQueue.add(Integer.valueOf((slot < 9) ? (slot + 36) : slot));
/*     */       } else {
/* 394 */         this.timer.reset();
/* 395 */         if (EventManager.serversprint && ((Boolean)this.StopSprint.getValue()).booleanValue())
/* 396 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING)); 
/* 397 */         mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 398 */         mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 399 */         mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, (slot < 9) ? (slot + 36) : slot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean crystalCheck() {
/* 405 */     float cumDmg = 0.0F;
/* 406 */     ArrayList<Float> damageValues = new ArrayList<>();
/* 407 */     damageValues.add(Float.valueOf(calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(1, 0, 0))));
/* 408 */     damageValues.add(Float.valueOf(calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(-1, 0, 0))));
/* 409 */     damageValues.add(Float.valueOf(calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(0, 0, 1))));
/* 410 */     damageValues.add(Float.valueOf(calculateDamageAABB(mc.field_71439_g.func_180425_c().func_177982_a(0, 0, -1))));
/* 411 */     damageValues.add(Float.valueOf(calculateDamageAABB(mc.field_71439_g.func_180425_c())));
/* 412 */     for (Iterator<Float> iterator = damageValues.iterator(); iterator.hasNext(); ) { float damage = ((Float)iterator.next()).floatValue();
/* 413 */       cumDmg += damage;
/* 414 */       if (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - damage <= ((Float)this.totemHealthThreshold.getValue()).floatValue()) {
/* 415 */         return true;
/*     */       } }
/*     */     
/* 418 */     return (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - cumDmg <= ((Float)this.totemHealthThreshold.getValue()).floatValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float calculateDamageAABB(BlockPos pos) {
/* 424 */     List<Entity> crystalsInAABB = (List<Entity>)mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
/* 425 */     float totalDamage = 0.0F;
/* 426 */     for (Entity crystal : crystalsInAABB) {
/* 427 */       totalDamage += CrystalUtils.calculateDamage(crystal.field_70165_t, crystal.field_70163_u, crystal.field_70161_v, (Entity)mc.field_71439_g);
/*     */     }
/* 429 */     return totalDamage;
/*     */   }
/*     */   
/*     */   private boolean checkTNT() {
/* 433 */     if (!((Boolean)this.checkTNT.getValue()).booleanValue()) {
/* 434 */       return false;
/*     */     }
/* 436 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 437 */       if (entity instanceof net.minecraft.entity.item.EntityTNTPrimed && mc.field_71439_g.func_70068_e(entity) <= 25.0D) {
/* 438 */         return true;
/*     */       }
/* 440 */       if (entity instanceof net.minecraft.entity.item.EntityMinecartTNT && mc.field_71439_g.func_70068_e(entity) <= 25.0D) {
/* 441 */         return true;
/*     */       }
/*     */     } 
/* 444 */     return false;
/*     */   }
/*     */   
/*     */   private boolean IsValidBlockPos(BlockPos pos) {
/* 448 */     IBlockState state = mc.field_71441_e.func_180495_p(pos);
/* 449 */     return state.func_177230_c() instanceof net.minecraft.block.BlockObsidian;
/*     */   }
/*     */   
/*     */   private boolean checkObsidian() {
/* 453 */     if (!((Boolean)this.checkObsidian.getValue()).booleanValue()) {
/* 454 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 458 */     BlockPos pos = getSphere(new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v)), 5.0F, 6, false, true, 0).stream().filter(this::IsValidBlockPos).min(Comparator.comparing(blockPos -> Double.valueOf(getDistanceOfEntityToBlock((Entity)mc.field_71439_g, blockPos)))).orElse(null);
/* 459 */     return (pos != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostMotion(EventPostSync e) {
/* 465 */     if (this.mode.getValue() == ModeEn.Strict) {
/* 466 */       if (mc.field_71462_r == null) {
/* 467 */         int itemSlot = getItemSlot();
/* 468 */         if (itemSlot != -1 && 
/* 469 */           !isOffhand(mc.field_71439_g.field_71069_bz.func_75139_a(itemSlot).func_75211_c()) && this.timer.passedMs(200L)) {
/* 470 */           this.timer.reset();
/* 471 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/* 472 */           mc.field_71442_b.func_187098_a(0, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 473 */           mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 474 */           if (mc.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
/* 475 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(mc.field_71439_g.field_71069_bz.field_75152_c));
/* 476 */             mc.field_71442_b.func_78765_e();
/*     */             
/*     */             return;
/*     */           } 
/* 480 */           if (mc.field_71439_g.field_71071_by.func_70445_o().func_77973_b() == Items.field_151153_ao) {
/* 481 */             mc.field_71442_b.func_187098_a(0, ((Integer)this.GappleSlot.getValue()).intValue() + 36, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */           }
/* 483 */           if (mc.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
/* 484 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(mc.field_71439_g.field_71069_bz.field_75152_c));
/* 485 */             mc.field_71442_b.func_78765_e();
/*     */             return;
/*     */           } 
/* 488 */           int returnSlot = -1;
/* 489 */           for (int i = 9; i < 45; i++) {
/* 490 */             if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_190926_b()) {
/* 491 */               returnSlot = i;
/*     */               break;
/*     */             } 
/*     */           } 
/* 495 */           if (returnSlot != -1) {
/* 496 */             mc.field_71442_b.func_187098_a(0, returnSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */           }
/* 498 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(mc.field_71439_g.field_71069_bz.field_75152_c));
/* 499 */           mc.field_71442_b.func_78765_e();
/*     */         } 
/*     */       } 
/*     */       
/* 503 */       if (((Boolean)this.deathVerbose.getValue()).booleanValue() && 
/* 504 */         mc.field_71462_r instanceof net.minecraft.client.gui.GuiGameOver && this.stop_spam.passedMs(3000L)) {
/* 505 */         Command.sendMessage(getVerbose());
/* 506 */         this.stop_spam.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.ReceivePost event) {
/* 514 */     if (fullNullCheck())
/* 515 */       return;  if (this.mode.getValue() == ModeEn.Strict) {
/* 516 */       if (event.getPacket() instanceof SPacketEntityStatus && ((SPacketEntityStatus)event.getPacket()).func_149160_c() == 35) {
/* 517 */         Entity entity = ((SPacketEntityStatus)event.getPacket()).func_149161_a((World)mc.field_71441_e);
/*     */         
/* 519 */         if (entity != null && entity.equals(mc.field_71439_g) && this.timer.passedMs(200L)) {
/* 520 */           this.timer.reset();
/* 521 */           int itemSlot = getItemSlot();
/* 522 */           if (itemSlot != -1) {
/* 523 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/* 524 */             mc.field_71442_b.func_187098_a(0, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 525 */             mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 526 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(mc.field_71439_g.field_71069_bz.field_75152_c));
/* 527 */             mc.field_71442_b.func_78765_e();
/*     */           } 
/*     */         } 
/*     */       } 
/* 531 */       last_packet_time = (int)(System.currentTimeMillis() - packet_latency_timer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getItemSlot() {
/*     */     Item item;
/* 537 */     if (this.offhand.getValue() == OffHand.Totem) {
/* 538 */       item = Items.field_190929_cY;
/* 539 */     } else if (this.offhand.getValue() == OffHand.Crystal) {
/* 540 */       item = Items.field_185158_cP;
/*     */     } else {
/* 542 */       item = Items.field_151153_ao;
/*     */     } 
/* 544 */     if (((Boolean)this.offhandoverride.getValue()).booleanValue() && 
/* 545 */       isSword(mc.field_71439_g.func_184614_ca().func_77973_b()) && mc.field_71474_y.field_74313_G.func_151470_d()) {
/* 546 */       item = Items.field_151153_ao;
/*     */     }
/*     */     
/* 549 */     if (((Boolean)this.lethal.getValue()).booleanValue()) {
/* 550 */       if ((EntityUtil.getHealth((Entity)mc.field_71439_g) - (mc.field_71439_g.field_70143_R - 3.0F) / 2.0F + 3.5F) < 0.5D && !mc.field_71439_g.func_191953_am()) {
/* 551 */         item = Items.field_190929_cY;
/*     */       }
/* 553 */       if (mc.field_71439_g.func_184613_cA()) {
/* 554 */         item = Items.field_190929_cY;
/*     */       }
/* 556 */       for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 557 */         if (entity == null || entity.field_70128_L) {
/*     */           continue;
/*     */         }
/* 560 */         double crystalRange = mc.field_71439_g.func_70032_d(entity);
/* 561 */         if (crystalRange > 6.0D) {
/*     */           continue;
/*     */         }
/* 564 */         if (entity instanceof EntityEnderCrystal && (
/* 565 */           EntityUtil.getHealth((Entity)mc.field_71439_g) - CrystalUtils.calculateDamage((EntityEnderCrystal)entity, (Entity)mc.field_71439_g)) < 0.5D) {
/* 566 */           item = Items.field_190929_cY;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 573 */     if (EntityUtil.getHealth((Entity)mc.field_71439_g) <= ((Float)this.healthF.getValue()).floatValue()) {
/* 574 */       item = Items.field_190929_cY;
/*     */     }
/*     */     
/* 577 */     int itemSlot = -1;
/* 578 */     int gappleSlot = -1;
/* 579 */     int crappleSlot = -1;
/*     */     
/* 581 */     for (int i = 9; i < 45; i++) {
/* 582 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c().func_77973_b().equals(item)) {
/* 583 */         if (item.equals(Items.field_151153_ao)) {
/* 584 */           ItemStack stack = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 585 */           if (stack.func_77962_s()) {
/* 586 */             gappleSlot = i;
/*     */           } else {
/* 588 */             crappleSlot = i;
/*     */           } 
/*     */         } else {
/* 591 */           itemSlot = i;
/*     */           
/*     */           break;
/*     */         } 
/*     */       }
/*     */     } 
/* 597 */     if (item.equals(Items.field_151153_ao)) {
/* 598 */       if (((Boolean)this.crapple.getValue()).booleanValue()) {
/* 599 */         if (mc.field_71439_g.func_70644_a(MobEffects.field_76444_x)) {
/* 600 */           if (crappleSlot != -1) {
/* 601 */             itemSlot = crappleSlot;
/* 602 */           } else if (gappleSlot != -1) {
/* 603 */             itemSlot = gappleSlot;
/*     */           } 
/* 605 */         } else if (gappleSlot != -1) {
/* 606 */           itemSlot = gappleSlot;
/*     */         }
/*     */       
/* 609 */       } else if (gappleSlot != -1) {
/* 610 */         itemSlot = gappleSlot;
/* 611 */       } else if (crappleSlot != -1) {
/* 612 */         itemSlot = crappleSlot;
/*     */       } 
/*     */     }
/*     */     
/* 616 */     return itemSlot;
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onPacketSend(PacketEvent.Send e) {
/* 621 */     GuiMove.pause = true;
/* 622 */     if (e.getPacket() instanceof net.minecraft.network.play.client.CPacketClickWindow && this.mode.getValue() == ModeEn.Strict && ((Boolean)this.funnyGame.getValue()).booleanValue() && 
/* 623 */       mc.field_71439_g.field_70122_E && MovementUtil.isMoving() && mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, 0.0656D, 0.0D)).isEmpty()) {
/* 624 */       if (mc.field_71439_g.func_70051_ag()) {
/* 625 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */       }
/* 627 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0656D, mc.field_71439_g.field_70161_v, false));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOffhand(ItemStack in) {
/* 633 */     ItemStack offhandItem = mc.field_71439_g.func_184592_cb();
/* 634 */     if (in.func_77973_b().equals(Items.field_151153_ao)) {
/* 635 */       if (offhandItem.func_77973_b().equals(in.func_77973_b())) {
/* 636 */         boolean gapple = in.func_77962_s();
/* 637 */         return (gapple == offhandItem.func_77962_s());
/*     */       } 
/*     */     } else {
/* 640 */       return offhandItem.func_77973_b().equals(in.func_77973_b());
/*     */     } 
/* 642 */     return false;
/*     */   }
/*     */   
/*     */   public String getVerbose() {
/* 646 */     String in = "Death due to possible reasons: ";
/* 647 */     String server_latency = "server processing latency of " + Thunderhack.serverManager.getPing() + " ms, ";
/* 648 */     String packet_latency = "packet process latency of " + MathUtil.round2((last_packet_time / 1000.0F)) + " ms, ";
/*     */     
/* 650 */     if (InventoryUtil.getCount(Items.field_190929_cY) == 0)
/* 651 */       return ChatFormatting.RED + in + server_latency + packet_latency + "(do not worry about rare spikes of 5-10 ms), no totems"; 
/* 652 */     if (Thunderhack.serverManager.getPing() > 300)
/* 653 */       return ChatFormatting.RED + in + server_latency + packet_latency + "(high ping) , totem fail"; 
/* 654 */     if (MathUtil.round2((last_packet_time / 1000.0F)) > 20.0F) {
/* 655 */       return ChatFormatting.RED + in + server_latency + packet_latency + "(high load on the packet listener! turn off unnecessary modules) , totem fail";
/*     */     }
/* 657 */     return ChatFormatting.RED + in + server_latency + packet_latency + "(do not worry about rare spikes of 5-10 ms), totem fail";
/*     */   }
/*     */   
/*     */   private enum ModeEn
/*     */   {
/* 662 */     Strict,
/* 663 */     SemiStrict,
/* 664 */     Matrix;
/*     */   }
/*     */   
/*     */   private enum OffHand {
/* 668 */     Totem,
/* 669 */     Crystal,
/* 670 */     GApple;
/*     */   }
/*     */   
/*     */   private enum CrystalCheck {
/* 674 */     NONE,
/* 675 */     DAMAGE,
/* 676 */     RANGE;
/*     */   }
/*     */   
/*     */   private enum Default {
/* 680 */     TOTEM((String)Items.field_190929_cY),
/* 681 */     CRYSTAL((String)Items.field_185158_cP),
/* 682 */     GAPPLE((String)Items.field_151153_ao),
/* 683 */     AIR((String)Items.field_190931_a),
/* 684 */     SHIELD((String)Items.field_185159_cQ);
/*     */     
/*     */     public Item item;
/*     */     
/*     */     Default(Item item) {
/* 689 */       this.item = item;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AutoTotem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */