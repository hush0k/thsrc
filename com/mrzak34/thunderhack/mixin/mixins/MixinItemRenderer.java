/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.RenderItemEvent;
/*     */ import com.mrzak34.thunderhack.modules.render.Animations;
/*     */ import com.mrzak34.thunderhack.modules.render.NoRender;
/*     */ import com.mrzak34.thunderhack.modules.render.ViewModel;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin(value = {ItemRenderer.class}, priority = 9998)
/*     */ public abstract class MixinItemRenderer
/*     */ {
/*     */   @Shadow
/*     */   public ItemStack field_187468_e;
/*     */   @Shadow
/*     */   public float field_187470_g;
/*     */   @Shadow
/*     */   public float field_187469_f;
/*     */   private float spin;
/*     */   
/*     */   @Inject(method = {"transformSideFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void transformSideFirstPersonHook(EnumHandSide hand, float p_187459_2_, CallbackInfo cancel) {
/*  45 */     RenderItemEvent event = new RenderItemEvent(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
/*  46 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*  47 */     if (ViewModel.getInstance().isEnabled()) {
/*  48 */       boolean bob = (ViewModel.getInstance().isDisabled() || ((Boolean)(ViewModel.getInstance()).doBob.getValue()).booleanValue());
/*  49 */       int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
/*     */       
/*  51 */       if (!((Boolean)(ViewModel.getInstance()).XBob.getValue()).booleanValue()) {
/*  52 */         GlStateManager.func_179109_b(i * 0.56F, -0.52F + (bob ? p_187459_2_ : 0.0F) * -0.6F, -0.72F);
/*     */       } else {
/*  54 */         GlStateManager.func_179109_b(i * 0.56F, -0.52F, -0.72F - p_187459_2_ * -((Float)(ViewModel.getInstance()).zbobcorr.getValue()).floatValue());
/*     */       } 
/*     */       
/*  57 */       if (hand == EnumHandSide.RIGHT) {
/*  58 */         GlStateManager.func_179109_b(event.getMainX(), event.getMainY(), event.getMainZ());
/*  59 */         RenderUtil.rotationHelper(event.getMainRotX(), event.getMainRotY(), event.getMainRotZ());
/*     */       } else {
/*  61 */         GlStateManager.func_179109_b(event.getOffX(), event.getOffY(), event.getOffZ());
/*  62 */         RenderUtil.rotationHelper(event.getOffRotX(), event.getOffRotY(), event.getOffRotZ());
/*     */       } 
/*  64 */       cancel.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderFireInFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderFireInFirstPersonHook(CallbackInfo info) {
/*  70 */     if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).fire.getValue()).booleanValue()) {
/*  71 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"transformEatFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void transformEatFirstPersonHook(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo cancel) {
/*  77 */     if (ViewModel.getInstance().isEnabled()) {
/*  78 */       if (!((Boolean)(ViewModel.getInstance()).noEatAnimation.getValue()).booleanValue()) {
/*  79 */         float f = (Minecraft.func_71410_x()).field_71439_g.func_184605_cv() - p_187454_1_ + 1.0F;
/*  80 */         float f1 = f / stack.func_77988_m();
/*     */         
/*  82 */         if (f1 < 0.8F) {
/*  83 */           float f2 = MathHelper.func_76135_e(MathHelper.func_76134_b(f / 4.0F * 3.1415927F) * 0.1F);
/*  84 */           GlStateManager.func_179109_b(0.0F, f2, 0.0F);
/*     */         } 
/*  86 */         if (Thunderhack.class.getName().length() != 35) {
/*  87 */           Minecraft.func_71410_x().func_71400_g();
/*     */         }
/*  89 */         float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
/*  90 */         int i = (hand == EnumHandSide.RIGHT) ? 1 : -1;
/*  91 */         GlStateManager.func_179109_b(f3 * 0.6F * i * ((Float)(ViewModel.getInstance()).eatX.getValue()).floatValue(), f3 * 0.5F * -((Float)(ViewModel.getInstance()).eatY.getValue()).floatValue(), 0.0F);
/*  92 */         GlStateManager.func_179114_b(i * f3 * 90.0F, 0.0F, 1.0F, 0.0F);
/*  93 */         GlStateManager.func_179114_b(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
/*  94 */         GlStateManager.func_179114_b(i * f3 * 30.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/*  96 */       cancel.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderSuffocationOverlay"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderSuffocationOverlay(CallbackInfo ci) {
/* 102 */     if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).blocks.getValue()).booleanValue()) {
/* 103 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_187459_b(EnumHandSide paramEnumHandSide, float paramFloat);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_187456_a(float paramFloat1, float paramFloat2, EnumHandSide paramEnumHandSide);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_187454_a(float paramFloat, EnumHandSide paramEnumHandSide, ItemStack paramItemStack);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_187453_a(EnumHandSide paramEnumHandSide, float paramFloat);
/*     */   
/*     */   @Shadow
/*     */   public abstract void func_187462_a(EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack, ItemCameraTransforms.TransformType paramTransformType, boolean paramBoolean);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_187465_a(float paramFloat1, EnumHandSide paramEnumHandSide, float paramFloat2, ItemStack paramItemStack);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_187463_a(float paramFloat1, float paramFloat2, float paramFloat3);
/*     */   
/*     */   @Inject(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderItemInFirstPersonHook(AbstractClientPlayer p_187457_1_, float p_187457_2_, float p_187457_3_, EnumHand p_187457_4_, float p_187457_5_, ItemStack p_187457_6_, float p_187457_7_, CallbackInfo ci) {
/* 130 */     if (((Animations)Thunderhack.moduleManager.getModuleByClass(Animations.class)).isEnabled()) {
/* 131 */       ci.cancel();
/* 132 */       renderAnimations(p_187457_1_, p_187457_2_, p_187457_3_, p_187457_4_, p_187457_5_, p_187457_6_, p_187457_7_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderAnimations(AbstractClientPlayer p_187457_1_, float p_187457_2_, float p_187457_3_, EnumHand p_187457_4_, float p_187457_5_, ItemStack p_187457_6_, float p_187457_7_) {
/*     */     // Byte code:
/*     */     //   0: aload #4
/*     */     //   2: getstatic net/minecraft/util/EnumHand.MAIN_HAND : Lnet/minecraft/util/EnumHand;
/*     */     //   5: if_acmpne -> 12
/*     */     //   8: iconst_1
/*     */     //   9: goto -> 13
/*     */     //   12: iconst_0
/*     */     //   13: istore #8
/*     */     //   15: iload #8
/*     */     //   17: ifeq -> 27
/*     */     //   20: aload_1
/*     */     //   21: invokevirtual func_184591_cq : ()Lnet/minecraft/util/EnumHandSide;
/*     */     //   24: goto -> 34
/*     */     //   27: aload_1
/*     */     //   28: invokevirtual func_184591_cq : ()Lnet/minecraft/util/EnumHandSide;
/*     */     //   31: invokevirtual func_188468_a : ()Lnet/minecraft/util/EnumHandSide;
/*     */     //   34: astore #9
/*     */     //   36: invokestatic func_179094_E : ()V
/*     */     //   39: aload #6
/*     */     //   41: invokevirtual func_190926_b : ()Z
/*     */     //   44: ifeq -> 72
/*     */     //   47: iload #8
/*     */     //   49: ifeq -> 1767
/*     */     //   52: aload_1
/*     */     //   53: invokevirtual func_82150_aj : ()Z
/*     */     //   56: ifne -> 1767
/*     */     //   59: aload_0
/*     */     //   60: fload #7
/*     */     //   62: fload #5
/*     */     //   64: aload #9
/*     */     //   66: invokevirtual func_187456_a : (FFLnet/minecraft/util/EnumHandSide;)V
/*     */     //   69: goto -> 1767
/*     */     //   72: aload #6
/*     */     //   74: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   77: instanceof net/minecraft/item/ItemMap
/*     */     //   80: ifeq -> 125
/*     */     //   83: iload #8
/*     */     //   85: ifeq -> 110
/*     */     //   88: aload_0
/*     */     //   89: getfield field_187468_e : Lnet/minecraft/item/ItemStack;
/*     */     //   92: invokevirtual func_190926_b : ()Z
/*     */     //   95: ifeq -> 110
/*     */     //   98: aload_0
/*     */     //   99: fload_3
/*     */     //   100: fload #7
/*     */     //   102: fload #5
/*     */     //   104: invokevirtual func_187463_a : (FFF)V
/*     */     //   107: goto -> 1767
/*     */     //   110: aload_0
/*     */     //   111: fload #7
/*     */     //   113: aload #9
/*     */     //   115: fload #5
/*     */     //   117: aload #6
/*     */     //   119: invokevirtual func_187465_a : (FLnet/minecraft/util/EnumHandSide;FLnet/minecraft/item/ItemStack;)V
/*     */     //   122: goto -> 1767
/*     */     //   125: aload #9
/*     */     //   127: getstatic net/minecraft/util/EnumHandSide.RIGHT : Lnet/minecraft/util/EnumHandSide;
/*     */     //   130: if_acmpne -> 137
/*     */     //   133: iconst_1
/*     */     //   134: goto -> 138
/*     */     //   137: iconst_0
/*     */     //   138: istore #10
/*     */     //   140: aload_1
/*     */     //   141: invokevirtual func_184587_cr : ()Z
/*     */     //   144: ifeq -> 479
/*     */     //   147: aload_1
/*     */     //   148: invokevirtual func_184605_cv : ()I
/*     */     //   151: ifle -> 479
/*     */     //   154: aload_1
/*     */     //   155: invokevirtual func_184600_cs : ()Lnet/minecraft/util/EnumHand;
/*     */     //   158: aload #4
/*     */     //   160: if_acmpne -> 479
/*     */     //   163: iload #10
/*     */     //   165: ifeq -> 172
/*     */     //   168: iconst_1
/*     */     //   169: goto -> 173
/*     */     //   172: iconst_m1
/*     */     //   173: istore #11
/*     */     //   175: getstatic com/mrzak34/thunderhack/mixin/mixins/MixinItemRenderer$1.$SwitchMap$net$minecraft$item$EnumAction : [I
/*     */     //   178: aload #6
/*     */     //   180: invokevirtual func_77975_n : ()Lnet/minecraft/item/EnumAction;
/*     */     //   183: invokevirtual ordinal : ()I
/*     */     //   186: iaload
/*     */     //   187: tableswitch default -> 476, 1 -> 220, 2 -> 231, 3 -> 231, 4 -> 251, 5 -> 262
/*     */     //   220: aload_0
/*     */     //   221: aload #9
/*     */     //   223: fload #7
/*     */     //   225: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   228: goto -> 476
/*     */     //   231: aload_0
/*     */     //   232: fload_2
/*     */     //   233: aload #9
/*     */     //   235: aload #6
/*     */     //   237: invokevirtual func_187454_a : (FLnet/minecraft/util/EnumHandSide;Lnet/minecraft/item/ItemStack;)V
/*     */     //   240: aload_0
/*     */     //   241: aload #9
/*     */     //   243: fload #7
/*     */     //   245: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   248: goto -> 476
/*     */     //   251: aload_0
/*     */     //   252: aload #9
/*     */     //   254: fload #7
/*     */     //   256: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   259: goto -> 476
/*     */     //   262: aload_0
/*     */     //   263: aload #9
/*     */     //   265: fload #7
/*     */     //   267: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   270: iload #11
/*     */     //   272: i2f
/*     */     //   273: ldc_w -0.2785682
/*     */     //   276: fmul
/*     */     //   277: ldc_w 0.18344387
/*     */     //   280: ldc_w 0.15731531
/*     */     //   283: invokestatic func_179109_b : (FFF)V
/*     */     //   286: ldc_w -13.935
/*     */     //   289: fconst_1
/*     */     //   290: fconst_0
/*     */     //   291: fconst_0
/*     */     //   292: invokestatic func_179114_b : (FFFF)V
/*     */     //   295: iload #11
/*     */     //   297: i2f
/*     */     //   298: ldc_w 35.3
/*     */     //   301: fmul
/*     */     //   302: fconst_0
/*     */     //   303: fconst_1
/*     */     //   304: fconst_0
/*     */     //   305: invokestatic func_179114_b : (FFFF)V
/*     */     //   308: iload #11
/*     */     //   310: i2f
/*     */     //   311: ldc_w -9.785
/*     */     //   314: fmul
/*     */     //   315: fconst_0
/*     */     //   316: fconst_0
/*     */     //   317: fconst_1
/*     */     //   318: invokestatic func_179114_b : (FFFF)V
/*     */     //   321: aload #6
/*     */     //   323: invokevirtual func_77988_m : ()I
/*     */     //   326: i2f
/*     */     //   327: getstatic com/mrzak34/thunderhack/util/Util.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   330: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   333: invokevirtual func_184605_cv : ()I
/*     */     //   336: i2f
/*     */     //   337: fload_2
/*     */     //   338: fsub
/*     */     //   339: fconst_1
/*     */     //   340: fadd
/*     */     //   341: fsub
/*     */     //   342: fstore #12
/*     */     //   344: fload #12
/*     */     //   346: ldc_w 20.0
/*     */     //   349: fdiv
/*     */     //   350: fstore #13
/*     */     //   352: fload #13
/*     */     //   354: fload #13
/*     */     //   356: fmul
/*     */     //   357: fload #13
/*     */     //   359: fconst_2
/*     */     //   360: fmul
/*     */     //   361: fadd
/*     */     //   362: ldc_w 3.0
/*     */     //   365: fdiv
/*     */     //   366: fstore #13
/*     */     //   368: fload #13
/*     */     //   370: fconst_1
/*     */     //   371: fcmpl
/*     */     //   372: ifle -> 378
/*     */     //   375: fconst_1
/*     */     //   376: fstore #13
/*     */     //   378: fload #13
/*     */     //   380: ldc 0.1
/*     */     //   382: fcmpl
/*     */     //   383: ifle -> 431
/*     */     //   386: fload #12
/*     */     //   388: ldc 0.1
/*     */     //   390: fsub
/*     */     //   391: ldc_w 1.3
/*     */     //   394: fmul
/*     */     //   395: invokestatic func_76126_a : (F)F
/*     */     //   398: fstore #14
/*     */     //   400: fload #13
/*     */     //   402: ldc 0.1
/*     */     //   404: fsub
/*     */     //   405: fstore #15
/*     */     //   407: fload #14
/*     */     //   409: fload #15
/*     */     //   411: fmul
/*     */     //   412: fstore #16
/*     */     //   414: fload #16
/*     */     //   416: fconst_0
/*     */     //   417: fmul
/*     */     //   418: fload #16
/*     */     //   420: ldc_w 0.004
/*     */     //   423: fmul
/*     */     //   424: fload #16
/*     */     //   426: fconst_0
/*     */     //   427: fmul
/*     */     //   428: invokestatic func_179109_b : (FFF)V
/*     */     //   431: fload #13
/*     */     //   433: fconst_0
/*     */     //   434: fmul
/*     */     //   435: fload #13
/*     */     //   437: fconst_0
/*     */     //   438: fmul
/*     */     //   439: fload #13
/*     */     //   441: ldc_w 0.04
/*     */     //   444: fmul
/*     */     //   445: invokestatic func_179109_b : (FFF)V
/*     */     //   448: fconst_1
/*     */     //   449: fconst_1
/*     */     //   450: fconst_1
/*     */     //   451: fload #13
/*     */     //   453: ldc_w 0.2
/*     */     //   456: fmul
/*     */     //   457: fadd
/*     */     //   458: invokestatic func_179152_a : (FFF)V
/*     */     //   461: iload #11
/*     */     //   463: i2f
/*     */     //   464: ldc_w 45.0
/*     */     //   467: fmul
/*     */     //   468: fconst_0
/*     */     //   469: ldc_w -1.0
/*     */     //   472: fconst_0
/*     */     //   473: invokestatic func_179114_b : (FFFF)V
/*     */     //   476: goto -> 1736
/*     */     //   479: ldc_w -0.4
/*     */     //   482: fload #5
/*     */     //   484: invokestatic func_76129_c : (F)F
/*     */     //   487: ldc 3.1415927
/*     */     //   489: fmul
/*     */     //   490: invokestatic func_76126_a : (F)F
/*     */     //   493: fmul
/*     */     //   494: fstore #11
/*     */     //   496: ldc_w 0.2
/*     */     //   499: fload #5
/*     */     //   501: invokestatic func_76129_c : (F)F
/*     */     //   504: ldc_w 6.2831855
/*     */     //   507: fmul
/*     */     //   508: invokestatic func_76126_a : (F)F
/*     */     //   511: fmul
/*     */     //   512: fstore #12
/*     */     //   514: ldc_w -0.2
/*     */     //   517: fload #5
/*     */     //   519: ldc 3.1415927
/*     */     //   521: fmul
/*     */     //   522: invokestatic func_76126_a : (F)F
/*     */     //   525: fmul
/*     */     //   526: fstore #13
/*     */     //   528: iload #10
/*     */     //   530: ifeq -> 537
/*     */     //   533: iconst_1
/*     */     //   534: goto -> 538
/*     */     //   537: iconst_m1
/*     */     //   538: istore #14
/*     */     //   540: fconst_1
/*     */     //   541: aload_0
/*     */     //   542: getfield field_187470_g : F
/*     */     //   545: aload_0
/*     */     //   546: getfield field_187469_f : F
/*     */     //   549: aload_0
/*     */     //   550: getfield field_187470_g : F
/*     */     //   553: fsub
/*     */     //   554: fload_2
/*     */     //   555: fmul
/*     */     //   556: fadd
/*     */     //   557: fsub
/*     */     //   558: fstore #15
/*     */     //   560: getstatic com/mrzak34/thunderhack/util/Util.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   563: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   566: fload_2
/*     */     //   567: invokevirtual func_70678_g : (F)F
/*     */     //   570: fstore #16
/*     */     //   572: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   575: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   577: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   580: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   583: getfield rMode : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   586: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   589: checkcast com/mrzak34/thunderhack/modules/render/Animations$rmode
/*     */     //   592: astore #17
/*     */     //   594: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   597: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   599: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   602: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   605: invokevirtual isEnabled : ()Z
/*     */     //   608: ifeq -> 1707
/*     */     //   611: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   614: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   616: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   619: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   622: getfield rMode : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   625: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   628: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.Slow : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   631: if_acmpeq -> 1707
/*     */     //   634: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   637: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   639: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   642: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   645: invokevirtual isEnabled : ()Z
/*     */     //   648: ifeq -> 677
/*     */     //   651: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   654: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   656: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   659: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   662: getfield auraOnly : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   665: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   668: checkcast java/lang/Boolean
/*     */     //   671: invokevirtual booleanValue : ()Z
/*     */     //   674: ifeq -> 731
/*     */     //   677: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   680: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   682: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   685: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   688: invokevirtual isEnabled : ()Z
/*     */     //   691: ifeq -> 1675
/*     */     //   694: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   697: ldc_w com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   700: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   703: checkcast com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   706: invokevirtual isEnabled : ()Z
/*     */     //   709: ifeq -> 1675
/*     */     //   712: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   715: ldc_w com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   718: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   721: checkcast com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   724: pop
/*     */     //   725: getstatic com/mrzak34/thunderhack/modules/combat/Aura.target : Lnet/minecraft/entity/EntityLivingBase;
/*     */     //   728: ifnull -> 1675
/*     */     //   731: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   734: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   736: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   739: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   742: invokevirtual isEnabled : ()Z
/*     */     //   745: ifeq -> 774
/*     */     //   748: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   751: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   753: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   756: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   759: getfield auraOnly : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   762: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   765: checkcast java/lang/Boolean
/*     */     //   768: invokevirtual booleanValue : ()Z
/*     */     //   771: ifeq -> 828
/*     */     //   774: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   777: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   779: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   782: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   785: invokevirtual isEnabled : ()Z
/*     */     //   788: ifeq -> 1656
/*     */     //   791: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   794: ldc_w com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   797: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   800: checkcast com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   803: invokevirtual isEnabled : ()Z
/*     */     //   806: ifeq -> 1656
/*     */     //   809: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   812: ldc_w com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   815: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   818: checkcast com/mrzak34/thunderhack/modules/combat/Aura
/*     */     //   821: pop
/*     */     //   822: getstatic com/mrzak34/thunderhack/modules/combat/Aura.target : Lnet/minecraft/entity/EntityLivingBase;
/*     */     //   825: ifnull -> 1656
/*     */     //   828: aload #9
/*     */     //   830: getstatic com/mrzak34/thunderhack/util/Util.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   833: getfield field_71474_y : Lnet/minecraft/client/settings/GameSettings;
/*     */     //   836: getfield field_186715_A : Lnet/minecraft/util/EnumHandSide;
/*     */     //   839: getstatic net/minecraft/util/EnumHandSide.LEFT : Lnet/minecraft/util/EnumHandSide;
/*     */     //   842: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   845: ifeq -> 854
/*     */     //   848: getstatic net/minecraft/util/EnumHandSide.RIGHT : Lnet/minecraft/util/EnumHandSide;
/*     */     //   851: goto -> 857
/*     */     //   854: getstatic net/minecraft/util/EnumHandSide.LEFT : Lnet/minecraft/util/EnumHandSide;
/*     */     //   857: if_acmpeq -> 1624
/*     */     //   860: aload #17
/*     */     //   862: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.Default : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   865: if_acmpne -> 929
/*     */     //   868: aload_0
/*     */     //   869: aload #9
/*     */     //   871: fload #7
/*     */     //   873: invokespecial transformSideFirstPerson2 : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   876: fload #16
/*     */     //   878: fload #16
/*     */     //   880: fmul
/*     */     //   881: ldc 3.1415927
/*     */     //   883: fmul
/*     */     //   884: invokestatic func_76126_a : (F)F
/*     */     //   887: fstore #18
/*     */     //   889: fload #16
/*     */     //   891: invokestatic func_76129_c : (F)F
/*     */     //   894: ldc 3.1415927
/*     */     //   896: fmul
/*     */     //   897: invokestatic func_76126_a : (F)F
/*     */     //   900: fstore #19
/*     */     //   902: fload #19
/*     */     //   904: ldc_w -20.0
/*     */     //   907: fmul
/*     */     //   908: fconst_0
/*     */     //   909: fconst_0
/*     */     //   910: fconst_2
/*     */     //   911: invokestatic func_179114_b : (FFFF)V
/*     */     //   914: fload #19
/*     */     //   916: ldc_w -75.0
/*     */     //   919: fmul
/*     */     //   920: fconst_1
/*     */     //   921: fconst_0
/*     */     //   922: fconst_0
/*     */     //   923: invokestatic func_179114_b : (FFFF)V
/*     */     //   926: goto -> 1736
/*     */     //   929: aload #17
/*     */     //   931: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.Swipe : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   934: if_acmpne -> 1018
/*     */     //   937: aload_0
/*     */     //   938: fload #15
/*     */     //   940: ldc_w 3.0
/*     */     //   943: fdiv
/*     */     //   944: fload #16
/*     */     //   946: invokespecial transformFirstPersonItem : (FF)V
/*     */     //   949: aload_0
/*     */     //   950: invokespecial translate : ()V
/*     */     //   953: fload #16
/*     */     //   955: fload #16
/*     */     //   957: fmul
/*     */     //   958: ldc 3.1415927
/*     */     //   960: fmul
/*     */     //   961: invokestatic func_76126_a : (F)F
/*     */     //   964: fstore #18
/*     */     //   966: fload #16
/*     */     //   968: invokestatic func_76129_c : (F)F
/*     */     //   971: ldc 3.1415927
/*     */     //   973: fmul
/*     */     //   974: invokestatic func_76126_a : (F)F
/*     */     //   977: fstore #19
/*     */     //   979: fload #18
/*     */     //   981: ldc_w -20.0
/*     */     //   984: fmul
/*     */     //   985: fconst_0
/*     */     //   986: fconst_1
/*     */     //   987: fconst_0
/*     */     //   988: invokestatic func_179114_b : (FFFF)V
/*     */     //   991: fload #19
/*     */     //   993: ldc_w -20.0
/*     */     //   996: fmul
/*     */     //   997: fconst_0
/*     */     //   998: fconst_0
/*     */     //   999: fconst_2
/*     */     //   1000: invokestatic func_179114_b : (FFFF)V
/*     */     //   1003: fload #19
/*     */     //   1005: ldc_w -75.0
/*     */     //   1008: fmul
/*     */     //   1009: fconst_1
/*     */     //   1010: fconst_0
/*     */     //   1011: fconst_0
/*     */     //   1012: invokestatic func_179114_b : (FFFF)V
/*     */     //   1015: goto -> 1736
/*     */     //   1018: aload #17
/*     */     //   1020: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.Rich : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   1023: if_acmpne -> 1091
/*     */     //   1026: aload_0
/*     */     //   1027: aload #9
/*     */     //   1029: fload #7
/*     */     //   1031: invokespecial transformSideFirstPerson2 : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1034: aload_0
/*     */     //   1035: invokespecial translate4 : ()V
/*     */     //   1038: fload #16
/*     */     //   1040: fload #16
/*     */     //   1042: fmul
/*     */     //   1043: ldc 3.1415927
/*     */     //   1045: fmul
/*     */     //   1046: invokestatic func_76126_a : (F)F
/*     */     //   1049: fstore #18
/*     */     //   1051: fload #16
/*     */     //   1053: invokestatic func_76129_c : (F)F
/*     */     //   1056: ldc 3.1415927
/*     */     //   1058: fmul
/*     */     //   1059: invokestatic func_76126_a : (F)F
/*     */     //   1062: fstore #19
/*     */     //   1064: fload #19
/*     */     //   1066: ldc_w -20.0
/*     */     //   1069: fmul
/*     */     //   1070: fconst_0
/*     */     //   1071: fconst_0
/*     */     //   1072: fconst_2
/*     */     //   1073: invokestatic func_179114_b : (FFFF)V
/*     */     //   1076: fload #19
/*     */     //   1078: ldc_w -75.0
/*     */     //   1081: fmul
/*     */     //   1082: fconst_1
/*     */     //   1083: fconst_0
/*     */     //   1084: fconst_0
/*     */     //   1085: invokestatic func_179114_b : (FFFF)V
/*     */     //   1088: goto -> 1736
/*     */     //   1091: aload #17
/*     */     //   1093: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.New : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   1096: if_acmpne -> 1168
/*     */     //   1099: aload_0
/*     */     //   1100: aload #9
/*     */     //   1102: fload #7
/*     */     //   1104: invokespecial transformSideFirstPerson2 : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1107: aload_0
/*     */     //   1108: invokespecial translate3 : ()V
/*     */     //   1111: fload #16
/*     */     //   1113: fload #16
/*     */     //   1115: fmul
/*     */     //   1116: ldc 3.1415927
/*     */     //   1118: fmul
/*     */     //   1119: invokestatic func_76126_a : (F)F
/*     */     //   1122: fstore #18
/*     */     //   1124: fload #16
/*     */     //   1126: invokestatic func_76129_c : (F)F
/*     */     //   1129: ldc 3.1415927
/*     */     //   1131: fmul
/*     */     //   1132: invokestatic func_76126_a : (F)F
/*     */     //   1135: fstore #19
/*     */     //   1137: fload #19
/*     */     //   1139: ldc_w -70.0
/*     */     //   1142: fmul
/*     */     //   1143: fload #19
/*     */     //   1145: ldc_w 40.0
/*     */     //   1148: fmul
/*     */     //   1149: fconst_0
/*     */     //   1150: fconst_0
/*     */     //   1151: invokestatic func_179114_b : (FFFF)V
/*     */     //   1154: ldc_w 40.0
/*     */     //   1157: ldc_w -30.0
/*     */     //   1160: fconst_0
/*     */     //   1161: fconst_0
/*     */     //   1162: invokestatic func_179114_b : (FFFF)V
/*     */     //   1165: goto -> 1736
/*     */     //   1168: aload #17
/*     */     //   1170: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.Oblique : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   1173: if_acmpne -> 1222
/*     */     //   1176: aload_0
/*     */     //   1177: aload #9
/*     */     //   1179: fload #7
/*     */     //   1181: invokespecial transformSideFirstPerson2 : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1184: fload #16
/*     */     //   1186: invokestatic func_76129_c : (F)F
/*     */     //   1189: ldc 3.1415927
/*     */     //   1191: fmul
/*     */     //   1192: invokestatic func_76126_a : (F)F
/*     */     //   1195: fstore #18
/*     */     //   1197: fload #18
/*     */     //   1199: ldc_w -70.0
/*     */     //   1202: fmul
/*     */     //   1203: fload #18
/*     */     //   1205: ldc_w 70.0
/*     */     //   1208: fmul
/*     */     //   1209: fconst_0
/*     */     //   1210: fload #18
/*     */     //   1212: ldc_w -90.0
/*     */     //   1215: fmul
/*     */     //   1216: invokestatic func_179114_b : (FFFF)V
/*     */     //   1219: goto -> 1736
/*     */     //   1222: aload #17
/*     */     //   1224: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.Glide : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   1227: if_acmpne -> 1246
/*     */     //   1230: aload_0
/*     */     //   1231: fload #15
/*     */     //   1233: fconst_2
/*     */     //   1234: fdiv
/*     */     //   1235: fconst_0
/*     */     //   1236: invokespecial transformFirstPersonItem : (FF)V
/*     */     //   1239: aload_0
/*     */     //   1240: invokespecial translate : ()V
/*     */     //   1243: goto -> 1736
/*     */     //   1246: aload #17
/*     */     //   1248: getstatic com/mrzak34/thunderhack/modules/render/Animations$rmode.Fap : Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   1251: if_acmpne -> 1736
/*     */     //   1254: aload_0
/*     */     //   1255: aload #9
/*     */     //   1257: fload #7
/*     */     //   1259: invokespecial transformSideFirstPerson2 : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1262: ldc_w 0.96
/*     */     //   1265: ldc_w -0.02
/*     */     //   1268: ldc_w -0.71999997
/*     */     //   1271: invokestatic func_179109_b : (FFF)V
/*     */     //   1274: ldc_w 45.0
/*     */     //   1277: fconst_0
/*     */     //   1278: fconst_1
/*     */     //   1279: fconst_0
/*     */     //   1280: invokestatic func_179114_b : (FFFF)V
/*     */     //   1283: fconst_0
/*     */     //   1284: invokestatic func_76126_a : (F)F
/*     */     //   1287: fstore #18
/*     */     //   1289: fconst_0
/*     */     //   1290: invokestatic func_76129_c : (F)F
/*     */     //   1293: ldc 3.1415927
/*     */     //   1295: fmul
/*     */     //   1296: invokestatic func_76126_a : (F)F
/*     */     //   1299: fstore #19
/*     */     //   1301: fload #18
/*     */     //   1303: ldc_w -20.0
/*     */     //   1306: fmul
/*     */     //   1307: fconst_0
/*     */     //   1308: fconst_1
/*     */     //   1309: fconst_0
/*     */     //   1310: invokestatic func_179114_b : (FFFF)V
/*     */     //   1313: fload #19
/*     */     //   1315: ldc_w -20.0
/*     */     //   1318: fmul
/*     */     //   1319: fconst_0
/*     */     //   1320: fconst_0
/*     */     //   1321: fconst_1
/*     */     //   1322: invokestatic func_179114_b : (FFFF)V
/*     */     //   1325: fload #19
/*     */     //   1327: ldc_w -80.0
/*     */     //   1330: fmul
/*     */     //   1331: fconst_1
/*     */     //   1332: fconst_0
/*     */     //   1333: fconst_0
/*     */     //   1334: invokestatic func_179114_b : (FFFF)V
/*     */     //   1337: ldc_w -0.5
/*     */     //   1340: ldc_w 0.2
/*     */     //   1343: fconst_0
/*     */     //   1344: invokestatic func_179109_b : (FFF)V
/*     */     //   1347: ldc_w 30.0
/*     */     //   1350: fconst_0
/*     */     //   1351: fconst_1
/*     */     //   1352: fconst_0
/*     */     //   1353: invokestatic func_179114_b : (FFFF)V
/*     */     //   1356: ldc_w -80.0
/*     */     //   1359: fconst_1
/*     */     //   1360: fconst_0
/*     */     //   1361: fconst_0
/*     */     //   1362: invokestatic func_179114_b : (FFFF)V
/*     */     //   1365: ldc_w 60.0
/*     */     //   1368: fconst_0
/*     */     //   1369: fconst_1
/*     */     //   1370: fconst_0
/*     */     //   1371: invokestatic func_179114_b : (FFFF)V
/*     */     //   1374: ldc2_w 255
/*     */     //   1377: invokestatic currentTimeMillis : ()J
/*     */     //   1380: ldc2_w 255
/*     */     //   1383: lrem
/*     */     //   1384: ldc2_w 127
/*     */     //   1387: lcmp
/*     */     //   1388: ifle -> 1411
/*     */     //   1391: invokestatic currentTimeMillis : ()J
/*     */     //   1394: invokestatic abs : (J)J
/*     */     //   1397: ldc2_w 255
/*     */     //   1400: lrem
/*     */     //   1401: ldc2_w 255
/*     */     //   1404: lsub
/*     */     //   1405: invokestatic abs : (J)J
/*     */     //   1408: goto -> 1418
/*     */     //   1411: invokestatic currentTimeMillis : ()J
/*     */     //   1414: ldc2_w 255
/*     */     //   1417: lrem
/*     */     //   1418: ldc2_w 2
/*     */     //   1421: lmul
/*     */     //   1422: invokestatic min : (JJ)J
/*     */     //   1425: l2i
/*     */     //   1426: istore #20
/*     */     //   1428: fload #12
/*     */     //   1430: f2d
/*     */     //   1431: ldc2_w 0.5
/*     */     //   1434: dcmpl
/*     */     //   1435: ifle -> 1445
/*     */     //   1438: fconst_1
/*     */     //   1439: fload #12
/*     */     //   1441: fsub
/*     */     //   1442: goto -> 1447
/*     */     //   1445: fload #12
/*     */     //   1447: fstore #21
/*     */     //   1449: ldc_w 0.3
/*     */     //   1452: ldc_w -0.0
/*     */     //   1455: ldc_w 0.4
/*     */     //   1458: invokestatic func_179109_b : (FFF)V
/*     */     //   1461: fconst_0
/*     */     //   1462: fconst_0
/*     */     //   1463: fconst_0
/*     */     //   1464: fconst_1
/*     */     //   1465: invokestatic func_179114_b : (FFFF)V
/*     */     //   1468: fconst_0
/*     */     //   1469: ldc_w 0.5
/*     */     //   1472: fconst_0
/*     */     //   1473: invokestatic func_179109_b : (FFF)V
/*     */     //   1476: ldc_w 90.0
/*     */     //   1479: fconst_1
/*     */     //   1480: fconst_0
/*     */     //   1481: ldc_w -1.0
/*     */     //   1484: invokestatic func_179114_b : (FFFF)V
/*     */     //   1487: ldc 0.6
/*     */     //   1489: ldc_w 0.5
/*     */     //   1492: fconst_0
/*     */     //   1493: invokestatic func_179109_b : (FFF)V
/*     */     //   1496: ldc_w -90.0
/*     */     //   1499: fconst_1
/*     */     //   1500: fconst_0
/*     */     //   1501: ldc_w -1.0
/*     */     //   1504: invokestatic func_179114_b : (FFFF)V
/*     */     //   1507: ldc_w -10.0
/*     */     //   1510: fconst_1
/*     */     //   1511: fconst_0
/*     */     //   1512: ldc_w -1.0
/*     */     //   1515: invokestatic func_179114_b : (FFFF)V
/*     */     //   1518: fload #21
/*     */     //   1520: fneg
/*     */     //   1521: ldc_w 10.0
/*     */     //   1524: fmul
/*     */     //   1525: ldc_w 10.0
/*     */     //   1528: ldc_w 10.0
/*     */     //   1531: ldc_w -9.0
/*     */     //   1534: invokestatic func_179114_b : (FFFF)V
/*     */     //   1537: ldc_w 10.0
/*     */     //   1540: ldc_w -1.0
/*     */     //   1543: fconst_0
/*     */     //   1544: fconst_0
/*     */     //   1545: invokestatic func_179114_b : (FFFF)V
/*     */     //   1548: dconst_0
/*     */     //   1549: dconst_0
/*     */     //   1550: ldc2_w -0.5
/*     */     //   1553: invokestatic func_179137_b : (DDD)V
/*     */     //   1556: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   1559: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   1561: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   1564: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   1567: getfield abobka228 : Z
/*     */     //   1570: ifeq -> 1604
/*     */     //   1573: iload #20
/*     */     //   1575: ineg
/*     */     //   1576: i2f
/*     */     //   1577: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   1580: ldc com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   1582: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   1585: checkcast com/mrzak34/thunderhack/modules/render/Animations
/*     */     //   1588: getfield fapSmooth : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   1591: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   1594: checkcast java/lang/Float
/*     */     //   1597: invokevirtual floatValue : ()F
/*     */     //   1600: fdiv
/*     */     //   1601: goto -> 1605
/*     */     //   1604: fconst_1
/*     */     //   1605: fconst_1
/*     */     //   1606: ldc_w -0.0
/*     */     //   1609: fconst_1
/*     */     //   1610: invokestatic func_179114_b : (FFFF)V
/*     */     //   1613: dconst_0
/*     */     //   1614: dconst_0
/*     */     //   1615: ldc2_w 0.5
/*     */     //   1618: invokestatic func_179137_b : (DDD)V
/*     */     //   1621: goto -> 1736
/*     */     //   1624: iload #14
/*     */     //   1626: i2f
/*     */     //   1627: fload #11
/*     */     //   1629: fmul
/*     */     //   1630: fload #12
/*     */     //   1632: fload #13
/*     */     //   1634: invokestatic func_179109_b : (FFF)V
/*     */     //   1637: aload_0
/*     */     //   1638: aload #9
/*     */     //   1640: fload #7
/*     */     //   1642: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1645: aload_0
/*     */     //   1646: aload #9
/*     */     //   1648: fload #5
/*     */     //   1650: invokevirtual func_187453_a : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1653: goto -> 1736
/*     */     //   1656: aload_0
/*     */     //   1657: aload #9
/*     */     //   1659: fload #7
/*     */     //   1661: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1664: aload_0
/*     */     //   1665: aload #9
/*     */     //   1667: fload #5
/*     */     //   1669: invokevirtual func_187453_a : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1672: goto -> 1736
/*     */     //   1675: iload #14
/*     */     //   1677: i2f
/*     */     //   1678: fload #11
/*     */     //   1680: fmul
/*     */     //   1681: fload #12
/*     */     //   1683: fload #13
/*     */     //   1685: invokestatic func_179109_b : (FFF)V
/*     */     //   1688: aload_0
/*     */     //   1689: aload #9
/*     */     //   1691: fload #7
/*     */     //   1693: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1696: aload_0
/*     */     //   1697: aload #9
/*     */     //   1699: fload #5
/*     */     //   1701: invokevirtual func_187453_a : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1704: goto -> 1736
/*     */     //   1707: iload #14
/*     */     //   1709: i2f
/*     */     //   1710: fload #11
/*     */     //   1712: fmul
/*     */     //   1713: fload #12
/*     */     //   1715: fload #13
/*     */     //   1717: invokestatic func_179109_b : (FFF)V
/*     */     //   1720: aload_0
/*     */     //   1721: aload #9
/*     */     //   1723: fload #7
/*     */     //   1725: invokevirtual func_187459_b : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1728: aload_0
/*     */     //   1729: aload #9
/*     */     //   1731: fload #5
/*     */     //   1733: invokevirtual func_187453_a : (Lnet/minecraft/util/EnumHandSide;F)V
/*     */     //   1736: aload_0
/*     */     //   1737: aload_1
/*     */     //   1738: aload #6
/*     */     //   1740: iload #10
/*     */     //   1742: ifeq -> 1751
/*     */     //   1745: getstatic net/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType.FIRST_PERSON_RIGHT_HAND : Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;
/*     */     //   1748: goto -> 1754
/*     */     //   1751: getstatic net/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType.FIRST_PERSON_LEFT_HAND : Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;
/*     */     //   1754: iload #10
/*     */     //   1756: ifne -> 1763
/*     */     //   1759: iconst_1
/*     */     //   1760: goto -> 1764
/*     */     //   1763: iconst_0
/*     */     //   1764: invokevirtual func_187462_a : (Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V
/*     */     //   1767: invokestatic func_179121_F : ()V
/*     */     //   1770: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #139	-> 0
/*     */     //   #140	-> 15
/*     */     //   #141	-> 36
/*     */     //   #143	-> 39
/*     */     //   #144	-> 47
/*     */     //   #145	-> 59
/*     */     //   #147	-> 72
/*     */     //   #148	-> 83
/*     */     //   #149	-> 98
/*     */     //   #151	-> 110
/*     */     //   #154	-> 125
/*     */     //   #156	-> 140
/*     */     //   #157	-> 163
/*     */     //   #159	-> 175
/*     */     //   #161	-> 220
/*     */     //   #162	-> 228
/*     */     //   #166	-> 231
/*     */     //   #167	-> 240
/*     */     //   #168	-> 248
/*     */     //   #171	-> 251
/*     */     //   #172	-> 259
/*     */     //   #175	-> 262
/*     */     //   #176	-> 270
/*     */     //   #177	-> 286
/*     */     //   #178	-> 295
/*     */     //   #179	-> 308
/*     */     //   #180	-> 321
/*     */     //   #181	-> 344
/*     */     //   #182	-> 352
/*     */     //   #184	-> 368
/*     */     //   #185	-> 375
/*     */     //   #188	-> 378
/*     */     //   #189	-> 386
/*     */     //   #190	-> 400
/*     */     //   #191	-> 407
/*     */     //   #192	-> 414
/*     */     //   #195	-> 431
/*     */     //   #196	-> 448
/*     */     //   #197	-> 461
/*     */     //   #199	-> 476
/*     */     //   #200	-> 479
/*     */     //   #201	-> 496
/*     */     //   #202	-> 514
/*     */     //   #203	-> 528
/*     */     //   #204	-> 540
/*     */     //   #205	-> 560
/*     */     //   #206	-> 572
/*     */     //   #207	-> 594
/*     */     //   #208	-> 634
/*     */     //   #209	-> 731
/*     */     //   #210	-> 828
/*     */     //   #211	-> 860
/*     */     //   #212	-> 868
/*     */     //   #213	-> 876
/*     */     //   #214	-> 889
/*     */     //   #215	-> 902
/*     */     //   #216	-> 914
/*     */     //   #217	-> 926
/*     */     //   #218	-> 937
/*     */     //   #219	-> 949
/*     */     //   #220	-> 953
/*     */     //   #221	-> 966
/*     */     //   #222	-> 979
/*     */     //   #223	-> 991
/*     */     //   #224	-> 1003
/*     */     //   #225	-> 1015
/*     */     //   #226	-> 1026
/*     */     //   #227	-> 1034
/*     */     //   #228	-> 1038
/*     */     //   #229	-> 1051
/*     */     //   #230	-> 1064
/*     */     //   #231	-> 1076
/*     */     //   #232	-> 1088
/*     */     //   #233	-> 1099
/*     */     //   #234	-> 1107
/*     */     //   #235	-> 1111
/*     */     //   #236	-> 1124
/*     */     //   #237	-> 1137
/*     */     //   #238	-> 1154
/*     */     //   #239	-> 1165
/*     */     //   #240	-> 1176
/*     */     //   #241	-> 1184
/*     */     //   #242	-> 1197
/*     */     //   #243	-> 1219
/*     */     //   #244	-> 1230
/*     */     //   #245	-> 1239
/*     */     //   #246	-> 1246
/*     */     //   #247	-> 1254
/*     */     //   #248	-> 1262
/*     */     //   #249	-> 1274
/*     */     //   #250	-> 1283
/*     */     //   #251	-> 1289
/*     */     //   #252	-> 1301
/*     */     //   #253	-> 1313
/*     */     //   #254	-> 1325
/*     */     //   #255	-> 1337
/*     */     //   #256	-> 1347
/*     */     //   #257	-> 1356
/*     */     //   #258	-> 1365
/*     */     //   #259	-> 1374
/*     */     //   #260	-> 1428
/*     */     //   #261	-> 1449
/*     */     //   #262	-> 1461
/*     */     //   #263	-> 1468
/*     */     //   #264	-> 1476
/*     */     //   #265	-> 1487
/*     */     //   #266	-> 1496
/*     */     //   #267	-> 1507
/*     */     //   #268	-> 1518
/*     */     //   #269	-> 1537
/*     */     //   #272	-> 1548
/*     */     //   #273	-> 1556
/*     */     //   #274	-> 1613
/*     */     //   #275	-> 1621
/*     */     //   #277	-> 1624
/*     */     //   #278	-> 1637
/*     */     //   #279	-> 1645
/*     */     //   #282	-> 1656
/*     */     //   #283	-> 1664
/*     */     //   #286	-> 1675
/*     */     //   #287	-> 1688
/*     */     //   #288	-> 1696
/*     */     //   #291	-> 1707
/*     */     //   #292	-> 1720
/*     */     //   #293	-> 1728
/*     */     //   #296	-> 1736
/*     */     //   #298	-> 1767
/*     */     //   #299	-> 1770
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   400	31	14	f7	F
/*     */     //   407	24	15	f3	F
/*     */     //   414	17	16	f4	F
/*     */     //   344	132	12	f5	F
/*     */     //   352	124	13	f6	F
/*     */     //   175	301	11	j	I
/*     */     //   889	37	18	var3	F
/*     */     //   902	24	19	var4	F
/*     */     //   966	49	18	var3	F
/*     */     //   979	36	19	var4	F
/*     */     //   1051	37	18	var3	F
/*     */     //   1064	24	19	var4	F
/*     */     //   1124	41	18	var3	F
/*     */     //   1137	28	19	var4	F
/*     */     //   1197	22	18	var4	F
/*     */     //   1289	332	18	var3	F
/*     */     //   1301	320	19	var4	F
/*     */     //   1428	193	20	alpha	I
/*     */     //   1449	172	21	f5	F
/*     */     //   496	1240	11	f	F
/*     */     //   514	1222	12	f1	F
/*     */     //   528	1208	13	f2	F
/*     */     //   540	1196	14	i	I
/*     */     //   560	1176	15	equipProgress	F
/*     */     //   572	1164	16	swingprogress	F
/*     */     //   594	1142	17	mode	Lcom/mrzak34/thunderhack/modules/render/Animations$rmode;
/*     */     //   140	1627	10	flag1	Z
/*     */     //   0	1771	0	this	Lcom/mrzak34/thunderhack/mixin/mixins/MixinItemRenderer;
/*     */     //   0	1771	1	p_187457_1_	Lnet/minecraft/client/entity/AbstractClientPlayer;
/*     */     //   0	1771	2	p_187457_2_	F
/*     */     //   0	1771	3	p_187457_3_	F
/*     */     //   0	1771	4	p_187457_4_	Lnet/minecraft/util/EnumHand;
/*     */     //   0	1771	5	p_187457_5_	F
/*     */     //   0	1771	6	p_187457_6_	Lnet/minecraft/item/ItemStack;
/*     */     //   0	1771	7	p_187457_7_	F
/*     */     //   15	1756	8	flag	Z
/*     */     //   36	1735	9	enumhandside	Lnet/minecraft/util/EnumHandSide;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformSideFirstPerson2(EnumHandSide enumHandSide, float p_187459_2_) {
/* 303 */     RenderItemEvent event = new RenderItemEvent(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 312 */     if (ViewModel.getInstance().isEnabled()) {
/* 313 */       boolean bob = (ViewModel.getInstance().isDisabled() || ((Boolean)(ViewModel.getInstance()).doBob.getValue()).booleanValue());
/* 314 */       int j = (enumHandSide == EnumHandSide.RIGHT) ? 1 : -1;
/*     */       
/* 316 */       if (!((Boolean)(ViewModel.getInstance()).XBob.getValue()).booleanValue()) {
/* 317 */         GlStateManager.func_179109_b(j * 0.56F, -0.52F + (bob ? p_187459_2_ : 0.0F) * -0.6F, -0.72F);
/*     */       } else {
/* 319 */         GlStateManager.func_179109_b(j * 0.56F, -0.52F, -0.72F - p_187459_2_ * -((Float)(ViewModel.getInstance()).zbobcorr.getValue()).floatValue());
/*     */       } 
/*     */       
/* 322 */       if (enumHandSide == EnumHandSide.RIGHT) {
/* 323 */         GlStateManager.func_179109_b(event.getMainX(), event.getMainY(), event.getMainZ());
/* 324 */         RenderUtil.rotationHelper(event.getMainRotX(), event.getMainRotY(), event.getMainRotZ());
/*     */       } else {
/* 326 */         GlStateManager.func_179109_b(event.getOffX(), event.getOffY(), event.getOffZ());
/* 327 */         RenderUtil.rotationHelper(event.getOffRotX(), event.getOffRotY(), event.getOffRotZ());
/*     */       } 
/*     */     } 
/* 330 */     int i = (enumHandSide == EnumHandSide.RIGHT) ? 1 : -1;
/* 331 */     GlStateManager.func_179109_b(i, -0.52F, -0.72F);
/*     */   }
/*     */   
/*     */   private void transformFirstPersonItem(float equipProgress, float swingProgress) {
/* 335 */     RenderItemEvent event = new RenderItemEvent(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
/* 336 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 337 */     if (ViewModel.getInstance().isEnabled()) {
/* 338 */       boolean bob = (ViewModel.getInstance().isDisabled() || ((Boolean)(ViewModel.getInstance()).doBob.getValue()).booleanValue());
/*     */ 
/*     */       
/* 341 */       if (!((Boolean)(ViewModel.getInstance()).XBob.getValue()).booleanValue()) {
/* 342 */         GlStateManager.func_179109_b(0.56F, -0.52F + (bob ? equipProgress : 0.0F) * -0.6F, -0.72F);
/*     */       } else {
/* 344 */         GlStateManager.func_179109_b(0.56F, -0.52F, -0.72F - equipProgress * -((Float)(ViewModel.getInstance()).zbobcorr.getValue()).floatValue());
/*     */       } 
/* 346 */       GlStateManager.func_179109_b(event.getMainX(), event.getMainY(), event.getMainZ());
/* 347 */       RenderUtil.rotationHelper(event.getMainRotX(), event.getMainRotY(), event.getMainRotZ());
/*     */     } 
/* 349 */     GlStateManager.func_179109_b(0.56F, -0.44F, -0.71999997F);
/* 350 */     GlStateManager.func_179109_b(0.0F, equipProgress * -0.6F, 0.0F);
/* 351 */     GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
/* 352 */     float f = MathHelper.func_76126_a(swingProgress * swingProgress * 3.1415927F);
/* 353 */     float f2 = MathHelper.func_76126_a(MathHelper.func_76129_c(swingProgress) * 3.1415927F);
/* 354 */     GlStateManager.func_179114_b(f * -20.0F, 0.0F, 0.0F, 0.0F);
/* 355 */     GlStateManager.func_179114_b(f2 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 356 */     GlStateManager.func_179114_b(f2 * -80.0F, 0.01F, 0.0F, 0.0F);
/* 357 */     GlStateManager.func_179109_b(0.4F, 0.2F, 0.2F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void translate() {
/* 362 */     GlStateManager.func_179114_b(20.0F, 0.0F, 1.0F, 0.0F);
/* 363 */     GlStateManager.func_179114_b(-80.0F, 1.0F, 0.0F, 0.0F);
/* 364 */     GlStateManager.func_179114_b(20.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */   private void translate3() {
/* 368 */     GlStateManager.func_179114_b(-80.0F, 1.0F, 0.0F, 0.0F);
/* 369 */     GlStateManager.func_179114_b(70.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void translate4() {
/* 374 */     GlStateManager.func_179114_b(30.0F, 0.0F, 1.0F, 0.0F);
/* 375 */     GlStateManager.func_179114_b(-70.0F, 1.0F, 0.0F, 0.0F);
/* 376 */     GlStateManager.func_179114_b(30.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */   private void translate2() {
/* 380 */     GlStateManager.func_179114_b(50.0F, 10.0F, 0.0F, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */