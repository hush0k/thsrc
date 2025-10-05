/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class StaffAlert extends Module {
/*  18 */   private static final Pattern validUserPattern = Pattern.compile("^\\w{3,16}$");
/*  19 */   List<String> players = new ArrayList<>();
/*  20 */   List<String> notSpec = new ArrayList<>();
/*  21 */   private final LinkedHashMap<UUID, String> nameMap = new LinkedHashMap<>();
/*     */   public StaffAlert() {
/*  23 */     super("StaffAlert", "StaffAlert", Module.Category.MISC);
/*     */   }
/*     */   
/*     */   public static List<String> getOnlinePlayer() {
/*  27 */     return (List<String>)mc.field_71439_g.field_71174_a.func_175106_d().stream()
/*  28 */       .map(NetworkPlayerInfo::func_178845_a)
/*  29 */       .map(GameProfile::getName)
/*  30 */       .filter(profileName -> validUserPattern.matcher(profileName).matches())
/*  31 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<String> getOnlinePlayerD() {
/*  36 */     List<String> S = new ArrayList<>();
/*  37 */     for (NetworkPlayerInfo player : mc.field_71439_g.field_71174_a.func_175106_d()) {
/*  38 */       if (mc.func_71356_B() || player.func_178850_i() == null)
/*  39 */         break;  String prefix = player.func_178850_i().func_96668_e();
/*     */       
/*  41 */       if (check(ChatFormatting.stripFormatting(prefix).toLowerCase()) || player
/*     */         
/*  43 */         .func_178845_a().getName().toLowerCase().contains("1danil_mansoru1") || player.func_178850_i().func_96668_e().contains("YT")) {
/*  44 */         String name = Arrays.<Object>asList(player.func_178850_i().func_96670_d().stream().toArray()).toString().replace("[", "").replace("]", "");
/*     */         
/*  46 */         if (player.func_178848_b() == GameType.SPECTATOR) {
/*  47 */           S.add(player.func_178850_i().func_96668_e() + name + ":gm3");
/*     */           continue;
/*     */         } 
/*  50 */         S.add(player.func_178850_i().func_96668_e() + name + ":active");
/*     */       } 
/*     */     } 
/*  53 */     return S;
/*     */   }
/*     */   
/*     */   public static boolean check(String name) {
/*  57 */     return (name.contains("helper") || name.contains("moder") || name.contains("admin") || name.contains("owner") || name.contains("curator") || name.contains("������") || name.contains("�����") || name.contains("�����") || name.contains("�������"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  62 */     this.nameMap.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  67 */     if (mc.field_71439_g.field_70173_aa % 10 == 0) {
/*  68 */       this.players = getVanish();
/*  69 */       this.notSpec = getOnlinePlayerD();
/*  70 */       this.players.sort(String::compareTo);
/*  71 */       this.notSpec.sort(String::compareTo);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent er) {
/*  77 */     if (this.players.isEmpty() && this.notSpec.isEmpty())
/*  78 */       return;  List<String> all = new ArrayList<>();
/*  79 */     all.addAll(this.players);
/*  80 */     all.addAll(this.notSpec);
/*     */ 
/*     */     
/*  83 */     int staffY = 11;
/*  84 */     for (String player : all) {
/*  85 */       String a = player.split(":")[1].equalsIgnoreCase("vanish") ? (ChatFormatting.RED + "VANISH") : (player.split(":")[1].equalsIgnoreCase("gm3") ? (ChatFormatting.RED + "VANISH " + ChatFormatting.YELLOW + "(GM 3)") : (ChatFormatting.GREEN + "ACTIVE"));
/*  86 */       FontRender.drawString6(player.split(":")[0] + " " + a, 13.0F, (204 + staffY), -1, false);
/*  87 */       staffY += 13;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getVanish() {
/*  92 */     List<String> list = new ArrayList<>();
/*  93 */     for (ScorePlayerTeam s : mc.field_71441_e.func_96441_U().func_96525_g()) {
/*  94 */       if (s.func_96668_e().length() == 0 || mc.func_71356_B())
/*  95 */         continue;  String name = Arrays.<Object>asList(s.func_96670_d().stream().toArray()).toString().replace("[", "").replace("]", "");
/*     */       
/*  97 */       if (getOnlinePlayer().contains(name) || name.isEmpty()) {
/*     */         continue;
/*     */       }
/*     */       
/* 101 */       if (check(s.func_96668_e().toLowerCase()) || name.toLowerCase().contains("1danil_mansoru1") || s.func_96668_e().contains("YT"))
/* 102 */         list.add(s.func_96668_e() + name + ":vanish"); 
/*     */     } 
/* 104 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\StaffAlert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */