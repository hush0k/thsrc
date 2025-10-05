/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.ClickMiddleEvent;
/*     */ import com.mrzak34.thunderhack.events.GameZaloopEvent;
/*     */ import com.mrzak34.thunderhack.events.KeyEvent;
/*     */ import com.mrzak34.thunderhack.events.KeyboardEvent;
/*     */ import com.mrzak34.thunderhack.events.MouseEvent;
/*     */ import com.mrzak34.thunderhack.gui.mainmenu.ThunderMenu;
/*     */ import com.mrzak34.thunderhack.modules.client.AntiDisconnect;
/*     */ import com.mrzak34.thunderhack.modules.client.MainSettings;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.phobos.InterfaceMinecraft;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ @Mixin({Minecraft.class})
/*     */ public abstract class MixinMinecraft implements InterfaceMinecraft {
/*     */   @Shadow
/*     */   @Nullable
/*     */   public GuiScreen field_71462_r;
/*  34 */   private int gameLoop = 0;
/*     */   
/*     */   @Inject(method = {"shutdownMinecraftApplet"}, at = {@At("HEAD")})
/*     */   private void stopClient(CallbackInfo callbackInfo) {
/*  38 */     unload();
/*     */   }
/*     */   
/*     */   @Redirect(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
/*     */   public void displayCrashReport(Minecraft minecraft, CrashReport crashReport) {
/*  43 */     unload();
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"runTickKeyboard"}, at = {@At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE)})
/*     */   private void onKeyboard(CallbackInfo callbackInfo) {
/*  49 */     int i = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey(), n = i;
/*  50 */     if (Keyboard.getEventKeyState()) {
/*  51 */       KeyEvent event = new KeyEvent(i);
/*  52 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"runGameLoop"}, at = {@At("HEAD")})
/*     */   private void runGameLoopHead(CallbackInfo callbackInfo) {
/*  58 */     this.gameLoop++;
/*     */   }
/*     */   
/*     */   @Inject(method = {"middleClickMouse"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void middleClickMouseHook(CallbackInfo callbackInfo) {
/*  63 */     ClickMiddleEvent event = new ClickMiddleEvent();
/*  64 */     MinecraftForge.EVENT_BUS.post((Event)event);
/*     */     
/*  66 */     if (event.isCanceled()) {
/*  67 */       callbackInfo.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"runTick()V"}, at = {@At("RETURN")})
/*     */   private void runTick(CallbackInfo callbackInfo) {
/*  73 */     if ((Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiMainMenu && Thunderhack.moduleManager != null && ((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).mainMenu.getValue()).booleanValue()) {
/*  74 */       Minecraft.func_71410_x().func_147108_a((GuiScreen)new ThunderMenu());
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"displayGuiScreen"}, at = {@At("HEAD")})
/*     */   private void displayGuiScreenHook(GuiScreen screen, CallbackInfo ci) {
/*  80 */     if (screen instanceof net.minecraft.client.gui.GuiMainMenu && Thunderhack.moduleManager != null && ((Boolean)((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).mainMenu.getValue()).booleanValue()) {
/*  81 */       Util.mc.func_147108_a((GuiScreen)new ThunderMenu());
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"runTickMouse"}, at = {@At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButton()I", remap = false)})
/*     */   public void runTickMouseHook(CallbackInfo ci) {
/*  87 */     MinecraftForge.EVENT_BUS.post((Event)new MouseEvent(Mouse.getEventButton(), Mouse.getEventButtonState()));
/*     */   }
/*     */   
/*     */   @Inject(method = {"runTick"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;tick()V", shift = At.Shift.AFTER)})
/*     */   private void postUpdateWorld(CallbackInfo info) {
/*  92 */     MinecraftForge.EVENT_BUS.post((Event)new PostWorldTick());
/*     */   }
/*     */   
/*     */   @Inject(method = {"runGameLoop"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", ordinal = 0, shift = At.Shift.AFTER)})
/*     */   private void post_ScheduledTasks(CallbackInfo callbackInfo) {
/*  97 */     MinecraftForge.EVENT_BUS.post((Event)new GameZaloopEvent());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGameLoop() {
/* 102 */     return this.gameLoop;
/*     */   }
/*     */   
/*     */   @Inject(method = {"runTickKeyboard"}, at = {@At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false)})
/*     */   public void runTickKeyboardHook(CallbackInfo callbackInfo) {
/* 107 */     MinecraftForge.EVENT_BUS.post((Event)new KeyboardEvent(Keyboard.getEventKeyState(), Keyboard.getEventKey(), Keyboard.getEventCharacter()));
/*     */   }
/*     */   
/*     */   @Redirect(method = {"runGameLoop"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;shutdown()V"))
/*     */   private void Method5080(Minecraft minecraft) {
/* 112 */     if (minecraft.field_71441_e != null && ((AntiDisconnect)Thunderhack.moduleManager.getModuleByClass(AntiDisconnect.class)).isOn()) {
/* 113 */       GuiScreen screen = minecraft.field_71462_r;
/* 114 */       GuiYesNo g = new GuiYesNo((result, id) -> { if (result) { minecraft.func_71400_g(); } else { Minecraft.func_71410_x().func_147108_a(screen); }  }"Ты точно хочешь закрыть майн?", "", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       Minecraft.func_71410_x().func_147108_a((GuiScreen)g);
/*     */     } else {
/* 123 */       minecraft.func_71400_g();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unload() {
/* 128 */     Thunderhack.unload(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */