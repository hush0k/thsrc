/*     */ package com.mrzak34.thunderhack.gui.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.modules.misc.NoCom;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiScanner
/*     */   extends GuiScreen
/*     */ {
/*     */   public static boolean neartrack = false;
/*     */   public static boolean track = false;
/*     */   public static boolean busy = false;
/*  23 */   private static GuiScanner INSTANCE = new GuiScanner();
/*     */ 
/*     */   
/*  26 */   public ArrayList<NoCom.cout> consoleout = new ArrayList<>();
/*  27 */   int radarx = 0;
/*  28 */   int radary = 0;
/*  29 */   int radarx1 = 0;
/*  30 */   int radary1 = 0;
/*  31 */   int centerx = 0;
/*  32 */   int centery = 0;
/*  33 */   int consolex = 0;
/*  34 */   int consoley = 0;
/*  35 */   int consolex1 = 0;
/*  36 */   int consoley1 = 0;
/*  37 */   int hovery = 0;
/*  38 */   int hoverx = 0;
/*  39 */   int searchx = 0;
/*  40 */   int searchy = 0;
/*  41 */   int wheely = 0;
/*     */   
/*     */   public GuiScanner() {
/*  44 */     setInstance();
/*  45 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   public static GuiScanner getInstance() {
/*  50 */     if (INSTANCE == null) {
/*  51 */       INSTANCE = new GuiScanner();
/*     */     }
/*  53 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static GuiScanner getGuiScanner() {
/*  57 */     return getInstance();
/*     */   }
/*     */   
/*     */   public boolean func_73868_f() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void load() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void setInstance() {
/*  71 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public float getscale() {
/*  75 */     if (((Integer)(NoCom.getInstance()).scale.getValue()).intValue() == 1) {
/*  76 */       return 500.0F;
/*     */     }
/*  78 */     if (((Integer)(NoCom.getInstance()).scale.getValue()).intValue() == 2) {
/*  79 */       return 250.0F;
/*     */     }
/*  81 */     if (((Integer)(NoCom.getInstance()).scale.getValue()).intValue() == 3) {
/*  82 */       return 125.0F;
/*     */     }
/*  84 */     if (((Integer)(NoCom.getInstance()).scale.getValue()).intValue() == 4) {
/*  85 */       return 75.0F;
/*     */     }
/*  87 */     return 705.0F;
/*     */   }
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  91 */     ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/*  92 */     checkMouseWheel(mouseX, mouseY);
/*     */     
/*  94 */     this.radarx = sr.func_78326_a() / 8;
/*  95 */     this.radarx1 = sr.func_78326_a() * 5 / 8;
/*  96 */     this.radary = sr.func_78328_b() / 2 - (this.radarx1 - this.radarx) / 2;
/*  97 */     this.radary1 = sr.func_78328_b() / 2 + (this.radarx1 - this.radarx) / 2;
/*     */     
/*  99 */     this.centerx = (this.radarx + this.radarx1) / 2;
/* 100 */     this.centery = (this.radary + this.radary1) / 2;
/*     */     
/* 102 */     this.consolex = (int)(sr.func_78326_a() * 5.5F / 8.0F);
/* 103 */     this.consolex1 = sr.func_78326_a() - 50;
/* 104 */     this.consoley = this.radary;
/* 105 */     this.consoley1 = this.radary1 - 50;
/*     */ 
/*     */     
/* 108 */     RenderUtil.drawOutlineRect(this.consolex, this.consoley, (this.consolex1 - this.consolex), (this.consoley1 - this.consoley), 4.0F, (new Color(-844584792, true)).getRGB());
/* 109 */     RenderUtil.drawRect2(this.consolex, this.consoley, this.consolex1, this.consoley1, (new Color(-150205428, true)).getRGB());
/*     */     
/* 111 */     RenderUtil.drawOutlineRect(this.consolex, (this.consoley1 + 3), (this.consolex1 - this.consolex), 15.0F, 4.0F, (new Color(-844584792, true)).getRGB());
/* 112 */     RenderUtil.drawRect2(this.consolex, (this.consoley1 + 3), this.consolex1, (this.consoley1 + 17), (new Color(-150205428, true)).getRGB());
/* 113 */     FontRender.drawString3("cursor pos: " + (this.hoverx * 64) + "x  " + (this.hovery * 64) + "z", (this.consolex + 4), (this.consoley1 + 6), -1);
/*     */     
/* 115 */     RenderUtil.drawOutlineRect(this.consolex, (this.consoley1 + 20), (this.consolex1 - this.consolex), 15.0F, 4.0F, (new Color(-844584792, true)).getRGB());
/*     */     
/* 117 */     if (!track) {
/* 118 */       RenderUtil.drawRect2(this.consolex, (this.consoley1 + 20), this.consolex1, (this.consoley1 + 35), (new Color(-150205428, true)).getRGB());
/* 119 */       FontRender.drawString3("tracker off", (this.consolex + 4), (this.consoley1 + 26), -1);
/*     */     } else {
/* 121 */       RenderUtil.drawRect2(this.consolex, (this.consoley1 + 20), this.consolex1, (this.consoley1 + 35), (new Color(-144810402, true)).getRGB());
/* 122 */       FontRender.drawString3("tracker on", (this.consolex + 4), (this.consoley1 + 26), -1);
/*     */     } 
/*     */     
/* 125 */     RenderUtil.drawOutlineRect(this.radarx, this.radary, (this.radarx1 - this.radarx), (this.radary1 - this.radary), 4.0F, (new Color(-844584792, true)).getRGB());
/* 126 */     RenderUtil.drawRect2(this.radarx, this.radary, this.radarx1, this.radary1, (new Color(-535489259, true)).getRGB());
/*     */     try {
/* 128 */       for (NoCom.Dot point : NoCom.dots) {
/* 129 */         if (point.type == NoCom.DotType.Searched) {
/* 130 */           RenderUtil.drawRect2((point.posX / 4.0F + this.centerx), (point.posY / 4.0F + this.centery), (point.posX / 4.0F + (this.radarx1 - this.radarx) / getscale() + this.centerx), (point.posY / 4.0F + (this.radary1 - this.radary) / getscale() + this.centery), (new Color(-408377176, true)).getRGB()); continue;
/*     */         } 
/* 132 */         RenderUtil.drawRect2((point.posX / 4.0F + this.centerx), (point.posY / 4.0F + this.centery), (point.posX / 4.0F + (this.radarx1 - this.radarx) / getscale() + this.centerx), (point.posY / 4.0F + (this.radary1 - this.radary) / getscale() + this.centery), (new Color(3991304)).getRGB());
/*     */       }
/*     */     
/* 135 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 138 */     RenderUtil.drawRect2((this.centerx - 1.0F), (this.centery - 1.0F), (this.centerx + 1.0F), (this.centery + 1.0F), (new Color(16712451)).getRGB());
/* 139 */     RenderUtil.drawRect2(this.field_146297_k.field_71439_g.field_70165_t / 16.0D / 4.0D + this.centerx, this.field_146297_k.field_71439_g.field_70161_v / 16.0D / 4.0D + this.centery, this.field_146297_k.field_71439_g.field_70165_t / 16.0D / 4.0D + ((this.radarx1 - this.radarx) / getscale()) + this.centerx, this.field_146297_k.field_71439_g.field_70161_v / 16.0D / 4.0D + ((this.radary1 - this.radary) / getscale()) + this.centery, (new Color(4863)).getRGB());
/*     */     
/* 141 */     if (mouseX > this.radarx && mouseX < this.radarx1 && mouseY > this.radary && mouseY < this.radary1) {
/* 142 */       this.hoverx = mouseX - this.centerx;
/* 143 */       this.hovery = mouseY - this.centery;
/*     */     } 
/*     */     
/* 146 */     RenderUtil.glScissor(this.consolex, this.consoley, this.consolex1, (this.consoley1 - 10), sr);
/* 147 */     GL11.glEnable(3089);
/*     */     try {
/* 149 */       for (NoCom.cout out : this.consoleout) {
/* 150 */         FontRender.drawString3(out.string, (this.consolex + 4), (this.consoley + 6 + out.posY * 11 + this.wheely), -1);
/*     */       }
/* 152 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 155 */     GL11.glDisable(3089);
/*     */     
/* 157 */     FontRender.drawString3("X+", (this.radarx1 + 5), this.centery, -1);
/* 158 */     FontRender.drawString3("X-", (this.radarx - 15), this.centery, -1);
/* 159 */     FontRender.drawString3("Y+", this.centerx, (this.radary1 + 5), -1);
/* 160 */     FontRender.drawString3("Y-", this.centerx, (this.radary - 8), -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73864_a(int mouseX, int mouseY, int clickedButton) {
/* 167 */     if (mouseX > this.radarx && mouseX < this.radarx1 && mouseY > this.radary && mouseY < this.radary1) {
/* 168 */       busy = true;
/* 169 */       this.searchx = mouseX - this.centerx;
/* 170 */       this.searchy = mouseY - this.centery;
/*     */       
/* 172 */       Command.sendMessage((this.searchx * 64) + " " + (this.searchy * 64));
/* 173 */       NoCom.rerun(this.searchx * 64, this.searchy * 64);
/* 174 */       (getInstance()).consoleout.add(new NoCom.cout((NoCom.getInstance()).couti, "Selected pos " + (this.searchx * 65) + "x " + (this.searchy * 64) + "z "));
/* 175 */       (NoCom.getInstance()).couti++;
/*     */     } 
/*     */     
/* 178 */     if (mouseX > this.consolex && mouseX < this.consolex1 && mouseY > this.consoley1 + 20 && mouseY < this.consoley1 + 36) {
/* 179 */       track = !track;
/*     */     }
/*     */   }
/*     */   
/*     */   public void checkMouseWheel(int mouseX, int mouseY) {
/* 184 */     int dWheel = Mouse.getDWheel();
/* 185 */     if (dWheel < 0) {
/* 186 */       this.wheely -= 20;
/* 187 */     } else if (dWheel > 0) {
/* 188 */       this.wheely += 20;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\misc\GuiScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */