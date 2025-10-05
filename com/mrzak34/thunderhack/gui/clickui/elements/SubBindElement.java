/*    */ package com.mrzak34.thunderhack.gui.clickui.elements;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.setting.SubBind;
/*    */ 
/*    */ public class SubBindElement
/*    */   extends AbstractElement {
/*    */   public boolean isListening;
/*    */   
/*    */   public SubBindElement(Setting setting) {
/* 14 */     super(setting);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(int mouseX, int mouseY, float delta) {
/* 19 */     super.render(mouseX, mouseY, delta);
/* 20 */     if (this.isListening) {
/* 21 */       FontRender.drawString5("...", (float)(this.x + 3.0D), (float)(this.y + this.height / 2.0D - (FontRender.getFontHeight5() / 2.0F)), -1);
/*    */     } else {
/* 23 */       FontRender.drawString5("SubBind " + ChatFormatting.GRAY + this.setting.getValue().toString().toUpperCase(), (float)(this.x + 3.0D), (float)(this.y + this.height / 2.0D - (FontRender.getFontHeight5() / 2.0F)), -1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 29 */     if (this.hovered && button == 0) {
/* 30 */       this.isListening = !this.isListening;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void keyTyped(char chr, int keyCode) {
/* 36 */     if (this.isListening) {
/* 37 */       SubBind subBindbind = new SubBind(keyCode);
/* 38 */       if (subBindbind.toString().equalsIgnoreCase("Escape")) {
/*    */         return;
/*    */       }
/* 41 */       if (subBindbind.toString().equalsIgnoreCase("Delete")) {
/* 42 */         subBindbind = new SubBind(-1);
/*    */       }
/* 44 */       this.setting.setValue(subBindbind);
/* 45 */       this.isListening = !this.isListening;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\elements\SubBindElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */