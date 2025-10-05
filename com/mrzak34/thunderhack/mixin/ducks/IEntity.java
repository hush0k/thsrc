package com.mrzak34.thunderhack.mixin.ducks;

import com.mrzak34.thunderhack.modules.combat.BackTrack;
import com.mrzak34.thunderhack.modules.render.PlayerTrails;
import com.mrzak34.thunderhack.util.Timer;
import com.mrzak34.thunderhack.util.phobos.Dummy;
import java.util.List;

public interface IEntity extends Dummy {
  void setInPortal(boolean paramBoolean);
  
  boolean isPseudoDeadT();
  
  void setPseudoDeadT(boolean paramBoolean);
  
  Timer getPseudoTimeT();
  
  long getTimeStampT();
  
  boolean isInWeb();
  
  List<BackTrack.Box> getPosition_history();
  
  List<PlayerTrails.Trail> getTrails();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\ducks\IEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */