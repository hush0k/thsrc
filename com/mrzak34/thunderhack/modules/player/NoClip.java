/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ import com.mrzak34.thunderhack.events.DestroyBlockEvent;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class NoClip extends Module {
/*     */   public Setting<Mode> mode;
/*     */   private final Setting<Integer> timeout;
/*     */   public Setting<Boolean> silent;
/*     */   
/*     */   public NoClip() {
/*  28 */     super("NoClip", "NoClip", Module.Category.PLAYER);
/*     */ 
/*     */     
/*  31 */     this.mode = register(new Setting("Mode", Mode.Default));
/*  32 */     this.timeout = register(new Setting("Timeout", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(10), v -> (this.mode.getValue() == Mode.CC)));
/*  33 */     this.silent = register(new Setting("Silent", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.SunriseBypass)));
/*     */     
/*  35 */     this.waitBreak = register(new Setting("WaitBreak", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.SunriseBypass)));
/*  36 */     this.afterBreak = register(new Setting("BreakTimeout", Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(20), v -> (this.mode.getValue() == Mode.SunriseBypass && ((Boolean)this.waitBreak.getValue()).booleanValue())));
/*     */   }
/*     */   public int itemIndex; public Setting<Boolean> waitBreak; private Setting<Integer> afterBreak; public int clipTimer;
/*     */   public boolean playerInsideBlock() {
/*  40 */     return (mc.field_71441_e.func_180495_p(mc.field_71439_g.func_180425_c()).func_177230_c() != Blocks.field_150350_a);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPreSync(EventSync e) {
/*  45 */     if (this.mode.getValue() == Mode.SunriseBypass && (mc.field_71439_g.field_70123_F || playerInsideBlock()) && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab()) {
/*  46 */       double[] dir = MovementUtil.forward(0.5D);
/*     */       
/*  48 */       BlockPos blockToBreak = null;
/*  49 */       if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/*  50 */         blockToBreak = new BlockPos(mc.field_71439_g.field_70165_t + dir[0], mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v + dir[1]);
/*  51 */       } else if (MovementUtil.isMoving()) {
/*  52 */         blockToBreak = new BlockPos(mc.field_71439_g.field_70165_t + dir[0], mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + dir[1]);
/*     */       } 
/*  54 */       if (blockToBreak == null)
/*  55 */         return;  int best_tool = getTool(blockToBreak);
/*  56 */       if (best_tool == -1)
/*  57 */         return;  this.itemIndex = best_tool;
/*     */       
/*  59 */       if (((Boolean)this.silent.getValue()).booleanValue()) {
/*  60 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(best_tool));
/*     */       } else {
/*  62 */         mc.field_71439_g.field_71071_by.field_70461_c = best_tool;
/*  63 */         InventoryUtil.syncItem();
/*     */       } 
/*     */       
/*  66 */       if (blockToBreak != null) {
/*  67 */         mc.field_71442_b.func_180512_c(blockToBreak, mc.field_71439_g.func_174811_aO());
/*  68 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */       } 
/*  70 */       if (((Boolean)this.silent.getValue()).booleanValue()) {
/*  71 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  78 */     if (this.clipTimer > 0) this.clipTimer--; 
/*  79 */     if (this.mode.getValue() == Mode.CC) {
/*  80 */       if (MovementUtil.isMoving()) {
/*  81 */         disable();
/*     */         return;
/*     */       } 
/*  84 */       if (mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72314_b(0.01D, 0.0D, 0.01D)).size() < 2) {
/*  85 */         mc.field_71439_g.func_70107_b(MathUtil.roundToClosest(mc.field_71439_g.field_70165_t, Math.floor(mc.field_71439_g.field_70165_t) + 0.301D, Math.floor(mc.field_71439_g.field_70165_t) + 0.699D), mc.field_71439_g.field_70163_u, MathUtil.roundToClosest(mc.field_71439_g.field_70161_v, Math.floor(mc.field_71439_g.field_70161_v) + 0.301D, Math.floor(mc.field_71439_g.field_70161_v) + 0.699D));
/*  86 */       } else if (mc.field_71439_g.field_70173_aa % ((Integer)this.timeout.getValue()).intValue() == 0) {
/*  87 */         mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t + MathHelper.func_151237_a(MathUtil.roundToClosest(mc.field_71439_g.field_70165_t, Math.floor(mc.field_71439_g.field_70165_t) + 0.241D, Math.floor(mc.field_71439_g.field_70165_t) + 0.759D) - mc.field_71439_g.field_70165_t, -0.03D, 0.03D), mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v + MathHelper.func_151237_a(MathUtil.roundToClosest(mc.field_71439_g.field_70161_v, Math.floor(mc.field_71439_g.field_70161_v) + 0.241D, Math.floor(mc.field_71439_g.field_70161_v) + 0.759D) - mc.field_71439_g.field_70161_v, -0.03D, 0.03D));
/*  88 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, true));
/*  89 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(MathUtil.roundToClosest(mc.field_71439_g.field_70165_t, Math.floor(mc.field_71439_g.field_70165_t) + 0.23D, Math.floor(mc.field_71439_g.field_70165_t) + 0.77D), mc.field_71439_g.field_70163_u, MathUtil.roundToClosest(mc.field_71439_g.field_70161_v, Math.floor(mc.field_71439_g.field_70161_v) + 0.23D, Math.floor(mc.field_71439_g.field_70161_v) + 0.77D), true));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canNoClip() {
/*  97 */     if (this.mode.getValue() == Mode.Default) return true; 
/*  98 */     if (!((Boolean)this.waitBreak.getValue()).booleanValue()) return true; 
/*  99 */     return (this.clipTimer != 0);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onDestroyBlock(DestroyBlockEvent e) {
/* 104 */     this.clipTimer = ((Integer)this.afterBreak.getValue()).intValue();
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 108 */     Default, SunriseBypass, CC;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getTool(BlockPos pos) {
/* 113 */     int index = -1;
/* 114 */     float CurrentFastest = 1.0F;
/* 115 */     for (int i = 0; i < 9; i++) {
/* 116 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 117 */       if (stack != ItemStack.field_190927_a) {
/* 118 */         mc.field_71439_g.field_71071_by.func_70301_a(i).func_77958_k();
/* 119 */         mc.field_71439_g.field_71071_by.func_70301_a(i).func_77952_i();
/*     */         
/* 121 */         float digSpeed = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack);
/* 122 */         float destroySpeed = stack.func_150997_a(mc.field_71441_e.func_180495_p(pos));
/*     */         
/* 124 */         if (mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockAir) return -1; 
/* 125 */         mc.field_71441_e.func_180495_p(pos).func_177230_c();
/* 126 */         if (digSpeed + destroySpeed > CurrentFastest) {
/* 127 */           CurrentFastest = digSpeed + destroySpeed;
/* 128 */           index = i;
/*     */         } 
/*     */       } 
/*     */     } 
/* 132 */     return index;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\NoClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */