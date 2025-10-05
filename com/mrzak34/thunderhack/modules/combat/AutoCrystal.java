/*      */ package com.mrzak34.thunderhack.modules.combat;
/*      */ import com.mrzak34.thunderhack.Thunderhack;
/*      */ import com.mrzak34.thunderhack.command.Command;
/*      */ import com.mrzak34.thunderhack.events.DestroyBlockEvent;
/*      */ import com.mrzak34.thunderhack.events.EventSync;
/*      */ import com.mrzak34.thunderhack.events.KeyboardEvent;
/*      */ import com.mrzak34.thunderhack.events.PacketEvent;
/*      */ import com.mrzak34.thunderhack.events.PostRenderEntitiesEvent;
/*      */ import com.mrzak34.thunderhack.events.UpdateEntitiesEvent;
/*      */ import com.mrzak34.thunderhack.modules.Module;
/*      */ import com.mrzak34.thunderhack.modules.render.Trajectories;
/*      */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*      */ import com.mrzak34.thunderhack.setting.Setting;
/*      */ import com.mrzak34.thunderhack.setting.SubBind;
/*      */ import com.mrzak34.thunderhack.util.EntityUtil;
/*      */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*      */ import com.mrzak34.thunderhack.util.TessellatorUtil;
/*      */ import com.mrzak34.thunderhack.util.Timer;
/*      */ import com.mrzak34.thunderhack.util.math.MathUtil;
/*      */ import com.mrzak34.thunderhack.util.phobos.AbstractCalculation;
/*      */ import com.mrzak34.thunderhack.util.phobos.AntiTotemHelper;
/*      */ import com.mrzak34.thunderhack.util.phobos.CalculationMotion;
/*      */ import com.mrzak34.thunderhack.util.phobos.CrystalTimeStamp;
/*      */ import com.mrzak34.thunderhack.util.phobos.DamageHelper;
/*      */ import com.mrzak34.thunderhack.util.phobos.DiscreteTimer;
/*      */ import com.mrzak34.thunderhack.util.phobos.ExtrapolationHelper;
/*      */ import com.mrzak34.thunderhack.util.phobos.ForceAntiTotemHelper;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperBreakMotion;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperEntityBlocksPlace;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperLiquids;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperObby;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperPlace;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperRotation;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperSequential;
/*      */ import com.mrzak34.thunderhack.util.phobos.HelperUtil;
/*      */ import com.mrzak34.thunderhack.util.phobos.IDHelper;
/*      */ import com.mrzak34.thunderhack.util.phobos.ListenerSound;
/*      */ import com.mrzak34.thunderhack.util.phobos.MotionTracker;
/*      */ import com.mrzak34.thunderhack.util.phobos.MutableWrapper;
/*      */ import com.mrzak34.thunderhack.util.phobos.RotationCanceller;
/*      */ import com.mrzak34.thunderhack.util.phobos.RotationFunction;
/*      */ import com.mrzak34.thunderhack.util.phobos.RotationUtil;
/*      */ import com.mrzak34.thunderhack.util.phobos.ServerTimeHelper;
/*      */ import com.mrzak34.thunderhack.util.phobos.SoundObserver;
/*      */ import com.mrzak34.thunderhack.util.phobos.ThreadHelper;
/*      */ import com.mrzak34.thunderhack.util.render.BlockRenderUtil;
/*      */ import java.awt.Color;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.item.EntityEnderCrystal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.network.play.client.CPacketUseEntity;
/*      */ import net.minecraft.network.play.server.SPacketBlockChange;
/*      */ import net.minecraft.network.play.server.SPacketEntity;
/*      */ import net.minecraft.network.play.server.SPacketMultiBlockChange;
/*      */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MouseFilter;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class AutoCrystal extends Module {
/*   76 */   public static final PositionHistoryHelper POSITION_HISTORY = new PositionHistoryHelper();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   private static final ScheduledExecutorService EXECUTOR = ThreadUtil.newDaemonScheduledExecutor("AutoCrystal");
/*   86 */   private static final AtomicBoolean ATOMIC_STARTED = new AtomicBoolean();
/*   87 */   public static Timer timercheckerfg = new Timer();
/*   88 */   public static Timer timercheckerwfg = new Timer();
/*      */   public static boolean psdead = true;
/*      */   private static boolean started;
/*      */   
/*      */   static {
/*   93 */     MinecraftForge.EVENT_BUS.register(POSITION_HISTORY);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*   98 */   public final Map<BlockPos, CrystalTimeStamp> placed = new ConcurrentHashMap<>();
/*   99 */   public final HelperSequential sequentialHelper = new HelperSequential(this);
/*  100 */   public final IDHelper idHelper = new IDHelper();
/*  101 */   public final HelperRotation rotationHelper = new HelperRotation(this);
/*  102 */   public final HelperRange rangeHelper = new HelperRange(this);
/*  103 */   private final MouseFilter pitchMouseFilter = new MouseFilter();
/*  104 */   private final MouseFilter yawMouseFilter = new MouseFilter();
/*  105 */   private final Map<BlockPos, Long> fadeList = new HashMap<>();
/*  106 */   public Setting<settingtypeEn> settingType = register(new Setting("Settings", settingtypeEn.Noob));
/*  107 */   public Setting<pages> page = register(new Setting("Page", pages.Place));
/*      */   
/*  109 */   public final Setting<Integer> extrapol = register(new Setting("Extrapolation", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(50), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  110 */   public final Setting<Integer> bExtrapol = register(new Setting("Break-Extrapolation", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(50), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  111 */   public final Setting<Integer> blockExtrapol = register(new Setting("Block-Extrapolation", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(50), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  112 */   public final Setting<BlockExtrapolationMode> blockExtraMode = register(new Setting("BlockExtraMode", BlockExtrapolationMode.Pessimistic, v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  113 */   public final Setting<Boolean> doubleExtraCheck = register(new Setting("DoubleExtraCheck", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  114 */   public final Setting<Boolean> avgPlaceDamage = register(new Setting("AvgPlaceExtra", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  115 */   public final Setting<Double> placeExtraWeight = register(new Setting("P-Extra-Weight", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  116 */   public final Setting<Double> placeNormalWeight = register(new Setting("P-Norm-Weight", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  117 */   public final Setting<Boolean> avgBreakExtra = register(new Setting("AvgBreakExtra", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  118 */   public final Setting<Double> breakExtraWeight = register(new Setting("B-Extra-Weight", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  119 */   public final Setting<Double> breakNormalWeight = register(new Setting("B-Norm-Weight", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  120 */   public final Setting<Boolean> gravityExtrapolation = register(new Setting("Extra-Gravity", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  121 */   public final Setting<Double> gravityFactor = register(new Setting("Gravity-Factor", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  122 */   public final Setting<Double> yPlusFactor = register(new Setting("Y-Plus-Factor", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  123 */   public final Setting<Double> yMinusFactor = register(new Setting("Y-Minus-Factor", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  124 */   public final Setting<Boolean> selfExtrapolation = register(new Setting("SelfExtrapolation", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro)));
/*  125 */   public final Setting<Boolean> useSafetyFactor = register(new Setting("UseSafetyFactor", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  126 */   public final Setting<Double> selfFactor = register(new Setting("SelfFactor", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  127 */   public final Setting<Double> safetyFactor = register(new Setting("SafetyFactor", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  128 */   public final Setting<Double> compareDiff = register(new Setting("CompareDiff", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  129 */   public final Setting<Boolean> facePlaceCompare = register(new Setting("FacePlaceCompare", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  131 */   public Setting<Boolean> place = register(new Setting("Place", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Place)));
/*  132 */   public Setting<Target> targetMode = register(new Setting("Target", Target.Closest, v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  133 */   public Setting<Float> placeRange = register(new Setting("PlaceRange", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(6.0F), v -> (this.page.getValue() == pages.Place)));
/*  134 */   public Setting<Float> placeTrace = register(new Setting("PlaceTrace", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(6.0F), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  135 */   public Setting<Float> minDamage = register(new Setting("MinDamage", Float.valueOf(6.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Place)));
/*  136 */   public Setting<Integer> placeDelay = register(new Setting("PlaceDelay", Integer.valueOf(25), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Place)));
/*      */   
/*  138 */   public DiscreteTimer placeTimer = (new GuardTimer(1000L, 5L)).reset(((Integer)this.placeDelay.getValue()).intValue());
/*  139 */   public Setting<Float> maxSelfPlace = register(new Setting("MaxSelfPlace", Float.valueOf(9.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  140 */   public Setting<Integer> multiPlace = register(new Setting("MultiPlace", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(5), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  141 */   public Setting<Float> slowPlaceDmg = register(new Setting("SlowPlace", Float.valueOf(4.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  142 */   public Setting<Integer> slowPlaceDelay = register(new Setting("SlowPlaceDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  143 */   public Setting<Boolean> override = register(new Setting("OverridePlace", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  144 */   public Setting<Boolean> newVer = register(new Setting("1.13+", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  145 */   public Setting<Boolean> newVerEntities = register(new Setting("1.13-Entities", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  146 */   public Setting<SwingTime> placeSwing = register(new Setting("PlaceSwing", SwingTime.Post, v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  147 */   public Setting<Boolean> smartTrace = register(new Setting("Smart-Trace", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  148 */   public Setting<Boolean> placeRangeEyes = register(new Setting("PlaceRangeEyes", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  149 */   public Setting<Boolean> placeRangeCenter = register(new Setting("PlaceRangeCenter", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  150 */   public Setting<Double> traceWidth = register(new Setting("TraceWidth", Double.valueOf(-1.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  151 */   public Setting<Boolean> fallbackTrace = register(new Setting("Fallback-Trace", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  152 */   public Setting<Boolean> rayTraceBypass = register(new Setting("RayTraceBypass", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  153 */   public Setting<Boolean> forceBypass = register(new Setting("ForceBypass", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  154 */   public Setting<Boolean> rayBypassFacePlace = register(new Setting("RayBypassFacePlace", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  155 */   public Setting<Boolean> rayBypassFallback = register(new Setting("RayBypassFallback", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  156 */   public Setting<Integer> bypassTicks = register(new Setting("BypassTicks", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(20), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  157 */   public Setting<Float> rbYaw = register(new Setting("RB-Yaw", Float.valueOf(180.0F), Float.valueOf(0.0F), Float.valueOf(180.0F), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  158 */   public Setting<Float> rbPitch = register(new Setting("RB-Pitch", Float.valueOf(90.0F), Float.valueOf(0.0F), Float.valueOf(90.0F), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  159 */   public Setting<Integer> bypassRotationTime = register(new Setting("RayBypassRotationTime", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  160 */   public Setting<Boolean> ignoreNonFull = register(new Setting("IgnoreNonFull", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  161 */   public Setting<Boolean> efficientPlacements = register(new Setting("EfficientPlacements", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  162 */   public Setting<Integer> simulatePlace = register(new Setting("Simulate-Place", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10), v -> (this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro)));
/*  163 */   public FakeCrystalRender crystalRender = new FakeCrystalRender(this.simulatePlace);
/*      */   
/*  165 */   public Setting<Attack2> attackMode = register(new Setting("Attack", Attack2.Crystal, v -> (this.page.getValue() == pages.Break)));
/*  166 */   public Setting<Boolean> attack = register(new Setting("Break", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Break)));
/*  167 */   public Setting<Float> breakRange = register(new Setting("BreakRange", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(6.0F), v -> (this.page.getValue() == pages.Break)));
/*  168 */   public Setting<Integer> breakDelay = register(new Setting("BreakDelay", Integer.valueOf(25), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Break)));
/*  169 */   public DiscreteTimer breakTimer = (new GuardTimer(1000L, 5L)).reset(((Integer)this.breakDelay.getValue()).intValue());
/*  170 */   public Setting<Float> breakTrace = register(new Setting("BreakTrace", Float.valueOf(3.0F), Float.valueOf(0.0F), Float.valueOf(6.0F), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  171 */   public Setting<Float> minBreakDamage = register(new Setting("MinBreakDmg", Float.valueOf(0.5F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  172 */   public Setting<Float> maxSelfBreak = register(new Setting("MaxSelfBreak", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  173 */   public Setting<Float> slowBreakDamage = register(new Setting("SlowBreak", Float.valueOf(3.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  174 */   public Setting<Integer> slowBreakDelay = register(new Setting("SlowBreakDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  175 */   public Setting<Boolean> instant = register(new Setting("Instant", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Break)));
/*  176 */   public Setting<Boolean> asyncCalc = register(new Setting("Async-Calc", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  177 */   public Setting<Boolean> alwaysCalc = register(new Setting("Always-Calc", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  178 */   public Setting<Boolean> ncpRange = register(new Setting("NCP-Range", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  179 */   public Setting<SmartRange> placeBreakRange = register(new Setting("SmartRange", SmartRange.None, v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  180 */   public Setting<Integer> smartTicks = register(new Setting("SmartRange-Ticks", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(20), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  181 */   public Setting<Integer> negativeTicks = register(new Setting("Negative-Ticks", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(20), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  182 */   public Setting<Boolean> smartBreakTrace = register(new Setting("SmartBreakTrace", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  183 */   public Setting<Boolean> negativeBreakTrace = register(new Setting("NegativeBreakTrace", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  184 */   public Setting<Integer> packets = register(new Setting("Packets", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(5), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  185 */   public Setting<Boolean> overrideBreak = register(new Setting("OverrideBreak", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  186 */   public Setting<AntiWeakness> antiWeakness = register(new Setting("AntiWeakness", AntiWeakness.None, v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  187 */   public Setting<Boolean> instantAntiWeak = register(new Setting("AW-Instant", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro)));
/*  188 */   public Setting<Boolean> efficient = register(new Setting("Efficient", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  189 */   public Setting<Boolean> manually = register(new Setting("Manually", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  190 */   public Setting<Integer> manualDelay = register(new Setting("ManualDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  191 */   public Setting<SwingTime> breakSwing = register(new Setting("BreakSwing", SwingTime.Post, v -> (this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  193 */   public Setting<ACRotate> rotate = register(new Setting("Rotate", ACRotate.None, v -> (this.page.getValue() == pages.Rotations)));
/*  194 */   public Setting<RotateMode> rotateMode = register(new Setting("Rotate-Mode", RotateMode.Normal, v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro)));
/*  195 */   public Setting<Float> smoothSpeed = register(new Setting("Smooth-Speed", Float.valueOf(0.5F), Float.valueOf(0.1F), Float.valueOf(2.0F), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro)));
/*  196 */   public Setting<Integer> endRotations = register(new Setting("End-Rotations", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  197 */   public Setting<Float> angle = register(new Setting("Break-Angle", Float.valueOf(180.0F), Float.valueOf(0.1F), Float.valueOf(180.0F), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro)));
/*  198 */   public Setting<Float> placeAngle = register(new Setting("Place-Angle", Float.valueOf(180.0F), Float.valueOf(0.1F), Float.valueOf(180.0F), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro)));
/*  199 */   public Setting<Float> height = register(new Setting("Height", Float.valueOf(0.05F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  200 */   public Setting<Double> placeHeight = register(new Setting("Place-Height", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(1.0D), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  201 */   public Setting<Integer> rotationTicks = register(new Setting("Rotations-Existed", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro)));
/*  202 */   public Setting<Boolean> focusRotations = register(new Setting("Focus-Rotations", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  203 */   public Setting<Boolean> focusAngleCalc = register(new Setting("FocusRotationCompare", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  204 */   public Setting<Double> focusExponent = register(new Setting("FocusExponent", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  205 */   public Setting<Double> focusDiff = register(new Setting("FocusDiff", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  206 */   public Setting<Double> rotationExponent = register(new Setting("RotationExponent", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  207 */   public Setting<Double> minRotDiff = register(new Setting("MinRotationDiff", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  208 */   public Setting<Integer> existed = register(new Setting("Existed", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro)));
/*  209 */   public Setting<Boolean> pingExisted = register(new Setting("Ping-Existed", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro)));
/*      */   
/*  211 */   public Setting<Float> targetRange = register(new Setting("TargetRange", Float.valueOf(20.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Misc)));
/*  212 */   public Setting<Float> pbTrace = register(new Setting("CombinedTrace", Float.valueOf(3.0F), Float.valueOf(0.0F), Float.valueOf(6.0F), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro)));
/*  213 */   public Setting<Float> range = register(new Setting("Range", Float.valueOf(12.0F), Float.valueOf(0.1F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Misc)));
/*  214 */   public Setting<Boolean> suicide = register(new Setting("Suicide", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc)));
/*  215 */   public Setting<Boolean> shield = register(new Setting("Shield", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  216 */   public Setting<Integer> shieldCount = register(new Setting("ShieldCount", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(5), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  217 */   public Setting<Float> shieldMinDamage = register(new Setting("ShieldMinDamage", Float.valueOf(6.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  218 */   public Setting<Float> shieldSelfDamage = register(new Setting("ShieldSelfDamage", Float.valueOf(2.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  219 */   public Setting<Integer> shieldDelay = register(new Setting("ShieldPlaceDelay", Integer.valueOf(50), Integer.valueOf(0), Integer.valueOf(5000), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  220 */   public Setting<Float> shieldRange = register(new Setting("ShieldRange", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  221 */   public Setting<Boolean> shieldPrioritizeHealth = register(new Setting("Shield-PrioritizeHealth", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  222 */   public Setting<Boolean> multiTask = register(new Setting("MultiTask", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro)));
/*  223 */   public Setting<Boolean> multiPlaceCalc = register(new Setting("MultiPlace-Calc", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  224 */   public Setting<Boolean> multiPlaceMinDmg = register(new Setting("MultiPlace-MinDmg", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  225 */   public Setting<Boolean> countDeadCrystals = register(new Setting("CountDeadCrystals", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  226 */   public Setting<Boolean> countDeathTime = register(new Setting("CountWithinDeathTime", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  227 */   public Setting<Boolean> yCalc = register(new Setting("Y-Calc", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  228 */   public Setting<Boolean> dangerSpeed = register(new Setting("Danger-Speed", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro)));
/*  229 */   public Setting<Float> dangerHealth = register(new Setting("Danger-Health", Float.valueOf(0.0F), Float.valueOf(0.0F), Float.valueOf(36.0F), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro)));
/*  230 */   public Setting<Integer> cooldown = register(new Setting("CoolDown", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(10000), v -> (this.page.getValue() == pages.Misc)));
/*  231 */   public WeaknessHelper weaknessHelper = new WeaknessHelper(this.antiWeakness, this.cooldown);
/*  232 */   public Setting<Integer> placeCoolDown = register(new Setting("PlaceCooldown", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(10000), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro)));
/*  233 */   public Setting<AntiFriendPop> antiFriendPop = register(new Setting("AntiFriendPop", AntiFriendPop.None, v -> (this.page.getValue() == pages.Misc)));
/*  234 */   public Setting<Boolean> antiFeetPlace = register(new Setting("AntiFeetPlace", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  235 */   public Setting<Integer> feetBuffer = register(new Setting("FeetBuffer", Integer.valueOf(5), Integer.valueOf(0), Integer.valueOf(50), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  236 */   public ServerTimeHelper serverTimeHelper = new ServerTimeHelper(this, this.rotate, this.placeSwing, this.antiFeetPlace, this.newVer, this.feetBuffer);
/*  237 */   public Setting<Boolean> stopWhenEating = register(new Setting("StopWhenEating", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc)));
/*  238 */   public Setting<Boolean> stopWhenMining = register(new Setting("StopWhenMining", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc)));
/*  239 */   public Setting<Boolean> dangerFacePlace = register(new Setting("Danger-FacePlace", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro)));
/*  240 */   public Setting<Boolean> motionCalc = register(new Setting("Motion-Calc", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  242 */   public Setting<Boolean> holdFacePlace = register(new Setting("HoldFacePlace", Boolean.valueOf(false), v -> (this.page.getValue() == pages.FacePlace)));
/*  243 */   public Setting<Float> facePlace = register(new Setting("FacePlace", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(36.0F), v -> (this.page.getValue() == pages.FacePlace)));
/*  244 */   public Setting<Float> minFaceDmg = register(new Setting("Min-FP", Float.valueOf(2.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro)));
/*  245 */   public Setting<Float> armorPlace = register(new Setting("ArmorPlace", Float.valueOf(5.0F), Float.valueOf(0.0F), Float.valueOf(100.0F), v -> (this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro)));
/*  246 */   public Setting<Boolean> pickAxeHold = register(new Setting("PickAxe-Hold", Boolean.valueOf(false), v -> (this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro)));
/*  247 */   public Setting<Boolean> antiNaked = register(new Setting("AntiNaked", Boolean.valueOf(false), v -> (this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro)));
/*  248 */   public Setting<Boolean> fallBack = register(new Setting("FallBack", Boolean.valueOf(true), v -> (this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  249 */   public Setting<Float> fallBackDiff = register(new Setting("Fallback-Difference", Float.valueOf(10.0F), Float.valueOf(0.0F), Float.valueOf(16.0F), v -> (this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  250 */   public Setting<Float> fallBackDmg = register(new Setting("FallBackDmg", Float.valueOf(3.0F), Float.valueOf(0.0F), Float.valueOf(6.0F), v -> (this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  252 */   public Setting<AutoSwitch> autoSwitch = register(new Setting("AutoSwitch", AutoSwitch.Bind, v -> (this.page.getValue() == pages.SwitchNSwing)));
/*  253 */   public Setting<Boolean> mainHand = register(new Setting("MainHand", Boolean.valueOf(false), v -> (this.page.getValue() == pages.SwitchNSwing)));
/*  254 */   public Setting<SubBind> switchBind = register(new Setting("SwitchBind", new SubBind(0), v -> (this.page.getValue() == pages.SwitchNSwing)));
/*  255 */   public Setting<Boolean> switchBack = register(new Setting("SwitchBack", Boolean.valueOf(true), v -> (this.page.getValue() == pages.SwitchNSwing)));
/*  256 */   public Setting<Boolean> useAsOffhand = register(new Setting("UseAsOffHandBind", Boolean.valueOf(false), v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro)));
/*  257 */   public Setting<Boolean> instantOffhand = register(new Setting("Instant-Offhand", Boolean.valueOf(true), v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  258 */   public Setting<Boolean> switchMessage = register(new Setting("Switch-Message", Boolean.valueOf(false), v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  259 */   public Setting<SwingType> swing = register(new Setting("BreakHand", SwingType.MainHand, v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  260 */   public Setting<SwingType> placeHand = register(new Setting("PlaceHand", SwingType.MainHand, v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  261 */   public Setting<CooldownBypass2> cooldownBypass = register(new Setting("CooldownBypass", CooldownBypass2.None, v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro)));
/*  262 */   public Setting<CooldownBypass2> obsidianBypass = register(new Setting("ObsidianBypass", CooldownBypass2.None, v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro)));
/*  263 */   public Setting<CooldownBypass2> antiWeaknessBypass = register(new Setting("AntiWeaknessBypass", CooldownBypass2.None, v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro)));
/*  264 */   public Setting<CooldownBypass2> mineBypass = register(new Setting("MineBypass", CooldownBypass2.None, v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro)));
/*  265 */   public Setting<SwingType> obbyHand = register(new Setting("ObbyHand", SwingType.MainHand, v -> (this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  267 */   public Setting<Boolean> render = register(new Setting("Render", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  268 */   public Setting<Integer> renderTime = register(new Setting("Render-Time", Integer.valueOf(600), Integer.valueOf(0), Integer.valueOf(5000), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  269 */   public Setting<Boolean> box = register(new Setting("Draw-Box", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  270 */   public Setting<Boolean> fade = register(new Setting("Fade", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  271 */   public Setting<Boolean> fadeComp = register(new Setting("Fade-Compatibility", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  272 */   public Setting<Integer> fadeTime = register(new Setting("Fade-Time", Integer.valueOf(1000), Integer.valueOf(0), Integer.valueOf(5000), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  273 */   public Setting<Boolean> realtime = register(new Setting("Realtime", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  274 */   public Setting<Boolean> slide = register(new Setting("Slide", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  275 */   public Setting<Boolean> smoothSlide = register(new Setting("SmoothenSlide", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  276 */   public Setting<Integer> slideTime = register(new Setting("Slide-Time", Integer.valueOf(250), Integer.valueOf(1), Integer.valueOf(1000), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  277 */   public Setting<Boolean> zoom = register(new Setting("Zoom", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  278 */   public Setting<Double> zoomTime = register(new Setting("Zoom-Time", Double.valueOf(100.0D), Double.valueOf(1.0D), Double.valueOf(1000.0D), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  279 */   public Setting<Double> zoomOffset = register(new Setting("Zoom-Offset", Double.valueOf(-0.5D), Double.valueOf(-1.0D), Double.valueOf(1.0D), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  280 */   public Setting<Boolean> multiZoom = register(new Setting("Multi-Zoom", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  281 */   public Setting<Boolean> renderExtrapolation = register(new Setting("RenderExtrapolation", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  282 */   public Setting<RenderDamagePos> renderDamage = register(new Setting("DamageRender", RenderDamagePos.None, v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*  283 */   public Setting<RenderDamage> renderMode = register(new Setting("DamageMode", RenderDamage.Normal, v -> (this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro)));
/*      */   
/*  285 */   public Setting<Boolean> setDead = register(new Setting("SetDead", Boolean.valueOf(false), v -> (this.page.getValue() == pages.SetDead)));
/*  286 */   public Setting<Boolean> instantSetDead = register(new Setting("Instant-Dead", Boolean.valueOf(false), v -> (this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro)));
/*  287 */   public Setting<Boolean> pseudoSetDead = register(new Setting("Pseudo-Dead", Boolean.valueOf(true), v -> (this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro)));
/*  288 */   public Setting<Boolean> simulateExplosion = register(new Setting("SimulateExplosion", Boolean.valueOf(false), v -> (this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  289 */   public Setting<Boolean> soundRemove = register(new Setting("SoundRemove", Boolean.valueOf(true), v -> (this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro)));
/*  290 */   public Setting<Boolean> useSafeDeathTime = register(new Setting("UseSafeDeathTime", Boolean.valueOf(false), v -> (this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  291 */   public Setting<Integer> safeDeathTime = register(new Setting("Safe-Death-Time", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  292 */   public Setting<Integer> deathTime = register(new Setting("Death-Time", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro)));
/*      */   
/*  294 */   public Setting<Boolean> obsidian = register(new Setting("Obsidian", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Obsidian)));
/*  295 */   public Setting<Boolean> basePlaceOnly = register(new Setting("BasePlaceOnly", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  296 */   public Setting<Boolean> obbySwitch = register(new Setting("Obby-Switch", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  297 */   public Setting<Integer> obbyDelay = register(new Setting("ObbyDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(5000), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  298 */   public Setting<Integer> obbyCalc = register(new Setting("ObbyCalc", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(5000), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  299 */   public Setting<Integer> helpingBlocks = register(new Setting("HelpingBlocks", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(5), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  300 */   public Setting<Float> obbyMinDmg = register(new Setting("Obby-MinDamage", Float.valueOf(7.0F), Float.valueOf(0.1F), Float.valueOf(36.0F), v -> (this.page.getValue() == pages.Obsidian)));
/*  301 */   public Setting<Boolean> terrainCalc = register(new Setting("TerrainCalc", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Obsidian)));
/*  302 */   public Setting<Boolean> obbySafety = register(new Setting("ObbySafety", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  303 */   public Setting<RayTraceMode> obbyTrace = register(new Setting("Obby-Raytrace", RayTraceMode.Fast, v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  304 */   public Setting<Boolean> obbyTerrain = register(new Setting("Obby-Terrain", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  305 */   public Setting<Boolean> obbyPreSelf = register(new Setting("Obby-PreSelf", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  306 */   public Setting<Integer> fastObby = register(new Setting("Fast-Obby", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(3), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro)));
/*  307 */   public Setting<Integer> maxDiff = register(new Setting("Max-Difference", Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(5), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  308 */   public Setting<Double> maxDmgDiff = register(new Setting("Max-DamageDiff", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  309 */   public Setting<Boolean> setState = register(new Setting("Client-Blocks", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  310 */   public Setting<PlaceSwing> obbySwing = register(new Setting("Obby-Swing", PlaceSwing.Once, v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  311 */   public Setting<Boolean> obbyFallback = register(new Setting("Obby-Fallback", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  312 */   public Setting<Rotate> obbyRotate = register(new Setting("Obby-Rotate", Rotate.None, v -> (this.page.getValue() == pages.Obsidian)));
/*      */   
/*  314 */   public Setting<Boolean> interact = register(new Setting("Interact", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  315 */   public Setting<Boolean> inside = register(new Setting("Inside", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  316 */   public Setting<Boolean> lava = register(new Setting("Lava", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  317 */   public Setting<Boolean> water = register(new Setting("Water", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  318 */   public Setting<Boolean> liquidObby = register(new Setting("LiquidObby", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  319 */   public Setting<Boolean> liquidRayTrace = register(new Setting("LiquidRayTrace", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  320 */   public Setting<Integer> liqDelay = register(new Setting("LiquidDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  321 */   public Setting<Rotate> liqRotate = register(new Setting("LiquidRotate", Rotate.None, v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  322 */   public Setting<Boolean> pickaxeOnly = register(new Setting("PickaxeOnly", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  323 */   public Setting<Boolean> interruptSpeedmine = register(new Setting("InterruptSpeedmine", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  324 */   public Setting<Boolean> setAir = register(new Setting("SetAir", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro)));
/*  325 */   public Setting<Boolean> absorb = register(new Setting("Absorb", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  326 */   public Setting<Boolean> requireOnGround = register(new Setting("RequireOnGround", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  327 */   public Setting<Boolean> ignoreLavaItems = register(new Setting("IgnoreLavaItems", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  328 */   public Setting<Boolean> sponges = register(new Setting("Sponges", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  330 */   public Setting<Boolean> antiTotem = register(new Setting("AntiTotem", Boolean.valueOf(false), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Pro)));
/*  331 */   public Setting<Float> totemHealth = register(new Setting("Totem-Health", Float.valueOf(1.5F), Float.valueOf(0.0F), Float.valueOf(10.0F), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  332 */   public AntiTotemHelper antiTotemHelper = new AntiTotemHelper(this.totemHealth);
/*  333 */   public Setting<Float> minTotemOffset = register(new Setting("Min-Offset", Float.valueOf(0.5F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  334 */   public Setting<Float> maxTotemOffset = register(new Setting("Max-Offset", Float.valueOf(2.0F), Float.valueOf(0.0F), Float.valueOf(5.0F), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  335 */   public Setting<Float> popDamage = register(new Setting("Pop-Damage", Float.valueOf(12.0F), Float.valueOf(10.0F), Float.valueOf(20.0F), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  336 */   public Setting<Boolean> totemSync = register(new Setting("TotemSync", Boolean.valueOf(true), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  337 */   public Setting<Boolean> forceAntiTotem = register(new Setting("Force-AntiTotem", Boolean.valueOf(false), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  338 */   public Setting<Boolean> forceSlow = register(new Setting("Force-Slow", Boolean.valueOf(false), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  339 */   public Setting<Boolean> syncForce = register(new Setting("Sync-Force", Boolean.valueOf(true), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  340 */   public Setting<Boolean> dangerForce = register(new Setting("Danger-Force", Boolean.valueOf(false), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  341 */   public Setting<Integer> forcePlaceConfirm = register(new Setting("Force-Place", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  342 */   public Setting<Integer> forceBreakConfirm = register(new Setting("Force-Break", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  343 */   public Setting<Integer> attempts = register(new Setting("Attempts", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(10000), v -> (this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  345 */   public Setting<Boolean> damageSync = register(new Setting("DamageSync", Boolean.valueOf(false), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Pro)));
/*  346 */   public Setting<Boolean> preSynCheck = register(new Setting("Pre-SyncCheck", Boolean.valueOf(false), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  347 */   public Setting<Boolean> discreteSync = register(new Setting("Discrete-Sync", Boolean.valueOf(false), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  348 */   public Setting<Boolean> dangerSync = register(new Setting("Danger-Sync", Boolean.valueOf(false), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Pro)));
/*  349 */   public Setting<Integer> placeConfirm = register(new Setting("Place-Confirm", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  350 */   public Setting<Integer> breakConfirm = register(new Setting("Break-Confirm", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  351 */   public Setting<Integer> syncDelay = register(new Setting("SyncDelay", Integer.valueOf(500), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Pro)));
/*  352 */   public DamageSyncHelper damageSyncHelper = new DamageSyncHelper(this.discreteSync, this.syncDelay, this.dangerSync);
/*  353 */   public ForceAntiTotemHelper forceHelper = new ForceAntiTotemHelper(this.discreteSync, this.syncDelay, this.forcePlaceConfirm, this.forceBreakConfirm, this.dangerForce);
/*  354 */   public Setting<Boolean> surroundSync = register(new Setting("SurroundSync", Boolean.valueOf(true), v -> (this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  356 */   public Setting<Boolean> idPredict = register(new Setting("ID-Predict", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Pro)));
/*  357 */   public Setting<Integer> idOffset = register(new Setting("ID-Offset", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10), v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  358 */   public Setting<Integer> idDelay = register(new Setting("ID-Delay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(500), v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  359 */   public Setting<Integer> idPackets = register(new Setting("ID-Packets", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(10), v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  360 */   public Setting<Boolean> godAntiTotem = register(new Setting("God-AntiTotem", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  361 */   public Setting<Boolean> holdingCheck = register(new Setting("Holding-Check", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  362 */   public Setting<Boolean> toolCheck = register(new Setting("Tool-Check", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  363 */   public Setting<PlaceSwing> godSwing = register(new Setting("God-Swing", PlaceSwing.Once, v -> (this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  365 */   public Setting<PreCalc> preCalc = register(new Setting("Pre-Calc", PreCalc.None, v -> (this.page.getValue() == pages.Efficiency && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  366 */   public Setting<ExtrapolationType> preCalcExtra = register(new Setting("PreCalcExtra", ExtrapolationType.Place, v -> (this.page.getValue() == pages.Efficiency && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  367 */   public Setting<Float> preCalcDamage = register(new Setting("Pre-CalcDamage", Float.valueOf(15.0F), Float.valueOf(0.0F), Float.valueOf(36.0F), v -> (this.page.getValue() == pages.Efficiency && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  369 */   public Setting<Boolean> multiThread = register(new Setting("MultiThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  370 */   public Setting<Boolean> smartPost = register(new Setting("Smart-Post", Boolean.valueOf(true), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  371 */   public Setting<Boolean> mainThreadThreads = register(new Setting("MainThreadThreads", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  372 */   public Setting<RotationThread> rotationThread = register(new Setting("RotationThread", RotationThread.Predict, v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  373 */   public Setting<Float> partial = register(new Setting("Partial", Float.valueOf(0.8F), Float.valueOf(0.0F), Float.valueOf(1.0F), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  374 */   public Setting<Integer> maxCancel = register(new Setting("MaxCancel", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(50), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  375 */   public RotationCanceller rotationCanceller = new RotationCanceller(this, this.maxCancel);
/*  376 */   public Setting<Integer> timeOut = register(new Setting("Wait", Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(10), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  377 */   public Setting<Boolean> blockDestroyThread = register(new Setting("BlockDestroyThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  378 */   public Setting<Integer> threadDelay = register(new Setting("ThreadDelay", Integer.valueOf(25), Integer.valueOf(0), Integer.valueOf(100), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  379 */   public ThreadHelper threadHelper = new ThreadHelper(this, this.multiThread, this.mainThreadThreads, this.threadDelay, this.rotationThread, this.rotate);
/*  380 */   public Setting<Integer> tickThreshold = register(new Setting("TickThreshold", Integer.valueOf(5), Integer.valueOf(1), Integer.valueOf(20), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  381 */   public Setting<Integer> preSpawn = register(new Setting("PreSpawn", Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(20), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  382 */   public Setting<Integer> maxEarlyThread = register(new Setting("MaxEarlyThread", Integer.valueOf(8), Integer.valueOf(1), Integer.valueOf(20), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  383 */   public Setting<Integer> pullBasedDelay = register(new Setting("PullBasedDelay", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  384 */   public Setting<Boolean> explosionThread = register(new Setting("ExplosionThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  385 */   public Setting<Boolean> soundThread = register(new Setting("SoundThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  386 */   public Setting<Boolean> entityThread = register(new Setting("EntityThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  387 */   public Setting<Boolean> spawnThread = register(new Setting("SpawnThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  388 */   public Setting<Boolean> spawnThreadWhenAttacked = register(new Setting("SpawnThreadWhenAttacked", Boolean.valueOf(true), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  389 */   public Setting<Boolean> destroyThread = register(new Setting("DestroyThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  390 */   public Setting<Boolean> serverThread = register(new Setting("ServerThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  391 */   public Setting<Boolean> gameloop = register(new Setting("Gameloop", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  392 */   public Setting<Boolean> asyncServerThread = register(new Setting("AsyncServerThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  393 */   public Setting<Boolean> earlyFeetThread = register(new Setting("EarlyFeetThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  394 */   public Setting<Boolean> lateBreakThread = register(new Setting("LateBreakThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  395 */   public Setting<Boolean> motionThread = register(new Setting("MotionThread", Boolean.valueOf(true), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*  396 */   public Setting<Boolean> blockChangeThread = register(new Setting("BlockChangeThread", Boolean.valueOf(false), v -> (this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro)));
/*      */   
/*  398 */   public Setting<Integer> priority = register(new Setting("Priority", Integer.valueOf(1500), Integer.valueOf(-2147483648), Integer.valueOf(2147483647), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  399 */   public Setting<Boolean> spectator = register(new Setting("Spectator", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  400 */   public Setting<Boolean> noPacketFlyRotationChecks = register(new Setting("NoPacketFlyRotationChecks", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  401 */   public Setting<Boolean> clearPost = register(new Setting("ClearPost", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  402 */   public Setting<Boolean> sequential = register(new Setting("Sequential", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  403 */   public Setting<Integer> seqTime = register(new Setting("Seq-Time", Integer.valueOf(250), Integer.valueOf(0), Integer.valueOf(1000), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  404 */   public Setting<Boolean> endSequenceOnSpawn = register(new Setting("EndSequenceOnSpawn", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  405 */   public Setting<Boolean> endSequenceOnBreak = register(new Setting("EndSequenceOnBreak", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  406 */   public Setting<Boolean> endSequenceOnExplosion = register(new Setting("EndSequenceOnExplosion", Boolean.valueOf(true), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  407 */   public Setting<Boolean> antiPlaceFail = register(new Setting("AntiPlaceFail", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  408 */   public Setting<Boolean> debugAntiPlaceFail = register(new Setting("DebugAntiPlaceFail", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev)));
/*  409 */   public Setting<Boolean> alwaysBomb = register(new Setting("Always-Bomb", Boolean.valueOf(false), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*  410 */   public Setting<Integer> removeTime = register(new Setting("Remove-Time", Integer.valueOf(1000), Integer.valueOf(0), Integer.valueOf(2500), v -> (this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker)));
/*      */   
/*  412 */   public final Setting<ColorSetting> boxColor = register(new Setting("Box", new ColorSetting(1354711231)));
/*  413 */   public final Setting<ColorSetting> outLine = register(new Setting("Outline", new ColorSetting(1354711231)));
/*  414 */   public final Setting<ColorSetting> indicatorColor = register(new Setting("IndicatorColor", new ColorSetting(1354711231)));
/*      */   
/*  416 */   public ListenerSound soundObserver = new ListenerSound(this);
/*  417 */   public AtomicInteger motionID = new AtomicInteger();
/*  418 */   public Timer renderTimer = new Timer();
/*  419 */   public Timer bypassTimer = new Timer();
/*  420 */   public Timer obbyTimer = new Timer();
/*  421 */   public Timer obbyCalcTimer = new Timer();
/*  422 */   public Timer targetTimer = new Timer();
/*  423 */   public Timer cTargetTimer = new Timer();
/*  424 */   public Timer forceTimer = new Timer();
/*  425 */   public Timer liquidTimer = new Timer();
/*  426 */   public Timer shieldTimer = new Timer();
/*  427 */   public Timer slideTimer = new Timer();
/*  428 */   public Timer zoomTimer = new Timer();
/*  429 */   public Timer pullTimer = new Timer();
/*      */   
/*  431 */   public Queue<Runnable> post = new ConcurrentLinkedQueue<>();
/*      */   
/*      */   public volatile RotationFunction rotation;
/*      */   public BlockPos bombPos;
/*      */   public EntityPlayer target;
/*      */   public Entity crystal;
/*      */   public Entity focus;
/*      */   public BlockPos renderPos;
/*      */   public BlockPos slidePos;
/*      */   public boolean switching;
/*      */   public boolean isSpoofing;
/*      */   public boolean noGod;
/*      */   public String damage;
/*  444 */   public ExtrapolationHelper extrapolationHelper = new ExtrapolationHelper(this);
/*  445 */   public HelperLiquids liquidHelper = new HelperLiquids(this);
/*  446 */   public HelperPlace placeHelper = new HelperPlace(this);
/*  447 */   public HelperBreak breakHelper = new HelperBreak(this);
/*  448 */   public HelperObby obbyHelper = new HelperObby(this);
/*  449 */   public HelperBreakMotion breakHelperMotion = new HelperBreakMotion(this);
/*  450 */   public HelperEntityBlocksPlace bbBlockingHelper = new HelperEntityBlocksPlace(this);
/*      */   private BlockPos bypassPos;
/*  452 */   private float forward = 0.004F;
/*  453 */   public DamageHelper damageHelper = new DamageHelper(this, this.extrapolationHelper, this.terrainCalc, this.extrapol, this.bExtrapol, this.selfExtrapolation, this.obbyTerrain); private Timer inv_timer;
/*      */   private int prev_crystals_ammount;
/*      */   private int crys_speed;
/*      */   
/*  457 */   public AutoCrystal() { super("AutoCrystal", "ставит и ломает кристалы", "do you really need-an explanation?)", Module.Category.COMBAT);
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
/*  961 */     this.inv_timer = new Timer(); } public static boolean canBeFeetPlaced(EntityPlayer player, boolean ignoreCrystals, boolean noBoost2) { BlockPos origin = player.func_180425_c().func_177977_b(); for (EnumFacing face : EnumFacing.field_176754_o) { BlockPos off = origin.func_177972_a(face); IBlockState state = mc.field_71441_e.func_180495_p(off); if (ServerTimeHelper.canPlaceCrystal(off, ignoreCrystals, noBoost2)) return true;  BlockPos off2 = off.func_177972_a(face); if (ServerTimeHelper.canPlaceCrystal(off2, ignoreCrystals, noBoost2) && state.func_177230_c() == Blocks.field_150350_a) return true;  }  return false; } public static EntityPlayer getByFov(List<EntityPlayer> players, double maxRange) { EntityPlayer closest = null; double closestAngle = 360.0D; for (EntityPlayer player : players) { if (!ServerTimeHelper.isValid((Entity)player, maxRange)) continue;  double angle = RotationUtil.getAngle((Entity)player, 1.4D); if (angle < closestAngle && angle < (mc.field_71474_y.field_74334_X / 2.0F)) { closest = player; closestAngle = angle; }  }  return closest; } public static EntityPlayer getByAngle(List<EntityPlayer> players, double maxRange) { EntityPlayer closest = null; double closestAngle = 360.0D; for (EntityPlayer player : players) { if (!ServerTimeHelper.isValid((Entity)player, maxRange)) continue;  double angle = RotationUtil.getAngle((Entity)player, 1.4D); if (angle < closestAngle && angle < (mc.field_71474_y.field_74334_X / 2.0F)) { closest = player; closestAngle = angle; }  }  return closest; } public static AxisAlignedBB interpolatePos(BlockPos pos, float height) { return new AxisAlignedBB(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), (pos.func_177958_n() + 1), (pos.func_177956_o() + height), (pos.func_177952_p() + 1)); } public boolean isNotCheckingRotations() { return (((Boolean)this.noPacketFlyRotationChecks.getValue()).booleanValue() && ((PacketFly)Thunderhack.moduleManager.getModuleByClass(PacketFly.class)).isEnabled()); } public void onEnable() { resetModule(); Thunderhack.setDeadManager.addObserver((SoundObserver)this.soundObserver); } public void onDisable() { Thunderhack.setDeadManager.removeObserver((SoundObserver)this.soundObserver); resetModule(); } public String getDisplayInfo() { if (this.switching) return ChatFormatting.GREEN + "Switching";  String cps = (this.crys_speed * 2) + " c/s"; EntityPlayer t = getTarget(); return (t == null) ? null : (t.func_70005_c_() + " " + cps); } public void setRenderPos(BlockPos pos, float damage) { setRenderPos(pos, MathUtil.round(damage, 1) + ""); } public void setRenderPos(BlockPos pos, String text) { this.renderTimer.reset(); if (pos != null && !pos.equals(this.slidePos) && (!((Boolean)this.smoothSlide.getValue()).booleanValue() || this.slideTimer.passedMs(((Integer)this.slideTime.getValue()).intValue()))) { this.slidePos = this.renderPos; this.slideTimer.reset(); }  if (pos != null && (((Boolean)this.multiZoom.getValue()).booleanValue() || !pos.equals(this.renderPos))) this.zoomTimer.reset();  this.renderPos = pos; this.damage = text; this.bypassPos = null; } public BlockPos getRenderPos() { if (this.renderTimer.passedMs(((Integer)this.renderTime.getValue()).intValue())) { this.renderPos = null; this.slidePos = null; }  return this.renderPos; } public EntityPlayer getTarget() { if (this.targetTimer.passedMs(600L)) this.target = null;  return this.target; } public void setTarget(EntityPlayer target) { this.targetTimer.reset(); this.target = target; } public Entity getCrystal() { if (this.cTargetTimer.passedMs(600L)) this.crystal = null;  return this.crystal; } public void setCrystal(Entity crystal) { if (((Boolean)this.focusRotations.getValue()).booleanValue() && !noRotateNigga(ACRotate.Break)) this.focus = crystal;  this.cTargetTimer.reset(); this.crystal = crystal; }
/*      */   public float getMinDamage() { return ((((Boolean)this.holdFacePlace.getValue()).booleanValue() && mc.field_71462_r == null && Mouse.isButtonDown(0) && (!(mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemPickaxe) || ((Boolean)this.pickAxeHold.getValue()).booleanValue())) || ((Boolean)this.dangerFacePlace.getValue()).booleanValue()) ? ((Float)this.minFaceDmg.getValue()).floatValue() : ((Float)this.minDamage.getValue()).floatValue(); }
/*      */   public void runPost() { CollectionUtil.emptyQueue(this.post); }
/*      */   public void resetModule() { this.target = null; this.crystal = null; this.renderPos = null; this.slidePos = null; this.rotation = null; this.switching = false; this.bypassPos = null; this.post.clear(); mc.func_152344_a(this.crystalRender::clear); try { this.placed.clear(); this.threadHelper.resetThreadHelper(); this.rotationCanceller.reset(); this.antiTotemHelper.setTarget(null); this.antiTotemHelper.setTargetPos(null); this.idHelper.setUpdated(false); this.idHelper.setHighestID(0); } catch (Throwable t) { t.printStackTrace(); }  }
/*      */   public boolean shouldDanger() { return (((Boolean)this.dangerSpeed.getValue()).booleanValue() && EntityUtil.getHealth((Entity)mc.field_71439_g) < ((Float)this.dangerHealth.getValue()).floatValue()); }
/*      */   public void checkExecutor() { if (!started && ((Boolean)this.asyncServerThread.getValue()).booleanValue() && ((Boolean)this.serverThread.getValue()).booleanValue() && ((Boolean)this.multiThread.getValue()).booleanValue() && this.rotate.getValue() == ACRotate.None) synchronized (AutoCrystal.class) { if (!ATOMIC_STARTED.get()) { startExecutor(); ATOMIC_STARTED.set(true); started = true; }  }   }
/*  967 */   public void onTick() { if (this.inv_timer.passedMs(500L)) {
/*  968 */       this.crys_speed = this.prev_crystals_ammount - InventoryUtil.getItemCount(Items.field_185158_cP);
/*  969 */       this.prev_crystals_ammount = InventoryUtil.getItemCount(Items.field_185158_cP);
/*  970 */       this.inv_timer.reset();
/*      */     } 
/*      */     
/*  973 */     checkExecutor();
/*  974 */     this.placed.values().removeIf(stamp -> (System.currentTimeMillis() - stamp.getTimeStamp() > ((Integer)this.removeTime.getValue()).intValue()));
/*      */ 
/*      */ 
/*      */     
/*  978 */     this.crystalRender.tick();
/*  979 */     if (!this.idHelper.isUpdated()) {
/*  980 */       this.idHelper.update();
/*  981 */       this.idHelper.setUpdated(true);
/*      */     } 
/*  983 */     this.weaknessHelper.updateWeakness(); }
/*      */   private void startExecutor() { EXECUTOR.scheduleAtFixedRate((Runnable)this::doExecutorTick, 0L, 1L, TimeUnit.MILLISECONDS); }
/*      */   private void doExecutorTick() { if (mc.field_71439_g != null && mc.field_71441_e != null && ((Boolean)this.asyncServerThread.getValue()).booleanValue() && this.rotate.getValue() == ACRotate.None && ((Boolean)this.serverThread.getValue()).booleanValue() && ((Boolean)this.multiThread.getValue()).booleanValue()) if (Thunderhack.servtickManager.valid(Thunderhack.servtickManager.getTickTimeAdjusted(), Thunderhack.servtickManager.normalize(Thunderhack.servtickManager.getSpawnTime() - ((Integer)this.tickThreshold.getValue()).intValue()), Thunderhack.servtickManager.normalize(Thunderhack.servtickManager.getSpawnTime() - ((Integer)this.preSpawn.getValue()).intValue()))) { if (!((Boolean)this.earlyFeetThread.getValue()).booleanValue()) { this.threadHelper.startThread(new BlockPos[0]); } else if (((Boolean)this.lateBreakThread.getValue()).booleanValue()) { this.threadHelper.startThread(true, false, new BlockPos[0]); }  } else { EntityPlayer closest = ServerTimeHelper.getClosestEnemy(); if (closest != null && ServerTimeHelper.isSemiSafe(closest, true, ((Boolean)this.newVer.getValue()).booleanValue()) && canBeFeetPlaced(closest, true, ((Boolean)this.newVer.getValue()).booleanValue()) && ((Boolean)this.earlyFeetThread.getValue()).booleanValue() && Thunderhack.servtickManager.valid(Thunderhack.servtickManager.getTickTimeAdjusted(), 0, ((Integer)this.maxEarlyThread.getValue()).intValue())) this.threadHelper.startThread(false, true, new BlockPos[0]);  }   }
/*      */   public boolean isSuicideModule() { return false; }
/*      */   public BlockPos getBypassPos() { if (this.bypassTimer.passedMs(((Integer)this.bypassRotationTime.getValue()).intValue()) || !((Boolean)this.forceBypass.getValue()).booleanValue() || !((Boolean)this.rayTraceBypass.getValue()).booleanValue()) this.bypassPos = null;  return this.bypassPos; }
/*      */   public void setBypassPos(BlockPos pos) { this.bypassTimer.reset(); this.bypassPos = pos; }
/*  989 */   public boolean isEating() { ItemStack stack = mc.field_71439_g.func_184607_cu(); return (mc.field_71439_g.func_184587_cr() && !stack.func_190926_b() && stack.func_77973_b().func_77661_b(stack) == EnumAction.EAT); } public boolean isMining() { return mc.field_71442_b.func_181040_m(); } public boolean isOutsidePlaceRange(BlockPos pos) { EntityPlayerSP entityPlayerSP = mc.field_71439_g; double x = ((EntityPlayer)entityPlayerSP).field_70165_t; double y = ((EntityPlayer)entityPlayerSP).field_70163_u + (((Boolean)this.placeRangeEyes.getValue()).booleanValue() ? entityPlayerSP.func_70047_e() : 0.0F); double z = ((EntityPlayer)entityPlayerSP).field_70161_v; double distance = ((Boolean)this.placeRangeCenter.getValue()).booleanValue() ? pos.func_177957_d(x, y, z) : pos.func_177954_c(x, y, z); return (distance >= MathUtil.square(((Float)this.placeRange.getValue()).floatValue())); } public int getDeathTime() { if (((Boolean)this.useSafeDeathTime.getValue()).booleanValue()) return ((Integer)this.safeDeathTime.getValue()).intValue();  if (!((Boolean)this.pseudoSetDead.getValue()).booleanValue() && !((Boolean)this.setDead.getValue()).booleanValue()) return 0;  return ((Integer)this.deathTime.getValue()).intValue(); } public boolean noRotateNigga(ACRotate rotate2) { switch ((ACRotate)this.rotate.getValue()) { case INVALID: return true;case ROTATIONS: return false;case VALID: return (rotate2 == ACRotate.Break || rotate2 == ACRotate.None);case null: return (rotate2 == ACRotate.Place || rotate2 == ACRotate.None); }  return false; } private void updater228(PacketEvent.Send event) { if (((Boolean)this.multiThread.getValue()).booleanValue() && !this.isSpoofing && this.rotate.getValue() != ACRotate.None && this.rotationThread.getValue() == RotationThread.Cancel)
/*  990 */     { this.rotationCanceller.onPacketNigger(event); }
/*      */     else
/*  992 */     { this.rotationCanceller.reset(); }  }
/*      */   public boolean shouldCalcFuckinBitch(AntiFriendPop type) { switch ((AntiFriendPop)this.antiFriendPop.getValue()) { case INVALID: return false;case ROTATIONS: return true;case VALID: return (type == AntiFriendPop.Break);case null: return (type == AntiFriendPop.Place); }  return false; }
/*      */   public boolean shouldcalcN() { switch ((Attack2)this.attackMode.getValue()) { case INVALID: return true;case ROTATIONS: return true;case VALID: return InventoryUtil.isHolding(Items.field_185158_cP); }  return true; }
/*      */   public boolean shouldattackN() { switch ((Attack2)this.attackMode.getValue()) { case INVALID: return InventoryUtil.isHolding(Items.field_185158_cP);case ROTATIONS: return true;case VALID: return InventoryUtil.isHolding(Items.field_185158_cP); }  return true; }
/*      */   public boolean isOutsideBreakRange(double x, double y, double z, AutoCrystal module) { switch ((SmartRange)this.placeBreakRange.getValue()) { case INVALID: return (!module.rangeHelper.isCrystalInRange(x, y, z, ((Integer)module.smartTicks.getValue()).intValue()) && !module.rangeHelper.isCrystalInRange(x, y, z, 0));case ROTATIONS: return false;case VALID: return !module.rangeHelper.isCrystalInRange(x, y, z, 0);case null: return !module.rangeHelper.isCrystalInRange(x, y, z, ((Integer)module.smartTicks.getValue()).intValue()); }  return (!module.rangeHelper.isCrystalInRange(x, y, z, ((Integer)module.smartTicks.getValue()).intValue()) && !module.rangeHelper.isCrystalInRange(x, y, z, 0)); }
/*      */   public boolean isOutsideBreakRange(BlockPos pos, AutoCrystal module) { return isOutsideBreakRange((pos.func_177958_n() + 0.5F), (pos.func_177956_o() + 1), (pos.func_177952_p() + 0.5F), module); }
/*  998 */   @SubscribeEvent public void onBoobs(UpdateEntitiesEvent e) { ExtrapolationHelper.onUpdateEntity(e); } public void onLogin() { resetModule(); } @SubscribeEvent public void onPacketReceive(PacketEvent.Receive e) { if (fullNullCheck()) return;  this.threadHelper.schedulePacket(e); if (e.getPacket() instanceof SPacketBlockChange && ((Boolean)this.multiThread.getValue()).booleanValue() && ((Boolean)this.blockChangeThread.getValue()).booleanValue()) { SPacketBlockChange packet = (SPacketBlockChange)e.getPacket(); if (packet.func_180728_a().func_177230_c() == Blocks.field_150350_a && mc.field_71439_g.func_174818_b(packet.func_179827_b()) < 40.0D) e.addPostEvent(() -> { if (mc.field_71441_e != null && HelperUtil.validChange(packet.func_179827_b(), mc.field_71441_e.field_73010_i)) this.threadHelper.startThread(new BlockPos[0]);  });  }  if (e.getPacket() instanceof SPacketMultiBlockChange && ((Boolean)this.multiThread.getValue()).booleanValue() && ((Boolean)this.blockChangeThread.getValue()).booleanValue()) { SPacketMultiBlockChange packet = (SPacketMultiBlockChange)e.getPacket(); e.addPostEvent(() -> { for (SPacketMultiBlockChange.BlockUpdateData data : packet.func_179844_a()) { if (data.func_180088_c().func_185904_a() == Material.field_151579_a && HelperUtil.validChange(data.func_180090_a(), mc.field_71441_e.field_73010_i)) { this.threadHelper.startThread(new BlockPos[0]); break; }  }  }); }  if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketDestroyEntities && ((Boolean)this.destroyThread.getValue()).booleanValue()) this.threadHelper.schedulePacket(e);  if (e.getPacket() instanceof SPacketEntity.S15PacketEntityRelMove) onEvent22((SPacketEntity)e.getPacket());  if (e.getPacket() instanceof SPacketEntity.S17PacketEntityLookMove) onEvent22((SPacketEntity)e.getPacket());  if (e.getPacket() instanceof SPacketExplosion && ((Boolean)this.explosionThread.getValue()).booleanValue() && !((SPacketExplosion)e.getPacket()).func_149150_j().isEmpty()) this.threadHelper.schedulePacket(e);  if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) this.rotationCanceller.drop();  if (e.getPacket() instanceof SPacketSpawnObject) try { onEvent33(e); } catch (Throwable t) { t.printStackTrace(); }   if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketSoundEffect && ((Boolean)this.soundThread.getValue()).booleanValue()) this.threadHelper.startThread(new BlockPos[0]);  } @SubscribeEvent public void onPacketSend(PacketEvent.Send event) { if (event.getPacket() instanceof CPacketPlayer) updater228(event);  if (event.getPacket() instanceof CPacketPlayer.Position) updater228(event);  if (event.getPacket() instanceof CPacketPlayer.Rotation) updater228(event);  if (event.getPacket() instanceof CPacketPlayer.PositionRotation) updater228(event);  } @SubscribeEvent public void onPacketSendPost(PacketEvent.SendPost event) { if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && ((Boolean)this.idPredict.getValue()).booleanValue() && !this.noGod && this.breakTimer.passed(((Integer)this.breakDelay.getValue()).intValue()) && mc.field_71439_g.func_184586_b(((CPacketPlayerTryUseItemOnBlock)event.getPacket()).func_187022_c()).func_77973_b() == Items.field_185158_cP && this.idHelper.isSafe(mc.field_71441_e.field_73010_i, ((Boolean)this.holdingCheck.getValue()).booleanValue(), ((Boolean)this.toolCheck.getValue()).booleanValue())) { this.idHelper.attack((SwingTime)this.breakSwing.getValue(), (PlaceSwing)this.godSwing.getValue(), ((Integer)this.idOffset.getValue()).intValue(), ((Integer)this.idPackets.getValue()).intValue(), ((Integer)this.idDelay.getValue()).intValue()); this.breakTimer.reset(((Integer)this.breakDelay.getValue()).intValue()); }  if (event.getPacket() instanceof CPacketUseEntity) { Entity entity = null; if (entity == null) { entity = ((CPacketUseEntity)event.getPacket()).func_149564_a((World)mc.field_71441_e); if (entity == null) return;  }  this.serverTimeHelper.onUseEntity((CPacketUseEntity)event.getPacket(), entity); }  } @SubscribeEvent public void onDestroyBlock(DestroyBlockEvent event) { if (((Boolean)this.blockDestroyThread.getValue()).booleanValue() && ((Boolean)this.multiThread
/*  999 */       .getValue()).booleanValue() && 
/* 1000 */       !event.isCanceled() && 
/* 1001 */       HelperUtil.validChange(event.getBlockPos(), mc.field_71441_e.field_73010_i)) {
/* 1002 */       this.threadHelper.startThread(new BlockPos[] { event.getBlockPos().func_177977_b() });
/*      */     } }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onGameZaloop(GameZaloopEvent event) {
/* 1008 */     this.rotationCanceller.onGameLoop();
/* 1009 */     if (!((Boolean)this.multiThread.getValue()).booleanValue()) {
/*      */       return;
/*      */     }
/*      */     
/* 1013 */     if (((Boolean)this.gameloop.getValue()).booleanValue()) {
/* 1014 */       this.threadHelper.startThread(new BlockPos[0]);
/* 1015 */     } else if (this.rotate.getValue() != ACRotate.None && this.rotationThread.getValue() == RotationThread.Predict && mc.func_184121_ak() >= ((Float)this.partial.getValue()).floatValue()) {
/* 1016 */       this.threadHelper.startThread(new BlockPos[0]);
/* 1017 */     } else if (this.rotate.getValue() == ACRotate.None && ((Boolean)this.serverThread
/* 1018 */       .getValue()).booleanValue() && mc.field_71441_e != null && mc.field_71439_g != null) {
/*      */ 
/*      */       
/* 1021 */       if (Thunderhack.servtickManager.valid(Thunderhack.servtickManager
/* 1022 */           .getTickTimeAdjusted(), Thunderhack.servtickManager
/* 1023 */           .normalize(Thunderhack.servtickManager.getSpawnTime() - ((Integer)this.tickThreshold
/* 1024 */             .getValue()).intValue()), Thunderhack.servtickManager
/* 1025 */           .normalize(Thunderhack.servtickManager.getSpawnTime() - ((Integer)this.preSpawn
/* 1026 */             .getValue()).intValue()))) {
/* 1027 */         if (!((Boolean)this.earlyFeetThread.getValue()).booleanValue()) {
/* 1028 */           this.threadHelper.startThread(new BlockPos[0]);
/* 1029 */         } else if (((Boolean)this.lateBreakThread.getValue()).booleanValue()) {
/* 1030 */           this.threadHelper.startThread(true, false, new BlockPos[0]);
/*      */         } 
/* 1032 */       } else if (ServerTimeHelper.getClosestEnemy() != null && 
/* 1033 */         ServerTimeHelper.isSemiSafe(ServerTimeHelper.getClosestEnemy(), true, ((Boolean)this.newVer.getValue()).booleanValue()) && 
/* 1034 */         canBeFeetPlaced(ServerTimeHelper.getClosestEnemy(), true, ((Boolean)this.newVer.getValue()).booleanValue()) && ((Boolean)this.earlyFeetThread
/* 1035 */         .getValue()).booleanValue() && Thunderhack.servtickManager
/* 1036 */         .valid(Thunderhack.servtickManager.getTickTimeAdjusted(), 0, ((Integer)this.maxEarlyThread.getValue()).intValue())) {
/* 1037 */         this.threadHelper.startThread(false, true, new BlockPos[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onKeyBoard(KeyboardEvent event) {
/* 1044 */     if (event.getEventState() && event
/* 1045 */       .getKey() == ((SubBind)this.switchBind.getValue()).getKey()) {
/* 1046 */       if (((Boolean)this.useAsOffhand.getValue()).booleanValue()) {
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
/* 1065 */         this.switching = false;
/* 1066 */       } else if (this.autoSwitch.getValue() == AutoSwitch.Bind) {
/* 1067 */         this.switching = !this.switching;
/* 1068 */         if (((Boolean)this.switchMessage.getValue()).booleanValue()) {
/* 1069 */           Command.sendMessage(this.switching ? (TextFormatting.GREEN + "Switch on") : (TextFormatting.RED + "Switch off"));
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void nigga(EventSync event) {
/* 1078 */     if (!((Boolean)this.multiThread.getValue()).booleanValue() && ((Boolean)this.motionCalc
/* 1079 */       .getValue()).booleanValue() && (Thunderhack.positionManager
/* 1080 */       .getX() != mc.field_71439_g.field_70165_t || Thunderhack.positionManager
/* 1081 */       .getY() != mc.field_71439_g.field_70163_u || Thunderhack.positionManager
/* 1082 */       .getZ() != mc.field_71439_g.field_70161_v)) {
/* 1083 */       CalculationMotion calc = new CalculationMotion(this, mc.field_71441_e.field_72996_f, mc.field_71441_e.field_73010_i);
/*      */ 
/*      */       
/* 1086 */       this.threadHelper.start((AbstractCalculation)calc, false);
/*      */     }
/* 1088 */     else if (((Boolean)this.motionThread.getValue()).booleanValue()) {
/* 1089 */       this.threadHelper.startThread(new BlockPos[0]);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1094 */     AbstractCalculation<?> current = this.threadHelper.getCurrentCalc();
/* 1095 */     if (current != null && 
/* 1096 */       !current.isFinished() && this.rotate
/* 1097 */       .getValue() != ACRotate.None && this.rotationThread
/* 1098 */       .getValue() == RotationThread.Wait) {
/* 1099 */       synchronized (this) {
/*      */         try {
/* 1101 */           wait(((Integer)this.timeOut.getValue()).intValue());
/* 1102 */         } catch (InterruptedException e) {
/* 1103 */           Command.sendMessage("Minecraft Main-Thread interrupted!");
/* 1104 */           Thread.currentThread().interrupt();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1109 */     RotationFunction rotation = this.rotation;
/* 1110 */     if (rotation != null) {
/* 1111 */       this.isSpoofing = true;
/* 1112 */       float[] rotations = rotation.apply(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, event
/*      */ 
/*      */           
/* 1115 */           .getYaw(), event
/* 1116 */           .getPitch());
/*      */       
/* 1118 */       if (this.rotateMode.getValue() == RotateMode.Smooth) {
/* 1119 */         float yaw = this.yawMouseFilter.func_76333_a(rotations[0] + MathUtil.random(-1.0F, 5.0F), ((Float)this.smoothSpeed.getValue()).floatValue());
/* 1120 */         float pitch = this.pitchMouseFilter.func_76333_a(rotations[1] + MathUtil.random(-1.2F, 3.5F), ((Float)this.smoothSpeed.getValue()).floatValue());
/* 1121 */         mc.field_71439_g.field_70177_z = yaw;
/* 1122 */         mc.field_71439_g.field_70125_A = pitch;
/*      */       } else {
/* 1124 */         mc.field_71439_g.field_70177_z = rotations[0];
/* 1125 */         mc.field_71439_g.field_70125_A = rotations[1];
/*      */       } 
/* 1127 */     } else if (((Boolean)this.rayTraceBypass.getValue()).booleanValue() && ((Boolean)this.forceBypass
/* 1128 */       .getValue()).booleanValue()) {
/* 1129 */       BlockPos bypassPos = getBypassPos();
/* 1130 */       if (bypassPos != null) {
/*      */         
/* 1132 */         float[] rotations = RotationUtil.getRotationsToTopMiddleUp(bypassPos);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1138 */         float pitch = (rotations[1] == 0.0F && ((Float)this.rbYaw.getValue()).floatValue() != 0.0F) ? 0.0F : ((rotations[1] < 0.0F) ? (rotations[1] + ((Float)this.rbPitch.getValue()).floatValue()) : (rotations[1] - ((Float)this.rbPitch.getValue()).floatValue()));
/*      */         
/* 1140 */         mc.field_71439_g.field_70177_z = (rotations[0] + ((Float)this.rbYaw.getValue()).floatValue()) % 360.0F;
/* 1141 */         mc.field_71439_g.field_70125_A = pitch;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onPostMotion(EventPostSync e) {
/* 1148 */     this.motionID.incrementAndGet();
/* 1149 */     synchronized (this.post) {
/* 1150 */       runPost();
/*      */     } 
/*      */     
/* 1153 */     this.isSpoofing = false;
/*      */   }
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onMotion(NoMotionUpdateEvent event) {
/* 1158 */     if (((Boolean)this.multiThread.getValue()).booleanValue() && !this.isSpoofing && this.rotate
/*      */       
/* 1160 */       .getValue() != ACRotate.None && this.rotationThread
/* 1161 */       .getValue() == RotationThread.Cancel) {
/* 1162 */       this.forward = -this.forward;
/* 1163 */       float yaw = Thunderhack.rotationManager.getServerYaw() + this.forward;
/* 1164 */       float pitch = Thunderhack.rotationManager.getServerPitch() + this.forward;
/*      */       
/* 1166 */       this.rotationCanceller.onPacketNigger9(new CPacketPlayer.Rotation(yaw, pitch, Thunderhack.positionManager.isOnGround()));
/*      */     } else {
/* 1168 */       this.rotationCanceller.reset();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onEvent22(SPacketEntity packet) {
/* 1173 */     if (!shouldCalc22()) {
/*      */       return;
/*      */     }
/*      */     
/* 1177 */     EntityPlayer p = null;
/* 1178 */     if (mc.field_71441_e.func_73045_a(((ISPacketEntity)packet).getEntityId()) instanceof EntityPlayer)
/* 1179 */       p = (EntityPlayer)mc.field_71441_e.func_73045_a(((ISPacketEntity)packet).getEntityId()); 
/* 1180 */     if (p == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1184 */     double x = (p.field_70118_ct + packet.func_186952_a()) / 4096.0D;
/* 1185 */     double y = (p.field_70117_cu + packet.func_186953_b()) / 4096.0D;
/* 1186 */     double z = (p.field_70116_cv + packet.func_186951_c()) / 4096.0D;
/*      */     
/* 1188 */     onEvent22(p, x, y, z);
/*      */   }
/*      */   
/*      */   protected void onEvent22(EntityPlayer player, double x, double y, double z) {
/* 1192 */     EntityPlayer entityPlayer = RotationUtil.getRotationPlayer();
/* 1193 */     if (entityPlayer != null && entityPlayer
/* 1194 */       .func_70092_e(x, y, z) < 
/* 1195 */       MathUtil.square(((Float)this.targetRange.getValue()).floatValue()) && 
/* 1196 */       !Thunderhack.friendManager.isFriend(player))
/*      */     {
/* 1198 */       Scheduler.getInstance().scheduleAsynchronously(() -> {
/*      */             if (mc.field_71441_e == null) {
/*      */               return;
/*      */             }
/*      */             List<EntityPlayer> enemies = Collections.emptyList();
/*      */             EntityPlayer target = getTTRG(mc.field_71441_e.field_73010_i, enemies, (Float)this.targetRange.getValue());
/*      */             if (target == null || target.equals(player)) {
/*      */               this.threadHelper.startThread(new BlockPos[0]);
/*      */             }
/*      */           });
/*      */     }
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
/*      */   public EntityPlayer getTTRG(List<EntityPlayer> players, List<EntityPlayer> enemies, Float maxRange) {
/*      */     EntityPlayer enemy;
/* 1221 */     switch ((Target)this.targetMode.getValue()) {
/*      */       case INVALID:
/* 1223 */         enemy = getByFov(enemies, maxRange.floatValue());
/* 1224 */         if (enemy == null) {
/* 1225 */           return getByFov(players, maxRange.floatValue());
/*      */         }
/* 1227 */         return enemy;
/*      */       
/*      */       case ROTATIONS:
/* 1230 */         enemy = getByAngle(enemies, maxRange.floatValue());
/* 1231 */         return (enemy == null) ? getByAngle(players, maxRange.floatValue()) : enemy;
/*      */       
/*      */       case VALID:
/* 1234 */         return null;
/*      */       
/*      */       case null:
/* 1237 */         return ServerTimeHelper.getClosestEnemy(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, maxRange
/*      */ 
/*      */             
/* 1240 */             .floatValue(), enemies, players);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1245 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean shouldCalc22() {
/* 1251 */     return (((Boolean)this.multiThread.getValue()).booleanValue() && ((Boolean)this.entityThread
/* 1252 */       .getValue()).booleanValue() && (this.rotate
/* 1253 */       .getValue() == ACRotate.None || this.rotationThread
/* 1254 */       .getValue() != RotationThread.Predict));
/*      */   }
/*      */   
/*      */   protected EntityPlayer getEntity22(int id) {
/* 1258 */     List<Entity> entities = mc.field_71441_e.field_72996_f;
/* 1259 */     if (entities == null) {
/* 1260 */       return null;
/*      */     }
/*      */     
/* 1263 */     Entity entity = null;
/* 1264 */     for (Entity e : entities) {
/* 1265 */       if (e != null && e.func_145782_y() == id) {
/* 1266 */         entity = e;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1271 */     if (entity instanceof EntityPlayer) {
/* 1272 */       return (EntityPlayer)entity;
/*      */     }
/*      */     
/* 1275 */     return null;
/*      */   }
/*      */   
/*      */   private void onEvent33(PacketEvent.Receive event) {
/* 1279 */     WorldClient worldClient = mc.field_71441_e;
/* 1280 */     if (mc.field_71439_g == null || worldClient == null || ((Boolean)this.basePlaceOnly
/*      */       
/* 1282 */       .getValue()).booleanValue() || ((SPacketSpawnObject)event
/* 1283 */       .getPacket()).func_148993_l() != 51 || mc.field_71441_e == null || (
/*      */       
/* 1285 */       !((Boolean)this.spectator.getValue()).booleanValue() && mc.field_71439_g.func_175149_v()) || (((Boolean)this.stopWhenEating
/* 1286 */       .getValue()).booleanValue() && isEating()) || (((Boolean)this.stopWhenMining
/* 1287 */       .getValue()).booleanValue() && isMining()) || ((ISPacketSpawnObject)event
/* 1288 */       .getPacket()).isAttacked()) {
/*      */       return;
/*      */     }
/*      */     
/* 1292 */     SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
/* 1293 */     double x = packet.func_186880_c();
/* 1294 */     double y = packet.func_186882_d();
/* 1295 */     double z = packet.func_186881_e();
/* 1296 */     EntityEnderCrystal entity = new EntityEnderCrystal((World)worldClient, x, y, z);
/*      */     
/* 1298 */     if (((Integer)this.simulatePlace.getValue()).intValue() != 0) {
/* 1299 */       event.addPostEvent(() -> {
/*      */             if (mc.field_71441_e == null) {
/*      */               return;
/*      */             }
/*      */             
/*      */             Entity e = mc.field_71441_e.func_73045_a(packet.func_149001_c());
/*      */             
/*      */             if (e instanceof EntityEnderCrystal) {
/*      */               this.crystalRender.onSpawn((EntityEnderCrystal)e);
/*      */             }
/*      */           });
/*      */     }
/*      */     
/* 1312 */     if (!((Boolean)this.instant.getValue()).booleanValue() || !this.breakTimer.passed(((Integer)this.breakDelay.getValue()).intValue())) {
/*      */       return;
/*      */     }
/*      */     
/* 1316 */     BlockPos pos = new BlockPos(x, y, z);
/* 1317 */     CrystalTimeStamp stamp = this.placed.get(pos);
/* 1318 */     entity.func_184517_a(false);
/* 1319 */     entity.func_145769_d(packet.func_149001_c());
/* 1320 */     entity.func_184221_a(packet.func_186879_b());
/*      */     
/* 1322 */     boolean attacked = false;
/* 1323 */     if ((!((Boolean)this.alwaysCalc.getValue()).booleanValue() || (pos.equals(this.bombPos) && ((Boolean)this.alwaysBomb.getValue()).booleanValue())) && stamp != null && stamp.isValid() && (stamp.getDamage() > ((Float)this.slowBreakDamage.getValue()).floatValue() || stamp.isShield() || this.breakTimer.passed(((Integer)this.slowBreakDelay.getValue()).intValue()) || pos.func_177977_b().equals(this.antiTotemHelper.getTargetPos()))) {
/* 1324 */       if (pos.equals(this.bombPos)) {
/* 1325 */         this.bombPos = null;
/*      */       }
/*      */       
/* 1328 */       float damage = checkPos((Entity)entity);
/* 1329 */       if (damage <= -1000.0F) {
/* 1330 */         MutableWrapper<Boolean> a = new MutableWrapper(Boolean.valueOf(false));
/* 1331 */         this.rotation = this.rotationHelper.forBreaking((Entity)entity, a);
/* 1332 */         event.addPostEvent(() -> {
/*      */               if (mc.field_71441_e != null) {
/*      */                 Entity e = mc.field_71441_e.func_73045_a(packet.func_149001_c());
/*      */                 
/*      */                 if (e != null) {
/*      */                   this.post.add(this.rotationHelper.post(e, a));
/*      */                   
/*      */                   this.rotation = this.rotationHelper.forBreaking(e, a);
/*      */                   
/*      */                   setCrystal(e);
/*      */                 } 
/*      */               } 
/*      */             });
/*      */         return;
/*      */       } 
/* 1347 */       if (damage < 0.0F) {
/*      */         return;
/*      */       }
/*      */       
/* 1351 */       if (damage > ((Float)this.shieldSelfDamage.getValue()).floatValue() && stamp.isShield()) {
/*      */         return;
/*      */       }
/* 1354 */       attack(packet, event, entity, (stamp.getDamage() <= ((Float)this.slowBreakDamage.getValue()).floatValue()));
/* 1355 */       attacked = true;
/* 1356 */     } else if (((Boolean)this.asyncCalc.getValue()).booleanValue() || ((Boolean)this.alwaysCalc.getValue()).booleanValue()) {
/* 1357 */       List<EntityPlayer> players = mc.field_71441_e.field_73010_i;
/* 1358 */       if (players == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1362 */       float self = checkPos((Entity)entity);
/* 1363 */       if (self < 0.0F) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1368 */       boolean slow = true;
/* 1369 */       boolean attack = false;
/* 1370 */       for (EntityPlayer player : players) {
/* 1371 */         if (player == null || 
/* 1372 */           EntityUtil.isDead((Entity)player) || player
/* 1373 */           .func_70092_e(x, y, z) > 144.0D) {
/*      */           continue;
/*      */         }
/*      */         
/* 1377 */         if (Thunderhack.friendManager.isFriend(player) && (!isSuicideModule() || !player.equals(mc.field_71439_g))) {
/* 1378 */           if (shouldCalcFuckinBitch(AntiFriendPop.Break) && 
/* 1379 */             this.damageHelper.getDamage((Entity)entity, (EntityLivingBase)player) > EntityUtil.getHealth((Entity)player) - 0.5F) {
/* 1380 */             attack = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1388 */         float dmg = this.damageHelper.getDamage((Entity)entity, (EntityLivingBase)player);
/* 1389 */         if ((dmg > self || (((Boolean)this.suicide
/* 1390 */           .getValue()).booleanValue() && dmg >= ((Float)this.minDamage
/* 1391 */           .getValue()).floatValue())) && dmg > ((Float)this.minBreakDamage
/* 1392 */           .getValue()).floatValue() && (dmg > ((Float)this.slowBreakDamage
/* 1393 */           .getValue()).floatValue() || 
/* 1394 */           shouldDanger() || this.breakTimer
/* 1395 */           .passed(((Integer)this.slowBreakDelay
/* 1396 */             .getValue()).intValue()))) {
/* 1397 */           slow = (slow && dmg <= ((Float)this.slowBreakDamage.getValue()).floatValue());
/* 1398 */           attack = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1402 */       if (attack) {
/*      */         
/* 1404 */         attack(packet, event, entity, ((stamp == null || !stamp.isShield()) && slow));
/* 1405 */         attacked = true;
/* 1406 */       } else if (stamp != null && stamp
/* 1407 */         .isShield() && self >= 0.0F && self <= ((Float)this.shieldSelfDamage
/*      */         
/* 1409 */         .getValue()).floatValue()) {
/* 1410 */         attack(packet, event, entity, false);
/* 1411 */         attacked = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1415 */     if (((Boolean)this.spawnThread.getValue()).booleanValue() && (!((Boolean)this.spawnThreadWhenAttacked.getValue()).booleanValue() || attacked)) {
/* 1416 */       this.threadHelper.schedulePacket(event);
/*      */     }
/*      */   }
/*      */   
/*      */   private void attack(SPacketSpawnObject packet, PacketEvent.Receive event, EntityEnderCrystal entityIn, boolean slow) {
/* 1421 */     HelperInstantAttack.attack(this, packet, event, entityIn, slow);
/*      */   }
/*      */   private float checkPos(Entity entity) {
/*      */     float damage;
/* 1425 */     BreakValidity validity = HelperUtil.isValid(this, entity, true);
/* 1426 */     switch (validity) {
/*      */       
/*      */       case INVALID:
/* 1429 */         return -1.0F;
/*      */       case ROTATIONS:
/* 1431 */         damage = getSelfDamage(entity);
/* 1432 */         if (damage < 0.0F) {
/* 1433 */           return damage;
/*      */         }
/*      */         
/* 1436 */         return -1000.0F - damage;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1441 */     return getSelfDamage(entity);
/*      */   }
/*      */   
/*      */   private float getSelfDamage(Entity entity) {
/* 1445 */     float damage = this.damageHelper.getDamage(entity);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1451 */     return (damage > ((Float)this.maxSelfBreak.getValue()).floatValue() || (damage > 
/* 1452 */       EntityUtil.getHealth((Entity)mc.field_71439_g) - 1.0F && 
/* 1453 */       !((Boolean)this.suicide.getValue()).booleanValue())) ? -1.0F : damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onRender3D(Render3DEvent event) {
/* 1461 */     if (((Boolean)this.render.getValue()).booleanValue() && ((Boolean)this.box.getValue()).booleanValue() && ((Boolean)this.fade.getValue()).booleanValue()) {
/* 1462 */       for (Map.Entry<BlockPos, Long> set : this.fadeList.entrySet()) {
/* 1463 */         if (getRenderPos() == set.getKey()) {
/*      */           continue;
/*      */         }
/*      */         
/* 1467 */         Color boxColor = ((ColorSetting)this.boxColor.getValue()).getColorObject();
/* 1468 */         Color outlineColor = ((ColorSetting)this.outLine.getValue()).getColorObject();
/* 1469 */         float maxBoxAlpha = boxColor.getAlpha();
/* 1470 */         float maxOutlineAlpha = outlineColor.getAlpha();
/* 1471 */         float alphaBoxAmount = maxBoxAlpha / ((Integer)this.fadeTime.getValue()).intValue();
/* 1472 */         float alphaOutlineAmount = maxOutlineAlpha / ((Integer)this.fadeTime.getValue()).intValue();
/* 1473 */         int fadeBoxAlpha = MathHelper.func_76125_a((int)(alphaBoxAmount * (float)(((Long)set.getValue()).longValue() + ((Integer)this.fadeTime.getValue()).intValue() - System.currentTimeMillis())), 0, (int)maxBoxAlpha);
/* 1474 */         int fadeOutlineAlpha = MathHelper.func_76125_a((int)(alphaOutlineAmount * (float)(((Long)set.getValue()).longValue() + ((Integer)this.fadeTime.getValue()).intValue() - System.currentTimeMillis())), 0, (int)maxOutlineAlpha);
/*      */         
/* 1476 */         if (((Boolean)this.box.getValue()).booleanValue()) {
/*      */           
/* 1478 */           BlockRenderUtil.prepareGL();
/* 1479 */           TessellatorUtil.drawBox(interpolatePos(set.getKey(), 1.0F), new Color(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue(), fadeBoxAlpha));
/* 1480 */           BlockRenderUtil.releaseGL();
/*      */           
/* 1482 */           BlockRenderUtil.prepareGL();
/* 1483 */           TessellatorUtil.drawBoundingBox(interpolatePos(set.getKey(), 1.0F), 1.5D, new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), fadeOutlineAlpha));
/* 1484 */           BlockRenderUtil.releaseGL();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     BlockPos pos;
/*      */     
/* 1491 */     if (((Boolean)this.render.getValue()).booleanValue() && (pos = getRenderPos()) != null) {
/* 1492 */       if (!((Boolean)this.fade.getValue()).booleanValue() && (
/* 1493 */         (Boolean)this.box.getValue()).booleanValue()) {
/* 1494 */         BlockRenderUtil.prepareGL();
/* 1495 */         TessellatorUtil.drawBox(interpolatePos(pos, 1.0F), ((ColorSetting)this.boxColor.getValue()).getColorObject());
/* 1496 */         BlockRenderUtil.releaseGL();
/*      */         
/* 1498 */         BlockRenderUtil.prepareGL();
/* 1499 */         TessellatorUtil.drawBoundingBox(interpolatePos(pos, 1.0F), 1.5D, ((ColorSetting)this.outLine.getValue()).getColorObject());
/* 1500 */         BlockRenderUtil.releaseGL();
/*      */       } 
/*      */       
/* 1503 */       if (this.renderDamage.getValue() != RenderDamagePos.None) {
/* 1504 */         renderDamage(pos);
/*      */       }
/* 1506 */       if (((Boolean)this.fade.getValue()).booleanValue()) {
/* 1507 */         this.fadeList.put(pos, Long.valueOf(System.currentTimeMillis()));
/*      */       }
/*      */     } 
/* 1510 */     this.fadeList.entrySet().removeIf(e -> (((Long)e.getValue()).longValue() + ((Integer)this.fadeTime.getValue()).intValue() < System.currentTimeMillis()));
/*      */ 
/*      */     
/* 1513 */     if (((Boolean)this.renderExtrapolation.getValue()).booleanValue()) {
/* 1514 */       for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*      */         MotionTracker tracker;
/* 1516 */         if (player == null || 
/* 1517 */           EntityUtil.isDead((Entity)player) || 
/* 1518 */           RenderUtil.getEntity().func_70068_e((Entity)player) > 200.0D || 
/* 1519 */           !RotationUtil.inFov((Entity)player) || player
/* 1520 */           .equals(mc.field_71439_g) || (
/*      */           
/* 1522 */           tracker = this.extrapolationHelper.getTrackerFromEntity((Entity)player)) == null || !tracker.active) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1527 */         Vec3d interpolation = EntityUtil.interpolateEntity((Entity)player, mc.func_184121_ak());
/* 1528 */         double x = interpolation.field_72450_a - Trajectories.getRenderPosX();
/* 1529 */         double y = interpolation.field_72448_b - Trajectories.getRenderPosY();
/* 1530 */         double z = interpolation.field_72449_c - Trajectories.getRenderPosZ();
/*      */         
/* 1532 */         double tX = tracker.field_70165_t - Trajectories.getRenderPosX();
/* 1533 */         double tY = tracker.field_70163_u - Trajectories.getRenderPosY();
/* 1534 */         double tZ = tracker.field_70161_v - Trajectories.getRenderPosZ();
/*      */ 
/*      */         
/* 1537 */         GlStateManager.func_179141_d();
/* 1538 */         GlStateManager.func_179147_l();
/* 1539 */         GlStateManager.func_179094_E();
/* 1540 */         GlStateManager.func_179096_D();
/*      */         
/* 1542 */         if (Thunderhack.friendManager.isFriend(player)) {
/* 1543 */           GL11.glColor4f(0.33333334F, 0.78431374F, 0.78431374F, 0.55F);
/*      */         } else {
/* 1545 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */         } 
/*      */         
/* 1548 */         boolean viewBobbing = mc.field_71474_y.field_74336_f;
/* 1549 */         mc.field_71474_y.field_74336_f = false;
/* 1550 */         ((IEntityRenderer)mc.field_71460_t).orientCam(event.getPartialTicks());
/* 1551 */         mc.field_71474_y.field_74336_f = viewBobbing;
/*      */         
/* 1553 */         GL11.glLineWidth(1.5F);
/* 1554 */         GL11.glBegin(1);
/* 1555 */         GL11.glVertex3d(tX, tY, tZ);
/* 1556 */         GL11.glVertex3d(x, y, z);
/* 1557 */         GL11.glEnd();
/*      */         
/* 1559 */         GlStateManager.func_179121_F();
/* 1560 */         GlStateManager.func_179118_c();
/* 1561 */         GlStateManager.func_179084_k();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderDamage(BlockPos pos) {
/* 1568 */     GL11.glEnable(2884);
/* 1569 */     GL11.glEnable(3553);
/* 1570 */     GL11.glDisable(3042);
/* 1571 */     GL11.glEnable(2929);
/* 1572 */     String text = this.damage;
/* 1573 */     GlStateManager.func_179094_E();
/* 1574 */     RenderHelper.func_74519_b();
/* 1575 */     GlStateManager.func_179088_q();
/* 1576 */     GlStateManager.func_179136_a(1.0F, -1500000.0F);
/* 1577 */     GlStateManager.func_179140_f();
/* 1578 */     GlStateManager.func_179097_i();
/*      */     
/* 1580 */     double x = pos.func_177958_n() + 0.5D;
/* 1581 */     double y = pos.func_177956_o() + ((this.renderDamage.getValue() == RenderDamagePos.OnTop) ? 1.35D : 0.5D);
/* 1582 */     double z = pos.func_177952_p() + 0.5D;
/*      */     
/* 1584 */     float scale = 0.016666668F * ((this.renderMode.getValue() == RenderDamage.Indicator) ? 0.95F : 1.3F);
/*      */     
/* 1586 */     GlStateManager.func_179137_b(x - Trajectories.getRenderPosX(), y - Trajectories.getRenderPosY(), z - Trajectories.getRenderPosZ());
/*      */     
/* 1588 */     GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
/* 1589 */     GlStateManager.func_179114_b(-mc.field_71439_g.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 1590 */     GlStateManager.func_179114_b(mc.field_71439_g.field_70125_A, (mc.field_71474_y.field_74320_O == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/* 1591 */     GlStateManager.func_179152_a(-scale, -scale, scale);
/*      */     
/* 1593 */     int distance = (int)mc.field_71439_g.func_70011_f(x, y, z);
/* 1594 */     float scaleD = distance / 2.0F / 3.0F;
/* 1595 */     if (scaleD < 1.0F) {
/* 1596 */       scaleD = 1.0F;
/*      */     }
/*      */     
/* 1599 */     GlStateManager.func_179152_a(scaleD, scaleD, scaleD);
/* 1600 */     GlStateManager.func_179137_b(-(FontRender.getStringWidth6(text) / 2.0D), 0.0D, 0.0D);
/*      */     
/* 1602 */     FontRender.drawString6(text, 0.0F, 0.0F, -1, false);
/*      */     
/* 1604 */     GlStateManager.func_179113_r();
/* 1605 */     GlStateManager.func_179136_a(1.0F, 1500000.0F);
/* 1606 */     GlStateManager.func_179121_F();
/* 1607 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onRenderEntity(PostRenderEntitiesEvent event) {
/* 1613 */     if (event.getPass() == 0) {
/* 1614 */       this.crystalRender.render(event.getPartialTicks());
/*      */     }
/*      */   }
/*      */   
/*      */   public enum settingtypeEn
/*      */   {
/* 1620 */     Noob,
/* 1621 */     Pro,
/* 1622 */     Hacker;
/*      */   }
/*      */   
/*      */   public enum pages
/*      */   {
/* 1627 */     Place,
/* 1628 */     Break,
/* 1629 */     Rotations,
/* 1630 */     Misc,
/* 1631 */     FacePlace,
/* 1632 */     SwitchNSwing,
/* 1633 */     Render,
/* 1634 */     Predict,
/* 1635 */     Dev,
/* 1636 */     SetDead,
/* 1637 */     Obsidian,
/* 1638 */     Liquid,
/* 1639 */     AntiTotem,
/* 1640 */     DamageSync,
/* 1641 */     Extrapolation,
/* 1642 */     Efficiency,
/* 1643 */     MultiThreading;
/*      */   }
/*      */   
/*      */   public enum ACRotate
/*      */   {
/* 1648 */     None,
/* 1649 */     All,
/* 1650 */     Break,
/* 1651 */     Place; }
/*      */   
/*      */   public enum AntiFriendPop {
/* 1654 */     All,
/* 1655 */     Break,
/* 1656 */     Place,
/* 1657 */     None;
/*      */   }
/*      */   
/*      */   public enum AntiWeakness {
/* 1661 */     None,
/* 1662 */     Switch;
/*      */   }
/*      */   
/*      */   public enum Attack2 {
/* 1666 */     Always,
/* 1667 */     Crystal,
/* 1668 */     Calc;
/*      */   }
/*      */   
/*      */   public enum BlockExtrapolationMode {
/* 1672 */     Extrapolated,
/* 1673 */     Pessimistic,
/* 1674 */     Optimistic;
/*      */   }
/*      */   
/*      */   public enum BreakValidity {
/* 1678 */     INVALID,
/* 1679 */     ROTATIONS,
/* 1680 */     VALID;
/*      */   }
/*      */   
/*      */   public enum ExtrapolationType {
/* 1684 */     None,
/* 1685 */     Place,
/* 1686 */     Break,
/* 1687 */     Block;
/*      */   }
/*      */   
/*      */   public enum PreCalc {
/* 1691 */     None,
/* 1692 */     Target,
/* 1693 */     Damage;
/*      */   }
/*      */   
/*      */   public enum RenderDamage {
/* 1697 */     Normal,
/* 1698 */     Indicator;
/*      */   }
/*      */   
/*      */   public enum RenderDamagePos
/*      */   {
/* 1703 */     None,
/* 1704 */     Inside,
/* 1705 */     OnTop;
/*      */   }
/*      */   
/*      */   public enum RotateMode {
/* 1709 */     Normal,
/* 1710 */     Smooth;
/*      */   }
/*      */   
/*      */   public enum RotationThread
/*      */   {
/* 1715 */     Predict,
/* 1716 */     Cancel,
/* 1717 */     Wait;
/*      */   }
/*      */   
/*      */   public enum SwingTime {
/* 1721 */     None,
/* 1722 */     Pre,
/* 1723 */     Post;
/*      */   }
/*      */   
/*      */   public enum SwingType {
/* 1727 */     None,
/* 1728 */     MainHand,
/* 1729 */     OffHand;
/*      */   }
/*      */   
/*      */   public enum Target {
/* 1733 */     Closest,
/* 1734 */     Damage,
/* 1735 */     Angle,
/* 1736 */     Fov;
/*      */   }
/*      */   
/*      */   public enum SmartRange {
/* 1740 */     None,
/* 1741 */     Normal,
/* 1742 */     All,
/* 1743 */     Extrapolated;
/*      */   }
/*      */   
/*      */   public enum AutoSwitch
/*      */   {
/* 1748 */     None,
/* 1749 */     Bind,
/* 1750 */     Always;
/*      */   }
/*      */   
/*      */   public enum CooldownBypass2
/*      */   {
/* 1755 */     None,
/* 1756 */     Swap,
/* 1757 */     Pick,
/* 1758 */     Slot;
/*      */   }
/*      */   
/*      */   public enum RayTraceMode
/*      */   {
/* 1763 */     Fast,
/* 1764 */     Resign,
/* 1765 */     Force,
/* 1766 */     Smart;
/*      */   }
/*      */   
/*      */   public enum PlaceSwing {
/* 1770 */     Always,
/* 1771 */     Never,
/* 1772 */     Once;
/*      */   }
/*      */   
/*      */   public enum Rotate
/*      */   {
/* 1777 */     None,
/* 1778 */     Normal,
/* 1779 */     Packet;
/*      */   }
/*      */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\combat\AutoCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */