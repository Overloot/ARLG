package com.rommax;

public class AchievementSet {

	public static final int ACHIEVEMENT_FIND_LEVEL  = 0;
	public static final int ACHIEVEMENT_KILL_ENEMY  = 1;
	

	private static BaseAchievement[] ACHIEVEMENTSET;
	
	private static int count = 0;
	
	// Типы достижений
	public static final int TYPE_NONE = 0;				// Нет типа
	public static final int TYPE_FIND_LEVEL 	= 1;	// Достичь нужного подземелья
	public static final int TYPE_KILL_ENEMY 	= 2;	// Победить N врагов
	public static final int TYPE_FIND_ITEM  	= 3;	// Нашел N предметов
	public static final int TYPE_FIND_MUSH	 	= 4;	// Нашел N грибов
	public static final int TYPE_FIND_PLANT 	= 5;	// Нашел N растений
	public static final int TYPE_FIND_SCROLL 	= 6;	// Нашел N свитков
	public static final int TYPE_FIND_POTION 	= 7;	// Нашел N зелий
	public static final int TYPE_DAMAGE_ENEMY 	= 8;	// Нанес N урона врагам
	public static final int TYPE_READ_SCROLL 	= 9;	// Прочитал N свитков
	public static final int TYPE_QUAFF_POTION 	= 10;	// Выпил N зелий

	private static void add(String name, String descr, String path, int type, int value, int level){
		ACHIEVEMENTSET[count] = new BaseAchievement(count, name, descr, path, type, value, level);
		count++;
	}

	public static final int MAX_ACHIEVEMENTS = 8 * 3;
	
	static{
		ACHIEVEMENTSET = new BaseAchievement[MAX_ACHIEVEMENTS];
		
		add("Охотник I", "Победил 3 врагов", "res/achievements/achievement2.png", TYPE_KILL_ENEMY, 3, 1);
		add("Охотник II", "Победил 4 врагов", "res/achievements/achievement2.png", TYPE_KILL_ENEMY, 4, 2);
		add("Охотник III", "Победил 5 врагов", "res/achievements/achievement2.png", TYPE_KILL_ENEMY, 5, 3);

		add("В глубину I!", "Достигнут 2-й уровень подземелья", "res/achievements/achievement1.png", TYPE_FIND_LEVEL, 1, 1);
		add("В глубину II!", "Достигнут 3-й уровень подземелья", "res/achievements/achievement1.png", TYPE_FIND_LEVEL, 2, 2);
		add("В глубину III!", "Достигнут 4-й уровень подземелья", "res/achievements/achievement1.png", TYPE_FIND_LEVEL, 3, 3);

		add("Собиратель I", "Нашел 3 предмета", "res/achievements/achievement3.png", TYPE_FIND_ITEM, 3, 1);
		add("Собиратель II", "Нашел 4 предмета", "res/achievements/achievement3.png", TYPE_FIND_ITEM, 4, 2);
		add("Собиратель III", "Нашел 5 предметов", "res/achievements/achievement3.png", TYPE_FIND_ITEM, 5, 3);

		add("Лезвие I", "Нанес 100 урона", "res/achievements/achievement3.png", TYPE_DAMAGE_ENEMY, 100, 1);
		add("Лезвие II", "Нанес 200 урона", "res/achievements/achievement3.png", TYPE_DAMAGE_ENEMY, 200, 2);
		add("Лезвие III", "Нанес 300 урона", "res/achievements/achievement3.png", TYPE_DAMAGE_ENEMY, 300, 3);

		add("Травник I", "Нашел 3 растения", "res/achievements/achievement3.png", TYPE_FIND_PLANT, 3, 1);
		add("Травник II", "Нашел 4 растения", "res/achievements/achievement3.png", TYPE_FIND_PLANT, 4, 2);
		add("Травник III", "Нашел 5 растений", "res/achievements/achievement3.png", TYPE_FIND_PLANT, 5, 3);

		add("Грибник I", "Нашел 3 гриба", "res/achievements/achievement3.png", TYPE_FIND_MUSH, 3, 1);
		add("Грибник II", "Нашел 4 гриба", "res/achievements/achievement3.png", TYPE_FIND_MUSH, 4, 2);
		add("Грибник III", "Нашел 5 грибов", "res/achievements/achievement3.png", TYPE_FIND_MUSH, 5, 3);

		add("Эрудит I", "Нашел 3 свитка", "res/achievements/achievement3.png", TYPE_FIND_SCROLL, 3, 1);
		add("Эрудит II", "Нашел 4 свитка", "res/achievements/achievement3.png", TYPE_FIND_SCROLL, 4, 2);
		add("Эрудит III", "Нашел 5 свитков", "res/achievements/achievement3.png", TYPE_FIND_SCROLL, 5, 3);

		add("Выпивоха I", "Нашел 3 зелья", "res/achievements/achievement3.png", TYPE_FIND_POTION, 3, 1);
		add("Выпивоха II", "Нашел 4 зелья", "res/achievements/achievement3.png", TYPE_FIND_POTION, 4, 2);
		add("Выпивоха III", "Нашел 5 зелий", "res/achievements/achievement3.png", TYPE_FIND_POTION, 5, 3);
	}

	public static BaseAchievement getAchievement(int id){
		return ACHIEVEMENTSET[id];
	}

}
