/*     */ package com.mrzak34.thunderhack.gui.hud.elements;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.HudElement;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.Aura;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoExplosion;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.C4Aura;
/*     */ import com.mrzak34.thunderhack.modules.misc.NameProtect;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.PositionSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.PNGtoResourceLocation;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.render.Stencil;
/*     */ import com.mrzak34.thunderhack.util.shaders.BetterAnimation;
/*     */ import com.mrzak34.thunderhack.util.shaders.BetterDynamicAnimation;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class TargetHud
/*     */   extends HudElement
/*     */ {
/*  53 */   private static final ResourceLocation thudPic = new ResourceLocation("textures/thud.png");
/*  54 */   public static BetterDynamicAnimation healthanimation = new BetterDynamicAnimation();
/*  55 */   public static BetterDynamicAnimation ebaloAnimation = new BetterDynamicAnimation();
/*     */   static ResourceLocation customImg;
/*  57 */   private final ArrayList<Particles> particles = new ArrayList<>();
/*  58 */   private final Timer timer = new Timer();
/*  59 */   public BetterAnimation animation = new BetterAnimation();
/*     */   float ticks;
/*  61 */   private final Setting<ColorSetting> color = register(new Setting("Color1", new ColorSetting(-16492289)));
/*  62 */   private final Setting<ColorSetting> color2 = register(new Setting("Color2", new ColorSetting(-2353224)));
/*  63 */   private final Setting<Integer> slices = register(new Setting("colorOffset1", Integer.valueOf(135), Integer.valueOf(10), Integer.valueOf(500)));
/*  64 */   private final Setting<Integer> slices1 = register(new Setting("colorOffset2", Integer.valueOf(211), Integer.valueOf(10), Integer.valueOf(500)));
/*  65 */   private final Setting<Integer> slices2 = register(new Setting("colorOffset3", Integer.valueOf(162), Integer.valueOf(10), Integer.valueOf(500)));
/*  66 */   private final Setting<Integer> slices3 = register(new Setting("colorOffset4", Integer.valueOf(60), Integer.valueOf(10), Integer.valueOf(500)));
/*  67 */   private final Setting<Integer> pcount = register(new Setting("ParticleCount", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(50)));
/*  68 */   private final Setting<Float> psize = register(new Setting("ParticleSize", Float.valueOf(4.0F), Float.valueOf(0.1F), Float.valueOf(15.0F)));
/*  69 */   private final Setting<Integer> blurRadius = register(new Setting("BallonBlur", Integer.valueOf(10), Float.valueOf(1.0F), Integer.valueOf(10)));
/*  70 */   private final Setting<PositionSetting> pos = register(new Setting("Position", new PositionSetting(0.5F, 0.5F)));
/*  71 */   private final Setting<Integer> animX = register(new Setting("AnimationX", Integer.valueOf(0), Integer.valueOf(-2000), Integer.valueOf(2000)));
/*  72 */   private final Setting<Integer> animY = register(new Setting("AnimationY", Integer.valueOf(0), Integer.valueOf(-2000), Integer.valueOf(2000)));
/*  73 */   private final Setting<HPmodeEn> hpMode = register(new Setting("HP Mode", HPmodeEn.HP));
/*  74 */   private final Setting<ImageModeEn> imageMode = register(new Setting("Image", ImageModeEn.Anime));
/*     */   private boolean sentParticles;
/*     */   private boolean direction = false;
/*     */   private EntityPlayer target;
/*     */   
/*     */   public TargetHud() {
/*  80 */     super("TargetHud", "ПИЗДАТЕЙШИЙ", 150, 50);
/*     */   }
/*     */   
/*     */   public static void renderTexture(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/*  84 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(thudPic);
/*  85 */     GL11.glEnable(3042);
/*  86 */     Gui.func_152125_a((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
/*  87 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   public static void sizeAnimation(double width, double height, double animation) {
/*  91 */     GL11.glTranslated(width, height, 0.0D);
/*  92 */     GL11.glScaled(animation, animation, 1.0D);
/*  93 */     GL11.glTranslated(-width, -height, 0.0D);
/*     */   }
/*     */   
/*     */   public static String getPotionName(Potion potion) {
/*  97 */     if (potion == MobEffects.field_76428_l)
/*  98 */       return "Reg"; 
/*  99 */     if (potion == MobEffects.field_76420_g)
/* 100 */       return "Str"; 
/* 101 */     if (potion == MobEffects.field_76424_c)
/* 102 */       return "Spd"; 
/* 103 */     if (potion == MobEffects.field_76422_e)
/* 104 */       return "H"; 
/* 105 */     if (potion == MobEffects.field_76437_t)
/* 106 */       return "W"; 
/* 107 */     if (potion == MobEffects.field_76429_m) {
/* 108 */       return "Res";
/*     */     }
/* 110 */     return "pon";
/*     */   }
/*     */   
/*     */   public static String getDurationString(PotionEffect pe) {
/* 114 */     if (pe.func_100011_g()) {
/* 115 */       return "*:*";
/*     */     }
/* 117 */     int var1 = pe.func_76459_b();
/* 118 */     return StringUtils.func_76337_a(var1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius) {
/* 123 */     float z = 0.0F;
/* 124 */     if (paramXStart > paramXEnd) {
/* 125 */       z = paramXStart;
/* 126 */       paramXStart = paramXEnd;
/* 127 */       paramXEnd = z;
/*     */     } 
/*     */     
/* 130 */     if (paramYStart > paramYEnd) {
/* 131 */       z = paramYStart;
/* 132 */       paramYStart = paramYEnd;
/* 133 */       paramYEnd = z;
/*     */     } 
/*     */     
/* 136 */     double x1 = (paramXStart + radius);
/* 137 */     double y1 = (paramYStart + radius);
/* 138 */     double x2 = (paramXEnd - radius);
/* 139 */     double y2 = (paramYEnd - radius);
/*     */     
/* 141 */     GL11.glEnable(2848);
/* 142 */     GL11.glLineWidth(1.0F);
/* 143 */     GL11.glBegin(9);
/* 144 */     double degree = 0.017453292519943295D; double i;
/* 145 */     for (i = 0.0D; i <= 90.0D; i++)
/* 146 */       GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius); 
/* 147 */     for (i = 90.0D; i <= 180.0D; i++)
/* 148 */       GL11.glVertex2d(x2 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius); 
/* 149 */     for (i = 180.0D; i <= 270.0D; i++)
/* 150 */       GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius); 
/* 151 */     for (i = 270.0D; i <= 360.0D; i++)
/* 152 */       GL11.glVertex2d(x1 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius); 
/* 153 */     GL11.glEnd();
/* 154 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
/* 158 */     double thing = speed / 4.0D % 1.0D;
/* 159 */     float val = MathHelper.func_76131_a((float)Math.sin(18.84955592153876D * thing) / 2.0F + 0.5F, 0.0F, 1.0F);
/* 160 */     return new Color(lerp(cl1.getRed() / 255.0F, cl2.getRed() / 255.0F, val), lerp(cl1.getGreen() / 255.0F, cl2.getGreen() / 255.0F, val), lerp(cl1.getBlue() / 255.0F, cl2.getBlue() / 255.0F, val));
/*     */   }
/*     */   
/*     */   public static float lerp(float a, float b, float f) {
/* 164 */     return a + f * (b - a);
/*     */   }
/*     */   
/*     */   public static void renderPlayerModelTexture(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
/* 168 */     ResourceLocation skin = target.func_110306_p();
/* 169 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(skin);
/* 170 */     GL11.glEnable(3042);
/* 171 */     Gui.func_152125_a((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
/* 172 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 177 */     this.animation.update(this.direction);
/* 178 */     healthanimation.update();
/* 179 */     ebaloAnimation.update();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTHud(Render2DEvent e) {
/* 187 */     if (Aura.target != null) {
/* 188 */       if (Aura.target instanceof EntityPlayer) {
/* 189 */         this.target = (EntityPlayer)Aura.target;
/* 190 */         this.direction = true;
/*     */       } else {
/* 192 */         this.target = null;
/* 193 */         this.direction = false;
/*     */       } 
/* 195 */     } else if (C4Aura.target != null) {
/* 196 */       this.target = C4Aura.target;
/* 197 */       this.direction = true;
/* 198 */     } else if (AutoExplosion.trgt != null) {
/* 199 */       this.target = AutoExplosion.trgt;
/* 200 */       this.direction = true;
/* 201 */     } else if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).getTarget() != null) {
/* 202 */       this.target = ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).getTarget();
/* 203 */       this.direction = true;
/* 204 */     } else if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiChat || mc.field_71462_r instanceof HudEditorGui || (mc.field_71462_r instanceof ThunderGui2 && (ThunderGui2.getInstance()).current_category == Module.Category.HUD && ThunderGui2.currentMode == ThunderGui2.CurrentMode.Modules)) {
/* 205 */       this.target = (EntityPlayer)mc.field_71439_g;
/* 206 */       this.direction = true;
/*     */     } else {
/* 208 */       this.direction = false;
/* 209 */       if (this.animation.getAnimationd() < 0.02D)
/* 210 */         this.target = null; 
/*     */     } 
/* 212 */     if (this.target == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 217 */     GlStateManager.func_179094_E();
/* 218 */     sizeAnimation((getPosX() + 75.0F + ((Integer)this.animX.getValue()).intValue()), (getPosY() + 25.0F + ((Integer)this.animY.getValue()).intValue()), this.animation.getAnimationd());
/*     */     
/* 220 */     if (this.animation.getAnimationd() > 0.0D) {
/*     */       
/* 222 */       float hurtPercent = (this.target.field_70737_aN - mc.func_184121_ak()) / 6.0F;
/*     */ 
/*     */       
/* 225 */       Particles.roundedRect(getPosX(), getPosY(), 70.0D, 50.0D, 12.0D, new Color(0, 0, 0, 139));
/* 226 */       Particles.roundedRect((getPosX() + 50.0F), getPosY(), 100.0D, 50.0D, 12.0D, new Color(0, 0, 0, 255));
/*     */ 
/*     */ 
/*     */       
/* 230 */       if (this.imageMode.getValue() != ImageModeEn.None) {
/* 231 */         GL11.glPushMatrix();
/* 232 */         Stencil.write(false);
/* 233 */         boolean texture2 = GL11.glIsEnabled(3553);
/* 234 */         boolean blend2 = GL11.glIsEnabled(3042);
/* 235 */         GL11.glDisable(3553);
/* 236 */         GL11.glEnable(3042);
/* 237 */         GL11.glBlendFunc(770, 771);
/* 238 */         Particles.roundedRect((getPosX() + 50.0F), getPosY(), 100.0D, 50.0D, 12.0D, new Color(0, 0, 0, 255));
/* 239 */         if (!blend2)
/* 240 */           GL11.glDisable(3042); 
/* 241 */         if (texture2)
/* 242 */           GL11.glEnable(3553); 
/* 243 */         Stencil.erase(true);
/* 244 */         GlStateManager.func_179124_c(0.3F, 0.3F, 0.3F);
/* 245 */         if (this.imageMode.getValue() == ImageModeEn.Anime) {
/* 246 */           renderTexture((getPosX() + 50.0F), getPosY(), 0.0F, 0.0F, 100, 50, 100, 50, 100.0F, 50.0F);
/*     */         } else {
/* 248 */           renderCustomTexture((getPosX() + 50.0F), getPosY(), 0.0F, 0.0F, 100, 50, 100, 50, 100.0F, 50.0F);
/* 249 */         }  Stencil.dispose();
/* 250 */         GL11.glPopMatrix();
/* 251 */         GlStateManager.func_179117_G();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       for (Particles p : this.particles) {
/* 258 */         if (p.opacity > 4.0D) p.render2D();
/*     */       
/*     */       } 
/* 261 */       if (this.timer.passedMs(16L)) {
/* 262 */         this.ticks += 0.1F;
/* 263 */         for (Particles p : this.particles) {
/* 264 */           p.updatePosition();
/*     */           
/* 266 */           if (p.opacity < 1.0D) this.particles.remove(p); 
/*     */         } 
/* 268 */         this.timer.reset();
/*     */       } 
/*     */       
/* 271 */       ArrayList<Particles> removeList = new ArrayList<>();
/* 272 */       for (Particles p : this.particles) {
/* 273 */         if (p.opacity <= 1.0D) {
/* 274 */           removeList.add(p);
/*     */         }
/*     */       } 
/*     */       
/* 278 */       for (Particles p : removeList) {
/* 279 */         this.particles.remove(p);
/*     */       }
/*     */       
/* 282 */       if (this.target.field_70737_aN == 9 && !this.sentParticles) {
/* 283 */         for (int i = 0; i <= ((Integer)this.pcount.getValue()).intValue(); i++) {
/* 284 */           Particles p = new Particles();
/* 285 */           Color color = Particles.mixColors(((ColorSetting)this.color.getValue()).getColorObject(), ((ColorSetting)this.color2.getValue()).getColorObject(), (Math.sin((this.ticks + getPosX() * 0.4F + i)) + 1.0D) * 0.5D);
/* 286 */           p.init((getPosX() + 19.0F), (getPosY() + 19.0F), (Math.random() - 0.5D) * 2.0D * 1.4D, (Math.random() - 0.5D) * 2.0D * 1.4D, Math.random() * ((Float)this.psize.getValue()).floatValue(), color);
/* 287 */           this.particles.add(p);
/*     */         } 
/* 289 */         this.sentParticles = true;
/*     */       } 
/*     */       
/* 292 */       if (this.target.field_70737_aN == 8) this.sentParticles = false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       GL11.glPushMatrix();
/* 298 */       Stencil.write(false);
/* 299 */       boolean texture = GL11.glIsEnabled(3553);
/* 300 */       boolean blend = GL11.glIsEnabled(3042);
/* 301 */       GL11.glDisable(3553);
/* 302 */       GL11.glEnable(3042);
/*     */       
/* 304 */       float hurtPercent2 = hurtPercent;
/* 305 */       ebaloAnimation.setValue(hurtPercent2);
/* 306 */       hurtPercent2 = (float)ebaloAnimation.getAnimationD();
/* 307 */       if (hurtPercent2 < 0.0F && hurtPercent2 > -0.17D) {
/* 308 */         hurtPercent2 = 0.0F;
/*     */       }
/*     */       
/* 311 */       GL11.glBlendFunc(770, 771);
/* 312 */       fastRoundedRect(getPosX() + 5.5F + hurtPercent2, getPosY() + 5.5F + hurtPercent2, getPosX() + 44.0F - hurtPercent2 * 2.0F, getPosY() + 44.0F - hurtPercent2 * 2.0F, 6.0F);
/*     */       
/* 314 */       if (!blend)
/* 315 */         GL11.glDisable(3042); 
/* 316 */       if (texture)
/* 317 */         GL11.glEnable(3553); 
/* 318 */       Stencil.erase(true);
/* 319 */       GlStateManager.func_179124_c(1.0F, 1.0F - hurtPercent, 1.0F - hurtPercent);
/*     */       
/* 321 */       renderPlayerModelTexture((getPosX() + 5.5F + hurtPercent2), (getPosY() + 5.5F + hurtPercent2), 3.0F, 3.0F, 3, 3, (int)(39.0D - hurtPercent2 * 2.0D), (int)(39.0D - hurtPercent2 * 2.0D), 24.0F, 24.5F, (AbstractClientPlayer)this.target);
/* 322 */       renderPlayerModelTexture((getPosX() + 5.5F + hurtPercent2), (getPosY() + 5.5F + hurtPercent2), 15.0F, 3.0F, 3, 3, (int)(39.0D - hurtPercent2 * 2.0D), (int)(39.0D - hurtPercent2 * 2.0D), 24.0F, 24.5F, (AbstractClientPlayer)this.target);
/*     */       
/* 324 */       Stencil.dispose();
/* 325 */       GL11.glPopMatrix();
/* 326 */       GlStateManager.func_179117_G();
/* 327 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */ 
/*     */ 
/*     */       
/* 331 */       float health = Math.min(20.0F, this.target.func_110143_aJ());
/* 332 */       healthanimation.setValue(health);
/* 333 */       health = (float)healthanimation.getAnimationD();
/* 334 */       Color a = TwoColoreffect(((ColorSetting)this.color.getValue()).getColorObject(), ((ColorSetting)this.color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)this.slices.getValue()).intValue() * 2.55D / 60.0D);
/* 335 */       Color b = TwoColoreffect(((ColorSetting)this.color.getValue()).getColorObject(), ((ColorSetting)this.color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)this.slices1.getValue()).intValue() * 2.55D / 60.0D);
/* 336 */       Color c = TwoColoreffect(((ColorSetting)this.color.getValue()).getColorObject(), ((ColorSetting)this.color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)this.slices2.getValue()).intValue() * 2.55D / 60.0D);
/* 337 */       Color d = TwoColoreffect(((ColorSetting)this.color.getValue()).getColorObject(), ((ColorSetting)this.color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)this.slices3.getValue()).intValue() * 2.55D / 60.0D);
/*     */       
/* 339 */       RenderUtil.drawBlurredShadow(getPosX() + 54.0F, getPosY() + 34.0F - 12.0F, 90.0F, 8.0F, ((Integer)this.blurRadius.getValue()).intValue(), a);
/*     */       
/* 341 */       RoundedShader.drawGradientRound(getPosX() + 55.0F, getPosY() + 35.0F - 12.0F, 90.0F, 8.0F, 2.0F, a.darker().darker(), b.darker().darker().darker().darker(), c.darker().darker().darker().darker(), d.darker().darker().darker().darker());
/* 342 */       RoundedShader.drawGradientRound(getPosX() + 55.0F, getPosY() + 35.0F - 12.0F, 90.0F * health / 20.0F, 8.0F, 2.0F, a, b, c, d);
/* 343 */       if (this.hpMode.getValue() == HPmodeEn.HP) {
/* 344 */         FontRender.drawCentString6(String.valueOf(Math.round(10.0D * health) / 10.0D), getPosX() + 100.0F, getPosY() + 25.5F, -1);
/*     */       } else {
/* 346 */         FontRender.drawCentString6((Math.round(10.0D * health) / 10.0D / 20.0D * 100.0D) + "%", getPosX() + 100.0F, getPosY() + 25.5F, -1);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 351 */       NonNullList<ItemStack> armor = this.target.field_71071_by.field_70460_b;
/* 352 */       ItemStack[] items = { this.target.func_184614_ca(), (ItemStack)armor.get(3), (ItemStack)armor.get(2), (ItemStack)armor.get(1), (ItemStack)armor.get(0), this.target.func_184592_cb() };
/*     */       
/* 354 */       float xItemOffset = getPosX() + 60.0F;
/* 355 */       for (ItemStack itemStack : items) {
/* 356 */         if (!itemStack.func_190926_b()) {
/* 357 */           GL11.glPushMatrix();
/* 358 */           GL11.glTranslated(xItemOffset, (getPosY() + 35.0F), 0.0D);
/* 359 */           GL11.glScaled(0.75D, 0.75D, 0.75D);
/*     */           
/* 361 */           RenderHelper.func_74520_c();
/* 362 */           mc.func_175599_af().func_184391_a((EntityLivingBase)mc.field_71439_g, itemStack, 0, 0);
/* 363 */           mc.func_175599_af().func_175030_a(mc.field_71466_p, itemStack, 0, 0);
/* 364 */           RenderHelper.func_74518_a();
/*     */           
/* 366 */           GL11.glPopMatrix();
/* 367 */           xItemOffset += 14.0F;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 372 */       if (!((NameProtect)Thunderhack.moduleManager.getModuleByClass(NameProtect.class)).isEnabled()) {
/* 373 */         FontRender.drawString6(this.target.func_70005_c_() + " | " + (Math.round(10.0D * mc.field_71439_g.func_70032_d((Entity)this.target)) / 10.0D) + " m", getPosX() + 55.0F, getPosY() + 5.0F, -1, false);
/*     */       } else {
/* 375 */         FontRender.drawString6("Protected | " + (Math.round(10.0D * mc.field_71439_g.func_70032_d((Entity)this.target)) / 10.0D) + " m", getPosX() + 55.0F, getPosY() + 5.0F, -1, false);
/*     */       } 
/*     */       
/* 378 */       drawPotionEffect(this.target);
/*     */     } 
/* 380 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/* 385 */     super.onRender2D(e);
/* 386 */     GL11.glPushAttrib(1048575);
/* 387 */     renderTHud(e);
/* 388 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   public void renderCustomTexture(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 392 */     if (customImg == null) {
/* 393 */       if (PNGtoResourceLocation.getCustomImg("targethud", "png") != null) {
/* 394 */         customImg = PNGtoResourceLocation.getCustomImg("targethud", "png");
/*     */       } else {
/* 396 */         Command.sendMessage("Перейди в .minecraft/ThunderHack/images и добавь туда png картинку с названием targethud");
/* 397 */         toggle();
/*     */       } 
/*     */       return;
/*     */     } 
/* 401 */     Minecraft.func_71410_x().func_110434_K().func_110577_a(customImg);
/* 402 */     GL11.glEnable(3042);
/* 403 */     Gui.func_152125_a((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
/* 404 */     GL11.glDisable(3042);
/*     */   }
/*     */   
/*     */   private void drawPotionEffect(EntityPlayer entity) {
/* 408 */     StringBuilder finalString = new StringBuilder();
/* 409 */     for (PotionEffect potionEffect : entity.func_70651_bq()) {
/* 410 */       Potion potion = potionEffect.func_188419_a();
/* 411 */       if (potion != MobEffects.field_76428_l && potion != MobEffects.field_76424_c && potion != MobEffects.field_76420_g && potion != MobEffects.field_76437_t) {
/*     */         continue;
/*     */       }
/*     */       
/* 415 */       boolean potRanOut = (potionEffect.func_76459_b() != 0.0D);
/* 416 */       if (!entity.func_70644_a(potion) || !potRanOut)
/*     */         continue; 
/* 418 */       GlStateManager.func_179094_E();
/* 419 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 420 */       finalString.append(I18n.func_135052_a(getPotionName(potion), new Object[0])).append((potionEffect.func_76458_c() < 1) ? "" : Integer.valueOf(potionEffect.func_76458_c() + 1)).append(" ").append(getDurationString(potionEffect)).append(" ");
/* 421 */       GlStateManager.func_179121_F();
/*     */     } 
/* 423 */     FontRender.drawString7(finalString.toString(), getPosX() + 55.0F, getPosY() + 14.0F, (new Color(9276813)).getRGB(), false);
/*     */   }
/*     */   
/*     */   public int normaliseX() {
/* 427 */     return (int)(Mouse.getX() / 2.0F);
/*     */   }
/*     */   
/*     */   public int normaliseY() {
/* 431 */     ScaledResolution sr = new ScaledResolution(mc);
/* 432 */     return (-Mouse.getY() + sr.func_78328_b() + sr.func_78328_b()) / 2;
/*     */   }
/*     */   
/*     */   public boolean isHovering() {
/* 436 */     return (normaliseX() > getPosX() && normaliseX() < getPosX() + 150.0F && normaliseY() > getPosY() && normaliseY() < getPosY() + 50.0F);
/*     */   }
/*     */   
/*     */   public enum HPmodeEn {
/* 440 */     HP,
/* 441 */     Percentage;
/*     */   }
/*     */   
/*     */   public enum ImageModeEn
/*     */   {
/* 446 */     None,
/* 447 */     Anime,
/* 448 */     Custom;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\gui\hud\elements\TargetHud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */