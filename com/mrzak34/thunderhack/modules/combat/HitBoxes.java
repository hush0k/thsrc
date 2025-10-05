/*    */ package com.mrzak34.thunderhack.modules.combat;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class HitBoxes extends Module {
/*  7 */   public Setting<Float> expand = register(new Setting("Value", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(5.0F)));
/*    */   
/*    */   public HitBoxes() {
/* 10 */     super("HitBoxes", "Увеличивает хитбоксы", Module.Category.COMBAT);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\HitBoxes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */