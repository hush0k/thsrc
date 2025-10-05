/*    */ package com.mrzak34.thunderhack.modules.render;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.BlockUtils;
/*    */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class TunnelESP
/*    */   extends Module
/*    */ {
/* 17 */   private final Setting<Integer> boxAlpha = register(new Setting("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255)));
/* 18 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/* 19 */   private final Setting<ColorSetting> Color1 = register(new Setting("Color1", new ColorSetting(-2013200640)));
/* 20 */   private final Setting<ColorSetting> Color2 = register(new Setting("Color2", new ColorSetting(-2013200640)));
/* 21 */   public Setting<Boolean> box = register(new Setting("Box", Boolean.valueOf(true)));
/* 22 */   public Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/* 23 */   List<BlockPos> tunnelbp = new ArrayList<>();
/*    */   int delay;
/*    */   
/*    */   public TunnelESP() {
/* 27 */     super("TunnelESP", "Подсвечивает туннели", Module.Category.RENDER);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/*    */     try {
/* 34 */       for (BlockPos bp : this.tunnelbp) {
/* 35 */         RenderUtil.drawBoxESP(bp, ((ColorSetting)this.Color1.getValue()).getColorObject(), ((Boolean)this.outline.getValue()).booleanValue(), ((ColorSetting)this.Color2.getValue()).getColorObject(), ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.outline.getValue()).booleanValue(), ((Boolean)this.box.getValue()).booleanValue(), ((Integer)this.boxAlpha.getValue()).intValue(), true, 0);
/*    */       }
/* 37 */     } catch (Exception e) {
/* 38 */       System.out.println("Concurrent exception");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 44 */     if (this.delay++ > 100) {
/* 45 */       (new Thread(() -> {
/*    */             for (int x = (int)(mc.field_71439_g.field_70165_t - 124.0D); x < mc.field_71439_g.field_70165_t + 124.0D; x++) {
/*    */               for (int z = (int)(mc.field_71439_g.field_70161_v - 124.0D); z < mc.field_71439_g.field_70161_v + 124.0D; z++) {
/*    */                 for (int y = 1; y < 120; y++) {
/*    */                   if (one_one(new BlockPos(x, y, z))) {
/*    */                     this.tunnelbp.add(new BlockPos(x, y, z));
/*    */                   } else if (one_two(new BlockPos(x, y, z))) {
/*    */                     this.tunnelbp.add(new BlockPos(x, y, z));
/*    */                     this.tunnelbp.add(new BlockPos(x, y + 1, z));
/*    */                   } 
/*    */                 } 
/*    */               } 
/*    */             } 
/* 58 */           })).start();
/* 59 */       this.delay = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean one_two(BlockPos pos) {
/* 65 */     if (this.tunnelbp.contains(pos)) return false; 
/* 66 */     if (!BlockUtils.isAir(pos) || !BlockUtils.isAir(pos.func_177984_a())) return false; 
/* 67 */     if (BlockUtils.isAir(pos.func_177977_b()) || BlockUtils.isAir(pos.func_177984_a().func_177984_a())) return false; 
/* 68 */     if (BlockUtils.isAir(pos.func_177978_c()) && BlockUtils.isAir(pos.func_177968_d()) && BlockUtils.isAir(pos.func_177984_a().func_177978_c()) && BlockUtils.isAir(pos.func_177984_a().func_177968_d())) {
/* 69 */       return (!BlockUtils.isAir(pos.func_177974_f()) && !BlockUtils.isAir(pos.func_177976_e()) && !BlockUtils.isAir(pos.func_177984_a().func_177974_f()) && !BlockUtils.isAir(pos.func_177984_a().func_177976_e()));
/*    */     }
/* 71 */     if (BlockUtils.isAir(pos.func_177974_f()) && BlockUtils.isAir(pos.func_177976_e()) && BlockUtils.isAir(pos.func_177984_a().func_177974_f()) && BlockUtils.isAir(pos.func_177984_a().func_177976_e())) {
/* 72 */       return (!BlockUtils.isAir(pos.func_177978_c()) && !BlockUtils.isAir(pos.func_177968_d()) && !BlockUtils.isAir(pos.func_177984_a().func_177978_c()) && !BlockUtils.isAir(pos.func_177984_a().func_177968_d()));
/*    */     }
/* 74 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean one_one(BlockPos pos) {
/* 79 */     if (this.tunnelbp.contains(pos)) return false; 
/* 80 */     if (!BlockUtils.isAir(pos)) return false; 
/* 81 */     if (BlockUtils.isAir(pos.func_177977_b()) || BlockUtils.isAir(pos.func_177984_a())) return false; 
/* 82 */     if (BlockUtils.isAir(pos.func_177978_c()) && BlockUtils.isAir(pos.func_177968_d()) && BlockUtils.isAir(pos.func_177984_a().func_177978_c()) && BlockUtils.isAir(pos.func_177984_a().func_177968_d())) {
/* 83 */       return (!BlockUtils.isAir(pos.func_177974_f()) && !BlockUtils.isAir(pos.func_177976_e()) && !BlockUtils.isAir(pos.func_177984_a().func_177974_f()) && !BlockUtils.isAir(pos.func_177984_a().func_177976_e()));
/*    */     }
/* 85 */     if (BlockUtils.isAir(pos.func_177974_f()) && BlockUtils.isAir(pos.func_177976_e()) && BlockUtils.isAir(pos.func_177984_a().func_177974_f()) && BlockUtils.isAir(pos.func_177984_a().func_177976_e())) {
/* 86 */       return (!BlockUtils.isAir(pos.func_177978_c()) && !BlockUtils.isAir(pos.func_177968_d()) && !BlockUtils.isAir(pos.func_177984_a().func_177978_c()) && !BlockUtils.isAir(pos.func_177984_a().func_177968_d()));
/*    */     }
/* 88 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\TunnelESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */