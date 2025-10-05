/*     */ package com.mrzak34.thunderhack.manager;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.ArrayList;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.Player;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.TPSCounter;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.combat.AimAssist;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.AntiTPhere;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.AutoCappRegear;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.AutoOzera;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.AutoPot;
/*     */ import com.mrzak34.thunderhack.modules.funnygame.PhotoMath;
/*     */ import com.mrzak34.thunderhack.modules.misc.DiscordEmbeds;
/*     */ import com.mrzak34.thunderhack.modules.misc.Weather;
/*     */ import com.mrzak34.thunderhack.modules.movement.Flight;
/*     */ import com.mrzak34.thunderhack.modules.movement.GuiMove;
/*     */ import com.mrzak34.thunderhack.modules.movement.ReverseStep;
/*     */ import com.mrzak34.thunderhack.modules.player.AutoGApple;
/*     */ import com.mrzak34.thunderhack.modules.render.FogColor;
/*     */ import com.mrzak34.thunderhack.modules.render.JumpCircle;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ public class ModuleManager {
/*  33 */   public ArrayList<Module> modules = new ArrayList<>();
/*  34 */   public List<Module> sortedModules = new ArrayList<>();
/*     */   
/*     */   public void init() {
/*  37 */     this.modules.add(new ClickGui());
/*  38 */     this.modules.add(new ExtraTab());
/*  39 */     this.modules.add(new GuiMove());
/*  40 */     this.modules.add(new AutoSoup());
/*  41 */     this.modules.add(new AimAssist());
/*  42 */     this.modules.add(new AutoBuy());
/*  43 */     this.modules.add(new DurabilityAlert());
/*  44 */     this.modules.add(new NoGlitchBlock());
/*  45 */     this.modules.add(new AutoTotem());
/*  46 */     this.modules.add(new TeleportBack());
/*  47 */     this.modules.add(new AntiAim());
/*  48 */     this.modules.add(new AutoAuth());
/*  49 */     this.modules.add(new AutoBuff());
/*  50 */     this.modules.add(new DiscordEmbeds());
/*  51 */     this.modules.add(new Welcomer());
/*  52 */     this.modules.add(new FastFall());
/*  53 */     this.modules.add(new Search());
/*  54 */     this.modules.add(new Tracers());
/*  55 */     this.modules.add(new LagMessage());
/*  56 */     this.modules.add(new NewChunks());
/*  57 */     this.modules.add(new SilentBow());
/*  58 */     this.modules.add(new Spammer());
/*  59 */     this.modules.add(new FastUse());
/*  60 */     this.modules.add(new PacketStatistics());
/*  61 */     this.modules.add(new AutoFlyme());
/*  62 */     this.modules.add(new ReverseStep());
/*  63 */     this.modules.add(new Step());
/*  64 */     this.modules.add(new NoInteract());
/*  65 */     this.modules.add(new ClanInvite());
/*  66 */     this.modules.add(new AntiBadEffects());
/*  67 */     this.modules.add(new Dismemberment());
/*  68 */     this.modules.add(new Reach());
/*  69 */     this.modules.add(new PasswordHider());
/*  70 */     this.modules.add(new LowHPScreen());
/*  71 */     this.modules.add(new AutoCrystal());
/*  72 */     this.modules.add(new AutoMine());
/*  73 */     this.modules.add(new PvPResources());
/*  74 */     this.modules.add(new ElytraFly2b2tNew());
/*  75 */     this.modules.add(new CivBreaker());
/*  76 */     this.modules.add(new AntiTPhere());
/*  77 */     this.modules.add(new PearlBait());
/*  78 */     this.modules.add(new AutoSheep());
/*  79 */     this.modules.add(new BoatFly());
/*  80 */     this.modules.add(new AirStuck());
/*  81 */     this.modules.add(new TargetHud());
/*  82 */     this.modules.add(new Aura());
/*  83 */     this.modules.add(new NoSlow());
/*  84 */     this.modules.add(new HitParticles2());
/*  85 */     this.modules.add(new Spider());
/*  86 */     this.modules.add(new AutoExplosion());
/*  87 */     this.modules.add(new AutoAmericano());
/*  88 */     this.modules.add(new AutoOzera());
/*  89 */     this.modules.add(new NoCameraClip());
/*  90 */     this.modules.add(new SpawnerNameTag());
/*  91 */     this.modules.add(new BlockHighlight());
/*  92 */     this.modules.add(new TickShift());
/*  93 */     this.modules.add(new StorageEsp());
/*  94 */     this.modules.add(new Ambience());
/*  95 */     this.modules.add(new Ghost());
/*  96 */     this.modules.add(new BowAim());
/*  97 */     this.modules.add(new Shulkerception());
/*  98 */     this.modules.add(new FunnyClicker());
/*  99 */     this.modules.add(new BreakHighLight());
/* 100 */     this.modules.add(new ThirdPersView());
/* 101 */     this.modules.add(new SolidWeb());
/* 102 */     this.modules.add(new NoCom());
/* 103 */     this.modules.add(new DMGFly());
/* 104 */     this.modules.add(new Sprint());
/* 105 */     this.modules.add(new FreeLook());
/* 106 */     this.modules.add(new ItemScroller());
/* 107 */     this.modules.add(new Quiver());
/* 108 */     this.modules.add(new NoFall());
/* 109 */     this.modules.add(new KeepSprint());
/* 110 */     this.modules.add(new LevitationControl());
/* 111 */     this.modules.add(new CustomEnchants());
/* 112 */     this.modules.add(new HoleESP());
/* 113 */     this.modules.add(new Skeleton());
/* 114 */     this.modules.add(new Trajectories());
/* 115 */     this.modules.add(new LongJump());
/* 116 */     this.modules.add(new FakePlayer());
/* 117 */     this.modules.add(new TpsSync());
/* 118 */     this.modules.add(new ItemShaders());
/* 119 */     this.modules.add(new ThunderHackGui());
/* 120 */     this.modules.add(new ElytraSwap());
/* 121 */     this.modules.add(new VisualRange());
/* 122 */     this.modules.add(new StashLogger());
/* 123 */     this.modules.add(new PearlESP());
/* 124 */     this.modules.add(new EntityESP());
/* 125 */     this.modules.add(new AutoFish());
/* 126 */     this.modules.add(new PlayerTrails());
/* 127 */     this.modules.add(new CrystalChams());
/* 128 */     this.modules.add(new FreeCam());
/* 129 */     this.modules.add(new PacketFly());
/* 130 */     this.modules.add(new Timer());
/* 131 */     this.modules.add(new AutoTrap());
/* 132 */     this.modules.add(new NoEntityTrace());
/* 133 */     this.modules.add(new PistonAura());
/* 134 */     this.modules.add(new LiquidInteract());
/* 135 */     this.modules.add(new Weather());
/* 136 */     this.modules.add(new Models());
/* 137 */     this.modules.add(new Jesus());
/* 138 */     this.modules.add(new EChestFarmer());
/* 139 */     this.modules.add(new SeedOverlay());
/* 140 */     this.modules.add(new MiddleClick());
/* 141 */     this.modules.add(new NoInterp());
/* 142 */     this.modules.add(new Anchor());
/* 143 */     this.modules.add(new NotificationManager());
/* 144 */     this.modules.add(new Speedmine());
/* 145 */     this.modules.add(new NoVoid());
/* 146 */     this.modules.add(new HoleFiller());
/* 147 */     this.modules.add(new NoHandShake());
/* 148 */     this.modules.add(new WTap());
/* 149 */     this.modules.add(new AutoRegear());
/* 150 */     this.modules.add(new AutoLeave());
/* 151 */     this.modules.add(new ShiftInterp());
/* 152 */     this.modules.add(new Particles());
/* 153 */     this.modules.add(new ElytraFlight());
/* 154 */     this.modules.add(new RusherScaffold());
/* 155 */     this.modules.add(new PortalGodMode());
/* 156 */     this.modules.add(new FpsCounter());
/* 157 */     this.modules.add(new Blink());
/* 158 */     this.modules.add(new NoServerRotation());
/* 159 */     this.modules.add(new MainSettings());
/* 160 */     this.modules.add(new TPSCounter());
/* 161 */     this.modules.add(new WaterMark());
/* 162 */     this.modules.add(new Player());
/* 163 */     this.modules.add(new Surround());
/* 164 */     this.modules.add(new Speedometer());
/* 165 */     this.modules.add(new ArmorHud());
/* 166 */     this.modules.add(new LagNotifier());
/* 167 */     this.modules.add(new BreadCrumbs());
/* 168 */     this.modules.add(new KillFeed());
/* 169 */     this.modules.add(new LogoutSpots());
/* 170 */     this.modules.add(new LegitStrafe());
/* 171 */     this.modules.add(new BackTrack());
/* 172 */     this.modules.add(new XCarry());
/* 173 */     this.modules.add(new NameProtect());
/* 174 */     this.modules.add(new Radar());
/* 175 */     this.modules.add(new FogColor());
/* 176 */     this.modules.add(new LegitScaff());
/* 177 */     this.modules.add(new PopChams());
/* 178 */     this.modules.add(new ContainerPreviewModule());
/* 179 */     this.modules.add(new EbatteSratte());
/* 180 */     this.modules.add(new RPC());
/* 181 */     this.modules.add(new ViewModel());
/* 182 */     this.modules.add(new NoRender());
/* 183 */     this.modules.add(new VoidESP());
/* 184 */     this.modules.add(new TunnelESP());
/* 185 */     this.modules.add(new Criticals());
/* 186 */     this.modules.add(new EzingKids());
/* 187 */     this.modules.add(new IceSpeed());
/* 188 */     this.modules.add(new Shaders());
/* 189 */     this.modules.add(new Indicators());
/* 190 */     this.modules.add(new ChestStealer());
/* 191 */     this.modules.add(new InvManager());
/* 192 */     this.modules.add(new AutoMend());
/* 193 */     this.modules.add(new AutoArmor());
/* 194 */     this.modules.add(new ChorusESP());
/* 195 */     this.modules.add(new GroundBoost());
/* 196 */     this.modules.add(new BeakonESP());
/* 197 */     this.modules.add(new Speed());
/* 198 */     this.modules.add(new Burrow());
/* 199 */     this.modules.add(new AntiHunger());
/* 200 */     this.modules.add(new TriggerBot());
/* 201 */     this.modules.add(new FullBright());
/* 202 */     this.modules.add(new Velocity());
/* 203 */     this.modules.add(new NameTags());
/* 204 */     this.modules.add(new AutoTPaccept());
/* 205 */     this.modules.add(new AntiDisconnect());
/* 206 */     this.modules.add(new XRay());
/* 207 */     this.modules.add(new NoJumpDelay());
/* 208 */     this.modules.add(new Flight());
/* 209 */     this.modules.add(new HitBoxes());
/* 210 */     this.modules.add(new NGriefCleaner());
/* 211 */     this.modules.add(new PearlBlockThrow());
/* 212 */     this.modules.add(new Strafe());
/* 213 */     this.modules.add(new MultiConnect());
/* 214 */     this.modules.add(new RadarRewrite());
/* 215 */     this.modules.add(new ArrayList());
/* 216 */     this.modules.add(new Coords());
/* 217 */     this.modules.add(new DiscordWebhook());
/* 218 */     this.modules.add(new KillEffect());
/* 219 */     this.modules.add(new PacketFly2());
/* 220 */     this.modules.add(new AutoTool());
/* 221 */     this.modules.add(new TargetStrafe());
/* 222 */     this.modules.add(new EZbowPOP());
/* 223 */     this.modules.add(new NoClip());
/* 224 */     this.modules.add(new BowSpam());
/* 225 */     this.modules.add(new ItemESP());
/* 226 */     this.modules.add(new DMGParticles());
/* 227 */     this.modules.add(new AutoRespawn());
/* 228 */     this.modules.add(new PhotoMath());
/* 229 */     this.modules.add(new KDShop());
/* 230 */     this.modules.add(new AntiBowBomb());
/* 231 */     this.modules.add(new EffectsRemover());
/* 232 */     this.modules.add(new TrueDurability());
/* 233 */     this.modules.add(new ItemPhysics());
/* 234 */     this.modules.add(new Potions());
/* 235 */     this.modules.add(new NoServerSlot());
/* 236 */     this.modules.add(new CevBreaker());
/* 237 */     this.modules.add(new AntiBot());
/* 238 */     this.modules.add(new AntiTittle());
/* 239 */     this.modules.add(new CoolCrosshair());
/* 240 */     this.modules.add(new AutoCappRegear());
/* 241 */     this.modules.add(new ToolTips());
/* 242 */     this.modules.add(new Macros());
/* 243 */     this.modules.add(new AutoGApple());
/* 244 */     this.modules.add(new HudEditor());
/* 245 */     this.modules.add(new AutoPot());
/* 246 */     this.modules.add(new StaffBoard());
/*     */     
/* 248 */     this.modules.add(new MSTSpeed());
/* 249 */     this.modules.add(new Animations());
/* 250 */     this.modules.add(new C4Aura());
/* 251 */     this.modules.add(new AutoEZ());
/* 252 */     this.modules.add(new MessageAppend());
/* 253 */     this.modules.add(new ImageESP());
/* 254 */     this.modules.add(new PyroRadar());
/* 255 */     this.modules.add(new JumpCircle());
/* 256 */     this.modules.add(new ClickTP());
/* 257 */     this.modules.add(new KeyBinds());
/*     */   }
/*     */   
/*     */   public Module getModuleByName(String name) {
/* 261 */     for (Module module : this.modules) {
/* 262 */       if (!module.getName().equalsIgnoreCase(name))
/* 263 */         continue;  return module;
/*     */     } 
/* 265 */     return null;
/*     */   }
/*     */   
/*     */   public <T extends Module> T getModuleByClass(Class<T> clazz) {
/* 269 */     for (Module module : this.modules) {
/* 270 */       if (!clazz.isInstance(module))
/* 271 */         continue;  return (T)module;
/*     */     } 
/* 273 */     return null;
/*     */   }
/*     */   
/*     */   public Module getModuleByDisplayName(String displayName) {
/* 277 */     for (Module module : this.modules) {
/* 278 */       if (!module.getDisplayName().equalsIgnoreCase(displayName))
/* 279 */         continue;  return module;
/*     */     } 
/* 281 */     return null;
/*     */   }
/*     */   
/*     */   public ArrayList<Module> getEnabledModules() {
/* 285 */     ArrayList<Module> enabledModules = new ArrayList<>();
/* 286 */     for (Module module : this.modules) {
/* 287 */       if (!module.isEnabled())
/* 288 */         continue;  enabledModules.add(module);
/*     */     } 
/* 290 */     return enabledModules;
/*     */   }
/*     */   
/*     */   public ArrayList<Module> getModulesByCategory(Module.Category category) {
/* 294 */     ArrayList<Module> modulesCategory = new ArrayList<>();
/* 295 */     this.modules.forEach(module -> {
/*     */           if (module.getCategory() == category) {
/*     */             modulesCategory.add(module);
/*     */           }
/*     */         });
/* 300 */     return modulesCategory;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<Module> getModulesSearch(String string) {
/* 305 */     ArrayList<Module> modulesCategory = new ArrayList<>();
/* 306 */     this.modules.forEach(module -> {
/*     */           if (module.getName().toLowerCase().contains(string.toLowerCase())) {
/*     */             modulesCategory.add(module);
/*     */           }
/*     */         });
/*     */     
/* 312 */     this.modules.forEach(module -> {
/*     */           if (module.getDescription().toLowerCase().contains(string.toLowerCase())) {
/*     */             modulesCategory.add(module);
/*     */           }
/*     */         });
/*     */     
/* 318 */     return modulesCategory;
/*     */   }
/*     */   
/*     */   public List<Module.Category> getCategories() {
/* 322 */     return Arrays.asList(Module.Category.values());
/*     */   }
/*     */   
/*     */   public void onLoad() {
/* 326 */     this.modules.sort(Comparator.comparing(Module::getName));
/* 327 */     this.modules.stream().filter(Module::listening).forEach(MinecraftForge.EVENT_BUS::register);
/* 328 */     this.modules.forEach(Module::onLoad);
/*     */   }
/*     */   
/*     */   public void onUpdate() {
/* 332 */     this.modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate);
/*     */   }
/*     */   
/*     */   public void onTick() {
/* 336 */     this.modules.stream().filter(Module::isEnabled).forEach(Module::onTick);
/* 337 */     this.modules.forEach(module -> {
/*     */           if (!PlayerUtils.isKeyDown(module.getBind().getKey()) && module.isEnabled() && module.getBind().isHold()) {
/*     */             module.disable();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void onRender2D(Render2DEvent event) {
/* 345 */     this.modules.stream().filter(Module::isEnabled).forEach(module -> module.onRender2D(event));
/*     */   }
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {
/* 349 */     this.modules.stream().filter(Module::isEnabled).forEach(module -> module.onRender3D(event));
/*     */   }
/*     */   
/*     */   public void sortModules(boolean reverse) {
/* 353 */     this.sortedModules = (List<Module>)getEnabledModules().stream().filter(Module::isDrawn).sorted(Comparator.comparing(module -> Integer.valueOf(FontRender.getStringWidth6(module.getFullArrayString()) * (reverse ? -1 : 1)))).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLogout() {
/* 358 */     this.modules.forEach(Module::onLogout);
/*     */   }
/*     */   
/*     */   public void onLogin() {
/* 362 */     this.modules.forEach(Module::onLogin);
/*     */   }
/*     */   
/*     */   public void onUnload() {
/* 366 */     this.modules.forEach(MinecraftForge.EVENT_BUS::unregister);
/* 367 */     this.modules.forEach(Module::onUnload);
/*     */   }
/*     */   
/*     */   public void onUnloadPost() {
/* 371 */     for (Module module : this.modules) {
/* 372 */       module.enabled.setValue(Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onKeyPressed(int eventKey) {
/* 377 */     if (eventKey == 0 || !Keyboard.getEventKeyState() || Util.mc.field_71462_r instanceof com.mrzak34.thunderhack.gui.clickui.ClickUI || Util.mc.field_71462_r instanceof com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2) {
/*     */       return;
/*     */     }
/* 380 */     this.modules.forEach(module -> {
/*     */           if (module.getBind().getKey() == eventKey)
/*     */             module.toggle(); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\ModuleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */