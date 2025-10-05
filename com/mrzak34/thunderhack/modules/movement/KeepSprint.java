/*    */ package com.mrzak34.thunderhack.modules.movement;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class KeepSprint
/*    */   extends Module {
/*  8 */   public final Setting<Boolean> sprint = register(new Setting("Sprint", Boolean.valueOf(true)));
/*  9 */   public final Setting<Float> motion = register(new Setting("motion", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0F)));
/*    */   public KeepSprint() {
/* 11 */     super("KeepSprint", "Не сбивать скорость при ударе", Module.Category.MOVEMENT);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\KeepSprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */