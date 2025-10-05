/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.manager.ConfigManager;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import java.awt.Desktop;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class ConfigCommand
/*    */   extends Command
/*    */ {
/*    */   public ConfigCommand() {
/* 16 */     super("config");
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 20 */     if (commands.length == 1) {
/* 21 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 22 */         sendMessage("Конфиги сохраняются в  ThunderHack/configs/");
/*    */       } else {
/* 24 */         sendMessage("Configurations are saved in ThunderHack/configs/");
/*    */       } 
/*    */       return;
/*    */     } 
/* 28 */     if (commands.length == 2) {
/* 29 */       if ("list".equals(commands[0])) {
/* 30 */         StringBuilder configs = new StringBuilder("Configs: ");
/* 31 */         for (String str : Objects.<List>requireNonNull(ConfigManager.getConfigList())) {
/* 32 */           configs.append("\n- ").append(str);
/*    */         }
/* 34 */         sendMessage(configs.toString());
/* 35 */       } else if ("dir".equals(commands[0])) {
/*    */         try {
/* 37 */           Desktop.getDesktop().browse((new File("ThunderHack/configs/")).toURI());
/* 38 */         } catch (Exception e) {
/* 39 */           e.printStackTrace();
/*    */         }
/*    */       
/* 42 */       } else if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 43 */         sendMessage("Нет такой команды!... Может list ?");
/*    */       } else {
/* 45 */         sendMessage("Wrong command!... Maybe list?");
/*    */       } 
/*    */     }
/* 48 */     if (commands.length >= 3) {
/* 49 */       switch (commands[0]) {
/*    */         case "save":
/*    */         case "create":
/* 52 */           ConfigManager.save(commands[1]);
/*    */           return;
/*    */         case "set":
/*    */         case "load":
/* 56 */           ConfigManager.load(commands[1]);
/*    */           return;
/*    */       } 
/* 59 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 60 */         sendMessage("Нет такой команды! Пример использования: <save/load>");
/*    */       } else {
/* 62 */         sendMessage("Wrong command! try: <save/load>");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\ConfigCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */