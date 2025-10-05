/*    */ package com.mrzak34.thunderhack.command.commands;
/*    */ 
/*    */ import com.mrzak34.thunderhack.command.Command;
/*    */ import com.mrzak34.thunderhack.modules.misc.AutoBuy;
/*    */ import java.util.Arrays;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class AutoBuyCommand
/*    */   extends Command
/*    */ {
/*    */   public AutoBuyCommand() {
/* 12 */     super("ab");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 19 */     if (args.length >= 4) {
/* 20 */       if (args[0] == null) {
/* 21 */         Command.sendMessage(usage());
/*    */       }
/*    */       
/* 24 */       if (args[0].equals("add")) {
/* 25 */         String itemName = args[1];
/* 26 */         String price = args[2].toUpperCase();
/* 27 */         String ench1 = String.join(" ", Arrays.<CharSequence>copyOfRange((CharSequence[])args, 3, args.length - 1));
/* 28 */         String[] ench2 = ench1.split(" ");
/*    */         
/* 30 */         AutoBuy.AutoBuyItem item = new AutoBuy.AutoBuyItem(itemName, ench2, Integer.parseInt(price), 0, (args.length == 4));
/* 31 */         AutoBuy.items.add(item);
/* 32 */         sendMessage("Добавлен предмет " + itemName + " стоимостью до " + price + ((args.length == 4) ? " без чаров" : (" с чарами " + ench1)));
/*    */       } 
/* 34 */     } else if (args.length > 1) {
/* 35 */       if (args[0].equals("list")) {
/* 36 */         AutoBuy.items.forEach(itm -> sendMessage("\n####################\nПредмет: " + itm.getName() + "\nМакс цена: " + itm.getPrice1() + "\nЧары: " + Arrays.toString((Object[])itm.getEnchantments()) + "\n####################"));
/*    */       }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 44 */       if (args[0].equals("remove")) {
/* 45 */         String itemName = args[1];
/* 46 */         boolean removed = false;
/* 47 */         for (AutoBuy.AutoBuyItem abitem : AutoBuy.items) {
/* 48 */           if (Objects.equals(abitem.getName(), itemName)) {
/* 49 */             AutoBuy.items.remove(abitem);
/* 50 */             removed = true;
/*    */           } 
/*    */         } 
/* 53 */         if (removed) {
/* 54 */           sendMessage("Предмет успешно удален!");
/*    */         } else {
/* 56 */           sendMessage("Предмета не существует!");
/*    */         } 
/*    */       } 
/*    */     } else {
/* 60 */       sendMessage(usage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   String usage() {
/* 66 */     return ".ab add/remove/list   пример: .ab add bow 51(1) 48(5)\nАйди зачаров смотреть на сайте https://idpredmetov.ru/id-zacharovanij/\nЕсли левела зачара нет - пишем первый левел";
/*    */   }
/*    */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\command\commands\AutoBuyCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */