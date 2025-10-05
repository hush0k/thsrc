/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*    */ import net.minecraft.scoreboard.Team;
/*    */ 
/*    */ public class ExtraTab extends Module {
/* 11 */   private static ExtraTab INSTANCE = new ExtraTab();
/* 12 */   public Setting<Integer> size = register(new Setting("Size", Integer.valueOf(250), Integer.valueOf(1), Integer.valueOf(1000)));
/*    */ 
/*    */   
/*    */   public ExtraTab() {
/* 16 */     super("ExtraTab", "расширяет таб", Module.Category.MISC);
/* 17 */     setInstance();
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
/* 22 */     String name = (networkPlayerInfoIn.func_178854_k() != null) ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName()), string = name;
/* 23 */     if (Thunderhack.friendManager.isFriend(name))
/*    */     {
/* 25 */       return ChatFormatting.GREEN + name;
/*    */     }
/* 27 */     return name;
/*    */   }
/*    */   
/*    */   public static ExtraTab getINSTANCE() {
/* 31 */     if (INSTANCE == null) {
/* 32 */       INSTANCE = new ExtraTab();
/*    */     }
/* 34 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 38 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\ExtraTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */