/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.DeathEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.C4Aura;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoEZ extends Module {
/*  24 */   public static ArrayList<String> EZWORDS = new ArrayList<>();
/*  25 */   public Setting<Boolean> global = register(new Setting("global", Boolean.valueOf(true)));
/*  26 */   String a = "";
/*  27 */   String b = "";
/*  28 */   String c = "";
/*  29 */   String[] EZ = new String[] { "%player% БЫЛ ПОПУЩЕН", "%player% БЫЛ ПРИХЛОПНУТ ТАПКОМ", "%player% EZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ", "%player% ПОСЫПАЛСЯ EZZZZZZZZZ", "%player% ТЫ БЫ ХОТЬ КИЛЛКУ ВКЛЮЧИЛ", "%player% СЛОЖИЛСЯ ПОПОЛАМ", "%player% ТУДААААААААААААА", "%player% ИЗИ БЛЯТЬ ХАХАХААХХАХАХА", "%player% УЛЕТЕЛ НА ТОТ СВЕТ", "%player% ПОПУЩЕННННННН", "%player% ВЫТАЩИ ЗАЛУПУ ИЗО РТА", "%player% БОЖЕ ЕЗЗКА", "%player% ИЗИИИИИИИИИИИИ", "%player% ЧЕ ТАК ЛЕГКО????", "RAGE OWNS %player% AND ALL", "RAGE ЗАОВНИЛ %player%", "%player% АХАХАХААХАХАХАХХААХАХ", "%player% GET GOOD JOIN RAGE", "%player% ЛЕГЧАЙШАЯ", "%player% GG EZ", "%player% НУЛИНА", "%player% ЛЕЖАТЬ ПЛЮС СОСАТЬ", "%player% ИЗИ БОТЯРА" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final Setting<ModeEn> Mode = register(new Setting("Mode", ModeEn.Basic));
/*  55 */   private final Setting<ServerMode> server = register(new Setting("Mode", ServerMode.Universal));
/*     */ 
/*     */   
/*     */   public AutoEZ() {
/*  59 */     super("AutoEZ", "Пишет изи убил убил - после килла", "only for-mcfunny.su", Module.Category.MISC);
/*  60 */     loadEZ();
/*     */   }
/*     */   
/*     */   public static void loadEZ() {
/*     */     try {
/*  65 */       File file = new File("ThunderHack/misc/AutoEZ.txt");
/*  66 */       if (!file.exists()) file.createNewFile();
/*     */       
/*  68 */       (new Thread(() -> {
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
/*     */               
/*     */               for (String l : lines) {
/*     */                 if (l.equals("")) {
/*     */                   newline = true;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */               
/*     */               EZWORDS.clear();
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
/*     */               EZWORDS = spamList;
/* 113 */             } catch (Exception e) {
/*     */               System.err.println("Could not load file ");
/*     */             } 
/* 116 */           })).start();
/* 117 */     } catch (IOException e) {
/* 118 */       System.err.println("Could not load file ");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 125 */     loadEZ();
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/* 130 */     if (fullNullCheck())
/* 131 */       return;  if (this.server.getValue() == ServerMode.Universal)
/* 132 */       return;  if (e.getPacket() instanceof SPacketChat) {
/* 133 */       SPacketChat packet = (SPacketChat)e.getPacket();
/* 134 */       if (packet.func_192590_c() != ChatType.GAME_INFO) {
/* 135 */         this.a = packet.func_148915_c().func_150254_d();
/* 136 */         if (this.a.contains("Вы убили игрока")) {
/* 137 */           this.b = ThunderUtils.solvename(this.a);
/*     */           
/* 139 */           if (this.Mode.getValue() == ModeEn.Basic) {
/*     */             
/* 141 */             int n = (int)Math.floor(Math.random() * this.EZ.length);
/* 142 */             this.c = this.EZ[n].replace("%player%", this.b);
/*     */           } else {
/* 144 */             if (EZWORDS.isEmpty()) {
/* 145 */               Command.sendMessage("Файл с AutoEZ пустой!");
/*     */               return;
/*     */             } 
/* 148 */             this.c = EZWORDS.get((new Random()).nextInt(EZWORDS.size()));
/* 149 */             this.c = this.c.replaceAll("%player%", this.b);
/*     */           } 
/*     */           
/* 152 */           mc.field_71439_g.func_71165_d(((Boolean)this.global.getValue()).booleanValue() ? ("!" + this.c) : this.c);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerDeath(DeathEvent e) {
/* 163 */     if (this.server.getValue() != ServerMode.Universal)
/* 164 */       return;  if (Aura.target != null && Aura.target == e.player) {
/* 165 */       sayEZ(e.player.func_70005_c_());
/*     */       return;
/*     */     } 
/* 168 */     if (C4Aura.target != null && C4Aura.target == e.player) {
/* 169 */       sayEZ(e.player.func_70005_c_());
/*     */       return;
/*     */     } 
/* 172 */     if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).target != null && ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).target == e.player) {
/* 173 */       sayEZ(e.player.func_70005_c_());
/*     */     }
/*     */   }
/*     */   
/*     */   public void sayEZ(String pn) {
/* 178 */     if (this.Mode.getValue() == ModeEn.Basic) {
/*     */       
/* 180 */       int n = (int)Math.floor(Math.random() * this.EZ.length);
/* 181 */       this.c = this.EZ[n].replace("%player%", pn);
/*     */     } else {
/* 183 */       if (EZWORDS.isEmpty()) {
/* 184 */         Command.sendMessage("Файл с AutoEZ пустой!");
/*     */         return;
/*     */       } 
/* 187 */       this.c = EZWORDS.get((new Random()).nextInt(EZWORDS.size()));
/* 188 */       this.c = this.c.replaceAll("%player%", pn);
/*     */     } 
/*     */     
/* 191 */     mc.field_71439_g.func_71165_d(((Boolean)this.global.getValue()).booleanValue() ? ("!" + this.c) : this.c);
/*     */   }
/*     */   
/*     */   public enum ModeEn
/*     */   {
/* 196 */     Custom,
/* 197 */     Basic; }
/*     */   
/*     */   public enum ServerMode {
/* 200 */     Universal,
/* 201 */     FunnyGame,
/* 202 */     NexusGrief;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoEZ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */