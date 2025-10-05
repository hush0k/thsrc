/*     */ package com.mrzak34.thunderhack.gui.mainmenu;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.util.PNGtoResourceLocation;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Session;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AltCompoment
/*     */ {
/*     */   int posX;
/*     */   int posY;
/*     */   String name;
/*  26 */   ResourceLocation head = null;
/*  27 */   ResourceLocation crackedSkin = new ResourceLocation("textures/cracked.png");
/*     */   
/*     */   public AltCompoment(int posX, int posY, String name) {
/*  30 */     this.posX = posX;
/*  31 */     this.posY = posY;
/*  32 */     this.head = PNGtoResourceLocation.getTexture2(name, "png");
/*  33 */     this.name = name;
/*     */   }
/*     */   
/*     */   public static void login(String string) {
/*     */     try {
/*  38 */       Field field = Minecraft.class.getDeclaredField("field_71449_j");
/*  39 */       field.setAccessible(true);
/*  40 */       Field field2 = Field.class.getDeclaredField("modifiers");
/*  41 */       field2.setAccessible(true);
/*  42 */       field2.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/*  43 */       field.set(Util.mc, new Session(string, "", "", "mojang"));
/*  44 */       System.out.println("logged in " + string);
/*  45 */     } catch (Exception e) {
/*  46 */       System.out.println("ALT MANAGER ERROR!");
/*  47 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void render(int mouseX, int mouseY) {
/*  52 */     Color color = new Color(1644167168, true);
/*  53 */     Color selected_color = new Color(-2128776703, true);
/*     */ 
/*     */     
/*  56 */     RoundedShader.drawRound(this.posX, this.posY, 210.0F, 40.0F, 8.0F, Util.mc.func_110432_I().func_111285_a().equals(this.name) ? selected_color : color);
/*     */     
/*  58 */     renderCustomTexture((this.posX + 5), (this.posY + 5), 30, 30, 16.0F, 16.0F);
/*  59 */     FontRender.drawString3(this.name, (this.posX + 38), (this.posY + 5), Util.mc.func_110432_I().func_111285_a().equals(this.name) ? (new Color(8026746)).getRGB() : -1);
/*     */     
/*  61 */     RoundedShader.drawRound((this.posX + 165), (this.posY + 5), 35.0F, 12.0F, 3.0F, isHoveringLoggin(mouseX, mouseY) ? new Color(-2130380743, true) : new Color(-2126577920, true));
/*  62 */     FontRender.drawCentString6("log", this.posX + 182.5F, (this.posY + 10), isHoveringLoggin(mouseX, mouseY) ? (new Color(8026746)).getRGB() : -1);
/*     */     
/*  64 */     RoundedShader.drawRound((this.posX + 165), (this.posY + 22), 35.0F, 12.0F, 3.0F, isHoveringDelete(mouseX, mouseY) ? new Color(-2113994752, true) : new Color(-2114584062, true));
/*  65 */     FontRender.drawCentString6("del", this.posX + 182.5F, (this.posY + 27), isHoveringDelete(mouseX, mouseY) ? (new Color(8026746)).getRGB() : -1);
/*     */     
/*  67 */     if (Mouse.isButtonDown(0)) {
/*  68 */       mouseClicked(mouseX, mouseY, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isHoveringLoggin(int x, int y) {
/*  73 */     return (x >= this.posX + 165 && x <= this.posX + 200 && y >= this.posY + 5 && y <= this.posY + 17);
/*     */   }
/*     */   
/*     */   private boolean isHoveringDelete(int x, int y) {
/*  77 */     return (x >= this.posX + 165 && x <= this.posX + 200 && y >= this.posY + 22 && y <= this.posY + 34);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int x, int y, int button) {
/*  82 */     if (!GuiAltManager.clicktimer.passedMs(500L)) {
/*     */       return;
/*     */     }
/*  85 */     if (isHoveringLoggin(x, y)) {
/*  86 */       login(this.name);
/*  87 */       GuiAltManager.clicktimer.reset();
/*     */     } 
/*  89 */     if (isHoveringDelete(x, y)) {
/*  90 */       Thunderhack.alts.remove(this.name);
/*  91 */       GuiAltManager.clicktimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderCustomTexture(double x, double y, int width, int height, float tileWidth, float tileHeight) {
/*  96 */     if (this.head != null) {
/*  97 */       Util.mc.func_110434_K().func_110577_a(this.head);
/*     */     } else {
/*  99 */       Util.mc.func_110434_K().func_110577_a(this.crackedSkin);
/*     */     } 
/* 101 */     GL11.glEnable(3042);
/* 102 */     Gui.func_152125_a((int)x, (int)y, 0.0F, 0.0F, 16, 16, width, height, tileWidth, tileHeight);
/* 103 */     GL11.glDisable(3042);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\mainmenu\AltCompoment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */