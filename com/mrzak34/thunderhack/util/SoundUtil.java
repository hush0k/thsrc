/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import sun.audio.AudioPlayer;
/*    */ import sun.audio.AudioStream;
/*    */ 
/*    */ public class SoundUtil
/*    */ {
/* 10 */   public static final SoundUtil INSTANCE = new SoundUtil();
/* 11 */   public static ResourceLocation ON_SOUND = new ResourceLocation("sounds/on.wav");
/* 12 */   public static ResourceLocation OFF_SOUND = new ResourceLocation("sounds/off.wav");
/* 13 */   public static ResourceLocation SUCCESS_SOUND = new ResourceLocation("sounds/success.wav");
/* 14 */   public static ResourceLocation ERROR_SOUND = new ResourceLocation("sounds/error.wav");
/*    */ 
/*    */   
/*    */   public static void playSound(ThunderSound snd) {
/* 18 */     ResourceLocation resourceLocation = null;
/*    */     
/* 20 */     switch (snd) {
/*    */       case ON:
/* 22 */         resourceLocation = ON_SOUND;
/*    */         break;
/*    */       case OFF:
/* 25 */         resourceLocation = OFF_SOUND;
/*    */         break;
/*    */       case SUCCESS:
/* 28 */         resourceLocation = SUCCESS_SOUND;
/*    */         break;
/*    */       case ERROR:
/* 31 */         resourceLocation = ERROR_SOUND; break;
/*    */     } 
/*    */     try {
/* 34 */       AudioPlayer.player.start((InputStream)new AudioStream(Util.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b()));
/* 35 */     } catch (Exception exception) {}
/*    */   }
/*    */   
/*    */   public enum ThunderSound {
/* 39 */     ON, OFF, ERROR, SUCCESS;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\SoundUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */