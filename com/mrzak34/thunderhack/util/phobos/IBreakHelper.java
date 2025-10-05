package com.mrzak34.thunderhack.util.phobos;

import java.util.Collection;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public interface IBreakHelper<T extends CrystalData> {
  BreakData<T> newData(Collection<T> paramCollection);
  
  BreakData<T> getData(Collection<T> paramCollection, List<Entity> paramList, List<EntityPlayer> paramList1, List<EntityPlayer> paramList2);
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\IBreakHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */