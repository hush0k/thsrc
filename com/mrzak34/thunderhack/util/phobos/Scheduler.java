/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.events.GameZaloopEvent;
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Queue;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Scheduler
/*    */ {
/* 16 */   private static final Scheduler INSTANCE = new Scheduler();
/*    */   
/* 18 */   private final Queue<Runnable> scheduled = new LinkedList<>();
/* 19 */   private final Queue<Runnable> toSchedule = new LinkedList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean executing;
/*    */ 
/*    */   
/*    */   private int gameLoop;
/*    */ 
/*    */ 
/*    */   
/*    */   public static Scheduler getInstance() {
/* 31 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void init() {
/* 35 */     MinecraftForge.EVENT_BUS.register(this);
/*    */   }
/*    */   
/*    */   public void unload() {
/* 39 */     MinecraftForge.EVENT_BUS.unregister(this);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onGameZaloop(GameZaloopEvent e) {
/* 44 */     this.gameLoop = ((InterfaceMinecraft)Util.mc).getGameLoop();
/*    */     
/* 46 */     this.executing = true;
/* 47 */     CollectionUtil.emptyQueue(this.scheduled, Runnable::run);
/* 48 */     this.executing = false;
/*    */     
/* 50 */     CollectionUtil.emptyQueue(this.toSchedule, this.scheduled::add);
/*    */   }
/*    */   
/*    */   public void scheduleAsynchronously(Runnable runnable) {
/* 54 */     Util.mc.func_152344_a(() -> schedule(runnable, false));
/*    */   }
/*    */ 
/*    */   
/*    */   public void schedule(Runnable runnable, boolean checkGameLoop) {
/* 59 */     if (Util.mc.func_152345_ab()) {
/* 60 */       if (this.executing || (checkGameLoop && this.gameLoop != ((InterfaceMinecraft)Util.mc)
/* 61 */         .getGameLoop())) {
/* 62 */         this.toSchedule.add(runnable);
/*    */       } else {
/* 64 */         this.scheduled.add(runnable);
/*    */       } 
/*    */     } else {
/* 67 */       Util.mc.func_152344_a(runnable);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Scheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */