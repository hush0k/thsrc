/*     */ package com.mrzak34.thunderhack.util.seedoverlay;
/*     */ 
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IChunkProviderClient;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.ChunkGeneratorHell;
/*     */ import net.minecraft.world.gen.IChunkGenerator;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
/*     */ import net.minecraftforge.event.terraingen.PopulateChunkEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ public class WorldLoader
/*     */ {
/*     */   public static IChunkGenerator ChunkGenerator;
/*  23 */   public static long seed = 44776655L;
/*     */   
/*     */   public static boolean GenerateStructures = true;
/*     */   public static AwesomeWorld fakeworld;
/*     */   public static Random rand;
/*     */   
/*     */   public static void setup() {
/*  30 */     WorldSettings worldSettings = new WorldSettings(seed, GameType.SURVIVAL, GenerateStructures, false, WorldType.field_77137_b);
/*  31 */     WorldInfo worldInfo = new WorldInfo(worldSettings, "FakeWorld");
/*  32 */     worldInfo.func_176128_f(true);
/*  33 */     fakeworld = new AwesomeWorld(worldInfo);
/*  34 */     if (Util.mc.field_71439_g.field_71093_bK == -1) {
/*  35 */       ChunkGenerator = (IChunkGenerator)new ChunkGeneratorHell(fakeworld, fakeworld.func_72912_H().func_76089_r(), seed);
/*     */     } else {
/*  37 */       ChunkGenerator = fakeworld.field_73011_w.func_186060_c();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Chunk CreateChunk(int x, int z, int dis) {
/*     */     Chunk Testchunk;
/*  45 */     if (dis == -1 && 
/*  46 */       !(ChunkGenerator instanceof ChunkGeneratorHell)) {
/*  47 */       ChunkGenerator = (IChunkGenerator)new ChunkGeneratorHell(fakeworld, fakeworld.func_72912_H().func_76089_r(), seed);
/*     */     }
/*     */     
/*  50 */     if (!fakeworld.func_190526_b(x, z))
/*  51 */     { Testchunk = ChunkGenerator.func_185932_a(x, z); }
/*  52 */     else { Testchunk = fakeworld.func_72964_e(x, z); }
/*     */     
/*  54 */     ((IChunkProviderClient)fakeworld.getChunkProvider()).getLoadedChunks().put(ChunkPos.func_77272_a(x, z), Testchunk);
/*  55 */     Testchunk.func_76631_c();
/*  56 */     populate((IChunkProvider)fakeworld.getChunkProvider(), ChunkGenerator, x, z);
/*     */     
/*  58 */     return Testchunk;
/*     */   }
/*     */   
/*     */   public static void populate(IChunkProvider chunkProvider, IChunkGenerator chunkGenrator, int x, int z) {
/*  62 */     Chunk chunk = chunkProvider.func_186026_b(x, z - 1);
/*  63 */     Chunk chunk1 = chunkProvider.func_186026_b(x + 1, z);
/*  64 */     Chunk chunk2 = chunkProvider.func_186026_b(x, z + 1);
/*  65 */     Chunk chunk3 = chunkProvider.func_186026_b(x - 1, z);
/*     */     
/*  67 */     if (chunk1 != null && chunk2 != null && chunkProvider.func_186026_b(x + 1, z + 1) != null) {
/*  68 */       Awesomepopulate(chunkGenrator, fakeworld, x, z);
/*     */     }
/*     */     
/*  71 */     if (chunk3 != null && chunk2 != null && chunkProvider.func_186026_b(x - 1, z + 1) != null) {
/*  72 */       Awesomepopulate(chunkGenrator, fakeworld, x - 1, z);
/*     */     }
/*     */     
/*  75 */     if (chunk != null && chunk1 != null && chunkProvider.func_186026_b(x + 1, z - 1) != null) {
/*  76 */       Awesomepopulate(chunkGenrator, fakeworld, x, z - 1);
/*     */     }
/*     */     
/*  79 */     if (chunk != null && chunk3 != null) {
/*  80 */       Chunk chunk4 = chunkProvider.func_186026_b(x - 1, z - 1);
/*     */       
/*  82 */       if (chunk4 != null) {
/*  83 */         Awesomepopulate(chunkGenrator, fakeworld, x - 1, z - 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void Awesomepopulate(IChunkGenerator overworldChunkGen, AwesomeWorld fakeworld, int x, int z) {
/*  90 */     Chunk testchunk = fakeworld.func_72964_e(x, z);
/*  91 */     if (testchunk.func_177419_t()) {
/*  92 */       if (overworldChunkGen.func_185933_a(testchunk, x, z)) {
/*  93 */         testchunk.func_76630_e();
/*     */       }
/*     */     } else {
/*  96 */       testchunk.func_150809_p();
/*  97 */       overworldChunkGen.func_185931_b(x, z);
/*  98 */       testchunk.func_76630_e();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void event(PopulateChunkEvent.Populate event) {
/* 104 */     event.setResult(Event.Result.ALLOW);
/*     */   }
/*     */   
/*     */   public static void DecorateBiomeEvent(DecorateBiomeEvent.Decorate event) {
/* 108 */     event.setResult(Event.Result.ALLOW);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\seedoverlay\WorldLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */