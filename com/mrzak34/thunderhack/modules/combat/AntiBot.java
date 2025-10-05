/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AntiBot
/*     */   extends Module {
/*  18 */   public static ArrayList<EntityPlayer> bots = new ArrayList<>();
/*  19 */   public Setting<Boolean> remove = register(new Setting("Remove", Boolean.valueOf(false)));
/*  20 */   public Setting<Boolean> onlyAura = register(new Setting("OnlyAura", Boolean.valueOf(true)));
/*  21 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.MotionCheck));
/*  22 */   public Setting<Integer> checkticks = register(new Setting("checkTicks", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(10), v -> (this.mode.getValue() == Mode.MotionCheck)));
/*  23 */   private final Timer timer = new Timer();
/*  24 */   private int botsNumber = 0;
/*  25 */   private int ticks = 0;
/*     */   public AntiBot() {
/*  27 */     super("AntiBot", "Убирает ботов", Module.Category.COMBAT);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPre(EventSync e) {
/*  33 */     if (!((Boolean)this.onlyAura.getValue()).booleanValue()) {
/*  34 */       for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*  35 */         if (this.mode.getValue() == Mode.MotionCheck) {
/*  36 */           if (player != null) {
/*  37 */             double speed = (player.field_70165_t - player.field_70169_q) * (player.field_70165_t - player.field_70169_q) + (player.field_70161_v - player.field_70166_s) * (player.field_70161_v - player.field_70166_s);
/*  38 */             if (player != mc.field_71439_g && speed > 0.5D && mc.field_71439_g.func_70068_e((Entity)player) <= (((Float)((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).attackDistance.getValue()).floatValue() * ((Float)((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).attackDistance.getValue()).floatValue()) && !bots.contains(player) && 
/*  39 */               !bots.contains(player)) {
/*  40 */               Command.sendMessage(player.func_70005_c_() + " is a bot!");
/*  41 */               this.botsNumber++;
/*  42 */               bots.add(player);
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         } 
/*  47 */         if (!player.func_110124_au().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.func_70005_c_()).getBytes(StandardCharsets.UTF_8))) && player instanceof net.minecraft.client.entity.EntityOtherPlayerMP && 
/*  48 */           !bots.contains(player)) {
/*  49 */           Command.sendMessage(player.func_70005_c_() + " is a bot!");
/*  50 */           this.botsNumber++;
/*  51 */           bots.add(player);
/*     */         } 
/*     */         
/*  54 */         if (!player.func_110124_au().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.func_70005_c_()).getBytes(StandardCharsets.UTF_8))) && player.func_82150_aj() && player instanceof net.minecraft.client.entity.EntityOtherPlayerMP && 
/*  55 */           !bots.contains(player)) {
/*  56 */           Command.sendMessage(player.func_70005_c_() + " is a bot!");
/*  57 */           this.botsNumber++;
/*  58 */           bots.add(player);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/*  64 */     else if (Aura.target != null && 
/*  65 */       Aura.target instanceof EntityPlayer) {
/*  66 */       if (this.mode.getValue() == Mode.MotionCheck) {
/*  67 */         double speed = (Aura.target.field_70165_t - Aura.target.field_70169_q) * (Aura.target.field_70165_t - Aura.target.field_70169_q) + (Aura.target.field_70161_v - Aura.target.field_70166_s) * (Aura.target.field_70161_v - Aura.target.field_70166_s);
/*  68 */         if (speed > 0.5D && !bots.contains(Aura.target)) {
/*  69 */           if (this.ticks >= ((Integer)this.checkticks.getValue()).intValue()) {
/*  70 */             Command.sendMessage(Aura.target.func_70005_c_() + " is a bot!");
/*  71 */             this.botsNumber++;
/*  72 */             bots.add((EntityPlayer)Aura.target);
/*     */           } 
/*  74 */           this.ticks++;
/*     */         } 
/*     */       } else {
/*  77 */         if (!Aura.target.func_110124_au().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + Aura.target.func_70005_c_()).getBytes(StandardCharsets.UTF_8))) && Aura.target instanceof net.minecraft.client.entity.EntityOtherPlayerMP) {
/*  78 */           Command.sendMessage(Aura.target.func_70005_c_() + " is a bot!");
/*  79 */           this.botsNumber++;
/*  80 */           bots.add((EntityPlayer)Aura.target);
/*     */         } 
/*  82 */         if (!Aura.target.func_110124_au().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + Aura.target.func_70005_c_()).getBytes(StandardCharsets.UTF_8))) && Aura.target.func_82150_aj() && Aura.target instanceof net.minecraft.client.entity.EntityOtherPlayerMP) {
/*  83 */           Command.sendMessage(Aura.target.func_70005_c_() + " is a bot!");
/*  84 */           this.botsNumber++;
/*  85 */           bots.add((EntityPlayer)Aura.target);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     for (EntityPlayer bot : bots) {
/*  95 */       if (((Boolean)this.remove.getValue()).booleanValue()) {
/*     */         try {
/*  97 */           mc.field_71441_e.func_72900_e((Entity)bot);
/*  98 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 103 */     if (this.timer.passedMs(10000L)) {
/* 104 */       bots.clear();
/* 105 */       this.botsNumber = 0;
/* 106 */       this.timer.reset();
/* 107 */       this.ticks = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 113 */     return String.valueOf(this.botsNumber);
/*     */   }
/*     */   
/*     */   public enum Mode
/*     */   {
/* 118 */     UUIDCheck, MotionCheck;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AntiBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */