/*     */ package com.mrzak34.thunderhack.modules.funnygame;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IPlayerControllerMP;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IMinecraft;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InteractionUtil;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.CombatRules;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class C4Aura extends Module {
/*  47 */   public Setting<Float> mindmg = register(new Setting("MinDamage", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  48 */   public Setting<Float> maxSelfDmg = register(new Setting("MaxSelfDamage", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  49 */   public Setting<Float> stophp = register(new Setting("StopHp", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(20.0F)));
/*  50 */   public Setting<Float> targetRange = register(new Setting("TargetRange", Float.valueOf(8.0F), Float.valueOf(3.0F), Float.valueOf(15.0F)));
/*  51 */   public Setting<Boolean> placeinside = register(new Setting("placeInside", Boolean.valueOf(true)));
/*  52 */   public Setting<Boolean> autoBurrow = register(new Setting("AutoBurrow", Boolean.valueOf(true)));
/*     */   
/*  54 */   private List<BlockPos> positions = null;
/*     */   private BlockPos renderblockpos;
/*     */   private BlockPos postSyncPlace;
/*  57 */   public static EntityPlayer target = null;
/*     */ 
/*     */   
/*     */   public C4Aura() {
/*  61 */     super("C4Aura", "Ставит с4", "mcfunny.su only", Module.Category.FUNNYGAME);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntitySync(EventSync e) {
/*  67 */     if (fullNullCheck())
/*  68 */       return;  if (((Boolean)this.autoBurrow.getValue()).booleanValue() && mc.field_71439_g.func_70093_af() && 
/*  69 */       findC4() != -1) {
/*  70 */       if (!canPlaceC4(new BlockPos((Entity)mc.field_71439_g)))
/*  71 */         return;  mc.field_71439_g.field_70125_A = 90.0F;
/*  72 */       PlayerUtils.centerPlayer(mc.field_71439_g.func_174791_d());
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     if (((Float)this.stophp.getValue()).floatValue() >= mc.field_71439_g.func_110143_aJ()) {
/*     */       return;
/*     */     }
/*  79 */     if (findC4() == -1) {
/*     */       return;
/*     */     }
/*  82 */     target = findTarget();
/*  83 */     if (mc.field_71439_g.field_70173_aa % 5 == 0) {
/*  84 */       this.positions = getPositions((Entity)mc.field_71439_g);
/*     */     }
/*  86 */     if (target != null && this.positions != null) {
/*  87 */       BlockPos bp = getBestPos(this.positions, target);
/*  88 */       if (bp != null) {
/*  89 */         placePre(bp);
/*     */       }
/*  91 */       if (mc.field_71439_g.func_184614_ca().func_82833_r().contains("C4") && !mc.field_71439_g.func_184614_ca().func_82833_r().contains("2")) {
/*  92 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v), EnumFacing.UP));
/*     */       }
/*     */     } else {
/*  95 */       this.renderblockpos = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void postSync(EventPostSync event) {
/* 101 */     if (fullNullCheck())
/* 102 */       return;  if (((Boolean)this.autoBurrow.getValue()).booleanValue() && mc.field_71439_g.func_70093_af() && 
/* 103 */       findC4() != -1) {
/* 104 */       if (!canPlaceC4(new BlockPos((Entity)mc.field_71439_g)))
/* 105 */         return;  mc.field_71439_g.field_71071_by.field_70461_c = 2;
/* 106 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(findC4()));
/* 107 */       placePost(new BlockPos((Entity)mc.field_71439_g));
/* 108 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(2));
/*     */       
/*     */       return;
/*     */     } 
/* 112 */     if (this.postSyncPlace != null) {
/* 113 */       placePost(this.postSyncPlace);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private List<BlockPos> getPositions(Entity entity2) {
/* 119 */     ArrayList<BlockPos> arrayList = new ArrayList<>();
/* 120 */     int playerX = (int)entity2.field_70165_t;
/* 121 */     int playerY = (int)entity2.field_70163_u;
/* 122 */     int playerZ = (int)entity2.field_70161_v;
/* 123 */     int n4 = 4;
/* 124 */     double playerX1 = entity2.field_70165_t - 0.5D;
/* 125 */     double playerY1 = entity2.field_70163_u + entity2.func_70047_e() - 1.0D;
/* 126 */     double playerZ1 = entity2.field_70161_v - 0.5D;
/* 127 */     for (int n5 = playerX - n4; n5 <= playerX + n4; n5++) {
/* 128 */       for (int n6 = playerZ - n4; n6 <= playerZ + n4; n6++) {
/* 129 */         for (int n8 = playerY - n4; n8 < playerY + n4; n8++) {
/* 130 */           if ((n5 - playerX1) * (n5 - playerX1) + (n8 - playerY1) * (n8 - playerY1) + (n6 - playerZ1) * (n6 - playerZ1) <= 16.0D && canPlaceC4(new BlockPos(n5, n8, n6))) {
/* 131 */             arrayList.add(new BlockPos(n5, n8, n6));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 136 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceC4(BlockPos bp) {
/* 141 */     if (mc.field_71439_g.func_70092_e(bp.func_177958_n(), bp.func_177956_o(), bp.func_177952_p()) > 16.0D) {
/* 142 */       return false;
/*     */     }
/* 144 */     if (target != null) {
/* 145 */       BlockPos jew = new BlockPos((Entity)target);
/* 146 */       if (Objects.equals(bp, jew) && !((Boolean)this.placeinside.getValue()).booleanValue()) {
/* 147 */         return false;
/*     */       }
/* 149 */       if (Objects.equals(bp, jew.func_177982_a(0, 1, 0))) {
/* 150 */         return false;
/*     */       }
/*     */     } 
/* 153 */     if (getDamage(bp.func_177958_n(), bp.func_177956_o(), bp.func_177952_p(), (EntityLivingBase)mc.field_71439_g) > ((Float)this.maxSelfDmg.getValue()).floatValue()) {
/* 154 */       return false;
/*     */     }
/* 156 */     if (!mc.field_71441_e.func_180495_p(bp).func_185904_a().func_76222_j()) {
/* 157 */       return false;
/*     */     }
/* 159 */     if (mc.field_71441_e.func_180495_p(bp.func_177977_b()).func_177230_c() == Blocks.field_150465_bP) {
/* 160 */       return false;
/*     */     }
/* 162 */     if (mc.field_71441_e.func_180495_p(bp.func_177977_b()).func_177230_c() == Blocks.field_150442_at) {
/* 163 */       return false;
/*     */     }
/* 165 */     if (mc.field_71439_g.func_184614_ca().func_82833_r().contains("C4") && mc.field_71439_g.func_184614_ca().func_82833_r().contains("0")) {
/* 166 */       return false;
/*     */     }
/* 168 */     return (mc.field_71441_e.func_180495_p(bp).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(bp.func_177977_b()).func_177230_c() != Blocks.field_150350_a);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamage(double d7, double d2, double d3, EntityLivingBase entityLivingBase) {
/* 174 */     double d5 = entityLivingBase.func_70011_f(d7, d2, d3) / 12.0D;
/* 175 */     double d6 = entityLivingBase.field_70170_p.func_72842_a(new Vec3d(d7, d2, d3), entityLivingBase.func_174813_aQ());
/* 176 */     d5 = (1.0D - d5) * d6;
/* 177 */     float f = (int)((d5 * d5 + d5) / 2.0D * 7.0D * 12.0D + 1.0D);
/* 178 */     f = Math.min(f / 2.0F + 1.0F, f);
/* 179 */     DamageSource dmgg = DamageSource.func_94539_a(new Explosion((World)mc.field_71441_e, (Entity)mc.field_71439_g, d7, d2, d3, 6.0F, false, true));
/* 180 */     f = CombatRules.func_189427_a(f, entityLivingBase.func_70658_aO(), (float)entityLivingBase.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/* 181 */     int n = EnchantmentHelper.func_77508_a(entityLivingBase.func_184193_aE(), dmgg);
/* 182 */     if (n > 0) {
/* 183 */       f = CombatRules.func_188401_b(f, n);
/*     */     }
/* 185 */     if (entityLivingBase.func_70660_b(MobEffects.field_76429_m) != null) {
/* 186 */       f = f * (25 - (((PotionEffect)Objects.<PotionEffect>requireNonNull(entityLivingBase.func_70660_b(MobEffects.field_76429_m))).func_76458_c() + 1) * 5) / 25.0F;
/*     */     }
/* 188 */     f = Math.max(f, 0.0F);
/* 189 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getBestPos(List<BlockPos> vsepos, EntityPlayer nigger) {
/* 194 */     BlockPos pos = null;
/* 195 */     double bestdmg = ((Float)this.mindmg.getValue()).floatValue();
/* 196 */     for (BlockPos pos1 : vsepos) {
/* 197 */       if (getDamage(pos1.func_177958_n(), pos1.func_177956_o(), pos1.func_177952_p(), (EntityLivingBase)nigger) > bestdmg) {
/* 198 */         bestdmg = getDamage(pos1.func_177958_n(), pos1.func_177956_o(), pos1.func_177952_p(), (EntityLivingBase)nigger);
/* 199 */         pos = pos1;
/*     */       } 
/*     */     } 
/* 202 */     return pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer findTarget() {
/* 207 */     EntityPlayer target = null;
/* 208 */     double distance = this.targetRange.getPow2Value();
/* 209 */     for (EntityPlayer entity : mc.field_71441_e.field_73010_i) {
/* 210 */       if (entity == mc.field_71439_g) {
/*     */         continue;
/*     */       }
/* 213 */       if (Thunderhack.friendManager.isFriend(entity)) {
/*     */         continue;
/*     */       }
/* 216 */       if (mc.field_71439_g.func_70068_e((Entity)entity) <= distance) {
/* 217 */         target = entity;
/* 218 */         distance = mc.field_71439_g.func_70068_e((Entity)entity);
/*     */       } 
/*     */     } 
/* 221 */     return target;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent e) {
/* 227 */     if (mc.field_71439_g == null && mc.field_71441_e == null) {
/*     */       return;
/*     */     }
/* 230 */     if (this.renderblockpos != null && target != null) {
/*     */       try {
/* 232 */         DecimalFormat df = new DecimalFormat("0.0");
/* 233 */         RenderUtil.drawBlockOutline(this.renderblockpos, new Color(392654), 3.0F, true, 0);
/*     */         
/* 235 */         GlStateManager.func_179094_E();
/* 236 */         GlStateManager.func_179097_i();
/* 237 */         GlStateManager.func_179140_f();
/* 238 */         RenderUtil.glBillboardDistanceScaled(this.renderblockpos.func_177958_n() + 0.5F, this.renderblockpos.func_177956_o() + 0.5F, this.renderblockpos.func_177952_p() + 0.5F, (EntityPlayer)mc.field_71439_g, 1.0F);
/* 239 */         FontRender.drawString3(df.format(getDamage(this.renderblockpos.func_177958_n(), this.renderblockpos.func_177956_o(), this.renderblockpos.func_177952_p(), (EntityLivingBase)target)), (int)-(FontRender.getStringWidth(df.format(getDamage(this.renderblockpos.func_177958_n(), (this.renderblockpos.func_177956_o() + 1), this.renderblockpos.func_177952_p(), (EntityLivingBase)target))) / 2.0D), -4.0F, -1);
/* 240 */         GlStateManager.func_179145_e();
/* 241 */         GlStateManager.func_179126_j();
/* 242 */         GlStateManager.func_179121_F();
/* 243 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int findC4() {
/* 250 */     for (int i = 0; i < 9; ) {
/* 251 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 252 */       if (itemStack.func_77973_b() != Item.func_150898_a(Blocks.field_150442_at) || 
/* 253 */         !itemStack.func_82833_r().contains("C4")) { i++; continue; }
/* 254 */        return i;
/*     */     } 
/* 256 */     return -1;
/*     */   }
/*     */   
/*     */   public void placePre(BlockPos position) {
/* 260 */     mc.field_71439_g.field_71071_by.field_70461_c = findC4();
/* 261 */     ((IPlayerControllerMP)mc.field_71442_b).syncItem();
/* 262 */     this.renderblockpos = position; EnumFacing[] arrayOfEnumFacing; int i; byte b;
/* 263 */     for (arrayOfEnumFacing = EnumFacing.values(), i = arrayOfEnumFacing.length, b = 0; b < i; ) { EnumFacing direction = arrayOfEnumFacing[b];
/* 264 */       BlockPos directionOffset = position.func_177972_a(direction);
/* 265 */       EnumFacing oppositeFacing = direction.func_176734_d();
/* 266 */       if (mc.field_71441_e.func_180495_p(directionOffset).func_185904_a().func_76222_j()) { b++; continue; }
/* 267 */        float[] rotation = InteractionUtil.getAnglesToBlock(directionOffset, oppositeFacing);
/* 268 */       Vec3d interactVector = null;
/* 269 */       RayTraceResult result = InteractionUtil.getTraceResult(4.0D, rotation[0], rotation[1]);
/* 270 */       if (result != null && result.field_72313_a.equals(RayTraceResult.Type.BLOCK)) {
/* 271 */         interactVector = result.field_72307_f;
/*     */       }
/* 273 */       if (interactVector == null) {
/* 274 */         interactVector = (new Vec3d((Vec3i)directionOffset)).func_72441_c(0.5D, 0.5D, 0.5D);
/* 275 */         rotation = calculateAngles(interactVector);
/*     */       } 
/* 277 */       mc.field_71439_g.field_70177_z = rotation[0];
/* 278 */       mc.field_71439_g.field_70125_A = rotation[1];
/* 279 */       this.postSyncPlace = position; }
/*     */   
/*     */   }
/*     */   public void placePost(BlockPos position) {
/*     */     EnumFacing[] arrayOfEnumFacing;
/*     */     int i;
/*     */     byte b;
/* 286 */     for (arrayOfEnumFacing = EnumFacing.values(), i = arrayOfEnumFacing.length, b = 0; b < i; ) { EnumFacing direction = arrayOfEnumFacing[b];
/* 287 */       BlockPos directionOffset = position.func_177972_a(direction);
/* 288 */       EnumFacing oppositeFacing = direction.func_176734_d();
/* 289 */       if (mc.field_71441_e.func_180495_p(directionOffset).func_185904_a().func_76222_j()) { b++; continue; }
/* 290 */        boolean sprint = mc.field_71439_g.func_70051_ag();
/* 291 */       if (sprint) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING)); 
/* 292 */       boolean sneak = mc.field_71441_e.func_180495_p(directionOffset).func_177230_c().func_180639_a((World)mc.field_71441_e, directionOffset, mc.field_71441_e.func_180495_p(directionOffset), (EntityPlayer)mc.field_71439_g, EnumHand.MAIN_HAND, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
/* 293 */       if (sneak) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING)); 
/* 294 */       float[] rotation = InteractionUtil.getAnglesToBlock(directionOffset, oppositeFacing);
/* 295 */       Vec3d interactVector = null;
/* 296 */       RayTraceResult result = InteractionUtil.getTraceResult(4.0D, rotation[0], rotation[1]);
/* 297 */       if (result != null && result.field_72313_a.equals(RayTraceResult.Type.BLOCK)) {
/* 298 */         interactVector = result.field_72307_f;
/*     */       }
/* 300 */       if (interactVector == null) {
/* 301 */         interactVector = (new Vec3d((Vec3i)directionOffset)).func_72441_c(0.5D, 0.5D, 0.5D);
/*     */       }
/* 303 */       mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, directionOffset, direction.func_176734_d(), interactVector, EnumHand.MAIN_HAND);
/* 304 */       if (sneak) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING)); 
/* 305 */       if (sprint) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING)); 
/* 306 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/* 307 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v), EnumFacing.UP));
/* 308 */       ((IMinecraft)mc).setRightClickDelayTimer(4);
/* 309 */       this.postSyncPlace = null; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] calculateAngles(Vec3d to) {
/* 316 */     float yaw = (float)(Math.toDegrees(Math.atan2((to.func_178788_d(mc.field_71439_g.func_174824_e(1.0F))).field_72449_c, (to.func_178788_d(mc.field_71439_g.func_174824_e(1.0F))).field_72450_a)) - 90.0D);
/* 317 */     float pitch = (float)Math.toDegrees(-Math.atan2((to.func_178788_d(mc.field_71439_g.func_174824_e(1.0F))).field_72448_b, Math.hypot((to.func_178788_d(mc.field_71439_g.func_174824_e(1.0F))).field_72450_a, (to.func_178788_d(mc.field_71439_g.func_174824_e(1.0F))).field_72449_c)));
/* 318 */     return new float[] { MathHelper.func_76142_g(yaw), MathHelper.func_76142_g(pitch) };
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\funnygame\C4Aura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */