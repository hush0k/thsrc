/*      */ package com.mrzak34.thunderhack.util.phobos;
/*      */ import com.mrzak34.thunderhack.Thunderhack;
/*      */ import com.mrzak34.thunderhack.command.Command;
/*      */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*      */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*      */ import com.mrzak34.thunderhack.util.EntityUtil;
/*      */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*      */ import com.mrzak34.thunderhack.util.RotationUtil;
/*      */ import com.mrzak34.thunderhack.util.Util;
/*      */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.function.Predicate;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.item.EntityEnderCrystal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.client.CPacketUseEntity;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public abstract class AbstractCalculation<T extends CrystalData> extends Finishable {
/*      */   protected final Set<BlockPos> blackList;
/*   39 */   protected double maxY = Double.MAX_VALUE;
/*      */   protected final List<Entity> entities;
/*      */   protected final AutoCrystal module;
/*      */   protected final List<EntityPlayer> raw;
/*      */   protected List<EntityPlayer> friends;
/*      */   protected List<EntityPlayer> enemies;
/*      */   protected List<EntityPlayer> players;
/*      */   protected List<EntityPlayer> all;
/*      */   protected BreakData<T> breakData;
/*      */   protected PlaceData placeData;
/*      */   protected boolean scheduling;
/*      */   protected boolean attacking;
/*      */   protected boolean noPlace;
/*      */   protected boolean noBreak;
/*      */   protected boolean rotating;
/*      */   protected boolean placing;
/*      */   protected boolean fallback;
/*      */   protected int motionID;
/*      */   protected int count;
/*      */   protected int shieldCount;
/*      */   protected int shieldRange;
/*      */   
/*      */   public AbstractCalculation(AutoCrystal module, List<Entity> entities, List<EntityPlayer> players, BlockPos... blackList) {
/*   62 */     this.noPlace = false;
/*   63 */     this.noBreak = false;
/*   64 */     this.motionID = module.motionID.get();
/*   65 */     if (blackList.length == 0) {
/*   66 */       this.blackList = new EmptySet<>();
/*      */     } else {
/*   68 */       this.blackList = new HashSet<>();
/*   69 */       for (BlockPos pos : blackList) {
/*   70 */         if (pos != null) {
/*   71 */           this.blackList.add(pos);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*   76 */     this.module = module;
/*   77 */     this.entities = entities;
/*   78 */     this.raw = players;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractCalculation(AutoCrystal module, List<Entity> entities, List<EntityPlayer> players, boolean breakOnly, boolean noBreak, BlockPos... blackList) {
/*   87 */     this(module, entities, players, blackList);
/*   88 */     this.noPlace = breakOnly;
/*   89 */     this.noBreak = noBreak;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void execute() {
/*      */     try {
/*   97 */       if (((Boolean)this.module.clearPost.getValue()).booleanValue())
/*      */       {
/*   99 */         this.module.post.clear();
/*      */       }
/*      */       
/*  102 */       runCalc();
/*  103 */     } catch (Throwable t) {
/*  104 */       t.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void runCalc() {
/*  109 */     if (check()) {
/*      */       return;
/*      */     }
/*      */     
/*  113 */     if (((Boolean)this.module.forceAntiTotem.getValue()).booleanValue() && ((Boolean)this.module.antiTotem
/*  114 */       .getValue()).booleanValue() && 
/*  115 */       checkForceAntiTotem()) {
/*      */       return;
/*      */     }
/*      */     
/*  119 */     float minDamage = this.module.getMinDamage();
/*  120 */     if (((Boolean)this.module.focusRotations.getValue()).booleanValue() && !this.module.noRotateNigga(AutoCrystal.ACRotate.Break) && focusBreak()) {
/*      */       return;
/*      */     }
/*  123 */     this.module.focus = null;
/*      */ 
/*      */     
/*  126 */     if (this.breakData == null && breakCheck()) {
/*  127 */       this
/*  128 */         .breakData = getBreakHelper().getData(getBreakDataSet(), this.entities, this.all, this.friends);
/*      */       
/*  130 */       setCount(this.breakData, minDamage);
/*  131 */       if (evaluate(this.breakData)) {
/*      */         return;
/*      */       }
/*  134 */     } else if (((Boolean)this.module.multiPlaceCalc.getValue()).booleanValue()) {
/*  135 */       if (this.breakData != null) {
/*  136 */         setCount(this.breakData, minDamage);
/*  137 */         this.breakData = null;
/*      */       }
/*      */       else {
/*      */         
/*  141 */         BreakData<T> onlyForCountData = getBreakHelper().getData(new ArrayList<>(5), this.entities, this.all, this.friends);
/*  142 */         setCount(onlyForCountData, minDamage);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  148 */     if (placeCheck()) {
/*  149 */       this.placeData = this.module.placeHelper.getData(this.all, this.players, this.enemies, this.friends, this.entities, minDamage, this.blackList, this.maxY);
/*  150 */       if (place(this.placeData)) {
/*  151 */         boolean passed = this.module.obbyCalcTimer.passedMs(((Integer)this.module.obbyCalc.getValue()).intValue());
/*  152 */         if ((obbyCheck() && passed && placeObby(this.placeData, null)) || ((Boolean)this.module.basePlaceOnly.getValue()).booleanValue()) {
/*      */           return;
/*      */         }
/*      */         
/*  156 */         if (preSpecialCheck() && (
/*  157 */           !((Boolean)this.module.requireOnGround.getValue()).booleanValue() || Util.mc.field_71439_g.field_70122_E) && ((Boolean)this.module.interruptSpeedmine
/*      */           
/*  159 */           .getValue()).booleanValue() && (
/*      */ 
/*      */ 
/*      */           
/*  163 */           !((Boolean)this.module.pickaxeOnly.getValue()).booleanValue() || Util.mc.field_71439_g
/*  164 */           .func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemPickaxe) && this.module.liquidTimer
/*      */           
/*  166 */           .passedMs(((Integer)this.module.liqDelay.getValue()).intValue()) && (((Boolean)this.module.lava
/*  167 */           .getValue()).booleanValue() || ((Boolean)this.module.water.getValue()).booleanValue())) {
/*  168 */           MineSlots mineSlots = HelperLiquids.getSlots(((Boolean)this.module.requireOnGround.getValue()).booleanValue());
/*  169 */           if (mineSlots.getBlockSlot() == -1 || mineSlots
/*  170 */             .getDamage() < 1.0F) {
/*      */             return;
/*      */           }
/*      */           
/*  174 */           PlaceData liquidData = this.module.liquidHelper.calculate(this.module.placeHelper, this.placeData, this.friends, this.all, ((Float)this.module.minDamage.getValue()).floatValue());
/*      */           
/*  176 */           boolean attackingBefore = this.attacking;
/*  177 */           if (placeNoAntiTotem(liquidData, mineSlots) && attackingBefore == this.attacking && ((Boolean)this.module.liquidObby.getValue()).booleanValue() && obbyCheck() && passed) {
/*  178 */             placeObby(this.placeData, mineSlots);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void setCount(BreakData<T> breakData, float minDmg) {
/*  186 */     this.shieldCount = breakData.getShieldCount();
/*  187 */     if (((Boolean)this.module.multiPlaceMinDmg.getValue()).booleanValue()) {
/*  188 */       this
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  193 */         .count = (int)breakData.getData().stream().filter(d -> (((Boolean)this.module.countDeadCrystals.getValue()).booleanValue() || !EntityUtil.isDead(d.getCrystal()))).filter(d -> (d.getDamage() > minDmg)).count();
/*      */       
/*      */       return;
/*      */     } 
/*  197 */     this
/*      */ 
/*      */ 
/*      */       
/*  201 */       .count = (int)breakData.getData().stream().filter(d -> (((Boolean)this.module.countDeadCrystals.getValue()).booleanValue() || !EntityUtil.isDead(d.getCrystal()))).count();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean evaluate(BreakData<T> breakData) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   4: invokevirtual shouldDanger : ()Z
/*      */     //   7: istore_2
/*      */     //   8: iload_2
/*      */     //   9: ifne -> 16
/*      */     //   12: iconst_1
/*      */     //   13: goto -> 17
/*      */     //   16: iconst_0
/*      */     //   17: istore_3
/*      */     //   18: aload_0
/*      */     //   19: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*      */     //   22: invokevirtual getAntiTotem : ()Lnet/minecraft/entity/Entity;
/*      */     //   25: ifnull -> 120
/*      */     //   28: aload_0
/*      */     //   29: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   32: aload_0
/*      */     //   33: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*      */     //   36: invokevirtual getAntiTotem : ()Lnet/minecraft/entity/Entity;
/*      */     //   39: invokestatic isValid : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;Lnet/minecraft/entity/Entity;)Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   42: dup
/*      */     //   43: astore #4
/*      */     //   45: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.INVALID : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   48: if_acmpeq -> 120
/*      */     //   51: aload_0
/*      */     //   52: aload_0
/*      */     //   53: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*      */     //   56: invokevirtual getAntiTotem : ()Lnet/minecraft/entity/Entity;
/*      */     //   59: aload #4
/*      */     //   61: invokevirtual attack : (Lnet/minecraft/entity/Entity;Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;)Z
/*      */     //   64: pop
/*      */     //   65: aload_0
/*      */     //   66: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   69: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*      */     //   72: aload_0
/*      */     //   73: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   76: getfield breakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   79: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   82: checkcast java/lang/Integer
/*      */     //   85: invokevirtual intValue : ()I
/*      */     //   88: i2l
/*      */     //   89: invokeinterface reset : (J)Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*      */     //   94: pop
/*      */     //   95: aload_0
/*      */     //   96: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   99: getfield antiTotemHelper : Lcom/mrzak34/thunderhack/util/phobos/AntiTotemHelper;
/*      */     //   102: aconst_null
/*      */     //   103: invokevirtual setTarget : (Lnet/minecraft/entity/player/EntityPlayer;)V
/*      */     //   106: aload_0
/*      */     //   107: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   110: getfield antiTotemHelper : Lcom/mrzak34/thunderhack/util/phobos/AntiTotemHelper;
/*      */     //   113: aconst_null
/*      */     //   114: invokevirtual setTargetPos : (Lnet/minecraft/util/math/BlockPos;)V
/*      */     //   117: goto -> 562
/*      */     //   120: aload_0
/*      */     //   121: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   124: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate.Break : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;
/*      */     //   127: invokevirtual noRotateNigga : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;)Z
/*      */     //   130: ifne -> 137
/*      */     //   133: iconst_1
/*      */     //   134: goto -> 153
/*      */     //   137: aload_0
/*      */     //   138: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   141: getfield packets : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   144: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   147: checkcast java/lang/Integer
/*      */     //   150: invokevirtual intValue : ()I
/*      */     //   153: istore #5
/*      */     //   155: aconst_null
/*      */     //   156: astore #6
/*      */     //   158: new java/util/ArrayList
/*      */     //   161: dup
/*      */     //   162: iload #5
/*      */     //   164: invokespecial <init> : (I)V
/*      */     //   167: astore #7
/*      */     //   169: aload_0
/*      */     //   170: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*      */     //   173: invokevirtual getData : ()Ljava/util/Collection;
/*      */     //   176: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   181: astore #8
/*      */     //   183: aload #8
/*      */     //   185: invokeinterface hasNext : ()Z
/*      */     //   190: ifeq -> 286
/*      */     //   193: aload #8
/*      */     //   195: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   200: checkcast com/mrzak34/thunderhack/util/phobos/CrystalData
/*      */     //   203: astore #9
/*      */     //   205: aload #9
/*      */     //   207: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*      */     //   210: invokestatic isDead : (Lnet/minecraft/entity/Entity;)Z
/*      */     //   213: ifeq -> 219
/*      */     //   216: goto -> 183
/*      */     //   219: aload_0
/*      */     //   220: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   223: aload #9
/*      */     //   225: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*      */     //   228: invokestatic isValid : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;Lnet/minecraft/entity/Entity;)Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   231: astore #4
/*      */     //   233: aload #4
/*      */     //   235: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.VALID : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   238: if_acmpne -> 266
/*      */     //   241: aload #7
/*      */     //   243: invokeinterface size : ()I
/*      */     //   248: iload #5
/*      */     //   250: if_icmpge -> 266
/*      */     //   253: aload #7
/*      */     //   255: aload #9
/*      */     //   257: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   262: pop
/*      */     //   263: goto -> 283
/*      */     //   266: aload #4
/*      */     //   268: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.ROTATIONS : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   271: if_acmpne -> 283
/*      */     //   274: aload #6
/*      */     //   276: ifnonnull -> 283
/*      */     //   279: aload #9
/*      */     //   281: astore #6
/*      */     //   283: goto -> 183
/*      */     //   286: aload_0
/*      */     //   287: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   290: getfield slowBreakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   293: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   296: checkcast java/lang/Integer
/*      */     //   299: invokevirtual intValue : ()I
/*      */     //   302: istore #8
/*      */     //   304: aload_0
/*      */     //   305: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   308: getfield slowBreakDamage : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   311: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   314: checkcast java/lang/Float
/*      */     //   317: invokevirtual floatValue : ()F
/*      */     //   320: fstore #9
/*      */     //   322: aload #7
/*      */     //   324: invokeinterface isEmpty : ()Z
/*      */     //   329: ifeq -> 402
/*      */     //   332: aload #6
/*      */     //   334: ifnull -> 562
/*      */     //   337: aload_0
/*      */     //   338: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   341: invokevirtual shouldDanger : ()Z
/*      */     //   344: ifne -> 386
/*      */     //   347: aload #6
/*      */     //   349: invokevirtual getDamage : ()F
/*      */     //   352: fload #9
/*      */     //   354: fcmpg
/*      */     //   355: ifgt -> 362
/*      */     //   358: iconst_1
/*      */     //   359: goto -> 363
/*      */     //   362: iconst_0
/*      */     //   363: dup
/*      */     //   364: istore_3
/*      */     //   365: ifeq -> 386
/*      */     //   368: aload_0
/*      */     //   369: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   372: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*      */     //   375: iload #8
/*      */     //   377: i2l
/*      */     //   378: invokeinterface passed : (J)Z
/*      */     //   383: ifeq -> 562
/*      */     //   386: aload_0
/*      */     //   387: aload #6
/*      */     //   389: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*      */     //   392: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.ROTATIONS : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   395: invokevirtual attack : (Lnet/minecraft/entity/Entity;Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;)Z
/*      */     //   398: pop
/*      */     //   399: goto -> 562
/*      */     //   402: aload #7
/*      */     //   404: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   409: astore #10
/*      */     //   411: aload #10
/*      */     //   413: invokeinterface hasNext : ()Z
/*      */     //   418: ifeq -> 562
/*      */     //   421: aload #10
/*      */     //   423: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   428: checkcast com/mrzak34/thunderhack/util/phobos/CrystalData
/*      */     //   431: astore #11
/*      */     //   433: aload #11
/*      */     //   435: invokevirtual getDamage : ()F
/*      */     //   438: aload_0
/*      */     //   439: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   442: getfield slowBreakDamage : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   445: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   448: checkcast java/lang/Float
/*      */     //   451: invokevirtual floatValue : ()F
/*      */     //   454: fcmpl
/*      */     //   455: ifle -> 462
/*      */     //   458: iconst_1
/*      */     //   459: goto -> 463
/*      */     //   462: iconst_0
/*      */     //   463: istore #12
/*      */     //   465: iload #12
/*      */     //   467: ifne -> 531
/*      */     //   470: iload_2
/*      */     //   471: ifne -> 531
/*      */     //   474: aload_0
/*      */     //   475: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   478: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*      */     //   481: aload_0
/*      */     //   482: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   485: getfield slowBreakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   488: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   491: checkcast java/lang/Integer
/*      */     //   494: invokevirtual intValue : ()I
/*      */     //   497: i2l
/*      */     //   498: invokeinterface passed : (J)Z
/*      */     //   503: ifeq -> 559
/*      */     //   506: aload #11
/*      */     //   508: invokevirtual getDamage : ()F
/*      */     //   511: aload_0
/*      */     //   512: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   515: getfield minBreakDamage : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   518: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   521: checkcast java/lang/Float
/*      */     //   524: invokevirtual floatValue : ()F
/*      */     //   527: fcmpl
/*      */     //   528: iflt -> 559
/*      */     //   531: iload_3
/*      */     //   532: ifeq -> 544
/*      */     //   535: iload #12
/*      */     //   537: ifne -> 544
/*      */     //   540: iconst_1
/*      */     //   541: goto -> 545
/*      */     //   544: iconst_0
/*      */     //   545: istore_3
/*      */     //   546: aload_0
/*      */     //   547: aload #11
/*      */     //   549: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*      */     //   552: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.VALID : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   555: invokevirtual attack : (Lnet/minecraft/entity/Entity;Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;)Z
/*      */     //   558: pop
/*      */     //   559: goto -> 411
/*      */     //   562: aload_0
/*      */     //   563: getfield attacking : Z
/*      */     //   566: ifeq -> 623
/*      */     //   569: aload_0
/*      */     //   570: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   573: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*      */     //   576: iload_3
/*      */     //   577: ifeq -> 600
/*      */     //   580: aload_0
/*      */     //   581: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   584: getfield slowBreakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   587: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   590: checkcast java/lang/Integer
/*      */     //   593: invokevirtual intValue : ()I
/*      */     //   596: i2l
/*      */     //   597: goto -> 617
/*      */     //   600: aload_0
/*      */     //   601: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   604: getfield breakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*      */     //   607: invokevirtual getValue : ()Ljava/lang/Object;
/*      */     //   610: checkcast java/lang/Integer
/*      */     //   613: invokevirtual intValue : ()I
/*      */     //   616: i2l
/*      */     //   617: invokeinterface reset : (J)Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*      */     //   622: pop
/*      */     //   623: aload_0
/*      */     //   624: getfield rotating : Z
/*      */     //   627: ifeq -> 647
/*      */     //   630: aload_0
/*      */     //   631: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*      */     //   634: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate.Place : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;
/*      */     //   637: invokevirtual noRotateNigga : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;)Z
/*      */     //   640: ifne -> 647
/*      */     //   643: iconst_1
/*      */     //   644: goto -> 648
/*      */     //   647: iconst_0
/*      */     //   648: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #206	-> 0
/*      */     //   #207	-> 8
/*      */     //   #211	-> 18
/*      */     //   #212	-> 51
/*      */     //   #213	-> 65
/*      */     //   #214	-> 95
/*      */     //   #215	-> 106
/*      */     //   #217	-> 120
/*      */     //   #219	-> 155
/*      */     //   #220	-> 158
/*      */     //   #222	-> 169
/*      */     //   #224	-> 205
/*      */     //   #225	-> 216
/*      */     //   #228	-> 219
/*      */     //   #229	-> 233
/*      */     //   #230	-> 253
/*      */     //   #231	-> 266
/*      */     //   #232	-> 279
/*      */     //   #234	-> 283
/*      */     //   #236	-> 286
/*      */     //   #237	-> 304
/*      */     //   #242	-> 322
/*      */     //   #243	-> 332
/*      */     //   #244	-> 386
/*      */     //   #247	-> 402
/*      */     //   #248	-> 433
/*      */     //   #249	-> 465
/*      */     //   #250	-> 531
/*      */     //   #251	-> 546
/*      */     //   #253	-> 559
/*      */     //   #257	-> 562
/*      */     //   #258	-> 569
/*      */     //   #261	-> 623
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   45	75	4	validity	Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   205	78	9	data	Lcom/mrzak34/thunderhack/util/phobos/CrystalData;
/*      */     //   233	53	4	validity	Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*      */     //   465	94	12	high	Z
/*      */     //   433	126	11	valid	Lcom/mrzak34/thunderhack/util/phobos/CrystalData;
/*      */     //   155	407	5	packets	I
/*      */     //   158	404	6	firstRotation	Lcom/mrzak34/thunderhack/util/phobos/CrystalData;
/*      */     //   169	393	7	valids	Ljava/util/List;
/*      */     //   304	258	8	slowDelay	I
/*      */     //   322	240	9	slow	F
/*      */     //   0	649	0	this	Lcom/mrzak34/thunderhack/util/phobos/AbstractCalculation;
/*      */     //   0	649	1	breakData	Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*      */     //   8	641	2	shouldDanger	Z
/*      */     //   18	631	3	slowReset	Z
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   205	78	9	data	TT;
/*      */     //   433	126	11	valid	TT;
/*      */     //   158	404	6	firstRotation	TT;
/*      */     //   169	393	7	valids	Ljava/util/List<TT;>;
/*      */     //   0	649	0	this	Lcom/mrzak34/thunderhack/util/phobos/AbstractCalculation<TT;>;
/*      */     //   0	649	1	breakData	Lcom/mrzak34/thunderhack/util/phobos/BreakData<TT;>;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean breakCheck() {
/*  266 */     return (((Boolean)this.module.attack.getValue()).booleanValue() && !this.noBreak && Thunderhack.switchManager
/*      */       
/*  268 */       .getLastSwitch() >= ((Integer)this.module.cooldown.getValue()).intValue() && this.module.breakTimer
/*  269 */       .passed(((Integer)this.module.breakDelay.getValue()).intValue()));
/*      */   }
/*      */   
/*      */   protected boolean placeCheck() {
/*  273 */     if (this.module.sequentialHelper.isBlockingPlacement()) {
/*  274 */       return false;
/*      */     }
/*      */     
/*  277 */     if (((Boolean)this.module.damageSync.getValue()).booleanValue()) {
/*  278 */       Confirmer c = this.module.damageSyncHelper.getConfirmer();
/*  279 */       if (c.isValid() && (
/*  280 */         !c.isPlaceConfirmed(((Integer)this.module.placeConfirm.getValue()).intValue()) || 
/*  281 */         !c.isBreakConfirmed(((Integer)this.module.breakConfirm.getValue()).intValue())))
/*      */       {
/*  283 */         if (c.isValid() && ((Boolean)this.module.preSynCheck.getValue()).booleanValue()) {
/*  284 */           return false;
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/*  289 */     return (this.count < ((Integer)this.module.multiPlace.getValue()).intValue() && Thunderhack.switchManager
/*  290 */       .getLastSwitch() >= ((Integer)this.module.placeCoolDown
/*  291 */       .getValue()).intValue() && ((Boolean)this.module.place
/*  292 */       .getValue()).booleanValue() && (!this.attacking || ((Boolean)this.module.multiTask
/*  293 */       .getValue()).booleanValue()) && (!this.rotating || this.module
/*      */       
/*  295 */       .noRotateNigga(AutoCrystal.ACRotate.Place)) && this.module.placeTimer
/*  296 */       .passed(((Integer)this.module.placeDelay.getValue()).intValue()) && !this.noPlace);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean obbyCheck() {
/*  301 */     return (preSpecialCheck() && ((Boolean)this.module.obsidian.getValue()).booleanValue() && this.module.obbyTimer.passedMs(((Integer)this.module.obbyDelay.getValue()).intValue()));
/*      */   }
/*      */   
/*      */   protected boolean preSpecialCheck() {
/*  305 */     return (!this.placing && this.placeData != null && (this.placeData
/*      */       
/*  307 */       .getTarget() != null || this.module.targetMode
/*  308 */       .getValue() == AutoCrystal.Target.Damage) && !this.fallback);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean check() {
/*  313 */     if ((!((Boolean)this.module.spectator.getValue()).booleanValue() && Util.mc.field_71439_g.func_175149_v()) || this.raw == null || this.entities == null || (((Boolean)this.module.stopWhenEating
/*      */ 
/*      */       
/*  316 */       .getValue()).booleanValue() && this.module.isEating()) || (((Boolean)this.module.stopWhenMining
/*  317 */       .getValue()).booleanValue() && this.module.isMining())) {
/*  318 */       return true;
/*      */     }
/*      */     
/*  321 */     setFriendsAndEnemies();
/*  322 */     if (this.all.isEmpty()) {
/*  323 */       return true;
/*      */     }
/*      */     
/*  326 */     if (!this.module.shouldcalcN() && this.module.autoSwitch
/*  327 */       .getValue() != AutoCrystal.AutoSwitch.Always && 
/*  328 */       !this.module.weaknessHelper.canSwitch() && !this.module.switching)
/*      */     {
/*  330 */       return true;
/*      */     }
/*      */     
/*  333 */     return (this.module.weaknessHelper.isWeaknessed() && this.module.antiWeakness
/*  334 */       .getValue() == AutoCrystal.AntiWeakness.None);
/*      */   }
/*      */   
/*      */   protected void setFriendsAndEnemies() {
/*  338 */     if (this.module.isSuicideModule()) {
/*      */ 
/*      */       
/*  341 */       this
/*  342 */         .enemies = new ArrayList<>(Arrays.asList(new EntityPlayer[] { RotationUtil.getRotationPlayer() }));
/*  343 */       this.players = new ArrayList<>(0);
/*  344 */       this.friends = new ArrayList<>(0);
/*  345 */       this.all = this.enemies;
/*      */       
/*      */       return;
/*      */     } 
/*  349 */     List<List<EntityPlayer>> split = CollectionUtil.split(this.raw, (Predicate<EntityPlayer>[])new Predicate[] { p -> (p == null || EntityUtil.isDead((Entity)p) || p.equals(Util.mc.field_71439_g) || p.func_70068_e((Entity)Util.mc.field_71439_g) > MathUtil.square(((Float)this.module.targetRange.getValue()).floatValue())), Thunderhack.friendManager::isFriend, Thunderhack.friendManager::isEnemy });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  354 */     this.friends = split.get(1);
/*  355 */     this.enemies = split.get(2);
/*  356 */     this.players = split.get(3);
/*  357 */     this.all = new ArrayList<>(this.enemies.size() + this.players.size());
/*  358 */     this.shieldRange = (int)(this.shieldRange + this.enemies.stream().peek(e -> this.all.add(e)).filter(e -> (e.func_70068_e((Entity)Util.mc.field_71439_g) <= MathUtil.square(((Float)this.module.shieldRange.getValue()).floatValue()))).count());
/*  359 */     this.shieldRange = (int)(this.shieldRange + this.players.stream().peek(e -> this.all.add(e)).filter(e -> (e.func_70068_e((Entity)Util.mc.field_71439_g) <= MathUtil.square(((Float)this.module.shieldRange.getValue()).floatValue()))).count());
/*  360 */     if (((Boolean)this.module.yCalc.getValue()).booleanValue()) {
/*  361 */       this.maxY = Double.MIN_VALUE;
/*  362 */       for (EntityPlayer player : this.all) {
/*  363 */         if (player.field_70163_u > this.maxY)
/*  364 */           this.maxY = player.field_70163_u; 
/*      */       } 
/*      */     } 
/*      */   } protected boolean attack(Entity entity, AutoCrystal.BreakValidity validity) {
/*      */     MutableWrapper<Boolean> attacked;
/*      */     Runnable post;
/*      */     RotationFunction function;
/*  371 */     if (((Boolean)this.module.basePlaceOnly.getValue()).booleanValue()) {
/*  372 */       return (validity != AutoCrystal.BreakValidity.INVALID);
/*      */     }
/*      */     
/*  375 */     this.module.setCrystal(entity);
/*  376 */     switch (validity) {
/*      */       case VALID:
/*  378 */         if (this.module.weaknessHelper.isWeaknessed()) {
/*  379 */           if (this.module.antiWeakness.getValue() == AutoCrystal.AntiWeakness.None) {
/*  380 */             return false;
/*      */           }
/*  382 */           Runnable r = this.module.rotationHelper.post(entity, new MutableWrapper<>(
/*  383 */                 Boolean.valueOf(false)));
/*  384 */           r.run();
/*  385 */           this.attacking = true;
/*      */           
/*  387 */           if (!this.module.noRotateNigga(AutoCrystal.ACRotate.Break)) {
/*  388 */             this.module.rotation = this.module.rotationHelper.forBreaking(entity, new MutableWrapper<>(Boolean.valueOf(true)));
/*      */           }
/*      */           
/*  391 */           return true;
/*      */         } 
/*      */ 
/*      */         
/*  395 */         if (this.module.breakSwing.getValue() == AutoCrystal.SwingTime.Pre) {
/*  396 */           Swing.Packet.swing(EnumHand.MAIN_HAND);
/*      */         }
/*      */         
/*  399 */         Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity));
/*      */         
/*  401 */         if (((Boolean)this.module.pseudoSetDead.getValue()).booleanValue()) {
/*  402 */           ((IEntity)entity).setPseudoDeadT(true);
/*  403 */         } else if (((Boolean)this.module.setDead.getValue()).booleanValue()) {
/*  404 */           Thunderhack.setDeadManager.setDead(entity);
/*      */         } 
/*      */         
/*  407 */         if (this.module.breakSwing.getValue() == AutoCrystal.SwingTime.Post) {
/*  408 */           Swing.Packet.swing(EnumHand.MAIN_HAND);
/*      */         }
/*      */         
/*  411 */         Swing.Client.swing(EnumHand.MAIN_HAND);
/*  412 */         this.attacking = true;
/*      */         
/*  414 */         if (!this.module.noRotateNigga(AutoCrystal.ACRotate.Break)) {
/*  415 */           this.module
/*  416 */             .rotation = this.module.rotationHelper.forBreaking(entity, new MutableWrapper<>(
/*  417 */                 Boolean.valueOf(true)));
/*      */         }
/*  419 */         return true;
/*      */       case ROTATIONS:
/*  421 */         this.attacking = true;
/*  422 */         this.rotating = true;
/*  423 */         attacked = new MutableWrapper<>(Boolean.valueOf(false));
/*      */         
/*  425 */         post = this.module.rotationHelper.post(entity, attacked);
/*      */         
/*  427 */         function = this.module.rotationHelper.forBreaking(entity, attacked);
/*      */         
/*  429 */         if (((Boolean)this.module.multiThread.getValue()).booleanValue() && this.module.rotationThread
/*  430 */           .getValue() == AutoCrystal.RotationThread.Cancel && this.module.rotationCanceller
/*      */           
/*  432 */           .setRotations(function) && 
/*  433 */           HelperUtil.isValid(this.module, entity) == AutoCrystal.BreakValidity.VALID) {
/*      */           
/*  435 */           this.rotating = false;
/*  436 */           post.run();
/*      */         } else {
/*  438 */           this.module.rotation = function;
/*  439 */           this.module.post.add(post);
/*      */         } 
/*      */         
/*  442 */         return true;
/*      */     } 
/*      */     
/*  445 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean checkForceAntiTotem() {
/*  450 */     BlockPos forcePos = this.module.forceHelper.getPos();
/*  451 */     if (forcePos != null && this.module.forceHelper
/*  452 */       .isForcing(((Boolean)this.module.syncForce.getValue()).booleanValue())) {
/*  453 */       for (Entity entity : this.entities) {
/*  454 */         if (entity instanceof EntityEnderCrystal && 
/*  455 */           !EntityUtil.isDead(entity) && entity
/*  456 */           .func_174813_aQ()
/*  457 */           .func_72326_a(new AxisAlignedBB(forcePos.func_177984_a()))) {
/*  458 */           attack(entity, HelperUtil.isValid(this.module, entity));
/*  459 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/*  463 */       return true;
/*      */     } 
/*      */     
/*  466 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean place(PlaceData data) {
/*  471 */     AntiTotemData antiTotem = null;
/*      */     
/*  473 */     boolean god = (((Boolean)this.module.godAntiTotem.getValue()).booleanValue() && this.module.idHelper.isSafe(this.raw, ((Boolean)this.module.holdingCheck
/*  474 */         .getValue()).booleanValue(), ((Boolean)this.module.toolCheck
/*  475 */         .getValue()).booleanValue()));
/*  476 */     for (AntiTotemData antiTotemData : data.getAntiTotem()) {
/*  477 */       if (!antiTotemData.getCorresponding().isEmpty() && 
/*  478 */         !antiTotemData.isBlocked()) {
/*  479 */         BlockPos pos = antiTotemData.getPos();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  484 */         EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)Util.mc.field_71441_e, (pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F));
/*      */         
/*  486 */         if (god)
/*      */         {
/*  488 */           for (PositionData positionData : antiTotemData.getCorresponding()) {
/*  489 */             if (positionData.isBlocked()) {
/*      */               continue;
/*      */             }
/*      */             
/*  493 */             BlockPos up = positionData.getPos().func_177984_a();
/*  494 */             double y = ((Boolean)this.module.newVerEntities.getValue()).booleanValue() ? 1.0D : 2.0D;
/*  495 */             if (entityEnderCrystal.func_174813_aQ().func_72326_a(new AxisAlignedBB(up
/*      */                   
/*  497 */                   .func_177958_n(), up
/*  498 */                   .func_177956_o(), up
/*  499 */                   .func_177952_p(), up
/*  500 */                   .func_177958_n() + 1.0D, up
/*  501 */                   .func_177956_o() + y, up
/*  502 */                   .func_177952_p() + 1.0D))) {
/*      */               continue;
/*      */             }
/*      */             
/*  506 */             if (((Boolean)this.module.totemSync.getValue()).booleanValue() && this.module.damageSyncHelper
/*  507 */               .isSyncing(0.0F, true)) {
/*  508 */               return false;
/*      */             }
/*      */             
/*  511 */             this.module.noGod = true;
/*  512 */             this.module.antiTotemHelper
/*  513 */               .setTargetPos(antiTotemData.getPos());
/*      */             
/*  515 */             EntityPlayer player = antiTotemData.getFirstTarget();
/*  516 */             Command.sendMessage("Attempting God-AntiTotem: " + ((player == null) ? "null" : player.func_70005_c_()));
/*      */             
/*  518 */             place(antiTotemData, player, false, false, false);
/*      */             
/*  520 */             this.module.idHelper.attack((AutoCrystal.SwingTime)this.module.breakSwing.getValue(), (AutoCrystal.PlaceSwing)this.module.godSwing
/*  521 */                 .getValue(), 1, ((Integer)this.module.idPackets
/*      */                 
/*  523 */                 .getValue()).intValue(), 0);
/*      */ 
/*      */             
/*  526 */             place(positionData, player, false, false, false);
/*      */             
/*  528 */             this.module.idHelper.attack((AutoCrystal.SwingTime)this.module.breakSwing.getValue(), (AutoCrystal.PlaceSwing)this.module.godSwing
/*  529 */                 .getValue(), 2, ((Integer)this.module.idPackets
/*      */                 
/*  531 */                 .getValue()).intValue(), 0);
/*      */ 
/*      */             
/*  534 */             this.module.breakTimer.reset(((Integer)this.module.breakDelay.getValue()).intValue());
/*  535 */             this.module.noGod = false;
/*  536 */             return false;
/*      */           } 
/*      */         }
/*      */         
/*  540 */         if (antiTotem == null) {
/*  541 */           antiTotem = antiTotemData;
/*  542 */           if (!god) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  549 */     if (antiTotem != null) {
/*  550 */       EntityPlayer player = antiTotem.getFirstTarget();
/*  551 */       this.module.setTarget(player);
/*      */       
/*  553 */       if (((Boolean)this.module.totemSync.getValue()).booleanValue() && this.module.damageSyncHelper
/*  554 */         .isSyncing(0.0F, true)) {
/*  555 */         return false;
/*      */       }
/*      */       
/*  558 */       Command.sendMessage("Attempting AntiTotem: " + ((player == null) ? "null" : player
/*  559 */           .func_70005_c_()));
/*      */       
/*  561 */       this.module.antiTotemHelper.setTargetPos(antiTotem.getPos());
/*  562 */       place(antiTotem, player, !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), (this.rotating || this.scheduling), false);
/*      */       
/*  564 */       return false;
/*      */     } 
/*      */     
/*  567 */     if (((Boolean)this.module.forceAntiTotem.getValue()).booleanValue() && ((Boolean)this.module.antiTotem
/*  568 */       .getValue()).booleanValue() && this.module.forceTimer
/*  569 */       .passedMs(((Integer)this.module.attempts.getValue()).intValue()))
/*      */     {
/*      */       
/*  572 */       for (Map.Entry<EntityPlayer, ForceData> entry : data.getForceData().entrySet()) {
/*  573 */         ForceData forceData = entry.getValue();
/*      */ 
/*      */ 
/*      */         
/*  577 */         PositionData first = forceData.getForceData().stream().findFirst().orElse(null);
/*  578 */         if (first == null || 
/*  579 */           !forceData.hasPossibleAntiTotem() || 
/*  580 */           !forceData.hasPossibleHighDamage()) {
/*      */           continue;
/*      */         }
/*      */         
/*  584 */         if (((Boolean)this.module.syncForce.getValue()).booleanValue() && this.module.damageSyncHelper
/*  585 */           .isSyncing(0.0F, true)) {
/*  586 */           return false;
/*      */         }
/*      */         
/*  589 */         this.module.forceHelper.setSync(first.getPos(), ((Boolean)this.module.newVerEntities
/*  590 */             .getValue()).booleanValue());
/*  591 */         place(first, entry.getKey(), !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), (this.rotating || this.scheduling), ((Boolean)this.module.forceSlow.getValue()).booleanValue());
/*      */         
/*  593 */         this.module.forceTimer.reset();
/*  594 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*  598 */     return placeNoAntiTotem(data, null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean placeNoAntiTotem(PlaceData data, MineSlots liquid) {
/*  603 */     float maxBlockedDamage = 0.0F;
/*  604 */     PositionData firstData = null;
/*      */     
/*  606 */     for (PositionData d : data.getData()) {
/*  607 */       if ((((Boolean)this.module.override.getValue()).booleanValue() && d
/*  608 */         .getSelfDamage() > ((Float)this.module.maxSelfPlace.getValue()).floatValue() && d
/*  609 */         .getMaxDamage() < d.getHealth()) || (((Boolean)this.module.efficientPlacements
/*  610 */         .getValue()).booleanValue() && d
/*  611 */         .getMaxDamage() < d.getSelfDamage() && (
/*  612 */         !((Boolean)this.module.override.getValue()).booleanValue() || d.getMaxDamage() < d.getHealth()))) {
/*      */         continue;
/*      */       }
/*      */       
/*  616 */       if (d.isBlocked()) {
/*  617 */         if (maxBlockedDamage < d.getMaxDamage()) {
/*  618 */           maxBlockedDamage = d.getMaxDamage();
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  624 */       firstData = d;
/*      */     } 
/*      */ 
/*      */     
/*  628 */     boolean isRayBypass = false;
/*  629 */     if (((Boolean)this.module.rayTraceBypass.getValue()).booleanValue() && ((Boolean)this.module.forceBypass.getValue()).booleanValue() && firstData == null) {
/*  630 */       for (PositionData d : data.getRaytraceData()) {
/*  631 */         if (d.isBlocked()) {
/*  632 */           if (maxBlockedDamage < d.getMaxDamage()) {
/*  633 */             maxBlockedDamage = d.getMaxDamage();
/*  634 */             isRayBypass = true;
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  640 */         firstData = d;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  645 */     if (this.breakData != null && !this.attacking) {
/*  646 */       Entity fallback = this.breakData.getFallBack();
/*  647 */       if (((Boolean)this.module.fallBack.getValue()).booleanValue() && (!isRayBypass || ((Boolean)this.module.rayBypassFallback
/*  648 */         .getValue()).booleanValue()) && this.breakData
/*  649 */         .getFallBackDmg() < ((Float)this.module.fallBackDmg
/*  650 */         .getValue()).floatValue() && fallback != null && maxBlockedDamage != 0.0F && (firstData == null || maxBlockedDamage - firstData
/*      */ 
/*      */ 
/*      */         
/*  654 */         .getMaxDamage() >= ((Float)this.module.fallBackDiff
/*  655 */         .getValue()).floatValue())) {
/*  656 */         attack(fallback, HelperUtil.isValid(this.module, fallback));
/*  657 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  662 */     if (firstData != null && firstData.isRaytraceBypass()) {
/*  663 */       this.module.setRenderPos(firstData.getPos(), MathUtil.round(firstData.getMaxDamage(), 1) + " (RB)");
/*  664 */       this.module.setBypassPos(firstData.getPos());
/*  665 */       return false;
/*      */     } 
/*      */     
/*  668 */     if (firstData != null && !this.module.damageSyncHelper.isSyncing(firstData.getMaxDamage(), ((Boolean)this.module.damageSync.getValue()).booleanValue()) && (liquid == null || ((Float)this.module.minDamage.getValue()).floatValue() <= firstData.getMaxDamage())) {
/*  669 */       boolean slow = false;
/*  670 */       if (firstData.getMaxDamage() <= ((Float)this.module.slowPlaceDmg.getValue()).floatValue() && !this.module.shouldDanger()) {
/*  671 */         if (this.module.placeTimer.passed(((Integer)this.module.slowPlaceDelay.getValue()).intValue())) {
/*  672 */           slow = true;
/*      */         } else {
/*  674 */           return !this.module.damageSyncHelper.isSyncing(0.0F, ((Boolean)this.module.damageSync.getValue()).booleanValue());
/*      */         } 
/*      */       }
/*      */       
/*  678 */       MutableWrapper<Boolean> liquidBreak = null;
/*  679 */       if (liquid != null) {
/*  680 */         liquidBreak = placeAndBreakLiquid(firstData, liquid, this.rotating);
/*      */       }
/*      */       
/*  683 */       place(firstData, firstData.getTarget(), !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), (this.rotating || this.scheduling), slow, slow ? firstData.getMaxDamage() : Float.MAX_VALUE, liquidBreak);
/*  684 */       return false;
/*      */     } 
/*      */     
/*      */     Optional<PositionData> shield;
/*  688 */     if (((Boolean)this.module.shield.getValue()).booleanValue() && this.module.shieldTimer
/*  689 */       .passedMs(((Integer)this.module.shieldDelay.getValue()).intValue()) && (this.shieldCount < ((Integer)this.module.shieldCount
/*  690 */       .getValue()).intValue() || !this.attacking) && (
/*  691 */       shield = data.getShieldData().stream().findFirst()).isPresent() && this.placeData
/*  692 */       .getHighestSelfDamage() >= ((Float)this.module.shieldMinDamage
/*  693 */       .getValue()).floatValue() && this.shieldRange > 0) {
/*      */       
/*  695 */       place(shield.get(), ((PositionData)shield.get()).getTarget(), !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), (this.rotating || this.scheduling), false, Float.MAX_VALUE, null, true);
/*      */       
/*  697 */       this.module.shieldTimer.reset();
/*  698 */       return false;
/*      */     } 
/*      */     
/*  701 */     return !this.module.damageSyncHelper.isSyncing(0.0F, ((Boolean)this.module.damageSync
/*  702 */         .getValue()).booleanValue());
/*      */   }
/*      */   
/*      */   protected void place(PositionData data, EntityPlayer target, boolean rotate, boolean schedule, boolean resetSlow) {
/*  706 */     place(data, target, rotate, schedule, resetSlow, Float.MAX_VALUE, null);
/*      */   }
/*      */   
/*      */   protected void place(PositionData data, EntityPlayer target, boolean rotate, boolean schedule, boolean resetSlow, float damage, MutableWrapper<Boolean> liquidBreak) {
/*  710 */     place(data, target, rotate, schedule, resetSlow, damage, liquidBreak, false);
/*      */   }
/*      */   
/*      */   protected void place(PositionData data, EntityPlayer target, boolean rotate, boolean schedule, boolean resetSlow, float damage, MutableWrapper<Boolean> liquidBreak, boolean shield) {
/*  714 */     if (((Boolean)this.module.basePlaceOnly.getValue()).booleanValue()) {
/*      */       return;
/*      */     }
/*      */     
/*  718 */     if (liquidBreak != null) {
/*  719 */       this.module.liquidTimer.reset();
/*      */     }
/*      */     
/*  722 */     this.module.placeTimer.reset(resetSlow ? ((Integer)this.module.slowPlaceDelay.getValue()).intValue() : ((Integer)this.module.placeDelay
/*  723 */         .getValue()).intValue());
/*  724 */     BlockPos pos = data.getPos();
/*      */     
/*  726 */     BlockPos crystalPos = new BlockPos((pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F));
/*      */     
/*  728 */     this.module.placed.put(crystalPos, new CrystalTimeStamp(damage, shield));
/*  729 */     this.module.damageSyncHelper.setSync(pos, data.getMaxDamage(), ((Boolean)this.module.newVerEntities.getValue()).booleanValue());
/*  730 */     this.module.setTarget(target);
/*  731 */     boolean realtime = ((Boolean)this.module.realtime.getValue()).booleanValue();
/*  732 */     if (!realtime) {
/*  733 */       this.module.setRenderPos(pos, data.getMaxDamage());
/*      */     }
/*      */     
/*  736 */     MutableWrapper<Boolean> hasPlaced = new MutableWrapper<>(Boolean.valueOf(false));
/*  737 */     if (InventoryUtil.isHolding(Items.field_185158_cP) || 
/*  738 */       this.module.autoSwitch.getValue() == AutoCrystal.AutoSwitch.Always || this.module.autoSwitch
/*  739 */       .getValue() != AutoCrystal.AutoSwitch.Bind || this.module.switching);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  750 */     Runnable post = this.module.rotationHelper.post(this.module, data
/*  751 */         .getMaxDamage(), realtime, pos, hasPlaced, liquidBreak);
/*      */     
/*  753 */     if (rotate) {
/*      */       
/*  755 */       RotationFunction function = this.module.rotationHelper.forPlacing(pos, hasPlaced);
/*      */       
/*  757 */       if (this.module.rotationCanceller.setRotations(function)) {
/*  758 */         this.module.runPost();
/*  759 */         post.run();
/*      */         
/*  761 */         if (((Boolean)this.module.attack.getValue()).booleanValue() && ((Boolean)hasPlaced.get()).booleanValue()) {
/*  762 */           this.module.rotation = function;
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  768 */       this.module.rotation = function;
/*      */     } 
/*      */     
/*  771 */     if (schedule || !placeCheckPre(pos)) {
/*  772 */       this.module.post.add(post);
/*      */     } else {
/*  774 */       post.run();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean placeObby(PlaceData data, MineSlots liquid) {
/*  784 */     PositionData bestData = this.module.obbyHelper.findBestObbyData((liquid != null) ? data
/*  785 */         .getLiquidObby() : data
/*  786 */         .getAllObbyData(), this.all, this.friends, this.entities, data
/*      */ 
/*      */ 
/*      */         
/*  790 */         .getTarget(), ((Boolean)this.module.newVer
/*      */         
/*  792 */         .getValue()).booleanValue());
/*      */ 
/*      */     
/*  795 */     this.module.obbyCalcTimer.reset();
/*  796 */     if (bestData != null && bestData
/*  797 */       .getMaxDamage() > ((Float)this.module.obbyMinDmg.getValue()).floatValue()) {
/*  798 */       this.module.setTarget(bestData.getTarget());
/*  799 */       if (this.module.obbyRotate.getValue() != AutoCrystal.Rotate.None && !this.rotating && (bestData
/*      */         
/*  801 */         .getPath()).length > 0) {
/*  802 */         this.module.rotation = this.module.rotationHelper.forObby(bestData);
/*  803 */         this.rotating = true;
/*      */       } 
/*      */       
/*  806 */       Runnable r = this.module.rotationHelper.postBlock(bestData);
/*  807 */       if (!this.rotating) {
/*  808 */         r.run();
/*      */       } else {
/*  810 */         this.module.post.add(r);
/*      */       } 
/*      */       
/*  813 */       if (liquid != null) {
/*  814 */         placeAndBreakLiquid(bestData, liquid, this.rotating);
/*      */       }
/*      */       
/*  817 */       place(bestData, bestData
/*  818 */           .getTarget(), 
/*  819 */           !this.module.noRotateNigga(AutoCrystal.ACRotate.Place), (this.rotating || this.scheduling), false);
/*      */ 
/*      */ 
/*      */       
/*  823 */       this.module.obbyTimer.reset();
/*  824 */       return true;
/*      */     } 
/*      */     
/*  827 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFinished(boolean finished) {
/*  832 */     if (((Boolean)this.module.multiThread.getValue()).booleanValue() && ((Boolean)this.module.smartPost
/*  833 */       .getValue()).booleanValue() && this.module.motionID
/*  834 */       .get() != this.motionID) {
/*  835 */       this.module.runPost();
/*      */     }
/*      */     
/*  838 */     super.setFinished(finished);
/*  839 */     if (finished) {
/*  840 */       synchronized (this.module) {
/*  841 */         this.module.notifyAll();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean placeCheckPre(BlockPos pos) {
/*  847 */     double x = Thunderhack.positionManager.getX();
/*  848 */     double y = Thunderhack.positionManager.getY() + (((Boolean)this.module.placeRangeEyes.getValue()).booleanValue() ? RotationUtil.getRotationPlayer().func_70047_e() : 0.0F);
/*  849 */     double z = Thunderhack.positionManager.getZ();
/*      */     
/*  851 */     if ((((Boolean)this.module.placeRangeCenter.getValue()).booleanValue() ? pos
/*  852 */       .func_177957_d(x, y, z) : pos
/*  853 */       .func_177954_c(x, y, z)) >= MathUtil.square(((Float)this.module.placeRange.getValue()).floatValue())) {
/*  854 */       return false;
/*      */     }
/*      */     
/*  857 */     if (!this.module.noRotateNigga(AutoCrystal.ACRotate.Place) && !this.module.isNotCheckingRotations()) {
/*  858 */       RayTraceResult result = CalculationMotion.rayTraceTo(pos, (IBlockAccess)Util.mc.field_71441_e);
/*  859 */       if (result == null || !result.func_178782_a().equals(pos)) {
/*  860 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  864 */     if (pos.func_177957_d(x, y, z) >= 
/*  865 */       MathUtil.square(((Float)this.module.placeTrace.getValue()).floatValue())) {
/*  866 */       RayTraceResult result; if (((Boolean)this.module.rayTraceBypass.getValue()).booleanValue() && 
/*  867 */         !Visible.INSTANCE.check(pos, ((Integer)this.module.bypassTicks.getValue()).intValue())) {
/*  868 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  872 */       if (this.module.isNotCheckingRotations()) {
/*  873 */         float[] rotations = RotationUtil.getRotationsToTopMiddle(pos);
/*  874 */         result = RotationUtil.rayTraceWithYP(pos, (IBlockAccess)Util.mc.field_71441_e, rotations[0], rotations[1], (b, p) -> true);
/*      */       } else {
/*  876 */         result = CalculationMotion.rayTraceTo(pos, (IBlockAccess)Util.mc.field_71441_e, (b, p) -> true);
/*      */       } 
/*      */       
/*  879 */       if (result != null && !result.func_178782_a().equals(pos))
/*      */       {
/*      */         
/*  882 */         return (((Boolean)this.module.ignoreNonFull.getValue()).booleanValue() && 
/*      */ 
/*      */           
/*  885 */           !Util.mc.field_71441_e.func_180495_p(result.func_178782_a()).func_177230_c().func_149730_j(Util.mc.field_71441_e.func_180495_p(result
/*  886 */               .func_178782_a())));
/*      */       }
/*      */       
/*  889 */       return (result != null && result.func_178782_a().equals(pos));
/*      */     } 
/*      */     
/*  892 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected MutableWrapper<Boolean> placeAndBreakLiquid(PositionData data, MineSlots liquid, boolean rotating) {
/*      */     int[] order;
/*  898 */     boolean newVer = ((Boolean)this.module.newVer.getValue()).booleanValue();
/*  899 */     boolean absorb = ((Boolean)this.module.absorb.getValue()).booleanValue();
/*  900 */     List<Ray> path = new ArrayList<>((newVer ? 1 : 2) + (absorb ? 1 : 0));
/*  901 */     BlockStateHelper access = new BlockStateHelper();
/*  902 */     path.add(RayTraceFactory.rayTrace(data.getFrom(), data
/*  903 */           .getPos(), EnumFacing.UP, access, Blocks.field_150424_aL
/*      */ 
/*      */           
/*  906 */           .func_176223_P(), 
/*  907 */           ((Boolean)this.module.liquidRayTrace.getValue()).booleanValue() ? -1.0D : 2.0D));
/*      */ 
/*      */ 
/*      */     
/*  911 */     BlockPos up = data.getPos().func_177984_a();
/*  912 */     access.addBlockState(up, Blocks.field_150424_aL.func_176223_P());
/*  913 */     IBlockState upState = Util.mc.field_71441_e.func_180495_p(up);
/*  914 */     if (!newVer && upState.func_185904_a().func_76224_d()) {
/*  915 */       path.add(RayTraceFactory.rayTrace(data.getFrom(), up, EnumFacing.UP, access, Blocks.field_150424_aL
/*      */ 
/*      */ 
/*      */             
/*  919 */             .func_176223_P(), 
/*  920 */             ((Boolean)this.module.liquidRayTrace.getValue()).booleanValue() ? -1.0D : 2.0D));
/*      */ 
/*      */ 
/*      */       
/*  924 */       access.addBlockState(up.func_177984_a(), Blocks.field_150424_aL.func_176223_P());
/*  925 */       order = new int[] { 0, 1 };
/*      */     } else {
/*  927 */       order = new int[] { 0 };
/*      */     } 
/*      */     
/*  930 */     if (absorb) {
/*  931 */       BlockPos absorpPos = up;
/*  932 */       EnumFacing absorbFacing = this.module.liquidHelper.getAbsorbFacing(absorpPos, this.entities, access, ((Float)this.module.placeRange
/*  933 */           .getValue()).floatValue());
/*  934 */       if (absorbFacing == null && !newVer) {
/*  935 */         absorpPos = up.func_177984_a();
/*  936 */         absorbFacing = this.module.liquidHelper.getAbsorbFacing(absorpPos, this.entities, access, ((Float)this.module.placeRange
/*  937 */             .getValue()).floatValue());
/*      */       } 
/*      */       
/*  940 */       if (absorbFacing != null) {
/*  941 */         path.add(RayTraceFactory.rayTrace(data.getFrom(), absorpPos, absorbFacing, access, Blocks.field_150424_aL
/*      */ 
/*      */ 
/*      */               
/*  945 */               .func_176223_P(), 
/*  946 */               ((Boolean)this.module.liquidRayTrace.getValue()).booleanValue() ? -1.0D : 2.0D));
/*      */ 
/*      */         
/*  949 */         (new int[3])[0] = 2; (new int[3])[1] = 1; (new int[3])[2] = 0; (new int[2])[0] = 1; (new int[2])[1] = 0; order = (order.length == 2) ? new int[3] : new int[2];
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  955 */     Ray[] pathArray = path.<Ray>toArray(new Ray[0]);
/*  956 */     data.setPath(pathArray);
/*  957 */     data.setValid(true);
/*  958 */     MutableWrapper<Boolean> placed = new MutableWrapper<>(Boolean.valueOf(false));
/*  959 */     MutableWrapper<Integer> postBlock = new MutableWrapper<>(Integer.valueOf(-1));
/*  960 */     Runnable r = this.module.rotationHelper.postBlock(data, liquid
/*  961 */         .getBlockSlot(), (AutoCrystal.Rotate)this.module.liqRotate.getValue(), placed, postBlock);
/*      */     
/*  963 */     Runnable b = this.module.rotationHelper.breakBlock(liquid
/*  964 */         .getToolSlot(), placed, postBlock, order, pathArray);
/*  965 */     Runnable a = null;
/*  966 */     if (((Boolean)this.module.setAir.getValue()).booleanValue()) {
/*  967 */       a = (() -> {
/*      */           for (Ray ray : path) {
/*      */             Util.mc.field_71441_e.func_175656_a(ray.getPos().func_177972_a(ray.getFacing()), Blocks.field_150350_a.func_176223_P());
/*      */           }
/*      */         });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  976 */     if (rotating) {
/*  977 */       synchronized (this.module.post) {
/*  978 */         this.module.post.add(r);
/*  979 */         this.module.post.add(b);
/*  980 */         if (a != null) {
/*  981 */           Util.mc.func_152344_a(a);
/*      */         }
/*      */       } 
/*      */     } else {
/*  985 */       r.run();
/*  986 */       b.run();
/*  987 */       if (a != null) {
/*  988 */         Util.mc.func_152344_a(a);
/*      */       }
/*      */     } 
/*      */     
/*  992 */     return placed;
/*      */   }
/*      */   
/*      */   protected boolean focusBreak() {
/*  996 */     Entity focus = this.module.focus;
/*  997 */     if (focus != null) {
/*  998 */       CrystalData crystalData; if (EntityUtil.isDead(focus) || (
/*  999 */         !this.module.rangeHelper.isCrystalInRangeOfLastPosition(focus) && 
/* 1000 */         !this.module.rangeHelper.isCrystalInRange(focus))) {
/* 1001 */         this.module.focus = null;
/* 1002 */         return false;
/*      */       } 
/* 1004 */       double exponent = ((Double)this.module.focusExponent.getValue()).doubleValue();
/* 1005 */       this.breakData = getBreakHelper().getData((((Boolean)this.module.focusAngleCalc.getValue()).booleanValue() && exponent != 0.0D) ? RotationComparator.<T>asSet(exponent, ((Double)this.module.focusDiff.getValue()).doubleValue()) : getBreakDataSet(), this.entities, this.all, this.friends);
/*      */       
/* 1007 */       List<T> focusList = new ArrayList<>(1);
/*      */       
/* 1009 */       BreakData<T> focusData = getBreakHelper().newData(focusList);
/*      */       
/* 1011 */       T minData = null;
/* 1012 */       double minAngle = Double.MAX_VALUE;
/* 1013 */       for (CrystalData crystalData1 : this.breakData.getData()) {
/* 1014 */         if (EntityUtil.isDead(crystalData1.getCrystal())) {
/*      */           continue;
/*      */         }
/*      */         
/* 1018 */         if (crystalData1.hasCachedRotations() && crystalData1.getAngle() < minAngle) {
/* 1019 */           minAngle = crystalData1.getAngle();
/* 1020 */           crystalData = crystalData1;
/*      */         } 
/*      */         
/* 1023 */         if (!crystalData1.getCrystal().equals(focus)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1027 */         if (crystalData1.getSelfDmg() > ((Float)this.module.maxSelfBreak
/* 1028 */           .getValue()).floatValue() || crystalData1
/* 1029 */           .getDamage() < ((Float)this.module.minBreakDamage
/* 1030 */           .getValue()).floatValue()) {
/* 1031 */           return false;
/*      */         }
/*      */         
/* 1034 */         focusData.getData().add(crystalData1);
/*      */       } 
/*      */       
/* 1037 */       Optional<T> first = focusData.getData().stream().filter(d -> !EntityUtil.isDead(d.getCrystal())).findFirst();
/* 1038 */       if (!first.isPresent()) {
/* 1039 */         this.module.focus = null;
/* 1040 */         return false;
/*      */       } 
/*      */       
/* 1043 */       if (((Boolean)this.module.focusAngleCalc.getValue()).booleanValue() && crystalData != null && 
/*      */         
/* 1045 */         !crystalData.equals(first.get())) {
/* 1046 */         focusList.set(0, (T)crystalData);
/*      */       }
/*      */       
/* 1049 */       evaluate(focusData);
/* 1050 */       return (this.rotating || this.attacking);
/*      */     } 
/*      */ 
/*      */     
/* 1054 */     return false;
/*      */   }
/*      */   
/*      */   protected Set<T> getBreakDataSet() {
/* 1058 */     double exponent = ((Double)this.module.rotationExponent.getValue()).doubleValue();
/* 1059 */     if (Double.compare(exponent, 0.0D) == 0 || this.module
/* 1060 */       .noRotateNigga(AutoCrystal.ACRotate.Break)) {
/* 1061 */       return new TreeSet<>();
/*      */     }
/*      */     
/* 1064 */     return RotationComparator.asSet(exponent, ((Double)this.module.minRotDiff.getValue()).doubleValue());
/*      */   }
/*      */   
/*      */   protected abstract IBreakHelper<T> getBreakHelper();
/*      */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\AbstractCalculation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */