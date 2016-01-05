package com.rommax;

public class ItemSet {


	/*>>>*/public static final int MAX_ITEMS = 60;


	private static BaseItem[] ITEMSET;
	public static int []ID_ITEMS;

	public static String[] TypeName;
	public static final int SLOT_ANY = -1;
	public static final int SLOT_HEAD = 0;
	public static final int SLOT_BODY = 1;
	public static final int SLOT_LEFT_ARM = 2;
	public static final int SLOT_RIGHT_ARM = 3;
	public static final int SLOT_LEGS = 4;
	public static final int SLOT_BACKPACK = 5;
	public static String[] SlotName;
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

	// ID экипировки
	public static final int CRUDE_SWORD = 20;
	// ID Грибов
	public static final int MIN_MUSH = 47;
	public static final int MAX_MUSH = 51;

	// ID Растений
	public static final int MIN_PLANT = 52;
	public static final int MAX_PLANT = 52;

	// ID ресурсов
	public static final int EMPTY_JAR = 53; // пустая баночка
	public static final int METALS = 54; // металлы
	public static final int WOOD = 55; // древесина
	public static final int LEATHER = 56; // кожа
	public static final int EMPTY_SCROOL = 57; // пустой свиток

	
	public static String getNameOfType(int id){
		return TypeName[id];
	}

	public static String getSlotName(int id){
			return SlotName[id];
		}


	static{
		ITEMSET = new BaseItem[MAX_ITEMS];
		ID_ITEMS = new int[MAX_ITEMS];

//!!!
ITEMSET[0] = new BaseItem(90,0,1,SLOT_BODY,TYPE_ARMOR,"Рубаха","res/items/body1.png",25,5,"#RNORMAL 3#", true, 100, "#I_LEATHER|100#");
ITEMSET[1] = new BaseItem(80,1,4,SLOT_BODY,TYPE_ARMOR,"Кожанка","res/items/body2.png",30,10,"#RNORMAL 5#", true, 100, "#I_LEATHER|100#");
ITEMSET[2] = new BaseItem(70,2,7,SLOT_BODY,TYPE_ARMOR,"Кожаная броня","res/items/body3.png",35,15,"#RNORMAL 7#", true, 100, "#I_LEATHER|100#");
ITEMSET[3] = new BaseItem(60,3,10,SLOT_BODY,TYPE_ARMOR,"Железная броня","res/items/body4.png",40,20,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
ITEMSET[4] = new BaseItem(50,4,13,SLOT_BODY,TYPE_ARMOR,"Стальной доспех","res/items/body5.png",50,30,"#RNORMAL 11#", true, 100, "#I_METALS|100#");
ITEMSET[5] = new BaseItem(90,5,1,SLOT_LEGS,TYPE_ARMOR,"Кожаные ботинки","res/items/boot1.png",10,5,"#RNORMAL 3#", true, 100, "#I_LEATHER|100#");
ITEMSET[6] = new BaseItem(80,6,4,SLOT_LEGS,TYPE_ARMOR,"Укрепленные ботинки","res/items/boot5.png",10,5,"#RNORMAL 5#", true, 100, "#I_LEATHER|100#I_METALS|100#");
ITEMSET[7] = new BaseItem(70,7,7,SLOT_LEGS,TYPE_ARMOR,"Железные сапоги","res/items/boot2.png",10,10,"#RNORMAL 7#", true, 100, "#I_METALS|100#");
ITEMSET[8] = new BaseItem(60,8,10,SLOT_LEGS,TYPE_ARMOR,"Стальные сапоги","res/items/boot3.png",10,10,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
ITEMSET[9] = new BaseItem(50,9,13,SLOT_LEGS,TYPE_ARMOR,"Крепкие стальные сапоги","res/items/boot4.png",15,15,"#RNORMAL 11#", true, 100, "#I_METALS|100#");
ITEMSET[10] = new BaseItem(90,10,1,SLOT_HEAD,TYPE_ARMOR,"Шапка","res/items/helmet5.png",10,10,"#RNORMAL 3#", true, 100, "#I_LEATHER|100#");
ITEMSET[11] = new BaseItem(80,11,4,SLOT_HEAD,TYPE_ARMOR,"Кожаный шлем","res/items/helmet2.png",10,10,"#RNORMAL 5#", true, 100, "#I_LEATHER|100#");
ITEMSET[12] = new BaseItem(70,12,7,SLOT_HEAD,TYPE_ARMOR,"Железный шлем","res/items/helmet1.png",10,10,"#RNORMAL 7#", true, 100, "#I_METALS|100#");
ITEMSET[13] = new BaseItem(60,13,10,SLOT_HEAD,TYPE_ARMOR,"Стальной шлем","res/items/helmet3.png",20,20,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
ITEMSET[14] = new BaseItem(50,14,13,SLOT_HEAD,TYPE_ARMOR,"Рыцарский шлем","res/items/helmet4.png",25,25,"#RNORMAL 11#", true, 100, "#I_METALS|100#");
ITEMSET[15] = new BaseItem(90,15,1,SLOT_RIGHT_ARM,TYPE_ARMOR,"Изношенный щит","res/items/shield1.png",20,20,"#RNORMAL 3#", true, 100, "#I_METALS|100#");
ITEMSET[16] = new BaseItem(80,16,4,SLOT_RIGHT_ARM,TYPE_ARMOR,"Железный щит","res/items/shield2.png",20,20,"#RNORMAL 5#", true, 100, "#I_METALS|100#");
ITEMSET[17] = new BaseItem(70,17,7,SLOT_RIGHT_ARM,TYPE_ARMOR,"Стальной щит","res/items/shield5.png",50,40,"#RNORMAL 7#", true, 100, "#I_METALS|100#");
ITEMSET[18] = new BaseItem(60,18,10,SLOT_RIGHT_ARM,TYPE_ARMOR,"Башенный щит","res/items/shield3.png",50,40,"#RNORMAL 9#", true, 100, "#I_METALS|100#");
ITEMSET[19] = new BaseItem(35,19,13,SLOT_RIGHT_ARM,TYPE_ARMOR,"Темный щит","res/items/shield4.png",50,50,"#RNORMAL 11#RCOLD 30#RFIRE -30#", true, 100, "#I_METALS|100#");
ITEMSET[CRUDE_SWORD] = new BaseItem(90,CRUDE_SWORD,1,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Изношенный меч","res/items/sword2.png",20,10,"#DNORMAL 3_5#", true, 100, "#I_METALS|100#");
ITEMSET[21] = new BaseItem(80,21,4,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Старый меч","res/items/sword1.png",20,15,"#DNORMAL 5_7#", true, 100, "#I_METALS|100#");
ITEMSET[22] = new BaseItem(70,22,7,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Длинный меч","res/items/sword4.png",25,20,"#DNORMAL 7_10#", true, 100, "#I_METALS|100#");
ITEMSET[23] = new BaseItem(60,23,10,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Зазубренный меч","res/items/sword3.png",25,25,"#DNORMAL 12_15#", true, 100, "#I_METALS|100#");
ITEMSET[24] = new BaseItem(50,24,13,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_SWORD,"Золотой меч","res/items/sword5.png",30,30,"#DNORMAL 15_20#", true, 100, "#I_METALS|100#");
ITEMSET[25] = new BaseItem(30,25,1,SLOT_ANY,TYPE_SCROLL,"Свиток телепортации","res/items/scroll1.png",2,1,"#TELEPORT#", true, 100, "#I_EMPTY_SCROOL|100#");
ITEMSET[26] = new BaseItem(35,26,1,SLOT_ANY,TYPE_SCROLL,"Свиток идентификации","res/items/scroll1.png",2,1,"#IDENTIFY#", true, 100, "#I_EMPTY_SCROOL|100#");
ITEMSET[27] = new BaseItem(50,27,1,SLOT_ANY,TYPE_SCROLL,"Свиток последней надежды","res/items/scroll1.png",2,1,"#TELEPORT#PARALYZECOUNT 5#HEALSELF 0=20#", true, 100, "#I_EMPTY_SCROOL|100#");
ITEMSET[28] = new BaseItem(50,28,1,SLOT_ANY,TYPE_SCROLL,"Свиток слепоты","res/items/scroll1.png",2,1,"#FOVRAD -100|15#", true, 100, "#I_EMPTY_SCROOL|100#");
ITEMSET[29] = new BaseItem(40,29,1,SLOT_ANY,TYPE_SCROLL,"Свиток исцеляющей слепоты","res/items/scroll1.png",2,1,"#FOVRAD -100|15#HEALPOISON 10=50#", true, 100, "#I_EMPTY_SCROOL|100#");
ITEMSET[30] = new BaseItem(20,30,1,SLOT_ANY,TYPE_SCROLL,"Свиток лечения","res/items/scroll1.png",2,1,"#HEALSELF 10=50#", true, 100, "#I_EMPTY_SCROOL|100#");
ITEMSET[31] = new BaseItem(30,31,1,SLOT_ANY,TYPE_POTION,"Зелье лечения","res/items/potion1.png",5,5,"#HEALSELF 10=50#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[32] = new BaseItem(20,32,4,SLOT_ANY,TYPE_POTION,"Зелье сильного лечения","res/items/potion2.png",5,5,"#HEALSELF 20=70#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[33] = new BaseItem(15,33,7,SLOT_ANY,TYPE_POTION,"Зелье исцеления","res/items/potion3.png",5,5,"#HEALSELF 30=110#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[34] = new BaseItem(50,34,1,SLOT_ANY,TYPE_POTION,"Зелье слепоты","res/items/potion4.png",5,5,"#FOVRAD -100|20#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[35] = new BaseItem(40,35,1,SLOT_ANY,TYPE_POTION,"Зелье искажения","res/items/potion5.png",5,5,"#FOVRAD -100|10#TELEPORT#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[36] = new BaseItem(50,36,1,SLOT_ANY,TYPE_POTION,"Зелье яда","res/items/potion1.png",5,5,"#POISONCOUNT 10=20#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[37] = new BaseItem(40,37,4,SLOT_ANY,TYPE_POTION,"Зелье сильного яда","res/items/potion1.png",5,5,"#POISONCOUNT 20=40#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[38] = new BaseItem(30,38,7,SLOT_ANY,TYPE_POTION,"Зелье яда королевского скорпиона","res/items/potion2.png",5,5,"#POISONCOUNT 40=80#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[39] = new BaseItem(25,39,1,SLOT_ANY,TYPE_POTION,"Зелье лечения яда","res/items/potion3.png",5,5,"#HEALPOISON 10=50#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[40] = new BaseItem(10,40,1,SLOT_ANY,TYPE_POTION,"Зелье силы","res/items/potion3.png",5,5,"#STR_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[41] = new BaseItem(10,41,1,SLOT_ANY,TYPE_POTION,"Зелье ловкости","res/items/potion4.png",5,5,"#AGI_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[42] = new BaseItem(10,42,1,SLOT_ANY,TYPE_POTION,"Зелье выносливости","res/items/potion5.png",5,5,"#END_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[43] = new BaseItem(10,43,1,SLOT_ANY,TYPE_POTION,"Зелье удачи","res/items/potion1.png",5,5,"#LUCK_UP 1#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[44] = new BaseItem(10,44,1,SLOT_ANY,TYPE_POTION,"Зелье сопротивления","res/items/potion1.png",5,5,"#RFIRE 20|10#RCOLD 20|10#RPOISON 20|10#RELEC 20|10#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[45] = new BaseItem(15,45,1,SLOT_ANY,TYPE_POTION,"Зелье каменной кожи","res/items/potion2.png",5,5,"#RNORMAL 20|15#", true, 100, "#I_EMPTY_JAR|100#");
ITEMSET[46] = new BaseItem(90,46,1,SLOT_BACKPACK,TYPE_CONTAINER,"Рюкзак","res/items/backpack1.png",50,20,"#SW_UP 100#", true, 100, "#I_LEATHER|100#");
ITEMSET[47] = new BaseItem(20,47,1,SLOT_ANY,TYPE_FOOD,"Черный гриб","res/items/mushroom1.png",3,2,"#PARALYZECOUNT 5#", false, 100, "");
ITEMSET[48] = new BaseItem(20,48,1,SLOT_ANY,TYPE_FOOD,"Меховая шляпка","res/items/mushroom2.png",3,2,"#HEALTIME 1|10#", false, 100, "");
ITEMSET[49] = new BaseItem(20,49,1,SLOT_ANY,TYPE_FOOD,"Солнечник","res/items/mushroom3.png",3,2,"#HEALSELF 5=10#", false, 100, "");
ITEMSET[50] = new BaseItem(20,50,1,SLOT_ANY,TYPE_FOOD,"Орочий гриб","res/items/mushroom4.png",3,2,"#FOVRAD -100|10#", false, 100, "");
ITEMSET[51] = new BaseItem(20,51,1,SLOT_ANY,TYPE_FOOD,"Кровавый гриб","res/items/mushroom5.png",3,2,"#POISONCOUNT 10=15#", false, 100, "");
ITEMSET[52] = new BaseItem(25,52,1,SLOT_ANY,TYPE_FOOD,"Эльфийская крапива","res/items/plant1.png",3,3,"#HEALTIME 1|15#", false, 100, "");
ITEMSET[59] = new BaseItem(90,59,1,SLOT_LEFT_ARM, TYPE_MELEE_WEAPON_AXE,"Изношенный топор","res/items/axe.png",20,10,"#DNORMAL 3_5#", true, 100, "#I_METALS|100#I_WOOD|100#");

// Ресурсы
ITEMSET[EMPTY_JAR] = new BaseItem(0,EMPTY_JAR,1,SLOT_ANY, TYPE_RESOURCE,"Пустая баночка","res/items/res_empty_jar.png",5,1,"", false, 100, "");
ITEMSET[METALS] = new BaseItem(0,METALS,1,SLOT_ANY, TYPE_RESOURCE,"Металлы","res/items/res_metal.png",5,20,"", false, 100, "");
ITEMSET[WOOD] = new BaseItem(0,WOOD,1,SLOT_ANY, TYPE_RESOURCE,"Древесина","res/items/res_wood.png",5,20,"", false, 100, "");
ITEMSET[LEATHER] = new BaseItem(0,LEATHER,1,SLOT_ANY, TYPE_RESOURCE,"Кожа","res/items/res_leather.png",5,5,"", false, 100, "");
ITEMSET[EMPTY_SCROOL] = new BaseItem(0,EMPTY_SCROOL,1,SLOT_ANY, TYPE_RESOURCE,"Пустой свиток","res/items/scroll1.png",2,1,"", false, 100, "");

// 
ITEMSET[58] = new BaseItem(20,58,1,SLOT_ANY,TYPE_ANY,"Ключ","res/items/key.png",1,1,"", false, 1, "");
//!!!

		TypeName = new String[MAX_TYPES];
		TypeName[TYPE_ANY] = "Любые предметы";
		TypeName[TYPE_MISC] = "Бесполезные предметы";
		TypeName[TYPE_ARMOR] = "Броня";
		TypeName[TYPE_MELEE_WEAPON_SWORD] = "Оружие";
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

	public static BaseItem getItem(int id){
		return ITEMSET[id];
	}

}
