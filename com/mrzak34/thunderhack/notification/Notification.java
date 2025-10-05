/*     */ package com.mrzak34.thunderhack.notification;
/*     */ 
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.shaders.BetterAnimation;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ 
/*     */ public class Notification
/*     */ {
/*     */   private final String message;
/*     */   private final Timer timer;
/*     */   private final Type type;
/*  19 */   private final float height = 25.0F;
/*     */   private final long stayTime;
/*  21 */   public BetterAnimation animation = new BetterAnimation();
/*     */   private float posY;
/*     */   private final float width;
/*     */   private float animationX;
/*     */   private boolean direction = false;
/*  26 */   private final Timer animationTimer = new Timer();
/*     */   
/*     */   public Notification(String message, Type type, int time) {
/*  29 */     this.stayTime = time;
/*  30 */     this.message = message;
/*  31 */     this.type = type;
/*  32 */     this.timer = new Timer();
/*  33 */     this.timer.reset();
/*  34 */     ScaledResolution sr = new ScaledResolution(Util.mc);
/*  35 */     this.width = (FontRender.getStringWidth5(message) + 34);
/*  36 */     this.animationX = this.width;
/*  37 */     this.posY = sr.func_78328_b() - 25.0F;
/*     */   }
/*     */   
/*     */   public void render(float getY) {
/*  41 */     Color scolor = new Color(-15263977);
/*  42 */     Color icolor = new Color(scolor.getRed(), scolor.getGreen(), scolor.getBlue(), (int)MathUtil.clamp(255.0D * (1.0D - this.animation.getAnimationd()), 0.0D, 255.0D));
/*  43 */     Color icolor2 = new Color(255, 255, 255, (int)MathUtil.clamp(1.0D - this.animation.getAnimationd(), 0.0D, 255.0D));
/*     */     
/*  45 */     ScaledResolution resolution = new ScaledResolution(Util.mc);
/*     */     
/*  47 */     this.direction = isFinished();
/*     */     
/*  49 */     this.animationX = (float)(this.width * this.animation.getAnimationd());
/*     */     
/*  51 */     this.posY = animate(this.posY, getY);
/*     */     
/*  53 */     int x1 = (int)((resolution.func_78326_a() - 6) - this.width + this.animationX);
/*  54 */     int y1 = (int)this.posY;
/*     */     
/*  56 */     RenderUtil.drawBlurredShadow(x1, y1, this.width, 25.0F, 20, icolor);
/*  57 */     RoundedShader.drawRound(x1, y1, this.width, 25.0F, 6.0F, icolor);
/*     */     
/*  59 */     FontRender.drawString5(this.type.getName(), (x1 + 6), (y1 + 4), -1);
/*  60 */     FontRender.drawString5(this.message, (x1 + 6), (int)((y1 + 4) + (25.0F - FontRender.getFontHeight5()) / 2.0F), icolor2.getRGB());
/*     */     
/*  62 */     if (this.animationTimer.passedMs(50L)) {
/*  63 */       this.animation.update(this.direction);
/*  64 */       this.animationTimer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFinished() {
/*  70 */     return this.timer.passedMs(this.stayTime);
/*     */   }
/*     */   
/*     */   public double getHeight() {
/*  74 */     return 25.0D;
/*     */   }
/*     */   
/*     */   public boolean shouldDelete() {
/*  78 */     return (isFinished() && this.animationX >= this.width - 5.0F);
/*     */   }
/*     */   
/*     */   public float animate(float value, float target) {
/*  82 */     return value + (target - value) / 8.0F;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/*  87 */     SUCCESS("Success"),
/*  88 */     INFO("Information"),
/*  89 */     WARNING("Warning"),
/*  90 */     ERROR("Error"),
/*  91 */     ENABLED("Module enabled"),
/*  92 */     DISABLED("Module disabled");
/*     */     
/*     */     final String name;
/*     */     
/*     */     Type(String name) {
/*  97 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 101 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\notification\Notification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */