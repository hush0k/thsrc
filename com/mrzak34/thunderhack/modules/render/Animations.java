/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IItemRenderer;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Animations
/*    */   extends Module
/*    */ {
/* 14 */   private static Animations INSTANCE = new Animations();
/* 15 */   public Setting<Boolean> ed = register(new Setting("EquipDisable", Boolean.valueOf(true)));
/* 16 */   public Setting<Boolean> auraOnly = register(new Setting("auraOnly", Boolean.valueOf(false)));
/* 17 */   public Setting<Float> fapSmooth = register(new Setting("fapSmooth", Float.valueOf(4.0F), Float.valueOf(0.5F), Float.valueOf(15.0F)));
/* 18 */   public Setting<Integer> slowValue = register(new Setting("SlowValue", Integer.valueOf(6), Integer.valueOf(1), Integer.valueOf(50)));
/* 19 */   public Setting<rmode> rMode = register(new Setting("SwordMode", rmode.Swipe));
/* 20 */   public float shitfix = 1.0F;
/*    */   
/*    */   public boolean abobka228 = false;
/*    */   
/*    */   public Animations() {
/* 25 */     super("Animations", "анимации удара", Module.Category.RENDER);
/* 26 */     setInstance();
/*    */   }
/*    */   
/*    */   public static Animations getInstance() {
/* 30 */     if (INSTANCE == null) {
/* 31 */       INSTANCE = new Animations();
/*    */     }
/* 33 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 37 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 42 */     if (mc.field_71441_e != null && mc.field_71439_g != null) {
/* 43 */       this.shitfix = mc.field_71439_g.func_70678_g(mc.func_184121_ak());
/*    */     }
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/* 48 */     if (fullNullCheck()) {
/*    */       return;
/*    */     }
/* 51 */     this.abobka228 = (((IItemRenderer)mc.func_175597_ag()).getEquippedProgressMainHand() < 1.0F);
/* 52 */     if (((Boolean)this.ed.getValue()).booleanValue() && ((IItemRenderer)mc.func_175597_ag()).getEquippedProgressMainHand() >= 0.9D) {
/* 53 */       ((IItemRenderer)mc.func_175597_ag()).setEquippedProgressMainHand(1.0F);
/* 54 */       ((IItemRenderer)mc.func_175597_ag()).setItemStackMainHand(mc.field_71439_g.func_184614_ca());
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum rmode {
/* 59 */     Swipe, Rich, Glide, Default, New, Oblique, Fap, Slow;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Animations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */