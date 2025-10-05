/*    */ package com.mrzak34.thunderhack.gui.clickui.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*    */ import com.mrzak34.thunderhack.notification.Animation;
/*    */ import com.mrzak34.thunderhack.notification.DecelerateAnimation;
/*    */ import com.mrzak34.thunderhack.notification.Direction;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.Drawable;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ModeElement
/*    */   extends AbstractElement
/*    */ {
/* 18 */   private final Animation rotation = (Animation)new DecelerateAnimation(240, 1.0D, Direction.FORWARDS);
/*    */   public Setting setting2;
/*    */   private boolean open;
/*    */   private double wheight;
/*    */   
/*    */   public ModeElement(Setting setting) {
/* 24 */     super(setting);
/* 25 */     this.setting2 = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(int mouseX, int mouseY, float delta) {
/* 30 */     super.render(mouseX, mouseY, delta);
/* 31 */     this.rotation.setDirection(this.open ? Direction.BACKWARDS : Direction.FORWARDS);
/*    */ 
/*    */     
/* 34 */     float tx = (float)(this.x + this.width - 7.0D);
/* 35 */     float ty = (float)(this.y + this.wheight / 2.0D);
/*    */     
/* 37 */     float thetaRotation = (float)(-180.0D * this.rotation.getOutput());
/* 38 */     GlStateManager.func_179094_E();
/*    */     
/* 40 */     GlStateManager.func_179109_b(tx, ty, 0.0F);
/* 41 */     GlStateManager.func_179114_b(thetaRotation, 0.0F, 0.0F, 1.0F);
/* 42 */     GlStateManager.func_179109_b(-tx, -ty, 0.0F);
/*    */     
/* 44 */     Drawable.drawTexture(new ResourceLocation("textures/arrow.png"), this.x + this.width - 10.0D, this.y + (this.wheight - 6.0D) / 2.0D, 6.0D, 6.0D);
/* 45 */     GlStateManager.func_179121_F();
/*    */     
/* 47 */     FontRender.drawString5(this.setting2.getName(), (float)(this.x + 3.0D), (float)(this.y + this.wheight / 2.0D - (FontRender.getFontHeight5() / 2.0F)) + 3.0F, -1);
/* 48 */     FontRender.drawString5(this.setting2.currentEnumName(), (float)(this.x + this.width - 16.0D - FontRender.getStringWidth5(this.setting.currentEnumName())), 3.0F + (float)(this.y + this.wheight / 2.0D - (FontRender.getFontHeight5() / 2.0F)), -1);
/*    */     
/* 50 */     if (this.open) {
/* 51 */       Color color = ClickGui.getInstance().getColor(0);
/* 52 */       double offsetY = 0.0D;
/* 53 */       for (int i = 0; i <= (this.setting2.getModes()).length - 1; i++) {
/* 54 */         FontRender.drawCentString5(this.setting2.getModes()[i], (float)this.x + (float)this.width / 2.0F, (float)(this.y + this.wheight + (6.0F - FontRender.getFontHeight5() / 2.0F - 1.0F) + offsetY), this.setting2.currentEnumName().equalsIgnoreCase(this.setting2.getModes()[i]) ? color.getRGB() : -1);
/* 55 */         offsetY += 12.0D;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 63 */     if (Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.wheight)) {
/* 64 */       if (button == 0) {
/* 65 */         this.setting2.increaseEnum();
/*    */       } else {
/* 67 */         this.open = !this.open;
/*    */       } 
/*    */     }
/* 70 */     if (this.open) {
/* 71 */       double offsetY = 0.0D;
/* 72 */       for (int i = 0; i <= (this.setting2.getModes()).length - 1; i++) {
/* 73 */         if (Drawable.isHovered(mouseX, mouseY, this.x, this.y + this.wheight + offsetY, this.width, 12.0D) && button == 0)
/* 74 */           this.setting2.setEnumByNumber(i); 
/* 75 */         offsetY += 12.0D;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetAnimation() {}
/*    */ 
/*    */   
/*    */   public void setWHeight(double height) {
/* 86 */     this.wheight = height;
/*    */   }
/*    */   
/*    */   public boolean isOpen() {
/* 90 */     return this.open;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\elements\ModeElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */