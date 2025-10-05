/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class FullBright
/*    */   extends Module {
/* 13 */   public Setting<Mode> mode = register(new Setting("Mode", Mode.GAMMA));
/* 14 */   public Setting<Boolean> effects = register(new Setting("Effects", Boolean.valueOf(false)));
/* 15 */   private float previousSetting = 1.0F;
/*    */   
/*    */   public FullBright() {
/* 18 */     super("Fullbright", "делает поярче", Module.Category.RENDER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 24 */     this.previousSetting = mc.field_71474_y.field_74333_Y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 29 */     if (this.mode.getValue() == Mode.GAMMA) {
/* 30 */       mc.field_71474_y.field_74333_Y = 1000.0F;
/*    */     }
/* 32 */     if (this.mode.getValue() == Mode.POTION) {
/* 33 */       mc.field_71439_g.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 5210));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 39 */     if (this.mode.getValue() == Mode.POTION) {
/* 40 */       mc.field_71439_g.func_184589_d(MobEffects.field_76439_r);
/*    */     }
/* 42 */     mc.field_71474_y.field_74333_Y = this.previousSetting;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 47 */     if (event.getPacket() instanceof SPacketEntityEffect && ((Boolean)this.effects.getValue()).booleanValue()) {
/* 48 */       SPacketEntityEffect packet = (SPacketEntityEffect)event.getPacket();
/* 49 */       if (mc.field_71439_g != null && packet.func_149426_d() == mc.field_71439_g.func_145782_y() && (packet.func_149427_e() == 9 || packet.func_149427_e() == 15))
/* 50 */         event.setCanceled(true); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum Mode
/*    */   {
/* 56 */     GAMMA,
/* 57 */     POTION;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\FullBright.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */