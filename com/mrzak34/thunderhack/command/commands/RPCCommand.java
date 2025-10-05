/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.RPC;
/*    */ 
/*    */ public class RPCCommand
/*    */   extends Command
/*    */ {
/*    */   public RPCCommand() {
/* 10 */     super("rpc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 16 */     if (args.length == 1) {
/* 17 */       ModuleCommand.sendMessage(".rpc url or .rpc url url");
/*    */       
/*    */       return;
/*    */     } 
/* 21 */     if (args.length == 2) {
/* 22 */       RPC.WriteFile(args[0], "none");
/* 23 */       Command.sendMessage("Большая картинка RPC изменена на " + args[0]);
/*    */       return;
/*    */     } 
/* 26 */     if (args.length >= 2) {
/* 27 */       RPC.WriteFile(args[0], args[1]);
/* 28 */       Command.sendMessage("Большая картинка RPC изменена на " + args[0]);
/* 29 */       Command.sendMessage("Маленькая картинка RPC изменена на " + args[1]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\RPCCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */