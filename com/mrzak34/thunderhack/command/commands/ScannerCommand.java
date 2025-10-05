/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.misc.NoCom;
/*    */ 
/*    */ public class ScannerCommand
/*    */   extends Command {
/*    */   public ScannerCommand() {
/* 10 */     super("scanner");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     Command.sendMessage("scanner gui loaded");
/* 16 */     (NoCom)Thunderhack.moduleManager.getModuleByClass(NoCom.class); NoCom.getgui();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\ScannerCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */