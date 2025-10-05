/*     */ package com.mrzak34.thunderhack.gui.mainmenu;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ public class GuiMainMenuButton
/*     */   extends GuiButton
/*     */ {
/*     */   static ScaledResolution sr;
/*     */   boolean _double;
/*     */   boolean alt;
/*     */   String name;
/*     */   
/*     */   public GuiMainMenuButton(int buttonId, int x, int y, boolean _double, String name, boolean alt) {
/*  27 */     super(buttonId, x, y, name);
/*  28 */     sr = new ScaledResolution(Util.mc);
/*  29 */     this._double = _double;
/*  30 */     if (_double) {
/*  31 */       func_175211_a(222);
/*     */     } else {
/*  33 */       func_175211_a(107);
/*     */     } 
/*  35 */     this.name = name;
/*  36 */     this.alt = alt;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getMouseX() {
/*  41 */     return Mouse.getX() * sr.func_78326_a() / (Minecraft.func_71410_x()).field_71443_c;
/*     */   }
/*     */   
/*     */   public static int getMouseY() {
/*  45 */     return sr.func_78328_b() - Mouse.getY() * sr.func_78328_b() / (Minecraft.func_71410_x()).field_71440_d - 1;
/*     */   }
/*     */   
/*     */   public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float mouseButton) {
/*  49 */     if (this.field_146125_m) {
/*  50 */       this.field_146123_n = (mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + 35);
/*     */       
/*  52 */       GlStateManager.func_179147_l();
/*  53 */       GlStateManager.func_179120_a(770, 771, 1, 0);
/*  54 */       GlStateManager.func_179112_b(770, 771);
/*     */       
/*  56 */       Color color = new Color(-2046820352, true);
/*     */       
/*  58 */       GlStateManager.func_179094_E();
/*     */ 
/*     */       
/*  61 */       if (!this.alt) {
/*  62 */         if (this._double) {
/*  63 */           RoundedShader.drawGradientRound(this.field_146123_n ? (this.field_146128_h + 1) : this.field_146128_h, this.field_146123_n ? (this.field_146129_i + 1) : this.field_146129_i, this.field_146123_n ? 220.0F : 222.0F, this.field_146123_n ? 33.0F : 35.0F, 7.0F, color, color, color, color);
/*  64 */           FontRender.drawCentString6(this.name, (this.field_146128_h + 111), this.field_146129_i + 17.0F, this.field_146123_n ? (new Color(8026746)).getRGB() : -1);
/*     */         } else {
/*  66 */           RoundedShader.drawGradientRound(this.field_146123_n ? (this.field_146128_h + 1) : this.field_146128_h, this.field_146123_n ? (this.field_146129_i + 1) : this.field_146129_i, this.field_146123_n ? 105.0F : 107.0F, this.field_146123_n ? 33.0F : 35.0F, 7.0F, color, color, color, color);
/*  67 */           FontRender.drawCentString6(this.name, this.field_146128_h + 53.5F, this.field_146129_i + 17.0F, this.field_146123_n ? (new Color(8026746)).getRGB() : -1);
/*     */         }
/*     */       
/*  70 */       } else if (this._double) {
/*  71 */         RoundedShader.drawGradientRound(this.field_146123_n ? (this.field_146128_h + 1) : this.field_146128_h, this.field_146123_n ? (this.field_146129_i + 1) : this.field_146129_i, this.field_146123_n ? 237.0F : 239.0F, this.field_146123_n ? 28.0F : 30.0F, 7.0F, color, color, color, color);
/*  72 */         FontRender.drawCentString6(this.name, this.field_146128_h + 124.5F, this.field_146129_i + 15.0F, this.field_146123_n ? (new Color(8026746)).getRGB() : -1);
/*     */       } else {
/*  74 */         RoundedShader.drawGradientRound(this.field_146123_n ? (this.field_146128_h + 1) : this.field_146128_h, this.field_146123_n ? (this.field_146129_i + 1) : this.field_146129_i, this.field_146123_n ? 113.0F : 115.0F, this.field_146123_n ? 28.0F : 30.0F, 7.0F, color, color, color, color);
/*  75 */         FontRender.drawCentString6(this.name, this.field_146128_h + 53.5F, this.field_146129_i + 15.0F, this.field_146123_n ? (new Color(8026746)).getRGB() : -1);
/*     */       } 
/*     */ 
/*     */       
/*  79 */       GlStateManager.func_179121_F();
/*     */ 
/*     */       
/*  82 */       func_146119_b(mc, mouseX, mouseY);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146119_b(Minecraft mc, int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public void func_146118_a(int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
/*  94 */     return (this.field_146124_l && this.field_146125_m && mouseX >= this.field_146128_h && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g + 10);
/*     */   }
/*     */   
/*     */   public boolean func_146115_a() {
/*  98 */     return this.field_146123_n;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146111_b(int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public void func_146113_a(SoundHandler soundHandlerIn) {
/* 106 */     soundHandlerIn.func_147682_a((ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
/*     */   }
/*     */   
/*     */   public int func_146117_b() {
/* 110 */     return this.field_146120_f;
/*     */   }
/*     */   
/*     */   public void func_175211_a(int width) {
/* 114 */     this.field_146120_f = width;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\mainmenu\GuiMainMenuButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */