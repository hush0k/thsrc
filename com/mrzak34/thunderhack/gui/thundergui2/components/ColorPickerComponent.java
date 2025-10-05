/*     */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
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
/*     */ public class ColorPickerComponent
/*     */   extends SettingElement
/*     */ {
/*     */   private final Setting colorSetting;
/*     */   private boolean open;
/*     */   private float hue;
/*     */   private float saturation;
/*     */   private float brightness;
/*     */   private int alpha;
/*     */   private boolean afocused;
/*     */   private boolean hfocused;
/*     */   private boolean sbfocused;
/*     */   private boolean copy_focused;
/*     */   private boolean paste_focused;
/*     */   private boolean rainbow_focused;
/*     */   private float spos;
/*     */   private float bpos;
/*     */   private float hpos;
/*     */   private float apos;
/*     */   private Color prevColor;
/*     */   private boolean firstInit;
/*     */   
/*     */   public ColorPickerComponent(Setting setting) {
/*  39 */     super(setting);
/*  40 */     this.colorSetting = setting;
/*  41 */     this.firstInit = true;
/*     */   }
/*     */   
/*     */   public ColorSetting getColorSetting() {
/*  45 */     return (ColorSetting)this.colorSetting.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float delta) {
/*  50 */     super.render(mouseX, mouseY, delta);
/*  51 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/*  54 */     FontRender.drawString6(getSetting().getName(), (float)getX(), (float)getY() + 5.0F, isHovered() ? -1 : (new Color(-1325400065, true)).getRGB(), false);
/*  55 */     Drawable.drawBlurredShadow((int)(this.x + this.width - 20.0D), (int)(this.y + 5.0D), 14.0F, 6.0F, 10, getColorSetting().getColorObject());
/*  56 */     RoundedShader.drawRound((float)(this.x + this.width - 20.0D), (float)(this.y + 5.0D), 14.0F, 6.0F, 1.0F, getColorSetting().getColorObject());
/*  57 */     if (this.open) {
/*  58 */       renderPicker(mouseX, mouseY, getColorSetting().getColorObject());
/*     */     }
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  63 */     super.onTick();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderPicker(int mouseX, int mouseY, Color color) {
/*  68 */     double cx = this.x + 6.0D;
/*  69 */     double cy = this.y + 20.0D;
/*  70 */     double cw = this.width - 38.0D;
/*  71 */     double ch = this.height - 20.0D;
/*     */     
/*  73 */     if (this.prevColor != getColorSetting().getColorObject()) {
/*  74 */       updatePos();
/*  75 */       this.prevColor = getColorSetting().getColorObject();
/*     */     } 
/*     */     
/*  78 */     if (this.firstInit) {
/*  79 */       this.spos = (float)(cx + cw - cw - cw * this.saturation);
/*  80 */       this.bpos = (float)(cy + ch - ch * this.brightness);
/*  81 */       this.hpos = (float)(cy + ch - 3.0D + (ch - 3.0D) * this.hue);
/*  82 */       this.apos = (float)(cy + ch - 3.0D - (ch - 3.0D) * (this.alpha / 255.0F));
/*  83 */       this.firstInit = false;
/*     */     } 
/*     */     
/*  86 */     this.spos = RenderUtil.scrollAnimate(this.spos, (float)(cx + 40.0D + cw - 40.0D - cw - 40.0D - (cw - 40.0D) * this.saturation), 0.6F);
/*  87 */     this.bpos = RenderUtil.scrollAnimate(this.bpos, (float)(cy + ch - ch * this.brightness), 0.6F);
/*  88 */     this.hpos = RenderUtil.scrollAnimate(this.hpos, (float)(cy + ch - 3.0D + (ch - 3.0D) * this.hue), 0.6F);
/*  89 */     this.apos = RenderUtil.scrollAnimate(this.apos, (float)(cy + ch - 3.0D - (ch - 3.0D) * (this.alpha / 255.0F)), 0.6F);
/*     */     
/*  91 */     Color colorA = Color.getHSBColor(this.hue, 0.0F, 1.0F), colorB = Color.getHSBColor(this.hue, 1.0F, 1.0F);
/*  92 */     Color colorC = new Color(0, 0, 0, 0), colorD = new Color(0, 0, 0);
/*     */     
/*  94 */     Drawable.horizontalGradient(cx + 40.0D, cy, cx + cw, cy + ch, colorA.getRGB(), colorB.getRGB());
/*  95 */     Drawable.verticalGradient(cx + 40.0D, cy, cx + cw, cy + ch, colorC.getRGB(), colorD.getRGB());
/*     */     float i;
/*  97 */     for (i = 1.0F; i < ch - 2.0D; i++) {
/*  98 */       float curHue = (float)(1.0D / ch / i);
/*  99 */       Drawable.drawRectWH(cx + cw + 4.0D, cy + i, 8.0D, 1.0D, Color.getHSBColor(curHue, 1.0F, 1.0F).getRGB());
/*     */     } 
/*     */     
/* 102 */     Drawable.drawRectWH(cx + cw + 17.0D, cy + 1.0D, 8.0D, ch - 3.0D, -1);
/*     */     
/* 104 */     Drawable.verticalGradient(cx + cw + 17.0D, cy + 0.8D, cx + cw + 25.0D, cy + ch - 2.0D, (new Color(color.getRed(), color.getGreen(), color.getBlue(), 255)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */     
/* 106 */     Drawable.drawRectWH(cx + cw + 3.0D, (this.hpos + 0.5F), 10.0D, 1.0D, -1);
/* 107 */     Drawable.drawRectWH(cx + cw + 16.0D, (this.apos + 0.5F), 10.0D, 1.0D, -1);
/* 108 */     RoundedShader.drawRoundOutline(this.spos, this.bpos, 3.0F, 3.0F, 1.5F, 1.0F, color, new Color(-1));
/*     */     
/* 110 */     Color value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
/*     */     
/* 112 */     if (this.sbfocused) {
/* 113 */       this.saturation = (float)(MathUtil.clamp(mouseX - cx + 40.0D, 0.0D, cw - 40.0D) / (cw - 40.0D));
/*     */       
/* 115 */       this.brightness = (float)((ch - MathUtil.clamp(mouseY - cy, 0.0D, ch)) / ch);
/* 116 */       value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
/* 117 */       setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
/*     */     } 
/*     */     
/* 120 */     if (this.hfocused) {
/* 121 */       this.hue = (float)-((ch - MathUtil.clamp((float)(mouseY - cy), 0.0F, (float)ch)) / ch);
/* 122 */       value = Color.getHSBColor(this.hue, this.saturation, this.brightness);
/* 123 */       setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
/*     */     } 
/*     */     
/* 126 */     if (this.afocused) {
/* 127 */       this.alpha = (int)((ch - MathUtil.clamp((float)(mouseY - cy), 0.0F, (float)ch)) / ch * 255.0D);
/* 128 */       setColor(new Color(value.getRed(), value.getGreen(), value.getBlue(), this.alpha));
/*     */     } 
/*     */     
/* 131 */     this.rainbow_focused = Drawable.isHovered(mouseX, mouseY, getX(), cy, 40.0D, 10.0D);
/* 132 */     this.copy_focused = Drawable.isHovered(mouseX, mouseY, getX(), cy + 13.0D, 40.0D, 10.0D);
/* 133 */     this.paste_focused = Drawable.isHovered(mouseX, mouseY, getX(), cy + 26.0D, 40.0D, 10.0D);
/*     */     
/* 135 */     RoundedShader.drawRound((float)getX(), (float)cy, 40.0F, 10.0F, 2.0F, getColorSetting().isCycle() ? new Color(86, 63, 105, 250) : (this.rainbow_focused ? new Color(66, 48, 80, 250) : new Color(50, 35, 60, 250)));
/* 136 */     RoundedShader.drawRound((float)getX(), (float)cy + 13.0F, 40.0F, 10.0F, 2.0F, this.copy_focused ? new Color(66, 48, 80, 250) : new Color(50, 35, 60, 250));
/* 137 */     RoundedShader.drawRound((float)getX(), (float)cy + 26.0F, 40.0F, 9.5F, 2.0F, this.paste_focused ? new Color(66, 48, 80, 250) : new Color(50, 35, 60, 250));
/*     */     
/* 139 */     FontRender.drawCentString6("rainbow", (float)getX() + 20.0F, (float)cy + 4.0F, this.rainbow_focused ? -1 : (getColorSetting().isCycle() ? getColorSetting().getColor() : (new Color(-1241513985, true)).getRGB()));
/* 140 */     FontRender.drawCentString6("copy", (float)getX() + 20.0F, (float)cy + 16.5F, this.copy_focused ? -1 : (new Color(-1241513985, true)).getRGB());
/* 141 */     FontRender.drawCentString6("paste", (float)getX() + 20.0F, (float)cy + 29.5F, this.paste_focused ? -1 : (new Color(-1241513985, true)).getRGB());
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePos() {
/* 146 */     float[] hsb = Color.RGBtoHSB(getColorSetting().getColorObject().getRed(), getColorSetting().getColorObject().getGreen(), getColorSetting().getColorObject().getBlue(), null);
/* 147 */     this.hue = -1.0F + hsb[0];
/* 148 */     this.saturation = hsb[1];
/* 149 */     this.brightness = hsb[2];
/* 150 */     this.alpha = getColorSetting().getColorObject().getAlpha();
/*     */   }
/*     */   
/*     */   private void setColor(Color color) {
/* 154 */     getColorSetting().setColor(color.getRGB());
/* 155 */     this.prevColor = color;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 160 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/* 163 */     double cx = this.x + 4.0D;
/* 164 */     double cy = this.y + 21.0D;
/* 165 */     double cw = this.width - 34.0D;
/* 166 */     double ch = this.height - 20.0D;
/*     */     
/* 168 */     if (Drawable.isHovered(mouseX, mouseY, this.x + this.width - 20.0D, this.y + 5.0D, 14.0D, 6.0D)) {
/* 169 */       this.open = !this.open;
/*     */     }
/* 171 */     if (!this.open) {
/*     */       return;
/*     */     }
/* 174 */     if (Drawable.isHovered(mouseX, mouseY, cx + cw + 17.0D, cy, 8.0D, ch) && button == 0) {
/* 175 */       this.afocused = true;
/*     */     }
/* 177 */     else if (Drawable.isHovered(mouseX, mouseY, cx + cw + 4.0D, cy, 8.0D, ch) && button == 0) {
/* 178 */       this.hfocused = true;
/*     */     }
/* 180 */     else if (Drawable.isHovered(mouseX, mouseY, cx + 40.0D, cy, cw - 40.0D, ch) && button == 0) {
/* 181 */       this.sbfocused = true;
/*     */     } 
/*     */     
/* 184 */     if (this.rainbow_focused) getColorSetting().setCycle(!getColorSetting().isCycle()); 
/* 185 */     if (this.copy_focused) Thunderhack.copy_color = getColorSetting().getColorObject(); 
/* 186 */     if (this.paste_focused) {
/* 187 */       setColor((Thunderhack.copy_color == null) ? getColorSetting().getColorObject() : Thunderhack.copy_color);
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int button) {
/* 192 */     this.hfocused = false;
/* 193 */     this.afocused = false;
/* 194 */     this.sbfocused = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 199 */     this.hfocused = false;
/* 200 */     this.afocused = false;
/* 201 */     this.sbfocused = false;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 205 */     return this.open;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\ColorPickerComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */