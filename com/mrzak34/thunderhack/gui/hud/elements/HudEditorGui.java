/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.clickui.EaseBackIn;
/*     */ import com.mrzak34.thunderhack.gui.clickui.window.ModuleWindow;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*     */ import com.mrzak34.thunderhack.notification.Animation;
/*     */ import com.mrzak34.thunderhack.notification.DecelerateAnimation;
/*     */ import com.mrzak34.thunderhack.notification.Direction;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class HudEditorGui
/*     */   extends GuiScreen {
/*  23 */   private static HudEditorGui INSTANCE = new HudEditorGui();
/*     */   private final List<ModuleWindow> windows;
/*     */   private Animation openAnimation;
/*     */   private Animation bgAnimation;
/*     */   private Animation rAnimation;
/*     */   private double scrollSpeed;
/*     */   private boolean firstOpen;
/*     */   private double dWheel;
/*     */   public static boolean mouse_state;
/*     */   public static int mouse_x;
/*     */   public static int mouse_y;
/*     */   
/*     */   public HudEditorGui() {
/*  36 */     this.windows = Lists.newArrayList();
/*  37 */     this.firstOpen = true;
/*  38 */     setInstance();
/*     */   }
/*     */   
/*     */   public static HudEditorGui getInstance() {
/*  42 */     if (INSTANCE == null) {
/*  43 */       INSTANCE = new HudEditorGui();
/*     */     }
/*  45 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static HudEditorGui getHudGui() {
/*  49 */     return getInstance();
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  53 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  59 */     this.openAnimation = (Animation)new EaseBackIn(270, 0.4000000059604645D, 1.13F);
/*  60 */     this.rAnimation = (Animation)new DecelerateAnimation(300, 1.0D);
/*  61 */     this.bgAnimation = (Animation)new DecelerateAnimation(300, 1.0D);
/*  62 */     if (this.firstOpen) {
/*  63 */       double x = 20.0D, y = 20.0D;
/*  64 */       double offset = 0.0D;
/*  65 */       int windowHeight = 18;
/*  66 */       ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/*  67 */       int i = 0;
/*  68 */       for (Module.Category category : Thunderhack.moduleManager.getCategories()) {
/*  69 */         if (!category.getName().contains("HUD"))
/*  70 */           continue;  ModuleWindow window = new ModuleWindow(category.getName(), Thunderhack.moduleManager.getModulesByCategory(category), i, x + offset, y, 108.0D, windowHeight);
/*  71 */         window.setOpen(true);
/*  72 */         this.windows.add(window);
/*  73 */         offset += 110.0D;
/*     */         
/*  75 */         if (offset > sr.func_78326_a()) {
/*  76 */           offset = 0.0D;
/*     */         }
/*  78 */         i++;
/*     */       } 
/*  80 */       this.firstOpen = false;
/*     */     } 
/*     */     
/*  83 */     this.windows.forEach(ModuleWindow::init);
/*     */     
/*  85 */     super.func_73866_w_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float delta) {
/*  91 */     if (this.openAnimation.isDone() && this.openAnimation.getDirection().equals(Direction.BACKWARDS)) {
/*  92 */       this.windows.forEach(ModuleWindow::onClose);
/*  93 */       this.field_146297_k.field_71462_r = null;
/*  94 */       this.field_146297_k.func_147108_a(null);
/*     */     } 
/*     */     
/*  97 */     this.dWheel = Mouse.getDWheel();
/*     */     
/*  99 */     mouse_x = mouseX;
/* 100 */     mouse_y = mouseY;
/*     */     
/* 102 */     if (this.dWheel > 0.0D) {
/* 103 */       this.scrollSpeed += 14.0D;
/* 104 */     } else if (this.dWheel < 0.0D) {
/* 105 */       this.scrollSpeed -= 14.0D;
/*     */     } 
/* 107 */     double anim = this.openAnimation.getOutput() + 0.6000000238418579D;
/*     */ 
/*     */     
/* 110 */     GlStateManager.func_179094_E();
/*     */     
/* 112 */     double centerX = (this.field_146294_l >> 1);
/* 113 */     double centerY = (this.field_146295_m >> 1);
/*     */     
/* 115 */     GlStateManager.func_179137_b(centerX, centerY, 0.0D);
/* 116 */     GlStateManager.func_179139_a(anim, anim, 1.0D);
/* 117 */     GlStateManager.func_179137_b(-centerX, -centerY, 0.0D);
/*     */     
/* 119 */     for (ModuleWindow window : this.windows) {
/* 120 */       if (Keyboard.isKeyDown(208)) {
/* 121 */         window.setY(window.getY() + 2.0D);
/* 122 */       } else if (Keyboard.isKeyDown(200)) {
/* 123 */         window.setY(window.getY() - 2.0D);
/* 124 */       } else if (Keyboard.isKeyDown(203)) {
/* 125 */         window.setX(window.getX() - 2.0D);
/* 126 */       } else if (Keyboard.isKeyDown(205)) {
/* 127 */         window.setX(window.getX() + 2.0D);
/* 128 */       }  if (this.dWheel != 0.0D) {
/* 129 */         window.setY(window.getY() + this.scrollSpeed);
/*     */       } else {
/* 131 */         this.scrollSpeed = 0.0D;
/*     */       } 
/* 133 */       window.render(mouseX, mouseY, delta, ((ColorSetting)(ClickGui.getInstance()).hcolor1.getValue()).getColorObject(), (this.openAnimation.isDone() && this.openAnimation.getDirection() == Direction.FORWARDS));
/*     */     } 
/* 135 */     GlStateManager.func_179121_F();
/*     */     
/* 137 */     super.func_73863_a(mouseX, mouseY, delta);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146281_b() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73876_c() {
/* 147 */     this.windows.forEach(ModuleWindow::tick);
/* 148 */     super.func_73876_c();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73864_a(int mouseX, int mouseY, int button) throws IOException {
/* 153 */     this.windows.forEach(w -> {
/*     */           w.mouseClicked(mouseX, mouseY, button);
/*     */ 
/*     */           
/*     */           this.windows.forEach(());
/*     */         });
/*     */ 
/*     */     
/* 161 */     mouse_state = true;
/* 162 */     super.func_73864_a(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146286_b(int mouseX, int mouseY, int button) {
/* 167 */     this.windows.forEach(w -> w.mouseReleased(mouseX, mouseY, button));
/* 168 */     mouse_state = false;
/* 169 */     super.func_146286_b(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146274_d() throws IOException {
/* 174 */     this.windows.forEach(w -> {
/*     */           try {
/*     */             w.handleMouseInput();
/* 177 */           } catch (IOException iOException) {}
/*     */         });
/*     */ 
/*     */     
/* 181 */     super.func_146274_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73869_a(char chr, int keyCode) throws IOException {
/* 186 */     this.windows.forEach(w -> w.keyTyped(chr, keyCode));
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (keyCode == 1 || keyCode == ((ClickGui)Thunderhack.moduleManager.getModuleByClass(ClickGui.class)).getBind().getKey()) {
/* 191 */       this.bgAnimation.setDirection(Direction.BACKWARDS);
/* 192 */       this.rAnimation.setDirection(Direction.BACKWARDS);
/* 193 */       this.openAnimation.setDirection(Direction.BACKWARDS);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\HudEditorGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */