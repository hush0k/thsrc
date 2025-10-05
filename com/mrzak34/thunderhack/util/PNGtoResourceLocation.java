/*    */ package com.mrzak34.thunderhack.util;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.misc.DiscordEmbeds;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.util.HashMap;
/*    */ import javax.imageio.ImageIO;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PNGtoResourceLocation
/*    */ {
/* 22 */   private static final HashMap<String, ResourceLocation> image_cache = new HashMap<>();
/*    */ 
/*    */   
/*    */   public static ResourceLocation getTexture2(String name, String format) {
/* 26 */     if (image_cache.containsKey(name)) {
/* 27 */       return image_cache.get(name);
/*    */     }
/*    */     
/* 30 */     BufferedImage bufferedImage = null;
/*    */     try {
/* 32 */       bufferedImage = ImageIO.read(new File("ThunderHack/temp/heads/" + name + "." + format));
/* 33 */     } catch (Exception e) {
/* 34 */       return null;
/*    */     } 
/* 36 */     DynamicTexture texture = new DynamicTexture(bufferedImage);
/* 37 */     WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().func_110434_K().func_110578_a(name + "." + format, texture));
/* 38 */     image_cache.put(name, wr.location);
/* 39 */     return wr.location;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ResourceLocation getCustomImg(String name, String format) {
/* 44 */     BufferedImage bufferedImage = null;
/*    */     try {
/* 46 */       bufferedImage = ImageIO.read(new File("ThunderHack/images/" + name + "." + format));
/* 47 */     } catch (Exception e) {
/* 48 */       return null;
/*    */     } 
/* 50 */     DynamicTexture texture = new DynamicTexture(bufferedImage);
/* 51 */     WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().func_110434_K().func_110578_a(name + "." + format, texture));
/* 52 */     return wr.location;
/*    */   }
/*    */   
/*    */   public static ResourceLocation getTexture3(String name, String format) {
/* 56 */     BufferedImage bufferedImage = null;
/*    */     try {
/* 58 */       bufferedImage = ImageIO.read(new File("ThunderHack/temp/skins/" + name + "." + format));
/* 59 */     } catch (Exception e) {
/* 60 */       return null;
/*    */     } 
/* 62 */     DynamicTexture texture = new DynamicTexture(bufferedImage);
/* 63 */     WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().func_110434_K().func_110578_a(name + "." + format, texture));
/* 64 */     return wr.location;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ResourceLocation getTexture(String name, String format) {
/* 69 */     BufferedImage bufferedImage = null;
/*    */     try {
/* 71 */       bufferedImage = ImageIO.read(new File("ThunderHack/temp/embeds/" + name + "." + format));
/* 72 */     } catch (Exception e) {
/* 73 */       return null;
/*    */     } 
/* 75 */     float ratio = bufferedImage.getWidth() / bufferedImage.getHeight();
/* 76 */     DiscordEmbeds.nigw = ((Integer)((DiscordEmbeds)Thunderhack.moduleManager.getModuleByClass(DiscordEmbeds.class)).multip.getValue()).intValue();
/* 77 */     DiscordEmbeds.nigh = ((Integer)((DiscordEmbeds)Thunderhack.moduleManager.getModuleByClass(DiscordEmbeds.class)).multip.getValue()).intValue() / ratio;
/*    */     
/* 79 */     DynamicTexture texture = new DynamicTexture(bufferedImage);
/* 80 */     WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().func_110434_K().func_110578_a(name + "." + format, texture));
/* 81 */     return wr.location;
/*    */   }
/*    */   
/*    */   public static class WrappedResource
/*    */   {
/*    */     public final ResourceLocation location;
/*    */     
/*    */     public WrappedResource(ResourceLocation location) {
/* 89 */       this.location = location;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\PNGtoResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */