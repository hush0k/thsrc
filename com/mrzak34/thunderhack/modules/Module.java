/*     */ package com.mrzak34.thunderhack.modules;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*     */ import com.mrzak34.thunderhack.notification.Notification;
/*     */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*     */ import com.mrzak34.thunderhack.setting.Bind;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.SoundUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Module
/*     */ {
/*  27 */   private List<Setting> settings = new ArrayList<>();
/*     */   private final String description;
/*     */   private final String eng_description;
/*  30 */   public static Minecraft mc = Util.mc;
/*     */   private final Category category;
/*  32 */   public Setting<Boolean> enabled = register(new Setting("Enabled", Boolean.valueOf(false)));
/*     */   public Setting<String> displayName;
/*  34 */   public Setting<Bind> bind = register(new Setting("Keybind", new Bind(-1)));
/*  35 */   public Setting<Boolean> drawn = register(new Setting("Drawn", Boolean.valueOf(true)));
/*     */ 
/*     */   
/*     */   public Module(String name, String description, Category category) {
/*  39 */     this.displayName = register(new Setting("DisplayName", name));
/*  40 */     this.description = description;
/*  41 */     this.category = category;
/*  42 */     this.eng_description = "no english_description";
/*     */   }
/*     */ 
/*     */   
/*     */   public Module(String name, String description, String eng_description, Category category) {
/*  47 */     this.displayName = register(new Setting("DisplayName", name));
/*  48 */     this.description = description;
/*  49 */     this.eng_description = eng_description;
/*  50 */     this.category = category;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {}
/*     */ 
/*     */   
/*     */   public void onLoad() {}
/*     */ 
/*     */   
/*     */   public void onTick() {}
/*     */ 
/*     */   
/*     */   public void onLogin() {}
/*     */ 
/*     */   
/*     */   public void onLogout() {}
/*     */ 
/*     */   
/*     */   public void onUpdate() {}
/*     */ 
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {}
/*     */ 
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {}
/*     */ 
/*     */   
/*     */   public void onUnload() {}
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isOn() {
/*  91 */     return ((Boolean)this.enabled.getValue()).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean isOff() {
/*  95 */     return !((Boolean)this.enabled.getValue()).booleanValue();
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  99 */     if (enabled) {
/* 100 */       enable();
/*     */     } else {
/* 102 */       disable();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void enable() {
/* 107 */     this.enabled.setValue(Boolean.valueOf(true));
/* 108 */     onEnable();
/*     */     
/* 110 */     if (!Objects.equals(getDisplayName(), "ThunderGui") && !Objects.equals(getDisplayName(), "ClickGUI"))
/*     */     {
/*     */       
/* 113 */       SoundUtil.playSound(SoundUtil.ThunderSound.ON);
/*     */     }
/* 115 */     if (!Objects.equals(getDisplayName(), "ElytraSwap") && !Objects.equals(getDisplayName(), "ClickGui") && !Objects.equals(getDisplayName(), "ThunderGui") && !Objects.equals(getDisplayName(), "Windows")) {
/* 116 */       NotificationManager.publicity(getDisplayName() + " was enabled!", 2, Notification.Type.ENABLED);
/*     */     }
/* 118 */     if (((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).notifyToggles.getValue()).booleanValue()) {
/* 119 */       TextComponentString text = new TextComponentString(Thunderhack.commandManager.getClientMessage() + " " + ChatFormatting.GREEN + getDisplayName() + " toggled on.");
/* 120 */       Util.mc.field_71456_v.func_146158_b().func_146234_a((ITextComponent)text, 1);
/*     */     } 
/* 122 */     if (isOn()) {
/* 123 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void disable() {
/* 128 */     MinecraftForge.EVENT_BUS.unregister(this);
/* 129 */     if (mc.field_71439_g == null) {
/*     */       return;
/*     */     }
/* 132 */     if (!Objects.equals(getDisplayName(), "ThunderGui") && !Objects.equals(getDisplayName(), "ClickGUI"))
/*     */     {
/*     */       
/* 135 */       SoundUtil.playSound(SoundUtil.ThunderSound.OFF);
/*     */     }
/*     */     
/* 138 */     this.enabled.setValue(Boolean.valueOf(false));
/* 139 */     if (!Objects.equals(getDisplayName(), "ElytraSwap") && !Objects.equals(getDisplayName(), "ThunderGui") && !Objects.equals(getDisplayName(), "ClickGui") && !Objects.equals(getDisplayName(), "Windows")) {
/* 140 */       NotificationManager.publicity(getDisplayName() + " was disabled!", 2, Notification.Type.DISABLED);
/*     */     }
/* 142 */     if (((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).notifyToggles.getValue()).booleanValue()) {
/* 143 */       TextComponentString text = new TextComponentString(Thunderhack.commandManager.getClientMessage() + " " + ChatFormatting.RED + getDisplayName() + " toggled off.");
/* 144 */       if (text != null) {
/* 145 */         Util.mc.field_71456_v.func_146158_b().func_146234_a((ITextComponent)text, 1);
/*     */       }
/*     */     } 
/* 148 */     onDisable();
/*     */   }
/*     */   
/*     */   public void toggle() {
/* 152 */     setEnabled(!isEnabled());
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/* 156 */     return (String)this.displayName.getValue();
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 160 */     if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 161 */       return this.description;
/*     */     }
/* 163 */     if (!Objects.equals(this.eng_description, "english_description")) {
/* 164 */       return this.eng_description;
/*     */     }
/* 166 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawn() {
/* 172 */     return ((Boolean)this.drawn.getValue()).booleanValue();
/*     */   }
/*     */   
/*     */   public void setDrawn(boolean drawn) {
/* 176 */     this.drawn.setValue(Boolean.valueOf(drawn));
/*     */   }
/*     */   
/*     */   public Category getCategory() {
/* 180 */     return this.category;
/*     */   }
/*     */   
/*     */   public String getInfo() {
/* 184 */     return null;
/*     */   }
/*     */   
/*     */   public Bind getBind() {
/* 188 */     return (Bind)this.bind.getValue();
/*     */   }
/*     */   
/*     */   public void setBind(int key) {
/* 192 */     this.bind.setValue(new Bind(key));
/*     */   }
/*     */   
/*     */   public boolean listening() {
/* 196 */     return isOn();
/*     */   }
/*     */   
/*     */   public String getFullArrayString() {
/* 200 */     return getDisplayName() + ChatFormatting.GRAY + ((getDisplayInfo() != null) ? (" [" + ChatFormatting.WHITE + getDisplayInfo() + ChatFormatting.GRAY + "]") : "");
/*     */   }
/*     */   
/*     */   public static boolean fullNullCheck() {
/* 204 */     return (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 208 */     return getDisplayName();
/*     */   }
/*     */   
/*     */   public List<Setting> getSettings() {
/* 212 */     return this.settings;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 216 */     return isOn();
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/* 220 */     return !isEnabled();
/*     */   }
/*     */   
/*     */   public Setting register(Setting setting) {
/* 224 */     setting.setModule(this);
/* 225 */     this.settings.add(setting);
/* 226 */     return setting;
/*     */   }
/*     */   
/*     */   public Setting getSettingByName(String name) {
/* 230 */     for (Setting setting : this.settings) {
/* 231 */       if (!setting.getName().equalsIgnoreCase(name))
/* 232 */         continue;  return setting;
/*     */     } 
/* 234 */     return null;
/*     */   }
/*     */   
/*     */   public enum Category {
/* 238 */     COMBAT("Combat"),
/* 239 */     MISC("Misc"),
/* 240 */     RENDER("Render"),
/* 241 */     MOVEMENT("Movement"),
/* 242 */     PLAYER("Player"),
/* 243 */     FUNNYGAME("FunnyGame"),
/* 244 */     CLIENT("Client"),
/* 245 */     HUD("HUD");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     Category(String name) {
/* 250 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 254 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\Module.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */