/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.render.Models;
/*    */ import com.mrzak34.thunderhack.modules.render.NoRender;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({LayerArmorBase.class})
/*    */ public class MixinLayerArmorBase
/*    */ {
/*    */   @Inject(method = {"doRenderLayer"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
/* 20 */     if (NoRender.getInstance().isEnabled() && (NoRender.getInstance()).noArmor.getValue() == NoRender.NoArmor.ALL) {
/* 21 */       ci.cancel();
/*    */     }
/* 23 */     if (Thunderhack.moduleManager == null) {
/*    */       return;
/*    */     }
/* 26 */     if (Thunderhack.friendManager == null) {
/*    */       return;
/*    */     }
/* 29 */     if (((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).isOn() && ((Boolean)((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).onlySelf.getValue()).booleanValue() && entitylivingbaseIn == Util.mc.field_71439_g) {
/* 30 */       ci.cancel();
/* 31 */     } else if (((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).isOn() && !((Boolean)((Models)Thunderhack.moduleManager.getModuleByClass(Models.class)).onlySelf.getValue()).booleanValue()) {
/* 32 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderArmorLayer"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn, CallbackInfo ci) {
/* 38 */     if (NoRender.getInstance().isEnabled() && (NoRender.getInstance()).noArmor.getValue() == NoRender.NoArmor.HELMET && slotIn == EntityEquipmentSlot.HEAD)
/* 39 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinLayerArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */