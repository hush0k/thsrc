/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Welcomer
/*     */   extends Module {
/*  16 */   public final Setting<Boolean> serverside = register(new Setting("ServerSide", Boolean.valueOf(false)));
/*  17 */   public final Setting<Boolean> global = register(new Setting("Global", Boolean.valueOf(false), v -> ((Boolean)this.serverside.getValue()).booleanValue()));
/*  18 */   private final String[] bb = new String[] { "See you later, ", "Catch ya later, ", "See you next time, ", "Farewell, ", "Bye, ", "Good bye, ", "Later, " };
/*  19 */   private final String[] qq = new String[] { "Good to see you, ", "Greetings, ", "Hello, ", "Howdy, ", "Hey, ", "Good evening, ", "Welcome to SERVERIP1D5A9E, " };
/*  20 */   private final Timer timer = new Timer();
/*  21 */   private String string1 = "server";
/*  22 */   private final LinkedHashMap<UUID, String> nameMap = new LinkedHashMap<>();
/*     */ 
/*     */   
/*     */   public Welcomer() {
/*  26 */     super("Welcomer", "Приветствует игроков", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  31 */     this.nameMap.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  36 */     if (this.timer.passedMs(15000L)) {
/*  37 */       for (NetworkPlayerInfo b : mc.field_71439_g.field_71174_a.func_175106_d()) {
/*  38 */         if (!this.nameMap.containsKey(b.func_178845_a().getId())) {
/*  39 */           this.nameMap.put(b.func_178845_a().getId(), b.func_178845_a().getName());
/*     */         }
/*     */       } 
/*  42 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive e) {
/*  48 */     if (e.getPacket() instanceof SPacketPlayerListItem) {
/*  49 */       SPacketPlayerListItem pck = (SPacketPlayerListItem)e.getPacket();
/*     */       
/*  51 */       int n = (int)Math.floor(Math.random() * this.bb.length);
/*  52 */       int n2 = (int)Math.floor(Math.random() * this.qq.length);
/*  53 */       if (mc.func_147104_D() != null) {
/*  54 */         this.string1 = this.qq[n2].replace("SERVERIP1D5A9E", (mc.func_147104_D()).field_78845_b);
/*     */       } else {
/*  56 */         this.string1 = "server";
/*     */       } 
/*     */       
/*  59 */       for (SPacketPlayerListItem.AddPlayerData item : pck.func_179767_a()) {
/*  60 */         switch (pck.func_179768_b()) {
/*     */           case REMOVE_PLAYER:
/*  62 */             if (!this.nameMap.containsKey(item.func_179962_a().getId())) {
/*     */               return;
/*     */             }
/*  65 */             if (antiBot(this.nameMap.get(item.func_179962_a().getId()))) {
/*     */               return;
/*     */             }
/*  68 */             if (((Boolean)this.serverside.getValue()).booleanValue()) {
/*  69 */               mc.field_71439_g.func_71165_d((((Boolean)this.global.getValue()).booleanValue() ? "!" : "") + this.bb[n] + (String)this.nameMap.get(item.func_179962_a().getId()));
/*     */             } else {
/*  71 */               Command.sendMessage(this.bb[n] + (String)this.nameMap.get(item.func_179962_a().getId()));
/*     */             } 
/*     */ 
/*     */             
/*  75 */             this.nameMap.remove(item.func_179962_a().getId());
/*     */             continue;
/*     */           case ADD_PLAYER:
/*  78 */             if (antiBot(item.func_179962_a().getName())) {
/*     */               return;
/*     */             }
/*  81 */             if (((Boolean)this.serverside.getValue()).booleanValue()) {
/*  82 */               mc.field_71439_g.func_71165_d((((Boolean)this.global.getValue()).booleanValue() ? "!" : "") + this.string1 + item.func_179962_a().getName());
/*     */             } else {
/*  84 */               Command.sendMessage(this.string1 + item.func_179962_a().getName());
/*     */             } 
/*  86 */             this.nameMap.put(item.func_179962_a().getId(), item.func_179962_a().getName());
/*     */             continue;
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean antiBot(String s) {
/*  96 */     for (int i = 0; i < s.length(); i++) {
/*  97 */       if (Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
/*  98 */         return true;
/*     */       }
/*     */     } 
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\Welcomer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */