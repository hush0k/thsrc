/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IMinecraft;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class FastUse extends Module {
/*    */   private final Setting<Integer> delay;
/*    */   public Setting<Boolean> blocks;
/*    */   
/*    */   public FastUse() {
/* 12 */     super("FastUse", "убирает задержку-испольования пкм", "FastUse", Module.Category.PLAYER);
/*    */ 
/*    */     
/* 15 */     this.delay = register(new Setting("Delay", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(4)));
/* 16 */     this.blocks = register(new Setting("Blocks", Boolean.valueOf(false)));
/* 17 */     this.crystals = register(new Setting("Crystals", Boolean.valueOf(false)));
/* 18 */     this.xp = register(new Setting("XP", Boolean.valueOf(false)));
/* 19 */     this.all = register(new Setting("All", Boolean.valueOf(true)));
/*    */   }
/*    */   public Setting<Boolean> crystals; public Setting<Boolean> xp; public Setting<Boolean> all;
/*    */   public void onUpdate() {
/* 23 */     if (check(mc.field_71439_g.func_184614_ca().func_77973_b()) && (
/* 24 */       (IMinecraft)mc).getRightClickDelayTimer() > ((Integer)this.delay.getValue()).intValue()) {
/* 25 */       ((IMinecraft)mc).setRightClickDelayTimer(((Integer)this.delay.getValue()).intValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean check(Item item) {
/* 30 */     return ((item instanceof net.minecraft.item.ItemBlock && ((Boolean)this.blocks.getValue()).booleanValue()) || (item == Items.field_185158_cP && ((Boolean)this.crystals
/* 31 */       .getValue()).booleanValue()) || (item == Items.field_151062_by && ((Boolean)this.xp
/* 32 */       .getValue()).booleanValue()) || ((Boolean)this.all
/* 33 */       .getValue()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\FastUse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */