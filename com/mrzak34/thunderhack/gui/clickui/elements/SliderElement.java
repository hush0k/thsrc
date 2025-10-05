/*     */ package com.mrzak34.thunderhack.gui.clickui.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class SliderElement
/*     */   extends AbstractElement
/*     */ {
/*     */   private final float min;
/*     */   private final float max;
/*     */   public boolean listening;
/*  19 */   public String Stringnumber = "";
/*     */   private float animation;
/*     */   private double stranimation;
/*     */   private boolean dragging;
/*     */   
/*     */   public SliderElement(Setting setting) {
/*  25 */     super(setting);
/*  26 */     this.min = ((Number)setting.getMin()).floatValue();
/*  27 */     this.max = ((Number)setting.getMax()).floatValue();
/*     */   }
/*     */   
/*     */   public static String removeLastChar(String str) {
/*  31 */     String output = "";
/*  32 */     if (str != null && str.length() > 0) {
/*  33 */       output = str.substring(0, str.length() - 1);
/*     */     }
/*  35 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float delta) {
/*  42 */     double currentPos = ((((Number)this.setting.getValue()).floatValue() - this.min) / (this.max - this.min));
/*  43 */     this.stranimation += ((((Number)this.setting.getValue()).floatValue() * 100.0F / 100.0F) - this.stranimation) / 2.0D;
/*  44 */     this.animation = RenderUtil.scrollAnimate(this.animation, (float)currentPos, 0.5F);
/*  45 */     super.render(mouseX, mouseY, delta);
/*     */     
/*  47 */     String value = "";
/*     */     
/*  49 */     if (this.setting.getValue() instanceof Float) {
/*  50 */       value = String.valueOf(MathUtil.round(((Float)this.setting.getValue()).floatValue(), 2));
/*     */     }
/*  52 */     if (this.setting.getValue() instanceof Integer) {
/*  53 */       value = String.valueOf(MathUtil.round(((Integer)this.setting.getValue()).intValue(), 2));
/*     */     }
/*  55 */     if (!this.listening) {
/*  56 */       FontRender.drawString5(this.setting.getName(), (float)(this.x + 4.0D), (float)(this.y + 4.0D), -1);
/*  57 */       if (this.setting.getValue() instanceof Float)
/*  58 */         FontRender.drawString5(String.valueOf(MathUtil.round(((Float)this.setting.getValue()).floatValue(), 2)), (float)(this.x + this.width - 4.0D - FontRender.getStringWidth6(value)), (float)this.y + 5.0F, -1); 
/*  59 */       if (this.setting.getValue() instanceof Integer) {
/*  60 */         FontRender.drawString5(String.valueOf(MathUtil.round(((Integer)this.setting.getValue()).intValue(), 2)), (float)(this.x + this.width - 4.0D - FontRender.getStringWidth6(value)), (float)this.y + 5.0F, -1);
/*     */       }
/*     */     } else {
/*  63 */       FontRender.drawString5(this.setting.getName(), (float)(this.x + 4.0D), (float)(this.y + 4.0D), -1);
/*  64 */       if (Objects.equals(this.Stringnumber, "")) {
/*  65 */         FontRender.drawString5("...", (float)(this.x + this.width - 4.0D - FontRender.getStringWidth6(value)), (float)this.y + 5.0F, -1);
/*     */       } else {
/*  67 */         FontRender.drawString5(this.Stringnumber, (float)(this.x + this.width - 4.0D - FontRender.getStringWidth6(value)), (float)this.y + 5.0F, -1);
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     Color color = new Color(-1973791);
/*  72 */     RoundedShader.drawRound((float)(this.x + 4.0D), (float)(this.y + this.height - 4.0D), (float)(this.width - 8.0D), 1.0F, 0.5F, new Color(-15856114));
/*  73 */     RoundedShader.drawRound((float)(this.x + 4.0D), (float)(this.y + this.height - 4.0D), (float)((this.width - 8.0D) * this.animation), 1.0F, 0.5F, color);
/*  74 */     RoundedShader.drawRound((float)(this.x + 2.0D + (this.width - 8.0D) * this.animation), (float)(this.y + this.height - 5.5D), 4.0F, 4.0F, 1.5F, color);
/*     */     
/*  76 */     this.animation = MathUtil.clamp(this.animation, 0.0F, 1.0F);
/*     */     
/*  78 */     if (this.dragging)
/*  79 */       setValue(mouseX, this.x + 7.0D, this.width - 14.0D); 
/*     */   }
/*     */   
/*     */   private void setValue(int mouseX, double x, double width) {
/*  83 */     double diff = (((Number)this.setting.getMax()).floatValue() - ((Number)this.setting.getMin()).floatValue());
/*  84 */     double percentBar = MathHelper.func_151237_a((mouseX - x) / width, 0.0D, 1.0D);
/*  85 */     double value = ((Number)this.setting.getMin()).floatValue() + percentBar * diff;
/*     */ 
/*     */     
/*  88 */     if (this.setting.getValue() instanceof Float) {
/*  89 */       this.setting.setValue(Float.valueOf((float)value));
/*  90 */     } else if (this.setting.getValue() instanceof Integer) {
/*  91 */       this.setting.setValue(Integer.valueOf((int)value));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/*  97 */     if (button == 0 && this.hovered) {
/*  98 */       this.dragging = true;
/*  99 */     } else if (this.hovered) {
/* 100 */       this.Stringnumber = "";
/* 101 */       this.listening = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int button) {
/* 107 */     this.dragging = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetAnimation() {
/* 112 */     this.dragging = false;
/* 113 */     this.animation = 0.0F;
/* 114 */     this.stranimation = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/* 119 */     if (this.listening) {
/* 120 */       switch (keyCode) {
/*     */         case 1:
/* 122 */           this.listening = false;
/* 123 */           this.Stringnumber = "";
/*     */           return;
/*     */         
/*     */         case 28:
/*     */           try {
/* 128 */             searchNumber();
/*     */           }
/* 130 */           catch (Exception e) {
/* 131 */             this.Stringnumber = "";
/* 132 */             this.listening = false;
/*     */           } 
/*     */         
/*     */         case 14:
/* 136 */           this.Stringnumber = removeLastChar(this.Stringnumber);
/*     */           break;
/*     */       } 
/*     */       
/* 140 */       this.Stringnumber += typedChar;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void searchNumber() {
/* 146 */     if (this.setting.getValue() instanceof Float) {
/* 147 */       this.setting.setValue(Float.valueOf(this.Stringnumber));
/* 148 */       this.Stringnumber = "";
/* 149 */       this.listening = false;
/* 150 */     } else if (this.setting.getValue() instanceof Integer) {
/* 151 */       this.setting.setValue(Integer.valueOf(this.Stringnumber));
/* 152 */       this.Stringnumber = "";
/* 153 */       this.listening = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\elements\SliderElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */