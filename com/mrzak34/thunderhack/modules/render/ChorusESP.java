/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.ChorusEvent;
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class ChorusESP
/*    */   extends Module
/*    */ {
/* 16 */   private final Setting<Integer> time = register(new Setting("Duration", Integer.valueOf(500), Integer.valueOf(50), Integer.valueOf(3000)));
/* 17 */   private final Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(true)));
/* 18 */   private final Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/* 19 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F), v -> ((Boolean)this.outline.getValue()).booleanValue()));
/* 20 */   private final Timer timer = new Timer();
/* 21 */   private final Setting<ColorSetting> outlineColor = register(new Setting("OutlineColor", new ColorSetting(575714484)));
/* 22 */   private final Setting<ColorSetting> boxColor = register(new Setting("BoxColor", new ColorSetting(575714484)));
/*    */   
/*    */   private double x;
/*    */   
/*    */   public ChorusESP() {
/* 27 */     super("ChorusESP", "рендерит звук хоруса", Module.Category.RENDER);
/*    */   }
/*    */   private double y; private double z;
/*    */   @SubscribeEvent
/*    */   public void onChorus(ChorusEvent event) {
/* 32 */     this.x = event.getChorusX();
/* 33 */     this.y = event.getChorusY();
/* 34 */     this.z = event.getChorusZ();
/* 35 */     this.timer.reset();
/*    */   }
/*    */   
/*    */   public void onRender3D(Render3DEvent render3DEvent) {
/* 39 */     if (this.timer.passedMs(((Integer)this.time.getValue()).intValue())) {
/*    */       return;
/*    */     }
/* 42 */     AxisAlignedBB pos = RenderUtil.interpolateAxis(new AxisAlignedBB(this.x - 0.3D, this.y, this.z - 0.3D, this.x + 0.3D, this.y + 1.8D, this.z + 0.3D));
/* 43 */     if (((Boolean)this.outline.getValue()).booleanValue()) {
/* 44 */       RenderUtil.drawBlockOutline(pos, ((ColorSetting)this.outlineColor.getValue()).getColorObject(), ((Float)this.lineWidth.getValue()).floatValue());
/*    */     }
/* 46 */     if (((Boolean)this.box.getValue()).booleanValue())
/* 47 */       RenderUtil.drawFilledBox(pos, ((ColorSetting)this.boxColor.getValue()).getRawColor()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\ChorusESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */