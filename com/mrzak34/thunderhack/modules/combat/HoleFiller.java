/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.notification.Notification;
/*     */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.InteractionUtil;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.SilentRotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class HoleFiller
/*     */   extends Module
/*     */ {
/*  33 */   private final Setting<Float> rangeXZ = register(new Setting("Range", Float.valueOf(6.0F), Float.valueOf(1.0F), Float.valueOf(7.0F)));
/*  34 */   private final Setting<Integer> predictTicks = register(new Setting("PredictTicks", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(25)));
/*     */ 
/*     */ 
/*     */   
/*  38 */   private final List<BlockPos> Holes = new ArrayList<>();
/*  39 */   private final Timer notification_timer = new Timer(); EntityPlayer target;
/*     */   BlockPos targetPosition;
/*     */   
/*     */   public HoleFiller() {
/*  43 */     super("HoleFiller", "HoleFiller", "HoleFiller", Module.Category.COMBAT);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntitySync(EventSync e) {
/*  50 */     Iterable<BlockPos> blocks = BlockPos.func_177980_a(mc.field_71439_g.func_180425_c().func_177963_a(-((Float)this.rangeXZ.getValue()).floatValue(), -((Float)this.rangeXZ.getValue()).floatValue(), -((Float)this.rangeXZ.getValue()).floatValue()), mc.field_71439_g.func_180425_c().func_177963_a(((Float)this.rangeXZ.getValue()).floatValue(), ((Float)this.rangeXZ.getValue()).floatValue(), ((Float)this.rangeXZ.getValue()).floatValue()));
/*  51 */     this.Holes.clear();
/*  52 */     for (BlockPos pos : blocks) {
/*  53 */       if ((!mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76230_c() || !mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a().func_76230_c() || !mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_185904_a().func_76230_c()) && 
/*  54 */         checkHole(pos)) {
/*  55 */         this.Holes.add(pos);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  60 */     if (((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).isEnabled() && ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).getTarget() != null) {
/*  61 */       this.target = ((AutoCrystal)Thunderhack.moduleManager.getModuleByClass(AutoCrystal.class)).getTarget();
/*     */     } else {
/*  63 */       this.target = findTarget();
/*     */     } 
/*     */     
/*  66 */     if (this.target == null)
/*     */       return; 
/*  68 */     double predict_x = (this.target.field_70165_t - this.target.field_70169_q) * ((Integer)this.predictTicks.getValue()).intValue();
/*  69 */     double predict_z = (this.target.field_70161_v - this.target.field_70166_s) * ((Integer)this.predictTicks.getValue()).intValue();
/*  70 */     BlockPos predict_pos = new BlockPos(this.target.field_70165_t + predict_x, this.target.field_70163_u, this.target.field_70161_v + predict_z);
/*     */     
/*  72 */     for (BlockPos bp : this.Holes) {
/*  73 */       if (this.target.func_174818_b(bp) < 4.0D) {
/*  74 */         fixHolePre(bp); continue;
/*  75 */       }  if (predict_pos.func_177954_c(bp.func_177958_n(), bp.func_177956_o(), bp.func_177952_p()) < 4.0D) {
/*  76 */         fixHolePre(bp);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixHolePre(BlockPos bp) {
/*  83 */     float[] angle = SilentRotationUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d((Vec3i)bp.func_177977_b().func_177963_a(0.5D, 1.0D, 0.5D)));
/*  84 */     mc.field_71439_g.field_70177_z = angle[0];
/*  85 */     mc.field_71439_g.field_70125_A = angle[1];
/*  86 */     this.targetPosition = bp;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void postEntitySync(EventPostSync e) {
/*  91 */     if (this.targetPosition != null) {
/*  92 */       int obby_slot = InventoryUtil.findHotbarBlock(Blocks.field_150343_Z);
/*  93 */       if (obby_slot == -1) {
/*  94 */         Command.sendMessage("no obby");
/*  95 */         this.targetPosition = null;
/*  96 */         toggle();
/*     */         return;
/*     */       } 
/*  99 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(obby_slot));
/* 100 */       InteractionUtil.placeBlock(this.targetPosition, true);
/* 101 */       if (this.notification_timer.passedMs(200L) && ((NotificationManager)Thunderhack.moduleManager.getModuleByClass(NotificationManager.class)).isOn()) {
/* 102 */         NotificationManager.publicity("HoleFiller " + ChatFormatting.GREEN + "hole X" + this.targetPosition.func_177958_n() + " Y" + this.targetPosition.func_177956_o() + " Z" + this.targetPosition.func_177952_p() + " is successfully blocked", 2, Notification.Type.SUCCESS);
/* 103 */         this.notification_timer.reset();
/*     */       } 
/* 105 */       this.targetPosition = null;
/* 106 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */     } 
/*     */   }
/*     */   
/*     */   public EntityPlayer findTarget() {
/* 111 */     EntityPlayer target = null;
/* 112 */     double distance = this.rangeXZ.getPow2Value();
/* 113 */     for (EntityPlayer entity : mc.field_71441_e.field_73010_i) {
/* 114 */       if (entity == mc.field_71439_g) {
/*     */         continue;
/*     */       }
/* 117 */       if (Thunderhack.friendManager.isFriend(entity)) {
/*     */         continue;
/*     */       }
/* 120 */       if (mc.field_71439_g.func_70068_e((Entity)entity) <= distance) {
/* 121 */         target = entity;
/* 122 */         distance = mc.field_71439_g.func_70068_e((Entity)entity);
/*     */       } 
/*     */     } 
/* 125 */     return target;
/*     */   }
/*     */   
/*     */   public boolean isOccupied(BlockPos blockPos) {
/* 129 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/* 130 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/* 131 */     for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost))) {
/* 132 */       if (entity instanceof EntityPlayer) {
/* 133 */         return true;
/*     */       }
/*     */     } 
/* 136 */     for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2))) {
/* 137 */       if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/* 138 */         return true;
/*     */       }
/*     */     } 
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public boolean checkHole(BlockPos pos) {
/* 145 */     return ((BlockUtils.validObi(pos) || BlockUtils.validBedrock(pos)) && !isOccupied(pos));
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\HoleFiller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */