/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketChatMessage;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.PNGtoResourceLocation;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.network.play.client.CPacketChatMessage;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ChatType;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.event.HoverEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class DiscordEmbeds extends Module {
/*     */   public static boolean nado = false;
/*  39 */   public static float nigw = 0.0F; public static boolean once = false;
/*  40 */   public static float nigh = 0.0F;
/*  41 */   public static Timer timer = new Timer();
/*  42 */   static String lasturl = "";
/*  43 */   static String filename = "";
/*  44 */   static String formatfila = "";
/*  45 */   public Setting<Integer> multip = register(new Setting("Scale", Integer.valueOf(200), Integer.valueOf(50), Integer.valueOf(1280)));
/*  46 */   public Setting<Integer> posY = register(new Setting("PosY", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000)));
/*  47 */   public Setting<Integer> posX = register(new Setting("PosX", Integer.valueOf(0), Integer.valueOf(-1000), Integer.valueOf(1000)));
/*  48 */   public Setting<Boolean> fgbypass = register(new Setting("FunnyGame", Boolean.valueOf(false)));
/*  49 */   public ResourceLocation logo = PNGtoResourceLocation.getTexture(filename, formatfila);
/*  50 */   String discord = "";
/*  51 */   String last = "";
/*  52 */   int xvalue = 0;
/*  53 */   int yvalue = 0;
/*     */   
/*     */   public DiscordEmbeds() {
/*  56 */     super("DiscordEmbeds", "DiscordEmbeds", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String codeFGBypass(String raw) {
/*  61 */     String final_string = raw.replace("https://cdn.discordapp.com/attachments/", "THCRYPT");
/*  62 */     final_string = final_string.replace("0", "а");
/*  63 */     final_string = final_string.replace("1", "б");
/*  64 */     final_string = final_string.replace("2", "в");
/*  65 */     final_string = final_string.replace("3", "г");
/*  66 */     final_string = final_string.replace("4", "д");
/*  67 */     final_string = final_string.replace("5", "е");
/*  68 */     final_string = final_string.replace("6", "ж");
/*  69 */     final_string = final_string.replace("7", "з");
/*  70 */     final_string = final_string.replace("8", "и");
/*  71 */     final_string = final_string.replace("9", "й");
/*  72 */     final_string = final_string.replace("/", "к");
/*  73 */     final_string = final_string.replace(".png", "о");
/*  74 */     final_string = final_string.replace(".", "л");
/*  75 */     final_string = final_string.replace("-", "м");
/*  76 */     final_string = final_string.replace("_", "н");
/*  77 */     return final_string;
/*     */   }
/*     */   
/*     */   public static void saveDickPick(String s, String format) {
/*  81 */     if (Objects.equals(lasturl, s) && nado) {
/*     */       return;
/*     */     }
/*     */     try {
/*  85 */       lasturl = s;
/*  86 */       URL url = new URL(s);
/*  87 */       URLConnection openConnection = url.openConnection();
/*  88 */       boolean check = true;
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
/* 122 */           int niggermod = (int)(Math.random() * 10000.0D);
/* 123 */           ImageIO.write(img, format, new File("ThunderHack/temp/embeds/" + niggermod + "." + format));
/* 124 */           filename = String.valueOf(niggermod);
/* 125 */           formatfila = format;
/* 126 */           once = true;
/* 127 */         } catch (IOException e) {
/* 128 */           System.out.println("Couldn't create/send the output image.");
/* 129 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/* 132 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send e) {
/* 139 */     if (e.getPacket() instanceof CPacketChatMessage) {
/* 140 */       CPacketChatMessage pac = (CPacketChatMessage)e.getPacket();
/* 141 */       if (((ICPacketChatMessage)pac).getMessage().contains("https://cdn.discordapp.com/attachments/") && ((Boolean)this.fgbypass.getValue()).booleanValue()) {
/* 142 */         ((ICPacketChatMessage)pac).setMessage("!" + 
/* 143 */             codeFGBypass(((ICPacketChatMessage)pac).getMessage().replace("!", "")));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String decodeFGBypass(String coded) {
/* 151 */     String final_string = coded.split("THCRYPT")[1];
/* 152 */     final_string = final_string.replace("а", "0");
/* 153 */     final_string = final_string.replace("б", "1");
/* 154 */     final_string = final_string.replace("в", "2");
/* 155 */     final_string = final_string.replace("г", "3");
/* 156 */     final_string = final_string.replace("д", "4");
/* 157 */     final_string = final_string.replace("е", "5");
/* 158 */     final_string = final_string.replace("ж", "6");
/* 159 */     final_string = final_string.replace("з", "7");
/* 160 */     final_string = final_string.replace("и", "8");
/* 161 */     final_string = final_string.replace("й", "9");
/* 162 */     final_string = final_string.replace("к", "/");
/* 163 */     final_string = final_string.replace("о", ".png");
/* 164 */     final_string = final_string.replace("л", ".");
/* 165 */     final_string = final_string.replace("м", "-");
/* 166 */     final_string = final_string.replace("н", "_");
/* 167 */     Command.sendMessage("https://cdn.discordapp.com/attachments/" + final_string);
/* 168 */     return "https://cdn.discordapp.com/attachments/" + final_string;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 173 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 176 */     if (event.getPacket() instanceof SPacketChat) {
/* 177 */       SPacketChat packet = (SPacketChat)event.getPacket();
/* 178 */       if (packet.func_192590_c() == ChatType.GAME_INFO || check(packet.func_148915_c().func_150254_d()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean check(String message) {
/* 185 */     if (message.contains("THCRYPT")) {
/* 186 */       check(decodeFGBypass(message));
/*     */     }
/*     */     
/* 189 */     if (message.contains("discordapp") && message.contains(".png")) {
/* 190 */       this.discord = message;
/*     */       try {
/* 192 */         String[] splitted = this.discord.split("https://");
/* 193 */         String url = "https://" + splitted[1];
/* 194 */         String[] splitted1 = url.split(".png");
/* 195 */         this.last = splitted1[0] + ".png";
/* 196 */         TextComponentString textComponentString = new TextComponentString(this.last);
/*     */         
/* 198 */         if (Objects.equals(solvename(message), "err")) {
/* 199 */           TextComponentString textComponentString1 = new TextComponentString("Получена картинка через THCRYPT [ПОКАЗАТЬ]");
/* 200 */           textComponentString1.func_150255_a(textComponentString1.func_150256_b().func_150238_a(TextFormatting.AQUA).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)textComponentString)));
/* 201 */           Command.sendIText((ITextComponent)textComponentString1);
/*     */         } else {
/* 203 */           TextComponentString textComponentString1 = new TextComponentString("<" + solvename(message) + "> Отправил картинку [Show Discord Image]");
/* 204 */           textComponentString1.func_150255_a(textComponentString1.func_150256_b().func_150238_a(TextFormatting.AQUA).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)textComponentString)));
/* 205 */           Command.sendIText((ITextComponent)textComponentString1);
/*     */         }
/*     */       
/* 208 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 211 */     if (message.contains("discordapp") && message.contains(".jpg")) {
/* 212 */       this.discord = message;
/*     */       try {
/* 214 */         String[] splitted = this.discord.split("https://");
/* 215 */         String url = "https://" + splitted[1];
/* 216 */         String[] splitted1 = url.split(".jpg");
/* 217 */         this.last = splitted1[0] + ".jpg";
/* 218 */         TextComponentString textComponentString1 = new TextComponentString(this.last);
/* 219 */         TextComponentString textComponentString2 = new TextComponentString("<" + solvename(message) + "> Отправил картинку [Show Discord Image]");
/* 220 */         textComponentString2.func_150255_a(textComponentString2.func_150256_b().func_150238_a(TextFormatting.AQUA).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)textComponentString1)));
/* 221 */         Command.sendIText((ITextComponent)textComponentString2);
/* 222 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 225 */     return true;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/* 230 */     GuiScreen guiscreen = mc.field_71462_r;
/* 231 */     if (nado && guiscreen instanceof net.minecraft.client.gui.GuiChat && !Objects.equals(filename, "") && !Objects.equals(formatfila, "")) {
/* 232 */       if (once) {
/* 233 */         this.logo = PNGtoResourceLocation.getTexture(filename, formatfila);
/* 234 */         once = false;
/*     */       } 
/*     */       
/* 237 */       if (this.logo != null) {
/* 238 */         Util.mc.func_110434_K().func_110577_a(this.logo);
/* 239 */         ElytraSwap.drawCompleteImage(this.xvalue - nigw / 2.0F + ((Integer)this.posX.getValue()).intValue(), this.yvalue - nigh / 2.0F + ((Integer)this.posY.getValue()).intValue(), (int)nigw, (int)nigh);
/*     */       } 
/*     */     } 
/* 242 */     if (!nado) {
/* 243 */       this.logo = null;
/*     */     }
/* 245 */     if (timer.passedMs(500L)) {
/* 246 */       nado = false;
/*     */     }
/*     */ 
/*     */     
/* 250 */     ScaledResolution sr = new ScaledResolution(mc);
/*     */     
/* 252 */     this.xvalue = Mouse.getX() / 2;
/* 253 */     this.yvalue = (sr.func_78328_b() - Mouse.getY()) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String solvename(String notsolved) {
/* 258 */     AtomicReference<String> mb = new AtomicReference<>("err");
/* 259 */     ((NetHandlerPlayClient)Objects.<NetHandlerPlayClient>requireNonNull(Util.mc.func_147114_u())).func_175106_d().forEach(player -> {
/*     */           if (notsolved.contains(player.func_178845_a().getName())) {
/*     */             mb.set(player.func_178845_a().getName());
/*     */           }
/*     */         });
/* 264 */     return mb.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\DiscordEmbeds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */