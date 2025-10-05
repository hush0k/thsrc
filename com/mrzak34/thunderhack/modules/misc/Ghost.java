/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class Ghost
/*    */   extends Module {
/*    */   private boolean bypass = false;
/*    */   
/*    */   public Ghost() {
/* 12 */     super("Ghost", "Жить после смерти", Module.Category.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 17 */     this.bypass = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 22 */     if (mc.field_71439_g != null) mc.field_71439_g.func_71004_bE(); 
/* 23 */     this.bypass = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 28 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/*    */       return; 
/* 30 */     if (mc.field_71439_g.func_110143_aJ() == 0.0F) {
/* 31 */       mc.field_71439_g.func_70606_j(20.0F);
/* 32 */       mc.field_71439_g.field_70128_L = false;
/* 33 */       this.bypass = true;
/* 34 */       mc.func_147108_a(null);
/* 35 */       mc.field_71439_g.func_70634_a(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 41 */     if (this.bypass && event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer) event.setCanceled(true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\Ghost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */