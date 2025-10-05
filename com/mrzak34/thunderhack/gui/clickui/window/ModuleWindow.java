/*     */ package com.mrzak34.thunderhack.gui.clickui.window;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.clickui.EaseBackIn;
/*     */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*     */ import com.mrzak34.thunderhack.gui.clickui.button.ModuleButton;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*     */ import com.mrzak34.thunderhack.notification.Animation;
/*     */ import com.mrzak34.thunderhack.notification.DecelerateAnimation;
/*     */ import com.mrzak34.thunderhack.notification.Direction;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleWindow
/*     */ {
/*     */   private final List<ModuleButton> buttons;
/*     */   private final ResourceLocation ICON;
/*  30 */   private final Animation animation = (Animation)new EaseBackIn(270, 1.0D, 1.03F, Direction.BACKWARDS);
/*  31 */   private final Animation dragAnimation = (Animation)new DecelerateAnimation(260, 1.0D, Direction.BACKWARDS);
/*  32 */   private final Animation rotationAnim = (Animation)new DecelerateAnimation(260, 1.0D, Direction.FORWARDS);
/*     */   
/*     */   public double animationY;
/*     */   
/*     */   public boolean dragging;
/*     */   
/*     */   protected double prevTargetX;
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double width;
/*  42 */   private float rotation = 0.0F; protected double height; protected boolean hovered; protected double factor; private double prevScrollProgress; private double scrollProgress; private boolean scrollHover;
/*     */   private final String name;
/*     */   private double prevX;
/*     */   private double prevY;
/*     */   private boolean open;
/*     */   
/*     */   public ModuleWindow(String name, List<Module> features, int index, double x, double y, double width, double height) {
/*  49 */     this.name = name;
/*  50 */     this.x = x;
/*  51 */     this.y = y;
/*  52 */     this.width = width;
/*  53 */     this.height = height;
/*  54 */     this.open = false;
/*     */     
/*  56 */     this.buttons = new ArrayList<>();
/*  57 */     this.ICON = new ResourceLocation("textures/" + name.toLowerCase() + ".png");
/*     */     
/*  59 */     features.forEach(feature -> {
/*     */           ModuleButton button = new ModuleButton(feature);
/*     */           button.setHeight(15.0D);
/*     */           this.buttons.add(button);
/*     */         });
/*     */   }
/*     */   
/*     */   public void init() {
/*  67 */     this.buttons.forEach(ModuleButton::init);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float delta, Color color, boolean finished) {
/*  72 */     this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
/*  73 */     this.animationY = RenderUtil.interpolate(this.y, this.animationY, 0.05D);
/*  74 */     if (this.dragging) {
/*  75 */       this.prevTargetX = this.x;
/*  76 */       this.x = this.prevX + mouseX;
/*  77 */       this.y = this.prevY + mouseY;
/*     */     } else {
/*  79 */       this.prevTargetX = this.x;
/*     */     } 
/*  81 */     double maxHeight = 4000.0D;
/*     */     
/*  83 */     this.scrollHover = Drawable.isHovered(mouseX, mouseY, this.x, this.y + this.height, this.width, maxHeight);
/*     */     
/*  85 */     this.animation.setDirection(isOpen() ? Direction.FORWARDS : Direction.BACKWARDS);
/*  86 */     this.dragAnimation.setDirection(this.dragging ? Direction.FORWARDS : Direction.BACKWARDS);
/*  87 */     this.rotationAnim.setDirection(Direction.FORWARDS);
/*     */     
/*  89 */     GlStateManager.func_179094_E();
/*     */     
/*  91 */     float centerX = (float)(this.x + (mouseX - this.prevTargetX) / 2.0D);
/*  92 */     float centerY = (float)(this.y + this.height / 2.0D);
/*     */     
/*  94 */     this.rotation = (this.prevTargetX > this.x) ? RenderUtil.scrollAnimate(this.rotation, (float)-(5.0D - (this.x - this.prevTargetX) * 3.3D), 0.94F) : ((this.prevTargetX < this.x) ? RenderUtil.scrollAnimate(this.rotation, (float)(5.0D + (this.x - this.prevTargetX) * 3.3D), 0.94F) : RenderUtil.scrollAnimate(this.rotation, 0.0F, 0.8F));
/*     */     
/*  96 */     float dragScale = (float)(1.0D - 0.01600000075995922D * this.dragAnimation.getOutput());
/*  97 */     GlStateManager.func_179109_b(centerX, centerY, 1.0F);
/*  98 */     GlStateManager.func_179152_a(dragScale + Math.abs(this.rotation / 200.0F), dragScale, 1.0F);
/*  99 */     GlStateManager.func_179114_b(this.rotation, 0.0F, 0.0F, 1.0F);
/* 100 */     GlStateManager.func_179109_b(-centerX, -centerY, 1.0F);
/*     */     
/* 102 */     RoundedShader.drawRound((float)this.x + 2.0F, (float)(this.y + this.height - 5.0D), (float)this.width - 4.0F, (float)((getButtonsHeight() + 8.0D) * this.animation.getOutput()), 3.0F, true, ((ColorSetting)(ClickGui.getInstance()).plateColor.getValue()).getColorObject());
/*     */     
/* 104 */     if (this.animation.finished(Direction.FORWARDS)) {
/* 105 */       Drawable.drawBlurredShadow(((int)this.x + 4), (int)(this.y + this.height - 1.0D), ((int)this.width - 8), 3.0F, 7, new Color(0, 0, 0, 180));
/* 106 */       for (ModuleButton button : this.buttons) {
/* 107 */         button.setX(this.x + 2.0D);
/* 108 */         button.setY(this.y + this.height - getScrollProgress());
/* 109 */         button.setWidth(this.width - 4.0D);
/* 110 */         button.setHeight(15.0D);
/* 111 */         button.render(mouseX, mouseY, delta, color, finished);
/*     */       } 
/*     */     } 
/* 114 */     Drawable.drawRectWH(this.x, this.y, this.width, this.height, ((ColorSetting)(ClickGui.getInstance()).catColor.getValue()).getColor());
/* 115 */     Drawable.drawTexture(this.ICON, this.x + 3.0D, this.y + (this.height - 12.0D) / 2.0D, 12.0D, 12.0D);
/* 116 */     FontRender.drawString6(getName(), (float)this.x + 19.0F, (float)this.y + (float)this.height / 2.0F - (FontRender.getFontHeight6() / 2), -1, true);
/* 117 */     GlStateManager.func_179121_F();
/* 118 */     updatePosition();
/*     */   }
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 122 */     if (this.hovered && button == 0) {
/* 123 */       this.dragging = true;
/* 124 */       this.prevX = this.x - mouseX;
/* 125 */       this.prevY = this.y - mouseY;
/*     */     } 
/* 127 */     if (button == 1 && this.hovered) {
/* 128 */       setOpen(!isOpen());
/*     */     }
/*     */     
/* 131 */     if (isOpen() && this.scrollHover) {
/* 132 */       this.buttons.forEach(b -> b.mouseClicked(mouseX, mouseY, button));
/* 133 */     } else if (!isOpen()) {
/* 134 */       this.buttons.forEach(ModuleButton::resetAnimation);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick() {
/* 139 */     if (isOpen()) {
/* 140 */       this.buttons.forEach(ModuleButton::tick);
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int button) {
/* 145 */     if (button == 0)
/* 146 */       this.dragging = false; 
/* 147 */     if (isOpen())
/* 148 */       this.buttons.forEach(b -> b.mouseReleased(mouseX, mouseY, button)); 
/*     */   }
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 152 */     for (ModuleButton button : this.buttons)
/* 153 */       button.handleMouseInput(); 
/*     */   }
/*     */   
/*     */   public void keyTyped(char chr, int keyCode) {
/* 157 */     if (isOpen())
/* 158 */       for (ModuleButton button : this.buttons) {
/* 159 */         button.keyTyped(chr, keyCode);
/*     */       } 
/*     */   }
/*     */   
/*     */   public void onClose() {
/* 164 */     this.buttons.forEach(ModuleButton::onGuiClosed);
/*     */   }
/*     */   
/*     */   private double getScrollProgress() {
/* 168 */     return this.prevScrollProgress + (this.scrollProgress - this.prevScrollProgress) * Util.mc.func_184121_ak();
/*     */   }
/*     */   
/*     */   private void updatePosition() {
/* 172 */     double offsetY = 0.0D;
/* 173 */     double openY = 0.0D;
/* 174 */     for (ModuleButton button : this.buttons) {
/* 175 */       button.setOffsetY(offsetY);
/* 176 */       if (button.isOpen()) {
/* 177 */         for (AbstractElement element : button.getElements()) {
/* 178 */           if (element.isVisible())
/* 179 */             offsetY += element.getHeight(); 
/*     */         } 
/* 181 */         offsetY += 2.0D;
/*     */       } 
/* 183 */       offsetY += button.getHeight() + openY;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getButtonsHeight() {
/* 188 */     double height = 0.0D;
/* 189 */     for (ModuleButton button : this.buttons) {
/* 190 */       height += button.getElementsHeight();
/* 191 */       height += button.getHeight();
/*     */     } 
/*     */     
/* 194 */     return height;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 198 */     return this.open;
/*     */   }
/*     */   
/*     */   public void setOpen(boolean open) {
/* 202 */     this.open = open;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 206 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/* 211 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/* 215 */     this.x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 219 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/* 223 */     this.y = y;
/*     */   }
/*     */   
/*     */   public double getWidth() {
/* 227 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(double width) {
/* 231 */     this.width = width;
/*     */   }
/*     */   
/*     */   public double getHeight() {
/* 235 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(double height) {
/* 239 */     this.height = height;
/*     */   }
/*     */   
/*     */   public void setFactor(double factor) {
/* 243 */     this.factor = factor;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\window\ModuleWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */