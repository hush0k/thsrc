/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.TotemPopEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LagMessage
/*    */   extends Module
/*    */ {
/*    */   private static final String LAG_MESSAGE = "āȁ́Ё؁܁ࠁँਁଁก༁ခᄁሁጁᐁᔁᘁᜁ᠁ᤁᨁᬁᰁᴁḁἁ ℁∁⌁␁━✁⠁⤁⨁⬁Ⰱⴁ⸁⼁、㄁㈁㌁㐁㔁㘁㜁㠁㤁㨁㬁㰁㴁㸁㼁䀁䄁䈁䌁䐁䔁䘁䜁䠁䤁䨁䬁䰁䴁丁企倁儁刁匁吁唁嘁圁堁夁威嬁封崁币弁态愁戁持搁攁昁朁栁椁樁欁氁洁渁漁瀁焁爁猁琁甁瘁省码礁稁笁簁紁縁缁老脁舁茁萁蔁蘁蜁蠁褁訁謁谁贁踁輁送鄁鈁錁鐁锁阁霁頁餁騁鬁鰁鴁鸁鼁ꀁꄁꈁꌁꐁꔁꘁ꜁ꠁ꤁ꨁꬁ각괁긁꼁뀁넁눁댁됁딁똁뜁렁뤁먁묁밁봁";
/* 37 */   public Setting<Boolean> onlyair = register(new Setting("OnlyInAir", Boolean.valueOf(true)));
/*    */   
/* 39 */   List<EntityPlayer> players = new ArrayList<>();
/*    */   
/*    */   public LagMessage() {
/* 42 */     super("LagMessage", "LagMessage", "LagMessage", Module.Category.MISC);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTotemPop(TotemPopEvent event) {
/* 47 */     if (event.getEntity() == mc.field_71439_g)
/* 48 */       return;  if (Thunderhack.friendManager.isFriend(event.getEntity()))
/* 49 */       return;  if (this.players.contains(event.getEntity()))
/* 50 */       return;  if (((Boolean)this.onlyair.getValue()).booleanValue() && (event.getEntity()).field_70122_E)
/* 51 */       return;  mc.field_71439_g.func_71165_d("/w " + event.getEntity().func_70005_c_() + " " + "āȁ́Ё؁܁ࠁँਁଁก༁ခᄁሁጁᐁᔁᘁᜁ᠁ᤁᨁᬁᰁᴁḁἁ ℁∁⌁␁━✁⠁⤁⨁⬁Ⰱⴁ⸁⼁、㄁㈁㌁㐁㔁㘁㜁㠁㤁㨁㬁㰁㴁㸁㼁䀁䄁䈁䌁䐁䔁䘁䜁䠁䤁䨁䬁䰁䴁丁企倁儁刁匁吁唁嘁圁堁夁威嬁封崁币弁态愁戁持搁攁昁朁栁椁樁欁氁洁渁漁瀁焁爁猁琁甁瘁省码礁稁笁簁紁縁缁老脁舁茁萁蔁蘁蜁蠁褁訁謁谁贁踁輁送鄁鈁錁鐁锁阁霁頁餁騁鬁鰁鴁鸁鼁ꀁꄁꈁꌁꐁꔁꘁ꜁ꠁ꤁ꨁꬁ각괁긁꼁뀁넁눁댁됁딁똁뜁렁뤁먁묁밁봁");
/* 52 */     this.players.add(event.getEntity());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onLogin() {
/* 58 */     if (this.players.contains(mc.field_71439_g))
/* 59 */       return;  Command.sendMessage("āȁ́Ё؁܁ࠁँਁଁก༁ခᄁሁጁᐁᔁᘁᜁ᠁ᤁᨁᬁᰁᴁḁἁ ℁∁⌁␁━✁⠁⤁⨁⬁Ⰱⴁ⸁⼁、㄁㈁㌁㐁㔁㘁㜁㠁㤁㨁㬁㰁㴁㸁㼁䀁䄁䈁䌁䐁䔁䘁䜁䠁䤁䨁䬁䰁䴁丁企倁儁刁匁吁唁嘁圁堁夁威嬁封崁币弁态愁戁持搁攁昁朁栁椁樁欁氁洁渁漁瀁焁爁猁琁甁瘁省码礁稁笁簁紁縁缁老脁舁茁萁蔁蘁蜁蠁褁訁謁谁贁踁輁送鄁鈁錁鐁锁阁霁頁餁騁鬁鰁鴁鸁鼁ꀁꄁꈁꌁꐁꔁꘁ꜁ꠁ꤁ꨁꬁ각괁긁꼁뀁넁눁댁됁딁똁뜁렁뤁먁묁밁봁");
/* 60 */     this.players.add(mc.field_71439_g);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 65 */     if (this.players.contains(mc.field_71439_g))
/* 66 */       return;  Command.sendMessage("āȁ́Ё؁܁ࠁँਁଁก༁ခᄁሁጁᐁᔁᘁᜁ᠁ᤁᨁᬁᰁᴁḁἁ ℁∁⌁␁━✁⠁⤁⨁⬁Ⰱⴁ⸁⼁、㄁㈁㌁㐁㔁㘁㜁㠁㤁㨁㬁㰁㴁㸁㼁䀁䄁䈁䌁䐁䔁䘁䜁䠁䤁䨁䬁䰁䴁丁企倁儁刁匁吁唁嘁圁堁夁威嬁封崁币弁态愁戁持搁攁昁朁栁椁樁欁氁洁渁漁瀁焁爁猁琁甁瘁省码礁稁笁簁紁縁缁老脁舁茁萁蔁蘁蜁蠁褁訁謁谁贁踁輁送鄁鈁錁鐁锁阁霁頁餁騁鬁鰁鴁鸁鼁ꀁꄁꈁꌁꐁꔁꘁ꜁ꠁ꤁ꨁꬁ각괁긁꼁뀁넁눁댁됁딁똁뜁렁뤁먁묁밁봁");
/* 67 */     this.players.add(mc.field_71439_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\LagMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */