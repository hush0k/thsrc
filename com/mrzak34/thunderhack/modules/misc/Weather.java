/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ public class Weather
/*     */   extends Module {
/*  21 */   private static final Random RANDOM = new Random();
/*  22 */   private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
/*  23 */   private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");
/*  24 */   private final Setting<Integer> height = register(new Setting("Height", Integer.valueOf(80), Integer.valueOf(0), Integer.valueOf(255)));
/*  25 */   private final Setting<Float> strength = register(new Setting("Strength", Float.valueOf(0.8F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/*  26 */   public Setting<Boolean> snow = register(new Setting("Snow", Boolean.valueOf(true)));
/*     */   public Weather() {
/*  28 */     super("Weather", "изменяет погоду в мире-на клиентской стороне", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float partialTicks) {
/*  33 */     float f = ((Float)this.strength.getValue()).floatValue();
/*     */     
/*  35 */     EntityRenderer renderer = mc.field_71460_t;
/*  36 */     renderer.func_180436_i();
/*  37 */     this; Entity entity = mc.func_175606_aa();
/*  38 */     if (entity == null) {
/*     */       return;
/*     */     }
/*     */     
/*  42 */     this; WorldClient worldClient = mc.field_71441_e;
/*  43 */     int i = MathHelper.func_76128_c(entity.field_70165_t);
/*  44 */     int j = MathHelper.func_76128_c(entity.field_70163_u);
/*  45 */     int k = MathHelper.func_76128_c(entity.field_70161_v);
/*  46 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  47 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  48 */     GlStateManager.func_179129_p();
/*  49 */     GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
/*  50 */     GlStateManager.func_179147_l();
/*  51 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  52 */     GlStateManager.func_179092_a(516, 0.1F);
/*  53 */     double d0 = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * partialTicks;
/*  54 */     double d1 = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * partialTicks;
/*  55 */     double d2 = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * partialTicks;
/*  56 */     int l = MathHelper.func_76128_c(d1);
/*  57 */     int i1 = 5;
/*     */     
/*  59 */     this; if (mc.field_71474_y.field_74347_j) {
/*  60 */       i1 = 10;
/*     */     }
/*     */     
/*  63 */     int j1 = -1;
/*  64 */     float f1 = ((IEntityRenderer)renderer).getRendererUpdateCount() + partialTicks;
/*  65 */     bufferbuilder.func_178969_c(-d0, -d1, -d2);
/*  66 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/*  68 */     for (int k1 = k - i1; k1 <= k + i1; k1++) {
/*  69 */       for (int l1 = i - i1; l1 <= i + i1; l1++) {
/*  70 */         int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
/*  71 */         double d3 = ((IEntityRenderer)renderer).getRainXCoords()[i2] * 0.5D;
/*  72 */         double d4 = ((IEntityRenderer)renderer).getRainYCoords()[i2] * 0.5D;
/*  73 */         blockpos$mutableblockpos.func_181079_c(l1, 0, k1);
/*  74 */         Biome biome = worldClient.func_180494_b((BlockPos)blockpos$mutableblockpos);
/*     */         
/*  76 */         int j2 = ((Integer)this.height.getValue()).intValue();
/*  77 */         int k2 = j - i1;
/*  78 */         int l2 = j + i1;
/*     */         
/*  80 */         if (k2 < j2) {
/*  81 */           k2 = j2;
/*     */         }
/*     */         
/*  84 */         if (l2 < j2) {
/*  85 */           l2 = j2;
/*     */         }
/*     */         
/*  88 */         int i3 = Math.max(j2, l);
/*     */         
/*  90 */         if (k2 != l2) {
/*  91 */           RANDOM.setSeed(l1 * l1 * 3121L + l1 * 45238971L ^ k1 * k1 * 418711L + k1 * 13761L);
/*  92 */           blockpos$mutableblockpos.func_181079_c(l1, k2, k1);
/*  93 */           float f2 = biome.func_180626_a((BlockPos)blockpos$mutableblockpos);
/*     */           
/*  95 */           if (!((Boolean)this.snow.getValue()).booleanValue()) {
/*  96 */             if (j1 != 0) {
/*  97 */               if (j1 >= 0) {
/*  98 */                 tessellator.func_78381_a();
/*     */               }
/*     */               
/* 101 */               j1 = 0;
/* 102 */               this; mc.func_110434_K().func_110577_a(RAIN_TEXTURES);
/* 103 */               bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181704_d);
/*     */             } 
/*     */             
/* 106 */             double d5 = -((((IEntityRenderer)renderer).getRendererUpdateCount() + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 0x1F) + partialTicks) / 32.0D * (3.0D + RANDOM.nextDouble());
/* 107 */             double d6 = (l1 + 0.5F) - entity.field_70165_t;
/* 108 */             double d7 = (k1 + 0.5F) - entity.field_70161_v;
/* 109 */             float f3 = MathHelper.func_76133_a(d6 * d6 + d7 * d7) / i1;
/* 110 */             float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * f;
/* 111 */             blockpos$mutableblockpos.func_181079_c(l1, i3, k1);
/* 112 */             int j3 = worldClient.func_175626_b((BlockPos)blockpos$mutableblockpos, 0);
/* 113 */             int k3 = j3 >> 16 & 0xFFFF;
/* 114 */             int l3 = j3 & 0xFFFF;
/* 115 */             bufferbuilder.func_181662_b(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).func_187315_a(0.0D, k2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_187314_a(k3, l3).func_181675_d();
/* 116 */             bufferbuilder.func_181662_b(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).func_187315_a(1.0D, k2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_187314_a(k3, l3).func_181675_d();
/* 117 */             bufferbuilder.func_181662_b(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).func_187315_a(1.0D, l2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_187314_a(k3, l3).func_181675_d();
/* 118 */             bufferbuilder.func_181662_b(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).func_187315_a(0.0D, l2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_187314_a(k3, l3).func_181675_d();
/*     */           } else {
/* 120 */             if (j1 != 1) {
/* 121 */               if (j1 >= 0) {
/* 122 */                 tessellator.func_78381_a();
/*     */               }
/*     */               
/* 125 */               j1 = 1;
/* 126 */               this; mc.func_110434_K().func_110577_a(SNOW_TEXTURES);
/* 127 */               bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181704_d);
/*     */             } 
/*     */             
/* 130 */             double d8 = (-((((IEntityRenderer)renderer).getRendererUpdateCount() & 0x1FF) + partialTicks) / 512.0F);
/* 131 */             double d9 = RANDOM.nextDouble() + f1 * 0.01D * (float)RANDOM.nextGaussian();
/* 132 */             double d10 = RANDOM.nextDouble() + (f1 * (float)RANDOM.nextGaussian()) * 0.001D;
/* 133 */             double d11 = (l1 + 0.5F) - entity.field_70165_t;
/* 134 */             double d12 = (k1 + 0.5F) - entity.field_70161_v;
/* 135 */             float f6 = MathHelper.func_76133_a(d11 * d11 + d12 * d12) / i1;
/* 136 */             float f5 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f;
/* 137 */             blockpos$mutableblockpos.func_181079_c(l1, i3, k1);
/* 138 */             int i4 = (worldClient.func_175626_b((BlockPos)blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
/* 139 */             int j4 = i4 >> 16 & 0xFFFF;
/* 140 */             int k4 = i4 & 0xFFFF;
/* 141 */             bufferbuilder.func_181662_b(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).func_187315_a(0.0D + d9, k2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f5).func_187314_a(j4, k4).func_181675_d();
/* 142 */             bufferbuilder.func_181662_b(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).func_187315_a(1.0D + d9, k2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f5).func_187314_a(j4, k4).func_181675_d();
/* 143 */             bufferbuilder.func_181662_b(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).func_187315_a(1.0D + d9, l2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f5).func_187314_a(j4, k4).func_181675_d();
/* 144 */             bufferbuilder.func_181662_b(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).func_187315_a(0.0D + d9, l2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f5).func_187314_a(j4, k4).func_181675_d();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     if (j1 >= 0) {
/* 151 */       tessellator.func_78381_a();
/*     */     }
/*     */     
/* 154 */     bufferbuilder.func_178969_c(0.0D, 0.0D, 0.0D);
/* 155 */     GlStateManager.func_179089_o();
/* 156 */     GlStateManager.func_179084_k();
/* 157 */     GlStateManager.func_179092_a(516, 0.1F);
/* 158 */     renderer.func_175072_h();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\Weather.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */