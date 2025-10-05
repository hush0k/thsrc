package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SPacketExplosion.class})
public interface ISPacketExplosion {
  @Accessor("motionX")
  float getMotionX();
  
  @Accessor("motionY")
  float getMotionY();
  
  @Accessor("motionZ")
  float getMotionZ();
  
  @Accessor("motionX")
  void setMotionX(float paramFloat);
  
  @Accessor("motionY")
  void setMotionY(float paramFloat);
  
  @Accessor("motionZ")
  void setMotionZ(float paramFloat);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ISPacketExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */