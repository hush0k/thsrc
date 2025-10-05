/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ItemPhysics
/*     */   extends Module
/*     */ {
/*     */   public static long Field1898;
/*     */   private static double Field1899;
/*  24 */   private static final Random Field1900 = new Random();
/*  25 */   public Setting<Float> rotatespeed = register(new Setting("RotateSpeed", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*     */ 
/*     */   
/*     */   public ItemPhysics() {
/*  29 */     super("ItemPhysics", "описание", Module.Category.RENDER);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int Method2280(ItemStack itemStack) {
/*  34 */     int n = 1;
/*  35 */     if (itemStack.func_190916_E() > 48) {
/*  36 */       n = 5;
/*  37 */     } else if (itemStack.func_190916_E() > 32) {
/*  38 */       n = 4;
/*  39 */     } else if (itemStack.func_190916_E() > 16) {
/*  40 */       n = 3;
/*  41 */     } else if (itemStack.func_190916_E() > 1) {
/*  42 */       n = 2;
/*     */     } 
/*  44 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void Method2279(Entity entity, double d, double d2, double d3) {
/*  50 */     Field1899 = (System.nanoTime() - Field1898) / 2500000.0D * ((Float)this.rotatespeed.getValue()).floatValue();
/*  51 */     if (!mc.field_71415_G)
/*  52 */       Field1899 = 0.0D;  EntityItem entityItem;
/*     */     ItemStack itemStack;
/*  54 */     int n = ((itemStack = (entityItem = (EntityItem)entity).func_92059_d()) != null && itemStack.func_77973_b() != null) ? (Item.func_150891_b(itemStack.func_77973_b()) + itemStack.func_77960_j()) : 187;
/*  55 */     Field1900.setSeed(n);
/*  56 */     mc.func_110434_K().func_110577_a(TextureMap.field_110575_b);
/*  57 */     mc.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174936_b(false, false);
/*  58 */     GlStateManager.func_179091_B();
/*  59 */     GlStateManager.func_179092_a(516, 0.1F);
/*  60 */     GlStateManager.func_179147_l();
/*  61 */     RenderHelper.func_74519_b();
/*  62 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/*  63 */     GlStateManager.func_179094_E();
/*  64 */     IBakedModel iBakedModel = mc.func_175599_af().func_175037_a().func_178089_a(itemStack);
/*  65 */     boolean bl = iBakedModel.func_177556_c();
/*  66 */     boolean bl2 = iBakedModel.func_177556_c();
/*  67 */     int n2 = Method2280(itemStack);
/*  68 */     GlStateManager.func_179109_b((float)d, (float)d2, (float)d3);
/*  69 */     if (iBakedModel.func_177556_c()) {
/*  70 */       GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F);
/*     */     }
/*  72 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/*  73 */     GL11.glRotatef(entityItem.field_70177_z, 0.0F, 0.0F, 1.0F);
/*  74 */     GlStateManager.func_179137_b(0.0D, 0.0D, bl2 ? -0.08D : -0.04D);
/*  75 */     if (bl2 || (mc.func_175598_ae()).field_78733_k != null) {
/*     */       
/*  77 */       if (bl2) {
/*  78 */         if (!entityItem.field_70122_E) {
/*  79 */           double d4 = Field1899 * 2.0D;
/*  80 */           entityItem.field_70125_A = (float)(entityItem.field_70125_A + d4);
/*     */         } 
/*  82 */       } else if (!Double.isNaN(entityItem.field_70165_t) && !Double.isNaN(entityItem.field_70163_u) && !Double.isNaN(entityItem.field_70161_v) && entityItem.field_70170_p != null) {
/*  83 */         if (entityItem.field_70122_E) {
/*  84 */           entityItem.field_70125_A = 0.0F;
/*     */         } else {
/*  86 */           double d4 = Field1899 * 2.0D;
/*  87 */           entityItem.field_70125_A = (float)(entityItem.field_70125_A + d4);
/*     */         } 
/*     */       } 
/*  90 */       GlStateManager.func_179114_b(entityItem.field_70125_A, 1.0F, 0.0F, 0.0F);
/*     */     } 
/*  92 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  93 */     for (int i = 0; i < n2; i++) {
/*  94 */       GlStateManager.func_179094_E();
/*  95 */       if (bl) {
/*  96 */         if (i > 0) {
/*  97 */           float f = (Field1900.nextFloat() * 2.0F - 1.0F) * 0.15F;
/*  98 */           float f2 = (Field1900.nextFloat() * 2.0F - 1.0F) * 0.15F;
/*  99 */           float f3 = (Field1900.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 100 */           GlStateManager.func_179109_b(f, f2, f3);
/*     */         } 
/* 102 */         mc.func_175599_af().func_180454_a(itemStack, iBakedModel);
/* 103 */         GlStateManager.func_179121_F();
/*     */       } else {
/*     */         
/* 106 */         mc.func_175599_af().func_180454_a(itemStack, iBakedModel);
/* 107 */         GlStateManager.func_179121_F();
/* 108 */         GlStateManager.func_179109_b(0.0F, 0.0F, 0.05375F);
/*     */       } 
/* 110 */     }  GlStateManager.func_179121_F();
/* 111 */     GlStateManager.func_179101_C();
/* 112 */     GlStateManager.func_179084_k();
/* 113 */     mc.func_110434_K().func_110577_a(TextureMap.field_110575_b);
/* 114 */     mc.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174935_a();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent e) {
/* 119 */     Field1898 = System.nanoTime();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\ItemPhysics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */