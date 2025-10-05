/*    */ package com.mrzak34.thunderhack.command;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentBase;
/*    */ 
/*    */ 
/*    */ public abstract class Command
/*    */ {
/*    */   protected String name;
/*    */   public Minecraft mc;
/*    */   
/*    */   public Command(String name) {
/* 20 */     this.name = name;
/* 21 */     this.mc = Util.mc;
/*    */   }
/*    */   
/*    */   public static void sendMessage(String message) {
/* 25 */     sendSilentMessage(Thunderhack.commandManager.getClientMessage() + " " + ChatFormatting.GRAY + message);
/*    */   }
/*    */   
/*    */   public static void sendMessageWithoutTH(String message) {
/* 29 */     sendSilentMessage(ChatFormatting.GRAY + message);
/*    */   }
/*    */   
/*    */   public static void sendSilentMessage(String message) {
/* 33 */     if (Module.fullNullCheck()) {
/*    */       return;
/*    */     }
/* 36 */     Util.mc.field_71439_g.func_145747_a((ITextComponent)new ChatMessage(message));
/*    */   }
/*    */   
/*    */   public static void sendIText(ITextComponent message) {
/* 40 */     if (Module.fullNullCheck()) {
/*    */       return;
/*    */     }
/* 43 */     Util.mc.field_71439_g.func_145747_a(message);
/*    */   }
/*    */   
/*    */   public static String getCommandPrefix() {
/* 47 */     return Thunderhack.commandManager.getPrefix();
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void execute(String[] paramArrayOfString);
/*    */ 
/*    */   
/*    */   public String getName() {
/* 55 */     return this.name;
/*    */   }
/*    */   
/*    */   public static class ChatMessage
/*    */     extends TextComponentBase
/*    */   {
/*    */     private final String text;
/*    */     
/*    */     public ChatMessage(String text) {
/* 64 */       Pattern pattern = Pattern.compile("&[0123456789abcdefrlosmk]");
/* 65 */       Matcher matcher = pattern.matcher(text);
/* 66 */       StringBuffer stringBuffer = new StringBuffer();
/* 67 */       while (matcher.find()) {
/* 68 */         String replacement = matcher.group().substring(1);
/* 69 */         matcher.appendReplacement(stringBuffer, replacement);
/*    */       } 
/* 71 */       matcher.appendTail(stringBuffer);
/* 72 */       this.text = stringBuffer.toString();
/*    */     }
/*    */     
/*    */     public String func_150261_e() {
/* 76 */       return this.text;
/*    */     }
/*    */     
/*    */     public ITextComponent func_150259_f() {
/* 80 */       return null;
/*    */     }
/*    */     
/*    */     public ITextComponent shallowCopy() {
/* 84 */       return (ITextComponent)new ChatMessage(this.text);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */