/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ 
/*    */ public class AutoAmericano
/*    */   extends Module
/*    */ {
/* 17 */   public Timer timer = new Timer();
/*    */   private final Setting<Mode> mainMode;
/*    */   
/*    */   public AutoAmericano() {
/* 21 */     super("AutoAmericano", "AutoAmericano", Module.Category.FUNNYGAME);
/*    */ 
/*    */     
/* 24 */     this.mainMode = register(new Setting("Version", Mode.New));
/* 25 */   } public enum Mode { Old, New; }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 29 */     if (this.timer.passedMs(200L) && InventoryUtil.getAmericanoAtHotbar((this.mainMode.getValue() == Mode.Old)) != -1 && !mc.field_71439_g.func_70644_a(MobEffects.field_76422_e)) {
/* 30 */       int hotbarslot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 31 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(InventoryUtil.getAmericanoAtHotbar((this.mainMode.getValue() == Mode.Old))));
/* 32 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 33 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(hotbarslot));
/* 34 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AutoAmericano.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */