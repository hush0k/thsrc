/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.AccessorMinecraft;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class AutoMine
/*     */   extends Module {
/*  30 */   public Setting<Boolean> autodisable = register(new Setting("AutoDisable", Boolean.valueOf(true)));
/*  31 */   public Setting<Boolean> switchbool = register(new Setting("Switch", Boolean.valueOf(true)));
/*  32 */   public Setting<Boolean> requirepickaxe = register(new Setting("RequirePick", Boolean.valueOf(true)));
/*  33 */   public Setting<Boolean> focused = register(new Setting("Focused", Boolean.valueOf(true)));
/*  34 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.FEET));
/*  35 */   private BlockPos blockpos = null;
/*     */ 
/*     */   
/*     */   public AutoMine() {
/*  39 */     super("AutoMine", "AutoMine", Module.Category.PLAYER);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<BlockPos> blockPosList(BlockPos blockPos) {
/*  44 */     ArrayList<BlockPos> arrayList = new ArrayList<>();
/*  45 */     arrayList.add(blockPos.func_177982_a(1, 0, 0));
/*  46 */     arrayList.add(blockPos.func_177982_a(-1, 0, 0));
/*  47 */     arrayList.add(blockPos.func_177982_a(0, 0, 1));
/*  48 */     arrayList.add(blockPos.func_177982_a(0, 0, -1));
/*  49 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3d vec3dPosition() {
/*  54 */     return new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] shitMethod(Vec3d vec3d) {
/*  59 */     Vec3d vec3d2 = vec3dPosition();
/*  60 */     Vec3d vec3d3 = vec3d;
/*  61 */     double d = vec3d3.field_72450_a - vec3d2.field_72450_a;
/*  62 */     double d2 = vec3d3.field_72448_b - vec3d2.field_72448_b;
/*  63 */     double d3 = vec3d3.field_72449_c - vec3d2.field_72449_c;
/*  64 */     double d4 = d;
/*  65 */     double d5 = d3;
/*  66 */     double d6 = Math.sqrt(d4 * d4 + d5 * d5);
/*  67 */     float f = (float)Math.toDegrees(Math.atan2(d3, d)) - 90.0F;
/*  68 */     float f2 = (float)-Math.toDegrees(Math.atan2(d2, d6));
/*  69 */     float[] fArray = new float[2];
/*  70 */     fArray[0] = mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(f - mc.field_71439_g.field_70177_z);
/*  71 */     fArray[1] = mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(f2 - mc.field_71439_g.field_70125_A);
/*  72 */     return fArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float[] shitMethod2(BlockPos blockPos) {
/*  77 */     Vec3d vec3d = vec3dPosition();
/*  78 */     Vec3d vec3d2 = (new Vec3d((Vec3i)blockPos)).func_72441_c(0.5D, 0.5D, 0.5D);
/*  79 */     double d = vec3d.func_72436_e(vec3d2);
/*  80 */     EnumFacing[] enumFacingArray = EnumFacing.values();
/*  81 */     int n = enumFacingArray.length;
/*  82 */     int n2 = 0;
/*  83 */     if (0 < n) {
/*  84 */       EnumFacing enumFacing = enumFacingArray[n2];
/*  85 */       Vec3d vec3d3 = vec3d2.func_178787_e((new Vec3d(enumFacing.func_176730_m())).func_186678_a(0.5D));
/*  86 */       return shitMethod(vec3d3);
/*     */     } 
/*  88 */     return shitMethod(vec3d2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(BlockPos blockPos) {
/*  93 */     Vec3d vec3d = vec3dPosition();
/*  94 */     Vec3d vec3d2 = (new Vec3d((Vec3i)blockPos)).func_72441_c(0.5D, 0.5D, 0.5D);
/*  95 */     double d = vec3d.func_72436_e(vec3d2);
/*  96 */     EnumFacing[] enumFacingArray = EnumFacing.values();
/*  97 */     int n = enumFacingArray.length;
/*  98 */     int n2 = 0;
/*  99 */     if (0 < n) {
/* 100 */       EnumFacing enumFacing = enumFacingArray[n2];
/* 101 */       return enumFacing;
/*     */     } 
/* 103 */     return EnumFacing.UP;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 108 */     this.blockpos = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 113 */     this.blockpos = null;
/* 114 */     KeyBinding.func_74510_a(mc.field_71474_y.field_74312_F.func_151463_i(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 119 */     if (this.mode.getValue() == Mode.CONTINIOUS) {
/* 120 */       if (!((Boolean)this.focused.getValue()).booleanValue()) {
/* 121 */         ((AccessorMinecraft)mc).setLeftClickCounter(0);
/*     */       }
/* 123 */       ((AccessorMinecraft)mc).invokeSendClickBlockToController(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPreMotion(EventSync event) {
/* 129 */     if (this.mode.getValue() == Mode.CONTINIOUS)
/* 130 */       return;  if (!((Boolean)this.switchbool.getValue()).booleanValue() || checkPickaxe()) {
/* 131 */       if (this.blockpos != null && 
/* 132 */         mc.field_71441_e.func_180495_p(this.blockpos).func_177230_c().equals(Blocks.field_150350_a)) {
/* 133 */         if (((Boolean)this.autodisable.getValue()).booleanValue()) {
/* 134 */           disable();
/*     */           
/*     */           return;
/*     */         } 
/* 138 */         this.blockpos = null;
/*     */       } 
/*     */ 
/*     */       
/* 142 */       BlockPos blockpos2 = null;
/*     */       
/* 144 */       for (Entity obj : mc.field_71441_e.field_73010_i.stream().filter(player -> (player != mc.field_71439_g && !Thunderhack.friendManager.isFriend(player.func_70005_c_()) && Float.compare(mc.field_71439_g.func_70032_d((Entity)player), 7.0F) < 0)).collect(Collectors.toList())) {
/* 145 */         BlockPos pos = new BlockPos(obj.func_174791_d());
/* 146 */         if (!checkBlockPos(pos))
/*     */           continue; 
/* 148 */         for (BlockPos pos2 : blockPosList(pos)) {
/* 149 */           if (!(mc.field_71441_e.func_180495_p(pos2).func_177230_c() instanceof net.minecraft.block.BlockObsidian) || 
/* 150 */             !mc.field_71441_e.func_180495_p(pos2.func_177982_a(0, 1, 0)).func_185904_a().equals(Material.field_151579_a))
/*     */             continue; 
/* 152 */           double dist = mc.field_71439_g.func_70011_f(pos2.func_177958_n(), pos2.func_177956_o(), pos2.func_177952_p());
/* 153 */           if (dist < 5.0D) {
/* 154 */             blockpos2 = pos2;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 160 */       if (blockpos2 != null) {
/* 161 */         if (((Boolean)this.switchbool.getValue()).booleanValue() && InventoryUtil.getPicatHotbar() != -1) {
/* 162 */           mc.field_71439_g.field_71071_by.field_70461_c = InventoryUtil.getPicatHotbar();
/*     */         }
/*     */         
/* 165 */         float[] rotation = shitMethod2(blockpos2);
/* 166 */         mc.field_71439_g.field_70177_z = rotation[0];
/* 167 */         mc.field_71439_g.field_70125_A = rotation[1];
/*     */         
/* 169 */         if (!((Boolean)this.requirepickaxe.getValue()).booleanValue() || mc.field_71439_g.func_184614_ca().func_77973_b().equals(Items.field_151046_w)) {
/* 170 */           if (this.blockpos != null && 
/* 171 */             this.blockpos.equals(blockpos2) && (
/* 172 */             (Speedmine)Thunderhack.moduleManager.getModuleByClass(Speedmine.class)).isEnabled()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 177 */           mc.field_71442_b.func_180512_c(blockpos2, getFacing(blockpos2));
/* 178 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 179 */           this.blockpos = blockpos2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean checkPickaxe() {
/* 186 */     Item item = mc.field_71439_g.func_184614_ca().func_77973_b();
/*     */     
/* 188 */     return (item.equals(Items.field_151046_w) || item.equals(Items.field_151035_b) || item
/* 189 */       .equals(Items.field_151005_D) || item.equals(Items.field_151050_s) || item
/* 190 */       .equals(Items.field_151039_o));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkValidBlock(Block block) {
/* 195 */     return (block.equals(Blocks.field_150343_Z) || block.equals(Blocks.field_150357_h));
/*     */   }
/*     */   
/*     */   public boolean checkBlockPos(BlockPos blockPos) {
/* 199 */     Block block = mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, -1, 0)).func_177230_c();
/* 200 */     Block block2 = mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, -1)).func_177230_c();
/* 201 */     Block block3 = mc.field_71441_e.func_180495_p(blockPos.func_177982_a(1, 0, 0)).func_177230_c();
/* 202 */     Block block4 = mc.field_71441_e.func_180495_p(blockPos.func_177982_a(0, 0, 1)).func_177230_c();
/* 203 */     Block block5 = mc.field_71441_e.func_180495_p(blockPos.func_177982_a(-1, 0, 0)).func_177230_c();
/* 204 */     if (mc.field_71441_e.func_175623_d(blockPos) && 
/* 205 */       mc.field_71441_e.func_175623_d(blockPos.func_177982_a(0, 1, 0)) && 
/* 206 */       mc.field_71441_e.func_175623_d(blockPos.func_177982_a(0, 2, 0)) && 
/* 207 */       checkValidBlock(block) && 
/* 208 */       checkValidBlock(block2) && 
/* 209 */       checkValidBlock(block3) && 
/* 210 */       checkValidBlock(block4)) {
/* 211 */       return checkValidBlock(block5);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     return false;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 223 */     FEET, CONTINIOUS;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\AutoMine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */