/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventEntityMove;
/*     */ import com.mrzak34.thunderhack.events.PreRenderEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.AstolfoAnimation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class PlayerTrails
/*     */   extends Module
/*     */ {
/*  24 */   public static AstolfoAnimation astolfo = new AstolfoAnimation();
/*  25 */   private final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  26 */   private final Setting<Boolean> shfix = register(new Setting("ShaderFix", Boolean.valueOf(false)));
/*  27 */   public Setting<Float> down = register(new Setting("Down", Float.valueOf(0.5F), Float.valueOf(0.0F), Float.valueOf(2.0F)));
/*  28 */   public Setting<Float> width = register(new Setting("Height", Float.valueOf(1.3F), Float.valueOf(0.1F), Float.valueOf(2.0F)));
/*  29 */   public Setting<modeEn> mode = register(new Setting("ColorMode", modeEn.Ukraine));
/*     */   
/*     */   public PlayerTrails() {
/*  32 */     super("PlayerTrails", "трейлы позади-игроков", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  37 */     if (((Boolean)this.shfix.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/*  41 */     for (EntityPlayer entity : mc.field_71441_e.field_73010_i) {
/*  42 */       if (entity instanceof net.minecraft.client.entity.EntityPlayerSP && mc.field_71474_y.field_74320_O == 0)
/*     */         continue; 
/*  44 */       float alpha = ((ColorSetting)this.color.getValue()).getAlpha() / 255.0F;
/*  45 */       ((IEntityRenderer)mc.field_71460_t).invokeSetupCameraTransform(mc.func_184121_ak(), 2);
/*  46 */       if (((IEntity)entity).getTrails().size() > 0) {
/*  47 */         GL11.glPushMatrix();
/*  48 */         GL11.glEnable(3042);
/*  49 */         GL11.glDisable(3008);
/*  50 */         GL11.glDisable(2884);
/*  51 */         GL11.glDisable(3553);
/*  52 */         GL11.glBlendFunc(770, 771);
/*  53 */         GL11.glShadeModel(7425);
/*  54 */         GL11.glEnable(2848);
/*  55 */         GL11.glBegin(8);
/*     */         
/*  57 */         if (this.mode.getValue() == modeEn.Default) {
/*  58 */           int i; for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/*  59 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/*  60 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/*  61 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/*  62 */             GL11.glColor4d(c.field_72450_a, c.field_72448_b, c.field_72449_c, alpha * ctx.animation(mc.func_184121_ak()));
/*  63 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*  64 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/*  68 */           GL11.glEnd();
/*  69 */           GL11.glLineWidth(1.0F);
/*     */ 
/*     */           
/*  72 */           GL11.glBegin(3);
/*  73 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/*  74 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/*  75 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/*  76 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/*  77 */             GL11.glColor4d(c.field_72450_a, c.field_72448_b, c.field_72449_c, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/*  78 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/*  82 */           GL11.glEnd();
/*  83 */           GL11.glBegin(3);
/*     */ 
/*     */           
/*  86 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/*  87 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/*  88 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/*  89 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/*  90 */             GL11.glColor4d(c.field_72450_a, c.field_72448_b, c.field_72449_c, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/*  91 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*  93 */         } else if (this.mode.getValue() == modeEn.Ukraine) {
/*  94 */           int i; for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/*  95 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/*  96 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/*  97 */             GL11.glColor4d(0.9607843160629272D, 0.8901960849761963D, 0.25882354378700256D, alpha * ctx.animation(mc.func_184121_ak()));
/*  98 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*  99 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 2.0F), pos.field_72449_c);
/*     */           } 
/* 101 */           GL11.glEnd();
/* 102 */           GL11.glBegin(8);
/*     */           
/* 104 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 105 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 106 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 107 */             GL11.glColor4d(0.25882354378700256D, 0.4000000059604645D, 0.9607843160629272D, alpha * ctx.animation(mc.func_184121_ak()));
/* 108 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 2.0F), pos.field_72449_c);
/* 109 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + ((Float)this.width.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */           
/* 112 */           GL11.glEnd();
/* 113 */           GL11.glLineWidth(1.0F);
/*     */ 
/*     */           
/* 116 */           GL11.glBegin(3);
/* 117 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 118 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/* 119 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 120 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 121 */             GL11.glColor4d(0.25882354378700256D, 0.4000000059604645D, 0.9607843160629272D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 122 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/* 126 */           GL11.glEnd();
/* 127 */           GL11.glBegin(3);
/*     */ 
/*     */           
/* 130 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 131 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/* 132 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 133 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 134 */             GL11.glColor4d(0.9607843160629272D, 0.8901960849761963D, 0.25882354378700256D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 135 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/* 137 */         } else if (this.mode.getValue() == modeEn.RUSSIA) {
/* 138 */           int i; for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 139 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 140 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 141 */             GL11.glColor4d(1.0D, 0.0D, 0.0D, alpha * ctx.animation(mc.func_184121_ak()));
/* 142 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/* 143 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 3.0F), pos.field_72449_c);
/*     */           } 
/* 145 */           GL11.glEnd();
/* 146 */           GL11.glBegin(8);
/*     */           
/* 148 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 149 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 150 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 151 */             GL11.glColor4d(0.0D, 0.0D, 1.0D, alpha * ctx.animation(mc.func_184121_ak()));
/* 152 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 3.0F), pos.field_72449_c);
/* 153 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() * 0.6666667F), pos.field_72449_c);
/*     */           } 
/*     */           
/* 156 */           GL11.glEnd();
/* 157 */           GL11.glBegin(8);
/* 158 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 159 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 160 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 161 */             GL11.glColor4d(1.0D, 1.0D, 1.0D, alpha * ctx.animation(mc.func_184121_ak()));
/* 162 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() * 0.6666667F), pos.field_72449_c);
/* 163 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + ((Float)this.width.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */           
/* 166 */           GL11.glEnd();
/* 167 */           GL11.glLineWidth(1.0F);
/*     */ 
/*     */           
/* 170 */           GL11.glBegin(3);
/* 171 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 172 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 173 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 174 */             GL11.glColor4d(1.0D, 1.0D, 1.0D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 175 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/* 179 */           GL11.glEnd();
/* 180 */           GL11.glBegin(3);
/*     */ 
/*     */           
/* 183 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 184 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 185 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 186 */             GL11.glColor4d(1.0D, 0.0D, 0.0D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 187 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 192 */         GL11.glEnd();
/* 193 */         GL11.glDisable(2848);
/* 194 */         GL11.glEnable(3553);
/* 195 */         GL11.glDisable(3042);
/* 196 */         GL11.glEnable(3008);
/* 197 */         GL11.glShadeModel(7424);
/* 198 */         GL11.glEnable(2884);
/* 199 */         GL11.glPopMatrix();
/*     */       } 
/* 201 */       GlStateManager.func_179117_G();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRenderPost(PreRenderEvent event) {
/* 207 */     if (!((Boolean)this.shfix.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 211 */     for (EntityPlayer entity : mc.field_71441_e.field_73010_i) {
/* 212 */       if (entity instanceof net.minecraft.client.entity.EntityPlayerSP && mc.field_71474_y.field_74320_O == 0)
/* 213 */         continue;  float alpha = ((ColorSetting)this.color.getValue()).getAlpha() / 255.0F;
/* 214 */       ((IEntityRenderer)mc.field_71460_t).invokeSetupCameraTransform(mc.func_184121_ak(), 2);
/* 215 */       if (((IEntity)entity).getTrails().size() > 0) {
/* 216 */         GL11.glPushMatrix();
/* 217 */         GL11.glEnable(3042);
/* 218 */         GL11.glDisable(3008);
/* 219 */         GL11.glDisable(2884);
/* 220 */         GL11.glDisable(3553);
/* 221 */         GL11.glBlendFunc(770, 771);
/* 222 */         GL11.glShadeModel(7425);
/* 223 */         GL11.glEnable(2848);
/* 224 */         GL11.glBegin(8);
/*     */         
/* 226 */         if (this.mode.getValue() == modeEn.Default) {
/* 227 */           int i; for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 228 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/* 229 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 230 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 231 */             GL11.glColor4d(c.field_72450_a, c.field_72448_b, c.field_72449_c, alpha * ctx.animation(mc.func_184121_ak()));
/* 232 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/* 233 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/* 237 */           GL11.glEnd();
/* 238 */           GL11.glLineWidth(1.0F);
/*     */ 
/*     */           
/* 241 */           GL11.glBegin(3);
/* 242 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 243 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/* 244 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 245 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 246 */             GL11.glColor4d(c.field_72450_a, c.field_72448_b, c.field_72449_c, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 247 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/* 251 */           GL11.glEnd();
/* 252 */           GL11.glBegin(3);
/*     */ 
/*     */           
/* 255 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 256 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/* 257 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 258 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 259 */             GL11.glColor4d(c.field_72450_a, c.field_72448_b, c.field_72449_c, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 260 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/* 262 */         } else if (this.mode.getValue() == modeEn.Ukraine) {
/* 263 */           int i; for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 264 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 265 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 266 */             GL11.glColor4d(0.9607843160629272D, 0.8901960849761963D, 0.25882354378700256D, alpha * ctx.animation(mc.func_184121_ak()));
/* 267 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/* 268 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 2.0F), pos.field_72449_c);
/*     */           } 
/* 270 */           GL11.glEnd();
/* 271 */           GL11.glBegin(8);
/*     */           
/* 273 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 274 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 275 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 276 */             GL11.glColor4d(0.25882354378700256D, 0.4000000059604645D, 0.9607843160629272D, alpha * ctx.animation(mc.func_184121_ak()));
/* 277 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 2.0F), pos.field_72449_c);
/* 278 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + ((Float)this.width.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */           
/* 281 */           GL11.glEnd();
/* 282 */           GL11.glLineWidth(1.0F);
/*     */ 
/*     */           
/* 285 */           GL11.glBegin(3);
/* 286 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 287 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/* 288 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 289 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 290 */             GL11.glColor4d(0.25882354378700256D, 0.4000000059604645D, 0.9607843160629272D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 291 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/* 295 */           GL11.glEnd();
/* 296 */           GL11.glBegin(3);
/*     */ 
/*     */           
/* 299 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 300 */             Vec3d c = ((Trail)((IEntity)entity).getTrails().get(i)).color();
/* 301 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 302 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 303 */             GL11.glColor4d(0.9607843160629272D, 0.8901960849761963D, 0.25882354378700256D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 304 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/* 306 */         } else if (this.mode.getValue() == modeEn.RUSSIA) {
/* 307 */           int i; for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 308 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 309 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 310 */             GL11.glColor4d(1.0D, 0.0D, 0.0D, alpha * ctx.animation(mc.func_184121_ak()));
/* 311 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/* 312 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 3.0F), pos.field_72449_c);
/*     */           } 
/* 314 */           GL11.glEnd();
/* 315 */           GL11.glBegin(8);
/*     */           
/* 317 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 318 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 319 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 320 */             GL11.glColor4d(0.0D, 0.0D, 1.0D, alpha * ctx.animation(mc.func_184121_ak()));
/* 321 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() / 3.0F), pos.field_72449_c);
/* 322 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() * 0.6666667F), pos.field_72449_c);
/*     */           } 
/*     */           
/* 325 */           GL11.glEnd();
/* 326 */           GL11.glBegin(8);
/* 327 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 328 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 329 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 330 */             GL11.glColor4d(1.0D, 1.0D, 1.0D, alpha * ctx.animation(mc.func_184121_ak()));
/* 331 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + (((Float)this.width.getValue()).floatValue() * 0.6666667F), pos.field_72449_c);
/* 332 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue() + ((Float)this.width.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */           
/* 335 */           GL11.glEnd();
/* 336 */           GL11.glLineWidth(1.0F);
/*     */ 
/*     */           
/* 339 */           GL11.glBegin(3);
/* 340 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 341 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 342 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 343 */             GL11.glColor4d(1.0D, 1.0D, 1.0D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 344 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.width.getValue()).floatValue() + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */ 
/*     */           
/* 348 */           GL11.glEnd();
/* 349 */           GL11.glBegin(3);
/*     */ 
/*     */           
/* 352 */           for (i = 0; i < ((IEntity)entity).getTrails().size(); i++) {
/* 353 */             Trail ctx = ((IEntity)entity).getTrails().get(i);
/* 354 */             Vec3d pos = ctx.interpolate(mc.func_184121_ak());
/* 355 */             GL11.glColor4d(1.0D, 0.0D, 0.0D, (alpha + 0.15F) * ctx.animation(mc.func_184121_ak()));
/* 356 */             GL11.glVertex3d(pos.field_72450_a, pos.field_72448_b + ((Float)this.down.getValue()).floatValue(), pos.field_72449_c);
/*     */           } 
/*     */         } 
/* 359 */         GL11.glEnd();
/* 360 */         GL11.glDisable(2848);
/* 361 */         GL11.glEnable(3553);
/* 362 */         GL11.glDisable(3042);
/* 363 */         GL11.glEnable(3008);
/* 364 */         GL11.glShadeModel(7424);
/* 365 */         GL11.glEnable(2884);
/* 366 */         GL11.glPopMatrix();
/*     */       } 
/* 368 */       GlStateManager.func_179117_G();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityMove(EventEntityMove e) {
/* 374 */     if (e.ctx() instanceof EntityPlayer) {
/* 375 */       float red = ((ColorSetting)this.color.getValue()).getRed() / 255.0F, green = ((ColorSetting)this.color.getValue()).getGreen() / 255.0F, blue = ((ColorSetting)this.color.getValue()).getBlue() / 255.0F;
/* 376 */       EntityPlayer a = (EntityPlayer)e.ctx();
/* 377 */       ((IEntity)a).getTrails().add(new Trail(e.from(), e.ctx().func_174791_d(), new Vec3d(red, green, blue)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 383 */     astolfo.update();
/* 384 */     for (EntityPlayer player : mc.field_71441_e.field_73010_i)
/* 385 */       ((IEntity)player).getTrails().removeIf(Trail::update); 
/*     */   }
/*     */   
/*     */   public enum modeEn
/*     */   {
/* 390 */     Default,
/* 391 */     Ukraine,
/* 392 */     RUSSIA; }
/*     */   
/*     */   public static class Trail {
/*     */     private final Vec3d from;
/*     */     private final Vec3d to;
/*     */     private final Vec3d color;
/*     */     private int ticks;
/*     */     private int prevTicks;
/*     */     
/*     */     public Trail(Vec3d from, Vec3d to, Vec3d color) {
/* 402 */       this.from = from;
/* 403 */       this.to = to;
/* 404 */       this.ticks = 10;
/* 405 */       this.color = color;
/*     */     }
/*     */     
/*     */     public Vec3d interpolate(float pt) {
/* 409 */       double x = this.from.field_72450_a + (this.to.field_72450_a - this.from.field_72450_a) * pt - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX();
/* 410 */       double y = this.from.field_72448_b + (this.to.field_72448_b - this.from.field_72448_b) * pt - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY();
/* 411 */       double z = this.from.field_72449_c + (this.to.field_72449_c - this.from.field_72449_c) * pt - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ();
/* 412 */       return new Vec3d(x, y, z);
/*     */     }
/*     */     
/*     */     public double animation(float pt) {
/* 416 */       return (this.prevTicks + (this.ticks - this.prevTicks) * pt) / 10.0D;
/*     */     }
/*     */     
/*     */     public boolean update() {
/* 420 */       this.prevTicks = this.ticks; return 
/* 421 */         (this.ticks-- <= 0);
/*     */     }
/*     */     
/*     */     public Vec3d color() {
/* 425 */       return this.color;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\PlayerTrails.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */