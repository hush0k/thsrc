/*    */ package com.mrzak34.thunderhack.modules.client;
/*    */ import com.mrzak34.thunderhack.gui.hud.elements.HudEditorGui;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class HudEditor extends Module {
/*  8 */   private static HudEditor INSTANCE = new HudEditor();
/*    */   
/*    */   public HudEditor() {
/* 11 */     super("HudEditor", "худ изменять да", Module.Category.CLIENT);
/* 12 */     setInstance();
/*    */   }
/*    */   
/*    */   public static HudEditor getInstance() {
/* 16 */     if (INSTANCE == null) {
/* 17 */       INSTANCE = new HudEditor();
/*    */     }
/* 19 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private void setInstance() {
/* 23 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 29 */     Util.mc.func_147108_a((GuiScreen)HudEditorGui.getHudGui());
/* 30 */     toggle();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\HudEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */