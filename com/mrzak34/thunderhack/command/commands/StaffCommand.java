/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class StaffCommand
/*    */   extends Command {
/* 10 */   public static List<String> staffNames = new ArrayList<>();
/*    */   
/*    */   public StaffCommand() {
/* 13 */     super("staff");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 18 */     if (commands.length == 1) {
/* 19 */       if (staffNames.isEmpty()) {
/* 20 */         sendMessage("Staff list empty");
/*    */       } else {
/* 22 */         StringBuilder f = new StringBuilder("Staff: ");
/* 23 */         for (String staff : staffNames) {
/*    */           try {
/* 25 */             f.append(staff).append(", ");
/* 26 */           } catch (Exception exception) {}
/*    */         } 
/*    */         
/* 29 */         sendMessage(f.toString());
/*    */       } 
/*    */       return;
/*    */     } 
/* 33 */     if (commands.length == 2) {
/* 34 */       if ("reset".equals(commands[0])) {
/* 35 */         staffNames.clear();
/* 36 */         sendMessage("staff list got reset.");
/*    */         return;
/*    */       } 
/*    */       return;
/*    */     } 
/* 41 */     if (commands.length >= 2) {
/* 42 */       switch (commands[0]) {
/*    */         case "add":
/* 44 */           staffNames.add(commands[1]);
/* 45 */           sendMessage(ChatFormatting.GREEN + commands[1] + " added to staff list");
/*    */           return;
/*    */         
/*    */         case "del":
/* 49 */           staffNames.remove(commands[1]);
/* 50 */           sendMessage(ChatFormatting.GREEN + commands[1] + " removed from staff list");
/*    */           return;
/*    */       } 
/*    */       
/* 54 */       sendMessage("Unknown Command, try staff add/del (name)");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\StaffCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */