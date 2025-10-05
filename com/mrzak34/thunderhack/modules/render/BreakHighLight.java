/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IPlayerControllerMP;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderGlobal;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.TessellatorUtil;
/*     */ import com.mrzak34.thunderhack.util.render.BlockRenderUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.DestroyBlockProgress;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class BreakHighLight
/*     */   extends Module {
/*  29 */   private final Setting<BreakRenderMode> bRenderMode = register(new Setting("BRenderMove", BreakRenderMode.GROW));
/*  30 */   private final Setting<Float> bRange = register(new Setting("BRange", Float.valueOf(15.0F), Float.valueOf(5.0F), Float.valueOf(255.0F)));
/*  31 */   private final Setting<Boolean> bOutline = register(new Setting("BOutline", Boolean.valueOf(true)));
/*  32 */   private final Setting<Boolean> bWireframe = register(new Setting("BWireframe", Boolean.valueOf(false)));
/*  33 */   private final Setting<Float> bWidth = register(new Setting("BWidth", Float.valueOf(1.5F), Float.valueOf(1.0F), Float.valueOf(10.0F)));
/*  34 */   private final Setting<ColorSetting> bOutlineColor = register(new Setting("BOutlineColor", new ColorSetting(-65536)));
/*  35 */   private final Setting<ColorSetting> bCrossOutlineColor = register(new Setting("BCrossOutlineColor", new ColorSetting(-65536)));
/*  36 */   private final Setting<Boolean> naame = register(new Setting("Name", Boolean.valueOf(true)));
/*  37 */   private final Setting<Boolean> bFill = register(new Setting("BFill", Boolean.valueOf(true)));
/*  38 */   private final Setting<ColorSetting> bFillColor = register(new Setting("BFillColor", new ColorSetting(1727987712)));
/*  39 */   private final Setting<ColorSetting> bCrossFillColor = register(new Setting("BCrossFillColor", new ColorSetting(1727987712)));
/*  40 */   private final Setting<Boolean> bTracer = register(new Setting("BTracer", Boolean.valueOf(false)));
/*  41 */   private final Setting<ColorSetting> bTracerColor = register(new Setting("BTracerColor", new ColorSetting(-65536)));
/*  42 */   private final Setting<Boolean> pOutline = register(new Setting("POutline", Boolean.valueOf(true)));
/*  43 */   private final Setting<Boolean> pWireframe = register(new Setting("PWireframe", Boolean.valueOf(false)));
/*  44 */   private final Setting<Float> pWidth = register(new Setting("PWidth", Float.valueOf(1.5F), Float.valueOf(1.0F), Float.valueOf(10.0F)));
/*  45 */   private final Setting<ColorSetting> pOutlineColor = register(new Setting("POutlineColor", new ColorSetting(-16776961)));
/*  46 */   private final Setting<Boolean> pFill = register(new Setting("PFill", Boolean.valueOf(true)));
/*  47 */   private final Setting<ColorSetting> pFillColor = register(new Setting("PFillColor", new ColorSetting(1711276287)));
/*     */   public BreakHighLight() {
/*  49 */     super("BreakHighLight", "рендерит ломания-блоков", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   public static void renderBreakingBB2(AxisAlignedBB bb, Color fill, Color outline) {
/*  53 */     BlockRenderUtil.prepareGL();
/*  54 */     TessellatorUtil.drawBox(bb, fill);
/*  55 */     BlockRenderUtil.releaseGL();
/*  56 */     BlockRenderUtil.prepareGL();
/*  57 */     TessellatorUtil.drawBoundingBox(bb, 1.0D, outline);
/*  58 */     BlockRenderUtil.releaseGL();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  63 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/*  64 */       return;  GL11.glPushMatrix();
/*  65 */     GL11.glPushAttrib(1048575);
/*  66 */     GlStateManager.func_179090_x();
/*  67 */     GlStateManager.func_179118_c();
/*  68 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/*  69 */     GlStateManager.func_179103_j(7425);
/*  70 */     GlStateManager.func_179097_i();
/*     */     
/*  72 */     if (mc.field_71442_b.func_181040_m()) {
/*  73 */       float progress = ((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP();
/*     */       
/*  75 */       BlockPos pos = ((IPlayerControllerMP)mc.field_71442_b).getCurrentBlock();
/*  76 */       AxisAlignedBB bb = mc.field_71441_e.func_180495_p(pos).func_185900_c((IBlockAccess)mc.field_71441_e, pos).func_186670_a(pos);
/*     */       
/*  78 */       switch ((BreakRenderMode)this.bRenderMode.getValue()) {
/*     */         case GROW:
/*  80 */           renderBreakingBB(bb.func_186664_h(0.5D - progress * 0.5D), (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/*     */           break;
/*     */         
/*     */         case SHRINK:
/*  84 */           renderBreakingBB(bb.func_186664_h(progress * 0.5D), (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/*     */           break;
/*     */         
/*     */         case CROSS:
/*  88 */           renderBreakingBB(bb.func_186664_h(0.5D - progress * 0.5D), (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/*  89 */           renderBreakingBB(bb.func_186664_h(progress * 0.5D), (ColorSetting)this.bCrossFillColor.getValue(), (ColorSetting)this.bCrossOutlineColor.getValue());
/*     */           break;
/*     */         
/*     */         default:
/*  93 */           renderBreakingBB(bb, (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/*  98 */       if (((Boolean)this.bTracer.getValue()).booleanValue()) {
/*     */ 
/*     */ 
/*     */         
/* 102 */         Vec3d eyes = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians(mc.field_71439_g.field_70125_A))).func_178785_b(
/* 103 */             -((float)Math.toRadians(mc.field_71439_g.field_70177_z)));
/*     */         
/* 105 */         renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, pos
/* 106 */             .func_177958_n() - ((IRenderManager)mc.func_175598_ae()).getRenderPosX() + 0.5D, pos
/* 107 */             .func_177956_o() - ((IRenderManager)mc.func_175598_ae()).getRenderPosY() + 0.5D, pos
/* 108 */             .func_177952_p() - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ() + 0.5D, ((ColorSetting)this.bTracerColor
/* 109 */             .getValue()).getColor());
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     ((IRenderGlobal)mc.field_71438_f).getDamagedBlocks().forEach((integer, destroyBlockProgress) -> {
/*     */           renderGlobalBreakage(destroyBlockProgress);
/*     */           
/*     */           Entity object = mc.field_71441_e.func_73045_a(integer.intValue());
/*     */           if (object != null && ((Boolean)this.naame.getValue()).booleanValue() && !object.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())) {
/*     */             GlStateManager.func_179094_E();
/*     */             BlockPos pos = destroyBlockProgress.func_180246_b();
/*     */             try {
/*     */               RenderUtil.glBillboardDistanceScaled(pos.func_177958_n() + 0.5F, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 0.5F, (EntityPlayer)mc.field_71439_g, 1.0F);
/* 122 */             } catch (Exception exception) {}
/*     */             
/*     */             String name = object.func_70005_c_();
/*     */             
/*     */             FontRender.drawString3(name, (int)-(FontRender.getStringWidth(name) / 2.0D), -4.0F, -1);
/*     */             
/*     */             GlStateManager.func_179121_F();
/*     */           } 
/*     */         });
/* 131 */     GL11.glPopAttrib();
/* 132 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   private void renderGlobalBreakage(DestroyBlockProgress destroyBlockProgress) {
/* 136 */     if (destroyBlockProgress != null) {
/* 137 */       BlockPos pos = destroyBlockProgress.func_180246_b();
/* 138 */       if (mc.field_71442_b.func_181040_m() && (
/* 139 */         (IPlayerControllerMP)mc.field_71442_b).getCurrentBlock().equals(pos))
/*     */         return; 
/* 141 */       if (mc.field_71439_g.func_70011_f(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D) > ((Float)this.bRange.getValue()).floatValue())
/* 142 */         return;  float progress = Math.min(1.0F, destroyBlockProgress.func_73106_e() / 8.0F);
/*     */       
/* 144 */       AxisAlignedBB bb = mc.field_71441_e.func_180495_p(pos).func_185900_c((IBlockAccess)mc.field_71441_e, pos).func_186670_a(pos);
/*     */       
/* 146 */       switch ((BreakRenderMode)this.bRenderMode.getValue()) {
/*     */         case GROW:
/* 148 */           renderBreakingBB(bb.func_186664_h(0.5D - progress * 0.5D), (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/*     */           break;
/*     */         
/*     */         case SHRINK:
/* 152 */           renderBreakingBB(bb.func_186664_h(progress * 0.5D), (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/*     */           break;
/*     */         
/*     */         case CROSS:
/* 156 */           renderBreakingBB(bb.func_186664_h(0.5D - progress * 0.5D), (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/* 157 */           renderBreakingBB(bb.func_186664_h(progress * 0.5D), (ColorSetting)this.bCrossFillColor.getValue(), (ColorSetting)this.bCrossOutlineColor.getValue());
/*     */           break;
/*     */         
/*     */         default:
/* 161 */           renderBreakingBB(bb, (ColorSetting)this.bFillColor.getValue(), (ColorSetting)this.bOutlineColor.getValue());
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 166 */       if (((Boolean)this.bTracer.getValue()).booleanValue()) {
/*     */ 
/*     */ 
/*     */         
/* 170 */         Vec3d eyes = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians(mc.field_71439_g.field_70125_A))).func_178785_b(
/* 171 */             -((float)Math.toRadians(mc.field_71439_g.field_70177_z)));
/*     */         
/* 173 */         renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, pos
/* 174 */             .func_177958_n() - ((IRenderManager)mc.func_175598_ae()).getRenderPosX() + 0.5D, pos
/* 175 */             .func_177956_o() - ((IRenderManager)mc.func_175598_ae()).getRenderPosY() + 0.5D, pos
/* 176 */             .func_177952_p() - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ() + 0.5D, ((ColorSetting)this.bTracerColor
/* 177 */             .getValue()).getColor());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderPlacingBB(AxisAlignedBB bb) {
/* 183 */     if (((Boolean)this.pFill.getValue()).booleanValue()) {
/* 184 */       BlockRenderUtil.prepareGL();
/* 185 */       TessellatorUtil.drawBox(bb, ((ColorSetting)this.pFillColor.getValue()).getColorObject());
/* 186 */       BlockRenderUtil.releaseGL();
/*     */     } 
/*     */     
/* 189 */     if (((Boolean)this.pOutline.getValue()).booleanValue()) {
/* 190 */       BlockRenderUtil.prepareGL();
/* 191 */       if (((Boolean)this.pWireframe.getValue()).booleanValue()) {
/* 192 */         BlockRenderUtil.drawWireframe(bb.func_72317_d(-((IRenderManager)Module.mc.func_175598_ae()).getRenderPosX(), -((IRenderManager)Module.mc.func_175598_ae()).getRenderPosY(), -((IRenderManager)Module.mc.func_175598_ae()).getRenderPosZ()), ((ColorSetting)this.pOutlineColor.getValue()).getColor(), ((Float)this.pWidth.getValue()).floatValue());
/*     */       } else {
/* 194 */         TessellatorUtil.drawBoundingBox(bb, ((Float)this.pWidth.getValue()).floatValue(), ((ColorSetting)this.pOutlineColor.getValue()).getColorObject());
/*     */       } 
/* 196 */       BlockRenderUtil.releaseGL();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderBreakingBB(AxisAlignedBB bb, ColorSetting fill, ColorSetting outline) {
/* 201 */     if (((Boolean)this.bFill.getValue()).booleanValue()) {
/* 202 */       BlockRenderUtil.prepareGL();
/* 203 */       TessellatorUtil.drawBox(bb, fill.getColorObject());
/* 204 */       BlockRenderUtil.releaseGL();
/*     */     } 
/*     */     
/* 207 */     if (((Boolean)this.bOutline.getValue()).booleanValue()) {
/* 208 */       BlockRenderUtil.prepareGL();
/* 209 */       if (((Boolean)this.bWireframe.getValue()).booleanValue()) {
/* 210 */         BlockRenderUtil.drawWireframe(bb.func_72317_d(-((IRenderManager)mc.func_175598_ae()).getRenderPosX(), -((IRenderManager)mc.func_175598_ae()).getRenderPosY(), -((IRenderManager)mc.func_175598_ae()).getRenderPosZ()), outline.getColor(), ((Float)this.bWidth.getValue()).floatValue());
/*     */       } else {
/* 212 */         TessellatorUtil.drawBoundingBox(bb, ((Float)this.bWidth.getValue()).floatValue(), outline.getColorObject());
/*     */       } 
/* 214 */       BlockRenderUtil.releaseGL();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderTracer(double x, double y, double z, double x2, double y2, double z2, int color) {
/* 219 */     GL11.glBlendFunc(770, 771);
/* 220 */     GL11.glEnable(3042);
/* 221 */     GL11.glLineWidth(1.5F);
/* 222 */     GL11.glDisable(3553);
/* 223 */     GL11.glDisable(2929);
/* 224 */     GL11.glDepthMask(false);
/*     */     
/* 226 */     GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
/* 227 */     GlStateManager.func_179140_f();
/* 228 */     GL11.glLoadIdentity();
/*     */     
/* 230 */     ((IEntityRenderer)mc.field_71460_t).orientCam(mc.func_184121_ak());
/*     */     
/* 232 */     GL11.glEnable(2848);
/*     */     
/* 234 */     GL11.glBegin(1);
/* 235 */     GL11.glVertex3d(x, y, z);
/* 236 */     GL11.glVertex3d(x2, y2, z2);
/* 237 */     GL11.glVertex3d(x2, y2, z2);
/* 238 */     GL11.glEnd();
/*     */     
/* 240 */     GL11.glDisable(2848);
/*     */     
/* 242 */     GL11.glEnable(3553);
/* 243 */     GL11.glEnable(2929);
/* 244 */     GL11.glDepthMask(true);
/* 245 */     GL11.glDisable(3042);
/* 246 */     GL11.glColor3d(1.0D, 1.0D, 1.0D);
/* 247 */     GlStateManager.func_179145_e();
/*     */   }
/*     */   
/*     */   private enum BreakRenderMode
/*     */   {
/* 252 */     GROW, SHRINK, CROSS, STATIC;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\BreakHighLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */