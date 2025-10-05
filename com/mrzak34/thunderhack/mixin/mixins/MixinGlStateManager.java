/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.render.NoRender;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GlStateManager.class})
/*    */ public class MixinGlStateManager
/*    */ {
/*    */   @Inject(method = {"enableFog"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onEnableFog(CallbackInfo info) {
/* 16 */     if (((Boolean)((NoRender)Thunderhack.moduleManager.getModuleByClass(NoRender.class)).fog.getValue()).booleanValue() && ((NoRender)Thunderhack.moduleManager.getModuleByClass(NoRender.class)).isOn())
/* 17 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGlStateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */