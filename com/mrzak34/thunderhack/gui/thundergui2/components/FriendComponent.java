/*     */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.Particles;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.util.PNGtoResourceLocation;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.render.Stencil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class FriendComponent
/*     */ {
/*  22 */   float scroll_animation = 0.0F;
/*  23 */   ResourceLocation head = null;
/*  24 */   ResourceLocation crackedSkin = new ResourceLocation("textures/cracked.png");
/*     */   private final String name;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int progress;
/*     */   private int fade;
/*     */   private final int index;
/*     */   private boolean first_open = true;
/*     */   private float scrollPosY;
/*     */   private float prevPosY;
/*     */   
/*     */   public FriendComponent(String name, int posX, int posY, int index) {
/*  36 */     this.name = name;
/*  37 */     this.posX = posX;
/*  38 */     this.posY = posY;
/*  39 */     this.fade = 0;
/*  40 */     this.index = index * 5;
/*  41 */     this.head = PNGtoResourceLocation.getTexture2(name, "png");
/*  42 */     this.scrollPosY = posY;
/*  43 */     this.scroll_animation = 0.0F;
/*     */   }
/*     */   
/*     */   public static void drawCompleteImage(float posX, float posY, int width, int height, Color color) {
/*  47 */     GlStateManager.func_179131_c(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/*  48 */     GL11.glPushMatrix();
/*  49 */     GL11.glTranslatef(posX, posY, 0.0F);
/*  50 */     GL11.glBegin(7);
/*  51 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  52 */     GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/*  53 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  54 */     GL11.glVertex3f(0.0F, height, 0.0F);
/*  55 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  56 */     GL11.glVertex3f(width, height, 0.0F);
/*  57 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  58 */     GL11.glVertex3f(width, 0.0F, 0.0F);
/*  59 */     GL11.glEnd();
/*  60 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public void render(int MouseX, int MouseY) {
/*  64 */     if (this.scrollPosY != this.posY) {
/*  65 */       this.scroll_animation = ThunderGui2.fast(this.scroll_animation, 1.0F, 15.0F);
/*  66 */       this.posY = (int)RenderUtil.interpolate(this.scrollPosY, this.prevPosY, this.scroll_animation);
/*     */     } 
/*  68 */     if (this.posY > (ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m || this.posY < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/*  71 */     RoundedShader.drawRound((this.posX + 5), this.posY, 285.0F, 30.0F, 4.0F, ColorUtil.applyOpacity(new Color(44, 35, 52, 255), getFadeFactor()));
/*     */     
/*  73 */     if (this.first_open) {
/*  74 */       GL11.glPushMatrix();
/*  75 */       Stencil.write(false);
/*  76 */       Particles.roundedRect(this.posX - 0.5D + 5.0D, this.posY - 0.5D, 286.0D, 31.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/*  77 */       Stencil.erase(true);
/*  78 */       RenderUtil.drawBlurredShadow((MouseX - 20), (MouseY - 20), 40.0F, 40.0F, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), getFadeFactor()));
/*  79 */       Stencil.dispose();
/*  80 */       GL11.glPopMatrix();
/*  81 */       this.first_open = false;
/*     */     } 
/*     */     
/*  84 */     if (isHovered(MouseX, MouseY)) {
/*  85 */       GL11.glPushMatrix();
/*  86 */       Stencil.write(false);
/*  87 */       Particles.roundedRect(this.posX - 0.5D + 5.0D, this.posY - 0.5D, 286.0D, 31.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/*  88 */       Stencil.erase(true);
/*  89 */       RenderUtil.drawBlurredShadow((MouseX - 20), (MouseY - 20), 40.0F, 40.0F, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), getFadeFactor()));
/*  90 */       Stencil.dispose();
/*  91 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/*  94 */     RoundedShader.drawRound((this.posX + 266), (this.posY + 8), 14.0F, 14.0F, 2.0F, ColorUtil.applyOpacity(new Color(25, 20, 30, 255), getFadeFactor()));
/*     */     
/*  96 */     if (Drawable.isHovered(MouseX, MouseY, (this.posX + 268), (this.posY + 10), 10.0D, 10.0D)) {
/*  97 */       RoundedShader.drawRound((this.posX + 268), (this.posY + 10), 10.0F, 10.0F, 2.0F, ColorUtil.applyOpacity(new Color(65, 1, 13, 255), getFadeFactor()));
/*     */     } else {
/*  99 */       RoundedShader.drawRound((this.posX + 268), (this.posY + 10), 10.0F, 10.0F, 2.0F, ColorUtil.applyOpacity(new Color(94, 1, 18, 255), getFadeFactor()));
/*     */     } 
/* 101 */     FontRender.drawIcon("w", this.posX + 268, this.posY + 13, ColorUtil.applyOpacity(-1, getFadeFactor()));
/*     */     
/* 103 */     GL11.glPushMatrix();
/* 104 */     Stencil.write(false);
/* 105 */     Particles.roundedRect((this.posX + 10), (this.posY + 3), 22.0D, 22.0D, 8.0D, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), getFadeFactor()));
/* 106 */     Stencil.erase(true);
/* 107 */     if (this.head != null) {
/* 108 */       Util.mc.func_110434_K().func_110577_a(this.head);
/* 109 */       drawCompleteImage((this.posX + 10), (this.posY + 3), 22, 22, ColorUtil.applyOpacity(new Color(255, 255, 255, 255), getFadeFactor()));
/*     */     } else {
/* 111 */       Util.mc.func_110434_K().func_110577_a(this.crackedSkin);
/* 112 */       drawCompleteImage((this.posX + 10), (this.posY + 3), 22, 22, ColorUtil.applyOpacity(new Color(255, 255, 255, 255), getFadeFactor()));
/*     */     } 
/* 114 */     Stencil.dispose();
/* 115 */     GL11.glPopMatrix();
/*     */     
/* 117 */     FontRender.drawString6(this.name, (this.posX + 37), (this.posY + 6), ColorUtil.applyOpacity(-1, getFadeFactor()), false);
/*     */     
/* 119 */     boolean online = (Util.mc.field_71439_g.field_71174_a.func_175104_a(this.name) != null);
/*     */     
/* 121 */     FontRender.drawString7(online ? "online" : "offline", (this.posX + 37), (this.posY + 17), online ? ColorUtil.applyOpacity((new Color(-16025088, true)).getRGB(), getFadeFactor()) : ColorUtil.applyOpacity((new Color(-4342339, true)).getRGB(), getFadeFactor()), false);
/*     */   }
/*     */   
/*     */   private float getFadeFactor() {
/* 125 */     return this.fade / (5.0F + this.index);
/*     */   }
/*     */   
/*     */   public void onTick() {
/* 129 */     if (this.progress > 4) {
/* 130 */       this.progress = 0;
/*     */     }
/* 132 */     this.progress++;
/*     */     
/* 134 */     if (this.fade < 10 + this.index) {
/* 135 */       this.fade++;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isHovered(int mouseX, int mouseY) {
/* 140 */     return (mouseX > this.posX && mouseX < this.posX + 295 && mouseY > this.posY && mouseY < this.posY + 30);
/*     */   }
/*     */   
/*     */   public void movePosition(float deltaX, float deltaY) {
/* 144 */     this.posY = (int)(this.posY + deltaY);
/* 145 */     this.posX = (int)(this.posX + deltaX);
/* 146 */     this.scrollPosY = this.posY;
/*     */   }
/*     */   
/*     */   public void mouseClicked(int MouseX, int MouseY, int clickedButton) {
/* 150 */     if (this.posY > (ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m || this.posY < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/* 153 */     if (Drawable.isHovered(MouseX, MouseY, (this.posX + 268), (this.posY + 10), 10.0D, 10.0D)) {
/* 154 */       Thunderhack.friendManager.removeFriend(this.name);
/* 155 */       ThunderGui2.getInstance().loadFriends();
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getPosX() {
/* 160 */     return this.posX;
/*     */   }
/*     */   
/*     */   public double getPosY() {
/* 164 */     return this.posY;
/*     */   }
/*     */   
/*     */   public void scrollElement(float deltaY) {
/* 168 */     this.scroll_animation = 0.0F;
/* 169 */     this.prevPosY = this.posY;
/* 170 */     this.scrollPosY += deltaY;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\FriendComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */