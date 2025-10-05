/*    */ package com.mrzak34.thunderhack.events;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.phobos.SafeRunnable;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EventPostSync
/*    */   extends Event {
/* 10 */   private final Deque<Runnable> postEvents = new ArrayDeque<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addPostEvent(SafeRunnable runnable) {
/* 17 */     this.postEvents.add(runnable);
/*    */   }
/*    */ 
/*    */   
/*    */   public Deque<Runnable> getPostEvents() {
/* 22 */     return this.postEvents;
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\events\EventPostSync.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */