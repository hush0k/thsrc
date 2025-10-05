package com.mrzak34.thunderhack.mixin.ducks;

import com.mrzak34.thunderhack.util.phobos.MotionTracker;

public interface IEntityPlayer {
  MotionTracker getMotionTrackerT();
  
  void setMotionTrackerT(MotionTracker paramMotionTracker);
  
  MotionTracker getBreakMotionTrackerT();
  
  void setBreakMotionTrackerT(MotionTracker paramMotionTracker);
  
  MotionTracker getBlockMotionTrackerT();
  
  void setBlockMotionTrackerT(MotionTracker paramMotionTracker);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\ducks\IEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */