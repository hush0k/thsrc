/*     */ package com.mrzak34.thunderhack.gui.particles;
/*     */ 
/*     */ import com.mrzak34.thunderhack.modules.client.Particles;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.util.Random;
/*     */ import javax.vecmath.Vector2f;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ 
/*     */ public class Particle
/*     */ {
/*  13 */   public static Color[] colors = new Color[] { new Color(0, 233, 255), new Color(209, 2, 209), new Color(237, 0, 87), new Color(195, 0, 195), new Color(255, 1, 255), new Color(1, 95, 245), new Color(206, 2, 30), new Color(90, 14, 214) };
/*     */ 
/*     */   
/*     */   private final Color color;
/*     */ 
/*     */   
/*     */   private final Vector2f pos;
/*     */ 
/*     */   
/*     */   private final Vector2f prev_pos;
/*     */ 
/*     */   
/*     */   private Vector2f velocity;
/*     */   
/*     */   private float size;
/*     */   
/*     */   private float alpha;
/*     */ 
/*     */   
/*     */   public Particle(Vector2f velocity, float x, float y, float size, Color color) {
/*  33 */     this.velocity = velocity;
/*  34 */     this.color = color;
/*  35 */     this.pos = new Vector2f(x, y);
/*  36 */     this.prev_pos = new Vector2f(x, y);
/*  37 */     this.size = size;
/*     */   }
/*     */   
/*     */   public static Particle generateParticle(float sc) {
/*  41 */     ScaledResolution sr = new ScaledResolution(Util.mc);
/*  42 */     Vector2f velocity = new Vector2f((float)(Math.random() * 2.0D - 1.0D), (float)(Math.random() * 2.0D - 1.0D));
/*  43 */     float x = getRandomNumberUsingNextInt(100, sr.func_78326_a() - 30);
/*  44 */     float y = getRandomNumberUsingNextInt(100, sr.func_78328_b() - 30);
/*  45 */     float size = sc + getRandomNumberUsingNextInt(0, (int)(sc / 3.0F));
/*  46 */     int n = (int)Math.floor(Math.random() * colors.length);
/*  47 */     Color color = colors[n];
/*  48 */     return new Particle(velocity, x, y, size, color);
/*     */   }
/*     */   
/*     */   public static int getRandomNumberUsingNextInt(int min, int max) {
/*  52 */     Random random = new Random();
/*  53 */     return random.nextInt(max - min) + min;
/*     */   }
/*     */   
/*     */   public static double distance(float x, float y, float x1, float y1) {
/*  57 */     return Math.sqrt(((x - x1) * (x - x1) + (y - y1) * (y - y1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAlpha() {
/*  62 */     return this.alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getX() {
/*  67 */     return this.pos.getX();
/*     */   }
/*     */   
/*     */   public float getY() {
/*  71 */     return this.pos.getY();
/*     */   }
/*     */   
/*     */   public float get_prevX() {
/*  75 */     return this.prev_pos.getX();
/*     */   }
/*     */   
/*     */   public float get_prevY() {
/*  79 */     return this.prev_pos.getY();
/*     */   }
/*     */   
/*     */   public void setX(float x) {
/*  83 */     this.pos.setX(x);
/*     */   }
/*     */   
/*     */   public void setY(float y) {
/*  87 */     this.pos.setY(y);
/*     */   }
/*     */   
/*     */   public float getSize() {
/*  91 */     return this.size;
/*     */   }
/*     */   
/*     */   public void setSize(float size) {
/*  95 */     this.size = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(int delta, float speed) {
/* 100 */     ScaledResolution sr = new ScaledResolution(Util.mc);
/*     */     
/* 102 */     this.prev_pos.x = this.pos.x;
/* 103 */     this.prev_pos.y = this.pos.y;
/* 104 */     this.pos.x += this.velocity.getX() * delta * speed;
/* 105 */     this.pos.y += this.velocity.getY() * delta * speed;
/*     */ 
/*     */     
/* 108 */     if (this.alpha < 255.0F) this.alpha += 0.05F * delta; 
/* 109 */     if (this.pos.getX() + ((Float)(Particles.getInstance()).scale1.getValue()).floatValue() > sr.func_78326_a())
/* 110 */       this.velocity = new Vector2f(-this.velocity.x, this.velocity.y); 
/* 111 */     if (this.pos.getX() - ((Float)(Particles.getInstance()).scale1.getValue()).floatValue() < 0.0F) {
/* 112 */       this.velocity = new Vector2f(-this.velocity.x, this.velocity.y);
/*     */     }
/* 114 */     if (this.pos.getY() + ((Float)(Particles.getInstance()).scale1.getValue()).floatValue() > sr.func_78326_a())
/* 115 */       this.velocity = new Vector2f(this.velocity.x, -this.velocity.y); 
/* 116 */     if (this.pos.getY() - ((Float)(Particles.getInstance()).scale1.getValue()).floatValue() < 0.0F)
/* 117 */       this.velocity = new Vector2f(this.velocity.x, -this.velocity.y); 
/*     */   }
/*     */   
/*     */   public float getDistanceTo(Particle particle1) {
/* 121 */     return getDistanceTo(particle1.getX(), particle1.getY());
/*     */   }
/*     */   
/*     */   public Color getColor() {
/* 125 */     return this.color;
/*     */   }
/*     */   
/*     */   public float getDistanceTo(float x, float y) {
/* 129 */     return (float)distance(getX(), getY(), x, y);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\particles\Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */