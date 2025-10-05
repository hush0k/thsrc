/*    */ package com.mrzak34.thunderhack.modules.combat;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.minecraft.network.play.client.CPacketUseEntity;
/*    */ 
/*    */ public class WTap extends Module {
/*    */   public WTap() {
/* 11 */     super("WTap", "Прожимает W-после удара", Module.Category.COMBAT);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onSendPacket(PacketEvent event) {
/* 17 */     if (event.getPacket() instanceof CPacketUseEntity) {
/* 18 */       CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
/* 19 */       if (packet.func_149565_c() == CPacketUseEntity.Action.ATTACK) {
/* 20 */         mc.field_71439_g.func_70031_b(false);
/* 21 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/* 22 */         mc.field_71439_g.func_70031_b(true);
/* 23 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\WTap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */