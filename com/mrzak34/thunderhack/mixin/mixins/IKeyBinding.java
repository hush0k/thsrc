package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({KeyBinding.class})
public interface IKeyBinding {
  @Accessor("pressed")
  boolean isPressed();
  
  @Accessor("pressed")
  void setPressed(boolean paramBoolean);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IKeyBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */