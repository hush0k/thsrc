/*    */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*    */ 
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*    */ import com.mrzak34.thunderhack.setting.Parent;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ public class ParentComponent
/*    */   extends SettingElement
/*    */ {
/*    */   public ParentComponent(Setting setting) {
/* 15 */     super(setting);
/* 16 */     Parent parent = (Parent)setting.getValue();
/* 17 */     parent.setExtended(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(int mouseX, int mouseY, float partialTicks) {
/* 22 */     super.render(mouseX, mouseY, partialTicks);
/* 23 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*    */       return;
/*    */     }
/* 26 */     FontRender.drawCentString6(getSetting().getName(), (float)(getX() + this.width / 2.0D), (float)getY() + 5.0F, (new Color(-1325400065, true)).getRGB());
/* 27 */     RenderUtil.draw2DGradientRect((float)(getX() + 10.0D), (float)(getY() + 6.0D), (float)(getX() + this.width / 2.0D - 20.0D), (float)(getY() + 7.0D), (new Color(16777215, true)).getRGB(), (new Color(16777215, true)).getRGB(), (new Color(-1325400065, true)).getRGB(), (new Color(-1325400065, true)).getRGB());
/* 28 */     RenderUtil.draw2DGradientRect((float)(getX() + this.width / 2.0D + 20.0D), (float)(getY() + 6.0D), (float)(getX() + this.width - 10.0D), (float)(getY() + 7.0D), (new Color(-1325400065, true)).getRGB(), (new Color(-1325400065, true)).getRGB(), (new Color(16777215, true)).getRGB(), (new Color(16777215, true)).getRGB());
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 33 */     if (getY() > ((ThunderGui2.getInstance()).main_posY + (ThunderGui2.getInstance()).field_146295_m) || getY() < (ThunderGui2.getInstance()).main_posY) {
/*    */       return;
/*    */     }
/* 36 */     if (this.hovered) {
/* 37 */       Parent parent = (Parent)this.setting.getValue();
/* 38 */       parent.setExtended(!parent.isExtended());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\ParentComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */