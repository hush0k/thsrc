/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoSoup extends Module {
/* 14 */   public Setting<Float> thealth = register(new Setting("TriggerHealth", Float.valueOf(7.0F), Float.valueOf(1.0F), Float.valueOf(20.0F)));
/*    */   
/*    */   public AutoSoup() {
/* 17 */     super("AutoSoup", "Автосуп для-Мигосмси", Module.Category.MISC);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onUpdateWalkingPlayer(EventSync event) {
/* 22 */     if (mc.field_71439_g.func_110143_aJ() <= ((Float)this.thealth.getValue()).floatValue()) {
/* 23 */       int soupslot = InventoryUtil.findSoupAtHotbar();
/* 24 */       int currentslot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 25 */       if (soupslot != -1) {
/* 26 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(soupslot));
/* 27 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 28 */         InventoryUtil.switchToHotbarSlot(currentslot, true);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoSoup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */