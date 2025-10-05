/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ISPacketPlayerPosLook;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoVoid
/*    */   extends Module {
/*    */   boolean aboveVoid = true;
/* 16 */   private final Timer voidTimer = new Timer();
/*    */   public NoVoid() {
/* 18 */     super("NoVoid", "рубербендит если ты-упал в пустоту", Module.Category.MOVEMENT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 23 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/* 24 */       return;  if (PlayerUtils.isPlayerAboveVoid() && mc.field_71439_g.field_70163_u <= 1.0D) {
/* 25 */       if (this.aboveVoid && this.voidTimer.passedMs(1000L)) {
/* 26 */         this.aboveVoid = false;
/* 27 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.1D, mc.field_71439_g.field_70161_v, false));
/*    */       } 
/*    */     } else {
/* 30 */       this.aboveVoid = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onReceive(PacketEvent.Receive event) {
/* 36 */     if (event.getPacket() instanceof SPacketPlayerPosLook && 
/* 37 */       !(mc.field_71462_r instanceof net.minecraft.client.gui.GuiDownloadTerrain)) {
/* 38 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
/* 39 */       ((ISPacketPlayerPosLook)packet).setYaw(mc.field_71439_g.field_70177_z);
/* 40 */       ((ISPacketPlayerPosLook)packet).setPitch(mc.field_71439_g.field_70125_A);
/* 41 */       packet.func_179834_f().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
/* 42 */       packet.func_179834_f().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\NoVoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */