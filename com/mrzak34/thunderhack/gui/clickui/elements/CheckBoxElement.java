/*    */ package com.mrzak34.thunderhack.gui.clickui.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.RoundedShader;
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class CheckBoxElement
/*    */   extends AbstractElement
/*    */ {
/* 15 */   float animation = 0.0F;
/*    */   
/*    */   public CheckBoxElement(Setting setting) {
/* 18 */     super(setting);
/*    */   }
/*    */   
/*    */   public static double deltaTime() {
/* 22 */     return (Minecraft.func_175610_ah() > 0) ? (1.0D / Minecraft.func_175610_ah()) : 1.0D;
/*    */   }
/*    */   
/*    */   public static float fast(float end, float start, float multiple) {
/* 26 */     return (1.0F - MathUtil.clamp((float)(deltaTime() * multiple), 0.0F, 1.0F)) * end + MathUtil.clamp((float)(deltaTime() * multiple), 0.0F, 1.0F) * start;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */ 
/*    */   
/*    */   public void render(int mouseX, int mouseY, float delta) {
/* 35 */     super.render(mouseX, mouseY, delta);
/*    */     
/* 37 */     this.animation = fast(this.animation, ((Boolean)this.setting.getValue()).booleanValue() ? 1.0F : 0.0F, 15.0F);
/*    */     
/* 39 */     double paddingX = (7.0F * this.animation);
/*    */ 
/*    */     
/* 42 */     Color color = ClickGui.getInstance().getColor(0);
/* 43 */     RoundedShader.drawRound((float)(this.x + this.width - 18.0D), (float)(this.y + this.height / 2.0D - 4.0D), 15.0F, 8.0F, 4.0F, (paddingX > 4.0D) ? color : new Color(-5066319));
/*    */     
/* 45 */     RoundedShader.drawRound((float)(this.x + this.width - 17.0D + paddingX), (float)(this.y + this.height / 2.0D - 3.0D), 6.0F, 6.0F, 3.0F, true, new Color(-1));
/*    */     
/* 47 */     FontRender.drawString5(this.setting.getName(), (float)(this.x + 3.0D), (float)(this.y + this.height / 2.0D - (FontRender.getFontHeight5() / 2.0F)) + 2.0F, -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 52 */     if (this.hovered && button == 0)
/* 53 */       this.setting.setValue(Boolean.valueOf(!((Boolean)this.setting.getValue()).booleanValue())); 
/*    */   }
/*    */   
/*    */   public void resetAnimation() {}
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\elements\CheckBoxElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */