/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class FpsCounter
/*    */   extends HudElement {
/* 14 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*    */   
/*    */   public FpsCounter() {
/* 17 */     super("Fps", "fps", 50, 10);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 22 */     super.onRender2D(e);
/* 23 */     FontRender.drawString6("FPS " + ChatFormatting.WHITE + Minecraft.func_175610_ah(), getPosX(), getPosY(), ((ColorSetting)this.color.getValue()).getRawColor(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\FpsCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */