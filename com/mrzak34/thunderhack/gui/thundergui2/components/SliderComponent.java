/*     */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class SliderComponent
/*     */   extends SettingElement
/*     */ {
/*     */   private final float min;
/*     */   private final float max;
/*     */   public boolean listening;
/*  19 */   public String Stringnumber = "";
/*     */   
/*     */   private float animation;
/*     */   private double stranimation;
/*     */   private boolean dragging;
/*     */   
/*     */   public SliderComponent(Setting setting) {
/*  26 */     super(setting);
/*  27 */     this.min = ((Number)setting.getMin()).floatValue();
/*  28 */     this.max = ((Number)setting.getMax()).floatValue();
/*     */   }
/*     */   
/*     */   public static String removeLastChar(String str) {
/*  32 */     String output = "";
/*  33 */     if (str != null && str.length() > 0) {
/*  34 */       output = str.substring(0, str.length() - 1);
/*     */     }
/*  36 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float partialTicks) {
/*  41 */     super.render(mouseX, mouseY, partialTicks);
/*  42 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/*     */     
/*  46 */     FontRender.drawString6(getSetting().getName(), (float)getX(), (float)getY() + 5.0F, isHovered() ? -1 : (new Color(-1325400065, true)).getRGB(), false);
/*     */     
/*  48 */     double currentPos = ((((Number)this.setting.getValue()).floatValue() - this.min) / (this.max - this.min));
/*  49 */     this.stranimation += ((((Number)this.setting.getValue()).floatValue() * 100.0F / 100.0F) - this.stranimation) / 2.0D;
/*  50 */     this.animation = RenderUtil.scrollAnimate(this.animation, (float)currentPos, 0.5F);
/*     */     
/*  52 */     Color color = new Color(-1973791);
/*  53 */     RoundedShader.drawRound((float)(this.x + 54.0D), (float)(this.y + this.height - 8.0D), 90.0F, 1.0F, 0.5F, new Color(-15856114));
/*  54 */     RoundedShader.drawRound((float)(this.x + 54.0D), (float)(this.y + this.height - 8.0D), 90.0F * this.animation, 1.0F, 0.5F, color);
/*  55 */     RoundedShader.drawRound((float)(this.x + 52.0D + (90.0F * this.animation)), (float)(this.y + this.height - 9.5D), 4.0F, 4.0F, 1.5F, color);
/*     */     
/*  57 */     if (mouseX > this.x + 154.0D && mouseX < this.x + 176.0D && mouseY > this.y + this.height - 11.0D && mouseY < this.y + this.height - 4.0D) {
/*  58 */       RoundedShader.drawRound((float)(this.x + 154.0D), (float)(this.y + this.height - 11.0D), 22.0F, 7.0F, 0.5F, new Color(82, 57, 100, 178));
/*     */     } else {
/*  60 */       RoundedShader.drawRound((float)(this.x + 154.0D), (float)(this.y + this.height - 11.0D), 22.0F, 7.0F, 0.5F, new Color(50, 35, 60, 178));
/*     */     } 
/*     */     
/*  63 */     if (!this.listening) {
/*  64 */       if (this.setting.getValue() instanceof Float)
/*  65 */         FontRender.drawString6(String.valueOf(MathUtil.round(((Float)this.setting.getValue()).floatValue(), 2)), (float)(this.x + 156.0D), (float)(this.y + this.height - 9.0D), (new Color(-1157627905, true)).getRGB(), false); 
/*  66 */       if (this.setting.getValue() instanceof Integer) {
/*  67 */         FontRender.drawString6(String.valueOf(this.setting.getValue()), (float)(this.x + 156.0D), (float)(this.y + this.height - 9.0D), (new Color(-1157627905, true)).getRGB(), false);
/*     */       }
/*  69 */     } else if (Objects.equals(this.Stringnumber, "")) {
/*  70 */       FontRender.drawString6("...", (float)(this.x + 156.0D), (float)(this.y + this.height - 9.0D), (new Color(-1157627905, true)).getRGB(), false);
/*     */     } else {
/*  72 */       FontRender.drawString6(this.Stringnumber, (float)(this.x + 156.0D), (float)(this.y + this.height - 9.0D), (new Color(-1157627905, true)).getRGB(), false);
/*     */     } 
/*     */ 
/*     */     
/*  76 */     this.animation = MathUtil.clamp(this.animation, 0.0F, 1.0F);
/*     */     
/*  78 */     if (this.dragging) {
/*  79 */       setValue(mouseX, this.x + 54.0D, 90.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setValue(int mouseX, double x, double width) {
/*  84 */     double diff = (((Number)this.setting.getMax()).floatValue() - ((Number)this.setting.getMin()).floatValue());
/*  85 */     double percentBar = MathHelper.func_151237_a((mouseX - x) / width, 0.0D, 1.0D);
/*  86 */     double value = ((Number)this.setting.getMin()).floatValue() + percentBar * diff;
/*     */ 
/*     */     
/*  89 */     if (this.setting.getValue() instanceof Float) {
/*  90 */       this.setting.setValue(Float.valueOf((float)value));
/*  91 */     } else if (this.setting.getValue() instanceof Integer) {
/*  92 */       this.setting.setValue(Integer.valueOf((int)value));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/*  98 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/* 101 */     if (mouseX > this.x + 154.0D && mouseX < this.x + 176.0D && mouseY > this.y + this.height - 11.0D && mouseY < this.y + this.height - 4.0D) {
/* 102 */       this.Stringnumber = "";
/* 103 */       this.listening = true;
/*     */     }
/* 105 */     else if (button == 0 && this.hovered) {
/* 106 */       this.dragging = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int button) {
/* 113 */     this.dragging = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetAnimation() {
/* 118 */     this.dragging = false;
/* 119 */     this.animation = 0.0F;
/* 120 */     this.stranimation = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/* 125 */     if (this.listening) {
/* 126 */       switch (keyCode) {
/*     */         case 1:
/* 128 */           this.listening = false;
/* 129 */           this.Stringnumber = "";
/*     */           return;
/*     */         
/*     */         case 28:
/*     */           try {
/* 134 */             searchNumber();
/*     */           }
/* 136 */           catch (Exception e) {
/* 137 */             this.Stringnumber = "";
/* 138 */             this.listening = false;
/*     */           } 
/*     */         
/*     */         case 14:
/* 142 */           this.Stringnumber = removeLastChar(this.Stringnumber);
/*     */           break;
/*     */       } 
/* 145 */       this.Stringnumber += typedChar;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void searchNumber() {
/* 150 */     if (this.setting.getValue() instanceof Float) {
/* 151 */       this.setting.setValue(Float.valueOf(this.Stringnumber));
/* 152 */       this.Stringnumber = "";
/* 153 */       this.listening = false;
/* 154 */     } else if (this.setting.getValue() instanceof Integer) {
/* 155 */       this.setting.setValue(Integer.valueOf(this.Stringnumber));
/* 156 */       this.Stringnumber = "";
/* 157 */       this.listening = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkMouseWheel(float value) {
/* 163 */     super.checkMouseWheel(value);
/* 164 */     if (isHovered()) {
/* 165 */       ThunderGui2.scroll_lock = true;
/*     */     } else {
/*     */       return;
/*     */     } 
/* 169 */     if (value < 0.0F) {
/* 170 */       if (this.setting.getValue() instanceof Float) {
/* 171 */         this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() + 0.01F));
/* 172 */       } else if (this.setting.getValue() instanceof Integer) {
/* 173 */         this.setting.setValue(Integer.valueOf(((Integer)this.setting.getValue()).intValue() + 1));
/*     */       } 
/* 175 */     } else if (value > 0.0F) {
/* 176 */       if (this.setting.getValue() instanceof Float) {
/* 177 */         this.setting.setValue(Float.valueOf(((Float)this.setting.getValue()).floatValue() - 0.01F));
/* 178 */       } else if (this.setting.getValue() instanceof Integer) {
/* 179 */         this.setting.setValue(Integer.valueOf(((Integer)this.setting.getValue()).intValue() - 1));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\SliderComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */