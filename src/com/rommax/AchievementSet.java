package com.rommax;

public class AchievementSet {

	public static final int ACHIEVEMENT_FIND_LEVEL  = 0;
	public static final int ACHIEVEMENT_KILL_ENEMY  = 1;
	
	public static final int MAX_ACHIEVEMENTS = 2;

	private static BaseAchievement[] ACHIEVEMENTSET;
	
	private static int count = 0;
	
	// Типы достижений
	public static final int TYPE_NONE = 0;			// Нет типа
	public static final int TYPE_FIND_LEVEL = 1;	// Достичь нужного подземелья
	public static final int TYPE_KILL_ENEMY = 2;	// Победить N врагов

	private static void add(String name, String descr, String path, int type, int value){
		ACHIEVEMENTSET[count] = new BaseAchievement(count, name, descr, path, type, value);
		count++;
	}

	static{
		ACHIEVEMENTSET = new BaseAchievement[MAX_ACHIEVEMENTS];
		
		add("В глубину!", "Достигнут 3-й уровень подземелья", "res/skills/heal.png", TYPE_FIND_LEVEL, 2);
		add("Охотник", "Победить 5 врагов", "res/skills/heal.png", TYPE_KILL_ENEMY, 3);
	}

	public static BaseAchievement getAchievement(int id){
		return ACHIEVEMENTSET[id];
	}

}
