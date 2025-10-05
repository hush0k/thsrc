/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockStateHelper
/*     */   implements IBlockStateHelper
/*     */ {
/*     */   private final Map<BlockPos, IBlockState> states;
/*     */   private final Supplier<IBlockAccess> world;
/*     */   
/*     */   public BlockStateHelper() {
/*  33 */     this(new HashMap<>());
/*     */   }
/*     */   
/*     */   public BlockStateHelper(Supplier<IBlockAccess> world) {
/*  37 */     this(new HashMap<>(), world);
/*     */   }
/*     */   
/*     */   public BlockStateHelper(Map<BlockPos, IBlockState> stateMap) {
/*  41 */     this(stateMap, () -> Util.mc.field_71441_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockStateHelper(Map<BlockPos, IBlockState> stateMap, Supplier<IBlockAccess> world) {
/*  46 */     this.states = stateMap;
/*  47 */     this.world = world;
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
/*     */ 
/*     */   
/*     */   public IBlockState func_180495_p(BlockPos pos) {
/*  61 */     IBlockState state = this.states.get(pos);
/*  62 */     if (state == null) {
/*  63 */       return ((IBlockAccess)this.world.get()).func_180495_p(pos);
/*     */     }
/*     */     
/*  66 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlockState(BlockPos pos, IBlockState state) {
/*  71 */     this.states.putIfAbsent(pos.func_185334_h(), state);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity func_175625_s(BlockPos pos) {
/*  76 */     return ((IBlockAccess)this.world.get()).func_175625_s(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_175626_b(BlockPos pos, int lightValue) {
/*  81 */     return ((IBlockAccess)this.world.get()).func_175626_b(pos, lightValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175623_d(BlockPos pos) {
/*  86 */     return func_180495_p(pos)
/*  87 */       .func_177230_c()
/*  88 */       .isAir(func_180495_p(pos), this, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public Biome func_180494_b(BlockPos pos) {
/*  93 */     return ((IBlockAccess)this.world.get()).func_180494_b(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_175627_a(BlockPos pos, EnumFacing direction) {
/*  98 */     return func_180495_p(pos).func_185893_b(this, pos, direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType func_175624_G() {
/* 103 */     return ((IBlockAccess)this.world.get()).func_175624_G();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
/* 108 */     if (!Util.mc.field_71441_e.func_175701_a(pos)) {
/* 109 */       return _default;
/*     */     }
/*     */     
/* 112 */     Chunk chunk = Util.mc.field_71441_e.func_175726_f(pos);
/*     */     
/* 114 */     if (chunk == null || chunk.func_76621_g()) {
/* 115 */       return _default;
/*     */     }
/*     */     
/* 118 */     return func_180495_p(pos).isSideSolid(this, pos, side);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\BlockStateHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */