/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Bind;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class BindCommand
/*    */   extends Command {
/*    */   public BindCommand() {
/* 13 */     super("bind");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 18 */     if (commands.length == 1) {
/* 19 */       sendMessage("Please specify a module.");
/*    */       return;
/*    */     } 
/* 22 */     String rkey = commands[1];
/* 23 */     String moduleName = commands[0];
/* 24 */     Module module = Thunderhack.moduleManager.getModuleByName(moduleName);
/* 25 */     if (module == null) {
/* 26 */       sendMessage("Unknown module '" + module + "'!");
/*    */       return;
/*    */     } 
/* 29 */     if (rkey == null) {
/* 30 */       sendMessage(module.getName() + " is bound to " + ChatFormatting.GRAY + module.getBind().toString());
/*    */       return;
/*    */     } 
/* 33 */     int key = Keyboard.getKeyIndex(rkey.toUpperCase());
/* 34 */     if (rkey.equalsIgnoreCase("none")) {
/* 35 */       key = -1;
/*    */     }
/* 37 */     if (key == 0) {
/* 38 */       sendMessage("Unknown key '" + rkey + "'!");
/*    */       return;
/*    */     } 
/* 41 */     module.bind.setValue(new Bind(key));
/* 42 */     sendMessage("Bind for " + ChatFormatting.GREEN + module.getName() + ChatFormatting.WHITE + " set to " + ChatFormatting.GRAY + rkey.toUpperCase());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\BindCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */