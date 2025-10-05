/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ 
/*    */ public class HelpCommand
/*    */   extends Command {
/*    */   public HelpCommand() {
/* 10 */     super("help");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     sendMessage("Commands: ");
/* 16 */     for (Command command : Thunderhack.commandManager.getCommands())
/* 17 */       sendMessage(ChatFormatting.GRAY + Thunderhack.commandManager.getPrefix() + command.getName()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\HelpCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */