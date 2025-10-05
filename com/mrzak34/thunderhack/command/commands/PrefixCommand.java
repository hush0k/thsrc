/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ 
/*    */ public class PrefixCommand
/*    */   extends Command {
/*    */   public PrefixCommand() {
/* 11 */     super("prefix");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 16 */     if (commands.length == 1) {
/* 17 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 18 */         Command.sendMessage(ChatFormatting.GREEN + "Текущий префикс:" + Thunderhack.commandManager.getPrefix());
/*    */       } else {
/* 20 */         Command.sendMessage(ChatFormatting.GREEN + "current prefix:" + Thunderhack.commandManager.getPrefix());
/*    */       } 
/*    */       return;
/*    */     } 
/* 24 */     Thunderhack.commandManager.setPrefix(commands[0]);
/* 25 */     if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 26 */       Command.sendMessage("Префикс изменен на  " + ChatFormatting.GRAY + commands[0]);
/*    */     } else {
/* 28 */       Command.sendMessage("Prefix changed to  " + ChatFormatting.GRAY + commands[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\PrefixCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */