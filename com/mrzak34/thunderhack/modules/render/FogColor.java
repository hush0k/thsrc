/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class FogColor extends Module {
/*  8 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  9 */   public Setting<Float> distance = register(new Setting("Distance", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*    */   public FogColor() {
/* 11 */     super("FogColor", "меняет цвет тумана", Module.Category.RENDER);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\FogColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */