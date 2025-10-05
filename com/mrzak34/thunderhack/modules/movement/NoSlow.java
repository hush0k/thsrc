/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventSync;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraftforge.client.event.InputUpdateEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NoSlow
/*    */   extends Module {
/* 13 */   public Setting<Integer> speed = register(new Setting("Speed", Integer.valueOf(100), Integer.valueOf(1), Integer.valueOf(100)));
/* 14 */   private final Setting<mode> Mode = register(new Setting("Mode", mode.NCP));
/*    */   
/*    */   public NoSlow() {
/* 17 */     super("NoSlow", "NoSlow", Module.Category.MOVEMENT);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onInput(InputUpdateEvent e) {
/* 22 */     if ((this.Mode.getValue() != mode.StrictNCP || this.Mode.getValue() != mode.NCP) && mc.field_71439_g.func_184587_cr() && !mc.field_71439_g.func_184218_aH()) {
/* 23 */       mc.field_71439_g.field_71158_b.field_192832_b *= 5.0F * ((Integer)this.speed.getValue()).intValue() / 100.0F;
/* 24 */       mc.field_71439_g.field_71158_b.field_78902_a *= 5.0F * ((Integer)this.speed.getValue()).intValue() / 100.0F;
/*    */     } 
/*    */     
/* 27 */     if ((this.Mode.getValue() == mode.StrictNCP || this.Mode.getValue() == mode.NCP) && 
/* 28 */       mc.field_71439_g.func_184587_cr() && !mc.field_71439_g.func_184218_aH() && !mc.field_71439_g.func_70093_af()) {
/* 29 */       if (this.Mode.getValue() == mode.StrictNCP && (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemFood || mc.field_71439_g.func_184592_cb().func_77973_b() instanceof net.minecraft.item.ItemFood))
/* 30 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c)); 
/* 31 */       mc.field_71439_g.field_71158_b.field_192832_b = (float)(mc.field_71439_g.field_71158_b.field_192832_b / 0.2D);
/* 32 */       mc.field_71439_g.field_71158_b.field_78902_a = (float)(mc.field_71439_g.field_71158_b.field_78902_a / 0.2D);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onPreMotion(EventSync event) {
/* 39 */     if (mc.field_71439_g.func_184587_cr()) {
/* 40 */       if (mc.field_71439_g.field_70122_E) {
/* 41 */         if (mc.field_71439_g.field_70173_aa % 2 == 0) {
/* 42 */           if (this.Mode.getValue() == mode.Matrix) {
/* 43 */             mc.field_71439_g.field_70159_w *= (mc.field_71439_g.field_70702_br == 0.0F) ? 0.55D : 0.5D;
/* 44 */             mc.field_71439_g.field_70179_y *= (mc.field_71439_g.field_70702_br == 0.0F) ? 0.55D : 0.5D;
/* 45 */           } else if (this.Mode.getValue() == mode.SunRise) {
/* 46 */             mc.field_71439_g.field_70159_w *= 0.47D;
/* 47 */             mc.field_71439_g.field_70179_y *= 0.47D;
/* 48 */           } else if (this.Mode.getValue() == mode.Matrix2) {
/* 49 */             mc.field_71439_g.field_70159_w *= 0.5D;
/* 50 */             mc.field_71439_g.field_70179_y *= 0.5D;
/*    */           } 
/*    */         }
/* 53 */       } else if (this.Mode.getValue() == mode.Matrix2) {
/* 54 */         mc.field_71439_g.field_70159_w *= 0.95D;
/* 55 */         mc.field_71439_g.field_70179_y *= 0.95D;
/* 56 */       } else if (mc.field_71439_g.field_70143_R > ((this.Mode.getValue() == mode.Matrix) ? 0.7D : 0.2D)) {
/* 57 */         if (this.Mode.getValue() == mode.Matrix) {
/* 58 */           mc.field_71439_g.field_70159_w *= 0.93D;
/* 59 */           mc.field_71439_g.field_70179_y *= 0.93D;
/* 60 */         } else if (this.Mode.getValue() == mode.SunRise) {
/* 61 */           mc.field_71439_g.field_70159_w *= 0.91D;
/* 62 */           mc.field_71439_g.field_70179_y *= 0.91D;
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum mode
/*    */   {
/* 73 */     NCP, StrictNCP, Matrix, Matrix2, SunRise;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\NoSlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */