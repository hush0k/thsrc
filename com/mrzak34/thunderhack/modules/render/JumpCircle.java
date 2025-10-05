/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventJump;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.math.AstolfoAnimation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class JumpCircle
/*     */   extends Module
/*     */ {
/*  21 */   public static AstolfoAnimation astolfo = new AstolfoAnimation();
/*  22 */   static List<Circle> circles = new ArrayList<>();
/*  23 */   public Setting<Float> range2 = register(new Setting("Radius", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(3.0F)));
/*  24 */   public Setting<Float> range = register(new Setting("Radius2", Float.valueOf(3.0F), Float.valueOf(0.1F), Float.valueOf(3.0F)));
/*  25 */   public Setting<Integer> lifetime = register(new Setting("live", Integer.valueOf(1000), Integer.valueOf(1), Integer.valueOf(10000)));
/*  26 */   public Setting<mode> Mode = register(new Setting("Mode", mode.Jump));
/*  27 */   public Timer timer = new Timer();
/*     */   
/*     */   public JumpCircle() {
/*  30 */     super("JumpCircle", "JumpCircle", Module.Category.RENDER);
/*     */   }
/*     */   boolean check = false;
/*     */   
/*     */   public void onUpdate() {
/*  35 */     if (mc.field_71439_g.field_70124_G && this.Mode.getValue() == mode.Landing && this.check) {
/*  36 */       circles.add(new Circle(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0625D, mc.field_71439_g.field_70161_v)));
/*  37 */       this.check = false;
/*     */     } 
/*  39 */     astolfo.update();
/*  40 */     for (Circle circle : circles) {
/*  41 */       circle.update();
/*     */     }
/*  43 */     circles.removeIf(Circle::update);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onJump(EventJump e) {
/*  48 */     if (this.Mode.getValue() == mode.Jump) {
/*  49 */       circles.add(new Circle(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.0625D, mc.field_71439_g.field_70161_v)));
/*     */     }
/*  51 */     this.check = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  58 */     GlStateManager.func_179094_E();
/*  59 */     GL11.glDisable(3553);
/*  60 */     GL11.glEnable(3042);
/*  61 */     GL11.glBlendFunc(770, 771);
/*  62 */     GL11.glDisable(2929);
/*  63 */     GL11.glDisable(3008);
/*  64 */     GL11.glEnable(2848);
/*  65 */     GlStateManager.func_179117_G();
/*  66 */     GL11.glShadeModel(7425);
/*  67 */     double ix = -(mc.field_71439_g.field_70142_S + (mc.field_71439_g.field_70165_t - mc.field_71439_g.field_70142_S) * mc.func_184121_ak());
/*  68 */     double iy = -(mc.field_71439_g.field_70137_T + (mc.field_71439_g.field_70163_u - mc.field_71439_g.field_70137_T) * mc.func_184121_ak());
/*  69 */     double iz = -(mc.field_71439_g.field_70136_U + (mc.field_71439_g.field_70161_v - mc.field_71439_g.field_70136_U) * mc.func_184121_ak());
/*  70 */     GL11.glTranslated(ix, iy, iz);
/*  71 */     Collections.reverse(circles);
/*     */     try {
/*  73 */       for (Circle c : circles) {
/*  74 */         double x = (c.position()).field_72450_a;
/*  75 */         double y = (c.position()).field_72448_b;
/*  76 */         double z = (c.position()).field_72449_c;
/*  77 */         float k = (float)c.timer.getPassedTimeMs() / ((Integer)this.lifetime.getValue()).intValue();
/*  78 */         float start = k * ((Float)this.range.getValue()).floatValue();
/*  79 */         float end = k * ((Float)this.range2.getValue()).floatValue();
/*     */         
/*  81 */         float middle = (start + end) / 2.0F;
/*  82 */         GL11.glBegin(8); int i;
/*  83 */         for (i = 0; i <= 360; i += 5) {
/*     */           
/*  85 */           double stage = (i + 90) / 360.0D;
/*  86 */           int clr = astolfo.getColor(stage);
/*  87 */           int red = clr >> 16 & 0xFF;
/*  88 */           int green = clr >> 8 & 0xFF;
/*  89 */           int blue = clr & 0xFF;
/*     */           
/*  91 */           GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 0.0F);
/*  92 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * start, y, z + Math.sin(Math.toRadians(i)) * start);
/*  93 */           GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F - (float)c.timer.getPassedTimeMs() / ((Integer)this.lifetime.getValue()).intValue());
/*  94 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * middle, y, z + Math.sin(Math.toRadians(i)) * middle);
/*     */         } 
/*  96 */         GL11.glEnd();
/*  97 */         GL11.glBegin(8);
/*  98 */         for (i = 0; i <= 360; i += 5) {
/*  99 */           double stage = (i + 90) / 360.0D;
/* 100 */           int clr = astolfo.getColor(stage);
/* 101 */           int red = clr >> 16 & 0xFF;
/* 102 */           int green = clr >> 8 & 0xFF;
/* 103 */           int blue = clr & 0xFF;
/* 104 */           GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F - (float)c.timer.getPassedTimeMs() / ((Integer)this.lifetime.getValue()).intValue());
/* 105 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * middle, y, z + Math.sin(Math.toRadians(i)) * middle);
/* 106 */           GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 0.0F);
/* 107 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * end, y, z + Math.sin(Math.toRadians(i)) * end);
/*     */         } 
/* 109 */         GL11.glEnd();
/*     */       } 
/* 111 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 114 */     GL11.glEnable(3553);
/* 115 */     GL11.glEnable(2929);
/* 116 */     GL11.glDisable(2848);
/* 117 */     GL11.glEnable(3008);
/* 118 */     GlStateManager.func_179117_G();
/* 119 */     Collections.reverse(circles);
/* 120 */     GlStateManager.func_179121_F();
/* 121 */     GL11.glShadeModel(7424);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum mode
/*     */   {
/* 129 */     Jump, Landing;
/*     */   }
/*     */   
/*     */   class Circle {
/*     */     private final Vec3d vec;
/* 134 */     Timer timer = new Timer();
/*     */     
/*     */     Circle(Vec3d vec) {
/* 137 */       this.vec = vec;
/* 138 */       this.timer.reset();
/*     */     }
/*     */     
/*     */     Vec3d position() {
/* 142 */       return this.vec;
/*     */     }
/*     */     
/*     */     public boolean update() {
/* 146 */       return this.timer.passedMs(((Integer)JumpCircle.this.lifetime.getValue()).intValue());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\JumpCircle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */