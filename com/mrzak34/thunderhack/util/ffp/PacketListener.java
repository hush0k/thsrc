package com.mrzak34.thunderhack.util.ffp;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface PacketListener {
  Packet<?> packetReceived(EnumPacketDirection paramEnumPacketDirection, int paramInt, Packet<?> paramPacket, ByteBuf paramByteBuf);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\ffp\PacketListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */