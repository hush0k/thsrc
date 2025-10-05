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
/*    */ public class CfgCommand extends Command {
/*    */   public CfgCommand() {
/* 14 */     super("cfg");
/*    */   }
/*    */   
/*    */   public void execute(String[] commands) {
/* 18 */     if (commands.length == 1) {
/*    */       
/* 20 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 21 */         sendMessage("Конфиги сохраняются в  ThunderHack/configs/");
/*    */       } else {
/* 23 */         sendMessage("Configurations are saved in ThunderHack/configs/");
/*    */       } 
/*    */       return;
/*    */     } 
/* 27 */     if (commands.length == 2) {
/* 28 */       if ("list".equals(commands[0])) {
/* 29 */         StringBuilder configs = new StringBuilder("Configs: ");
/* 30 */         for (String str : Objects.<List>requireNonNull(ConfigManager.getConfigList())) {
/* 31 */           configs.append("\n- ").append(str);
/*    */         }
/* 33 */         sendMessage(configs.toString());
/* 34 */       } else if ("dir".equals(commands[0])) {
/*    */         try {
/* 36 */           Desktop.getDesktop().browse((new File("ThunderHack/configs/")).toURI());
/* 37 */         } catch (Exception e) {
/* 38 */           e.printStackTrace();
/*    */         }
/*    */       
/* 41 */       } else if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 42 */         sendMessage("Нет такой команды!... Может list ?");
/*    */       } else {
/* 44 */         sendMessage("Wrong command!... Maybe list?");
/*    */       } 
/*    */     }
/* 47 */     if (commands.length >= 3) {
/* 48 */       switch (commands[0]) {
/*    */         case "save":
/*    */         case "create":
/* 51 */           ConfigManager.save(commands[1]);
/*    */           return;
/*    */         case "set":
/*    */         case "load":
/* 55 */           ConfigManager.load(commands[1]);
/*    */           return;
/*    */       } 
/* 58 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 59 */         sendMessage("Нет такой команды! Пример использования: <save/load>");
/*    */       } else {
/* 61 */         sendMessage("Wrong command! try: <save/load>");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\CfgCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */