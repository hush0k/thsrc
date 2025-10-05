/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ 
/*    */ public class FriendCommand extends Command {
/*    */   public FriendCommand() {
/*  9 */     super("friend");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 14 */     if (commands.length == 1) {
/* 15 */       if (Thunderhack.friendManager.getFriends().isEmpty()) {
/* 16 */         sendMessage("Friend list empty D:.");
/*    */       } else {
/* 18 */         String f = "Friends: ";
/* 19 */         for (String friend : Thunderhack.friendManager.getFriends()) {
/*    */           try {
/* 21 */             f = f + friend + ", ";
/* 22 */           } catch (Exception exception) {}
/*    */         } 
/*    */         
/* 25 */         sendMessage(f);
/*    */       } 
/*    */       return;
/*    */     } 
/* 29 */     if (commands.length == 2) {
/* 30 */       if (commands[0].equals("reset")) {
/* 31 */         Thunderhack.friendManager.clear();
/* 32 */         sendMessage("Friends got reset.");
/*    */         return;
/*    */       } 
/* 35 */       sendMessage(commands[0] + (Thunderhack.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
/*    */       return;
/*    */     } 
/* 38 */     if (commands.length >= 2) {
/* 39 */       switch (commands[0]) {
/*    */         case "add":
/* 41 */           Thunderhack.friendManager.addFriend(commands[1]);
/* 42 */           sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
/* 43 */           this.mc.field_71439_g.func_71165_d("/w " + commands[1] + " i friended u at ThunderHack");
/*    */           return;
/*    */         
/*    */         case "del":
/* 47 */           Thunderhack.friendManager.removeFriend(commands[1]);
/* 48 */           sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
/*    */           return;
/*    */       } 
/*    */       
/* 52 */       sendMessage("Unknown Command, try friend add/del (name)");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\FriendCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */