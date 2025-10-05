/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class LowHPScreen
/*    */   extends Module
/*    */ {
/* 16 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(575714484)));
/* 17 */   int dynamic_alpha = 0;
/* 18 */   int nuyahz = 0;
/*    */   public LowHPScreen() {
/* 20 */     super("LowHPScreen", "LowHPScreen", Module.Category.RENDER);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 26 */     Color color2 = new Color(((ColorSetting)this.color.getValue()).getRed(), ((ColorSetting)this.color.getValue()).getGreen(), ((ColorSetting)this.color.getValue()).getBlue(), MathUtil.clamp(this.dynamic_alpha + 40, 0, 255));
/*    */     
/* 28 */     if (mc.field_71439_g.func_110143_aJ() < 10.0F) {
/* 29 */       ScaledResolution sr = new ScaledResolution(mc);
/* 30 */       RenderUtil.draw2DGradientRect(0.0F, 0.0F, sr.func_78326_a(), sr.func_78326_a(), color2.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), color2.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 31 */       if (mc.field_71439_g.func_110143_aJ() > 9.0F) {
/* 32 */         this.nuyahz = 18;
/* 33 */       } else if (mc.field_71439_g.func_110143_aJ() > 8.0F) {
/* 34 */         this.nuyahz = 36;
/* 35 */       } else if (mc.field_71439_g.func_110143_aJ() > 7.0F) {
/* 36 */         this.nuyahz = 54;
/* 37 */       } else if (mc.field_71439_g.func_110143_aJ() > 6.0F) {
/* 38 */         this.nuyahz = 72;
/* 39 */       } else if (mc.field_71439_g.func_110143_aJ() > 5.0F) {
/* 40 */         this.nuyahz = 90;
/* 41 */       } else if (mc.field_71439_g.func_110143_aJ() > 4.0F) {
/* 42 */         this.nuyahz = 108;
/* 43 */       } else if (mc.field_71439_g.func_110143_aJ() > 3.0F) {
/* 44 */         this.nuyahz = 126;
/* 45 */       } else if (mc.field_71439_g.func_110143_aJ() > 2.0F) {
/* 46 */         this.nuyahz = 144;
/* 47 */       } else if (mc.field_71439_g.func_110143_aJ() > 1.0F) {
/* 48 */         this.nuyahz = 162;
/* 49 */       } else if (mc.field_71439_g.func_110143_aJ() > 0.0F) {
/* 50 */         this.nuyahz = 180;
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     if (this.nuyahz > this.dynamic_alpha) {
/* 55 */       this.dynamic_alpha += 3;
/*    */     }
/* 57 */     if (this.nuyahz < this.dynamic_alpha)
/* 58 */       this.dynamic_alpha -= 3; 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\LowHPScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */