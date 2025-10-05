/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.setting.SubBind;
/*    */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KDShop
/*    */   extends Module
/*    */ {
/*    */   public static GuiScreen lastGui;
/*    */   public static boolean cancelRender = false;
/* 19 */   public Setting<SubBind> breakBind = register(new Setting("Open", new SubBind(23)));
/*    */   boolean closeInv;
/*    */   
/*    */   public KDShop() {
/* 23 */     super("KDShop", "Не всегда работает-но да ладно", Module.Category.FUNNYGAME);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 28 */     if (mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer) {
/* 29 */       lastGui = mc.field_71462_r;
/*    */     }
/* 31 */     if (PlayerUtils.isKeyDown(((SubBind)this.breakBind.getValue()).getKey())) {
/* 32 */       mc.func_147108_a(lastGui);
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 38 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketCloseWindow) {
/* 39 */       event.setCanceled(true);
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 45 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketCloseWindow)
/* 46 */       Command.sendMessage("fuck"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\KDShop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */