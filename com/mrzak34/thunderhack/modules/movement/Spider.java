/*     */ package com.mrzak34.thunderhack.modules.movement;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.MovementUtil;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class Spider extends Module {
/*  24 */   public final Setting<Integer> delay = register(new Setting("delay", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(15)));
/*  25 */   public Setting<Boolean> dropBlocks = register(new Setting("DropBlocks", Boolean.valueOf(false)));
/*  26 */   private final Setting<mode> a = register(new Setting("Mode", mode.Matrix));
/*     */   
/*     */   public Spider() {
/*  29 */     super("Spider", "Spider", Module.Category.MOVEMENT);
/*     */   }
/*     */   
/*     */   public static EnumFacing getPlaceableSide(BlockPos pos) {
/*  33 */     for (EnumFacing side : EnumFacing.values()) {
/*  34 */       BlockPos neighbour = pos.func_177972_a(side);
/*  35 */       if (!mc.field_71441_e.func_175623_d(neighbour)) {
/*     */ 
/*     */         
/*  38 */         IBlockState blockState = mc.field_71441_e.func_180495_p(neighbour);
/*  39 */         if (!blockState.func_185904_a().func_76222_j())
/*  40 */           return side; 
/*     */       } 
/*     */     } 
/*  43 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  48 */     if (!mc.field_71439_g.field_70123_F) {
/*     */       return;
/*     */     }
/*  51 */     if (this.a.getValue() == mode.Default) {
/*  52 */       mc.field_71439_g.field_70181_x = 0.2D;
/*  53 */       mc.field_71439_g.field_70160_al = false;
/*  54 */     } else if (this.a.getValue() == mode.Matrix) {
/*  55 */       if (mc.field_71439_g.field_70173_aa % ((Integer)this.delay.getValue()).intValue() == 0) {
/*  56 */         mc.field_71439_g.field_70122_E = true;
/*  57 */         mc.field_71439_g.field_70160_al = false;
/*     */       } else {
/*  59 */         mc.field_71439_g.field_70122_E = false;
/*     */       } 
/*  61 */       mc.field_71439_g.field_70167_r -= 2.0E-232D;
/*  62 */       if (mc.field_71439_g.field_70122_E) {
/*  63 */         mc.field_71439_g.field_70181_x = 0.41999998688697815D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMotion(EventSync event) {
/*  72 */     if (mc.field_71474_y.field_74314_A.func_151470_d() && mc.field_71439_g.field_70181_x <= -0.3739040364667221D && this.a.getValue() == mode.MatrixNew) {
/*  73 */       mc.field_71439_g.field_70122_E = true;
/*  74 */       mc.field_71439_g.field_70181_x = 0.48114514191918D;
/*     */     } 
/*  76 */     if (mc.field_71439_g.field_70173_aa % ((Integer)this.delay.getValue()).intValue() == 0 && mc.field_71439_g.field_70123_F && MovementUtil.isMoving() && this.a.getValue() == mode.Blocks) {
/*  77 */       int find = -2;
/*  78 */       for (int i = 0; i <= 8; i++) {
/*  79 */         if (mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof net.minecraft.item.ItemBlock)
/*  80 */           find = i; 
/*     */       } 
/*  82 */       if (find == -2) {
/*     */         return;
/*     */       }
/*  85 */       BlockPos pos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 2.0D, mc.field_71439_g.field_70161_v);
/*  86 */       EnumFacing side = getPlaceableSide(pos);
/*  87 */       if (side != null) {
/*  88 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(find));
/*     */         
/*  90 */         BlockPos neighbour = (new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 2.0D, mc.field_71439_g.field_70161_v)).func_177972_a(side);
/*  91 */         EnumFacing opposite = side.func_176734_d();
/*     */         
/*  93 */         Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
/*     */         
/*  95 */         float x = (float)(hitVec.field_72450_a - neighbour.func_177958_n());
/*  96 */         float y = (float)(hitVec.field_72448_b - neighbour.func_177956_o());
/*  97 */         float z = (float)(hitVec.field_72449_c - neighbour.func_177952_p());
/*     */         
/*  99 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(neighbour, opposite, EnumHand.MAIN_HAND, x, y, z));
/* 100 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/* 101 */         if (mc.field_71441_e.func_180495_p((new BlockPos((Entity)mc.field_71439_g)).func_177982_a(0, 2, 0)).func_177230_c() != Blocks.field_150350_a) {
/* 102 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
/* 103 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, neighbour, opposite));
/*     */         } 
/* 105 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */       } 
/*     */ 
/*     */       
/* 109 */       mc.field_71439_g.field_70122_E = true;
/* 110 */       mc.field_71439_g.field_70160_al = true;
/* 111 */       mc.field_71439_g.func_70664_aZ();
/* 112 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/* 113 */       if (((Boolean)this.dropBlocks.getValue()).booleanValue()) {
/* 114 */         for (int j = 9; j < 45; j++) {
/* 115 */           if (mc.field_71439_g.field_71069_bz.func_75139_a(j).func_75216_d() && 
/* 116 */             mc.field_71439_g.field_71069_bz.func_75139_a(j).func_75211_c().func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/* 117 */             mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, j, 0, ClickType.THROW, (EntityPlayer)mc.field_71439_g);
/* 118 */             mc.field_71439_g.func_70664_aZ();
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
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
/*     */   public enum mode
/*     */   {
/* 148 */     Default, Matrix, MatrixNew, Blocks;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\movement\Spider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */