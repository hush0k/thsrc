/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.mixin.ducks.IEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.RotationUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.BiPredicate;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CalculationMotion
/*     */   extends AbstractCalculation<CrystalDataMotion>
/*     */ {
/*     */   public CalculationMotion(AutoCrystal module, List<Entity> entities, List<EntityPlayer> players) {
/*  29 */     super(module, entities, players, new BlockPos[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isLegit(Entity entity, Entity... additional) {
/*  34 */     RayTraceResult result = RayTracer.rayTraceEntities((World)Util.mc.field_71441_e, (Entity)Util.mc.field_71439_g, Util.mc.field_71439_g
/*     */         
/*  36 */         .func_70032_d(entity) + 1.0D, Thunderhack.positionManager, Thunderhack.rotationManager, e -> 
/*     */ 
/*     */ 
/*     */         
/*  40 */         (e != null && e.equals(entity)), additional);
/*     */     
/*  42 */     return (result != null && result.field_72308_g != null && (entity
/*     */       
/*  44 */       .equals(result.field_72308_g) || (additional != null && additional.length != 0 && 
/*     */ 
/*     */       
/*  47 */       Arrays.<Entity>stream(additional)
/*  48 */       .anyMatch(e -> result.field_72308_g.equals(e)))));
/*     */   }
/*     */   
/*     */   public static boolean isLegit(BlockPos pos) {
/*  52 */     return isLegit(pos, (EnumFacing)null);
/*     */   }
/*     */   
/*     */   public static boolean isLegit(BlockPos pos, EnumFacing facing) {
/*  56 */     return isLegit(pos, facing, (IBlockAccess)Util.mc.field_71441_e);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLegit(BlockPos pos, EnumFacing facing, IBlockAccess world) {
/*  62 */     RayTraceResult ray = rayTraceTo(pos, world);
/*     */     
/*  64 */     return (ray != null && ray
/*  65 */       .func_178782_a() != null && ray
/*  66 */       .func_178782_a().equals(pos) && (facing == null || ray.field_178784_b == facing));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTraceTo(BlockPos pos, IBlockAccess world) {
/*  72 */     return rayTraceTo(pos, world, (b, p) -> p.equals(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTraceTo(BlockPos pos, IBlockAccess world, BiPredicate<Block, BlockPos> check) {
/*  78 */     return rayTraceWithYP(pos, world, Thunderhack.rotationManager
/*  79 */         .getServerYaw(), Thunderhack.rotationManager
/*  80 */         .getServerPitch(), check);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult rayTraceWithYP(BlockPos pos, IBlockAccess world, float yaw, float pitch, BiPredicate<Block, BlockPos> check) {
/*  87 */     EntityPlayerSP entityPlayerSP = Util.mc.field_71439_g;
/*  88 */     Vec3d start = Thunderhack.positionManager.getVec().func_72441_c(0.0D, entityPlayerSP.func_70047_e(), 0.0D);
/*  89 */     Vec3d look = RotationUtil.getVec3d(yaw, pitch);
/*  90 */     double d = entityPlayerSP.func_70011_f(pos.func_177958_n() + 0.5D, pos
/*  91 */         .func_177956_o() + 0.5D, pos
/*  92 */         .func_177952_p() + 0.5D) + 1.0D;
/*     */     
/*  94 */     Vec3d end = start.func_72441_c(look.field_72450_a * d, look.field_72448_b * d, look.field_72449_c * d);
/*     */     
/*  96 */     return RayTracer.trace((World)Util.mc.field_71441_e, world, start, end, true, false, true, check);
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
/*     */   protected IBreakHelper<CrystalDataMotion> getBreakHelper() {
/* 108 */     return this.module.breakHelperMotion;
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
/*     */   protected boolean evaluate(BreakData<CrystalDataMotion> breakData) {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_2
/*     */     //   2: aload_0
/*     */     //   3: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*     */     //   6: invokevirtual getAntiTotem : ()Lnet/minecraft/entity/Entity;
/*     */     //   9: ifnull -> 102
/*     */     //   12: aload_0
/*     */     //   13: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   16: aload_0
/*     */     //   17: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*     */     //   20: invokevirtual getAntiTotem : ()Lnet/minecraft/entity/Entity;
/*     */     //   23: invokestatic isValid : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;Lnet/minecraft/entity/Entity;)Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   26: dup
/*     */     //   27: astore_3
/*     */     //   28: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.INVALID : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   31: if_acmpeq -> 102
/*     */     //   34: aload_0
/*     */     //   35: aload_0
/*     */     //   36: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*     */     //   39: invokevirtual getAntiTotem : ()Lnet/minecraft/entity/Entity;
/*     */     //   42: aload_3
/*     */     //   43: invokevirtual attack : (Lnet/minecraft/entity/Entity;Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;)Z
/*     */     //   46: pop
/*     */     //   47: aload_0
/*     */     //   48: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   51: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*     */     //   54: aload_0
/*     */     //   55: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   58: getfield breakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   61: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   64: checkcast java/lang/Integer
/*     */     //   67: invokevirtual intValue : ()I
/*     */     //   70: i2l
/*     */     //   71: invokeinterface reset : (J)Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*     */     //   76: pop
/*     */     //   77: aload_0
/*     */     //   78: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   81: getfield antiTotemHelper : Lcom/mrzak34/thunderhack/util/phobos/AntiTotemHelper;
/*     */     //   84: aconst_null
/*     */     //   85: invokevirtual setTarget : (Lnet/minecraft/entity/player/EntityPlayer;)V
/*     */     //   88: aload_0
/*     */     //   89: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   92: getfield antiTotemHelper : Lcom/mrzak34/thunderhack/util/phobos/AntiTotemHelper;
/*     */     //   95: aconst_null
/*     */     //   96: invokevirtual setTargetPos : (Lnet/minecraft/util/math/BlockPos;)V
/*     */     //   99: goto -> 610
/*     */     //   102: aload_0
/*     */     //   103: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   106: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate.Break : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;
/*     */     //   109: invokevirtual noRotateNigga : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;)Z
/*     */     //   112: ifne -> 119
/*     */     //   115: iconst_1
/*     */     //   116: goto -> 135
/*     */     //   119: aload_0
/*     */     //   120: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   123: getfield packets : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   126: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   129: checkcast java/lang/Integer
/*     */     //   132: invokevirtual intValue : ()I
/*     */     //   135: istore #4
/*     */     //   137: aconst_null
/*     */     //   138: astore #5
/*     */     //   140: new java/util/ArrayList
/*     */     //   143: dup
/*     */     //   144: iload #4
/*     */     //   146: invokespecial <init> : (I)V
/*     */     //   149: astore #6
/*     */     //   151: aload_0
/*     */     //   152: getfield breakData : Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*     */     //   155: invokevirtual getData : ()Ljava/util/Collection;
/*     */     //   158: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   163: astore #7
/*     */     //   165: aload #7
/*     */     //   167: invokeinterface hasNext : ()Z
/*     */     //   172: ifeq -> 299
/*     */     //   175: aload #7
/*     */     //   177: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   182: checkcast com/mrzak34/thunderhack/util/phobos/CrystalDataMotion
/*     */     //   185: astore #8
/*     */     //   187: aload #8
/*     */     //   189: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*     */     //   192: invokestatic isDead : (Lnet/minecraft/entity/Entity;)Z
/*     */     //   195: ifeq -> 201
/*     */     //   198: goto -> 165
/*     */     //   201: aload #8
/*     */     //   203: invokevirtual getTiming : ()Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   206: getstatic com/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing.NONE : Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   209: if_acmpne -> 215
/*     */     //   212: goto -> 165
/*     */     //   215: aload_0
/*     */     //   216: aload_0
/*     */     //   217: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   220: aload #8
/*     */     //   222: invokespecial isValid : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion;)Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   225: astore_3
/*     */     //   226: aload_3
/*     */     //   227: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.VALID : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   230: if_acmpne -> 258
/*     */     //   233: aload #6
/*     */     //   235: invokeinterface size : ()I
/*     */     //   240: iload #4
/*     */     //   242: if_icmpge -> 258
/*     */     //   245: aload #6
/*     */     //   247: aload #8
/*     */     //   249: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   254: pop
/*     */     //   255: goto -> 296
/*     */     //   258: aload_3
/*     */     //   259: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.ROTATIONS : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   262: if_acmpne -> 296
/*     */     //   265: aload #8
/*     */     //   267: invokevirtual getTiming : ()Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   270: getstatic com/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing.BOTH : Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   273: if_acmpeq -> 287
/*     */     //   276: aload #8
/*     */     //   278: invokevirtual getTiming : ()Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   281: getstatic com/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing.POST : Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   284: if_acmpne -> 296
/*     */     //   287: aload #5
/*     */     //   289: ifnonnull -> 296
/*     */     //   292: aload #8
/*     */     //   294: astore #5
/*     */     //   296: goto -> 165
/*     */     //   299: aload_0
/*     */     //   300: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   303: getfield slowBreakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   306: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   309: checkcast java/lang/Integer
/*     */     //   312: invokevirtual intValue : ()I
/*     */     //   315: istore #7
/*     */     //   317: aload_0
/*     */     //   318: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   321: getfield slowBreakDamage : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   324: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   327: checkcast java/lang/Float
/*     */     //   330: invokevirtual floatValue : ()F
/*     */     //   333: fstore #8
/*     */     //   335: aload #6
/*     */     //   337: invokeinterface isEmpty : ()Z
/*     */     //   342: ifeq -> 415
/*     */     //   345: aload #5
/*     */     //   347: ifnull -> 610
/*     */     //   350: aload_0
/*     */     //   351: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   354: invokevirtual shouldDanger : ()Z
/*     */     //   357: ifne -> 399
/*     */     //   360: aload #5
/*     */     //   362: invokevirtual getDamage : ()F
/*     */     //   365: fload #8
/*     */     //   367: fcmpg
/*     */     //   368: ifgt -> 375
/*     */     //   371: iconst_1
/*     */     //   372: goto -> 376
/*     */     //   375: iconst_0
/*     */     //   376: dup
/*     */     //   377: istore_2
/*     */     //   378: ifeq -> 399
/*     */     //   381: aload_0
/*     */     //   382: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   385: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*     */     //   388: iload #7
/*     */     //   390: i2l
/*     */     //   391: invokeinterface passed : (J)Z
/*     */     //   396: ifeq -> 610
/*     */     //   399: aload_0
/*     */     //   400: aload #5
/*     */     //   402: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*     */     //   405: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.ROTATIONS : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   408: invokevirtual attack : (Lnet/minecraft/entity/Entity;Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;)Z
/*     */     //   411: pop
/*     */     //   412: goto -> 610
/*     */     //   415: aload_0
/*     */     //   416: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   419: invokevirtual shouldDanger : ()Z
/*     */     //   422: ifne -> 429
/*     */     //   425: iconst_1
/*     */     //   426: goto -> 430
/*     */     //   429: iconst_0
/*     */     //   430: istore_2
/*     */     //   431: aload #6
/*     */     //   433: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   438: astore #9
/*     */     //   440: aload #9
/*     */     //   442: invokeinterface hasNext : ()Z
/*     */     //   447: ifeq -> 610
/*     */     //   450: aload #9
/*     */     //   452: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   457: checkcast com/mrzak34/thunderhack/util/phobos/CrystalDataMotion
/*     */     //   460: astore #10
/*     */     //   462: aload #10
/*     */     //   464: invokevirtual getDamage : ()F
/*     */     //   467: aload_0
/*     */     //   468: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   471: getfield slowBreakDamage : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   474: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   477: checkcast java/lang/Float
/*     */     //   480: invokevirtual floatValue : ()F
/*     */     //   483: fcmpl
/*     */     //   484: ifle -> 491
/*     */     //   487: iconst_1
/*     */     //   488: goto -> 492
/*     */     //   491: iconst_0
/*     */     //   492: istore #11
/*     */     //   494: iload #11
/*     */     //   496: ifne -> 531
/*     */     //   499: aload_0
/*     */     //   500: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   503: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*     */     //   506: aload_0
/*     */     //   507: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   510: getfield slowBreakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   513: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   516: checkcast java/lang/Integer
/*     */     //   519: invokevirtual intValue : ()I
/*     */     //   522: i2l
/*     */     //   523: invokeinterface passed : (J)Z
/*     */     //   528: ifeq -> 607
/*     */     //   531: iload_2
/*     */     //   532: ifeq -> 544
/*     */     //   535: iload #11
/*     */     //   537: ifne -> 544
/*     */     //   540: iconst_1
/*     */     //   541: goto -> 545
/*     */     //   544: iconst_0
/*     */     //   545: istore_2
/*     */     //   546: aload #10
/*     */     //   548: invokevirtual getTiming : ()Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   551: getstatic com/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing.POST : Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   554: if_acmpeq -> 582
/*     */     //   557: aload #10
/*     */     //   559: invokevirtual getTiming : ()Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   562: getstatic com/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing.BOTH : Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion$Timing;
/*     */     //   565: if_acmpne -> 594
/*     */     //   568: aload #10
/*     */     //   570: invokevirtual getPostSelf : ()F
/*     */     //   573: aload #10
/*     */     //   575: invokevirtual getSelfDmg : ()F
/*     */     //   578: fcmpg
/*     */     //   579: ifge -> 594
/*     */     //   582: aload_0
/*     */     //   583: aload #10
/*     */     //   585: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*     */     //   588: invokevirtual attackPost : (Lnet/minecraft/entity/Entity;)V
/*     */     //   591: goto -> 607
/*     */     //   594: aload_0
/*     */     //   595: aload #10
/*     */     //   597: invokevirtual getCrystal : ()Lnet/minecraft/entity/Entity;
/*     */     //   600: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity.VALID : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   603: invokevirtual attack : (Lnet/minecraft/entity/Entity;Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;)Z
/*     */     //   606: pop
/*     */     //   607: goto -> 440
/*     */     //   610: aload_0
/*     */     //   611: getfield attacking : Z
/*     */     //   614: ifeq -> 671
/*     */     //   617: aload_0
/*     */     //   618: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   621: getfield breakTimer : Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*     */     //   624: iload_2
/*     */     //   625: ifeq -> 648
/*     */     //   628: aload_0
/*     */     //   629: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   632: getfield slowBreakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   635: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   638: checkcast java/lang/Integer
/*     */     //   641: invokevirtual intValue : ()I
/*     */     //   644: i2l
/*     */     //   645: goto -> 665
/*     */     //   648: aload_0
/*     */     //   649: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   652: getfield breakDelay : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   655: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   658: checkcast java/lang/Integer
/*     */     //   661: invokevirtual intValue : ()I
/*     */     //   664: i2l
/*     */     //   665: invokeinterface reset : (J)Lcom/mrzak34/thunderhack/util/phobos/DiscreteTimer;
/*     */     //   670: pop
/*     */     //   671: aload_0
/*     */     //   672: getfield rotating : Z
/*     */     //   675: ifeq -> 695
/*     */     //   678: aload_0
/*     */     //   679: getfield module : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal;
/*     */     //   682: getstatic com/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate.Place : Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;
/*     */     //   685: invokevirtual noRotateNigga : (Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$ACRotate;)Z
/*     */     //   688: ifne -> 695
/*     */     //   691: iconst_1
/*     */     //   692: goto -> 696
/*     */     //   695: iconst_0
/*     */     //   696: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #114	-> 0
/*     */     //   #116	-> 2
/*     */     //   #118	-> 20
/*     */     //   #120	-> 34
/*     */     //   #121	-> 47
/*     */     //   #122	-> 77
/*     */     //   #123	-> 88
/*     */     //   #125	-> 102
/*     */     //   #127	-> 126
/*     */     //   #129	-> 137
/*     */     //   #130	-> 140
/*     */     //   #131	-> 151
/*     */     //   #132	-> 187
/*     */     //   #133	-> 198
/*     */     //   #136	-> 201
/*     */     //   #137	-> 212
/*     */     //   #140	-> 215
/*     */     //   #141	-> 226
/*     */     //   #142	-> 245
/*     */     //   #143	-> 258
/*     */     //   #144	-> 267
/*     */     //   #145	-> 278
/*     */     //   #147	-> 292
/*     */     //   #149	-> 296
/*     */     //   #151	-> 299
/*     */     //   #152	-> 317
/*     */     //   #153	-> 335
/*     */     //   #154	-> 345
/*     */     //   #155	-> 354
/*     */     //   #156	-> 362
/*     */     //   #157	-> 391
/*     */     //   #158	-> 399
/*     */     //   #161	-> 415
/*     */     //   #162	-> 431
/*     */     //   #163	-> 462
/*     */     //   #164	-> 474
/*     */     //   #165	-> 494
/*     */     //   #166	-> 513
/*     */     //   #167	-> 531
/*     */     //   #168	-> 546
/*     */     //   #169	-> 559
/*     */     //   #170	-> 570
/*     */     //   #171	-> 582
/*     */     //   #173	-> 594
/*     */     //   #176	-> 607
/*     */     //   #180	-> 610
/*     */     //   #181	-> 617
/*     */     //   #182	-> 635
/*     */     //   #183	-> 655
/*     */     //   #181	-> 665
/*     */     //   #186	-> 671
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   28	74	3	validity	Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   187	109	8	data	Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion;
/*     */     //   226	73	3	validity	Lcom/mrzak34/thunderhack/modules/combat/AutoCrystal$BreakValidity;
/*     */     //   494	113	11	high	Z
/*     */     //   462	145	10	v	Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion;
/*     */     //   137	473	4	packets	I
/*     */     //   140	470	5	firstRotation	Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion;
/*     */     //   151	459	6	valids	Ljava/util/List;
/*     */     //   317	293	7	slowDelay	I
/*     */     //   335	275	8	slow	F
/*     */     //   0	697	0	this	Lcom/mrzak34/thunderhack/util/phobos/CalculationMotion;
/*     */     //   0	697	1	breakData	Lcom/mrzak34/thunderhack/util/phobos/BreakData;
/*     */     //   2	695	2	slowReset	Z
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   151	459	6	valids	Ljava/util/List<Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion;>;
/*     */     //   0	697	1	breakData	Lcom/mrzak34/thunderhack/util/phobos/BreakData<Lcom/mrzak34/thunderhack/util/phobos/CrystalDataMotion;>;
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
/*     */   protected void attackPost(Entity entity) {
/* 190 */     this.attacking = true;
/* 191 */     this.scheduling = true;
/* 192 */     this.rotating = !this.module.noRotateNigga(AutoCrystal.ACRotate.Break);
/* 193 */     MutableWrapper<Boolean> attacked = new MutableWrapper<>(Boolean.valueOf(false));
/* 194 */     Runnable post = this.module.rotationHelper.post(entity, attacked);
/* 195 */     this.module.post.add(post);
/*     */   }
/*     */ 
/*     */   
/*     */   private AutoCrystal.BreakValidity isValid(AutoCrystal module, CrystalDataMotion dataMotion) {
/* 200 */     Entity crystal = dataMotion.getCrystal();
/* 201 */     if (((Integer)module.existed.getValue()).intValue() != 0 && (
/* 202 */       System.currentTimeMillis() - ((IEntity)crystal)
/* 203 */       .getTimeStampT()) + (
/* 204 */       ((Boolean)module.pingExisted.getValue()).booleanValue() ? (Thunderhack.serverManager
/* 205 */       .getPing() / 2.0D) : 0.0D) < ((Integer)module.existed
/*     */       
/* 207 */       .getValue()).intValue()) {
/* 208 */       return AutoCrystal.BreakValidity.INVALID;
/*     */     }
/*     */     
/* 211 */     if (module.noRotateNigga(AutoCrystal.ACRotate.Break) || module
/* 212 */       .isNotCheckingRotations() || (
/* 213 */       isLegit(crystal, new Entity[] { crystal }) && AutoCrystal.POSITION_HISTORY
/*     */       
/* 215 */       .arePreviousRotationsLegit(crystal, ((Integer)module.rotationTicks
/*     */         
/* 217 */         .getValue()).intValue(), true)))
/*     */     {
/* 219 */       return AutoCrystal.BreakValidity.VALID;
/*     */     }
/*     */     
/* 222 */     return AutoCrystal.BreakValidity.ROTATIONS;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\CalculationMotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */