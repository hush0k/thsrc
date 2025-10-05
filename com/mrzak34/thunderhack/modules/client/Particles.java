/*     */ package com.mrzak34.thunderhack.modules.client;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.RadarRewrite;
/*     */ import com.mrzak34.thunderhack.gui.particles.Particle;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.render.RenderHelper;
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraftforge.client.event.GuiOpenEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Particles
/*     */   extends Module
/*     */ {
/*     */   private static final float SPEED = 0.1F;
/*  26 */   private static Particles INSTANCE = new Particles();
/*  27 */   public Setting<Integer> delta = register(new Setting("Speed", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(60)));
/*  28 */   public Setting<Integer> amount = register(new Setting("Amount ", Integer.valueOf(150), Integer.valueOf(0), Integer.valueOf(666)));
/*  29 */   public Setting<Float> scale1 = register(new Setting("Scale", Float.valueOf(5.0F), Float.valueOf(0.1F), Float.valueOf(30.0F)));
/*  30 */   public Setting<Float> linet = register(new Setting("lineT", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(10.0F)));
/*  31 */   public Setting<Integer> dist = register(new Setting("Dist ", Integer.valueOf(50), Integer.valueOf(1), Integer.valueOf(500)));
/*  32 */   private final List<Particle> particleList = new CopyOnWriteArrayList<>();
/*     */ 
/*     */   
/*     */   public Particles() {
/*  36 */     super("Particles", "рисует партиклы в гуи", Module.Category.CLIENT);
/*  37 */     setInstance();
/*     */   }
/*     */   
/*     */   public static Particles getInstance() {
/*  41 */     if (INSTANCE == null) {
/*  42 */       INSTANCE = new Particles();
/*     */     }
/*  44 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static void drawGradientLine(float x1, float y1, float x2, float y2, float lineWidth, Color color1, Color color2) {
/*  48 */     GL11.glLineWidth(lineWidth);
/*  49 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  50 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  51 */     GlStateManager.func_179147_l();
/*  52 */     GL11.glDisable(3553);
/*  53 */     GL11.glDisable(3042);
/*  54 */     GL11.glBlendFunc(770, 771);
/*  55 */     GlStateManager.func_179103_j(7425);
/*  56 */     bufferbuilder.func_181668_a(1, DefaultVertexFormats.field_181706_f);
/*     */     
/*  58 */     bufferbuilder.func_181662_b(x1, y1, 0.0D).func_181666_a(color1.getRed() / 255.0F, color1.getGreen() / 255.0F, color1.getBlue() / 255.0F, color1.getAlpha() / 255.0F).func_181675_d();
/*  59 */     bufferbuilder.func_181662_b(x2, y2, 0.0D).func_181666_a(color2.getRed() / 255.0F, color2.getGreen() / 255.0F, color2.getBlue() / 255.0F, color2.getAlpha() / 255.0F).func_181675_d();
/*     */     
/*  61 */     tessellator.func_78381_a();
/*  62 */     GlStateManager.func_179103_j(7424);
/*  63 */     GL11.glEnable(3042);
/*  64 */     GL11.glEnable(3553);
/*  65 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public static double distance(float x, float y, float x1, float y1) {
/*  69 */     return Math.sqrt(((x - x1) * (x - x1) + (y - y1) * (y - y1)));
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  73 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  78 */     for (Particle particle : this.particleList) {
/*  79 */       particle.tick(((Integer)this.delta.getValue()).intValue(), 0.1F);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onGuiOpened(GuiOpenEvent event) {
/*  85 */     if (event.getGui() != null) {
/*  86 */       addParticles(((Integer)this.amount.getValue()).intValue());
/*     */     } else {
/*  88 */       this.particleList.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addParticles(int amount) {
/*  93 */     for (int i = 0; i < amount; i++) {
/*  94 */       this.particleList.add(Particle.generateParticle(((Float)this.scale1.getValue()).floatValue()));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/*     */     try {
/* 101 */       render();
/* 102 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render() {
/* 109 */     for (Particle particle : this.particleList) {
/* 110 */       float nearestDistance = 0.0F;
/* 111 */       Particle nearestParticle = null;
/* 112 */       for (Particle particle1 : this.particleList) {
/* 113 */         float distance = particle.getDistanceTo(particle1);
/* 114 */         if (distance <= ((Integer)this.dist.getValue()).intValue() && (nearestDistance <= 0.0F || distance <= nearestDistance)) {
/* 115 */           nearestDistance = distance;
/* 116 */           nearestParticle = particle1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 122 */       if (nearestParticle != null) {
/* 123 */         drawGradientLine((float)RadarRewrite.interp(particle.getX(), particle.get_prevX()), (float)RadarRewrite.interp(particle.getY(), particle.get_prevY()), (float)RadarRewrite.interp(nearestParticle.getX(), nearestParticle.get_prevX()), (float)RadarRewrite.interp(nearestParticle.getY(), nearestParticle.get_prevY()), ((Float)this.linet.getValue()).floatValue(), particle.getColor(), nearestParticle.getColor());
/*     */       }
/* 125 */       RenderHelper.drawCircle((float)RadarRewrite.interp(particle.getX(), particle.get_prevX()), (float)RadarRewrite.interp(particle.getY(), particle.get_prevY()), particle.getSize(), true, particle.getColor());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\Particles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */