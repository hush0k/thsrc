/*     */ package com.mrzak34.thunderhack.modules.funnygame;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class AntiTittle
/*     */   extends Module
/*     */ {
/*  20 */   public Setting<Boolean> tittle = register(new Setting("AntiTitle", Boolean.valueOf(true)));
/*  21 */   public Setting<Boolean> armorstands = register(new Setting("AntiSpawnLag", Boolean.valueOf(true)));
/*  22 */   public Setting<Boolean> scoreBoard = register(new Setting("ScoreBoard", Boolean.valueOf(true)));
/*  23 */   public Setting<Integer> waterMarkZ1 = register(new Setting("Y", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(524)));
/*  24 */   public Setting<Integer> waterMarkZ2 = register(new Setting("X", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(862)));
/*  25 */   public Setting<Boolean> counter = register(new Setting("Counter", Boolean.valueOf(false)));
/*  26 */   public Setting<Boolean> chat = register(new Setting("ChatAds", Boolean.valueOf(true)));
/*  27 */   public Setting<Boolean> donators = register(new Setting("Donators", Boolean.valueOf(true)));
/*  28 */   int count = 0;
/*  29 */   int y1 = 0;
/*  30 */   int x1 = 0;
/*  31 */   ScaledResolution sr = new ScaledResolution(mc);
/*     */   public AntiTittle() {
/*  33 */     super("Adblock", "Адблок для ебучего-фанигейма", Module.Category.FUNNYGAME);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/*  38 */     if (((Boolean)this.counter.getValue()).booleanValue()) {
/*  39 */       this.y1 = (int)(this.sr.func_78328_b() / 1000.0F / ((Integer)this.waterMarkZ1.getValue()).intValue());
/*  40 */       this.x1 = (int)(this.sr.func_78326_a() / 1000.0F / ((Integer)this.waterMarkZ2.getValue()).intValue());
/*     */       
/*  42 */       RenderUtil.drawSmoothRect(((Integer)this.waterMarkZ2.getValue()).intValue(), ((Integer)this.waterMarkZ1.getValue()).intValue(), (75 + ((Integer)this.waterMarkZ2.getValue()).intValue()), (11 + ((Integer)this.waterMarkZ1.getValue()).intValue()), (new Color(35, 35, 40, 230)).getRGB());
/*  43 */       Util.fr.func_175063_a("Ads Blocked : ", (((Integer)this.waterMarkZ2.getValue()).intValue() + 3), (((Integer)this.waterMarkZ1.getValue()).intValue() + 1), PaletteHelper.astolfo(false, 1).getRGB());
/*  44 */       Util.fr.func_175063_a(String.valueOf(this.count), (((Integer)this.waterMarkZ2.getValue()).intValue() + 6 + Util.fr.func_78256_a("Ads Blocked : ")), (((Integer)this.waterMarkZ1.getValue()).intValue() + 1), -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  51 */     if (((Boolean)this.tittle.getValue()).booleanValue() && 
/*  52 */       e.getPacket() instanceof net.minecraft.network.play.server.SPacketTitle) {
/*  53 */       this.count++;
/*  54 */       e.setCanceled(true);
/*     */     } 
/*     */     
/*  57 */     if (((Boolean)this.chat.getValue()).booleanValue() && e.getPacket() instanceof SPacketChat) {
/*  58 */       SPacketChat packet = (SPacketChat)e.getPacket();
/*  59 */       if (shouldCancel(packet.func_148915_c().func_150254_d())) {
/*  60 */         e.setCanceled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldCancel(String message) {
/*  66 */     if (message.contains("Все очистится через"))
/*  67 */       return true; 
/*  68 */     if (message.contains("Предметы на карте успешно"))
/*  69 */       return true; 
/*  70 */     if (message.contains("Обычный чат работает на"))
/*  71 */       return true; 
/*  72 */     if (message.contains("Хочешь выделиться на сервере?"))
/*  73 */       return true; 
/*  74 */     if (message.contains("Успей использовать промо-код"))
/*  75 */       return true; 
/*  76 */     if (message.contains("В данный момент действуют большие"))
/*  77 */       return true; 
/*  78 */     if (message.contains("есть любые способы оплаты"))
/*  79 */       return true; 
/*  80 */     if (message.contains("Открыть купленные ключи"))
/*  81 */       return true; 
/*  82 */     if (message.contains("Группа сервера ВКонтакте"))
/*  83 */       return true; 
/*  84 */     if (message.contains("чем больше ключей вы покупаете"))
/*  85 */       return true; 
/*  86 */     if (message.contains("Не хватает денег на привилегию"))
/*  87 */       return true; 
/*  88 */     if (message.contains("Продавать что-либо за реальную валюту"))
/*  89 */       return true; 
/*  90 */     if (message.contains("Сейчас действуют большие скидки"))
/*  91 */       return true; 
/*  92 */     if (message.contains("/donate"))
/*  93 */       return true; 
/*  94 */     if (message.contains("Чтобы избежать взлома"))
/*  95 */       return true; 
/*  96 */     if (message.contains("Оскорбление администрации строго"))
/*  97 */       return true; 
/*  98 */     if (message.contains("Включить пвп в своем регионе"))
/*  99 */       return true; 
/* 100 */     if (message.contains("/trade"))
/* 101 */       return true; 
/* 102 */     if (message.contains("После вайпа остается пароль+привилегия"))
/* 103 */       return true; 
/* 104 */     if (message.contains("FunnyGame.su")) {
/* 105 */       return true;
/*     */     }
/* 107 */     if (((Boolean)this.donators.getValue()).booleanValue()) {
/* 108 */       String premessage = message;
/* 109 */       message = message.replace("§r§6§l[§r§b§lПРЕЗИДЕНТ§r§6§l]§r", "§r");
/* 110 */       message = message.replace("§r§d§l[§r§5§lАдмин§r§d§l]§r", "§r");
/* 111 */       message = message.replace("§r§b§l[§r§3§lГл.Админ§r§b§l]§r", "§r");
/* 112 */       message = message.replace("§8[§r§6Игрок§r§8]§r", "§r");
/* 113 */       message = message.replace("§r§5§l[§r§e§lБОГ§r§5§l]§r", "§r");
/* 114 */       message = message.replace("§r§a§l[§r§2§lКреатив§r§a§l]", "§r");
/* 115 */       message = message.replace("§r§4§l[§r§c§lВладелец§r§4§l]", "§r");
/* 116 */       message = message.replace("§r§5§l[§r§d§lОснователь§r§5§l]", "§r");
/* 117 */       message = message.replace("§r§b§l[§r§e§l?§r§d§lСПОНСОР§r§e§l?§r§b§l]", "§r");
/* 118 */       message = message.replace("§r§6§l[§r§e§lЛорд§r§6§l]", "§r");
/* 119 */       message = message.replace("§r§4§l[§r§2§lВЛАДЫКА§r§4§l]", "§r");
/* 120 */       if (!message.equals(premessage)) {
/* 121 */         Command.sendMessageWithoutTH(message);
/* 122 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AntiTittle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */