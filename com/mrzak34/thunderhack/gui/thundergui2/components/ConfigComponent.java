/*     */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.Particles;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.manager.ConfigManager;
/*     */ import com.mrzak34.thunderhack.modules.client.ThunderHackGui;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.render.Stencil;
/*     */ import java.awt.Color;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ConfigComponent
/*     */ {
/*  19 */   float scroll_animation = 0.0F;
/*     */   private final String name;
/*     */   private final String date;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int progress;
/*     */   private int fade;
/*     */   private final int index;
/*     */   private boolean first_open = true;
/*     */   private float scrollPosY;
/*     */   private float prevPosY;
/*     */   
/*     */   public ConfigComponent(String name, String date, int posX, int posY, int index) {
/*  32 */     this.name = name;
/*  33 */     this.date = date;
/*  34 */     this.posX = posX;
/*  35 */     this.posY = posY;
/*  36 */     this.fade = 0;
/*  37 */     this.index = index * 5;
/*  38 */     this.scrollPosY = posY;
/*  39 */     this.scroll_animation = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int MouseX, int MouseY) {
/*  44 */     if (this.scrollPosY != this.posY) {
/*  45 */       this.scroll_animation = ThunderGui2.fast(this.scroll_animation, 1.0F, 15.0F);
/*  46 */       this.posY = (int)RenderUtil.interpolate(this.scrollPosY, this.prevPosY, this.scroll_animation);
/*     */     } 
/*     */     
/*  49 */     if (this.posY > (ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m || this.posY < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/*     */     
/*  53 */     if (ConfigManager.currentConfig.getName().equals(this.name + ".th")) {
/*  54 */       RoundedShader.drawGradientRound((this.posX + 5), this.posY, 285.0F, 30.0F, 4.0F, 
/*  55 */           ColorUtil.applyOpacity(new Color(55, 44, 66, 255), getFadeFactor()), 
/*  56 */           ColorUtil.applyOpacity(new Color(25, 20, 30, 255), getFadeFactor()), 
/*  57 */           ColorUtil.applyOpacity(((ColorSetting)(ThunderHackGui.getInstance()).onColor1.getValue()).getColorObject(), getFadeFactor()), 
/*  58 */           ColorUtil.applyOpacity(((ColorSetting)(ThunderHackGui.getInstance()).onColor2.getValue()).getColorObject(), getFadeFactor()));
/*     */     } else {
/*  60 */       RoundedShader.drawRound((this.posX + 5), this.posY, 285.0F, 30.0F, 4.0F, ColorUtil.applyOpacity(new Color(44, 35, 52, 255), getFadeFactor()));
/*     */     } 
/*     */ 
/*     */     
/*  64 */     if (this.first_open) {
/*  65 */       GL11.glPushMatrix();
/*  66 */       Stencil.write(false);
/*  67 */       Particles.roundedRect(this.posX - 0.5D + 5.0D, this.posY - 0.5D, 286.0D, 31.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/*  68 */       Stencil.erase(true);
/*  69 */       RenderUtil.drawBlurredShadow((MouseX - 20), (MouseY - 20), 40.0F, 40.0F, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), getFadeFactor()));
/*  70 */       Stencil.dispose();
/*  71 */       GL11.glPopMatrix();
/*  72 */       this.first_open = false;
/*     */     } 
/*     */     
/*  75 */     if (isHovered(MouseX, MouseY)) {
/*  76 */       GL11.glPushMatrix();
/*  77 */       Stencil.write(false);
/*  78 */       Particles.roundedRect(this.posX - 0.5D + 5.0D, this.posY - 0.5D, 286.0D, 31.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/*  79 */       Stencil.erase(true);
/*  80 */       RenderUtil.drawBlurredShadow((MouseX - 20), (MouseY - 20), 40.0F, 40.0F, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), getFadeFactor()));
/*  81 */       Stencil.dispose();
/*  82 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/*  85 */     RoundedShader.drawRound((this.posX + 250), (this.posY + 8), 30.0F, 14.0F, 2.0F, ColorUtil.applyOpacity(new Color(25, 20, 30, 255), getFadeFactor()));
/*     */     
/*  87 */     if (Drawable.isHovered(MouseX, MouseY, (this.posX + 252), (this.posY + 10), 10.0D, 10.0D)) {
/*  88 */       RoundedShader.drawRound((this.posX + 252), (this.posY + 10), 10.0F, 10.0F, 2.0F, ColorUtil.applyOpacity(new Color(21, 58, 0, 255), getFadeFactor()));
/*     */     } else {
/*  90 */       RoundedShader.drawRound((this.posX + 252), (this.posY + 10), 10.0F, 10.0F, 2.0F, ColorUtil.applyOpacity(new Color(32, 89, 0, 255), getFadeFactor()));
/*     */     } 
/*     */     
/*  93 */     if (Drawable.isHovered(MouseX, MouseY, (this.posX + 268), (this.posY + 10), 10.0D, 10.0D)) {
/*  94 */       RoundedShader.drawRound((this.posX + 268), (this.posY + 10), 10.0F, 10.0F, 2.0F, ColorUtil.applyOpacity(new Color(65, 1, 13, 255), getFadeFactor()));
/*     */     } else {
/*  96 */       RoundedShader.drawRound((this.posX + 268), (this.posY + 10), 10.0F, 10.0F, 2.0F, ColorUtil.applyOpacity(new Color(94, 1, 18, 255), getFadeFactor()));
/*     */     } 
/*  98 */     FontRender.drawIcon("x", this.posX + 252, this.posY + 13, ColorUtil.applyOpacity(-1, getFadeFactor()));
/*  99 */     FontRender.drawIcon("w", this.posX + 268, this.posY + 13, ColorUtil.applyOpacity(-1, getFadeFactor()));
/*     */ 
/*     */     
/* 102 */     FontRender.drawMidIcon("u", (this.posX + 7), (this.posY + 5), ColorUtil.applyOpacity(-1, getFadeFactor()));
/* 103 */     FontRender.drawString6(this.name, (this.posX + 37), (this.posY + 6), ColorUtil.applyOpacity(-1, getFadeFactor()), false);
/* 104 */     FontRender.drawString7("updated on: " + this.date, (this.posX + 37), (this.posY + 17), ColorUtil.applyOpacity((new Color(-4342339, true)).getRGB(), getFadeFactor()), false);
/*     */   }
/*     */   
/*     */   private float getFadeFactor() {
/* 108 */     return this.fade / (5.0F + this.index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 113 */     if (this.progress > 4) {
/* 114 */       this.progress = 0;
/*     */     }
/* 116 */     this.progress++;
/*     */     
/* 118 */     if (this.fade < 10 + this.index) {
/* 119 */       this.fade++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isHovered(int mouseX, int mouseY) {
/* 125 */     return (mouseX > this.posX && mouseX < this.posX + 295 && mouseY > this.posY && mouseY < this.posY + 30);
/*     */   }
/*     */   
/*     */   public void movePosition(float deltaX, float deltaY) {
/* 129 */     this.posY = (int)(this.posY + deltaY);
/* 130 */     this.posX = (int)(this.posX + deltaX);
/* 131 */     this.scrollPosY = this.posY;
/*     */   }
/*     */   
/*     */   public void mouseClicked(int MouseX, int MouseY, int clickedButton) {
/* 135 */     if (this.posY > (ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m || this.posY < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/* 138 */     if (Drawable.isHovered(MouseX, MouseY, (this.posX + 252), (this.posY + 10), 10.0D, 10.0D)) {
/* 139 */       ConfigManager.load(this.name);
/*     */     }
/* 141 */     if (Drawable.isHovered(MouseX, MouseY, (this.posX + 268), (this.posY + 10), 10.0D, 10.0D)) {
/* 142 */       ConfigManager.delete(this.name);
/* 143 */       ThunderGui2.getInstance().loadConfigs();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPosX() {
/* 149 */     return this.posX;
/*     */   }
/*     */   
/*     */   public double getPosY() {
/* 153 */     return this.posY;
/*     */   }
/*     */   
/*     */   public void scrollElement(float deltaY) {
/* 157 */     this.scroll_animation = 0.0F;
/* 158 */     this.prevPosY = this.posY;
/* 159 */     this.scrollPosY += deltaY;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\ConfigComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */