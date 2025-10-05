/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.misc.NameProtect;
/*    */ import com.mrzak34.thunderhack.modules.misc.PasswordHider;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({FontRenderer.class})
/*    */ public abstract class MixinFontRenderer
/*    */ {
/*    */   @Shadow
/*    */   protected abstract void func_78255_a(String paramString, boolean paramBoolean);
/*    */   
/*    */   @Redirect(method = {"renderString(Ljava/lang/String;FFIZ)I"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"))
/*    */   public void renderStringAtPosHook(FontRenderer fontRenderer, String string, boolean bl) {
/* 23 */     if (Thunderhack.moduleManager == null) {
/* 24 */       func_78255_a(string, bl);
/*    */       return;
/*    */     } 
/* 27 */     if (((PasswordHider)Thunderhack.moduleManager.getModuleByClass(PasswordHider.class)).isEnabled() && (
/* 28 */       string.contains("/l ") || string.contains("/login ") || string.contains("/reg ") || (string.contains("/register ") && Util.mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat))) {
/* 29 */       StringBuilder final_string = new StringBuilder();
/* 30 */       for (char cha : string.replace("/login ", "").replace("/register ", "").replace("/l ", "").replace("/reg ", "").toCharArray()) {
/* 31 */         final_string.append("*");
/*    */       }
/*    */       
/* 34 */       if (string.contains("/register")) {
/* 35 */         func_78255_a("/register " + final_string, bl); return;
/*    */       } 
/* 37 */       if (string.contains("/login")) {
/* 38 */         func_78255_a("/login " + final_string, bl); return;
/*    */       } 
/* 40 */       if (string.contains("/l ")) {
/* 41 */         func_78255_a("/l " + final_string, bl); return;
/*    */       } 
/* 43 */       if (string.contains("/reg ")) {
/* 44 */         func_78255_a("/reg " + final_string, bl);
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     if (((NameProtect)Thunderhack.moduleManager.getModuleByClass(NameProtect.class)).isEnabled()) {
/* 51 */       if (Util.mc == null || Util.mc.func_110432_I() == null) {
/*    */         return;
/*    */       }
/* 54 */       func_78255_a(string.replace(Util.mc.func_110432_I().func_111285_a(), "Protected"), bl);
/*    */     } else {
/* 56 */       func_78255_a(string, bl);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */