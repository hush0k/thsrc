/*     */ package com.mrzak34.thunderhack.modules.player;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mrzak34.thunderhack.events.EventPostSync;
/*     */ import com.mrzak34.thunderhack.events.EventSync;
/*     */ import com.mrzak34.thunderhack.events.PacketEvent;
/*     */ import com.mrzak34.thunderhack.events.TotemPopEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.PositionforFP;
/*     */ import com.mrzak34.thunderhack.util.math.DamageUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class FakePlayer extends Module {
/*  35 */   protected final List<PositionforFP> positions = new ArrayList<>();
/*  36 */   private final ItemStack[] armors = new ItemStack[] { new ItemStack((Item)Items.field_151175_af), new ItemStack((Item)Items.field_151173_ae), new ItemStack((Item)Items.field_151163_ad), new ItemStack((Item)Items.field_151161_ac) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public Setting<Integer> vulnerabilityTick = register(new Setting("Vulnerability Tick", Integer.valueOf(4), Integer.valueOf(0), Integer.valueOf(10)));
/*  43 */   public Setting<Integer> resetHealth = register(new Setting("Reset Health", Integer.valueOf(10), Integer.valueOf(0), Integer.valueOf(36)));
/*  44 */   public Setting<Integer> tickRegenVal = register(new Setting("Tick Regen", Integer.valueOf(4), Integer.valueOf(0), Integer.valueOf(30)));
/*  45 */   public Setting<Integer> startHealth = register(new Setting("Start Health", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(30)));
/*  46 */   public Setting<String> nameFakePlayer = register(new Setting("Name FakePlayer", "Ebatte_Sratte"));
/*     */   int incr;
/*  48 */   EntityOtherPlayerMP clonedPlayer = null;
/*     */   
/*     */   boolean beforePressed;
/*  51 */   ArrayList<playerInfo> listPlayers = new ArrayList<>();
/*  52 */   int index = 0;
/*  53 */   private final Setting<Boolean> copyInventory = register(new Setting("Copy Inventory", Boolean.valueOf(false)));
/*  54 */   private final Setting<Boolean> playerStacked = register(new Setting("Player Stacked", Boolean.valueOf(true), v -> !((Boolean)this.copyInventory.getValue()).booleanValue()));
/*  55 */   private final Setting<Boolean> onShift = register(new Setting("On Shift", Boolean.valueOf(false)));
/*  56 */   private final Setting<Boolean> simulateDamage = register(new Setting("Simulate Damage", Boolean.valueOf(false)));
/*  57 */   private final Setting<Boolean> resistance = register(new Setting("Resistance", Boolean.valueOf(true)));
/*  58 */   private final Setting<Boolean> pop = register(new Setting("Pop", Boolean.valueOf(true)));
/*  59 */   private final Setting<Boolean> record2 = register(new Setting("Record", Boolean.valueOf(true)));
/*  60 */   private final Setting<Boolean> play = register(new Setting("Play", Boolean.valueOf(true)));
/*     */   private int ticks;
/*     */   
/*     */   public FakePlayer() {
/*  64 */     super("FakePlayer", "фейкплеер для тестов", Module.Category.PLAYER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLogout() {
/*  69 */     if (isOn()) {
/*  70 */       disable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  76 */     this.incr = 0;
/*  77 */     this.beforePressed = false;
/*  78 */     if (mc.field_71439_g == null || mc.field_71439_g.field_70128_L) {
/*  79 */       disable();
/*     */       return;
/*     */     } 
/*  82 */     if (!((Boolean)this.onShift.getValue()).booleanValue()) {
/*  83 */       spawnPlayer();
/*     */     }
/*     */   }
/*     */   
/*     */   void spawnPlayer() {
/*  88 */     this.clonedPlayer = new EntityOtherPlayerMP((World)mc.field_71441_e, new GameProfile(UUID.fromString("fdee323e-7f0c-4c15-8d1c-0f277442342a"), (String)this.nameFakePlayer.getValue()));
/*     */     
/*  90 */     this.clonedPlayer.func_82149_j((Entity)mc.field_71439_g);
/*  91 */     this.clonedPlayer.field_70759_as = mc.field_71439_g.field_70759_as;
/*  92 */     this.clonedPlayer.field_70177_z = mc.field_71439_g.field_70177_z;
/*  93 */     this.clonedPlayer.field_70125_A = mc.field_71439_g.field_70125_A;
/*     */     
/*  95 */     this.clonedPlayer.func_71033_a(GameType.SURVIVAL);
/*  96 */     this.clonedPlayer.func_70606_j(((Integer)this.startHealth.getValue()).intValue());
/*     */     
/*  98 */     mc.field_71441_e.func_73027_a(-1234 + this.incr, (Entity)this.clonedPlayer);
/*  99 */     this.incr++;
/*     */     
/* 101 */     if (((Boolean)this.copyInventory.getValue()).booleanValue()) {
/* 102 */       this.clonedPlayer.field_71071_by.func_70455_b(mc.field_71439_g.field_71071_by);
/*     */     
/*     */     }
/* 105 */     else if (((Boolean)this.playerStacked.getValue()).booleanValue()) {
/*     */       
/* 107 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 109 */         ItemStack item = this.armors[i];
/*     */         
/* 111 */         item.func_77966_a((i == 3) ? Enchantments.field_185297_d : Enchantments.field_180310_c, 4);
/*     */ 
/*     */ 
/*     */         
/* 115 */         this.clonedPlayer.field_71071_by.field_70460_b.set(i, item);
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     if (((Boolean)this.resistance.getValue()).booleanValue())
/* 120 */       this.clonedPlayer.func_70690_d(new PotionEffect(Potion.func_188412_a(11), 123456789, 0)); 
/* 121 */     this.clonedPlayer.func_70030_z();
/* 122 */     this.listPlayers.add(new playerInfo(this.clonedPlayer.func_70005_c_()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 128 */     if (((Boolean)this.onShift.getValue()).booleanValue() && mc.field_71474_y.field_74311_E.func_151468_f() && !this.beforePressed)
/* 129 */     { this.beforePressed = true;
/* 130 */       spawnPlayer(); }
/* 131 */     else { this.beforePressed = false; }
/*     */ 
/*     */     
/* 134 */     for (int i = 0; i < this.listPlayers.size(); i++) {
/* 135 */       if (((playerInfo)this.listPlayers.get(i)).update()) {
/* 136 */         int finalI = i;
/*     */ 
/*     */         
/* 139 */         Optional<EntityPlayer> temp = mc.field_71441_e.field_73010_i.stream().filter(e -> e.func_70005_c_().equals(((playerInfo)this.listPlayers.get(finalI)).name)).findAny();
/* 140 */         if (temp.isPresent() && (
/* 141 */           (EntityPlayer)temp.get()).func_110143_aJ() < 20.0F)
/* 142 */           ((EntityPlayer)temp.get()).func_70606_j(((EntityPlayer)temp.get()).func_110143_aJ() + 1.0F); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 148 */     if (mc.field_71441_e != null) {
/* 149 */       for (int i = 0; i < this.incr; i++) {
/* 150 */         mc.field_71441_e.func_73028_b(-1234 + i);
/*     */       }
/*     */     }
/* 153 */     this.listPlayers.clear();
/* 154 */     this.positions.clear();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 159 */     if (((Boolean)this.simulateDamage.getValue()).booleanValue() && 
/* 160 */       event.getPacket() instanceof SPacketSoundEffect) {
/* 161 */       SPacketSoundEffect packetSoundEffect = (SPacketSoundEffect)event.getPacket();
/* 162 */       if (packetSoundEffect.func_186977_b() == SoundCategory.BLOCKS && packetSoundEffect.func_186978_a() == SoundEvents.field_187539_bB) {
/* 163 */         for (Entity entity : new ArrayList(mc.field_71441_e.field_72996_f)) {
/* 164 */           if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && 
/* 165 */             entity.func_70092_e(packetSoundEffect.func_149207_d(), packetSoundEffect.func_149211_e(), packetSoundEffect.func_149210_f()) <= 36.0D) {
/* 166 */             for (EntityPlayer entityPlayer : mc.field_71441_e.field_73010_i) {
/* 167 */               if (entityPlayer.func_70005_c_().equals(this.nameFakePlayer.getValue())) {
/*     */ 
/*     */ 
/*     */                 
/* 171 */                 Optional<playerInfo> temp = this.listPlayers.stream().filter(e -> e.name.equals(entityPlayer.func_70005_c_())).findAny();
/*     */                 
/* 173 */                 if (!temp.isPresent() || !((playerInfo)temp.get()).canPop()) {
/*     */                   continue;
/*     */                 }
/* 176 */                 float damage = DamageUtil.calculateDamage(packetSoundEffect.func_149207_d(), packetSoundEffect.func_149211_e(), packetSoundEffect.func_149210_f(), (Entity)entityPlayer, false);
/* 177 */                 if (damage > entityPlayer.func_110143_aJ())
/* 178 */                 { entityPlayer.func_70606_j(((Integer)this.resetHealth.getValue()).intValue());
/* 179 */                   if (((Boolean)this.pop.getValue()).booleanValue()) {
/* 180 */                     mc.field_71452_i.func_191271_a((Entity)entityPlayer, EnumParticleTypes.TOTEM, 30);
/* 181 */                     mc.field_71441_e.func_184134_a(entityPlayer.field_70165_t, entityPlayer.field_70163_u, entityPlayer.field_70161_v, SoundEvents.field_191263_gW, entity.func_184176_by(), 1.0F, 1.0F, false);
/*     */                   } 
/* 183 */                   MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(entityPlayer)); }
/* 184 */                 else { entityPlayer.func_70606_j(entityPlayer.func_110143_aJ() - damage); }
/*     */                 
/* 186 */                 ((playerInfo)temp.get()).tickPop = 0;
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMotionUpdateEvent(EventSync event) {
/* 199 */     if (!((Boolean)this.record2.getValue()).booleanValue()) {
/* 200 */       if (((Boolean)this.play.getValue()).booleanValue()) {
/* 201 */         if (this.positions.isEmpty()) {
/*     */           return;
/*     */         }
/*     */         
/* 205 */         if (this.index >= this.positions.size()) {
/* 206 */           this.index = 0;
/*     */         }
/*     */         
/* 209 */         if (this.ticks++ % 2 == 0) {
/* 210 */           PositionforFP p = this.positions.get(this.index++);
/* 211 */           this.clonedPlayer.field_70177_z = p.getYaw();
/* 212 */           this.clonedPlayer.field_70125_A = p.getPitch();
/* 213 */           this.clonedPlayer.field_70759_as = p.getHead();
/* 214 */           this.clonedPlayer.func_180426_a(p
/* 215 */               .getX(), p.getY(), p.getZ(), p.getYaw(), p.getPitch(), 3, false);
/*     */         } 
/*     */       } else {
/*     */         
/* 219 */         this.index = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMotionUpdateEventPost(EventPostSync event) {
/* 227 */     if (((Boolean)this.record2.getValue()).booleanValue() && 
/* 228 */       this.ticks++ % 2 == 0) {
/* 229 */       this.positions.add(new PositionforFP((EntityPlayer)mc.field_71439_g));
/*     */     }
/*     */   }
/*     */   
/*     */   public enum movingmode
/*     */   {
/* 235 */     None, Line, Circle, Random;
/*     */   }
/*     */   
/*     */   class playerInfo {
/*     */     final String name;
/* 240 */     int tickPop = -1;
/* 241 */     int tickRegen = 0;
/*     */ 
/*     */     
/*     */     public playerInfo(String name) {
/* 245 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean update() {
/* 250 */       if (this.tickPop != -1 && 
/* 251 */         ++this.tickPop >= ((Integer)FakePlayer.this.vulnerabilityTick.getValue()).intValue()) {
/* 252 */         this.tickPop = -1;
/*     */       }
/* 254 */       if (++this.tickRegen >= ((Integer)FakePlayer.this.tickRegenVal.getValue()).intValue()) {
/* 255 */         this.tickRegen = 0;
/* 256 */         return true;
/* 257 */       }  return false;
/*     */     }
/*     */     
/*     */     boolean canPop() {
/* 261 */       return (this.tickPop == -1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\player\FakePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */