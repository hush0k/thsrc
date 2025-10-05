/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.init.Enchantments;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class AutoTool
/*    */   extends Module
/*    */ {
/* 19 */   public Setting<Boolean> swapBack = register(new Setting("SwapBack", Boolean.valueOf(true)));
/* 20 */   public Setting<Boolean> saveItem = register(new Setting("SaveItem", Boolean.valueOf(true)));
/* 21 */   public Setting<Boolean> silent = register(new Setting("Silent", Boolean.valueOf(false)));
/* 22 */   public Setting<Boolean> echestSilk = register(new Setting("EchestSilk", Boolean.valueOf(true)));
/*    */   public int itemIndex;
/*    */   private boolean swap;
/*    */   private long swapDelay;
/* 26 */   private final List<Integer> lastItem = new ArrayList<>();
/*    */   public AutoTool() {
/* 28 */     super("AutoTool", "Автоматом свапается на-лучший инструмент", Module.Category.PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 33 */     if (mc.field_71476_x == null)
/* 34 */       return;  if (mc.field_71476_x.func_178782_a() == null)
/* 35 */       return;  if (getTool(mc.field_71476_x.func_178782_a()) != -1 && ((IKeyBinding)mc.field_71474_y.field_74312_F).isPressed()) {
/* 36 */       this.lastItem.add(Integer.valueOf(mc.field_71439_g.field_71071_by.field_70461_c));
/*    */       
/* 38 */       if (((Boolean)this.silent.getValue()).booleanValue()) {
/* 39 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(getTool(mc.field_71476_x.func_178782_a())));
/*    */       } else {
/* 41 */         mc.field_71439_g.field_71071_by.field_70461_c = getTool(mc.field_71476_x.func_178782_a());
/*    */       } 
/* 43 */       this.itemIndex = getTool(mc.field_71476_x.func_178782_a());
/* 44 */       this.swap = true;
/*    */       
/* 46 */       this.swapDelay = System.currentTimeMillis();
/*    */     }
/* 48 */     else if (this.swap && !this.lastItem.isEmpty() && System.currentTimeMillis() >= this.swapDelay + 300L && ((Boolean)this.swapBack.getValue()).booleanValue()) {
/*    */       
/* 50 */       if (((Boolean)this.silent.getValue()).booleanValue()) {
/* 51 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(((Integer)this.lastItem.get(0)).intValue()));
/*    */       } else {
/* 53 */         mc.field_71439_g.field_71071_by.field_70461_c = ((Integer)this.lastItem.get(0)).intValue();
/*    */       } 
/* 55 */       this.itemIndex = ((Integer)this.lastItem.get(0)).intValue();
/* 56 */       this.lastItem.clear();
/* 57 */       this.swap = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private int getTool(BlockPos pos) {
/* 63 */     int index = -1;
/* 64 */     float CurrentFastest = 1.0F;
/* 65 */     for (int i = 0; i < 9; i++) {
/* 66 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 67 */       if (stack != ItemStack.field_190927_a && (
/* 68 */         mc.field_71439_g.field_71071_by.func_70301_a(i).func_77958_k() - mc.field_71439_g.field_71071_by.func_70301_a(i).func_77952_i() > 10 || !((Boolean)this.saveItem.getValue()).booleanValue())) {
/*    */ 
/*    */         
/* 71 */         float digSpeed = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack);
/* 72 */         float destroySpeed = stack.func_150997_a(mc.field_71441_e.func_180495_p(pos));
/*    */         
/* 74 */         if (mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockAir) return -1; 
/* 75 */         if (mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockEnderChest && ((Boolean)this.echestSilk.getValue()).booleanValue()) {
/* 76 */           if (EnchantmentHelper.func_77506_a(Enchantments.field_185306_r, stack) > 0 && digSpeed + destroySpeed > CurrentFastest) {
/* 77 */             CurrentFastest = digSpeed + destroySpeed;
/* 78 */             index = i;
/*    */           } 
/* 80 */         } else if (digSpeed + destroySpeed > CurrentFastest) {
/* 81 */           CurrentFastest = digSpeed + destroySpeed;
/* 82 */           index = i;
/*    */         } 
/*    */       } 
/*    */     } 
/* 86 */     return index;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\AutoTool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */