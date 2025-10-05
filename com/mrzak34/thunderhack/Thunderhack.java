/*     */ package com.mrzak34.thunderhack;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.CFontRenderer;
/*     */ import com.mrzak34.thunderhack.manager.CommandManager;
/*     */ import com.mrzak34.thunderhack.manager.ConfigManager;
/*     */ import com.mrzak34.thunderhack.manager.EventManager;
/*     */ import com.mrzak34.thunderhack.manager.FriendManager;
/*     */ import com.mrzak34.thunderhack.manager.MacroManager;
/*     */ import com.mrzak34.thunderhack.manager.PositionManager;
/*     */ import com.mrzak34.thunderhack.manager.ReloadManager;
/*     */ import com.mrzak34.thunderhack.manager.RotationManager;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.ffp.NetworkHandler;
/*     */ import com.mrzak34.thunderhack.util.phobos.CombatManager;
/*     */ import com.mrzak34.thunderhack.util.phobos.NoMotionUpdateService;
/*     */ import com.mrzak34.thunderhack.util.phobos.Scheduler;
/*     */ import com.mrzak34.thunderhack.util.phobos.ServerTickManager;
/*     */ import com.mrzak34.thunderhack.util.phobos.SetDeadManager;
/*     */ import com.mrzak34.thunderhack.util.phobos.Sphere;
/*     */ import com.mrzak34.thunderhack.util.phobos.SwitchManager;
/*     */ import com.mrzak34.thunderhack.util.phobos.ThreadManager;
/*     */ import java.awt.Font;
/*     */ import java.io.InputStream;
/*     */ import java.util.Objects;
/*     */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ @Mod(modid = "thunderhack", name = "ThunderHack", version = "2.41", acceptableRemoteVersions = "*")
/*     */ public class Thunderhack {
/*  29 */   public static float TICK_TIMER = 1.0F; @Instance
/*  30 */   public static Thunderhack INSTANCE; public static List<String> alts = new ArrayList<>();
/*     */   
/*     */   public static long initTime;
/*     */   
/*     */   public static BlockPos gps_position;
/*     */   
/*     */   public static Color copy_color;
/*     */   
/*     */   public static NoMotionUpdateService noMotionUpdateService;
/*     */   
/*     */   public static ServerTickManager servtickManager;
/*     */   
/*     */   public static PositionManager positionManager;
/*     */   
/*     */   public static RotationManager rotationManager;
/*     */   
/*     */   public static EntityProvider entityProvider;
/*     */   
/*     */   public static CommandManager commandManager;
/*     */   
/*     */   public static SetDeadManager setDeadManager;
/*     */   
/*     */   public static NetworkHandler networkHandler;
/*     */   
/*     */   public static ThreadManager threadManager;
/*     */   public static SwitchManager switchManager;
/*     */   public static ReloadManager reloadManager;
/*     */   public static CombatManager combatManager;
/*     */   public static ServerManager serverManager;
/*     */   public static FriendManager friendManager;
/*     */   public static ModuleManager moduleManager;
/*     */   public static EventManager eventManager;
/*     */   public static MacroManager macromanager;
/*     */   public static Scheduler yahz;
/*     */   public static CFontRenderer fontRenderer;
/*     */   public static CFontRenderer fontRenderer2;
/*     */   public static CFontRenderer fontRenderer3;
/*     */   public static CFontRenderer fontRenderer4;
/*     */   public static CFontRenderer fontRenderer5;
/*     */   public static CFontRenderer fontRenderer6;
/*     */   public static CFontRenderer fontRenderer7;
/*     */   public static CFontRenderer fontRenderer8;
/*     */   public static CFontRenderer icons;
/*     */   public static CFontRenderer middleicons;
/*     */   public static CFontRenderer BIGicons;
/*     */   private static boolean unloaded = false;
/*     */   
/*     */   public static void load() {
/*  78 */     ConfigManager.loadAlts();
/*  79 */     ConfigManager.loadSearch();
/*  80 */     unloaded = false;
/*  81 */     if (reloadManager != null) {
/*  82 */       reloadManager.unload();
/*  83 */       reloadManager = null;
/*     */     } 
/*     */     
/*  86 */     ConfigManager.init();
/*     */     
/*     */     try {
/*  89 */       fontRenderer = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"))).deriveFont(24.0F), true, true);
/*  90 */       fontRenderer2 = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont3.ttf"))).deriveFont(28.0F), true, true);
/*  91 */       fontRenderer3 = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"))).deriveFont(18.0F), true, true);
/*  92 */       fontRenderer4 = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont2.ttf"))).deriveFont(50.0F), true, true);
/*  93 */       fontRenderer5 = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/Monsterrat.ttf"))).deriveFont(12.0F), true, true);
/*  94 */       fontRenderer6 = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/Monsterrat.ttf"))).deriveFont(14.0F), true, true);
/*  95 */       fontRenderer7 = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/Monsterrat.ttf"))).deriveFont(10.0F), true, true);
/*  96 */       fontRenderer8 = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/ThunderFont3.ttf"))).deriveFont(62.0F), true, true);
/*  97 */       icons = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/icons.ttf"))).deriveFont(20.0F), true, true);
/*  98 */       middleicons = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/icons.ttf"))).deriveFont(46.0F), true, true);
/*  99 */       BIGicons = new CFontRenderer(Font.createFont(0, Objects.<InputStream>requireNonNull(Thunderhack.class.getResourceAsStream("/fonts/icons.ttf"))).deriveFont(72.0F), true, true);
/* 100 */     } catch (Exception e) {
/* 101 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 104 */     noMotionUpdateService = new NoMotionUpdateService();
/* 105 */     servtickManager = new ServerTickManager();
/* 106 */     positionManager = new PositionManager();
/* 107 */     rotationManager = new RotationManager();
/* 108 */     commandManager = new CommandManager();
/* 109 */     entityProvider = new EntityProvider();
/* 110 */     networkHandler = new NetworkHandler();
/* 111 */     setDeadManager = new SetDeadManager();
/* 112 */     serverManager = new ServerManager();
/* 113 */     threadManager = new ThreadManager();
/* 114 */     switchManager = new SwitchManager();
/* 115 */     combatManager = new CombatManager();
/* 116 */     friendManager = new FriendManager();
/* 117 */     moduleManager = new ModuleManager();
/* 118 */     eventManager = new EventManager();
/* 119 */     macromanager = new MacroManager();
/* 120 */     yahz = new Scheduler();
/*     */     
/* 122 */     noMotionUpdateService.init();
/* 123 */     positionManager.init();
/* 124 */     rotationManager.init();
/* 125 */     servtickManager.init();
/* 126 */     moduleManager.init();
/* 127 */     entityProvider.init();
/* 128 */     setDeadManager.init();
/* 129 */     combatManager.init();
/* 130 */     switchManager.init();
/* 131 */     eventManager.init();
/* 132 */     serverManager.init();
/* 133 */     FriendManager.loadFriends();
/* 134 */     yahz.init();
/* 135 */     ConfigManager.load(ConfigManager.getCurrentConfig());
/* 136 */     moduleManager.onLoad();
/* 137 */     ThunderUtils.syncCapes();
/* 138 */     MacroManager.onLoad();
/* 139 */     if (Util.mc.func_110432_I() != null && !alts.contains(Util.mc.func_110432_I().func_111285_a())) {
/* 140 */       alts.add(Util.mc.func_110432_I().func_111285_a());
/*     */     }
/*     */   }
/*     */   
/*     */   public static void unload(boolean initReloadManager) {
/* 145 */     Display.setTitle("Minecraft 1.12.2");
/* 146 */     if (initReloadManager) {
/* 147 */       reloadManager = new ReloadManager();
/* 148 */       reloadManager.init((commandManager != null) ? commandManager.getPrefix() : ".");
/*     */     } 
/* 150 */     ConfigManager.saveAlts();
/* 151 */     ConfigManager.saveSearch();
/* 152 */     FriendManager.saveFriends();
/* 153 */     if (!unloaded) {
/* 154 */       eventManager.onUnload();
/*     */       
/* 156 */       noMotionUpdateService.unload();
/* 157 */       positionManager.unload();
/* 158 */       rotationManager.unload();
/* 159 */       servtickManager.unload();
/* 160 */       entityProvider.unload();
/* 161 */       setDeadManager.unload();
/* 162 */       combatManager.unload();
/* 163 */       switchManager.unload();
/* 164 */       serverManager.unload();
/* 165 */       yahz.unload();
/* 166 */       moduleManager.onUnload();
/* 167 */       ConfigManager.save(ConfigManager.getCurrentConfig());
/* 168 */       MacroManager.saveMacro();
/* 169 */       moduleManager.onUnloadPost();
/* 170 */       unloaded = true;
/*     */     } 
/*     */     
/* 173 */     eventManager = null;
/* 174 */     friendManager = null;
/* 175 */     fontRenderer = null;
/* 176 */     macromanager = null;
/* 177 */     networkHandler = null;
/* 178 */     commandManager = null;
/* 179 */     serverManager = null;
/* 180 */     servtickManager = null;
/*     */   }
/*     */   
/*     */   public static void reload() {
/* 184 */     unload(false);
/* 185 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent event) {
/* 191 */     RenderingRegistry.registerEntityRenderingHandler(EntityGib.class, com.mrzak34.thunderhack.util.dism.RenderGib::new);
/* 192 */     GlobalExecutor.EXECUTOR.submit(() -> Sphere.cacheSphere());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void init(FMLInitializationEvent event) {
/* 197 */     Display.setTitle("ThunderHack+");
/* 198 */     initTime = System.currentTimeMillis();
/* 199 */     load();
/* 200 */     MinecraftForge.EVENT_BUS.register(networkHandler);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\Thunderhack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */