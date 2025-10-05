/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ public class LevitationControl extends Module {
/* 11 */   private final Setting<Integer> upAmplifier = register(new Setting("upAmplifier", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(3)));
/* 12 */   private final Setting<Integer> downAmplifier = register(new Setting("downAmplifier", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(3)));
/*    */   public LevitationControl() {
/* 14 */     super("LevitCtrl", "хз херня какаята", Module.Category.MOVEMENT);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 19 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_188424_y)) {
/*    */       
/* 21 */       int amplifier = ((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.field_71439_g.func_70660_b(Objects.<Potion>requireNonNull(Potion.func_188412_a(25))))).func_76458_c();
/*    */       
/* 23 */       if (mc.field_71474_y.field_74314_A.func_151470_d()) {
/* 24 */         mc.field_71439_g.field_70181_x = (0.05D * (amplifier + 1) - mc.field_71439_g.field_70181_x) * 0.2D * ((Integer)this.upAmplifier.getValue()).intValue();
/* 25 */       } else if (mc.field_71474_y.field_74311_E.func_151470_d()) {
/* 26 */         mc.field_71439_g.field_70181_x = -((0.05D * (amplifier + 1) - mc.field_71439_g.field_70181_x) * 0.2D * ((Integer)this.downAmplifier.getValue()).intValue());
/*    */       } else {
/* 28 */         mc.field_71439_g.field_70181_x = 0.0D;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\LevitationControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */