/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ 
/*    */ public class ReloadCommand
/*    */   extends Command {
/*    */   public ReloadCommand() {
/*  9 */     super("reload");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     Thunderhack.reload();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\ReloadCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */