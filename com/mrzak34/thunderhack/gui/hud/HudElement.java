/*    */ package com.mrzak34.thunderhack.gui.hud;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.PositionSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HudElement
/*    */   extends Module
/*    */ {
/*    */   int height;
/*    */   int width;
/*    */   int dragX;
/* 19 */   int dragY = 0;
/*    */   private boolean mousestate = false;
/* 21 */   float x1 = 0.0F;
/* 22 */   float y1 = 0.0F;
/*    */   
/* 24 */   public final Setting<ColorSetting> shadowColor = register(new Setting("ShadowColor", new ColorSetting(-15724528)));
/* 25 */   public final Setting<ColorSetting> color2 = register(new Setting("Color", new ColorSetting(-15724528)));
/* 26 */   public final Setting<ColorSetting> color3 = register(new Setting("Color2", new ColorSetting(-979657829)));
/* 27 */   public final Setting<ColorSetting> textColor = register(new Setting("TextColor", new ColorSetting(12500670)));
/*    */   private final Setting<PositionSetting> pos;
/*    */   
/* 30 */   public HudElement(String name, String description, int width, int height) { super(name, description, Module.Category.HUD);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     this.pos = register(new Setting("Position", new PositionSetting(0.5F, 0.5F))); this.height = height; this.width = width; } public HudElement(String name, String description, String eng_description, int width, int height) { super(name, description, eng_description, Module.Category.HUD); this.pos = register(new Setting("Position", new PositionSetting(0.5F, 0.5F)));
/*    */     this.height = height;
/*    */     this.width = width; } public int normaliseX() {
/* 44 */     return (int)(Mouse.getX() / 2.0F);
/*    */   }
/*    */   
/*    */   public int normaliseY() {
/* 48 */     ScaledResolution sr = new ScaledResolution(mc);
/* 49 */     return (-Mouse.getY() + sr.func_78328_b() + sr.func_78328_b()) / 2;
/*    */   }
/*    */   
/*    */   public boolean isHovering() {
/* 53 */     return (normaliseX() > this.x1 && normaliseX() < this.x1 + this.width && normaliseY() > this.y1 && normaliseY() < this.y1 + this.height);
/*    */   }
/*    */   
/*    */   public void onRender2D(Render2DEvent e) {
/* 57 */     this.y1 = e.scaledResolution.func_78328_b() * ((PositionSetting)this.pos.getValue()).getY();
/* 58 */     this.x1 = e.scaledResolution.func_78326_a() * ((PositionSetting)this.pos.getValue()).getX();
/*    */     
/* 60 */     if ((mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat || mc.field_71462_r instanceof com.mrzak34.thunderhack.gui.hud.elements.HudEditorGui || mc.field_71462_r instanceof com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2) && 
/* 61 */       Mouse.isButtonDown(0) && this.mousestate) {
/* 62 */       ((PositionSetting)this.pos.getValue()).setX((normaliseX() - this.dragX) / e.scaledResolution.func_78326_a());
/* 63 */       ((PositionSetting)this.pos.getValue()).setY((normaliseY() - this.dragY) / e.scaledResolution.func_78328_b());
/*    */     } 
/*    */     
/* 66 */     if (Mouse.isButtonDown(0)) {
/* 67 */       if (!this.mousestate && isHovering()) {
/* 68 */         this.dragX = (int)(normaliseX() - ((PositionSetting)this.pos.getValue()).getX() * e.scaledResolution.func_78326_a());
/* 69 */         this.dragY = (int)(normaliseY() - ((PositionSetting)this.pos.getValue()).getY() * e.scaledResolution.func_78328_b());
/* 70 */         this.mousestate = true;
/*    */       } 
/*    */     } else {
/* 73 */       this.mousestate = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public float getPosX() {
/* 78 */     return this.x1;
/*    */   }
/*    */   
/*    */   public float getPosY() {
/* 82 */     return this.y1;
/*    */   }
/*    */   
/*    */   public float getX() {
/* 86 */     return ((PositionSetting)this.pos.getValue()).x;
/*    */   }
/*    */   
/*    */   public float getY() {
/* 90 */     return ((PositionSetting)this.pos.getValue()).y;
/*    */   }
/*    */   
/*    */   public void setHeight(int h) {
/* 94 */     this.height = h;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\HudElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */