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
	public static void check(Game game, int type, int value){
		
		boolean flag = false;
		for(int i = 0; i <= AchievementSet.MAX_ACHIEVEMENTS - 1; i++){

			if(unlock[i])continue;
			flag = false;
		
			if (AchievementSet.getAchievement(i).getType() == type){
				switch(type){
					case AchievementSet.TYPE_NONE:
						break;
					case AchievementSet.TYPE_FIND_LEVEL:
						if(AchievementSet.getAchievement(i).getValue() == value){
							flag = true;
							unlock[i] = true;
						}
						break;
					case AchievementSet.TYPE_KILL_ENEMY:
						counter[i] = counter[i] + value;
						if(AchievementSet.getAchievement(i).getValue() <= counter[i]){
							flag = true;
							unlock[i] = true;
						}
						break;
				}
				if (flag)
					game.logMessage("#5#Новое достижение: " + 
						AchievementSet.getAchievement(i).getName().toUpperCase() + "#^#/#");
			}
		}
	}
	
}