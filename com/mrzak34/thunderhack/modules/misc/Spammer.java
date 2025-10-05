/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class Spammer extends Module {
/*  17 */   public static ArrayList<String> SpamList = new ArrayList<>();
/*  18 */   public Setting<Boolean> global = register(new Setting("global", Boolean.valueOf(true)));
/*  19 */   public Setting<Integer> delay = register(new Setting("delay", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(30)));
/*  20 */   private final Setting<ModeEn> Mode = register(new Setting("Mode", ModeEn.API));
/*  21 */   private final Timer timer_delay = new Timer();
/*  22 */   private String word_from_api = "-";
/*     */   
/*     */   public Spammer() {
/*  25 */     super("Spammer", "спаммер", Module.Category.MISC);
/*     */   }
/*     */   
/*     */   public static void loadSpammer() {
/*     */     try {
/*  30 */       File file = new File("ThunderHack/misc/spammer.txt");
/*  31 */       if (!file.exists()) file.createNewFile(); 
/*  32 */       (new Thread(() -> {
/*     */             try {
/*     */               FileInputStream fis = new FileInputStream(file);
/*     */               
/*     */               InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
/*     */               
/*     */               BufferedReader reader = new BufferedReader(isr);
/*     */               
/*     */               ArrayList<String> lines = new ArrayList<>();
/*     */               
/*     */               String line;
/*     */               
/*     */               while ((line = reader.readLine()) != null) {
/*     */                 lines.add(line);
/*     */               }
/*     */               
/*     */               boolean newline = false;
/*     */               for (String l : lines) {
/*     */                 if (l.equals("")) {
/*     */                   newline = true;
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */               SpamList.clear();
/*     */               ArrayList<String> spamList = new ArrayList<>();
/*     */               if (newline) {
/*     */                 StringBuilder spamChunk = new StringBuilder();
/*     */                 for (String l : lines) {
/*     */                   if (l.equals("")) {
/*     */                     if (spamChunk.length() > 0) {
/*     */                       spamList.add(spamChunk.toString());
/*     */                       spamChunk = new StringBuilder();
/*     */                     } 
/*     */                     continue;
/*     */                   } 
/*     */                   spamChunk.append(l).append(" ");
/*     */                 } 
/*     */                 spamList.add(spamChunk.toString());
/*     */               } else {
/*     */                 spamList.addAll(lines);
/*     */               } 
/*     */               SpamList = spamList;
/*  74 */             } catch (Exception e) {
/*     */               System.err.println("Could not load file ");
/*     */             } 
/*  77 */           })).start();
/*  78 */     } catch (IOException e) {
/*  79 */       System.err.println("Could not load file ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  86 */     loadSpammer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  91 */     if (this.timer_delay.passedS(((Integer)this.delay.getValue()).intValue())) {
/*  92 */       if (this.Mode.getValue() != ModeEn.Custom) {
/*  93 */         getMsg();
/*  94 */         if (!Objects.equals(this.word_from_api, "-")) {
/*  95 */           this.word_from_api = this.word_from_api.replace("<p>", "");
/*  96 */           this.word_from_api = this.word_from_api.replace("</p>", "");
/*  97 */           this.word_from_api = this.word_from_api.replace(".", "");
/*  98 */           this.word_from_api = this.word_from_api.replace(",", "");
/*     */           
/* 100 */           mc.field_71439_g.func_71165_d(((Boolean)this.global.getValue()).booleanValue() ? ("!" + this.word_from_api) : this.word_from_api);
/*     */         } 
/*     */       } else {
/* 103 */         if (SpamList.isEmpty()) {
/* 104 */           Command.sendMessage("Файл spammer пустой!");
/* 105 */           toggle();
/*     */           return;
/*     */         } 
/* 108 */         String c = SpamList.get((new Random()).nextInt(SpamList.size()));
/* 109 */         mc.field_71439_g.func_71165_d(((Boolean)this.global.getValue()).booleanValue() ? ("!" + c) : c);
/*     */       } 
/* 111 */       this.timer_delay.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getMsg() {
/* 116 */     (new Thread(() -> {
/*     */           try {
/*     */             URL api = new URL("https://fish-text.ru/get?format=html&number=1");
/*     */             BufferedReader in = new BufferedReader(new InputStreamReader(api.openStream(), StandardCharsets.UTF_8));
/*     */             String inputLine;
/*     */             if ((inputLine = in.readLine()) != null) {
/*     */               this.word_from_api = inputLine;
/*     */             }
/* 124 */           } catch (Exception exception) {}
/*     */         
/* 126 */         })).start();
/*     */   }
/*     */   
/*     */   public enum ModeEn {
/* 130 */     Custom,
/* 131 */     API;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\Spammer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */