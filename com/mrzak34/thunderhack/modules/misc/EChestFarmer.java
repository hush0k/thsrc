/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InteractionUtil;
/*     */ import com.mrzak34.thunderhack.util.SilentRotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class EChestFarmer extends Module {
/*  28 */   private final Setting<Integer> range = register(new Setting("Range", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(3)));
/*  29 */   private final Setting<Integer> bd = register(new Setting("BreakDelay", Integer.valueOf(4000), Integer.valueOf(0), Integer.valueOf(5000)));
/*  30 */   private final Timer timer = new Timer();
/*  31 */   private final Timer breakTimer = new Timer();
/*  32 */   private InteractionUtil.Placement placement = null;
/*     */   public EChestFarmer() {
/*  34 */     super("EChestFarmer", "афк фарм обсы", Module.Category.MISC);
/*     */   }
/*     */   
/*     */   public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
/*  38 */     List<BlockPos> circleblocks = new ArrayList<>();
/*  39 */     int cx = loc.func_177958_n();
/*  40 */     int cy = loc.func_177956_o();
/*  41 */     int cz = loc.func_177952_p();
/*  42 */     for (int x = cx - (int)r; x <= cx + r; x++) {
/*  43 */       for (int z = cz - (int)r; z <= cz + r; ) {
/*  44 */         int y = sphere ? (cy - (int)r) : cy; for (;; z++) { if (y < (sphere ? (cy + r) : (cy + h))) {
/*  45 */             double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
/*  46 */             if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
/*  47 */               BlockPos l = new BlockPos(x, y + plus_y, z);
/*  48 */               circleblocks.add(l);
/*     */             }  y++; continue;
/*     */           }  }
/*     */       
/*     */       } 
/*  53 */     }  return circleblocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  58 */     this.placement = null;
/*  59 */     this.breakTimer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayer(EventSync event) {
/*  65 */     this.placement = null;
/*  66 */     if (event.isCanceled() || !InteractionUtil.canPlaceNormally()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  71 */     BlockPos closestEChest = getSphere(new BlockPos((Entity)mc.field_71439_g), ((Integer)this.range.getValue()).intValue(), ((Integer)this.range.getValue()).intValue(), false, true, 0).stream().filter(pos -> mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockEnderChest).min(Comparator.comparing(pos -> Double.valueOf(mc.field_71439_g.func_70011_f(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D)))).orElse(null);
/*     */     
/*  73 */     if (closestEChest != null) {
/*  74 */       if (this.breakTimer.passedMs(((Integer)this.bd.getValue()).intValue())) {
/*  75 */         boolean holdingPickaxe = (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151046_w);
/*     */         
/*  77 */         if (!holdingPickaxe) {
/*  78 */           for (int i = 0; i < 9; i++) {
/*  79 */             ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*     */             
/*  81 */             if (!stack.func_190926_b())
/*     */             {
/*     */ 
/*     */               
/*  85 */               if (stack.func_77973_b() == Items.field_151046_w) {
/*  86 */                 holdingPickaxe = true;
/*  87 */                 mc.field_71439_g.field_71071_by.field_70461_c = i;
/*  88 */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(i));
/*     */                 break;
/*     */               } 
/*     */             }
/*     */           } 
/*     */         }
/*  94 */         if (!holdingPickaxe) {
/*     */           return;
/*     */         }
/*     */         
/*  98 */         EnumFacing facing = mc.field_71439_g.func_174811_aO().func_176734_d();
/*     */         
/* 100 */         SilentRotationUtil.lookAtVector(new Vec3d(closestEChest.func_177958_n() + 0.5D + facing.func_176730_m().func_177958_n() * 0.5D, closestEChest
/* 101 */               .func_177956_o() + 0.5D + facing.func_176730_m().func_177956_o() * 0.5D, closestEChest
/* 102 */               .func_177952_p() + 0.5D + facing.func_176730_m().func_177952_p() * 0.5D));
/*     */         
/* 104 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 105 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, closestEChest, facing));
/* 106 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, closestEChest, facing));
/* 107 */         this.breakTimer.reset();
/*     */       } 
/* 109 */     } else if (this.timer.passedMs(350L)) {
/* 110 */       this.timer.reset();
/* 111 */       if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemBlock) {
/* 112 */         ItemBlock block = (ItemBlock)mc.field_71439_g.func_184614_ca().func_77973_b();
/* 113 */         if (block.func_179223_d() != Blocks.field_150477_bB && 
/* 114 */           !changeToEChest()) {
/*     */           return;
/*     */         }
/* 117 */       } else if (!changeToEChest()) {
/*     */         return;
/*     */       } 
/* 120 */       for (BlockPos pos : getSphere(new BlockPos((Entity)mc.field_71439_g), ((Integer)this.range.getValue()).intValue(), ((Integer)this.range.getValue()).intValue(), false, true, 0)) {
/* 121 */         InteractionUtil.Placement cPlacement = InteractionUtil.preparePlacement(pos, true, event);
/* 122 */         if (cPlacement != null) {
/* 123 */           this.placement = cPlacement;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdateWalkingPlayerPost(EventPostSync event) {
/* 132 */     if (this.placement != null) {
/* 133 */       InteractionUtil.placeBlockSafely(this.placement, EnumHand.MAIN_HAND, false);
/* 134 */       this.breakTimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean changeToEChest() {
/* 139 */     for (int i = 0; i < 9; i++) {
/* 140 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*     */       
/* 142 */       if (!stack.func_190926_b() && stack.func_77973_b() instanceof ItemBlock) {
/*     */ 
/*     */ 
/*     */         
/* 146 */         ItemBlock block = (ItemBlock)stack.func_77973_b();
/* 147 */         if (block.func_179223_d() == Blocks.field_150477_bB) {
/* 148 */           mc.field_71439_g.field_71071_by.field_70461_c = i;
/* 149 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(i));
/* 150 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 154 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\EChestFarmer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */