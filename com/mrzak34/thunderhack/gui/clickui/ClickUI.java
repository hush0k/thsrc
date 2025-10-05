/*     */ package com.mrzak34.thunderhack.gui.clickui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.clickui.window.ModuleWindow;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClickUI
/*     */   extends GuiScreen
/*     */ {
/*  21 */   private static ClickUI INSTANCE = new ClickUI();
/*     */   private final List<ModuleWindow> windows;
/*     */   private double scrollSpeed;
/*     */   private boolean firstOpen;
/*     */   
/*     */   public ClickUI() {
/*  27 */     this.windows = Lists.newArrayList();
/*  28 */     this.firstOpen = true;
/*  29 */     setInstance();
/*     */   }
/*     */   
/*     */   public static ClickUI getInstance() {
/*  33 */     if (INSTANCE == null) {
/*  34 */       INSTANCE = new ClickUI();
/*     */     }
/*  36 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static ClickUI getClickGui() {
/*  40 */     return getInstance();
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  44 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  50 */     if (this.firstOpen) {
/*  51 */       double x = 20.0D, y = 20.0D;
/*  52 */       double offset = 0.0D;
/*  53 */       int windowHeight = 18;
/*  54 */       ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/*  55 */       int i = 0;
/*  56 */       for (Module.Category category : Thunderhack.moduleManager.getCategories()) {
/*  57 */         if (category.getName().contains("HUD"))
/*  58 */           continue;  ModuleWindow window = new ModuleWindow(category.getName(), Thunderhack.moduleManager.getModulesByCategory(category), i, x + offset, y, 108.0D, windowHeight);
/*  59 */         window.setOpen(true);
/*  60 */         this.windows.add(window);
/*  61 */         offset += 110.0D;
/*     */         
/*  63 */         if (offset > sr.func_78326_a()) {
/*  64 */           offset = 0.0D;
/*     */         }
/*  66 */         i++;
/*     */       } 
/*  68 */       this.firstOpen = false;
/*     */     } 
/*     */     
/*  71 */     this.windows.forEach(ModuleWindow::init);
/*     */     
/*  73 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float delta) {
/*  79 */     double dWheel = Mouse.getDWheel();
/*     */     
/*  81 */     if (dWheel > 0.0D) {
/*  82 */       this.scrollSpeed += 14.0D;
/*  83 */     } else if (dWheel < 0.0D) {
/*  84 */       this.scrollSpeed -= 14.0D;
/*     */     } 
/*     */     
/*  87 */     for (ModuleWindow window : this.windows) {
/*  88 */       if (Keyboard.isKeyDown(208)) {
/*  89 */         window.setY(window.getY() + 2.0D);
/*  90 */       } else if (Keyboard.isKeyDown(200)) {
/*  91 */         window.setY(window.getY() - 2.0D);
/*  92 */       } else if (Keyboard.isKeyDown(203)) {
/*  93 */         window.setX(window.getX() - 2.0D);
/*  94 */       } else if (Keyboard.isKeyDown(205)) {
/*  95 */         window.setX(window.getX() + 2.0D);
/*  96 */       }  if (dWheel != 0.0D) {
/*  97 */         window.setY(window.getY() + this.scrollSpeed);
/*     */       } else {
/*  99 */         this.scrollSpeed = 0.0D;
/* 100 */       }  window.render(mouseX, mouseY, delta, ((ColorSetting)(ClickGui.getInstance()).hcolor1.getValue()).getColorObject(), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146281_b() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 123 */     this.windows.forEach(ModuleWindow::tick);
/* 124 */     super.func_73876_c();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73864_a(int mouseX, int mouseY, int button) throws IOException {
/* 129 */     this.windows.forEach(w -> {
/*     */           w.mouseClicked(mouseX, mouseY, button);
/*     */ 
/*     */           
/*     */           this.windows.forEach(());
/*     */         });
/*     */ 
/*     */     
/* 137 */     super.func_73864_a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146286_b(int mouseX, int mouseY, int button) {
/* 142 */     this.windows.forEach(w -> w.mouseReleased(mouseX, mouseY, button));
/* 143 */     super.func_146286_b(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() throws IOException {
/* 148 */     this.windows.forEach(w -> {
/*     */           try {
/*     */             w.handleMouseInput();
/* 151 */           } catch (IOException iOException) {}
/*     */         });
/*     */ 
/*     */     
/* 155 */     super.func_146274_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73869_a(char chr, int keyCode) throws IOException {
/* 160 */     this.windows.forEach(w -> w.keyTyped(chr, keyCode));
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (keyCode == 1 || keyCode == ((ClickGui)Thunderhack.moduleManager.getModuleByClass(ClickGui.class)).getBind().getKey()) {
/* 165 */       this.field_146297_k.field_71462_r = null;
/* 166 */       this.field_146297_k.func_147108_a(null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\ClickUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */