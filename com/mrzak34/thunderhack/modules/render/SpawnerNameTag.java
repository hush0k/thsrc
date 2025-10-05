/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class SpawnerNameTag
/*     */   extends Module
/*     */ {
/*  23 */   public final Setting<ColorSetting> rectcolor = register(new Setting("RectColor", new ColorSetting(1426063360)));
/*  24 */   public final Setting<ColorSetting> color = register(new Setting("ESPColor", new ColorSetting(-2013200640)));
/*  25 */   private final Setting<Float> scaling = register(new Setting("Size", Float.valueOf(20.0F), Float.valueOf(0.1F), Float.valueOf(30.0F)));
/*  26 */   private final Setting<Boolean> scaleing = register(new Setting("Scale", Boolean.valueOf(true)));
/*  27 */   private final Setting<Float> factor = register(new Setting("Factor", Float.valueOf(0.17F), Float.valueOf(0.1F), Float.valueOf(1.0F)));
/*     */   public SpawnerNameTag() {
/*  29 */     super("SpawnerNameTag", "Подсвечивает спавнера", "spawner esp", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  34 */     for (TileEntity tileent : mc.field_71441_e.field_147482_g) {
/*  35 */       if (tileent instanceof TileEntityMobSpawner) {
/*  36 */         TileEntityMobSpawner spawner = (TileEntityMobSpawner)tileent;
/*  37 */         double n = spawner.func_174877_v().func_177958_n();
/*  38 */         mc.func_175598_ae();
/*  39 */         double x = n - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosX();
/*  40 */         double n2 = spawner.func_174877_v().func_177956_o();
/*  41 */         mc.func_175598_ae();
/*  42 */         double y = n2 - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosY();
/*  43 */         double n3 = spawner.func_174877_v().func_177952_p();
/*  44 */         mc.func_175598_ae();
/*  45 */         double z = n3 - ((IRenderManager)Util.mc.func_175598_ae()).getRenderPosZ();
/*     */         
/*  47 */         GL11.glPushMatrix();
/*  48 */         RenderUtil.drawBlockOutline(spawner.func_174877_v(), ((ColorSetting)this.color.getValue()).getColorObject(), 3.0F, true, 0);
/*  49 */         RenderHelper.func_74518_a();
/*     */         
/*  51 */         String entity = StringUtils.substringBetween(spawner.func_189517_E_().toString(), "id:\"minecraft:", "\"");
/*  52 */         int time = Integer.parseInt(StringUtils.substringBetween(spawner.func_189517_E_().toString(), ",Delay:", "s,")) / 20;
/*  53 */         renderNameTag(x + 0.5D, y, z + 0.5D, event.getPartialTicks(), entity + " " + time + " s");
/*  54 */         RenderHelper.func_74519_b();
/*  55 */         GL11.glPopMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderNameTag(double x, double y, double z, float delta, String displayTag) {
/*  62 */     double tempY = y;
/*  63 */     tempY += 0.7D;
/*  64 */     Entity camera = NameTags.mc.func_175606_aa();
/*  65 */     assert camera != null;
/*  66 */     double originalPositionX = camera.field_70165_t;
/*  67 */     double originalPositionY = camera.field_70163_u;
/*  68 */     double originalPositionZ = camera.field_70161_v;
/*  69 */     camera.field_70165_t = RenderUtil.interpolate(camera.field_70169_q, camera.field_70165_t, delta);
/*  70 */     camera.field_70163_u = RenderUtil.interpolate(camera.field_70167_r, camera.field_70163_u, delta);
/*  71 */     camera.field_70161_v = RenderUtil.interpolate(camera.field_70166_s, camera.field_70161_v, delta);
/*  72 */     double distance = camera.func_70011_f(x + (NameTags.mc.func_175598_ae()).field_78730_l, y + (NameTags.mc.func_175598_ae()).field_78731_m, z + (NameTags.mc.func_175598_ae()).field_78728_n);
/*  73 */     int width = mc.field_71466_p.func_78256_a(displayTag) / 2;
/*  74 */     double scale = (0.0018D + ((Float)this.scaling.getValue()).floatValue() * distance * ((Float)this.factor.getValue()).floatValue()) / 1000.0D;
/*  75 */     if (!((Boolean)this.scaleing.getValue()).booleanValue()) {
/*  76 */       scale = ((Float)this.scaling.getValue()).floatValue() / 100.0D;
/*     */     }
/*  78 */     GlStateManager.func_179094_E();
/*  79 */     RenderHelper.func_74519_b();
/*  80 */     GlStateManager.func_179088_q();
/*  81 */     GlStateManager.func_179136_a(1.0F, -1500000.0F);
/*  82 */     GlStateManager.func_179140_f();
/*  83 */     GlStateManager.func_179109_b((float)x, (float)tempY + 1.4F, (float)z);
/*  84 */     GlStateManager.func_179114_b(-(NameTags.mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
/*  85 */     GlStateManager.func_179114_b((NameTags.mc.func_175598_ae()).field_78732_j, (NameTags.mc.field_71474_y.field_74320_O == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/*  86 */     GlStateManager.func_179139_a(-scale, -scale, scale);
/*  87 */     GlStateManager.func_179097_i();
/*  88 */     GlStateManager.func_179147_l();
/*     */     
/*  90 */     RenderUtil.drawRect((-width - 2), -4.0F, width + 2.0F, 4.0F, ((ColorSetting)this.rectcolor.getValue()).getColor());
/*     */     
/*  92 */     GlStateManager.func_179084_k();
/*  93 */     mc.field_71466_p.func_175063_a(displayTag, -width, -4.0F, -1);
/*  94 */     camera.field_70165_t = originalPositionX;
/*  95 */     camera.field_70163_u = originalPositionY;
/*  96 */     camera.field_70161_v = originalPositionZ;
/*  97 */     GlStateManager.func_179126_j();
/*  98 */     GlStateManager.func_179113_r();
/*  99 */     GlStateManager.func_179136_a(1.0F, 1500000.0F);
/* 100 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\SpawnerNameTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */