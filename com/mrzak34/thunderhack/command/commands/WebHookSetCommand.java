/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.DiscordWebhook;
/*    */ 
/*    */ public class WebHookSetCommand
/*    */   extends Command {
/*    */   public WebHookSetCommand() {
/*  9 */     super("webhook");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     if (commands.length == 1) {
/* 15 */       Command.sendMessage("Напиши URL вебхука");
/*    */       return;
/*    */     } 
/* 18 */     DiscordWebhook.saveurl(commands[0]);
/* 19 */     sendMessage("Успешно!");
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\WebHookSetCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */