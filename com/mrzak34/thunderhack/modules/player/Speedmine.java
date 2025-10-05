/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.DamageBlockEvent;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IPlayerControllerMP;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.render.BreakHighLight;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.SilentRotationUtil;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketClickWindow;
/*     */ import net.minecraft.network.play.client.CPacketConfirmTransaction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Speedmine
/*     */   extends Module
/*     */ {
/*     */   private static float mineDamage;
/*  45 */   private final Setting<Float> range = register(new Setting("Range", Float.valueOf(4.2F), Float.valueOf(3.0F), Float.valueOf(10.0F)));
/*  46 */   public Setting<Boolean> rotate = register(new Setting("Rotate", Boolean.valueOf(false)));
/*  47 */   public Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  48 */   public Setting<Boolean> strictReMine = register(new Setting("StrictBreak", Boolean.valueOf(false)));
/*  49 */   public Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(false)));
/*  50 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.Packet));
/*  51 */   private final Setting<Float> startDamage = register(new Setting("StartDamage", Float.valueOf(0.1F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.mode.getValue() == Mode.Damage)));
/*  52 */   private final Setting<Float> endDamage = register(new Setting("EndDamage", Float.valueOf(0.9F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.mode.getValue() == Mode.Damage)));
/*     */   
/*     */   private BlockPos minePosition;
/*     */   
/*     */   public Speedmine() {
/*  57 */     super("Speedmine", "позволяет быстро-копать", "Allows you to dig-quickly", Module.Category.PLAYER);
/*     */   }
/*     */   private EnumFacing mineFacing; private int mineBreaks;
/*     */   
/*     */   public void onUpdate() {
/*  62 */     if (!mc.field_71439_g.field_71075_bZ.field_75098_d) {
/*  63 */       if (this.minePosition != null) {
/*  64 */         double mineDistance = mc.field_71439_g.func_174818_b(this.minePosition.func_177963_a(0.5D, 0.5D, 0.5D));
/*  65 */         if ((this.mineBreaks >= 2 && ((Boolean)this.strictReMine.getValue()).booleanValue()) || mineDistance > this.range.getPow2Value()) {
/*  66 */           this.minePosition = null;
/*  67 */           this.mineFacing = null;
/*  68 */           mineDamage = 0.0F;
/*  69 */           this.mineBreaks = 0;
/*     */         } 
/*     */       } 
/*  72 */       if (this.mode.getValue() == Mode.Damage) {
/*  73 */         if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() < ((Float)this.startDamage.getValue()).floatValue())
/*  74 */           ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(((Float)this.startDamage.getValue()).floatValue()); 
/*  75 */         if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() >= ((Float)this.endDamage.getValue()).floatValue())
/*  76 */           ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(1.0F); 
/*  77 */       } else if (this.mode.getValue() == Mode.NexusGrief) {
/*  78 */         if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemPickaxe) {
/*  79 */           if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() < 0.17F)
/*  80 */             ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(0.17F); 
/*  81 */           if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() >= 0.83D)
/*  82 */             ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(1.0F); 
/*  83 */         } else if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemAxe) {
/*  84 */           if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() < 0.17F)
/*  85 */             ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(0.17F); 
/*  86 */           if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() >= 1.0F)
/*  87 */             ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(1.0F); 
/*  88 */         } else if (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151051_r || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151037_a || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151047_v) {
/*  89 */           if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() < 0.17F)
/*  90 */             ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(0.17F); 
/*  91 */           if (((IPlayerControllerMP)mc.field_71442_b).getCurBlockDamageMP() >= 1.0F)
/*  92 */             ((IPlayerControllerMP)mc.field_71442_b).setCurBlockDamageMP(1.0F); 
/*     */         } 
/*  94 */       } else if (this.mode.getValue() == Mode.Packet) {
/*  95 */         if (this.minePosition != null && !mc.field_71441_e.func_175623_d(this.minePosition)) {
/*  96 */           if (mineDamage >= 1.0F) {
/*  97 */             int previousSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*  98 */             int swapSlot = getTool(this.minePosition);
/*  99 */             if (swapSlot == -1)
/* 100 */               return;  if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 101 */               short nextTransactionID = mc.field_71439_g.field_71070_bA.func_75136_a(mc.field_71439_g.field_71071_by);
/* 102 */               ItemStack itemstack = mc.field_71439_g.field_71070_bA.func_184996_a(swapSlot, mc.field_71439_g.field_71071_by.field_70461_c, ClickType.SWAP, (EntityPlayer)mc.field_71439_g);
/* 103 */               mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketClickWindow(mc.field_71439_g.field_71069_bz.field_75152_c, swapSlot, mc.field_71439_g.field_71071_by.field_70461_c, ClickType.SWAP, itemstack, nextTransactionID));
/*     */             } else {
/* 105 */               mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(swapSlot));
/*     */             } 
/*     */             
/* 108 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.minePosition, this.mineFacing));
/* 109 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.minePosition, EnumFacing.UP));
/*     */             
/* 111 */             if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 112 */               mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.minePosition, this.mineFacing));
/*     */             }
/* 114 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.minePosition, this.mineFacing));
/* 115 */             if (previousSlot != -1) {
/* 116 */               if (((Boolean)this.strict.getValue()).booleanValue()) {
/* 117 */                 short nextTransactionID = mc.field_71439_g.field_71070_bA.func_75136_a(mc.field_71439_g.field_71071_by);
/* 118 */                 ItemStack itemstack = mc.field_71439_g.field_71070_bA.func_184996_a(swapSlot, mc.field_71439_g.field_71071_by.field_70461_c, ClickType.SWAP, (EntityPlayer)mc.field_71439_g);
/* 119 */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketClickWindow(mc.field_71439_g.field_71069_bz.field_75152_c, swapSlot, mc.field_71439_g.field_71071_by.field_70461_c, ClickType.SWAP, itemstack, nextTransactionID));
/* 120 */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketConfirmTransaction(mc.field_71439_g.field_71069_bz.field_75152_c, nextTransactionID, true));
/*     */               } else {
/* 122 */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(previousSlot));
/*     */               } 
/*     */             }
/* 125 */             mineDamage = 0.0F;
/* 126 */             this.mineBreaks++;
/*     */           } 
/* 128 */           mineDamage += getBlockStrength(mc.field_71441_e.func_180495_p(this.minePosition), this.minePosition);
/*     */         } else {
/* 130 */           mineDamage = 0.0F;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getBlockStrength(IBlockState state, BlockPos position) {
/* 137 */     float hardness = state.func_185887_b((World)mc.field_71441_e, position);
/* 138 */     if (hardness < 0.0F) {
/* 139 */       return 0.0F;
/*     */     }
/* 141 */     if (!canBreak(position)) {
/* 142 */       return getDigSpeed(state) / hardness / 100.0F;
/*     */     }
/* 144 */     return getDigSpeed(state) / hardness / 30.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDigSpeed(IBlockState state) {
/* 149 */     float digSpeed = getDestroySpeed(state);
/* 150 */     if (digSpeed > 1.0F) {
/* 151 */       ItemStack itemstack = getTool2(state);
/* 152 */       int efficiencyModifier = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, itemstack);
/* 153 */       if (efficiencyModifier > 0 && !itemstack.func_190926_b()) {
/* 154 */         digSpeed = (float)(digSpeed + StrictMath.pow(efficiencyModifier, 2.0D) + 1.0D);
/*     */       }
/*     */     } 
/* 157 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76422_e)) {
/* 158 */       digSpeed *= 1.0F + (mc.field_71439_g.func_70660_b(MobEffects.field_76422_e).func_76458_c() + 1) * 0.2F;
/*     */     }
/* 160 */     if (mc.field_71439_g.func_70644_a(MobEffects.field_76419_f)) {
/*     */       float fatigueScale;
/* 162 */       switch (mc.field_71439_g.func_70660_b(MobEffects.field_76419_f).func_76458_c()) {
/*     */         case 0:
/* 164 */           fatigueScale = 0.3F;
/*     */           break;
/*     */         case 1:
/* 167 */           fatigueScale = 0.09F;
/*     */           break;
/*     */         case 2:
/* 170 */           fatigueScale = 0.0027F;
/*     */           break;
/*     */         
/*     */         default:
/* 174 */           fatigueScale = 8.1E-4F;
/*     */           break;
/*     */       } 
/* 177 */       digSpeed *= fatigueScale;
/*     */     } 
/* 179 */     if (mc.field_71439_g.func_70055_a(Material.field_151586_h) && !EnchantmentHelper.func_185287_i((EntityLivingBase)mc.field_71439_g)) {
/* 180 */       digSpeed /= 5.0F;
/*     */     }
/* 182 */     if (!mc.field_71439_g.field_70122_E) {
/* 183 */       digSpeed /= 5.0F;
/*     */     }
/* 185 */     return (digSpeed < 0.0F) ? 0.0F : digSpeed;
/*     */   }
/*     */   
/*     */   public float getDestroySpeed(IBlockState state) {
/* 189 */     float destroySpeed = 1.0F;
/* 190 */     if (getTool2(state) != null && !getTool2(state).func_190926_b()) {
/* 191 */       destroySpeed *= getTool2(state).func_150997_a(state);
/*     */     }
/* 193 */     return destroySpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 198 */     this.minePosition = null;
/* 199 */     this.mineFacing = null;
/* 200 */     mineDamage = 0.0F;
/* 201 */     this.mineBreaks = 0;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent e) {
/* 206 */     if (this.mode.getValue() == Mode.Packet && 
/* 207 */       this.minePosition != null && !mc.field_71441_e.func_175623_d(this.minePosition)) {
/* 208 */       GlStateManager.func_179094_E();
/* 209 */       GL11.glPushAttrib(1048575);
/*     */ 
/*     */       
/* 212 */       GlStateManager.func_179120_a(770, 771, 1, 0);
/* 213 */       GlStateManager.func_179103_j(7425);
/* 214 */       GlStateManager.func_179097_i();
/* 215 */       AxisAlignedBB mineBox = mc.field_71441_e.func_180495_p(this.minePosition).func_185918_c((World)mc.field_71441_e, this.minePosition);
/* 216 */       Vec3d mineCenter = mineBox.func_189972_c();
/* 217 */       AxisAlignedBB shrunkMineBox = new AxisAlignedBB(mineCenter.field_72450_a, mineCenter.field_72448_b, mineCenter.field_72449_c, mineCenter.field_72450_a, mineCenter.field_72448_b, mineCenter.field_72449_c);
/* 218 */       BreakHighLight.renderBreakingBB2(shrunkMineBox.func_186664_h(MathUtil.clamp(mineDamage, 0.0F, 1.0F) * 0.5D), (mineDamage >= 0.95D) ? new Color(47, 255, 0, 120) : new Color(255, 0, 0, 120), (mineDamage >= 0.95D) ? new Color(0, 255, 13, 120) : new Color(255, 0, 0, 120));
/* 219 */       GL11.glPopAttrib();
/* 220 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onLeftClickBlock(DamageBlockEvent event) {
/* 227 */     if (canBreak(event.getBlockPos()) && !mc.field_71439_g.field_71075_bZ.field_75098_d) {
/* 228 */       if (this.mode.getValue() == Mode.Creative) {
/* 229 */         mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 230 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
/* 231 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getBlockPos(), event.getEnumFacing()));
/* 232 */         mc.field_71442_b.func_187103_a(event.getBlockPos());
/* 233 */         mc.field_71441_e.func_175698_g(event.getBlockPos());
/*     */       } 
/* 235 */       if (this.mode.getValue() == Mode.Packet && 
/* 236 */         !event.getBlockPos().equals(this.minePosition)) {
/* 237 */         this.minePosition = event.getBlockPos();
/* 238 */         this.mineFacing = event.getEnumFacing();
/* 239 */         mineDamage = 0.0F;
/* 240 */         this.mineBreaks = 0;
/* 241 */         if (this.minePosition != null && this.mineFacing != null) {
/* 242 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.minePosition, this.mineFacing));
/* 243 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.minePosition, EnumFacing.UP));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntitySync(EventSync event) {
/* 252 */     if (((Boolean)this.rotate.getValue()).booleanValue() && 
/* 253 */       mineDamage > 0.95D && 
/* 254 */       this.minePosition != null) {
/* 255 */       float[] angle = SilentRotationUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d((Vec3i)this.minePosition.func_177963_a(0.5D, 0.5D, 0.5D)));
/* 256 */       mc.field_71439_g.field_70177_z = angle[0];
/* 257 */       mc.field_71439_g.field_70125_A = angle[1];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 265 */     if (event.getPacket() instanceof CPacketHeldItemChange && (
/* 266 */       (Boolean)this.strict.getValue()).booleanValue()) {
/* 267 */       mineDamage = 0.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int getTool(BlockPos pos) {
/* 273 */     int index = -1;
/* 274 */     float CurrentFastest = 1.0F;
/* 275 */     for (int i = 0; i < 9; i++) {
/* 276 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 277 */       if (stack != ItemStack.field_190927_a) {
/* 278 */         float digSpeed = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack);
/* 279 */         float destroySpeed = stack.func_150997_a(mc.field_71441_e.func_180495_p(pos));
/* 280 */         if (digSpeed + destroySpeed > CurrentFastest) {
/* 281 */           CurrentFastest = digSpeed + destroySpeed;
/* 282 */           index = i;
/*     */         } 
/*     */       } 
/*     */     } 
/* 286 */     return index;
/*     */   }
/*     */   
/*     */   private ItemStack getTool2(IBlockState pos) {
/* 290 */     ItemStack itemStack = null;
/* 291 */     float CurrentFastest = 1.0F;
/* 292 */     for (int i = 0; i < 9; i++) {
/* 293 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 294 */       if (stack != ItemStack.field_190927_a) {
/* 295 */         float digSpeed = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, stack);
/* 296 */         float destroySpeed = stack.func_150997_a(pos);
/*     */         
/* 298 */         if (digSpeed + destroySpeed > CurrentFastest) {
/* 299 */           CurrentFastest = digSpeed + destroySpeed;
/* 300 */           itemStack = stack;
/*     */         } 
/*     */       } 
/*     */     } 
/* 304 */     return itemStack;
/*     */   }
/*     */   
/*     */   private boolean canBreak(BlockPos pos) {
/* 308 */     IBlockState blockState = mc.field_71441_e.func_180495_p(pos);
/* 309 */     Block block = blockState.func_177230_c();
/* 310 */     return (block.func_176195_g(blockState, (World)mc.field_71441_e, pos) != -1.0F);
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 314 */     Packet, Damage, Creative, NexusGrief;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\Speedmine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */