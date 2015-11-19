package com.rommax;

public class AchievementSet {

	public static final int ACHIEVEMENT_FIND_LEVEL  = 0;
	public static final int ACHIEVEMENT_KILL_ENEMY  = 1;
	

	private static BaseAchievement[] ACHIEVEMENTSET;
	
	private static int count = 0;
	
	// Типы достижений
	public static final int TYPE_NONE = 0;			// Нет типа
	public static final int TYPE_FIND_LEVEL = 1;	// Достичь нужного подземелья
	public static final int TYPE_KILL_ENEMY = 2;	// Победить N врагов
	public static final int TYPE_FIND_ITEM  = 3;	// Нашел N предметов
	public static final int TYPE_FIND_PLANT = 4;	// Нашел N грибов и растений

	private static void add(String name, String descr, String path, int type, int value, int level){
		ACHIEVEMENTSET[count] = new BaseAchievement(count, name, descr, path, type, value, level);
		count++;
	}

	public static final int MAX_ACHIEVEMENTS = 3 * 3;
	
	static{
		ACHIEVEMENTSET = new BaseAchievement[MAX_ACHIEVEMENTS];
		
		add("В глубину I!", "Достигнут 2-й уровень подземелья", "res/achievements/achievement1.png", TYPE_FIND_LEVEL, 1, 1);
		add("В глубину II!", "Достигнут 3-й уровень подземелья", "res/achievements/achievement1.png", TYPE_FIND_LEVEL, 2, 2);
		add("В глубину III!", "Достигнут 4-й уровень подземелья", "res/achievements/achievement1.png", TYPE_FIND_LEVEL, 3, 3);

		add("Охотник I", "Победил 3 врагов", "res/achievements/achievement2.png", TYPE_KILL_ENEMY, 3, 1);
		add("Охотник II", "Победил 4 врагов", "res/achievements/achievement2.png", TYPE_KILL_ENEMY, 4, 2);
		add("Охотник III", "Победил 5 врагов", "res/achievements/achievement2.png", TYPE_KILL_ENEMY, 5, 3);

		add("Собиратель I", "Нашел 3 предмета", "res/achievements/achievement3.png", TYPE_FIND_ITEM, 3, 1);
		add("Собиратель II", "Нашел 4 предмета", "res/achievements/achievement3.png", TYPE_FIND_ITEM, 4, 2);
		add("Собиратель III", "Нашел 5 предметов", "res/achievements/achievement3.png", TYPE_FIND_ITEM, 5, 3);
	}

	public static BaseAchievement getAchievement(int id){
		return ACHIEVEMENTSET[id];
	}

}
