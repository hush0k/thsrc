/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*    */ import com.mrzak34.thunderhack.util.PNGtoResourceLocation;
/*    */ import com.mrzak34.thunderhack.util.ThunderUtils;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.HashMap;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.client.FMLClientHandler;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({AbstractClientPlayer.class})
/*    */ public abstract class MixinAbstractClientPlayer
/*    */ {
/*    */   public ResourceLocation caperes;
/* 27 */   HashMap<String, ResourceLocation> users = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   @Shadow
/*    */   @Nullable
/*    */   protected abstract NetworkPlayerInfo func_175155_b();
/*    */ 
/*    */   
/*    */   @Inject(method = {"getLocationCape"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getLocationCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
/* 38 */     String name = ((NetworkPlayerInfo)Objects.<NetworkPlayerInfo>requireNonNull(func_175155_b())).func_178845_a().getName();
/* 39 */     if (ThunderUtils.isTHUser(name) && ((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).showcapes.getValue()).booleanValue())
/* 40 */       if (!this.users.containsKey(name)) {
/*    */         try {
/* 42 */           BufferedImage image = ThunderUtils.getCustomCape(name);
/* 43 */           DynamicTexture texture = new DynamicTexture(image);
/* 44 */           PNGtoResourceLocation.WrappedResource wr = new PNGtoResourceLocation.WrappedResource(FMLClientHandler.instance().getClient().func_110434_K().func_110578_a(name, texture));
/* 45 */           this.caperes = wr.location;
/* 46 */           callbackInfoReturnable.setReturnValue(this.caperes);
/* 47 */           this.users.put(name, this.caperes);
/* 48 */         } catch (Exception e) {
/* 49 */           e.printStackTrace();
/*    */         } 
/*    */       } else {
/* 52 */         callbackInfoReturnable.setReturnValue(this.users.get(name));
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinAbstractClientPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */