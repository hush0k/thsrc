/*     */ package com.mrzak34.thunderhack.gui.mainmenu;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiMultiplayer;
/*     */ import net.minecraft.client.gui.GuiOptions;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiWorldSelection;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThunderMenu
/*     */   extends GuiScreen
/*     */ {
/*     */   private MainMenuShader backgroundShader;
/*     */   
/*     */   public ThunderMenu() {
/*     */     try {
/*  31 */       if (Thunderhack.moduleManager != null) {
/*  32 */         switch ((MainSettings.ShaderModeEn)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).shaderMode.getValue()) {
/*     */           case WarThunder:
/*  34 */             this.backgroundShader = new MainMenuShader("/moon.fsh");
/*     */             break;
/*     */           case Smoke:
/*  37 */             this.backgroundShader = new MainMenuShader("/mainmenu.fsh");
/*     */             break;
/*     */           case Dicks:
/*  40 */             this.backgroundShader = new MainMenuShader("/dicks.fsh");
/*     */             break;
/*     */         } 
/*     */       }
/*  44 */     } catch (IOException var9) {
/*  45 */       throw new IllegalStateException("Failed to load backgound shader", var9);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float func(float var0) {
/*  50 */     if ((var0 %= 360.0F) >= 180.0F) {
/*  51 */       var0 -= 360.0F;
/*     */     }
/*     */     
/*  54 */     if (var0 < -180.0F) {
/*  55 */       var0 += 360.0F;
/*     */     }
/*     */     
/*  58 */     return var0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  63 */     ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/*  64 */     this.field_146294_l = sr.func_78326_a();
/*  65 */     this.field_146295_m = sr.func_78328_b();
/*     */     
/*  67 */     this.field_146292_n.add(new GuiMainMenuButton(1, sr.func_78326_a() / 2 - 110, sr.func_78328_b() / 2 - 70, false, "SINGLEPLAYER", false));
/*  68 */     this.field_146292_n.add(new GuiMainMenuButton(2, sr.func_78326_a() / 2 + 4, sr.func_78328_b() / 2 - 70, false, "MULTIPLAYER", false));
/*  69 */     this.field_146292_n.add(new GuiMainMenuButton(0, sr.func_78326_a() / 2 - 110, sr.func_78328_b() / 2 - 29, false, "SETTINGS", false));
/*  70 */     this.field_146292_n.add(new GuiMainMenuButton(14, sr.func_78326_a() / 2 + 4, sr.func_78328_b() / 2 - 29, false, "ALTMANAGER", false));
/*  71 */     this.field_146292_n.add(new GuiMainMenuButton(666, sr.func_78326_a() / 2 - 110, sr.func_78328_b() / 2 + 12, true, "EXIT", false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  76 */     ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/*  77 */     GlStateManager.func_179129_p();
/*  78 */     this.backgroundShader.useShader((int)(sr.func_78326_a() * 2.0F), (int)(sr.func_78328_b() * 2.0F), mouseX, mouseY, (float)(System.currentTimeMillis() - Thunderhack.initTime) / 1000.0F);
/*     */     
/*  80 */     GL11.glBegin(7);
/*  81 */     GL11.glVertex2f(-1.0F, -1.0F);
/*  82 */     GL11.glVertex2f(-1.0F, 1.0F);
/*  83 */     GL11.glVertex2f(1.0F, 1.0F);
/*  84 */     GL11.glVertex2f(1.0F, -1.0F);
/*  85 */     GL11.glEnd();
/*  86 */     GL20.glUseProgram(0);
/*  87 */     GlStateManager.func_179129_p();
/*  88 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  89 */     Color color = new Color(-2046820352, true);
/*     */ 
/*     */     
/*  92 */     float half_w = sr.func_78326_a() / 2.0F;
/*  93 */     float halh_h = sr.func_78328_b() / 2.0F;
/*     */ 
/*     */     
/*  96 */     RoundedShader.drawGradientRound(half_w - 120.0F, halh_h - 80.0F, 240.0F, 140.0F, 15.0F, color, color, color, color);
/*     */ 
/*     */     
/*  99 */     FontRender.drawCentString8("THUNDERHACK", ((int)half_w - 52), ((int)halh_h - 82 - FontRender.getFontHeight8()), -1);
/*     */     
/* 101 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton button) {
/* 106 */     if (button.field_146127_k == 0) {
/* 107 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiOptions(this, this.field_146297_k.field_71474_y));
/*     */     }
/* 109 */     if (button.field_146127_k == 1) {
/* 110 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiWorldSelection(this));
/*     */     }
/* 112 */     if (button.field_146127_k == 2) {
/* 113 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiMultiplayer(this));
/*     */     }
/* 115 */     if (button.field_146127_k == 14) {
/* 116 */       this.field_146297_k.func_147108_a(new GuiAltManager());
/*     */     }
/* 118 */     if (button.field_146127_k == 666) {
/* 119 */       Thunderhack.unload(false);
/* 120 */       this.field_146297_k.func_71400_g();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\mainmenu\ThunderMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */