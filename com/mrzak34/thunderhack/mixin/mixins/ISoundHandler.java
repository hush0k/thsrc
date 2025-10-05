package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SoundHandler.class})
public interface ISoundHandler {
  @Accessor("sndManager")
  SoundManager getSoundManager();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\ISoundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */