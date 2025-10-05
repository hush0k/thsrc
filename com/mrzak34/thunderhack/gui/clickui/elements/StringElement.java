/*    */ package com.mrzak34.thunderhack.gui.clickui.elements;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.util.ChatAllowedCharacters;
/*    */ 
/*    */ public class StringElement
/*    */   extends AbstractElement
/*    */ {
/*    */   public boolean isListening;
/* 13 */   private CurrentString currentString = new CurrentString("");
/*    */   public StringElement(Setting setting) {
/* 15 */     super(setting);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(int mouseX, int mouseY, float delta) {
/* 20 */     super.render(mouseX, mouseY, delta);
/* 21 */     if (this.isListening) {
/* 22 */       FontRender.drawString5(this.currentString.getString() + getIdleSign(), (float)(this.x + 3.0D), (float)(this.y + this.height / 2.0D - (FontRender.getFontHeight5() / 2.0F)), -1);
/*    */     } else {
/* 24 */       FontRender.drawString5(this.setting.getName().equals("Buttons") ? "Buttons " : ((this.setting.getName().equals("Prefix") ? ("Prefix  " + ChatFormatting.GRAY) : "") + this.setting.getValue()), (float)(this.x + 3.0D), (float)(this.y + this.height / 2.0D - (FontRender.getFontHeight5() / 2.0F)), -1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getIdleSign() {
/* 31 */     return "...";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 37 */     if (this.hovered && button == 0) {
/* 38 */       this.isListening = !this.isListening;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void keyTyped(char chr, int keyCode) {
/* 44 */     if (this.isListening) {
/* 45 */       switch (keyCode) {
/*    */         case 1:
/*    */           return;
/*    */         
/*    */         case 28:
/* 50 */           enterString();
/*    */         
/*    */         case 14:
/* 53 */           setString(SliderElement.removeLastChar(this.currentString.getString()));
/*    */           break;
/*    */       } 
/* 56 */       if (ChatAllowedCharacters.func_71566_a(chr)) {
/* 57 */         setString(this.currentString.getString() + chr);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void enterString() {
/* 64 */     if (this.currentString.getString().isEmpty()) {
/* 65 */       this.setting.setValue(this.setting.getDefaultValue());
/*    */     } else {
/* 67 */       this.setting.setValue(this.currentString.getString());
/*    */     } 
/* 69 */     setString("");
/* 70 */     this.isListening = !this.isListening;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setString(String newString) {
/* 75 */     this.currentString = new CurrentString(newString);
/*    */   }
/*    */   
/*    */   public static class CurrentString {
/*    */     private final String string;
/*    */     
/*    */     public CurrentString(String string) {
/* 82 */       this.string = string;
/*    */     }
/*    */     
/*    */     public String getString() {
/* 86 */       return this.string;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\elements\StringElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */