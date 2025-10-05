/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ 
/*    */ public class AutoRespawn
/*    */   extends Module {
/*    */   private final Timer timer;
/* 12 */   public Setting<Boolean> deathcoords = register(new Setting("deathcoords", Boolean.valueOf(true)));
/* 13 */   public Setting<Boolean> autokit = register(new Setting("Auto Kit", Boolean.valueOf(false)));
/* 14 */   public Setting<String> kit = register(new Setting("kit name", "kitname", v -> ((Boolean)this.autokit.getValue()).booleanValue()));
/* 15 */   public Setting<Boolean> autohome = register(new Setting("Auto Home", Boolean.valueOf(false)));
/*    */ 
/*    */   
/*    */   public AutoRespawn() {
/* 19 */     super("AutoRespawn", "автореспавн с автокитом", Module.Category.PLAYER);
/* 20 */     this.timer = new Timer();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 25 */     if (fullNullCheck())
/*    */       return; 
/* 27 */     if (this.timer.passedMs(2100L)) {
/* 28 */       this.timer.reset();
/*    */     }
/* 30 */     if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiGameOver) {
/* 31 */       mc.field_71439_g.func_71004_bE();
/* 32 */       mc.func_147108_a(null);
/*    */     } 
/* 34 */     if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiGameOver && this.timer.getPassedTimeMs() > 200L) {
/* 35 */       if (((Boolean)this.autokit.getValue()).booleanValue()) {
/* 36 */         mc.field_71439_g.func_71165_d("/kit " + (String)this.kit.getValue());
/*    */       }
/* 38 */       if (((Boolean)this.deathcoords.getValue()).booleanValue()) {
/* 39 */         Command.sendMessage(ChatFormatting.GOLD + "[PlayerDeath] " + ChatFormatting.YELLOW + (int)mc.field_71439_g.field_70165_t + " " + (int)mc.field_71439_g.field_70163_u + " " + (int)mc.field_71439_g.field_70161_v);
/*    */       }
/* 41 */       this.timer.reset();
/*    */     } 
/*    */     
/* 44 */     if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiGameOver && this.timer.getPassedTimeMs() > 1000L) {
/* 45 */       if (((Boolean)this.autohome.getValue()).booleanValue()) {
/* 46 */         mc.field_71439_g.func_71165_d("/home");
/*    */       }
/* 48 */       if (((Boolean)this.deathcoords.getValue()).booleanValue()) {
/* 49 */         Command.sendMessage(ChatFormatting.GOLD + "[PlayerDeath] " + ChatFormatting.YELLOW + (int)mc.field_71439_g.field_70165_t + " " + (int)mc.field_71439_g.field_70163_u + " " + (int)mc.field_71439_g.field_70161_v);
/*    */       }
/* 51 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\AutoRespawn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */