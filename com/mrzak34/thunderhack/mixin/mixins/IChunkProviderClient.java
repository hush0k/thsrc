package com.mrzak34.thunderhack.mixin.mixins;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ChunkProviderClient.class})
public interface IChunkProviderClient {
  @Accessor("loadedChunks")
  Long2ObjectMap<Chunk> getLoadedChunks();
}


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\mixin\mixins\IChunkProviderClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */