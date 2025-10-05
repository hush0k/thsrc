package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({Render.class})
abstract class MixinRenderer {
  @Shadow
  protected boolean field_188301_f;
  
  @Shadow
  @Final
  protected RenderManager field_76990_c;
  
  @Shadow
  protected abstract boolean func_180548_c(Entity paramEntity);
  
  @Shadow
  protected abstract int func_188298_c(Entity paramEntity);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\MixinRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */