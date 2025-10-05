/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.EventPostSync;
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.DrawHelper;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class BreadCrumbs
/*    */   extends Module {
/*    */   private final Setting<Integer> limit;
/*    */   
/*    */   public BreadCrumbs() {
/* 23 */     super("BreadCrumbs", "оставляет линию-при ходьбе", "BreadCrumbs", Module.Category.RENDER);
/*    */ 
/*    */     
/* 26 */     this.limit = register(new Setting("ListLimit", Integer.valueOf(1000), Integer.valueOf(10), Integer.valueOf(99999)));
/* 27 */     this.color = register(new Setting("Color", new ColorSetting(3649978)));
/* 28 */     this.positions = new CopyOnWriteArrayList<>();
/*    */   }
/*    */   private final Setting<ColorSetting> color; private final List<Vec3d> positions;
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender3D(Render3DEvent event) {
/* 34 */     GL11.glPushAttrib(1048575);
/* 35 */     GL11.glPushMatrix();
/* 36 */     GL11.glDisable(3553);
/* 37 */     GL11.glBlendFunc(770, 771);
/* 38 */     GL11.glEnable(2848);
/* 39 */     GL11.glEnable(3042);
/* 40 */     GL11.glDisable(2929);
/* 41 */     mc.field_71460_t.func_175072_h();
/*    */     
/* 43 */     GL11.glLineWidth(2.0F);
/* 44 */     GL11.glBegin(3);
/* 45 */     for (Vec3d pos : this.positions) {
/* 46 */       RenderUtil.glColor(((ColorSetting)this.color.getValue()).getColor());
/* 47 */       GL11.glVertex3d(pos.field_72450_a - ((IRenderManager)mc
/* 48 */           .func_175598_ae()).getRenderPosX(), pos.field_72448_b - ((IRenderManager)mc
/* 49 */           .func_175598_ae()).getRenderPosY(), pos.field_72449_c - ((IRenderManager)mc
/* 50 */           .func_175598_ae()).getRenderPosZ());
/*    */     } 
/* 52 */     GL11.glEnd();
/*    */     
/* 54 */     GL11.glLineWidth(5.0F);
/* 55 */     GL11.glBegin(3);
/* 56 */     for (Vec3d pos : this.positions) {
/* 57 */       RenderUtil.glColor(DrawHelper.injectAlpha(((ColorSetting)this.color.getValue()).getColorObject(), 80));
/* 58 */       GL11.glVertex3d(pos.field_72450_a - ((IRenderManager)mc
/* 59 */           .func_175598_ae()).getRenderPosX(), pos.field_72448_b - ((IRenderManager)mc
/* 60 */           .func_175598_ae()).getRenderPosY(), pos.field_72449_c - ((IRenderManager)mc
/* 61 */           .func_175598_ae()).getRenderPosZ());
/*    */     } 
/* 63 */     GL11.glEnd();
/*    */     
/* 65 */     GL11.glLineWidth(10.0F);
/* 66 */     GL11.glBegin(3);
/* 67 */     for (Vec3d pos : this.positions) {
/* 68 */       RenderUtil.glColor(DrawHelper.injectAlpha(((ColorSetting)this.color.getValue()).getColorObject(), 50));
/* 69 */       GL11.glVertex3d(pos.field_72450_a - ((IRenderManager)mc
/* 70 */           .func_175598_ae()).getRenderPosX(), pos.field_72448_b - ((IRenderManager)mc
/* 71 */           .func_175598_ae()).getRenderPosY(), pos.field_72449_c - ((IRenderManager)mc
/* 72 */           .func_175598_ae()).getRenderPosZ());
/*    */     } 
/* 74 */     GL11.glEnd();
/*    */     
/* 76 */     GlStateManager.func_179117_G();
/* 77 */     GL11.glPopMatrix();
/* 78 */     GL11.glPopAttrib();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void postSync(EventPostSync event) {
/* 83 */     if (this.positions.size() > ((Integer)this.limit.getValue()).intValue()) {
/* 84 */       this.positions.remove(0);
/*    */     }
/* 86 */     this.positions.add(new Vec3d(mc.field_71439_g.field_70165_t, (mc.field_71439_g.func_174813_aQ()).field_72338_b, mc.field_71439_g.field_70161_v));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 91 */     this.positions.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\BreadCrumbs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */