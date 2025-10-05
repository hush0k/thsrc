/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.macro.Macro;
/*    */ import com.mrzak34.thunderhack.manager.MacroManager;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import java.util.Arrays;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ public class MacroCommand
/*    */   extends Command
/*    */ {
/*    */   public MacroCommand() {
/* 16 */     super("macro");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 21 */     if (args[0] == null) {
/* 22 */       Command.sendMessage(usage());
/*    */     }
/* 24 */     if (args[0].equals("list")) {
/*    */       
/* 26 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 27 */         sendMessage("Макросы:");
/*    */       } else {
/* 29 */         sendMessage("Macro list:");
/*    */       } 
/*    */       
/* 32 */       sendMessage(" ");
/* 33 */       Thunderhack.macromanager.getMacros().forEach(macro -> sendMessage(macro.getName() + ((macro.getBind() != 0) ? (" [" + Keyboard.getKeyName(macro.getBind()) + "]") : "") + " {" + macro.getText() + "}"));
/*    */     } 
/* 35 */     if (args[0].equals("remove")) {
/* 36 */       if (Thunderhack.macromanager.getMacroByName(args[1]) != null) {
/* 37 */         Macro macro = Thunderhack.macromanager.getMacroByName(args[1]);
/* 38 */         Thunderhack.macromanager.removeMacro(macro);
/* 39 */         if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 40 */           sendMessage("Удален макрос " + macro.getName());
/*    */         } else {
/* 42 */           sendMessage("Deleted macro " + macro.getName());
/*    */         }
/*    */       
/*    */       }
/* 46 */       else if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 47 */         sendMessage("Не существует макроса с именем " + args[1]);
/*    */       } else {
/* 49 */         sendMessage("Macro " + args[1] + " not exist!");
/*    */       } 
/*    */     }
/*    */     
/* 53 */     if (args.length >= 4) {
/* 54 */       if (args[0].equals("add")) {
/* 55 */         String name = args[1];
/* 56 */         String bind = args[2].toUpperCase();
/* 57 */         String text = String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])args, 3, args.length - 1));
/* 58 */         if (Keyboard.getKeyIndex(bind) == 0) {
/* 59 */           if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 60 */             sendMessage("Неправильный бинд!");
/*    */           } else {
/* 62 */             sendMessage("Wrong button!");
/*    */           } 
/*    */           return;
/*    */         } 
/* 66 */         Macro macro = new Macro(name, text, Keyboard.getKeyIndex(bind));
/* 67 */         MacroManager.addMacro(macro);
/*    */         
/* 69 */         if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 70 */           sendMessage("Добавлен макрос " + name + " на кнопку " + Keyboard.getKeyName(macro.getBind()));
/*    */         } else {
/* 72 */           sendMessage("Added macros " + name + " on button " + Keyboard.getKeyName(macro.getBind()));
/*    */         } 
/*    */       } else {
/* 75 */         sendMessage(usage());
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   String usage() {
/* 82 */     return "macro add/remove/list (macro add name key text), (macro remove name)";
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\MacroCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */