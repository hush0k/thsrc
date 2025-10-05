/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ 
/*    */ public class DrawCommand extends Command {
/*    */   public DrawCommand() {
/* 10 */     super("draw");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     if (commands.length == 1) {
/* 16 */       Command.sendMessage("Напиши название модуля");
/*    */       return;
/*    */     } 
/* 19 */     String moduleName = commands[0];
/* 20 */     Module module = Thunderhack.moduleManager.getModuleByName(moduleName);
/* 21 */     if (module == null) {
/* 22 */       Command.sendMessage("Неизвестный модуль'" + module + "'!");
/*    */       
/*    */       return;
/*    */     } 
/* 26 */     module.setDrawn(!module.isDrawn());
/* 27 */     BindCommand.sendMessage("Модуль " + ChatFormatting.GREEN + module.getName() + ChatFormatting.WHITE + " теперь " + (module.isDrawn() ? "виден в ArrayList" : "не виден в ArrayList"));
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\DrawCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */