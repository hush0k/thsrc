/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadFactoryBuilder
/*    */ {
/*    */   private Boolean daemon;
/*    */   private String nameFormat;
/*    */   
/*    */   private static String format(String format, Object... args) {
/* 18 */     return String.format(Locale.ROOT, format, args);
/*    */   }
/*    */   
/*    */   public ThreadFactoryBuilder setDaemon(boolean daemon) {
/* 22 */     this.daemon = Boolean.valueOf(daemon);
/* 23 */     return this;
/*    */   }
/*    */   
/*    */   public ThreadFactoryBuilder setNameFormat(String nameFormat) {
/* 27 */     this.nameFormat = nameFormat;
/* 28 */     return this;
/*    */   }
/*    */   
/*    */   public ThreadFactory build() {
/* 32 */     Boolean daemon = this.daemon;
/* 33 */     String nameFormat = this.nameFormat;
/* 34 */     AtomicLong id = (nameFormat != null) ? new AtomicLong(0L) : null;
/* 35 */     return r -> {
/*    */         Thread thread = Executors.defaultThreadFactory().newThread(r);
/*    */         if (daemon != null)
/*    */           thread.setDaemon(daemon.booleanValue()); 
/*    */         if (nameFormat != null)
/*    */           thread.setName(format(nameFormat, new Object[] { Long.valueOf(id.getAndIncrement()) })); 
/*    */         return thread;
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\ThreadFactoryBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */