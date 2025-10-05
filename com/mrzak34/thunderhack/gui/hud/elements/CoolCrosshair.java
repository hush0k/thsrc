/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.RenderAttackIndicatorEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.EZbowPOP;
/*     */ import com.mrzak34.thunderhack.modules.misc.Timer;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.phobos.IEntityLivingBase;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CoolCrosshair
/*     */   extends Module
/*     */ {
/*  26 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  27 */   private final Setting<Boolean> smt = register(new Setting("smooth", Boolean.FALSE));
/*  28 */   public Setting<Float> car = register(new Setting("otstup", Float.valueOf(0.0F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*  29 */   public Setting<Float> lwid = register(new Setting("otstup2", Float.valueOf(0.0F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*  30 */   public Setting<Float> rounded2 = register(new Setting("Round2", Float.valueOf(0.0F), Float.valueOf(0.5F), Float.valueOf(20.0F)));
/*  31 */   int status = 0;
/*  32 */   int santi = 0;
/*  33 */   float animation = 0.0F;
/*     */ 
/*     */   
/*     */   public CoolCrosshair() {
/*  37 */     super("CoolCrosshair", "CoolCrosshair", Module.Category.HUD);
/*     */   }
/*     */   
/*     */   public static void drawPartialCircle(float x, float y, float radius, int startAngle, int endAngle, float thickness, Color colour, boolean smooth) {
/*  41 */     GL11.glDisable(3553);
/*  42 */     GL11.glBlendFunc(770, 771);
/*  43 */     if (startAngle > endAngle) {
/*  44 */       int temp = startAngle;
/*  45 */       startAngle = endAngle;
/*  46 */       endAngle = temp;
/*     */     } 
/*  48 */     if (startAngle < 0)
/*  49 */       startAngle = 0; 
/*  50 */     if (endAngle > 360)
/*  51 */       endAngle = 360; 
/*  52 */     if (smooth) {
/*  53 */       GL11.glEnable(2848);
/*     */     } else {
/*  55 */       GL11.glDisable(2848);
/*     */     } 
/*  57 */     GL11.glLineWidth(thickness);
/*  58 */     GL11.glColor4f(colour.getRed() / 255.0F, colour.getGreen() / 255.0F, colour.getBlue() / 255.0F, colour.getAlpha() / 255.0F);
/*  59 */     GL11.glBegin(3);
/*  60 */     float ratio = 0.01745328F; int i;
/*  61 */     for (i = startAngle; i <= endAngle; i++) {
/*  62 */       float radians = (i - 90) * ratio;
/*  63 */       GL11.glVertex2f(x + (float)Math.cos(radians) * radius, y + (float)Math.sin(radians) * radius);
/*     */     } 
/*  65 */     GL11.glEnd();
/*  66 */     GL11.glEnable(3553);
/*  67 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderAttackIndicator(RenderAttackIndicatorEvent event) {
/*  72 */     event.setCanceled(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  77 */     if ((float)EZbowPOP.delayTimer.getPassedTimeMs() < ((Float)((EZbowPOP)Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class)).delay.getValue()).floatValue() * 1000.0F && 
/*  78 */       this.animation < 20.0F) {
/*  79 */       this.animation++;
/*     */     }
/*     */ 
/*     */     
/*  83 */     if (this.santi < this.status) {
/*  84 */       this.santi += 60;
/*     */     }
/*  86 */     if (this.status < this.santi) {
/*  87 */       this.santi -= 360;
/*     */     }
/*  89 */     if (this.santi < 0) {
/*  90 */       this.santi = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent event) {
/*  97 */     super.onRender2D(event);
/*     */     
/*  99 */     float x1 = (float)(event.scaledResolution.func_78327_c() / 2.0D);
/* 100 */     float y1 = (float)(event.scaledResolution.func_78324_d() / 2.0D);
/*     */ 
/*     */     
/* 103 */     boolean blend = GL11.glIsEnabled(3042);
/* 104 */     GL11.glEnable(3042);
/*     */     
/* 106 */     if ((float)EZbowPOP.delayTimer.getPassedTimeMs() > ((Float)((EZbowPOP)Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class)).delay.getValue()).floatValue() * 1000.0F || mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151031_f) {
/* 107 */       this.animation = 0.0F;
/* 108 */       this.status = (getCooledAttackStrength() == 0.0F) ? 0 : (int)(360.0F / 1.0F / getCooledAttackStrength());
/* 109 */       drawPartialCircle(x1, y1, ((Float)this.rounded2.getValue()).floatValue(), 0, 360, ((Float)this.lwid.getValue()).floatValue(), ((ColorSetting)this.color.getValue()).withAlpha((((ColorSetting)this.color.getValue()).getAlpha() > 210) ? ((ColorSetting)this.color.getValue()).getAlpha() : (((ColorSetting)this.color.getValue()).getAlpha() + 40)).getColorObject(), ((Boolean)this.smt.getValue()).booleanValue());
/* 110 */       drawPartialCircle(x1, y1, ((Float)this.rounded2.getValue()).floatValue() - ((Float)this.car.getValue()).floatValue(), 0, 360, ((Float)this.lwid.getValue()).floatValue(), ((ColorSetting)this.color.getValue()).withAlpha((((ColorSetting)this.color.getValue()).getAlpha() > 210) ? ((ColorSetting)this.color.getValue()).getAlpha() : (((ColorSetting)this.color.getValue()).getAlpha() + 40)).getColorObject(), ((Boolean)this.smt.getValue()).booleanValue());
/* 111 */       drawPartialCircle(x1, y1, ((Float)this.rounded2.getValue()).floatValue() + ((Float)this.car.getValue()).floatValue(), 0, 360, ((Float)this.lwid.getValue()).floatValue(), ((ColorSetting)this.color.getValue()).withAlpha((((ColorSetting)this.color.getValue()).getAlpha() > 210) ? ((ColorSetting)this.color.getValue()).getAlpha() : (((ColorSetting)this.color.getValue()).getAlpha() + 40)).getColorObject(), ((Boolean)this.smt.getValue()).booleanValue());
/* 112 */       drawPartialCircle(x1, y1, ((Float)this.rounded2.getValue()).floatValue(), 0, this.status, ((Float)this.lwid.getValue()).floatValue(), PaletteHelper.astolfo(false, 1), ((Boolean)this.smt.getValue()).booleanValue());
/* 113 */       drawPartialCircle(x1, y1, ((Float)this.rounded2.getValue()).floatValue() - ((Float)this.car.getValue()).floatValue(), 0, this.status, ((Float)this.lwid.getValue()).floatValue(), PaletteHelper.astolfo(false, 1), ((Boolean)this.smt.getValue()).booleanValue());
/* 114 */       drawPartialCircle(x1, y1, ((Float)this.rounded2.getValue()).floatValue() + ((Float)this.car.getValue()).floatValue(), 0, this.status, ((Float)this.lwid.getValue()).floatValue(), PaletteHelper.astolfo(false, 1), ((Boolean)this.smt.getValue()).booleanValue());
/*     */     } else {
/*     */       
/* 117 */       if (this.animation < 20.0F) {
/* 118 */         this.animation++;
/*     */       }
/* 120 */       RoundedShader.drawRound(x1 - this.animation, y1 - 3.0F, this.animation * 2.0F, 6.0F, 4.0F, new Color(657930));
/* 121 */       RenderUtil.glScissor(x1 - this.animation, y1 - 3.0F, x1 + this.animation * 2.0F, x1 + 6.0F, event.scaledResolution);
/* 122 */       GL11.glEnable(3089);
/*     */       
/* 124 */       if ((float)EZbowPOP.delayTimer.getPassedTimeMs() > ((Float)((EZbowPOP)Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class)).delay.getValue()).floatValue() * 666.0F) {
/* 125 */         FontRender.drawCentString5("charging.  ", x1, y1 - 0.5F, -1);
/* 126 */       } else if ((float)EZbowPOP.delayTimer.getPassedTimeMs() > ((Float)((EZbowPOP)Thunderhack.moduleManager.getModuleByClass(EZbowPOP.class)).delay.getValue()).floatValue() * 333.0F) {
/* 127 */         FontRender.drawCentString5("charging.. ", x1, y1 - 0.5F, -1);
/*     */       } else {
/* 129 */         FontRender.drawCentString5("charging...", x1, y1 - 0.5F, -1);
/*     */       } 
/* 131 */       GL11.glDisable(3089);
/*     */     } 
/*     */ 
/*     */     
/* 135 */     if (!blend) {
/* 136 */       GL11.glDisable(3042);
/*     */     }
/*     */   }
/*     */   
/*     */   private float getCooledAttackStrength() {
/* 141 */     return MathHelper.func_76131_a(((IEntityLivingBase)mc.field_71439_g).getTicksSinceLastSwing() / getCooldownPeriod(), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float getCooldownPeriod() {
/* 145 */     return (float)(1.0D / mc.field_71439_g.func_110148_a(SharedMonsterAttributes.field_188790_f).func_111126_e() * (((Timer)Thunderhack.moduleManager.getModuleByClass(Timer.class)).isOn() ? (20.0F * ((Float)((Timer)Thunderhack.moduleManager.getModuleByClass(Timer.class)).speed.getValue()).floatValue()) : 20.0D));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\CoolCrosshair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */