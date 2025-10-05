/*    */ package com.mrzak34.thunderhack.gui.hud.elements;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.DeathEvent;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*    */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*    */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*    */ import com.mrzak34.thunderhack.modules.funnygame.C4Aura;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.util.RoundedShader;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class KillFeed
/*    */   extends HudElement {
/*    */   List<String> players;
/*    */   
/*    */   public KillFeed() {
/* 27 */     super("KillFeed", "статистика убийств", 100, 100);
/*    */ 
/*    */     
/* 30 */     this.players = new CopyOnWriteArrayList<>();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPlayerDeath(DeathEvent e) {
/* 35 */     if (Aura.target != null && Aura.target == e.player) {
/* 36 */       this.players.add(getFullName(e.player.func_70005_c_()));
/*    */       return;
/*    */     } 
/* 39 */     if (C4Aura.target != null && C4Aura.target == e.player) {
/* 40 */       this.players.add(getFullName(e.player.func_70005_c_()));
/*    */       return;
/*    */     } 
/* 43 */     if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).target != null && ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).target == e.player) {
/* 44 */       this.players.add(getFullName(e.player.func_70005_c_()));
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 50 */     super.onRender2D(e);
/* 51 */     int y_offset1 = 11;
/* 52 */     float scale_x = 50.0F;
/* 53 */     for (String player : this.players) {
/* 54 */       if (player != null && 
/* 55 */         FontRender.getStringWidth6("EZ - " + player) > scale_x) {
/* 56 */         scale_x = FontRender.getStringWidth6("EZ - " + player);
/*    */       }
/*    */       
/* 59 */       y_offset1 += 13;
/*    */     } 
/* 61 */     RenderUtil.drawBlurredShadow(getPosX(), getPosY(), scale_x + 20.0F, (20 + y_offset1), 20, ((ColorSetting)this.shadowColor.getValue()).getColorObject());
/* 62 */     RoundedShader.drawRound(getPosX(), getPosY(), scale_x + 20.0F, (20 + y_offset1), 7.0F, ((ColorSetting)this.color2.getValue()).getColorObject());
/* 63 */     FontRender.drawCentString6("KillFeed [" + this.players.size() + "]", getPosX() + (scale_x + 20.0F) / 2.0F, getPosY() + 5.0F, ((ColorSetting)this.textColor.getValue()).getColor());
/* 64 */     RoundedShader.drawRound(getPosX() + 2.0F, getPosY() + 13.0F, scale_x + 16.0F, 1.0F, 0.5F, ((ColorSetting)this.color3.getValue()).getColorObject());
/* 65 */     int y_offset = 11;
/* 66 */     for (String player : this.players) {
/* 67 */       GlStateManager.func_179094_E();
/* 68 */       GlStateManager.func_179117_G();
/* 69 */       FontRender.drawString6(ChatFormatting.RED + "EZ - " + ChatFormatting.RESET + player, getPosX() + 5.0F, getPosY() + 18.0F + y_offset, -1, false);
/* 70 */       GlStateManager.func_179117_G();
/* 71 */       GlStateManager.func_179121_F();
/* 72 */       y_offset += 13;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getFullName(String raw) {
/* 78 */     for (NetworkPlayerInfo player : mc.field_71439_g.field_71174_a.func_175106_d()) {
/* 79 */       if (mc.func_71356_B() || player.func_178850_i() == null)
/* 80 */         break;  String name = Arrays.<Object>asList(player.func_178850_i().func_96670_d().stream().toArray()).toString().replace("[", "").replace("]", "");
/* 81 */       if (name.contains(raw)) {
/* 82 */         return player.func_178850_i().func_96668_e() + name;
/*    */       }
/*    */     } 
/* 85 */     return "null";
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\KillFeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */