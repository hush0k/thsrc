/*    */ package com.mrzak34.thunderhack.util.ffp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.EnumPacketDirection;
/*    */ import net.minecraft.network.NettyPacketEncoder;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class OutboundInterceptor
/*    */   extends NettyPacketEncoder {
/*    */   private final EnumPacketDirection direction;
/*    */   private final NetworkHandler handler;
/*    */   private boolean isPlay;
/*    */   
/*    */   public OutboundInterceptor(NetworkHandler handler, EnumPacketDirection direction) {
/* 21 */     super(direction);
/* 22 */     this.handler = handler;
/* 23 */     this.direction = direction;
/* 24 */     this.isPlay = false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext context, Packet<?> packet, ByteBuf out) throws Exception {
/* 29 */     if (!this.isPlay) {
/* 30 */       EnumConnectionState state = (EnumConnectionState)context.channel().attr(NetworkManager.field_150739_c).get();
/* 31 */       this.isPlay = (state == EnumConnectionState.PLAY);
/*    */     } 
/*    */     
/* 34 */     if (this.isPlay) {
/* 35 */       int id = ((EnumConnectionState)context.channel().attr(NetworkManager.field_150739_c).get()).func_179246_a(this.direction, packet).intValue();
/*    */       
/* 37 */       packet = this.handler.packetReceived(this.direction, id, packet, null);
/*    */       
/* 39 */       if (packet == null)
/*    */         return; 
/*    */     } 
/* 42 */     super.encode(context, packet, out);
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\ffp\OutboundInterceptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */