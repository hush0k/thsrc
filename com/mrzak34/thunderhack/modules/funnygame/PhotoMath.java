/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.ThunderUtils;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.network.play.server.SPacketChat;
/*    */ import net.minecraft.util.text.ChatType;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class PhotoMath
/*    */   extends Module {
/*    */   public PhotoMath() {
/* 16 */     super("PhotoMath", "Решает чат игру автоматом", Module.Category.FUNNYGAME);
/*    */     
/* 18 */     this.spam = register(new Setting("Spam", Boolean.valueOf(false)));
/*    */   } public Setting<Boolean> spam;
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 22 */     if (event.getPacket() instanceof SPacketChat) {
/* 23 */       SPacketChat packet = (SPacketChat)event.getPacket();
/* 24 */       if (packet.func_192590_c() != ChatType.GAME_INFO && packet.func_148915_c().func_150254_d().contains("Решите: ") && Objects.equals(ThunderUtils.solvename(packet.func_148915_c().func_150254_d()), "err")) {
/* 25 */         int solve = Integer.parseInt(StringUtils.substringBetween(packet.func_148915_c().func_150260_c(), "Решите: ", " + ")) + Integer.parseInt(StringUtils.substringBetween(packet.func_148915_c().func_150260_c(), " + ", " кто первый"));
/* 26 */         for (int i = 0; i < (((Boolean)this.spam.getValue()).booleanValue() ? 9 : 1); ) { mc.field_71439_g.func_71165_d(String.valueOf(solve)); i++; }
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\PhotoMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */