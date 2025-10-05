/*    */ package com.mrzak34.thunderhack.manager;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class ReloadManager
/*    */ {
/*    */   public String prefix;
/*    */   
/*    */   public void init(String prefix) {
/* 17 */     this.prefix = prefix;
/* 18 */     MinecraftForge.EVENT_BUS.register(this);
/* 19 */     if (!Module.fullNullCheck()) {
/* 20 */       Command.sendMessage(ChatFormatting.RED + "Тандерхак отключен! напиши " + prefix + "reload чтобы включить");
/*    */     }
/*    */   }
/*    */   
/*    */   public void unload() {
/* 25 */     MinecraftForge.EVENT_BUS.unregister(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/*    */     CPacketChatMessage packet;
/* 31 */     if (event.getPacket() instanceof CPacketChatMessage && (packet = (CPacketChatMessage)event.getPacket()).func_149439_c().startsWith(this.prefix) && packet.func_149439_c().contains("reload")) {
/* 32 */       Thunderhack.load();
/* 33 */       event.setCanceled(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\ReloadManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */