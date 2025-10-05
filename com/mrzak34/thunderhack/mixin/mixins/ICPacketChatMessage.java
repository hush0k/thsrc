package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.play.client.CPacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketChatMessage.class})
public interface ICPacketChatMessage {
  @Accessor("message")
  String getMessage();
  
  @Accessor("message")
  void setMessage(String paramString);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ICPacketChatMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */