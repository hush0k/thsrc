/*     */ package com.mrzak34.thunderhack.util;
/*     */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ 
/*     */ public class ThunderUtils {
/*  21 */   private static final List<Pair<String, BufferedImage>> userCapes = new ArrayList<>();
/*     */   
/*     */   public static void saveUserAvatar(String s, String nickname) {
/*     */     try {
/*  25 */       URL url = new URL(s);
/*  26 */       URLConnection openConnection = url.openConnection();
/*  27 */       boolean check = true;
/*     */ 
/*     */       
/*     */       try {
/*  31 */         openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
/*  32 */         openConnection.connect();
/*     */         
/*  34 */         if (openConnection.getContentLength() > 8000000) {
/*  35 */           System.out.println(" file size is too big.");
/*  36 */           check = false;
/*     */         } 
/*  38 */       } catch (Exception e) {
/*  39 */         System.out.println("Couldn't create a connection to the link, please recheck the link.");
/*  40 */         check = false;
/*  41 */         e.printStackTrace();
/*     */       } 
/*  43 */       if (check) {
/*  44 */         BufferedImage img = null;
/*     */         try {
/*  46 */           InputStream in = new BufferedInputStream(openConnection.getInputStream());
/*  47 */           ByteArrayOutputStream out = new ByteArrayOutputStream();
/*  48 */           byte[] buf = new byte[1024];
/*  49 */           int n = 0;
/*  50 */           while (-1 != (n = in.read(buf))) {
/*  51 */             out.write(buf, 0, n);
/*     */           }
/*  53 */           out.close();
/*  54 */           in.close();
/*  55 */           byte[] response = out.toByteArray();
/*  56 */           img = ImageIO.read(new ByteArrayInputStream(response));
/*  57 */         } catch (Exception e) {
/*  58 */           System.out.println(" couldn't read an image from this link.");
/*  59 */           e.printStackTrace();
/*     */         } 
/*     */         try {
/*  62 */           ImageIO.write(img, "png", new File("ThunderHack/temp/heads/" + nickname + ".png"));
/*  63 */         } catch (IOException e) {
/*  64 */           System.out.println("Couldn't create/send the output image.");
/*  65 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*  68 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String solvename(String notsolved) {
/*  74 */     AtomicReference<String> mb = new AtomicReference<>("err");
/*  75 */     ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(Util.mc.func_147114_u())).func_175106_d().forEach(player -> {
/*     */           if (notsolved.contains(player.func_178845_a().getName())) {
/*     */             mb.set(player.func_178845_a().getName());
/*     */           }
/*     */         });
/*  80 */     return mb.get();
/*     */   }
/*     */   
/*     */   public static void savePlayerSkin(String s, String nickname) {
/*     */     try {
/*  85 */       URL url = new URL(s);
/*  86 */       URLConnection openConnection = url.openConnection();
/*  87 */       boolean check = true;
/*     */ 
/*     */       
/*     */       try {
/*  91 */         openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
/*  92 */         openConnection.connect();
/*     */         
/*  94 */         if (openConnection.getContentLength() > 8000000) {
/*  95 */           System.out.println(" file size is too big.");
/*  96 */           check = false;
/*     */         } 
/*  98 */       } catch (Exception e) {
/*  99 */         System.out.println("Couldn't create a connection to the link, please recheck the link.");
/* 100 */         check = false;
/* 101 */         e.printStackTrace();
/*     */       } 
/* 103 */       if (check) {
/* 104 */         BufferedImage img = null;
/*     */         try {
/* 106 */           InputStream in = new BufferedInputStream(openConnection.getInputStream());
/* 107 */           ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 108 */           byte[] buf = new byte[1024];
/* 109 */           int n = 0;
/* 110 */           while (-1 != (n = in.read(buf))) {
/* 111 */             out.write(buf, 0, n);
/*     */           }
/* 113 */           out.close();
/* 114 */           in.close();
/* 115 */           byte[] response = out.toByteArray();
/* 116 */           img = ImageIO.read(new ByteArrayInputStream(response));
/* 117 */         } catch (Exception e) {
/* 118 */           System.out.println(" couldn't read an image from this link.");
/* 119 */           e.printStackTrace();
/*     */         } 
/*     */         try {
/* 122 */           ImageIO.write(img, "png", new File("ThunderHack/temp/skins/" + nickname + ".png"));
/* 123 */         } catch (IOException e) {
/* 124 */           System.out.println("Couldn't create/send the output image.");
/* 125 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/* 128 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage getCustomCape(String name) {
/* 134 */     for (Pair<String, BufferedImage> donator : userCapes) {
/* 135 */       if (((String)donator.getKey()).equalsIgnoreCase(name)) {
/* 136 */         return donator.getValue();
/*     */       }
/*     */     } 
/* 139 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isTHUser(String name) {
/* 143 */     for (Pair<String, BufferedImage> donator : userCapes) {
/* 144 */       if (((String)donator.getKey()).equalsIgnoreCase(name)) {
/* 145 */         return true;
/*     */       }
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */   
/*     */   public static void syncCapes() {
/* 152 */     if (!((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).DownloadCapes.getValue()).booleanValue())
/*     */       return; 
/* 154 */     (new Thread(() -> {
/*     */           try {
/*     */             File tmp = new File("ThunderHack" + File.separator + "temp" + File.separator + "capes");
/*     */             if (!tmp.exists()) {
/*     */               tmp.mkdirs();
/*     */             }
/*     */             URL capesList = new URL("https://pastebin.com/raw/TYLWEa2E");
/*     */             BufferedReader in = new BufferedReader(new InputStreamReader(capesList.openStream()));
/*     */             String inputLine;
/*     */             while ((inputLine = in.readLine()) != null) {
/*     */               String colune = inputLine.trim();
/*     */               String name = colune.split(":")[0];
/*     */               String cape = colune.split(":")[1];
/*     */               URL capeUrl = new URL("https://raw.githubusercontent.com/Pan4ur/cape/main/" + cape + ".png");
/*     */               BufferedImage capeImage = ImageIO.read(capeUrl);
/*     */               ImageIO.write(capeImage, "png", new File("ThunderHack/temp/capes/" + name + ".png"));
/*     */               userCapes.add(new Pair<>(name, capeImage));
/*     */             } 
/* 172 */           } catch (Exception exception) {}
/*     */         
/* 174 */         })).start();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\ThunderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */