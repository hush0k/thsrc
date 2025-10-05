package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Block.class})
public interface IBlock {
  @Accessor("blockResistance")
  float getBlockResistance();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */