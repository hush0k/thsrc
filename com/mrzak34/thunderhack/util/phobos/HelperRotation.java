/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.modules.combat.Burrow;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class HelperRotation
/*     */ {
/*  32 */   private static final AtomicInteger ID = new AtomicInteger();
/*     */   
/*     */   private final RotationSmoother smoother;
/*     */   
/*     */   private final AutoCrystal module;
/*     */ 
/*     */   
/*     */   public HelperRotation(AutoCrystal module) {
/*  40 */     this.smoother = new RotationSmoother(Thunderhack.rotationManager);
/*  41 */     this.module = module;
/*     */   }
/*     */   
/*     */   public static Runnable wrap(Runnable runnable) {
/*  45 */     return () -> acquire(runnable);
/*     */   }
/*     */   
/*     */   public static void acquire(Runnable runnable) {
/*     */     try {
/*  50 */       runnable.run();
/*  51 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startDigging(BlockPos pos, EnumFacing facing) {
/*  57 */     Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, facing));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void stopDigging(BlockPos pos, EnumFacing facing) {
/*  62 */     Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, facing));
/*     */   }
/*     */ 
/*     */   
/*     */   public static WeaknessSwitch antiWeakness(AutoCrystal module) {
/*  67 */     if (!module.weaknessHelper.isWeaknessed())
/*  68 */       return WeaknessSwitch.NONE; 
/*  69 */     if (module.antiWeakness.getValue() == AutoCrystal.AntiWeakness.None || ((Integer)module.cooldown
/*  70 */       .getValue()).intValue() != 0) {
/*  71 */       return WeaknessSwitch.INVALID;
/*     */     }
/*     */     
/*  74 */     return new WeaknessSwitch(DamageUtil.findAntiWeakness(), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public RotationFunction forPlacing(BlockPos pos, MutableWrapper<Boolean> hasPlaced) {
/*  79 */     int id = ID.incrementAndGet();
/*  80 */     Timer timer = new Timer();
/*  81 */     MutableWrapper<Boolean> ended = new MutableWrapper<>(Boolean.valueOf(false));
/*  82 */     return (x, y, z, yaw, pitch) -> {
/*     */         boolean breaking = false;
/*     */         float[] rotations = null;
/*     */         if (((Boolean)hasPlaced.get()).booleanValue() || (Util.mc.field_71439_g.func_174818_b(pos) > 64.0D && pos.func_177954_c(x, y, z) > 64.0D) || (this.module.autoSwitch.getValue() != AutoCrystal.AutoSwitch.Always && !this.module.switching && !this.module.weaknessHelper.canSwitch() && !InventoryUtil.isHolding(Items.field_185158_cP))) {
/*     */           if (!((Boolean)ended.get()).booleanValue()) {
/*     */             ended.set(Boolean.valueOf(true));
/*     */             timer.reset();
/*     */           } 
/*     */           if (!((Boolean)this.module.attack.getValue()).booleanValue() || timer.passedMs(((Integer)this.module.endRotations.getValue()).intValue())) {
/*     */             if (id == ID.get()) {
/*     */               this.module.rotation = null;
/*     */             }
/*     */             return new float[] { yaw, pitch };
/*     */           } 
/*     */           breaking = true;
/*     */           double height = 1.7D * ((Float)this.module.height.getValue()).floatValue();
/*     */           rotations = RotationSmoother.getRotations((pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1) + height, (pos.func_177952_p() + 0.5F), x, y, z, Util.mc.field_71439_g.func_70047_e());
/*     */         } else {
/*     */           double height = ((Double)this.module.placeHeight.getValue()).doubleValue();
/*     */           if (((Boolean)this.module.smartTrace.getValue()).booleanValue()) {
/*     */             for (EnumFacing facing : EnumFacing.values()) {
/*     */               Ray ray = RayTraceFactory.rayTrace((Entity)Util.mc.field_71439_g, pos, facing, (IBlockAccess)Util.mc.field_71441_e, Blocks.field_150343_Z.func_176223_P(), ((Double)this.module.traceWidth.getValue()).doubleValue());
/*     */               if (ray.isLegit()) {
/*     */                 rotations = ray.getRotations();
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           }
/*     */           if (rotations == null) {
/*     */             if (((Boolean)this.module.fallbackTrace.getValue()).booleanValue()) {
/*     */               rotations = RotationSmoother.getRotations(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 1.0D, pos.func_177952_p() + 0.5D, x, y, z, Util.mc.field_71439_g.func_70047_e());
/*     */             } else {
/*     */               rotations = RotationSmoother.getRotations(pos.func_177958_n() + 0.5D, pos.func_177956_o() + height, pos.func_177952_p() + 0.5D, x, y, z, Util.mc.field_71439_g.func_70047_e());
/*     */             } 
/*     */           }
/*     */         } 
/*     */         return this.smoother.smoothen(rotations, breaking ? ((Float)this.module.angle.getValue()).floatValue() : ((Float)this.module.placeAngle.getValue()).floatValue());
/*     */       };
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
/*     */   public RotationFunction forBreaking(Entity entity, MutableWrapper<Boolean> attacked) {
/* 160 */     int id = ID.incrementAndGet();
/* 161 */     Timer timer = new Timer();
/* 162 */     MutableWrapper<Boolean> ended = new MutableWrapper<>(Boolean.valueOf(false));
/* 163 */     return (x, y, z, yaw, pitch) -> {
/*     */         if (Util.mc.field_71439_g.func_70068_e(entity) > 64.0D) {
/*     */           attacked.set(Boolean.valueOf(true));
/*     */         }
/*     */         if (this.module.getTarget() == null) {
/*     */           attacked.set(Boolean.valueOf(true));
/*     */         }
/*     */         if (((Boolean)attacked.get()).booleanValue()) {
/*     */           if (!((Boolean)ended.get()).booleanValue()) {
/*     */             ended.set(Boolean.valueOf(true));
/*     */             timer.reset();
/*     */           } 
/*     */           if (((Boolean)ended.get()).booleanValue() && timer.passedMs(((Integer)this.module.endRotations.getValue()).intValue())) {
/*     */             if (id == ID.get()) {
/*     */               this.module.rotation = null;
/*     */             }
/*     */             return new float[] { yaw, pitch };
/*     */           } 
/*     */         } 
/*     */         return this.smoother.getRotations(entity, x, y, z, Util.mc.field_71439_g.func_70047_e(), ((Float)this.module.height.getValue()).floatValue(), ((Float)this.module.angle.getValue()).floatValue());
/*     */       };
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
/*     */   public RotationFunction forObby(PositionData data) {
/* 197 */     return (x, y, z, yaw, pitch) -> {
/*     */         if ((data.getPath()).length <= 0) {
/*     */           return new float[] { yaw, pitch };
/*     */         }
/*     */         Ray ray = data.getPath()[0];
/*     */         ray.updateRotations((Entity)Util.mc.field_71439_g);
/*     */         return ray.getRotations();
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable post(AutoCrystal module, float damage, boolean realtime, BlockPos pos, MutableWrapper<Boolean> placed, MutableWrapper<Boolean> liquidBreak) {
/* 215 */     return () -> {
/*     */         if (liquidBreak != null && !((Boolean)liquidBreak.get()).booleanValue()) {
/*     */           return;
/*     */         }
/*     */         if (!InventoryUtil.isHolding(Items.field_185158_cP)) {
/*     */           if (module.autoSwitch.getValue() == AutoCrystal.AutoSwitch.Always || (module.autoSwitch.getValue() == AutoCrystal.AutoSwitch.Bind && module.switching)) {
/*     */             if (!((Boolean)module.mainHand.getValue()).booleanValue());
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */         }
/*     */         int slot = -1;
/*     */         EnumHand hand = InventoryUtil.getHand(Items.field_185158_cP);
/*     */         if (hand == null) {
/*     */           if (((Boolean)module.mainHand.getValue()).booleanValue()) {
/*     */             slot = InventoryUtil.getCrysathotbar();
/*     */             if (slot == -1) {
/*     */               return;
/*     */             }
/*     */             hand = (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */         }
/*     */         RayTraceResult ray = CalculationMotion.rayTraceTo(pos, (IBlockAccess)Util.mc.field_71441_e);
/*     */         if (ray == null || !pos.equals(ray.func_178782_a())) {
/*     */           if (!module.noRotateNigga(AutoCrystal.ACRotate.Place) && !module.isNotCheckingRotations()) {
/*     */             return;
/*     */           }
/*     */           ray = new RayTraceResult(new Vec3d(0.5D, 1.0D, 0.5D), EnumFacing.UP);
/*     */         } else if (((Boolean)module.fallbackTrace.getValue()).booleanValue() && Util.mc.field_71441_e.func_180495_p(ray.func_178782_a().func_177972_a(ray.field_178784_b)).func_185904_a().func_76220_a()) {
/*     */           ray = new RayTraceResult(new Vec3d(0.5D, 1.0D, 0.5D), EnumFacing.UP);
/*     */         } 
/*     */         module.switching = false;
/*     */         AutoCrystal.SwingTime swingTime = (AutoCrystal.SwingTime)module.placeSwing.getValue();
/*     */         float[] f = RayTraceUtil.hitVecToPlaceVec(pos, ray.field_72307_f);
/*     */         boolean noGodded = false;
/*     */         if (module.idHelper.isDangerous((EntityPlayer)Util.mc.field_71439_g, ((Boolean)module.holdingCheck.getValue()).booleanValue(), ((Boolean)module.toolCheck.getValue()).booleanValue())) {
/*     */           module.noGod = true;
/*     */           noGodded = true;
/*     */         } 
/*     */         int finalSlot = slot;
/*     */         EnumHand finalHand = hand;
/*     */         RayTraceResult finalRay = ray;
/*     */         boolean finalNoGodded = noGodded;
/*     */         acquire(());
/*     */         if (realtime) {
/*     */           module.setRenderPos(pos, damage);
/*     */         }
/*     */         if (((Integer)module.simulatePlace.getValue()).intValue() != 0) {
/*     */           module.crystalRender.addFakeCrystal(new EntityEnderCrystal((World)Util.mc.field_71441_e, (pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F)));
/*     */         }
/*     */       };
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
/*     */   public Runnable post(Entity entity, MutableWrapper<Boolean> attacked) {
/* 380 */     return () -> {
/*     */         WeaknessSwitch w = antiWeakness(this.module);
/*     */         if ((w.needsSwitch() && w.getSlot() == -1) || EntityUtil.isDead(entity) || (!this.module.noRotateNigga(AutoCrystal.ACRotate.Break) && !this.module.isNotCheckingRotations() && !CalculationMotion.isLegit(entity, new Entity[0]))) {
/*     */           return;
/*     */         }
/*     */         CPacketUseEntity packet = new CPacketUseEntity(entity);
/*     */         AutoCrystal.SwingTime swingTime = (AutoCrystal.SwingTime)this.module.breakSwing.getValue();
/*     */         Runnable runnable = ();
/*     */         if (w.getSlot() != -1) {
/*     */           acquire(runnable);
/*     */         } else {
/*     */           runnable.run();
/*     */         } 
/*     */         if (((Boolean)this.module.pseudoSetDead.getValue()).booleanValue()) {
/*     */           ((IEntity)entity).setPseudoDeadT(true);
/*     */         }
/*     */         if (((Boolean)this.module.setDead.getValue()).booleanValue()) {
/*     */           Thunderhack.setDeadManager.setDead(entity);
/*     */         }
/*     */       };
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
/*     */   public Runnable postBlock(PositionData data) {
/* 459 */     return postBlock(data, -1, (AutoCrystal.Rotate)this.module.obbyRotate.getValue(), null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Runnable postBlock(PositionData data, int preSlot, AutoCrystal.Rotate rotate, MutableWrapper<Boolean> placed, MutableWrapper<Integer> switchBack) {
/* 467 */     return () -> {
/*     */         if (!data.isValid()) {
/*     */           return;
/*     */         }
/*     */         int slot = (preSlot == -1) ? InventoryUtil.findHotbarBlock(Blocks.field_150343_Z) : preSlot;
/*     */         if (slot == -1) {
/*     */           return;
/*     */         }
/*     */         EnumHand hand = (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
/*     */         AutoCrystal.PlaceSwing placeSwing = (AutoCrystal.PlaceSwing)this.module.obbySwing.getValue();
/*     */         acquire(());
/*     */         EnumHand swingHand = resolvehand3();
/*     */         if (swingHand != null) {
/*     */           Swing.Client.swing(swingHand);
/*     */         }
/*     */       };
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
/*     */   public Runnable breakBlock(int toolSlot, MutableWrapper<Boolean> placed, MutableWrapper<Integer> lastSlot, int[] order, Ray... positions) {
/* 578 */     return wrap(() -> {
/*     */           if (order.length != positions.length) {
/*     */             throw new IndexOutOfBoundsException("Order length: " + order.length + ", Positions length: " + positions.length);
/*     */           }
/*     */           if (!((Boolean)placed.get()).booleanValue()) {
/*     */             return;
/*     */           }
/*     */           switch ((AutoCrystal.CooldownBypass2)this.module.mineBypass.getValue()) {
/*     */             case None:
/*     */               CooldownBypass.None.switchTo(toolSlot);
/*     */               break;
/*     */             case Pick:
/*     */               CooldownBypass.Pick.switchTo(toolSlot);
/*     */               break;
/*     */             case Slot:
/*     */               CooldownBypass.Slot.switchTo(toolSlot);
/*     */               break;
/*     */             case Swap:
/*     */               CooldownBypass.Swap.switchTo(toolSlot);
/*     */               break;
/*     */           } 
/*     */           for (int i : order) {
/*     */             Ray ray = positions[i];
/*     */             BlockPos pos = ray.getPos().func_177972_a(ray.getFacing());
/*     */             startDigging(pos, ray.getFacing().func_176734_d());
/*     */             stopDigging(pos, ray.getFacing().func_176734_d());
/*     */             Swing.Packet.swing(EnumHand.MAIN_HAND);
/*     */           } 
/*     */           switch ((AutoCrystal.CooldownBypass2)this.module.mineBypass.getValue()) {
/*     */             case None:
/*     */               CooldownBypass.None.switchBack(((Integer)lastSlot.get()).intValue(), toolSlot);
/*     */               break;
/*     */             case Pick:
/*     */               CooldownBypass.Pick.switchBack(((Integer)lastSlot.get()).intValue(), toolSlot);
/*     */               break;
/*     */             case Slot:
/*     */               CooldownBypass.Slot.switchBack(((Integer)lastSlot.get()).intValue(), toolSlot);
/*     */               break;
/*     */             case Swap:
/*     */               CooldownBypass.Swap.switchBack(((Integer)lastSlot.get()).intValue(), toolSlot);
/*     */               break;
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void swing(EnumHand hand, boolean breaking) {
/* 629 */     Swing.Packet.swing(hand);
/* 630 */     EnumHand swingHand = breaking ? resolvehand() : resolvehand2();
/*     */     
/* 632 */     if (swingHand != null) {
/* 633 */       Swing.Client.swing(swingHand);
/*     */     }
/*     */   }
/*     */   
/*     */   EnumHand resolvehand() {
/* 638 */     switch ((AutoCrystal.SwingType)this.module.swing.getValue()) {
/*     */       case None:
/* 640 */         return null;
/*     */       case Pick:
/* 642 */         return EnumHand.OFF_HAND;
/*     */       case Slot:
/* 644 */         return EnumHand.MAIN_HAND;
/*     */     } 
/* 646 */     return EnumHand.MAIN_HAND;
/*     */   }
/*     */   
/*     */   EnumHand resolvehand2() {
/* 650 */     switch ((AutoCrystal.SwingType)this.module.placeHand.getValue()) {
/*     */       case None:
/* 652 */         return null;
/*     */       case Pick:
/* 654 */         return EnumHand.OFF_HAND;
/*     */       case Slot:
/* 656 */         return EnumHand.MAIN_HAND;
/*     */     } 
/* 658 */     return EnumHand.MAIN_HAND;
/*     */   }
/*     */   
/*     */   EnumHand resolvehand3() {
/* 662 */     switch ((AutoCrystal.SwingType)this.module.obbyHand.getValue()) {
/*     */       case None:
/* 664 */         return null;
/*     */       case Pick:
/* 666 */         return EnumHand.OFF_HAND;
/*     */       case Slot:
/* 668 */         return EnumHand.MAIN_HAND;
/*     */     } 
/* 670 */     return EnumHand.MAIN_HAND;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperRotation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */