/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ 
/*    */ public class Models extends Module {
/*  8 */   public Setting<Boolean> onlySelf = register(new Setting("onlySelf", Boolean.valueOf(false)));
/*  9 */   public Setting<Boolean> friends = register(new Setting("friends", Boolean.valueOf(false)));
/* 10 */   public Setting<Boolean> friendHighlight = register(new Setting("friendHighLight", Boolean.valueOf(false)));
/* 11 */   public Setting<mode> Mode = register(new Setting("Mode", mode.Freddy));
/* 12 */   public Setting<ColorSetting> eyeColor = register(new Setting("eyeColor", new ColorSetting(-2009289807)));
/* 13 */   public Setting<ColorSetting> bodyColor = register(new Setting("bodyColor", new ColorSetting(-2009289807)));
/* 14 */   public Setting<ColorSetting> legsColor = register(new Setting("legsColor", new ColorSetting(-2009289807)));
/*    */   public Models() {
/* 16 */     super("Models", "Models", Module.Category.RENDER);
/*    */   }
/*    */   
/* 19 */   public enum mode { Amogus, Rabbit, Freddy; }
/*    */ 
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Models.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */