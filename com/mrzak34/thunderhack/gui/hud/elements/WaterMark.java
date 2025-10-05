/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class WaterMark
/*     */   extends HudElement
/*     */ {
/*  19 */   public final Setting<ColorSetting> color1 = register(new Setting("TextColor", new ColorSetting(-1)));
/*  20 */   public final Setting<ColorSetting> color2 = register(new Setting("Color", new ColorSetting(-15724528)));
/*  21 */   public final Setting<ColorSetting> shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528)));
/*  22 */   public Timer timer = new Timer();
/*  23 */   int i = 0;
/*     */   public WaterMark() {
/*  25 */     super("WaterMark", "WaterMark", 200, 20);
/*     */   }
/*     */   
/*     */   public static void setColor(int color) {
/*  29 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/*  34 */     super.onRender2D(e);
/*  35 */     RenderUtil.drawBlurredShadow(4.0F, 4.0F, (FontRender.getStringWidth6("ThunderHack  |  " + mc.field_71439_g.func_70005_c_() + "  |  " + Thunderhack.serverManager.getPing() + " ms  |  " + ((mc.func_147104_D() == null) ? "SinglePlayer" : (mc.func_147104_D()).field_78845_b)) + 29), 12.0F, 10, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/*  36 */     RoundedShader.drawRound(4.0F, 4.0F, (FontRender.getStringWidth6("ThunderHack  |  " + mc.field_71439_g.func_70005_c_() + "  |  " + Thunderhack.serverManager.getPing() + " ms  |  " + ((mc.func_147104_D() == null) ? "SinglePlayer" : (mc.func_147104_D()).field_78845_b)) + 30), 13.0F, 2.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/*     */     
/*  38 */     if (this.timer.passedMs(350L)) {
/*  39 */       this.i++;
/*  40 */       this.timer.reset();
/*     */     } 
/*     */     
/*  43 */     if (this.i == 24) {
/*  44 */       this.i = 0;
/*     */     }
/*     */     
/*  47 */     String w1 = "_";
/*  48 */     String w2 = "T_";
/*  49 */     String w3 = "Th_";
/*  50 */     String w4 = "Thu_";
/*  51 */     String w5 = "Thun_";
/*  52 */     String w6 = "Thund_";
/*  53 */     String w7 = "Thunde_";
/*  54 */     String w8 = "Thunder_";
/*  55 */     String w9 = "ThunderH_";
/*  56 */     String w10 = "ThunderHa_";
/*  57 */     String w11 = "ThunderHac_";
/*  58 */     String w12 = "ThunderHack";
/*  59 */     String w13 = "ThunderHack";
/*  60 */     String w14 = "ThunderHack";
/*  61 */     String w15 = "ThunderHac_";
/*  62 */     String w16 = "ThunderHa_";
/*  63 */     String w17 = "ThunderH_";
/*  64 */     String w18 = "Thunder_";
/*  65 */     String w19 = "Thunde_";
/*  66 */     String w20 = "Thund_";
/*  67 */     String w21 = "Thun_";
/*  68 */     String w22 = "Thu_";
/*  69 */     String w23 = "Th_";
/*  70 */     String w24 = "T_";
/*  71 */     String w25 = "_";
/*  72 */     String text = "";
/*     */     
/*  74 */     if (this.i == 0) {
/*  75 */       text = w1;
/*     */     }
/*  77 */     if (this.i == 1) {
/*  78 */       text = w2;
/*     */     }
/*  80 */     if (this.i == 2) {
/*  81 */       text = w3;
/*     */     }
/*  83 */     if (this.i == 3) {
/*  84 */       text = w4;
/*     */     }
/*  86 */     if (this.i == 4) {
/*  87 */       text = w5;
/*     */     }
/*  89 */     if (this.i == 5) {
/*  90 */       text = w6;
/*     */     }
/*  92 */     if (this.i == 6) {
/*  93 */       text = w7;
/*     */     }
/*  95 */     if (this.i == 7) {
/*  96 */       text = w8;
/*     */     }
/*  98 */     if (this.i == 8) {
/*  99 */       text = w9;
/*     */     }
/* 101 */     if (this.i == 9) {
/* 102 */       text = w10;
/*     */     }
/* 104 */     if (this.i == 10) {
/* 105 */       text = w11;
/*     */     }
/* 107 */     if (this.i == 11) {
/* 108 */       text = w12;
/*     */     }
/* 110 */     if (this.i == 12) {
/* 111 */       text = w13;
/*     */     }
/* 113 */     if (this.i == 13) {
/* 114 */       text = w14;
/*     */     }
/* 116 */     if (this.i == 14) {
/* 117 */       text = w15;
/*     */     }
/* 119 */     if (this.i == 15) {
/* 120 */       text = w16;
/*     */     }
/* 122 */     if (this.i == 16) {
/* 123 */       text = w17;
/*     */     }
/* 125 */     if (this.i == 17) {
/* 126 */       text = w18;
/*     */     }
/* 128 */     if (this.i == 18) {
/* 129 */       text = w19;
/*     */     }
/* 131 */     if (this.i == 19) {
/* 132 */       text = w20;
/*     */     }
/* 134 */     if (this.i == 20) {
/* 135 */       text = w21;
/*     */     }
/* 137 */     if (this.i == 21) {
/* 138 */       text = w22;
/*     */     }
/* 140 */     if (this.i == 22) {
/* 141 */       text = w23;
/*     */     }
/* 143 */     if (this.i == 23) {
/* 144 */       text = w24;
/*     */     }
/* 146 */     if (this.i == 23) {
/* 147 */       text = w25;
/*     */     }
/*     */ 
/*     */     
/* 151 */     FontRender.drawString6(text, 7.0F, 9.0F, -1, false);
/* 152 */     FontRender.drawString6("  |  " + mc.field_71439_g.func_70005_c_() + "  |  " + Thunderhack.serverManager.getPing() + " ms  |  " + ((mc.func_147104_D() == null) ? "SinglePlayer" : (mc.func_147104_D()).field_78845_b), (FontRender.getStringWidth6("ThunderHack") + 10), 9.0F, ((ColorSetting)this.color1.getValue()).getColor(), false);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\WaterMark.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */