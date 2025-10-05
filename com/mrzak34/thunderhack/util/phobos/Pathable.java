package com.mrzak34.thunderhack.util.phobos;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface Pathable {
  BlockPos getPos();
  
  Entity getFrom();
  
  Ray[] getPath();
  
  void setPath(Ray... paramVarArgs);
  
  int getMaxLength();
  
  boolean isValid();
  
  void setValid(boolean paramBoolean);
  
  List<BlockingEntity> getBlockingEntities();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\Pathable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */