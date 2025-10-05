/*     */ package com.mrzak34.thunderhack.manager;
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import com.mrzak34.thunderhack.Thunderhack;
/*     */ import com.mrzak34.thunderhack.command.Command;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.KeyEvent;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*     */ import com.mrzak34.thunderhack.events.Render3DEvent;
/*     */ import com.mrzak34.thunderhack.events.TotemPopEvent;
/*     */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*     */ import com.mrzak34.thunderhack.gui.hud.elements.RadarRewrite;
/*     */ import com.mrzak34.thunderhack.gui.thundergui2.ThunderGui2;
/*     */ import com.mrzak34.thunderhack.macro.Macro;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.modules.client.ClickGui;
/*     */ import com.mrzak34.thunderhack.modules.misc.Macros;
/*     */ import com.mrzak34.thunderhack.modules.misc.Timer;
/*     */ import com.mrzak34.thunderhack.modules.render.PearlESP;
/*     */ import com.mrzak34.thunderhack.setting.ColorSetting;
/*     */ import com.mrzak34.thunderhack.util.RoundedShader;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import com.mrzak34.thunderhack.util.render.RenderUtil;
/*     */ import com.mrzak34.thunderhack.util.shaders.BetterDynamicAnimation;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.ClientChatEvent;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class EventManager {
/*     */   public static Module hoveredModule;
/*     */   public static boolean serversprint = false;
/*     */   public static int backX;
/*  54 */   public static BetterDynamicAnimation timerAnimation = new BetterDynamicAnimation(); public static int backY; public static int backZ; public static boolean lock_sprint = false;
/*     */   public static boolean isMacro = false;
/*  56 */   private final Timer chorusTimer = new Timer();
/*  57 */   Timer lastPacket = new Timer();
/*     */ 
/*     */   
/*     */   public static void setColor(int color) {
/*  61 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
/*     */   }
/*     */   
/*     */   public void init() {
/*  65 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public void onUnload() {
/*  69 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdate(LivingEvent.LivingUpdateEvent event) {
/*  74 */     if (!fullNullCheck() && (event.getEntity().func_130014_f_()).field_72995_K && event.getEntityLiving().equals(Util.mc.field_71439_g)) {
/*  75 */       Thunderhack.moduleManager.onUpdate();
/*  76 */       Thunderhack.moduleManager.sortModules(true);
/*     */     } 
/*  78 */     if (!fullNullCheck() && (
/*  79 */       (ClickGui)Thunderhack.moduleManager.<ClickGui>getModuleByClass(ClickGui.class)).getBind().getKey() == -1) {
/*  80 */       Command.sendMessage(ChatFormatting.RED + "Default clickgui keybind --> P");
/*  81 */       ((ClickGui)Thunderhack.moduleManager.<ClickGui>getModuleByClass(ClickGui.class)).setBind(Keyboard.getKeyIndex("P"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
/*  88 */     Thunderhack.moduleManager.onLogin();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/*  93 */     Thunderhack.moduleManager.onLogout();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent event) {
/*  98 */     if (fullNullCheck()) {
/*     */       return;
/*     */     }
/* 101 */     if (event.phase != TickEvent.Phase.END) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 106 */     if (Util.mc.field_71462_r instanceof net.minecraft.client.gui.GuiGameOver) {
/* 107 */       backY = (int)Util.mc.field_71439_g.field_70163_u;
/* 108 */       backZ = (int)Util.mc.field_71439_g.field_70161_v;
/* 109 */       backX = (int)Util.mc.field_71439_g.field_70165_t;
/*     */     } 
/*     */ 
/*     */     
/* 113 */     Thunderhack.moduleManager.onTick();
/* 114 */     ThunderGui2.getInstance().onTick();
/* 115 */     timerAnimation.update();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayer(EventSync event) {
/* 120 */     if (fullNullCheck())
/*     */       return; 
/* 122 */     if (!this.lastPacket.passedMs(100L)) {
/* 123 */       ((Timer)Thunderhack.moduleManager.<Timer>getModuleByClass(Timer.class)).m();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent.Send e) {
/* 129 */     if (e.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Position || e.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation || e.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation) {
/* 130 */       this.lastPacket.reset();
/*     */     }
/* 132 */     if (e.getPacket() instanceof CPacketEntityAction) {
/* 133 */       CPacketEntityAction ent = (CPacketEntityAction)e.getPacket();
/* 134 */       if (ent.func_180764_b() == CPacketEntityAction.Action.START_SPRINTING) {
/* 135 */         if (lock_sprint) {
/* 136 */           e.setCanceled(true);
/*     */           return;
/*     */         } 
/* 139 */         serversprint = true;
/*     */       } 
/* 141 */       if (ent.func_180764_b() == CPacketEntityAction.Action.STOP_SPRINTING) {
/* 142 */         if (lock_sprint) {
/* 143 */           e.setCanceled(true);
/*     */           return;
/*     */         } 
/* 146 */         serversprint = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 153 */     if (event.getPacket() instanceof SPacketEntityStatus) {
/* 154 */       SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
/* 155 */       if (packet.func_149160_c() == 35 && packet.func_149161_a((World)Util.mc.field_71441_e) instanceof EntityPlayer) {
/* 156 */         EntityPlayer player = (EntityPlayer)packet.func_149161_a((World)Util.mc.field_71441_e);
/* 157 */         MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(player));
/*     */       } 
/*     */     } 
/* 160 */     if (event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).func_186978_a() == SoundEvents.field_187544_ad) {
/* 161 */       if (!this.chorusTimer.passedMs(100L)) {
/* 162 */         MinecraftForge.EVENT_BUS.post((Event)new ChorusEvent(((SPacketSoundEffect)event.getPacket()).func_149207_d(), ((SPacketSoundEffect)event.getPacket()).func_149211_e(), ((SPacketSoundEffect)event.getPacket()).func_149210_f()));
/*     */       }
/* 164 */       this.chorusTimer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldRender(RenderWorldLastEvent event) {
/* 170 */     if (event.isCanceled())
/*     */       return; 
/* 172 */     GL11.glPushAttrib(1048575);
/* 173 */     GL11.glDisable(2884);
/* 174 */     GL11.glDisable(3553);
/* 175 */     GL11.glEnable(3042);
/* 176 */     GL11.glDisable(2929);
/* 177 */     GlStateManager.func_187441_d(1.0F);
/* 178 */     Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
/* 179 */     Thunderhack.moduleManager.onRender3D(render3dEvent);
/* 180 */     GlStateManager.func_187441_d(1.0F);
/* 181 */     GL11.glPopAttrib();
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderHUD(RenderGameOverlayEvent.Post event) {}
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
/* 190 */     if (event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
/*     */       
/* 192 */       boolean blend = GL11.glIsEnabled(3042);
/* 193 */       boolean depth = GL11.glIsEnabled(2929);
/*     */       
/* 195 */       ScaledResolution resolution = new ScaledResolution(Util.mc);
/* 196 */       Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
/* 197 */       Thunderhack.moduleManager.onRender2D(render2DEvent);
/* 198 */       if (((Boolean)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).indicator.getValue()).booleanValue()) {
/* 199 */         float posX = resolution.func_78326_a() / 2.0F;
/* 200 */         float posY = (resolution.func_78328_b() - ((Integer)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).yyy.getValue()).intValue());
/*     */         
/* 202 */         Color a = Timer.TwoColoreffect(((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color.getValue()).getColorObject(), ((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).slices.getValue()).intValue() * 2.55D / 60.0D);
/* 203 */         Color b = Timer.TwoColoreffect(((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color.getValue()).getColorObject(), ((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).slices1.getValue()).intValue() * 2.55D / 60.0D);
/* 204 */         Color c = Timer.TwoColoreffect(((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color.getValue()).getColorObject(), ((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).slices2.getValue()).intValue() * 2.55D / 60.0D);
/* 205 */         Color d = Timer.TwoColoreffect(((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color.getValue()).getColorObject(), ((ColorSetting)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).color2.getValue()).getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0D + 3.0D * ((Integer)((Timer)Thunderhack.moduleManager.getModuleByClass((Class)Timer.class)).slices3.getValue()).intValue() * 2.55D / 60.0D);
/* 206 */         RenderUtil.drawBlurredShadow(posX - 33.0F, posY - 3.0F, 66.0F, 16.0F, 10, a);
/*     */ 
/*     */         
/* 209 */         float timerStatus = (float)(61.0D * (10.0D - Timer.value) / (Math.abs(((Timer)Thunderhack.moduleManager.<Timer>getModuleByClass(Timer.class)).getMin()) + 10));
/* 210 */         timerAnimation.setValue(timerStatus);
/* 211 */         timerStatus = (float)timerAnimation.getAnimationD();
/*     */         
/* 213 */         int status = (int)((10.0D - Timer.value) / (Math.abs(((Timer)Thunderhack.moduleManager.<Timer>getModuleByClass(Timer.class)).getMin()) + 10) * 100.0D);
/*     */         
/* 215 */         RoundedShader.drawGradientRound(posX - 31.0F, posY, 62.0F, 12.0F, 3.0F, new Color(1), new Color(1), new Color(1), new Color(1));
/* 216 */         RoundedShader.drawGradientRound(posX - 30.5F, posY + 0.5F, timerStatus, 11.0F, 3.0F, a, b, c, d);
/* 217 */         FontRender.drawCentString6((status >= 99) ? "100%" : (status + "%"), resolution.func_78326_a() / 2.0F, posY + 5.25F, (new Color(200, 200, 200, 255)).getRGB());
/*     */       } 
/* 219 */       GlStateManager.func_179117_G();
/* 220 */       if (blend)
/* 221 */         GL11.glEnable(3042); 
/* 222 */       if (depth) {
/* 223 */         GL11.glEnable(2929);
/*     */       }
/* 225 */       if (Thunderhack.gps_position != null) {
/* 226 */         float xOffset = resolution.func_78326_a() / 2.0F;
/* 227 */         float yOffset = resolution.func_78328_b() / 2.0F;
/*     */         
/* 229 */         GlStateManager.func_179094_E();
/* 230 */         float yaw = RadarRewrite.getRotations(Thunderhack.gps_position) - Util.mc.field_71439_g.field_70177_z;
/* 231 */         GL11.glTranslatef(xOffset, yOffset, 0.0F);
/* 232 */         GL11.glRotatef(yaw, 0.0F, 0.0F, 1.0F);
/* 233 */         GL11.glTranslatef(-xOffset, -yOffset, 0.0F);
/* 234 */         ((PearlESP)Thunderhack.moduleManager.<PearlESP>getModuleByClass(PearlESP.class)).drawTriangle(xOffset, yOffset - 50.0F, 12.5F, ClickGui.getInstance().getColor(1).getRGB());
/* 235 */         GL11.glTranslatef(xOffset, yOffset, 0.0F);
/* 236 */         GL11.glRotatef(-yaw, 0.0F, 0.0F, 1.0F);
/* 237 */         GL11.glTranslatef(-xOffset, -yOffset, 0.0F);
/* 238 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 239 */         GlStateManager.func_179121_F();
/* 240 */         FontRender.drawCentString6("gps (" + getDistance(Thunderhack.gps_position) + "m)", (float)get_x(yaw) + xOffset, (float)(yOffset - get_y(yaw)) - 20.0F, -1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
/*     */   public void onKeyInput(InputEvent.KeyInputEvent event) {
/* 248 */     if (Keyboard.getEventKeyState()) {
/* 249 */       Thunderhack.moduleManager.onKeyPressed(Keyboard.getEventKey());
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST)
/*     */   public void onChatSent(ClientChatEvent event) {
/* 255 */     if (event.getMessage().startsWith(Command.getCommandPrefix())) {
/* 256 */       event.setCanceled(true);
/*     */       try {
/* 258 */         Util.mc.field_71456_v.func_146158_b().func_146239_a(event.getMessage());
/* 259 */         if (event.getMessage().length() > 1) {
/* 260 */           Thunderhack.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
/*     */         } else {
/* 262 */           Command.sendMessage("Неверная команда!");
/*     */         } 
/* 264 */       } catch (Exception e) {
/* 265 */         e.printStackTrace();
/* 266 */         Command.sendMessage(ChatFormatting.RED + "Ошибка команды!");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onKeyPress(KeyEvent event) {
/* 273 */     if (event.getKey() == 0)
/* 274 */       return;  if (((Macros)Thunderhack.moduleManager.<Macros>getModuleByClass(Macros.class)).isEnabled()) {
/* 275 */       for (Macro m : Thunderhack.macromanager.getMacros()) {
/* 276 */         if (m.getBind() == event.getKey()) {
/* 277 */           isMacro = true;
/* 278 */           m.runMacro();
/* 279 */           isMacro = false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private double get_x(double rad) {
/* 287 */     return Math.sin(Math.toRadians(rad)) * 50.0D;
/*     */   }
/*     */   
/*     */   private double get_y(double rad) {
/* 291 */     return Math.cos(Math.toRadians(rad)) * 50.0D;
/*     */   }
/*     */   
/*     */   public int getDistance(BlockPos bp) {
/* 295 */     double d0 = Util.mc.field_71439_g.field_70165_t - bp.func_177958_n();
/* 296 */     double d2 = Util.mc.field_71439_g.field_70161_v - bp.func_177952_p();
/* 297 */     return (int)MathHelper.func_76133_a(d0 * d0 + d2 * d2);
/*     */   }
/*     */   
/*     */   public static boolean fullNullCheck() {
/* 301 */     return (Util.mc.field_71439_g == null || Util.mc.field_71441_e == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */