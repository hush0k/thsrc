/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EntityAddedEvent;
/*     */ import com.mrzak34.thunderhack.events.EntityRemovedEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.notification.Notification;
/*     */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VisualRange
/*     */   extends Module
/*     */ {
/*  27 */   private static final ArrayList<String> entities = new ArrayList<>();
/*  28 */   public Setting<Boolean> leave = register(new Setting("Leave", Boolean.valueOf(true)));
/*  29 */   public Setting<Boolean> enter = register(new Setting("Enter", Boolean.valueOf(true)));
/*  30 */   public Setting<Boolean> friends = register(new Setting("Friends", Boolean.valueOf(true)));
/*  31 */   public Setting<Boolean> soundpl = register(new Setting("Sound", Boolean.valueOf(true)));
/*  32 */   public Setting<Boolean> funnyGame = register(new Setting("FunnyGame", Boolean.valueOf(false)));
/*  33 */   public Setting<mode> Mode = register(new Setting("Mode", mode.Notification));
/*     */ 
/*     */   
/*     */   public VisualRange() {
/*  37 */     super("VisualRange", "оповещает о игроках-в зоне прогрузки", Module.Category.MISC);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityAdded(EntityAddedEvent event) {
/*  42 */     if (((Boolean)this.funnyGame.getValue()).booleanValue() && !mc.func_71356_B() && 
/*  43 */       Objects.equals((Minecraft.func_71410_x().func_147104_D()).field_78845_b, "mcfunny.su")) {
/*     */       return;
/*     */     }
/*     */     
/*  47 */     if (!isValid(event.entity)) {
/*     */       return;
/*     */     }
/*     */     
/*  51 */     if (!entities.contains(event.entity.func_70005_c_())) {
/*  52 */       entities.add(event.entity.func_70005_c_());
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/*  57 */     if (((Boolean)this.enter.getValue()).booleanValue()) {
/*  58 */       notify(event.entity, true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityRemoved(EntityRemovedEvent event) {
/*  64 */     if (!isValid(event.entity)) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     if (entities.contains(event.entity.func_70005_c_())) {
/*  69 */       entities.remove(event.entity.func_70005_c_());
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */     
/*  74 */     if (((Boolean)this.leave.getValue()).booleanValue()) {
/*  75 */       notify(event.entity, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void notify(Entity entity, boolean enter) {
/*  80 */     String message = "";
/*  81 */     if (Thunderhack.friendManager.isFriend(entity.func_70005_c_())) {
/*  82 */       message = ChatFormatting.AQUA + entity.func_70005_c_();
/*     */     } else {
/*  84 */       message = ChatFormatting.GRAY + entity.func_70005_c_();
/*     */     } 
/*     */     
/*  87 */     if (enter) {
/*  88 */       message = message + ChatFormatting.GREEN + " was found!";
/*     */     } else {
/*  90 */       message = message + ChatFormatting.RED + " left!";
/*     */     } 
/*     */     
/*  93 */     if (this.Mode.getValue() == mode.Chat) {
/*  94 */       Command.sendMessage(message);
/*     */     }
/*  96 */     if (this.Mode.getValue() == mode.Notification) {
/*  97 */       NotificationManager.publicity(message, 2, Notification.Type.WARNING);
/*     */     }
/*     */     
/* 100 */     if (((Boolean)this.soundpl.getValue()).booleanValue()) {
/*     */       try {
/* 102 */         if (enter) {
/* 103 */           mc.field_71441_e.func_184156_a(PlayerUtils.getPlayerPos(), SoundEvents.field_187802_ec, SoundCategory.AMBIENT, 150.0F, 10.0F, true);
/*     */         } else {
/* 105 */           mc.field_71441_e.func_184156_a(PlayerUtils.getPlayerPos(), SoundEvents.field_187604_bf, SoundCategory.NEUTRAL, 150.0F, 10.0F, true);
/*     */         } 
/* 107 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(Entity entity) {
/* 115 */     if (mc.field_71439_g == null || !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
/* 116 */       return false;
/*     */     }
/*     */     
/* 119 */     if (entity.func_70028_i((Entity)mc.field_71439_g) || (Thunderhack.friendManager.isFriend(entity.func_70005_c_()) && !((Boolean)this.friends.getValue()).booleanValue()) || entity.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
/* 120 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 124 */     return (entity.func_145782_y() != -100);
/*     */   }
/*     */   
/*     */   public enum mode {
/* 128 */     Chat, Notification;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\VisualRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */