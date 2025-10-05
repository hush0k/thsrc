/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ import com.mrzak34.thunderhack.events.RenderHand;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.CircleShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.FlowShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.PhobosShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.SmokeShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.outline.AquaOutlineShader;
/*     */ import java.awt.Color;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class ItemShaders extends Module {
/*  16 */   private static ItemShaders INSTANCE = new ItemShaders();
/*  17 */   private final Setting<ColorSetting> colorImgOutline = register(new Setting("colorImgOutline", new ColorSetting(-2013200640)));
/*  18 */   private final Setting<ColorSetting> secondColorImgOutline = register(new Setting("secondColorImgOutline", new ColorSetting(-2013200640)));
/*  19 */   private final Setting<ColorSetting> thirdColorImgOutline = register(new Setting("thirdColorImgOutline", new ColorSetting(-2013200640)));
/*  20 */   private final Setting<ColorSetting> colorESP = register(new Setting("colorESP", new ColorSetting(-2013200640)));
/*  21 */   private final Setting<ColorSetting> colorImgFill = register(new Setting("colorImgFill", new ColorSetting(-2013200640)));
/*  22 */   private final Setting<ColorSetting> secondcolorImgFill = register(new Setting("secondcolorImgFill", new ColorSetting(-2013200640)));
/*  23 */   private final Setting<ColorSetting> thirdcolorImgFill = register(new Setting("thirdcolorImgFill", new ColorSetting(-2013200640)));
/*  24 */   public Setting<fillShadermode> fillShader = register(new Setting("Fill Shader", fillShadermode.None));
/*  25 */   public Setting<glowESPmode> glowESP = register(new Setting("Glow ESP", glowESPmode.None));
/*  26 */   public Setting<Float> duplicateOutline = register(new Setting("Speed", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  27 */   public Setting<Float> duplicateFill = register(new Setting("Duplicate Fill", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*  28 */   public Setting<Float> speedOutline = register(new Setting("Speed Outline", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(100.0F)));
/*  29 */   public Setting<Float> speedFill = register(new Setting("Speed Fill", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(100.0F)));
/*     */ 
/*     */ 
/*     */   
/*  33 */   public Setting<Float> rad = register(new Setting("RAD Fill", Float.valueOf(0.75F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.fillShader.getValue() == fillShadermode.Circle)));
/*  34 */   public Setting<Float> PI = register(new Setting("PI Fill", Float.valueOf(3.1415927F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.fillShader.getValue() == fillShadermode.Circle)));
/*  35 */   public Setting<Float> saturationFill = register(new Setting("saturation", Float.valueOf(0.4F), Float.valueOf(0.0F), Float.valueOf(3.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  36 */   public Setting<Float> distfadingFill = register(new Setting("distfading", Float.valueOf(0.56F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  37 */   public Setting<Float> titleFill = register(new Setting("Tile", Float.valueOf(0.45F), Float.valueOf(0.0F), Float.valueOf(1.3F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  38 */   public Setting<Float> stepSizeFill = register(new Setting("Step Size", Float.valueOf(0.19F), Float.valueOf(0.0F), Float.valueOf(0.7F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  39 */   public Setting<Float> volumStepsFill = register(new Setting("Volum Steps", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  40 */   public Setting<Float> zoomFill = register(new Setting("Zoom", Float.valueOf(3.9F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  41 */   public Setting<Float> formuparam2Fill = register(new Setting("formuparam2", Float.valueOf(0.89F), Float.valueOf(0.0F), Float.valueOf(1.5F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  42 */   public Setting<Float> saturationOutline = register(new Setting("saturation", Float.valueOf(0.4F), Float.valueOf(0.0F), Float.valueOf(3.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  43 */   public Setting<Integer> iterationsFill = register(new Setting("Iteration", Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(20), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  44 */   public Setting<Integer> redFill = register(new Setting("Tick Regen", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(100), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  45 */   public Setting<Integer> MaxIterFill = register(new Setting("Max Iter", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(30), v -> (this.fillShader.getValue() == fillShadermode.Aqua)));
/*  46 */   public Setting<Integer> NUM_OCTAVESFill = register(new Setting("NUM_OCTAVES", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(30), v -> (this.fillShader.getValue() == fillShadermode.Smoke)));
/*  47 */   public Setting<Integer> BSTARTFIll = register(new Setting("BSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  48 */   public Setting<Integer> GSTARTFill = register(new Setting("GSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  49 */   public Setting<Integer> RSTARTFill = register(new Setting("RSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  50 */   public Setting<Integer> WaveLenghtFIll = register(new Setting("Wave Lenght", Integer.valueOf(555), Integer.valueOf(0), Integer.valueOf(2000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  51 */   public Setting<Integer> volumStepsOutline = register(new Setting("Volum Steps", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(10), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  52 */   public Setting<Integer> iterationsOutline = register(new Setting("Iteration", Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(20), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  53 */   public Setting<Integer> redOutline = register(new Setting("Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(100), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  54 */   public Setting<Integer> MaxIterOutline = register(new Setting("Max Iter", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(30), v -> (this.glowESP.getValue() == glowESPmode.Aqua)));
/*  55 */   public Setting<Integer> NUM_OCTAVESOutline = register(new Setting("NUM_OCTAVES", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(30), v -> (this.glowESP.getValue() == glowESPmode.Smoke)));
/*  56 */   public Setting<Integer> BSTARTOutline = register(new Setting("BSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  57 */   public Setting<Integer> GSTARTOutline = register(new Setting("GSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  58 */   public Setting<Integer> RSTARTOutline = register(new Setting("RSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  59 */   public Setting<Integer> WaveLenghtOutline = register(new Setting("Wave Lenght", Integer.valueOf(555), Integer.valueOf(0), Integer.valueOf(2000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  60 */   public Setting<Boolean> cancelItem = register(new Setting("Cancel Item", Boolean.valueOf(false)));
/*  61 */   public Setting<Float> alphaFill = register(new Setting("AlphaF", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral || this.fillShader.getValue() == fillShadermode.Smoke)));
/*  62 */   public Setting<Float> blueFill = register(new Setting("BlueF", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  63 */   public Setting<Float> greenFill = register(new Setting("GreenF", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  64 */   public Setting<Float> tauFill = register(new Setting("TAU", Float.valueOf(6.2831855F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.fillShader.getValue() == fillShadermode.Aqua)));
/*  65 */   public Setting<Float> creepyFill = register(new Setting("Creepy", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.fillShader.getValue() == fillShadermode.Smoke)));
/*  66 */   public Setting<Float> moreGradientFill = register(new Setting("More Gradient", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.fillShader.getValue() == fillShadermode.Smoke)));
/*  67 */   public Setting<Float> distfadingOutline = register(new Setting("distfading", Float.valueOf(0.56F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  68 */   public Setting<Float> titleOutline = register(new Setting("Tile", Float.valueOf(0.45F), Float.valueOf(0.0F), Float.valueOf(1.3F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  69 */   public Setting<Float> stepSizeOutline = register(new Setting("Step Size", Float.valueOf(0.2F), Float.valueOf(0.1F), Float.valueOf(1.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  70 */   public Setting<Float> zoomOutline = register(new Setting("Zoom", Float.valueOf(3.9F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  71 */   public Setting<Float> formuparam2Outline = register(new Setting("formuparam2", Float.valueOf(0.89F), Float.valueOf(0.0F), Float.valueOf(1.5F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  72 */   public Setting<Float> alphaOutline = register(new Setting("Alpha", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral || this.glowESP.getValue() == glowESPmode.Gradient)));
/*  73 */   public Setting<Float> blueOutline = register(new Setting("Blue", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  74 */   public Setting<Float> greenOutline = register(new Setting("Green", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  75 */   public Setting<Float> tauOutline = register(new Setting("TAU", Float.valueOf(6.2831855F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.glowESP.getValue() == glowESPmode.Aqua)));
/*  76 */   public Setting<Float> creepyOutline = register(new Setting("Creepy", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.glowESP.getValue() == glowESPmode.Gradient)));
/*  77 */   public Setting<Float> moreGradientOutline = register(new Setting("More Gradient", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.glowESP.getValue() == glowESPmode.Gradient)));
/*  78 */   public Setting<Float> radOutline = register(new Setting("RAD Outline", Float.valueOf(0.75F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.glowESP.getValue() == glowESPmode.Circle)));
/*  79 */   public Setting<Float> PIOutline = register(new Setting("PI Outline", Float.valueOf(3.1415927F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.glowESP.getValue() == glowESPmode.Circle)));
/*  80 */   public Setting<Float> quality = register(new Setting("quality", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  81 */   public Setting<Float> radius = register(new Setting("radius", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*  82 */   private final Setting<Boolean> rangeCheck = register(new Setting("Range Check", Boolean.valueOf(true)));
/*     */   
/*  84 */   private final Setting<Boolean> fadeFill = register(new Setting("Fade Fill", Boolean.valueOf(false)));
/*     */   
/*  86 */   private final Setting<Boolean> fadeOutline = register(new Setting("FadeOL Fill", Boolean.valueOf(false)));
/*  87 */   private final Setting<Boolean> GradientAlpha = register(new Setting("Gradient Alpha", Boolean.valueOf(false)));
/*  88 */   public Setting<Integer> alphaValue = register(new Setting("Alpha Outline", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> !((Boolean)this.GradientAlpha.getValue()).booleanValue()));
/*     */   public ItemShaders() {
/*  90 */     super("ItemShaders", "ItemShaders", Module.Category.RENDER);
/*  91 */     setInstance();
/*     */   }
/*     */   
/*     */   public static ItemShaders getInstance() {
/*  95 */     if (INSTANCE == null) {
/*  96 */       INSTANCE = new ItemShaders();
/*     */     }
/*  98 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/* 102 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHand(RenderHand.PreOutline event) {
/* 107 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/*     */       return; 
/* 109 */     GlStateManager.func_179094_E();
/* 110 */     GlStateManager.func_179123_a();
/* 111 */     GlStateManager.func_179147_l();
/* 112 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 113 */     GlStateManager.func_179126_j();
/* 114 */     GlStateManager.func_179132_a(true);
/* 115 */     GlStateManager.func_179141_d();
/*     */ 
/*     */     
/* 118 */     switch ((glowESPmode)this.glowESP.getValue()) {
/*     */       case Astral:
/* 120 */         GlowShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Aqua:
/* 123 */         RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Smoke:
/* 126 */         GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case RainbowCube:
/* 129 */         AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Gradient:
/* 132 */         AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Fill:
/* 135 */         CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Circle:
/* 138 */         SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHand(RenderHand.PostOutline event) {
/* 146 */     if (mc.field_71441_e == null || mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     switch ((glowESPmode)this.glowESP.getValue()) {
/*     */       case Astral:
/* 152 */         GlowShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue());
/*     */         break;
/*     */       case Aqua:
/* 155 */         RainbowCubeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.colorImgOutline.getValue()).getColorObject(), ((Integer)this.WaveLenghtOutline.getValue()).intValue(), ((Integer)this.RSTARTOutline.getValue()).intValue(), ((Integer)this.GSTARTOutline.getValue()).intValue(), ((Integer)this.BSTARTOutline.getValue()).intValue());
/* 156 */         RainbowCubeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Smoke:
/* 159 */         GradientOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.moreGradientOutline.getValue()).floatValue(), ((Float)this.creepyOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/* 160 */         GradientOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case RainbowCube:
/* 163 */         AstralOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.redOutline
/* 164 */             .getValue()).floatValue(), ((Float)this.greenOutline.getValue()).floatValue(), ((Float)this.blueOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.iterationsOutline
/* 165 */             .getValue()).intValue(), ((Float)this.formuparam2Outline.getValue()).floatValue(), ((Float)this.zoomOutline.getValue()).floatValue(), ((Integer)this.volumStepsOutline.getValue()).intValue(), ((Float)this.stepSizeOutline.getValue()).floatValue(), ((Float)this.titleOutline.getValue()).floatValue(), ((Float)this.distfadingOutline.getValue()).floatValue(), ((Float)this.saturationOutline
/* 166 */             .getValue()).floatValue(), 0.0F, ((Boolean)this.fadeOutline.getValue()).booleanValue() ? 1 : 0);
/* 167 */         AstralOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Gradient:
/* 170 */         AquaOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.MaxIterOutline.getValue()).intValue(), ((Float)this.tauOutline.getValue()).floatValue());
/* 171 */         AquaOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Fill:
/* 174 */         CircleOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.PIOutline.getValue()).floatValue(), ((Float)this.radOutline.getValue()).floatValue());
/* 175 */         CircleOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Circle:
/* 178 */         SmokeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.secondColorImgOutline.getValue()).getColorObject(), ((ColorSetting)this.thirdColorImgOutline.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/* 179 */         SmokeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */     } 
/* 182 */     GlStateManager.func_179084_k();
/* 183 */     GlStateManager.func_179118_c();
/* 184 */     GlStateManager.func_179097_i();
/* 185 */     GlStateManager.func_179099_b();
/* 186 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHand(RenderHand.PreFill event) {
/* 191 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/*     */       return; 
/* 193 */     GlStateManager.func_179094_E();
/* 194 */     GlStateManager.func_179123_a();
/* 195 */     GlStateManager.func_179147_l();
/* 196 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 197 */     GlStateManager.func_179126_j();
/* 198 */     GlStateManager.func_179132_a(true);
/* 199 */     GlStateManager.func_179141_d();
/*     */     
/* 201 */     switch ((fillShadermode)this.fillShader.getValue()) {
/*     */       case Astral:
/* 203 */         FlowShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Aqua:
/* 206 */         AquaShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Smoke:
/* 209 */         SmokeShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case RainbowCube:
/* 212 */         RainbowCubeShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Gradient:
/* 215 */         GradientShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Fill:
/* 218 */         FillShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Circle:
/* 221 */         CircleShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Phobos:
/* 224 */         PhobosShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHand(RenderHand.PostFill event) {
/* 231 */     if (mc.field_71441_e == null || mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     switch ((fillShadermode)this.fillShader.getValue()) {
/*     */       case Astral:
/* 237 */         FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.redFill
/* 238 */             .getValue()).floatValue(), ((Float)this.greenFill.getValue()).floatValue(), ((Float)this.blueFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.iterationsFill
/* 239 */             .getValue()).intValue(), ((Float)this.formuparam2Fill.getValue()).floatValue(), ((Float)this.zoomFill.getValue()).floatValue(), ((Float)this.volumStepsFill.getValue()).floatValue(), ((Float)this.stepSizeFill.getValue()).floatValue(), ((Float)this.titleFill.getValue()).floatValue(), ((Float)this.distfadingFill.getValue()).floatValue(), ((Float)this.saturationFill
/* 240 */             .getValue()).floatValue(), 0.0F, ((Boolean)this.fadeFill.getValue()).booleanValue() ? 1 : 0);
/* 241 */         FlowShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Aqua:
/* 244 */         AquaShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/* 245 */         AquaShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Smoke:
/* 248 */         SmokeShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((ColorSetting)this.secondcolorImgFill.getValue()).getColorObject(), ((ColorSetting)this.thirdcolorImgFill.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/* 249 */         SmokeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case RainbowCube:
/* 252 */         RainbowCubeShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.WaveLenghtFIll.getValue()).intValue(), ((Integer)this.RSTARTFill.getValue()).intValue(), ((Integer)this.GSTARTFill.getValue()).intValue(), ((Integer)this.BSTARTFIll.getValue()).intValue());
/* 253 */         RainbowCubeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Gradient:
/* 256 */         GradientShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Float)this.moreGradientFill.getValue()).floatValue(), ((Float)this.creepyFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/* 257 */         GradientShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Fill:
/* 260 */         FillShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject());
/* 261 */         FillShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Circle:
/* 264 */         CircleShader.INSTANCE.stopDraw(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), (Float)this.PI.getValue(), (Float)this.rad.getValue());
/* 265 */         CircleShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Phobos:
/* 268 */         PhobosShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/* 269 */         PhobosShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */     } 
/*     */     
/* 273 */     GlStateManager.func_179084_k();
/* 274 */     GlStateManager.func_179118_c();
/* 275 */     GlStateManager.func_179097_i();
/* 276 */     GlStateManager.func_179099_b();
/* 277 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHand(RenderHand.PreBoth event) {
/* 282 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/*     */       return; 
/* 284 */     GlStateManager.func_179094_E();
/* 285 */     GlStateManager.func_179123_a();
/* 286 */     GlStateManager.func_179147_l();
/* 287 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 288 */     GlStateManager.func_179126_j();
/* 289 */     GlStateManager.func_179132_a(true);
/* 290 */     GlStateManager.func_179141_d();
/*     */ 
/*     */     
/* 293 */     switch ((glowESPmode)this.glowESP.getValue()) {
/*     */       case Astral:
/* 295 */         GlowShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Aqua:
/* 298 */         RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Smoke:
/* 301 */         GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case RainbowCube:
/* 304 */         AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Gradient:
/* 307 */         AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Fill:
/* 310 */         CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */       case Circle:
/* 313 */         SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderHand(RenderHand.PostBoth event) {
/* 321 */     if (mc.field_71441_e == null || mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/* 324 */     Predicate<Boolean> newFill = getFill();
/*     */     
/* 326 */     switch ((glowESPmode)this.glowESP.getValue()) {
/*     */       
/*     */       case Astral:
/* 329 */         GlowShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue());
/*     */         break;
/*     */       
/*     */       case Aqua:
/* 333 */         RainbowCubeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.colorImgOutline.getValue()).getColorObject(), ((Integer)this.WaveLenghtOutline.getValue()).intValue(), ((Integer)this.RSTARTOutline.getValue()).intValue(), ((Integer)this.GSTARTOutline.getValue()).intValue(), ((Integer)this.BSTARTOutline.getValue()).intValue());
/*     */         
/* 335 */         RainbowCubeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       
/*     */       case Smoke:
/* 339 */         GradientOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.moreGradientOutline.getValue()).floatValue(), ((Float)this.creepyOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/*     */         
/* 341 */         GradientOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case RainbowCube:
/* 348 */         AstralOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.redOutline
/* 349 */             .getValue()).floatValue(), ((Float)this.greenOutline.getValue()).floatValue(), ((Float)this.blueOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.iterationsOutline
/* 350 */             .getValue()).intValue(), ((Float)this.formuparam2Outline.getValue()).floatValue(), ((Float)this.zoomOutline.getValue()).floatValue(), ((Integer)this.volumStepsOutline.getValue()).intValue(), ((Float)this.stepSizeOutline.getValue()).floatValue(), ((Float)this.titleOutline.getValue()).floatValue(), ((Float)this.distfadingOutline.getValue()).floatValue(), ((Float)this.saturationOutline
/* 351 */             .getValue()).floatValue(), 0.0F, ((Boolean)this.fadeOutline.getValue()).booleanValue() ? 1 : 0);
/* 352 */         AstralOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       
/*     */       case Gradient:
/* 356 */         AquaOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.MaxIterOutline.getValue()).intValue(), ((Float)this.tauOutline.getValue()).floatValue());
/*     */ 
/*     */         
/* 359 */         AquaOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       
/*     */       case Fill:
/* 363 */         CircleOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.PIOutline.getValue()).floatValue(), ((Float)this.radOutline.getValue()).floatValue());
/*     */         
/* 365 */         CircleOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       
/*     */       case Circle:
/* 369 */         SmokeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.secondColorImgOutline.getValue()).getColorObject(), ((ColorSetting)this.thirdColorImgOutline.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/*     */         
/* 371 */         SmokeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 376 */     GlStateManager.func_179084_k();
/* 377 */     GlStateManager.func_179118_c();
/* 378 */     GlStateManager.func_179097_i();
/* 379 */     GlStateManager.func_179099_b();
/* 380 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   Predicate<Boolean> getFill() {
/*     */     Color col;
/* 384 */     Predicate<Boolean> output = a -> true;
/*     */     
/* 386 */     switch ((fillShadermode)this.fillShader.getValue()) {
/*     */       case Astral:
/* 388 */         output = (a -> {
/*     */             FlowShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.redFill.getValue()).floatValue(), ((Float)this.greenFill.getValue()).floatValue(), ((Float)this.blueFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.iterationsFill.getValue()).intValue(), ((Float)this.formuparam2Fill.getValue()).floatValue(), ((Float)this.zoomFill.getValue()).floatValue(), ((Float)this.volumStepsFill.getValue()).floatValue(), ((Float)this.stepSizeFill.getValue()).floatValue(), ((Float)this.titleFill.getValue()).floatValue(), ((Float)this.distfadingFill.getValue()).floatValue(), ((Float)this.saturationFill.getValue()).floatValue(), 0.0F, ((Boolean)this.fadeFill.getValue()).booleanValue() ? 1 : 0);
/*     */ 
/*     */             
/*     */             return true;
/*     */           });
/*     */         
/* 395 */         FlowShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Aqua:
/* 398 */         output = (a -> {
/*     */             AquaShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/*     */             return true;
/*     */           });
/* 402 */         AquaShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Smoke:
/* 405 */         output = (a -> {
/*     */             SmokeShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((ColorSetting)this.secondcolorImgFill.getValue()).getColorObject(), ((ColorSetting)this.thirdcolorImgFill.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/*     */             return true;
/*     */           });
/* 409 */         SmokeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case RainbowCube:
/* 412 */         output = (a -> {
/*     */             RainbowCubeShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.WaveLenghtFIll.getValue()).intValue(), ((Integer)this.RSTARTFill.getValue()).intValue(), ((Integer)this.GSTARTFill.getValue()).intValue(), ((Integer)this.BSTARTFIll.getValue()).intValue());
/*     */             return true;
/*     */           });
/* 416 */         RainbowCubeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Gradient:
/* 419 */         output = (a -> {
/*     */             GradientShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((Float)this.moreGradientFill.getValue()).floatValue(), ((Float)this.creepyFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/*     */             return true;
/*     */           });
/* 423 */         GradientShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Fill:
/* 426 */         col = ((ColorSetting)this.colorImgFill.getValue()).getColorObject();
/* 427 */         output = (a -> {
/*     */             FillShader.INSTANCE.startShader(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
/*     */             return false;
/*     */           });
/* 431 */         FillShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Circle:
/* 434 */         output = (a -> {
/*     */             CircleShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), (Float)this.PI.getValue(), (Float)this.rad.getValue());
/*     */             return true;
/*     */           });
/* 438 */         CircleShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Phobos:
/* 441 */         output = (a -> {
/*     */             PhobosShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/*     */             return true;
/*     */           });
/* 445 */         PhobosShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */     } 
/* 448 */     return output;
/*     */   }
/*     */   
/*     */   public enum fillShadermode {
/* 452 */     Astral, Aqua, Smoke, RainbowCube, Gradient, Fill, Circle, Phobos, None;
/*     */   }
/*     */   
/*     */   public enum glowESPmode
/*     */   {
/* 457 */     None, Color, Astral, RainbowCube, Gradient, Circle, Smoke, Aqua;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\ItemShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */