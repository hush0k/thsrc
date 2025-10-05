/*    */ package com.mrzak34.thunderhack.manager;
/*    */ import com.mrzak34.thunderhack.macro.Macro;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ 
/*    */ public class MacroManager {
/* 10 */   private static CopyOnWriteArrayList<Macro> macros = new CopyOnWriteArrayList<>();
/*    */   
/*    */   public static void addMacro(Macro macro) {
/* 13 */     if (!macros.contains(macro)) {
/* 14 */       macros.add(macro);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void onLoad() {
/* 19 */     macros = new CopyOnWriteArrayList<>();
/*    */     try {
/* 21 */       File file = new File("ThunderHack/misc/macro.txt");
/*    */       
/* 23 */       if (file.exists()) {
/* 24 */         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
/* 25 */           while (reader.ready()) {
/* 26 */             String[] nameandkey = reader.readLine().split(":");
/* 27 */             String name = nameandkey[0];
/* 28 */             String key = nameandkey[1];
/* 29 */             String command = nameandkey[2];
/* 30 */             addMacro(new Macro(name, command, Integer.parseInt(key)));
/*    */           }
/*    */         
/*    */         } 
/*    */       }
/* 35 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static void saveMacro() {
/* 40 */     File file = new File("ThunderHack/misc/macro.txt");
/*    */     try {
/* 42 */       (new File("ThunderHack")).mkdirs();
/* 43 */       file.createNewFile();
/* 44 */     } catch (Exception exception) {}
/*    */ 
/*    */     
/* 47 */     try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
/* 48 */       for (Macro macro : macros) {
/* 49 */         writer.write(macro.getName() + ":" + macro.getBind() + ":" + macro.getText() + "\n");
/*    */       }
/* 51 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeMacro(Macro macro) {
/* 56 */     macros.remove(macro);
/*    */   }
/*    */   
/*    */   public CopyOnWriteArrayList<Macro> getMacros() {
/* 60 */     return macros;
/*    */   }
/*    */   
/*    */   public Macro getMacroByName(String name) {
/* 64 */     for (Macro macro : getMacros()) {
/* 65 */       if (macro.getName().equalsIgnoreCase(name)) {
/* 66 */         return macro;
/*    */       }
/*    */     } 
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\MacroManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */