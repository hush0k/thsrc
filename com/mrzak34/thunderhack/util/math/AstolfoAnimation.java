/*    */ package com.mrzak34.thunderhack.util.math;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class AstolfoAnimation {
/*    */   private double value;
/*    */   
/*    */   public static int HSBtoRGB(float hue, float saturation, float brightness) {
/*  9 */     int r = 0, g = 0, b = 0;
/* 10 */     if (saturation == 0.0F) {
/* 11 */       r = g = b = (int)(brightness * 255.0F + 0.5F);
/*    */     } else {
/* 13 */       float h = (hue - (float)Math.floor(hue)) * 6.0F;
/* 14 */       float f = h - (float)Math.floor(h);
/* 15 */       float p = brightness * (1.0F - saturation);
/* 16 */       float q = brightness * (1.0F - saturation * f);
/* 17 */       float t = brightness * (1.0F - saturation * (1.0F - f));
/* 18 */       switch ((int)h) {
/*    */         case 0:
/* 20 */           r = (int)(brightness * 255.0F + 0.5F);
/* 21 */           g = (int)(t * 255.0F + 0.5F);
/* 22 */           b = (int)(p * 255.0F + 0.5F);
/*    */           break;
/*    */         case 1:
/* 25 */           r = (int)(q * 255.0F + 0.5F);
/* 26 */           g = (int)(brightness * 255.0F + 0.5F);
/* 27 */           b = (int)(p * 255.0F + 0.5F);
/*    */           break;
/*    */         case 2:
/* 30 */           r = (int)(p * 255.0F + 0.5F);
/* 31 */           g = (int)(brightness * 255.0F + 0.5F);
/* 32 */           b = (int)(t * 255.0F + 0.5F);
/*    */           break;
/*    */         case 3:
/* 35 */           r = (int)(p * 255.0F + 0.5F);
/* 36 */           g = (int)(q * 255.0F + 0.5F);
/* 37 */           b = (int)(brightness * 255.0F + 0.5F);
/*    */           break;
/*    */         case 4:
/* 40 */           r = (int)(t * 255.0F + 0.5F);
/* 41 */           g = (int)(p * 255.0F + 0.5F);
/* 42 */           b = (int)(brightness * 255.0F + 0.5F);
/*    */           break;
/*    */         case 5:
/* 45 */           r = (int)(brightness * 255.0F + 0.5F);
/* 46 */           g = (int)(p * 255.0F + 0.5F);
/* 47 */           b = (int)(q * 255.0F + 0.5F);
/*    */           break;
/*    */       } 
/*    */     } 
/* 51 */     return 0xFF000000 | r << 16 | g << 8 | b;
/*    */   }
/*    */   private double prevValue;
/*    */   public void update() {
/* 55 */     this.prevValue = this.value;
/* 56 */     this.value += 0.01D;
/*    */   }
/*    */   
/*    */   public int getColor(double offset) {
/* 60 */     double hue = (this.prevValue + (this.value - this.prevValue) * Minecraft.func_71410_x().func_184121_ak() + offset) % 1.0D;
/* 61 */     if (hue > 0.5D) {
/* 62 */       hue = 0.5D - hue - 0.5D;
/*    */     }
/* 64 */     hue += 0.5D;
/* 65 */     return HSBtoRGB((float)hue, 0.5F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\math\AstolfoAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */