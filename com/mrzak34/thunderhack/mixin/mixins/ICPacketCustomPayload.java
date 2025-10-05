package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketCustomPayload.class})
public interface ICPacketCustomPayload {
  @Accessor("data")
  void setData(PacketBuffer paramPacketBuffer);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ICPacketCustomPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */