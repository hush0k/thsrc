/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.setting.SubBind;
/*    */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoCappRegear
/*    */   extends Module
/*    */ {
/* 16 */   public Timer timer = new Timer();
/* 17 */   public Setting<SubBind> aboba = register(new Setting("BuyBind", new SubBind(24)));
/* 18 */   public Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(100), Integer.valueOf(1), Integer.valueOf(500)));
/*    */   
/*    */   boolean open_shop = false;
/*    */   
/*    */   public AutoCappRegear() {
/* 23 */     super("CappRegear", "регирит каппучино-по бинду", Module.Category.FUNNYGAME);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 31 */     if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat) {
/*    */       return;
/*    */     }
/*    */     
/* 35 */     if (PlayerUtils.isKeyDown(((SubBind)this.aboba.getValue()).getKey())) {
/* 36 */       if (!this.open_shop) {
/* 37 */         mc.field_71439_g.func_71165_d("/drinks");
/* 38 */         this.open_shop = true;
/*    */       } 
/* 40 */       if (this.open_shop && this.timer.passedMs(((Integer)this.delay.getValue()).intValue())) {
/* 41 */         mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71070_bA.field_75152_c, 1, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 42 */         mc.field_71442_b.func_187098_a(0, 1, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 43 */         this.timer.reset();
/*    */       } 
/*    */     } else {
/* 46 */       this.open_shop = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AutoCappRegear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */