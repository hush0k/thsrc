/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class BlockHighlight extends Module {
/* 13 */   public final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(575714484)));
/* 14 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*    */   
/*    */   public BlockHighlight() {
/* 17 */     super("BlockHighlight", "подсвечивает блок на-который ты смотришь", Module.Category.RENDER);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender3D(Render3DEvent event) {
/* 22 */     RayTraceResult ray = mc.field_71476_x;
/* 23 */     if (ray != null && ray.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 24 */       BlockPos blockpos = ray.func_178782_a();
/* 25 */       RenderUtil.drawBlockOutline(blockpos, ((ColorSetting)this.color.getValue()).getColorObject(), ((Float)this.lineWidth.getValue()).floatValue(), false, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\BlockHighlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */