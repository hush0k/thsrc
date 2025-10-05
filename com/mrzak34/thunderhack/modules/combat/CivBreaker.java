/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InteractionUtil;
/*     */ import com.mrzak34.thunderhack.util.RotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ public class CivBreaker
/*     */   extends Module
/*     */ {
/*  35 */   private final Setting<type> targetType = register(new Setting("Target", type.NEAREST));
/*  36 */   private final Setting<mode> breakMode = register(new Setting("Break Mode", mode.Vanilla));
/*  37 */   private final Setting<Integer> startDelay = register(new Setting("Start Delay", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(10)));
/*  38 */   private final Setting<Integer> breakDelay = register(new Setting("Break Delay", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(10)));
/*  39 */   private final Setting<Integer> crystalDelay = register(new Setting("Crystal Delay", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(10)));
/*  40 */   private final Setting<Integer> hitDelay = register(new Setting("Hit Delay", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(10)));
/*  41 */   private final Setting<Integer> nosleep = register(new Setting("Block Delay", Integer.valueOf(3), Integer.valueOf(0), Integer.valueOf(10)));
/*     */   private boolean placedCrystal = false;
/*     */   private boolean breaking = false;
/*     */   private boolean broke = false;
/*  45 */   private EntityPlayer target = null;
/*  46 */   private BlockPos breakPos = null;
/*  47 */   private BlockPos placePos = null;
/*  48 */   private int timer = 0;
/*  49 */   private int attempts = 0;
/*     */   public CivBreaker() {
/*  51 */     super("CivBreaker", "CivBreaker", Module.Category.COMBAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  56 */     int pix = findItem(Items.field_151046_w);
/*  57 */     if (pix != -1) {
/*  58 */       mc.field_71439_g.field_71071_by.field_70461_c = pix;
/*     */     }
/*  60 */     this.target = null;
/*  61 */     this.placedCrystal = false;
/*  62 */     this.breaking = false;
/*  63 */     this.broke = false;
/*  64 */     this.timer = 0;
/*  65 */     this.attempts = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntitySync(EventSync ev) {
/*  71 */     int pix = findItem(Items.field_151046_w);
/*  72 */     int crystal = findItem(Items.field_185158_cP);
/*  73 */     int obby = findMaterials();
/*     */     
/*  75 */     if (pix == -1 || crystal == -1 || obby == -1) {
/*  76 */       Command.sendMessage("No materials!");
/*  77 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     if (this.target == null) {
/*  82 */       if (this.targetType.getValue() == type.NEAREST) {
/*  83 */         this.target = Util.mc.field_71441_e.field_73010_i.stream().filter(p -> (p.func_145782_y() != Util.mc.field_71439_g.func_145782_y())).min(Comparator.comparing(p -> Float.valueOf(p.func_70032_d((Entity)Util.mc.field_71439_g)))).orElse(null);
/*     */       }
/*  85 */       if (this.target == null) {
/*  86 */         disable();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  91 */     searchSpace();
/*     */     
/*  93 */     if (!this.placedCrystal) {
/*  94 */       if (this.timer < ((Integer)this.startDelay.getValue()).intValue()) {
/*  95 */         this.timer++;
/*     */         return;
/*     */       } 
/*  98 */       this.timer = 0;
/*  99 */       doPlace(obby, crystal);
/* 100 */     } else if (!this.breaking) {
/* 101 */       if (this.timer < ((Integer)this.breakDelay.getValue()).intValue()) {
/* 102 */         this.timer++;
/*     */         return;
/*     */       } 
/* 105 */       this.timer = 0;
/* 106 */       if (this.breakMode.getValue() == mode.Vanilla) {
/* 107 */         Util.mc.field_71439_g.field_71071_by.field_70461_c = pix;
/* 108 */         Util.mc.field_71442_b.func_78765_e();
/* 109 */         Util.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 110 */         Util.mc.field_71442_b.func_180512_c(this.breakPos, EnumFacing.DOWN);
/*     */       } else {
/* 112 */         Util.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 113 */         Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
/* 114 */         Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, EnumFacing.DOWN));
/*     */       } 
/* 116 */       this.breaking = true;
/* 117 */     } else if (!this.broke) {
/* 118 */       if (getBlock(this.breakPos) == Blocks.field_150350_a) {
/* 119 */         this.broke = true;
/*     */       }
/*     */     } else {
/* 122 */       if (this.timer < ((Integer)this.crystalDelay.getValue()).intValue()) {
/* 123 */         this.timer++;
/*     */         return;
/*     */       } 
/* 126 */       this.timer = 0;
/* 127 */       Entity bcrystal = Util.mc.field_71441_e.field_72996_f.stream().filter(e -> e instanceof net.minecraft.entity.item.EntityEnderCrystal).min(Comparator.comparing(c -> Float.valueOf(c.func_70032_d((Entity)this.target)))).orElse(null);
/* 128 */       if (bcrystal == null) {
/* 129 */         if (this.attempts < ((Integer)this.hitDelay.getValue()).intValue()) {
/* 130 */           this.attempts++;
/*     */           return;
/*     */         } 
/* 133 */         if (this.attempts < ((Integer)this.nosleep.getValue()).intValue()) {
/* 134 */           this.attempts++;
/*     */           return;
/*     */         } 
/* 137 */         this.placedCrystal = false;
/* 138 */         this.breaking = false;
/* 139 */         this.broke = false;
/* 140 */         this.attempts = 0;
/*     */       } else {
/* 142 */         float[] angle = MathUtil.calcAngle(AutoCrystal.mc.field_71439_g.func_174824_e(mc.func_184121_ak()), bcrystal.func_174791_d());
/* 143 */         mc.field_71439_g.field_70177_z = angle[0];
/* 144 */         mc.field_71439_g.field_70125_A = angle[1];
/* 145 */         Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(bcrystal));
/* 146 */         this.placedCrystal = false;
/* 147 */         this.breaking = false;
/* 148 */         this.broke = false;
/* 149 */         this.attempts = 0;
/*     */       } 
/*     */     } 
/* 152 */     if (this.breaking && 
/* 153 */       this.breakPos != null) {
/* 154 */       float[] angle = RotationUtil.getRotations(this.breakPos, EnumFacing.DOWN);
/* 155 */       mc.field_71439_g.field_70177_z = angle[0];
/* 156 */       mc.field_71439_g.field_70125_A = angle[1];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void doPlace(int obby, int crystal) {
/* 163 */     if (this.placePos == null)
/* 164 */       return;  if (getBlock(this.placePos) == Blocks.field_150350_a) {
/* 165 */       int oldslot = Util.mc.field_71439_g.field_71071_by.field_70461_c;
/* 166 */       Util.mc.field_71439_g.field_71071_by.field_70461_c = obby;
/* 167 */       Util.mc.field_71442_b.func_78765_e();
/* 168 */       InteractionUtil.placeBlock(this.placePos, true);
/* 169 */       Util.mc.field_71439_g.field_71071_by.field_70461_c = oldslot;
/* 170 */     } else if (!this.placedCrystal) {
/* 171 */       int oldslot = Util.mc.field_71439_g.field_71071_by.field_70461_c;
/* 172 */       if (crystal != 999) {
/* 173 */         Util.mc.field_71439_g.field_71071_by.field_70461_c = crystal;
/*     */       }
/* 175 */       Util.mc.field_71442_b.func_78765_e();
/* 176 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(this.placePos, EnumFacing.UP, (Util.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
/* 177 */       Util.mc.field_71439_g.field_71071_by.field_70461_c = oldslot;
/* 178 */       this.placedCrystal = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void searchSpace() {
/* 183 */     BlockPos tpos = new BlockPos(this.target.field_70165_t, this.target.field_70163_u, this.target.field_70161_v);
/*     */     
/* 185 */     BlockPos[] offset = { new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 193 */     if (getBlock(tpos) != Blocks.field_150350_a || getBlock(tpos.func_177982_a(0, 1, 0)) != Blocks.field_150350_a) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     List<BlockPos> posList = new ArrayList<>();
/* 198 */     for (BlockPos blockPos : offset) {
/* 199 */       BlockPos offsetPos = tpos.func_177971_a((Vec3i)blockPos);
/* 200 */       Block block = getBlock(offsetPos);
/* 201 */       Block block2 = getBlock(offsetPos.func_177982_a(0, 1, 0));
/* 202 */       Block block3 = getBlock(offsetPos.func_177982_a(0, 2, 0));
/* 203 */       if (block != Blocks.field_150350_a && !(block instanceof net.minecraft.block.BlockLiquid) && block2 != Blocks.field_150357_h && block3 == Blocks.field_150350_a) {
/* 204 */         posList.add(offsetPos);
/*     */       }
/*     */     } 
/*     */     
/* 208 */     BlockPos base = posList.stream().max(Comparator.comparing(b -> Double.valueOf(mc.field_71439_g.func_70011_f(b.func_177958_n(), b.func_177956_o(), b.func_177952_p())))).orElse(null);
/* 209 */     if (base == null) {
/*     */       return;
/*     */     }
/* 212 */     this.placePos = base.func_177982_a(0, 1, 0);
/* 213 */     this.breakPos = base.func_177982_a(0, 1, 0);
/*     */   }
/*     */   
/*     */   private int findMaterials() {
/* 217 */     for (int i = 0; i < 9; i++) {
/* 218 */       if (Util.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof ItemBlock && ((ItemBlock)Util.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b()).func_179223_d() == Blocks.field_150343_Z) {
/* 219 */         return i;
/*     */       }
/*     */     } 
/* 222 */     return -1;
/*     */   }
/*     */   
/*     */   private int findItem(Item item) {
/* 226 */     if (item == Items.field_185158_cP && Util.mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_185158_cP) {
/* 227 */       return 999;
/*     */     }
/* 229 */     for (int i = 0; i < 9; i++) {
/* 230 */       if (Util.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == item) {
/* 231 */         return i;
/*     */       }
/*     */     } 
/* 234 */     return -1;
/*     */   }
/*     */   
/*     */   private Block getBlock(BlockPos b) {
/* 238 */     return Util.mc.field_71441_e.func_180495_p(b).func_177230_c();
/*     */   }
/*     */   
/*     */   public enum type
/*     */   {
/* 243 */     NEAREST,
/* 244 */     LOOKING;
/*     */   }
/*     */   
/*     */   public enum mode {
/* 248 */     Vanilla,
/* 249 */     Packet;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\CivBreaker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */