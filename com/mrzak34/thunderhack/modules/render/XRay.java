/*     */ package com.mrzak34.thunderhack.modules.render;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketMultiBlockChange;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class XRay extends Module {
/*     */   public static int done;
/*     */   public static int all;
/*  32 */   public Setting<Boolean> wh = register(new Setting("wallhack", Boolean.valueOf(false)));
/*  33 */   public Setting<Boolean> brutForce = register(new Setting("BrutForce", Boolean.valueOf(false)));
/*  34 */   public Setting<Integer> checkSpeed = register(new Setting("checkSpeed", Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(5), v -> ((Boolean)this.brutForce.getValue()).booleanValue()));
/*  35 */   public Setting<Integer> rxz = register(new Setting("Radius XZ", Integer.valueOf(20), Integer.valueOf(5), Integer.valueOf(200), v -> ((Boolean)this.brutForce.getValue()).booleanValue()));
/*  36 */   public Setting<Integer> ry = register(new Setting("Radius Y", Integer.valueOf(6), Integer.valueOf(2), Integer.valueOf(50), v -> ((Boolean)this.brutForce.getValue()).booleanValue()));
/*  37 */   public Setting<Boolean> diamond = register(new Setting("diamond ", Boolean.valueOf(false)));
/*  38 */   public Setting<Boolean> gold = register(new Setting("gold", Boolean.valueOf(false)));
/*  39 */   public Setting<Boolean> iron = register(new Setting("iron", Boolean.valueOf(false)));
/*  40 */   public Setting<Boolean> emerald = register(new Setting("emerald", Boolean.valueOf(false)));
/*  41 */   public Setting<Boolean> redstone = register(new Setting("redstone", Boolean.valueOf(false)));
/*  42 */   public Setting<Boolean> lapis = register(new Setting("lapis", Boolean.valueOf(false)));
/*  43 */   public Setting<Boolean> coal = register(new Setting("coal", Boolean.valueOf(false)));
/*  44 */   public Setting<Boolean> wow = register(new Setting("WowEffect", Boolean.valueOf(true), v -> ((Boolean)this.brutForce.getValue()).booleanValue()));
/*  45 */   public Setting<Boolean> water = register(new Setting("water", Boolean.valueOf(false)));
/*  46 */   public Setting<Boolean> lava = register(new Setting("lava", Boolean.valueOf(false)));
/*  47 */   ArrayList<BlockPos> ores = new ArrayList<>();
/*  48 */   ArrayList<BlockPos> toCheck = new ArrayList<>();
/*     */   BlockPos verycute;
/*  50 */   private final Setting<mode> Mode = register(new Setting("Render Mode", mode.FullBox));
/*     */   public XRay() {
/*  52 */     super("XRay", "Искать алмазы на ezzzzz", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  57 */     this.ores.clear();
/*  58 */     this.toCheck.clear();
/*  59 */     int radXZ = ((Integer)this.rxz.getValue()).intValue();
/*  60 */     int radY = ((Integer)this.ry.getValue()).intValue();
/*  61 */     ArrayList<BlockPos> blockPositions = getBlocks(radXZ, radY, radXZ);
/*  62 */     for (BlockPos pos : blockPositions) {
/*  63 */       IBlockState state = BlockUtils.getState(pos);
/*  64 */       if (!isCheckableOre(Block.func_149682_b(state.func_177230_c())))
/*  65 */         continue;  this.toCheck.add(pos);
/*     */     } 
/*  67 */     all = this.toCheck.size();
/*  68 */     done = 0;
/*  69 */     if (((Boolean)this.wh.getValue()).booleanValue() && ((Boolean)this.brutForce.getValue()).booleanValue()) {
/*  70 */       this.wh.setValue(Boolean.valueOf(false));
/*     */     }
/*  72 */     if (!((Boolean)this.brutForce.getValue()).booleanValue()) {
/*  73 */       mc.field_71438_f.func_72712_a();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  79 */     mc.field_71438_f.func_72712_a();
/*  80 */     super.onDisable();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPre(EventSync event) {
/*  85 */     for (int i = 0; i < ((Integer)this.checkSpeed.getValue()).intValue(); i++) {
/*  86 */       if (this.toCheck.size() < 1) {
/*     */         return;
/*     */       }
/*  89 */       BlockPos pos = this.toCheck.remove(0);
/*  90 */       done++;
/*  91 */       mc.func_147114_u().func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
/*  92 */       if (((Boolean)this.wow.getValue()).booleanValue()) {
/*  93 */         this.verycute = pos;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onReceivePacket(PacketEvent e) {
/* 101 */     if (e.getPacket() instanceof SPacketBlockChange) {
/* 102 */       SPacketBlockChange p = (SPacketBlockChange)e.getPacket();
/* 103 */       if (isEnabledOre(Block.func_149682_b(p.func_180728_a().func_177230_c()))) {
/* 104 */         this.ores.add(p.func_179827_b());
/*     */       }
/* 106 */     } else if (e.getPacket() instanceof SPacketMultiBlockChange) {
/* 107 */       SPacketMultiBlockChange p = (SPacketMultiBlockChange)e.getPacket();
/* 108 */       for (SPacketMultiBlockChange.BlockUpdateData dat : p.func_179844_a()) {
/* 109 */         if (isEnabledOre(Block.func_149682_b(dat.func_180088_c().func_177230_c())))
/* 110 */           this.ores.add(dat.func_180090_a()); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent e) {
/*     */     try {
/* 118 */       for (BlockPos pos : this.ores) {
/* 119 */         IBlockState state = BlockUtils.getState(pos);
/* 120 */         Block mat = state.func_177230_c();
/* 121 */         if (this.Mode.getValue() == mode.FullBox) {
/* 122 */           if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 56 && ((Boolean)this.diamond.getValue()).booleanValue() && Block.func_149682_b(mat) == 56) {
/* 123 */             RenderUtil.blockEsp(pos, new Color(0, 255, 255, 50), 1.0D, 1.0D);
/*     */           }
/* 125 */           if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 14 && ((Boolean)this.gold.getValue()).booleanValue() && Block.func_149682_b(mat) == 14) {
/* 126 */             RenderUtil.blockEsp(pos, new Color(255, 215, 0, 100), 1.0D, 1.0D);
/*     */           }
/* 128 */           if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 15 && ((Boolean)this.iron.getValue()).booleanValue() && Block.func_149682_b(mat) == 15) {
/* 129 */             RenderUtil.blockEsp(pos, new Color(213, 213, 213, 100), 1.0D, 1.0D);
/*     */           }
/* 131 */           if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 129 && ((Boolean)this.emerald.getValue()).booleanValue() && Block.func_149682_b(mat) == 129) {
/* 132 */             RenderUtil.blockEsp(pos, new Color(0, 255, 77, 100), 1.0D, 1.0D);
/*     */           }
/* 134 */           if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 73 && ((Boolean)this.redstone.getValue()).booleanValue() && Block.func_149682_b(mat) == 73) {
/* 135 */             RenderUtil.blockEsp(pos, new Color(255, 0, 0, 100), 1.0D, 1.0D);
/*     */           }
/* 137 */           if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 16 && ((Boolean)this.coal.getValue()).booleanValue() && Block.func_149682_b(mat) == 16) {
/* 138 */             RenderUtil.blockEsp(pos, new Color(0, 0, 0, 100), 1.0D, 1.0D);
/*     */           }
/* 140 */           if (Block.func_149682_b(mat) == 0 || Block.func_149682_b(mat) != 21 || !((Boolean)this.lapis.getValue()).booleanValue() || Block.func_149682_b(mat) != 21)
/*     */             continue; 
/* 142 */           RenderUtil.blockEsp(pos, new Color(38, 97, 156, 100), 1.0D, 1.0D);
/*     */           
/*     */           continue;
/*     */         } 
/* 146 */         if (this.Mode.getValue() != mode.Frame)
/* 147 */           continue;  if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 56 && ((Boolean)this.diamond.getValue()).booleanValue() && Block.func_149682_b(mat) == 56) {
/* 148 */           RenderUtil.blockEspFrame(pos, 0.0D, 255.0D, 255.0D);
/*     */         }
/* 150 */         if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 14 && ((Boolean)this.gold.getValue()).booleanValue() && Block.func_149682_b(mat) == 14) {
/* 151 */           RenderUtil.blockEspFrame(pos, 255.0D, 215.0D, 0.0D);
/*     */         }
/* 153 */         if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 15 && ((Boolean)this.iron.getValue()).booleanValue() && Block.func_149682_b(mat) == 15) {
/* 154 */           RenderUtil.blockEspFrame(pos, 213.0D, 213.0D, 213.0D);
/*     */         }
/* 156 */         if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 129 && ((Boolean)this.emerald.getValue()).booleanValue() && Block.func_149682_b(mat) == 129) {
/* 157 */           RenderUtil.blockEspFrame(pos, 0.0D, 255.0D, 77.0D);
/*     */         }
/* 159 */         if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 73 && ((Boolean)this.redstone.getValue()).booleanValue() && Block.func_149682_b(mat) == 73) {
/* 160 */           RenderUtil.blockEspFrame(pos, 255.0D, 0.0D, 0.0D);
/*     */         }
/* 162 */         if (Block.func_149682_b(mat) != 0 && Block.func_149682_b(mat) == 16 && ((Boolean)this.coal.getValue()).booleanValue() && Block.func_149682_b(mat) == 16) {
/* 163 */           RenderUtil.blockEspFrame(pos, 0.0D, 0.0D, 0.0D);
/*     */         }
/* 165 */         if (Block.func_149682_b(mat) == 0 || Block.func_149682_b(mat) != 21 || !((Boolean)this.lapis.getValue()).booleanValue() || Block.func_149682_b(mat) != 21)
/*     */           continue; 
/* 167 */         RenderUtil.blockEspFrame(pos, 38.0D, 97.0D, 156.0D);
/*     */       } 
/*     */       
/* 170 */       if (this.verycute != null && done != all && ((Boolean)this.wow.getValue()).booleanValue()) {
/* 171 */         RenderUtil.drawBlockOutline(this.verycute, new Color(255, 0, 30), 1.0F, false, 0);
/*     */       }
/* 173 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender2D(Render2DEvent e) {
/* 180 */     String f = "" + all;
/* 181 */     String g = "" + done;
/* 182 */     ScaledResolution sr = new ScaledResolution(mc);
/* 183 */     FontRenderer font = mc.field_71466_p;
/* 184 */     int size = 125;
/* 185 */     float xOffset = sr.func_78326_a() / 2.0F - size / 2.0F;
/* 186 */     float yOffset = 5.0F;
/* 187 */     float Y = 0.0F;
/* 188 */     RenderUtil.rectangleBordered((xOffset + 2.0F), (yOffset + 1.0F), (xOffset + 10.0F + size + font.func_78256_a(g) + 1.0F), (yOffset + size / 6.0F + 3.0F + font.field_78288_b + 2.2F), 0.5D, 90, 0);
/* 189 */     RenderUtil.rectangleBordered((xOffset + 3.0F), (yOffset + 2.0F), (xOffset + 10.0F + size + font.func_78256_a(g)), (yOffset + size / 6.0F + 2.0F + font.field_78288_b + 2.2F), 0.5D, 27, 61);
/* 190 */     font.func_175063_a("" + ChatFormatting.GREEN + "Done: " + ChatFormatting.WHITE + done + " / " + ChatFormatting.RED + "All: " + ChatFormatting.WHITE + all, xOffset + 25.0F, yOffset + font.field_78288_b + 4.0F, -1);
/* 191 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   private boolean isCheckableOre(int id) {
/* 195 */     int check = 0;
/* 196 */     int check1 = 0;
/* 197 */     int check2 = 0;
/* 198 */     int check3 = 0;
/* 199 */     int check4 = 0;
/* 200 */     int check5 = 0;
/* 201 */     int check6 = 0;
/* 202 */     if (((Boolean)this.diamond.getValue()).booleanValue() && id != 0) {
/* 203 */       check = 56;
/*     */     }
/* 205 */     if (((Boolean)this.gold.getValue()).booleanValue() && id != 0) {
/* 206 */       check1 = 14;
/*     */     }
/* 208 */     if (((Boolean)this.iron.getValue()).booleanValue() && id != 0) {
/* 209 */       check2 = 15;
/*     */     }
/* 211 */     if (((Boolean)this.emerald.getValue()).booleanValue() && id != 0) {
/* 212 */       check3 = 129;
/*     */     }
/* 214 */     if (((Boolean)this.redstone.getValue()).booleanValue() && id != 0) {
/* 215 */       check4 = 73;
/*     */     }
/* 217 */     if (((Boolean)this.coal.getValue()).booleanValue() && id != 0) {
/* 218 */       check5 = 16;
/*     */     }
/* 220 */     if (((Boolean)this.lapis.getValue()).booleanValue() && id != 0) {
/* 221 */       check6 = 21;
/*     */     }
/* 223 */     if (id == 0) {
/* 224 */       return false;
/*     */     }
/* 226 */     return (id == check || id == check1 || id == check2 || id == check3 || id == check4 || id == check5 || id == check6);
/*     */   }
/*     */   
/*     */   private boolean isEnabledOre(int id) {
/* 230 */     int check = 0;
/* 231 */     int check1 = 0;
/* 232 */     int check2 = 0;
/* 233 */     int check3 = 0;
/* 234 */     int check4 = 0;
/* 235 */     int check5 = 0;
/* 236 */     int check6 = 0;
/* 237 */     if (((Boolean)this.diamond.getValue()).booleanValue() && id != 0) {
/* 238 */       check = 56;
/*     */     }
/* 240 */     if (((Boolean)this.gold.getValue()).booleanValue() && id != 0) {
/* 241 */       check1 = 14;
/*     */     }
/* 243 */     if (((Boolean)this.iron.getValue()).booleanValue() && id != 0) {
/* 244 */       check2 = 15;
/*     */     }
/* 246 */     if (((Boolean)this.emerald.getValue()).booleanValue() && id != 0) {
/* 247 */       check3 = 129;
/*     */     }
/* 249 */     if (((Boolean)this.redstone.getValue()).booleanValue() && id != 0) {
/* 250 */       check4 = 73;
/*     */     }
/* 252 */     if (((Boolean)this.coal.getValue()).booleanValue() && id != 0) {
/* 253 */       check5 = 16;
/*     */     }
/* 255 */     if (((Boolean)this.lapis.getValue()).booleanValue() && id != 0) {
/* 256 */       check6 = 21;
/*     */     }
/* 258 */     if (id == 0) {
/* 259 */       return false;
/*     */     }
/* 261 */     return (id == check || id == check1 || id == check2 || id == check3 || id == check4 || id == check5 || id == check6);
/*     */   }
/*     */   
/*     */   private ArrayList<BlockPos> getBlocks(int x, int y, int z) {
/* 265 */     BlockPos min = new BlockPos(Util.mc.field_71439_g.field_70165_t - x, Util.mc.field_71439_g.field_70163_u - y, Util.mc.field_71439_g.field_70161_v - z);
/* 266 */     BlockPos max = new BlockPos(Util.mc.field_71439_g.field_70165_t + x, Util.mc.field_71439_g.field_70163_u + y, Util.mc.field_71439_g.field_70161_v + z);
/* 267 */     return BlockUtils.getAllInBox(min, max);
/*     */   }
/*     */   
/*     */   public Boolean shouldRender(Block cast) {
/* 271 */     if (cast == Blocks.field_150482_ag && ((Boolean)this.diamond.getValue()).booleanValue()) {
/* 272 */       return Boolean.valueOf(true);
/*     */     }
/* 274 */     if (cast == Blocks.field_150352_o && ((Boolean)this.gold.getValue()).booleanValue()) {
/* 275 */       return Boolean.valueOf(true);
/*     */     }
/* 277 */     if (cast == Blocks.field_150355_j && ((Boolean)this.water.getValue()).booleanValue()) {
/* 278 */       return Boolean.valueOf(true);
/*     */     }
/* 280 */     if (cast == Blocks.field_150353_l && ((Boolean)this.lava.getValue()).booleanValue()) {
/* 281 */       return Boolean.valueOf(true);
/*     */     }
/* 283 */     if (cast == Blocks.field_150366_p && ((Boolean)this.iron.getValue()).booleanValue()) {
/* 284 */       return Boolean.valueOf(true);
/*     */     }
/* 286 */     if (cast == Blocks.field_150412_bA && ((Boolean)this.emerald.getValue()).booleanValue()) {
/* 287 */       return Boolean.valueOf(true);
/*     */     }
/* 289 */     if (cast == Blocks.field_150450_ax && ((Boolean)this.redstone.getValue()).booleanValue()) {
/* 290 */       return Boolean.valueOf(true);
/*     */     }
/* 292 */     if (cast == Blocks.field_150369_x && ((Boolean)this.lapis.getValue()).booleanValue()) {
/* 293 */       return Boolean.valueOf(true);
/*     */     }
/* 295 */     if (cast == Blocks.field_150365_q && ((Boolean)this.coal.getValue()).booleanValue()) {
/* 296 */       return Boolean.valueOf(true);
/*     */     }
/* 298 */     return Boolean.valueOf(!((Boolean)this.wh.getValue()).booleanValue());
/*     */   }
/*     */   
/*     */   public enum mode {
/* 302 */     FullBox, Frame;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\render\XRay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */