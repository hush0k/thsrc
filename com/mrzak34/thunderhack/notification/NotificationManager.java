/*    */ package com.mrzak34.thunderhack.notification;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.Render2DEvent;
/*    */ import com.mrzak34.thunderhack.modules.Module;
/*    */ import com.mrzak34.thunderhack.setting.Setting;
/*    */ import com.mrzak34.thunderhack.util.SoundUtil;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ public class NotificationManager
/*    */   extends Module
/*    */ {
/* 14 */   private static final List<Notification> notificationsnew = new CopyOnWriteArrayList<>();
/* 15 */   private final Setting<Float> position = register(new Setting("Position", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(1.0F)));
/*    */   
/*    */   public NotificationManager() {
/* 18 */     super("Notifications", "aga", Module.Category.CLIENT);
/*    */   }
/*    */   
/*    */   public static void publicity(String content, int second, Notification.Type type) {
/* 22 */     notificationsnew.add(new Notification(content, type, second * 1000));
/* 23 */     if (type == Notification.Type.SUCCESS) {
/* 24 */       SoundUtil.playSound(SoundUtil.ThunderSound.SUCCESS);
/*    */     }
/* 26 */     if (type == Notification.Type.ERROR) {
/* 27 */       SoundUtil.playSound(SoundUtil.ThunderSound.ERROR);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRender2D(Render2DEvent event) {
/* 34 */     if (notificationsnew.size() > 8)
/* 35 */       notificationsnew.remove(0); 
/* 36 */     float startY = (float)(event.getScreenHeight() * ((Float)this.position.getValue()).floatValue() - 36.0D);
/* 37 */     for (int i = 0; i < notificationsnew.size(); i++) {
/* 38 */       Notification notification = notificationsnew.get(i);
/* 39 */       notificationsnew.removeIf(Notification::shouldDelete);
/* 40 */       notification.render(startY);
/* 41 */       startY = (float)(startY - notification.getHeight() + 3.0D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\notification\NotificationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */