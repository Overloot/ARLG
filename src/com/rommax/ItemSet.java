package com.rommax;

public class ItemSet {

	private static BaseItem[] ITEMSET;
	public static int[] ID_ITEMS;

	public static String[] TypeName;
	public static String[] SlotName;

	public static final int SLOT_ANY = -1;
	public static final int SLOT_HEAD = 0;
	public static final int SLOT_BODY = 1;
	public static final int SLOT_LEFT_ARM = 2;
	public static final int SLOT_RIGHT_ARM = 3;
	public static final int SLOT_LEGS = 4;
	public static final int SLOT_BACKPACK = 5;
	public static final int MAX_SLOTS = 6;

	public static final int TYPE_ANY = 0;
	public static final int TYPE_MISC = 1;
	public static final int TYPE_ARMOR = 2;
	public static final int TYPE_MELEE_WEAPON_SWORD = 3;
	public static final int TYPE_POTION = 4;
	public static final int TYPE_SCROLL = 5;
	public static final int TYPE_CONTAINER = 6;
	public static final int TYPE_FOOD = 7;
	public static final int TYPE_RESOURCE = 8;
	public static final int TYPE_MELEE_WEAPON_AXE = 9;
	public static final int MAX_TYPES = 10;

	// Количество предметов
	public static final int MAX_ITEMS = 77;

	private static int count = 0;			// Количество предметов
	private static int crudeSwordItem = 0;	// 
	private static int crudeAxeItem = 0;	// 
	private static int keyItem = 0; 		// ID ключа
	private static int minMushroom = 0; 	// 
	private static int maxMushroom = 0; 	// 
	private static int minPlant = 0; 		// 
	private static int maxPlant = 0; 		// 
	private static int emptyScrollItem = 0; // Пустой свиток
	private static int emptyJarItem = 0; 	// Пустая баночка
	private static int metalMat = 0; 		// Металл
	private static int woodMat = 0; 		// Древесина
	private static int leatherMat = 0; 		// Кожа
	
	private static void add(int chance, int level, int slot, int type, String name, String path, int size, int mass, String script, boolean destroyable, int maxHP, String loot){
		ITEMSET[count] = new BaseItem(chance, count, level, slot, type, name, path, size, mass, script, destroyable, maxHP, loot);
		count++;
	}

	static{
		ITEMSET = new BaseItem[MAX_ITEMS];
		ID_ITEMS = new int[MAX_ITEMS];

		// Доспехи
		add(90,1,SLOT_BODY,TYPE_ARMOR,"Рубаха","res/items/body1.png",25,5,"#RNORMAL 3#", true, 100, "#I_LEATHER|100#");
		add(80,4,SLOT_BODY,TYPE_ARMOR,"Кожанка","res/items/body2.png",30,10,"#RNORMAL 5#", true, 100, "#I_LEATHER|100#");
		add(70,7,SLOT_BODY,TYPE_ARMOR,"Кожаная броня","res/items/body3.png",35,15,"#RNORMAL 7#", true, 100, "#I_LEATHER|100#");
		add(60,10,SLOT_BODY,TYPE_ARMOR,"Железная броня","res/items/body4.png",40,20,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
		add(50,13,SLOT_BODY,TYPE_ARMOR,"Стальной доспех","res/items/body5.png",50,30,"#RNORMAL 11#", true, 100, "#I_METALS|100#");

		// Обувь
		add(90,1,SLOT_LEGS,TYPE_ARMOR,"Кожаные ботинки","res/items/boot1.png",10,5,"#RNORMAL 3#", true, 100, "#I_LEATHER|100#");
		add(80,4,SLOT_LEGS,TYPE_ARMOR,"Укрепленные ботинки","res/items/boot5.png",10,5,"#RNORMAL 5#", true, 100, "#I_LEATHER|100#I_METALS|100#");
		add(70,7,SLOT_LEGS,TYPE_ARMOR,"Железные сапоги","res/items/boot2.png",10,10,"#RNORMAL 7#", true, 100, "#I_METALS|100#");
		add(60,10,SLOT_LEGS,TYPE_ARMOR,"Стальные сапоги","res/items/boot3.png",10,10,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
		add(50,13,SLOT_LEGS,TYPE_ARMOR,"Крепкие стальные сапоги","res/items/boot4.png",15,15,"#RNORMAL 11#", true, 100, "#I_METALS|100#");

		// Гол. уборы
		add(90,1,SLOT_HEAD,TYPE_ARMOR,"Шапка","res/items/helmet5.png",10,10,"#RNORMAL 3#", true, 100, "#I_LEATHER|100#");
		add(80,4,SLOT_HEAD,TYPE_ARMOR,"Кожаный шлем","res/items/helmet2.png",10,10,"#RNORMAL 5#", true, 100, "#I_LEATHER|100#");
		add(70,7,SLOT_HEAD,TYPE_ARMOR,"Железный шлем","res/items/helmet1.png",10,10,"#RNORMAL 7#", true, 100, "#I_METALS|100#");
		add(60,10,SLOT_HEAD,TYPE_ARMOR,"Стальной шлем","res/items/helmet3.png",20,20,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
		add(50,13,SLOT_HEAD,TYPE_ARMOR,"Рыцарский шлем","res/items/helmet4.png",25,25,"#RNORMAL 11#", true, 100, "#I_METALS|100#");

		// Щиты
		add(90,1,SLOT_RIGHT_ARM,TYPE_ARMOR,"Изношенный щит","res/items/shield1.png",20,15,"#RNORMAL 3#", true, 100, "#I_METALS|100#");
		add(80,4,SLOT_RIGHT_ARM,TYPE_ARMOR,"Железный щит","res/items/shield2.png",20,20,"#RNORMAL 5#", true, 100, "#I_METALS|100#");
		add(70,7,SLOT_RIGHT_ARM,TYPE_ARMOR,"Стальной щит","res/items/shield5.png",50,40,"#RNORMAL 7#", true, 100, "#I_METALS|100#");
		add(60,10,SLOT_RIGHT_ARM,TYPE_ARMOR,"Башенный щит","res/items/shield3.png",50,40,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
		add(50,13,SLOT_RIGHT_ARM,TYPE_ARMOR,"Темный щит","res/items/shield4.png",50,50,"#RNORMAL 11#RCOLD 30#RFIRE -30#", true, 100, "#I_METALS|100#");

		// Мечи
		add(90,1,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Изношенный меч","res/items/sword2.png",20,10,"#DNORMAL 3_5#", true, 100, "#I_METALS|100#"); crudeSwordItem = count - 1;
		add(80,4,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Старый меч","res/items/sword1.png",20,15,"#DNORMAL 5_7#", true, 100, "#I_METALS|100#");
		add(70,7,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Длинный меч","res/items/sword4.png",25,20,"#DNORMAL 7_10#", true, 100, "#I_METALS|100#");
		add(60,10,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Зазубренный меч","res/items/sword3.png",25,25,"#DNORMAL 12_15#", true, 100, "#I_METALS|100#");
		add(50,13,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Золотой меч","res/items/sword5.png",30,30,"#DNORMAL 15_20#", true, 100, "#I_METALS|100#");

		// Топоры
		add(90,1,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_AXE,"Изношенный топор","res/items/axe.png",20,10,"#DNORMAL 3_5#", true, 100, "#I_METALS|100#I_WOOD|100#"); crudeAxeItem = count - 1;
		add(80,4,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_AXE,"Изношенный топор","res/items/axe.png",25,15,"#DNORMAL 5_7#", true, 100, "#I_METALS|100#I_WOOD|100#");
		add(70,7,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_AXE,"Изношенный топор","res/items/axe.png",30,20,"#DNORMAL 7_10#", true, 100, "#I_METALS|100#I_WOOD|100#");
		add(60,10,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_AXE,"Изношенный топор","res/items/axe.png",35,25,"#DNORMAL 12_15#", true, 100, "#I_METALS|100#I_WOOD|100#");
		add(50,13,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_AXE,"Изношенный топор","res/items/axe.png",40,30,"#DNORMAL 15_20#", true, 100, "#I_METALS|100#I_WOOD|100#");

		// Ключи
		add(30,1,SLOT_ANY,TYPE_ANY,"Ключ","res/items/key.png",1,1,"", false, 1, ""); keyItem = count - 1;

		// Контейнеры
		add(30,1,SLOT_BACKPACK,TYPE_CONTAINER,"Рюкзак","res/items/backpack1.png",50,20,"#SW_UP 100#", true, 100, "#I_LEATHER|100#");

		// Свитки
		add(30,1,SLOT_ANY,TYPE_SCROLL,"Свиток телепортации","res/items/scroll1.png",2,1,"#TELEPORT#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(35,1,SLOT_ANY,TYPE_SCROLL,"Свиток идентификации","res/items/scroll1.png",2,1,"#IDENTIFY#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(50,1,SLOT_ANY,TYPE_SCROLL,"Свиток последней надежды","res/items/scroll1.png",2,1,"#TELEPORT#PARALYZECOUNT 5#HEALSELF 0=20#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(50,1,SLOT_ANY,TYPE_SCROLL,"Свиток слепоты","res/items/scroll1.png",2,1,"#FOVRAD -100|15#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(40,1,SLOT_ANY,TYPE_SCROLL,"Свиток исцеляющей слепоты","res/items/scroll1.png",2,1,"#FOVRAD -100|15#HEALPOISON 10=50#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(20,1,SLOT_ANY,TYPE_SCROLL,"Свиток лечения","res/items/scroll1.png",2,1,"#HEALSELF 10=50#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(20,1,SLOT_ANY,TYPE_SCROLL,"Свиток маны","res/items/scroll1.png",2,1,"#MANASELF 10=50#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(10,4,SLOT_ANY,TYPE_SCROLL,"Свиток отпирания","res/items/scroll1.png",2,1,"#OPENSKEY#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(10,4,SLOT_ANY,TYPE_SCROLL,"Свиток обнаружения угроз","res/items/scroll1.png",2,1,"#SHOWALLTRAPS#", true, 100, "#I_EMPTY_SCROOL|100#");
		add(15,4,SLOT_ANY,TYPE_SCROLL,"Свиток восстановления","res/items/scroll1.png",2,1,"#HEALSELF 10=50#MANASELF 10=50#", true, 100, "#I_EMPTY_SCROOL|100#");

		// Зелья
		add(30,1,SLOT_ANY,TYPE_POTION,"Зелье лечения","res/items/potion1.png",5,5,"#HEALSELF 10=50#", true, 100, "#I_EMPTY_JAR|100#");
		add(20,4,SLOT_ANY,TYPE_POTION,"Зелье сильного лечения","res/items/potion2.png",5,5,"#HEALSELF 20=100#", true, 100, "#I_EMPTY_JAR|100#");
		add(15,7,SLOT_ANY,TYPE_POTION,"Зелье исцеления","res/items/potion3.png",5,5,"#HEALSELF 30=150#", true, 100, "#I_EMPTY_JAR|100#");
		add(50,1,SLOT_ANY,TYPE_POTION,"Зелье слепоты","res/items/potion4.png",5,5,"#FOVRAD -100|20#", true, 100, "#I_EMPTY_JAR|100#");
		add(40,1,SLOT_ANY,TYPE_POTION,"Зелье искажения","res/items/potion5.png",5,5,"#FOVRAD -100|10#TELEPORT#", true, 100, "#I_EMPTY_JAR|100#");
		add(50,1,SLOT_ANY,TYPE_POTION,"Зелье яда","res/items/potion1.png",5,5,"#POISONCOUNT 10=20#", true, 100, "#I_EMPTY_JAR|100#");
		add(40,4,SLOT_ANY,TYPE_POTION,"Зелье сильного яда","res/items/potion1.png",5,5,"#POISONCOUNT 20=40#", true, 100, "#I_EMPTY_JAR|100#");
		add(30,7,SLOT_ANY,TYPE_POTION,"Зелье яда королевского скорпиона","res/items/potion2.png",5,5,"#POISONCOUNT 40=80#", true, 100, "#I_EMPTY_JAR|100#");
		add(25,1,SLOT_ANY,TYPE_POTION,"Зелье лечения яда","res/items/potion3.png",5,5,"#HEALPOISON 10=50#", true, 100, "#I_EMPTY_JAR|100#");
		add(10,1,SLOT_ANY,TYPE_POTION,"Зелье силы","res/items/potion3.png",5,5,"#STR_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
		add(10,1,SLOT_ANY,TYPE_POTION,"Зелье ловкости","res/items/potion4.png",5,5,"#AGI_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
		add(10,1,SLOT_ANY,TYPE_POTION,"Зелье выносливости","res/items/potion5.png",5,5,"#END_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
		add(10,1,SLOT_ANY,TYPE_POTION,"Зелье интеллекта","res/items/potion4.png",5,5,"#INT_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
		add(10,1,SLOT_ANY,TYPE_POTION,"Зелье удачи","res/items/potion1.png",5,5,"#LUCK_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
		add(10,1,SLOT_ANY,TYPE_POTION,"Зелье сопротивления","res/items/potion1.png",5,5,"#RFIRE 20|10#RCOLD 20|10#RPOISON 20|10#RELEC 20|10#", true, 100, "#I_EMPTY_JAR|100#");
		add(30,1,SLOT_ANY,TYPE_POTION,"Зелье маны","res/items/potion1.png",5,5,"#MANASELF 10=50#", true, 100, "#I_EMPTY_JAR|100#");
		add(20,4,SLOT_ANY,TYPE_POTION,"Зелье большой маны","res/items/potion2.png",5,5,"#MANASELF 20=100#", true, 100, "#I_EMPTY_JAR|100#");
		add(15,7,SLOT_ANY,TYPE_POTION,"Зелье энергии","res/items/potion3.png",5,5,"#MANASELF 30=150#", true, 100, "#I_EMPTY_JAR|100#");
		add(15,4,SLOT_ANY,TYPE_POTION,"Зелье каменной кожи","res/items/potion2.png",5,5,"#RNORMAL 20|15#", true, 100, "#I_EMPTY_JAR|100#");
		add(20,4,SLOT_ANY,TYPE_POTION,"Зелье восстановления","res/items/potion5.png",5,5,"#HEALSELF 10=50#MANASELF 10=50#", true, 100, "#I_EMPTY_JAR|100#");

		// Грибы
		add(25,1,SLOT_ANY,TYPE_FOOD,"Черный гриб","res/items/mushroom1.png",3,2,"#PARALYZECOUNT 5#", false, 100, ""); minMushroom = count - 1;
		add(20,1,SLOT_ANY,TYPE_FOOD,"Меховая шляпка","res/items/mushroom2.png",3,2,"#HEALTIME 1|10#", false, 100, "");
		add(20,1,SLOT_ANY,TYPE_FOOD,"Солнечник","res/items/mushroom3.png",3,2,"#HEALSELF 3=10#", false, 100, "");
		add(30,1,SLOT_ANY,TYPE_FOOD,"Орочий гриб","res/items/mushroom4.png",3,2,"#FOVRAD -100|10#", false, 100, "");
		add(40,1,SLOT_ANY,TYPE_FOOD,"Кровавый гриб","res/items/mushroom5.png",3,2,"#POISONCOUNT 10=15#", false, 100, ""); maxMushroom = count - 1;

		// Растения
		add(20,1,SLOT_ANY,TYPE_FOOD,"Эльфийская крапива","res/items/plant1.png",3,3,"#HEALTIME 1|15#", false, 100, ""); minPlant = count - 1;
		add(20,1,SLOT_ANY,TYPE_FOOD,"Корень папоротника","res/items/plant1.png",3,3,"#HEALSELF 4=10#", false, 100, "");
		add(25,1,SLOT_ANY,TYPE_FOOD,"Болотный лист","res/items/plant1.png",3,3,"#MANATIME 2|15#", false, 100, "");
		add(25,1,SLOT_ANY,TYPE_FOOD,"Золотой цветок","res/items/plant1.png",3,3,"#MANASELF 5=10#", false, 100, "");
		add(15,1,SLOT_ANY,TYPE_FOOD,"Кошачья мята","res/items/plant1.png",3,3,"#HEALTIME 1|10#MANATIME 2|10#", false, 100, ""); maxPlant = count - 1;

		// Ресурсы
		add(0,1,SLOT_ANY, TYPE_RESOURCE,"Пустая баночка","res/items/res_empty_jar.png",5,1,"", false, 100, ""); emptyJarItem = count - 1;
		add(0,1,SLOT_ANY, TYPE_RESOURCE,"Пустой свиток","res/items/scroll1.png",2,1,"", false, 100, ""); emptyScrollItem = count - 1;
		add(0,1,SLOT_ANY, TYPE_RESOURCE,"Металлы","res/items/res_metal.png",5,20,"", false, 100, ""); metalMat = count - 1;
		add(0,1,SLOT_ANY, TYPE_RESOURCE,"Древесина","res/items/res_wood.png",5,20,"", false, 100, ""); woodMat = count - 1;
		add(0,1,SLOT_ANY, TYPE_RESOURCE,"Кожа","res/items/res_leather.png",5,5,"", false, 100, ""); leatherMat = count - 1;
		
		// Количество предметов можно узнать из лога
		new LogWriter().myMessage("ItemSet.java: count: " + count + ", MAX_ITEMS: " + MAX_ITEMS);

		TypeName = new String[MAX_TYPES];
		TypeName[TYPE_ANY] = "Любые предметы";
		TypeName[TYPE_MISC] = "Бесполезные предметы";
		TypeName[TYPE_ARMOR] = "Броня";
		TypeName[TYPE_MELEE_WEAPON_SWORD] = "Режущее оружие";
		TypeName[TYPE_MELEE_WEAPON_AXE] = "Рубящее оружие";
		TypeName[TYPE_POTION] = "Зелья";
		TypeName[TYPE_SCROLL] = "Свитки";
		TypeName[TYPE_CONTAINER] = "Контейнеры";
		TypeName[TYPE_FOOD] = "Еда";
		TypeName[TYPE_RESOURCE] = "Ресурсы";
		
		SlotName = new String[MAX_SLOTS];
		SlotName[SLOT_HEAD] = "Вы можете надеть это #8#на голову.#^#";
		SlotName[SLOT_BODY] = "Вы можете надеть это #8#на тело.#^#";
		SlotName[SLOT_LEFT_ARM] = "Вы можете взять это #8#в левую руку.#^#";
		SlotName[SLOT_RIGHT_ARM] = "Вы можете взять это #8#в правую руку.#^#";
		SlotName[SLOT_LEGS] = "Вы можете надеть это #8#на ноги.#^#";
		SlotName[SLOT_BACKPACK] = "Вы можете использовать это как #8#контейнер для предметов.#^#";
	}

	public static String getNameOfType(int id){return TypeName[id];}
	public static String getSlotName(int id){return SlotName[id];}
	public static int getCrudeSwordID(){return crudeSwordItem;}
	public static int getMinMushroomID(){return minMushroom;}
	public static int getMaxMushroomID(){return maxMushroom;}
	public static int getMinPlantID(){return minPlant;}
	public static int getMaxPlantID(){return maxPlant;}
	public static int getKeyID(){return keyItem;}
	public static int getEmptyJarID(){return emptyJarItem;}
	public static int getEmptyScrollID(){return emptyScrollItem;}
	public static int getMetalMaterialID(){return metalMat;}
	public static int getWoodMaterialID(){return woodMat;}
	public static int getLeatherMaterialID(){return leatherMat;}

	public static BaseItem getItem(int id){
		return ITEMSET[id];
	}
}
