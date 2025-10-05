/*    */ package com.mrzak34.thunderhack.modules.misc;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class ThirdPersView
/*    */   extends Module {
/*  8 */   public Setting<Integer> x = register(new Setting("x", Integer.valueOf(0), Integer.valueOf(-180), Integer.valueOf(180)));
/*  9 */   public Setting<Integer> y = register(new Setting("y", Integer.valueOf(0), Integer.valueOf(-180), Integer.valueOf(180)));
/* 10 */   public Setting<Float> z = register(new Setting("z", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*    */   public ThirdPersView() {
/* 12 */     super("ThirdPersView", "ThirdPersView", Module.Category.MISC);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\ThirdPersView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */