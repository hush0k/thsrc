/*    */ package com.mrzak34.thunderhack.gui.clickui.elements;
/*    */ 
/*    */ import com.mrzak34.thunderhack.gui.clickui.base.AbstractElement;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.notification.Animation;
/*    */ import com.mrzak34.thunderhack.notification.DecelerateAnimation;
/*    */ import com.mrzak34.thunderhack.notification.Direction;
/*    */ import com.mrzak34.thunderhack.setting.Parent;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.Drawable;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ParentElement
/*    */   extends AbstractElement {
/*    */   private final Setting<Parent> parentSetting;
/* 17 */   private final Animation rotation = (Animation)new DecelerateAnimation(240, 1.0D, Direction.FORWARDS);
/*    */   
/*    */   public ParentElement(Setting<Parent> setting) {
/* 20 */     super(setting);
/* 21 */     this.parentSetting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(int mouseX, int mouseY, float delta) {
/* 26 */     super.render(mouseX, mouseY, delta);
/*    */     
/* 28 */     this.rotation.setDirection(((Parent)getParentSetting().getValue()).isExtended() ? Direction.BACKWARDS : Direction.FORWARDS);
/* 29 */     float tx = (float)(this.x + this.width - 7.0D);
/* 30 */     float ty = (float)(this.y + 8.5D);
/* 31 */     float thetaRotation = (float)(-180.0D * this.rotation.getOutput());
/* 32 */     GlStateManager.func_179094_E();
/*    */     
/* 34 */     GlStateManager.func_179109_b(tx, ty, 0.0F);
/* 35 */     GlStateManager.func_179114_b(thetaRotation, 0.0F, 0.0F, 1.0F);
/* 36 */     GlStateManager.func_179109_b(-tx, -ty, 0.0F);
/*    */     
/* 38 */     Drawable.drawTexture(new ResourceLocation("textures/arrow.png"), this.x + this.width - 10.0D, this.y + 5.5D, 6.0D, 6.0D);
/* 39 */     GlStateManager.func_179121_F();
/* 40 */     FontRender.drawString5(this.setting.getName(), (float)(this.x + 3.0D), (float)(this.y + this.height / 2.0D - (FontRender.getFontHeight5() / 2.0F)) + 3.0F, -1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 46 */     if (this.hovered) {
/* 47 */       ((Parent)getParentSetting().getValue()).setExtended(!((Parent)getParentSetting().getValue()).isExtended());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Setting<Parent> getParentSetting() {
/* 53 */     return this.parentSetting;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\clickui\elements\ParentElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */