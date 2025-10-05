/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityBeacon;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.Sphere;
/*    */ 
/*    */ public class BeakonESP extends Module {
/* 17 */   public final Setting<ColorSetting> color = register(new Setting("ESPColor", new ColorSetting(-2013200640)));
/* 18 */   public final Setting<ColorSetting> color2 = register(new Setting("CircleColor", new ColorSetting(-2013200640)));
/* 19 */   private final Setting<Integer> slices = register(new Setting("slices", Integer.valueOf(60), Integer.valueOf(10), Integer.valueOf(240)));
/* 20 */   private final Setting<Integer> stacks = register(new Setting("stacks", Integer.valueOf(60), Integer.valueOf(10), Integer.valueOf(240)));
/*    */   public BeakonESP() {
/* 22 */     super("BeakonESP", "радиус действия маяка", Module.Category.RENDER);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender3D(Render3DEvent event) {
/* 27 */     for (TileEntity tileent : mc.field_71441_e.field_147482_g) {
/* 28 */       if (tileent instanceof TileEntityBeacon) {
/* 29 */         TileEntityBeacon beacon = (TileEntityBeacon)tileent;
/* 30 */         double n = beacon.func_174877_v().func_177958_n();
/* 31 */         mc.func_175598_ae();
/* 32 */         double x = n - ((IRenderManager)mc.func_175598_ae()).getRenderPosX();
/* 33 */         double n2 = beacon.func_174877_v().func_177956_o();
/* 34 */         mc.func_175598_ae();
/* 35 */         double y = n2 - ((IRenderManager)mc.func_175598_ae()).getRenderPosY();
/* 36 */         double n3 = beacon.func_174877_v().func_177952_p();
/* 37 */         mc.func_175598_ae();
/* 38 */         double z = n3 - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ();
/* 39 */         GL11.glPushMatrix();
/* 40 */         RenderUtil.drawBlockOutline(beacon.func_174877_v(), ((ColorSetting)this.color.getValue()).getColorObject(), 3.0F, true, 0);
/* 41 */         RenderHelper.func_74518_a();
/* 42 */         float var12 = beacon.func_191979_s();
/* 43 */         float var13 = (var12 == 1.0F) ? 19.0F : ((var12 == 2.0F) ? 29.0F : ((var12 == 3.0F) ? 39.0F : ((var12 == 4.0F) ? 49.0F : 0.0F)));
/* 44 */         draw(x, y, z, (int)var13);
/* 45 */         RenderHelper.func_74519_b();
/* 46 */         GL11.glPopMatrix();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(double x, double y, double z, int power) {
/* 53 */     GL11.glDisable(2896);
/* 54 */     GL11.glDisable(3553);
/* 55 */     GL11.glEnable(3042);
/* 56 */     GL11.glBlendFunc(770, 771);
/* 57 */     GL11.glDisable(2929);
/* 58 */     GL11.glEnable(2848);
/* 59 */     GL11.glDepthMask(true);
/* 60 */     GL11.glLineWidth(1.0F);
/* 61 */     GL11.glTranslated(x, y, z);
/* 62 */     GL11.glColor4f(((ColorSetting)this.color2.getValue()).getRed() / 255.0F, ((ColorSetting)this.color2.getValue()).getBlue() / 255.0F, ((ColorSetting)this.color2.getValue()).getBlue() / 255.0F, ((ColorSetting)this.color2.getValue()).getAlpha() / 255.0F);
/* 63 */     Sphere tip = new Sphere();
/* 64 */     tip.setDrawStyle(100013);
/*    */     
/* 66 */     tip.draw(power, ((Integer)this.slices.getValue()).intValue(), ((Integer)this.stacks.getValue()).intValue());
/*    */     
/* 68 */     GL11.glDepthMask(true);
/* 69 */     GL11.glDisable(2848);
/* 70 */     GL11.glEnable(2929);
/* 71 */     GL11.glDisable(3042);
/* 72 */     GL11.glEnable(2896);
/* 73 */     GL11.glEnable(3553);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\BeakonESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */