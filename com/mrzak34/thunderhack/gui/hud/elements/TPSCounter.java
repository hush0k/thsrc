/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class TPSCounter extends HudElement {
/* 13 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*    */   
/*    */   public TPSCounter() {
/* 16 */     super("TPS", "trps", 50, 10);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 21 */     super.onRender2D(e);
/* 22 */     String str = "TPS " + ChatFormatting.WHITE + Thunderhack.serverManager.getTPS();
/* 23 */     FontRender.drawString6(str, getPosX(), getPosY(), ((ColorSetting)this.color.getValue()).getRawColor(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\TPSCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */