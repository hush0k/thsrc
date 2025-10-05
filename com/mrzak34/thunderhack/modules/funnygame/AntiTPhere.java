/*    */ package com.mrzak34.thunderhack.modules.funnygame;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.ThunderUtils;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.play.server.SPacketChat;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiTPhere
/*    */   extends Module
/*    */ {
/* 20 */   public Setting<Integer> delay = register(new Setting("delay", Integer.valueOf(100), Integer.valueOf(1), Integer.valueOf(1000)));
/* 21 */   Timer timer = new Timer();
/* 22 */   Timer checktimer = new Timer();
/* 23 */   private final Setting<Modes> mode = register(new Setting("Mode", Modes.Back));
/*    */   
/*    */   public AntiTPhere() {
/* 26 */     super("AntiTPhere", "AntiTPhere", Module.Category.FUNNYGAME);
/*    */   }
/*    */   private boolean flag = false;
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 31 */     if (event.getPacket() instanceof SPacketChat) {
/* 32 */       SPacketChat packet = (SPacketChat)event.getPacket();
/* 33 */       if (packet.func_148915_c().func_150254_d().contains("Телепортирование...") && check(packet.func_148915_c().func_150254_d())) {
/* 34 */         this.flag = true;
/* 35 */         this.timer.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 42 */     if (this.flag && this.timer.passedMs(((Integer)this.delay.getValue()).intValue())) {
/* 43 */       StringBuilder log = new StringBuilder("Тебя телепортировали в X: " + (int)mc.field_71439_g.field_70165_t + " Z: " + (int)mc.field_71439_g.field_70161_v + ". Ближайшие игроки : ");
/*    */ 
/*    */       
/* 46 */       for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 47 */         if (!(entity instanceof net.minecraft.entity.player.EntityPlayer) || 
/* 48 */           entity == mc.field_71439_g) {
/*    */           continue;
/*    */         }
/* 51 */         log.append(entity.func_70005_c_()).append(" ");
/*    */       } 
/*    */       
/* 54 */       Command.sendMessage(String.valueOf(log));
/*    */       
/* 56 */       switch ((Modes)this.mode.getValue()) {
/*    */         case RTP:
/* 58 */           mc.field_71439_g.func_71165_d("/rtp");
/*    */           break;
/*    */         
/*    */         case Back:
/* 62 */           mc.field_71439_g.func_71165_d("/back");
/*    */           break;
/*    */         
/*    */         case Home:
/* 66 */           mc.field_71439_g.func_71165_d("/home");
/*    */           break;
/*    */         
/*    */         case Spawn:
/* 70 */           mc.field_71439_g.func_71165_d("/spawn");
/*    */           break;
/*    */       } 
/*    */       
/* 74 */       this.flag = false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean check(String checkstring) {
/* 80 */     return (this.checktimer.passedMs(3000L) && Objects.equals(ThunderUtils.solvename(checkstring), "err"));
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send e) {
/* 85 */     if (e.getPacket() instanceof net.minecraft.network.play.client.CPacketChatMessage)
/* 86 */       this.checktimer.reset(); 
/*    */   }
/*    */   
/*    */   public enum Modes
/*    */   {
/* 91 */     Back, Home, RTP, Spawn;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\AntiTPhere.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */