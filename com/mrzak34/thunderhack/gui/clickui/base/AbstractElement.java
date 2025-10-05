/*    */ package com.mrzak34.thunderhack.gui.clickui.base;
/*    */ 
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.Drawable;
/*    */ import java.awt.Color;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractElement
/*    */ {
/*    */   protected Setting setting;
/*    */   protected double x;
/*    */   protected double y;
/*    */   protected double width;
/*    */   protected double height;
/*    */   protected double offsetY;
/*    */   protected boolean hovered;
/* 19 */   protected int bgcolor = (new Color(24, 24, 27)).getRGB();
/*    */   
/*    */   public AbstractElement(Setting setting) {
/* 22 */     this.setting = setting;
/*    */   }
/*    */   
/*    */   public void render(int mouseX, int mouseY, float delta) {
/* 26 */     this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {}
/*    */ 
/*    */   
/*    */   public void tick() {}
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY, int button) {}
/*    */ 
/*    */   
/*    */   public void handleMouseInput() throws IOException {}
/*    */ 
/*    */   
/*    */   public void keyTyped(char chr, int keyCode) {}
/*    */ 
/*    */   
/*    */   public void onClose() {}
/*    */ 
/*    */   
/*    */   public void resetAnimation() {}
/*    */ 
/*    */   
/*    */   public Setting getSetting() {
/* 55 */     return this.setting;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 59 */     return this.x;
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 63 */     this.x = x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 67 */     return this.y;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 71 */     this.y = y + this.offsetY;
/*    */   }
/*    */   
/*    */   public double getWidth() {
/* 75 */     return this.width;
/*    */   }
/*    */   
/*    */   public void setWidth(double width) {
/* 79 */     this.width = width;
/*    */   }
/*    */   
/*    */   public double getHeight() {
/* 83 */     return this.height;
/*    */   }
/*    */   
/*    */   public void setHeight(double height) {
/* 87 */     this.height = height;
/*    */   }
/*    */   
/*    */   public void setOffsetY(double offsetY) {
/* 91 */     this.offsetY = offsetY;
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 95 */     return this.setting.isVisible();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\base\AbstractElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */