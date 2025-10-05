/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import com.mrzak34.thunderhack.util.ThunderUtils;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class ChangeSkinCommand
/*    */   extends Command {
/* 11 */   private static ChangeSkinCommand INSTANCE = new ChangeSkinCommand();
/* 12 */   public ArrayList<String> changedplayers = new ArrayList<>();
/*    */   
/*    */   public ChangeSkinCommand() {
/* 15 */     super("skinset");
/* 16 */     setInstance();
/*    */   }
/*    */   
/*    */   public static ChangeSkinCommand getInstance() {
/* 20 */     if (INSTANCE == null) {
/* 21 */       INSTANCE = new ChangeSkinCommand();
/*    */     }
/* 23 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 27 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 32 */     if (commands.length == 1) {
/* 33 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 34 */         Command.sendMessage("skinset имяигрока имяскина");
/*    */       } else {
/* 36 */         Command.sendMessage("skinset playername skinname");
/*    */       } 
/*    */       return;
/*    */     } 
/* 40 */     if (commands.length == 2) {
/* 41 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 42 */         Command.sendMessage("skinset имяигрока имяскина");
/*    */       } else {
/* 44 */         Command.sendMessage("skinset playername skinname");
/*    */       } 
/*    */       return;
/*    */     } 
/* 48 */     if (commands.length == 3) {
/* 49 */       ThunderUtils.savePlayerSkin("https://minotar.net/skin/" + commands[1], commands[0]);
/* 50 */       this.changedplayers.add(commands[0]);
/* 51 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 52 */         Command.sendMessage("Скин игрока " + commands[0] + " изменен на " + commands[1]);
/*    */       } else {
/* 54 */         Command.sendMessage("Player " + commands[0] + "'s skin has been changed to " + commands[1]);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\ChangeSkinCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */