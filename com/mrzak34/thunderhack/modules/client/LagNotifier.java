/*    */ package com.mrzak34.thunderhack.modules.client;
/*    */ import com.mrzak34.thunderhack.Thunderhack;
/*    */ import com.mrzak34.thunderhack.events.PacketEvent;
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.gui.fontstuff.FontRender;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.notification.Notification;
/*    */ import com.mrzak34.thunderhack.notification.NotificationManager;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.Timer;
/*    */ import com.mrzak34.thunderhack.util.render.Drawable;
/*    */ import java.awt.Color;
/*    */ import java.text.DecimalFormat;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class LagNotifier extends Module {
/*    */   private final ResourceLocation ICON;
/*    */   private final Setting<Integer> timeout;
/*    */   private Timer notifTimer;
/*    */   
/*    */   public LagNotifier() {
/* 22 */     super("LagNotifier", "оповещает о-проблемах с сервером", "LagNotifier", Module.Category.CLIENT);
/*    */     
/* 24 */     this.ICON = new ResourceLocation("textures/lagg.png");
/* 25 */     this.timeout = register(new Setting("Timeout", Integer.valueOf(5), Integer.valueOf(5), Integer.valueOf(30)));
/*    */     
/* 27 */     this.notifTimer = new Timer();
/* 28 */     this.packetTimer = new Timer();
/* 29 */     this.rubberbandTimer = new Timer();
/*    */     
/* 31 */     this.isLag = false;
/*    */   }
/*    */   private Timer packetTimer; private Timer rubberbandTimer; private boolean isLag;
/*    */   @SubscribeEvent
/*    */   public void onPacketReceive(PacketEvent.Receive e) {
/* 36 */     if (fullNullCheck())
/* 37 */       return;  if (e.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
/* 38 */       this.rubberbandTimer.reset();
/*    */     }
/* 40 */     this.packetTimer.reset();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent e) {
/* 45 */     if (!this.rubberbandTimer.passedMs(5000L)) {
/* 46 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 47 */         DecimalFormat decimalFormat = new DecimalFormat("#.#");
/* 48 */         FontRender.drawCentString6("Обнаружен руббербенд! " + decimalFormat.format(((5000.0F - (float)this.rubberbandTimer.getTimeMs()) / 1000.0F)), (float)e.getScreenWidth() / 2.0F, (float)e.getScreenHeight() / 3.0F, (new Color(16768768)).getRGB());
/*    */       } else {
/* 50 */         DecimalFormat decimalFormat = new DecimalFormat("#.#");
/* 51 */         FontRender.drawCentString6("Rubberband detected! " + decimalFormat.format(((5000.0F - (float)this.rubberbandTimer.getTimeMs()) / 1000.0F)), (float)e.getScreenWidth() / 2.0F, (float)e.getScreenHeight() / 3.0F, (new Color(16768768)).getRGB());
/*    */       } 
/*    */     }
/* 54 */     if (this.packetTimer.passedMs((((Integer)this.timeout.getValue()).intValue() * 1000))) {
/* 55 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 56 */         DecimalFormat decimalFormat = new DecimalFormat("#.#");
/* 57 */         FontRender.drawCentString6("Сервер перестал отвечать! " + decimalFormat.format(((float)this.packetTimer.getTimeMs() / 1000.0F)), (float)e.getScreenWidth() / 2.0F, (float)e.getScreenHeight() / 3.0F, (new Color(16768768)).getRGB());
/* 58 */         Drawable.drawTexture(this.ICON, ((float)e.getScreenWidth() / 2.0F - 40.0F), ((float)e.getScreenHeight() / 3.0F - 120.0F), 80.0D, 80.0D, new Color(16768768));
/*    */       } else {
/* 60 */         DecimalFormat decimalFormat = new DecimalFormat("#.#");
/* 61 */         FontRender.drawCentString6("Server offline! " + decimalFormat.format(((float)this.packetTimer.getTimeMs() / 1000.0F)), (float)e.getScreenWidth() / 2.0F, (float)e.getScreenHeight() / 3.0F, (new Color(16768768)).getRGB());
/* 62 */         Drawable.drawTexture(this.ICON, ((float)e.getScreenWidth() / 2.0F - 40.0F), ((float)e.getScreenHeight() / 3.0F - 120.0F), 80.0D, 80.0D, new Color(16768768));
/*    */       } 
/*    */     }
/* 65 */     if (Thunderhack.serverManager.getTPS() < 10.0F && this.notifTimer.passedMs(60000L)) {
/* 66 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 67 */         NotificationManager.publicity("LagNotifier ТПС сервера ниже 10! Рекомендуется включить TPSSync", 8, Notification.Type.ERROR);
/*    */       } else {
/* 69 */         NotificationManager.publicity("LagNotifier TPS below 10! It is recommended to enable TPSSync", 8, Notification.Type.ERROR);
/*    */       } 
/* 71 */       this.isLag = true;
/* 72 */       this.notifTimer.reset();
/*    */     } 
/* 74 */     if (Thunderhack.serverManager.getTPS() > 15.0F && this.isLag) {
/* 75 */       if (((MainSettings)Thunderhack.moduleManager.getModuleByClass(MainSettings.class)).language.getValue() == MainSettings.Language.RU) {
/* 76 */         NotificationManager.publicity("ТПС сервера стабилизировался!", 8, Notification.Type.SUCCESS);
/*    */       } else {
/* 78 */         NotificationManager.publicity("TPS of the server has stabilized!", 8, Notification.Type.SUCCESS);
/*    */       } 
/* 80 */       this.isLag = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\client\LagNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */