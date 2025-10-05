/*     */ package com.mrzak34.thunderhack.gui.fontstuff;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CFontRenderer
/*     */   extends CFont {
/*  14 */   private final int[] colorCode = new int[32];
/*  15 */   protected CFont.CharData[] boldChars = new CFont.CharData[1104];
/*  16 */   protected CFont.CharData[] italicChars = new CFont.CharData[1104];
/*  17 */   protected CFont.CharData[] boldItalicChars = new CFont.CharData[1104];
/*     */   protected DynamicTexture texBold;
/*     */   protected DynamicTexture texItalic;
/*     */   protected DynamicTexture texItalicBold;
/*     */   
/*     */   public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  23 */     super(font, antiAlias, fractionalMetrics);
/*  24 */     setupMinecraftColorcodes();
/*  25 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   public void drawStringWithDropShadow(String text, float x, float y, int color) {
/*  29 */     for (int i = 0; i < 5; i++) {
/*  30 */       drawString(text, x + 0.5F * i, y + 0.5F * i, (new Color(0, 0, 0, 100 - i * 20)).hashCode());
/*     */     }
/*     */     
/*  33 */     drawString(text, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public float drawString(String text, float x, float y, int color) {
/*  38 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float drawString(String text, double x, double y, int color) {
/*  45 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float drawStringWithShadow(String text, float x, float y, int color) {
/*  51 */     float shadowWidth = drawString(text, x + 0.5D, y + 0.5D, color, true);
/*  52 */     return Math.max(shadowWidth, drawString(text, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, double x, double y, int color) {
/*  56 */     float shadowWidth = drawString(text, x + 0.5D, y + 0.5D, color, true);
/*  57 */     return Math.max(shadowWidth, drawString(text, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawCenteredString(String text, float x, float y, int color) {
/*  61 */     return drawString(text, x - getStringWidth(text) / 2.0F, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public float drawCenteredString(String text, double x, double y, int color) {
/*  66 */     return drawString(text, x - (getStringWidth(text) / 2.0F), y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
/*  70 */     drawString(text, (x - getStringWidth(text) / 2.0F) + 0.45D, y + 0.5D, color, true);
/*  71 */     return drawString(text, x - getStringWidth(text) / 2.0F, y, color);
/*     */   }
/*     */   
/*     */   public void drawStringWithOutline(String text, double x, double y, int color) {
/*  75 */     drawString(text, x - 0.5D, y, 0);
/*  76 */     drawString(text, x + 0.5D, y, 0);
/*  77 */     drawString(text, x, y - 0.5D, 0);
/*  78 */     drawString(text, x, y + 0.5D, 0);
/*  79 */     drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public void drawCenteredStringWithOutline(String text, double x, double y, int color) {
/*  83 */     drawCenteredString(ChatFormatting.stripFormatting(text), x - 0.5D, y, 0);
/*  84 */     drawCenteredString(ChatFormatting.stripFormatting(text), x + 0.5D, y, 0);
/*  85 */     drawCenteredString(ChatFormatting.stripFormatting(text), x, y - 0.5D, 0);
/*  86 */     drawCenteredString(ChatFormatting.stripFormatting(text), x, y + 0.5D, 0);
/*  87 */     drawCenteredString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
/*  91 */     drawString(text, x - (getStringWidth(text) / 2.0F) + 0.45D, y + 0.5D, color, true);
/*  92 */     return drawString(text, x - (getStringWidth(text) / 2.0F), y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public float drawString(String text2, double x, double y, int color, boolean shadow) {
/*     */     try {
/*  98 */       x--;
/*     */       
/* 100 */       if (text2 == null) {
/* 101 */         return 0.0F;
/*     */       }
/*     */       
/* 104 */       if (color == 553648127) {
/* 105 */         color = 16777215;
/*     */       }
/* 107 */       if ((color & 0xFC000000) == 0) {
/* 108 */         color |= 0xFF000000;
/*     */       }
/* 110 */       if (shadow) {
/* 111 */         color = (color & 0xFCFCFC) >> 2 | color & (new Color(20, 20, 20, 200)).getRGB();
/*     */       }
/* 113 */       CFont.CharData[] currentData = this.charData;
/* 114 */       float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 115 */       boolean bold = false;
/* 116 */       boolean italic = false;
/* 117 */       boolean strikethrough = false;
/* 118 */       boolean underline = false;
/* 119 */       x *= 2.0D;
/* 120 */       y = (y - 3.0D) * 2.0D;
/* 121 */       GL11.glPushMatrix();
/* 122 */       GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
/* 123 */       GlStateManager.func_179147_l();
/* 124 */       GlStateManager.func_179112_b(770, 771);
/* 125 */       GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/*     */ 
/*     */ 
/*     */       
/* 129 */       int size = text2.length();
/*     */       
/* 131 */       GlStateManager.func_179098_w();
/* 132 */       GlStateManager.func_179144_i(this.tex.func_110552_b());
/* 133 */       GL11.glBindTexture(3553, this.tex.func_110552_b());
/* 134 */       for (int i = 0; i < size; i++) {
/*     */ 
/*     */ 
/*     */         
/* 138 */         char character = text2.charAt(i);
/*     */         
/* 140 */         if (String.valueOf(character).equals("§")) {
/* 141 */           int colorIndex = 21;
/*     */ 
/*     */           
/*     */           try {
/* 145 */             colorIndex = "0123456789abcdefklmnor".indexOf(text2.charAt(i + 1));
/*     */           }
/* 147 */           catch (Exception var19) {
/* 148 */             var19.printStackTrace();
/*     */           } 
/* 150 */           if (colorIndex < 16) {
/* 151 */             bold = false;
/* 152 */             italic = false;
/* 153 */             underline = false;
/* 154 */             strikethrough = false;
/* 155 */             GlStateManager.func_179144_i(this.tex.func_110552_b());
/* 156 */             currentData = this.charData;
/* 157 */             if (colorIndex < 0) {
/* 158 */               colorIndex = 15;
/*     */             }
/* 160 */             if (shadow) {
/* 161 */               colorIndex += 16;
/*     */             }
/* 163 */             int colorcode = this.colorCode[colorIndex];
/* 164 */             GlStateManager.func_179131_c((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 165 */           } else if (colorIndex == 17) {
/* 166 */             bold = true;
/* 167 */             if (italic) {
/* 168 */               GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
/* 169 */               currentData = this.boldItalicChars;
/*     */             } else {
/* 171 */               GlStateManager.func_179144_i(this.texBold.func_110552_b());
/* 172 */               currentData = this.boldChars;
/*     */             } 
/* 174 */           } else if (colorIndex == 18) {
/* 175 */             strikethrough = true;
/* 176 */           } else if (colorIndex == 19) {
/* 177 */             underline = true;
/* 178 */           } else if (colorIndex == 20) {
/* 179 */             italic = true;
/* 180 */             if (bold) {
/* 181 */               GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
/* 182 */               currentData = this.boldItalicChars;
/*     */             } else {
/* 184 */               GlStateManager.func_179144_i(this.texItalic.func_110552_b());
/* 185 */               currentData = this.italicChars;
/*     */             } 
/* 187 */           } else if (colorIndex == 21) {
/* 188 */             bold = false;
/* 189 */             italic = false;
/* 190 */             underline = false;
/* 191 */             strikethrough = false;
/* 192 */             GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 193 */             GlStateManager.func_179144_i(this.tex.func_110552_b());
/* 194 */             currentData = this.charData;
/*     */           } 
/* 196 */           i++;
/*     */         
/*     */         }
/* 199 */         else if (character < currentData.length) {
/* 200 */           GL11.glBegin(4);
/* 201 */           drawChar(currentData, character, (float)x, (float)y);
/* 202 */           GL11.glEnd();
/* 203 */           if (strikethrough) {
/* 204 */             drawLine(x, y + ((currentData[character]).height / 2.0F), x + (currentData[character]).width - 8.0D, y + ((currentData[character]).height / 2.0F), 1.0F);
/*     */           }
/* 206 */           if (underline) {
/* 207 */             drawLine(x, y + (currentData[character]).height - 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height - 2.0D, 1.0F);
/*     */           }
/* 209 */           x += ((currentData[character]).width - 8 + this.charOffset);
/*     */         } 
/* 211 */       }  GL11.glPopMatrix();
/* 212 */     } catch (Exception exception) {
/* 213 */       exception.printStackTrace();
/*     */     } 
/* 215 */     return (float)x / 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStringWidth(String text1) {
/* 221 */     text1 = text1;
/* 222 */     if (text1 == null) {
/* 223 */       return 0;
/*     */     }
/*     */     
/* 226 */     int width = 0;
/* 227 */     CFont.CharData[] currentData = this.charData;
/* 228 */     boolean bold = false;
/* 229 */     boolean italic = false;
/*     */ 
/*     */     
/* 232 */     int size = text1.length();
/*     */     
/* 234 */     for (int i = 0; i < size; i++) {
/*     */ 
/*     */ 
/*     */       
/* 238 */       char character = text1.charAt(i);
/*     */       
/* 240 */       if (String.valueOf(character).equals("§")) {
/* 241 */         int colorIndex = "0123456789abcdefklmnor".indexOf(character);
/* 242 */         if (colorIndex < 16) {
/* 243 */           bold = false;
/* 244 */           italic = false;
/* 245 */         } else if (colorIndex == 17) {
/* 246 */           bold = true;
/* 247 */           currentData = italic ? this.boldItalicChars : this.boldChars;
/* 248 */         } else if (colorIndex == 20) {
/* 249 */           italic = true;
/* 250 */           currentData = bold ? this.boldItalicChars : this.italicChars;
/* 251 */         } else if (colorIndex == 21) {
/* 252 */           bold = false;
/* 253 */           italic = false;
/* 254 */           currentData = this.charData;
/*     */         } 
/* 256 */         i++;
/*     */       
/*     */       }
/* 259 */       else if (character < currentData.length) {
/* 260 */         width += (currentData[character]).width - 8 + this.charOffset;
/*     */       } 
/* 262 */     }  return width / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidthCust(String text) {
/* 266 */     if (text == null) {
/* 267 */       return 0;
/*     */     }
/* 269 */     int width = 0;
/* 270 */     CFont.CharData[] currentData = this.charData;
/* 271 */     boolean bold = false;
/* 272 */     boolean italic = false;
/* 273 */     int size = text.length();
/* 274 */     for (int i = 0; i < size; i++) {
/* 275 */       char character = text.charAt(i);
/* 276 */       if (String.valueOf(character).equals("§") && i < size) {
/* 277 */         int colorIndex = "0123456789abcdefklmnor".indexOf(character);
/* 278 */         if (colorIndex < 16) {
/* 279 */           bold = false;
/* 280 */           italic = false;
/* 281 */         } else if (colorIndex == 17) {
/* 282 */           bold = true;
/* 283 */           currentData = italic ? this.boldItalicChars : this.boldChars;
/* 284 */         } else if (colorIndex == 20) {
/* 285 */           italic = true;
/* 286 */           currentData = bold ? this.boldItalicChars : this.italicChars;
/* 287 */         } else if (colorIndex == 21) {
/* 288 */           bold = false;
/* 289 */           italic = false;
/* 290 */           currentData = this.charData;
/*     */         } 
/* 292 */         i++;
/*     */       
/*     */       }
/* 295 */       else if (character < currentData.length && character >= '\000') {
/* 296 */         width += (currentData[character]).width - 8 + this.charOffset;
/*     */       } 
/* 298 */     }  return (width - this.charOffset) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 303 */     super.setFont(font);
/* 304 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 309 */     super.setAntiAlias(antiAlias);
/* 310 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 315 */     super.setFractionalMetrics(fractionalMetrics);
/* 316 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   private void setupBoldItalicIDs() {
/* 320 */     this.texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
/* 321 */     this.texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
/* 322 */     this.texItalicBold = setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
/*     */   }
/*     */   
/*     */   private void drawLine(double x, double y, double x1, double y1, float width) {
/* 326 */     GL11.glDisable(3553);
/* 327 */     GL11.glLineWidth(width);
/* 328 */     GL11.glBegin(1);
/* 329 */     GL11.glVertex2d(x, y);
/* 330 */     GL11.glVertex2d(x1, y1);
/* 331 */     GL11.glEnd();
/* 332 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public List wrapWords(String text, double width) {
/* 336 */     ArrayList<String> finalWords = new ArrayList<>();
/* 337 */     if (getStringWidth(text) > width) {
/* 338 */       String[] words = text.split(" ");
/* 339 */       String currentWord = "";
/* 340 */       char lastColorCode = Character.MAX_VALUE;
/* 341 */       String[] var8 = words;
/* 342 */       int var9 = words.length;
/* 343 */       for (int var10 = 0; var10 < var9; var10++) {
/* 344 */         String word = var8[var10];
/* 345 */         for (int i = 0; i < (word.toCharArray()).length; i++) {
/* 346 */           char c = word.toCharArray()[i];
/* 347 */           if (String.valueOf(c).equals("§") && i < (word.toCharArray()).length - 1)
/* 348 */             lastColorCode = word.toCharArray()[i + 1]; 
/*     */         } 
/* 350 */         StringBuilder stringBuilder = new StringBuilder();
/* 351 */         if (getStringWidth(stringBuilder.append(currentWord).append(word).append(" ").toString()) < width) {
/* 352 */           currentWord = currentWord + word + " ";
/*     */         } else {
/*     */           
/* 355 */           finalWords.add(currentWord);
/* 356 */           currentWord = "" + lastColorCode + word + " ";
/*     */         } 
/* 358 */       }  if (currentWord.length() > 0) {
/* 359 */         if (getStringWidth(currentWord) < width) {
/* 360 */           finalWords.add("" + lastColorCode + currentWord + " ");
/* 361 */           currentWord = "";
/*     */         } else {
/* 363 */           for (Object s : formatString(currentWord, width)) {
/* 364 */             finalWords.add((String)s);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 369 */       finalWords.add(text);
/*     */     } 
/* 371 */     return finalWords;
/*     */   }
/*     */   
/*     */   public List formatString(String string, double width) {
/* 375 */     ArrayList<String> finalWords = new ArrayList<>();
/* 376 */     String currentWord = "";
/* 377 */     char lastColorCode = Character.MAX_VALUE;
/* 378 */     char[] chars = string.toCharArray();
/* 379 */     for (int i = 0; i < chars.length; i++) {
/* 380 */       char c = chars[i];
/* 381 */       if (String.valueOf(c).equals("§") && i < chars.length - 1) {
/* 382 */         lastColorCode = chars[i + 1];
/*     */       }
/* 384 */       StringBuilder stringBuilder = new StringBuilder();
/* 385 */       if (getStringWidth(stringBuilder.append(currentWord).append(c).toString()) < width) {
/* 386 */         currentWord = currentWord + c;
/*     */       } else {
/*     */         
/* 389 */         finalWords.add(currentWord);
/* 390 */         currentWord = "" + lastColorCode + c;
/*     */       } 
/* 392 */     }  if (currentWord.length() > 0) {
/* 393 */       finalWords.add(currentWord);
/*     */     }
/* 395 */     return finalWords;
/*     */   }
/*     */   
/*     */   private void setupMinecraftColorcodes() {
/* 399 */     for (int index = 0; index < 32; index++) {
/* 400 */       int noClue = (index >> 3 & 0x1) * 85;
/* 401 */       int red = (index >> 2 & 0x1) * 170 + noClue;
/* 402 */       int green = (index >> 1 & 0x1) * 170 + noClue;
/* 403 */       int blue = (index >> 0 & 0x1) * 170 + noClue;
/* 404 */       if (index == 6) {
/* 405 */         red += 85;
/*     */       }
/* 407 */       if (index >= 16) {
/* 408 */         red /= 4;
/* 409 */         green /= 4;
/* 410 */         blue /= 4;
/*     */       } 
/* 412 */       this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\fontstuff\CFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */