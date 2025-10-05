package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketPlayerTryUseItemOnBlock.class})
public interface ICPacketPlayerTryUseItemOnBlock {
  @Accessor("hand")
  void setHand(EnumHand paramEnumHand);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ICPacketPlayerTryUseItemOnBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */