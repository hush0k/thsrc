/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IMinecraft;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class LegitScaff
/*    */   extends Module
/*    */ {
/* 17 */   private final Setting<Integer> blue = register(new Setting("CPS timer", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(6)));
/* 18 */   public Setting<Boolean> shift = register(new Setting("shift", Boolean.valueOf(true)));
/* 19 */   public Setting<Boolean> only = register(new Setting("OnlyBlocks", Boolean.valueOf(true)));
/* 20 */   public Setting<Boolean> fast = register(new Setting("RealyDamnFast", Boolean.valueOf(true)));
/* 21 */   public Setting<Boolean> lt = register(new Setting("LegitTower", Boolean.valueOf(true)));
/* 22 */   public Timer timr = new Timer();
/*    */   
/*    */   public LegitScaff() {
/* 25 */     super("LegitScaff", "можно и легитнее", Module.Category.FUNNYGAME);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 30 */     if (Util.mc.field_71439_g != null && Util.mc.field_71441_e != null) {
/* 31 */       if (((Boolean)this.fast.getValue()).booleanValue()) {
/* 32 */         ((IMinecraft)mc).setRightClickDelayTimer(((Integer)this.blue.getValue()).intValue());
/*    */       }
/* 34 */       if (((Boolean)this.lt.getValue()).booleanValue() && 
/* 35 */         mc.field_71439_g.field_71158_b.field_78901_c) {
/*    */         int i;
/* 37 */         for (i = (int)mc.field_71439_g.field_70125_A; i < 83; i++) {
/* 38 */           mc.field_71439_g.field_70125_A = i;
/*    */         }
/* 40 */         for (i = (int)mc.field_71439_g.field_70125_A; i > 83; i--) {
/* 41 */           mc.field_71439_g.field_70125_A = i;
/*    */         }
/*    */         
/* 44 */         KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), true);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 49 */       if (((Boolean)this.shift.getValue()).booleanValue()) {
/* 50 */         ItemStack i = Util.mc.field_71439_g.func_184614_ca();
/* 51 */         BlockPos bP = new BlockPos(Util.mc.field_71439_g.field_70165_t, Util.mc.field_71439_g.field_70163_u - 1.0D, Util.mc.field_71439_g.field_70161_v);
/* 52 */         if (i != null && (
/* 53 */           !((Boolean)this.only.getValue()).booleanValue() || i.func_77973_b() instanceof net.minecraft.item.ItemBlock)) {
/* 54 */           KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
/* 55 */           if (mc.field_71441_e.func_180495_p(bP).func_177230_c() == Blocks.field_150350_a) {
/*    */             
/* 57 */             KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), true);
/*    */             
/* 59 */             if (this.timr.passedMs(50L)) {
/* 60 */               KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), true);
/* 61 */               this.timr.reset();
/*    */             } else {
/* 63 */               KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), false);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 76 */     KeyBinding.func_74510_a(mc.field_71474_y.field_74311_E.func_151463_i(), false);
/* 77 */     KeyBinding.func_74510_a(mc.field_71474_y.field_74313_G.func_151463_i(), false);
/*    */     
/* 79 */     super.onDisable();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\LegitScaff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */