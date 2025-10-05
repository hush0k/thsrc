/*     */ package com.mrzak34.thunderhack.util;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IEntityPlayerSP;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class InteractionUtil {
/*  25 */   private static final List<Block> shiftBlocks = Arrays.asList(new Block[] { Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, (Block)Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn, Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private static final Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   public static boolean canPlaceNormally() {
/*  57 */     return true;
/*     */   }
/*     */   
/*     */   public static Placement preparePlacement(BlockPos pos, boolean rotate, EventSync e) {
/*  61 */     return preparePlacement(pos, rotate, false, e);
/*     */   }
/*     */   
/*     */   public static boolean canPlaceNormally(boolean rotate) {
/*  65 */     if (!rotate) return true; 
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public static Placement preparePlacement(BlockPos pos, boolean rotate, boolean instant, EventSync e) {
/*  70 */     return preparePlacement(pos, rotate, instant, false, e);
/*     */   }
/*     */   
/*     */   public static Placement preparePlacement(BlockPos pos, boolean rotate, boolean instant, boolean strictDirection, EventSync e) {
/*  74 */     return preparePlacement(pos, rotate, instant, strictDirection, false, e);
/*     */   }
/*     */   
/*     */   public static Placement preparePlacement(BlockPos pos, boolean rotate, boolean instant, boolean strictDirection, boolean rayTrace, EventSync e) {
/*  78 */     EnumFacing side = null;
/*  79 */     Vec3d hitVec = null;
/*  80 */     double dist = 69420.0D;
/*  81 */     for (EnumFacing facing : getPlacableFacings(pos, strictDirection, rayTrace)) {
/*  82 */       BlockPos tempNeighbour = pos.func_177972_a(facing);
/*  83 */       Vec3d tempVec = (new Vec3d((Vec3i)tempNeighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(facing.func_176730_m())).func_186678_a(0.5D));
/*  84 */       if (mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D).func_72438_d(tempVec) < dist) {
/*  85 */         side = facing;
/*  86 */         hitVec = tempVec;
/*     */       } 
/*     */     } 
/*  89 */     if (side == null) {
/*  90 */       return null;
/*     */     }
/*  92 */     BlockPos neighbour = pos.func_177972_a(side);
/*  93 */     EnumFacing opposite = side.func_176734_d();
/*  94 */     float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), hitVec);
/*  95 */     if (rotate) {
/*  96 */       if (instant) {
/*  97 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], mc.field_71439_g.field_70122_E));
/*  98 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedYaw(angle[0]);
/*  99 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedPitch(angle[1]);
/*     */       } else {
/* 101 */         mc.field_71439_g.field_70125_A = angle[1];
/* 102 */         mc.field_71439_g.field_70177_z = angle[0];
/*     */       } 
/*     */     }
/*     */     
/* 106 */     return new Placement(neighbour, opposite, hitVec, angle[0], angle[1]);
/*     */   }
/*     */   
/*     */   public static Placement preparePlacement(BlockPos pos, boolean rotate, boolean instant) {
/* 110 */     return preparePlacement(pos, rotate, instant, false);
/*     */   }
/*     */   
/*     */   public static Placement preparePlacement(BlockPos pos, boolean rotate, boolean instant, boolean strictDirection) {
/* 114 */     return preparePlacement(pos, rotate, instant, strictDirection, false);
/*     */   }
/*     */   
/*     */   public static Placement preparePlacement(BlockPos pos, boolean rotate, boolean instant, boolean strictDirection, boolean rayTrace) {
/* 118 */     EnumFacing side = null;
/* 119 */     Vec3d hitVec = null;
/* 120 */     double dist = 69420.0D;
/* 121 */     for (EnumFacing facing : getPlacableFacings(pos, strictDirection, rayTrace)) {
/* 122 */       BlockPos tempNeighbour = pos.func_177972_a(facing);
/* 123 */       Vec3d tempVec = (new Vec3d((Vec3i)tempNeighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(facing.func_176730_m())).func_186678_a(0.5D));
/* 124 */       if (mc.field_71439_g.func_174791_d().func_72441_c(0.0D, mc.field_71439_g.func_70047_e(), 0.0D).func_72438_d(tempVec) < dist) {
/* 125 */         side = facing;
/* 126 */         hitVec = tempVec;
/*     */       } 
/*     */     } 
/* 129 */     if (side == null) {
/* 130 */       return null;
/*     */     }
/* 132 */     BlockPos neighbour = pos.func_177972_a(side);
/* 133 */     EnumFacing opposite = side.func_176734_d();
/* 134 */     float[] angle = SilentRotationUtil.calculateAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), hitVec);
/* 135 */     if (rotate) {
/* 136 */       if (instant) {
/* 137 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(angle[0], angle[1], mc.field_71439_g.field_70122_E));
/* 138 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedYaw(angle[0]);
/* 139 */         ((IEntityPlayerSP)mc.field_71439_g).setLastReportedPitch(angle[1]);
/*     */       } else {
/* 141 */         mc.field_71439_g.field_70177_z = angle[0];
/* 142 */         mc.field_71439_g.field_70125_A = angle[1];
/*     */       } 
/*     */     }
/*     */     
/* 146 */     return new Placement(neighbour, opposite, hitVec, angle[0], angle[1]);
/*     */   }
/*     */   
/*     */   public static void placeBlockSafely(Placement placement, EnumHand hand, boolean packet) {
/* 150 */     boolean isSprinting = mc.field_71439_g.func_70051_ag();
/* 151 */     boolean shouldSneak = BlockUtils.shouldSneakWhileRightClicking(placement.getNeighbour());
/*     */     
/* 153 */     if (isSprinting) {
/* 154 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */     }
/*     */     
/* 157 */     if (shouldSneak) {
/* 158 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */     }
/*     */     
/* 161 */     placeBlock(placement, hand, packet);
/*     */     
/* 163 */     if (shouldSneak) {
/* 164 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */     }
/*     */     
/* 167 */     if (isSprinting) {
/* 168 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void placeBlock(Placement placement, EnumHand hand, boolean packet) {
/* 173 */     rightClickBlock(placement.getNeighbour(), placement.getHitVec(), hand, placement.getOpposite(), packet, true);
/*     */   }
/*     */   
/*     */   public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet, boolean swing) {
/* 177 */     if (packet) {
/* 178 */       float dX = (float)(vec.field_72450_a - pos.func_177958_n());
/* 179 */       float dY = (float)(vec.field_72448_b - pos.func_177956_o());
/* 180 */       float dZ = (float)(vec.field_72449_c - pos.func_177952_p());
/* 181 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, dX, dY, dZ));
/*     */     } else {
/* 183 */       mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, direction, vec, hand);
/*     */     } 
/* 185 */     if (swing) {
/* 186 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(hand));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canPlaceBlock(BlockPos pos, boolean strictDirection) {
/* 192 */     return canPlaceBlock(pos, strictDirection, true);
/*     */   }
/*     */   
/*     */   public static boolean canPlaceBlock(BlockPos pos, boolean strictDirection, boolean checkEntities) {
/* 196 */     return canPlaceBlock(pos, strictDirection, false, checkEntities);
/*     */   }
/*     */   
/*     */   public static boolean canPlaceBlock(BlockPos pos, boolean strictDirection, boolean rayTrace, boolean checkEntities) {
/* 200 */     Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
/* 201 */     if (!(block instanceof net.minecraft.block.BlockAir) && !(block instanceof net.minecraft.block.BlockLiquid) && !(block instanceof net.minecraft.block.BlockTallGrass) && !(block instanceof net.minecraft.block.BlockFire) && !(block instanceof net.minecraft.block.BlockDeadBush) && !(block instanceof net.minecraft.block.BlockSnow)) {
/* 202 */       return false;
/*     */     }
/* 204 */     if (checkEntities) {
/* 205 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(pos))) {
/* 206 */         if (entity instanceof net.minecraft.entity.item.EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb)
/* 207 */           continue;  return false;
/*     */       } 
/*     */     }
/* 210 */     for (EnumFacing side : getPlacableFacings(pos, strictDirection, rayTrace)) {
/* 211 */       if (!canClick(pos.func_177972_a(side)))
/* 212 */         continue;  return true;
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean canClick(BlockPos pos) {
/* 218 */     return mc.field_71441_e.func_180495_p(pos).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(pos), false);
/*     */   }
/*     */   
/*     */   public static List<EnumFacing> getPlacableFacings(BlockPos pos, boolean strictDirection, boolean rayTrace) {
/* 222 */     ArrayList<EnumFacing> validFacings = new ArrayList<>();
/* 223 */     for (EnumFacing side : EnumFacing.values()) {
/* 224 */       if (rayTrace) {
/* 225 */         Vec3d testVec = (new Vec3d((Vec3i)pos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(side.func_176730_m())).func_186678_a(0.5D));
/* 226 */         RayTraceResult result = mc.field_71441_e.func_72933_a(mc.field_71439_g.func_174824_e(1.0F), testVec);
/* 227 */         if (result != null && result.field_72313_a != RayTraceResult.Type.MISS) {
/* 228 */           System.out.println("weary");
/*     */           continue;
/*     */         } 
/*     */       } 
/* 232 */       BlockPos neighbour = pos.func_177972_a(side);
/* 233 */       if (strictDirection) {
/* 234 */         Vec3d eyePos = mc.field_71439_g.func_174824_e(1.0F);
/* 235 */         Vec3d blockCenter = new Vec3d(neighbour.func_177958_n() + 0.5D, neighbour.func_177956_o() + 0.5D, neighbour.func_177952_p() + 0.5D);
/* 236 */         IBlockState iBlockState = mc.field_71441_e.func_180495_p(neighbour);
/* 237 */         boolean isFullBox = (iBlockState.func_177230_c() == Blocks.field_150350_a || iBlockState.func_185913_b());
/* 238 */         ArrayList<EnumFacing> validAxis = new ArrayList<>();
/* 239 */         validAxis.addAll(checkAxis(eyePos.field_72450_a - blockCenter.field_72450_a, EnumFacing.WEST, EnumFacing.EAST, !isFullBox));
/* 240 */         validAxis.addAll(checkAxis(eyePos.field_72448_b - blockCenter.field_72448_b, EnumFacing.DOWN, EnumFacing.UP, true));
/* 241 */         validAxis.addAll(checkAxis(eyePos.field_72449_c - blockCenter.field_72449_c, EnumFacing.NORTH, EnumFacing.SOUTH, !isFullBox));
/* 242 */         if (!validAxis.contains(side.func_176734_d()))
/*     */           continue; 
/* 244 */       }  IBlockState blockState = mc.field_71441_e.func_180495_p(neighbour);
/* 245 */       if (blockState != null && blockState.func_177230_c().func_176209_a(blockState, false) && !blockState.func_185904_a().func_76222_j())
/*     */       {
/* 247 */         validFacings.add(side); }  continue;
/*     */     } 
/* 249 */     return validFacings;
/*     */   }
/*     */   
/*     */   public static ArrayList<EnumFacing> checkAxis(double diff, EnumFacing negativeSide, EnumFacing positiveSide, boolean bothIfInRange) {
/* 253 */     ArrayList<EnumFacing> valid = new ArrayList<>();
/* 254 */     if (diff < -0.5D) {
/* 255 */       valid.add(negativeSide);
/*     */     }
/* 257 */     if (diff > 0.5D) {
/* 258 */       valid.add(positiveSide);
/*     */     }
/* 260 */     if (bothIfInRange) {
/* 261 */       if (!valid.contains(negativeSide)) valid.add(negativeSide); 
/* 262 */       if (!valid.contains(positiveSide)) valid.add(positiveSide); 
/*     */     } 
/* 264 */     return valid;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void placeBlock(BlockPos position, boolean strict) {
/* 269 */     for (EnumFacing direction : EnumFacing.values()) {
/* 270 */       BlockPos directionOffset = position.func_177972_a(direction);
/*     */       
/* 272 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(position))) {
/* 273 */         if (!(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 278 */       EnumFacing oppositeFacing = direction.func_176734_d();
/* 279 */       if (!strict || getVisibleSides(directionOffset).contains(direction.func_176734_d()))
/*     */       {
/*     */         
/* 282 */         if (!mc.field_71441_e.func_180495_p(directionOffset).func_185904_a().func_76222_j()) {
/*     */ 
/*     */ 
/*     */           
/* 286 */           boolean sneak = (shiftBlocks.contains(mc.field_71441_e.func_180495_p(directionOffset).func_177230_c()) && !mc.field_71439_g.func_70093_af());
/* 287 */           if (sneak) {
/* 288 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */           }
/*     */           
/* 291 */           float[] angle = getAnglesToBlock(directionOffset, oppositeFacing);
/*     */           
/* 293 */           Vec3d interactVector = null;
/*     */           
/* 295 */           if (strict) {
/* 296 */             RayTraceResult result = getTraceResult(mc.field_71442_b.func_78757_d(), angle[0], angle[1]);
/* 297 */             if (result != null && result.field_72313_a.equals(RayTraceResult.Type.BLOCK)) {
/* 298 */               interactVector = result.field_72307_f;
/*     */             }
/*     */           } 
/*     */           
/* 302 */           if (interactVector == null) {
/* 303 */             interactVector = (new Vec3d((Vec3i)directionOffset)).func_72441_c(0.5D, 0.5D, 0.5D);
/*     */           }
/*     */           
/* 306 */           mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, directionOffset, direction.func_176734_d(), interactVector, EnumHand.MAIN_HAND);
/*     */           
/* 308 */           if (sneak) {
/* 309 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */           }
/* 311 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*     */           break;
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   public static void placeBlock(BlockPos position, boolean strict, boolean rotate) {
/* 317 */     for (EnumFacing direction : EnumFacing.values()) {
/* 318 */       BlockPos directionOffset = position.func_177972_a(direction);
/*     */       
/* 320 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(position))) {
/* 321 */         if (!(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 326 */       EnumFacing oppositeFacing = direction.func_176734_d();
/* 327 */       if (!strict || getVisibleSides(directionOffset).contains(direction.func_176734_d()))
/*     */       {
/*     */         
/* 330 */         if (!mc.field_71441_e.func_180495_p(directionOffset).func_185904_a().func_76222_j()) {
/*     */ 
/*     */ 
/*     */           
/* 334 */           boolean sneak = (shiftBlocks.contains(mc.field_71441_e.func_180495_p(directionOffset).func_177230_c()) && !mc.field_71439_g.func_70093_af());
/* 335 */           if (sneak) {
/* 336 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*     */           }
/*     */           
/* 339 */           float[] angle = getAnglesToBlock(directionOffset, oppositeFacing);
/*     */           
/* 341 */           if (rotate) {
/* 342 */             mc.field_71439_g.field_70177_z = angle[0];
/* 343 */             mc.field_71439_g.field_70125_A = angle[1];
/*     */           } 
/*     */           
/* 346 */           Vec3d interactVector = null;
/*     */           
/* 348 */           if (strict) {
/* 349 */             RayTraceResult result = getTraceResult(mc.field_71442_b.func_78757_d(), angle[0], angle[1]);
/* 350 */             if (result != null && result.field_72313_a.equals(RayTraceResult.Type.BLOCK)) {
/* 351 */               interactVector = result.field_72307_f;
/*     */             }
/*     */           } 
/*     */           
/* 355 */           if (interactVector == null) {
/* 356 */             interactVector = (new Vec3d((Vec3i)directionOffset)).func_72441_c(0.5D, 0.5D, 0.5D);
/*     */           }
/*     */           
/* 359 */           mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, directionOffset, direction.func_176734_d(), interactVector, EnumHand.MAIN_HAND);
/*     */           
/* 361 */           if (sneak) {
/* 362 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */           }
/* 364 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*     */           break;
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   public static float[] getAnglesToBlock(BlockPos pos, EnumFacing facing) {
/* 370 */     Vec3d diff = new Vec3d(pos.func_177958_n() + 0.5D - mc.field_71439_g.field_70165_t + facing.func_82601_c() / 2.0D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D - mc.field_71439_g.field_70161_v + facing.func_82599_e() / 2.0D);
/* 371 */     double distance = Math.sqrt(diff.field_72450_a * diff.field_72450_a + diff.field_72449_c * diff.field_72449_c);
/* 372 */     float yaw = (float)(Math.atan2(diff.field_72449_c, diff.field_72450_a) * 180.0D / Math.PI - 90.0D);
/* 373 */     float pitch = (float)(Math.atan2(mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e() - diff.field_72448_b, distance) * 180.0D / Math.PI);
/* 374 */     return new float[] { MathHelper.func_76142_g(yaw), MathHelper.func_76142_g(pitch) };
/*     */   }
/*     */   
/*     */   public static RayTraceResult getTraceResult(double distance, float yaw, float pitch) {
/* 378 */     Vec3d eyes = mc.field_71439_g.func_174824_e(1.0F);
/* 379 */     Vec3d rotationVector = getVectorForRotation(yaw, pitch);
/* 380 */     return mc.field_71441_e.func_147447_a(eyes, eyes.func_72441_c(rotationVector.field_72450_a * distance, rotationVector.field_72448_b * distance, rotationVector.field_72449_c * distance), false, false, true);
/*     */   }
/*     */   
/*     */   public static Vec3d getVectorForRotation(float yaw, float pitch) {
/* 384 */     float yawCos = MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F);
/* 385 */     float yawSin = MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F);
/* 386 */     float pitchCos = -MathHelper.func_76134_b(-pitch * 0.017453292F);
/* 387 */     float pitchSin = MathHelper.func_76126_a(-pitch * 0.017453292F);
/* 388 */     return new Vec3d((yawSin * pitchCos), pitchSin, (yawCos * pitchCos));
/*     */   }
/*     */   
/*     */   public static List<EnumFacing> getVisibleSides(BlockPos position) {
/* 392 */     List<EnumFacing> visibleSides = new ArrayList<>();
/*     */     
/* 394 */     Vec3d positionVector = (new Vec3d((Vec3i)position)).func_72441_c(0.5D, 0.5D, 0.5D);
/*     */     
/* 396 */     double facingX = (mc.field_71439_g.func_174824_e(1.0F)).field_72450_a - positionVector.field_72450_a;
/* 397 */     double facingY = (mc.field_71439_g.func_174824_e(1.0F)).field_72448_b - positionVector.field_72448_b;
/* 398 */     double facingZ = (mc.field_71439_g.func_174824_e(1.0F)).field_72449_c - positionVector.field_72449_c;
/*     */     
/* 400 */     if (facingX < -0.5D) {
/* 401 */       visibleSides.add(EnumFacing.WEST);
/* 402 */     } else if (facingX > 0.5D) {
/* 403 */       visibleSides.add(EnumFacing.EAST);
/* 404 */     } else if (!mc.field_71441_e.func_180495_p(position).func_185913_b() || !mc.field_71441_e.func_175623_d(position)) {
/* 405 */       visibleSides.add(EnumFacing.WEST);
/* 406 */       visibleSides.add(EnumFacing.EAST);
/*     */     } 
/*     */     
/* 409 */     if (facingY < -0.5D) {
/* 410 */       visibleSides.add(EnumFacing.DOWN);
/* 411 */     } else if (facingY > 0.5D) {
/* 412 */       visibleSides.add(EnumFacing.UP);
/*     */     } else {
/* 414 */       visibleSides.add(EnumFacing.DOWN);
/* 415 */       visibleSides.add(EnumFacing.UP);
/*     */     } 
/*     */     
/* 418 */     if (facingZ < -0.5D) {
/* 419 */       visibleSides.add(EnumFacing.NORTH);
/* 420 */     } else if (facingZ > 0.5D) {
/* 421 */       visibleSides.add(EnumFacing.SOUTH);
/* 422 */     } else if (!mc.field_71441_e.func_180495_p(position).func_185913_b() || !mc.field_71441_e.func_175623_d(position)) {
/* 423 */       visibleSides.add(EnumFacing.NORTH);
/* 424 */       visibleSides.add(EnumFacing.SOUTH);
/*     */     } 
/*     */     
/* 427 */     return visibleSides;
/*     */   }
/*     */   
/*     */   public static class Placement {
/*     */     private final BlockPos neighbour;
/*     */     private final EnumFacing opposite;
/*     */     private final Vec3d hitVec;
/*     */     private final float yaw;
/*     */     private final float pitch;
/*     */     
/*     */     public Placement(BlockPos neighbour, EnumFacing opposite, Vec3d hitVec, float yaw, float pitch) {
/* 438 */       this.neighbour = neighbour;
/* 439 */       this.opposite = opposite;
/* 440 */       this.hitVec = hitVec;
/* 441 */       this.yaw = yaw;
/* 442 */       this.pitch = pitch;
/*     */     }
/*     */     
/*     */     public BlockPos getNeighbour() {
/* 446 */       return this.neighbour;
/*     */     }
/*     */     
/*     */     public EnumFacing getOpposite() {
/* 450 */       return this.opposite;
/*     */     }
/*     */     
/*     */     public Vec3d getHitVec() {
/* 454 */       return this.hitVec;
/*     */     }
/*     */     
/*     */     public float getYaw() {
/* 458 */       return this.yaw;
/*     */     }
/*     */     
/*     */     public float getPitch() {
/* 462 */       return this.pitch;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\InteractionUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */