/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AirStuck
/*    */   extends Module
/*    */ {
/*    */   public AirStuck() {
/* 11 */     super("AirStuck", "AirStuck", Module.Category.MOVEMENT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 16 */     mc.field_71439_g.field_70159_w = 0.0D;
/* 17 */     mc.field_71439_g.field_70181_x = 0.0D;
/* 18 */     mc.field_71439_g.field_70179_y = 0.0D;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onSendPacket(PacketEvent.Receive event) {
/* 23 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer) {
/* 24 */       event.setCanceled(true);
/*    */     }
/* 26 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Position) {
/* 27 */       event.setCanceled(true);
/*    */     }
/* 29 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation) {
/* 30 */       event.setCanceled(true);
/*    */     }
/* 32 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketEntityAction)
/* 33 */       event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\AirStuck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */