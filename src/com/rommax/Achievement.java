package com.rommax;

public class Achievement{
	
	private static int count = 0;

	public static int[] counter;
	public static int[] unlock;
	
	static {
		counter = new int[AchievementSet.MAX_ACHIEVEMENTS];
		unlock = new int[AchievementSet.MAX_ACHIEVEMENTS];

		for(int i = 0; i <= AchievementSet.MAX_ACHIEVEMENTS - 1; i++){
			counter[i] = 0;
			unlock[i] = 0;
		}
	}

	public static boolean isLock(int i){
		return unlock[i] == 0 || unlock[i] == 2;
	}
	
	// Проверка
	public static void check(Game game, int type, int value){
		
		boolean flag = false;
		for(int i = 0; i <= AchievementSet.MAX_ACHIEVEMENTS - 1; i++){

			if (unlock[i] > 0) continue;
			flag = false;
		
			if (AchievementSet.getAchievement(i).getType() == type){
				switch(type){
					case AchievementSet.TYPE_FIND_LEVEL:
						if(AchievementSet.getAchievement(i).getValue() == value){
							flag = true;
							unlock[i] = 1;
						}
						break;
					case AchievementSet.TYPE_KILL_ENEMY:
					case AchievementSet.TYPE_FIND_ITEM:
						counter[i] = counter[i] + value;
						if(AchievementSet.getAchievement(i).getValue() <= counter[i]){
							if (i > 0 && AchievementSet.getAchievement(i - 1).getType() == AchievementSet.getAchievement(i).getType())
								unlock[i - 1] = 2;
							flag = true;
							unlock[i] = 1;
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