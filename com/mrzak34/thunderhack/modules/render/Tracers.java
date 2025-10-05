/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.EntityUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class Tracers
/*    */   extends Module
/*    */ {
/* 22 */   private final Setting<Boolean> showFriends = register(new Setting("ShowFriends", Boolean.valueOf(true)));
/* 23 */   private final Setting<ColorSetting> colorSetting = register(new Setting("Color", new ColorSetting(-1)));
/* 24 */   private final Setting<ColorSetting> fcolorSetting = register(new Setting("FriendColor", new ColorSetting(-1)));
/* 25 */   private final Setting<Float> width = register(new Setting("Width", Float.valueOf(2.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/* 26 */   private final Setting<Float> tracerRange = register(new Setting("Range", Float.valueOf(128.0F), Float.valueOf(32.0F), Float.valueOf(256.0F)));
/*    */   public Tracers() {
/* 28 */     super("Tracers", "ебучая паутина-на экране", "tracers", Module.Category.RENDER);
/*    */   }
/*    */   
/*    */   public static void renderTracer(double x, double y, double z, double x2, double y2, double z2, int color) {
/* 32 */     GL11.glBlendFunc(770, 771);
/* 33 */     GL11.glEnable(3042);
/* 34 */     GL11.glLineWidth(1.5F);
/* 35 */     GL11.glDisable(3553);
/* 36 */     GL11.glDisable(2929);
/* 37 */     GL11.glDepthMask(false);
/* 38 */     GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
/* 39 */     GlStateManager.func_179140_f();
/* 40 */     GL11.glLoadIdentity();
/* 41 */     ((IEntityRenderer)mc.field_71460_t).orientCam(mc.func_184121_ak());
/* 42 */     GL11.glEnable(2848);
/* 43 */     GL11.glBegin(1);
/* 44 */     GL11.glVertex3d(x, y, z);
/* 45 */     GL11.glVertex3d(x2, y2, z2);
/* 46 */     GL11.glVertex3d(x2, y2, z2);
/* 47 */     GL11.glEnd();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender3D(Render3DEvent event) {
/* 52 */     if (fullNullCheck())
/* 53 */       return;  GL11.glPushAttrib(1048575);
/* 54 */     for (Entity e : mc.field_71441_e.field_72996_f) {
/* 55 */       if (e instanceof net.minecraft.entity.player.EntityPlayer && e != mc.field_71439_g && 
/* 56 */         mc.field_71439_g.func_70032_d(e) <= ((Float)this.tracerRange.getValue()).floatValue()) {
/* 57 */         Vec3d pos = EntityUtil.interpolateEntity(e, event.getPartialTicks());
/* 58 */         GL11.glBlendFunc(770, 771);
/* 59 */         GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 60 */         GlStateManager.func_187441_d(((Float)this.width.getValue()).floatValue());
/* 61 */         GlStateManager.func_179090_x();
/* 62 */         GlStateManager.func_179132_a(false);
/* 63 */         GlStateManager.func_179147_l();
/* 64 */         GlStateManager.func_179097_i();
/* 65 */         GlStateManager.func_179140_f();
/* 66 */         GlStateManager.func_179129_p();
/* 67 */         GlStateManager.func_179141_d();
/* 68 */         GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
/* 69 */         boolean bobbing = mc.field_71474_y.field_74336_f;
/* 70 */         mc.field_71474_y.field_74336_f = false;
/*    */         
/* 72 */         Color color = (Thunderhack.friendManager.isFriend(e.func_70005_c_()) && ((Boolean)this.showFriends.getValue()).booleanValue()) ? ((ColorSetting)this.fcolorSetting.getValue()).getColorObject() : ((ColorSetting)this.colorSetting.getValue()).getColorObject();
/* 73 */         Vec3d eyes = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians(mc.field_71439_g.field_70125_A))).func_178785_b(-((float)Math.toRadians(mc.field_71439_g.field_70177_z)));
/* 74 */         renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, pos.field_72450_a - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), pos.field_72448_b - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), pos.field_72449_c - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ(), color.getRGB());
/* 75 */         mc.field_71474_y.field_74336_f = bobbing;
/* 76 */         GlStateManager.func_179084_k();
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 81 */     GL11.glPopAttrib();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Tracers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */