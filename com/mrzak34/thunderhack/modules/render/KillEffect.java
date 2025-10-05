/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ import com.mrzak34.thunderhack.events.DeathEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.effect.EntityLightningBolt;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class KillEffect extends Module {
/* 14 */   private final Setting<Boolean> sound = register(new Setting("Sound", Boolean.valueOf(false)));
/* 15 */   private final Timer timer = new Timer();
/*    */   
/*    */   public KillEffect() {
/* 18 */     super("KillEffect", "KillEffect", Module.Category.RENDER);
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onDeath(DeathEvent event) {
/* 24 */     if (!fullNullCheck() && event.player != null) {
/* 25 */       EntityPlayer entityPlayer = event.player;
/* 26 */       if (entityPlayer != null && (
/* 27 */         ((Entity)entityPlayer).field_70128_L || (entityPlayer.func_110143_aJ() <= 0.0F && this.timer.passedMs(1500L)))) {
/* 28 */         mc.field_71441_e.func_72838_d((Entity)new EntityLightningBolt((World)mc.field_71441_e, ((Entity)entityPlayer).field_70165_t, ((Entity)entityPlayer).field_70163_u, ((Entity)entityPlayer).field_70161_v, true));
/* 29 */         if (((Boolean)this.sound.getValue()).booleanValue()) mc.field_71439_g.func_184185_a(SoundEvents.field_187754_de, 0.5F, 1.0F); 
/* 30 */         this.timer.reset();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\KillEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */