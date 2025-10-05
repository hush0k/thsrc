/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import java.lang.reflect.Field;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ public class loginCommand
/*    */   extends Command
/*    */ {
/*    */   public loginCommand() {
/* 15 */     super("login");
/*    */   }
/*    */   
/*    */   public static void login(String string) {
/*    */     try {
/* 20 */       Field field = Minecraft.class.getDeclaredField("field_71449_j");
/* 21 */       field.setAccessible(true);
/* 22 */       Field field2 = Field.class.getDeclaredField("modifiers");
/* 23 */       field2.setAccessible(true);
/* 24 */       field2.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/* 25 */       field.set(Util.mc, new Session(string, "", "", "mojang"));
/* 26 */     } catch (Exception exception) {
/* 27 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 28 */         Command.sendMessage("Неверное имя! " + exception);
/*    */       } else {
/* 30 */         Command.sendMessage("Wrong name! " + exception);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] var1) {
/*    */     try {
/* 38 */       login(var1[0]);
/* 39 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 40 */         Command.sendMessage("Аккаунт изменен на: " + Util.mc.func_110432_I().func_111285_a());
/*    */       } else {
/* 42 */         Command.sendMessage("Account switched to: " + Util.mc.func_110432_I().func_111285_a());
/*    */       } 
/* 44 */     } catch (Exception exception) {
/* 45 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 46 */         Command.sendMessage("Использование: .login nick");
/*    */       } else {
/* 48 */         Command.sendMessage("Try: .login nick");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\loginCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */