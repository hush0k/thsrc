/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.CrystalRenderEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CrystalChams
/*     */   extends Module
/*     */ {
/*  16 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(3649978)));
/*  17 */   public final Setting<ColorSetting> wireFrameColor = register(new Setting("WireframeColor", new ColorSetting(3649978)));
/*  18 */   public Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(6.0F)));
/*  19 */   public Setting<Boolean> chams = register(new Setting("Chams", Boolean.valueOf(true)));
/*  20 */   public Setting<Boolean> throughWalls = register(new Setting("ThroughWalls", Boolean.valueOf(true)));
/*  21 */   public Setting<Boolean> wireframe = register(new Setting("Wireframe", Boolean.valueOf(true)));
/*  22 */   public Setting<Boolean> wireWalls = register(new Setting("WireThroughWalls", Boolean.valueOf(true)));
/*  23 */   public Setting<Boolean> texture = register(new Setting("Texture", Boolean.valueOf(false)));
/*  24 */   private final Setting<ChamsMode> mode = register(new Setting("Mode", ChamsMode.Normal));
/*     */   public CrystalChams() {
/*  26 */     super("CrystalChams", "CrystalChams", Module.Category.MISC);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderCrystal(CrystalRenderEvent.Pre e) {
/*  31 */     if (!((Boolean)this.texture.getValue()).booleanValue()) {
/*  32 */       e.setCanceled(true);
/*     */     }
/*     */     
/*  35 */     if (this.mode.getValue() == ChamsMode.Gradient) {
/*  36 */       GL11.glPushAttrib(1048575);
/*  37 */       GL11.glEnable(3042);
/*  38 */       GL11.glDisable(2896);
/*  39 */       GL11.glDisable(3553);
/*  40 */       float alpha = ((ColorSetting)this.color.getValue()).getAlpha() / 255.0F;
/*  41 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
/*  42 */       e.getModel().func_78088_a(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
/*  43 */       GL11.glEnable(3553);
/*     */       
/*  45 */       GL11.glBlendFunc(770, 771);
/*  46 */       float f = (e.getEntity()).field_70173_aa + mc.func_184121_ak();
/*  47 */       mc.func_110434_K().func_110577_a(new ResourceLocation("textures/rainbow.png"));
/*  48 */       mc.field_71460_t.func_191514_d(true);
/*  49 */       GlStateManager.func_179147_l();
/*  50 */       GlStateManager.func_179143_c(514);
/*  51 */       GlStateManager.func_179132_a(false);
/*  52 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, alpha);
/*     */       
/*  54 */       for (int i = 0; i < 2; i++) {
/*  55 */         GlStateManager.func_179140_f();
/*  56 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, alpha);
/*  57 */         GlStateManager.func_179128_n(5890);
/*  58 */         GlStateManager.func_179096_D();
/*  59 */         GlStateManager.func_179152_a(0.33333334F, 0.33333334F, 0.33333334F);
/*  60 */         GlStateManager.func_179114_b(30.0F - i * 60.0F, 0.0F, 0.0F, 0.5F);
/*  61 */         GlStateManager.func_179109_b(0.0F, f * (0.001F + i * 0.003F) * 20.0F, 0.0F);
/*  62 */         GlStateManager.func_179128_n(5888);
/*  63 */         e.getModel().func_78088_a(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
/*     */       } 
/*     */       
/*  66 */       GlStateManager.func_179128_n(5890);
/*  67 */       GlStateManager.func_179096_D();
/*  68 */       GlStateManager.func_179128_n(5888);
/*  69 */       GlStateManager.func_179145_e();
/*  70 */       GlStateManager.func_179132_a(true);
/*  71 */       GlStateManager.func_179143_c(515);
/*  72 */       GlStateManager.func_179084_k();
/*  73 */       mc.field_71460_t.func_191514_d(false);
/*  74 */       GL11.glPopAttrib();
/*     */     } else {
/*  76 */       if (((Boolean)this.wireframe.getValue()).booleanValue()) {
/*  77 */         Color wireColor = ((ColorSetting)this.wireFrameColor.getValue()).getColorObject();
/*  78 */         GL11.glPushAttrib(1048575);
/*  79 */         GL11.glEnable(3042);
/*  80 */         GL11.glDisable(3553);
/*  81 */         GL11.glDisable(2896);
/*  82 */         GL11.glBlendFunc(770, 771);
/*  83 */         GL11.glPolygonMode(1032, 6913);
/*  84 */         GL11.glLineWidth(((Float)this.lineWidth.getValue()).floatValue());
/*  85 */         if (((Boolean)this.wireWalls.getValue()).booleanValue()) {
/*  86 */           GL11.glDepthMask(false);
/*  87 */           GL11.glDisable(2929);
/*     */         } 
/*     */         
/*  90 */         GL11.glColor4f(wireColor.getRed() / 255.0F, wireColor
/*  91 */             .getGreen() / 255.0F, wireColor
/*  92 */             .getBlue() / 255.0F, wireColor
/*  93 */             .getAlpha() / 255.0F);
/*  94 */         e.getModel().func_78088_a(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e
/*  95 */             .getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
/*  96 */         GL11.glPopAttrib();
/*     */       } 
/*     */       
/*  99 */       if (((Boolean)this.chams.getValue()).booleanValue()) {
/* 100 */         Color chamsColor = ((ColorSetting)this.color.getValue()).getColorObject();
/* 101 */         GL11.glPushAttrib(1048575);
/* 102 */         GL11.glEnable(3042);
/* 103 */         GL11.glDisable(3553);
/* 104 */         GL11.glDisable(2896);
/* 105 */         GL11.glDisable(3008);
/* 106 */         GL11.glBlendFunc(770, 771);
/* 107 */         GL11.glEnable(2960);
/* 108 */         GL11.glEnable(10754);
/* 109 */         if (((Boolean)this.throughWalls.getValue()).booleanValue()) {
/* 110 */           GL11.glDepthMask(false);
/* 111 */           GL11.glDisable(2929);
/*     */         } 
/* 113 */         GL11.glColor4f(chamsColor.getRed() / 255.0F, chamsColor
/* 114 */             .getGreen() / 255.0F, chamsColor
/* 115 */             .getBlue() / 255.0F, chamsColor
/* 116 */             .getAlpha() / 255.0F);
/* 117 */         e.getModel().func_78088_a(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e
/* 118 */             .getAgeInTicks(), e.getNetHeadYaw(), e.getHeadPitch(), e.getScale());
/* 119 */         GL11.glPopAttrib();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum ChamsMode
/*     */   {
/* 126 */     Normal,
/* 127 */     Gradient;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\CrystalChams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */