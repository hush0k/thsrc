/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayList
/*     */   extends HudElement
/*     */ {
/*  30 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.ColorText));
/*  31 */   private final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  32 */   private final Setting<Float> rainbowSpeed = register(new Setting("Speed", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(20.0F)));
/*  33 */   private final Setting<Float> saturation = register(new Setting("Saturation", Float.valueOf(0.5F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*  34 */   private final Setting<Integer> gste = register(new Setting("GS", Integer.valueOf(30), Integer.valueOf(1), Integer.valueOf(50)));
/*  35 */   private final Setting<Boolean> glow = register(new Setting("glow", Boolean.valueOf(false)));
/*  36 */   private final Setting<cMode> cmode = register(new Setting("ColorMode", cMode.Rainbow));
/*  37 */   private final Setting<Boolean> hrender = register(new Setting("HideHud", Boolean.valueOf(true)));
/*  38 */   private final Setting<Boolean> hhud = register(new Setting("HideRender", Boolean.valueOf(true)));
/*  39 */   private final Setting<ColorSetting> color2 = register(new Setting("Color2", new ColorSetting(237176633)));
/*  40 */   private final Setting<ColorSetting> color3 = register(new Setting("RectColor", new ColorSetting(-16777216)));
/*  41 */   private final Setting<ColorSetting> color4 = register(new Setting("SideRectColor", new ColorSetting(-16777216)));
/*     */   
/*     */   boolean reverse;
/*     */   
/*     */   public ArrayList() {
/*  46 */     super("ArrayList", "arraylist", 50, 30);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect(double left, double top, double right, double bottom, int color) {
/*  51 */     if (left < right) {
/*  52 */       double i = left;
/*  53 */       left = right;
/*  54 */       right = i;
/*     */     } 
/*  56 */     if (top < bottom) {
/*  57 */       double j = top;
/*  58 */       top = bottom;
/*  59 */       bottom = j;
/*     */     } 
/*  61 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  62 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  63 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  64 */     float f2 = (color & 0xFF) / 255.0F;
/*  65 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  66 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  67 */     GlStateManager.func_179147_l();
/*  68 */     GlStateManager.func_179090_x();
/*  69 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  70 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/*  71 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/*  72 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/*  73 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/*  74 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/*  75 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/*  76 */     tessellator.func_78381_a();
/*  77 */     GlStateManager.func_179098_w();
/*  78 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/*  83 */     super.onRender2D(e);
/*     */ 
/*     */     
/*  86 */     this.reverse = (getPosX() > (float)(e.getScreenWidth() / 2.0D));
/*  87 */     int offset = 0;
/*  88 */     int offset2 = 0;
/*     */     
/*  90 */     int yTotal = 0;
/*  91 */     for (int i = 0; i < Thunderhack.moduleManager.sortedModules.size(); i++) {
/*  92 */       yTotal += FontRender.getFontHeight6() + 3;
/*     */     }
/*  94 */     setHeight(yTotal);
/*     */ 
/*     */     
/*  97 */     if (this.mode.getValue() == Mode.ColorText) {
/*  98 */       for (int j = 0; j < Thunderhack.moduleManager.sortedModules.size(); j++) {
/*  99 */         Module module = Thunderhack.moduleManager.sortedModules.get(j);
/* 100 */         if (module.isDrawn() && (
/* 101 */           !((Boolean)this.hrender.getValue()).booleanValue() || module.getCategory() != Module.Category.RENDER) && (
/* 102 */           !((Boolean)this.hhud.getValue()).booleanValue() || module.getCategory() != Module.Category.HUD)) {
/* 103 */           Color color1 = null;
/* 104 */           if (this.cmode.getValue() == cMode.Rainbow) {
/* 105 */             color1 = PaletteHelper.astolfo(offset2, yTotal, ((Float)this.saturation.getValue()).floatValue(), ((Float)this.rainbowSpeed.getValue()).floatValue());
/* 106 */           } else if (this.cmode.getValue() == cMode.DoubleColor) {
/* 107 */             color1 = RenderUtil.TwoColoreffect(((ColorSetting)this.color.getValue()).getColorObject(), ((ColorSetting)this.color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + (offset2 * (20.0F - ((Float)this.rainbowSpeed.getValue()).floatValue()) / 200.0F));
/*     */           } else {
/* 109 */             color1 = (new Color(((ColorSetting)this.color.getValue()).getColor())).darker();
/*     */           } 
/* 111 */           if (!this.reverse) {
/* 112 */             int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
/* 113 */             if (((Boolean)this.glow.getValue()).booleanValue()) RenderHelper.drawBlurredShadow(getPosX() - 3.0F, getPosY() + offset2 - 1.0F, stringWidth + 4.0F, 9.0F, ((Integer)this.gste.getValue()).intValue(), color1); 
/*     */           } 
/* 115 */           if (this.reverse) {
/* 116 */             int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
/* 117 */             if (((Boolean)this.glow.getValue()).booleanValue()) RenderHelper.drawBlurredShadow(getPosX() - stringWidth - 3.0F, getPosY() + offset2 - 1.0F, (stringWidth + 4), 9.0F, ((Integer)this.gste.getValue()).intValue(), color1); 
/*     */           } 
/* 119 */           offset2 += 8;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 124 */     for (int k = 0; k < Thunderhack.moduleManager.sortedModules.size(); k++) {
/* 125 */       Module module = Thunderhack.moduleManager.sortedModules.get(k);
/* 126 */       if (module.isDrawn())
/*     */       {
/*     */         
/* 129 */         if (!((Boolean)this.hrender.getValue()).booleanValue() || module.getCategory() != Module.Category.RENDER)
/*     */         {
/*     */           
/* 132 */           if (!((Boolean)this.hhud.getValue()).booleanValue() || module.getCategory() != Module.Category.HUD) {
/*     */ 
/*     */             
/* 135 */             Color color1 = null;
/*     */             
/* 137 */             if (this.cmode.getValue() == cMode.Rainbow) {
/* 138 */               color1 = PaletteHelper.astolfo(offset, yTotal, ((Float)this.saturation.getValue()).floatValue(), ((Float)this.rainbowSpeed.getValue()).floatValue());
/* 139 */             } else if (this.cmode.getValue() == cMode.DoubleColor) {
/* 140 */               color1 = RenderUtil.TwoColoreffect(((ColorSetting)this.color.getValue()).getColorObject(), ((ColorSetting)this.color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + (offset * (20.0F - ((Float)this.rainbowSpeed.getValue()).floatValue()) / 200.0F));
/*     */             } else {
/* 142 */               color1 = (new Color(((ColorSetting)this.color.getValue()).getColor())).darker();
/*     */             } 
/*     */             
/* 145 */             if (this.mode.getValue() == Mode.ColorRect) {
/* 146 */               if (!this.reverse) {
/* 147 */                 int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
/* 148 */                 if (((Boolean)this.glow.getValue()).booleanValue()) {
/* 149 */                   RenderHelper.drawBlurredShadow(getPosX() - 3.0F, getPosY() + offset - 1.0F, stringWidth + 4.0F, 9.0F, ((Integer)this.gste.getValue()).intValue(), color1);
/*     */                 }
/* 151 */                 drawRect(getPosX(), (getPosY() + offset), (getPosX() + stringWidth + 1.0F), (getPosY() + offset + 8.0F), color1.getRGB());
/* 152 */                 drawRect((getPosX() - 2.0F), (getPosY() + offset), (getPosX() + 1.0F), (getPosY() + offset + 8.0F), ((ColorSetting)this.color4.getValue()).getColor());
/* 153 */                 FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), getPosX() + 3.0F, getPosY() + 2.0F + offset, -1, false);
/*     */               } 
/* 155 */               if (this.reverse) {
/* 156 */                 int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
/* 157 */                 if (((Boolean)this.glow.getValue()).booleanValue()) {
/* 158 */                   RenderHelper.drawBlurredShadow(getPosX() - stringWidth - 3.0F, getPosY() + offset - 1.0F, (stringWidth + 4), 9.0F, ((Integer)this.gste.getValue()).intValue(), color1);
/*     */                 }
/* 160 */                 drawRect((getPosX() - stringWidth), (getPosY() + offset), (getPosX() + 1.0F), (getPosY() + offset + 8.0F), color1.getRGB());
/* 161 */                 drawRect((getPosX() + 1.0F), (getPosY() + offset), (getPosX() + 4.0F), (getPosY() + offset + 8.0F), ((ColorSetting)this.color4.getValue()).getColor());
/* 162 */                 FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), getPosX() - stringWidth + 2.0F, getPosY() + 2.0F + offset, -1, false);
/*     */               } 
/*     */             } else {
/* 165 */               if (!this.reverse) {
/* 166 */                 int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
/* 167 */                 drawRect(getPosX(), (getPosY() + offset), (getPosX() + stringWidth + 1.0F), (getPosY() + offset + 8.0F), ((ColorSetting)this.color3.getValue()).getColor());
/* 168 */                 drawRect((getPosX() - 2.0F), (getPosY() + offset), (getPosX() + 1.0F), (getPosY() + offset + 8.0F), color1.getRGB());
/* 169 */                 FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), getPosX() + 3.0F, getPosY() + 2.0F + offset, color1.getRGB(), false);
/*     */               } 
/* 171 */               if (this.reverse) {
/* 172 */                 int stringWidth = FontRender.getStringWidth6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : "")) + 3;
/* 173 */                 drawRect((getPosX() - stringWidth), (getPosY() + offset), (getPosX() + 1.0F), (getPosY() + offset + 8.0F), ((ColorSetting)this.color3.getValue()).getColor());
/* 174 */                 drawRect((getPosX() + 1.0F), (getPosY() + offset), (getPosX() + 4.0F), (getPosY() + offset + 8.0F), color1.getRGB());
/* 175 */                 FontRender.drawString6(module.getDisplayName() + ChatFormatting.GRAY + ((module.getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + module.getDisplayInfo() + ChatFormatting.GRAY + "]") : ""), getPosX() - stringWidth + 2.0F, getPosY() + 2.0F + offset, color1.getRGB(), false);
/*     */               } 
/*     */             } 
/* 178 */             offset += 8;
/*     */           }  }  } 
/*     */     } 
/*     */   }
/*     */   
/* 183 */   private enum cMode { Rainbow, Custom, DoubleColor; }
/*     */ 
/*     */   
/*     */   private enum Mode {
/* 187 */     ColorText, ColorRect;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\ArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */