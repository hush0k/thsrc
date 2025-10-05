/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.render.Search;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class SearchCommand
/*    */   extends Command {
/*    */   public SearchCommand() {
/* 12 */     super("search");
/*    */   }
/*    */   
/*    */   private static Block getRegisteredBlock(String blockName) {
/* 16 */     return (Block)Block.field_149771_c.func_82594_a(new ResourceLocation(blockName));
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] commands) {
/* 21 */     if (commands.length == 1) {
/* 22 */       if (Search.defaultBlocks.isEmpty()) {
/* 23 */         sendMessage("Search list empty");
/*    */       } else {
/* 25 */         String f = "Search list: ";
/* 26 */         for (Block name : Search.defaultBlocks) {
/*    */           try {
/* 28 */             f = f + name.getRegistryName() + ", ";
/* 29 */           } catch (Exception exception) {}
/*    */         } 
/*    */         
/* 32 */         sendMessage(f);
/*    */       } 
/*    */       return;
/*    */     } 
/* 36 */     if (commands.length == 2) {
/* 37 */       if ("reset".equals(commands[0])) {
/* 38 */         Search.defaultBlocks.clear();
/* 39 */         sendMessage("Search got reset.");
/* 40 */         this.mc.field_71438_f.func_72712_a();
/*    */         
/*    */         return;
/*    */       } 
/*    */       return;
/*    */     } 
/* 46 */     if (commands.length >= 2) {
/* 47 */       switch (commands[0]) {
/*    */         case "add":
/* 49 */           Search.defaultBlocks.add(getRegisteredBlock(commands[1]));
/* 50 */           sendMessage(ChatFormatting.GREEN + commands[1] + " added to search");
/* 51 */           this.mc.field_71438_f.func_72712_a();
/*    */           return;
/*    */         
/*    */         case "del":
/* 55 */           Search.defaultBlocks.remove(getRegisteredBlock(commands[1]));
/* 56 */           sendMessage(ChatFormatting.RED + commands[1] + " removed from search");
/* 57 */           this.mc.field_71438_f.func_72712_a();
/*    */           return;
/*    */       } 
/*    */       
/* 61 */       sendMessage("Unknown Command, try search add/del <block name>");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\SearchCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */