/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.CrystalUtils;
/*     */ import com.mrzak34.thunderhack.util.TessellatorUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HoleESP
/*     */   extends Module
/*     */ {
/*  38 */   private final Setting<Integer> rangeXZ = register(new Setting("RangeXZ", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(25)));
/*  39 */   private final Setting<Integer> rangeY = register(new Setting("RangeY", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(25)));
/*     */   
/*  41 */   private final Setting<Float> width = register(new Setting("Width", Float.valueOf(1.5F), Float.valueOf(0.0F), Float.valueOf(10.0F)));
/*  42 */   private final Setting<Float> height = register(new Setting("Height", Float.valueOf(1.0F), Float.valueOf(-2.0F), Float.valueOf(8.0F)));
/*     */   
/*  44 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.FULL));
/*  45 */   private final Setting<Integer> fadeAlpha = register(new Setting("FadeAlpha", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(255), v -> (this.mode.getValue() == Mode.FADE)));
/*  46 */   private final Setting<Boolean> depth = register(new Setting("Depth", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.FADE)));
/*  47 */   private final Setting<Boolean> noLineDepth = register(new Setting("NotLines", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.FADE && ((Boolean)this.depth.getValue()).booleanValue())));
/*  48 */   private final Setting<Lines> lines = register(new Setting("Lines", Lines.BOTTOM, v -> (this.mode.getValue() == Mode.FADE)));
/*  49 */   private final Setting<Boolean> sides = register(new Setting("Sides", Boolean.valueOf(false), v -> (this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.FADE)));
/*  50 */   private final Setting<Boolean> notSelf = register(new Setting("NotSelf", Boolean.valueOf(true), v -> (this.mode.getValue() == Mode.FADE)));
/*  51 */   private final Setting<Boolean> twoBlock = register(new Setting("TwoBlock", Boolean.valueOf(false)));
/*  52 */   private final Setting<Boolean> bedrock = register(new Setting("Bedrock", Boolean.valueOf(true)));
/*  53 */   private final Setting<Boolean> obsidian = register(new Setting("Obsidian", Boolean.valueOf(true)));
/*  54 */   private final Setting<Boolean> vunerable = register(new Setting("Vulnerable", Boolean.valueOf(false)));
/*  55 */   private final Setting<Boolean> selfVunerable = register(new Setting("Self", Boolean.valueOf(false)));
/*  56 */   private final Setting<ColorSetting> bRockHoleColor = register(new Setting("bRockHoleColor", new ColorSetting(-2013200640)));
/*  57 */   private final Setting<ColorSetting> bRockLineColor = register(new Setting("bRockLineColor", new ColorSetting(-1996554240)));
/*  58 */   private final Setting<ColorSetting> obiHoleColor = register(new Setting("obiHoleColor", new ColorSetting(-2013200640)));
/*  59 */   private final Setting<ColorSetting> obiLineHoleColor = register(new Setting("obiLineHoleColor", new ColorSetting(-65536)));
/*  60 */   private final Setting<ColorSetting> vunerableColor = register(new Setting("vunerableColor", new ColorSetting(1727987967)));
/*  61 */   private final Setting<ColorSetting> vunerableLineColor = register(new Setting("vunerableLineColor", new ColorSetting(-65281)));
/*  62 */   private final List<BlockPos> obiHoles = new ArrayList<>();
/*  63 */   private final List<BlockPos> bedrockHoles = new ArrayList<>();
/*  64 */   private final List<TwoBlockHole> obiHolesTwoBlock = new ArrayList<>();
/*  65 */   private final List<TwoBlockHole> bedrockHolesTwoBlock = new ArrayList<>();
/*     */   
/*     */   public HoleESP() {
/*  68 */     super("HoleESP", "рендерить безопасные-холки", Module.Category.RENDER);
/*     */   }
/*     */   
/*     */   public static void prepareGL2() {
/*  72 */     GL11.glPushAttrib(1048575);
/*     */     
/*  74 */     GL11.glPushMatrix();
/*     */     
/*  76 */     GL11.glDisable(3008);
/*  77 */     GL11.glEnable(3042);
/*     */     
/*  79 */     GL11.glBlendFunc(770, 771);
/*     */     
/*  81 */     GL11.glDisable(3553);
/*  82 */     GL11.glDisable(2929);
/*     */     
/*  84 */     GL11.glDepthMask(false);
/*     */ 
/*     */     
/*  87 */     GL11.glEnable(2884);
/*     */     
/*  89 */     GL11.glEnable(2848);
/*     */     
/*  91 */     GL11.glHint(3154, 4353);
/*     */     
/*  93 */     GL11.glDisable(2896);
/*     */   }
/*     */   
/*     */   public static void releaseGL2() {
/*  97 */     GL11.glEnable(2896);
/*  98 */     GL11.glDisable(2848);
/*  99 */     GL11.glEnable(3553);
/* 100 */     GL11.glEnable(2929);
/* 101 */     GL11.glDisable(3042);
/* 102 */     GL11.glEnable(3008);
/*     */     
/* 104 */     GL11.glDepthMask(true);
/*     */     
/* 106 */     GL11.glCullFace(1029);
/*     */     
/* 108 */     GL11.glPopMatrix();
/* 109 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   public static void drawWireframe2(AxisAlignedBB axisAlignedBB, int color, float lineWidth) {
/* 113 */     GL11.glPushMatrix();
/*     */     
/* 115 */     GL11.glEnable(3042);
/*     */     
/* 117 */     GL11.glBlendFunc(770, 771);
/*     */     
/* 119 */     GL11.glDisable(2896);
/* 120 */     GL11.glDisable(3553);
/* 121 */     GL11.glEnable(2848);
/* 122 */     GL11.glDisable(2929);
/* 123 */     GL11.glDepthMask(false);
/*     */     
/* 125 */     GL11.glLineWidth(lineWidth);
/*     */     
/* 127 */     GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, (color >> 24 & 0xFF) / 255.0F);
/*     */     
/* 129 */     if (axisAlignedBB == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 134 */     GL11.glBegin(1);
/* 135 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 136 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 137 */     GL11.glEnd();
/*     */     
/* 139 */     GL11.glBegin(1);
/* 140 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 141 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 142 */     GL11.glEnd();
/*     */     
/* 144 */     GL11.glBegin(1);
/* 145 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 146 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 147 */     GL11.glEnd();
/*     */     
/* 149 */     GL11.glBegin(1);
/* 150 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 151 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 152 */     GL11.glEnd();
/*     */ 
/*     */     
/* 155 */     GL11.glBegin(1);
/* 156 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 157 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 158 */     GL11.glEnd();
/*     */     
/* 160 */     GL11.glBegin(1);
/* 161 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 162 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 163 */     GL11.glEnd();
/*     */     
/* 165 */     GL11.glBegin(1);
/* 166 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 167 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 168 */     GL11.glEnd();
/*     */     
/* 170 */     GL11.glBegin(1);
/* 171 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 172 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 173 */     GL11.glEnd();
/*     */     
/* 175 */     GL11.glBegin(1);
/* 176 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 177 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 178 */     GL11.glEnd();
/*     */     
/* 180 */     GL11.glBegin(1);
/* 181 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 182 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 183 */     GL11.glEnd();
/*     */     
/* 185 */     GL11.glBegin(1);
/* 186 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 187 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 188 */     GL11.glEnd();
/*     */     
/* 190 */     GL11.glBegin(1);
/* 191 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 192 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 193 */     GL11.glEnd();
/*     */     
/* 195 */     GL11.glBegin(1);
/* 196 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 197 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 198 */     GL11.glEnd();
/*     */     
/* 200 */     GL11.glBegin(1);
/* 201 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 202 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 203 */     GL11.glEnd();
/*     */     
/* 205 */     GL11.glBegin(1);
/* 206 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 207 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 208 */     GL11.glEnd();
/*     */     
/* 210 */     GL11.glBegin(1);
/* 211 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 212 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 213 */     GL11.glEnd();
/*     */     
/* 215 */     GL11.glBegin(1);
/* 216 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 217 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 218 */     GL11.glEnd();
/*     */     
/* 220 */     GL11.glBegin(1);
/* 221 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 222 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 223 */     GL11.glEnd();
/*     */     
/* 225 */     GL11.glBegin(1);
/* 226 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 227 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 228 */     GL11.glEnd();
/*     */     
/* 230 */     GL11.glBegin(1);
/* 231 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 232 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 233 */     GL11.glEnd();
/*     */     
/* 235 */     GL11.glBegin(1);
/* 236 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 237 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 238 */     GL11.glEnd();
/*     */     
/* 240 */     GL11.glBegin(1);
/* 241 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 242 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 243 */     GL11.glEnd();
/*     */     
/* 245 */     GL11.glBegin(1);
/* 246 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 247 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 248 */     GL11.glEnd();
/*     */     
/* 250 */     GL11.glBegin(1);
/* 251 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 252 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 253 */     GL11.glEnd();
/*     */     
/* 255 */     GL11.glBegin(1);
/* 256 */     GL11.glVertex3d(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c);
/* 257 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f);
/* 258 */     GL11.glEnd();
/*     */     
/* 260 */     GL11.glBegin(1);
/* 261 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c);
/* 262 */     GL11.glVertex3d(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f);
/* 263 */     GL11.glEnd();
/*     */     
/* 265 */     GL11.glLineWidth(1.0F);
/*     */     
/* 267 */     GL11.glDisable(2848);
/* 268 */     GL11.glEnable(3553);
/* 269 */     GL11.glEnable(2896);
/* 270 */     GL11.glEnable(2929);
/*     */     
/* 272 */     GL11.glDepthMask(true);
/*     */     
/* 274 */     GL11.glDisable(3042);
/* 275 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<BlockPos> getVulnerablePositions(BlockPos root) {
/* 287 */     ArrayList<BlockPos> vP = new ArrayList<>();
/* 288 */     if (!(mc.field_71441_e.func_180495_p(root).func_177230_c() instanceof net.minecraft.block.BlockAir)) {
/* 289 */       return vP;
/*     */     }
/* 291 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 292 */       if (mc.field_71441_e.func_180495_p(root.func_177972_a(facing)).func_177230_c() instanceof net.minecraft.block.BlockAir) return new ArrayList<>(); 
/* 293 */       if (mc.field_71441_e.func_180495_p(root.func_177972_a(facing)).func_177230_c() instanceof net.minecraft.block.BlockObsidian)
/* 294 */         if (CrystalUtils.canPlaceCrystal(root.func_177967_a(facing, 2).func_177977_b()) && mc.field_71441_e.func_180495_p(root.func_177972_a(facing)).func_177230_c() != Blocks.field_150350_a) {
/* 295 */           vP.add(root.func_177972_a(facing));
/* 296 */         } else if (CrystalUtils.canPlaceCrystal(root.func_177972_a(facing)) && mc.field_71441_e.func_180495_p(root.func_177972_a(facing)).func_177230_c() != Blocks.field_150350_a && (mc.field_71441_e
/* 297 */           .func_180495_p(root.func_177972_a(facing).func_177977_b()).func_177230_c() == Blocks.field_150357_h || mc.field_71441_e.func_180495_p(root.func_177972_a(facing).func_177977_b()).func_177230_c() == Blocks.field_150343_Z)) {
/*     */           
/* 299 */           vP.add(root.func_177972_a(facing));
/*     */         }  
/*     */     } 
/* 302 */     return vP;
/*     */   }
/*     */   
/*     */   public static void drawBoxOutline(AxisAlignedBB box, float red, float green, float blue, float alpha) {
/* 306 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 307 */     BufferBuilder buffer = tessellator.func_178180_c();
/* 308 */     buffer.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/*     */     
/* 310 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 311 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 312 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 313 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 314 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 315 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 316 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 317 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 318 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 319 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 320 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 321 */     buffer.func_181662_b(box.field_72340_a, box.field_72337_e, box.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 322 */     buffer.func_181662_b(box.field_72340_a, box.field_72338_b, box.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 323 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72334_f).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 324 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 325 */     buffer.func_181662_b(box.field_72336_d, box.field_72337_e, box.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 326 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 327 */     buffer.func_181662_b(box.field_72336_d, box.field_72338_b, box.field_72339_c).func_181666_a(red, green, blue, 0.0F).func_181675_d();
/* 328 */     tessellator.func_78381_a();
/*     */   }
/*     */   
/*     */   public static void drawBoundingBox(AxisAlignedBB bb, Color color) {
/* 332 */     AxisAlignedBB boundingBox = bb.func_72317_d(-(mc.func_175598_ae()).field_78730_l, -(mc.func_175598_ae()).field_78731_m, -(mc.func_175598_ae()).field_78728_n);
/*     */     
/* 334 */     drawBoxOutline(boundingBox.func_186662_g(0.0020000000949949026D), (color.getRed() * 255), (color.getGreen() * 255), (color.getBlue() * 255), (color.getAlpha() * 255));
/*     */   }
/*     */   
/*     */   public static void beginRender() {
/* 338 */     GL11.glBlendFunc(770, 771);
/* 339 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 340 */     GlStateManager.func_179147_l();
/* 341 */     GlStateManager.func_179140_f();
/* 342 */     GlStateManager.func_179129_p();
/* 343 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void endRender() {
/* 351 */     GlStateManager.func_179117_G();
/* 352 */     GlStateManager.func_179089_o();
/* 353 */     GlStateManager.func_179145_e();
/* 354 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 359 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/* 360 */       return;  this.obiHoles.clear();
/* 361 */     this.bedrockHoles.clear();
/* 362 */     this.obiHolesTwoBlock.clear();
/* 363 */     this.bedrockHolesTwoBlock.clear();
/* 364 */     Iterable<BlockPos> blocks = BlockPos.func_177980_a(mc.field_71439_g.func_180425_c().func_177982_a(-((Integer)this.rangeXZ.getValue()).intValue(), -((Integer)this.rangeY.getValue()).intValue(), -((Integer)this.rangeXZ.getValue()).intValue()), mc.field_71439_g.func_180425_c().func_177982_a(((Integer)this.rangeXZ.getValue()).intValue(), ((Integer)this.rangeY.getValue()).intValue(), ((Integer)this.rangeXZ.getValue()).intValue()));
/*     */     
/* 366 */     for (BlockPos pos : blocks) {
/*     */       
/* 368 */       if (!mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76230_c() || 
/* 369 */         !mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a().func_76230_c() || 
/* 370 */         !mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_185904_a().func_76230_c()) {
/*     */ 
/*     */ 
/*     */         
/* 374 */         if (BlockUtils.validObi(pos) && ((Boolean)this.obsidian.getValue()).booleanValue()) {
/* 375 */           this.obiHoles.add(pos);
/*     */         } else {
/* 377 */           BlockPos blockPos = BlockUtils.validTwoBlockObiXZ(pos);
/* 378 */           if (blockPos != null && ((Boolean)this.obsidian.getValue()).booleanValue() && ((Boolean)this.twoBlock.getValue()).booleanValue()) {
/* 379 */             this.obiHolesTwoBlock.add(new TwoBlockHole(pos, pos.func_177982_a(blockPos.func_177958_n(), blockPos.func_177956_o(), blockPos.func_177952_p())));
/*     */           }
/*     */         } 
/*     */         
/* 383 */         if (BlockUtils.validBedrock(pos) && ((Boolean)this.bedrock.getValue()).booleanValue()) {
/* 384 */           this.bedrockHoles.add(pos); continue;
/*     */         } 
/* 386 */         BlockPos validTwoBlock = BlockUtils.validTwoBlockBedrockXZ(pos);
/* 387 */         if (validTwoBlock != null && ((Boolean)this.bedrock.getValue()).booleanValue() && ((Boolean)this.twoBlock.getValue()).booleanValue()) {
/* 388 */           this.bedrockHolesTwoBlock.add(new TwoBlockHole(pos, pos.func_177982_a(validTwoBlock.func_177958_n(), validTwoBlock.func_177956_o(), validTwoBlock.func_177952_p())));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/* 400 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/* 401 */       return;  GL11.glPushAttrib(1048575);
/* 402 */     if (this.mode.getValue() == Mode.BOTTOM) {
/* 403 */       GlStateManager.func_179094_E();
/* 404 */       beginRender();
/* 405 */       GlStateManager.func_179147_l();
/* 406 */       GlStateManager.func_187441_d(5.0F);
/* 407 */       GlStateManager.func_179090_x();
/* 408 */       GlStateManager.func_179132_a(false);
/* 409 */       GlStateManager.func_179097_i();
/*     */       
/* 411 */       GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */       
/* 413 */       for (BlockPos pos : this.bedrockHoles) {
/* 414 */         AxisAlignedBB box = new AxisAlignedBB(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), (pos.func_177958_n() + 1), pos.func_177956_o(), (pos.func_177952_p() + 1));
/*     */         
/* 416 */         drawBoundingBox(box, ((ColorSetting)this.bRockHoleColor.getValue()).getColorObject());
/*     */       } 
/*     */       
/* 419 */       for (BlockPos pos : this.obiHoles) {
/* 420 */         AxisAlignedBB box = new AxisAlignedBB(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), (pos.func_177958_n() + 1), pos.func_177956_o(), (pos.func_177952_p() + 1));
/*     */         
/* 422 */         drawBoundingBox(box, ((ColorSetting)this.obiHoleColor.getValue()).getColorObject());
/*     */       } 
/*     */       
/* 425 */       for (TwoBlockHole pos : this.bedrockHolesTwoBlock) {
/* 426 */         AxisAlignedBB box = new AxisAlignedBB(pos.getOne().func_177958_n(), pos.getOne().func_177956_o(), pos.getOne().func_177952_p(), (pos.getExtra().func_177958_n() + 1), pos.getExtra().func_177956_o(), (pos.getExtra().func_177952_p() + 1));
/*     */         
/* 428 */         drawBoundingBox(box, ((ColorSetting)this.bRockHoleColor.getValue()).getColorObject());
/*     */       } 
/*     */       
/* 431 */       for (TwoBlockHole pos : this.obiHolesTwoBlock) {
/* 432 */         AxisAlignedBB box = new AxisAlignedBB(pos.getOne().func_177958_n(), pos.getOne().func_177956_o(), pos.getOne().func_177952_p(), (pos.getExtra().func_177958_n() + 1), pos.getExtra().func_177956_o(), (pos.getExtra().func_177952_p() + 1));
/*     */         
/* 434 */         drawBoundingBox(box, ((ColorSetting)this.obiHoleColor.getValue()).getColorObject());
/*     */       } 
/*     */       
/* 437 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 438 */       GlStateManager.func_179126_j();
/* 439 */       GlStateManager.func_179132_a(true);
/* 440 */       GlStateManager.func_179098_w();
/* 441 */       GlStateManager.func_179084_k();
/* 442 */       endRender();
/* 443 */       GlStateManager.func_179121_F();
/*     */     } else {
/* 445 */       for (BlockPos pos : this.bedrockHoles) {
/* 446 */         drawHole(pos, (ColorSetting)this.bRockHoleColor.getValue(), (ColorSetting)this.bRockLineColor.getValue());
/*     */       }
/*     */       
/* 449 */       for (BlockPos pos : this.obiHoles) {
/* 450 */         drawHole(pos, (ColorSetting)this.obiHoleColor.getValue(), (ColorSetting)this.obiLineHoleColor.getValue());
/*     */       }
/*     */       
/* 453 */       for (TwoBlockHole pos : this.bedrockHolesTwoBlock) {
/* 454 */         drawHoleTwoBlock(pos.getOne(), pos.getExtra(), (ColorSetting)this.bRockHoleColor.getValue(), (ColorSetting)this.bRockLineColor.getValue());
/*     */       }
/*     */       
/* 457 */       for (TwoBlockHole pos : this.obiHolesTwoBlock) {
/* 458 */         drawHoleTwoBlock(pos.getOne(), pos.getExtra(), (ColorSetting)this.obiHoleColor.getValue(), (ColorSetting)this.obiLineHoleColor.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 462 */     if (((Boolean)this.vunerable.getValue()).booleanValue()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 470 */       List<Entity> targetsInRange = (List<Entity>)mc.field_71441_e.field_72996_f.stream().filter(e -> e instanceof net.minecraft.entity.player.EntityPlayer).filter(e -> (e.func_70032_d((Entity)mc.field_71439_g) < ((Integer)this.rangeXZ.getValue()).intValue())).filter(e -> (e != mc.field_71439_g || ((Boolean)this.selfVunerable.getValue()).booleanValue())).filter(e -> !Thunderhack.friendManager.isFriend(e.func_70005_c_())).sorted(Comparator.comparing(e -> Float.valueOf(mc.field_71439_g.func_70032_d(e)))).collect(Collectors.toList());
/*     */       
/* 472 */       for (Entity target : targetsInRange) {
/* 473 */         ArrayList<BlockPos> vuns = getVulnerablePositions(new BlockPos(target));
/*     */         
/* 475 */         for (BlockPos pos : vuns) {
/* 476 */           AxisAlignedBB axisAlignedBB = mc.field_71441_e.func_180495_p(pos).func_185900_c((IBlockAccess)mc.field_71441_e, pos).func_186670_a(pos);
/* 477 */           TessellatorUtil.prepare();
/* 478 */           TessellatorUtil.drawBox(axisAlignedBB, true, 1.0D, ((ColorSetting)this.vunerableColor.getValue()).getColorObject(), ((ColorSetting)this.vunerableColor.getValue()).getAlpha(), 63);
/* 479 */           TessellatorUtil.drawBoundingBox(axisAlignedBB, ((Float)this.width.getValue()).floatValue(), ((ColorSetting)this.vunerableLineColor.getValue()).getColorObject());
/* 480 */           TessellatorUtil.release();
/*     */         } 
/*     */       } 
/*     */     } 
/* 484 */     GL11.glPopAttrib();
/*     */   }
/*     */   
/*     */   public void drawHole(BlockPos pos, ColorSetting color, ColorSetting lineColor) {
/* 488 */     AxisAlignedBB axisAlignedBB = mc.field_71441_e.func_180495_p(pos).func_185900_c((IBlockAccess)mc.field_71441_e, pos).func_186670_a(pos);
/*     */     
/* 490 */     axisAlignedBB = axisAlignedBB.func_186666_e(axisAlignedBB.field_72338_b + ((Float)this.height.getValue()).floatValue());
/*     */     
/* 492 */     if (this.mode.getValue() == Mode.FULL) {
/* 493 */       TessellatorUtil.prepare();
/* 494 */       TessellatorUtil.drawBox(axisAlignedBB, true, 1.0D, color.getColorObject(), color.getAlpha(), ((Boolean)this.sides.getValue()).booleanValue() ? 60 : 63);
/* 495 */       TessellatorUtil.release();
/*     */     } 
/*     */     
/* 498 */     if (this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.OUTLINE) {
/* 499 */       TessellatorUtil.prepare();
/* 500 */       TessellatorUtil.drawBoundingBox(axisAlignedBB, ((Float)this.width.getValue()).floatValue(), lineColor.getColorObject());
/* 501 */       TessellatorUtil.release();
/*     */     } 
/*     */     
/* 504 */     if (this.mode.getValue() == Mode.WIREFRAME) {
/* 505 */       prepareGL2();
/* 506 */       drawWireframe2(axisAlignedBB.func_72317_d(-((IRenderManager)mc.func_175598_ae()).getRenderPosX(), -((IRenderManager)mc.func_175598_ae()).getRenderPosY(), -((IRenderManager)mc.func_175598_ae()).getRenderPosZ()), lineColor.getColor(), ((Float)this.width.getValue()).floatValue());
/* 507 */       releaseGL2();
/*     */     } 
/*     */     
/* 510 */     if (this.mode.getValue() == Mode.FADE) {
/* 511 */       AxisAlignedBB tBB = mc.field_71441_e.func_180495_p(pos).func_185900_c((IBlockAccess)mc.field_71441_e, pos).func_186670_a(pos);
/* 512 */       tBB = tBB.func_186666_e(tBB.field_72338_b + ((Float)this.height.getValue()).floatValue());
/*     */       
/* 514 */       if (mc.field_71439_g.func_174813_aQ() != null && tBB.func_72326_a(mc.field_71439_g.func_174813_aQ()) && ((Boolean)this.notSelf.getValue()).booleanValue()) {
/* 515 */         tBB = tBB.func_186666_e(Math.min(tBB.field_72337_e, mc.field_71439_g.field_70163_u + 1.0D));
/*     */       }
/*     */       
/* 518 */       TessellatorUtil.prepare();
/* 519 */       if (((Boolean)this.depth.getValue()).booleanValue()) {
/* 520 */         GlStateManager.func_179126_j();
/* 521 */         tBB = tBB.func_186664_h(0.01D);
/*     */       } 
/* 523 */       TessellatorUtil.drawBox(tBB, true, ((Float)this.height.getValue()).floatValue(), color.getColorObject(), ((Integer)this.fadeAlpha.getValue()).intValue(), ((Boolean)this.sides.getValue()).booleanValue() ? 60 : 63);
/* 524 */       if (((Float)this.width.getValue()).floatValue() >= 0.1F) {
/* 525 */         if (this.lines.getValue() == Lines.BOTTOM) {
/* 526 */           tBB = new AxisAlignedBB(tBB.field_72340_a, tBB.field_72338_b, tBB.field_72339_c, tBB.field_72336_d, tBB.field_72338_b, tBB.field_72334_f);
/* 527 */         } else if (this.lines.getValue() == Lines.TOP) {
/* 528 */           tBB = new AxisAlignedBB(tBB.field_72340_a, tBB.field_72337_e, tBB.field_72339_c, tBB.field_72336_d, tBB.field_72337_e, tBB.field_72334_f);
/*     */         } 
/* 530 */         if (((Boolean)this.noLineDepth.getValue()).booleanValue()) {
/* 531 */           GlStateManager.func_179097_i();
/*     */         }
/* 533 */         TessellatorUtil.drawBoundingBox(tBB, ((Float)this.width.getValue()).floatValue(), lineColor.getColorObject(), ((Integer)this.fadeAlpha.getValue()).intValue());
/*     */       } 
/* 535 */       TessellatorUtil.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawHoleTwoBlock(BlockPos pos, BlockPos two, ColorSetting color, ColorSetting lineColor) {
/* 540 */     AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), (two.func_177958_n() + 1), (two.func_177956_o() + ((Float)this.height.getValue()).floatValue()), (two.func_177952_p() + 1));
/*     */     
/* 542 */     if (this.mode.getValue() == Mode.FULL) {
/* 543 */       TessellatorUtil.prepare();
/* 544 */       TessellatorUtil.drawBox(axisAlignedBB, true, 1.0D, color.getColorObject(), color.getAlpha(), ((Boolean)this.sides.getValue()).booleanValue() ? 60 : 63);
/* 545 */       TessellatorUtil.release();
/*     */     } 
/*     */     
/* 548 */     if (this.mode.getValue() == Mode.FULL || this.mode.getValue() == Mode.OUTLINE) {
/* 549 */       TessellatorUtil.prepare();
/* 550 */       TessellatorUtil.drawBoundingBox(axisAlignedBB, ((Float)this.width.getValue()).floatValue(), lineColor.getColorObject());
/* 551 */       TessellatorUtil.release();
/*     */     } 
/*     */     
/* 554 */     if (this.mode.getValue() == Mode.WIREFRAME) {
/* 555 */       prepareGL2();
/* 556 */       drawWireframe2(axisAlignedBB.func_72317_d(-((IRenderManager)mc.func_175598_ae()).getRenderPosX(), -((IRenderManager)mc.func_175598_ae()).getRenderPosY(), -((IRenderManager)mc.func_175598_ae()).getRenderPosZ()), lineColor.getColor(), ((Float)this.width.getValue()).floatValue());
/* 557 */       releaseGL2();
/*     */     } 
/*     */     
/* 560 */     if (this.mode.getValue() == Mode.FADE) {
/* 561 */       AxisAlignedBB tBB = new AxisAlignedBB(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), (two.func_177958_n() + 1), (two.func_177956_o() + ((Float)this.height.getValue()).floatValue()), (two.func_177952_p() + 1));
/*     */       
/* 563 */       if (tBB.func_72326_a(mc.field_71439_g.func_174813_aQ()) && ((Boolean)this.notSelf.getValue()).booleanValue()) {
/* 564 */         tBB = tBB.func_186666_e(Math.min(tBB.field_72337_e, mc.field_71439_g.field_70163_u + 1.0D));
/*     */       }
/*     */       
/* 567 */       TessellatorUtil.prepare();
/* 568 */       if (((Boolean)this.depth.getValue()).booleanValue()) {
/* 569 */         GlStateManager.func_179126_j();
/* 570 */         tBB = tBB.func_186664_h(0.01D);
/*     */       } 
/* 572 */       TessellatorUtil.drawBox(tBB, true, ((Float)this.height.getValue()).floatValue(), color.getColorObject(), ((Integer)this.fadeAlpha.getValue()).intValue(), ((Boolean)this.sides.getValue()).booleanValue() ? 60 : 63);
/* 573 */       if (((Float)this.width.getValue()).floatValue() >= 0.1F) {
/* 574 */         if (this.lines.getValue() == Lines.BOTTOM) {
/* 575 */           tBB = new AxisAlignedBB(tBB.field_72340_a, tBB.field_72338_b, tBB.field_72339_c, tBB.field_72336_d, tBB.field_72338_b, tBB.field_72334_f);
/* 576 */         } else if (this.lines.getValue() == Lines.TOP) {
/* 577 */           tBB = new AxisAlignedBB(tBB.field_72340_a, tBB.field_72337_e, tBB.field_72339_c, tBB.field_72336_d, tBB.field_72337_e, tBB.field_72334_f);
/*     */         } 
/* 579 */         if (((Boolean)this.noLineDepth.getValue()).booleanValue()) {
/* 580 */           GlStateManager.func_179097_i();
/*     */         }
/* 582 */         TessellatorUtil.drawBoundingBox(tBB, ((Float)this.width.getValue()).floatValue(), lineColor.getColorObject(), ((Integer)this.fadeAlpha.getValue()).intValue());
/*     */       } 
/* 584 */       TessellatorUtil.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum Lines {
/* 589 */     FULL, BOTTOM, TOP;
/*     */   }
/*     */   
/*     */   private enum Mode {
/* 593 */     BOTTOM,
/* 594 */     OUTLINE,
/* 595 */     FULL,
/* 596 */     WIREFRAME,
/* 597 */     FADE;
/*     */   }
/*     */   
/*     */   private static class TwoBlockHole
/*     */   {
/*     */     private final BlockPos one;
/*     */     private final BlockPos extra;
/*     */     
/*     */     public TwoBlockHole(BlockPos one, BlockPos extra) {
/* 606 */       this.one = one;
/* 607 */       this.extra = extra;
/*     */     }
/*     */     
/*     */     public BlockPos getOne() {
/* 611 */       return this.one;
/*     */     }
/*     */     
/*     */     public BlockPos getExtra() {
/* 615 */       return this.extra;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\HoleESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */