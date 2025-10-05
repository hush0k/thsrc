/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.notification.Notification;
/*    */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.network.play.server.SPacketChat;
/*    */ import org.apache.commons.lang3.RandomStringUtils;
/*    */ 
/*    */ public class AutoAuth extends Module {
/*    */   private String password;
/*    */   private final Setting<Mode> passwordMode;
/*    */   
/*    */   public AutoAuth() {
/* 18 */     super("AutoAuth", "Автоматически-логинится на -серверах", "AutoAuth", Module.Category.MISC);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 23 */     this.passwordMode = register(new Setting("Password Mode", Mode.Custom));
/* 24 */     this.cpass = register(new Setting("Password", "babidjon777", v -> (this.passwordMode.getValue() == Mode.Custom)));
/* 25 */     this.showPasswordInChat = register(new Setting("Show Password In Chat", Boolean.valueOf(true)));
/*    */   }
/*    */   public Setting<String> cpass; public Setting<Boolean> showPasswordInChat;
/* 28 */   private enum Mode { Custom, Random, Qwerty; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 33 */     Command.sendMessage(ChatFormatting.RED + "Внимание!!! " + ChatFormatting.RESET + "Пароль сохраняется в конфиге, перед передачей конфига " + ChatFormatting.RED + " ВЫКЛЮЧИ МОДУЛЬ!");
/* 34 */     Command.sendMessage(ChatFormatting.RED + "Внимание!!! " + ChatFormatting.RESET + "Пароль сохраняется в конфиге, перед передачей конфига " + ChatFormatting.RED + " ВЫКЛЮЧИ МОДУЛЬ!");
/* 35 */     Command.sendMessage(ChatFormatting.RED + "Внимание!!! " + ChatFormatting.RESET + "Пароль сохраняется в конфиге, перед передачей конфига " + ChatFormatting.RED + " ВЫКЛЮЧИ МОДУЛЬ!");
/* 36 */     Command.sendMessage(ChatFormatting.RED + "Attention!!! " + ChatFormatting.RESET + "The password is saved in the config, before sharing the config " + ChatFormatting.RED + " TURN OFF THIS MODULE!");
/* 37 */     Command.sendMessage(ChatFormatting.RED + "Attention!!! " + ChatFormatting.RESET + "The password is saved in the config, before sharing the config " + ChatFormatting.RED + " TURN OFF THIS MODULE!");
/* 38 */     Command.sendMessage(ChatFormatting.RED + "Attention!!! " + ChatFormatting.RESET + "The password is saved in the config, before sharing the config " + ChatFormatting.RED + " TURN OFF THIS MODULE!");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 43 */     Command.sendMessage(ChatFormatting.RED + "AutoAuth " + ChatFormatting.RESET + "reseting password...");
/* 44 */     this.cpass.setValue("none");
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 49 */     if (event.getPacket() instanceof SPacketChat) {
/* 50 */       SPacketChat pac = (SPacketChat)event.getPacket();
/* 51 */       if (this.passwordMode.getValue() == Mode.Custom) {
/* 52 */         this.password = (String)this.cpass.getValue();
/* 53 */       } else if (this.passwordMode.getValue() == Mode.Qwerty) {
/* 54 */         this.password = "qwerty123";
/* 55 */       } else if (this.passwordMode.getValue() == Mode.Random) {
/* 56 */         String str1 = RandomStringUtils.randomAlphabetic(5);
/* 57 */         String str2 = RandomStringUtils.randomPrint(5);
/* 58 */         this.password = str1 + str2;
/*    */       } 
/* 60 */       if (this.passwordMode.getValue() == Mode.Custom && (this.password == null || this.password.isEmpty()))
/*    */         return; 
/* 62 */       if (pac.func_148915_c().func_150254_d().contains("/reg") || pac.func_148915_c().func_150254_d().contains("/register") || pac.func_148915_c().func_150254_d().contains("Зарегестрируйтесь")) {
/* 63 */         mc.field_71439_g.func_71165_d("/reg " + this.password + " " + this.password);
/* 64 */         if (((Boolean)this.showPasswordInChat.getValue()).booleanValue())
/* 65 */           Command.sendMessage("Your password: " + ChatFormatting.RED + this.password); 
/* 66 */         if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass(NotificationManager.class)).isEnabled())
/* 67 */           NotificationManager.publicity("You are successfully registered!", 4, Notification.Type.SUCCESS); 
/* 68 */       } else if (pac.func_148915_c().func_150254_d().contains("Авторизуйтесь") || pac.func_148915_c().func_150254_d().contains("/l")) {
/* 69 */         mc.field_71439_g.func_71165_d("/login " + this.password);
/* 70 */         if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass(NotificationManager.class)).isEnabled())
/* 71 */           NotificationManager.publicity("You are successfully login!", 4, Notification.Type.SUCCESS); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoAuth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */