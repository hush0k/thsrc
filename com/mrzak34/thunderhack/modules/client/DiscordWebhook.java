/*     */ package com.mrzak34.thunderhack.modules.client;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.ConnectToServerEvent;
/*     */ import com.mrzak34.thunderhack.events.TotemPopEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.AutoPot;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Base64;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.gui.GuiChat;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.client.event.ScreenshotEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.apache.commons.lang3.RandomUtils;
/*     */ 
/*     */ public class DiscordWebhook extends Module {
/*  39 */   public Setting<Boolean> ochat = register(new Setting("OpenChat", Boolean.valueOf(false))); private static final String CLIENT_ID = "efce6070269a7f1";
/*  40 */   public Setting<Boolean> sendToDiscord = register(new Setting("SendToDiscord", Boolean.valueOf(true)));
/*  41 */   public Setting<Boolean> SDescr = register(new Setting("ScreenDescription", Boolean.valueOf(true)));
/*     */   
/*     */   public ByteArrayOutputStream byteArrayOutputStream;
/*  44 */   int killz = 0;
/*     */ 
/*     */   
/*     */   public DiscordWebhook() {
/*  48 */     super("DiscordWebhook", "DiscordWebhook", Module.Category.CLIENT);
/*     */   }
/*     */   
/*     */   public static String readurl() {
/*     */     try {
/*  53 */       File file = new File("ThunderHack/misc/WHOOK.txt");
/*     */       
/*  55 */       if (file.exists()) {
/*  56 */         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
/*  57 */           if (reader.ready()) {
/*  58 */             return reader.readLine();
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/*  63 */         return "none";
/*     */       } 
/*  65 */     } catch (Exception exception) {}
/*     */     
/*  67 */     return "none";
/*     */   }
/*     */   
/*     */   public static void saveurl(String rat) {
/*  71 */     File file = new File("ThunderHack/misc/WHOOK.txt");
/*     */     try {
/*  73 */       (new File("ThunderHack")).mkdirs();
/*  74 */       file.createNewFile();
/*  75 */       try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
/*  76 */         writer.write(rat + '\n');
/*  77 */       } catch (Exception exception) {}
/*     */     }
/*  79 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendMsg(String message, String webhook) {
/*  84 */     PrintWriter out = null;
/*  85 */     BufferedReader in = null;
/*  86 */     StringBuilder result = new StringBuilder();
/*     */     
/*     */     try {
/*  89 */       URL realUrl = new URL(webhook);
/*  90 */       URLConnection conn = realUrl.openConnection();
/*  91 */       conn.setRequestProperty("accept", "*/*");
/*  92 */       conn.setRequestProperty("connection", "Keep-Alive");
/*  93 */       conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
/*  94 */       conn.setDoOutput(true);
/*  95 */       conn.setDoInput(true);
/*  96 */       out = new PrintWriter(conn.getOutputStream());
/*  97 */       String postData = URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
/*  98 */       out.print(postData);
/*  99 */       out.flush();
/* 100 */       in = new BufferedReader(new InputStreamReader(conn.getInputStream())); String line;
/* 101 */       while ((line = in.readLine()) != null) {
/* 102 */         result.append("/n").append(line);
/*     */       }
/* 104 */     } catch (Exception e) {
/* 105 */       e.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 108 */         if (out != null) {
/* 109 */           out.close();
/*     */         }
/* 111 */         if (in != null) {
/* 112 */           in.close();
/*     */         }
/* 114 */       } catch (IOException ex) {
/* 115 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/* 118 */     System.out.println(result);
/*     */   }
/*     */   
/*     */   public static void sendAuraMsg(EntityPlayer name, int killz) {
/* 122 */     int caps = AutoPot.neededCap;
/* 123 */     (new Thread(() -> sendMsg("```" + mc.field_71439_g.func_70005_c_() + getAuraWord() + name.func_70005_c_() + " c помощью " + mc.field_71439_g.func_184614_ca().func_82833_r() + "\nУбийств за сегодня: " + killz + "\nКаппучино потребовалось: " + caps + "```", readurl())))
/*     */ 
/*     */ 
/*     */       
/* 127 */       .start();
/* 128 */     AutoPot.neededCap = 0;
/*     */   }
/*     */   
/*     */   public static String getAuraWord() {
/* 132 */     int n2 = RandomUtils.nextInt(0, 5);
/* 133 */     switch (n2) {
/*     */       case 0:
/* 135 */         return " убил ";
/*     */       
/*     */       case 1:
/* 138 */         return " попустил ";
/*     */       
/*     */       case 2:
/* 141 */         return " кильнул ";
/*     */       
/*     */       case 3:
/* 144 */         return " дропнул ";
/*     */       
/*     */       case 4:
/* 147 */         return " отымел ";
/*     */       
/*     */       case 5:
/* 150 */         return " пропенил ";
/*     */     } 
/*     */ 
/*     */     
/* 154 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 160 */     Command.sendMessage("Использовние: ");
/* 161 */     Command.sendMessage("1. Введи ссылку на вебхук (со своего дискорд сервера) с помощью команды .whook <ссылка> ");
/* 162 */     Command.sendMessage("2. Выбери действия в настройках модуля (по умолчанию отправляет скрины, килы ауры, килы лука, заходы на сервера) ");
/*     */ 
/*     */     
/* 165 */     Command.sendMessage("Using: ");
/* 166 */     Command.sendMessage("1. Enter the link to the webhook (from your discord server) using the command .whook <link>");
/* 167 */     Command.sendMessage("2. Choose actions in the module settings (by default sends screenshots, aura kills, bow kills, server joins)");
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onScreenshotEvent(ScreenshotEvent screenshotEvent) {
/* 172 */     Command.sendMessage("SS Getted!");
/* 173 */     this.byteArrayOutputStream = new ByteArrayOutputStream();
/* 174 */     uploadToImgur(screenshotEvent.getImage());
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConnectionEvent(ConnectToServerEvent e) {
/* 179 */     (new Thread(() -> {
/*     */           try {
/*     */             TimeUnit.MILLISECONDS.sleep(6000L);
/* 182 */           } catch (InterruptedException var2) {
/*     */             var2.printStackTrace();
/*     */           } 
/*     */           String msg = "```" + mc.field_71439_g.func_70005_c_() + " зашёл на сервер " + e.getIp() + "```";
/*     */           sendMsg(msg, readurl());
/* 187 */         })).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 192 */     if (Aura.target != null && 
/* 193 */       Aura.target.func_110143_aJ() <= 0.0F && 
/* 194 */       Aura.target instanceof EntityPlayer) {
/* 195 */       this.killz++;
/* 196 */       sendAuraMsg((EntityPlayer)Aura.target, this.killz);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void uploadToImgur(BufferedImage bufferedImage) {
/* 203 */     (new Thread(() -> {
/*     */           try {
/*     */             URL uRL = new URL("https://api.imgur.com/3/image");
/*     */             
/*     */             HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
/*     */             httpURLConnection.setDoOutput(true);
/*     */             httpURLConnection.setDoInput(true);
/*     */             httpURLConnection.setRequestMethod("POST");
/*     */             httpURLConnection.setRequestProperty("Authorization", "Client-ID efce6070269a7f1");
/*     */             httpURLConnection.setRequestMethod("POST");
/*     */             httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*     */             httpURLConnection.connect();
/*     */             ImageIO.write(bufferedImage, "png", this.byteArrayOutputStream);
/*     */             this.byteArrayOutputStream.flush();
/*     */             byte[] byArray = this.byteArrayOutputStream.toByteArray();
/*     */             String string2 = Base64.getEncoder().encodeToString(byArray);
/*     */             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
/*     */             String string3 = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(string2, "UTF-8");
/*     */             outputStreamWriter.write(string3);
/*     */             outputStreamWriter.flush();
/*     */             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
/*     */             StringBuilder stringBuilder = new StringBuilder();
/*     */             String string;
/*     */             while ((string = bufferedReader.readLine()) != null) {
/*     */               stringBuilder.append(string).append("\n");
/*     */             }
/*     */             outputStreamWriter.close();
/*     */             bufferedReader.close();
/*     */             JsonObject jsonObject = (new JsonParser()).parse(stringBuilder.toString()).getAsJsonObject();
/*     */             String string4 = jsonObject.get("data").getAsJsonObject().get("link").getAsString();
/*     */             if (((Boolean)this.ochat.getValue()).booleanValue()) {
/*     */               mc.func_147108_a((GuiScreen)new GuiChat(string4));
/*     */             }
/*     */             String ip = "ошибка";
/*     */             try {
/*     */               ip = ((ServerData)Objects.requireNonNull((T)mc.func_147104_D())).field_78845_b;
/* 239 */             } catch (Exception exception) {}
/*     */ 
/*     */             
/*     */             Date date = new Date(System.currentTimeMillis());
/*     */             
/*     */             String description = "```Скрин сделан игроком " + mc.field_71439_g.func_70005_c_() + "\n" + date + "\nна сервере " + ip + "```";
/*     */             
/*     */             if (((Boolean)this.sendToDiscord.getValue()).booleanValue()) {
/*     */               sendMsg(string4, readurl());
/*     */               
/*     */               if (((Boolean)this.SDescr.getValue()).booleanValue()) {
/*     */                 sendMsg(description, readurl());
/*     */               }
/*     */             } 
/* 253 */           } catch (Exception exception) {
/*     */             Command.sendMessage(exception.getMessage());
/*     */           } 
/* 256 */         })).start();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTotemPop(TotemPopEvent e) {
/* 261 */     if (Aura.target == e.getEntity() || C4Aura.target == e.getEntity() || getEntityUnderMouse(100) == e.getEntity()) {
/* 262 */       (new Thread(() -> {
/*     */             String str = "```" + mc.field_71439_g.func_70005_c_() + " " + getWord() + e.getEntity().func_70005_c_() + "```";
/*     */             sendMsg(str, readurl());
/* 265 */           })).start();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getWord() {
/* 270 */     int n2 = RandomUtils.nextInt(0, 3);
/* 271 */     switch (n2) {
/*     */       case 0:
/* 273 */         return " дал тотем ";
/*     */       
/*     */       case 1:
/* 276 */         return " снял тотем ";
/*     */       
/*     */       case 2:
/* 279 */         return " отжал тотем у ";
/*     */       
/*     */       case 3:
/* 282 */         return " попнул ";
/*     */     } 
/*     */     
/* 285 */     return "";
/*     */   }
/*     */   
/*     */   public EntityPlayer getEntityUnderMouse(int range) {
/* 289 */     Entity entity = mc.func_175606_aa();
/* 290 */     if (entity != null) {
/* 291 */       Vec3d pos = mc.field_71439_g.func_174824_e(1.0F); float i;
/* 292 */       for (i = 0.0F; i < range; i += 0.5F) {
/* 293 */         pos = pos.func_178787_e(mc.field_71439_g.func_70040_Z().func_186678_a(0.5D));
/* 294 */         for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/* 295 */           if (player == mc.field_71439_g)
/* 296 */             continue;  AxisAlignedBB bb = player.func_174813_aQ();
/* 297 */           if (bb == null)
/* 298 */             continue;  if (player.func_70032_d((Entity)mc.field_71439_g) > 6.0F) {
/* 299 */             bb = bb.func_186662_g(0.5D);
/*     */           }
/* 301 */           if (bb.func_72318_a(pos)) return player;
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/* 306 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\DiscordWebhook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */