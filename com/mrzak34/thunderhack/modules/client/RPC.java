/*     */ package com.mrzak34.thunderhack.modules.client;
/*     */ import club.minnced.discord.rpc.DiscordRPC;
/*     */ import club.minnced.discord.rpc.DiscordRichPresence;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.DeathEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.C4Aura;
/*     */ import com.mrzak34.thunderhack.modules.misc.NameProtect;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class RPC extends Module {
/*     */   public Setting<mode> Mode;
/*     */   public Setting<Boolean> showIP;
/*     */   public Setting<Boolean> queue;
/*     */   public Setting<smode> sMode;
/*     */   public Setting<String> state;
/*     */   public Setting<Boolean> nickname;
/*     */   
/*     */   public RPC() {
/*  30 */     super("DiscordRPC", "крутая рпс", Module.Category.CLIENT);
/*     */ 
/*     */     
/*  33 */     this.Mode = register(new Setting("Picture", mode.MegaCute));
/*  34 */     this.showIP = register(new Setting("ShowIP", Boolean.valueOf(true)));
/*  35 */     this.queue = register(new Setting("Queue", Boolean.valueOf(true)));
/*  36 */     this.sMode = register(new Setting("StateMode", smode.Stats));
/*     */     
/*  38 */     this.state = register(new Setting("State", "ThunderHack+"));
/*  39 */     this.nickname = register(new Setting("Nickname", Boolean.valueOf(true)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     this.kills = 0;
/*     */   } public static boolean inQ = false; private static final DiscordRPC rpc = DiscordRPC.INSTANCE; public static DiscordRichPresence presence = new DiscordRichPresence(); private static Thread thread;
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  52 */     if (fullNullCheck())
/*  53 */       return;  if (e.getPacket() instanceof SPacketChat && ((Boolean)this.queue.getValue()).booleanValue()) {
/*  54 */       SPacketChat packchat = (SPacketChat)e.getPacket();
/*  55 */       String wtf = packchat.func_148915_c().func_150260_c();
/*  56 */       position = StringUtils.substringBetween(wtf, "Position in queue: ", "\nYou can purchase");
/*  57 */       if (wtf.contains("Position in queue")) inQ = true; 
/*     */     } 
/*  59 */     if (mc.field_71439_g.field_70163_u < 63.0D || mc.field_71439_g.field_70163_u > 64.0D) inQ = false; 
/*     */   }
/*     */   public static boolean started; static String String1 = "none"; public static String position = ""; private int kills;
/*     */   
/*     */   public void onLogout() {
/*  64 */     inQ = false;
/*  65 */     position = "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  71 */     started = false;
/*  72 */     if (thread != null && !thread.isInterrupted()) {
/*  73 */       thread.interrupt();
/*     */     }
/*  75 */     rpc.Discord_Shutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerDeath(DeathEvent e) {
/*  81 */     if (Aura.target != null && Aura.target == e.player) {
/*  82 */       this.kills++;
/*     */       return;
/*     */     } 
/*  85 */     if (C4Aura.target != null && C4Aura.target == e.player) {
/*  86 */       this.kills++;
/*     */       return;
/*     */     } 
/*  89 */     if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).target != null && ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).target == e.player) {
/*  90 */       this.kills++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  96 */     if (!started) {
/*  97 */       started = true;
/*  98 */       Object handlers = new DiscordEventHandlers();
/*  99 */       rpc.Discord_Initialize("939112431488225280", (DiscordEventHandlers)handlers, true, "");
/* 100 */       presence.startTimestamp = System.currentTimeMillis() / 1000L;
/* 101 */       presence.largeImageText = "ldMEGA CFG lykdaes";
/* 102 */       rpc.Discord_UpdatePresence(presence);
/*     */       
/* 104 */       thread = new Thread(() -> {
/*     */             while (!Thread.currentThread().isInterrupted()) {
/*     */               rpc.Discord_RunCallbacks();
/*     */               
/*     */               if (!inQ) {
/* 109 */                 presence.details = ((Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiMainMenu || (Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiScreenServerList || (Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiScreenAddServer) ? "В главном меню" : ((Minecraft.func_71410_x().func_147104_D() != null) ? (((Boolean)this.showIP.getValue()).booleanValue() ? ("Играет на " + (Minecraft.func_71410_x().func_147104_D()).field_78845_b) : "НН сервер") : "Выбирает сервер");
/*     */               } else {
/*     */                 presence.details = "In queue: " + position;
/*     */               } 
/*     */               presence.details = (Util.mc.field_71462_r instanceof net.minecraft.client.gui.GuiMainMenu) ? "В главном меню" : ("Играет " + ((Minecraft.func_71410_x().func_147104_D() != null) ? (((Boolean)this.showIP.getValue()).booleanValue() ? ((Minecraft.func_71410_x().func_147104_D()).field_78845_b.equals("localhost") ? "на 2bt2.org via 2bored2wait" : ("на " + (Minecraft.func_71410_x().func_147104_D()).field_78845_b)) : " НН сервер") : " Читерит в одиночке"));
/*     */               if (this.sMode.getValue() == smode.Custom) {
/*     */                 presence.state = (String)this.state.getValue();
/*     */               } else {
/*     */                 presence.state = "Kills: " + this.kills + " | Hacks: " + Thunderhack.moduleManager.getEnabledModules().size() + " / " + Thunderhack.moduleManager.modules.size();
/*     */               } 
/*     */               if (((Boolean)this.nickname.getValue()).booleanValue()) {
/*     */                 if (((NameProtect)Thunderhack.moduleManager.getModuleByClass(NameProtect.class)).isDisabled()) {
/*     */                   presence.smallImageText = "logged as - " + Util.mc.func_110432_I().func_111285_a();
/*     */                 } else {
/*     */                   presence.smallImageText = "logged as - Protected";
/*     */                 } 
/*     */                 presence.smallImageKey = "https://minotar.net/helm/" + Util.mc.func_110432_I().func_111285_a() + "/100.png";
/*     */               } 
/*     */               switch ((mode)this.Mode.getValue()) {
/*     */                 case Thlogo:
/*     */                   presence.largeImageKey = "aboba3";
/*     */                   break;
/*     */ 
/*     */                 
/*     */                 case minecraft:
/*     */                   presence.largeImageKey = "minecraft";
/*     */                   break;
/*     */                 
/*     */                 case Unknown:
/*     */                   presence.largeImageKey = "th";
/*     */                   break;
/*     */                 
/*     */                 case Konas:
/*     */                   presence.largeImageKey = "2213";
/*     */                   break;
/*     */                 
/*     */                 case Astolfo:
/*     */                   presence.largeImageKey = "astolf";
/*     */                   break;
/*     */                 
/*     */                 case SlivSRC:
/*     */                   presence.largeImageKey = "hhh";
/*     */                   break;
/*     */                 
/*     */                 case pic:
/*     */                   presence.largeImageKey = "pic";
/*     */                   break;
/*     */                 
/*     */                 case MegaCute:
/*     */                   presence.largeImageKey = "https://media1.tenor.com/images/6bcbfcc0be97d029613b54f97845bc59/tenor.gif?itemid=26823781";
/*     */                   break;
/*     */                 
/*     */                 case Hunger:
/*     */                   presence.largeImageKey = "https://media.tenor.com/nUNorsu3_RIAAAAd/cat-sweet.gif";
/*     */                   break;
/*     */                 
/*     */                 case Custom:
/*     */                   readFile();
/*     */                   presence.largeImageKey = String1.split("SEPARATOR")[0];
/*     */                   if (!Objects.equals(String1.split("SEPARATOR")[1], "none")) {
/*     */                     presence.smallImageKey = String1.split("SEPARATOR")[1];
/*     */                   }
/*     */                   break;
/*     */               } 
/*     */               
/*     */               rpc.Discord_UpdatePresence(presence);
/*     */               try {
/*     */                 Thread.sleep(2000L);
/* 177 */               } catch (InterruptedException interruptedException) {}
/*     */             } 
/*     */           }"RPC-Callback-Handler");
/* 180 */       thread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readFile() {
/*     */     try {
/* 187 */       File file = new File("ThunderHack/misc/RPC.txt");
/* 188 */       if (file.exists()) {
/* 189 */         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
/* 190 */           while (reader.ready()) {
/* 191 */             String1 = reader.readLine();
/*     */           }
/*     */         } 
/*     */       }
/* 195 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void WriteFile(String url1, String url2) {
/* 200 */     File file = new File("ThunderHack/misc/RPC.txt");
/*     */     try {
/* 202 */       file.createNewFile();
/* 203 */       try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
/* 204 */         writer.write(url1 + "SEPARATOR" + url2 + '\n');
/* 205 */       } catch (Exception exception) {}
/* 206 */     } catch (Exception exception) {}
/*     */   }
/*     */   
/*     */   public enum mode {
/* 210 */     Konas, Custom, Thlogo, Unknown, minecraft, pic, SlivSRC, Astolfo, MegaCute, Hunger;
/*     */   }
/*     */   
/*     */   public enum smode {
/* 214 */     Custom, Stats;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\RPC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */