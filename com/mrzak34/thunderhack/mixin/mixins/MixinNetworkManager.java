/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoTotem;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({NetworkManager.class})
/*    */ public class MixinNetworkManager
/*    */ {
/*    */   @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onSendPacketPre(Packet<?> packet, CallbackInfo info) {
/* 26 */     PacketEvent.Send event = new PacketEvent.Send(packet);
/* 27 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 28 */     if (event.isCanceled()) {
/* 29 */       info.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void onSendPacketPost(Packet<?> packet, CallbackInfo info) {
/* 35 */     PacketEvent.SendPost event = new PacketEvent.SendPost(packet);
/* 36 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 37 */     if (event.isCanceled()) {
/* 38 */       info.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"channelRead0"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void onChannelReadPost(ChannelHandlerContext context, Packet<?> packet, CallbackInfo info) {
/* 44 */     if (Util.mc.field_71439_g != null && Util.mc.field_71441_e != null) {
/* 45 */       PacketEvent.ReceivePost event = new PacketEvent.ReceivePost(packet);
/* 46 */       MinecraftForge.EVENT_BUS.post((Event)event);
/* 47 */       if (event.isCanceled()) {
/* 48 */         info.cancel();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"channelRead0"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onChannelReadPre(ChannelHandlerContext context, Packet<?> packet, CallbackInfo info) {
/* 55 */     if (Util.mc.field_71439_g != null && Util.mc.field_71441_e != null) {
/* 56 */       if (packet instanceof SPacketEntityStatus && ((SPacketEntityStatus)packet).func_149160_c() == 35) {
/* 57 */         Entity entity = ((SPacketEntityStatus)packet).func_149161_a((World)Util.mc.field_71441_e);
/* 58 */         if (entity != null && entity.equals(Util.mc.field_71439_g)) {
/* 59 */           AutoTotem.packet_latency_timer = System.currentTimeMillis();
/*    */         }
/*    */       } 
/* 62 */       PacketEvent.Receive event = new PacketEvent.Receive(packet);
/* 63 */       MinecraftForge.EVENT_BUS.post((Event)event);
/* 64 */       if (event.isCanceled()) {
/* 65 */         info.cancel();
/* 66 */       } else if (!event.getPostEvents().isEmpty()) {
/* 67 */         for (Runnable runnable : event.getPostEvents()) {
/* 68 */           Minecraft.func_71410_x().func_152344_a(runnable);
/*    */         }
/* 70 */         info.cancel();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinNetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */