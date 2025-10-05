/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.ISoundHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReloadSoundCommand
/*    */   extends Command
/*    */ {
/*    */   public ReloadSoundCommand() {
/* 13 */     super("sound");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/*    */     try {
/* 19 */       ((ISoundHandler)this.mc.func_147118_V()).getSoundManager().func_148596_a();
/* 20 */       Command.sendMessage(ChatFormatting.GREEN + "Reloaded Sound System.");
/* 21 */     } catch (Exception e) {
/* 22 */       Command.sendMessage(ChatFormatting.RED + "Couldnt Reload Sound System!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\ReloadSoundCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */