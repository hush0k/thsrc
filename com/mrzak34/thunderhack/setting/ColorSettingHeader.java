/*    */ package com.mrzak34.thunderhack.setting;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ public class ColorSettingHeader
/*    */ {
/*    */   boolean opened;
/*    */   Color color;
/*    */   String name;
/*    */   
/*    */   public ColorSettingHeader(boolean extended) {
/* 12 */     this.opened = extended;
/*    */   }
/*    */   
/*    */   public void setOpenedCSH(boolean a) {
/* 16 */     this.opened = a;
/*    */   }
/*    */   
/*    */   public boolean getStateCSH() {
/* 20 */     return this.opened;
/*    */   }
/*    */   
/*    */   public void setNameCSH(String a) {
/* 24 */     this.name = a;
/*    */   }
/*    */   
/*    */   public String getNameCSH() {
/* 28 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setColorCSH(Color a) {
/* 32 */     this.color = a;
/*    */   }
/*    */   
/*    */   public Color getColorCSH() {
/* 36 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\setting\ColorSettingHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */