/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class NGriefCleaner extends Module {
/* 13 */   public final Setting<Float> delay1 = register(new Setting("Delay", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/* 14 */   private final Timer timer = new Timer();
/* 15 */   public Setting<Boolean> openinv = register(new Setting("OpenInv", Boolean.valueOf(true)));
/*    */   public NGriefCleaner() {
/* 17 */     super("NGriefCleaner", "убирает топорики-и головы", Module.Category.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 22 */     long delay = (long)(((Float)this.delay1.getValue()).floatValue() * 50.0F);
/* 23 */     if (!(mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiInventory) && ((Boolean)this.openinv.getValue()).booleanValue())
/*    */       return; 
/* 25 */     if (this.timer.passedMs(delay)) {
/* 26 */       for (int i = 9; i < 45; i++) {
/* 27 */         if (mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75216_d()) {
/* 28 */           ItemStack is = mc.field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c();
/* 29 */           if (shouldDrop(is, i)) {
/* 30 */             drop(i);
/* 31 */             if (delay == 0L) {
/* 32 */               mc.field_71439_g.func_71053_j();
/*    */             }
/* 34 */             this.timer.reset();
/* 35 */             if (delay > 0L) {
/*    */               break;
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public void drop(int slot) {
/* 45 */     mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, slot, 1, ClickType.THROW, (EntityPlayer)mc.field_71439_g);
/*    */   }
/*    */   
/*    */   public boolean shouldDrop(ItemStack stack, int slot) {
/* 49 */     if (stack.func_77973_b() == Items.field_151144_bL) {
/* 50 */       return true;
/*    */     }
/* 52 */     if (stack.func_77973_b() == Items.field_151038_n) {
/* 53 */       return true;
/*    */     }
/* 55 */     if (stack.func_77973_b() == Items.field_151055_y) {
/* 56 */       return true;
/*    */     }
/* 58 */     if (stack.func_77973_b() == Items.field_151121_aF) {
/* 59 */       return true;
/*    */     }
/* 61 */     if (stack.func_77973_b() == Items.field_151033_d) {
/* 62 */       return true;
/*    */     }
/* 64 */     if (stack.func_77973_b() == Items.field_151078_bh) {
/* 65 */       return true;
/*    */     }
/* 67 */     if (stack.func_77973_b() == Items.field_151014_N) {
/* 68 */       return true;
/*    */     }
/* 70 */     if (stack.func_77973_b() == Items.field_151133_ar) {
/* 71 */       return true;
/*    */     }
/* 73 */     if (stack.func_77973_b() == Items.field_192397_db) {
/* 74 */       return true;
/*    */     }
/* 76 */     if (stack.func_77973_b() == Item.func_150899_d(6)) {
/* 77 */       return true;
/*    */     }
/* 79 */     if (stack.func_77973_b() == Item.func_150899_d(50)) {
/* 80 */       return true;
/*    */     }
/* 82 */     if (stack.func_77973_b() == Items.field_151053_p) {
/* 83 */       return (slot < 36);
/*    */     }
/*    */     
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\NGriefCleaner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */