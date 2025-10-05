/*    */ package com.mrzak34.thunderhack.util.ffp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.EnumPacketDirection;
/*    */ import net.minecraft.network.NettyPacketDecoder;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class InboundInterceptor
/*    */   extends NettyPacketDecoder {
/*    */   private final EnumPacketDirection direction;
/*    */   private final NetworkHandler handler;
/*    */   private boolean isPlay;
/*    */   
/*    */   public InboundInterceptor(NetworkHandler handler, EnumPacketDirection direction) {
/* 22 */     super(direction);
/* 23 */     this.handler = handler;
/* 24 */     this.direction = direction;
/* 25 */     this.isPlay = false;
/*    */   }
/*    */   
/*    */   protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {
/* 29 */     if (in.readableBytes() != 0) {
/*    */       
/* 31 */       int start_index = in.readerIndex();
/* 32 */       super.decode(context, in, out);
/*    */       
/* 34 */       if (!this.isPlay) {
/* 35 */         EnumConnectionState state = (EnumConnectionState)context.channel().attr(NetworkManager.field_150739_c).get();
/* 36 */         this.isPlay = (state == EnumConnectionState.PLAY);
/*    */       } 
/*    */       
/* 39 */       if (this.isPlay && out.size() > 0) {
/* 40 */         Packet<?> packet = (Packet)out.get(0);
/* 41 */         int id = ((EnumConnectionState)context.channel().attr(NetworkManager.field_150739_c).get()).func_179246_a(this.direction, packet).intValue();
/* 42 */         int end_index = in.readerIndex();
/*    */         
/* 44 */         in.readerIndex(start_index);
/* 45 */         packet = this.handler.packetReceived(this.direction, id, packet, in);
/* 46 */         in.readerIndex(end_index);
/*    */         
/* 48 */         if (packet == null) { out.clear(); }
/* 49 */         else { out.set(0, packet); }
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\ffp\InboundInterceptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */