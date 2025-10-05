package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({RenderManager.class})
public interface IRenderManager {
  @Accessor("renderPosX")
  double getRenderPosX();
  
  @Accessor("renderPosY")
  double getRenderPosY();
  
  @Accessor("renderPosZ")
  double getRenderPosZ();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IRenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */