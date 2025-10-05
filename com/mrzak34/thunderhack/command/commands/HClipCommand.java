/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public class HClipCommand
/*    */   extends Command {
/*    */   public HClipCommand() {
/* 12 */     super("hclip");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 17 */     if (commands.length == 1) {
/* 18 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 19 */         Command.sendMessage("Попробуй .hclip <число>");
/*    */       } else {
/* 21 */         Command.sendMessage("Try .hclip <number>");
/*    */       } 
/*    */       return;
/*    */     } 
/* 25 */     if (commands.length == 2)
/*    */       try {
/* 27 */         if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 28 */           Command.sendMessage(TextFormatting.GREEN + "клипаемся на  " + Double.valueOf(commands[0]) + " блоков.");
/*    */         } else {
/* 30 */           Command.sendMessage(TextFormatting.GREEN + "clipping to  " + Double.valueOf(commands[0]) + " blocks.");
/*    */         } 
/*    */         
/* 33 */         float f = this.mc.field_71439_g.field_70177_z * 0.017453292F;
/* 34 */         double speed = Double.valueOf(commands[0]).doubleValue();
/* 35 */         double x = -(MathHelper.func_76126_a(f) * speed);
/* 36 */         double z = MathHelper.func_76134_b(f) * speed;
/* 37 */         this.mc.field_71439_g.func_70107_b(this.mc.field_71439_g.field_70165_t + x, this.mc.field_71439_g.field_70163_u, this.mc.field_71439_g.field_70161_v + z);
/* 38 */       } catch (Exception exception) {} 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\HClipCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */