/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.HandleLiquidJumpEvent;
/*     */ import com.mrzak34.thunderhack.events.JesusEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketPlayer;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Jesus
/*     */   extends Module
/*     */ {
/*  30 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.SOLID));
/*  31 */   private final Setting<Boolean> glide = register(new Setting("Glide", Boolean.valueOf(false)));
/*  32 */   private final Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  33 */   private final Setting<Boolean> boost = register(new Setting("Boost", Boolean.valueOf(false)));
/*     */   private boolean jumping;
/*  35 */   private int glideCounter = 0;
/*     */   private float lastOffset;
/*     */   
/*     */   public Jesus() {
/*  39 */     super("Jesus", "Jesus", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static IBlockState checkIfBlockInBB(Class<? extends Block> blockClass, int minY) {
/*  43 */     for (int iX = MathHelper.func_76128_c((mc.field_71439_g.func_174813_aQ()).field_72340_a); iX < MathHelper.func_76143_f((mc.field_71439_g.func_174813_aQ()).field_72336_d); iX++) {
/*  44 */       for (int iZ = MathHelper.func_76128_c((mc.field_71439_g.func_174813_aQ()).field_72339_c); iZ < MathHelper.func_76143_f((mc.field_71439_g.func_174813_aQ()).field_72334_f); iZ++) {
/*  45 */         IBlockState state = mc.field_71441_e.func_180495_p(new BlockPos(iX, minY, iZ));
/*  46 */         if (blockClass.isInstance(state.func_177230_c())) {
/*  47 */           return state;
/*     */         }
/*     */       } 
/*     */     } 
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isInLiquid() {
/*  56 */     if (mc.field_71439_g.field_70143_R >= 3.0F) {
/*  57 */       return false;
/*     */     }
/*     */     
/*  60 */     boolean inLiquid = false;
/*  61 */     AxisAlignedBB bb = (mc.field_71439_g.func_184187_bx() != null) ? mc.field_71439_g.func_184187_bx().func_174813_aQ() : mc.field_71439_g.func_174813_aQ();
/*  62 */     int y = (int)bb.field_72338_b;
/*  63 */     for (int x = MathHelper.func_76128_c(bb.field_72340_a); x < MathHelper.func_76128_c(bb.field_72336_d) + 1; x++) {
/*  64 */       for (int z = MathHelper.func_76128_c(bb.field_72339_c); z < MathHelper.func_76128_c(bb.field_72334_f) + 1; z++) {
/*  65 */         Block block = mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
/*  66 */         if (!(block instanceof net.minecraft.block.BlockAir)) {
/*  67 */           if (!(block instanceof BlockLiquid)) {
/*  68 */             return false;
/*     */           }
/*  70 */           inLiquid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*  74 */     return inLiquid;
/*     */   }
/*     */   
/*     */   public static boolean isOnLiquid() {
/*  78 */     if (mc.field_71439_g.field_70143_R >= 3.0F) {
/*  79 */       return false;
/*     */     }
/*     */     
/*  82 */     AxisAlignedBB bb = (mc.field_71439_g.func_184187_bx() != null) ? mc.field_71439_g.func_184187_bx().func_174813_aQ().func_191195_a(0.0D, 0.0D, 0.0D).func_72317_d(0.0D, -0.05000000074505806D, 0.0D) : mc.field_71439_g.func_174813_aQ().func_191195_a(0.0D, 0.0D, 0.0D).func_72317_d(0.0D, -0.05000000074505806D, 0.0D);
/*  83 */     boolean onLiquid = false;
/*  84 */     int y = (int)bb.field_72338_b;
/*  85 */     for (int x = MathHelper.func_76128_c(bb.field_72340_a); x < MathHelper.func_76128_c(bb.field_72336_d + 1.0D); x++) {
/*  86 */       for (int z = MathHelper.func_76128_c(bb.field_72339_c); z < MathHelper.func_76128_c(bb.field_72334_f + 1.0D); z++) {
/*  87 */         Block block = mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c();
/*  88 */         if (block != Blocks.field_150350_a) {
/*  89 */           if (!(block instanceof BlockLiquid)) {
/*  90 */             return false;
/*     */           }
/*  92 */           onLiquid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*  96 */     return onLiquid;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 102 */     if (this.mode.getValue() != Mode.SOLID)
/* 103 */       return;  if (!mc.field_71439_g.field_71158_b.field_78899_d && !mc.field_71439_g.field_71158_b.field_78901_c && isInLiquid()) {
/* 104 */       mc.field_71439_g.field_70181_x = 0.1D;
/*     */     }
/* 106 */     if (isOnLiquid() && mc.field_71439_g.field_70143_R < 3.0F && !mc.field_71439_g.field_71158_b.field_78901_c && !isInLiquid() && !mc.field_71439_g.func_70093_af() && ((Boolean)this.glide.getValue()).booleanValue()) {
/* 107 */       switch (this.glideCounter) {
/*     */         case 0:
/* 109 */           mc.field_71439_g.field_70159_w *= 1.1D;
/* 110 */           mc.field_71439_g.field_70179_y *= 1.1D;
/*     */           break;
/*     */         case 1:
/* 113 */           mc.field_71439_g.field_70159_w *= 1.27D;
/* 114 */           mc.field_71439_g.field_70179_y *= 1.27D;
/*     */           break;
/*     */         case 2:
/* 117 */           mc.field_71439_g.field_70159_w *= 1.51D;
/* 118 */           mc.field_71439_g.field_70179_y *= 1.51D;
/*     */           break;
/*     */         case 3:
/* 121 */           mc.field_71439_g.field_70159_w *= 1.15D;
/* 122 */           mc.field_71439_g.field_70179_y *= 1.15D;
/*     */           break;
/*     */         case 4:
/* 125 */           mc.field_71439_g.field_70159_w *= 1.23D;
/* 126 */           mc.field_71439_g.field_70179_y *= 1.23D;
/*     */           break;
/*     */       } 
/*     */       
/* 130 */       this.glideCounter++;
/*     */       
/* 132 */       if (this.glideCounter > 4) {
/* 133 */         this.glideCounter = 0;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 140 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/* 141 */       this.glideCounter = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLiquidJump(HandleLiquidJumpEvent event) {
/* 147 */     if (this.mode.getValue() == Mode.NexusCrit || this.mode.getValue() == Mode.NexusFast) {
/*     */       return;
/*     */     }
/* 150 */     if ((mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab()) && (mc.field_71439_g.field_70181_x == 0.1D || mc.field_71439_g.field_70181_x == 0.5D)) {
/* 151 */       event.setCanceled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWalkingPlayerUpdatePre(EventSync event) {
/* 157 */     if (mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 0.25D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockLiquid && 
/* 158 */       this.mode.getValue() == Mode.NexusCrit) {
/* 159 */       if (mc.field_71439_g.func_70090_H()) {
/* 160 */         mc.field_71439_g.func_70664_aZ();
/* 161 */         mc.field_71439_g.field_70181_x /= 1.559999942779541D;
/* 162 */         mc.field_71439_g.field_70159_w /= 2.880000114440918D;
/* 163 */         mc.field_71439_g.field_70179_y /= 2.880000114440918D;
/* 164 */       } else if (mc.field_71439_g.field_70143_R > 0.24F) {
/* 165 */         mc.field_71439_g.field_70181_x = -0.20000000298023224D;
/* 166 */         mc.field_71439_g.field_70181_x /= 1.559999942779541D;
/* 167 */         mc.field_71439_g.field_70159_w /= 0.8899999856948853D;
/* 168 */         mc.field_71439_g.field_70179_y /= 0.8899999856948853D;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 173 */     if (this.mode.getValue() == Mode.NexusFast && (mc.field_71441_e
/* 174 */       .func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 175 */       .func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 176 */       .func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 2.0D, mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150355_j))
/*     */     {
/*     */       
/* 179 */       if (mc.field_71439_g.func_70090_H()) {
/* 180 */         mc.field_71439_g.func_70664_aZ();
/* 181 */         mc.field_71439_g.field_70181_x /= 1.600000023841858D;
/* 182 */         mc.field_71439_g.field_70159_w /= 4.230000019073486D;
/* 183 */         mc.field_71439_g.field_70179_y /= 4.230000019073486D;
/* 184 */       } else if (mc.field_71439_g.field_70143_R > 0.0467F) {
/* 185 */         mc.field_71439_g.field_70181_x = -0.18440000712871552D;
/* 186 */         mc.field_71439_g.field_70181_x /= 0.46000000834465027D;
/* 187 */         mc.field_71439_g.field_70159_w /= 0.23000000417232513D;
/* 188 */         mc.field_71439_g.field_70179_y /= 0.23000000417232513D;
/*     */       } else {
/* 190 */         mc.field_71439_g.field_70159_w /= 1.5D;
/* 191 */         mc.field_71439_g.field_70179_y /= 1.5D;
/*     */       } 
/*     */     }
/*     */     
/* 195 */     if (this.mode.getValue() == Mode.NCP) {
/* 196 */       double x = mc.field_71439_g.field_70165_t;
/* 197 */       double y = mc.field_71439_g.field_70163_u;
/* 198 */       double z = mc.field_71439_g.field_70161_v;
/* 199 */       Thunderhack.TICK_TIMER = 1.0F;
/* 200 */       if (mc.field_71441_e
/* 201 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 202 */         .func_180495_p(new BlockPos(x + 0.3D, y, z)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 203 */         .func_180495_p(new BlockPos(x - 0.3D, y, z)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 204 */         .func_180495_p(new BlockPos(x, y, z + 0.3D)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 205 */         .func_180495_p(new BlockPos(x, y, z - 0.3D)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 206 */         .func_180495_p(new BlockPos(x + 0.3D, y, z + 0.3D)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 207 */         .func_180495_p(new BlockPos(x - 0.3D, y, z - 0.3D)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 208 */         .func_180495_p(new BlockPos(x - 0.3D, y, z + 0.3D)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 209 */         .func_180495_p(new BlockPos(x + 0.3D, y, z - 0.3D)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 210 */         .func_180495_p(new BlockPos(x, y, z)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 211 */         .func_180495_p(new BlockPos(x + 0.3D, y, z)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 212 */         .func_180495_p(new BlockPos(x - 0.3D, y, z)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 213 */         .func_180495_p(new BlockPos(x, y, z + 0.3D)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 214 */         .func_180495_p(new BlockPos(x, y, z - 0.3D)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 215 */         .func_180495_p(new BlockPos(x + 0.3D, y, z + 0.3D)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 216 */         .func_180495_p(new BlockPos(x - 0.3D, y, z - 0.3D)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 217 */         .func_180495_p(new BlockPos(x - 0.3D, y, z + 0.3D)).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e
/* 218 */         .func_180495_p(new BlockPos(x + 0.3D, y, z - 0.3D)).func_177230_c() == Blocks.field_150353_l) {
/*     */         
/* 220 */         if (mc.field_71439_g.field_71158_b.field_78901_c || mc.field_71439_g.field_70123_F) {
/* 221 */           if (mc.field_71439_g.field_70123_F) {
/* 222 */             mc.field_71439_g.func_70107_b(x, y + 0.2D, z);
/*     */           }
/* 224 */           mc.field_71439_g.field_70122_E = true;
/*     */         } 
/*     */         
/* 227 */         mc.field_71439_g.field_70159_w = 0.0D;
/* 228 */         mc.field_71439_g.field_70181_x = 0.04D;
/* 229 */         mc.field_71439_g.field_70179_y = 0.0D;
/*     */         
/* 231 */         if (mc.field_71441_e.func_180495_p(new BlockPos(x, y, z)).func_177230_c() != Blocks.field_150353_l && 
/* 232 */           mc.field_71439_g.field_70143_R != 0.0F && mc.field_71439_g.field_70159_w == 0.0D && mc.field_71439_g.field_70179_y == 0.0D) {
/* 233 */           mc.field_71439_g.func_70107_b(x, y - 0.0400005D, z);
/* 234 */           if (mc.field_71439_g.field_70143_R < 0.08D) {
/* 235 */             mc.field_71439_g.func_70107_b(x, y + 0.2D, z);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 240 */         if (mc.field_71439_g.func_70644_a(Potion.func_188412_a(1))) {
/* 241 */           mc.field_71439_g.field_70747_aH = 0.4005F;
/*     */         } else {
/* 243 */           mc.field_71439_g.field_70747_aH = 0.2865F;
/*     */         } 
/*     */       } 
/* 246 */       LegitStrafe.setSpeed((float)MovementUtil.getSpeed());
/* 247 */       if (!mc.field_71474_y.field_74314_A.func_151470_d() && (mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab())) {
/* 248 */         mc.field_71439_g.field_70181_x = 0.12D;
/* 249 */         Thunderhack.TICK_TIMER = 1.5F;
/* 250 */         if (mc.field_71439_g.func_70090_H() && mc.field_71441_e.func_180495_p(new BlockPos(x, y + 0.9D, z)).func_177230_c() == Blocks.field_150355_j && mc.field_71441_e.func_180495_p(new BlockPos(x, y + 1.0D, z)).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(new BlockPos(x, y - 1.0D, z)).func_177230_c() != Blocks.field_150355_j) {
/* 251 */           mc.field_71439_g.field_70163_u += 0.1D;
/* 252 */           mc.field_71439_g.field_70181_x = 0.42D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 258 */     if (this.mode.getValue() == Mode.TRAMPOLINE) {
/* 259 */       int minY = MathHelper.func_76128_c((mc.field_71439_g.func_174813_aQ()).field_72338_b - 0.2D);
/* 260 */       boolean inLiquid = (checkIfBlockInBB((Class)BlockLiquid.class, minY) != null);
/*     */       
/* 262 */       if (inLiquid && !mc.field_71439_g.func_70093_af()) {
/* 263 */         mc.field_71439_g.field_70122_E = false;
/*     */       }
/*     */       
/* 266 */       Block block = mc.field_71441_e.func_180495_p(new BlockPos((int)Math.floor(mc.field_71439_g.field_70165_t), (int)Math.floor(mc.field_71439_g.field_70163_u), (int)Math.floor(mc.field_71439_g.field_70161_v))).func_177230_c();
/*     */       
/* 268 */       if (this.jumping && !mc.field_71439_g.field_71075_bZ.field_75100_b && !mc.field_71439_g.func_70090_H()) {
/* 269 */         if (mc.field_71439_g.field_70181_x < -0.3D || mc.field_71439_g.field_70122_E || mc.field_71439_g.func_70617_f_()) {
/* 270 */           this.jumping = false;
/*     */           
/*     */           return;
/*     */         } 
/* 274 */         mc.field_71439_g.field_70181_x = mc.field_71439_g.field_70181_x / 0.9800000190734863D + 0.08D;
/* 275 */         mc.field_71439_g.field_70181_x -= 0.03120000000005D;
/*     */       } 
/*     */       
/* 278 */       if (mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab()) {
/* 279 */         mc.field_71439_g.field_70181_x = 0.1D;
/*     */       }
/*     */       
/* 282 */       if (!mc.field_71439_g.func_180799_ab() && (!mc.field_71439_g.func_70090_H() || ((Boolean)this.boost.getValue()).booleanValue()) && block instanceof BlockLiquid && mc.field_71439_g.field_70181_x < 0.2D) {
/* 283 */         mc.field_71439_g.field_70181_x = 0.5D;
/* 284 */         this.jumping = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLiquidCollision(JesusEvent event) {
/* 291 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 294 */     if (this.mode.getValue() == Mode.SOLID && mc.field_71441_e != null && mc.field_71439_g != null && checkCollide() && mc.field_71439_g.field_70181_x < 0.10000000149011612D && event.getPos().func_177956_o() < mc.field_71439_g.field_70163_u - 0.05000000074505806D) {
/* 295 */       if (mc.field_71439_g.func_184187_bx() != null) {
/* 296 */         event.setBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.949999988079071D, 1.0D));
/*     */       } else {
/* 298 */         event.setBoundingBox(Block.field_185505_j);
/*     */       } 
/* 300 */       event.setCanceled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void sendPacket(PacketEvent.Send event) {
/* 306 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/* 307 */       return;  if (this.mode.getValue() == Mode.SOLID && 
/* 308 */       event.getPacket() instanceof CPacketPlayer && mc.field_71439_g.field_70173_aa > 20 && ((Mode)this.mode
/*     */       
/* 310 */       .getValue()).equals(Mode.SOLID) && mc.field_71439_g
/* 311 */       .func_184187_bx() == null && 
/* 312 */       !mc.field_71474_y.field_74314_A.func_151470_d() && mc.field_71439_g.field_70143_R < 3.0F) {
/*     */       
/* 314 */       CPacketPlayer packet = (CPacketPlayer)event.getPacket();
/* 315 */       if (isOnLiquid() && !isInLiquid()) {
/* 316 */         ((ICPacketPlayer)packet).setOnGround(false);
/*     */         
/* 318 */         if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 319 */           this.lastOffset += 0.12F;
/*     */           
/* 321 */           if (this.lastOffset > 0.4F) {
/* 322 */             this.lastOffset = 0.2F;
/*     */           }
/* 324 */           ((ICPacketPlayer)packet).setY(packet.func_186996_b(mc.field_71439_g.field_70163_u) - this.lastOffset);
/*     */         } else {
/* 326 */           ((ICPacketPlayer)packet).setY((mc.field_71439_g.field_70173_aa % 2 == 0) ? (packet.func_186996_b(mc.field_71439_g.field_70163_u) - 0.05D) : packet.func_186996_b(mc.field_71439_g.field_70163_u));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkCollide() {
/* 335 */     if (mc.field_71439_g.func_70093_af()) {
/* 336 */       return false;
/*     */     }
/*     */     
/* 339 */     if (mc.field_71439_g.func_184187_bx() != null && 
/* 340 */       (mc.field_71439_g.func_184187_bx()).field_70143_R >= 3.0F) {
/* 341 */       return false;
/*     */     }
/*     */     
/* 344 */     return (mc.field_71439_g.field_70143_R <= 3.0F);
/*     */   }
/*     */   
/*     */   private enum Mode {
/* 348 */     SOLID, TRAMPOLINE, NexusCrit, NexusFast, NCP;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Jesus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */