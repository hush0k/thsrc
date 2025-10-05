/*    */ package com.mrzak34.thunderhack.modules.player;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import net.minecraft.init.MobEffects;
/*    */ 
/*    */ public class AntiBadEffects
/*    */   extends Module {
/*    */   public AntiBadEffects() {
/*  9 */     super("AntiBadEffects", "AntiBadEffects", Module.Category.PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 14 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76440_q)) {
/* 15 */       mc.field_71439_g.func_184596_c(MobEffects.field_76440_q);
/*    */     }
/* 17 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76431_k)) {
/* 18 */       mc.field_71439_g.func_184596_c(MobEffects.field_76431_k);
/*    */     }
/* 20 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76419_f)) {
/* 21 */       mc.field_71439_g.func_184596_c(MobEffects.field_76419_f);
/*    */     }
/* 23 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_188424_y)) {
/* 24 */       mc.field_71439_g.func_184596_c(MobEffects.field_188424_y);
/*    */     }
/* 26 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76421_d))
/* 27 */       mc.field_71439_g.func_184596_c(MobEffects.field_76421_d); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\AntiBadEffects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */