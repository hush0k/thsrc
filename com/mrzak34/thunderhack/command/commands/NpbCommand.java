/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ public class NpbCommand
/*    */   extends Command
/*    */ {
/*    */   public NpbCommand() {
/* 12 */     super("npb");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 17 */     if (args.length >= 3) {
/* 18 */       String text = String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])args, 2, args.length - 1));
/* 19 */       Command.sendMessageWithoutTH(ChatFormatting.GRAY + "[" + ChatFormatting.LIGHT_PURPLE + ChatFormatting.BOLD + "*" + ChatFormatting.RESET + ChatFormatting.GRAY + "] Игрок " + ChatFormatting.AQUA + ChatFormatting.BOLD + args[0] + ChatFormatting.RESET + ChatFormatting.GRAY + " забанил " + ChatFormatting.RED + ChatFormatting.BOLD + args[1] + ChatFormatting.RESET + ChatFormatting.GRAY + " на 10 минут по причине" + ChatFormatting.GREEN + " " + ChatFormatting.BOLD + text + ChatFormatting.RESET);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\NpbCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */