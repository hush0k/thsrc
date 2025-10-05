/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public class VClipCommand
/*    */   extends Command {
/*    */   public VClipCommand() {
/* 13 */     super("vclip");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 18 */     if (commands.length == 1) {
/* 19 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 20 */         Command.sendMessage("Попробуй .vclip <число>");
/*    */       } else {
/* 22 */         Command.sendMessage("Try .vclip <number>");
/*    */       } 
/*    */       return;
/*    */     } 
/* 26 */     if (commands.length == 2)
/*    */       
/*    */       try {
/*    */         
/* 30 */         if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 31 */           Command.sendMessage(TextFormatting.GREEN + "Клипаемся на " + Double.valueOf(commands[0]) + " блоков");
/*    */         } else {
/* 33 */           Command.sendMessage(TextFormatting.GREEN + "clipping to  " + Double.valueOf(commands[0]) + " blocks.");
/*    */         } 
/*    */         int i;
/* 36 */         for (i = 0; i < 10; i++) {
/* 37 */           this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v, false));
/*    */         }
/* 39 */         for (i = 0; i < 10; i++) {
/* 40 */           this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + Double.parseDouble(commands[0]), this.mc.field_71439_g.field_70161_v, false));
/*    */         }
/* 42 */         this.mc.field_71439_g.func_70107_b(this.mc.field_71439_g.field_70165_t, this.mc.field_71439_g.field_70163_u + Double.parseDouble(commands[0]), this.mc.field_71439_g.field_70161_v);
/* 43 */       } catch (Exception exception) {} 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\VClipCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */