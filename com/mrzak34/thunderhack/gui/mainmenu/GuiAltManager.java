/*     */ package com.mrzak34.thunderhack.gui.mainmenu;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.clickui.elements.SliderElement;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.ThunderUtils;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ public class GuiAltManager
/*     */   extends GuiScreen
/*     */ {
/*  27 */   public static List<AltCompoment> altscomponents = new ArrayList<>();
/*  28 */   public static Timer clicktimer = new Timer();
/*     */ 
/*     */   
/*     */   int dwheel;
/*     */   
/*     */   private MainMenuShader backgroundShader;
/*     */   
/*     */   private boolean listening = false;
/*     */   
/*  37 */   private String add_name = "";
/*     */   
/*     */   public GuiAltManager() {
/*     */     try {
/*  41 */       if (Thunderhack.moduleManager != null) {
/*  42 */         switch ((MainSettings.ShaderModeEn)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).shaderMode.getValue()) {
/*     */           case WarThunder:
/*  44 */             this.backgroundShader = new MainMenuShader("/moon.fsh");
/*     */             break;
/*     */           case Smoke:
/*  47 */             this.backgroundShader = new MainMenuShader("/mainmenu.fsh");
/*     */             break;
/*     */           case Dicks:
/*  50 */             this.backgroundShader = new MainMenuShader("/dicks.fsh");
/*     */             break;
/*     */         } 
/*     */       }
/*  54 */     } catch (IOException var9) {
/*  55 */       throw new IllegalStateException("Failed to load backgound shader", var9);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  61 */     ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/*  62 */     this.field_146294_l = sr.func_78326_a();
/*  63 */     this.field_146295_m = sr.func_78328_b();
/*     */ 
/*     */     
/*  66 */     this.field_146292_n.add(new GuiMainMenuButton(420, sr.func_78326_a() / 2 - 120, sr.func_78328_b() - 135, false, "ADD", true));
/*  67 */     this.field_146292_n.add(new GuiMainMenuButton(69, sr.func_78326_a() / 2 + 4, sr.func_78328_b() - 135, false, "RANDOM", true));
/*  68 */     this.field_146292_n.add(new GuiMainMenuButton(228, sr.func_78326_a() / 2 - 120, sr.func_78328_b() - 96, true, "BACK", true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  77 */     ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/*  78 */     GlStateManager.func_179129_p();
/*  79 */     this.backgroundShader.useShader(sr.func_78326_a() * 2, sr.func_78328_b() * 2, mouseX, mouseY, (float)(System.currentTimeMillis() - Thunderhack.initTime) / 1000.0F);
/*  80 */     checkMouseWheel();
/*     */     
/*  82 */     GL11.glBegin(7);
/*  83 */     GL11.glVertex2f(-1.0F, -1.0F);
/*  84 */     GL11.glVertex2f(-1.0F, 1.0F);
/*  85 */     GL11.glVertex2f(1.0F, 1.0F);
/*  86 */     GL11.glVertex2f(1.0F, -1.0F);
/*  87 */     GL11.glEnd();
/*  88 */     GL20.glUseProgram(0);
/*  89 */     GlStateManager.func_179129_p();
/*  90 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  91 */     Color color = new Color(-2046820352, true);
/*  92 */     Color color2 = new Color(-469762048, true);
/*  93 */     Color color3 = new Color(-466538191, true);
/*     */ 
/*     */     
/*  96 */     float half_w = sr.func_78326_a() / 2.0F;
/*  97 */     float halh_h = sr.func_78328_b() / 2.0F;
/*     */     
/*  99 */     RoundedShader.drawGradientRound(half_w - 120.0F, 20.0F, 240.0F, (sr.func_78328_b() - 160), 15.0F, color, color, color, color);
/*     */     
/* 101 */     int alts_y = 0;
/*     */     
/* 103 */     for (String alt : Thunderhack.alts) {
/* 104 */       altscomponents.add(new AltCompoment((int)(half_w - 105.0F), 30 + alts_y + this.dwheel, alt));
/* 105 */       alts_y += 49;
/*     */     } 
/*     */     
/* 108 */     RenderUtil.glScissor(half_w - 110.0F, 20.0F, half_w - 105.0F + 215.0F, (sr.func_78328_b() - 140), sr);
/* 109 */     GL11.glEnable(3089);
/* 110 */     altscomponents.forEach(altCompoment -> altCompoment.render(mouseX, mouseY));
/* 111 */     GL11.glDisable(3089);
/*     */     
/* 113 */     if (this.listening) {
/* 114 */       RoundedShader.drawGradientRound(half_w - 60.0F, halh_h - 40.0F, 120.0F, 80.0F, 7.0F, color2, color2, color2, color2);
/* 115 */       RoundedShader.drawGradientRound(half_w - 55.0F, halh_h - 10.0F, 110.0F, 10.0F, 1.0F, color3, color3, color3, color3);
/* 116 */       RoundedShader.drawGradientRound(half_w - 15.0F, halh_h + 10.0F, 30.0F, 10.0F, 2.0F, color3, color3, color3, color3);
/*     */ 
/*     */       
/* 119 */       boolean hover_add = (mouseX > half_w - 15.0F && mouseX < half_w + 15.0F && mouseY > halh_h + 10.0F && mouseY < halh_h + 20.0F);
/* 120 */       FontRender.drawCentString6("ADD", half_w, halh_h + 14.0F, hover_add ? (new Color(8026746)).getRGB() : -1);
/* 121 */       FontRender.drawCentString6(this.listening ? this.add_name : "name", half_w, halh_h - 7.0F, this.listening ? -1 : (new Color(8026746)).getRGB());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (mouseX > half_w - 15.0F && mouseX < half_w + 15.0F && mouseY > halh_h + 10.0F && mouseY < halh_h + 20.0F && Mouse.isButtonDown(0) && this.listening) {
/* 127 */       Thunderhack.alts.add(this.add_name);
/* 128 */       this.add_name = "";
/* 129 */       this.listening = false;
/*     */     } 
/*     */ 
/*     */     
/* 133 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/* 134 */     altscomponents.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73864_a(int x, int y, int button) {
/* 140 */     ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/* 141 */     if (x >= sr.func_78326_a() / 2 - 120 && x <= sr.func_78326_a() / 2 - 13 && y >= sr.func_78328_b() - 135 && y <= sr.func_78328_b() - 100) {
/* 142 */       this.listening = true;
/*     */     }
/* 144 */     if (x >= sr.func_78326_a() / 2 + 4 && x <= sr.func_78326_a() / 2 + 111 && y >= sr.func_78328_b() - 135 && y <= sr.func_78328_b() - 100) {
/* 145 */       String name = "Th" + (int)(Math.random() * 10000.0D);
/* 146 */       Thunderhack.alts.add(name);
/*     */       try {
/* 148 */         new Thread(() -> ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + name + "/16.png", name));
/* 149 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 152 */     if (x >= sr.func_78326_a() / 2 - 120 && x <= sr.func_78326_a() / 2 + 102 && y >= sr.func_78328_b() - 96 && y <= sr.func_78328_b() - 61) {
/* 153 */       this.field_146297_k.func_147108_a(new ThunderMenu());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73869_a(char chr, int keyCode) {
/* 159 */     if (this.listening) {
/* 160 */       switch (keyCode) {
/*     */         case 1:
/*     */           return;
/*     */         
/*     */         case 28:
/* 165 */           Thunderhack.alts.add(this.add_name);
/* 166 */           ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + this.add_name + "/16.png", this.add_name);
/* 167 */           this.add_name = "";
/* 168 */           this.listening = false;
/*     */         
/*     */         case 14:
/* 171 */           this.add_name = SliderElement.removeLastChar(this.add_name);
/*     */           break;
/*     */       } 
/* 174 */       if (ChatAllowedCharacters.func_71566_a(chr)) {
/* 175 */         this.add_name += chr;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkMouseWheel() {
/* 181 */     int dWheel = Mouse.getDWheel();
/* 182 */     if (dWheel < 0) {
/* 183 */       this.dwheel -= 10;
/* 184 */     } else if (dWheel > 0) {
/* 185 */       this.dwheel += 10;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\mainmenu\GuiAltManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */