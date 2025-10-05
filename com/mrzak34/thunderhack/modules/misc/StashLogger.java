/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.notification.Notification;
/*    */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.play.server.SPacketChunkData;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class StashLogger extends Module {
/* 23 */   public Setting<Boolean> chests = register(new Setting("Chests", Boolean.valueOf(true)));
/* 24 */   public Setting<Integer> chestsAmount = register(new Setting("ChestsAmount", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(100)));
/* 25 */   public Setting<Boolean> shulker = register(new Setting("Shulkers", Boolean.valueOf(true)));
/* 26 */   public Setting<Integer> shulkersAmount = register(new Setting("ShulkersAmount", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(100)));
/* 27 */   public Setting<Boolean> saveCoords = register(new Setting("SaveCoords", Boolean.valueOf(true)));
/*    */   public StashLogger() {
/* 29 */     super("StashFinder", "ищет стеши в зоне-прогрузки", Module.Category.MISC);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 34 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 37 */     if (event.getPacket() instanceof SPacketChunkData) {
/* 38 */       SPacketChunkData l_Packet = (SPacketChunkData)event.getPacket();
/* 39 */       int l_ChestsCount = 0;
/* 40 */       int shulkers = 0;
/* 41 */       for (NBTTagCompound l_Tag : l_Packet.func_189554_f()) {
/* 42 */         String l_Id = l_Tag.func_74779_i("id");
/* 43 */         if (l_Id.equals("minecraft:chest") && ((Boolean)this.chests.getValue()).booleanValue()) {
/* 44 */           l_ChestsCount++;
/*    */           continue;
/*    */         } 
/* 47 */         if (!l_Id.equals("minecraft:shulker_box") || !((Boolean)this.shulker.getValue()).booleanValue())
/* 48 */           continue;  shulkers++;
/*    */       } 
/* 50 */       if (l_ChestsCount >= ((Integer)this.chestsAmount.getValue()).intValue()) {
/* 51 */         SendMessage(String.format("%s chests located at X: %s, Z: %s", new Object[] { Integer.valueOf(l_ChestsCount), Integer.valueOf(l_Packet.func_149273_e() * 16), Integer.valueOf(l_Packet.func_149271_f() * 16) }), true);
/* 52 */         if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass(NotificationManager.class)).isOn()) {
/* 53 */           NotificationManager.publicity(String.format("%s chests located at X: %s, Z: %s", new Object[] { Integer.valueOf(l_ChestsCount), Integer.valueOf(l_Packet.func_149273_e() * 16), Integer.valueOf(l_Packet.func_149271_f() * 16) }), 5, Notification.Type.SUCCESS);
/*    */         }
/*    */       } 
/* 56 */       if (shulkers >= ((Integer)this.shulkersAmount.getValue()).intValue()) {
/* 57 */         SendMessage(String.format("%s shulker boxes at X: %s, Z: %s", new Object[] { Integer.valueOf(shulkers), Integer.valueOf(l_Packet.func_149273_e() * 16), Integer.valueOf(l_Packet.func_149271_f() * 16) }), true);
/* 58 */         if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass(NotificationManager.class)).isOn()) {
/* 59 */           NotificationManager.publicity(String.format("%s shulker boxes at X: %s, Z: %s", new Object[] { Integer.valueOf(shulkers), Integer.valueOf(l_Packet.func_149273_e() * 16), Integer.valueOf(l_Packet.func_149271_f() * 16) }), 5, Notification.Type.SUCCESS);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void SendMessage(String message, boolean save) {
/* 67 */     String server = mc.func_71356_B() ? "SINGLEPLAYER" : ((ServerData)Objects.requireNonNull((T)mc.func_147104_D())).field_78845_b;
/* 68 */     if (((Boolean)this.saveCoords.getValue()).booleanValue() && save) {
/*    */       try {
/* 70 */         FileWriter writer = new FileWriter("ThunderHack/misc/stashlogger.txt", true);
/* 71 */         writer.write("[" + server + "]: " + message + "\n");
/* 72 */         writer.close();
/* 73 */       } catch (IOException e) {
/* 74 */         e.printStackTrace();
/*    */       } 
/*    */     }
/* 77 */     mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_194007_a(SoundEvents.field_187604_bf, 1.0F, 1.0F));
/* 78 */     Command.sendMessage(ChatFormatting.GREEN + message);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\StashLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */