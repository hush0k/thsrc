/*    */ package com.mrzak34.thunderhack.mixin;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ public class THLoader
/*    */   implements IFMLLoadingPlugin
/*    */ {
/*    */   public THLoader() {
/* 13 */     MixinBootstrap.init();
/* 14 */     Mixins.addConfiguration("mixins.thunderhack.json");
/* 15 */     MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
/*    */   }
/*    */   
/*    */   public String[] getASMTransformerClass() {
/* 19 */     return new String[0];
/*    */   }
/*    */   
/*    */   public String getModContainerClass() {
/* 23 */     return null;
/*    */   }
/*    */   
/*    */   public String getSetupClass() {
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 33 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\THLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */