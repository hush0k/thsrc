/*     */ package com.mrzak34.thunderhack.modules.funnygame;
/*     */ 
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.DiscordWebhook;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.ThunderUtils;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.text.ChatType;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ public class ClanInvite
/*     */   extends Module
/*     */ {
/*  30 */   private static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new PlayerComparator());
/*  31 */   public Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(30)));
/*  32 */   public Setting<Mode> b = register(new Setting("Donate", Mode.Creativ));
/*  33 */   Timer timer = new Timer();
/*  34 */   ArrayList<String> playersNames = new ArrayList<>();
/*  35 */   int aboba = 0;
/*     */   
/*     */   public ClanInvite() {
/*  38 */     super("ClanInvite", "Автоматически приглашает-в клан", Module.Category.FUNNYGAME);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  43 */     if (this.timer.passedS(((Integer)this.delay.getValue()).intValue())) {
/*  44 */       NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
/*  45 */       List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.func_175106_d());
/*  46 */       for (NetworkPlayerInfo networkplayerinfo : list) {
/*  47 */         if (resolveDonate(getPlayerName(networkplayerinfo)) < resolveMode()) {
/*     */           continue;
/*     */         }
/*  50 */         this.playersNames.add(getPlayerName(networkplayerinfo));
/*     */       } 
/*  52 */       if (this.playersNames.size() > 1) {
/*  53 */         int randomName = (int)Math.floor(Math.random() * this.playersNames.size());
/*  54 */         mc.field_71439_g.func_71165_d("/c invite " + (String)this.playersNames.get(randomName));
/*  55 */         this.playersNames.clear();
/*  56 */         this.timer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getPlayerName2(NetworkPlayerInfo networkPlayerInfoIn) {
/*  62 */     return (networkPlayerInfoIn.func_178854_k() != null) ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName());
/*     */   }
/*     */   
/*     */   public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
/*  66 */     return networkPlayerInfoIn.func_178845_a().getName();
/*     */   }
/*     */   
/*     */   public int resolveMode() {
/*  70 */     return ((Mode)this.b.getValue()).ordinal();
/*     */   }
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
/*     */   
/*     */   public int resolveDonate(String nick) {
/*  98 */     String donate = "null";
/*  99 */     this; NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
/* 100 */     List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.func_175106_d());
/*     */     
/* 102 */     for (NetworkPlayerInfo networkplayerinfo : list) {
/* 103 */       if (getPlayerName2(networkplayerinfo).contains(nick)) {
/* 104 */         String raw = getPlayerName2(networkplayerinfo);
/* 105 */         if (raw.contains("Вип")) {
/* 106 */           return 1;
/*     */         }
/* 108 */         if (raw.contains("Премиум")) {
/* 109 */           return 2;
/*     */         }
/* 111 */         if (raw.contains("Креатив")) {
/* 112 */           return 3;
/*     */         }
/* 114 */         if (raw.contains("Админ")) {
/* 115 */           return 4;
/*     */         }
/* 117 */         if (raw.contains("Лорд")) {
/* 118 */           return 5;
/*     */         }
/* 120 */         if (raw.contains("Гл.Админ")) {
/* 121 */           return 6;
/*     */         }
/* 123 */         if (raw.contains("Создатель")) {
/* 124 */           return 7;
/*     */         }
/* 126 */         if (raw.contains("Основатель")) {
/* 127 */           return 8;
/*     */         }
/* 129 */         if (raw.contains("Владелец")) {
/* 130 */           return 9;
/*     */         }
/* 132 */         if (raw.contains("Цезарь")) {
/* 133 */           return 10;
/*     */         }
/* 135 */         if (raw.contains("Президент")) {
/* 136 */           return 11;
/*     */         }
/* 138 */         if (raw.contains("БОГ")) {
/* 139 */           return 12;
/*     */         }
/* 141 */         if (raw.contains("Властелин")) {
/* 142 */           return 13;
/*     */         }
/* 144 */         if (raw.contains("ПРАВИТЕЛЬ")) {
/* 145 */           return 14;
/*     */         }
/* 147 */         if (raw.contains("БАРОН")) {
/* 148 */           return 15;
/*     */         }
/* 150 */         if (raw.contains("Владыка")) {
/* 151 */           return 16;
/*     */         }
/* 153 */         if (raw.contains("Султан")) {
/* 154 */           return 17;
/*     */         }
/* 156 */         if (raw.contains("МАЖОР")) {
/* 157 */           return 18;
/*     */         }
/* 159 */         if (raw.contains("ГОСПОДЬ")) {
/* 160 */           return 19;
/*     */         }
/* 162 */         if (raw.contains("СПОНСОР")) {
/* 163 */           return 20;
/*     */         }
/*     */       } 
/*     */     } 
/* 167 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean helper(String nick) {
/* 171 */     this; NetHandlerPlayClient nethandlerplayclient = mc.field_71439_g.field_71174_a;
/* 172 */     List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.func_175106_d());
/* 173 */     for (NetworkPlayerInfo networkplayerinfo : list) {
/* 174 */       if (getPlayerName2(networkplayerinfo).contains(nick)) {
/* 175 */         String raw = getPlayerName2(networkplayerinfo);
/* 176 */         if (check(raw.toLowerCase())) {
/* 177 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 181 */     return false;
/*     */   }
/*     */   
/*     */   public boolean check(String name) {
/* 185 */     return (name.contains("helper") || name.contains("moder") || name.contains("бог") || name.contains("admin") || name.contains("owner") || name.contains("curator") || name.contains("хелпер") || name.contains("модер") || name.contains("админ") || name.contains("куратор"));
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/* 190 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 193 */     if (!((DiscordWebhook)Thunderhack.moduleManager.getModuleByClass(DiscordWebhook.class)).isEnabled()) {
/*     */       return;
/*     */     }
/* 196 */     if (e.getPacket() instanceof SPacketChat) {
/* 197 */       SPacketChat packet = (SPacketChat)e.getPacket();
/* 198 */       if (packet.func_192590_c() != ChatType.GAME_INFO)
/*     */       {
/* 200 */         if (packet.func_148915_c().func_150254_d().contains("принял ваше")) {
/* 201 */           this.aboba++;
/* 202 */           String finalmsg = "```Игрок " + ThunderUtils.solvename(packet.func_148915_c().func_150254_d()) + " принял приглашение в клан!\nПриглашено за сегодня " + this.aboba + "```";
/* 203 */           DiscordWebhook.sendMsg(finalmsg, DiscordWebhook.readurl());
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum Mode {
/* 210 */     ALL, Vip, Premium, Creativ, Admin, Lord, glAdmin, Sozdatel, Osnovatel, Vladelec, Cesar, President, Bog, Vlastelin, Pravitel, Baron, Vladika, Sultan, Major, Gospod, Sponsor;
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   static class PlayerComparator
/*     */     implements Comparator<NetworkPlayerInfo> {
/*     */     private PlayerComparator() {}
/*     */     
/*     */     public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
/* 219 */       ScorePlayerTeam scoreplayerteam = p_compare_1_.func_178850_i();
/* 220 */       ScorePlayerTeam scoreplayerteam1 = p_compare_2_.func_178850_i();
/* 221 */       return ComparisonChain.start().compareTrueFirst((p_compare_1_.func_178848_b() != GameType.SPECTATOR), (p_compare_2_.func_178848_b() != GameType.SPECTATOR)).compare((scoreplayerteam != null) ? scoreplayerteam.func_96661_b() : "", (scoreplayerteam1 != null) ? scoreplayerteam1.func_96661_b() : "").compare(p_compare_1_.func_178845_a().getName(), p_compare_2_.func_178845_a().getName()).result();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\ClanInvite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */