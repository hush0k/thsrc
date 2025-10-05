/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.BlockRenderEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityRenderer;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.TessellatorUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Search
/*     */   extends Module
/*     */ {
/*  28 */   public static CopyOnWriteArrayList<BlockVec> blocks = new CopyOnWriteArrayList<>();
/*  29 */   public static ArrayList<Block> defaultBlocks = new ArrayList<>();
/*  30 */   public Setting<Boolean> softReload = register(new Setting("SoftReload", Boolean.valueOf(true)));
/*  31 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(100.0F), Float.valueOf(1.0F), Float.valueOf(500.0F)));
/*  32 */   private final Setting<ColorSetting> color = register(new Setting("Color", new ColorSetting(-16711681)));
/*  33 */   private final Setting<Boolean> illegals = register(new Setting("Illegals", Boolean.valueOf(true)));
/*  34 */   private final Setting<Boolean> tracers = register(new Setting("Tracers", Boolean.valueOf(false)));
/*  35 */   private final Setting<Boolean> fill = register(new Setting("Fill", Boolean.valueOf(true)));
/*  36 */   private final Setting<Boolean> outline = register(new Setting("Outline", Boolean.valueOf(true)));
/*     */ 
/*     */   
/*     */   public Search() {
/*  40 */     super("Search", "подсветка блоков", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   public static void doSoftReload() {
/*  44 */     if (mc.field_71441_e != null && mc.field_71439_g != null) {
/*  45 */       int posX = (int)mc.field_71439_g.field_70165_t;
/*  46 */       int posY = (int)mc.field_71439_g.field_70163_u;
/*  47 */       int posZ = (int)mc.field_71439_g.field_70161_v;
/*  48 */       int range = mc.field_71474_y.field_151451_c * 16;
/*  49 */       mc.field_71438_f.func_147585_a(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderTracer(double x, double y, double z, double x2, double y2, double z2, int color) {
/*  54 */     GL11.glPushAttrib(1048575);
/*  55 */     GL11.glBlendFunc(770, 771);
/*  56 */     GL11.glEnable(3042);
/*  57 */     GL11.glLineWidth(1.5F);
/*  58 */     GL11.glDisable(3553);
/*  59 */     GL11.glDisable(2929);
/*  60 */     GL11.glDepthMask(false);
/*  61 */     GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
/*  62 */     GlStateManager.func_179140_f();
/*  63 */     GL11.glLoadIdentity();
/*  64 */     ((IEntityRenderer)mc.field_71460_t).orientCam(mc.func_184121_ak());
/*  65 */     GL11.glEnable(2848);
/*  66 */     GL11.glBegin(1);
/*  67 */     GL11.glVertex3d(x, y, z);
/*  68 */     GL11.glVertex3d(x2, y2, z2);
/*  69 */     GL11.glVertex3d(x2, y2, z2);
/*  70 */     GL11.glEnd();
/*  71 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  75 */     if (((Boolean)this.softReload.getValue()).booleanValue()) {
/*  76 */       doSoftReload();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onBlockRender(BlockRenderEvent event) {
/*  82 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/*  83 */       return;  if (blocks.size() > 100000) {
/*  84 */       blocks.clear();
/*     */     }
/*  86 */     if (shouldAdd(event.getBlock(), event.getPos())) {
/*  87 */       BlockVec vec = new BlockVec(event.getPos().func_177958_n(), event.getPos().func_177956_o(), event.getPos().func_177952_p());
/*  88 */       if (!blocks.contains(vec)) {
/*  89 */         blocks.add(vec);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*  97 */     if (mc.field_71441_e == null || mc.field_71439_g == null || blocks.isEmpty())
/*     */       return; 
/*  99 */     if (((Boolean)this.fill.getValue()).booleanValue() || ((Boolean)this.outline.getValue()).booleanValue()) {
/* 100 */       for (BlockVec vec : blocks) {
/* 101 */         if (vec.getDistance(new BlockVec(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)) > ((Float)this.range.getValue()).floatValue() || !shouldRender(vec)) {
/* 102 */           blocks.remove(vec);
/*     */           
/*     */           continue;
/*     */         } 
/* 106 */         BlockPos pos = new BlockPos(vec.x, vec.y, vec.z);
/*     */         
/* 108 */         AxisAlignedBB axisAlignedBB = mc.field_71441_e.func_180495_p(pos).func_185900_c((IBlockAccess)mc.field_71441_e, pos).func_186670_a(pos);
/*     */         
/* 110 */         if (((Boolean)this.fill.getValue()).booleanValue()) {
/* 111 */           TessellatorUtil.prepare();
/* 112 */           TessellatorUtil.drawBox(axisAlignedBB, ((ColorSetting)this.color.getValue()).getColorObject());
/* 113 */           TessellatorUtil.release();
/*     */         } 
/*     */         
/* 116 */         if (((Boolean)this.outline.getValue()).booleanValue()) {
/* 117 */           TessellatorUtil.prepare();
/* 118 */           TessellatorUtil.drawBoundingBox(axisAlignedBB, 1.5D, ((ColorSetting)this.color.getValue()).getColorObject());
/* 119 */           TessellatorUtil.release();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 124 */     if (((Boolean)this.tracers.getValue()).booleanValue()) {
/* 125 */       for (BlockVec vec : blocks) {
/* 126 */         if (vec.getDistance(new BlockVec(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)) > ((Float)this.range.getValue()).floatValue() || !shouldRender(vec)) {
/* 127 */           blocks.remove(vec);
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */         
/* 134 */         Vec3d eyes = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians(mc.field_71439_g.field_70125_A))).func_178785_b(
/* 135 */             -((float)Math.toRadians(mc.field_71439_g.field_70177_z)));
/*     */         
/* 137 */         renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, vec.x - ((IRenderManager)mc
/* 138 */             .func_175598_ae()).getRenderPosX() + 0.5D, vec.y - ((IRenderManager)mc
/* 139 */             .func_175598_ae()).getRenderPosY() + 0.5D, vec.z - ((IRenderManager)mc
/* 140 */             .func_175598_ae()).getRenderPosZ() + 0.5D, ((ColorSetting)this.color
/* 141 */             .getValue()).getColor());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean shouldAdd(Block block, BlockPos pos) {
/* 147 */     if (defaultBlocks.contains(block)) {
/* 148 */       return true;
/*     */     }
/* 150 */     if (((Boolean)this.illegals.getValue()).booleanValue()) {
/* 151 */       return isIllegal(block, pos);
/*     */     }
/* 153 */     return false;
/*     */   }
/*     */   
/*     */   private boolean shouldRender(BlockVec vec) {
/* 157 */     if (defaultBlocks.contains(mc.field_71441_e.func_180495_p(new BlockPos(vec.x, vec.y, vec.z)).func_177230_c())) {
/* 158 */       return true;
/*     */     }
/*     */     
/* 161 */     if (((Boolean)this.illegals.getValue()).booleanValue()) {
/* 162 */       return isIllegal(mc.field_71441_e.func_180495_p(new BlockPos(vec.x, vec.y, vec.z)).func_177230_c(), new BlockPos(vec.x, vec.y, vec.z));
/*     */     }
/*     */     
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isIllegal(Block block, BlockPos pos) {
/* 169 */     if (block instanceof net.minecraft.block.BlockCommandBlock || block instanceof net.minecraft.block.BlockBarrier) return true;
/*     */     
/* 171 */     if (block == Blocks.field_150357_h) {
/* 172 */       if (mc.field_71439_g.field_71093_bK == 0)
/* 173 */         return (pos.func_177956_o() > 4); 
/* 174 */       if (mc.field_71439_g.field_71093_bK == -1) {
/* 175 */         return (pos.func_177956_o() > 127 || (pos.func_177956_o() < 123 && pos.func_177956_o() > 4));
/*     */       }
/* 177 */       return true;
/*     */     } 
/*     */     
/* 180 */     return false;
/*     */   }
/*     */   
/*     */   private static class BlockVec {
/*     */     public final double x;
/*     */     public final double y;
/*     */     public final double z;
/*     */     
/*     */     public BlockVec(double x, double y, double z) {
/* 189 */       this.x = x;
/* 190 */       this.y = y;
/* 191 */       this.z = z;
/*     */     }
/*     */     
/*     */     public boolean equals(Object object) {
/* 195 */       if (object instanceof BlockVec) {
/* 196 */         BlockVec v = (BlockVec)object;
/* 197 */         return (Double.compare(this.x, v.x) == 0 && Double.compare(this.y, v.y) == 0 && Double.compare(this.z, v.z) == 0);
/*     */       } 
/* 199 */       return super.equals(object);
/*     */     }
/*     */     
/*     */     public double getDistance(BlockVec v) {
/* 203 */       double dx = this.x - v.x;
/* 204 */       double dy = this.y - v.y;
/* 205 */       double dz = this.z - v.z;
/*     */       
/* 207 */       return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\Search.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */