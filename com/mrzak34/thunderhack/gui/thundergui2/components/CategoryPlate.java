/*    */ package com.mrzak34.thunderhack.gui.thundergui2.components;
/*    */ 
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.gui.hud.elements.Particles;
/*    */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.util.RoundedShader;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import com.mrzak34.thunderhack.util.render.Stencil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class CategoryPlate
/*    */ {
/* 19 */   float category_animation = 0.0F;
/*    */   
/*    */   private final Module.Category cat;
/*    */   private int posX;
/*    */   private int posY;
/*    */   private final ScaledResolution sr;
/*    */   
/*    */   public CategoryPlate(Module.Category cat, int posX, int posY) {
/* 27 */     this.cat = cat;
/* 28 */     this.posX = posX;
/* 29 */     this.posY = posY;
/* 30 */     this.sr = new ScaledResolution(Util.mc);
/*    */   }
/*    */   
/*    */   public static double deltaTime() {
/* 34 */     return (Minecraft.func_175610_ah() > 0) ? (1.0D / Minecraft.func_175610_ah()) : 1.0D;
/*    */   }
/*    */   
/*    */   public static float fast(float end, float start, float multiple) {
/* 38 */     return (1.0F - MathUtil.clamp((float)(deltaTime() * multiple), 0.0F, 1.0F)) * end + MathUtil.clamp((float)(deltaTime() * multiple), 0.0F, 1.0F) * start;
/*    */   }
/*    */   
/*    */   public void render(int MouseX, int MouseY) {
/* 42 */     this.category_animation = fast(this.category_animation, isHovered(MouseX, MouseY) ? 1.0F : 0.0F, 15.0F);
/* 43 */     if (isHovered(MouseX, MouseY)) {
/* 44 */       RoundedShader.drawRound(this.posX, this.posY, 84.0F, 15.0F, 2.0F, new Color(25, 20, 30, (int)MathUtil.clamp(65.0F * this.category_animation, 0.0F, 255.0F)));
/* 45 */       GL11.glPushMatrix();
/* 46 */       Stencil.write(false);
/* 47 */       Particles.roundedRect((this.posX - 1), (this.posY - 1), 85.5D, 16.5D, 4.0D, new Color(0, 0, 0, 255));
/* 48 */       Stencil.erase(true);
/* 49 */       RenderUtil.drawBlurredShadow((MouseX - 20), (MouseY - 20), 40.0F, 40.0F, 60, new Color(-1017816450, true));
/* 50 */       Stencil.dispose();
/* 51 */       GL11.glPopMatrix();
/*    */     } 
/* 53 */     FontRender.drawString6(this.cat.getName(), (this.posX + 5), (this.posY + 6), -1, false);
/*    */   }
/*    */   
/*    */   public void movePosition(float deltaX, float deltaY) {
/* 57 */     this.posY = (int)(this.posY + deltaY);
/* 58 */     this.posX = (int)(this.posX + deltaX);
/*    */   }
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
/* 62 */     if (isHovered(mouseX, mouseY)) {
/* 63 */       (ThunderGui2.getInstance()).new_category = this.cat;
/* 64 */       if ((ThunderGui2.getInstance()).current_category == null) {
/* 65 */         (ThunderGui2.getInstance()).current_category = Module.Category.HUD;
/* 66 */         (ThunderGui2.getInstance()).new_category = this.cat;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean isHovered(int mouseX, int mouseY) {
/* 72 */     return (mouseX > this.posX && mouseX < this.posX + 84 && mouseY > this.posY && mouseY < this.posY + 15);
/*    */   }
/*    */   
/*    */   public Module.Category getCategory() {
/* 76 */     return this.cat;
/*    */   }
/*    */   
/*    */   public int getPosY() {
/* 80 */     return this.posY;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\thundergui2\components\CategoryPlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */