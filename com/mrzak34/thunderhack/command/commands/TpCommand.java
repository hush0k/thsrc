/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class TpCommand extends Command {
/*    */   public TpCommand() {
/* 10 */     super("tp");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 15 */     if (commands.length == 1) {
/* 16 */       Command.sendMessage("Попробуй .tp <число> <число> <число>");
/*    */       return;
/*    */     } 
/* 19 */     if (commands.length > 2) {
/* 20 */       BlockPos pos = new BlockPos(Integer.parseInt(commands[0]), Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
/*    */       
/* 22 */       for (int i = 0; i < 10; i++) {
/* 23 */         this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(pos.func_177958_n(), (1 + pos.func_177956_o()), pos.func_177952_p(), false));
/*    */       }
/* 25 */       this.mc.field_71439_g.func_70107_b(pos.func_177958_n(), (pos.func_177956_o() + 1), pos.func_177952_p());
/*    */       
/* 27 */       Command.sendMessage("Телепортируемся на координаты X: " + pos.func_177958_n() + " Y: " + pos.func_177956_o() + " Z: " + pos.func_177952_p());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\TpCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */