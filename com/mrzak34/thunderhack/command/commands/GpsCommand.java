/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class GpsCommand
/*    */   extends Command {
/*    */   public GpsCommand() {
/* 12 */     super("gps");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 17 */     if (commands.length == 1) {
/* 18 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 19 */         Command.sendMessage("Попробуй .gps off / .gps x y");
/*    */       } else {
/* 21 */         Command.sendMessage("Try .gps off / .gps x y");
/*    */       } 
/* 23 */     } else if (commands.length == 2) {
/* 24 */       if (Objects.equals(commands[0], "off"))
/* 25 */         Thunderhack.gps_position = null; 
/* 26 */     } else if (commands.length > 2) {
/* 27 */       BlockPos pos = new BlockPos(Integer.parseInt(commands[0]), 0, Integer.parseInt(commands[1]));
/* 28 */       Thunderhack.gps_position = pos;
/* 29 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 30 */         Command.sendMessage("GPS настроен на X: " + pos.func_177958_n() + " Z: " + pos.func_177952_p());
/*    */       } else {
/* 32 */         Command.sendMessage("GPS is set to X: " + pos.func_177958_n() + " Z: " + pos.func_177952_p());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\GpsCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */