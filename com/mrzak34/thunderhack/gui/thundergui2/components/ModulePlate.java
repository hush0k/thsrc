/*     */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.Particles;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.ThunderHackGui;
/*     */ import com.mrzak34.thunderhack.setting.Bind;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.render.Stencil;
/*     */ import java.awt.Color;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ModulePlate
/*     */ {
/*  22 */   float scroll_animation = 0.0F;
/*     */   
/*     */   private final Module module;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private float scrollPosY;
/*     */   private float prevPosY;
/*     */   private int progress;
/*     */   private final ScaledResolution sr;
/*     */   private int fade;
/*     */   private final int index;
/*     */   private boolean first_open = true;
/*     */   private boolean listening_bind = false;
/*     */   
/*     */   public ModulePlate(Module module, int posX, int posY, int index) {
/*  37 */     this.module = module;
/*  38 */     this.posX = posX;
/*  39 */     this.posY = posY;
/*  40 */     this.sr = new ScaledResolution(Util.mc);
/*  41 */     this.fade = 0;
/*  42 */     this.index = index * 5;
/*  43 */     this.scrollPosY = posY;
/*  44 */     this.scroll_animation = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(int MouseX, int MouseY) {
/*  50 */     if (this.scrollPosY != this.posY) {
/*  51 */       this.scroll_animation = ThunderGui2.fast(this.scroll_animation, 1.0F, 15.0F);
/*  52 */       this.posY = (int)RenderUtil.interpolate(this.scrollPosY, this.prevPosY, this.scroll_animation);
/*     */     } 
/*     */ 
/*     */     
/*  56 */     if (this.posY > (ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m || this.posY < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     if (this.module.isOn()) {
/*  61 */       RoundedShader.drawGradientRound(this.posX, this.posY, 90.0F, 30.0F, 4.0F, 
/*  62 */           ColorUtil.applyOpacity(new Color(25, 20, 30, 255), getFadeFactor()), 
/*  63 */           ColorUtil.applyOpacity(new Color(25, 20, 30, 255), getFadeFactor()), 
/*  64 */           ColorUtil.applyOpacity(((ColorSetting)(ThunderHackGui.getInstance()).onColor1.getValue()).getColorObject(), getFadeFactor()), 
/*  65 */           ColorUtil.applyOpacity(((ColorSetting)(ThunderHackGui.getInstance()).onColor2.getValue()).getColorObject(), getFadeFactor()));
/*     */     } else {
/*  67 */       RoundedShader.drawRound(this.posX, this.posY, 90.0F, 30.0F, 4.0F, ColorUtil.applyOpacity(new Color(25, 20, 30, 255), getFadeFactor()));
/*     */     } 
/*     */     
/*  70 */     if (this.first_open) {
/*  71 */       GL11.glPushMatrix();
/*  72 */       Stencil.write(false);
/*  73 */       Particles.roundedRect(this.posX - 0.5D, this.posY - 0.5D, 91.0D, 31.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/*  74 */       Stencil.erase(true);
/*  75 */       RenderUtil.drawBlurredShadow((MouseX - 20), (MouseY - 20), 40.0F, 40.0F, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), getFadeFactor()));
/*  76 */       Stencil.dispose();
/*  77 */       GL11.glPopMatrix();
/*  78 */       this.first_open = false;
/*     */     } 
/*     */     
/*  81 */     if (isHovered(MouseX, MouseY)) {
/*  82 */       GL11.glPushMatrix();
/*  83 */       Stencil.write(false);
/*  84 */       Particles.roundedRect(this.posX - 0.5D, this.posY - 0.5D, 91.0D, 31.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/*  85 */       Stencil.erase(true);
/*  86 */       RenderUtil.drawBlurredShadow((MouseX - 20), (MouseY - 20), 40.0F, 40.0F, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), getFadeFactor()));
/*  87 */       Stencil.dispose();
/*  88 */       GL11.glPopMatrix();
/*     */     } 
/*     */ 
/*     */     
/*  92 */     GL11.glPushMatrix();
/*  93 */     Stencil.write(false);
/*  94 */     Particles.roundedRect(this.posX - 0.5D, this.posY - 0.5D, 91.0D, 31.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/*  95 */     Stencil.erase(true);
/*  96 */     if (ThunderGui2.selected_plate != this) {
/*  97 */       FontRender.drawIcon("H", (int)(this.posX + 80.0F), (int)(this.posY + 22.0F), ColorUtil.applyOpacity((new Color(-1250068, true)).getRGB(), getFadeFactor()));
/*     */     } else {
/*  99 */       String gear = "H";
/* 100 */       switch (this.progress) {
/*     */         case 0:
/* 102 */           gear = "H";
/*     */           break;
/*     */         case 1:
/* 105 */           gear = "N";
/*     */           break;
/*     */         case 2:
/* 108 */           gear = "O";
/*     */           break;
/*     */         case 3:
/* 111 */           gear = "P";
/*     */           break;
/*     */         case 4:
/* 114 */           gear = "Q";
/*     */           break;
/*     */       } 
/* 117 */       FontRender.drawBigIcon(gear, (int)(this.posX + 80.0F), (int)(this.posY + 5.0F), ColorUtil.applyOpacity((new Color(-10197916, true)).getRGB(), getFadeFactor()));
/*     */     } 
/* 119 */     Stencil.dispose();
/* 120 */     GL11.glPopMatrix();
/*     */     
/* 122 */     FontRender.drawString6(this.module.getName(), (this.posX + 5), (this.posY + 5), ColorUtil.applyOpacity(-1, getFadeFactor()), false);
/*     */ 
/*     */     
/* 125 */     if (this.listening_bind) {
/* 126 */       FontRender.drawString6("...", (this.posX + 85 - FontRender.getStringWidth6(this.module.getBind().toString())), (this.posY + 5), ColorUtil.applyOpacity(new Color(11579568), getFadeFactor()).getRGB(), false);
/* 127 */     } else if (!Objects.equals(this.module.getBind().toString(), "None")) {
/* 128 */       FontRender.drawString6(this.module.getBind().toString(), (this.posX + 85 - FontRender.getStringWidth6(this.module.getBind().toString())), (this.posY + 5), ColorUtil.applyOpacity(new Color(11579568), getFadeFactor()).getRGB(), false);
/*     */     } 
/*     */     
/* 131 */     String[] splitString = this.module.getDescription().split("-");
/* 132 */     if (splitString[0] != null && !splitString[0].equals("")) {
/* 133 */       FontRender.drawString7(splitString[0], (this.posX + 5), (this.posY + 13), ColorUtil.applyOpacity((new Color(-4342339, true)).getRGB(), getFadeFactor()), false);
/*     */     }
/* 135 */     if (splitString.length > 1 && 
/* 136 */       splitString[1] != null && !splitString[1].equals("")) {
/* 137 */       FontRender.drawString7(splitString[1], (this.posX + 5), (this.posY + 18), ColorUtil.applyOpacity((new Color(-4342339, true)).getRGB(), getFadeFactor()), false);
/*     */     }
/*     */     
/* 140 */     if (splitString.length == 3 && 
/* 141 */       splitString[2] != null && !splitString[2].equals("")) {
/* 142 */       FontRender.drawString7(splitString[2], (this.posX + 5), (this.posY + 23), ColorUtil.applyOpacity((new Color(-4342339, true)).getRGB(), getFadeFactor()), false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float getFadeFactor() {
/* 149 */     return this.fade / (5.0F + this.index);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 154 */     if (this.progress > 4) {
/* 155 */       this.progress = 0;
/*     */     }
/* 157 */     this.progress++;
/*     */     
/* 159 */     if (this.fade < 10 + this.index) {
/* 160 */       this.fade++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isHovered(int mouseX, int mouseY) {
/* 166 */     return (mouseX > this.posX && mouseX < this.posX + 90 && mouseY > this.posY && mouseY < this.posY + 30);
/*     */   }
/*     */   
/*     */   public void movePosition(float deltaX, float deltaY) {
/* 170 */     this.posY = (int)(this.posY + deltaY);
/* 171 */     this.posX = (int)(this.posX + deltaX);
/* 172 */     this.scrollPosY = this.posY;
/*     */   }
/*     */   
/*     */   public void scrollElement(float deltaY) {
/* 176 */     this.scroll_animation = 0.0F;
/* 177 */     this.prevPosY = this.posY;
/* 178 */     this.scrollPosY += deltaY;
/*     */   }
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
/* 182 */     if (this.posY > (ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m || this.posY < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/* 185 */     if (mouseX > this.posX && mouseX < this.posX + 90 && mouseY > this.posY && mouseY < this.posY + 30) {
/* 186 */       switch (clickedButton) {
/*     */         case 0:
/* 188 */           this.module.toggle();
/*     */           break;
/*     */         case 1:
/* 191 */           ThunderGui2.selected_plate = this;
/*     */           break;
/*     */         case 2:
/* 194 */           this.listening_bind = !this.listening_bind;
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/* 201 */     if (this.listening_bind) {
/* 202 */       Bind bind = new Bind(keyCode);
/* 203 */       if (bind.toString().equalsIgnoreCase("Escape")) {
/*     */         return;
/*     */       }
/* 206 */       if (bind.toString().equalsIgnoreCase("Delete")) {
/* 207 */         bind = new Bind(-1);
/*     */       }
/* 209 */       this.module.bind.setValue(bind);
/* 210 */       this.listening_bind = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getPosX() {
/* 215 */     return this.posX;
/*     */   }
/*     */   
/*     */   public double getPosY() {
/* 219 */     return this.posY;
/*     */   }
/*     */   
/*     */   public Module getModule() {
/* 223 */     return this.module;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\ModulePlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */