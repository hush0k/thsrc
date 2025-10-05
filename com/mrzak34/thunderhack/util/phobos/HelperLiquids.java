/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.BlockUtils;
/*     */ import com.mrzak34.thunderhack.util.EntityUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HelperLiquids
/*     */ {
/*     */   private final AutoCrystal module;
/*     */   
/*     */   public HelperLiquids(AutoCrystal module) {
/*  31 */     this.module = module;
/*     */   }
/*     */   
/*     */   public static MineSlots getSlots(boolean onGroundCheck) {
/*  35 */     int bestBlock = -1;
/*  36 */     int bestTool = -1;
/*  37 */     float maxSpeed = 0.0F;
/*  38 */     for (int i = 8; i > -1; i--) {
/*  39 */       ItemStack stack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  40 */       if (stack.func_77973_b() instanceof ItemBlock) {
/*  41 */         Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
/*  42 */         int tool = getTool(block);
/*  43 */         float digSpeed = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, Util.mc.field_71439_g.field_71071_by.func_70301_a(tool));
/*  44 */         float destroySpeed = Util.mc.field_71439_g.field_71071_by.func_70301_a(tool).func_150997_a(block.func_176223_P());
/*  45 */         float damage = digSpeed + destroySpeed;
/*     */         
/*  47 */         if (damage > maxSpeed) {
/*  48 */           bestBlock = i;
/*  49 */           bestTool = tool;
/*  50 */           maxSpeed = damage;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     return new MineSlots(bestBlock, bestTool, maxSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlaceData calculate(HelperPlace placeHelper, PlaceData placeData, List<EntityPlayer> friends, List<EntityPlayer> players, float minDamage) {
/*  63 */     PlaceData newData = new PlaceData(minDamage);
/*  64 */     newData.setTarget(placeData.getTarget());
/*  65 */     for (PositionData data : placeData.getLiquid()) {
/*  66 */       if (placeHelper.validate(placeData, data, friends) != null) {
/*  67 */         placeHelper.calcPositionData(newData, data, players);
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return newData;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getTool(Block pos) {
/*  76 */     int index = -1;
/*  77 */     float CurrentFastest = 1.0F;
/*  78 */     for (int i = 0; i < 9; i++) {
/*  79 */       ItemStack stack = Util.mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  80 */       if (stack != ItemStack.field_190927_a) {
/*  81 */         float digSpeed = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack);
/*  82 */         float destroySpeed = stack.func_150997_a(pos.func_176223_P());
/*     */         
/*  84 */         if (pos instanceof net.minecraft.block.BlockAir) return 0; 
/*  85 */         if (digSpeed + destroySpeed > CurrentFastest) {
/*  86 */           CurrentFastest = digSpeed + destroySpeed;
/*  87 */           index = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*  91 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumFacing getAbsorbFacing(BlockPos pos, List<Entity> entities, IBlockAccess access, double placeRange) {
/*  98 */     for (EnumFacing facing : EnumFacing.values()) {
/*  99 */       if (facing != EnumFacing.DOWN) {
/*     */ 
/*     */ 
/*     */         
/* 103 */         BlockPos offset = pos.func_177972_a(facing);
/* 104 */         if (BlockUtils.getDistanceSq(offset) < MathUtil.square(placeRange))
/*     */         {
/*     */ 
/*     */           
/* 108 */           if (access.func_180495_p(offset).func_185904_a().func_76222_j()) {
/* 109 */             boolean found = false;
/* 110 */             AxisAlignedBB bb = new AxisAlignedBB(offset);
/* 111 */             for (Entity entity : entities) {
/* 112 */               if (entity == null || 
/* 113 */                 EntityUtil.isDead(entity) || !entity.field_70156_m) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/* 118 */               if (this.module.bbBlockingHelper.blocksBlock(bb, entity)) {
/* 119 */                 found = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 124 */             if (!found)
/* 125 */               return facing; 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 130 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\HelperLiquids.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */