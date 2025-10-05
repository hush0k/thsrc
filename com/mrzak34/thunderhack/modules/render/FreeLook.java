/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.TurnEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class FreeLook extends Module {
/* 11 */   private float dYaw = 0.0F;
/* 12 */   private float dPitch = 0.0F;
/* 13 */   private final Setting<Boolean> autoThirdPerson = register(new Setting("AutoThirdPerson", Boolean.valueOf(true)));
/*    */   
/*    */   public FreeLook() {
/* 16 */     super("FreeLook", "FreeLook", Module.Category.RENDER);
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 20 */     this.dYaw = 0.0F;
/* 21 */     this.dPitch = 0.0F;
/*    */     
/* 23 */     if (((Boolean)this.autoThirdPerson.getValue()).booleanValue()) {
/* 24 */       mc.field_71474_y.field_74320_O = 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 29 */     if (((Boolean)this.autoThirdPerson.getValue()).booleanValue()) {
/* 30 */       mc.field_71474_y.field_74320_O = 0;
/*    */     }
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
/* 36 */     if (mc.field_71474_y.field_74320_O > 0) {
/* 37 */       event.setYaw(event.getYaw() + this.dYaw);
/* 38 */       event.setPitch(event.getPitch() + this.dPitch);
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onTurnEvent(TurnEvent event) {
/* 44 */     if (mc.field_71474_y.field_74320_O > 0) {
/* 45 */       this.dYaw = (float)(this.dYaw + event.getYaw() * 0.15D);
/* 46 */       this.dPitch = (float)(this.dPitch - event.getPitch() * 0.15D);
/* 47 */       this.dPitch = MathHelper.func_76131_a(this.dPitch, -90.0F, 90.0F);
/* 48 */       event.setCanceled(true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\FreeLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */