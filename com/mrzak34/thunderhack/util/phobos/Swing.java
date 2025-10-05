/*    */ package com.mrzak34.thunderhack.util.phobos;
/*    */ 
/*    */ import com.mrzak34.thunderhack.util.Util;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketAnimation;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ public enum Swing {
/*  9 */   None
/*    */   {
/*    */ 
/*    */     
/*    */     public void swing(EnumHand hand) {}
/*    */   },
/* 15 */   Packet
/*    */   {
/*    */     public void swing(EnumHand hand) {
/* 18 */       Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(hand));
/*    */     }
/*    */   },
/* 21 */   Full
/*    */   {
/*    */     public void swing(EnumHand hand) {
/* 24 */       Util.mc.field_71439_g.func_184609_a(hand);
/*    */     }
/*    */   },
/* 27 */   Client
/*    */   {
/*    */     public void swing(EnumHand hand) {
/* 30 */       Util.mc.field_71439_g.func_184609_a(hand);
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract void swing(EnumHand paramEnumHand);
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Swing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */