/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.ThunderUtils;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.network.play.server.SPacketChat;
/*    */ import net.minecraft.util.text.ChatType;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class AutoTPaccept
/*    */   extends Module
/*    */ {
/* 16 */   public Setting<Boolean> onlyFriends = register(new Setting("onlyFriends", Boolean.TRUE));
/*    */   
/*    */   public AutoTPaccept() {
/* 19 */     super("AutoTPaccept", "Принимает тп автоматом", Module.Category.FUNNYGAME);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 24 */     if (event.getPacket() instanceof SPacketChat) {
/* 25 */       SPacketChat packet = (SPacketChat)event.getPacket();
/* 26 */       if (packet.func_192590_c() == ChatType.GAME_INFO || tryProcessChat(packet.func_148915_c().func_150254_d(), packet.func_148915_c().func_150260_c()));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean tryProcessChat(String message, String unformatted) {
/* 35 */     String out = message;
/*    */     
/* 37 */     out = message;
/*    */ 
/*    */     
/* 40 */     if (Util.mc.field_71439_g == null) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     if (out.contains("телепортироваться")) {
/* 45 */       if (((Boolean)this.onlyFriends.getValue()).booleanValue()) {
/* 46 */         if (Thunderhack.friendManager.isFriend(ThunderUtils.solvename(out))) {
/* 47 */           mc.field_71439_g.func_71165_d("/tpaccept");
/*    */         }
/*    */       } else {
/* 50 */         mc.field_71439_g.func_71165_d("/tpaccept");
/*    */       } 
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AutoTPaccept.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */