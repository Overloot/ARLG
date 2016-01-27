package com.rommax;

public class MonsterSet {

	// Команды
	public static final int PLAYER_TEAM  = 0;
	public static final int MONSTER_TEAM  = 1;

	// Игрок
	public static final int MONSTER_PLAYER  = 0;

	// Все существа: игрок, враги, NPC...
	public static final int PLAYER = 0;
	public static final int MOUSE = 1;
	public static final int SCORPION = 2;
	public static final int FIREFLY = 3;
	public static final int GOBLIN = 4;
	public static final int SPIDER = 5;
	public static final int SNAKE = 6;
	public static final int WARRIOR_SPIRIT = 7;
	public static final int HELLS_SHELL = 8;
	public static final int THUNDERCLOUD = 9;
	public static final int BEAR = 10;
	public static final int WOLF = 11;

	// Кол-во существ
	public static final int MAX_MONSTERS =12;
	
	private static BaseMonster[] MONSTERSET;

	static{
		MONSTERSET = new BaseMonster[MAX_MONSTERS];

		MONSTERSET[PLAYER] = new BaseMonster(1,PLAYER,"Игрок","",100,100,10,10,10,10,10,5,10,0,0,0,0,0,0,0,0,0,0,0,0,0,10, 5, 0, null, true, null, PLAYER_TEAM);
		MONSTERSET[MOUSE] = new BaseMonster(1,MOUSE,"Мышь","res/monsters/mouse.png",10,10,1,12,1,1,1,1,3,0,0,0,-15,0,0,15,0,0,0,0,2,20,15, 7, 1, "#I_LEATHER|100#", true, null, MONSTER_TEAM);
		MONSTERSET[SCORPION] = new BaseMonster(1,SCORPION,"Скорпион","res/monsters/scorpion.png",20,20,3,8,2,1,1,2,5,10,0,0,10,0,0,10,0,0,10,2,5,40,5, 4, 1, "#I_LEATHER|100#", true, null, MONSTER_TEAM);
		MONSTERSET[FIREFLY] = new BaseMonster(2,FIREFLY,"Светлячок","res/monsters/whisp.png",10,10,1,10,1,1,1,1,2,0,0,0,0,0,0,0,0,2,0,0,0,0,10, 6, 1, "#I_LEATHER|100#", true, null, MONSTER_TEAM);
		MONSTERSET[GOBLIN] = new BaseMonster(2,GOBLIN,"Гоблин","res/monsters/goblin.png",35,10,6,8,6,1,10,3,7,0,0,0,-20,0,0,0,0,0,0,0,2,20,10, 8, 1, "#I_LEATHER|100#I_CRUDE_SWORD|20#", true, null, MONSTER_TEAM);
		MONSTERSET[SPIDER] = new BaseMonster(4,SPIDER,"Паук","res/monsters/spider.png",30,10,3,13,5,1,30,1,5,0,0,0,-30,0,0,0,0,0,-10,2,5,40,10, 6, 1, "#I_LEATHER|100#", true, null, MONSTER_TEAM);
		MONSTERSET[SNAKE] = new BaseMonster(4,SNAKE,"Змея","res/monsters/snake.png",50,10,5,15,5,1,20,2,7,0,0,0,-30,0,0,0,0,0,-10,3,7,50,10, 6, 1, "#I_LEATHER|100#", true, null, MONSTER_TEAM);
		MONSTERSET[WARRIOR_SPIRIT] = new BaseMonster(6,WARRIOR_SPIRIT,"Дух воина","res/monsters/warrior_ghost.png",70,10,8,15,10,1,30,10,20,0,0,0,-50,0,0,100,0,0,100,0,0,100,10, 8, 1, "#I_LEATHER|100#I_CRUDE_SWORD|20#", true, null, MONSTER_TEAM);
		MONSTERSET[HELLS_SHELL] = new BaseMonster(8,HELLS_SHELL,"Адская ракушка","res/monsters/rakovina.png",80,10,6,10,10,1,30,10,20,10,0,0,-30,0,0,-30,0,0,0,5,10,0,10, 8, 1, "#I_LEATHER|100#", true, null, MONSTER_TEAM);
		MONSTERSET[THUNDERCLOUD] = new BaseMonster(9,THUNDERCLOUD,"Грозовая туча","res/monsters/oblako.png",20,10,1,20,1,1,1,0,0,0,0,0,100,0,0,100,0,3,100,0,0,100,10, 8, 1, null, true, null, MONSTER_TEAM);
		MONSTERSET[BEAR] = new BaseMonster(1,BEAR,"Бурый медведь","res/monsters/bear.png",10,10,1,12,1,1,1,1,3,0,0,0,-15,0,0,15,0,0,0,0,2,20,15, 7, 1, "#I_LEATHER|100#", true, null, MONSTER_TEAM);
		MONSTERSET[WOLF] = new BaseMonster(1,WOLF,"Волк","res/monsters/wolf.png",10,10,1,12,1,1,1,1,3,0,0,0,-15,0,0,15,0,0,0,0,2,20,15, 7, 1, "#I_LEATHER|100#", true, null, PLAYER_TEAM);
	}

	public static BaseMonster getMonster(int id){
		return MONSTERSET[id];
	}

}
