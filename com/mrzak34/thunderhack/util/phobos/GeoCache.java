package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.math.Vec3i;

public interface GeoCache {
  void cache();
  
  int getRadius(double paramDouble);
  
  Vec3i get(int paramInt);
  
  Vec3i[] array();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\GeoCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */