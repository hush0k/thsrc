/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.AquaShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.CircleShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.FillShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.FlowShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.GradientShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.PhobosShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.RainbowCubeShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.fill.SmokeShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.outline.AquaOutlineShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.outline.AstralOutlineShader;
/*     */ import com.mrzak34.thunderhack.util.shaders.impl.outline.SmokeOutlineShader;
/*     */ import java.awt.Color;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ 
/*     */ public class Shaders extends Module {
/*  22 */   private final Setting<ColorSetting> colorImgOutline = register(new Setting("colorImgOutline", new ColorSetting(-2013200640)));
/*  23 */   private final Setting<ColorSetting> secondColorImgOutline = register(new Setting("secondColorImgOutline", new ColorSetting(-2013200640)));
/*  24 */   private final Setting<ColorSetting> thirdColorImgOutline = register(new Setting("thirdColorImgOutline", new ColorSetting(-2013200640)));
/*  25 */   private final Setting<ColorSetting> colorESP = register(new Setting("colorESP", new ColorSetting(-2013200640)));
/*  26 */   private final Setting<ColorSetting> colorImgFill = register(new Setting("colorImgFill", new ColorSetting(-2013200640)));
/*  27 */   private final Setting<ColorSetting> thirdColorImgFIll = register(new Setting("thirdcolorImgFill", new ColorSetting(-2013200640)));
/*  28 */   private final Setting<ColorSetting> secondColorImgFill = register(new Setting("thirdcolorImgFill", new ColorSetting(-2013200640)));
/*  29 */   public Setting<Float> duplicateOutline = register(new Setting("duplicateOutline", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  30 */   public Setting<Float> duplicateFill = register(new Setting("Duplicate Fill", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*  31 */   public Setting<Float> speedOutline = register(new Setting("Speed Outline", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(100.0F)));
/*  32 */   public Setting<Float> speedFill = register(new Setting("Speed Fill", Float.valueOf(10.0F), Float.valueOf(1.0F), Float.valueOf(100.0F)));
/*  33 */   public Setting<Integer> maxEntities = register(new Setting("Max Entities", Integer.valueOf(100), Integer.valueOf(10), Integer.valueOf(500)));
/*  34 */   public Setting<Float> quality = register(new Setting("quality", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  35 */   public Setting<Float> radius = register(new Setting("radius", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*     */   public boolean renderTags = true;
/*     */   public boolean renderCape = true;
/*  38 */   private final Setting<fillShadermode> fillShader = register(new Setting("Fill Shader", fillShadermode.None));
/*  39 */   public Setting<Float> rad = register(new Setting("RAD Fill", Float.valueOf(0.75F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.fillShader.getValue() == fillShadermode.Circle)));
/*  40 */   public Setting<Float> PI = register(new Setting("PI Fill", Float.valueOf(3.1415927F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.fillShader.getValue() == fillShadermode.Circle)));
/*  41 */   public Setting<Float> saturationFill = register(new Setting("saturation", Float.valueOf(0.4F), Float.valueOf(0.0F), Float.valueOf(3.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  42 */   public Setting<Float> distfadingFill = register(new Setting("distfading", Float.valueOf(0.56F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  43 */   public Setting<Float> titleFill = register(new Setting("Tile", Float.valueOf(0.45F), Float.valueOf(0.0F), Float.valueOf(1.3F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  44 */   public Setting<Float> stepSizeFill = register(new Setting("Step Size", Float.valueOf(0.2F), Float.valueOf(0.0F), Float.valueOf(0.7F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  45 */   public Setting<Float> volumStepsFill = register(new Setting("Volum Steps", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  46 */   public Setting<Float> zoomFill = register(new Setting("Zoom", Float.valueOf(3.9F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  47 */   public Setting<Float> formuparam2Fill = register(new Setting("formuparam2", Float.valueOf(0.89F), Float.valueOf(0.0F), Float.valueOf(1.5F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  48 */   public Setting<Integer> iterationsFill = register(new Setting("Iteration", Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(20), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  49 */   public Setting<Integer> redFill = register(new Setting("Tick Regen", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(100), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  50 */   public Setting<Integer> MaxIterFill = register(new Setting("Max Iter", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(30), v -> (this.fillShader.getValue() == fillShadermode.Aqua)));
/*  51 */   public Setting<Integer> NUM_OCTAVESFill = register(new Setting("NUM_OCTAVES", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(30), v -> (this.fillShader.getValue() == fillShadermode.Smoke)));
/*  52 */   public Setting<Integer> BSTARTFIll = register(new Setting("BSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  53 */   public Setting<Integer> GSTARTFill = register(new Setting("GSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  54 */   public Setting<Integer> RSTARTFill = register(new Setting("RSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  55 */   public Setting<Integer> WaveLenghtFIll = register(new Setting("Wave Lenght", Integer.valueOf(555), Integer.valueOf(0), Integer.valueOf(2000), v -> (this.fillShader.getValue() == fillShadermode.RainbowCube)));
/*  56 */   public Setting<Float> alphaFill = register(new Setting("AlphaF", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral || this.fillShader.getValue() == fillShadermode.Smoke)));
/*  57 */   public Setting<Float> blueFill = register(new Setting("BlueF", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  58 */   public Setting<Float> greenFill = register(new Setting("GreenF", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.fillShader.getValue() == fillShadermode.Astral)));
/*  59 */   public Setting<Float> tauFill = register(new Setting("TAU", Float.valueOf(6.2831855F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.fillShader.getValue() == fillShadermode.Aqua)));
/*  60 */   public Setting<Float> creepyFill = register(new Setting("Creepy", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.fillShader.getValue() == fillShadermode.Smoke)));
/*  61 */   public Setting<Float> moreGradientFill = register(new Setting("More Gradient", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.fillShader.getValue() == fillShadermode.Smoke)));
/*  62 */   private final Setting<glowESPmode> glowESP = register(new Setting("Glow ESP", glowESPmode.None));
/*  63 */   public Setting<Float> saturationOutline = register(new Setting("saturation", Float.valueOf(0.4F), Float.valueOf(0.0F), Float.valueOf(3.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  64 */   public Setting<Integer> volumStepsOutline = register(new Setting("Volum Steps", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(10), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  65 */   public Setting<Integer> iterationsOutline = register(new Setting("Iteration", Integer.valueOf(4), Integer.valueOf(3), Integer.valueOf(20), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  66 */   public Setting<Integer> redOutline = register(new Setting("Red", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(100), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  67 */   public Setting<Integer> MaxIterOutline = register(new Setting("Max Iter", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(30), v -> (this.glowESP.getValue() == glowESPmode.Aqua)));
/*  68 */   public Setting<Integer> NUM_OCTAVESOutline = register(new Setting("NUM_OCTAVES", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(30), v -> (this.glowESP.getValue() == glowESPmode.Smoke)));
/*  69 */   public Setting<Integer> BSTARTOutline = register(new Setting("BSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  70 */   public Setting<Integer> GSTARTOutline = register(new Setting("GSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  71 */   public Setting<Integer> RSTARTOutline = register(new Setting("RSTART", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  72 */   public Setting<Integer> WaveLenghtOutline = register(new Setting("Wave Lenght", Integer.valueOf(555), Integer.valueOf(0), Integer.valueOf(2000), v -> (this.glowESP.getValue() == glowESPmode.RainbowCube)));
/*  73 */   public Setting<Float> speesaturationOutlined = register(new Setting("saturation", Float.valueOf(0.4F), Float.valueOf(0.0F), Float.valueOf(3.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  74 */   public Setting<Float> distfadingOutline = register(new Setting("distfading", Float.valueOf(0.56F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  75 */   public Setting<Float> titleOutline = register(new Setting("Tile", Float.valueOf(0.45F), Float.valueOf(0.0F), Float.valueOf(1.3F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  76 */   public Setting<Float> stepSizeOutline = register(new Setting("Step Size", Float.valueOf(0.19F), Float.valueOf(0.0F), Float.valueOf(0.7F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  77 */   public Setting<Float> zoomOutline = register(new Setting("Zoom", Float.valueOf(3.9F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  78 */   public Setting<Float> formuparam2Outline = register(new Setting("formuparam2", Float.valueOf(0.89F), Float.valueOf(0.0F), Float.valueOf(1.5F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  79 */   public Setting<Float> alphaOutline = register(new Setting("Alpha", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral || this.glowESP.getValue() == glowESPmode.Gradient)));
/*  80 */   public Setting<Float> blueOutline = register(new Setting("Blue", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  81 */   public Setting<Float> greenOutline = register(new Setting("Green", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.glowESP.getValue() == glowESPmode.Astral)));
/*  82 */   public Setting<Float> tauOutline = register(new Setting("TAU", Float.valueOf(6.2831855F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.glowESP.getValue() == glowESPmode.Aqua)));
/*  83 */   public Setting<Float> creepyOutline = register(new Setting("Creepy", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.glowESP.getValue() == glowESPmode.Gradient)));
/*  84 */   public Setting<Float> moreGradientOutline = register(new Setting("More Gradient", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.glowESP.getValue() == glowESPmode.Gradient)));
/*  85 */   public Setting<Float> radOutline = register(new Setting("RAD Outline", Float.valueOf(0.75F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.glowESP.getValue() == glowESPmode.Circle)));
/*  86 */   public Setting<Float> PIOutline = register(new Setting("PI Outline", Float.valueOf(3.1415927F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.glowESP.getValue() == glowESPmode.Circle)));
/*  87 */   private final Setting<Boolean> rangeCheck = register(new Setting("Range Check", Boolean.valueOf(true)));
/*  88 */   public Setting<Float> maxRange = register(new Setting("Max Range", Float.valueOf(20.0F), Float.valueOf(10.0F), Float.valueOf(100.0F), v -> ((Boolean)this.rangeCheck.getValue()).booleanValue()));
/*  89 */   public Setting<Float> minRange = register(new Setting("Min range", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> ((Boolean)this.rangeCheck.getValue()).booleanValue()));
/*  90 */   private final Setting<Boolean> arrowOutline = register(new Setting("Arrow Outline", Boolean.valueOf(false)));
/*  91 */   private final Setting<Boolean> enderPerleOutline = register(new Setting("EnderPerle Outline", Boolean.valueOf(false)));
/*  92 */   private final Setting<Boolean> minecartTntOutline = register(new Setting("MinecartTnt Outline", Boolean.valueOf(false)));
/*  93 */   private final Setting<Boolean> boatOutline = register(new Setting("Boat Outline", Boolean.valueOf(false)));
/*  94 */   private final Setting<Boolean> bottleOutline = register(new Setting("Bottle Outline", Boolean.valueOf(false)));
/*  95 */   private final Setting<Boolean> xpOutline = register(new Setting("XP Outline", Boolean.valueOf(false)));
/*  96 */   private final Setting<Boolean> crystalsOutline = register(new Setting("Crystals Outline", Boolean.valueOf(false)));
/*  97 */   private final Setting<Boolean> playersOutline = register(new Setting("Players Outline", Boolean.valueOf(false)));
/*  98 */   private final Setting<Boolean> mobsOutline = register(new Setting("Mobs Outline", Boolean.valueOf(false)));
/*  99 */   private final Setting<Boolean> itemsOutline = register(new Setting("Items Outline", Boolean.valueOf(false)));
/* 100 */   private final Setting<Boolean> arrowFill = register(new Setting("Arrow Fill", Boolean.valueOf(false)));
/* 101 */   private final Setting<Boolean> enderPerleFill = register(new Setting("EnderPerle Fill", Boolean.valueOf(false)));
/* 102 */   private final Setting<Boolean> minecartFill = register(new Setting("MinecartTnt Fill", Boolean.valueOf(false)));
/* 103 */   private final Setting<Boolean> boatFill = register(new Setting("Boat Fill", Boolean.valueOf(false)));
/* 104 */   private final Setting<Boolean> bottleFill = register(new Setting("Bottle Fill", Boolean.valueOf(false)));
/* 105 */   private final Setting<Boolean> xpFill = register(new Setting("XP Fill", Boolean.valueOf(false)));
/* 106 */   private final Setting<Boolean> crystalsFill = register(new Setting("Crystals Fill", Boolean.valueOf(false)));
/* 107 */   private final Setting<Boolean> playersFill = register(new Setting("Players Fill", Boolean.valueOf(false)));
/* 108 */   private final Setting<Boolean> mobsFill = register(new Setting("Mobs Fill", Boolean.valueOf(false)));
/* 109 */   private final Setting<Boolean> itemsFill = register(new Setting("Items Fill", Boolean.valueOf(false)));
/*     */   
/* 111 */   private final Setting<Boolean> fadeFill = register(new Setting("Fade Fill", Boolean.valueOf(false)));
/*     */   
/* 113 */   private final Setting<Boolean> fadeOutline = register(new Setting("FadeOL Fill", Boolean.valueOf(false)));
/* 114 */   private final Setting<Boolean> GradientAlpha = register(new Setting("Gradient Alpha", Boolean.valueOf(false)));
/* 115 */   public Setting<Integer> alphaValue = register(new Setting("Alpha Outline", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255), v -> !((Boolean)this.GradientAlpha.getValue()).booleanValue()));
/*     */   public Shaders() {
/* 117 */     super("Shaders", "Шейдеры", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderGameOverlay(RenderGameOverlayEvent event) {
/* 122 */     if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
/* 123 */       if (mc.field_71441_e == null || mc.field_71439_g == null) {
/*     */         return;
/*     */       }
/*     */       
/* 127 */       GlStateManager.func_179094_E();
/* 128 */       this.renderTags = false;
/* 129 */       this.renderCape = false;
/*     */       
/* 131 */       if (this.glowESP.getValue() != glowESPmode.None && this.fillShader.getValue() != fillShadermode.None) {
/*     */         
/* 133 */         switch ((fillShadermode)this.fillShader.getValue()) {
/*     */           case Color:
/* 135 */             FlowShader.INSTANCE.startDraw(event.getPartialTicks());
/* 136 */             renderPlayersFill(event.getPartialTicks());
/* 137 */             FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.redFill
/* 138 */                 .getValue()).floatValue(), ((Float)this.greenFill.getValue()).floatValue(), ((Float)this.blueFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.iterationsFill
/* 139 */                 .getValue()).intValue(), ((Float)this.formuparam2Fill.getValue()).floatValue(), ((Float)this.zoomFill.getValue()).floatValue(), ((Float)this.volumStepsFill.getValue()).floatValue(), ((Float)this.stepSizeFill.getValue()).floatValue(), ((Float)this.titleFill.getValue()).floatValue(), ((Float)this.distfadingFill.getValue()).floatValue(), ((Float)this.saturationFill
/* 140 */                 .getValue()).floatValue(), 0.0F, ((Boolean)this.fadeFill.getValue()).booleanValue() ? 1 : 0);
/* 141 */             FlowShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case RainbowCube:
/* 144 */             AquaShader.INSTANCE.startDraw(event.getPartialTicks());
/* 145 */             renderPlayersFill(event.getPartialTicks());
/* 146 */             AquaShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/* 147 */             AquaShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Gradient:
/* 150 */             SmokeShader.INSTANCE.startDraw(event.getPartialTicks());
/* 151 */             renderPlayersFill(event.getPartialTicks());
/* 152 */             SmokeShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((ColorSetting)this.secondColorImgFill.getValue()).getColorObject(), ((ColorSetting)this.thirdColorImgFIll.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/* 153 */             SmokeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Astral:
/* 156 */             RainbowCubeShader.INSTANCE.startDraw(event.getPartialTicks());
/* 157 */             renderPlayersFill(event.getPartialTicks());
/* 158 */             RainbowCubeShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.WaveLenghtFIll.getValue()).intValue(), ((Integer)this.RSTARTFill.getValue()).intValue(), ((Integer)this.GSTARTFill.getValue()).intValue(), ((Integer)this.BSTARTFIll.getValue()).intValue());
/* 159 */             RainbowCubeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Aqua:
/* 162 */             GradientShader.INSTANCE.startDraw(event.getPartialTicks());
/* 163 */             renderPlayersFill(event.getPartialTicks());
/* 164 */             GradientShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Float)this.moreGradientFill.getValue()).floatValue(), ((Float)this.creepyFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/* 165 */             GradientShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Circle:
/* 168 */             FillShader.INSTANCE.startDraw(event.getPartialTicks());
/* 169 */             renderPlayersFill(event.getPartialTicks());
/* 170 */             FillShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject());
/* 171 */             FillShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Smoke:
/* 174 */             CircleShader.INSTANCE.startDraw(event.getPartialTicks());
/* 175 */             renderPlayersFill(event.getPartialTicks());
/* 176 */             CircleShader.INSTANCE.stopDraw(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), (Float)this.PI.getValue(), (Float)this.rad.getValue());
/* 177 */             CircleShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case null:
/* 180 */             PhobosShader.INSTANCE.startDraw(event.getPartialTicks());
/* 181 */             renderPlayersFill(event.getPartialTicks());
/* 182 */             PhobosShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/* 183 */             PhobosShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */         } 
/* 186 */         switch ((glowESPmode)this.glowESP.getValue()) {
/*     */           case Color:
/* 188 */             GlowShader.INSTANCE.startDraw(event.getPartialTicks());
/* 189 */             renderPlayersOutline(event.getPartialTicks());
/* 190 */             GlowShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue());
/*     */             break;
/*     */           case RainbowCube:
/* 193 */             RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 194 */             renderPlayersOutline(event.getPartialTicks());
/* 195 */             RainbowCubeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.colorImgOutline.getValue()).getColorObject(), ((Integer)this.WaveLenghtOutline.getValue()).intValue(), ((Integer)this.RSTARTOutline.getValue()).intValue(), ((Integer)this.GSTARTOutline.getValue()).intValue(), ((Integer)this.BSTARTOutline.getValue()).intValue());
/* 196 */             RainbowCubeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Gradient:
/* 199 */             GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 200 */             renderPlayersOutline(event.getPartialTicks());
/* 201 */             GradientOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.moreGradientOutline.getValue()).floatValue(), ((Float)this.creepyOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/* 202 */             GradientOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Astral:
/* 205 */             AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 206 */             renderPlayersOutline(event.getPartialTicks());
/* 207 */             AstralOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.redOutline
/* 208 */                 .getValue()).floatValue(), ((Float)this.greenOutline.getValue()).floatValue(), ((Float)this.blueOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.iterationsOutline
/* 209 */                 .getValue()).intValue(), ((Float)this.formuparam2Outline.getValue()).floatValue(), ((Float)this.zoomOutline.getValue()).floatValue(), ((Integer)this.volumStepsOutline.getValue()).intValue(), ((Float)this.stepSizeOutline.getValue()).floatValue(), ((Float)this.titleOutline.getValue()).floatValue(), ((Float)this.distfadingOutline.getValue()).floatValue(), ((Float)this.saturationOutline
/* 210 */                 .getValue()).floatValue(), 0.0F, ((Boolean)this.fadeOutline.getValue()).booleanValue() ? 1 : 0);
/* 211 */             AstralOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Aqua:
/* 214 */             AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 215 */             renderPlayersOutline(event.getPartialTicks());
/* 216 */             AquaOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.MaxIterOutline.getValue()).intValue(), ((Float)this.tauOutline.getValue()).floatValue());
/* 217 */             AquaOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Circle:
/* 220 */             CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 221 */             renderPlayersOutline(event.getPartialTicks());
/* 222 */             CircleOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.PIOutline.getValue()).floatValue(), ((Float)this.radOutline.getValue()).floatValue());
/* 223 */             CircleOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Smoke:
/* 226 */             SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 227 */             renderPlayersOutline(event.getPartialTicks());
/* 228 */             SmokeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.secondColorImgOutline.getValue()).getColorObject(), ((ColorSetting)this.thirdColorImgOutline.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/* 229 */             SmokeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */         } 
/*     */       
/*     */       } else {
/* 234 */         switch ((glowESPmode)this.glowESP.getValue()) {
/*     */           case Color:
/* 236 */             GlowShader.INSTANCE.startDraw(event.getPartialTicks());
/* 237 */             renderPlayersOutline(event.getPartialTicks());
/* 238 */             GlowShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue());
/*     */             break;
/*     */           case RainbowCube:
/* 241 */             RainbowCubeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 242 */             renderPlayersOutline(event.getPartialTicks());
/* 243 */             RainbowCubeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.colorImgOutline.getValue()).getColorObject(), ((Integer)this.WaveLenghtOutline.getValue()).intValue(), ((Integer)this.RSTARTOutline.getValue()).intValue(), ((Integer)this.GSTARTOutline.getValue()).intValue(), ((Integer)this.BSTARTOutline.getValue()).intValue());
/* 244 */             RainbowCubeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Gradient:
/* 247 */             GradientOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 248 */             renderPlayersOutline(event.getPartialTicks());
/* 249 */             GradientOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.moreGradientOutline.getValue()).floatValue(), ((Float)this.creepyOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/* 250 */             GradientOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Astral:
/* 253 */             AstralOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 254 */             renderPlayersOutline(event.getPartialTicks());
/* 255 */             AstralOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.redOutline
/* 256 */                 .getValue()).floatValue(), ((Float)this.greenOutline.getValue()).floatValue(), ((Float)this.blueOutline.getValue()).floatValue(), ((Float)this.alphaOutline.getValue()).floatValue(), ((Integer)this.iterationsOutline
/* 257 */                 .getValue()).intValue(), ((Float)this.formuparam2Outline.getValue()).floatValue(), ((Float)this.zoomOutline.getValue()).floatValue(), ((Integer)this.volumStepsOutline.getValue()).intValue(), ((Float)this.stepSizeOutline.getValue()).floatValue(), ((Float)this.titleOutline.getValue()).floatValue(), ((Float)this.distfadingOutline.getValue()).floatValue(), ((Float)this.saturationOutline
/* 258 */                 .getValue()).floatValue(), 0.0F, ((Boolean)this.fadeOutline.getValue()).booleanValue() ? 1 : 0);
/* 259 */             AstralOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Aqua:
/* 262 */             AquaOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 263 */             renderPlayersOutline(event.getPartialTicks());
/* 264 */             AquaOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Integer)this.MaxIterOutline.getValue()).intValue(), ((Float)this.tauOutline.getValue()).floatValue());
/* 265 */             AquaOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Circle:
/* 268 */             CircleOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 269 */             renderPlayersOutline(event.getPartialTicks());
/* 270 */             CircleOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((Float)this.PIOutline.getValue()).floatValue(), ((Float)this.radOutline.getValue()).floatValue());
/* 271 */             CircleOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Smoke:
/* 274 */             SmokeOutlineShader.INSTANCE.startDraw(event.getPartialTicks());
/* 275 */             renderPlayersOutline(event.getPartialTicks());
/* 276 */             SmokeOutlineShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), ((Float)this.radius.getValue()).floatValue(), ((Float)this.quality.getValue()).floatValue(), ((Boolean)this.GradientAlpha.getValue()).booleanValue(), ((Integer)this.alphaValue.getValue()).intValue(), ((Float)this.duplicateOutline.getValue()).floatValue(), ((ColorSetting)this.secondColorImgOutline.getValue()).getColorObject(), ((ColorSetting)this.thirdColorImgOutline.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESOutline.getValue()).intValue());
/* 277 */             SmokeOutlineShader.INSTANCE.update((((Float)this.speedOutline.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */         } 
/*     */         
/* 281 */         switch ((fillShadermode)this.fillShader.getValue()) {
/*     */           case Color:
/* 283 */             FlowShader.INSTANCE.startDraw(event.getPartialTicks());
/* 284 */             renderPlayersFill(event.getPartialTicks());
/* 285 */             FlowShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.redFill
/* 286 */                 .getValue()).floatValue(), ((Float)this.greenFill.getValue()).floatValue(), ((Float)this.blueFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.iterationsFill
/* 287 */                 .getValue()).intValue(), ((Float)this.formuparam2Fill.getValue()).floatValue(), ((Float)this.zoomFill.getValue()).floatValue(), ((Float)this.volumStepsFill.getValue()).floatValue(), ((Float)this.stepSizeFill.getValue()).floatValue(), ((Float)this.titleFill.getValue()).floatValue(), ((Float)this.distfadingFill.getValue()).floatValue(), ((Float)this.saturationFill
/* 288 */                 .getValue()).floatValue(), 0.0F, ((Boolean)this.fadeFill.getValue()).booleanValue() ? 1 : 0);
/* 289 */             FlowShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case RainbowCube:
/* 292 */             AquaShader.INSTANCE.startDraw(event.getPartialTicks());
/* 293 */             renderPlayersFill(event.getPartialTicks());
/* 294 */             AquaShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/* 295 */             AquaShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Gradient:
/* 298 */             SmokeShader.INSTANCE.startDraw(event.getPartialTicks());
/* 299 */             renderPlayersFill(event.getPartialTicks());
/* 300 */             SmokeShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((ColorSetting)this.secondColorImgFill.getValue()).getColorObject(), ((ColorSetting)this.thirdColorImgFIll.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/* 301 */             SmokeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Astral:
/* 304 */             RainbowCubeShader.INSTANCE.startDraw(event.getPartialTicks());
/* 305 */             renderPlayersFill(event.getPartialTicks());
/* 306 */             RainbowCubeShader.INSTANCE.stopDraw(Color.WHITE, 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.WaveLenghtFIll.getValue()).intValue(), ((Integer)this.RSTARTFill.getValue()).intValue(), ((Integer)this.GSTARTFill.getValue()).intValue(), ((Integer)this.BSTARTFIll.getValue()).intValue());
/* 307 */             RainbowCubeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Aqua:
/* 310 */             GradientShader.INSTANCE.startDraw(event.getPartialTicks());
/* 311 */             renderPlayersFill(event.getPartialTicks());
/* 312 */             GradientShader.INSTANCE.stopDraw(((ColorSetting)this.colorESP.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Float)this.moreGradientFill.getValue()).floatValue(), ((Float)this.creepyFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/* 313 */             GradientShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Circle:
/* 316 */             FillShader.INSTANCE.startDraw(event.getPartialTicks());
/* 317 */             renderPlayersFill(event.getPartialTicks());
/* 318 */             FillShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject());
/* 319 */             FillShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case Smoke:
/* 322 */             CircleShader.INSTANCE.startDraw(event.getPartialTicks());
/* 323 */             renderPlayersFill(event.getPartialTicks());
/* 324 */             CircleShader.INSTANCE.stopDraw(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), (Float)this.PI.getValue(), (Float)this.rad.getValue());
/* 325 */             CircleShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */           case null:
/* 328 */             PhobosShader.INSTANCE.startDraw(event.getPartialTicks());
/* 329 */             renderPlayersFill(event.getPartialTicks());
/* 330 */             PhobosShader.INSTANCE.stopDraw(((ColorSetting)this.colorImgFill.getValue()).getColorObject(), 1.0F, 1.0F, ((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/* 331 */             PhobosShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 337 */       this.renderTags = true;
/* 338 */       this.renderCape = true;
/*     */       
/* 340 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   Predicate<Boolean> getFill() {
/*     */     Color col;
/* 346 */     Predicate<Boolean> output = a -> true;
/*     */     
/* 348 */     switch ((fillShadermode)this.fillShader.getValue()) {
/*     */       case Color:
/* 350 */         output = (a -> {
/*     */             FlowShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((Integer)this.redFill.getValue()).floatValue(), ((Float)this.greenFill.getValue()).floatValue(), ((Float)this.blueFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.iterationsFill.getValue()).intValue(), ((Float)this.formuparam2Fill.getValue()).floatValue(), ((Float)this.zoomFill.getValue()).floatValue(), ((Float)this.volumStepsFill.getValue()).floatValue(), ((Float)this.stepSizeFill.getValue()).floatValue(), ((Float)this.titleFill.getValue()).floatValue(), ((Float)this.distfadingFill.getValue()).floatValue(), ((Float)this.saturationFill.getValue()).floatValue(), 0.0F, ((Boolean)this.fadeFill.getValue()).booleanValue() ? 1 : 0);
/*     */ 
/*     */             
/*     */             return true;
/*     */           });
/*     */         
/* 357 */         FlowShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case RainbowCube:
/* 360 */         output = (a -> {
/*     */             AquaShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/*     */             return true;
/*     */           });
/* 364 */         AquaShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Gradient:
/* 367 */         output = (a -> {
/*     */             SmokeShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((ColorSetting)this.secondColorImgFill.getValue()).getColorObject(), ((ColorSetting)this.thirdColorImgFIll.getValue()).getColorObject(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/*     */             return true;
/*     */           });
/* 371 */         SmokeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Astral:
/* 374 */         output = (a -> {
/*     */             RainbowCubeShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.WaveLenghtFIll.getValue()).intValue(), ((Integer)this.RSTARTFill.getValue()).intValue(), ((Integer)this.GSTARTFill.getValue()).intValue(), ((Integer)this.BSTARTFIll.getValue()).intValue());
/*     */             return true;
/*     */           });
/* 378 */         RainbowCubeShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Aqua:
/* 381 */         output = (a -> {
/*     */             GradientShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((Float)this.moreGradientFill.getValue()).floatValue(), ((Float)this.creepyFill.getValue()).floatValue(), ((Float)this.alphaFill.getValue()).floatValue(), ((Integer)this.NUM_OCTAVESFill.getValue()).intValue());
/*     */             return true;
/*     */           });
/* 385 */         GradientShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */ 
/*     */       
/*     */       case Circle:
/* 390 */         col = ((ColorSetting)this.colorImgFill.getValue()).getColorObject();
/* 391 */         output = (a -> {
/*     */             FillShader.INSTANCE.startShader(col.getRed() / 255.0F, col.getGreen() / 255.0F, col.getBlue() / 255.0F, col.getAlpha() / 255.0F);
/*     */             return false;
/*     */           });
/* 395 */         FillShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case Smoke:
/* 398 */         output = (a -> {
/*     */             CircleShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), (Float)this.PI.getValue(), (Float)this.rad.getValue());
/*     */             return true;
/*     */           });
/* 402 */         CircleShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */       case null:
/* 405 */         output = (a -> {
/*     */             PhobosShader.INSTANCE.startShader(((Float)this.duplicateFill.getValue()).floatValue(), ((ColorSetting)this.colorImgFill.getValue()).getColorObject(), ((Integer)this.MaxIterFill.getValue()).intValue(), ((Float)this.tauFill.getValue()).floatValue());
/*     */             return true;
/*     */           });
/* 409 */         PhobosShader.INSTANCE.update((((Float)this.speedFill.getValue()).floatValue() / 1000.0F));
/*     */         break;
/*     */     } 
/* 412 */     return output;
/*     */   }
/*     */   
/*     */   void renderPlayersFill(float tick) {
/* 416 */     boolean rangeCheck = ((Boolean)this.rangeCheck.getValue()).booleanValue();
/* 417 */     double minRange = (((Float)this.minRange.getValue()).floatValue() * ((Float)this.minRange.getValue()).floatValue());
/* 418 */     double maxRange = (((Float)this.maxRange.getValue()).floatValue() * ((Float)this.maxRange.getValue()).floatValue());
/* 419 */     AtomicInteger nEntities = new AtomicInteger();
/* 420 */     int maxEntities = ((Integer)this.maxEntities.getValue()).intValue();
/*     */     try {
/* 422 */       mc.field_71441_e.field_72996_f.stream().filter(e -> {
/*     */             if (nEntities.getAndIncrement() > maxEntities)
/*     */               return false; 
/*     */             if (e instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */               if (((Boolean)this.playersFill.getValue()).booleanValue())
/* 427 */                 return (e != mc.field_71439_g || mc.field_71474_y.field_74320_O != 0); 
/*     */             } else {
/*     */               if (e instanceof net.minecraft.entity.item.EntityItem)
/*     */                 return ((Boolean)this.itemsFill.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.EntityCreature)
/*     */                 return ((Boolean)this.mobsFill.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityEnderCrystal)
/*     */                 return ((Boolean)this.crystalsFill.getValue()).booleanValue(); 
/*     */               if (e instanceof EntityXPOrb)
/*     */                 return ((Boolean)this.xpFill.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityExpBottle)
/*     */                 return ((Boolean)this.bottleFill.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityBoat)
/*     */                 return ((Boolean)this.boatFill.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityMinecart)
/*     */                 return ((Boolean)this.minecartFill.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityEnderPearl)
/*     */                 return ((Boolean)this.enderPerleFill.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.projectile.EntityArrow)
/*     */                 return ((Boolean)this.arrowFill.getValue()).booleanValue(); 
/*     */             } 
/*     */             return false;
/* 449 */           }).filter(e -> {
/*     */             if (!rangeCheck) {
/*     */               return true;
/*     */             }
/*     */             double distancePl = mc.field_71439_g.func_70068_e(e);
/* 454 */             return (distancePl > minRange && distancePl < maxRange);
/*     */           
/* 456 */           }).forEach(e -> mc.func_175598_ae().func_188388_a(e, tick, true));
/* 457 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void renderPlayersOutline(float tick) {
/*     */     try {
/* 464 */       boolean rangeCheck = ((Boolean)this.rangeCheck.getValue()).booleanValue();
/* 465 */       double minRange = (((Float)this.minRange.getValue()).floatValue() * ((Float)this.minRange.getValue()).floatValue());
/* 466 */       double maxRange = (((Float)this.maxRange.getValue()).floatValue() * ((Float)this.maxRange.getValue()).floatValue());
/* 467 */       AtomicInteger nEntities = new AtomicInteger();
/* 468 */       int maxEntities = ((Integer)this.maxEntities.getValue()).intValue();
/* 469 */       mc.field_71441_e.func_73027_a(-1000, (Entity)new EntityXPOrb((World)mc.field_71441_e, mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1000000.0D, mc.field_71439_g.field_70161_v, 1));
/* 470 */       mc.field_71441_e.field_72996_f.stream().filter(e -> {
/*     */             if (nEntities.getAndIncrement() > maxEntities)
/*     */               return false; 
/*     */             if (e instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */               if (((Boolean)this.playersOutline.getValue()).booleanValue())
/* 475 */                 return (e != mc.field_71439_g || mc.field_71474_y.field_74320_O != 0); 
/*     */             } else {
/*     */               if (e instanceof net.minecraft.entity.item.EntityItem)
/*     */                 return ((Boolean)this.itemsOutline.getValue()).booleanValue();  if (e instanceof net.minecraft.entity.EntityCreature)
/*     */                 return ((Boolean)this.mobsOutline.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityEnderCrystal)
/*     */                 return ((Boolean)this.crystalsOutline.getValue()).booleanValue(); 
/*     */               if (e instanceof EntityXPOrb) {
/* 483 */                 return (((Boolean)this.xpOutline.getValue()).booleanValue() || e.func_145782_y() == -1000);
/*     */               }
/*     */               if (e instanceof net.minecraft.entity.item.EntityExpBottle)
/*     */                 return ((Boolean)this.bottleOutline.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityBoat)
/*     */                 return ((Boolean)this.boatOutline.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityMinecart)
/*     */                 return ((Boolean)this.minecartTntOutline.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.item.EntityEnderPearl)
/*     */                 return ((Boolean)this.enderPerleOutline.getValue()).booleanValue(); 
/*     */               if (e instanceof net.minecraft.entity.projectile.EntityArrow)
/*     */                 return ((Boolean)this.arrowOutline.getValue()).booleanValue(); 
/*     */             } 
/*     */             return false;
/* 497 */           }).filter(e -> {
/*     */             if (!rangeCheck) {
/*     */               return true;
/*     */             }
/*     */             double distancePl = mc.field_71439_g.func_70068_e(e);
/* 502 */             return ((distancePl > minRange && distancePl < maxRange) || e.func_145782_y() == -1000);
/*     */           
/* 504 */           }).forEach(e -> mc.func_175598_ae().func_188388_a(e, tick, true));
/* 505 */       mc.field_71441_e.func_73028_b(-1000);
/* 506 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum fillShadermode
/*     */   {
/* 513 */     Astral, Aqua, Smoke, RainbowCube, Gradient, Fill, Circle, Phobos, None;
/*     */   }
/*     */   
/*     */   public enum glowESPmode {
/* 517 */     None, Color, Astral, RainbowCube, Gradient, Circle, Smoke, Aqua;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Shaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */