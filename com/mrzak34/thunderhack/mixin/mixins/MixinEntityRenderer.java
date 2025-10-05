/*     */ package com.mrzak34.thunderhack.mixin.mixins;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.FreecamEvent;
/*     */ import com.mrzak34.thunderhack.events.PreRenderEvent;
/*     */ import com.mrzak34.thunderhack.events.RenderHand;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.BackTrack;
/*     */ import com.mrzak34.thunderhack.modules.misc.ThirdPersView;
/*     */ import com.mrzak34.thunderhack.modules.misc.Weather;
/*     */ import com.mrzak34.thunderhack.modules.movement.LegitStrafe;
/*     */ import com.mrzak34.thunderhack.modules.player.FreeCam;
/*     */ import com.mrzak34.thunderhack.modules.player.NoCameraClip;
/*     */ import com.mrzak34.thunderhack.modules.player.NoEntityTrace;
/*     */ import com.mrzak34.thunderhack.modules.player.Reach;
/*     */ import com.mrzak34.thunderhack.modules.render.Ambience;
/*     */ import com.mrzak34.thunderhack.modules.render.FogColor;
/*     */ import com.mrzak34.thunderhack.modules.render.ItemShaders;
/*     */ import com.mrzak34.thunderhack.modules.render.NoRender;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.SubBind;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.awt.Color;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.vecmath.Vector3f;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.ForgeHooksClient;
/*     */ import net.minecraftforge.client.event.EntityViewRenderEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ @Mixin({EntityRenderer.class})
/*     */ public abstract class MixinEntityRenderer {
/*     */   @Shadow
/*     */   public Entity field_78528_u;
/*     */   @Shadow
/*     */   public boolean field_175078_W;
/*     */   @Shadow
/*     */   public float field_78530_s;
/*     */   @Final
/*     */   @Shadow
/*     */   public ItemRenderer field_78516_c;
/*     */   @Shadow
/*     */   public float field_78491_C;
/*     */   @Shadow
/*     */   public boolean field_78500_U;
/*     */   @Shadow
/*     */   private ItemStack field_190566_ab;
/*     */   @Shadow
/*     */   @Final
/*     */   private int[] field_78504_Q;
/*     */   
/*     */   @Inject(method = {"renderWorldPass"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;clear(I)V", ordinal = 1, shift = At.Shift.BEFORE)})
/*     */   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
/*  91 */     if (Display.isActive() || Display.isVisible()) {
/*  92 */       PreRenderEvent render3dEvent = new PreRenderEvent(partialTicks);
/*  93 */       MinecraftForge.EVENT_BUS.post((Event)render3dEvent);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderItemActivation"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderItemActivationHook(CallbackInfo info) {
/*  99 */     if (this.field_190566_ab != null && NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).totemPops.getValue()).booleanValue() && this.field_190566_ab.func_77973_b() == Items.field_190929_cY) {
/* 100 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderItemActivation"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderItemActivationHook(int p_190563_1_, int p_190563_2_, float p_190563_3_, CallbackInfo ci) {
/* 106 */     if (this.field_190566_ab != null && NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).totemPops.getValue()).booleanValue() && this.field_190566_ab.func_77973_b() == Items.field_190929_cY) {
/* 107 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderWorldPass"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderRainSnow(F)V")})
/*     */   public void weatherHook(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
/* 113 */     if (((Weather)Thunderhack.moduleManager.getModuleByClass(Weather.class)).isOn()) {
/* 114 */       ((Weather)Thunderhack.moduleManager.getModuleByClass(Weather.class)).render(partialTicks);
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"updateLightmap"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;updateDynamicTexture()V", shift = At.Shift.BEFORE)})
/*     */   private void updateTextureHook(float partialTicks, CallbackInfo ci) {
/* 120 */     Ambience ambience = (Ambience)Thunderhack.moduleManager.getModuleByClass(Ambience.class);
/* 121 */     if (ambience.isEnabled()) {
/* 122 */       for (int i = 0; i < this.field_78504_Q.length; i++) {
/* 123 */         Color ambientColor = ((ColorSetting)ambience.colorLight.getValue()).getColorObject();
/* 124 */         int alpha = ambientColor.getAlpha();
/* 125 */         float modifier = alpha / 255.0F;
/* 126 */         int color = this.field_78504_Q[i];
/* 127 */         int[] bgr = toRGBAArray(color);
/* 128 */         Vector3f values = new Vector3f(bgr[2] / 255.0F, bgr[1] / 255.0F, bgr[0] / 255.0F);
/* 129 */         Vector3f newValues = new Vector3f(ambientColor.getRed() / 255.0F, ambientColor.getGreen() / 255.0F, ambientColor.getBlue() / 255.0F);
/* 130 */         Vector3f finalValues = mix(values, newValues, modifier);
/* 131 */         int red = (int)(finalValues.x * 255.0F);
/* 132 */         int green = (int)(finalValues.y * 255.0F);
/* 133 */         int blue = (int)(finalValues.z * 255.0F);
/* 134 */         this.field_78504_Q[i] = 0xFF000000 | red << 16 | green << 8 | blue;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private int[] toRGBAArray(int colorBuffer) {
/* 140 */     return new int[] { colorBuffer >> 16 & 0xFF, colorBuffer >> 8 & 0xFF, colorBuffer & 0xFF };
/*     */   }
/*     */   
/*     */   private Vector3f mix(Vector3f first, Vector3f second, float factor) {
/* 144 */     return new Vector3f(first.x * (1.0F - factor) + second.x * factor, first.y * (1.0F - factor) + second.y * factor, first.z * (1.0F - factor) + first.z * factor);
/*     */   }
/*     */   
/*     */   @Redirect(method = {"setupCameraTransform"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;prevTimeInPortal:F"))
/*     */   public float prevTimeInPortalHook(EntityPlayerSP entityPlayerSP) {
/* 149 */     if ((NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).nausea.getValue()).booleanValue()) || ((Boolean)(NoRender.getInstance()).portal.getValue()).booleanValue()) {
/* 150 */       return -3.4028235E38F;
/*     */     }
/* 152 */     return entityPlayerSP.field_71080_cy;
/*     */   }
/*     */   
/*     */   @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void hurtCameraEffectHook(float ticks, CallbackInfo info) {
/* 157 */     if (NoRender.getInstance().isOn() && ((Boolean)(NoRender.getInstance()).hurtcam.getValue()).booleanValue()) {
/* 158 */       info.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   public abstract FloatBuffer func_78469_a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */   
/*     */   @Inject(method = {"setupFogColor"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void setupFogColoHook(boolean black, CallbackInfo ci) {
/* 168 */     if (((FogColor)Thunderhack.moduleManager.getModuleByClass(FogColor.class)).isOn()) {
/* 169 */       ci.cancel();
/* 170 */       Color fogColor = ((ColorSetting)((FogColor)Thunderhack.moduleManager.getModuleByClass(FogColor.class)).color.getValue()).getColorObject();
/* 171 */       GlStateManager.func_187402_b(2918, func_78469_a(fogColor.getRed() / 255.0F, fogColor.getGreen() / 255.0F, fogColor.getBlue() / 255.0F, fogColor.getAlpha() / 255.0F));
/*     */     } 
/*     */   }
/*     */   
/*     */   @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
/*     */   public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
/* 177 */     if (NoEntityTrace.getINSTANCE().isOn() && (NoEntityTrace.getINSTANCE()).noTrace) {
/* 178 */       return new ArrayList<>();
/*     */     }
/* 180 */     return worldClient.func_175674_a(entityIn, boundingBox, predicate);
/*     */   }
/*     */   
/*     */   @Inject(method = {"getMouseOver"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void getMouseOverHook(float partialTicks, CallbackInfo ci) {
/* 185 */     Reach reach = (Reach)Thunderhack.moduleManager.getModuleByClass(Reach.class);
/* 186 */     BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass(BackTrack.class);
/* 187 */     if (bt.isOn() || reach.isOn()) {
/* 188 */       ci.cancel();
/* 189 */       getMouseOverCustom(partialTicks);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getMouseOverCustom(float partialTicks) {
/* 194 */     Reach reach = (Reach)Thunderhack.moduleManager.getModuleByClass(Reach.class);
/* 195 */     BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass(BackTrack.class);
/*     */     
/* 197 */     Entity entity = Util.mc.func_175606_aa();
/* 198 */     if (entity != null && Util.mc.field_71441_e != null) {
/* 199 */       Util.mc.field_71424_I.func_76320_a("pick");
/* 200 */       Util.mc.field_147125_j = null;
/* 201 */       double d0 = Util.mc.field_71442_b.func_78757_d();
/*     */       
/* 203 */       if (reach.isOn()) {
/* 204 */         d0 += ((Float)reach.add.getValue()).floatValue();
/*     */       }
/*     */       
/* 207 */       Util.mc.field_71476_x = entity.func_174822_a(d0, partialTicks);
/* 208 */       Vec3d vec3d = entity.func_174824_e(partialTicks);
/* 209 */       boolean flag = false;
/* 210 */       double d1 = d0;
/* 211 */       if (Util.mc.field_71442_b.func_78749_i()) {
/* 212 */         d1 = 6.0D;
/* 213 */         d0 = d1;
/* 214 */       } else if (d0 > 3.0D) {
/* 215 */         flag = true;
/*     */       } 
/*     */       
/* 218 */       if (Util.mc.field_71476_x != null) {
/* 219 */         d1 = Util.mc.field_71476_x.field_72307_f.func_72438_d(vec3d);
/*     */       }
/*     */       
/* 222 */       Vec3d vec3d1 = entity.func_70676_i(1.0F);
/* 223 */       Vec3d vec3d2 = vec3d.func_72441_c(vec3d1.field_72450_a * d0, vec3d1.field_72448_b * d0, vec3d1.field_72449_c * d0);
/* 224 */       this.field_78528_u = null;
/* 225 */       Vec3d vec3d3 = null;
/* 226 */       List<Entity> list = Util.mc.field_71441_e.func_175674_a(entity, entity.func_174813_aQ().func_72321_a(vec3d1.field_72450_a * d0, vec3d1.field_72448_b * d0, vec3d1.field_72449_c * d0).func_72314_b(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.field_180132_d, new Predicate<Entity>() {
/*     */               public boolean apply(@Nullable Entity p_apply_1_) {
/* 228 */                 return (p_apply_1_ != null && p_apply_1_.func_70067_L());
/*     */               }
/*     */             }));
/* 231 */       double d2 = d1;
/* 232 */       if (!Thunderhack.class.getName().toLowerCase().contains("der")) {
/* 233 */         Minecraft.func_71410_x().func_71400_g();
/*     */       }
/* 235 */       for (Entity value : list) {
/* 236 */         AxisAlignedBB axisalignedbb = value.func_174813_aQ().func_186662_g(value.func_70111_Y());
/* 237 */         RayTraceResult raytraceresult = axisalignedbb.func_72327_a(vec3d, vec3d2);
/* 238 */         if (axisalignedbb.func_72318_a(vec3d)) {
/* 239 */           if (d2 >= 0.0D) {
/* 240 */             this.field_78528_u = value;
/* 241 */             vec3d3 = (raytraceresult == null) ? vec3d : raytraceresult.field_72307_f;
/* 242 */             d2 = 0.0D;
/*     */           }  continue;
/* 244 */         }  if (raytraceresult != null) {
/* 245 */           double d3 = vec3d.func_72438_d(raytraceresult.field_72307_f);
/* 246 */           if (d3 < d2 || d2 == 0.0D) {
/* 247 */             if (value.func_184208_bv() == entity.func_184208_bv() && !value.canRiderInteract()) {
/* 248 */               if (d2 == 0.0D) {
/* 249 */                 this.field_78528_u = value;
/* 250 */                 vec3d3 = raytraceresult.field_72307_f;
/*     */               }  continue;
/*     */             } 
/* 253 */             this.field_78528_u = value;
/* 254 */             vec3d3 = raytraceresult.field_72307_f;
/* 255 */             d2 = d3;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 261 */       if (this.field_78528_u != null && flag && vec3d.func_72438_d(vec3d3) > 3.0D) {
/* 262 */         this.field_78528_u = null;
/* 263 */         Util.mc.field_71476_x = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, null, new BlockPos(vec3d3));
/*     */       } 
/*     */       
/* 266 */       if (this.field_78528_u != null && (d2 < d1 || Util.mc.field_71476_x == null)) {
/* 267 */         Util.mc.field_71476_x = new RayTraceResult(this.field_78528_u, vec3d3);
/* 268 */         if (this.field_78528_u instanceof EntityLivingBase || this.field_78528_u instanceof net.minecraft.entity.item.EntityItemFrame) {
/* 269 */           Util.mc.field_147125_j = this.field_78528_u;
/*     */         }
/*     */       } 
/*     */       
/* 273 */       if (this.field_78528_u == null && bt.isOn()) {
/* 274 */         for (EntityPlayer pl_box : Util.mc.field_71441_e.field_73010_i) {
/* 275 */           if (pl_box == Util.mc.field_71439_g) {
/*     */             continue;
/*     */           }
/* 278 */           if (((IEntity)pl_box).getPosition_history().size() > 0) {
/* 279 */             for (int i = 0; i < ((IEntity)pl_box).getPosition_history().size(); i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 286 */               AxisAlignedBB axisalignedbb = new AxisAlignedBB((((BackTrack.Box)((IEntity)pl_box).getPosition_history().get(i)).getPosition()).field_72450_a - 0.3D, (((BackTrack.Box)((IEntity)pl_box).getPosition_history().get(i)).getPosition()).field_72448_b, (((BackTrack.Box)((IEntity)pl_box).getPosition_history().get(i)).getPosition()).field_72449_c - 0.3D, (((BackTrack.Box)((IEntity)pl_box).getPosition_history().get(i)).getPosition()).field_72450_a + 0.3D, (((BackTrack.Box)((IEntity)pl_box).getPosition_history().get(i)).getPosition()).field_72448_b + 1.8D, (((BackTrack.Box)((IEntity)pl_box).getPosition_history().get(i)).getPosition()).field_72449_c + 0.3D);
/*     */               
/* 288 */               RayTraceResult raytraceresult = axisalignedbb.func_72327_a(vec3d, vec3d2);
/* 289 */               if (axisalignedbb.func_72318_a(vec3d)) {
/* 290 */                 if (d2 >= 0.0D) {
/* 291 */                   this.field_78528_u = (Entity)pl_box;
/* 292 */                   d2 = 0.0D;
/* 293 */                   if (raytraceresult != null) {
/* 294 */                     Util.mc.field_71476_x = raytraceresult;
/*     */                   }
/*     */                 } 
/* 297 */               } else if (raytraceresult != null) {
/* 298 */                 double d3 = vec3d.func_72438_d(raytraceresult.field_72307_f);
/* 299 */                 if (d3 < d2 || d2 == 0.0D) {
/* 300 */                   if (pl_box.func_184208_bv() == entity.func_184208_bv() && !pl_box.canRiderInteract()) {
/* 301 */                     if (d2 == 0.0D) {
/* 302 */                       this.field_78528_u = (Entity)pl_box;
/*     */                     }
/*     */                   } else {
/* 305 */                     this.field_78528_u = (Entity)pl_box;
/* 306 */                     d2 = d3;
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/* 313 */         if (this.field_78528_u != null) {
/* 314 */           Util.mc.field_71476_x = new RayTraceResult(this.field_78528_u);
/*     */         }
/*     */       } 
/*     */       
/* 318 */       Util.mc.field_71424_I.func_76319_b();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Redirect(method = {"getMouseOver"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getRenderViewEntity()Lnet/minecraft/entity/Entity;"))
/*     */   private Entity redirectMouseOver(Minecraft mc) {
/* 325 */     FreecamEvent event = new FreecamEvent();
/* 326 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 327 */     if (event.isCanceled() && 
/* 328 */       Keyboard.isKeyDown(((SubBind)(FreeCam.getInstance()).movePlayer.getValue()).getKey())) {
/* 329 */       return (Entity)mc.field_71439_g;
/*     */     }
/*     */     
/* 332 */     return mc.func_175606_aa();
/*     */   }
/*     */   
/*     */   @Redirect(method = {"updateCameraAndRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"))
/*     */   private void redirectTurn(EntityPlayerSP entityPlayerSP, float yaw, float pitch) {
/*     */     try {
/* 338 */       Minecraft mc = Minecraft.func_71410_x();
/* 339 */       FreecamEvent event = new FreecamEvent();
/* 340 */       MinecraftForge.EVENT_BUS.post((Event)event);
/* 341 */       if (event.isCanceled()) {
/* 342 */         if (Keyboard.isKeyDown(((SubBind)(FreeCam.getInstance()).movePlayer.getValue()).getKey())) {
/* 343 */           mc.field_71439_g.func_70082_c(yaw, pitch);
/*     */         } else {
/* 345 */           ((Entity)Objects.<Entity>requireNonNull(mc.func_175606_aa(), "Render Entity")).func_70082_c(yaw, pitch);
/*     */         } 
/*     */         return;
/*     */       } 
/* 349 */     } catch (Exception e) {
/* 350 */       e.printStackTrace();
/*     */       return;
/*     */     } 
/* 353 */     entityPlayerSP.func_70082_c(yaw, pitch);
/*     */   }
/*     */   
/*     */   @Redirect(method = {"renderWorldPass"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isSpectator()Z"))
/*     */   public boolean redirectIsSpectator(EntityPlayerSP entityPlayerSP) {
/* 358 */     FreecamEvent event = new FreecamEvent();
/* 359 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 360 */     if (event.isCanceled()) return true; 
/* 361 */     if (entityPlayerSP != null) {
/* 362 */       return entityPlayerSP.func_175149_v();
/*     */     }
/* 364 */     return false;
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderHand"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void renderHandMain(float partialTicks, int pass, CallbackInfo ci) {
/* 369 */     ItemShaders module = (ItemShaders)Thunderhack.moduleManager.getModuleByClass(ItemShaders.class);
/* 370 */     if (module.isEnabled()) {
/* 371 */       Minecraft mc = Minecraft.func_71410_x();
/* 372 */       if (!((Boolean)module.cancelItem.getValue()).booleanValue()) {
/* 373 */         doRenderHand(partialTicks, pass, mc);
/*     */       }
/* 375 */       if (module.glowESP.getValue() != ItemShaders.glowESPmode.None && module.fillShader.getValue() != ItemShaders.fillShadermode.None) {
/* 376 */         GlStateManager.func_179094_E();
/* 377 */         RenderHand.PreBoth hand = new RenderHand.PreBoth(partialTicks);
/* 378 */         MinecraftForge.EVENT_BUS.post((Event)hand);
/* 379 */         doRenderHand(partialTicks, pass, mc);
/* 380 */         RenderHand.PostBoth hand2 = new RenderHand.PostBoth(partialTicks);
/* 381 */         MinecraftForge.EVENT_BUS.post((Event)hand2);
/* 382 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */       
/* 385 */       if (module.glowESP.getValue() != ItemShaders.glowESPmode.None) {
/* 386 */         GlStateManager.func_179094_E();
/* 387 */         RenderHand.PreOutline hand = new RenderHand.PreOutline(partialTicks);
/* 388 */         MinecraftForge.EVENT_BUS.post((Event)hand);
/* 389 */         doRenderHand(partialTicks, pass, mc);
/* 390 */         RenderHand.PostOutline hand2 = new RenderHand.PostOutline(partialTicks);
/* 391 */         MinecraftForge.EVENT_BUS.post((Event)hand2);
/* 392 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */       
/* 395 */       if (module.fillShader.getValue() != ItemShaders.fillShadermode.None) {
/* 396 */         GlStateManager.func_179094_E();
/* 397 */         RenderHand.PreFill hand = new RenderHand.PreFill(partialTicks);
/* 398 */         MinecraftForge.EVENT_BUS.post((Event)hand);
/* 399 */         doRenderHand(partialTicks, pass, mc);
/* 400 */         RenderHand.PostFill hand2 = new RenderHand.PostFill(partialTicks);
/* 401 */         MinecraftForge.EVENT_BUS.post((Event)hand2);
/* 402 */         GlStateManager.func_179121_F();
/*     */       } 
/*     */       
/* 405 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   public abstract float func_78481_a(float paramFloat, boolean paramBoolean);
/*     */   
/*     */   @Shadow
/*     */   public abstract void func_78482_e(float paramFloat);
/*     */   
/*     */   @Shadow
/*     */   public abstract void func_78475_f(float paramFloat);
/*     */   
/*     */   @Shadow
/*     */   public abstract void func_180436_i();
/*     */   
/*     */   @Shadow
/*     */   public abstract void func_175072_h();
/*     */   
/*     */   void doRenderHand(float partialTicks, int pass, Minecraft mc) {
/* 426 */     if (!this.field_175078_W) {
/* 427 */       GlStateManager.func_179128_n(5889);
/* 428 */       GlStateManager.func_179096_D();
/* 429 */       float f = 0.07F;
/*     */       
/* 431 */       if (mc.field_71474_y.field_74337_g) {
/* 432 */         GlStateManager.func_179109_b(-(pass * 2 - 1) * 0.07F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 435 */       Project.gluPerspective(func_78481_a(partialTicks, false), mc.field_71443_c / mc.field_71440_d, 0.05F, this.field_78530_s * 2.0F);
/* 436 */       GlStateManager.func_179128_n(5888);
/* 437 */       GlStateManager.func_179096_D();
/*     */       
/* 439 */       if (mc.field_71474_y.field_74337_g) {
/* 440 */         GlStateManager.func_179109_b((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 443 */       GlStateManager.func_179094_E();
/* 444 */       func_78482_e(partialTicks);
/*     */       
/* 446 */       if (mc.field_71474_y.field_74336_f) {
/* 447 */         func_78475_f(partialTicks);
/*     */       }
/*     */       
/* 450 */       boolean flag = (mc.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)mc.func_175606_aa()).func_70608_bn());
/*     */       
/* 452 */       if (!ForgeHooksClient.renderFirstPersonHand(mc.field_71438_f, partialTicks, pass) && 
/* 453 */         mc.field_71474_y.field_74320_O == 0 && !flag && !mc.field_71474_y.field_74319_N && !mc.field_71442_b.func_78747_a()) {
/* 454 */         func_180436_i();
/* 455 */         this.field_78516_c.func_78440_a(partialTicks);
/* 456 */         func_175072_h();
/*     */       } 
/*     */       
/* 459 */       GlStateManager.func_179121_F();
/*     */       
/* 461 */       if (mc.field_71474_y.field_74320_O == 0 && !flag) {
/* 462 */         this.field_78516_c.func_78447_b(partialTicks);
/* 463 */         func_78482_e(partialTicks);
/*     */       } 
/*     */       
/* 466 */       if (mc.field_71474_y.field_74336_f) {
/* 467 */         func_78475_f(partialTicks);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   public abstract void func_78476_b(float paramFloat, int paramInt);
/*     */   
/*     */   @Inject(method = {"orientCamera"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void orientCameraHook(float partialTicks, CallbackInfo ci) {
/* 477 */     if (((ThirdPersView)Thunderhack.moduleManager.getModuleByClass(ThirdPersView.class)).isOn()) {
/* 478 */       ci.cancel();
/* 479 */       orientCameraCustom(partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void orientCameraCustom(float partialTicks) {
/* 485 */     Entity entity = Util.mc.func_175606_aa();
/* 486 */     assert entity != null;
/* 487 */     float f = entity.func_70047_e();
/* 488 */     double d0 = entity.field_70169_q + (entity.field_70165_t - entity.field_70169_q) * partialTicks;
/* 489 */     double d1 = entity.field_70167_r + (entity.field_70163_u - entity.field_70167_r) * partialTicks + f;
/* 490 */     double d2 = entity.field_70166_s + (entity.field_70161_v - entity.field_70166_s) * partialTicks;
/*     */     
/* 492 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70608_bn()) {
/* 493 */       f = (float)(f + 1.0D);
/* 494 */       GlStateManager.func_179109_b(0.0F, 0.3F, 0.0F);
/* 495 */       if (!Util.mc.field_71474_y.field_74325_U) {
/* 496 */         BlockPos blockpos = new BlockPos(entity);
/* 497 */         IBlockState iblockstate = Util.mc.field_71441_e.func_180495_p(blockpos);
/* 498 */         ForgeHooksClient.orientBedCamera((IBlockAccess)Util.mc.field_71441_e, blockpos, iblockstate, entity);
/* 499 */         GlStateManager.func_179114_b(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
/* 500 */         GlStateManager.func_179114_b(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks, -1.0F, 0.0F, 0.0F);
/*     */       } 
/* 502 */     } else if (Util.mc.field_71474_y.field_74320_O > 0) {
/* 503 */       double d3 = ((ThirdPersView)Thunderhack.moduleManager.getModuleByClass(ThirdPersView.class)).isOn() ? ((Float)((ThirdPersView)Thunderhack.moduleManager.getModuleByClass(ThirdPersView.class)).z.getValue()).floatValue() : (this.field_78491_C + (4.0F - this.field_78491_C) * partialTicks);
/* 504 */       if (Util.mc.field_71474_y.field_74325_U) {
/* 505 */         GlStateManager.func_179109_b(0.0F, 0.0F, (float)-d3);
/*     */       } else {
/*     */         float f1, f2;
/* 508 */         if (((ThirdPersView)Thunderhack.moduleManager.getModuleByClass(ThirdPersView.class)).isOff()) {
/* 509 */           f1 = entity.field_70177_z;
/* 510 */           f2 = entity.field_70125_A;
/*     */         } else {
/* 512 */           f1 = entity.field_70177_z + ((Integer)((ThirdPersView)Thunderhack.moduleManager.getModuleByClass(ThirdPersView.class)).x.getValue()).intValue();
/* 513 */           f2 = entity.field_70125_A + ((Integer)((ThirdPersView)Thunderhack.moduleManager.getModuleByClass(ThirdPersView.class)).y.getValue()).intValue();
/*     */         } 
/*     */         
/* 516 */         if (Util.mc.field_71474_y.field_74320_O == 2) {
/* 517 */           f2 += 180.0F;
/*     */         }
/*     */         
/* 520 */         double d4 = (-MathHelper.func_76126_a(f1 * 0.017453292F) * MathHelper.func_76134_b(f2 * 0.017453292F)) * d3;
/* 521 */         double d5 = (MathHelper.func_76134_b(f1 * 0.017453292F) * MathHelper.func_76134_b(f2 * 0.017453292F)) * d3;
/* 522 */         double d6 = -MathHelper.func_76126_a(f2 * 0.017453292F) * d3;
/*     */         
/* 524 */         for (int i = 0; i < 8; i++) {
/* 525 */           float f3 = ((i & 0x1) * 2 - 1);
/* 526 */           float f4 = ((i >> 1 & 0x1) * 2 - 1);
/* 527 */           float f5 = ((i >> 2 & 0x1) * 2 - 1);
/* 528 */           f3 *= 0.1F;
/* 529 */           f4 *= 0.1F;
/* 530 */           f5 *= 0.1F;
/* 531 */           RayTraceResult raytraceresult = Util.mc.field_71441_e.func_72933_a(new Vec3d(d0 + f3, d1 + f4, d2 + f5), new Vec3d(d0 - d4 + f3 + f5, d1 - d6 + f4, d2 - d5 + f5));
/* 532 */           if (raytraceresult != null) {
/* 533 */             double d7 = raytraceresult.field_72307_f.func_72438_d(new Vec3d(d0, d1, d2));
/* 534 */             if (d7 < d3) {
/* 535 */               d3 = d7;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 540 */         if (Util.mc.field_71474_y.field_74320_O == 2) {
/* 541 */           GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 544 */         GlStateManager.func_179114_b(entity.field_70125_A - f2, 1.0F, 0.0F, 0.0F);
/* 545 */         GlStateManager.func_179114_b(entity.field_70177_z - f1, 0.0F, 1.0F, 0.0F);
/* 546 */         GlStateManager.func_179109_b(0.0F, 0.0F, (float)-d3);
/* 547 */         GlStateManager.func_179114_b(f1 - entity.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 548 */         GlStateManager.func_179114_b(f2 - entity.field_70125_A, 1.0F, 0.0F, 0.0F);
/*     */       } 
/*     */     } else {
/* 551 */       GlStateManager.func_179109_b(0.0F, 0.0F, 0.05F);
/*     */     } 
/*     */     
/* 554 */     if (!Util.mc.field_71474_y.field_74325_U) {
/* 555 */       float yaw = entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks + 180.0F;
/* 556 */       float pitch = entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks;
/* 557 */       float f1 = 0.0F;
/* 558 */       if (entity instanceof EntityAnimal) {
/* 559 */         EntityAnimal entityanimal = (EntityAnimal)entity;
/* 560 */         yaw = entityanimal.field_70758_at + (entityanimal.field_70759_as - entityanimal.field_70758_at) * partialTicks + 180.0F;
/*     */       } 
/*     */       
/* 563 */       IBlockState state = ActiveRenderInfo.func_186703_a((World)Util.mc.field_71441_e, entity, partialTicks);
/* 564 */       EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup(Util.mc.field_71460_t, entity, state, partialTicks, yaw, pitch, f1);
/* 565 */       MinecraftForge.EVENT_BUS.post((Event)event);
/* 566 */       GlStateManager.func_179114_b(event.getRoll(), 0.0F, 0.0F, 1.0F);
/* 567 */       GlStateManager.func_179114_b(event.getPitch(), 1.0F, 0.0F, 0.0F);
/* 568 */       GlStateManager.func_179114_b(event.getYaw(), 0.0F, 1.0F, 0.0F);
/*     */     } 
/*     */     
/* 571 */     GlStateManager.func_179109_b(0.0F, -f, 0.0F);
/* 572 */     d0 = entity.field_70169_q + (entity.field_70165_t - entity.field_70169_q) * partialTicks;
/* 573 */     d1 = entity.field_70167_r + (entity.field_70163_u - entity.field_70167_r) * partialTicks + f;
/* 574 */     d2 = entity.field_70166_s + (entity.field_70161_v - entity.field_70166_s) * partialTicks;
/* 575 */     this.field_78500_U = Util.mc.field_71438_f.func_72721_a(d0, d1, d2, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   @Redirect(method = {"orientCamera"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
/*     */   public RayTraceResult rayTraceBlocks(WorldClient world, Vec3d start, Vec3d end) {
/* 581 */     return ((NoCameraClip)Thunderhack.moduleManager.getModuleByClass(NoCameraClip.class)).isOn() ? null : world.func_72933_a(start, end);
/*     */   }
/*     */   
/*     */   @Inject(method = {"orientCamera"}, at = {@At("RETURN")})
/*     */   private void orientCameraStoreEyeHeight(float partialTicks, CallbackInfo ci) {
/* 586 */     if (!((LegitStrafe)Thunderhack.moduleManager.getModuleByClass(LegitStrafe.class)).isOn())
/* 587 */       return;  Entity entity = Util.mc.func_175606_aa();
/* 588 */     if (entity != null)
/* 589 */       GlStateManager.func_179109_b(0.0F, entity.func_70047_e() - 0.4F, 0.0F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */