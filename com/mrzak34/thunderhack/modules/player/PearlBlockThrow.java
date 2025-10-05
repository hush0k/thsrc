/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketPlayerTryUseItemOnBlock;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class PearlBlockThrow extends Module {
/*    */   public PearlBlockThrow() {
/* 13 */     super("PearlBlockThrow", "PearlBlockThrow", Module.Category.PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPackerSend(PacketEvent.Send event) {
/* 19 */     if (fullNullCheck())
/*    */       return; 
/* 21 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151079_bi && 
/* 22 */       event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
/* 23 */       CPacketPlayerTryUseItemOnBlock pac = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
/* 24 */       ((ICPacketPlayerTryUseItemOnBlock)pac).setHand(EnumHand.OFF_HAND);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\PearlBlockThrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */