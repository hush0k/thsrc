/*     */ package com.mrzak34.thunderhack.util;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IMinecraft;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class BlockUtils {
/*  32 */   public static final List<Block> blackList = Arrays.asList(new Block[] { Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, (Block)Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn });
/*  33 */   public static final List<Block> shulkerList = Arrays.asList(new Block[] { Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA });
/*  34 */   private static final Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */   
/*     */   public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
/*  38 */     if (packet) {
/*  39 */       float f = (float)(vec.field_72450_a - pos.func_177958_n());
/*  40 */       float f1 = (float)(vec.field_72448_b - pos.func_177956_o());
/*  41 */       float f2 = (float)(vec.field_72449_c - pos.func_177952_p());
/*  42 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
/*  43 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(hand));
/*     */     } else {
/*  45 */       mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, direction, vec, hand);
/*  46 */       mc.field_71439_g.func_184609_a(hand);
/*     */     } 
/*  48 */     ((IMinecraft)mc).setRightClickDelayTimer(4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Optional<ClickLocation> generateClickLocation(BlockPos pos) {
/*  53 */     return generateClickLocation(pos, false, false, false);
/*     */   }
/*     */   public static Optional<ClickLocation> generateClickLocation(BlockPos pos, boolean ignoreEntities, boolean noPistons) {
/*  56 */     return generateClickLocation(pos, ignoreEntities, noPistons, false);
/*     */   }
/*     */   
/*     */   public static double[] calculateLookAt(double x, double y, double z, EnumFacing facing, EntityPlayer me) {
/*  60 */     return PlayerUtils.calculateLookAt(x + 0.5D + facing.func_176730_m().func_177958_n() * 0.5D, y + 0.5D + facing.func_176730_m().func_177956_o() * 0.5D, z + 0.5D + facing.func_176730_m().func_177952_p() * 0.5D, me);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Optional<ClickLocation> generateClickLocation(BlockPos pos, boolean ignoreEntities, boolean noPistons, boolean onlyCrystals) {
/*  65 */     Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
/*  66 */     if (!(block instanceof net.minecraft.block.BlockAir) && !(block instanceof net.minecraft.block.BlockLiquid)) {
/*  67 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (!ignoreEntities) {
/*  72 */       for (Entity entity : mc.field_71441_e.func_72839_b(null, new AxisAlignedBB(pos))) {
/*  73 */         if ((!onlyCrystals || !(entity instanceof net.minecraft.entity.item.EntityEnderCrystal)) && 
/*  74 */           !(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb) && !(entity instanceof net.minecraft.entity.projectile.EntityArrow)) {
/*  75 */           return Optional.empty();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  81 */     EnumFacing side = null;
/*  82 */     for (EnumFacing blockSide : EnumFacing.values()) {
/*  83 */       BlockPos sidePos = pos.func_177972_a(blockSide);
/*  84 */       if (!noPistons || 
/*  85 */         mc.field_71441_e.func_180495_p(sidePos).func_177230_c() != Blocks.field_150331_J)
/*     */       {
/*  87 */         if (mc.field_71441_e.func_180495_p(sidePos).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(sidePos), false)) {
/*     */ 
/*     */           
/*  90 */           IBlockState blockState = mc.field_71441_e.func_180495_p(sidePos);
/*  91 */           if (!blockState.func_185904_a().func_76222_j()) {
/*  92 */             side = blockSide; break;
/*     */           } 
/*     */         }  } 
/*     */     } 
/*  96 */     if (side == null) {
/*  97 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/* 101 */     BlockPos neighbour = pos.func_177972_a(side);
/* 102 */     EnumFacing opposite = side.func_176734_d();
/* 103 */     if (!mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbour), false)) {
/* 104 */       return Optional.empty();
/*     */     }
/*     */     
/* 107 */     return Optional.of(new ClickLocation(neighbour, opposite));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean shouldSneakWhileRightClicking(BlockPos blockPos) {
/* 112 */     Block block = mc.field_71441_e.func_180495_p(blockPos).func_177230_c();
/* 113 */     TileEntity tileEntity = null;
/* 114 */     for (TileEntity tE : mc.field_71441_e.field_147482_g) {
/* 115 */       if (!tE.func_174877_v().equals(blockPos))
/* 116 */         continue;  tileEntity = tE;
/*     */     } 
/*     */     
/* 119 */     return (tileEntity != null || block instanceof net.minecraft.block.BlockBed || block instanceof net.minecraft.block.BlockContainer || block instanceof net.minecraft.block.BlockDoor || block instanceof net.minecraft.block.BlockTrapDoor || block instanceof net.minecraft.block.BlockFenceGate || block instanceof net.minecraft.block.BlockButton || block instanceof net.minecraft.block.BlockAnvil || block instanceof net.minecraft.block.BlockWorkbench || block instanceof net.minecraft.block.BlockCake || block instanceof net.minecraft.block.BlockRedstoneDiode);
/*     */   }
/*     */   
/*     */   public static boolean validObi(BlockPos pos) {
/* 123 */     return (!validBedrock(pos) && (mc.field_71441_e
/* 124 */       .func_180495_p(pos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 125 */       .func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 126 */       .func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 127 */       .func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 128 */       .func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h) && mc.field_71441_e
/* 129 */       .func_180495_p(pos).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 130 */       .func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 131 */       .func_180495_p(pos.func_177982_a(0, 2, 0)).func_185904_a() == Material.field_151579_a);
/*     */   }
/*     */   
/*     */   public static boolean validBedrock(BlockPos pos) {
/* 135 */     return (mc.field_71441_e.func_180495_p(pos.func_177982_a(0, -1, 0)).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 136 */       .func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 137 */       .func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 138 */       .func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 139 */       .func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 140 */       .func_180495_p(pos).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 141 */       .func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 142 */       .func_180495_p(pos.func_177982_a(0, 2, 0)).func_185904_a() == Material.field_151579_a);
/*     */   }
/*     */   
/*     */   public static BlockPos validTwoBlockObiXZ(BlockPos pos) {
/* 146 */     if ((mc.field_71441_e
/* 147 */       .func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 148 */       .func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 149 */       .func_180495_p(pos.func_177968_d()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 150 */       .func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150357_h) && mc.field_71441_e
/* 151 */       .func_180495_p(pos).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 152 */       .func_180495_p(pos.func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 153 */       .func_180495_p(pos.func_177981_b(2)).func_185904_a() == Material.field_151579_a && (mc.field_71441_e
/* 154 */       .func_180495_p(pos.func_177974_f().func_177977_b()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177974_f().func_177977_b()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 155 */       .func_180495_p(pos.func_177965_g(2)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177965_g(2)).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 156 */       .func_180495_p(pos.func_177974_f().func_177968_d()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177974_f().func_177968_d()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 157 */       .func_180495_p(pos.func_177974_f().func_177978_c()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177974_f().func_177978_c()).func_177230_c() == Blocks.field_150357_h) && mc.field_71441_e
/* 158 */       .func_180495_p(pos.func_177974_f()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 159 */       .func_180495_p(pos.func_177974_f().func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 160 */       .func_180495_p(pos.func_177974_f().func_177981_b(2)).func_185904_a() == Material.field_151579_a)
/*     */     {
/* 162 */       return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(1, 0, 0) : null; } 
/* 163 */     if ((mc.field_71441_e
/* 164 */       .func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 165 */       .func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 166 */       .func_180495_p(pos.func_177974_f()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 167 */       .func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150357_h) && mc.field_71441_e
/* 168 */       .func_180495_p(pos).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 169 */       .func_180495_p(pos.func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 170 */       .func_180495_p(pos.func_177981_b(2)).func_185904_a() == Material.field_151579_a && (mc.field_71441_e
/* 171 */       .func_180495_p(pos.func_177968_d().func_177977_b()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177968_d().func_177977_b()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 172 */       .func_180495_p(pos.func_177970_e(2)).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177970_e(2)).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 173 */       .func_180495_p(pos.func_177968_d().func_177974_f()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177968_d().func_177974_f()).func_177230_c() == Blocks.field_150357_h) && (mc.field_71441_e
/* 174 */       .func_180495_p(pos.func_177968_d().func_177976_e()).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(pos.func_177968_d().func_177976_e()).func_177230_c() == Blocks.field_150357_h) && mc.field_71441_e
/* 175 */       .func_180495_p(pos.func_177968_d()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 176 */       .func_180495_p(pos.func_177968_d().func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 177 */       .func_180495_p(pos.func_177968_d().func_177981_b(2)).func_185904_a() == Material.field_151579_a)
/*     */     {
/* 179 */       return (validTwoBlockBedrockXZ(pos) == null) ? new BlockPos(0, 0, 1) : null;
/*     */     }
/* 181 */     return null;
/*     */   }
/*     */   
/*     */   public static BlockPos validTwoBlockBedrockXZ(BlockPos pos) {
/* 185 */     if (mc.field_71441_e
/* 186 */       .func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 187 */       .func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 188 */       .func_180495_p(pos.func_177968_d()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 189 */       .func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 190 */       .func_180495_p(pos).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 191 */       .func_180495_p(pos.func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 192 */       .func_180495_p(pos.func_177981_b(2)).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 193 */       .func_180495_p(pos.func_177974_f().func_177977_b()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 194 */       .func_180495_p(pos.func_177965_g(2)).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 195 */       .func_180495_p(pos.func_177974_f().func_177968_d()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 196 */       .func_180495_p(pos.func_177974_f().func_177978_c()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 197 */       .func_180495_p(pos.func_177974_f()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 198 */       .func_180495_p(pos.func_177974_f().func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 199 */       .func_180495_p(pos.func_177974_f().func_177981_b(2)).func_185904_a() == Material.field_151579_a)
/*     */     {
/* 201 */       return new BlockPos(1, 0, 0); } 
/* 202 */     if (mc.field_71441_e
/* 203 */       .func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 204 */       .func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 205 */       .func_180495_p(pos.func_177974_f()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 206 */       .func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 207 */       .func_180495_p(pos).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 208 */       .func_180495_p(pos.func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 209 */       .func_180495_p(pos.func_177981_b(2)).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 210 */       .func_180495_p(pos.func_177968_d().func_177977_b()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 211 */       .func_180495_p(pos.func_177970_e(2)).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 212 */       .func_180495_p(pos.func_177968_d().func_177974_f()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 213 */       .func_180495_p(pos.func_177968_d().func_177976_e()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e
/* 214 */       .func_180495_p(pos.func_177968_d()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 215 */       .func_180495_p(pos.func_177968_d().func_177984_a()).func_185904_a() == Material.field_151579_a && mc.field_71441_e
/* 216 */       .func_180495_p(pos.func_177968_d().func_177981_b(2)).func_185904_a() == Material.field_151579_a)
/*     */     {
/* 218 */       return new BlockPos(0, 0, 1);
/*     */     }
/* 220 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isHole(BlockPos pos) {
/* 224 */     return (validObi(pos) || validBedrock(pos));
/*     */   }
/*     */   
/*     */   public static EntityPlayer getRotationPlayer() {
/* 228 */     EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/* 229 */     return (entityPlayerSP == null) ? (EntityPlayer)Util.mc.field_71439_g : (EntityPlayer)entityPlayerSP;
/*     */   }
/*     */   
/*     */   public static double getDistanceSq(BlockPos pos) {
/* 233 */     return getDistanceSq((Entity)getRotationPlayer(), pos);
/*     */   }
/*     */   
/*     */   public static double getDistanceSq(Entity from, BlockPos to) {
/* 237 */     return from.func_174831_c(to);
/*     */   }
/*     */   
/*     */   public static BlockPos getPosition(Entity entity) {
/* 241 */     return getPosition(entity, 0.0D);
/*     */   }
/*     */   
/*     */   public static BlockPos getPosition(Entity entity, double yOffset) {
/* 245 */     double y = entity.field_70163_u + yOffset;
/* 246 */     if (entity.field_70163_u - Math.floor(entity.field_70163_u) > 0.5D) {
/* 247 */       y = Math.ceil(entity.field_70163_u);
/*     */     }
/* 249 */     return new BlockPos(entity.field_70165_t, y, entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public static double getEyeHeight() {
/* 253 */     return getEyeHeight((Entity)Util.mc.field_71439_g);
/*     */   }
/*     */   
/*     */   public static double getEyeHeight(Entity entity) {
/* 257 */     return entity.field_70163_u + entity.func_70047_e();
/*     */   }
/*     */   
/*     */   public static boolean isAir(BlockPos pos) {
/* 261 */     return (mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a);
/*     */   }
/*     */   
/*     */   public static List<EnumFacing> getPossibleSides(BlockPos pos) {
/* 265 */     ArrayList<EnumFacing> facings = new ArrayList<>();
/* 266 */     for (EnumFacing side : EnumFacing.values()) {
/*     */       
/* 268 */       BlockPos neighbour = pos.func_177972_a(side); IBlockState blockState;
/* 269 */       if (mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbour), false) && !(blockState = mc.field_71441_e.func_180495_p(neighbour)).func_185904_a().func_76222_j())
/*     */       {
/* 271 */         facings.add(side); } 
/*     */     } 
/* 273 */     return facings;
/*     */   }
/*     */   
/*     */   public static Block getBlock(double x, double y, double z) {
/* 277 */     return mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
/*     */   }
/*     */   
/*     */   public static ArrayList<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
/* 281 */     ArrayList<BlockPos> blocks = new ArrayList<>();
/* 282 */     BlockPos min = new BlockPos(Math.min(from.func_177958_n(), to.func_177958_n()), Math.min(from.func_177956_o(), to.func_177956_o()), Math.min(from.func_177952_p(), to.func_177952_p()));
/* 283 */     BlockPos max = new BlockPos(Math.max(from.func_177958_n(), to.func_177958_n()), Math.max(from.func_177956_o(), to.func_177956_o()), Math.max(from.func_177952_p(), to.func_177952_p()));
/* 284 */     for (int x = min.func_177958_n(); x <= max.func_177958_n(); x++) {
/* 285 */       for (int y = min.func_177956_o(); y <= max.func_177956_o(); y++) {
/* 286 */         for (int z = min.func_177952_p(); z <= max.func_177952_p(); z++) {
/* 287 */           blocks.add(new BlockPos(x, y, z));
/*     */         }
/*     */       } 
/*     */     } 
/* 291 */     return blocks; } public static EnumFacing getFacing(BlockPos pos) {
/*     */     EnumFacing[] arrayOfEnumFacing;
/*     */     int i;
/*     */     byte b;
/* 295 */     for (arrayOfEnumFacing = EnumFacing.values(), i = arrayOfEnumFacing.length, b = 0; b < i; ) { EnumFacing facing = arrayOfEnumFacing[b];
/* 296 */       RayTraceResult rayTraceResult = mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(pos.func_177958_n() + 0.5D + facing.func_176730_m().func_177958_n() / 2.0D, pos.func_177956_o() + 0.5D + facing.func_176730_m().func_177956_o() / 2.0D, pos.func_177952_p() + 0.5D + facing.func_176730_m().func_177952_p() / 2.0D), false, true, false);
/* 297 */       if (rayTraceResult != null && (rayTraceResult.field_72313_a != RayTraceResult.Type.BLOCK || !rayTraceResult.func_178782_a().equals(pos))) {
/*     */         b++; continue;
/* 299 */       }  return facing; }
/*     */     
/* 301 */     if (pos.func_177956_o() > mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e()) {
/* 302 */       return EnumFacing.DOWN;
/*     */     }
/* 304 */     return EnumFacing.UP;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFirstFacing(BlockPos pos) {
/* 308 */     Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
/* 309 */     if (iterator.hasNext()) {
/* 310 */       EnumFacing facing = iterator.next();
/* 311 */       return facing;
/*     */     } 
/* 313 */     return null;
/*     */   }
/*     */   
/*     */   public static void rightClickBlock2(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
/* 317 */     if (packet) {
/* 318 */       float f = (float)(vec.field_72450_a - pos.func_177958_n());
/* 319 */       float f1 = (float)(vec.field_72448_b - pos.func_177956_o());
/* 320 */       float f2 = (float)(vec.field_72449_c - pos.func_177952_p());
/* 321 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
/*     */     } else {
/* 323 */       mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, direction, vec, hand);
/*     */     } 
/* 325 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 326 */     ((IMinecraft)mc).setRightClickDelayTimer(4);
/*     */   }
/*     */   
/*     */   public static boolean placeBlockSmartRotate(BlockPos pos, EnumHand hand, boolean rotate, boolean packet, boolean isSneaking, EventSync ev) {
/* 330 */     boolean sneaking = false;
/* 331 */     EnumFacing side = getFirstFacing(pos);
/* 332 */     if (side == null) {
/* 333 */       return isSneaking;
/*     */     }
/* 335 */     BlockPos neighbour = pos.func_177972_a(side);
/* 336 */     EnumFacing opposite = side.func_176734_d();
/* 337 */     Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
/* 338 */     Block neighbourBlock = mc.field_71441_e.func_180495_p(neighbour).func_177230_c();
/* 339 */     if (!mc.field_71439_g.func_70093_af() && (blackList.contains(neighbourBlock) || shulkerList.contains(neighbourBlock))) {
/* 340 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/* 341 */       sneaking = true;
/*     */     } 
/* 343 */     if (rotate) {
/* 344 */       float[] angle = MathUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d(hitVec.field_72450_a, hitVec.field_72448_b, hitVec.field_72449_c));
/* 345 */       mc.field_71439_g.field_70125_A = angle[1];
/* 346 */       mc.field_71439_g.field_70177_z = angle[0];
/*     */     } 
/* 348 */     rightClickBlock2(neighbour, hitVec, hand, opposite, packet);
/* 349 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 350 */     ((IMinecraft)mc).setRightClickDelayTimer(4);
/* 351 */     return (sneaking || isSneaking);
/*     */   }
/*     */   
/*     */   public static List<BlockPos> getSphere(float radius, boolean ignoreAir) {
/* 355 */     ArrayList<BlockPos> sphere = new ArrayList<>();
/* 356 */     BlockPos pos = new BlockPos(Util.mc.field_71439_g.func_174791_d());
/* 357 */     int posX = pos.func_177958_n();
/* 358 */     int posY = pos.func_177956_o();
/* 359 */     int posZ = pos.func_177952_p();
/* 360 */     int radiuss = (int)radius;
/* 361 */     int x = posX - radiuss;
/* 362 */     while (x <= posX + radius) {
/* 363 */       int z = posZ - radiuss;
/* 364 */       while (z <= posZ + radius) {
/* 365 */         int y = posY - radiuss;
/* 366 */         while (y < posY + radius) {
/*     */           
/* 368 */           double dist = ((posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y)); BlockPos position;
/* 369 */           if (dist < (radius * radius) && (Util.mc.field_71441_e.func_180495_p(position = new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150350_a || !ignoreAir)) {
/* 370 */             sphere.add(position);
/*     */           }
/* 372 */           y++;
/*     */         } 
/* 374 */         z++;
/*     */       } 
/* 376 */       x++;
/*     */     } 
/* 378 */     return sphere;
/*     */   }
/*     */   
/*     */   public static Block getBlock(BlockPos pos) {
/* 382 */     return getState(pos).func_177230_c();
/*     */   }
/*     */   
/*     */   public static Block getBlockgs(BlockPos pos) {
/* 386 */     return getState(pos).func_177230_c();
/*     */   }
/*     */   
/*     */   public static IBlockState getState(BlockPos pos) {
/* 390 */     return mc.field_71441_e.func_180495_p(pos);
/*     */   }
/*     */   
/*     */   public static Boolean isPosInFov(BlockPos pos) {
/* 394 */     int dirnumber = RotationUtil.getDirection4D();
/* 395 */     if (dirnumber == 0 && pos.func_177952_p() - (mc.field_71439_g.func_174791_d()).field_72449_c < 0.0D) {
/* 396 */       return Boolean.valueOf(false);
/*     */     }
/* 398 */     if (dirnumber == 1 && pos.func_177958_n() - (mc.field_71439_g.func_174791_d()).field_72450_a > 0.0D) {
/* 399 */       return Boolean.valueOf(false);
/*     */     }
/* 401 */     if (dirnumber == 2 && pos.func_177952_p() - (mc.field_71439_g.func_174791_d()).field_72449_c > 0.0D) {
/* 402 */       return Boolean.valueOf(false);
/*     */     }
/* 404 */     return Boolean.valueOf((dirnumber != 3 || pos.func_177958_n() - (mc.field_71439_g.func_174791_d()).field_72450_a >= 0.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ClickLocation
/*     */   {
/*     */     public final BlockPos neighbour;
/*     */     
/*     */     public final EnumFacing opposite;
/*     */ 
/*     */     
/*     */     public ClickLocation(BlockPos neighbour, EnumFacing opposite) {
/* 416 */       this.neighbour = neighbour;
/* 417 */       this.opposite = opposite;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\BlockUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */