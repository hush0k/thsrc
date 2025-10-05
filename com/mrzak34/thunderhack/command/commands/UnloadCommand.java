/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ 
/*    */ public class UnloadCommand
/*    */   extends Command {
/*    */   public UnloadCommand() {
/*  9 */     super("unload");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     Thunderhack.unload(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\UnloadCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */