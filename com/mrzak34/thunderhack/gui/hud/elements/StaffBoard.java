/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.command.commands.StaffCommand;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaffBoard
/*     */   extends HudElement
/*     */ {
/*  33 */   private static final Pattern validUserPattern = Pattern.compile("^\\w{3,16}$");
/*  34 */   public final Setting<ColorSetting> shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528)));
/*  35 */   public final Setting<ColorSetting> color2 = register(new Setting("Color", new ColorSetting(-15724528)));
/*  36 */   public final Setting<ColorSetting> color3 = register(new Setting("Color2", new ColorSetting(-979657829)));
/*  37 */   public final Setting<ColorSetting> textColor = register(new Setting("TextColor", new ColorSetting(12500670)));
/*  38 */   private final Setting<Float> psize = register(new Setting("Size", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/*  39 */   List<String> players = new ArrayList<>();
/*  40 */   List<String> notSpec = new ArrayList<>();
/*  41 */   private final LinkedHashMap<UUID, String> nameMap = new LinkedHashMap<>();
/*     */ 
/*     */   
/*     */   public StaffBoard() {
/*  45 */     super("StaffBoard", "StaffBoard", 50, 50);
/*     */   }
/*     */   
/*     */   public static void size(double width, double height, double animation) {
/*  49 */     GL11.glTranslated(width, height, 0.0D);
/*  50 */     GL11.glScaled(animation, animation, 1.0D);
/*  51 */     GL11.glTranslated(-width, -height, 0.0D);
/*     */   }
/*     */   
/*     */   public static List<String> getOnlinePlayer() {
/*  55 */     return (List<String>)mc.field_71439_g.field_71174_a.func_175106_d().stream()
/*  56 */       .map(NetworkPlayerInfo::func_178845_a)
/*  57 */       .map(GameProfile::getName)
/*  58 */       .filter(profileName -> validUserPattern.matcher(profileName).matches())
/*  59 */       .collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   public static List<String> getOnlinePlayerD() {
/*  63 */     List<String> S = new ArrayList<>();
/*  64 */     for (NetworkPlayerInfo player : mc.field_71439_g.field_71174_a.func_175106_d()) {
/*  65 */       if (mc.func_71356_B() || player.func_178850_i() == null)
/*  66 */         break;  String prefix = player.func_178850_i().func_96668_e();
/*  67 */       if (check(ChatFormatting.stripFormatting(prefix).toLowerCase()) || StaffCommand.staffNames
/*  68 */         .toString().toLowerCase().contains(player.func_178845_a().getName().toLowerCase()) || player
/*  69 */         .func_178845_a().getName().toLowerCase().contains("1danil_mansoru1") || player.func_178850_i().func_96668_e().contains("YT") || (player
/*  70 */         .func_178850_i().func_96668_e().contains("Y") && player.func_178850_i().func_96668_e().contains("T"))) {
/*  71 */         String name = Arrays.<Object>asList(player.func_178850_i().func_96670_d().stream().toArray()).toString().replace("[", "").replace("]", "");
/*     */         
/*  73 */         if (player.func_178848_b() == GameType.SPECTATOR) {
/*  74 */           S.add(player.func_178850_i().func_96668_e() + name + ":gm3");
/*     */           continue;
/*     */         } 
/*  77 */         S.add(player.func_178850_i().func_96668_e() + name + ":active");
/*     */       } 
/*     */     } 
/*  80 */     return S;
/*     */   }
/*     */   
/*     */   public List<String> getVanish() {
/*  84 */     List<String> list = new ArrayList<>();
/*  85 */     for (ScorePlayerTeam s : mc.field_71441_e.func_96441_U().func_96525_g()) {
/*  86 */       if (s.func_96668_e().length() == 0 || mc.func_71356_B())
/*  87 */         continue;  String name = Arrays.<Object>asList(s.func_96670_d().stream().toArray()).toString().replace("[", "").replace("]", "");
/*     */       
/*  89 */       if (getOnlinePlayer().contains(name) || name.isEmpty())
/*     */         continue; 
/*  91 */       if ((StaffCommand.staffNames.toString().toLowerCase().contains(name.toLowerCase()) && 
/*  92 */         check(s.func_96668_e().toLowerCase())) || 
/*  93 */         check(s.func_96668_e().toLowerCase()) || name
/*  94 */         .toLowerCase().contains("1danil_mansoru1") || s
/*  95 */         .func_96668_e().contains("YT") || (s
/*  96 */         .func_96668_e().contains("Y") && s.func_96668_e().contains("T")))
/*     */       {
/*  98 */         list.add(s.func_96668_e() + name + ":vanish"); } 
/*     */     } 
/* 100 */     return list;
/*     */   }
/*     */   
/*     */   public static boolean check(String name) {
/* 104 */     return (name.contains("helper") || name.contains("moder") || name.contains("admin") || name.contains("owner") || name.contains("curator") || name.contains("куратор") || name.contains("модер") || name.contains("админ") || name.contains("хелпер"));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/* 110 */     super.onRender2D(e);
/* 111 */     int y_offset1 = 11;
/* 112 */     List<String> all = new ArrayList<>();
/* 113 */     all.addAll(this.players);
/* 114 */     all.addAll(this.notSpec);
/* 115 */     float scale_x = 50.0F;
/* 116 */     for (String player : all) {
/* 117 */       if (player != null) {
/* 118 */         String a = player.split(":")[0] + " " + (player.split(":")[1].equalsIgnoreCase("vanish") ? (ChatFormatting.RED + "VANISH") : (player.split(":")[1].equalsIgnoreCase("gm3") ? (ChatFormatting.RED + "VANISH " + ChatFormatting.YELLOW + "(NEAR!)") : (ChatFormatting.GREEN + "ACTIVE")));
/* 119 */         if (FontRender.getStringWidth6(a) > scale_x) {
/* 120 */           scale_x = FontRender.getStringWidth6(a);
/*     */         }
/*     */       } 
/* 123 */       y_offset1 += 13;
/*     */     } 
/*     */ 
/*     */     
/* 127 */     GlStateManager.func_179094_E();
/* 128 */     size((getPosX() + 50.0F), (getPosY() + (20 + y_offset1) / 2.0F), ((Float)this.psize.getValue()).floatValue());
/*     */     
/* 130 */     RenderUtil.drawBlurredShadow(getPosX(), getPosY(), scale_x + 20.0F, (20 + y_offset1), 20, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/*     */ 
/*     */     
/* 133 */     RoundedShader.drawRound(getPosX(), getPosY(), scale_x + 20.0F, (20 + y_offset1), 7.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/* 134 */     FontRender.drawCentString6("StaffBoard", getPosX() + (scale_x + 20.0F) / 2.0F, getPosY() + 5.0F, ((ColorSetting)this.textColor.getValue()).getColor());
/* 135 */     RoundedShader.drawRound(getPosX() + 2.0F, getPosY() + 13.0F, scale_x + 16.0F, 1.0F, 0.5F, ((ColorSetting)this.color3.getValue()).getColorObject());
/*     */ 
/*     */     
/* 138 */     int y_offset = 11;
/* 139 */     for (String player : all) {
/* 140 */       GlStateManager.func_179094_E();
/* 141 */       GlStateManager.func_179117_G();
/* 142 */       String a = player.split(":")[0] + " " + (player.split(":")[1].equalsIgnoreCase("vanish") ? (ChatFormatting.RED + "VANISH") : (player.split(":")[1].equalsIgnoreCase("gm3") ? (ChatFormatting.RED + "VANISH " + ChatFormatting.YELLOW + "(NEAR!)") : (ChatFormatting.GREEN + "ACTIVE")));
/* 143 */       FontRender.drawString6(a, getPosX() + 5.0F, getPosY() + 18.0F + y_offset, -1, false);
/* 144 */       GlStateManager.func_179117_G();
/* 145 */       GlStateManager.func_179121_F();
/* 146 */       y_offset += 13;
/*     */     } 
/* 148 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 154 */     this.nameMap.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 159 */     if (mc.field_71439_g.field_70173_aa % 10 == 0) {
/* 160 */       this.players = getVanish();
/* 161 */       this.notSpec = getOnlinePlayerD();
/* 162 */       this.players.sort(String::compareTo);
/* 163 */       this.notSpec.sort(String::compareTo);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\StaffBoard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */