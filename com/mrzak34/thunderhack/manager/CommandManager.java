/*    */ package com.mrzak34.thunderhack.manager;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.command.commands.BackCommand;
/*    */ import com.mrzak34.thunderhack.command.commands.ChangeSkinCommand;
/*    */ import com.mrzak34.thunderhack.command.commands.HClipCommand;
/*    */ import com.mrzak34.thunderhack.command.commands.WebHookSetCommand;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ public class CommandManager {
/* 11 */   private final ArrayList<Command> commands = new ArrayList<>();
/* 12 */   private String prefix = ".";
/*    */   
/*    */   public CommandManager() {
/* 15 */     this.commands.add(new TpCommand());
/* 16 */     this.commands.add(new BackCommand());
/* 17 */     this.commands.add(new MacroCommand());
/* 18 */     this.commands.add(new BindCommand());
/* 19 */     this.commands.add(new ModuleCommand());
/* 20 */     this.commands.add(new PrefixCommand());
/* 21 */     this.commands.add(new NpbCommand());
/* 22 */     this.commands.add(new loginCommand());
/* 23 */     this.commands.add(new ConfigCommand());
/* 24 */     this.commands.add(new CfgCommand());
/* 25 */     this.commands.add(new SearchCommand());
/* 26 */     this.commands.add(new FriendCommand());
/* 27 */     this.commands.add(new ChangeSkinCommand());
/* 28 */     this.commands.add(new HelpCommand());
/* 29 */     this.commands.add(new StaffCommand());
/* 30 */     this.commands.add(new ReloadCommand());
/* 31 */     this.commands.add(new RPCCommand());
/* 32 */     this.commands.add(new GpsCommand());
/* 33 */     this.commands.add(new KitCommand());
/* 34 */     this.commands.add(new UnloadCommand());
/* 35 */     this.commands.add(new ScannerCommand());
/* 36 */     this.commands.add(new ReloadSoundCommand());
/* 37 */     this.commands.add(new EclipCommand());
/* 38 */     this.commands.add(new HClipCommand());
/* 39 */     this.commands.add(new VClipCommand());
/* 40 */     this.commands.add(new WebHookSetCommand());
/* 41 */     this.commands.add(new DrawCommand());
/* 42 */     this.commands.add(new AutoBuyCommand());
/*    */   }
/*    */   
/*    */   public static String[] removeElement(String[] input, int indexToDelete) {
/* 46 */     LinkedList<String> result = new LinkedList<>();
/* 47 */     for (int i = 0; i < input.length; i++) {
/* 48 */       if (i != indexToDelete)
/* 49 */         result.add(input[i]); 
/*    */     } 
/* 51 */     return result.<String>toArray(input);
/*    */   }
/*    */   
/*    */   private static String strip(String str, String key) {
/* 55 */     if (str.startsWith(key) && str.endsWith(key)) {
/* 56 */       return str.substring(key.length(), str.length() - key.length());
/*    */     }
/* 58 */     return str;
/*    */   }
/*    */   
/*    */   public void executeCommand(String command) {
/* 62 */     String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
/* 63 */     String name = parts[0].substring(1);
/* 64 */     String[] args = removeElement(parts, 0);
/* 65 */     for (int i = 0; i < args.length; i++) {
/* 66 */       if (args[i] != null)
/* 67 */         args[i] = strip(args[i], "\""); 
/*    */     } 
/* 69 */     for (Command c : this.commands) {
/* 70 */       if (!c.getName().equalsIgnoreCase(name))
/* 71 */         continue;  c.execute(parts);
/*    */       return;
/*    */     } 
/* 74 */     Command.sendMessage(ChatFormatting.GRAY + "Command not found, type 'help' for the commands list.");
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<Command> getCommands() {
/* 79 */     return this.commands;
/*    */   }
/*    */   
/*    */   public String getClientMessage() {
/* 83 */     return "[ThunderHack+]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 88 */     return this.prefix;
/*    */   }
/*    */   
/*    */   public void setPrefix(String prefix) {
/* 92 */     this.prefix = prefix;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */