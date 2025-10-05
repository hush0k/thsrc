/*     */ package com.mrzak34.thunderhack.command.commands;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Bind;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.EnumConverter;
/*     */ import com.mrzak34.thunderhack.setting.PositionSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ 
/*     */ public class ModuleCommand extends Command {
/*     */   public ModuleCommand() {
/*  16 */     super("module");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setCommandValue(Module feature, Setting setting, JsonElement element) {
/*  21 */     for (Setting setting2 : feature.getSettings()) {
/*  22 */       if (Objects.equals(setting.getName(), setting2.getName())) {
/*  23 */         String str; JsonArray array4; JsonArray array; JsonArray array3; switch (setting2.getType()) {
/*     */           case "Parent":
/*     */             return;
/*     */           case "Boolean":
/*  27 */             setting2.setValue(Boolean.valueOf(element.getAsBoolean()));
/*     */             return;
/*     */           case "Double":
/*  30 */             setting2.setValue(Double.valueOf(element.getAsDouble()));
/*     */             return;
/*     */           case "Float":
/*  33 */             setting2.setValue(Float.valueOf(element.getAsFloat()));
/*     */             return;
/*     */           case "Integer":
/*  36 */             setting2.setValue(Integer.valueOf(element.getAsInt()));
/*     */             return;
/*     */           case "String":
/*  39 */             str = element.getAsString();
/*  40 */             setting2.setValue(str.replace("_", " "));
/*     */             return;
/*     */           case "Bind":
/*  43 */             array4 = element.getAsJsonArray();
/*  44 */             setting2.setValue((new Bind.BindConverter()).doBackward(array4.get(0)));
/*  45 */             ((Bind)setting2.getValue()).setHold(array4.get(1).getAsBoolean());
/*     */             return;
/*     */           case "ColorSetting":
/*  48 */             array = element.getAsJsonArray();
/*  49 */             ((ColorSetting)setting2.getValue()).setColor(array.get(0).getAsInt());
/*  50 */             ((ColorSetting)setting2.getValue()).setCycle(array.get(1).getAsBoolean());
/*  51 */             ((ColorSetting)setting2.getValue()).setGlobalOffset(array.get(2).getAsInt());
/*     */             return;
/*     */           case "PositionSetting":
/*  54 */             array3 = element.getAsJsonArray();
/*  55 */             ((PositionSetting)setting2.getValue()).setX(array3.get(0).getAsFloat());
/*  56 */             ((PositionSetting)setting2.getValue()).setY(array3.get(1).getAsFloat());
/*     */             return;
/*     */           case "SubBind":
/*  59 */             setting2.setValue((new SubBind.SubBindConverter()).doBackward(element));
/*     */             return;
/*     */           case "Enum":
/*     */             try {
/*  63 */               EnumConverter converter = new EnumConverter(((Enum)setting2.getValue()).getClass());
/*  64 */               Enum value = converter.doBackward(element);
/*  65 */               setting2.setValue((value == null) ? setting2.getDefaultValue() : value);
/*  66 */             } catch (Exception exception) {}
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(String[] commands) {
/*  75 */     if (commands.length == 1) {
/*  76 */       sendMessage("Modules: ");
/*  77 */       for (Module.Category category : Thunderhack.moduleManager.getCategories()) {
/*  78 */         String modules = category.getName() + ": ";
/*  79 */         for (Module module1 : Thunderhack.moduleManager.getModulesByCategory(category)) {
/*  80 */           modules = modules + (module1.isEnabled() ? (String)ChatFormatting.GREEN : (String)ChatFormatting.RED) + module1.getName() + ChatFormatting.WHITE + ", ";
/*     */         }
/*  82 */         sendMessage(modules);
/*     */       } 
/*     */       return;
/*     */     } 
/*  86 */     Module module = Thunderhack.moduleManager.getModuleByDisplayName(commands[0]);
/*  87 */     if (module == null) {
/*  88 */       module = Thunderhack.moduleManager.getModuleByName(commands[0]);
/*  89 */       if (module == null) {
/*  90 */         sendMessage("This module doesnt exist.");
/*     */         return;
/*     */       } 
/*  93 */       sendMessage(" This is the original name of the module. Its current name is: " + module.getDisplayName());
/*     */       return;
/*     */     } 
/*  96 */     if (commands.length == 2) {
/*  97 */       sendMessage(module.getDisplayName() + " : " + module.getDescription());
/*  98 */       for (Setting setting2 : module.getSettings()) {
/*  99 */         sendMessage(setting2.getName() + " : " + setting2.getValue() + ", " + setting2.getDescription());
/*     */       }
/*     */       return;
/*     */     } 
/* 103 */     if (commands.length == 3) {
/* 104 */       if (commands[1].equalsIgnoreCase("set")) {
/* 105 */         sendMessage("Please specify a setting.");
/* 106 */       } else if (commands[1].equalsIgnoreCase("reset")) {
/* 107 */         for (Setting setting3 : module.getSettings()) {
/* 108 */           setting3.setValue(setting3.getDefaultValue());
/*     */         }
/*     */       } else {
/* 111 */         sendMessage("This command doesnt exist.");
/*     */       } 
/*     */       return;
/*     */     } 
/* 115 */     if (commands.length == 4) {
/* 116 */       sendMessage("Please specify a value."); return;
/*     */     } 
/*     */     Setting setting;
/* 119 */     if (commands.length == 5 && (setting = module.getSettingByName(commands[2])) != null) {
/* 120 */       JsonParser jp = new JsonParser();
/* 121 */       if (setting.getType().equalsIgnoreCase("String")) {
/* 122 */         setting.setValue(commands[3]);
/* 123 */         sendMessage(ChatFormatting.DARK_GRAY + module.getName() + " " + setting.getName() + " has been set to " + commands[3] + ".");
/*     */         return;
/*     */       } 
/*     */       try {
/* 127 */         if (setting.getName().equalsIgnoreCase("Enabled")) {
/* 128 */           if (commands[3].equalsIgnoreCase("true")) {
/* 129 */             module.enable();
/*     */           }
/* 131 */           if (commands[3].equalsIgnoreCase("false")) {
/* 132 */             module.disable();
/*     */           }
/*     */         } 
/* 135 */         setCommandValue(module, setting, jp.parse(commands[3]));
/* 136 */       } catch (Exception e) {
/* 137 */         sendMessage("Bad Value! This setting requires a: " + setting.getType() + " value.");
/*     */         return;
/*     */       } 
/* 140 */       sendMessage(ChatFormatting.GRAY + module.getName() + " " + setting.getName() + " has been set to " + commands[3] + ".");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\ModuleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */