/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.clickui.ColorUtil;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.math.AstolfoAnimation;
/*     */ import com.mrzak34.thunderhack.util.render.DrawHelper;
/*     */ import com.mrzak34.thunderhack.util.render.Drawable;
/*     */ import com.mrzak34.thunderhack.util.render.PaletteHelper;
/*     */ import com.mrzak34.thunderhack.util.render.RenderHelper;
/*     */ import java.awt.Color;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RadarRewrite
/*     */   extends HudElement
/*     */ {
/*  30 */   public static AstolfoAnimation astolfo = new AstolfoAnimation();
/*  31 */   public Setting<Boolean> glow = register(new Setting("TracerGlow", Boolean.valueOf(false)));
/*     */   
/*  33 */   float xOffset2 = 0.0F;
/*  34 */   float yOffset2 = 0.0F;
/*  35 */   private final Setting<Float> width = register(new Setting("TracerHeight", Float.valueOf(2.28F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*  36 */   private final Setting<Float> rad22ius = register(new Setting("TracerDown", Float.valueOf(3.63F), Float.valueOf(0.1F), Float.valueOf(20.0F)));
/*  37 */   private final Setting<Float> tracerA = register(new Setting("TracerWidth", Float.valueOf(0.44F), Float.valueOf(0.0F), Float.valueOf(8.0F)));
/*  38 */   private final Setting<Integer> xOffset = register(new Setting("TracerRadius", Integer.valueOf(68), Integer.valueOf(20), Integer.valueOf(100)));
/*  39 */   private final Setting<Integer> maxup2 = register(new Setting("PitchLock", Integer.valueOf(42), Integer.valueOf(-90), Integer.valueOf(90)));
/*  40 */   private final Setting<Integer> glowe = register(new Setting("GlowRadius", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20)));
/*  41 */   private final Setting<Integer> glowa = register(new Setting("GlowAlpha", Integer.valueOf(170), Integer.valueOf(0), Integer.valueOf(255)));
/*  42 */   private final Setting<triangleModeEn> triangleMode = register(new Setting("TracerCMode", triangleModeEn.Astolfo));
/*  43 */   private final Setting<mode2> Mode2 = register(new Setting("CircleCMode", mode2.Astolfo));
/*  44 */   private final Setting<Float> CRadius = register(new Setting("CompasRadius", Float.valueOf(47.0F), Float.valueOf(0.1F), Float.valueOf(70.0F)));
/*  45 */   private final Setting<Integer> fsef = register(new Setting("Correct", Integer.valueOf(12), Integer.valueOf(-90), Integer.valueOf(90)));
/*     */   
/*  47 */   public final Setting<Integer> colorOffset1 = register(new Setting("ColorOffset", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20)));
/*  48 */   public final Setting<ColorSetting> cColor2 = register(new Setting("CompassColor2", new ColorSetting(575714484)));
/*  49 */   private final Setting<ColorSetting> cColor = register(new Setting("CompassColor", new ColorSetting(575714484)));
/*     */   
/*  51 */   private final Setting<ColorSetting> ciColor = register(new Setting("CircleColor", new ColorSetting(575714484)));
/*  52 */   private final Setting<ColorSetting> colorf = register(new Setting("FriendColor", new ColorSetting(575714484)));
/*  53 */   private final Setting<ColorSetting> colors = register(new Setting("TracerColor", new ColorSetting(575714484)));
/*     */   private CopyOnWriteArrayList<EntityPlayer> players;
/*     */   
/*  56 */   public RadarRewrite() { super("AkrienRadar", "стрелочки", 50, 50);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     this.players = new CopyOnWriteArrayList<>(); }
/*     */   public static float clamp2(float num, float min, float max) { if (num < min)
/*     */       return min;  return Math.min(num, max); }
/*     */   public static void hexColor(int hexColor) { float red = (hexColor >> 16 & 0xFF) / 255.0F; float green = (hexColor >> 8 & 0xFF) / 255.0F; float blue = (hexColor & 0xFF) / 255.0F; float alpha = (hexColor >> 24 & 0xFF) / 255.0F; GL11.glColor4f(red, green, blue, alpha); }
/* 101 */   public static float getRotations(Entity entity) { double x = interp(entity.field_70165_t, entity.field_70142_S) - interp(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70142_S); double z = interp(entity.field_70161_v, entity.field_70136_U) - interp(mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70136_U); return (float)-(Math.atan2(x, z) * 57.29577951308232D); } public void onUpdate() { this.players.clear();
/* 102 */     this.players.addAll(mc.field_71441_e.field_73010_i);
/* 103 */     astolfo.update(); }
/*     */   public static double interp(double d, double d2) { return d2 + (d - d2) * mc.func_184121_ak(); }
/*     */   public static float getRotations(BlockPos entity) { double x = entity.func_177958_n() - interp(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70142_S); double z = entity.func_177952_p() - interp(mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70136_U);
/*     */     return (float)-(Math.atan2(x, z) * 57.29577951308232D); }
/* 107 */   public boolean isHovering() { return (normaliseX() > this.xOffset2 - 50.0F && normaliseX() < this.xOffset2 + 50.0F && normaliseY() > this.yOffset2 - 50.0F && normaliseY() < this.yOffset2 + 50.0F); } @SubscribeEvent public void onRender2D(Render2DEvent event) { super.onRender2D(event);
/* 108 */     GlStateManager.func_179094_E();
/* 109 */     rendercompass();
/* 110 */     GlStateManager.func_179121_F();
/*     */     
/* 112 */     this.xOffset2 = event.scaledResolution.func_78326_a() * getX();
/* 113 */     this.yOffset2 = event.scaledResolution.func_78328_b() * getY();
/*     */     
/* 115 */     int color = 0;
/* 116 */     switch ((triangleModeEn)this.triangleMode.getValue()) {
/*     */       case Custom:
/* 118 */         color = ((ColorSetting)this.colors.getValue()).getColor();
/*     */         break;
/*     */       case Astolfo:
/* 121 */         color = DrawHelper.astolfo(false, 1).getRGB();
/*     */         break;
/*     */       case Rainbow:
/* 124 */         color = DrawHelper.rainbow(300, 1.0F, 1.0F).getRGB();
/*     */         break;
/*     */     } 
/* 127 */     float xOffset = event.scaledResolution.func_78326_a() * getX();
/* 128 */     float yOffset = event.scaledResolution.func_78328_b() * getY();
/*     */     
/* 130 */     GlStateManager.func_179094_E();
/* 131 */     GlStateManager.func_179109_b(this.xOffset2, this.yOffset2, 0.0F);
/* 132 */     GL11.glRotatef(90.0F / Math.abs(90.0F / clamp2(mc.field_71439_g.field_70125_A, ((Integer)this.maxup2.getValue()).intValue(), 90.0F)) - 90.0F - ((Integer)this.fsef.getValue()).intValue(), 1.0F, 0.0F, 0.0F);
/* 133 */     GlStateManager.func_179109_b(-this.xOffset2, -this.yOffset2, 0.0F);
/*     */ 
/*     */     
/* 136 */     for (EntityPlayer e : this.players) {
/* 137 */       if (e != mc.field_71439_g) {
/* 138 */         GL11.glPushMatrix();
/* 139 */         float yaw = getRotations((Entity)e) - mc.field_71439_g.field_70177_z;
/* 140 */         GL11.glTranslatef(xOffset, yOffset, 0.0F);
/* 141 */         GL11.glRotatef(yaw, 0.0F, 0.0F, 1.0F);
/* 142 */         GL11.glTranslatef(-xOffset, -yOffset, 0.0F);
/* 143 */         if (Thunderhack.friendManager.isFriend(e)) {
/* 144 */           drawTracerPointer(xOffset, yOffset - ((Integer)this.xOffset.getValue()).intValue(), ((Float)this.width.getValue()).floatValue() * 5.0F, ((ColorSetting)this.colorf.getValue()).getColor());
/*     */         } else {
/* 146 */           drawTracerPointer(xOffset, yOffset - ((Integer)this.xOffset.getValue()).intValue(), ((Float)this.width.getValue()).floatValue() * 5.0F, color);
/*     */         } 
/* 148 */         GL11.glTranslatef(xOffset, yOffset, 0.0F);
/* 149 */         GL11.glRotatef(-yaw, 0.0F, 0.0F, 1.0F);
/* 150 */         GL11.glTranslatef(-xOffset, -yOffset, 0.0F);
/* 151 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 152 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/* 155 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 156 */     GlStateManager.func_179121_F(); }
/*     */ 
/*     */   
/*     */   public void rendercompass() {
/* 160 */     ScaledResolution sr = new ScaledResolution(mc);
/* 161 */     float x = sr.func_78326_a() * getX();
/* 162 */     float y = sr.func_78328_b() * getY();
/*     */     
/* 164 */     float nigga = Math.abs(90.0F / clamp2(mc.field_71439_g.field_70125_A, ((Integer)this.maxup2.getValue()).intValue(), 90.0F));
/*     */     
/* 166 */     if (this.Mode2.getValue() == mode2.Custom) {
/* 167 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.0F, ((ColorSetting)this.ciColor.getValue()).getColorObject(), false, (mode2)this.Mode2.getValue());
/* 168 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.5F, ((ColorSetting)this.ciColor.getValue()).getColorObject(), false, (mode2)this.Mode2.getValue());
/*     */     } 
/* 170 */     if (this.Mode2.getValue() == mode2.Rainbow) {
/* 171 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.0F, PaletteHelper.rainbow(300, 1.0F, 1.0F), false, (mode2)this.Mode2.getValue());
/* 172 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.5F, PaletteHelper.rainbow(300, 1.0F, 1.0F), false, (mode2)this.Mode2.getValue());
/*     */     } 
/* 174 */     if (this.Mode2.getValue() == mode2.Astolfo) {
/* 175 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.0F, ((ColorSetting)this.ciColor.getValue()).getColorObject(), false, (mode2)this.Mode2.getValue());
/* 176 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.5F, ((ColorSetting)this.ciColor.getValue()).getColorObject(), false, (mode2)this.Mode2.getValue());
/*     */     } 
/* 178 */     if (this.Mode2.getValue() == mode2.TwoColor) {
/* 179 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.0F, ((ColorSetting)this.ciColor.getValue()).getColorObject(), false, (mode2)this.Mode2.getValue());
/* 180 */       RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue() - 2.5F, ((ColorSetting)this.ciColor.getValue()).getColorObject(), false, (mode2)this.Mode2.getValue());
/*     */     } 
/*     */     
/* 183 */     RenderHelper.drawEllipsCompas(-((int)mc.field_71439_g.field_70177_z), x, y, nigga, 1.0F, ((Float)this.CRadius.getValue()).floatValue(), ((ColorSetting)this.cColor.getValue()).getColorObject(), true, mode2.Custom);
/*     */   }
/*     */   
/*     */   public void drawTracerPointer(float x, float y, float size, int color) {
/* 187 */     boolean blend = GL11.glIsEnabled(3042);
/* 188 */     GL11.glEnable(3042);
/* 189 */     boolean depth = GL11.glIsEnabled(2929);
/* 190 */     GL11.glDisable(2929);
/*     */     
/* 192 */     GL11.glDisable(3553);
/* 193 */     GL11.glBlendFunc(770, 771);
/* 194 */     GL11.glEnable(2848);
/* 195 */     GL11.glPushMatrix();
/*     */     
/* 197 */     hexColor(color);
/* 198 */     GL11.glBegin(7);
/* 199 */     GL11.glVertex2d(x, y);
/* 200 */     GL11.glVertex2d((x - size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 201 */     GL11.glVertex2d(x, (y + size - ((Float)this.rad22ius.getValue()).floatValue()));
/* 202 */     GL11.glVertex2d(x, y);
/* 203 */     GL11.glEnd();
/*     */     
/* 205 */     hexColor(ColorUtil.darker(new Color(color), 0.8F).getRGB());
/* 206 */     GL11.glBegin(7);
/* 207 */     GL11.glVertex2d(x, y);
/* 208 */     GL11.glVertex2d(x, (y + size - ((Float)this.rad22ius.getValue()).floatValue()));
/* 209 */     GL11.glVertex2d((x + size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 210 */     GL11.glVertex2d(x, y);
/* 211 */     GL11.glEnd();
/*     */ 
/*     */     
/* 214 */     hexColor(ColorUtil.darker(new Color(color), 0.6F).getRGB());
/* 215 */     GL11.glBegin(7);
/* 216 */     GL11.glVertex2d((x - size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 217 */     GL11.glVertex2d((x + size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 218 */     GL11.glVertex2d(x, (y + size - ((Float)this.rad22ius.getValue()).floatValue()));
/* 219 */     GL11.glVertex2d((x - size * ((Float)this.tracerA.getValue()).floatValue()), (y + size));
/* 220 */     GL11.glEnd();
/* 221 */     GL11.glPopMatrix();
/*     */     
/* 223 */     GL11.glEnable(3553);
/* 224 */     if (!blend)
/* 225 */       GL11.glDisable(3042); 
/* 226 */     GL11.glDisable(2848);
/*     */     
/* 228 */     if (((Boolean)this.glow.getValue()).booleanValue()) {
/* 229 */       Drawable.drawBlurredShadow(x - size * ((Float)this.tracerA.getValue()).floatValue(), y, x + size * ((Float)this.tracerA.getValue()).floatValue() - x - size * ((Float)this.tracerA.getValue()).floatValue(), size, ((Integer)this.glowe.getValue()).intValue(), DrawHelper.injectAlpha(new Color(color), ((Integer)this.glowa.getValue()).intValue()));
/*     */     }
/* 231 */     if (depth) {
/* 232 */       GL11.glEnable(2929);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum mode2
/*     */   {
/* 238 */     Custom, Rainbow, Astolfo, TwoColor;
/*     */   }
/*     */   
/*     */   public enum triangleModeEn {
/* 242 */     Custom, Astolfo, Rainbow;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\RadarRewrite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */