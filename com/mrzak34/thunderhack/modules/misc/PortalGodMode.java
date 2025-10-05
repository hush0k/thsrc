/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class PortalGodMode
/*    */   extends Module
/*    */ {
/*    */   public PortalGodMode() {
/* 11 */     super("PortalGodMode", "бессмертие пока ты в -портале", Module.Category.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 17 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketConfirmTeleport)
/* 18 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\PortalGodMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */