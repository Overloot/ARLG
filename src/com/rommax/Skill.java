package com.rommax;

public class Skill{
	
	// Макс. кол-во скиллов у игрока
	private static final int MAX_SKILLS = 4;
	
	private static int count = 0;

	public static int[] skill;
	private static int[] cooldown;

	static {
		skill = new int[MAX_SKILLS];
		cooldown = new int[MAX_SKILLS];
		for(int i = 0; i <= MAX_SKILLS - 1; i++){
			skill[i] = -1;
			cooldown[i] = 0;
		}
	}
	
	// Добавляем игроку новый скилл
	public static void add(int id){
		if (id < 0) return;
		if (count >= MAX_SKILLS) return;
		skill[count] = id;
		cooldown[count] = 0;
		count++;
	}

	// Ход игрока, у всех кулдаунов отнимаем 1
	public static void update(){
		for(int i = 0; i < count; i++){
			if (cooldown[i] > 0)
				cooldown[i]--;
		}		
	}
	
	// Есть ли такой скилл у игрока
	public static boolean isSkill(int id){
		for(int i = 0; i < count; i++)
			if (skill[i] == i) return true;
		return false;
	}
	
	// Поз. скилла в списке скиллов у игрока
	public static int posSkill(int id){
		for(int i = 0; i < count; i++)
			if (skill[i] == i) return i;
		return -1;
	}
	
	// Кулдаун у скилла игрока под номером n
	public static int getCooldown(int n){
		int ret = cooldown[n];
		if (ret < 0) ret = 0;
		return ret;
	}
	
	public static void setCooldown(int n, int value){
		cooldown[n] = value;
	}
	
	public static int getAmount(){return count;}
	
}