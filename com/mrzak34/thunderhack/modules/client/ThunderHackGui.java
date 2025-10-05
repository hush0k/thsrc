/*    */ package com.mrzak34.thunderhack.modules.client;
/*    */ 
/*    */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class ThunderHackGui extends Module {
/* 12 */   private static ThunderHackGui INSTANCE = new ThunderHackGui();
/* 13 */   public final Setting<ColorSetting> onColor1 = register(new Setting("OnColor1", new ColorSetting((new Color(71, 0, 117, 255)).getRGB())));
/* 14 */   public final Setting<ColorSetting> onColor2 = register(new Setting("OnColor2", new ColorSetting((new Color(32, 1, 96, 255)).getRGB())));
/*    */ 
/*    */   
/* 17 */   public Setting<Float> scrollSpeed = register(new Setting("ScrollSpeed", Float.valueOf(0.2F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*    */ 
/*    */   
/*    */   public ThunderHackGui() {
/* 21 */     super("ThunderGui", "новый клик гуи", Module.Category.CLIENT);
/* 22 */     setInstance();
/*    */   }
/*    */   
/*    */   public static ThunderHackGui getInstance() {
/* 26 */     if (INSTANCE == null) {
/* 27 */       INSTANCE = new ThunderHackGui();
/*    */     }
/* 29 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 33 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 38 */     Util.mc.func_147108_a((GuiScreen)ThunderGui2.getThunderGui());
/* 39 */     disable();
/*    */   }
/*    */   
/*    */   public Color getColorByTheme(int id) {
/* 43 */     switch (id) {
/*    */       case 0:
/* 45 */         return new Color(37, 27, 41, 250);
/*    */       case 1:
/* 47 */         return new Color(50, 35, 60, 250);
/*    */       case 2:
/* 49 */         return new Color(-1);
/*    */       case 3:
/* 51 */         return new Color(6645093);
/*    */       case 4:
/* 53 */         return new Color(50, 35, 60, 178);
/*    */       case 5:
/* 55 */         return new Color(133, 93, 162, 178);
/*    */       case 6:
/* 57 */         return new Color(88, 64, 107, 178);
/*    */       case 7:
/* 59 */         return new Color(25, 20, 30, 255);
/*    */       case 8:
/* 61 */         return new Color(6645093);
/*    */       case 9:
/* 63 */         return new Color(50, 35, 60, 178);
/*    */     } 
/* 65 */     return new Color(37, 27, 41, 250);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\ThunderHackGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */