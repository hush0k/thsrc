/*     */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import java.awt.Color;
/*     */ 
/*     */ public class ModeComponent
/*     */   extends SettingElement
/*     */ {
/*  14 */   int progress = 0;
/*     */   private double wheight;
/*     */   private boolean open;
/*     */   
/*     */   public ModeComponent(Setting setting) {
/*  19 */     super(setting);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float partialTicks) {
/*  24 */     super.render(mouseX, mouseY, partialTicks);
/*  25 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/*  28 */     FontRender.drawString6(getSetting().getName(), (float)getX(), (float)getY() + 5.0F, isHovered() ? -1 : (new Color(-1325400065, true)).getRGB(), false);
/*     */ 
/*     */     
/*  31 */     if (this.open) {
/*  32 */       double offsetY2 = 0.0D;
/*  33 */       for (int i = 0; i <= (this.setting.getModes()).length - 1; i++) {
/*  34 */         offsetY2 += 12.0D;
/*     */       }
/*  36 */       RoundedShader.drawRound((float)(this.x + 114.0D), (float)(this.y + 2.0D), 62.0F, (float)(11.0D + offsetY2), 0.5F, new Color(50, 35, 60, 121));
/*     */     } 
/*     */     
/*  39 */     if (mouseX > this.x + 114.0D && mouseX < this.x + 176.0D && mouseY > this.y + 2.0D && mouseY < this.y + 15.0D) {
/*  40 */       RoundedShader.drawRound((float)(this.x + 114.0D), (float)(this.y + 2.0D), 62.0F, 11.0F, 0.5F, new Color(82, 57, 100, 178));
/*     */     } else {
/*  42 */       RoundedShader.drawRound((float)(this.x + 114.0D), (float)(this.y + 2.0D), 62.0F, 11.0F, 0.5F, new Color(50, 35, 60, 178));
/*     */     } 
/*     */ 
/*     */     
/*  46 */     FontRender.drawString6(this.setting.currentEnumName(), (float)(this.x + 116.0D), (float)(this.y + 5.0D), (new Color(-1325400065, true)).getRGB(), false);
/*     */ 
/*     */     
/*  49 */     String arrow = "n";
/*  50 */     switch (this.progress) {
/*     */       case 0:
/*  52 */         arrow = "n";
/*     */         break;
/*     */       case 1:
/*  55 */         arrow = "o";
/*     */         break;
/*     */       case 2:
/*  58 */         arrow = "p";
/*     */         break;
/*     */       case 3:
/*  61 */         arrow = "q";
/*     */         break;
/*     */       case 4:
/*  64 */         arrow = "r";
/*     */         break;
/*     */     } 
/*  67 */     FontRender.drawIcon(arrow, (int)(this.x + 166.0D), (int)(this.y + 6.0D), -1);
/*     */     
/*  69 */     double offsetY = 13.0D;
/*  70 */     if (this.open) {
/*  71 */       Color color = ClickGui.getInstance().getColor(0);
/*  72 */       for (int i = 0; i <= (this.setting.getModes()).length - 1; i++) {
/*  73 */         FontRender.drawString5(this.setting.getModes()[i], (float)(this.x + 116.0D), (float)(this.y + 5.0D + offsetY), this.setting.currentEnumName().equalsIgnoreCase(this.setting.getModes()[i]) ? color.getRGB() : -1);
/*  74 */         offsetY += 12.0D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  83 */     if (this.open && this.progress > 0) {
/*  84 */       this.progress--;
/*     */     }
/*  86 */     if (!this.open && this.progress < 4) {
/*  87 */       this.progress++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/*  93 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*     */       return;
/*     */     }
/*  96 */     if (mouseX > this.x + 114.0D && mouseX < this.x + 176.0D && mouseY > this.y + 2.0D && mouseY < this.y + 15.0D) {
/*  97 */       this.open = !this.open;
/*     */     }
/*  99 */     if (this.open) {
/* 100 */       double offsetY = 0.0D;
/* 101 */       for (int i = 0; i <= (this.setting.getModes()).length - 1; i++) {
/* 102 */         if (Drawable.isHovered(mouseX, mouseY, this.x, this.y + this.wheight + offsetY, this.width, 12.0D) && button == 0)
/* 103 */           this.setting.setEnumByNumber(i); 
/* 104 */         offsetY += 12.0D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setWHeight(double height) {
/* 110 */     this.wheight = height;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/* 114 */     return this.open;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\ModeComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */