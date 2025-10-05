/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.funnygame.AntiTittle;
/*    */ import com.mrzak34.thunderhack.modules.render.EzingKids;
/*    */ import com.mrzak34.thunderhack.modules.render.NoRender;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({RenderLivingBase.class})
/*    */ public abstract class MixinRenderLivingBase<T extends EntityLivingBase>
/*    */   extends Render<T>
/*    */ {
/*    */   @Shadow
/*    */   protected ModelBase field_77045_g;
/*    */   
/*    */   protected MixinRenderLivingBase(RenderManager renderManager) {
/* 28 */     super(renderManager);
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private <T extends EntityLivingBase> void injectChamsPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo callbackInfo) {
/* 34 */     if (entity instanceof net.minecraft.entity.item.EntityArmorStand && ((NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).noarmorstands.getValue()).booleanValue()) || (((AntiTittle)Thunderhack.moduleManager.getModuleByClass(AntiTittle.class)).isOn() && ((Boolean)((AntiTittle)Thunderhack.moduleManager.getModuleByClass(AntiTittle.class)).armorstands.getValue()).booleanValue()))) {
/* 35 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"interpolateRotation"}, at = {@At("HEAD")})
/*    */   protected void interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks, CallbackInfoReturnable<Float> cir) {
/* 41 */     if (((EzingKids)Thunderhack.moduleManager.getModuleByClass(EzingKids.class)).isOn())
/* 42 */       this.field_77045_g.field_78091_s = true; 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinRenderLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */