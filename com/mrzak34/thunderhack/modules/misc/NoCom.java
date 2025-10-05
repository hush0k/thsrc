/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.gui.misc.GuiScanner;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.IChunkProviderClient;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.notification.Notification;
/*     */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.setting.SubBind;
/*     */ import com.mrzak34.thunderhack.util.PlayerUtils;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class NoCom extends Module {
/*  30 */   public static int scannedChunks = 0;
/*  31 */   public static List<Dot> dots = new ArrayList<>();
/*     */   private static NoCom INSTANCE;
/*  33 */   private static BlockPos playerPos = null;
/*  34 */   private static long time = 0L;
/*  35 */   private static int count = 0;
/*  36 */   private static int masynax = 0;
/*  37 */   private static int masynay = 0;
/*     */   
/*     */   static {
/*  40 */     INSTANCE = new NoCom();
/*     */   }
/*     */   
/*  43 */   private final Setting<SubBind> self = register(new Setting("openGui", new SubBind(0)));
/*  44 */   public Setting<Integer> delay = register(new Setting("Delay", Integer.valueOf(200), Integer.valueOf(0), Integer.valueOf(1000)));
/*  45 */   public Setting<Integer> loop = register(new Setting("LoopPerTick", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(100)));
/*  46 */   public Setting<Integer> startX = register(new Setting("StartX", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000000)));
/*  47 */   public Setting<Integer> startZ = register(new Setting("StartZ", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(1000000)));
/*  48 */   public Setting<Integer> scale = register(new Setting("PointerScale", Integer.valueOf(4), Integer.valueOf(1), Integer.valueOf(4)));
/*  49 */   public Setting<Boolean> you = register(new Setting("you", Boolean.valueOf(true)));
/*  50 */   public Setting<Boolean> loadgui = register(new Setting("LoadGui", Boolean.valueOf(true)));
/*  51 */   public int couti = 1;
/*  52 */   private int renderDistanceDiameter = 0;
/*     */   private int x;
/*     */   
/*     */   public NoCom() {
/*  56 */     super("NoCom", "эксплоит для поиска-игроков", Module.Category.MISC);
/*  57 */     setInstance();
/*     */   }
/*     */   private int z;
/*     */   public static NoCom getInstance() {
/*  61 */     if (INSTANCE == null) {
/*  62 */       INSTANCE = new NoCom();
/*     */     }
/*  64 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static void getgui() {
/*  68 */     Util.mc.func_147108_a((GuiScreen)GuiScanner.getGuiScanner());
/*     */   }
/*     */   
/*     */   public static void rerun(int x, int y) {
/*  72 */     dots.clear();
/*  73 */     playerPos = null;
/*  74 */     count = 0;
/*  75 */     time = 0L;
/*  76 */     masynax = x;
/*  77 */     masynay = y;
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  81 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  86 */     if (PlayerUtils.isKeyDown(((SubBind)this.self.getValue()).getKey())) {
/*  87 */       getgui();
/*     */     }
/*     */     
/*  90 */     if (GuiScanner.neartrack && scannedChunks > 25) {
/*  91 */       scannedChunks = 0;
/*     */     }
/*  93 */     if (GuiScanner.neartrack && scannedChunks == 0) {
/*  94 */       donocom((int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70161_v);
/*     */     }
/*  96 */     if (GuiScanner.neartrack) {
/*     */       return;
/*     */     }
/*     */     
/* 100 */     if (((Boolean)this.loadgui.getValue()).booleanValue()) {
/* 101 */       getgui();
/* 102 */       this.loadgui.setValue(Boolean.valueOf(false));
/*     */     } 
/* 104 */     if (!GuiScanner.busy) {
/* 105 */       if (!((Boolean)this.you.getValue()).booleanValue()) {
/* 106 */         donocom(((Integer)this.startX.getValue()).intValue(), ((Integer)this.startZ.getValue()).intValue());
/*     */       } else {
/* 108 */         donocom((int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70161_v);
/*     */       }
/*     */     
/* 111 */     } else if (masynax != 0 && masynay != 0) {
/* 112 */       donocom(masynax, masynay);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void donocom(int x3, int y3) {
/* 118 */     playerPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v);
/*     */     
/* 120 */     if (this.renderDistanceDiameter == 0)
/*     */     {
/*     */       
/* 123 */       this.renderDistanceDiameter = 8;
/*     */     }
/*     */     
/* 126 */     if (time == 0L) {
/* 127 */       time = System.currentTimeMillis();
/*     */     }
/*     */     
/* 130 */     if (System.currentTimeMillis() - time > ((Integer)this.delay.getValue()).intValue()) {
/* 131 */       for (int i = 0; i < ((Integer)this.loop.getValue()).intValue(); i++) {
/*     */         
/* 133 */         int x1 = 0;
/* 134 */         int z1 = 0;
/* 135 */         if (!((Boolean)this.you.getValue()).booleanValue()) {
/* 136 */           x1 = getSpiralCoords(count)[0] * this.renderDistanceDiameter * 16 + x3;
/* 137 */           z1 = getSpiralCoords(count)[1] * this.renderDistanceDiameter * 16 + y3;
/*     */         } else {
/* 139 */           x1 = getSpiralCoords(count)[0] * this.renderDistanceDiameter * 16 + x3;
/* 140 */           z1 = getSpiralCoords(count)[1] * this.renderDistanceDiameter * 16 + y3;
/*     */         } 
/*     */ 
/*     */         
/* 144 */         BlockPos position = new BlockPos(x1, 0, z1);
/* 145 */         this.x = x1;
/* 146 */         this.z = z1;
/* 147 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, playerPos, EnumFacing.EAST));
/* 148 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, position, EnumFacing.EAST));
/* 149 */         dots.add(new Dot(x1 / 16, z1 / 16, DotType.Searched));
/* 150 */         playerPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u - 1.0D, mc.field_71439_g.field_70161_v);
/* 151 */         time = System.currentTimeMillis();
/* 152 */         count++;
/* 153 */         scannedChunks++;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public final void onPacketReceive(PacketEvent.Receive event) {
/* 160 */     if (event.getPacket() instanceof SPacketBlockChange) {
/* 161 */       int x = ((SPacketBlockChange)event.getPacket()).func_179827_b().func_177958_n();
/* 162 */       int z = ((SPacketBlockChange)event.getPacket()).func_179827_b().func_177952_p();
/*     */       
/* 164 */       IChunkProviderClient chunkProviderClient = (IChunkProviderClient)mc.field_71441_e.func_72863_F();
/* 165 */       for (ObjectIterator<Chunk> objectIterator = chunkProviderClient.getLoadedChunks().values().iterator(); objectIterator.hasNext(); ) { Chunk chunk = objectIterator.next();
/* 166 */         if (chunk.field_76635_g == x / 16 || chunk.field_76647_h == z / 16) {
/*     */           return;
/*     */         } }
/*     */       
/* 170 */       String shittytext = "Player spotted at X: " + ChatFormatting.GREEN + x + ChatFormatting.RESET + " Z: " + ChatFormatting.GREEN + z;
/* 171 */       dots.add(new Dot(x / 16, z / 16, DotType.Spotted));
/* 172 */       Command.sendMessage(shittytext);
/* 173 */       (GuiScanner.getInstance()).consoleout.add(new cout(this.couti, shittytext));
/* 174 */       this.couti++;
/* 175 */       if (GuiScanner.track) {
/* 176 */         (GuiScanner.getInstance()).consoleout.add(new cout(this.couti, "tracking x " + x + " z " + z));
/* 177 */         rerun(x, z);
/*     */       } 
/* 179 */       if (((NotificationManager)Thunderhack.moduleManager.getModuleByClass(NotificationManager.class)).isEnabled()) {
/* 180 */         NotificationManager.publicity(shittytext, 3, Notification.Type.INFO);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int[] getSpiralCoords(int n) {
/* 186 */     int x = 0;
/* 187 */     int z = 0;
/* 188 */     int d = 1;
/* 189 */     int lineNumber = 1;
/* 190 */     int[] coords = { 0, 0 };
/* 191 */     for (int i = 0; i < n; i++) {
/* 192 */       if (2 * x * d < lineNumber) {
/* 193 */         x += d;
/* 194 */         coords = new int[] { x, z };
/* 195 */       } else if (2 * z * d < lineNumber) {
/* 196 */         z += d;
/* 197 */         coords = new int[] { x, z };
/*     */       } else {
/* 199 */         d *= -1;
/* 200 */         lineNumber++;
/* 201 */         n++;
/*     */       } 
/*     */     } 
/* 204 */     return coords;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 209 */     playerPos = null;
/* 210 */     count = 0;
/* 211 */     time = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 216 */     dots.clear();
/* 217 */     playerPos = null;
/* 218 */     count = 0;
/* 219 */     time = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayInfo() {
/* 224 */     return this.x + " , " + this.z;
/*     */   }
/*     */   
/*     */   public enum DotType
/*     */   {
/* 229 */     Spotted, Searched;
/*     */   }
/*     */   
/*     */   public static class cout
/*     */   {
/*     */     public String string;
/*     */     public int posY;
/*     */     
/*     */     public cout(int posY, String out) {
/* 238 */       this.posY = posY;
/* 239 */       this.string = out;
/*     */     } }
/*     */   
/*     */   public class Dot {
/*     */     public NoCom.DotType type;
/*     */     public int posX;
/*     */     public int posY;
/*     */     public Color color;
/*     */     public int ticks;
/*     */     
/*     */     public Dot(int posX, int posY, NoCom.DotType type) {
/* 250 */       this.posX = posX;
/* 251 */       this.posY = posY;
/* 252 */       this.type = type;
/* 253 */       this.ticks = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\NoCom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */