/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.RenderItemEvent;
/*    */ import com.mrzak34.thunderhack.modules.render.CustomEnchants;
/*    */ import com.mrzak34.thunderhack.modules.render.ViewModel;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderItem;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({RenderItem.class})
/*    */ public class MixinRenderItem
/*    */ {
/* 24 */   private static final ResourceLocation RESOURCE = new ResourceLocation("textures/rainbow.png");
/*    */ 
/*    */ 
/*    */   
/*    */   @Redirect(method = {"renderEffect"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", ordinal = 0))
/*    */   public void bindHook(TextureManager textureManager, ResourceLocation resource) {
/* 30 */     if (CustomEnchants.getInstance().isEnabled()) {
/* 31 */       textureManager.func_110577_a(RESOURCE);
/*    */     } else {
/* 33 */       textureManager.func_110577_a(resource);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderItemModel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V", shift = At.Shift.BEFORE)})
/*    */   private void renderItemModel(ItemStack stack, IBakedModel bakedModel, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo ci) {
/* 40 */     RenderItemEvent event = new RenderItemEvent(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 48 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 49 */     if (ViewModel.getInstance().isEnabled())
/* 50 */       if (!leftHanded) {
/* 51 */         GlStateManager.func_179152_a(event.getMainHandScaleX(), event.getMainHandScaleY(), event.getMainHandScaleZ());
/*    */       } else {
/* 53 */         GlStateManager.func_179152_a(event.getOffHandScaleX(), event.getOffHandScaleY(), event.getOffHandScaleZ());
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinRenderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */