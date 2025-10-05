/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.RoundedShader;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ public class PacketStatistics
/*    */   extends HudElement
/*    */ {
/*    */   public final Setting<ColorSetting> shadowColor;
/*    */   public final Setting<ColorSetting> color2;
/*    */   public final Setting<ColorSetting> textColor;
/*    */   public final Setting<ColorSetting> color3;
/*    */   
/*    */   public PacketStatistics() {
/* 24 */     super("PacketStatistics", "PacketStatistics", 100, 50);
/*    */ 
/*    */     
/* 27 */     this.shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528)));
/* 28 */     this.color2 = register(new Setting("Color", new ColorSetting(-15724528)));
/* 29 */     this.textColor = register(new Setting("TextColor", new ColorSetting(12500670)));
/* 30 */     this.color3 = register(new Setting("Color2", new ColorSetting(-979657829)));
/*    */   }
/*    */   int counter_in; int counter_out; int packets_in; int packets_out;
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 35 */     super.onRender2D(e);
/* 36 */     GlStateManager.func_179094_E();
/* 37 */     RenderUtil.drawBlurredShadow(getPosX(), getPosY(), 80.0F, 40.0F, 20, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/* 38 */     RoundedShader.drawRound(getPosX(), getPosY(), 80.0F, 40.0F, 7.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/* 39 */     RoundedShader.drawRound(getPosX() + 2.0F, getPosY() + 13.0F, 76.0F, 1.0F, 0.5F, ((ColorSetting)this.color3.getValue()).getColorObject());
/* 40 */     FontRender.drawCentString6("PacketStatistics", getPosX() + 40.0F, getPosY() + 5.0F, ((ColorSetting)this.textColor.getValue()).getColor());
/* 41 */     FontRender.drawString5("In: " + (this.packets_in * 4) + " p/s", getPosX() + 3.0F, getPosY() + 20.0F, ((ColorSetting)this.textColor.getValue()).getColor());
/* 42 */     FontRender.drawString5("Out: " + (this.packets_out * 4) + " p/s", getPosX() + 3.0F, getPosY() + 30.0F, ((ColorSetting)this.textColor.getValue()).getColor());
/* 43 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive e) {
/* 54 */     this.counter_in++;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 59 */     this.counter_out++;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 65 */     if (mc.field_71439_g.field_70173_aa % 5 == 0) {
/* 66 */       this.packets_in = this.counter_in;
/* 67 */       this.packets_out = this.counter_out;
/* 68 */       this.counter_out = 0;
/* 69 */       this.counter_in = 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\PacketStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */