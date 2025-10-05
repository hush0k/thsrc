/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventMoveDirection;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.events.PostPlayerUpdateEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.manager.EventManager;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IKeyBinding;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IRenderManager;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.EffectsRemover;
/*     */ import com.mrzak34.thunderhack.modules.player.AutoGApple;
/*     */ import com.mrzak34.thunderhack.modules.render.Search;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.setting.Parent;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.SilentRotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.math.ExplosionBuilder;
/*     */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*     */ import com.mrzak34.thunderhack.util.phobos.IEntityLivingBase;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.rotations.CastHelper;
/*     */ import com.mrzak34.thunderhack.util.rotations.RayTracingUtils;
/*     */ import com.mrzak34.thunderhack.util.rotations.ResolverUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Aura extends Module {
/*     */   public static EntityLivingBase target;
/*     */   public static BackTrack.Box bestBtBox;
/*     */   public static int CPSLimit;
/*  71 */   public final Setting<Parent> antiCheat = register(new Setting("AntiCheat", new Parent(false)));
/*  72 */   private final Setting<rotmod> rotation = register(new Setting("Rotation", rotmod.Matrix)).withParent(this.antiCheat);
/*  73 */   public final Setting<Float> rotateDistance = register(new Setting("RotateDst", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(5.0F))).withParent(this.antiCheat);
/*  74 */   public final Setting<Float> attackDistance = register(new Setting("AttackDst", Float.valueOf(3.1F), Float.valueOf(0.0F), Float.valueOf(7.0F))).withParent(this.antiCheat);
/*  75 */   public final Setting<RayTracingMode> rayTracing = register(new Setting("RayTracing", RayTracingMode.NewJitter)).withParent(this.antiCheat);
/*  76 */   public final Setting<PointsMode> pointsMode = register(new Setting("PointsSort", PointsMode.Distance)).withParent(this.antiCheat);
/*  77 */   public final Setting<TimingMode> timingMode = register(new Setting("Timing", TimingMode.Default)).withParent(this.antiCheat);
/*  78 */   public final Setting<Integer> minCPS = register(new Setting("MinCPS", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20), v -> (this.timingMode.getValue() == TimingMode.Old))).withParent(this.antiCheat);
/*  79 */   public final Setting<Integer> maxCPS = register(new Setting("MaxCPS", Integer.valueOf(12), Integer.valueOf(1), Integer.valueOf(20), v -> (this.timingMode.getValue() == TimingMode.Old))).withParent(this.antiCheat);
/*  80 */   public final Setting<Boolean> rtx = register(new Setting("RTX", Boolean.valueOf(true))).withParent(this.antiCheat);
/*  81 */   public final Setting<Float> walldistance = register(new Setting("WallDst", Float.valueOf(3.6F), Float.valueOf(0.0F), Float.valueOf(7.0F))).withParent(this.antiCheat);
/*  82 */   public final Setting<Integer> fov = register(new Setting("FOV", Integer.valueOf(180), Integer.valueOf(5), Integer.valueOf(180))).withParent(this.antiCheat);
/*  83 */   public final Setting<Float> hitboxScale = register(new Setting("RTXScale", Float.valueOf(2.8F), Float.valueOf(0.0F), Float.valueOf(3.0F))).withParent(this.antiCheat);
/*  84 */   public final Setting<Integer> yawStep = register(new Setting("YawStep", Integer.valueOf(80), Integer.valueOf(5), Integer.valueOf(180), v -> (this.rotation.getValue() == rotmod.Matrix))).withParent(this.antiCheat);
/*  85 */   public final Setting<Boolean> moveSync = register(new Setting("MoveSync", Boolean.valueOf(false))).withParent(this.antiCheat);
/*     */ 
/*     */   
/*  88 */   public final Setting<Parent> exploits = register(new Setting("Exploits", new Parent(false)));
/*  89 */   public final Setting<Boolean> resolver = register(new Setting("Resolver", Boolean.valueOf(false))).withParent(this.exploits);
/*  90 */   public final Setting<Boolean> shieldDesync = register(new Setting("Shield Desync", Boolean.valueOf(false))).withParent(this.exploits);
/*  91 */   public final Setting<Boolean> backTrack = register(new Setting("RotateToBackTrack", Boolean.valueOf(true))).withParent(this.exploits);
/*  92 */   public final Setting<Boolean> shiftTap = register(new Setting("ShiftTap", Boolean.valueOf(false))).withParent(this.exploits);
/*  93 */   public final Setting<Boolean> egravity = register(new Setting("ExtraGravity", Boolean.valueOf(false))).withParent(this.exploits);
/*     */   
/*  95 */   public final Setting<Parent> misc = register(new Setting("Misc", new Parent(false)));
/*  96 */   public final Setting<Boolean> shieldDesyncOnlyOnAura = register(new Setting("Wait Target", Boolean.valueOf(true), v -> ((Boolean)this.shieldDesync.getValue()).booleanValue())).withParent(this.misc);
/*  97 */   public final Setting<Boolean> criticals = register(new Setting("OnlyCrits", Boolean.valueOf(true))).withParent(this.misc);
/*  98 */   public final Setting<CritMode> critMode = register(new Setting("CritMode", CritMode.WexSide, v -> ((Boolean)this.criticals.getValue()).booleanValue())).withParent(this.misc);
/*  99 */   public final Setting<Float> critdist = register(new Setting("FallDistance", Float.valueOf(0.15F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (((Boolean)this.criticals.getValue()).booleanValue() && this.critMode.getValue() == CritMode.Simple))).withParent(this.misc);
/* 100 */   public final Setting<Boolean> criticals_autojump = register(new Setting("AutoJump", Boolean.valueOf(false), v -> ((Boolean)this.criticals.getValue()).booleanValue())).withParent(this.misc);
/* 101 */   public final Setting<Boolean> smartCrit = register(new Setting("SpaceOnly", Boolean.valueOf(true), v -> ((Boolean)this.criticals.getValue()).booleanValue())).withParent(this.misc);
/* 102 */   public final Setting<Boolean> watercrits = register(new Setting("WaterCrits", Boolean.valueOf(false), v -> ((Boolean)this.criticals.getValue()).booleanValue())).withParent(this.misc);
/* 103 */   public final Setting<Boolean> weaponOnly = register(new Setting("WeaponOnly", Boolean.valueOf(true))).withParent(this.misc);
/* 104 */   public final Setting<AutoSwitch> autoswitch = register(new Setting("AutoSwitch", AutoSwitch.None)).withParent(this.misc);
/* 105 */   public final Setting<Boolean> firstAxe = register(new Setting("FirstAxe", Boolean.valueOf(false), v -> (this.autoswitch.getValue() != AutoSwitch.None))).withParent(this.misc);
/* 106 */   public final Setting<Boolean> clientLook = register(new Setting("ClientLook", Boolean.valueOf(false))).withParent(this.misc);
/* 107 */   public final Setting<Boolean> snap = register(new Setting("Snap", Boolean.valueOf(false))).withParent(this.misc);
/* 108 */   public final Setting<Boolean> shieldBreaker = register(new Setting("ShieldBreaker", Boolean.valueOf(true))).withParent(this.misc);
/* 109 */   public final Setting<Boolean> offhand = register(new Setting("OffHandAttack", Boolean.valueOf(false))).withParent(this.misc);
/* 110 */   public final Setting<Boolean> teleport = register(new Setting("TP", Boolean.valueOf(false))).withParent(this.misc);
/* 111 */   public final Setting<Float> tpY = register(new Setting("TPY", Float.valueOf(3.0F), Float.valueOf(-5.0F), Float.valueOf(5.0F), v -> ((Boolean)this.teleport.getValue()).booleanValue())).withParent(this.misc);
/* 112 */   public final Setting<Boolean> Debug = register(new Setting("HitsDebug", Boolean.valueOf(false))).withParent(this.misc);
/*     */   
/* 114 */   public final Setting<Parent> targets = register(new Setting("Targets", new Parent(false)));
/* 115 */   public final Setting<Boolean> Playersss = register(new Setting("Players", Boolean.valueOf(true))).withParent(this.targets);
/* 116 */   public final Setting<Boolean> Mobsss = register(new Setting("Mobs", Boolean.valueOf(true))).withParent(this.targets);
/* 117 */   public final Setting<Boolean> Animalsss = register(new Setting("Animals", Boolean.valueOf(true))).withParent(this.targets);
/* 118 */   public final Setting<Boolean> Villagersss = register(new Setting("Villagers", Boolean.valueOf(true))).withParent(this.targets);
/* 119 */   public final Setting<Boolean> Slimesss = register(new Setting("Slimes", Boolean.valueOf(true))).withParent(this.targets);
/*     */   
/* 121 */   public final Setting<Boolean> Crystalsss = register(new Setting("Crystals", Boolean.valueOf(true))).withParent(this.targets);
/* 122 */   public final Setting<Boolean> ignoreNaked = register(new Setting("IgnoreNaked", Boolean.valueOf(false))).withParent(this.targets);
/* 123 */   public final Setting<Boolean> ignoreInvisible = register(new Setting("IgnoreInvis", Boolean.valueOf(false))).withParent(this.targets);
/* 124 */   public final Setting<Boolean> ignoreCreativ = register(new Setting("IgnoreCreativ", Boolean.valueOf(true))).withParent(this.targets);
/*     */   
/* 126 */   public final Setting<Parent> render = register(new Setting("Render", new Parent(false)));
/* 127 */   public final Setting<Boolean> RTXVisual = register(new Setting("RTXVisual", Boolean.valueOf(false))).withParent(this.render);
/* 128 */   public final Setting<Boolean> targetesp = register(new Setting("Target Esp", Boolean.valueOf(true))).withParent(this.render);
/* 129 */   public final Setting<Float> circleStep1 = register(new Setting("CircleSpeed", Float.valueOf(0.15F), Float.valueOf(0.1F), Float.valueOf(1.0F))).withParent(this.render);
/* 130 */   public final Setting<Float> circleHeight = register(new Setting("CircleHeight", Float.valueOf(0.15F), Float.valueOf(0.1F), Float.valueOf(1.0F))).withParent(this.render);
/* 131 */   public final Setting<Integer> colorOffset1 = register(new Setting("ColorOffset", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20))).withParent(this.render);
/* 132 */   public final Setting<ColorSetting> shitcollor = register(new Setting("TargetColor", new ColorSetting(-2009289807))).withParent(this.render);
/* 133 */   public final Setting<ColorSetting> shitcollor2 = register(new Setting("TargetColor2", new ColorSetting(-2009289807))).withParent(this.render);
/*     */   
/* 135 */   private final Timer oldTimer = new Timer();
/* 136 */   private final Timer hitttimer = new Timer(); private float prevCircleStep; private float circleStep; private float prevAdditionYaw; private boolean swappedToAxe; private boolean swapBack;
/*     */   private boolean rotatedBefore;
/*     */   private int tickshift;
/*     */   private Vec3d last_best_vec;
/*     */   private float rotation_smoother;
/*     */   public static float rotationPitch;
/*     */   public static float rotationYaw;
/*     */   float save_rotationYaw;
/*     */   
/*     */   public Aura() {
/* 146 */     super("Aura", "Запомните блядь-киллка тх не мисает-а дает шанс убежать", "attacks entities", Module.Category.COMBAT);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onCalc(PlayerUpdateEvent e) {
/* 151 */     if (((Boolean)this.targetesp.getValue()).booleanValue()) {
/* 152 */       this.prevCircleStep = this.circleStep;
/* 153 */       this.circleStep += ((Float)this.circleStep1.getValue()).floatValue();
/*     */     } 
/* 155 */     if (((Boolean)this.firstAxe.getValue()).booleanValue() && this.hitttimer.passedMs(3000L) && InventoryUtil.getBestAxe() != -1) {
/* 156 */       if (this.autoswitch.getValue() == AutoSwitch.Default) {
/* 157 */         mc.field_71439_g.field_71071_by.field_70461_c = InventoryUtil.getBestAxe();
/* 158 */         this.swappedToAxe = true;
/*     */       }
/*     */     
/* 161 */     } else if (this.autoswitch.getValue() == AutoSwitch.Default) {
/* 162 */       if (InventoryUtil.getBestSword() != -1) {
/* 163 */         mc.field_71439_g.field_71071_by.field_70461_c = InventoryUtil.getBestSword();
/* 164 */       } else if (InventoryUtil.getBestAxe() != -1) {
/* 165 */         mc.field_71439_g.field_71071_by.field_70461_c = InventoryUtil.getBestAxe();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (CPSLimit > 0) CPSLimit--;
/*     */     
/* 173 */     boolean shieldDesyncActive = ((Boolean)this.shieldDesync.getValue()).booleanValue();
/* 174 */     if (((Boolean)this.shieldDesyncOnlyOnAura.getValue()).booleanValue() && target == null) {
/* 175 */       shieldDesyncActive = false;
/*     */     }
/* 177 */     if (isActiveItemStackBlocking((EntityPlayer)mc.field_71439_g, 4 + (new Random()).nextInt(4)) && shieldDesyncActive && mc.field_71439_g.func_184587_cr()) {
/* 178 */       mc.field_71442_b.func_78766_c((EntityPlayer)mc.field_71439_g);
/*     */     }
/* 180 */     if (target != null) {
/* 181 */       if (target instanceof EntityOtherPlayerMP && ((Boolean)this.resolver.getValue()).booleanValue()) {
/* 182 */         ResolverUtil.resolve((EntityOtherPlayerMP)target);
/*     */       }
/* 184 */       if (!isEntityValid(target, false)) {
/* 185 */         target = null;
/* 186 */         ResolverUtil.reset();
/*     */       } 
/*     */     } 
/* 189 */     if (((Boolean)this.Crystalsss.getValue()).booleanValue()) {
/* 190 */       for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 191 */         if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && 
/* 192 */           getVector(entity) != null && needExplosion(entity.func_174791_d())) {
/* 193 */           if (this.oldTimer.passedMs(100L)) {
/* 194 */             attack(entity);
/* 195 */             this.oldTimer.reset();
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 203 */     if (target == null) {
/* 204 */       ResolverUtil.reset();
/* 205 */       target = findTarget();
/*     */     } 
/*     */     
/* 208 */     if (target == null || mc.field_71439_g.func_70068_e((Entity)target) > this.attackDistance.getPow2Value()) {
/* 209 */       BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass(BackTrack.class);
/* 210 */       if (bt.isOn() && ((Boolean)this.backTrack.getValue()).booleanValue()) {
/* 211 */         float best_distance = 100.0F;
/* 212 */         for (EntityPlayer BTtarget : mc.field_71441_e.field_73010_i) {
/* 213 */           if (mc.field_71439_g.func_70068_e((Entity)BTtarget) > 100.0D || 
/* 214 */             !isEntityValid((EntityLivingBase)BTtarget, true) || (
/* 215 */             (IEntity)BTtarget).getPosition_history().size() == 0)
/* 216 */             continue;  for (BackTrack.Box box : ((IEntity)BTtarget).getPosition_history()) {
/* 217 */             if (getDistanceBT(box) < best_distance) {
/* 218 */               best_distance = getDistanceBT(box);
/* 219 */               if (target != null && best_distance < mc.field_71439_g.func_70068_e((Entity)target)) {
/* 220 */                 target = (EntityLivingBase)BTtarget; continue;
/* 221 */               }  if (target == null && best_distance < this.attackDistance.getPow2Value()) {
/* 222 */                 target = (EntityLivingBase)BTtarget;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     if (target == null) {
/*     */       return;
/*     */     }
/*     */     
/* 234 */     if (((Boolean)this.weaponOnly.getValue()).booleanValue() && !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemSword) && !(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemAxe)) {
/*     */       return;
/*     */     }
/*     */     
/* 238 */     if (((Boolean)this.shiftTap.getValue()).booleanValue()) {
/* 239 */       if (!mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70143_R > 0.0F && mc.field_71439_g.field_70143_R < 0.7D) {
/* 240 */         ((IKeyBinding)mc.field_71474_y.field_74311_E).setPressed(true);
/*     */       } else {
/* 242 */         ((IKeyBinding)mc.field_71474_y.field_74311_E).setPressed(false);
/*     */       } 
/*     */     }
/* 245 */     this.rotatedBefore = false;
/* 246 */     attack((Entity)target);
/* 247 */     if (!this.rotatedBefore) {
/* 248 */       rotate((Entity)target, false);
/*     */     }
/* 250 */     if (target != null && ((Boolean)this.resolver.getValue()).booleanValue() && 
/* 251 */       target instanceof EntityOtherPlayerMP) {
/* 252 */       ResolverUtil.releaseResolver((EntityOtherPlayerMP)target);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRotate(EventSync e) {
/* 259 */     if (target != null) {
/* 260 */       if (((Boolean)this.egravity.getValue()).booleanValue() && !mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70143_R > 0.0F && ((Boolean)this.criticals.getValue()).booleanValue()) mc.field_71439_g.field_70181_x -= 0.03D; 
/* 261 */       if (this.rotation.getValue() != rotmod.None) {
/* 262 */         mc.field_71439_g.field_70177_z = rotationYaw;
/* 263 */         mc.field_71439_g.field_70125_A = rotationPitch;
/* 264 */         mc.field_71439_g.field_70759_as = rotationYaw;
/* 265 */         mc.field_71439_g.field_70761_aq = rotationYaw;
/*     */       } 
/*     */     } else {
/* 268 */       rotationYaw = mc.field_71439_g.field_70177_z;
/* 269 */       rotationPitch = mc.field_71439_g.field_70125_A;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 275 */     rotationYaw = mc.field_71439_g.field_70177_z;
/* 276 */     rotationPitch = mc.field_71439_g.field_70125_A;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRender3D(Render3DEvent e) {
/* 281 */     if (((Boolean)this.targetesp.getValue()).booleanValue()) {
/* 282 */       EntityLivingBase entity = target;
/* 283 */       if (entity != null) {
/* 284 */         double cs = (this.prevCircleStep + (this.circleStep - this.prevCircleStep) * mc.func_184121_ak());
/* 285 */         double prevSinAnim = absSinAnimation(cs - ((Float)this.circleHeight.getValue()).floatValue());
/* 286 */         double sinAnim = absSinAnimation(cs);
/* 287 */         double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosX();
/* 288 */         double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosY() + prevSinAnim * 1.399999976158142D;
/* 289 */         double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ();
/* 290 */         double nextY = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * mc.func_184121_ak() - ((IRenderManager)mc.func_175598_ae()).getRenderPosY() + sinAnim * 1.399999976158142D;
/*     */         
/* 292 */         GL11.glPushMatrix();
/*     */         
/* 294 */         boolean cullface = GL11.glIsEnabled(2884);
/* 295 */         boolean texture = GL11.glIsEnabled(3553);
/* 296 */         boolean blend = GL11.glIsEnabled(3042);
/* 297 */         boolean depth = GL11.glIsEnabled(2929);
/* 298 */         boolean alpha = GL11.glIsEnabled(3008);
/*     */ 
/*     */         
/* 301 */         GL11.glDisable(2884);
/* 302 */         GL11.glDisable(3553);
/* 303 */         GL11.glEnable(3042);
/* 304 */         GL11.glDisable(2929);
/* 305 */         GL11.glDisable(3008);
/*     */         
/* 307 */         GL11.glShadeModel(7425);
/*     */         
/* 309 */         GL11.glBegin(8); int i;
/* 310 */         for (i = 0; i <= 360; i++) {
/* 311 */           Color clr = getTargetColor(((ColorSetting)this.shitcollor.getValue()).getColorObject(), ((ColorSetting)this.shitcollor2.getValue()).getColorObject(), i);
/* 312 */           GL11.glColor4f(clr.getRed() / 255.0F, clr.getGreen() / 255.0F, clr.getBlue() / 255.0F, 0.6F);
/* 313 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * entity.field_70130_N * 0.8D, nextY, z + Math.sin(Math.toRadians(i)) * entity.field_70130_N * 0.8D);
/*     */           
/* 315 */           GL11.glColor4f(clr.getRed() / 255.0F, clr.getGreen() / 255.0F, clr.getBlue() / 255.0F, 0.01F);
/* 316 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * entity.field_70130_N * 0.8D, y, z + Math.sin(Math.toRadians(i)) * entity.field_70130_N * 0.8D);
/*     */         } 
/*     */         
/* 319 */         GL11.glEnd();
/* 320 */         GL11.glEnable(2848);
/* 321 */         GL11.glBegin(2);
/* 322 */         for (i = 0; i <= 360; i++) {
/* 323 */           Color clr = getTargetColor(((ColorSetting)this.shitcollor.getValue()).getColorObject(), ((ColorSetting)this.shitcollor2.getValue()).getColorObject(), i);
/* 324 */           GL11.glColor4f(clr.getRed() / 255.0F, clr.getGreen() / 255.0F, clr.getBlue() / 255.0F, 1.0F);
/* 325 */           GL11.glVertex3d(x + Math.cos(Math.toRadians(i)) * entity.field_70130_N * 0.8D, nextY, z + Math.sin(Math.toRadians(i)) * entity.field_70130_N * 0.8D);
/*     */         } 
/* 327 */         GL11.glEnd();
/*     */         
/* 329 */         if (!cullface) {
/* 330 */           GL11.glDisable(2848);
/*     */         }
/* 332 */         if (texture) {
/* 333 */           GL11.glEnable(3553);
/*     */         }
/*     */         
/* 336 */         if (depth) {
/* 337 */           GL11.glEnable(2929);
/*     */         }
/* 339 */         GL11.glShadeModel(7424);
/*     */         
/* 341 */         if (!blend)
/* 342 */           GL11.glDisable(3042); 
/* 343 */         if (cullface)
/* 344 */           GL11.glEnable(2884); 
/* 345 */         if (alpha)
/* 346 */           GL11.glEnable(3008); 
/* 347 */         GL11.glPopMatrix();
/* 348 */         GlStateManager.func_179117_G();
/*     */ 
/*     */         
/* 351 */         if (((Boolean)this.RTXVisual.getValue()).booleanValue()) {
/* 352 */           GlStateManager.func_179094_E();
/* 353 */           Vec3d eyes = (new Vec3d(0.0D, 0.0D, 1.0D)).func_178789_a(-((float)Math.toRadians(mc.field_71439_g.field_70125_A))).func_178785_b(-((float)Math.toRadians(mc.field_71439_g.field_70177_z)));
/* 354 */           if (this.rayTracing.getValue() == RayTracingMode.Beta) {
/* 355 */             Vec3d point = RayTracingUtils.getVecTarget((Entity)target, (((Float)this.attackDistance.getValue()).floatValue() + ((Float)this.rotateDistance.getValue()).floatValue()));
/* 356 */             if (point == null)
/* 357 */               return;  Search.renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, point.field_72450_a - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), point.field_72448_b - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), point.field_72449_c - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ(), (new Color(-369062144, true)).getRGB());
/*     */           } else {
/* 359 */             for (Vec3d point : RayTracingUtils.getHitBoxPoints(target.func_174791_d(), ((Float)this.hitboxScale.getValue()).floatValue() / 10.0F)) {
/* 360 */               if (!isPointVisible((Entity)target, point, (((Float)this.attackDistance.getValue()).floatValue() + ((Float)this.rotateDistance.getValue()).floatValue()))) {
/* 361 */                 Search.renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, point.field_72450_a - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), point.field_72448_b - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), point.field_72449_c - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ(), (new Color(-361861522, true)).getRGB()); continue;
/*     */               } 
/* 363 */               Search.renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, point.field_72450_a - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), point.field_72448_b - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), point.field_72449_c - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ(), (new Color(-939491283, true)).getRGB());
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 368 */           if (this.last_best_vec != null) {
/* 369 */             Search.renderTracer(eyes.field_72450_a, eyes.field_72448_b + mc.field_71439_g.func_70047_e(), eyes.field_72449_c, this.last_best_vec.field_72450_a - ((IRenderManager)mc.func_175598_ae()).getRenderPosX(), this.last_best_vec.field_72448_b - ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), this.last_best_vec.field_72449_c - ((IRenderManager)mc.func_175598_ae()).getRenderPosZ(), (new Color(-369033384, true)).getRGB());
/*     */           }
/* 371 */           GlStateManager.func_179121_F();
/* 372 */           GlStateManager.func_179117_G();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 380 */     target = null;
/* 381 */     if (EffectsRemover.jboost && mc.field_71439_g.func_70644_a(MobEffects.field_76420_g)) {
/* 382 */       mc.field_71439_g.func_70690_d(new PotionEffect(Objects.<Potion>requireNonNull(Potion.func_188412_a(8)), EffectsRemover.nig, 1));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isPointVisible(Entity target, Vec3d vector, double dst) {
/* 387 */     return (RayTracingUtils.getPointedEntity(getRotationForCoord(vector), dst, !ignoreWalls(target), target) == target);
/*     */   }
/*     */   
/*     */   public void attack(Entity base) {
/* 391 */     if ((base instanceof net.minecraft.entity.item.EntityEnderCrystal || canAttack()) && 
/* 392 */       getVector(base) != null) {
/* 393 */       rotate(base, true);
/*     */       
/* 395 */       if (RayTracingUtils.getMouseOver(base, rotationYaw, rotationPitch, ((Float)this.attackDistance.getValue()).floatValue(), ignoreWalls(base)) == base || (base instanceof net.minecraft.entity.item.EntityEnderCrystal && mc.field_71439_g
/* 396 */         .func_70068_e(base) <= 20.0D) || (((Boolean)this.backTrack
/* 397 */         .getValue()).booleanValue() && bestBtBox != null) || 
/* 398 */         !((Boolean)this.rtx.getValue()).booleanValue() || this.rotation
/* 399 */         .getValue() == rotmod.None) {
/*     */         
/* 401 */         if (((Boolean)this.teleport.getValue()).booleanValue()) {
/* 402 */           mc.field_71439_g.func_70107_b(base.field_70165_t, base.field_70163_u + ((Float)this.tpY.getValue()).floatValue(), base.field_70161_v);
/*     */         }
/* 404 */         boolean blocking = (mc.field_71439_g.func_184587_cr() && mc.field_71439_g.func_184607_cu().func_77973_b().func_77661_b(mc.field_71439_g.func_184607_cu()) == EnumAction.BLOCK);
/* 405 */         if (blocking) {
/* 406 */           mc.field_71442_b.func_78766_c((EntityPlayer)mc.field_71439_g);
/*     */         }
/* 408 */         boolean needSwap = false;
/*     */         
/* 410 */         if (EventManager.serversprint) {
/* 411 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/* 412 */           needSwap = true;
/*     */         } 
/* 414 */         if (((Boolean)this.snap.getValue()).booleanValue()) {
/* 415 */           mc.field_71439_g.field_70125_A = rotationPitch;
/* 416 */           mc.field_71439_g.field_70177_z = rotationYaw;
/*     */         } 
/*     */         
/* 419 */         if (((Boolean)this.Debug.getValue()).booleanValue() && 
/* 420 */           target != null && this.last_best_vec != null) {
/* 421 */           Command.sendMessage("Attacked with delay: " + this.hitttimer
/* 422 */               .getPassedTimeMs() + " | Distance to target: " + mc.field_71439_g
/* 423 */               .func_70032_d((Entity)target) + " | Distance to best point: " + mc.field_71439_g
/* 424 */               .func_70011_f(this.last_best_vec.field_72450_a, this.last_best_vec.field_72448_b, this.last_best_vec.field_72449_c) + " | CAS: " + 
/* 425 */               getCooledAttackStrength() + " | Possible damage " + 
/* 426 */               getDamage((Entity)target));
/*     */         }
/*     */ 
/*     */         
/* 430 */         mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, base);
/* 431 */         this.hitttimer.reset();
/* 432 */         mc.field_71439_g.func_184609_a(((Boolean)this.offhand.getValue()).booleanValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
/*     */         
/* 434 */         if (InventoryUtil.getBestAxe() >= 0 && ((Boolean)this.shieldBreaker.getValue()).booleanValue() && base instanceof EntityPlayer && isActiveItemStackBlocking((EntityPlayer)base, 1)) {
/* 435 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(InventoryUtil.getBestAxe()));
/* 436 */           mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, base);
/* 437 */           mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 438 */           mc.field_71439_g.func_184821_cY();
/* 439 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */         } 
/* 441 */         if (blocking) {
/* 442 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(mc.field_71439_g.func_184600_cs()));
/*     */         }
/* 444 */         if (needSwap) {
/* 445 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */         }
/* 447 */         if (this.swappedToAxe) {
/* 448 */           this.swapBack = true;
/* 449 */           this.swappedToAxe = false;
/*     */         } 
/* 451 */         CPSLimit = 10;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostAttack(EventPostSync e) {
/* 460 */     if (((Boolean)this.firstAxe.getValue()).booleanValue() && InventoryUtil.getBestSword() != -1 && this.swapBack && 
/* 461 */       this.autoswitch.getValue() == AutoSwitch.Default) {
/* 462 */       mc.field_71439_g.field_71071_by.field_70461_c = InventoryUtil.getBestSword();
/* 463 */       this.swapBack = false;
/*     */     } 
/*     */     
/* 466 */     if (((Boolean)this.clientLook.getValue()).booleanValue()) {
/* 467 */       mc.field_71439_g.field_70177_z = rotationYaw;
/* 468 */       mc.field_71439_g.field_70125_A = rotationPitch;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getDistanceBT(BackTrack.Box box) {
/* 473 */     float f = (float)(mc.field_71439_g.field_70165_t - (box.getPosition()).field_72450_a);
/* 474 */     float f1 = (float)((mc.field_71439_g.func_174824_e(1.0F)).field_72448_b - (box.getPosition()).field_72448_b);
/* 475 */     float f2 = (float)(mc.field_71439_g.field_70161_v - (box.getPosition()).field_72449_c);
/* 476 */     return f * f + f1 * f1 + f2 * f2;
/*     */   }
/*     */   
/*     */   public float getDistanceBTPoint(Vec3d point) {
/* 480 */     float f = (float)(mc.field_71439_g.field_70165_t - point.field_72450_a);
/* 481 */     float f1 = (float)((mc.field_71439_g.func_174824_e(1.0F)).field_72448_b - point.field_72448_b);
/* 482 */     float f2 = (float)(mc.field_71439_g.field_70161_v - point.field_72449_c);
/* 483 */     return f * f + f1 * f1 + f2 * f2;
/*     */   }
/*     */   
/*     */   public boolean isNakedPlayer(EntityLivingBase base) {
/* 487 */     if (!(base instanceof EntityOtherPlayerMP)) {
/* 488 */       return false;
/*     */     }
/* 490 */     return (base.func_70658_aO() == 0);
/*     */   }
/*     */   
/*     */   public boolean isInvisible(EntityLivingBase base) {
/* 494 */     if (!(base instanceof EntityOtherPlayerMP)) {
/* 495 */       return false;
/*     */     }
/* 497 */     return base.func_82150_aj();
/*     */   }
/*     */   
/*     */   public boolean needExplosion(Vec3d position) {
/* 501 */     ExplosionBuilder builder = new ExplosionBuilder((World)mc.field_71441_e, null, position.field_72450_a, position.field_72448_b, position.field_72449_c, 6.0F);
/* 502 */     boolean needExplosion = false;
/* 503 */     for (Map.Entry<EntityPlayer, Float> entry : (Iterable<Map.Entry<EntityPlayer, Float>>)builder.damageMap.entrySet()) {
/* 504 */       if (Thunderhack.friendManager.isFriend(((EntityPlayer)entry.getKey()).func_70005_c_()) && ((Float)entry.getValue()).floatValue() > ((EntityPlayer)entry.getKey()).func_110143_aJ()) {
/* 505 */         return false;
/*     */       }
/* 507 */       if (entry.getKey() == mc.field_71439_g && ((Float)entry.getValue()).floatValue() > 25.0F) {
/* 508 */         return false;
/*     */       }
/* 510 */       if (((Float)entry.getValue()).floatValue() > 35.0F) {
/* 511 */         needExplosion = true;
/*     */       }
/*     */     } 
/* 514 */     return needExplosion;
/*     */   }
/*     */   
/*     */   public boolean canAttack() {
/* 518 */     boolean reasonForCancelCritical = (mc.field_71439_g.func_70617_f_() || isInLiquid() || ((IEntity)mc.field_71439_g).isInWeb() || (((Boolean)this.smartCrit.getValue()).booleanValue() && !((Boolean)this.criticals_autojump.getValue()).booleanValue() && !mc.field_71474_y.field_74314_A.func_151470_d()));
/*     */     
/* 520 */     if (this.timingMode.getValue() == TimingMode.Default) {
/* 521 */       if (CPSLimit > 0) return false; 
/* 522 */       if (mc.field_71439_g.func_184825_o(1.5F) <= 0.93D) return false;
/*     */     
/* 524 */     } else if (!this.oldTimer.passedMs((long)((1000.0F + MathUtil.random(1.0F, 50.0F) - MathUtil.random(1.0F, 60.0F) + MathUtil.random(1.0F, 70.0F)) / (int)MathUtil.random(((Integer)this.minCPS.getValue()).intValue(), ((Integer)this.maxCPS.getValue()).intValue())))) {
/* 525 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 529 */     if (this.last_best_vec != null && 
/* 530 */       RayTracingUtils.getDistanceFromHead(new Vec3d(this.last_best_vec.field_72450_a, this.last_best_vec.field_72448_b, this.last_best_vec.field_72449_c)) > this.attackDistance.getPow2Value()) {
/* 531 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 535 */     if (((Boolean)this.criticals.getValue()).booleanValue() && ((Boolean)this.watercrits.getValue()).booleanValue() && mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof net.minecraft.block.BlockLiquid && mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.0D, mc.field_71439_g.field_70161_v)).func_177230_c() instanceof net.minecraft.block.BlockAir) {
/* 536 */       return (mc.field_71439_g.field_70143_R > 0.0F);
/*     */     }
/*     */     
/* 539 */     if (((Boolean)this.criticals.getValue()).booleanValue() && !reasonForCancelCritical) {
/* 540 */       if (this.critMode.getValue() == CritMode.WexSide) {
/* 541 */         EntityPlayerSP client = mc.field_71439_g;
/* 542 */         int r = (int)mc.field_71439_g.field_70163_u;
/* 543 */         int c = (int)Math.ceil(mc.field_71439_g.field_70163_u);
/* 544 */         if (r != c && mc.field_71439_g.field_70122_E && isBlockAboveHead()) {
/* 545 */           return true;
/*     */         }
/* 547 */         return (!client.field_70122_E && client.field_70143_R > 0.0F);
/* 548 */       }  if (this.critMode.getValue() == CritMode.Simple) {
/* 549 */         return ((isBlockAboveHead() ? (mc.field_71439_g.field_70143_R > 0.0F) : (mc.field_71439_g.field_70143_R >= ((Float)this.critdist.getValue()).floatValue())) && !mc.field_71439_g.field_70122_E);
/*     */       }
/*     */     } 
/* 552 */     this.oldTimer.reset();
/* 553 */     return true;
/*     */   }
/*     */   
/*     */   private float getCooledAttackStrength() {
/* 557 */     return MathHelper.func_76131_a((((IEntityLivingBase)mc.field_71439_g).getTicksSinceLastSwing() + 1.5F) / getCooldownPeriod(), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float getCooldownPeriod() {
/* 561 */     return (float)(1.0D / mc.field_71439_g.func_110148_a(SharedMonsterAttributes.field_188790_f).func_111126_e() * (20.0F * Thunderhack.TICK_TIMER));
/*     */   }
/*     */   
/*     */   public EntityLivingBase findTarget() {
/* 565 */     List<EntityLivingBase> targets = new ArrayList<>();
/* 566 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 567 */       if (entity instanceof EntityLivingBase && isEntityValid((EntityLivingBase)entity, false)) {
/* 568 */         targets.add((EntityLivingBase)entity);
/*     */       }
/*     */     } 
/* 571 */     targets.sort((e1, e2) -> {
/*     */           int dst1 = (int)(mc.field_71439_g.func_70032_d((Entity)e1) * 1000.0F);
/*     */           int dst2 = (int)(mc.field_71439_g.func_70032_d((Entity)e2) * 1000.0F);
/*     */           return dst1 - dst2;
/*     */         });
/* 576 */     return targets.isEmpty() ? null : targets.get(0);
/*     */   }
/*     */   
/*     */   public boolean isEntityValid(EntityLivingBase entity, boolean backtrack) {
/* 580 */     if (((Boolean)this.ignoreNaked.getValue()).booleanValue() && 
/* 581 */       isNakedPlayer(entity)) {
/* 582 */       return false;
/*     */     }
/* 584 */     if (((Boolean)this.ignoreInvisible.getValue()).booleanValue() && 
/* 585 */       isInvisible(entity)) {
/* 586 */       return false;
/*     */     }
/* 588 */     if (((Boolean)this.ignoreCreativ.getValue()).booleanValue() && 
/* 589 */       entity instanceof EntityPlayer && (
/* 590 */       (EntityPlayer)entity).func_184812_l_()) {
/* 591 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 595 */     if (entity.func_110143_aJ() <= 0.0F) {
/* 596 */       return false;
/*     */     }
/* 598 */     if (AntiBot.bots.contains(entity)) {
/* 599 */       return false;
/*     */     }
/* 601 */     if (!targetsCheck(entity)) {
/* 602 */       return false;
/*     */     }
/* 604 */     if (backtrack) {
/* 605 */       return true;
/*     */     }
/*     */     
/* 608 */     if (!ignoreWalls((Entity)entity)) {
/* 609 */       return (getVector((Entity)entity) != null);
/*     */     }
/* 611 */     return (mc.field_71439_g.func_70068_e((Entity)entity) <= Math.pow((((Float)this.attackDistance.getValue()).floatValue() + ((Float)this.rotateDistance.getValue()).floatValue()), 2.0D));
/*     */   }
/*     */   
/*     */   public Vec3d getVector(Entity target) {
/* 615 */     BackTrack bt = (BackTrack)Thunderhack.moduleManager.getModuleByClass(BackTrack.class);
/* 616 */     if (!((Boolean)this.backTrack.getValue()).booleanValue() || mc.field_71439_g
/* 617 */       .func_70068_e(target) <= this.attackDistance.getPow2Value() || bt
/* 618 */       .isOff() || !(target instanceof EntityPlayer) || (((Boolean)this.backTrack
/*     */       
/* 620 */       .getValue()).booleanValue() && ((IEntity)target).getPosition_history().size() == 0)) {
/*     */       
/* 622 */       if (this.rayTracing.getValue() == RayTracingMode.Beta) {
/* 623 */         return RayTracingUtils.getVecTarget(target, (((Float)this.attackDistance.getValue()).floatValue() + ((Float)this.rotateDistance.getValue()).floatValue()));
/*     */       }
/* 625 */       ArrayList<Vec3d> points = RayTracingUtils.getHitBoxPoints(target.func_174791_d(), ((Float)this.hitboxScale.getValue()).floatValue() / 10.0F);
/*     */       
/* 627 */       points.removeIf(point -> !isPointVisible(target, point, (((Float)this.attackDistance.getValue()).floatValue() + ((Float)this.rotateDistance.getValue()).floatValue())));
/*     */       
/* 629 */       if (points.isEmpty()) {
/* 630 */         return null;
/*     */       }
/*     */       
/* 633 */       float f1 = 100.0F;
/* 634 */       Vec3d best_point = null;
/* 635 */       float best_angle = 180.0F;
/*     */       
/* 637 */       if (this.pointsMode.getValue() == PointsMode.Angle) {
/* 638 */         for (Vec3d point : points) {
/* 639 */           Vec2f r = getDeltaForCoord(new Vec2f(rotationYaw, rotationPitch), point);
/* 640 */           float y = Math.abs(r.field_189983_j);
/* 641 */           if (y < best_angle) {
/* 642 */             best_angle = y;
/* 643 */             best_point = point;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 647 */         for (Vec3d point : points) {
/* 648 */           if (RayTracingUtils.getDistanceFromHead(point) < f1) {
/* 649 */             best_point = point;
/* 650 */             f1 = RayTracingUtils.getDistanceFromHead(point);
/*     */           } 
/*     */         } 
/*     */       } 
/* 654 */       this.last_best_vec = best_point;
/* 655 */       return best_point;
/*     */     } 
/* 657 */     bestBtBox = null;
/* 658 */     float best_distance = 36.0F;
/* 659 */     BackTrack.Box best_box = null;
/* 660 */     for (BackTrack.Box boxes : ((IEntity)target).getPosition_history()) {
/* 661 */       if (getDistanceBT(boxes) < best_distance) {
/* 662 */         best_box = boxes;
/* 663 */         best_distance = getDistanceBT(boxes);
/*     */       } 
/*     */     } 
/*     */     
/* 667 */     if (best_box != null) {
/* 668 */       bestBtBox = best_box;
/* 669 */       ArrayList<Vec3d> points = RayTracingUtils.getHitBoxPoints(best_box.getPosition(), ((Float)this.hitboxScale.getValue()).floatValue() / 10.0F);
/* 670 */       points.removeIf(point -> (getDistanceBTPoint(point) > Math.pow((((Float)this.attackDistance.getValue()).floatValue() + ((Float)this.rotateDistance.getValue()).floatValue()), 2.0D)));
/*     */       
/* 672 */       if (points.isEmpty()) {
/* 673 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 677 */       float best_distance2 = 100.0F;
/* 678 */       Vec3d best_point = null;
/* 679 */       float best_angle = 180.0F;
/*     */       
/* 681 */       if (this.pointsMode.getValue() == PointsMode.Angle) {
/* 682 */         for (Vec3d point : points) {
/* 683 */           Vec2f r = getDeltaForCoord(new Vec2f(rotationYaw, rotationPitch), point);
/* 684 */           float y = Math.abs(r.field_189983_j);
/* 685 */           if (y < best_angle) {
/* 686 */             best_angle = y;
/* 687 */             best_point = point;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 692 */         for (Vec3d point : points) {
/* 693 */           if (RayTracingUtils.getDistanceFromHead(point) < best_distance2) {
/* 694 */             best_point = point;
/* 695 */             best_distance2 = RayTracingUtils.getDistanceFromHead(point);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 700 */       this.last_best_vec = best_point;
/* 701 */       return best_point;
/*     */     } 
/*     */     
/* 704 */     return null;
/*     */   }
/*     */   
/*     */   public boolean targetsCheck(EntityLivingBase entity) {
/* 708 */     CastHelper castHelper = new CastHelper();
/* 709 */     if (((Boolean)this.Playersss.getValue()).booleanValue()) {
/* 710 */       castHelper.apply(CastHelper.EntityType.PLAYERS);
/*     */     }
/* 712 */     if (((Boolean)this.Mobsss.getValue()).booleanValue()) {
/* 713 */       castHelper.apply(CastHelper.EntityType.MOBS);
/*     */     }
/* 715 */     if (((Boolean)this.Animalsss.getValue()).booleanValue()) {
/* 716 */       castHelper.apply(CastHelper.EntityType.ANIMALS);
/*     */     }
/* 718 */     if (((Boolean)this.Villagersss.getValue()).booleanValue()) {
/* 719 */       castHelper.apply(CastHelper.EntityType.VILLAGERS);
/*     */     }
/* 721 */     if (entity instanceof net.minecraft.entity.monster.EntitySlime) {
/* 722 */       return ((Boolean)this.Slimesss.getValue()).booleanValue();
/*     */     }
/* 724 */     return (CastHelper.isInstanceof((Entity)entity, castHelper.build()) != null && !entity.field_70128_L);
/*     */   }
/*     */   
/*     */   public boolean ignoreWalls(Entity input) {
/* 728 */     if (input instanceof net.minecraft.entity.item.EntityEnderCrystal) return true; 
/* 729 */     if (mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_185904_a() != Material.field_151579_a)
/* 730 */       return true; 
/* 731 */     return (mc.field_71439_g.func_70068_e(input) <= this.walldistance.getPow2Value());
/*     */   }
/*     */   
/*     */   public void rotate(Entity base, boolean attackContext) {
/* 735 */     this.rotatedBefore = true;
/*     */     
/* 737 */     Vec3d bestVector = getVector(base);
/* 738 */     if (bestVector == null) {
/* 739 */       bestVector = base.func_174824_e(1.0F);
/*     */     }
/*     */     
/* 742 */     boolean inside_target = mc.field_71439_g.func_174813_aQ().func_72326_a(base.func_174813_aQ());
/*     */ 
/*     */     
/* 745 */     if (this.rotation.getValue() == rotmod.Matrix3 && inside_target) {
/* 746 */       bestVector = base.func_174791_d().func_178787_e(new Vec3d(0.0D, interpolateRandom(0.7F, 0.9F), 0.0D));
/*     */     }
/*     */ 
/*     */     
/* 750 */     double x = bestVector.field_72450_a - mc.field_71439_g.field_70165_t;
/* 751 */     double y = bestVector.field_72448_b - (mc.field_71439_g.func_174824_e(1.0F)).field_72448_b;
/* 752 */     double z = bestVector.field_72449_c - mc.field_71439_g.field_70161_v;
/* 753 */     double dst = Math.sqrt(Math.pow(x, 2.0D) + Math.pow(z, 2.0D));
/*     */     
/* 755 */     float yawToTarget = (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(z, x)) - 90.0D);
/* 756 */     float pitchToTarget = (float)-Math.toDegrees(Math.atan2(y, dst));
/*     */     
/* 758 */     float sensitivity = 1.0001F;
/*     */     
/* 760 */     float yawDelta = MathHelper.func_76142_g(yawToTarget - rotationYaw) / sensitivity;
/* 761 */     float pitchDelta = (pitchToTarget - rotationPitch) / sensitivity;
/*     */ 
/*     */     
/* 764 */     if (yawDelta > 180.0F) {
/* 765 */       yawDelta -= 180.0F;
/*     */     }
/*     */     
/* 768 */     int yawDeltaAbs = (int)Math.abs(yawDelta);
/*     */ 
/*     */     
/* 771 */     if (yawDeltaAbs < ((Integer)this.fov.getValue()).intValue()) {
/* 772 */       float pitchDeltaAbs; boolean sanik; float absoluteYaw; float[] ncp; float additionYaw; float f1; float randomize; float additionPitch; float f2; float randomizeClamp; float newYaw; float f3; boolean looking_at_box; float newPitch; float deltaYaw; float yaw_speed; float deltaPitch; float pitch_speed; float f5; float f4; float f7; float f6; float gcdFix1; float f8; double gcdFix2; float f9; float f10; double gcdFix; double d1; double d2; switch ((rotmod)this.rotation.getValue()) {
/*     */         case Matrix:
/* 774 */           pitchDeltaAbs = Math.abs(pitchDelta);
/*     */           
/* 776 */           additionYaw = Math.min(Math.max(yawDeltaAbs, 1), ((Integer)this.yawStep.getValue()).intValue());
/* 777 */           additionPitch = Math.max(attackContext ? pitchDeltaAbs : 1.0F, 2.0F);
/*     */           
/* 779 */           if (Math.abs(additionYaw - this.prevAdditionYaw) <= 3.0F) {
/* 780 */             additionYaw = this.prevAdditionYaw + 3.1F;
/*     */           }
/*     */           
/* 783 */           newYaw = rotationYaw + ((yawDelta > 0.0F) ? additionYaw : -additionYaw) * sensitivity;
/* 784 */           newPitch = MathHelper.func_76131_a(rotationPitch + ((pitchDelta > 0.0F) ? additionPitch : -additionPitch) * sensitivity, -90.0F, 90.0F);
/*     */           
/* 786 */           rotationYaw = newYaw;
/* 787 */           rotationPitch = newPitch;
/* 788 */           this.prevAdditionYaw = additionYaw;
/*     */           break;
/*     */         
/*     */         case SunRise:
/*     */         case Matrix2:
/* 793 */           sanik = (this.rotation.getValue() == rotmod.SunRise);
/* 794 */           f1 = MathHelper.func_76135_e(yawDelta);
/*     */           
/* 796 */           f2 = interpolateRandom(-3.0F, 3.0F);
/* 797 */           f3 = interpolateRandom(-5.0F, 5.0F);
/*     */           
/* 799 */           deltaYaw = MathHelper.func_76131_a(f1 + f2, -60.0F + f3, 60.0F + f3);
/* 800 */           deltaPitch = MathHelper.func_76131_a(pitchDelta + f2, -(sanik ? 13 : 45), sanik ? 13.0F : 45.0F);
/*     */           
/* 802 */           f5 = rotationYaw + ((yawDelta > 0.0F) ? deltaYaw : -deltaYaw);
/* 803 */           f7 = MathHelper.func_76131_a(rotationPitch + deltaPitch / (sanik ? 4.0F : 2.0F), -90.0F, 90.0F);
/*     */           
/* 805 */           gcdFix1 = mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
/* 806 */           gcdFix2 = Math.pow(gcdFix1, 3.0D) * 8.0D;
/* 807 */           gcdFix = gcdFix2 * 0.15000000596046448D;
/*     */           
/* 809 */           rotationYaw = (float)(f5 - (f5 - rotationYaw) % gcdFix);
/* 810 */           rotationPitch = (float)(f7 - (f7 - rotationPitch) % gcdFix);
/*     */           break;
/*     */         
/*     */         case Matrix3:
/* 814 */           absoluteYaw = MathHelper.func_76135_e(yawDelta);
/*     */           
/* 816 */           randomize = interpolateRandom(-2.0F, 2.0F);
/* 817 */           randomizeClamp = interpolateRandom(-5.0F, 5.0F);
/*     */           
/* 819 */           looking_at_box = (RayTracingUtils.getMouseOver(base, rotationYaw, rotationPitch, (((Float)this.attackDistance.getValue()).floatValue() + ((Float)this.rotateDistance.getValue()).floatValue()), ignoreWalls(base)) == base);
/*     */           
/* 821 */           if (looking_at_box) {
/* 822 */             this.rotation_smoother = 15.0F;
/* 823 */           } else if (this.rotation_smoother < 60.0F) {
/* 824 */             this.rotation_smoother += 9.0F;
/*     */           } 
/*     */           
/* 827 */           yaw_speed = (inside_target && attackContext) ? 60.0F : this.rotation_smoother;
/* 828 */           pitch_speed = looking_at_box ? 0.5F : (this.rotation_smoother / 2.0F);
/*     */           
/* 830 */           f4 = MathHelper.func_76131_a(absoluteYaw + randomize, -yaw_speed + randomizeClamp, yaw_speed + randomizeClamp);
/* 831 */           f6 = MathHelper.func_76131_a(pitchDelta, -pitch_speed, pitch_speed);
/*     */           
/* 833 */           f8 = rotationYaw + ((yawDelta > 0.0F) ? f4 : -f4);
/* 834 */           f9 = MathHelper.func_76131_a(rotationPitch + f6, -90.0F, 90.0F);
/*     */           
/* 836 */           f10 = mc.field_71474_y.field_74341_c * 0.6F + 0.2F;
/* 837 */           d1 = Math.pow(f10, 3.0D) * 8.0D;
/* 838 */           d2 = d1 * 0.15000000596046448D;
/*     */           
/* 840 */           rotationYaw = (float)(f8 - (f8 - rotationYaw) % d2);
/* 841 */           rotationPitch = (float)(f9 - (f9 - rotationPitch) % d2);
/*     */           break;
/*     */         
/*     */         case FunnyGame:
/* 845 */           ncp = SilentRotationUtil.calcAngle(getVector(base));
/* 846 */           if (ncp != null && !AutoGApple.stopAura) {
/* 847 */             rotationYaw = ncp[0];
/* 848 */             rotationPitch = ncp[1];
/*     */           } 
/*     */           break;
/*     */         
/*     */         case AAC:
/* 853 */           if (attackContext) {
/* 854 */             int i = (int)Math.abs(pitchDelta);
/* 855 */             float f11 = rotationYaw + ((yawDelta > 0.0F) ? yawDeltaAbs : -yawDeltaAbs) * sensitivity;
/* 856 */             float f12 = MathHelper.func_76131_a(rotationPitch + ((pitchDelta > 0.0F) ? i : -i) * sensitivity, -90.0F, 90.0F);
/* 857 */             rotationYaw = f11;
/* 858 */             rotationPitch = f12;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPostPlayerUpdate(PostPlayerUpdateEvent event) {
/* 868 */     if (((Boolean)this.criticals_autojump.getValue()).booleanValue() && 
/* 869 */       mc.field_71439_g.field_70122_E && !isInLiquid() && !mc.field_71439_g.func_70617_f_() && !((IEntity)mc.field_71439_g).isInWeb() && !mc.field_71439_g.func_70644_a(MobEffects.field_76421_d) && target != null && ((Boolean)this.criticals_autojump.getValue()).booleanValue()) {
/* 870 */       mc.field_71439_g.func_70664_aZ();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMoveDirection(EventMoveDirection e) {
/* 880 */     if (((Boolean)this.moveSync.getValue()).booleanValue()) {
/* 881 */       if (!e.isPost()) {
/* 882 */         this.save_rotationYaw = mc.field_71439_g.field_70177_z;
/* 883 */         mc.field_71439_g.field_70177_z = rotationYaw;
/*     */       } else {
/* 885 */         mc.field_71439_g.field_70177_z = this.save_rotationYaw;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isInLiquid() {
/* 892 */     return (mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab());
/*     */   }
/*     */   
/*     */   public static double absSinAnimation(double input) {
/* 896 */     return Math.abs(1.0D + Math.sin(input)) / 2.0D;
/*     */   }
/*     */   
/*     */   public static boolean isBlockAboveHead() {
/* 900 */     AxisAlignedBB axisAlignedBB = new AxisAlignedBB(mc.field_71439_g.field_70165_t - 0.3D, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v + 0.3D, mc.field_71439_g.field_70165_t + 0.3D, mc.field_71439_g.field_70163_u + (!mc.field_71439_g.field_70122_E ? 1.5D : 2.5D), mc.field_71439_g.field_70161_v - 0.3D);
/*     */ 
/*     */     
/* 903 */     return !mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, axisAlignedBB).isEmpty();
/*     */   }
/*     */   
/*     */   public static Vec2f getDeltaForCoord(Vec2f rot, Vec3d point) {
/* 907 */     EntityPlayerSP client = (Minecraft.func_71410_x()).field_71439_g;
/* 908 */     double x = point.field_72450_a - client.field_70165_t;
/* 909 */     double y = point.field_72448_b - (client.func_174824_e(1.0F)).field_72448_b;
/* 910 */     double z = point.field_72449_c - client.field_70161_v;
/* 911 */     double dst = Math.sqrt(Math.pow(x, 2.0D) + Math.pow(z, 2.0D));
/* 912 */     float yawToTarget = (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(z, x)) - 90.0D);
/* 913 */     float pitchToTarget = (float)-Math.toDegrees(Math.atan2(y, dst));
/* 914 */     float yawDelta = MathHelper.func_76142_g(yawToTarget - rot.field_189982_i);
/* 915 */     float pitchDelta = pitchToTarget - rot.field_189983_j;
/* 916 */     return new Vec2f(yawDelta, pitchDelta);
/*     */   }
/*     */   
/*     */   public static Vec2f getRotationForCoord(Vec3d point) {
/* 920 */     double x = point.field_72450_a - mc.field_71439_g.field_70165_t;
/* 921 */     double y = point.field_72448_b - (mc.field_71439_g.func_174824_e(1.0F)).field_72448_b;
/* 922 */     double z = point.field_72449_c - mc.field_71439_g.field_70161_v;
/* 923 */     double dst = Math.sqrt(Math.pow(x, 2.0D) + Math.pow(z, 2.0D));
/* 924 */     float yawToTarget = (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(z, x)) - 90.0D);
/* 925 */     float pitchToTarget = (float)-Math.toDegrees(Math.atan2(y, dst));
/* 926 */     return new Vec2f(yawToTarget, pitchToTarget);
/*     */   }
/*     */   
/*     */   public static boolean isActiveItemStackBlocking(EntityPlayer other, int time) {
/* 930 */     if (other.func_184587_cr() && !other.func_184607_cu().func_190926_b()) {
/* 931 */       Item item = other.func_184607_cu().func_77973_b();
/* 932 */       if (item.func_77661_b(other.func_184607_cu()) != EnumAction.BLOCK) {
/* 933 */         return false;
/*     */       }
/* 935 */       return (item.func_77626_a(other.func_184607_cu()) - ((IEntityLivingBase)other).getActiveItemStackUseCount() >= time);
/*     */     } 
/*     */     
/* 938 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float interpolateRandom(float var0, float var1) {
/* 943 */     return (float)(var0 + (var1 - var0) * Math.random());
/*     */   }
/*     */   
/*     */   private Color getTargetColor(Color color1, Color color2, int offset) {
/* 947 */     return RenderUtil.TwoColoreffect(color1, color2, Math.abs(System.currentTimeMillis() / 10L) / 100.0D + (offset * (20.0F - ((Integer)this.colorOffset1.getValue()).intValue()) / 200.0F));
/*     */   }
/*     */   
/*     */   public enum rotmod
/*     */   {
/* 952 */     Matrix, AAC, FunnyGame, Matrix2, SunRise, Matrix3, None;
/*     */   }
/*     */   
/*     */   public enum CritMode
/*     */   {
/* 957 */     WexSide, Simple;
/*     */   }
/*     */   
/*     */   public enum AutoSwitch
/*     */   {
/* 962 */     None, Default;
/*     */   }
/*     */   
/*     */   public enum PointsMode {
/* 966 */     Distance, Angle;
/*     */   }
/*     */   
/*     */   public enum TimingMode
/*     */   {
/* 971 */     Default, Old;
/*     */   }
/*     */   
/*     */   public enum RayTracingMode
/*     */   {
/* 976 */     NewJitter, New, Old, OldJitter, Beta;
/*     */   }
/*     */   
/*     */   public enum Hitbox {
/* 980 */     HEAD, CHEST, LEGS;
/*     */   }
/*     */   
/*     */   public float getDamage(Entity targetEntity) {
/* 984 */     float f = (float)mc.field_71439_g.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
/* 985 */     float f1 = EnchantmentHelper.func_152377_a(mc.field_71439_g.func_184614_ca(), ((EntityLivingBase)targetEntity).func_70668_bt());
/* 986 */     float f2 = mc.field_71439_g.func_184825_o(0.5F);
/* 987 */     f *= 0.2F + f2 * f2 * 0.8F;
/* 988 */     f1 *= f2;
/* 989 */     if (f > 0.0F || f1 > 0.0F) {
/* 990 */       boolean flag2 = (f2 > 0.9F && mc.field_71439_g.field_70143_R > 0.0F && !mc.field_71439_g.field_70122_E && !mc.field_71439_g.func_70617_f_() && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_70644_a(MobEffects.field_76440_q));
/* 991 */       flag2 = (flag2 && !mc.field_71439_g.func_70051_ag());
/* 992 */       if (flag2)
/*     */       {
/* 994 */         f *= 1.5F;
/*     */       }
/* 996 */       f += f1;
/*     */     } 
/* 998 */     return f;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\Aura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */