package com.mrzak34.thunderhack.mixin.mixins;

import java.util.Map;
import java.util.UUID;
import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.world.BossInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({GuiBossOverlay.class})
public interface IGuiBossOverlay {
  @Accessor("mapBossInfos")
  Map<UUID, BossInfoClient> getMapBossInfos();
  
  @Invoker("render")
  void invokeRender(int paramInt1, int paramInt2, BossInfo paramBossInfo);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IGuiBossOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */