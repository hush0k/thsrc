/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class XCarry
/*    */   extends Module {
/*    */   public XCarry() {
/* 10 */     super("XCarry", "позволяет хранить-предметы в мышке", Module.Category.PLAYER);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 15 */     if (fullNullCheck())
/* 16 */       return;  if (e.getPacket() instanceof net.minecraft.network.play.client.CPacketCloseWindow)
/* 17 */       e.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\XCarry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */