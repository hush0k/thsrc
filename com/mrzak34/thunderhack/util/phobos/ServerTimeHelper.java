/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerTimeHelper
/*     */ {
/*  36 */   private static final ScheduledExecutorService THREAD = ThreadUtil.newDaemonScheduledExecutor("Server-Helper");
/*     */   
/*     */   private final AutoCrystal module;
/*     */   
/*     */   private final Setting<AutoCrystal.ACRotate> rotate;
/*     */   
/*     */   private final Setting<AutoCrystal.SwingTime> placeSwing;
/*     */   
/*     */   private final Setting<Boolean> antiFeetPlace;
/*     */   
/*     */   private final Setting<Boolean> newVersion;
/*     */   
/*     */   private final Setting<Integer> buffer;
/*     */ 
/*     */   
/*     */   public ServerTimeHelper(AutoCrystal module, Setting<AutoCrystal.ACRotate> rotate, Setting<AutoCrystal.SwingTime> placeSwing, Setting<Boolean> antiFeetPlace, Setting<Boolean> newVersion, Setting<Integer> buffer) {
/*  52 */     this.module = module;
/*  53 */     this.rotate = rotate;
/*  54 */     this.placeSwing = placeSwing;
/*  55 */     this.antiFeetPlace = antiFeetPlace;
/*  56 */     this.newVersion = newVersion;
/*  57 */     this.buffer = buffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAtFeet(List<EntityPlayer> players, BlockPos pos, boolean ignoreCrystals, boolean noBoost2) {
/*  64 */     for (EntityPlayer player : players) {
/*  65 */       if (Thunderhack.friendManager.isFriend(player) || player == Util.mc.field_71439_g)
/*     */         continue; 
/*  67 */       if (isAtFeet(player, pos, ignoreCrystals, noBoost2)) return true; 
/*     */     } 
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAtFeet(EntityPlayer player, BlockPos pos, boolean ignoreCrystals, boolean noBoost2) {
/*  76 */     BlockPos up = pos.func_177984_a();
/*  77 */     if (!canPlaceCrystal(pos, ignoreCrystals, noBoost2)) return false; 
/*  78 */     for (EnumFacing face : EnumFacing.field_176754_o) {
/*  79 */       BlockPos off = up.func_177972_a(face);
/*     */       
/*  81 */       if (Util.mc.field_71441_e.func_72872_a(EntityPlayer.class, new AxisAlignedBB(off))
/*     */         
/*  83 */         .contains(player)) {
/*  84 */         return true;
/*     */       }
/*     */       
/*  87 */       BlockPos off2 = off.func_177972_a(face);
/*     */       
/*  89 */       if (Util.mc.field_71441_e.func_72872_a(EntityPlayer.class, new AxisAlignedBB(off2))
/*     */         
/*  91 */         .contains(player)) {
/*  92 */         return true;
/*     */       }
/*     */     } 
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canPlaceCrystal(BlockPos pos, boolean ignoreCrystals, boolean noBoost2) {
/* 101 */     return canPlaceCrystal(pos, ignoreCrystals, noBoost2, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canPlaceCrystal(BlockPos pos, boolean ignoreCrystals, boolean noBoost2, List<Entity> entities) {
/* 108 */     return canPlaceCrystal(pos, ignoreCrystals, noBoost2, entities, noBoost2, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canPlaceCrystal(BlockPos pos, boolean ignoreCrystals, boolean noBoost2, List<Entity> entities, boolean ignoreBoost2Entities, long deathTime) {
/* 117 */     IBlockState state = Util.mc.field_71441_e.func_180495_p(pos);
/* 118 */     if (state.func_177230_c() != Blocks.field_150343_Z && state
/* 119 */       .func_177230_c() != Blocks.field_150357_h) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     return checkBoost(pos, ignoreCrystals, noBoost2, entities, ignoreBoost2Entities, deathTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canPlaceCrystalReplaceable(BlockPos pos, boolean ignoreCrystals, boolean noBoost2, List<Entity> entities, boolean ignoreBoost2Entities, long deathTime) {
/* 133 */     IBlockState state = Util.mc.field_71441_e.func_180495_p(pos);
/* 134 */     if (state.func_177230_c() != Blocks.field_150343_Z && state
/* 135 */       .func_177230_c() != Blocks.field_150357_h && 
/* 136 */       !state.func_185904_a().func_76222_j()) {
/* 137 */       return false;
/*     */     }
/*     */     
/* 140 */     return checkBoost(pos, ignoreCrystals, noBoost2, entities, ignoreBoost2Entities, deathTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkBoost(BlockPos pos, boolean ignoreCrystals, boolean noBoost2, List<Entity> entities, boolean ignoreBoost2Entities, long deathTime) {
/* 150 */     BlockPos boost = pos.func_177984_a();
/* 151 */     if (Util.mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a || 
/* 152 */       !checkEntityList(boost, ignoreCrystals, entities, deathTime)) {
/* 153 */       return false;
/*     */     }
/*     */     
/* 156 */     if (!noBoost2) {
/* 157 */       BlockPos boost2 = boost.func_177984_a();
/*     */       
/* 159 */       if (Util.mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a) {
/* 160 */         return false;
/*     */       }
/*     */       
/* 163 */       return (ignoreBoost2Entities || 
/* 164 */         checkEntityList(boost2, ignoreCrystals, entities, deathTime));
/*     */     } 
/*     */     
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkEntityList(BlockPos pos, boolean ignoreCrystals, List<Entity> entities) {
/* 173 */     return checkEntityList(pos, ignoreCrystals, entities, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkEntityList(BlockPos pos, boolean ignoreCrystals, List<Entity> entities, long deathTime) {
/* 180 */     if (entities == null) {
/* 181 */       return checkEntities(pos, ignoreCrystals, deathTime);
/*     */     }
/*     */     
/* 184 */     AxisAlignedBB bb = new AxisAlignedBB(pos);
/* 185 */     for (Entity entity : entities) {
/* 186 */       if (checkEntity(entity, ignoreCrystals, deathTime) && entity
/* 187 */         .func_174813_aQ().func_72326_a(bb)) {
/* 188 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkEntities(BlockPos pos, boolean ignoreCrystals, long deathTime) {
/* 198 */     for (Entity entity : Util.mc.field_71441_e
/* 199 */       .func_72872_a(Entity.class, new AxisAlignedBB(pos))) {
/* 200 */       if (checkEntity(entity, ignoreCrystals, deathTime)) {
/* 201 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkEntity(Entity entity, boolean ignoreCrystals, long deathTime) {
/* 211 */     if (entity == null) {
/* 212 */       return false;
/*     */     }
/*     */     
/* 215 */     if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/* 216 */       if (ignoreCrystals) {
/* 217 */         return false;
/*     */       }
/*     */       
/* 220 */       return (!entity.field_70128_L || 
/* 221 */         !Thunderhack.setDeadManager.passedDeathTime(entity, deathTime));
/*     */     } 
/*     */     
/* 224 */     return !EntityUtil.isDead(entity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSemiSafe(EntityPlayer player, boolean ignoreCrystals, boolean noBoost2) {
/* 230 */     BlockPos origin = player.func_180425_c();
/* 231 */     int i = 0;
/* 232 */     for (EnumFacing face : EnumFacing.field_176754_o) {
/* 233 */       BlockPos off = origin.func_177972_a(face);
/* 234 */       if (Util.mc.field_71441_e.func_180495_p(off).func_177230_c() != Blocks.field_150350_a) i++; 
/*     */     } 
/* 236 */     return (i >= 3);
/*     */   }
/*     */   
/*     */   public static EntityPlayer getClosestEnemy() {
/* 240 */     return getClosestEnemy(Util.mc.field_71441_e.field_73010_i);
/*     */   }
/*     */   
/*     */   public static EntityPlayer getClosestEnemy(List<EntityPlayer> list) {
/* 244 */     return getClosestEnemy(Util.mc.field_71439_g.func_174791_d(), list);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayer getClosestEnemy(BlockPos pos, List<EntityPlayer> list) {
/* 249 */     return getClosestEnemy(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), list);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayer getClosestEnemy(Vec3d vec3d, List<EntityPlayer> list) {
/* 254 */     return getClosestEnemy(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c, list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayer getClosestEnemy(double x, double y, double z, double maxRange, List<EntityPlayer> enemies, List<EntityPlayer> players) {
/* 263 */     EntityPlayer closestEnemied = getClosestEnemy(x, y, z, enemies);
/* 264 */     if (closestEnemied != null && closestEnemied
/* 265 */       .func_70092_e(x, y, z) < 
/* 266 */       MathUtil.square(maxRange)) {
/* 267 */       return closestEnemied;
/*     */     }
/*     */     
/* 270 */     return getClosestEnemy(x, y, z, players);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayer getClosestEnemy(double x, double y, double z, List<EntityPlayer> players) {
/* 277 */     EntityPlayer closest = null;
/* 278 */     double distance = 3.4028234663852886E38D;
/*     */     
/* 280 */     for (EntityPlayer player : players) {
/* 281 */       if (player != null && !player.field_70128_L && 
/*     */         
/* 283 */         !player.equals(Util.mc.field_71439_g) && 
/* 284 */         !Thunderhack.friendManager.isFriend(player)) {
/* 285 */         double dist = player.func_70092_e(x, y, z);
/* 286 */         if (dist < distance) {
/* 287 */           closest = player;
/* 288 */           distance = dist;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     return closest;
/*     */   }
/*     */   
/*     */   public static boolean isValid(Entity player, double range) {
/* 297 */     return (player != null && !player.field_70128_L && Util.mc.field_71439_g
/*     */       
/* 299 */       .func_70068_e(player) <= MathUtil.square(range) && 
/* 300 */       !Thunderhack.friendManager.isFriend((EntityPlayer)player));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUseEntity(CPacketUseEntity packet, Entity crystal) {
/* 306 */     if (packet.func_149565_c() == CPacketUseEntity.Action.ATTACK && ((Boolean)this.antiFeetPlace
/* 307 */       .getValue()).booleanValue() && (this.rotate
/* 308 */       .getValue() == AutoCrystal.ACRotate.None || this.rotate.getValue() == AutoCrystal.ACRotate.Break) && crystal instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/*     */       EntityPlayer closest;
/* 310 */       if ((closest = getClosestEnemy()) != null && 
/* 311 */         isSemiSafe(closest, true, ((Boolean)this.newVersion.getValue()).booleanValue()) && 
/* 312 */         isAtFeet(Util.mc.field_71441_e.field_73010_i, crystal.func_180425_c().func_177977_b(), true, ((Boolean)this.newVersion.getValue()).booleanValue())) {
/* 313 */         int intoTick = Thunderhack.servtickManager.getTickTimeAdjusted();
/* 314 */         int sleep = Thunderhack.servtickManager.getServerTickLengthMS() + Thunderhack.servtickManager.getSpawnTime() + ((Integer)this.buffer.getValue()).intValue() - intoTick;
/* 315 */         place(crystal.func_180425_c().func_177977_b(), sleep);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void place(BlockPos pos, int sleep) {
/* 320 */     AutoCrystal.SwingTime time = (AutoCrystal.SwingTime)this.placeSwing.getValue();
/* 321 */     THREAD.schedule(() -> { if (InventoryUtil.isHolding(Items.field_185158_cP)) { EnumHand hand = InventoryUtil.getHand(Items.field_185158_cP); RayTraceResult ray = CalculationMotion.rayTraceTo(pos, (IBlockAccess)Util.mc.field_71441_e); float[] f = RayTraceUtil.hitVecToPlaceVec(pos, ray.field_72307_f); if (time == AutoCrystal.SwingTime.Pre) { Swing.Packet.swing(hand); Swing.Client.swing(hand); }  Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, ray.field_178784_b, hand, f[0], f[1], f[2])); this.module.sequentialHelper.setExpecting(pos); if (time == AutoCrystal.SwingTime.Post) { Swing.Packet.swing(hand); Swing.Client.swing(hand); }  }  }sleep, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ServerTimeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */