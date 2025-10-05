/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ import com.mrzak34.thunderhack.events.EventMove;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.surround.BlockPosWithFacing;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class RusherScaffold extends Module {
/*  32 */   public final Setting<ColorSetting> Color2 = register(new Setting("Color", new ColorSetting(-2013200640)));
/*  33 */   private final Setting<Float> lineWidth = register(new Setting("LineWidth", Float.valueOf(1.0F), Float.valueOf(0.1F), Float.valueOf(5.0F)));
/*  34 */   public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(true)));
/*  35 */   public Setting<Boolean> allowShift = register(new Setting("AllowShift", Boolean.valueOf(false)));
/*  36 */   public Setting<Boolean> autoswap = register(new Setting("AutoSwap", Boolean.valueOf(true)));
/*  37 */   public Setting<Boolean> tower = register(new Setting("Tower", Boolean.valueOf(true)));
/*  38 */   public Setting<Boolean> safewalk = register(new Setting("SafeWalk", Boolean.valueOf(true)));
/*  39 */   public Setting<Boolean> echestholding = register(new Setting("EchestHolding", Boolean.valueOf(false)));
/*  40 */   public Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true)));
/*     */   
/*  42 */   private final Timer timer = new Timer();
/*     */   private BlockPosWithFacing currentblock;
/*     */   
/*     */   public RusherScaffold() {
/*  46 */     super("Scaffold", "лучший скафф", Module.Category.PLAYER);
/*     */   }
/*     */   
/*     */   private boolean isBlockValid(Block block) {
/*  50 */     return block.func_176223_P().func_185904_a().func_76220_a();
/*     */   }
/*     */   
/*     */   private BlockPosWithFacing checkNearBlocks(BlockPos blockPos) {
/*  54 */     if (isBlockValid(mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c()))
/*  55 */       return new BlockPosWithFacing(blockPos.func_177982_a(0, -1, 0), EnumFacing.UP); 
/*  56 */     if (isBlockValid(mc.field_71441_e.func_180495_p(blockPos.func_177982_a(-1, 0, 0)).func_177230_c()))
/*  57 */       return new BlockPosWithFacing(blockPos.func_177982_a(-1, 0, 0), EnumFacing.EAST); 
/*  58 */     if (isBlockValid(mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c()))
/*  59 */       return new BlockPosWithFacing(blockPos.func_177982_a(1, 0, 0), EnumFacing.WEST); 
/*  60 */     if (isBlockValid(mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c()))
/*  61 */       return new BlockPosWithFacing(blockPos.func_177982_a(0, 0, 1), EnumFacing.NORTH); 
/*  62 */     if (isBlockValid(mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, -1)).func_177230_c()))
/*  63 */       return new BlockPosWithFacing(blockPos.func_177982_a(0, 0, -1), EnumFacing.SOUTH); 
/*  64 */     return null;
/*     */   }
/*     */   
/*     */   private int findBlockToPlace() {
/*  68 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBlock && 
/*  69 */       isBlockValid(((ItemBlock)mc.field_71439_g.func_184614_ca().func_77973_b()).func_179223_d())) {
/*  70 */       return mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     }
/*  72 */     for (int i = 0; i < 9; i++) {
/*  73 */       if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_190916_E() != 0 && 
/*  74 */         mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof ItemBlock && (
/*  75 */         !((Boolean)this.echestholding.getValue()).booleanValue() || (((Boolean)this.echestholding.getValue()).booleanValue() && !mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b().equals(Item.func_150898_a(Blocks.field_150477_bB)))) && 
/*  76 */         isBlockValid(((ItemBlock)mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b()).func_179223_d())) {
/*  77 */         return i;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  82 */     return -1;
/*     */   }
/*     */   
/*     */   private BlockPosWithFacing checkNearBlocksExtended(BlockPos blockPos) {
/*  86 */     BlockPosWithFacing ret = null;
/*     */     
/*  88 */     ret = checkNearBlocks(blockPos);
/*  89 */     if (ret != null) return ret;
/*     */     
/*  91 */     ret = checkNearBlocks(blockPos.func_177982_a(-1, 0, 0));
/*  92 */     if (ret != null) return ret;
/*     */     
/*  94 */     ret = checkNearBlocks(blockPos.func_177982_a(1, 0, 0));
/*  95 */     if (ret != null) return ret;
/*     */     
/*  97 */     ret = checkNearBlocks(blockPos.func_177982_a(0, 0, 1));
/*  98 */     if (ret != null) return ret;
/*     */     
/* 100 */     ret = checkNearBlocks(blockPos.func_177982_a(0, 0, -1));
/* 101 */     if (ret != null) return ret;
/*     */     
/* 103 */     ret = checkNearBlocks(blockPos.func_177982_a(-2, 0, 0));
/* 104 */     if (ret != null) return ret;
/*     */     
/* 106 */     ret = checkNearBlocks(blockPos.func_177982_a(2, 0, 0));
/* 107 */     if (ret != null) return ret;
/*     */     
/* 109 */     ret = checkNearBlocks(blockPos.func_177982_a(0, 0, 2));
/* 110 */     if (ret != null) return ret;
/*     */     
/* 112 */     ret = checkNearBlocks(blockPos.func_177982_a(0, 0, -2));
/* 113 */     if (ret != null) return ret;
/*     */     
/* 115 */     ret = checkNearBlocks(blockPos.func_177982_a(0, -1, 0));
/* 116 */     BlockPos blockPos2 = blockPos.func_177982_a(0, -1, 0);
/*     */     
/* 118 */     if (ret != null) return ret;
/*     */     
/* 120 */     ret = checkNearBlocks(blockPos2.func_177982_a(1, 0, 0));
/* 121 */     if (ret != null) return ret;
/*     */     
/* 123 */     ret = checkNearBlocks(blockPos2.func_177982_a(-1, 0, 0));
/* 124 */     if (ret != null) return ret;
/*     */     
/* 126 */     ret = checkNearBlocks(blockPos2.func_177982_a(0, 0, 1));
/* 127 */     if (ret != null) return ret;
/*     */     
/* 129 */     return checkNearBlocks(blockPos2.func_177982_a(0, 0, -1));
/*     */   }
/*     */   
/*     */   private int countValidBlocks() {
/* 133 */     int n = 36;
/* 134 */     int n2 = 0;
/*     */     
/* 136 */     while (n < 45) {
/*     */       
/* 138 */       if (mc.field_71439_g.field_71069_bz.func_75139_a(n).func_75216_d()) {
/* 139 */         ItemStack itemStack = mc.field_71439_g.field_71069_bz.func_75139_a(n).func_75211_c();
/* 140 */         if (itemStack.func_77973_b() instanceof ItemBlock && 
/* 141 */           isBlockValid(((ItemBlock)itemStack.func_77973_b()).func_179223_d())) {
/* 142 */           n2 += itemStack.func_190916_E();
/*     */         }
/*     */       } 
/*     */       
/* 146 */       n++;
/*     */     } 
/*     */     
/* 149 */     return n2;
/*     */   }
/*     */   
/*     */   private Vec3d getEyePosition() {
/* 153 */     return new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/*     */   }
/*     */   
/*     */   private float[] getRotations(BlockPos blockPos, EnumFacing enumFacing) {
/* 157 */     Vec3d vec3d = new Vec3d(blockPos.func_177958_n() + 0.5D, (mc.field_71441_e.func_180495_p(blockPos).func_185918_c((World)mc.field_71441_e, blockPos)).field_72337_e - 0.01D, blockPos.func_177952_p() + 0.5D);
/* 158 */     vec3d = vec3d.func_178787_e((new Vec3d(enumFacing.func_176730_m())).func_186678_a(0.5D));
/*     */     
/* 160 */     Vec3d vec3d2 = getEyePosition();
/*     */     
/* 162 */     double d = vec3d.field_72450_a - vec3d2.field_72450_a;
/* 163 */     double d2 = vec3d.field_72448_b - vec3d2.field_72448_b;
/* 164 */     double d3 = vec3d.field_72449_c - vec3d2.field_72449_c;
/* 165 */     double d6 = Math.sqrt(d * d + d3 * d3);
/*     */     
/* 167 */     float f = (float)(Math.toDegrees(Math.atan2(d3, d)) - 90.0D);
/* 168 */     float f2 = (float)-Math.toDegrees(Math.atan2(d2, d6));
/*     */     
/* 170 */     float[] ret = new float[2];
/* 171 */     ret[0] = mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(f - mc.field_71439_g.field_70177_z);
/* 172 */     ret[1] = mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(f2 - mc.field_71439_g.field_70125_A);
/*     */     
/* 174 */     return ret;
/*     */   }
/*     */   
/*     */   private void doSafeWalk(EventMove event) {
/* 178 */     double x = event.get_x();
/* 179 */     double y = event.get_y();
/* 180 */     double z = event.get_z();
/*     */     
/* 182 */     if (mc.field_71439_g.field_70122_E && !mc.field_71439_g.field_70145_X) {
/*     */       double increment;
/* 184 */       for (increment = 0.05D; x != 0.0D && isOffsetBBEmpty(x, 0.0D); ) {
/* 185 */         if (x < increment && x >= -increment) {
/* 186 */           x = 0.0D; continue;
/* 187 */         }  if (x > 0.0D) {
/* 188 */           x -= increment; continue;
/*     */         } 
/* 190 */         x += increment;
/*     */       } 
/*     */       
/* 193 */       while (z != 0.0D && isOffsetBBEmpty(0.0D, z)) {
/* 194 */         if (z < increment && z >= -increment) {
/* 195 */           z = 0.0D; continue;
/* 196 */         }  if (z > 0.0D) {
/* 197 */           z -= increment; continue;
/*     */         } 
/* 199 */         z += increment;
/*     */       } 
/*     */       
/* 202 */       while (x != 0.0D && z != 0.0D && isOffsetBBEmpty(x, z)) {
/* 203 */         if (x < increment && x >= -increment) {
/* 204 */           x = 0.0D;
/* 205 */         } else if (x > 0.0D) {
/* 206 */           x -= increment;
/*     */         } else {
/* 208 */           x += increment;
/*     */         } 
/* 210 */         if (z < increment && z >= -increment) {
/* 211 */           z = 0.0D; continue;
/* 212 */         }  if (z > 0.0D) {
/* 213 */           z -= increment; continue;
/*     */         } 
/* 215 */         z += increment;
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     event.set_x(x);
/* 220 */     event.set_y(y);
/* 221 */     event.set_z(z);
/* 222 */     event.setCanceled(true);
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void onMove(EventMove event) {
/* 227 */     if (fullNullCheck())
/*     */       return; 
/* 229 */     if (((Boolean)this.safewalk.getValue()).booleanValue())
/* 230 */       doSafeWalk(event); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent event) {
/* 235 */     if (((Boolean)this.render.getValue()).booleanValue() && this.currentblock != null) {
/* 236 */       GlStateManager.func_179094_E();
/* 237 */       RenderUtil.drawBlockOutline(this.currentblock.getPosition(), ((ColorSetting)this.Color2.getValue()).getColorObject(), ((Float)this.lineWidth.getValue()).floatValue(), false, 0);
/* 238 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isOffsetBBEmpty(double x, double z) {
/* 243 */     return mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(x, -2.0D, z)).isEmpty();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPre(EventSync event) {
/* 248 */     if (countValidBlocks() <= 0) {
/* 249 */       this.currentblock = null;
/*     */       return;
/*     */     } 
/* 252 */     if (mc.field_71439_g.field_70163_u < 257.0D) {
/* 253 */       this.currentblock = null;
/*     */       
/* 255 */       if (mc.field_71439_g.func_70093_af() && !((Boolean)this.allowShift.getValue()).booleanValue())
/*     */         return; 
/* 257 */       int n2 = findBlockToPlace();
/* 258 */       if (n2 == -1)
/*     */         return; 
/* 260 */       Item item = mc.field_71439_g.field_71071_by.func_70301_a(n2).func_77973_b();
/* 261 */       if (!(item instanceof ItemBlock))
/* 262 */         return;  Block block = ((ItemBlock)item).func_179223_d();
/*     */       
/* 264 */       boolean fullBlock = block.func_176223_P().func_185913_b();
/*     */       
/* 266 */       BlockPos blockPos2 = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - (fullBlock ? 1.0D : 0.01D), mc.field_71439_g.field_70161_v);
/* 267 */       if (!mc.field_71441_e.func_180495_p(blockPos2).func_185904_a().func_76222_j())
/*     */         return; 
/* 269 */       this.currentblock = checkNearBlocksExtended(blockPos2);
/* 270 */       if (this.currentblock != null && (
/* 271 */         (Boolean)this.rotate.getValue()).booleanValue()) {
/* 272 */         float[] rotations = getRotations(this.currentblock.getPosition(), this.currentblock.getFacing());
/* 273 */         mc.field_71439_g.field_70177_z = rotations[0];
/* 274 */         mc.field_71439_g.field_70761_aq = rotations[0];
/* 275 */         mc.field_71439_g.field_70125_A = rotations[1];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPost(EventPostSync e) {
/* 283 */     if (this.currentblock == null)
/* 284 */       return;  int prev_item = mc.field_71439_g.field_71071_by.field_70461_c;
/* 285 */     if (!(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBlock) && (
/* 286 */       (Boolean)this.autoswap.getValue()).booleanValue()) {
/* 287 */       int blockSlot = findBlockToPlace();
/* 288 */       if (blockSlot != -1) {
/* 289 */         mc.field_71439_g.field_71071_by.field_70461_c = blockSlot;
/* 290 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(blockSlot));
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBlock && isBlockValid(((ItemBlock)mc.field_71439_g.func_184614_ca().func_77973_b()).func_179223_d())) {
/* 295 */       if (!mc.field_71439_g.field_71158_b.field_78901_c || mc.field_71439_g.field_191988_bg != 0.0F || mc.field_71439_g.field_70702_br != 0.0F || !((Boolean)this.tower.getValue()).booleanValue()) {
/* 296 */         this.timer.reset();
/*     */       } else {
/* 298 */         mc.field_71439_g.func_70016_h(0.0D, 0.42D, 0.0D);
/* 299 */         if (this.timer.passedMs(1500L)) {
/* 300 */           mc.field_71439_g.field_70181_x = -0.28D;
/* 301 */           this.timer.reset();
/*     */         } 
/*     */       } 
/*     */       
/* 305 */       boolean sneak = mc.field_71441_e.func_180495_p(this.currentblock.getPosition()).func_177230_c().func_180639_a((World)mc.field_71441_e, this.currentblock.getPosition(), mc.field_71441_e.func_180495_p(this.currentblock.getPosition()), (EntityPlayer)mc.field_71439_g, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
/* 306 */       if (sneak)
/* 307 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING)); 
/* 308 */       mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, this.currentblock
/*     */ 
/*     */           
/* 311 */           .getPosition(), this.currentblock
/* 312 */           .getFacing(), new Vec3d(this.currentblock
/*     */             
/* 314 */             .getPosition().func_177958_n() + Math.random(), 
/* 315 */             (mc.field_71441_e.func_180495_p(this.currentblock.getPosition()).func_185918_c((World)mc.field_71441_e, this.currentblock.getPosition())).field_72337_e - 0.01D, this.currentblock
/* 316 */             .getPosition().func_177952_p() + Math.random()), EnumHand.MAIN_HAND);
/*     */ 
/*     */ 
/*     */       
/* 320 */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 321 */       if (sneak)
/* 322 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING)); 
/* 323 */       mc.field_71439_g.field_71071_by.field_70461_c = prev_item;
/* 324 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\RusherScaffold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */