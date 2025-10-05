/*     */ package com.mrzak34.thunderhack.modules.misc;
/*     */ 
/*     */ import com.mrzak34.thunderhack.events.AttackEvent;
/*     */ import com.mrzak34.thunderhack.modules.Module;
/*     */ import com.mrzak34.thunderhack.setting.Setting;
/*     */ import com.mrzak34.thunderhack.util.Timer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class EbatteSratte extends Module {
/*     */   Timer timer;
/*     */   
/*     */   public EbatteSratte() {
/*  14 */     super("Ebatte Sratte", "авто токсик и не только xD", Module.Category.MISC);
/*     */ 
/*     */     
/*  17 */     this.timer = new Timer();
/*  18 */     this.chatprefix = "";
/*     */     
/*  20 */     this.delay = register(new Setting("Delay", Integer.valueOf(500), Integer.valueOf(1), Integer.valueOf(1000)));
/*     */ 
/*     */     
/*  23 */     this.Mode = register(new Setting("Server", mode.FunnyGame));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  29 */     this.Mode2 = register(new Setting("Mode", mode2.Hard));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  36 */     this.FriendlyString = new String[] { "Помурчи в дискордике пжжжж", "ты сегодня такая няшечка мммммм", "Котик извини", "Скинь ножки пжжжжжжж", "Скинь кфг пжжжж" };
/*     */     
/*  38 */     this.Lite = new String[] { "Блять киллку настрой она же мисает я ебал АХАХААХ", "Блять удаляй чит он же нихуя не бустит", "Че так слабо АХАХАХАХ", "Сука как будто с ботом пехаюсь", "Блять включи киллку хоть заебал сосать", "Сука ты за этот чит бабки отдал лошара", "Ебать я тебя Вращаю на хуе" };
/*     */     
/*  40 */     this.Arsik2005 = new String[] { "Я ТВОЕЙ МАТЕРИ ЖИРНОЙ СЛОМАЛ СПИНУ И СБРОСИЛ С 12-И ЭТАЖНОГО ДОМА ОНА ТАК РАЗБИЛАСЬ ПРИЯТНО АЖ ТЕЛО КАК ПОПРЫГУН ПОЛЕТЕЛО ВЫШЕ НЕБЕС ПОТОМ ТРУП ТВОЕЙ МАМАШИ УПАЛ И СЛОМАЛ АСВАЛЬТ ТОНКОЕ ЧМО НА ПОДВЕСКЕ", "Я ТВОЕЙ МАТЕРИ ПИЗДУ ДО ПОЛЬШИ РАЗРЕЗАЛ Я ХУЕЮ Я В ЭТОЙ ПИЗДЕ ПОТЕРЯЛСЯ НАХУЙ Я ТАМ НАШЁЛ 3 КОНЦА СВЕТА И САТАНУ НАХУЙ ЕБАТЬ Я АХУЕЛ ТАМ ДАЖЕ ВИКИНГИ ПЛОТЬЮ ПИЗДЫ ТВОЕЙ МАМАШИ ПИТАЮТСЯ", "ЕБАТЬ ТЫ ОТБРОС Я В ПИЗДУ ТВОЕЙ МАТЕРИ КЛЕЩЕЙ ЗАКИНУЛ ОНА ЭТОМУ МОЛИЛАСЬ ОНА МНЕ ВСЕ ДЕНЬГИ ОТДАЛА", "ОТБРОС ЕБЛИВЫЙ Я КОГДА УВИДЕЛ ТВОЙ 1 СМ ХУЙ ПОКА ЕБАЛ ТЕБЯ ЕБАТЬ Я АХУЕЛ ХУЛИ ТЕБЯ ЕЩЁ В ШКОЛЕ НЕ ПИЗДЯТ ЧМО", "ЧМО ТЕБЯ КАЖДУЮ МИНУТУ В ШКОЛЕ ПИЗДЯТ ИДИ В УГОЛКЕ КАК В ШКОЛЕ ПОСИДИ ПОПЛАЧ ЧМЫРЬ МАЛОЛЕТНИЙ", "ЧМО Я ТВОЕМУ ОТЦУ ХУЙ РАЗРЕЗАЛ КОГДА ОН ШЁЛ С АВТОМАТОМ НА УЛИЦЕ ЕБАТЬ ОН ЧМО МНЕ СТЫДНО ЧТО Я ТЕБЯ ВООБЩЕ ВИЖУ ПОТОМУ ЧТО ТВОЙ БАТЯ НА ПРОЩАНИЕ СКАЗАЛ НЕ ОТДАВАЙ СЫНУ ДЕНЬГИ Я В АХУЕ", "СТЫДОБА СКАЖИ БАТЕ ЧТО БЫ КУПИЛ ТЕБЕ СОФТ THUNDERHACK А НЕ ТВОЙ ЁБАННЫЙ МАТИКС ЗА 5 РУБЛЕЙ НАХУЙ КОТОРЫЙ МОЖНО СКАЧАТЬ В FLAUNCHER И ВООБЩЕ БОМЖИК ХУЛИ ТЫ СМОТРИШЬ НА ЛЮДЕЙ РАБЫНЯ РАБОВ", "ЧМО ЕБАТЬ У ТЕБЯ СЕСТРА УРОДИНА Я ВООБЩЕ ЕБАЛ МНЕ КАЖЕТСЯ ЕСЛИ ТВОЯ СЕСТРА ПОЙДЁТ НА РАБОТУ ШЛЮХИ ОНА ПЛАТИТЬ ЗА СЕКС БУДЕТ", "СКАЖИ СЕСТРЕ ЧТО БЫ ВСЕМ ПОДРЯД СИСЬКИ В ШКОЛЕ НЕ ПОКАЗЫВАЛА АТО У НЕЁ НЕ СИСЬКИ А МРАМОРНАЯ ПИЗДА ИЗ ВАКУОЛЬНОЙ ХУЙНИ ЕЁ ДАЖЕ СЕЛИКОН НЕ СПАСЁТ БЛЯТЬ МЕНЯ ЧУТЬ НЕ ВЫРВАЛО КОГДА ОНА МНЕ СИСЬКИ ПОКАЗАЛА", "ЕБАТЬ ГУБЫ ТВОЕЙ СЕСТРИЧКИ КАК 4 МУСОРОВОЗА МНЕ КАЖЕТСЯ ЕСЛИ ОНА БУДЕТ С КЕМТО СОСАТЬСЯ ТО ЕЁ ГУБЫ ЗАСОСУТ КОГО ТО НАХУЙ", "ЕБАТЬ СКАЖИ СВОЕЙ МАТЕРИ ЧТО БЫ МОЙ ОГУРЕЦ С МОЛОКОМ НЕ ЗАГЛАТЫВАЛА АТО ПРЯМО ПРИ МНЕ ЕЙ ПИЗДУ СНЕСЛО Я ДУМАЛ ОНА ПОКА СОСЁТ СДОХНЕТ ИЗЗА ПОТЕРИ ПУЛЬСА НАХУЙ", "ХАХАХХА Я ТВОЮ МАМКУ В УТОПИЛ ЕЛИ ЕЛИ Я АХУЕЛ КОГДА ПО НОВОСТЯМ ПОКАЗАЛО ЧТО ТВОЯ МАТЬ ДОХЛАЯ ПОЛ ЧЁРНОГО МОРЯ ПЕРЕКРЫЛА ПОПИТЬ ЗАХОТЕЛА НАХУЙ ПОТОМ СДОХЛА КСТАТИ ЕЁ ТРУП СМЕШНО ПОПЛЫЛ АЖ САМОЛЁТ СБИЛ" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     this.myString = new String[] { "Я TBOЮ MATЬ БЛЯTЬ ПOДВEСИЛ НА КОЛ ОНА EБAHAЯ БЛЯДИHA", "МАМАШУ ТВОЮ АРМАТУРОЙ С ШИПАМИ ПО ХРЕБТУ ПИЗДИЛ", "Я ТВОЕЙ МАТЕРИ ПИЗДАК РАЗОРВАЛ СЫН БЛЯДИНЫ ТЫ ЕБАННОЙ", "ВГЕТАЙ ТАНДЕРХАК СЫН ЕБАННОЙ ШЛЮХИ", "ТЫ ПСИНА БЕЗ БРЕЙНА ДАВАЙ ТЕРПИ ТЕРПИ", "я твою мать об стол xуяpил сын тупорылой овчарки мать продал чит на кубики купил?", "СКУЛИ СВИHЬЯ ЕБAHAЯ , Я ТВОЮ MATЬ ПОДBECИЛ НА ЦЕПЬ И С ОКНА СБРОСИЛ ОНА ФЕМИНИСТКА ЕБАHAЯ ОНА СВОИМ ВЕСОМ 180КГ ПРОБУРИЛАСЬ ДО ЯДРА ЗЕМЛИ И СГОРЕЛА HAXУЙ АХАХАХАХА ЕБATЬ ОНА ГОРИТ ПРИКОЛЬНО", "ты мейн сначало свой пукни потом чет овысирай, с основы пиши нищ", "БАБКА СДОХЛА ОТ СТАРОСТИ Т.К. КОГДА ТВОЮ МATЬ РОДИЛИ ЕЙ БЫЛО 99 ЛЕТ И ОТ НЕРВОВ РАДОСТИ ОНА СДОХЛА ОЙ БЛ9TЬ ОТ РАДОСТИ ДЕД ТОЖЕ ОТ РАДОСТИ СДОХ HAXУЙ ДOЛБAЁБ EБAHЫЙ ЧТОБЫ ВЫЖИТЬ НА ПОМОЙКА МATЬ ТВOЯ ПOКА НЕ СДОХЛА EБAЛAСЬ С МУЖИКАМИ ЗА 2 КОПЕЙКИ", "ТЫ ПОНИМАЕШЬ ЧТО Я ТВОЮ МАТЬ ОТПРАВИЛ СО СВОЕГО XУЯ В НЕБО, ЧТОБ ОНА СВОИМ ПИЗДAKOМ ПРИНИМАЛА МИТЕОРИТНУЮ АТАКУ?)", "ТЫ ПОНИМАЕШЬ ЧТО ТBОЯ МATЬ СИДИТ У МЕНЯ НА ЦЕПИ И КАК БУЛЬДОГ EБАHЫЙ НА МОЙ XУЙ СЛЮНИ БЛ9ДЬ ПУСКАЕТ?))", "В ДЕТДОМЕ ТЕБЯ ПИЗДUЛИ ВСЕ КТО МОГ В ИТОГЕ ТЫ СДОХ НА УЛИЦЕ В 13 ЛЕТ ОТ НЕДОСТАТКА ЕДЫ ВОДУ ТЫ ЖЕ БРАЛ ЭТИМ ФИЛЬТРОМ И МОЧОЙ ДOЛБAЁБ ЕБAHЫЙ СУКA БЕЗ МATEPHAЯ ХУETА.", "Чё как нищий, купи тандерхак не позорься", "Your mom owned by Thunderhack", "АЛО БОМЖАТИНА БЕЗ МАТЕРИ Я ТВОЮ МАТЬ ОБ СТОЛ УБИЛ ЧЕРЕП ЕЙ РАЗБИЛ НОГОЙ БАТЮ ТВОЕГО С ОКНА ВЫКИНУЛ СУКА ЧМО ЕБАННОЕ ОТВЕТЬ ЧМО ЕБЛАН ТВАРЬ ШАЛАВА" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.Erp = new String[] { "Помурчи пж", "Я ТЕБЯ ЛЮБЛЮ", "А ты меня любишь?", "i love u", "Мурчи громче блин", "Сейчас на Земле официально 7 999 999 999 людей, которые мне нравятся меньше тебя", "Ты милый. Можно тебя оставить себе?", "Мурлычу от любви", "Ты самая лучшая в моей жизни", "Ты очень красивая", "Люблю тебя всем сердцем и душой", "Я не смогу жить без тебя" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.Funny = new String[] { "ПОГНАЛИ НАХУЙ!!!!!", "Я Бомж Валера", "Повар Спрашивает Повара Кем Работаешь А Повар Говорит Я МЕДИК СУКА", "Ну Чо Нормальна Тебе? Нармальна", "ААААА ЧИТЕР АААА", "Я В ШОКЕ БЛЯТЬ", "Я Вахуи С Тебя", "КАК ВЫРУБИТЬ ОПТИФАЙН", "СКАЧАТЬ МОД НА ФЛАЙ МАЙНКРАФТ", "Бебры Все В Адидасах", "РЕБЯТА НАУЧИТЕ МЕНЯ ИСКАТЬ АЛМАЗЫ ЗА 0 СЕК", "Скачать мод оптифайн на фпс сегодня", "Как удалить чит?", "Ну Чо Пацаны Погнали Нахуй!!!!", "Как скрафтить верстак скажите пж", "Казахстан угражает бамбадировки!", "Чел ты гений", "Ты понимаешь что я чепаев", "Бархатные тяги уфффф кэфтэмэ", "Чел ты просто нулёвый", "Стартуем!", "Пон", "Яблоки Не Жруться Что Делать!!!", "ЕБАТЬ ТЫ ВЕРТОЛЕТ Я ТУПО В УНИТАЗЕ ТЫ НЕ ПОНЯЛ ПОМАДА СЛОНА?", "Абоба", "ВСЕМЫРНЫЕ НОВОСТИ СООБЩАЮТ ЧТО ДИНАСТИЯ ПОМИДОР СОБИРАЕТСЯ СРАЗИТЬСЯ С КОРОЛЕВЧТВОМ ПИЛОЧЕК ДЛЯ НОГТЕЙ!! ОПАСНОСТЬ!!! ОПАСНОСТЬ!!! В ОЧКО ПРИРОДЫ ВХОДЯТ 2 АМБАЛА!!!" };
/*     */   }
/*     */ 
/*     */   
/*     */   String chatprefix;
/*     */   
/*     */   public Setting<Integer> delay;
/*     */   
/*     */   private Setting<mode> Mode;
/*     */   
/*     */   private Setting<mode2> Mode2;
/*     */   
/*     */   String[] FriendlyString;
/*     */   String[] Lite;
/*     */   String[] Arsik2005;
/*     */   String[] myString;
/*     */   String[] Erp;
/*     */   String[] Funny;
/*     */   
/*     */   public enum mode
/*     */   {
/*     */     FunnyGame, DirectMessage, OldServer, Local;
/*     */   }
/*     */   
/*     */   public enum mode2
/*     */   {
/*     */     ARSIK2005, Friendly, Lite, Hard, Erp, Funny;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onAttackEntity(AttackEvent event) {
/* 116 */     if (event.getStage() == 1)
/* 117 */       return;  if (event.getEntity() instanceof net.minecraft.entity.player.EntityPlayer && 
/* 118 */       this.timer.passedMs((((Integer)this.delay.getValue()).intValue() * 10))) {
/* 119 */       Entity entity = event.getEntity();
/* 120 */       if (entity == null) {
/*     */         return;
/*     */       }
/*     */       
/* 124 */       int n = 0;
/*     */       
/* 126 */       if (this.Mode2.getValue() == mode2.Hard) {
/* 127 */         n = (int)Math.floor(Math.random() * this.myString.length);
/* 128 */       } else if (this.Mode2.getValue() == mode2.Lite) {
/* 129 */         n = (int)Math.floor(Math.random() * this.Lite.length);
/* 130 */       } else if (this.Mode2.getValue() == mode2.Friendly) {
/* 131 */         n = (int)Math.floor(Math.random() * this.FriendlyString.length);
/* 132 */       } else if (this.Mode2.getValue() == mode2.ARSIK2005) {
/* 133 */         n = (int)Math.floor(Math.random() * this.Arsik2005.length);
/* 134 */       } else if (this.Mode2.getValue() == mode2.Erp) {
/* 135 */         n = (int)Math.floor(Math.random() * this.Erp.length);
/* 136 */       } else if (this.Mode2.getValue() == mode2.Funny) {
/* 137 */         n = (int)Math.floor(Math.random() * this.Funny.length);
/*     */       } 
/*     */       
/* 140 */       if (this.Mode.getValue() == mode.FunnyGame) {
/* 141 */         this.chatprefix = "!";
/*     */       }
/* 143 */       if (this.Mode.getValue() == mode.OldServer) {
/* 144 */         this.chatprefix = ">";
/*     */       }
/* 146 */       if (this.Mode.getValue() == mode.DirectMessage) {
/* 147 */         this.chatprefix = "/w ";
/*     */       }
/*     */       
/* 150 */       if (this.Mode2.getValue() == mode2.Hard) {
/* 151 */         mc.field_71439_g.func_71165_d(this.chatprefix + entity.func_70005_c_() + " " + this.myString[n]);
/* 152 */       } else if (this.Mode2.getValue() == mode2.Lite) {
/* 153 */         mc.field_71439_g.func_71165_d(this.chatprefix + entity.func_70005_c_() + " " + this.Lite[n]);
/* 154 */       } else if (this.Mode2.getValue() == mode2.Friendly) {
/* 155 */         mc.field_71439_g.func_71165_d(this.chatprefix + entity.func_70005_c_() + " " + this.FriendlyString[n]);
/* 156 */       } else if (this.Mode2.getValue() == mode2.ARSIK2005) {
/* 157 */         mc.field_71439_g.func_71165_d(this.chatprefix + entity.func_70005_c_() + " " + this.Arsik2005[n]);
/* 158 */       } else if (this.Mode2.getValue() == mode2.Erp) {
/* 159 */         mc.field_71439_g.func_71165_d(this.chatprefix + entity.func_70005_c_() + " " + this.Erp[n]);
/* 160 */       } else if (this.Mode2.getValue() == mode2.Funny) {
/* 161 */         mc.field_71439_g.func_71165_d(this.chatprefix + entity.func_70005_c_() + " " + this.Funny[n]);
/*     */       } 
/* 163 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\vchteam\Desktop\testpasrt\TH1122LDTEST.jar!\com\mrzak34\thunderhack\modules\misc\EbatteSratte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */