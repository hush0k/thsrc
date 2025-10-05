/*     */ package com.mrzak34.thunderhack.command.commands;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class KitCommand
/*     */   extends Command
/*     */ {
/*     */   private static final String pathSave = "ThunderHack/misc/kits/AutoGear.json";
/*  17 */   private static final HashMap<String, String> errorMessage = new HashMap<String, String>()
/*     */     {
/*     */     
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KitCommand() {
/*  27 */     super("kit");
/*     */   }
/*     */   
/*     */   private static void errorMessage(String e) {
/*  31 */     Command.sendMessage("Error: " + (String)errorMessage.get(e));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCurrentSet() {
/*  36 */     JsonObject completeJson = new JsonObject();
/*     */     
/*     */     try {
/*  39 */       completeJson = (new JsonParser()).parse(new FileReader("ThunderHack/misc/kits/AutoGear.json")).getAsJsonObject();
/*  40 */       if (!completeJson.get("pointer").getAsString().equals("none")) {
/*  41 */         return completeJson.get("pointer").getAsString();
/*     */       }
/*     */     }
/*  44 */     catch (IOException iOException) {}
/*     */ 
/*     */     
/*  47 */     errorMessage("NoEx");
/*  48 */     return "";
/*     */   }
/*     */   
/*     */   public static String getInventoryKit(String kit) {
/*  52 */     JsonObject completeJson = new JsonObject();
/*     */     
/*     */     try {
/*  55 */       completeJson = (new JsonParser()).parse(new FileReader("ThunderHack/misc/kits/AutoGear.json")).getAsJsonObject();
/*  56 */       return completeJson.get(kit).getAsString();
/*     */     
/*     */     }
/*  59 */     catch (IOException iOException) {
/*     */ 
/*     */       
/*  62 */       errorMessage("NoEx");
/*  63 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public void execute(String[] commands) {
/*  68 */     if (commands.length == 1) {
/*  69 */       Command.sendMessage("kit <create/set/del/list> <name>");
/*     */       return;
/*     */     } 
/*  72 */     if (commands.length == 2) {
/*  73 */       if (commands[0].equals("list")) {
/*  74 */         listMessage();
/*     */         
/*     */         return;
/*     */       } 
/*     */       return;
/*     */     } 
/*  80 */     if (commands.length >= 2) {
/*  81 */       switch (commands[0]) {
/*     */         case "create":
/*  83 */           save(commands[1]);
/*     */           return;
/*     */         
/*     */         case "set":
/*  87 */           set(commands[1]);
/*     */           return;
/*     */         
/*     */         case "del":
/*  91 */           delete(commands[1]);
/*     */           return;
/*     */       } 
/*     */       
/*  95 */       sendMessage(".kit create/set/del");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void listMessage() {
/* 100 */     JsonObject completeJson = new JsonObject();
/*     */     
/*     */     try {
/* 103 */       completeJson = (new JsonParser()).parse(new FileReader("ThunderHack/misc/kits/AutoGear.json")).getAsJsonObject();
/* 104 */       int lenghtJson = completeJson.entrySet().size();
/* 105 */       for (int i = 0; i < lenghtJson; i++) {
/* 106 */         String item = (new JsonParser()).parse(new FileReader("ThunderHack/misc/kits/AutoGear.json")).getAsJsonObject().entrySet().toArray()[i].toString().split("=")[0];
/* 107 */         if (!item.equals("pointer")) {
/* 108 */           Command.sendMessage("Kit avaible: " + item);
/*     */         }
/*     */       } 
/* 111 */     } catch (IOException e) {
/*     */       
/* 113 */       errorMessage("NoEx");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void delete(String name) {
/* 118 */     JsonObject completeJson = new JsonObject();
/*     */     
/*     */     try {
/* 121 */       completeJson = (new JsonParser()).parse(new FileReader("ThunderHack/misc/kits/AutoGear.json")).getAsJsonObject();
/* 122 */       if (completeJson.get(name) != null && !name.equals("pointer"))
/*     */       
/* 124 */       { completeJson.remove(name);
/*     */         
/* 126 */         if (completeJson.get("pointer").getAsString().equals(name)) {
/* 127 */           completeJson.addProperty("pointer", "none");
/*     */         }
/* 129 */         saveFile(completeJson, name, "deleted"); }
/* 130 */       else { errorMessage("NoEx"); }
/*     */     
/* 132 */     } catch (IOException e) {
/*     */       
/* 134 */       errorMessage("NoEx");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void set(String name) {
/* 139 */     JsonObject completeJson = new JsonObject();
/*     */     
/*     */     try {
/* 142 */       completeJson = (new JsonParser()).parse(new FileReader("ThunderHack/misc/kits/AutoGear.json")).getAsJsonObject();
/* 143 */       if (completeJson.get(name) != null && !name.equals("pointer"))
/*     */       
/* 145 */       { completeJson.addProperty("pointer", name);
/*     */         
/* 147 */         saveFile(completeJson, name, "selected"); }
/* 148 */       else { errorMessage("NoEx"); }
/*     */     
/* 150 */     } catch (IOException e) {
/*     */       
/* 152 */       errorMessage("NoEx");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void save(String name) {
/* 157 */     JsonObject completeJson = new JsonObject();
/*     */     
/*     */     try {
/* 160 */       completeJson = (new JsonParser()).parse(new FileReader("ThunderHack/misc/kits/AutoGear.json")).getAsJsonObject();
/* 161 */       if (completeJson.get(name) != null && !name.equals("pointer")) {
/* 162 */         errorMessage("Exist");
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/* 167 */     } catch (IOException e) {
/*     */       
/* 169 */       completeJson.addProperty("pointer", "none");
/*     */     } 
/*     */ 
/*     */     
/* 173 */     StringBuilder jsonInventory = new StringBuilder();
/* 174 */     for (ItemStack item : this.mc.field_71439_g.field_71071_by.field_70462_a)
/*     */     {
/* 176 */       jsonInventory.append(item.func_77973_b().getRegistryName().toString() + item.func_77960_j()).append(" ");
/*     */     }
/*     */     
/* 179 */     completeJson.addProperty(name, jsonInventory.toString());
/*     */     
/* 181 */     saveFile(completeJson, name, "saved");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveFile(JsonObject completeJson, String name, String operation) {
/*     */     try {
/* 188 */       BufferedWriter bw = new BufferedWriter(new FileWriter("ThunderHack/misc/kits/AutoGear.json"));
/*     */       
/* 190 */       bw.write(completeJson.toString());
/*     */       
/* 192 */       bw.close();
/*     */       
/* 194 */       Command.sendMessage("Kit " + name + " " + operation);
/* 195 */     } catch (IOException e) {
/* 196 */       errorMessage("Saving");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\KitCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */