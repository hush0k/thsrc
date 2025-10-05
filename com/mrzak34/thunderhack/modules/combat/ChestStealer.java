/*    */ package com.mrzak34.thunderhack.modules.combat;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerChest;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class ChestStealer extends Module {
/* 13 */   public Setting<Integer> delayed = register(new Setting("Delay", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(1000)));
/* 14 */   Timer timer = new Timer();
/*    */   public ChestStealer() {
/* 16 */     super("ChestStealer", "Стилит предметы-из сундука", Module.Category.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 21 */     if (Util.mc.field_71439_g.field_71070_bA != null && 
/* 22 */       Util.mc.field_71439_g.field_71070_bA instanceof ContainerChest) {
/* 23 */       ContainerChest container = (ContainerChest)Util.mc.field_71439_g.field_71070_bA;
/* 24 */       for (int i = 0; i < container.field_75151_b.size(); i++) {
/* 25 */         if (container.func_85151_d().func_70301_a(i).func_77973_b() != Item.func_150899_d(0) && this.timer.passedMs(((Integer)this.delayed.getValue()).intValue())) {
/* 26 */           mc.field_71442_b.func_187098_a(container.field_75152_c, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)Util.mc.field_71439_g);
/* 27 */           this.timer.reset();
/*    */         
/*    */         }
/* 30 */         else if (empty((Container)container)) {
/* 31 */           Util.mc.field_71439_g.func_71053_j();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean empty(Container container) {
/* 38 */     boolean voll = true;
/* 39 */     int slotAmount = (container.field_75151_b.size() == 90) ? 54 : 27;
/* 40 */     for (int i = 0; i < slotAmount; i++) {
/* 41 */       if (container.func_75139_a(i).func_75216_d())
/* 42 */         voll = false; 
/*    */     } 
/* 44 */     return voll;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\ChestStealer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */