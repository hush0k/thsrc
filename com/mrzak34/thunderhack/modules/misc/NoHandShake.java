/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketCustomPayload;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoHandShake
/*    */   extends Module
/*    */ {
/*    */   public NoHandShake() {
/* 15 */     super("NoHandshake", "не отправляет модлист-серверу", Module.Category.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 21 */     if (event.getPacket() instanceof net.minecraftforge.fml.common.network.internal.FMLProxyPacket && !mc.func_71356_B())
/* 22 */       event.setCanceled(true); 
/*    */     CPacketCustomPayload packet;
/* 24 */     if (event.getPacket() instanceof CPacketCustomPayload && (packet = (CPacketCustomPayload)event.getPacket()).func_149559_c().equals("MC|Brand"))
/* 25 */       ((ICPacketCustomPayload)packet).setData((new PacketBuffer(Unpooled.buffer())).func_180714_a("vanilla")); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\NoHandShake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */