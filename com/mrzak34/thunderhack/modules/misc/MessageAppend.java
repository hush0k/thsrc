/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketChatMessage;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class MessageAppend
/*    */   extends Module {
/* 13 */   public Setting<String> word = register(new Setting("word", "   RAGE"));
/*    */   
/*    */   public MessageAppend() {
/* 16 */     super("MessageAppend", "добавляет фразу-в конце сообщения", Module.Category.MISC);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 21 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/*    */     
/* 25 */     if (e.getPacket() instanceof CPacketChatMessage) {
/* 26 */       if (((CPacketChatMessage)e.getPacket()).func_149439_c().startsWith("/") || ((CPacketChatMessage)e.getPacket()).func_149439_c().startsWith(Command.getCommandPrefix())) {
/*    */         return;
/*    */       }
/* 29 */       CPacketChatMessage pac = (CPacketChatMessage)e.getPacket();
/* 30 */       ((ICPacketChatMessage)pac).setMessage(((CPacketChatMessage)e.getPacket()).func_149439_c() + (String)this.word.getValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\MessageAppend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */