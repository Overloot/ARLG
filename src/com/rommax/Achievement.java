package com.rommax;

public class Achievement{
	
	private static int count = 0;

	public static int[] counter;
	public static boolean[] unlock;
	
	static {
		counter = new int[AchievementSet.MAX_ACHIEVEMENTS];
		unlock = new boolean[AchievementSet.MAX_ACHIEVEMENTS];

		for(int i = 0; i <= AchievementSet.MAX_ACHIEVEMENTS - 1; i++){
			counter[i] = 0;
			unlock[i] = false;
		}
	}

	public static boolean isLock(int i){
		return !unlock[i];
	}
	
	// Проверка
	public static void check(Game game, int id, int value){
		
		if(unlock[id])return;
		boolean flag = false;
		
		switch(AchievementSet.getAchievement(id).getType()){
			case AchievementSet.TYPE_NONE:
				break;
			case AchievementSet.TYPE_FIND_LEVEL:
				if(AchievementSet.getAchievement(id).getValue() == value){
					flag = true;
					unlock[id] = true;
				}
				break;
			case AchievementSet.TYPE_KILL_ENEMY:
				counter[id] = counter[id] + value;
				if(AchievementSet.getAchievement(id).getValue() <= counter[id]){
					flag = true;
					unlock[id] = true;
				}
				break;
		}
		if(flag){
			game.logMessage("#5#Новое достижение: " + AchievementSet.getAchievement(id).getName().toLowerCase() + "#^#/#");
		}
	}
	
}