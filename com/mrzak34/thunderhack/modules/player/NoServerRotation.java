/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketPlayerPosLook;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*    */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoServerRotation extends Module {
/*    */   public NoServerRotation() {
/* 12 */     super("NoServerRotation", "Тебе не вертит бошку", Module.Category.PLAYER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent(priority = EventPriority.LOWEST)
/*    */   public void onReceivePacket(PacketEvent.Receive event) {
/* 19 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 22 */     if (event.getPacket() instanceof SPacketPlayerPosLook) {
/* 23 */       SPacketPlayerPosLook sp = (SPacketPlayerPosLook)event.getPacket();
/* 24 */       ((ISPacketPlayerPosLook)sp).setYaw(mc.field_71439_g.field_70177_z);
/* 25 */       ((ISPacketPlayerPosLook)sp).setPitch(mc.field_71439_g.field_70125_A);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\NoServerRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */