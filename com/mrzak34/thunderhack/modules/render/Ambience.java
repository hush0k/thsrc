/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class Ambience extends Module {
/*  8 */   public final Setting<ColorSetting> colorLight = register(new Setting("Color Light", new ColorSetting(-2013200640)));
/*    */   
/*    */   public Ambience() {
/* 11 */     super("Ambience", "изменяет цвет-окружения", Module.Category.RENDER);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Ambience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */