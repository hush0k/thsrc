/*    */ package com.mrzak34.thunderhack.modules.client;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class MainSettings extends Module {
/*  7 */   public Setting<Boolean> showcapes = register(new Setting("Capes", Boolean.valueOf(true)));
/*  8 */   public Setting<Boolean> DownloadCapes = register(new Setting("DownloadCapes", Boolean.valueOf(true)));
/*  9 */   public Setting<Boolean> notifyToggles = register(new Setting("NotifyToggles", Boolean.valueOf(false)));
/* 10 */   public Setting<Boolean> mainMenu = register(new Setting("MainMenu", Boolean.valueOf(true)));
/* 11 */   public Setting<Boolean> renderRotations = register(new Setting("RenderRotations", Boolean.valueOf(true)));
/* 12 */   public Setting<Boolean> eatAnim = register(new Setting("BedrockEatAnim", Boolean.valueOf(false)));
/*    */   
/* 14 */   public Setting<ShaderModeEn> shaderMode = register(new Setting("ShaderMode", ShaderModeEn.Smoke));
/* 15 */   public Setting<Language> language = register(new Setting("Language", Language.RU));
/*    */   public MainSettings() {
/* 17 */     super("ClientSettings", "Настройки клиента", Module.Category.CLIENT);
/*    */   }
/*    */   
/*    */   public enum ShaderModeEn
/*    */   {
/* 22 */     Smoke,
/* 23 */     WarThunder,
/* 24 */     Dicks;
/*    */   }
/*    */   
/*    */   public enum Language
/*    */   {
/* 29 */     RU,
/* 30 */     ENG;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\MainSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */