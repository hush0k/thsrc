/*    */ package com.mrzak34.thunderhack.mixin.mixins;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.RenderAttackIndicatorEvent;
/*    */ import com.mrzak34.thunderhack.gui.hud.elements.Potions;
/*    */ import com.mrzak34.thunderhack.modules.funnygame.AntiTittle;
/*    */ import com.mrzak34.thunderhack.modules.render.NoRender;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiIngame;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiIngame.class})
/*    */ public class MixinGuiIngame extends Gui {
/*    */   @Inject(method = {"renderPotionEffects"}, at = {@At("HEAD")}, cancellable = true)
/*    */   protected void renderPotionEffectsHook(ScaledResolution scaledRes, CallbackInfo info) {
/* 23 */     if (((Potions)Thunderhack.moduleManager.getModuleByClass(Potions.class)).isOn()) {
/* 24 */       info.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderScoreboard"}, at = {@At("HEAD")}, cancellable = true)
/*    */   protected void renderScoreboardHook(ScoreObjective objective, ScaledResolution scaledRes, CallbackInfo ci) {
/* 31 */     if (((Boolean)((AntiTittle)Thunderhack.moduleManager.getModuleByClass(AntiTittle.class)).scoreBoard.getValue()).booleanValue() && ((AntiTittle)Thunderhack.moduleManager.getModuleByClass(AntiTittle.class)).isOn()) {
/* 32 */       ci.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderPortal"}, at = {@At("HEAD")}, cancellable = true)
/*    */   protected void renderPortal(float n, ScaledResolution scaledResolution, CallbackInfo callbackInfo) {
/* 38 */     if (((Boolean)((NoRender)Thunderhack.moduleManager.getModuleByClass(NoRender.class)).portal.getValue()).booleanValue() && ((NoRender)Thunderhack.moduleManager.getModuleByClass(NoRender.class)).isOn()) {
/* 39 */       callbackInfo.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"renderAttackIndicator"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onRenderAttackIndicator(float partialTicks, ScaledResolution p_184045_2_, CallbackInfo ci) {
/* 46 */     RenderAttackIndicatorEvent event = new RenderAttackIndicatorEvent();
/* 47 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 48 */     if (event.isCanceled()) ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinGuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */