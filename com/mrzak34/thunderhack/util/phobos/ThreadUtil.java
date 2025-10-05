/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ScheduledExecutorService;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadUtil
/*    */ {
/* 13 */   public static final ThreadFactory FACTORY = newDaemonThreadFactoryBuilder().setNameFormat("ThunderHack-Thread-%d").build();
/* 14 */   public static ExecutorService executor = Executors.newCachedThreadPool();
/*    */ 
/*    */   
/*    */   public static ScheduledExecutorService newDaemonScheduledExecutor(String name) {
/* 18 */     ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
/* 19 */     factory.setNameFormat("ThunderHack-" + name + "-%d");
/* 20 */     return Executors.newSingleThreadScheduledExecutor(factory.build());
/*    */   }
/*    */   
/*    */   public static ExecutorService newDaemonCachedThreadPool() {
/* 24 */     return Executors.newCachedThreadPool(FACTORY);
/*    */   }
/*    */   
/*    */   public static ExecutorService newFixedThreadPool(int size) {
/* 28 */     ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
/* 29 */     factory.setNameFormat("ThunderHack-Fixed-%d");
/* 30 */     return Executors.newFixedThreadPool(Math.max(size, 1), factory.build());
/*    */   }
/*    */   
/*    */   public static ThreadFactoryBuilder newDaemonThreadFactoryBuilder() {
/* 34 */     ThreadFactoryBuilder factory = new ThreadFactoryBuilder();
/* 35 */     factory.setDaemon(true);
/* 36 */     return factory;
/*    */   }
/*    */   
/*    */   public static void run(Runnable runnable, long delay) {
/* 40 */     executor.execute(() -> {
/*    */           try {
/*    */             Thread.sleep(delay);
/* 43 */           } catch (InterruptedException e) {
/*    */             e.printStackTrace();
/*    */           } 
/*    */           runnable.run();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ThreadUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */