/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.notification.Notification;
/*     */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.seedoverlay.WorldLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class SeedOverlay
/*     */   extends Module {
/*     */   private static ExecutorService executor;
/*     */   private static ExecutorService executor2;
/*  27 */   private final Timer timer = new Timer();
/*  28 */   public Setting<Integer> Distance = register(new Setting("Distance", Integer.valueOf(6), Integer.valueOf(0), Integer.valueOf(15)));
/*  29 */   public Setting<Integer> renderDistance = register(new Setting("RenderDistance", Integer.valueOf(120), Integer.valueOf(0), Integer.valueOf(256)));
/*  30 */   public Setting<Boolean> GrassSpread = register(new Setting("GrassSpread", Boolean.valueOf(false)));
/*  31 */   public Setting<Boolean> FalsePositive = register(new Setting("FalsePositive", Boolean.valueOf(false)));
/*  32 */   public Setting<Boolean> LavaMix = register(new Setting("LavaMix", Boolean.valueOf(false)));
/*  33 */   public Setting<Boolean> Bush = register(new Setting("Bush", Boolean.valueOf(false)));
/*  34 */   public Setting<Boolean> Tree = register(new Setting("Tree", Boolean.valueOf(false)));
/*  35 */   public Setting<Boolean> Liquid = register(new Setting("Liquid", Boolean.valueOf(false)));
/*  36 */   public Setting<Boolean> Fallingblock = register(new Setting("Fallingblock", Boolean.valueOf(false)));
/*  37 */   public Setting<String> sd = register(new Setting("seed", "-4172144997902289642"));
/*  38 */   public int currentdis = 0;
/*     */ 
/*     */   
/*  41 */   private ArrayList<ChunkData> chunks = new ArrayList<>();
/*  42 */   private final ArrayList<int[]> tobesearch = (ArrayList)new ArrayList<>();
/*     */   public SeedOverlay() {
/*  44 */     super("SeedOverlay", "рендерит фейковый-мир для поиска-несоответсвий", Module.Category.MISC);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  50 */     if (this.timer.passedMs(500L)) {
/*  51 */       if (mc.field_71439_g.field_71093_bK != this.currentdis) {
/*  52 */         Command.sendMessage("Перевключи модуль");
/*  53 */         toggle();
/*     */       } 
/*  55 */       searchViewDistance();
/*  56 */       runviewdistance();
/*  57 */       this.timer.reset();
/*     */     } 
/*  59 */     int[] remove = null;
/*     */     try {
/*  61 */       for (int[] vec2d : this.tobesearch) {
/*  62 */         remove = vec2d;
/*  63 */         executor.execute(() -> WorldLoader.CreateChunk(vec2d[0], vec2d[1], mc.field_71439_g.field_71093_bK));
/*     */       } 
/*  65 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  68 */     this.tobesearch.remove(remove);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  74 */     WorldLoader.seed = Long.parseLong((String)this.sd.getValue());
/*     */     try {
/*  76 */       NotificationManager.publicity("Current seed: " + WorldLoader.seed, 3, Notification.Type.INFO);
/*  77 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  80 */     if (mc.func_71356_B()) {
/*  81 */       Command.sendMessage("Only in multiplayer");
/*  82 */       toggle();
/*     */     } 
/*  84 */     if (WorldLoader.seed == 44776655L) {
/*  85 */       Command.sendMessage("Нет сида дебил");
/*  86 */       toggle();
/*     */       return;
/*     */     } 
/*  89 */     this.currentdis = mc.field_71439_g.field_71093_bK;
/*  90 */     executor = Executors.newSingleThreadExecutor();
/*  91 */     executor2 = Executors.newSingleThreadExecutor();
/*  92 */     WorldLoader.setup();
/*  93 */     this.chunks = new ArrayList<>();
/*  94 */     searchViewDistance();
/*     */   }
/*     */   
/*     */   private void searchViewDistance() {
/*  98 */     executor.execute(() -> {
/*     */           for (int x = mc.field_71439_g.field_70176_ah - ((Integer)this.Distance.getValue()).intValue(); x <= mc.field_71439_g.field_70176_ah + ((Integer)this.Distance.getValue()).intValue(); x++) {
/*     */             for (int z = mc.field_71439_g.field_70164_aj - ((Integer)this.Distance.getValue()).intValue(); z <= mc.field_71439_g.field_70164_aj + ((Integer)this.Distance.getValue()).intValue(); z++) {
/*     */               if (havenotsearched(x, z) && mc.field_71441_e.func_190526_b(x, z)) {
/*     */                 boolean found = false;
/*     */                 for (int[] vec2d : this.tobesearch) {
/*     */                   if (vec2d[0] == x && vec2d[1] == z) {
/*     */                     found = true;
/*     */                     break;
/*     */                   } 
/*     */                 } 
/*     */                 if (!found) {
/*     */                   this.tobesearch.add(new int[] { x, z });
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void runviewdistance() {
/* 119 */     for (int x = mc.field_71439_g.field_70176_ah - ((Integer)this.Distance.getValue()).intValue(); x <= mc.field_71439_g.field_70176_ah + ((Integer)this.Distance.getValue()).intValue(); x++) {
/* 120 */       for (int z = mc.field_71439_g.field_70164_aj - ((Integer)this.Distance.getValue()).intValue(); z <= mc.field_71439_g.field_70164_aj + ((Integer)this.Distance.getValue()).intValue(); z++) {
/* 121 */         if (mc.field_71441_e.func_190526_b(x, z) && 
/* 122 */           WorldLoader.fakeworld.func_190526_b(x, z) && WorldLoader.fakeworld.func_190526_b(x + 1, z) && WorldLoader.fakeworld.func_190526_b(x, z + 1) && WorldLoader.fakeworld
/* 123 */           .func_190526_b(x + 1, z + 1) && 
/* 124 */           havenotsearched(x, z)) {
/* 125 */           ChunkData data = new ChunkData(new ChunkPos(x, z), false);
/* 126 */           searchChunk(mc.field_71441_e.func_72964_e(x, z), data);
/* 127 */           this.chunks.add(data);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean havenotsearched(int x, int z) {
/* 136 */     for (ChunkData chunk : this.chunks) {
/* 137 */       if (chunk.chunkPos.field_77276_a == x && chunk.chunkPos.field_77275_b == z) {
/* 138 */         return false;
/*     */       }
/*     */     } 
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void searchChunk(Chunk chunk, ChunkData data) {
/* 146 */     executor2.execute(() -> {
/*     */           try {
/*     */             for (int x = chunk.func_76632_l().func_180334_c(); x <= chunk.func_76632_l().func_180332_e(); x++) {
/*     */               for (int z = chunk.func_76632_l().func_180333_d(); z <= chunk.func_76632_l().func_180330_f(); z++) {
/*     */                 for (int y = 0; y < 255; y++) {
/*     */                   if (BlockFast(new BlockPos(x, y, z), WorldLoader.fakeworld.func_180495_p(new BlockPos(x, y, z)).func_177230_c(), chunk.func_186032_a(x, y, z).func_177230_c()))
/*     */                     data.blocks.add(new BlockPos(x, y, z)); 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             data.Searched = true;
/* 157 */           } catch (Exception exception) {}
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean BlockFast(BlockPos blockPos, Block FakeChunk, Block RealChunk) {
/* 164 */     if (RealChunk instanceof net.minecraft.block.BlockSnow)
/* 165 */       return false; 
/* 166 */     if (FakeChunk instanceof net.minecraft.block.BlockSnow)
/* 167 */       return false; 
/* 168 */     if (RealChunk instanceof net.minecraft.block.BlockVine)
/* 169 */       return false; 
/* 170 */     if (FakeChunk instanceof net.minecraft.block.BlockVine)
/* 171 */       return false; 
/* 172 */     if (!((Boolean)this.Fallingblock.getValue()).booleanValue()) {
/* 173 */       if (RealChunk instanceof net.minecraft.block.BlockFalling)
/* 174 */         return false; 
/* 175 */       if (FakeChunk instanceof net.minecraft.block.BlockFalling)
/* 176 */         return false; 
/*     */     } 
/* 178 */     if (!((Boolean)this.Liquid.getValue()).booleanValue()) {
/* 179 */       if (RealChunk instanceof net.minecraft.block.BlockLiquid)
/* 180 */         return false; 
/* 181 */       if (FakeChunk instanceof net.minecraft.block.BlockLiquid)
/* 182 */         return false; 
/* 183 */       if (mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() instanceof net.minecraft.block.BlockLiquid)
/* 184 */         return false; 
/* 185 */       if (mc.field_71441_e.func_180495_p(blockPos.func_177979_c(2)).func_177230_c() instanceof net.minecraft.block.BlockLiquid)
/* 186 */         return false; 
/*     */     } 
/* 188 */     if (!((Boolean)this.Tree.getValue()).booleanValue()) {
/* 189 */       if (FakeChunk instanceof net.minecraft.block.BlockGrass && 
/* 190 */         Treeroots(blockPos))
/* 191 */         return false; 
/* 192 */       if (RealChunk instanceof net.minecraft.block.BlockLog || RealChunk instanceof net.minecraft.block.BlockLeaves)
/* 193 */         return false; 
/* 194 */       if (FakeChunk instanceof net.minecraft.block.BlockLog || FakeChunk instanceof net.minecraft.block.BlockLeaves)
/* 195 */         return false; 
/*     */     } 
/* 197 */     if (!((Boolean)this.GrassSpread.getValue()).booleanValue()) {
/* 198 */       if (RealChunk instanceof net.minecraft.block.BlockGrass && FakeChunk instanceof net.minecraft.block.BlockDirt)
/* 199 */         return false; 
/* 200 */       if (RealChunk instanceof net.minecraft.block.BlockDirt && FakeChunk instanceof net.minecraft.block.BlockGrass)
/* 201 */         return false; 
/*     */     } 
/* 203 */     if (!((Boolean)this.Bush.getValue()).booleanValue()) {
/* 204 */       if (RealChunk instanceof net.minecraft.block.BlockBush)
/* 205 */         return false; 
/* 206 */       if (RealChunk instanceof net.minecraft.block.BlockReed)
/* 207 */         return false; 
/* 208 */       if (FakeChunk instanceof net.minecraft.block.BlockBush)
/* 209 */         return false; 
/*     */     } 
/* 211 */     if (!((Boolean)this.LavaMix.getValue()).booleanValue() && (
/* 212 */       RealChunk instanceof net.minecraft.block.BlockObsidian || RealChunk.equals(Blocks.field_150347_e)) && 
/* 213 */       Lavamix(blockPos)) {
/* 214 */       return false;
/*     */     }
/* 216 */     if (!((Boolean)this.FalsePositive.getValue()).booleanValue()) {
/* 217 */       if (FakeChunk instanceof net.minecraft.block.BlockOre && (RealChunk instanceof net.minecraft.block.BlockStone || RealChunk instanceof net.minecraft.block.BlockMagma || RealChunk instanceof net.minecraft.block.BlockNetherrack || RealChunk instanceof net.minecraft.block.BlockDirt))
/* 218 */         return false; 
/* 219 */       if (RealChunk instanceof net.minecraft.block.BlockOre && (FakeChunk instanceof net.minecraft.block.BlockStone || FakeChunk instanceof net.minecraft.block.BlockMagma || FakeChunk instanceof net.minecraft.block.BlockNetherrack || FakeChunk instanceof net.minecraft.block.BlockDirt)) {
/* 220 */         return false;
/*     */       }
/*     */       
/* 223 */       if (FakeChunk instanceof net.minecraft.block.BlockRedstoneOre && (RealChunk instanceof net.minecraft.block.BlockStone || RealChunk instanceof net.minecraft.block.BlockDirt))
/* 224 */         return false; 
/* 225 */       if (RealChunk instanceof net.minecraft.block.BlockRedstoneOre && (FakeChunk instanceof net.minecraft.block.BlockStone || FakeChunk instanceof net.minecraft.block.BlockDirt)) {
/* 226 */         return false;
/*     */       }
/* 228 */       if (FakeChunk instanceof net.minecraft.block.BlockGlowstone && RealChunk instanceof net.minecraft.block.BlockAir)
/* 229 */         return false; 
/* 230 */       if (RealChunk instanceof net.minecraft.block.BlockGlowstone && FakeChunk instanceof net.minecraft.block.BlockAir) {
/* 231 */         return false;
/*     */       }
/* 233 */       if (FakeChunk instanceof net.minecraft.block.BlockMagma && RealChunk instanceof net.minecraft.block.BlockNetherrack)
/* 234 */         return false; 
/* 235 */       if (RealChunk instanceof net.minecraft.block.BlockMagma && FakeChunk instanceof net.minecraft.block.BlockNetherrack)
/* 236 */         return false; 
/* 237 */       if (RealChunk instanceof net.minecraft.block.BlockFire || FakeChunk instanceof net.minecraft.block.BlockFire)
/* 238 */         return false; 
/* 239 */       if (RealChunk instanceof net.minecraft.block.BlockOre && FakeChunk instanceof net.minecraft.block.BlockOre)
/* 240 */         return false; 
/* 241 */       if (RealChunk.func_149732_F().equals(Blocks.field_150418_aU.func_149732_F()) && FakeChunk instanceof net.minecraft.block.BlockStone)
/* 242 */         return false; 
/* 243 */       if ((FakeChunk instanceof net.minecraft.block.BlockStone && RealChunk instanceof net.minecraft.block.BlockDirt) || (FakeChunk instanceof net.minecraft.block.BlockDirt && RealChunk instanceof net.minecraft.block.BlockStone))
/* 244 */         return false; 
/* 245 */       if (!(FakeChunk instanceof net.minecraft.block.BlockAir) && RealChunk instanceof net.minecraft.block.BlockAir && 
/* 246 */         !mc.field_71441_e.func_180495_p(blockPos).func_177230_c().func_149732_F().equals(RealChunk.func_149732_F())) {
/* 247 */         return false;
/*     */       }
/*     */     } 
/* 250 */     return !FakeChunk.func_149732_F().equals(RealChunk.func_149732_F());
/*     */   }
/*     */   
/*     */   public boolean Treeroots(BlockPos b) {
/* 254 */     return mc.field_71441_e.func_180495_p(b.func_177984_a()).func_177230_c() instanceof net.minecraft.block.BlockLog;
/*     */   }
/*     */   
/*     */   public boolean Lavamix(BlockPos b) {
/* 258 */     if (mc.field_71441_e.func_180495_p(b.func_177984_a()).func_177230_c() instanceof net.minecraft.block.BlockLiquid) {
/* 259 */       return true;
/*     */     }
/* 261 */     if (mc.field_71441_e.func_180495_p(b.func_177977_b()).func_177230_c() instanceof net.minecraft.block.BlockLiquid) {
/* 262 */       return true;
/*     */     }
/* 264 */     if (mc.field_71441_e.func_180495_p(b.func_177982_a(1, 0, 0)).func_177230_c() instanceof net.minecraft.block.BlockLiquid) {
/* 265 */       return true;
/*     */     }
/* 267 */     if (mc.field_71441_e.func_180495_p(b.func_177982_a(0, 0, 1)).func_177230_c() instanceof net.minecraft.block.BlockLiquid) {
/* 268 */       return true;
/*     */     }
/* 270 */     if (mc.field_71441_e.func_180495_p(b.func_177982_a(-1, 0, 0)).func_177230_c() instanceof net.minecraft.block.BlockLiquid) {
/* 271 */       return true;
/*     */     }
/* 273 */     return mc.field_71441_e.func_180495_p(b.func_177982_a(0, 0, -1)).func_177230_c() instanceof net.minecraft.block.BlockLiquid;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/*     */     try {
/* 279 */       ArrayList<ChunkData> Remove = new ArrayList<>();
/* 280 */       for (ChunkData chunk : this.chunks) {
/* 281 */         if (chunk.Searched) {
/* 282 */           if (mc.field_71439_g.func_70011_f(chunk.chunkPos.func_180332_e(), 100.0D, chunk.chunkPos.func_180330_f()) > 2000.0D)
/* 283 */             Remove.add(chunk); 
/* 284 */           for (BlockPos block : chunk.blocks) {
/*     */ 
/*     */             
/* 287 */             if (mc.field_71439_g.func_174818_b(new BlockPos(block.func_177958_n(), block.func_177956_o(), block.func_177952_p())) < (((Integer)this.renderDistance.getValue()).intValue() * ((Integer)this.renderDistance.getValue()).intValue())) {
/* 288 */               RenderUtil.blockEspFrame(new BlockPos(block.func_177958_n(), block.func_177956_o(), block.func_177952_p()), 0.0D, 255.0D, 255.0D);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 295 */       this.chunks.removeAll(Remove);
/* 296 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ChunkData
/*     */   {
/* 303 */     public final List<BlockPos> blocks = new ArrayList<>(); private boolean Searched;
/*     */     private final ChunkPos chunkPos;
/*     */     
/*     */     public ChunkData(ChunkPos chunkPos, boolean Searched) {
/* 307 */       this.chunkPos = chunkPos;
/* 308 */       this.Searched = Searched;
/*     */     }
/*     */     
/*     */     public List<BlockPos> getBlocks() {
/* 312 */       return this.blocks;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\SeedOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */