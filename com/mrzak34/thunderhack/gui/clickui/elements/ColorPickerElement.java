/*     */ package com.mrzak34.thunderhack.gui.clickui.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorPickerElement
/*     */   extends AbstractElement
/*     */ {
/*     */   private float hue;
/*     */   private float saturation;
/*     */   private float brightness;
/*     */   private int alpha;
/*     */   private boolean afocused;
/*     */   private boolean hfocused;
/*     */   private boolean sbfocused;
/*     */   private float spos;
/*     */   private float bpos;
/*     */   private float hpos;
/*     */   private float apos;
/*     */   private Color prevColor;
/*     */   private boolean firstInit;
/*     */   private final Setting colorSetting;
/*     */   
/*     */   public ColorPickerElement(Setting setting) {
/*  34 */     super(setting);
/*  35 */     this.colorSetting = setting;
/*  36 */     this.prevColor = getColorSetting().getColorObject();
/*  37 */     updatePos();
/*  38 */     this.firstInit = true;
/*     */   }
/*     */   
/*     */   public ColorSetting getColorSetting() {
/*  42 */     return (ColorSetting)this.colorSetting.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float delta) {
/*  47 */     RoundedShader.drawRound((float)this.x + 2.0F, (float)this.y + 2.0F, (float)this.width - 4.0F, (float)this.height - 4.0F, 4.0F, new Color(this.bgcolor));
/*  48 */     FontRender.drawString5(this.setting.getName(), (float)this.x + 4.0F, (float)this.y + 5.0F, -1);
/*     */     
/*  50 */     Drawable.drawBlurredShadow((int)(this.x + this.width - 20.0D), (int)(this.y + 5.0D), 14.0F, 6.0F, 10, getColorSetting().getColorObject());
/*  51 */     RoundedShader.drawRound((float)(this.x + this.width - 20.0D), (float)(this.y + 5.0D), 14.0F, 6.0F, 1.0F, getColorSetting().getColorObject());
/*     */     
/*  53 */     if (!getColorSetting().isCycle()) {
/*  54 */       FontRender.drawString5("R", (float)(this.x + this.width - 30.0D), (float)(this.y + 7.0D), (new Color(4737096)).getRGB());
/*     */     } else {
/*  56 */       FontRender.drawString5("R", (float)(this.x + this.width - 30.0D), (float)(this.y + 7.0D), -1);
/*     */     } 
/*     */ 
/*     */     
/*  60 */     renderPicker(mouseX, mouseY, getColorSetting().getColorObject());
/*     */   }
/*     */   
/*     */   private void renderPicker(int mouseX, int mouseY, Color color) {
/*  64 */     double cx = this.x + 6.0D;
/*  65 */     double cy = this.y + 16.0D;
/*  66 */     double cw = this.width - 38.0D;
/*  67 */     double ch = this.height - 20.0D;
/*     */     
/*  69 */     if (this.prevColor != getColorSetting().getColorObject()) {
/*  70 */       updatePos();
/*  71 */       this.prevColor = getColorSetting().getColorObject();
/*     */     } 
/*     */     
/*  74 */     if (this.firstInit) {
/*  75 */       this.spos = (float)(cx + cw - cw - cw * this.saturation);
/*  76 */       this.bpos = (float)(cy + ch - ch * this.brightness);
/*  77 */       this.hpos = (float)(cy + ch - 3.0D + (ch - 3.0D) * this.hue);
/*  78 */       this.apos = (float)(cy + ch - 3.0D - (ch - 3.0D) * (this.alpha / 255.0F));
/*  79 */       this.firstInit = false;
/*     */     } 
/*     */     
/*  82 */     this.spos = RenderUtil.scrollAnimate(this.spos, (float)(cx + cw - cw - cw * this.saturation), 0.6F);
/*  83 */     this.bpos = RenderUtil.scrollAnimate(this.bpos, (float)(cy + ch - ch * this.brightness), 0.6F);
/*  84 */     this.hpos = RenderUtil.scrollAnimate(this.hpos, (float)(cy + ch - 3.0D + (ch - 3.0D) * this.hue), 0.6F);
/*  85 */     this.apos = RenderUtil.scrollAnimate(this.apos, (float)(cy + ch - 3.0D - (ch - 3.0D) * (this.alpha / 255.0F)), 0.6F);
/*     */     
/*  87 */     Color colorA = Color.getHSBColor(this.hue, 0.0F, 1.0F), colorB = Color.getHSBColor(this.hue, 1.0F, 1.0F);
/*  88 */     Color colorC = new Color(0, 0, 0, 0), colorD = new Color(0, 0, 0);
/*     */     
/*  90 */     Drawable.horizontalGradient((float)cx, (float)cy, cx + cw, cy + ch, colorA.getRGB(), colorB.getRGB());
/*  91 */     Drawable.verticalGradient(cx, cy, cx + cw, cy + ch, colorC.getRGB(), colorD.getRGB());
/*     */     float i;
/*  93 */     for (i = 1.0F; i < ch - 2.0D; i++) {
/*  94 */       float curHue = (float)(1.0D / ch / i);
/*  95 */       Drawable.drawRectWH(cx + cw + 4.0D, cy + i, 8.0D, 1.0D, Color.getHSBColor(curHue, 1.0F, 1.0F).getRGB());
/*     */     } 
/*     */     
/*  98 */     Drawable.drawRectWH(cx + cw + 17.0D, cy + 1.0D, 8.0D, ch - 3.0D, -1);
/*     */     
/* 100 */     Drawable.verticalGradient(cx + cw + 17.0D, cy + 0.8D, cx + cw + 25.0D, cy + ch - 2.0D, (new Color(color
/* 101 */           .getRed(), color.getGreen(), color.getBlue(), 255)).getRGB(), (new Color(0, 0, 0, 0))
/* 102 */         .getRGB());
/*     */     
/* 104 */     Drawable.drawRectWH(cx + cw + 3.0D, (this.hpos + 0.5F), 10.0D, 1.0D, -1);
/* 105 */     Drawable.drawRectWH(cx + cw + 16.0D, (this.apos + 0.5F), 10.0D, 1.0D, -1);
/* 106 */     RoundedShader.drawRoundOutline(this.spos, this.bpos, 3.0F, 3.0F, 1.5F, 1.0F, color, new Color(-1));
/*     */     
/* 108 */     Color value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
/*     */     
/* 110 */     if (this.sbfocused) {
/* 111 */       this.saturation = (float)(MathUtil.clamp((float)(mouseX - cx), 0.0F, (float)cw) / cw);
/* 112 */       this.brightness = (float)((ch - MathUtil.clamp((float)(mouseY - cy), 0.0F, (float)ch)) / ch);
/* 113 */       value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
/* 114 */       setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
/*     */     } 
/*     */     
/* 117 */     if (this.hfocused) {
/* 118 */       this.hue = (float)-((ch - MathUtil.clamp((float)(mouseY - cy), 0.0F, (float)ch)) / ch);
/* 119 */       value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
/* 120 */       setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
/*     */     } 
/*     */     
/* 123 */     if (this.afocused) {
/* 124 */       this.alpha = (int)((ch - MathUtil.clamp((float)(mouseY - cy), 0.0F, (float)ch)) / ch * 255.0D);
/* 125 */       setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updatePos() {
/* 130 */     float[] hsb = Color.RGBtoHSB(getColorSetting().getColorObject().getRed(), getColorSetting().getColorObject().getGreen(), getColorSetting().getColorObject().getBlue(), null);
/* 131 */     this.hue = -1.0F + hsb[0];
/* 132 */     this.saturation = hsb[1];
/* 133 */     this.brightness = hsb[2];
/* 134 */     this.alpha = getColorSetting().getColorObject().getAlpha();
/*     */   }
/*     */   
/*     */   private void setColor(Color color) {
/* 138 */     getColorSetting().setColor(color.getRGB());
/* 139 */     this.prevColor = color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 144 */     double cx = this.x + 4.0D;
/* 145 */     double cy = this.y + 17.0D;
/* 146 */     double cw = this.width - 34.0D;
/* 147 */     double ch = this.height - 20.0D;
/*     */     
/* 149 */     if (Drawable.isHovered(mouseX, mouseY, cx + cw + 17.0D, cy, 8.0D, ch) && button == 0) {
/* 150 */       this.afocused = true;
/*     */     }
/* 152 */     else if (Drawable.isHovered(mouseX, mouseY, cx + cw + 4.0D, cy, 8.0D, ch) && button == 0) {
/* 153 */       this.hfocused = true;
/*     */     }
/* 155 */     else if (Drawable.isHovered(mouseX, mouseY, cx, cy, cw, ch) && button == 0) {
/* 156 */       this.sbfocused = true;
/*     */     }
/* 158 */     else if (Drawable.isHovered(mouseX, mouseY, (float)(this.x + this.width - 30.0D), (float)(this.y + 7.0D), 10.0D, 10.0D) && button == 0) {
/* 159 */       getColorSetting().setCycle(!getColorSetting().isCycle());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int button) {
/* 164 */     this.hfocused = false;
/* 165 */     this.afocused = false;
/* 166 */     this.sbfocused = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 171 */     this.hfocused = false;
/* 172 */     this.afocused = false;
/* 173 */     this.sbfocused = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\elements\ColorPickerElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */