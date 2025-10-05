/*     */ package com.mrzak34.thunderhack.gui.thundergui2;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.Particles;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.TargetHud;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.BooleanComponent;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.CategoryPlate;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.ColorPickerComponent;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.ConfigComponent;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.FriendComponent;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.ModeComponent;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.ModulePlate;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.ParentComponent;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.components.SettingElement;
/*     */ import com.mrzak34.thunderhack.manager.ConfigManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.ThunderHackGui;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.render.Stencil;
/*     */ import com.mrzak34.thunderhack.util.shaders.BetterAnimation;
/*     */ import java.awt.Color;
/*     */ import java.awt.Desktop;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ThunderGui2 extends GuiScreen {
/*  41 */   public static CurrentMode currentMode = CurrentMode.Modules; public static boolean scroll_lock = false;
/*     */   public static ModulePlate selected_plate;
/*     */   public static ModulePlate prev_selected_plate;
/*  44 */   public static BetterAnimation open_animation = new BetterAnimation(5);
/*     */ 
/*     */   
/*     */   public static boolean open_direction = false;
/*     */   
/*  49 */   private static ThunderGui2 INSTANCE = new ThunderGui2();
/*     */ 
/*     */   
/*  52 */   public final ArrayList<ModulePlate> components = new ArrayList<>();
/*  53 */   public final CopyOnWriteArrayList<CategoryPlate> categories = new CopyOnWriteArrayList<>();
/*  54 */   public final ArrayList<SettingElement> settings = new ArrayList<>();
/*  55 */   public final CopyOnWriteArrayList<ConfigComponent> configs = new CopyOnWriteArrayList<>();
/*  56 */   public final CopyOnWriteArrayList<FriendComponent> friends = new CopyOnWriteArrayList<>();
/*  57 */   private final int main_width = 400;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public int main_posX = 100;
/*  66 */   public int main_posY = 100;
/*  67 */   public Module.Category current_category = Module.Category.COMBAT;
/*  68 */   public Module.Category new_category = Module.Category.COMBAT;
/*  69 */   float category_animation = 0.0F;
/*  70 */   float settings_animation = 0.0F;
/*  71 */   float manager_animation = 0.0F; int prevCategoryY; int CategoryY; int slider_y;
/*     */   int slider_x;
/*  73 */   private int main_height = 250;
/*     */   private boolean dragging = false;
/*     */   private boolean rescale = false;
/*  76 */   private int drag_x = 0;
/*  77 */   private int drag_y = 0;
/*  78 */   private int rescale_y = 0;
/*  79 */   private float scroll = 0.0F;
/*     */   private boolean first_open = true;
/*     */   private boolean searching = false;
/*     */   private boolean listening_friend = false;
/*     */   private boolean listening_config = false;
/*  84 */   private String search_string = "Search";
/*  85 */   private String config_string = "Save config";
/*  86 */   private String friend_string = "Add friend";
/*  87 */   private CurrentMode prevMode = CurrentMode.Modules;
/*     */   
/*     */   public static boolean mouse_state;
/*     */   public static int mouse_x;
/*     */   public static int mouse_y;
/*     */   
/*     */   public ThunderGui2() {
/*  94 */     setInstance();
/*  95 */     load();
/*  96 */     this.CategoryY = getCategoryY(this.new_category);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ThunderGui2 getInstance() {
/* 101 */     if (INSTANCE == null) {
/* 102 */       INSTANCE = new ThunderGui2();
/*     */     }
/* 104 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static ThunderGui2 getThunderGui() {
/* 108 */     open_animation = new BetterAnimation();
/* 109 */     open_direction = true;
/* 110 */     return getInstance();
/*     */   }
/*     */   
/*     */   public static String removeLastChar(String str) {
/* 114 */     String output = "";
/* 115 */     if (str != null && str.length() > 0) {
/* 116 */       output = str.substring(0, str.length() - 1);
/*     */     }
/* 118 */     return output;
/*     */   }
/*     */   
/*     */   public static double deltaTime() {
/* 122 */     return (Minecraft.func_175610_ah() > 0) ? (1.0D / Minecraft.func_175610_ah()) : 1.0D;
/*     */   }
/*     */   
/*     */   public static float fast(float end, float start, float multiple) {
/* 126 */     return (1.0F - MathUtil.clamp((float)(deltaTime() * multiple), 0.0F, 1.0F)) * end + MathUtil.clamp((float)(deltaTime() * multiple), 0.0F, 1.0F) * start;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/* 130 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public void load() {
/* 134 */     this.categories.clear();
/* 135 */     this.components.clear();
/* 136 */     this.configs.clear();
/* 137 */     this.friends.clear();
/*     */     
/* 139 */     int module_y = 0;
/* 140 */     for (Module module : Thunderhack.moduleManager.getModulesByCategory(this.current_category)) {
/* 141 */       this.components.add(new ModulePlate(module, this.main_posX + 100, this.main_posY + 40 + module_y, module_y / 35));
/* 142 */       module_y += 35;
/*     */     } 
/*     */     
/* 145 */     int category_y = 0;
/* 146 */     for (Module.Category category : Thunderhack.moduleManager.getCategories()) {
/* 147 */       this.categories.add(new CategoryPlate(category, this.main_posX + 8, this.main_posY + 43 + category_y));
/* 148 */       category_y += 17;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadConfigs() {
/* 155 */     this.friends.clear();
/* 156 */     this.configs.clear();
/* 157 */     (new Thread(() -> {
/*     */           int config_y = 3;
/*     */           for (String file1 : Objects.<List>requireNonNull(ConfigManager.getConfigList())) {
/*     */             this.configs.add(new ConfigComponent(file1, ConfigManager.getConfigDate(file1), this.main_posX + 100, this.main_posY + 40 + config_y, config_y / 35));
/*     */             config_y += 35;
/*     */           } 
/* 163 */         })).start();
/*     */   }
/*     */   
/*     */   public void loadFriends() {
/* 167 */     this.configs.clear();
/* 168 */     this.friends.clear();
/* 169 */     int friend_y = 3;
/* 170 */     for (String friend : Thunderhack.friendManager.getFriends()) {
/* 171 */       this.friends.add(new FriendComponent(friend, this.main_posX + 100, this.main_posY + 40 + friend_y, friend_y / 35));
/* 172 */       friend_y += 35;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/* 177 */     GlStateManager.func_179094_E();
/* 178 */     mouse_x = mouseX;
/* 179 */     mouse_y = mouseY;
/* 180 */     TargetHud.sizeAnimation((this.main_posX + 200.0F), (this.main_posY + this.main_height / 2.0F), open_animation.getAnimationd());
/* 181 */     if (open_animation.getAnimationd() > 0.0D) {
/* 182 */       renderGui(mouseX, mouseY, partialTicks);
/*     */     }
/* 184 */     if (open_animation.getAnimationd() <= 0.01D && !open_direction) {
/* 185 */       open_animation = new BetterAnimation();
/* 186 */       open_direction = false;
/* 187 */       this.field_146297_k.field_71462_r = null;
/* 188 */       this.field_146297_k.func_147108_a(null);
/*     */     } 
/* 190 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public void renderGui(int mouseX, int mouseY, float partialTicks) {
/* 194 */     ScaledResolution sr = new ScaledResolution(this.field_146297_k);
/* 195 */     if (this.dragging) {
/* 196 */       float deltaX = (mouseX - this.drag_x - this.main_posX);
/* 197 */       float deltaY = (mouseY - this.drag_y - this.main_posY);
/*     */       
/* 199 */       this.main_posX = mouseX - this.drag_x;
/* 200 */       this.main_posY = mouseY - this.drag_y;
/*     */       
/* 202 */       this.slider_y += (int)deltaY;
/* 203 */       this.slider_x += (int)deltaX;
/*     */       
/* 205 */       this.configs.forEach(configComponent -> configComponent.movePosition(deltaX, deltaY));
/* 206 */       this.friends.forEach(friendComponent -> friendComponent.movePosition(deltaX, deltaY));
/* 207 */       this.components.forEach(component -> component.movePosition(deltaX, deltaY));
/* 208 */       this.categories.forEach(category -> category.movePosition(deltaX, deltaY));
/*     */     } 
/*     */     
/* 211 */     if (this.rescale) {
/* 212 */       float deltaY = (mouseY - this.rescale_y - this.main_height);
/* 213 */       if (this.main_height + deltaY > 250.0F) {
/* 214 */         this.main_height = (int)(this.main_height + deltaY);
/*     */       }
/*     */     } 
/* 217 */     if (this.current_category != null && this.current_category != this.new_category) {
/* 218 */       this.prevCategoryY = getCategoryY(this.current_category);
/* 219 */       this.CategoryY = getCategoryY(this.new_category);
/* 220 */       this.current_category = this.new_category;
/* 221 */       this.category_animation = 0.0F;
/* 222 */       this.slider_y = 0;
/* 223 */       this.search_string = "Search";
/* 224 */       this.config_string = "Save config";
/* 225 */       this.friend_string = "Add friend";
/* 226 */       currentMode = CurrentMode.Modules;
/* 227 */       load();
/*     */     } 
/*     */ 
/*     */     
/* 231 */     this.manager_animation = fast(this.manager_animation, 1.0F, 15.0F);
/* 232 */     this.category_animation = fast(this.category_animation, 1.0F, 15.0F);
/*     */     
/* 234 */     checkMouseWheel(mouseX, mouseY);
/*     */ 
/*     */     
/* 237 */     RoundedShader.drawRound(this.main_posX, this.main_posY, 400.0F, this.main_height, 9.0F, ThunderHackGui.getInstance().getColorByTheme(0));
/*     */ 
/*     */ 
/*     */     
/* 241 */     RoundedShader.drawRound((this.main_posX + 5), (this.main_posY + 5), 90.0F, 30.0F, 7.0F, ThunderHackGui.getInstance().getColorByTheme(1));
/* 242 */     FontRender.drawString2("THUNDERHACK+", this.main_posX + 10, this.main_posY + 15, ThunderHackGui.getInstance().getColorByTheme(2).getRGB());
/* 243 */     FontRender.drawString5("v2.41", (this.main_posX + 75), (this.main_posY + 30), ThunderHackGui.getInstance().getColorByTheme(3).getRGB());
/*     */ 
/*     */     
/* 246 */     RoundedShader.drawRound((this.main_posX + 5), (this.main_posY + 40), 90.0F, 140.0F, 7.0F, ThunderHackGui.getInstance().getColorByTheme(4));
/*     */ 
/*     */     
/* 249 */     if (currentMode == CurrentMode.Modules) {
/* 250 */       RoundedShader.drawRound((this.main_posX + 20), (this.main_posY + 195), 60.0F, 20.0F, 4.0F, ThunderHackGui.getInstance().getColorByTheme(4));
/* 251 */     } else if (currentMode == CurrentMode.ConfigManager) {
/* 252 */       RoundedShader.drawGradientRound((this.main_posX + 20), (this.main_posY + 195), 60.0F, 20.0F, 4.0F, ThunderHackGui.getInstance().getColorByTheme(5), ThunderHackGui.getInstance().getColorByTheme(5), ThunderHackGui.getInstance().getColorByTheme(4), ThunderHackGui.getInstance().getColorByTheme(4));
/*     */     } else {
/* 254 */       RoundedShader.drawGradientRound((this.main_posX + 20), (this.main_posY + 195), 60.0F, 20.0F, 4.0F, ThunderHackGui.getInstance().getColorByTheme(4), ThunderHackGui.getInstance().getColorByTheme(4), ThunderHackGui.getInstance().getColorByTheme(5), ThunderHackGui.getInstance().getColorByTheme(5));
/*     */     } 
/*     */ 
/*     */     
/* 258 */     RoundedShader.drawRound(this.main_posX + 49.5F, (this.main_posY + 197), 1.0F, 16.0F, 0.5F, ThunderHackGui.getInstance().getColorByTheme(6));
/*     */     
/* 260 */     FontRender.drawMidIcon("u", (this.main_posX + 20), (this.main_posY + 195), (currentMode == CurrentMode.ConfigManager) ? ThunderHackGui.getInstance().getColorByTheme(2).getRGB() : (new Color(9276813)).getRGB());
/* 261 */     FontRender.drawMidIcon("v", (this.main_posX + 54), (this.main_posY + 196), (currentMode == CurrentMode.FriendManager) ? ThunderHackGui.getInstance().getColorByTheme(2).getRGB() : (new Color(9276813)).getRGB());
/*     */     
/* 263 */     if (isHoveringItem((this.main_posX + 20), (this.main_posY + 195), 60.0F, 20.0F, mouseX, mouseY)) {
/* 264 */       RoundedShader.drawRound((this.main_posX + 20), (this.main_posY + 195), 60.0F, 20.0F, 4.0F, new Color(76, 56, 93, 31));
/* 265 */       GL11.glPushMatrix();
/* 266 */       Stencil.write(false);
/* 267 */       Particles.roundedRect((this.main_posX + 20), (this.main_posY + 195), 61.0D, 21.0D, 8.0D, new Color(0, 0, 0, 255));
/* 268 */       Stencil.erase(true);
/* 269 */       RenderUtil.drawBlurredShadow((mouseX - 20), (mouseY - 20), 40.0F, 40.0F, 60, new Color(-1017816450, true));
/* 270 */       Stencil.dispose();
/* 271 */       GL11.glPopMatrix();
/*     */     } 
/*     */ 
/*     */     
/* 275 */     if (this.first_open) {
/* 276 */       this.category_animation = 1.0F;
/* 277 */       RoundedShader.drawRound((this.main_posX + 8), this.CategoryY + this.slider_y, 84.0F, 15.0F, 2.0F, ThunderHackGui.getInstance().getColorByTheme(7));
/* 278 */       this.first_open = false;
/*     */     }
/* 280 */     else if (currentMode == CurrentMode.Modules) {
/* 281 */       RoundedShader.drawRound((this.main_posX + 8), (float)RenderUtil.interpolate(this.CategoryY, this.prevCategoryY, this.category_animation) + this.slider_y, 84.0F, 15.0F, 2.0F, ThunderHackGui.getInstance().getColorByTheme(7));
/*     */     } 
/*     */     
/* 284 */     if (selected_plate != prev_selected_plate) {
/* 285 */       prev_selected_plate = selected_plate;
/* 286 */       this.settings_animation = 0.0F;
/* 287 */       this.settings.clear();
/* 288 */       this.scroll = 0.0F;
/*     */       
/* 290 */       if (selected_plate != null) {
/* 291 */         for (Setting<?> setting : (Iterable<Setting<?>>)selected_plate.getModule().getSettings()) {
/* 292 */           if (setting.getValue() instanceof com.mrzak34.thunderhack.setting.Parent) {
/* 293 */             this.settings.add(new ParentComponent(setting));
/*     */           }
/* 295 */           if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled") && !setting.getName().equals("Drawn")) {
/* 296 */             this.settings.add(new BooleanComponent(setting));
/*     */           }
/* 298 */           if (setting.isEnumSetting()) {
/* 299 */             this.settings.add(new ModeComponent(setting));
/*     */           }
/* 301 */           if (setting.isColorSetting()) {
/* 302 */             this.settings.add(new ColorPickerComponent(setting));
/*     */           }
/* 304 */           if (setting.isNumberSetting() && setting.hasRestriction()) {
/* 305 */             this.settings.add(new SliderComponent(setting));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 312 */     this.settings_animation = fast(this.settings_animation, 1.0F, 15.0F);
/*     */ 
/*     */     
/* 315 */     if (currentMode != this.prevMode) {
/* 316 */       if (this.prevMode != CurrentMode.ConfigManager) {
/* 317 */         this.manager_animation = 0.0F;
/* 318 */         if (currentMode == CurrentMode.ConfigManager) {
/* 319 */           loadConfigs();
/*     */         }
/*     */       } 
/*     */       
/* 323 */       if (this.prevMode != CurrentMode.FriendManager) {
/* 324 */         this.manager_animation = 0.0F;
/* 325 */         if (currentMode == CurrentMode.FriendManager) {
/* 326 */           loadFriends();
/*     */         }
/*     */       } 
/* 329 */       this.prevMode = currentMode;
/*     */     } 
/*     */     
/* 332 */     if (selected_plate != null && 
/* 333 */       currentMode == CurrentMode.Modules) {
/* 334 */       RoundedShader.drawRound((float)RenderUtil.interpolate((this.main_posX + 200), selected_plate.getPosX(), this.settings_animation), (float)RenderUtil.interpolate((this.main_posY + 40), selected_plate.getPosY(), this.settings_animation), (float)RenderUtil.interpolate(195.0D, 90.0D, this.settings_animation), (float)RenderUtil.interpolate((this.main_height - 45), 30.0D, this.settings_animation), 4.0F, ThunderHackGui.getInstance().getColorByTheme(7));
/*     */     }
/*     */ 
/*     */     
/* 338 */     if (currentMode != CurrentMode.Modules) {
/* 339 */       this.searching = false;
/*     */       
/* 341 */       RenderUtil.glScissor((float)RenderUtil.interpolate((this.main_posX + 80), (this.main_posX + 200), this.manager_animation), (this.main_posY + 39), (float)RenderUtil.interpolate(399.0D, 195.0D, this.manager_animation) + this.main_posX + 36.0F, this.main_height + this.main_posY - 3.0F, sr, open_animation.getAnimationd());
/*     */       
/* 343 */       GL11.glEnable(3089);
/* 344 */       RoundedShader.drawRound((this.main_posX + 100), this.main_posY + 40.0F, 295.0F, this.main_height - 44.0F, 4.0F, ThunderHackGui.getInstance().getColorByTheme(7));
/* 345 */       this.configs.forEach(components -> components.render(mouseX, mouseY));
/* 346 */       this.friends.forEach(components -> components.render(mouseX, mouseY));
/* 347 */       RenderUtil.draw2DGradientRect((this.main_posX + 102), (this.main_posY + 34), (this.main_posX + 393), (this.main_posY + 60), (new Color(25, 20, 30, 0)).getRGB(), ThunderHackGui.getInstance().getColorByTheme(7).getRGB(), (new Color(25, 20, 30, 0)).getRGB(), (new Color(37, 27, 41, 245)).getRGB());
/* 348 */       RenderUtil.draw2DGradientRect((this.main_posX + 102), (this.main_posY + this.main_height - 35), (this.main_posX + 393), (this.main_posY + this.main_height), ThunderHackGui.getInstance().getColorByTheme(7).getRGB(), (new Color(25, 20, 30, 0)).getRGB(), ThunderHackGui.getInstance().getColorByTheme(7).getRGB(), (new Color(37, 27, 41, 0)).getRGB());
/* 349 */       GL11.glDisable(3089);
/*     */     } 
/*     */ 
/*     */     
/* 353 */     RenderUtil.glScissor((this.main_posX + 79), (this.main_posY + 35), (this.main_posX + 396 + 40), (this.main_posY + this.main_height), sr, open_animation.getAnimationd());
/* 354 */     GL11.glEnable(3089);
/* 355 */     this.components.forEach(components -> components.render(mouseX, mouseY));
/* 356 */     GL11.glDisable(3089);
/*     */     
/* 358 */     this.categories.forEach(category -> category.render(mouseX, mouseY));
/*     */     
/* 360 */     if (currentMode == CurrentMode.Modules) {
/* 361 */       RenderUtil.draw2DGradientRect((this.main_posX + 98), (this.main_posY + 34), (this.main_posX + 191), (this.main_posY + 50), (new Color(37, 27, 41, 0)).getRGB(), (new Color(37, 27, 41, 245)).getRGB(), (new Color(37, 27, 41, 0)).getRGB(), (new Color(37, 27, 41, 245)).getRGB());
/* 362 */       RenderUtil.draw2DGradientRect((this.main_posX + 98), (this.main_posY + this.main_height - 15), (this.main_posX + 191), (this.main_posY + this.main_height), (new Color(37, 27, 41, 245)).getRGB(), (new Color(37, 27, 41, 0)).getRGB(), (new Color(37, 27, 41, 245)).getRGB(), (new Color(37, 27, 41, 0)).getRGB());
/*     */     } 
/*     */     
/* 365 */     RoundedShader.drawRound((this.main_posX + 100), (this.main_posY + 5), 295.0F, 30.0F, 7.0F, new Color(25, 20, 30, 250));
/*     */ 
/*     */ 
/*     */     
/* 369 */     if (isHoveringItem((this.main_posX + 105), (this.main_posY + 14), 11.0F, 11.0F, mouseX, mouseY)) {
/* 370 */       RoundedShader.drawRound((this.main_posX + 105), (this.main_posY + 14), 11.0F, 11.0F, 3.0F, new Color(68, 49, 75, 250));
/*     */     } else {
/* 372 */       RoundedShader.drawRound((this.main_posX + 105), (this.main_posY + 14), 11.0F, 11.0F, 3.0F, new Color(52, 38, 58, 250));
/*     */     } 
/* 374 */     FontRender.drawString6("current cfg: " + ConfigManager.currentConfig.getName(), (this.main_posX + 120), (this.main_posY + 18), (new Color(-838860801, true)).getRGB(), false);
/* 375 */     FontRender.drawIcon("t", this.main_posX + 106, this.main_posY + 17, (new Color(-1023410177, true)).getRGB());
/*     */ 
/*     */ 
/*     */     
/* 379 */     RoundedShader.drawRound((this.main_posX + 250), (this.main_posY + 15), 140.0F, 10.0F, 3.0F, new Color(52, 38, 58, 250));
/* 380 */     if (currentMode == CurrentMode.Modules)
/* 381 */       FontRender.drawIcon("s", this.main_posX + 378, this.main_posY + 18, this.searching ? (new Color(-872415233, true)).getRGB() : (new Color(-2080374785, true)).getRGB()); 
/* 382 */     if (isHoveringItem((this.main_posX + 250), (this.main_posY + 15), 140.0F, 20.0F, mouseX, mouseY)) {
/* 383 */       GL11.glPushMatrix();
/* 384 */       RoundedShader.drawRound((this.main_posX + 250), (this.main_posY + 15), 140.0F, 10.0F, 3.0F, new Color(84, 63, 94, 36));
/* 385 */       Stencil.write(false);
/* 386 */       Particles.roundedRect((this.main_posX + 250), (this.main_posY + 15), 140.0D, 10.0D, 6.0D, new Color(0, 0, 0, 255));
/* 387 */       Stencil.erase(true);
/* 388 */       RenderUtil.drawBlurredShadow((mouseX - 20), (mouseY - 20), 40.0F, 40.0F, 60, new Color(-1017816450, true));
/* 389 */       Stencil.dispose();
/* 390 */       GL11.glPopMatrix();
/*     */     } 
/* 392 */     if (currentMode == CurrentMode.Modules)
/* 393 */       FontRender.drawString6(this.search_string, (this.main_posX + 252), (this.main_posY + 18), this.searching ? (new Color(-872415233, true)).getRGB() : (new Color(-2080374785, true)).getRGB(), false); 
/* 394 */     if (currentMode == CurrentMode.ConfigManager) {
/* 395 */       FontRender.drawString6(this.config_string, (this.main_posX + 252), (this.main_posY + 18), this.listening_config ? (new Color(-872415233, true)).getRGB() : (new Color(-2080374785, true)).getRGB(), false);
/* 396 */       RoundedShader.drawRound((this.main_posX + 368), (this.main_posY + 17), 20.0F, 6.0F, 1.0F, isHoveringItem((this.main_posX + 368), (this.main_posY + 17), 20.0F, 6.0F, mouseX, mouseY) ? new Color(59, 42, 63, 194) : new Color(33, 23, 35, 194));
/* 397 */       FontRender.drawCentString6("+", (this.main_posX + 378), (this.main_posY + 19), ThunderHackGui.getInstance().getColorByTheme(2).getRGB());
/*     */     } 
/* 399 */     if (currentMode == CurrentMode.FriendManager) {
/* 400 */       FontRender.drawString6(this.friend_string, (this.main_posX + 252), (this.main_posY + 18), this.listening_friend ? (new Color(-872415233, true)).getRGB() : (new Color(-2080374785, true)).getRGB(), false);
/* 401 */       RoundedShader.drawRound((this.main_posX + 368), (this.main_posY + 17), 20.0F, 6.0F, 1.0F, isHoveringItem((this.main_posX + 368), (this.main_posY + 17), 20.0F, 6.0F, mouseX, mouseY) ? new Color(59, 42, 63, 194) : new Color(33, 23, 35, 194));
/* 402 */       FontRender.drawCentString6("+", (this.main_posX + 378), (this.main_posY + 19), ThunderHackGui.getInstance().getColorByTheme(2).getRGB());
/*     */     } 
/*     */     
/* 405 */     if (selected_plate == null)
/*     */       return; 
/* 407 */     float scissorX1 = (float)RenderUtil.interpolate((this.main_posX + 200), selected_plate.getPosX(), this.settings_animation) - 20.0F;
/* 408 */     float scissorY1 = (float)RenderUtil.interpolate((this.main_posY + 40), selected_plate.getPosY(), this.settings_animation);
/* 409 */     float scissorX2 = Math.max((float)RenderUtil.interpolate(395.0D, 90.0D, this.settings_animation) + this.main_posX, (this.main_posX + 205)) + 40.0F;
/* 410 */     float scissorY2 = Math.max((float)RenderUtil.interpolate((this.main_height - 5), 30.0D, this.settings_animation) + this.main_posY, (this.main_posY + 45));
/*     */     
/* 412 */     if (scissorX2 < scissorX1) {
/* 413 */       scissorX2 = scissorX1;
/*     */     }
/*     */     
/* 416 */     if (scissorY2 < scissorY1) {
/* 417 */       scissorY2 = scissorY1;
/*     */     }
/*     */     
/* 420 */     RenderUtil.glScissor(scissorX1, scissorY1, scissorX2, scissorY2, sr, open_animation.getAnimationd());
/* 421 */     GL11.glEnable(3089);
/* 422 */     if (!this.settings.isEmpty()) {
/* 423 */       double offsetY = 0.0D;
/* 424 */       for (SettingElement element : this.settings) {
/* 425 */         if (!element.isVisible()) {
/*     */           continue;
/*     */         }
/* 428 */         element.setOffsetY(offsetY);
/* 429 */         element.setX((this.main_posX + 215));
/* 430 */         element.setY(((this.main_posY + 45) + this.scroll));
/* 431 */         element.setWidth(175.0D);
/* 432 */         element.setHeight(15.0D);
/*     */         
/* 434 */         if (element instanceof ColorPickerComponent && (
/* 435 */           (ColorPickerComponent)element).isOpen()) {
/* 436 */           element.setHeight(56.0D);
/*     */         }
/* 438 */         if (element instanceof ModeComponent) {
/* 439 */           ModeComponent component = (ModeComponent)element;
/* 440 */           component.setWHeight(15.0D);
/*     */           
/* 442 */           if (component.isOpen()) {
/* 443 */             offsetY += ((component.getSetting().getModes()).length * 6);
/* 444 */             element.setHeight(element.getHeight() + ((component.getSetting().getModes()).length * 6) + 3.0D);
/*     */           } else {
/* 446 */             element.setHeight(15.0D);
/*     */           } 
/*     */         } 
/* 449 */         element.render(mouseX, mouseY, partialTicks);
/* 450 */         offsetY += element.getHeight() + 3.0D;
/*     */       } 
/*     */     } 
/* 453 */     if (selected_plate != null && this.settings_animation < 0.9999D) {
/* 454 */       RoundedShader.drawRound((float)RenderUtil.interpolate((this.main_posX + 200), selected_plate.getPosX(), this.settings_animation), (float)RenderUtil.interpolate((this.main_posY + 40), selected_plate.getPosY(), this.settings_animation), (float)RenderUtil.interpolate(195.0D, 90.0D, this.settings_animation), (float)RenderUtil.interpolate((this.main_height - 45), 30.0D, this.settings_animation), 4.0F, ColorUtil.applyOpacity(ThunderHackGui.getInstance().getColorByTheme(7), 1.0F - this.settings_animation));
/*     */     }
/* 456 */     GL11.glDisable(3089);
/*     */   }
/*     */   
/*     */   private int getCategoryY(Module.Category category) {
/* 460 */     for (CategoryPlate categoryPlate : this.categories) {
/* 461 */       if (categoryPlate.getCategory() == category) {
/* 462 */         return categoryPlate.getPosY();
/*     */       }
/*     */     } 
/* 465 */     return 0;
/*     */   }
/*     */   
/*     */   public void onTick() {
/* 469 */     open_animation.update(open_direction);
/* 470 */     this.components.forEach(ModulePlate::onTick);
/* 471 */     this.settings.forEach(SettingElement::onTick);
/* 472 */     this.configs.forEach(ConfigComponent::onTick);
/* 473 */     this.friends.forEach(FriendComponent::onTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/* 478 */     return false;
/*     */   }
/*     */   
/*     */   public void func_73864_a(int mouseX, int mouseY, int clickedButton) {
/* 482 */     mouse_state = true;
/* 483 */     if (isHoveringItem((this.main_posX + 368), (this.main_posY + 17), 20.0F, 6.0F, mouseX, mouseY)) {
/* 484 */       if (this.listening_config) {
/* 485 */         ConfigManager.save(this.config_string);
/* 486 */         this.config_string = "Save config";
/* 487 */         this.listening_config = false;
/* 488 */         loadConfigs();
/*     */         return;
/*     */       } 
/* 491 */       if (this.listening_friend) {
/* 492 */         Thunderhack.friendManager.addFriend(this.friend_string);
/* 493 */         this.friend_string = "Add friend";
/* 494 */         this.listening_friend = false;
/* 495 */         loadFriends();
/*     */         return;
/*     */       } 
/*     */     } 
/* 499 */     if (isHoveringItem((this.main_posX + 105), (this.main_posY + 14), 11.0F, 11.0F, mouseX, mouseY)) {
/*     */       try {
/* 501 */         Desktop.getDesktop().browse((new File("ThunderHack/configs/")).toURI());
/* 502 */       } catch (Exception e) {
/* 503 */         Command.sendMessage("Не удалось открыть проводник!");
/*     */       } 
/*     */     }
/*     */     
/* 507 */     if (isHoveringItem((this.main_posX + 20), (this.main_posY + 195), 28.0F, 20.0F, mouseX, mouseY)) {
/* 508 */       this.current_category = null;
/* 509 */       currentMode = CurrentMode.ConfigManager;
/* 510 */       this.settings.clear();
/* 511 */       this.components.clear();
/*     */     } 
/* 513 */     if (isHoveringItem((this.main_posX + 50), (this.main_posY + 195), 28.0F, 20.0F, mouseX, mouseY)) {
/* 514 */       this.current_category = null;
/* 515 */       currentMode = CurrentMode.FriendManager;
/* 516 */       this.settings.clear();
/* 517 */       this.components.clear();
/*     */     } 
/* 519 */     if (isHoveringItem(this.main_posX, this.main_posY, 400.0F, 30.0F, mouseX, mouseY)) {
/* 520 */       this.drag_x = mouseX - this.main_posX;
/* 521 */       this.drag_y = mouseY - this.main_posY;
/* 522 */       this.dragging = true;
/*     */     } 
/*     */     
/* 525 */     if (isHoveringItem((this.main_posX + 250), (this.main_posY + 15), 140.0F, 10.0F, mouseX, mouseY) && currentMode == CurrentMode.Modules) {
/* 526 */       this.searching = true;
/*     */     }
/*     */     
/* 529 */     if (isHoveringItem((this.main_posX + 250), (this.main_posY + 15), 110.0F, 10.0F, mouseX, mouseY) && currentMode == CurrentMode.ConfigManager) {
/* 530 */       this.listening_config = true;
/*     */     }
/*     */     
/* 533 */     if (isHoveringItem((this.main_posX + 250), (this.main_posY + 15), 110.0F, 10.0F, mouseX, mouseY) && currentMode == CurrentMode.FriendManager) {
/* 534 */       this.listening_friend = true;
/*     */     }
/*     */     
/* 537 */     if (isHoveringItem(this.main_posX, (this.main_posY + this.main_height - 6), 400.0F, 12.0F, mouseX, mouseY)) {
/* 538 */       this.rescale_y = mouseY - this.main_height;
/* 539 */       this.rescale = true;
/*     */     } 
/*     */     
/* 542 */     this.settings.forEach(component -> component.mouseClicked(mouseX, mouseY, clickedButton));
/* 543 */     this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
/* 544 */     this.categories.forEach(category -> category.mouseClicked(mouseX, mouseY, clickedButton));
/* 545 */     this.configs.forEach(component -> component.mouseClicked(mouseX, mouseY, clickedButton));
/* 546 */     this.friends.forEach(component -> component.mouseClicked(mouseX, mouseY, clickedButton));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146286_b(int mouseX, int mouseY, int releaseButton) {
/* 551 */     mouse_state = false;
/* 552 */     this.dragging = false;
/* 553 */     this.rescale = false;
/* 554 */     this.settings.forEach(settingElement -> settingElement.mouseReleased(mouseX, mouseY, releaseButton));
/*     */   }
/*     */   
/*     */   public void func_73869_a(char typedChar, int keyCode) throws IOException {
/* 558 */     if (keyCode == 1) {
/* 559 */       open_direction = false;
/* 560 */       this.searching = false;
/*     */     } 
/*     */     
/* 563 */     this.settings.forEach(settingElement -> settingElement.keyTyped(typedChar, keyCode));
/* 564 */     this.components.forEach(component -> component.keyTyped(typedChar, keyCode));
/*     */     
/* 566 */     if (this.searching) {
/* 567 */       this.components.clear();
/* 568 */       if (this.search_string.equalsIgnoreCase("search")) {
/* 569 */         this.search_string = "";
/*     */       }
/* 571 */       int module_y = 0;
/* 572 */       for (Module module : Thunderhack.moduleManager.getModulesSearch(this.search_string)) {
/* 573 */         this.components.add(new ModulePlate(module, this.main_posX + 100, this.main_posY + 40 + module_y, module_y / 35));
/* 574 */         module_y += 35;
/*     */       } 
/*     */       
/* 577 */       switch (keyCode) {
/*     */         case 1:
/* 579 */           this.search_string = "Search";
/* 580 */           this.searching = false;
/*     */           return;
/*     */         
/*     */         case 14:
/* 584 */           this.search_string = removeLastChar(this.search_string);
/*     */           break;
/*     */       } 
/* 587 */       if (ChatAllowedCharacters.func_71566_a(typedChar)) {
/* 588 */         this.search_string += typedChar;
/*     */       }
/*     */     } 
/* 591 */     if (this.listening_config) {
/* 592 */       if (this.config_string.equalsIgnoreCase("Save config")) {
/* 593 */         this.config_string = "";
/*     */       }
/* 595 */       switch (keyCode) {
/*     */         case 1:
/* 597 */           this.config_string = "Save config";
/* 598 */           this.listening_config = false;
/*     */           return;
/*     */         
/*     */         case 14:
/* 602 */           this.config_string = removeLastChar(this.config_string);
/*     */           break;
/*     */         
/*     */         case 28:
/* 606 */           if (!this.config_string.equals("Save config") && !this.config_string.equals("")) {
/* 607 */             ConfigManager.save(this.config_string);
/* 608 */             this.config_string = "Save config";
/* 609 */             this.listening_config = false;
/* 610 */             loadConfigs();
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 615 */       if (ChatAllowedCharacters.func_71566_a(typedChar)) {
/* 616 */         this.config_string += typedChar;
/*     */       }
/*     */     } 
/*     */     
/* 620 */     if (this.listening_friend) {
/* 621 */       if (this.friend_string.equalsIgnoreCase("Add friend")) {
/* 622 */         this.friend_string = "";
/*     */       }
/* 624 */       switch (keyCode) {
/*     */         case 1:
/* 626 */           this.friend_string = "Add friend";
/* 627 */           this.listening_friend = false;
/*     */           return;
/*     */         
/*     */         case 14:
/* 631 */           this.friend_string = removeLastChar(this.friend_string);
/*     */           break;
/*     */         
/*     */         case 28:
/* 635 */           if (!this.friend_string.equals("Add friend") && !this.config_string.equals("")) {
/* 636 */             Thunderhack.friendManager.addFriend(this.friend_string);
/* 637 */             this.friend_string = "Add friend";
/* 638 */             this.listening_friend = false;
/* 639 */             loadFriends();
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 644 */       if (ChatAllowedCharacters.func_71566_a(typedChar)) {
/* 645 */         this.friend_string += typedChar;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146281_b() {}
/*     */ 
/*     */   
/*     */   public boolean isHoveringItem(float x, float y, float x1, float y1, float mouseX, float mouseY) {
/* 656 */     return (mouseX >= x && mouseY >= y && mouseX <= x1 + x && mouseY <= y1 + y);
/*     */   }
/*     */   
/*     */   public void checkMouseWheel(int mouseX, int mouseY) {
/* 660 */     float dWheel = Mouse.getDWheel();
/* 661 */     this.settings.forEach(component -> component.checkMouseWheel(dWheel));
/* 662 */     if (scroll_lock) {
/* 663 */       scroll_lock = false;
/*     */       return;
/*     */     } 
/* 666 */     if (isHoveringItem((this.main_posX + 200), (this.main_posY + 40), (this.main_posX + 395), (this.main_posY - 5 + this.main_height), mouseX, mouseY)) {
/* 667 */       this.scroll += dWheel * ((Float)(ThunderHackGui.getInstance()).scrollSpeed.getValue()).floatValue();
/*     */     } else {
/* 669 */       this.components.forEach(component -> component.scrollElement(dWheel * ((Float)(ThunderHackGui.getInstance()).scrollSpeed.getValue()).floatValue()));
/*     */     } 
/* 671 */     this.configs.forEach(component -> component.scrollElement(dWheel * ((Float)(ThunderHackGui.getInstance()).scrollSpeed.getValue()).floatValue()));
/* 672 */     this.friends.forEach(component -> component.scrollElement(dWheel * ((Float)(ThunderHackGui.getInstance()).scrollSpeed.getValue()).floatValue()));
/*     */   }
/*     */   
/*     */   public enum CurrentMode {
/* 676 */     Modules, ConfigManager, FriendManager;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\ThunderGui2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */