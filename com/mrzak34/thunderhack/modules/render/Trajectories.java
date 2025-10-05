/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Trajectories
/*     */   extends Module
/*     */ {
/*  26 */   private final Setting<ColorSetting> ncolor = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  27 */   public Setting<Boolean> landed = register(new Setting("Landed", Boolean.valueOf(true)));
/*  28 */   public Setting<ColorSetting> circleColor = register(new Setting("Color", new ColorSetting(869950564, true)));
/*  29 */   public Setting<Float> circleWidth = register(new Setting("Width", Float.valueOf(2.5F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*     */   
/*     */   public Trajectories() {
/*  32 */     super("Trajectories", "Draws trajectories.", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   public static double getRenderPosX() {
/*  36 */     return ((IRenderManager)mc.func_175598_ae()).getRenderPosX();
/*     */   }
/*     */   
/*     */   public static double getRenderPosY() {
/*  40 */     return ((IRenderManager)mc.func_175598_ae()).getRenderPosY();
/*     */   }
/*     */   
/*     */   public static void startRender() {
/*  44 */     GL11.glPushAttrib(1048575);
/*  45 */     GL11.glPushMatrix();
/*  46 */     GL11.glDisable(3008);
/*  47 */     GL11.glEnable(3042);
/*  48 */     GL11.glBlendFunc(770, 771);
/*  49 */     GL11.glDisable(3553);
/*  50 */     GL11.glDisable(2929);
/*  51 */     GL11.glDepthMask(false);
/*  52 */     GL11.glEnable(2884);
/*  53 */     GL11.glEnable(2848);
/*  54 */     GL11.glHint(3154, 4353);
/*  55 */     GL11.glDisable(2896);
/*     */   }
/*     */   
/*     */   public static void endRender() {
/*  59 */     GL11.glEnable(2896);
/*  60 */     GL11.glDisable(2848);
/*  61 */     GL11.glEnable(3553);
/*  62 */     GL11.glEnable(2929);
/*  63 */     GL11.glDisable(3042);
/*  64 */     GL11.glEnable(3008);
/*  65 */     GL11.glDepthMask(true);
/*  66 */     GL11.glCullFace(1029);
/*  67 */     GL11.glPopMatrix();
/*  68 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   public static double getRenderPosZ() {
/*  72 */     return ((IRenderManager)mc.func_175598_ae()).getRenderPosZ();
/*     */   }
/*     */   
/*     */   protected boolean isThrowable(Item item) {
/*  76 */     return (item instanceof net.minecraft.item.ItemEnderPearl || item instanceof net.minecraft.item.ItemExpBottle || item instanceof net.minecraft.item.ItemSnowball || item instanceof net.minecraft.item.ItemEgg || item instanceof net.minecraft.item.ItemSplashPotion || item instanceof net.minecraft.item.ItemLingeringPotion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getDistance(Item item) {
/*  85 */     return (item instanceof net.minecraft.item.ItemBow) ? 1.0F : 0.4F;
/*     */   }
/*     */   
/*     */   protected float getThrowVelocity(Item item) {
/*  89 */     if (item instanceof net.minecraft.item.ItemSplashPotion || item instanceof net.minecraft.item.ItemLingeringPotion) {
/*  90 */       return 0.5F;
/*     */     }
/*  92 */     if (item instanceof net.minecraft.item.ItemExpBottle) {
/*  93 */       return 0.59F;
/*     */     }
/*  95 */     return 1.5F;
/*     */   }
/*     */   
/*     */   protected int getThrowPitch(Item item) {
/*  99 */     if (item instanceof net.minecraft.item.ItemSplashPotion || item instanceof net.minecraft.item.ItemLingeringPotion || item instanceof net.minecraft.item.ItemExpBottle) {
/* 100 */       return 20;
/*     */     }
/* 102 */     return 0;
/*     */   }
/*     */   
/*     */   protected float getGravity(Item item) {
/* 106 */     if (item instanceof net.minecraft.item.ItemBow || item instanceof net.minecraft.item.ItemSplashPotion || item instanceof net.minecraft.item.ItemLingeringPotion || item instanceof net.minecraft.item.ItemExpBottle) {
/* 107 */       return 0.05F;
/*     */     }
/* 109 */     return 0.03F;
/*     */   }
/*     */   
/*     */   protected List<Entity> getEntitiesWithinAABB(AxisAlignedBB bb) {
/* 113 */     ArrayList<Entity> list = new ArrayList<>();
/* 114 */     int chunkMinX = MathHelper.func_76128_c((bb.field_72340_a - 2.0D) / 16.0D);
/* 115 */     int chunkMaxX = MathHelper.func_76128_c((bb.field_72336_d + 2.0D) / 16.0D);
/* 116 */     int chunkMinZ = MathHelper.func_76128_c((bb.field_72339_c - 2.0D) / 16.0D);
/* 117 */     int chunkMaxZ = MathHelper.func_76128_c((bb.field_72334_f + 2.0D) / 16.0D);
/* 118 */     for (int x = chunkMinX; x <= chunkMaxX; x++) {
/* 119 */       for (int z = chunkMinZ; z <= chunkMaxZ; z++) {
/* 120 */         if (mc.field_71441_e.func_72863_F().func_186026_b(x, z) != null) {
/* 121 */           mc.field_71441_e.func_72964_e(x, z).func_177414_a((Entity)mc.field_71439_g, bb, list, EntitySelectors.field_180132_d);
/*     */         }
/*     */       } 
/*     */     } 
/* 125 */     return list;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/* 130 */     if (mc.field_71439_g == null || mc.field_71441_e == null || mc.field_71474_y.field_74320_O != 0)
/*     */       return; 
/* 132 */     if ((mc.field_71439_g.func_184614_ca() == ItemStack.field_190927_a || !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemBow)) && (mc.field_71439_g.func_184614_ca() == ItemStack.field_190927_a || !isThrowable(mc.field_71439_g.func_184614_ca().func_77973_b())) && (mc.field_71439_g.func_184592_cb() == ItemStack.field_190927_a || !isThrowable(mc.field_71439_g.func_184592_cb().func_77973_b())))
/*     */       return; 
/* 134 */     double renderPosX = getRenderPosX();
/* 135 */     double renderPosY = getRenderPosY();
/* 136 */     double renderPosZ = getRenderPosZ();
/* 137 */     Item item = null;
/* 138 */     if (mc.field_71439_g.func_184614_ca() != ItemStack.field_190927_a && (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemBow || isThrowable(mc.field_71439_g.func_184614_ca().func_77973_b()))) {
/* 139 */       item = mc.field_71439_g.func_184614_ca().func_77973_b();
/* 140 */     } else if (mc.field_71439_g.func_184592_cb() != ItemStack.field_190927_a && isThrowable(mc.field_71439_g.func_184592_cb().func_77973_b())) {
/* 141 */       item = mc.field_71439_g.func_184592_cb().func_77973_b();
/*     */     } 
/* 143 */     if (item == null)
/* 144 */       return;  startRender();
/* 145 */     double posX = renderPosX - (MathHelper.func_76134_b(mc.field_71439_g.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
/* 146 */     double posY = renderPosY + mc.field_71439_g.func_70047_e() - 0.1000000014901161D;
/* 147 */     double posZ = renderPosZ - (MathHelper.func_76126_a(mc.field_71439_g.field_70177_z / 180.0F * 3.1415927F) * 0.16F);
/* 148 */     float maxDist = getDistance(item);
/* 149 */     double motionX = (-MathHelper.func_76126_a(mc.field_71439_g.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(mc.field_71439_g.field_70125_A / 180.0F * 3.1415927F) * maxDist);
/* 150 */     double motionY = (-MathHelper.func_76126_a((mc.field_71439_g.field_70125_A - getThrowPitch(item)) / 180.0F * 3.141593F) * maxDist);
/* 151 */     double motionZ = (MathHelper.func_76134_b(mc.field_71439_g.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(mc.field_71439_g.field_70125_A / 180.0F * 3.1415927F) * maxDist);
/* 152 */     int var6 = 72000 - mc.field_71439_g.func_184605_cv();
/* 153 */     float power = var6 / 20.0F;
/* 154 */     power = (power * power + power * 2.0F) / 3.0F;
/* 155 */     if (power > 1.0F) {
/* 156 */       power = 1.0F;
/*     */     }
/* 158 */     float distance = MathHelper.func_76133_a(motionX * motionX + motionY * motionY + motionZ * motionZ);
/* 159 */     motionX /= distance;
/* 160 */     motionY /= distance;
/* 161 */     motionZ /= distance;
/*     */     
/* 163 */     float pow = ((item instanceof net.minecraft.item.ItemBow) ? (power * 2.0F) : 1.0F) * getThrowVelocity(item);
/* 164 */     motionX *= pow;
/* 165 */     motionY *= pow;
/* 166 */     motionZ *= pow;
/* 167 */     if (!mc.field_71439_g.field_70122_E) {
/* 168 */       motionY += mc.field_71439_g.field_70181_x;
/*     */     }
/* 170 */     GlStateManager.func_179131_c(((ColorSetting)this.ncolor.getValue()).getRed() / 255.0F, ((ColorSetting)this.ncolor.getValue()).getGreen() / 255.0F, ((ColorSetting)this.ncolor.getValue()).getBlue() / 255.0F, ((ColorSetting)this.ncolor.getValue()).getAlpha() / 255.0F);
/* 171 */     GL11.glEnable(2848);
/* 172 */     float size = (float)((item instanceof net.minecraft.item.ItemBow) ? 0.3D : 0.25D);
/* 173 */     boolean hasLanded = false;
/* 174 */     Entity landingOnEntity = null;
/* 175 */     RayTraceResult landingPosition = null;
/* 176 */     GL11.glBegin(3);
/* 177 */     while (!hasLanded && posY > 0.0D) {
/* 178 */       Vec3d present = new Vec3d(posX, posY, posZ);
/* 179 */       Vec3d future = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
/* 180 */       RayTraceResult possibleLandingStrip = mc.field_71441_e.func_147447_a(present, future, false, true, false);
/* 181 */       if (possibleLandingStrip != null && possibleLandingStrip.field_72313_a != RayTraceResult.Type.MISS) {
/* 182 */         landingPosition = possibleLandingStrip;
/* 183 */         hasLanded = true;
/*     */       } 
/* 185 */       AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
/* 186 */       List<Entity> entities = getEntitiesWithinAABB(arrowBox.func_72317_d(motionX, motionY, motionZ).func_72321_a(1.0D, 1.0D, 1.0D));
/* 187 */       for (Entity entity : entities) {
/* 188 */         Entity boundingBox = entity;
/* 189 */         if (boundingBox.func_70067_L() && boundingBox != mc.field_71439_g) {
/* 190 */           float var7 = 0.3F;
/* 191 */           AxisAlignedBB var8 = boundingBox.func_174813_aQ().func_72321_a(var7, var7, var7);
/* 192 */           RayTraceResult possibleEntityLanding = var8.func_72327_a(present, future);
/* 193 */           if (possibleEntityLanding == null) {
/*     */             continue;
/*     */           }
/* 196 */           hasLanded = true;
/* 197 */           landingOnEntity = boundingBox;
/* 198 */           landingPosition = possibleEntityLanding;
/*     */         } 
/*     */       } 
/* 201 */       if (landingOnEntity != null) {
/* 202 */         GlStateManager.func_179131_c(1.0F, 0.0F, 0.0F, 1.0F);
/*     */       }
/* 204 */       posX += motionX;
/* 205 */       posY += motionY;
/* 206 */       posZ += motionZ;
/* 207 */       float motionAdjustment = 0.99F;
/* 208 */       motionX *= 0.9900000095367432D;
/* 209 */       motionY *= 0.9900000095367432D;
/* 210 */       motionZ *= 0.9900000095367432D;
/* 211 */       motionY -= getGravity(item);
/* 212 */       drawLine3D(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
/*     */     } 
/* 214 */     GL11.glEnd();
/* 215 */     if (((Boolean)this.landed.getValue()).booleanValue() && landingPosition != null && landingPosition.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 216 */       GlStateManager.func_179137_b(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
/* 217 */       int side = landingPosition.field_178784_b.func_176745_a();
/* 218 */       if (side == 2) {
/* 219 */         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
/* 220 */       } else if (side == 3) {
/* 221 */         GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
/* 222 */       } else if (side == 4) {
/* 223 */         GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
/* 224 */       } else if (side == 5) {
/* 225 */         GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
/*     */       } 
/* 227 */       if (landingOnEntity != null) {
/* 228 */         circle();
/*     */       }
/* 230 */       circle();
/*     */     } 
/* 232 */     endRender();
/*     */   }
/*     */   
/*     */   public void circle() {
/* 236 */     GlStateManager.func_179094_E();
/* 237 */     GlStateManager.func_179147_l();
/* 238 */     GlStateManager.func_179090_x();
/* 239 */     GlStateManager.func_179140_f();
/* 240 */     GlStateManager.func_179097_i();
/* 241 */     GL11.glEnable(3008);
/* 242 */     GL11.glBlendFunc(770, 771);
/* 243 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 244 */     IRenderManager renderManager = (IRenderManager)mc.func_175598_ae();
/* 245 */     float[] hsb = Color.RGBtoHSB(((ColorSetting)this.circleColor.getValue()).getRed(), ((ColorSetting)this.circleColor.getValue()).getGreen(), ((ColorSetting)this.circleColor.getValue()).getBlue(), null);
/* 246 */     float initialHue = (float)(System.currentTimeMillis() % 7200L) / 7200.0F;
/* 247 */     float hue = initialHue;
/* 248 */     int rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
/* 249 */     ArrayList<Vec3d> vecs = new ArrayList<>();
/* 250 */     double x = 0.0D;
/* 251 */     double y = 0.0D;
/* 252 */     double z = 0.0D;
/* 253 */     GL11.glShadeModel(7425);
/* 254 */     GlStateManager.func_179129_p();
/* 255 */     GL11.glLineWidth(((Float)this.circleWidth.getValue()).floatValue());
/* 256 */     GL11.glBegin(1);
/* 257 */     for (int i = 0; i <= 360; i++) {
/* 258 */       Vec3d vec = new Vec3d(x + Math.sin(i * Math.PI / 180.0D) * 0.5D, y + 0.01D, z + Math.cos(i * Math.PI / 180.0D) * 0.5D);
/* 259 */       vecs.add(vec);
/*     */     }  int j;
/* 261 */     for (j = 0; j < vecs.size() - 1; j++) {
/* 262 */       int red = rgb >> 16 & 0xFF;
/* 263 */       int green = rgb >> 8 & 0xFF;
/* 264 */       int blue = rgb & 0xFF;
/* 265 */       if (((ColorSetting)this.circleColor.getValue()).isCycle()) {
/* 266 */         GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F);
/*     */       } else {
/* 268 */         GL11.glColor4f(((ColorSetting)this.circleColor.getValue()).getRed() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getGreen() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getBlue() / 255.0F, 1.0F);
/*     */       } 
/* 270 */       GL11.glVertex3d(((Vec3d)vecs.get(j)).field_72450_a, ((Vec3d)vecs.get(j)).field_72448_b, ((Vec3d)vecs.get(j)).field_72449_c);
/* 271 */       GL11.glVertex3d(((Vec3d)vecs.get(j + 1)).field_72450_a, ((Vec3d)vecs.get(j + 1)).field_72448_b, ((Vec3d)vecs.get(j + 1)).field_72449_c);
/* 272 */       hue += 0.0027777778F;
/* 273 */       rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
/*     */     } 
/* 275 */     GL11.glEnd();
/*     */     
/* 277 */     hue = initialHue;
/* 278 */     GL11.glBegin(9);
/* 279 */     for (j = 0; j < vecs.size() - 1; j++) {
/* 280 */       int red = rgb >> 16 & 0xFF;
/* 281 */       int green = rgb >> 8 & 0xFF;
/* 282 */       int blue = rgb & 0xFF;
/* 283 */       if (((ColorSetting)this.circleColor.getValue()).isCycle()) {
/* 284 */         GL11.glColor4f(red / 255.0F, green / 255.0F, blue / 255.0F, ((ColorSetting)this.circleColor.getValue()).getAlpha() / 255.0F);
/*     */       } else {
/* 286 */         GL11.glColor4f(((ColorSetting)this.circleColor.getValue()).getRed() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getGreen() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getBlue() / 255.0F, ((ColorSetting)this.circleColor.getValue()).getAlpha() / 255.0F);
/*     */       } 
/* 288 */       GL11.glVertex3d(((Vec3d)vecs.get(j)).field_72450_a, ((Vec3d)vecs.get(j)).field_72448_b, ((Vec3d)vecs.get(j)).field_72449_c);
/* 289 */       GL11.glVertex3d(((Vec3d)vecs.get(j + 1)).field_72450_a, ((Vec3d)vecs.get(j + 1)).field_72448_b, ((Vec3d)vecs.get(j + 1)).field_72449_c);
/* 290 */       hue += 0.0027777778F;
/* 291 */       rgb = Color.getHSBColor(hue, hsb[1], hsb[2]).getRGB();
/*     */     } 
/* 293 */     GL11.glEnd();
/*     */     
/* 295 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 296 */     GL11.glDisable(3008);
/* 297 */     GlStateManager.func_179089_o();
/* 298 */     GL11.glShadeModel(7424);
/* 299 */     GlStateManager.func_179126_j();
/* 300 */     GlStateManager.func_179098_w();
/* 301 */     GlStateManager.func_179145_e();
/* 302 */     GlStateManager.func_179084_k();
/* 303 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public void drawLine3D(double var1, double var2, double var3) {
/* 307 */     GL11.glVertex3d(var1, var2, var3);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Trajectories.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */