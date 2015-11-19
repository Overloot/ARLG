package com.rommax;

public class Achievement{
	
	private static int count = 0;

	public static int[] counter;
	public static int[] unlock;
	private static Game game;
	
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
	
	// Ачивка разблокирована
	private static void done(Game game, int i){
		if (i > 0 && AchievementSet.getAchievement(i - 1).getType() == AchievementSet.getAchievement(i).getType())
			unlock[i - 1] = 2;
		unlock[i] = 1;
		game.logMessage("#5#Новое достижение: " + 
			AchievementSet.getAchievement(i).getName().toUpperCase() + "#^#/#");
	}
	
	// Проверка
	public static void check(Game game, int type, int value){
		
		for(int i = 0; i <= AchievementSet.MAX_ACHIEVEMENTS - 1; i++){

			if (unlock[i] > 0) continue;
		
			if (AchievementSet.getAchievement(i).getType() == type){
				switch(type){
					// По значению
					case AchievementSet.TYPE_FIND_LEVEL:
						if(AchievementSet.getAchievement(i).getValue() == value){
							done(game, i);
							continue;
						}
						break;
					// По счетчику
					case AchievementSet.TYPE_KILL_ENEMY:
					case AchievementSet.TYPE_FIND_ITEM:
						counter[i] = counter[i] + value;
						if(AchievementSet.getAchievement(i).getValue() <= counter[i]){
							done(game, i);
							continue;
						}
						break;
				}
			}
		}
	}
	
}