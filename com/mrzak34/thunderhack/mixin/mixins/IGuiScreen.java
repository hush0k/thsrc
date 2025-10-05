package com.mrzak34.thunderhack.mixin.mixins;

import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({GuiScreen.class})
public interface IGuiScreen {
  @Accessor("buttonList")
  List<GuiButton> getButtonList();
  
  @Accessor("buttonList")
  void setButtonList(List<GuiButton> paramList);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IGuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */