/*     */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ public class SettingElement
/*     */ {
/*     */   protected Setting setting;
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double width;
/*     */   protected double height;
/*     */   protected double offsetY;
/*     */   protected double prev_offsetY;
/*     */   protected double scroll_offsetY;
/*     */   protected float scroll_animation;
/*     */   protected boolean hovered;
/*     */   
/*     */   public SettingElement(Setting setting) {
/*  24 */     this.setting = setting;
/*  25 */     this.scroll_animation = 0.0F;
/*  26 */     this.prev_offsetY = this.y;
/*  27 */     this.scroll_offsetY = 0.0D;
/*     */   }
/*     */   
/*     */   public void render(int mouseX, int mouseY, float delta) {
/*  31 */     this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*  32 */     if (this.scroll_offsetY != this.y) {
/*  33 */       this.scroll_animation = ThunderGui2.fast(this.scroll_animation, 1.0F, 15.0F);
/*  34 */       this.y = (int)RenderUtil.interpolate(this.scroll_offsetY, this.prev_offsetY, this.scroll_animation);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */   
/*     */   public void onTick() {}
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {}
/*     */ 
/*     */   
/*     */   public void tick() {}
/*     */ 
/*     */   
/*     */   public boolean isHovered() {
/*  53 */     return this.hovered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int button) {}
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {}
/*     */ 
/*     */   
/*     */   public void keyTyped(char chr, int keyCode) {}
/*     */ 
/*     */   
/*     */   public void onClose() {}
/*     */ 
/*     */   
/*     */   public void resetAnimation() {}
/*     */   
/*     */   public Setting getSetting() {
/*  72 */     return this.setting;
/*     */   }
/*     */   
/*     */   public double getX() {
/*  76 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/*  80 */     this.x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  84 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/*  88 */     this.prev_offsetY = this.y;
/*  89 */     this.scroll_offsetY = y + this.offsetY;
/*     */   }
/*     */   
/*     */   public double getWidth() {
/*  93 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(double width) {
/*  97 */     this.width = width;
/*     */   }
/*     */   
/*     */   public double getHeight() {
/* 101 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(double height) {
/* 105 */     this.height = height;
/*     */   }
/*     */   
/*     */   public void setOffsetY(double offsetY) {
/* 109 */     this.offsetY = offsetY;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 113 */     return this.setting.isVisible();
/*     */   }
/*     */   
/*     */   public void checkMouseWheel(float dWheel) {
/* 117 */     if (dWheel != 0.0F)
/* 118 */       this.scroll_animation = 0.0F; 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\SettingElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */