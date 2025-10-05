/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.render.DrawHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Potions
/*     */   extends HudElement
/*     */ {
/*  33 */   int zLevel = 0;
/*  34 */   private final Setting<Modes> mode = register(new Setting("Mode", Modes.New));
/*  35 */   public final Setting<ColorSetting> color = register(new Setting("WexColor", new ColorSetting(-2013200640), v -> (this.mode.getValue() != Modes.New)));
/*  36 */   public Setting<Float> grange = register(new Setting("GlowRange", Float.valueOf(3.6F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.mode.getValue() == Modes.Wexside)));
/*  37 */   public Setting<Float> gmult = register(new Setting("GlowMultiplier", Float.valueOf(3.6F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.mode.getValue() == Modes.Wexside)));
/*  38 */   public final Setting<ColorSetting> shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528), v -> (this.mode.getValue() == Modes.New)));
/*  39 */   public final Setting<ColorSetting> color2 = register(new Setting("Color", new ColorSetting(-15724528), v -> (this.mode.getValue() == Modes.New)));
/*  40 */   public final Setting<ColorSetting> color3 = register(new Setting("Color2", new ColorSetting(-979657829), v -> (this.mode.getValue() == Modes.New)));
/*  41 */   public final Setting<ColorSetting> textColor = register(new Setting("TextColor", new ColorSetting(12500670), v -> (this.mode.getValue() == Modes.New)));
/*  42 */   public final Setting<ColorSetting> oncolor = register(new Setting("TextColor", new ColorSetting(12500670), v -> (this.mode.getValue() == Modes.New)));
/*     */ 
/*     */   
/*     */   public Potions() {
/*  46 */     super("Potions", "Potions", 100, 100);
/*     */   }
/*     */   
/*     */   public static String getDuration(PotionEffect potionEffect) {
/*  50 */     if (potionEffect.func_100011_g()) {
/*  51 */       return "**:**";
/*     */     }
/*  53 */     return StringUtils.func_76337_a(potionEffect.func_76459_b());
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/*  59 */     super.onRender2D(e);
/*  60 */     if (this.mode.getValue() == Modes.New) {
/*  61 */       drawNew();
/*     */     } else {
/*  63 */       drawWexside(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawNew() {
/*  68 */     int y_offset1 = 0;
/*  69 */     ArrayList<PotionEffect> effects = new ArrayList<>();
/*     */     
/*  71 */     for (PotionEffect potionEffect : mc.field_71439_g.func_70651_bq()) {
/*  72 */       if (potionEffect.func_76459_b() != 0 && !potionEffect.func_188419_a().func_76393_a().contains("effect.nightVision")) {
/*  73 */         effects.add(potionEffect);
/*  74 */         y_offset1 += 10;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  79 */     GlStateManager.func_179094_E();
/*     */     
/*  81 */     RenderUtil.drawBlurredShadow(getPosX(), getPosY(), 100.0F, (20 + y_offset1), 20, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/*     */ 
/*     */     
/*  84 */     RoundedShader.drawRound(getPosX(), getPosY(), 100.0F, (20 + y_offset1), 7.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/*  85 */     FontRender.drawCentString6("Potions", getPosX() + 50.0F, getPosY() + 5.0F, ((ColorSetting)this.textColor.getValue()).getColor());
/*  86 */     RoundedShader.drawRound(getPosX() + 2.0F, getPosY() + 13.0F, 96.0F, 1.0F, 0.5F, ((ColorSetting)this.color3.getValue()).getColorObject());
/*     */     
/*  88 */     int y_offset = 0;
/*     */ 
/*     */     
/*  91 */     for (PotionEffect potionEffect : effects) {
/*  92 */       Potion potion = potionEffect.func_188419_a();
/*  93 */       String power = "";
/*  94 */       if (potionEffect.func_76458_c() == 0) {
/*  95 */         power = "I";
/*  96 */       } else if (potionEffect.func_76458_c() == 1) {
/*  97 */         power = "II";
/*  98 */       } else if (potionEffect.func_76458_c() == 2) {
/*  99 */         power = "III";
/* 100 */       } else if (potionEffect.func_76458_c() == 3) {
/* 101 */         power = "IV";
/* 102 */       } else if (potionEffect.func_76458_c() == 4) {
/* 103 */         power = "V";
/*     */       } 
/* 105 */       String s = potionEffect.func_188419_a().func_76393_a().replace("effect.", "") + " " + power;
/* 106 */       String s2 = getDuration(potionEffect) + "";
/*     */ 
/*     */       
/* 109 */       GlStateManager.func_179094_E();
/* 110 */       GlStateManager.func_179117_G();
/* 111 */       FontRender.drawString6(s + "  " + s2, getPosX() + 5.0F, getPosY() + 20.0F + y_offset, ((ColorSetting)this.oncolor.getValue()).getColor(), false);
/* 112 */       GlStateManager.func_179117_G();
/* 113 */       GlStateManager.func_179121_F();
/* 114 */       y_offset += 10;
/*     */     } 
/*     */ 
/*     */     
/* 118 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawWexside(Render2DEvent e) {
/* 123 */     int i = 0;
/*     */ 
/*     */     
/* 126 */     ArrayList<PotionEffect> effects = new ArrayList<>();
/* 127 */     for (PotionEffect potionEffect : mc.field_71439_g.func_70651_bq()) {
/* 128 */       if (potionEffect.func_76459_b() != 0 && !potionEffect.func_188419_a().func_76393_a().contains("effect.nightVision")) {
/* 129 */         effects.add(potionEffect);
/*     */       }
/*     */     } 
/* 132 */     int j = e.scaledResolution.func_78328_b() / 2 - effects.size() * 24 / 2;
/* 133 */     for (PotionEffect potionEffect : effects) {
/* 134 */       Potion potion = potionEffect.func_188419_a();
/* 135 */       String power = "";
/* 136 */       if (potionEffect.func_76458_c() == 0) {
/* 137 */         power = "I";
/* 138 */       } else if (potionEffect.func_76458_c() == 1) {
/* 139 */         power = "II";
/* 140 */       } else if (potionEffect.func_76458_c() == 2) {
/* 141 */         power = "III";
/* 142 */       } else if (potionEffect.func_76458_c() == 3) {
/* 143 */         power = "IV";
/* 144 */       } else if (potionEffect.func_76458_c() == 4) {
/* 145 */         power = "V";
/*     */       } 
/* 147 */       String s = I18n.func_135052_a(potionEffect.func_188419_a().func_76393_a(), new Object[0]) + " " + power;
/* 148 */       String s2 = getDuration(potionEffect) + "";
/* 149 */       float maxWidth = (Math.max(FontRender.getStringWidth6(s), FontRender.getStringWidth6(s2)) + 32);
/*     */       
/* 151 */       DrawHelper.drawRectWithGlow((i + 2), (j + 5), (maxWidth - 4.0F + i + 2.0F), (18.5F + j + 5.0F), ((Float)this.grange.getValue()).floatValue(), ((Float)this.gmult.getValue()).floatValue(), ((ColorSetting)this.color.getValue()).getColorObject());
/* 152 */       mc.func_110434_K().func_110577_a(GuiContainer.field_147001_a);
/* 153 */       if (potion.func_76400_d()) {
/* 154 */         int i1 = potion.func_76392_e();
/* 155 */         drawTexturedModalRect(i + 5, j + 7, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
/*     */       } 
/* 157 */       FontRender.drawString6(s, (i + 28), j + 11.5F, (new Color(205, 205, 205, 205)).getRGB(), false);
/* 158 */       FontRender.drawString6(s2, (i + 28), j + 18.5F, (new Color(205, 205, 205, 205)).getRGB(), false);
/* 159 */       j += 24;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 164 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 165 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 166 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 167 */     bufferbuilder.func_181662_b(x, (y + height), this.zLevel).func_187315_a((textureX * 0.00390625F), ((textureY + height) * 0.00390625F)).func_181675_d();
/* 168 */     bufferbuilder.func_181662_b((x + width), (y + height), this.zLevel).func_187315_a(((textureX + width) * 0.00390625F), ((textureY + height) * 0.00390625F)).func_181675_d();
/* 169 */     bufferbuilder.func_181662_b((x + width), y, this.zLevel).func_187315_a(((textureX + width) * 0.00390625F), (textureY * 0.00390625F)).func_181675_d();
/* 170 */     bufferbuilder.func_181662_b(x, y, this.zLevel).func_187315_a((textureX * 0.00390625F), (textureY * 0.00390625F)).func_181675_d();
/* 171 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public enum Modes {
/* 175 */     Wexside, New;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\Potions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */