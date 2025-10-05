/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.CrystalRenderEvent;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderDragon;
/*     */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*     */ 
/*     */ 
/*     */ @Mixin({RenderEnderCrystal.class})
/*     */ public abstract class MixinRenderEnderCrystal
/*     */   extends Render<EntityEnderCrystal>
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private ModelBase field_76995_b;
/*     */   
/*     */   @Deprecated
/*     */   protected MixinRenderEnderCrystal(RenderManager renderManager) {
/*  34 */     super(renderManager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private ModelBase field_188316_g;
/*     */ 
/*     */ 
/*     */   
/*     */   private float scale;
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, locals = LocalCapture.CAPTURE_FAILHARD, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityEnderCrystal;shouldShowBottom()Z", shift = At.Shift.BEFORE)}, cancellable = true)
/*     */   public void preRenderHook(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci, float f, float f1) {
/*  52 */     float limbSwing = 0.0F;
/*  53 */     float limbSwingAmount = f * 3.0F;
/*  54 */     float ageInTicks = f1 * 0.2F;
/*  55 */     float netHeadYaw = 0.0F;
/*  56 */     float headPitch = 0.0F;
/*  57 */     float scale = 0.0625F;
/*     */     
/*  59 */     ModelBase modelBase = entity.func_184520_k() ? this.field_76995_b : this.field_188316_g;
/*     */     
/*  61 */     RenderEnderCrystal renderLiving = RenderEnderCrystal.class.cast(this);
/*  62 */     CrystalRenderEvent.Pre pre = new CrystalRenderEvent.Pre(renderLiving, (Entity)entity, modelBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     MinecraftForge.EVENT_BUS.post((Event)pre);
/*  72 */     if (pre.isCanceled()) {
/*  73 */       CrystalRenderEvent.Post post = new CrystalRenderEvent.Post(renderLiving, (Entity)entity, modelBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */ 
/*     */       
/*  76 */       MinecraftForge.EVENT_BUS.post((Event)post);
/*     */       
/*  78 */       exitDoRender(entity, x, y, z, entityYaw, partialTicks, f1);
/*  79 */       ci.cancel();
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
/*     */   @Inject(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderEnderCrystal;renderOutlines:Z", ordinal = 1, shift = At.Shift.BEFORE)}, locals = LocalCapture.CAPTURE_FAILHARD)
/*     */   public void postRenderHook(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci, float f, float f1) {
/*  96 */     float limbSwingAmount = f * 3.0F;
/*  97 */     float ageInTicks = f1 * 0.2F;
/*  98 */     ModelBase modelBase = entity.func_184520_k() ? this.field_76995_b : this.field_188316_g;
/*     */     
/* 100 */     RenderEnderCrystal renderLiving = RenderEnderCrystal.class.cast(this);
/*     */     
/* 102 */     CrystalRenderEvent.Post post = new CrystalRenderEvent.Post(renderLiving, (Entity)entity, modelBase, 0.0F, limbSwingAmount, ageInTicks, 0.0F, 0.0F, 0.0625F);
/*     */ 
/*     */ 
/*     */     
/* 106 */     MinecraftForge.EVENT_BUS.post((Event)post);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void exitDoRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, float f1) {
/* 114 */     if (this.field_188301_f) {
/* 115 */       GlStateManager.func_187417_n();
/* 116 */       GlStateManager.func_179119_h();
/*     */     } 
/*     */     
/* 119 */     GlStateManager.func_179121_F();
/* 120 */     BlockPos blockpos = entity.func_184518_j();
/*     */     
/* 122 */     if (blockpos != null) {
/* 123 */       func_110776_a(RenderDragon.field_110843_g);
/* 124 */       float f2 = blockpos.func_177958_n() + 0.5F;
/* 125 */       float f3 = blockpos.func_177956_o() + 0.5F;
/* 126 */       float f4 = blockpos.func_177952_p() + 0.5F;
/* 127 */       double d0 = f2 - entity.field_70165_t;
/* 128 */       double d1 = f3 - entity.field_70163_u;
/* 129 */       double d2 = f4 - entity.field_70161_v;
/* 130 */       RenderDragon.func_188325_a(x + d0, y - 0.3D + (f1 * 0.4F) + d1, z + d2, partialTicks, f2, f3, f4, entity.field_70261_a, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinRenderEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */