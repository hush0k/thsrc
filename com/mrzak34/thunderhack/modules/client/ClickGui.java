/*     */ package com.mrzak34.thunderhack.modules.client;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.clickui.ClickUI;
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.clickui.Colors;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClickGui
/*     */   extends Module
/*     */ {
/*  22 */   private static ClickGui INSTANCE = new ClickGui();
/*  23 */   public final Setting<ColorSetting> hcolor1 = register(new Setting("MainColor", new ColorSetting(-6974059)));
/*  24 */   public final Setting<ColorSetting> acolor = register(new Setting("MainColor2", new ColorSetting(-8365735)));
/*  25 */   public final Setting<ColorSetting> plateColor = register(new Setting("PlateColor", new ColorSetting(-14474718)));
/*  26 */   public final Setting<ColorSetting> catColor = register(new Setting("CategoryColor", new ColorSetting(-15395563)));
/*  27 */   public final Setting<ColorSetting> downColor = register(new Setting("DownColor", new ColorSetting(-14474461)));
/*  28 */   public Setting<Integer> colorSpeed = register(new Setting("ColorSpeed", Integer.valueOf(18), Integer.valueOf(2), Integer.valueOf(54)));
/*  29 */   public Setting<Boolean> showBinds = register(new Setting("ShowBinds", Boolean.valueOf(true)));
/*  30 */   private final Setting<colorModeEn> colorMode = register(new Setting("ColorMode", colorModeEn.Static));
/*  31 */   private final Setting<Moderator> shader = register(new Setting("shader", Moderator.none));
/*     */   
/*     */   public ClickGui() {
/*  34 */     super("ClickGui", "кликгуи", Module.Category.CLIENT);
/*  35 */     setInstance();
/*     */   }
/*     */   
/*     */   public static ClickGui getInstance() {
/*  39 */     if (INSTANCE == null) {
/*  40 */       INSTANCE = new ClickGui();
/*     */     }
/*  42 */     return INSTANCE;
/*     */   }
/*     */   public Color getColor(int count) {
/*     */     int val;
/*     */     Color analogous;
/*  47 */     int index = count;
/*  48 */     switch ((colorModeEn)this.colorMode.getValue()) {
/*     */       case Sky:
/*  50 */         return ColorUtil.skyRainbow(((Integer)this.colorSpeed.getValue()).intValue(), index);
/*     */       case LightRainbow:
/*  52 */         return ColorUtil.rainbow(((Integer)this.colorSpeed.getValue()).intValue(), index, 0.6F, 1.0F, 1.0F);
/*     */       
/*     */       case Rainbow:
/*  55 */         return ColorUtil.rainbow(((Integer)this.colorSpeed.getValue()).intValue(), index, 1.0F, 1.0F, 1.0F);
/*     */       
/*     */       case Fade:
/*  58 */         return ColorUtil.fade(((Integer)this.colorSpeed.getValue()).intValue(), index, ((ColorSetting)this.hcolor1.getValue()).getColorObject(), 1.0F);
/*     */       
/*     */       case DoubleColor:
/*  61 */         return ColorUtil.interpolateColorsBackAndForth(((Integer)this.colorSpeed.getValue()).intValue(), index, ((ColorSetting)this.hcolor1
/*  62 */             .getValue()).getColorObject(), Colors.ALTERNATE_COLOR, true);
/*     */       case Analogous:
/*  64 */         val = 1;
/*  65 */         analogous = ColorUtil.getAnalogousColor(((ColorSetting)this.acolor.getValue()).getColorObject())[val];
/*  66 */         return ColorUtil.interpolateColorsBackAndForth(((Integer)this.colorSpeed.getValue()).intValue(), index, ((ColorSetting)this.hcolor1.getValue()).getColorObject(), analogous, true);
/*     */     } 
/*  68 */     return ((ColorSetting)this.hcolor1.getValue()).getColorObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  75 */     Util.mc.func_147108_a((GuiScreen)ClickUI.getClickGui());
/*     */   }
/*     */ 
/*     */   
/*     */   private void setInstance() {
/*  80 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  85 */     if (fullNullCheck())
/*  86 */       return;  if (this.shader.getValue() != Moderator.none)
/*     */     {
/*  88 */       if (OpenGlHelper.field_148824_g && mc.func_175606_aa() instanceof net.minecraft.entity.player.EntityPlayer) {
/*  89 */         if (mc.field_71460_t.func_147706_e() != null) {
/*  90 */           mc.field_71460_t.func_147706_e().func_148021_a();
/*     */         }
/*     */         try {
/*  93 */           mc.field_71460_t.func_175069_a(new ResourceLocation("shaders/post/" + this.shader.getValue() + ".json"));
/*  94 */         } catch (Exception e) {
/*  95 */           e.printStackTrace();
/*     */         } 
/*  97 */       } else if (mc.field_71460_t.func_147706_e() != null && mc.field_71462_r == null) {
/*  98 */         mc.field_71460_t.func_147706_e().func_148021_a();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent event) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 111 */     if (mc.field_71460_t.func_147706_e() != null) {
/* 112 */       mc.field_71460_t.func_147706_e().func_148021_a();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 119 */     if (!(mc.field_71462_r instanceof ClickUI))
/* 120 */       disable(); 
/*     */   }
/*     */   
/*     */   public enum colorModeEn
/*     */   {
/* 125 */     Static,
/* 126 */     Sky,
/* 127 */     LightRainbow,
/* 128 */     Rainbow,
/* 129 */     Fade,
/* 130 */     DoubleColor,
/* 131 */     Analogous;
/*     */   }
/*     */   
/*     */   public enum Moderator {
/* 135 */     none,
/* 136 */     notch,
/* 137 */     antialias,
/* 138 */     art,
/* 139 */     bits,
/* 140 */     blobs,
/* 141 */     blobs2,
/* 142 */     blur,
/* 143 */     bumpy,
/* 144 */     color_convolve,
/* 145 */     creeper,
/* 146 */     deconverge,
/* 147 */     desaturate,
/* 148 */     flip,
/* 149 */     fxaa,
/* 150 */     green,
/* 151 */     invert,
/* 152 */     ntsc,
/* 153 */     pencil,
/* 154 */     phosphor,
/* 155 */     sobel,
/* 156 */     spider,
/* 157 */     wobble;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\ClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */