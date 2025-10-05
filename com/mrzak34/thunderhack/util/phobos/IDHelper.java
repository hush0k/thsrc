/*     */ package com.mrzak34.thunderhack.util.phobos;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.mixin.mixins.ICPacketUseEntity;
/*     */ import com.mrzak34.thunderhack.modules.combat.AutoCrystal;
/*     */ import com.mrzak34.thunderhack.util.InventoryUtil;
/*     */ import com.mrzak34.thunderhack.util.Util;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
/*     */ import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
/*     */ import net.minecraft.network.play.server.SPacketSpawnMob;
/*     */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPainting;
/*     */ import net.minecraft.network.play.server.SPacketSpawnPlayer;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class IDHelper
/*     */ {
/*  29 */   private static final ScheduledExecutorService THREAD = ThreadUtil.newDaemonScheduledExecutor("ID-Helper");
/*     */   
/*     */   private volatile int highestID;
/*     */   
/*     */   private boolean updated;
/*     */   
/*     */   public IDHelper() {
/*  36 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public static CPacketUseEntity attackPacket(int id) {
/*  40 */     CPacketUseEntity packet = new CPacketUseEntity();
/*  41 */     ((ICPacketUseEntity)packet).setEntityId(id);
/*  42 */     ((ICPacketUseEntity)packet).setAction(CPacketUseEntity.Action.ATTACK);
/*  43 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  49 */     if (event.getPacket() instanceof SPacketSpawnObject) {
/*  50 */       checkID(((SPacketSpawnObject)event.getPacket()).func_149001_c());
/*     */     }
/*  52 */     if (event.getPacket() instanceof SPacketSpawnExperienceOrb) {
/*  53 */       checkID(((SPacketSpawnExperienceOrb)event.getPacket()).func_148985_c());
/*     */     }
/*  55 */     if (event.getPacket() instanceof SPacketSpawnPlayer) {
/*  56 */       checkID(((SPacketSpawnPlayer)event.getPacket()).func_148943_d());
/*     */     }
/*  58 */     if (event.getPacket() instanceof SPacketSpawnGlobalEntity) {
/*  59 */       checkID(((SPacketSpawnGlobalEntity)event.getPacket()).func_149052_c());
/*     */     }
/*  61 */     if (event.getPacket() instanceof SPacketSpawnPainting) {
/*  62 */       checkID(((SPacketSpawnPainting)event.getPacket()).func_148965_c());
/*     */     }
/*  64 */     if (event.getPacket() instanceof SPacketSpawnMob) {
/*  65 */       checkID(((SPacketSpawnMob)event.getPacket()).func_149024_d());
/*     */     }
/*     */   }
/*     */   
/*     */   public int getHighestID() {
/*  70 */     return this.highestID;
/*     */   }
/*     */   
/*     */   public void setHighestID(int id) {
/*  74 */     this.highestID = id;
/*     */   }
/*     */   
/*     */   public boolean isUpdated() {
/*  78 */     return this.updated;
/*     */   }
/*     */   
/*     */   public void setUpdated(boolean updated) {
/*  82 */     this.updated = updated;
/*     */   }
/*     */   
/*     */   public void update() {
/*  86 */     int highest = getHighestID();
/*  87 */     for (Entity entity : Util.mc.field_71441_e.field_72996_f) {
/*  88 */       if (entity.func_145782_y() > highest) {
/*  89 */         highest = entity.func_145782_y();
/*     */       }
/*     */     } 
/*     */     
/*  93 */     if (highest > this.highestID) {
/*  94 */       this.highestID = highest;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSafe(List<EntityPlayer> players, boolean holdingCheck, boolean toolCheck) {
/* 101 */     if (!holdingCheck) {
/* 102 */       return true;
/*     */     }
/*     */     
/* 105 */     for (EntityPlayer player : players) {
/* 106 */       if (isDangerous(player, true, toolCheck)) {
/* 107 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDangerous(EntityPlayer player, boolean holdingCheck, boolean toolCheck) {
/* 117 */     if (!holdingCheck) {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     return (InventoryUtil.isHolding(player, (Item)Items.field_151031_f) || 
/* 122 */       InventoryUtil.isHolding(player, Items.field_151062_by) || (toolCheck && (player
/*     */       
/* 124 */       .func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemPickaxe || player
/* 125 */       .func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemSpade)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attack(AutoCrystal.SwingTime breakSwing, AutoCrystal.PlaceSwing godSwing, int idOffset, int packets, int sleep) {
/* 133 */     if (sleep <= 0) {
/* 134 */       attackPackets(breakSwing, godSwing, idOffset, packets);
/*     */     } else {
/* 136 */       THREAD.schedule(() -> { update(); attackPackets(breakSwing, godSwing, idOffset, packets); }sleep, TimeUnit.MILLISECONDS);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attackPackets(AutoCrystal.SwingTime breakSwing, AutoCrystal.PlaceSwing godSwing, int idOffset, int packets) {
/* 149 */     for (int i = 0; i < packets; i++) {
/* 150 */       int id = this.highestID + idOffset + i;
/* 151 */       Entity entity = Util.mc.field_71441_e.func_73045_a(id);
/* 152 */       if (entity == null || entity instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/* 153 */         if (godSwing == AutoCrystal.PlaceSwing.Always && breakSwing == AutoCrystal.SwingTime.Pre)
/*     */         {
/* 155 */           Swing.Packet.swing(EnumHand.MAIN_HAND);
/*     */         }
/*     */         
/* 158 */         CPacketUseEntity packet = attackPacket(id);
/* 159 */         Util.mc.field_71439_g.field_71174_a.func_147297_a((Packet)packet);
/*     */         
/* 161 */         if (godSwing == AutoCrystal.PlaceSwing.Always && breakSwing == AutoCrystal.SwingTime.Post) {
/* 162 */           Swing.Packet.swing(EnumHand.MAIN_HAND);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     if (godSwing == AutoCrystal.PlaceSwing.Once) {
/* 168 */       Swing.Packet.swing(EnumHand.MAIN_HAND);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkID(int id) {
/* 173 */     if (id > this.highestID)
/* 174 */       this.highestID = id; 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhac\\util\phobos\IDHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */