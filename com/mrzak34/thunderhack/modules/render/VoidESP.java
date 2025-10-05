/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.BlockUtils;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class VoidESP
/*    */   extends Module {
/* 17 */   public Setting<Float> range = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(3.0F), Float.valueOf(16.0F)));
/* 18 */   public Setting<Boolean> down = register(new Setting("Up", Boolean.valueOf(false)));
/* 19 */   private List<BlockPos> holes = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public VoidESP() {
/* 23 */     super("VoidESP", "VoidESP", Module.Category.PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 28 */     this.holes = calcHoles();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender3D(Render3DEvent event) {
/* 33 */     for (BlockPos pos : this.holes) {
/* 34 */       RenderUtil.renderCrosses(((Boolean)this.down.getValue()).booleanValue() ? pos.func_177984_a() : pos, new Color(255, 255, 255), 2.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public List<BlockPos> calcHoles() {
/* 39 */     ArrayList<BlockPos> voidHoles = new ArrayList<>();
/* 40 */     List<BlockPos> positions = BlockUtils.getSphere(((Float)this.range.getValue()).floatValue(), false);
/* 41 */     for (BlockPos pos : positions) {
/* 42 */       if (pos.func_177956_o() != 0 || mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150357_h)
/* 43 */         continue;  voidHoles.add(pos);
/*    */     } 
/* 45 */     return voidHoles;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\VoidESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */