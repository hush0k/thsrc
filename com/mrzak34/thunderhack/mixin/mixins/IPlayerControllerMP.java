package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerControllerMP.class})
public interface IPlayerControllerMP {
  @Accessor("curBlockDamageMP")
  float getCurBlockDamageMP();
  
  @Accessor("curBlockDamageMP")
  void setCurBlockDamageMP(float paramFloat);
  
  @Accessor("currentBlock")
  BlockPos getCurrentBlock();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IPlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */