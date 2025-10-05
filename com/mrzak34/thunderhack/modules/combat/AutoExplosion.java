/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.setting.SubBind;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.CrystalUtils;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.RotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class AutoExplosion extends Module {
/*  32 */   public Setting<Boolean> packetplace = register(new Setting("packetplace", Boolean.valueOf(true))); public static EntityPlayer trgt;
/*  33 */   public Setting<Integer> stophp = register(new Setting("stophp", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(20)));
/*  34 */   public Setting<Integer> delay = register(new Setting("TicksExisted", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(20)));
/*  35 */   public Setting<Integer> placedelay = register(new Setting("PlaceDelay", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(1000)));
/*  36 */   public Setting<Integer> maxself = register(new Setting("maxself", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20)));
/*  37 */   public Setting<SubBind> bindButton = register(new Setting("BindButton", new SubBind(42)));
/*  38 */   int ticksNoOnGround = 0;
/*     */   BlockPos CoolPosition;
/*  40 */   Timer placeDelay = new Timer();
/*  41 */   Timer breakDelay = new Timer();
/*  42 */   int extraTicks = 5;
/*  43 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.FullAuto));
/*  44 */   private final Setting<TargetMode> targetMode = register(new Setting("Target", TargetMode.Aura));
/*  45 */   public Setting<Boolean> offAura = register(new Setting("offAura", Boolean.valueOf(true), v -> (this.targetMode.getValue() == TargetMode.AutoExplosion))); private BlockPos crysToExplosion;
/*     */   
/*     */   public AutoExplosion() {
/*  48 */     super("AutoExplosion", "более тупая кристалка-для кринж серверов", "don't use-this shit", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerPre(EventSync e) {
/*  53 */     if (this.targetMode.getValue() == TargetMode.Aura) {
/*  54 */       this.offAura.setValue(Boolean.valueOf(false));
/*     */     }
/*  56 */     if (mc.field_71439_g.func_110143_aJ() < ((Integer)this.stophp.getValue()).intValue()) {
/*     */       return;
/*     */     }
/*  59 */     if (this.mode.getValue() == Mode.FullAuto) {
/*  60 */       FullAuto(e);
/*  61 */     } else if (this.mode.getValue() == Mode.Semi) {
/*  62 */       Semi(e);
/*  63 */     } else if (this.mode.getValue() == Mode.Bind && PlayerUtils.isKeyDown(((SubBind)this.bindButton.getValue()).getKey())) {
/*  64 */       onBind(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void Semi(EventSync e) {
/*  69 */     if (Mouse.isButtonDown(1)) {
/*  70 */       if (((Boolean)this.offAura.getValue()).booleanValue() && ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).isEnabled()) {
/*  71 */         ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).disable();
/*     */       }
/*  73 */       RayTraceResult ray = mc.field_71439_g.func_174822_a(4.5D, mc.func_184121_ak());
/*  74 */       BlockPos pos = null;
/*  75 */       if (ray != null) {
/*  76 */         pos = ray.func_178782_a();
/*     */       }
/*  78 */       if (pos != null && 
/*  79 */         mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150343_Z) {
/*  80 */         int crysslot = InventoryUtil.getCrysathotbar();
/*  81 */         if (crysslot == -1) {
/*     */           return;
/*     */         }
/*  84 */         int oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*  85 */         mc.field_71439_g.field_71071_by.field_70461_c = InventoryUtil.getCrysathotbar();
/*  86 */         mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, EnumFacing.UP, new Vec3d(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()), EnumHand.MAIN_HAND);
/*  87 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*  88 */         mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
/*  89 */         this.crysToExplosion = pos;
/*  90 */         this.extraTicks = 5;
/*     */       }
/*     */     
/*     */     }
/*  94 */     else if (this.crysToExplosion != null) {
/*  95 */       EntityEnderCrystal ourCrys = getCrystal(this.crysToExplosion);
/*  96 */       if (ourCrys != null) {
/*  97 */         if (ourCrys.field_70173_aa > ((Integer)this.delay.getValue()).intValue() && this.breakDelay.passedMs(156L) && 
/*  98 */           CrystalUtils.calculateDamage(ourCrys, (Entity)mc.field_71439_g) < ((Integer)this.maxself.getValue()).intValue()) {
/*  99 */           mc.field_71439_g.func_70031_b(false);
/* 100 */           float[] angle = RotationUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), ourCrys.func_174824_e(mc.func_184121_ak()));
/* 101 */           mc.field_71439_g.field_70177_z = angle[0];
/* 102 */           mc.field_71439_g.field_70125_A = angle[1];
/* 103 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity((Entity)ourCrys));
/* 104 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 105 */           this.breakDelay.reset();
/*     */         } 
/*     */       } else {
/*     */         
/* 109 */         this.extraTicks--;
/* 110 */         if (this.extraTicks <= 0) {
/* 111 */           this.crysToExplosion = null;
/* 112 */           this.extraTicks = 10;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void FullAuto(EventSync e) {
/* 120 */     for (Entity ent : mc.field_71441_e.field_72996_f) {
/* 121 */       if (ent instanceof EntityEnderCrystal && 
/* 122 */         mc.field_71439_g.func_70032_d(ent) < 5.0F && 
/* 123 */         ent.field_70173_aa >= ((Integer)this.delay.getValue()).intValue() && 
/* 124 */         this.breakDelay.passedMs(156L) && 
/* 125 */         CrystalUtils.calculateDamage((EntityEnderCrystal)ent, (Entity)mc.field_71439_g) < ((Integer)this.maxself.getValue()).intValue()) {
/* 126 */         if (((Boolean)this.offAura.getValue()).booleanValue()) {
/* 127 */           ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).disable();
/*     */         }
/* 129 */         mc.field_71439_g.func_70031_b(false);
/* 130 */         float[] angle = RotationUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), ent.func_174824_e(mc.func_184121_ak()));
/* 131 */         mc.field_71439_g.field_70177_z = angle[0];
/* 132 */         mc.field_71439_g.field_70125_A = angle[1];
/* 133 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(ent));
/* 134 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 135 */         this.breakDelay.reset();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (this.targetMode.getValue() == TargetMode.Aura) {
/* 144 */       if (Aura.target != null) {
/* 145 */         if (Aura.target instanceof EntityPlayer) {
/* 146 */           trgt = (EntityPlayer)Aura.target;
/* 147 */           if (!trgt.field_70122_E) {
/* 148 */             this.ticksNoOnGround++;
/*     */           } else {
/* 150 */             this.ticksNoOnGround = 0;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 154 */         trgt = null;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 158 */       for (EntityPlayer ent : mc.field_71441_e.field_73010_i) {
/* 159 */         if (mc.field_71439_g.func_70068_e((Entity)ent) < 36.0D && 
/* 160 */           !Thunderhack.friendManager.isFriend(ent)) {
/* 161 */           trgt = ent;
/*     */         }
/*     */       } 
/*     */       
/* 165 */       if (trgt == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 171 */     if (getPosition((EntityPlayer)mc.field_71439_g) != null && mc.field_71439_g.field_70163_u + 0.2280000001192093D < trgt.field_70163_u) {
/* 172 */       this.CoolPosition = getPosition((EntityPlayer)mc.field_71439_g);
/* 173 */       if (mc.field_71441_e.func_180495_p(this.CoolPosition).func_177230_c() == Blocks.field_150343_Z) {
/* 174 */         if (getCrystal(this.CoolPosition) != null) {
/*     */           return;
/*     */         }
/* 177 */         if (!this.placeDelay.passedMs(((Integer)this.placedelay.getValue()).intValue())) {
/*     */           return;
/*     */         }
/* 180 */         int crysslot = InventoryUtil.getCrysathotbar();
/* 181 */         if (crysslot == -1) {
/*     */           return;
/*     */         }
/* 184 */         if (((Boolean)this.offAura.getValue()).booleanValue()) {
/* 185 */           ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).disable();
/*     */         }
/* 187 */         InventoryUtil.switchToHotbarSlot(InventoryUtil.getCrysathotbar(), false);
/* 188 */         BlockUtils.placeBlockSmartRotate(this.CoolPosition.func_177982_a(0, 1, 0), EnumHand.MAIN_HAND, true, ((Boolean)this.packetplace.getValue()).booleanValue(), mc.field_71439_g.func_70093_af(), e);
/* 189 */         this.placeDelay.reset();
/*     */       } else {
/* 191 */         if (((Boolean)this.offAura.getValue()).booleanValue()) {
/* 192 */           ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).disable();
/*     */         }
/* 194 */         int obbyslot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 195 */         if (obbyslot == -1) {
/*     */           return;
/*     */         }
/* 198 */         InventoryUtil.switchToHotbarSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class), false);
/* 199 */         BlockUtils.placeBlockSmartRotate(this.CoolPosition, EnumHand.MAIN_HAND, true, ((Boolean)this.packetplace.getValue()).booleanValue(), mc.field_71439_g.func_70093_af(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onBind(EventSync e) {
/* 205 */     for (Entity ent : mc.field_71441_e.field_72996_f) {
/* 206 */       if (ent instanceof EntityEnderCrystal && 
/* 207 */         mc.field_71439_g.func_70032_d(ent) < 5.0F && 
/* 208 */         ent.field_70173_aa >= ((Integer)this.delay.getValue()).intValue() && 
/* 209 */         this.breakDelay.passedMs(156L) && 
/* 210 */         CrystalUtils.calculateDamage((EntityEnderCrystal)ent, (Entity)mc.field_71439_g) < ((Integer)this.maxself.getValue()).intValue()) {
/* 211 */         mc.field_71439_g.func_70031_b(false);
/* 212 */         float[] angle = RotationUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), ent.func_174824_e(mc.func_184121_ak()));
/* 213 */         mc.field_71439_g.field_70177_z = angle[0];
/* 214 */         mc.field_71439_g.field_70125_A = angle[1];
/* 215 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(ent));
/* 216 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 217 */         this.breakDelay.reset();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (this.targetMode.getValue() == TargetMode.Aura) {
/* 226 */       if (Aura.target != null) {
/* 227 */         if (Aura.target instanceof EntityPlayer) {
/* 228 */           trgt = (EntityPlayer)Aura.target;
/*     */         }
/*     */       } else {
/* 231 */         trgt = null;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 235 */       for (EntityPlayer ent : mc.field_71441_e.field_73010_i) {
/* 236 */         if (mc.field_71439_g.func_70068_e((Entity)ent) < 36.0D && 
/* 237 */           !Thunderhack.friendManager.isFriend(ent)) {
/* 238 */           trgt = ent;
/*     */         }
/*     */       } 
/*     */       
/* 242 */       if (trgt == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 248 */     if (getPosition((EntityPlayer)mc.field_71439_g) != null) {
/* 249 */       this.CoolPosition = getPosition((EntityPlayer)mc.field_71439_g);
/* 250 */       if (mc.field_71441_e.func_180495_p(this.CoolPosition).func_177230_c() == Blocks.field_150343_Z) {
/* 251 */         if (getCrystal(this.CoolPosition) != null) {
/*     */           return;
/*     */         }
/* 254 */         if (!this.placeDelay.passedMs(((Integer)this.placedelay.getValue()).intValue())) {
/*     */           return;
/*     */         }
/* 257 */         int crysslot = InventoryUtil.getCrysathotbar();
/* 258 */         if (crysslot == -1) {
/*     */           return;
/*     */         }
/* 261 */         if (((Boolean)this.offAura.getValue()).booleanValue()) {
/* 262 */           ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).disable();
/*     */         }
/* 264 */         InventoryUtil.switchToHotbarSlot(InventoryUtil.getCrysathotbar(), false);
/* 265 */         BlockUtils.placeBlockSmartRotate(this.CoolPosition.func_177982_a(0, 1, 0), EnumHand.MAIN_HAND, true, ((Boolean)this.packetplace.getValue()).booleanValue(), mc.field_71439_g.func_70093_af(), e);
/* 266 */         this.placeDelay.reset();
/*     */       } else {
/* 268 */         if (((Boolean)this.offAura.getValue()).booleanValue()) {
/* 269 */           ((Aura)Thunderhack.moduleManager.getModuleByClass(Aura.class)).disable();
/*     */         }
/* 271 */         int obbyslot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
/* 272 */         if (obbyslot == -1) {
/*     */           return;
/*     */         }
/* 275 */         InventoryUtil.switchToHotbarSlot(InventoryUtil.findHotbarBlock(BlockObsidian.class), false);
/* 276 */         BlockUtils.placeBlockSmartRotate(this.CoolPosition, EnumHand.MAIN_HAND, true, ((Boolean)this.packetplace.getValue()).booleanValue(), mc.field_71439_g.func_70093_af(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canPlace(BlockPos bp) {
/* 282 */     if (mc.field_71441_e.func_180495_p(bp.func_177982_a(0, 1, 0)).func_177230_c() != Blocks.field_150350_a) {
/* 283 */       return false;
/*     */     }
/* 285 */     if (mc.field_71441_e.func_180495_p(bp.func_177982_a(0, 2, 0)).func_177230_c() != Blocks.field_150350_a) {
/* 286 */       return false;
/*     */     }
/* 288 */     return (mc.field_71441_e.func_180495_p(bp).func_177230_c() == Blocks.field_150350_a || mc.field_71441_e.func_180495_p(bp).func_177230_c() == Blocks.field_150343_Z);
/*     */   }
/*     */   
/*     */   private BlockPos getPosition(EntityPlayer entity2) {
/* 292 */     ArrayList<BlockPos> arrayList = new ArrayList<>();
/* 293 */     int playerX = (int)entity2.field_70165_t;
/* 294 */     int playerZ = (int)entity2.field_70161_v;
/* 295 */     int n4 = 4;
/* 296 */     double playerX1 = entity2.field_70165_t - 0.5D;
/* 297 */     double playerY1 = entity2.field_70163_u + entity2.func_70047_e() - 1.0D;
/* 298 */     double playerZ1 = entity2.field_70161_v - 0.5D;
/* 299 */     for (int n5 = playerX - n4; n5 <= playerX + n4; n5++) {
/* 300 */       for (int n6 = playerZ - n4; n6 <= playerZ + n4; n6++) {
/* 301 */         if ((n5 - playerX1) * (n5 - playerX1) + (mc.field_71439_g.field_70163_u - playerY1) * (mc.field_71439_g.field_70163_u - playerY1) + (n6 - playerZ1) * (n6 - playerZ1) <= 25.0D && canPlace(new BlockPos(n5, mc.field_71439_g.field_70163_u, n6))) {
/* 302 */           if (mc.field_71441_e.func_180495_p(new BlockPos(n5, mc.field_71439_g.field_70163_u, n6)).func_177230_c() == Blocks.field_150343_Z && trgt.func_174831_c(new BlockPos(n5, mc.field_71439_g.field_70163_u, n6)) < 16.0D) {
/* 303 */             return new BlockPos(n5, mc.field_71439_g.field_70163_u, n6);
/*     */           }
/* 305 */           arrayList.add(new BlockPos(n5, mc.field_71439_g.field_70163_u, n6));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     return AI(arrayList);
/*     */   }
/*     */   
/*     */   public EntityEnderCrystal getCrystal(BlockPos blockPos) {
/* 314 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/* 315 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/* 316 */     for (Entity ent : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost, boost2.func_177982_a(1, 1, 1)))) {
/* 317 */       if (ent instanceof EntityEnderCrystal) {
/* 318 */         return (EntityEnderCrystal)ent;
/*     */       }
/*     */     } 
/* 321 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos AI(ArrayList<BlockPos> blocks) {
/* 326 */     BlockPos pos = null;
/* 327 */     double bestdist = 5.0D;
/* 328 */     if (trgt == null) return null; 
/* 329 */     for (BlockPos pos1 : blocks) {
/* 330 */       if (pos1.func_185332_f((int)trgt.field_70165_t, (int)trgt.field_70163_u, (int)trgt.field_70161_v) > 2.0D && trgt.func_174831_c(pos1) < bestdist) {
/* 331 */         bestdist = trgt.func_174831_c(pos1);
/* 332 */         pos = pos1;
/*     */       } 
/*     */     } 
/* 335 */     return pos;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 339 */     FullAuto, Semi, Bind;
/*     */   }
/*     */   
/*     */   public enum TargetMode {
/* 343 */     Aura, AutoExplosion;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AutoExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */