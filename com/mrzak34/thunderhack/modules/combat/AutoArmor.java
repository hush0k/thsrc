/*     */ package com.mrzak34.thunderhack.modules.combat;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.InvStack;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
/*     */ public class AutoArmor
/*     */   extends Module
/*     */ {
/*  37 */   private final Setting<Mode> mode = register(new Setting("Mode", Mode.FunnyGame));
/*  38 */   private final Setting<Boolean> armorSaver = register(new Setting("ArmorSaver", Boolean.valueOf(false)));
/*  39 */   public Setting<Float> depletion = register(new Setting("Depletion", Float.valueOf(0.75F), Float.valueOf(0.5F), Float.valueOf(0.95F), v -> ((Boolean)this.armorSaver.getValue()).booleanValue()));
/*  40 */   private final Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10)));
/*  41 */   private final Setting<Boolean> elytraPrio = register(new Setting("ElytraPrio", Boolean.valueOf(false)));
/*  42 */   private final Setting<Boolean> smart = register(new Setting("Smart", Boolean.valueOf(false), v -> ((Boolean)this.elytraPrio.getValue()).booleanValue()));
/*  43 */   private final Setting<Boolean> strict = register(new Setting("Strict", Boolean.valueOf(false)));
/*  44 */   private final Setting<Boolean> pauseWhenSafe = register(new Setting("PauseWhenSafe", Boolean.valueOf(false)));
/*  45 */   private final Setting<Boolean> allowMend = register(new Setting("AllowMend", Boolean.valueOf(false)));
/*  46 */   private final Timer rightClickTimer = new Timer();
/*     */   
/*     */   private boolean sleep;
/*     */   
/*     */   public AutoArmor() {
/*  51 */     super("AutoArmor", "Автоброня", "puts on best armor", Module.Category.PLAYER);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send event) {
/*  56 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketClickWindow) {
/*  57 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
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
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield mode : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   4: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   7: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor$Mode.Default : Lcom/mrzak34/thunderhack/modules/combat/AutoArmor$Mode;
/*     */     //   10: if_acmpne -> 1481
/*     */     //   13: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   16: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
/*     */     //   19: ifnull -> 31
/*     */     //   22: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   25: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   28: ifnonnull -> 32
/*     */     //   31: return
/*     */     //   32: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   35: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   38: getfield field_70173_aa : I
/*     */     //   41: aload_0
/*     */     //   42: getfield delay : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   45: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   48: checkcast java/lang/Integer
/*     */     //   51: invokevirtual intValue : ()I
/*     */     //   54: irem
/*     */     //   55: ifeq -> 59
/*     */     //   58: return
/*     */     //   59: aload_0
/*     */     //   60: getfield strict : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   63: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   66: checkcast java/lang/Boolean
/*     */     //   69: invokevirtual booleanValue : ()Z
/*     */     //   72: ifeq -> 104
/*     */     //   75: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   78: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   81: getfield field_70159_w : D
/*     */     //   84: dconst_0
/*     */     //   85: dcmpl
/*     */     //   86: ifne -> 103
/*     */     //   89: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   92: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   95: getfield field_70179_y : D
/*     */     //   98: dconst_0
/*     */     //   99: dcmpl
/*     */     //   100: ifeq -> 104
/*     */     //   103: return
/*     */     //   104: aload_0
/*     */     //   105: getfield pauseWhenSafe : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   108: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   111: checkcast java/lang/Boolean
/*     */     //   114: invokevirtual booleanValue : ()Z
/*     */     //   117: ifeq -> 166
/*     */     //   120: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   123: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
/*     */     //   126: getfield field_72996_f : Ljava/util/List;
/*     */     //   129: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   134: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   139: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   144: invokestatic toList : ()Ljava/util/stream/Collector;
/*     */     //   147: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
/*     */     //   152: checkcast java/util/List
/*     */     //   155: astore_1
/*     */     //   156: aload_1
/*     */     //   157: invokeinterface isEmpty : ()Z
/*     */     //   162: ifeq -> 166
/*     */     //   165: return
/*     */     //   166: getstatic com/mrzak34/thunderhack/modules/player/AutoMend.isMending : Z
/*     */     //   169: ifeq -> 173
/*     */     //   172: return
/*     */     //   173: aload_0
/*     */     //   174: getfield allowMend : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   177: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   180: checkcast java/lang/Boolean
/*     */     //   183: invokevirtual booleanValue : ()Z
/*     */     //   186: ifeq -> 408
/*     */     //   189: aload_0
/*     */     //   190: getfield rightClickTimer : Lcom/mrzak34/thunderhack/util/Timer;
/*     */     //   193: ldc2_w 500
/*     */     //   196: invokevirtual passedMs : (J)Z
/*     */     //   199: ifne -> 408
/*     */     //   202: iconst_0
/*     */     //   203: istore_1
/*     */     //   204: iload_1
/*     */     //   205: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   208: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   211: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   214: getfield field_70460_b : Lnet/minecraft/util/NonNullList;
/*     */     //   217: invokevirtual size : ()I
/*     */     //   220: if_icmpge -> 407
/*     */     //   223: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   226: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   229: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   232: getfield field_70460_b : Lnet/minecraft/util/NonNullList;
/*     */     //   235: iload_1
/*     */     //   236: invokevirtual get : (I)Ljava/lang/Object;
/*     */     //   239: checkcast net/minecraft/item/ItemStack
/*     */     //   242: astore_2
/*     */     //   243: aload_2
/*     */     //   244: invokevirtual func_77986_q : ()Lnet/minecraft/nbt/NBTTagList;
/*     */     //   247: ifnull -> 327
/*     */     //   250: iconst_0
/*     */     //   251: istore_3
/*     */     //   252: aload_2
/*     */     //   253: invokestatic func_82781_a : (Lnet/minecraft/item/ItemStack;)Ljava/util/Map;
/*     */     //   256: invokeinterface entrySet : ()Ljava/util/Set;
/*     */     //   261: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   266: astore #4
/*     */     //   268: aload #4
/*     */     //   270: invokeinterface hasNext : ()Z
/*     */     //   275: ifeq -> 320
/*     */     //   278: aload #4
/*     */     //   280: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   285: checkcast java/util/Map$Entry
/*     */     //   288: astore #5
/*     */     //   290: aload #5
/*     */     //   292: invokeinterface getKey : ()Ljava/lang/Object;
/*     */     //   297: checkcast net/minecraft/enchantment/Enchantment
/*     */     //   300: invokevirtual func_77320_a : ()Ljava/lang/String;
/*     */     //   303: ldc_w 'mending'
/*     */     //   306: invokevirtual contains : (Ljava/lang/CharSequence;)Z
/*     */     //   309: ifeq -> 317
/*     */     //   312: iconst_1
/*     */     //   313: istore_3
/*     */     //   314: goto -> 320
/*     */     //   317: goto -> 268
/*     */     //   320: iload_3
/*     */     //   321: ifne -> 327
/*     */     //   324: goto -> 401
/*     */     //   327: aload_2
/*     */     //   328: invokevirtual func_190926_b : ()Z
/*     */     //   331: ifeq -> 337
/*     */     //   334: goto -> 401
/*     */     //   337: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   340: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   343: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   346: getfield field_70462_a : Lnet/minecraft/util/NonNullList;
/*     */     //   349: invokevirtual stream : ()Ljava/util/stream/Stream;
/*     */     //   352: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   357: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   362: <illegal opcode> apply : ()Ljava/util/function/Function;
/*     */     //   367: invokeinterface map : (Ljava/util/function/Function;)Ljava/util/stream/Stream;
/*     */     //   372: invokeinterface count : ()J
/*     */     //   377: lstore_3
/*     */     //   378: lload_3
/*     */     //   379: lconst_0
/*     */     //   380: lcmp
/*     */     //   381: ifgt -> 385
/*     */     //   384: return
/*     */     //   385: aload_2
/*     */     //   386: invokevirtual func_77952_i : ()I
/*     */     //   389: ifeq -> 401
/*     */     //   392: aload_0
/*     */     //   393: bipush #8
/*     */     //   395: iload_1
/*     */     //   396: isub
/*     */     //   397: invokespecial shiftClickSpot : (I)V
/*     */     //   400: return
/*     */     //   401: iinc #1, 1
/*     */     //   404: goto -> 204
/*     */     //   407: return
/*     */     //   408: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   411: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*     */     //   414: instanceof net/minecraft/client/gui/inventory/GuiContainer
/*     */     //   417: ifeq -> 421
/*     */     //   420: return
/*     */     //   421: new java/util/concurrent/atomic/AtomicBoolean
/*     */     //   424: dup
/*     */     //   425: iconst_0
/*     */     //   426: invokespecial <init> : (Z)V
/*     */     //   429: astore_1
/*     */     //   430: aload_0
/*     */     //   431: getfield sleep : Z
/*     */     //   434: ifeq -> 443
/*     */     //   437: aload_0
/*     */     //   438: iconst_0
/*     */     //   439: putfield sleep : Z
/*     */     //   442: return
/*     */     //   443: aload_0
/*     */     //   444: getfield elytraPrio : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   447: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   450: checkcast java/lang/Boolean
/*     */     //   453: invokevirtual booleanValue : ()Z
/*     */     //   456: istore_2
/*     */     //   457: aload_0
/*     */     //   458: getfield smart : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   461: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   464: checkcast java/lang/Boolean
/*     */     //   467: invokevirtual booleanValue : ()Z
/*     */     //   470: ifeq -> 493
/*     */     //   473: getstatic com/mrzak34/thunderhack/Thunderhack.moduleManager : Lcom/mrzak34/thunderhack/manager/ModuleManager;
/*     */     //   476: ldc_w com/mrzak34/thunderhack/modules/movement/ElytraFlight
/*     */     //   479: invokevirtual getModuleByClass : (Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
/*     */     //   482: checkcast com/mrzak34/thunderhack/modules/movement/ElytraFlight
/*     */     //   485: invokevirtual isOn : ()Z
/*     */     //   488: ifne -> 493
/*     */     //   491: iconst_0
/*     */     //   492: istore_2
/*     */     //   493: new java/util/HashSet
/*     */     //   496: dup
/*     */     //   497: invokespecial <init> : ()V
/*     */     //   500: astore_3
/*     */     //   501: iconst_0
/*     */     //   502: istore #4
/*     */     //   504: iload #4
/*     */     //   506: bipush #36
/*     */     //   508: if_icmpge -> 579
/*     */     //   511: new com/mrzak34/thunderhack/util/InvStack
/*     */     //   514: dup
/*     */     //   515: iload #4
/*     */     //   517: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   520: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   523: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   526: iload #4
/*     */     //   528: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   531: invokespecial <init> : (ILnet/minecraft/item/ItemStack;)V
/*     */     //   534: astore #5
/*     */     //   536: aload #5
/*     */     //   538: getfield stack : Lnet/minecraft/item/ItemStack;
/*     */     //   541: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   544: instanceof net/minecraft/item/ItemArmor
/*     */     //   547: ifne -> 564
/*     */     //   550: aload #5
/*     */     //   552: getfield stack : Lnet/minecraft/item/ItemStack;
/*     */     //   555: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   558: instanceof net/minecraft/item/ItemElytra
/*     */     //   561: ifeq -> 573
/*     */     //   564: aload_3
/*     */     //   565: aload #5
/*     */     //   567: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   572: pop
/*     */     //   573: iinc #4, 1
/*     */     //   576: goto -> 504
/*     */     //   579: aload_3
/*     */     //   580: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   585: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   590: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   595: aload_0
/*     */     //   596: <illegal opcode> test : (Lcom/mrzak34/thunderhack/modules/combat/AutoArmor;)Ljava/util/function/Predicate;
/*     */     //   601: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   606: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
/*     */     //   611: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
/*     */     //   614: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   619: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
/*     */     //   624: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
/*     */     //   627: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   632: invokestatic toList : ()Ljava/util/stream/Collector;
/*     */     //   635: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
/*     */     //   640: checkcast java/util/List
/*     */     //   643: astore #4
/*     */     //   645: aload #4
/*     */     //   647: invokeinterface isEmpty : ()Z
/*     */     //   652: istore #5
/*     */     //   654: iload #5
/*     */     //   656: ifeq -> 714
/*     */     //   659: aload_3
/*     */     //   660: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   665: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   670: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   675: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
/*     */     //   680: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
/*     */     //   683: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   688: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
/*     */     //   693: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
/*     */     //   696: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   701: invokestatic toList : ()Ljava/util/stream/Collector;
/*     */     //   704: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
/*     */     //   709: checkcast java/util/List
/*     */     //   712: astore #4
/*     */     //   714: aload_3
/*     */     //   715: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   720: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   725: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   730: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
/*     */     //   735: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
/*     */     //   738: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
/*     */     //   743: invokestatic toList : ()Ljava/util/stream/Collector;
/*     */     //   746: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
/*     */     //   751: checkcast java/util/List
/*     */     //   754: astore #6
/*     */     //   756: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   759: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   762: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   765: bipush #39
/*     */     //   767: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   770: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   773: astore #7
/*     */     //   775: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   778: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   781: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   784: bipush #38
/*     */     //   786: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   789: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   792: astore #8
/*     */     //   794: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   797: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   800: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   803: bipush #37
/*     */     //   805: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   808: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   811: astore #9
/*     */     //   813: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   816: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   819: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   822: bipush #36
/*     */     //   824: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   827: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   830: astore #10
/*     */     //   832: aload #7
/*     */     //   834: getstatic net/minecraft/init/Items.field_190931_a : Lnet/minecraft/item/Item;
/*     */     //   837: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   840: ifne -> 916
/*     */     //   843: iload #5
/*     */     //   845: ifne -> 920
/*     */     //   848: aload_0
/*     */     //   849: getfield armorSaver : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   852: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   855: checkcast java/lang/Boolean
/*     */     //   858: invokevirtual booleanValue : ()Z
/*     */     //   861: ifeq -> 920
/*     */     //   864: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   867: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   870: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   873: bipush #39
/*     */     //   875: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   878: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   881: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   884: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   887: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   890: bipush #39
/*     */     //   892: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   895: invokevirtual getDurabilityForDisplay : (Lnet/minecraft/item/ItemStack;)D
/*     */     //   898: aload_0
/*     */     //   899: getfield depletion : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   902: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   905: checkcast java/lang/Float
/*     */     //   908: invokevirtual floatValue : ()F
/*     */     //   911: f2d
/*     */     //   912: dcmpl
/*     */     //   913: iflt -> 920
/*     */     //   916: iconst_1
/*     */     //   917: goto -> 921
/*     */     //   920: iconst_0
/*     */     //   921: istore #11
/*     */     //   923: aload #8
/*     */     //   925: getstatic net/minecraft/init/Items.field_190931_a : Lnet/minecraft/item/Item;
/*     */     //   928: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   931: ifne -> 1007
/*     */     //   934: iload #5
/*     */     //   936: ifne -> 1011
/*     */     //   939: aload_0
/*     */     //   940: getfield armorSaver : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   943: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   946: checkcast java/lang/Boolean
/*     */     //   949: invokevirtual booleanValue : ()Z
/*     */     //   952: ifeq -> 1011
/*     */     //   955: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   958: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   961: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   964: bipush #38
/*     */     //   966: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   969: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   972: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   975: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   978: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   981: bipush #38
/*     */     //   983: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   986: invokevirtual getDurabilityForDisplay : (Lnet/minecraft/item/ItemStack;)D
/*     */     //   989: aload_0
/*     */     //   990: getfield depletion : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   993: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   996: checkcast java/lang/Float
/*     */     //   999: invokevirtual floatValue : ()F
/*     */     //   1002: f2d
/*     */     //   1003: dcmpl
/*     */     //   1004: iflt -> 1011
/*     */     //   1007: iconst_1
/*     */     //   1008: goto -> 1012
/*     */     //   1011: iconst_0
/*     */     //   1012: istore #12
/*     */     //   1014: aload #9
/*     */     //   1016: getstatic net/minecraft/init/Items.field_190931_a : Lnet/minecraft/item/Item;
/*     */     //   1019: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   1022: ifne -> 1098
/*     */     //   1025: iload #5
/*     */     //   1027: ifne -> 1102
/*     */     //   1030: aload_0
/*     */     //   1031: getfield armorSaver : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   1034: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   1037: checkcast java/lang/Boolean
/*     */     //   1040: invokevirtual booleanValue : ()Z
/*     */     //   1043: ifeq -> 1102
/*     */     //   1046: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1049: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1052: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1055: bipush #37
/*     */     //   1057: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1060: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   1063: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1066: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1069: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1072: bipush #37
/*     */     //   1074: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1077: invokevirtual getDurabilityForDisplay : (Lnet/minecraft/item/ItemStack;)D
/*     */     //   1080: aload_0
/*     */     //   1081: getfield depletion : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   1084: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   1087: checkcast java/lang/Float
/*     */     //   1090: invokevirtual floatValue : ()F
/*     */     //   1093: f2d
/*     */     //   1094: dcmpl
/*     */     //   1095: iflt -> 1102
/*     */     //   1098: iconst_1
/*     */     //   1099: goto -> 1103
/*     */     //   1102: iconst_0
/*     */     //   1103: istore #13
/*     */     //   1105: aload #10
/*     */     //   1107: getstatic net/minecraft/init/Items.field_190931_a : Lnet/minecraft/item/Item;
/*     */     //   1110: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   1113: ifne -> 1189
/*     */     //   1116: iload #5
/*     */     //   1118: ifne -> 1193
/*     */     //   1121: aload_0
/*     */     //   1122: getfield armorSaver : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   1125: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   1128: checkcast java/lang/Boolean
/*     */     //   1131: invokevirtual booleanValue : ()Z
/*     */     //   1134: ifeq -> 1193
/*     */     //   1137: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1140: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1143: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1146: bipush #36
/*     */     //   1148: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1151: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   1154: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1157: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1160: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1163: bipush #36
/*     */     //   1165: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1168: invokevirtual getDurabilityForDisplay : (Lnet/minecraft/item/ItemStack;)D
/*     */     //   1171: aload_0
/*     */     //   1172: getfield depletion : Lcom/mrzak34/thunderhack/setting/Setting;
/*     */     //   1175: invokevirtual getValue : ()Ljava/lang/Object;
/*     */     //   1178: checkcast java/lang/Float
/*     */     //   1181: invokevirtual floatValue : ()F
/*     */     //   1184: f2d
/*     */     //   1185: dcmpl
/*     */     //   1186: iflt -> 1193
/*     */     //   1189: iconst_1
/*     */     //   1190: goto -> 1194
/*     */     //   1193: iconst_0
/*     */     //   1194: istore #14
/*     */     //   1196: iload #11
/*     */     //   1198: ifeq -> 1250
/*     */     //   1201: aload_1
/*     */     //   1202: invokevirtual get : ()Z
/*     */     //   1205: ifne -> 1250
/*     */     //   1208: aload #4
/*     */     //   1210: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   1215: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1220: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1225: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1230: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1235: invokeinterface findFirst : ()Ljava/util/Optional;
/*     */     //   1240: aload_0
/*     */     //   1241: aload_1
/*     */     //   1242: <illegal opcode> accept : (Lcom/mrzak34/thunderhack/modules/combat/AutoArmor;Ljava/util/concurrent/atomic/AtomicBoolean;)Ljava/util/function/Consumer;
/*     */     //   1247: invokevirtual ifPresent : (Ljava/util/function/Consumer;)V
/*     */     //   1250: iload_2
/*     */     //   1251: ifeq -> 1301
/*     */     //   1254: aload #8
/*     */     //   1256: instanceof net/minecraft/item/ItemElytra
/*     */     //   1259: ifne -> 1301
/*     */     //   1262: aload #6
/*     */     //   1264: invokeinterface size : ()I
/*     */     //   1269: ifle -> 1301
/*     */     //   1272: aload_1
/*     */     //   1273: invokevirtual get : ()Z
/*     */     //   1276: ifne -> 1301
/*     */     //   1279: aload #6
/*     */     //   1281: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   1286: invokeinterface findFirst : ()Ljava/util/Optional;
/*     */     //   1291: aload_0
/*     */     //   1292: aload_1
/*     */     //   1293: <illegal opcode> accept : (Lcom/mrzak34/thunderhack/modules/combat/AutoArmor;Ljava/util/concurrent/atomic/AtomicBoolean;)Ljava/util/function/Consumer;
/*     */     //   1298: invokevirtual ifPresent : (Ljava/util/function/Consumer;)V
/*     */     //   1301: iload #12
/*     */     //   1303: ifne -> 1328
/*     */     //   1306: iload_2
/*     */     //   1307: ifne -> 1370
/*     */     //   1310: aload #8
/*     */     //   1312: getstatic net/minecraft/init/Items.field_185160_cR : Lnet/minecraft/item/Item;
/*     */     //   1315: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   1318: ifeq -> 1370
/*     */     //   1321: aload_1
/*     */     //   1322: invokevirtual get : ()Z
/*     */     //   1325: ifne -> 1370
/*     */     //   1328: aload #4
/*     */     //   1330: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   1335: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1340: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1345: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1350: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1355: invokeinterface findFirst : ()Ljava/util/Optional;
/*     */     //   1360: aload_0
/*     */     //   1361: aload_1
/*     */     //   1362: <illegal opcode> accept : (Lcom/mrzak34/thunderhack/modules/combat/AutoArmor;Ljava/util/concurrent/atomic/AtomicBoolean;)Ljava/util/function/Consumer;
/*     */     //   1367: invokevirtual ifPresent : (Ljava/util/function/Consumer;)V
/*     */     //   1370: iload #13
/*     */     //   1372: ifeq -> 1424
/*     */     //   1375: aload_1
/*     */     //   1376: invokevirtual get : ()Z
/*     */     //   1379: ifne -> 1424
/*     */     //   1382: aload #4
/*     */     //   1384: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   1389: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1394: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1399: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1404: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1409: invokeinterface findFirst : ()Ljava/util/Optional;
/*     */     //   1414: aload_0
/*     */     //   1415: aload_1
/*     */     //   1416: <illegal opcode> accept : (Lcom/mrzak34/thunderhack/modules/combat/AutoArmor;Ljava/util/concurrent/atomic/AtomicBoolean;)Ljava/util/function/Consumer;
/*     */     //   1421: invokevirtual ifPresent : (Ljava/util/function/Consumer;)V
/*     */     //   1424: iload #14
/*     */     //   1426: ifeq -> 1478
/*     */     //   1429: aload_1
/*     */     //   1430: invokevirtual get : ()Z
/*     */     //   1433: ifne -> 1478
/*     */     //   1436: aload #4
/*     */     //   1438: invokeinterface stream : ()Ljava/util/stream/Stream;
/*     */     //   1443: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1448: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1453: <illegal opcode> test : ()Ljava/util/function/Predicate;
/*     */     //   1458: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
/*     */     //   1463: invokeinterface findFirst : ()Ljava/util/Optional;
/*     */     //   1468: aload_0
/*     */     //   1469: aload_1
/*     */     //   1470: <illegal opcode> accept : (Lcom/mrzak34/thunderhack/modules/combat/AutoArmor;Ljava/util/concurrent/atomic/AtomicBoolean;)Ljava/util/function/Consumer;
/*     */     //   1475: invokevirtual ifPresent : (Ljava/util/function/Consumer;)V
/*     */     //   1478: goto -> 1897
/*     */     //   1481: aload_0
/*     */     //   1482: pop
/*     */     //   1483: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1486: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1489: ifnull -> 1897
/*     */     //   1492: aload_0
/*     */     //   1493: pop
/*     */     //   1494: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1497: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
/*     */     //   1500: ifnull -> 1897
/*     */     //   1503: aload_0
/*     */     //   1504: pop
/*     */     //   1505: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1508: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1511: getfield field_70173_aa : I
/*     */     //   1514: iconst_2
/*     */     //   1515: irem
/*     */     //   1516: ifne -> 1520
/*     */     //   1519: return
/*     */     //   1520: aload_0
/*     */     //   1521: pop
/*     */     //   1522: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1525: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*     */     //   1528: instanceof net/minecraft/client/gui/inventory/GuiContainer
/*     */     //   1531: ifeq -> 1549
/*     */     //   1534: aload_0
/*     */     //   1535: pop
/*     */     //   1536: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1539: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*     */     //   1542: instanceof net/minecraft/client/renderer/InventoryEffectRenderer
/*     */     //   1545: ifne -> 1549
/*     */     //   1548: return
/*     */     //   1549: iconst_4
/*     */     //   1550: newarray int
/*     */     //   1552: astore_2
/*     */     //   1553: iconst_4
/*     */     //   1554: newarray int
/*     */     //   1556: astore_3
/*     */     //   1557: iconst_0
/*     */     //   1558: istore_1
/*     */     //   1559: iload_1
/*     */     //   1560: iconst_4
/*     */     //   1561: if_icmpge -> 1616
/*     */     //   1564: aload_0
/*     */     //   1565: pop
/*     */     //   1566: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1569: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1572: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1575: iload_1
/*     */     //   1576: invokevirtual func_70440_f : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1579: astore #4
/*     */     //   1581: aload #4
/*     */     //   1583: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   1586: instanceof net/minecraft/item/ItemArmor
/*     */     //   1589: ifeq -> 1606
/*     */     //   1592: aload_3
/*     */     //   1593: iload_1
/*     */     //   1594: aload #4
/*     */     //   1596: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   1599: checkcast net/minecraft/item/ItemArmor
/*     */     //   1602: getfield field_77879_b : I
/*     */     //   1605: iastore
/*     */     //   1606: aload_2
/*     */     //   1607: iload_1
/*     */     //   1608: iconst_m1
/*     */     //   1609: iastore
/*     */     //   1610: iinc #1, 1
/*     */     //   1613: goto -> 1559
/*     */     //   1616: iconst_0
/*     */     //   1617: istore #4
/*     */     //   1619: iload #4
/*     */     //   1621: bipush #36
/*     */     //   1623: if_icmpge -> 1759
/*     */     //   1626: aload_0
/*     */     //   1627: pop
/*     */     //   1628: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1631: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1634: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1637: iload #4
/*     */     //   1639: invokevirtual func_70301_a : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1642: astore #6
/*     */     //   1644: aload #6
/*     */     //   1646: invokevirtual func_190916_E : ()I
/*     */     //   1649: iconst_1
/*     */     //   1650: if_icmpgt -> 1753
/*     */     //   1653: aload #6
/*     */     //   1655: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   1658: instanceof net/minecraft/item/ItemArmor
/*     */     //   1661: ifne -> 1667
/*     */     //   1664: goto -> 1753
/*     */     //   1667: aload #6
/*     */     //   1669: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   1672: checkcast net/minecraft/item/ItemArmor
/*     */     //   1675: astore #7
/*     */     //   1677: aload #7
/*     */     //   1679: getfield field_77881_a : Lnet/minecraft/inventory/EntityEquipmentSlot;
/*     */     //   1682: invokevirtual ordinal : ()I
/*     */     //   1685: iconst_2
/*     */     //   1686: isub
/*     */     //   1687: istore #8
/*     */     //   1689: iload #8
/*     */     //   1691: iconst_2
/*     */     //   1692: if_icmpne -> 1723
/*     */     //   1695: aload_0
/*     */     //   1696: pop
/*     */     //   1697: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1700: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1703: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1706: iload #8
/*     */     //   1708: invokevirtual func_70440_f : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1711: invokevirtual func_77973_b : ()Lnet/minecraft/item/Item;
/*     */     //   1714: getstatic net/minecraft/init/Items.field_185160_cR : Lnet/minecraft/item/Item;
/*     */     //   1717: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   1720: ifne -> 1753
/*     */     //   1723: aload #7
/*     */     //   1725: getfield field_77879_b : I
/*     */     //   1728: dup
/*     */     //   1729: istore #5
/*     */     //   1731: aload_3
/*     */     //   1732: iload #8
/*     */     //   1734: iaload
/*     */     //   1735: if_icmpgt -> 1741
/*     */     //   1738: goto -> 1753
/*     */     //   1741: aload_2
/*     */     //   1742: iload #8
/*     */     //   1744: iload #4
/*     */     //   1746: iastore
/*     */     //   1747: aload_3
/*     */     //   1748: iload #8
/*     */     //   1750: iload #5
/*     */     //   1752: iastore
/*     */     //   1753: iinc #4, 1
/*     */     //   1756: goto -> 1619
/*     */     //   1759: iconst_0
/*     */     //   1760: istore_1
/*     */     //   1761: iload_1
/*     */     //   1762: iconst_4
/*     */     //   1763: if_icmpge -> 1897
/*     */     //   1766: aload_2
/*     */     //   1767: iload_1
/*     */     //   1768: iaload
/*     */     //   1769: istore #5
/*     */     //   1771: iload #5
/*     */     //   1773: iconst_m1
/*     */     //   1774: if_icmpeq -> 1891
/*     */     //   1777: aload_0
/*     */     //   1778: pop
/*     */     //   1779: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1782: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1785: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1788: iload_1
/*     */     //   1789: invokevirtual func_70440_f : (I)Lnet/minecraft/item/ItemStack;
/*     */     //   1792: dup
/*     */     //   1793: astore #4
/*     */     //   1795: getstatic net/minecraft/item/ItemStack.field_190927_a : Lnet/minecraft/item/ItemStack;
/*     */     //   1798: if_acmpne -> 1822
/*     */     //   1801: aload_0
/*     */     //   1802: pop
/*     */     //   1803: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1806: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1809: getfield field_71071_by : Lnet/minecraft/entity/player/InventoryPlayer;
/*     */     //   1812: invokevirtual func_70447_i : ()I
/*     */     //   1815: iconst_m1
/*     */     //   1816: if_icmpne -> 1822
/*     */     //   1819: goto -> 1891
/*     */     //   1822: iload #5
/*     */     //   1824: bipush #9
/*     */     //   1826: if_icmpge -> 1832
/*     */     //   1829: iinc #5, 36
/*     */     //   1832: aload_0
/*     */     //   1833: pop
/*     */     //   1834: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1837: getfield field_71442_b : Lnet/minecraft/client/multiplayer/PlayerControllerMP;
/*     */     //   1840: iconst_0
/*     */     //   1841: bipush #8
/*     */     //   1843: iload_1
/*     */     //   1844: isub
/*     */     //   1845: iconst_0
/*     */     //   1846: getstatic net/minecraft/inventory/ClickType.QUICK_MOVE : Lnet/minecraft/inventory/ClickType;
/*     */     //   1849: aload_0
/*     */     //   1850: pop
/*     */     //   1851: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1854: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1857: invokevirtual func_187098_a : (IIILnet/minecraft/inventory/ClickType;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;
/*     */     //   1860: pop
/*     */     //   1861: aload_0
/*     */     //   1862: pop
/*     */     //   1863: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1866: getfield field_71442_b : Lnet/minecraft/client/multiplayer/PlayerControllerMP;
/*     */     //   1869: iconst_0
/*     */     //   1870: iload #5
/*     */     //   1872: iconst_0
/*     */     //   1873: getstatic net/minecraft/inventory/ClickType.QUICK_MOVE : Lnet/minecraft/inventory/ClickType;
/*     */     //   1876: aload_0
/*     */     //   1877: pop
/*     */     //   1878: getstatic com/mrzak34/thunderhack/modules/combat/AutoArmor.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   1881: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   1884: invokevirtual func_187098_a : (IIILnet/minecraft/inventory/ClickType;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;
/*     */     //   1887: pop
/*     */     //   1888: goto -> 1897
/*     */     //   1891: iinc #1, 1
/*     */     //   1894: goto -> 1761
/*     */     //   1897: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #64	-> 0
/*     */     //   #66	-> 13
/*     */     //   #67	-> 32
/*     */     //   #68	-> 58
/*     */     //   #71	-> 59
/*     */     //   #72	-> 75
/*     */     //   #75	-> 104
/*     */     //   #76	-> 120
/*     */     //   #77	-> 156
/*     */     //   #80	-> 166
/*     */     //   #83	-> 173
/*     */     //   #84	-> 189
/*     */     //   #85	-> 202
/*     */     //   #86	-> 223
/*     */     //   #87	-> 243
/*     */     //   #88	-> 250
/*     */     //   #89	-> 252
/*     */     //   #90	-> 290
/*     */     //   #91	-> 312
/*     */     //   #92	-> 314
/*     */     //   #94	-> 317
/*     */     //   #95	-> 320
/*     */     //   #97	-> 327
/*     */     //   #98	-> 337
/*     */     //   #99	-> 349
/*     */     //   #100	-> 357
/*     */     //   #101	-> 367
/*     */     //   #102	-> 372
/*     */     //   #103	-> 378
/*     */     //   #104	-> 385
/*     */     //   #105	-> 392
/*     */     //   #106	-> 400
/*     */     //   #85	-> 401
/*     */     //   #109	-> 407
/*     */     //   #113	-> 408
/*     */     //   #115	-> 421
/*     */     //   #117	-> 430
/*     */     //   #118	-> 437
/*     */     //   #119	-> 442
/*     */     //   #122	-> 443
/*     */     //   #123	-> 457
/*     */     //   #124	-> 491
/*     */     //   #127	-> 493
/*     */     //   #129	-> 501
/*     */     //   #131	-> 511
/*     */     //   #132	-> 536
/*     */     //   #133	-> 564
/*     */     //   #129	-> 573
/*     */     //   #138	-> 579
/*     */     //   #139	-> 590
/*     */     //   #140	-> 601
/*     */     //   #141	-> 611
/*     */     //   #142	-> 624
/*     */     //   #143	-> 632
/*     */     //   #145	-> 645
/*     */     //   #147	-> 654
/*     */     //   #148	-> 659
/*     */     //   #149	-> 670
/*     */     //   #150	-> 680
/*     */     //   #151	-> 693
/*     */     //   #152	-> 701
/*     */     //   #155	-> 714
/*     */     //   #156	-> 725
/*     */     //   #157	-> 735
/*     */     //   #158	-> 743
/*     */     //   #161	-> 756
/*     */     //   #162	-> 775
/*     */     //   #163	-> 794
/*     */     //   #164	-> 813
/*     */     //   #166	-> 832
/*     */     //   #167	-> 923
/*     */     //   #168	-> 1014
/*     */     //   #169	-> 1105
/*     */     //   #172	-> 1196
/*     */     //   #173	-> 1208
/*     */     //   #174	-> 1230
/*     */     //   #175	-> 1235
/*     */     //   #181	-> 1250
/*     */     //   #182	-> 1279
/*     */     //   #188	-> 1301
/*     */     //   #189	-> 1328
/*     */     //   #190	-> 1350
/*     */     //   #191	-> 1355
/*     */     //   #197	-> 1370
/*     */     //   #198	-> 1382
/*     */     //   #199	-> 1404
/*     */     //   #200	-> 1409
/*     */     //   #206	-> 1424
/*     */     //   #207	-> 1436
/*     */     //   #208	-> 1458
/*     */     //   #209	-> 1463
/*     */     //   #214	-> 1478
/*     */     //   #215	-> 1481
/*     */     //   #217	-> 1503
/*     */     //   #218	-> 1519
/*     */     //   #220	-> 1520
/*     */     //   #221	-> 1548
/*     */     //   #223	-> 1549
/*     */     //   #224	-> 1553
/*     */     //   #225	-> 1557
/*     */     //   #226	-> 1564
/*     */     //   #227	-> 1581
/*     */     //   #228	-> 1592
/*     */     //   #230	-> 1606
/*     */     //   #225	-> 1610
/*     */     //   #232	-> 1616
/*     */     //   #234	-> 1626
/*     */     //   #235	-> 1644
/*     */     //   #236	-> 1667
/*     */     //   #237	-> 1677
/*     */     //   #238	-> 1689
/*     */     //   #239	-> 1738
/*     */     //   #240	-> 1741
/*     */     //   #241	-> 1747
/*     */     //   #232	-> 1753
/*     */     //   #243	-> 1759
/*     */     //   #245	-> 1766
/*     */     //   #246	-> 1771
/*     */     //   #247	-> 1819
/*     */     //   #248	-> 1822
/*     */     //   #249	-> 1829
/*     */     //   #251	-> 1832
/*     */     //   #252	-> 1861
/*     */     //   #253	-> 1888
/*     */     //   #243	-> 1891
/*     */     //   #258	-> 1897
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   156	10	1	proximity	Ljava/util/List;
/*     */     //   290	27	5	entry	Ljava/util/Map$Entry;
/*     */     //   252	75	3	mending	Z
/*     */     //   243	158	2	armorPiece	Lnet/minecraft/item/ItemStack;
/*     */     //   378	23	3	freeSlots	J
/*     */     //   204	203	1	i	I
/*     */     //   536	37	5	invStack	Lcom/mrzak34/thunderhack/util/InvStack;
/*     */     //   504	75	4	slot	I
/*     */     //   430	1048	1	hasSwapped	Ljava/util/concurrent/atomic/AtomicBoolean;
/*     */     //   457	1021	2	ep	Z
/*     */     //   501	977	3	replacements	Ljava/util/Set;
/*     */     //   645	833	4	armors	Ljava/util/List;
/*     */     //   654	824	5	wasEmpty	Z
/*     */     //   756	722	6	elytras	Ljava/util/List;
/*     */     //   775	703	7	currentHeadItem	Lnet/minecraft/item/Item;
/*     */     //   794	684	8	currentChestItem	Lnet/minecraft/item/Item;
/*     */     //   813	665	9	currentLegsItem	Lnet/minecraft/item/Item;
/*     */     //   832	646	10	currentFeetItem	Lnet/minecraft/item/Item;
/*     */     //   923	555	11	replaceHead	Z
/*     */     //   1014	464	12	replaceChest	Z
/*     */     //   1105	373	13	replaceLegs	Z
/*     */     //   1196	282	14	replaceFeet	Z
/*     */     //   1581	29	4	oldArmor	Lnet/minecraft/item/ItemStack;
/*     */     //   1731	22	5	armorValue	I
/*     */     //   1644	109	6	stack	Lnet/minecraft/item/ItemStack;
/*     */     //   1677	76	7	armor	Lnet/minecraft/item/ItemArmor;
/*     */     //   1689	64	8	armorType2	I
/*     */     //   1619	140	4	slot	I
/*     */     //   1795	96	4	oldArmor	Lnet/minecraft/item/ItemStack;
/*     */     //   1771	120	5	slot	I
/*     */     //   1559	338	1	armorType	I
/*     */     //   1553	344	2	bestArmorSlots	[I
/*     */     //   1557	340	3	bestArmorValues	[I
/*     */     //   0	1898	0	this	Lcom/mrzak34/thunderhack/modules/combat/AutoArmor;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   156	10	1	proximity	Ljava/util/List<Lnet/minecraft/entity/Entity;>;
/*     */     //   290	27	5	entry	Ljava/util/Map$Entry<Lnet/minecraft/enchantment/Enchantment;Ljava/lang/Integer;>;
/*     */     //   501	977	3	replacements	Ljava/util/Set<Lcom/mrzak34/thunderhack/util/InvStack;>;
/*     */     //   645	833	4	armors	Ljava/util/List<Lcom/mrzak34/thunderhack/util/InvStack;>;
/*     */     //   756	722	6	elytras	Ljava/util/List<Lcom/mrzak34/thunderhack/util/InvStack;>;
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
/*     */   @SubscribeEvent
/*     */   public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
/* 262 */     if (event.getEntityPlayer() != mc.field_71439_g)
/* 263 */       return;  if (event.getItemStack().func_77973_b() != Items.field_151062_by)
/* 264 */       return;  this.rightClickTimer.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   private void swapSlot(int source, int target) {
/* 269 */     mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, (source < 9) ? (source + 36) : source, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 270 */     mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, target, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 271 */     mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, (source < 9) ? (source + 36) : source, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */     
/* 273 */     this.sleep = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void shiftClickSpot(int source) {
/* 278 */     mc.field_71442_b.func_187098_a(mc.field_71439_g.field_71069_bz.field_75152_c, source, 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.field_71439_g);
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 282 */     FunnyGame,
/* 283 */     Default;
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AutoArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */