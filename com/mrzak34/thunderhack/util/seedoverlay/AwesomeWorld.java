/*    */ package com.mrzak34.thunderhack.util.seedoverlay;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.client.multiplayer.ChunkProviderClient;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.IChunkProvider;
/*    */ import net.minecraft.world.storage.ISaveHandler;
/*    */ import net.minecraft.world.storage.MapStorage;
/*    */ import net.minecraft.world.storage.SaveDataMemoryStorage;
/*    */ import net.minecraft.world.storage.SaveHandlerMP;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ import net.minecraftforge.common.DimensionManager;
/*    */ 
/*    */ public class AwesomeWorld extends World {
/*    */   protected AwesomeWorld(WorldInfo worldInfo) {
/* 18 */     super((ISaveHandler)new SaveHandlerMP(), worldInfo, DimensionManager.createProviderFor(0), Util.mc.field_71424_I, true);
/*    */     
/* 20 */     func_72912_H().func_176144_a(EnumDifficulty.PEACEFUL);
/* 21 */     this.field_73011_w.func_76558_a(this);
/* 22 */     func_175652_B(new BlockPos(8, 64, 8));
/* 23 */     this.field_73020_y = func_72970_h();
/* 24 */     this.field_72988_C = (MapStorage)new SaveDataMemoryStorage();
/* 25 */     func_72966_v();
/* 26 */     func_72947_a();
/* 27 */     initCapabilities();
/*    */   }
/*    */   private ChunkProviderClient clientChunkProvider;
/*    */   public ChunkProviderClient getChunkProvider() {
/* 31 */     return (ChunkProviderClient)super.func_72863_F();
/*    */   }
/*    */ 
/*    */   
/*    */   protected IChunkProvider func_72970_h() {
/* 36 */     this.clientChunkProvider = new ChunkProviderClient(this);
/* 37 */     return (IChunkProvider)this.clientChunkProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_175680_a(int x, int z, boolean allowEmpty) {
/* 42 */     return (allowEmpty || !getChunkProvider().func_186025_d(x, z).func_76621_g());
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\seedoverlay\AwesomeWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */