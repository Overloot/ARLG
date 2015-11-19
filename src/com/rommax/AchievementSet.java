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
	public static final int TYPE_FIND_MUSH  = 3;	// Нашел N грибов

	private static void add(String name, String descr, String path, int type, int value){
		ACHIEVEMENTSET[count] = new BaseAchievement(count, name, descr, path, type, value);
		count++;
	}

	public static final int MAX_ACHIEVEMENTS = 4;
	
	static{
		ACHIEVEMENTSET = new BaseAchievement[MAX_ACHIEVEMENTS];
		
		add("В глубину!", "Достигнут 3-й уровень подземелья", "res/skills/heal.png", TYPE_FIND_LEVEL, 2);
		add("Охотник I", "Победил 3 врагов", "res/skills/heal.png", TYPE_KILL_ENEMY, 3);
		add("Охотник II", "Победил 4 врагов", "res/skills/heal.png", TYPE_KILL_ENEMY, 4);
		add("Охотник III", "Победил 5 врагов", "res/skills/heal.png", TYPE_KILL_ENEMY, 5);
		add("Грибник I", "Нашел 3 гриба", "res/skills/heal.png", TYPE_FIND_MUSH, 5);
	}

	public static BaseAchievement getAchievement(int id){
		return ACHIEVEMENTSET[id];
	}

}
