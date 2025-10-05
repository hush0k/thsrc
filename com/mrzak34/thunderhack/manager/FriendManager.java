/*    */ package com.mrzak34.thunderhack.manager;
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class FriendManager {
/* 12 */   public static List<String> friends = new ArrayList<>();
/*    */   
/*    */   public static void loadFriends() {
/*    */     try {
/* 16 */       File file = new File("ThunderHack/misc/friends.txt");
/*    */       
/* 18 */       if (file.exists()) {
/* 19 */         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
/* 20 */           while (reader.ready()) {
/* 21 */             friends.add(reader.readLine());
/*    */           }
/*    */         }
/*    */       
/*    */       }
/* 26 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static void saveFriends() {
/* 31 */     File file = new File("ThunderHack/misc/friends.txt");
/*    */     try {
/* 33 */       file.createNewFile();
/* 34 */     } catch (Exception exception) {}
/*    */ 
/*    */     
/* 37 */     try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
/* 38 */       for (String friend : friends) {
/* 39 */         writer.write(friend + "\n");
/*    */       }
/* 41 */     } catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFriend(String name) {
/* 46 */     return friends.stream().anyMatch(friend -> friend.equalsIgnoreCase(name));
/*    */   }
/*    */   
/*    */   public boolean isFriend(EntityPlayer player) {
/* 50 */     return isFriend(player.func_70005_c_());
/*    */   }
/*    */   
/*    */   public boolean isEnemy(EntityPlayer player) {
/* 54 */     return false;
/*    */   }
/*    */   
/*    */   public void removeFriend(String name) {
/* 58 */     friends.remove(name);
/*    */   }
/*    */   
/*    */   public List<String> getFriends() {
/* 62 */     return friends;
/*    */   }
/*    */   
/*    */   public void addFriend(String friend) {
/* 66 */     friends.add(friend);
/*    */     try {
/* 68 */       ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + friend + "/100.png", friend);
/* 69 */     } catch (Exception e) {
/* 70 */       Command.sendMessage("Не удалось загрузить скин!");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 76 */     friends.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\manager\FriendManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */