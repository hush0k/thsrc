/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.RoundedShader;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyBinds
/*    */   extends HudElement
/*    */ {
/* 26 */   public final Setting<ColorSetting> shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528)));
/* 27 */   public final Setting<ColorSetting> color2 = register(new Setting("Color", new ColorSetting(-15724528)));
/* 28 */   public final Setting<ColorSetting> color3 = register(new Setting("Color2", new ColorSetting(-979657829)));
/* 29 */   public final Setting<ColorSetting> textColor = register(new Setting("TextColor", new ColorSetting(12500670)));
/* 30 */   public final Setting<ColorSetting> oncolor = register(new Setting("OnColor", new ColorSetting(12500670)));
/* 31 */   public final Setting<ColorSetting> offcolor = register(new Setting("OffColor", new ColorSetting(6579300)));
/*    */   
/*    */   public KeyBinds() {
/* 34 */     super("KeyBinds", "KeyBinds", 100, 100);
/*    */   }
/*    */   
/*    */   public static void size(double width, double height, double animation) {
/* 38 */     GL11.glTranslated(width, height, 0.0D);
/* 39 */     GL11.glScaled(animation, animation, 1.0D);
/* 40 */     GL11.glTranslated(-width, -height, 0.0D);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 45 */     super.onRender2D(e);
/* 46 */     int y_offset1 = 0;
/* 47 */     for (Module feature : Thunderhack.moduleManager.modules) {
/* 48 */       if (!Objects.equals(feature.getBind().toString(), "None") && !feature.getName().equalsIgnoreCase("clickgui") && !feature.getName().equalsIgnoreCase("thundergui")) {
/* 49 */         y_offset1 += 10;
/*    */       }
/*    */     } 
/*    */     
/* 53 */     GlStateManager.func_179094_E();
/*    */     
/* 55 */     RenderUtil.drawBlurredShadow(getPosX(), getPosY(), 100.0F, (20 + y_offset1), 20, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/* 56 */     RoundedShader.drawRound(getPosX(), getPosY(), 100.0F, (20 + y_offset1), 7.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/* 57 */     FontRender.drawCentString6("KeyBinds", getPosX() + 50.0F, getPosY() + 5.0F, ((ColorSetting)this.textColor.getValue()).getColor());
/* 58 */     RoundedShader.drawRound(getPosX() + 2.0F, getPosY() + 13.0F, 96.0F, 1.0F, 0.5F, ((ColorSetting)this.color3.getValue()).getColorObject());
/*    */     
/* 60 */     int y_offset = 0;
/* 61 */     for (Module feature : Thunderhack.moduleManager.modules) {
/* 62 */       if (!Objects.equals(feature.getBind().toString(), "None") && !feature.getName().equalsIgnoreCase("clickgui") && !feature.getName().equalsIgnoreCase("thundergui")) {
/* 63 */         GlStateManager.func_179094_E();
/* 64 */         GlStateManager.func_179117_G();
/* 65 */         FontRender.drawString6("[" + feature.getBind().toString() + "]  " + feature.getName(), getPosX() + 5.0F, getPosY() + 18.0F + y_offset, feature.isOn() ? ((ColorSetting)this.oncolor.getValue()).getColor() : ((ColorSetting)this.offcolor.getValue()).getColor(), false);
/* 66 */         GlStateManager.func_179117_G();
/* 67 */         GlStateManager.func_179121_F();
/* 68 */         y_offset += 10;
/*    */       } 
/*    */     } 
/* 71 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\KeyBinds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */