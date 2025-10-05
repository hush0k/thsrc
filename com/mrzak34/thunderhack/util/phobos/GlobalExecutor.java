/*   */ package com.mrzak34.thunderhack.util.phobos;
/*   */ 
/*   */ import java.util.concurrent.ExecutorService;
/*   */ 
/*   */ 
/*   */ public interface GlobalExecutor
/*   */ {
/* 8 */   public static final ExecutorService EXECUTOR = ThreadUtil.newDaemonCachedThreadPool();
/*   */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\GlobalExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */