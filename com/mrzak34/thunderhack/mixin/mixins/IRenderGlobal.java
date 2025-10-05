package com.mrzak34.thunderhack.mixin.mixins;

import java.util.Map;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({RenderGlobal.class})
public interface IRenderGlobal {
  @Accessor("damagedBlocks")
  Map<Integer, DestroyBlockProgress> getDamagedBlocks();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IRenderGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */