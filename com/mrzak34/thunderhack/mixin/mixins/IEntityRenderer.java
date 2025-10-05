package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({EntityRenderer.class})
public interface IEntityRenderer {
  @Invoker("orientCamera")
  void orientCam(float paramFloat);
  
  @Invoker("setupCameraTransform")
  void invokeSetupCameraTransform(float paramFloat, int paramInt);
  
  @Accessor("rendererUpdateCount")
  int getRendererUpdateCount();
  
  @Accessor("rainXCoords")
  float[] getRainXCoords();
  
  @Accessor("rainYCoords")
  float[] getRainYCoords();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */