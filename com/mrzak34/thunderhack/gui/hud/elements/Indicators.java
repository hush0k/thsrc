/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.modules.misc.Timer;
/*     */ import com.mrzak34.thunderhack.modules.movement.DMGFly;
/*     */ import com.mrzak34.thunderhack.modules.movement.MSTSpeed;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.math.AstolfoAnimation;
/*     */ import com.mrzak34.thunderhack.util.math.DynamicAnimation;
/*     */ import com.mrzak34.thunderhack.util.render.DrawHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Indicators
/*     */   extends HudElement
/*     */ {
/*  32 */   public static AstolfoAnimation astolfo = new AstolfoAnimation();
/*  33 */   private static final List<Indicator> indicators = new ArrayList<>();
/*  34 */   private final Setting<ColorSetting> cc = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  35 */   private final Setting<ColorSetting> cs = register(new Setting("RectColor", new ColorSetting(-2013200640)));
/*  36 */   public Setting<Boolean> dmgflyy = register(new Setting("DMGFly", Boolean.valueOf(true)));
/*  37 */   public Setting<Boolean> Memoryy = register(new Setting("Memory", Boolean.valueOf(true)));
/*  38 */   public Setting<Boolean> Timerr = register(new Setting("Timer", Boolean.valueOf(true)));
/*  39 */   public Setting<Boolean> TPS = register(new Setting("TPS", Boolean.valueOf(true)));
/*  40 */   public Setting<Boolean> dmgspeed = register(new Setting("DMGSpeed", Boolean.valueOf(true)));
/*  41 */   public Setting<Boolean> blur = register(new Setting("Blur", Boolean.valueOf(true)));
/*  42 */   public Setting<Float> grange = register(new Setting("GlowRange", Float.valueOf(3.6F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  43 */   public Setting<Float> gmult = register(new Setting("GlowMultiplier", Float.valueOf(3.6F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  44 */   public Setting<Float> range = register(new Setting("RangeBetween", Float.valueOf(46.0F), Float.valueOf(46.0F), Float.valueOf(100.0F)));
/*     */   boolean once = false;
/*  46 */   private final Setting<mode2> colorType = register(new Setting("Mode", mode2.Astolfo));
/*     */   
/*     */   public Indicators() {
/*  49 */     super("WexIndicators", "Индикаторы как в вексайде-(из вексайда)", 150, 50);
/*     */   }
/*     */   
/*     */   public static float[] getRG(double input) {
/*  53 */     return new float[] { 255.0F - 255.0F * (float)input, 255.0F * (float)input, 100.0F * (float)input };
/*     */   }
/*     */   
/*     */   protected void once() {
/*  57 */     indicators.add(new Indicator()
/*     */         {
/*     */           boolean enabled()
/*     */           {
/*  61 */             return ((Boolean)Indicators.this.Timerr.getValue()).booleanValue();
/*     */           }
/*     */ 
/*     */           
/*     */           String getName() {
/*  66 */             return "Timer";
/*     */           }
/*     */ 
/*     */           
/*     */           double getProgress() {
/*  71 */             return (10.0D - Timer.value) / (Math.abs(((Timer)Thunderhack.moduleManager.getModuleByClass(Timer.class)).getMin()) + 10);
/*     */           }
/*     */         });
/*  74 */     indicators.add(new Indicator()
/*     */         {
/*     */           boolean enabled()
/*     */           {
/*  78 */             return ((Boolean)Indicators.this.Memoryy.getValue()).booleanValue();
/*     */           }
/*     */ 
/*     */           
/*     */           String getName() {
/*  83 */             return "Memory";
/*     */           }
/*     */ 
/*     */           
/*     */           double getProgress() {
/*  88 */             long total = Runtime.getRuntime().totalMemory();
/*  89 */             long free = Runtime.getRuntime().freeMemory();
/*  90 */             long delta = total - free;
/*  91 */             return delta / Runtime.getRuntime().maxMemory();
/*     */           }
/*     */         });
/*  94 */     indicators.add(new Indicator()
/*     */         {
/*     */           boolean enabled()
/*     */           {
/*  98 */             return ((Boolean)Indicators.this.dmgflyy.getValue()).booleanValue();
/*     */           }
/*     */ 
/*     */           
/*     */           String getName() {
/* 103 */             return "DMG Fly";
/*     */           }
/*     */ 
/*     */           
/*     */           double getProgress() {
/* 108 */             return DMGFly.getProgress();
/*     */           }
/*     */         });
/* 111 */     indicators.add(new Indicator()
/*     */         {
/*     */           boolean enabled()
/*     */           {
/* 115 */             return ((Boolean)Indicators.this.dmgspeed.getValue()).booleanValue();
/*     */           }
/*     */ 
/*     */           
/*     */           String getName() {
/* 120 */             return "DMG Speed";
/*     */           }
/*     */ 
/*     */           
/*     */           double getProgress() {
/* 125 */             return MSTSpeed.getProgress();
/*     */           }
/*     */         });
/* 128 */     indicators.add(new Indicator()
/*     */         {
/*     */           boolean enabled()
/*     */           {
/* 132 */             return ((Boolean)Indicators.this.TPS.getValue()).booleanValue();
/*     */           }
/*     */ 
/*     */           
/*     */           String getName() {
/* 137 */             return "TPS";
/*     */           }
/*     */ 
/*     */           
/*     */           double getProgress() {
/* 142 */             return (Thunderhack.serverManager.getTPS() / 20.0F);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/* 149 */     super.onRender2D(e);
/* 150 */     draw(e.scaledResolution);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 156 */     if (!this.once) {
/* 157 */       once();
/* 158 */       this.once = true;
/*     */       return;
/*     */     } 
/* 161 */     astolfo.update();
/* 162 */     indicators.forEach(indicator -> indicator.update());
/*     */   }
/*     */   
/*     */   public void draw(ScaledResolution sr) {
/* 166 */     GL11.glPushMatrix();
/* 167 */     GL11.glTranslated((getX() * sr.func_78326_a()), (getY() * sr.func_78328_b()), 0.0D);
/*     */     
/* 169 */     List<Indicator> enabledIndicators = new ArrayList<>();
/* 170 */     for (Indicator indicator : indicators) {
/* 171 */       if (indicator.enabled())
/* 172 */         enabledIndicators.add(indicator); 
/*     */     } 
/* 174 */     int enabledCount = enabledIndicators.size();
/* 175 */     if (enabledCount > 0) {
/* 176 */       for (int i = 0; i < enabledCount; i++) {
/* 177 */         GL11.glPushMatrix();
/* 178 */         GL11.glTranslated((((Float)this.range.getValue()).floatValue() * i), 0.0D, 0.0D);
/* 179 */         Indicator ind = enabledIndicators.get(i);
/*     */         
/* 181 */         if (!((Boolean)this.blur.getValue()).booleanValue()) {
/* 182 */           RenderUtil.drawSmoothRect(0.0F, 0.0F, 44.0F, 44.0F, (new Color(25, 25, 25, 180)).getRGB());
/*     */         } else {
/* 184 */           DrawHelper.drawRectWithGlow(0.0D, 0.0D, 44.0D, 44.0D, ((Float)this.grange.getValue()).floatValue(), ((Float)this.gmult.getValue()).floatValue(), ((ColorSetting)this.cs.getValue()).getColorObject());
/*     */         } 
/*     */ 
/*     */         
/* 188 */         GL11.glTranslated(22.0D, 26.0D, 0.0D);
/* 189 */         drawCircle(ind.getName(), ind.progress());
/* 190 */         GL11.glPopMatrix();
/*     */       } 
/*     */     }
/* 193 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCircle(String name, double offset) {
/* 198 */     GL11.glDisable(3553);
/* 199 */     boolean oldState = GL11.glIsEnabled(3042);
/* 200 */     GL11.glEnable(3042);
/* 201 */     GL11.glEnable(2848);
/* 202 */     GL11.glBlendFunc(770, 771);
/* 203 */     GL11.glShadeModel(7425);
/* 204 */     GL11.glLineWidth(5.5F);
/* 205 */     GL11.glColor4f(0.1F, 0.1F, 0.1F, 0.5F);
/* 206 */     GL11.glBegin(3); int i;
/* 207 */     for (i = 0; i < 360; i++) {
/* 208 */       double x = Math.cos(Math.toRadians(i)) * 11.0D;
/* 209 */       double z = Math.sin(Math.toRadians(i)) * 11.0D;
/* 210 */       GL11.glVertex2d(x, z);
/*     */     } 
/* 212 */     GL11.glEnd();
/* 213 */     GL11.glBegin(3);
/* 214 */     for (i = -90; i < -90.0D + 360.0D * offset; i++) {
/*     */       
/* 216 */       float red = ((ColorSetting)this.cc.getValue()).getRed();
/* 217 */       float green = ((ColorSetting)this.cc.getValue()).getGreen();
/* 218 */       float blue = ((ColorSetting)this.cc.getValue()).getBlue();
/* 219 */       if (this.colorType.getValue() == mode2.StateBased) {
/* 220 */         float[] buffer = getRG(offset);
/* 221 */         red = buffer[0];
/* 222 */         green = buffer[1];
/* 223 */         blue = buffer[2];
/* 224 */       } else if (this.colorType.getValue() == mode2.Astolfo) {
/* 225 */         double stage = (i + 90) / 360.0D;
/* 226 */         int clr = astolfo.getColor(stage);
/* 227 */         red = (clr >> 16 & 0xFF);
/* 228 */         green = (clr >> 8 & 0xFF);
/* 229 */         blue = (clr & 0xFF);
/*     */       } 
/* 231 */       GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F);
/* 232 */       double x = Math.cos(Math.toRadians(i)) * 11.0D;
/* 233 */       double z = Math.sin(Math.toRadians(i)) * 11.0D;
/* 234 */       GL11.glVertex2d(x, z);
/*     */     } 
/* 236 */     GL11.glEnd();
/* 237 */     GL11.glDisable(2848);
/* 238 */     if (!oldState)
/* 239 */       GL11.glDisable(3042); 
/* 240 */     GL11.glEnable(3553);
/*     */     
/* 242 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 243 */     if (!Objects.equals(name, "TPS")) {
/* 244 */       FontRender.drawCentString6((int)(offset * 100.0D) + "%", 0.3F, -0.8F, (new Color(200, 200, 200, 255)).getRGB());
/* 245 */       FontRender.drawCentString6(name, 0.0F, -20.0F, (new Color(200, 200, 200, 255)).getRGB());
/*     */     } else {
/* 247 */       FontRender.drawCentString6(String.valueOf((int)(offset * 20.0D)), 0.0F, -0.8F, (new Color(200, 200, 200, 255)).getRGB());
/* 248 */       FontRender.drawCentString6(name, 0.0F, -20.0F, (new Color(200, 200, 200, 255)).getRGB());
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum mode2 {
/* 253 */     Static, StateBased, Astolfo;
/*     */   }
/*     */   
/*     */   public static abstract class Indicator {
/* 257 */     DynamicAnimation animation = new DynamicAnimation();
/*     */     
/*     */     void update() {
/* 260 */       this.animation.setValue(Math.max(getProgress(), 0.0D));
/* 261 */       this.animation.update();
/*     */     }
/*     */     
/*     */     double progress() {
/* 265 */       return this.animation.getValue();
/*     */     }
/*     */     
/*     */     abstract boolean enabled();
/*     */     
/*     */     abstract String getName();
/*     */     
/*     */     abstract double getProgress();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\Indicators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */