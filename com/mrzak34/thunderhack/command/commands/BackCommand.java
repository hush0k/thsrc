/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.manager.EventManager;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class BackCommand
/*    */   extends Command {
/*    */   public BackCommand() {
/* 14 */     super("back");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String[] var1) {
/* 20 */     if (EventManager.backX == 0 && EventManager.backY == 0 && EventManager.backZ == 0) {
/*    */       return;
/*    */     }
/* 23 */     BlockPos pos = new BlockPos(EventManager.backX, EventManager.backY, EventManager.backZ);
/*    */     
/* 25 */     for (int i = 0; i < 10; i++) {
/* 26 */       this.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(pos.func_177958_n(), (1 + pos.func_177956_o()), pos.func_177952_p(), false));
/*    */     }
/* 28 */     this.mc.field_71439_g.func_70107_b(pos.func_177958_n(), (pos.func_177956_o() + 1), pos.func_177952_p());
/*    */     
/* 30 */     if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 31 */       Command.sendMessage("Телепортируемся на координаты X: " + EventManager.backX + " Y: " + EventManager.backY + " Z: " + EventManager.backZ);
/*    */     } else {
/* 33 */       Command.sendMessage("Teleporting to X: " + EventManager.backX + " Y: " + EventManager.backY + " Z: " + EventManager.backZ);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\BackCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */