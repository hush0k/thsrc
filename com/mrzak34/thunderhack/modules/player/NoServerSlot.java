/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoServerSlot extends Module {
/*    */   public NoServerSlot() {
/* 11 */     super("NoServerSlot", "не дает серверу свапать слоты", Module.Category.PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 17 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketHeldItemChange) {
/* 18 */       event.setCanceled(true);
/* 19 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\NoServerSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */