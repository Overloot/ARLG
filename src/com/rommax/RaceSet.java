package com.rommax;

public class RaceSet{

	public static final int RACE_HUMAN  = 0;
	public static final int RACE_GNOME  = 1;
	public static final int RACE_ELF  = 2;
	
	public static final int MAX_RACES = 3;

	private static BaseRace[] RACESET;


	static{
		RACESET = new BaseRace[MAX_RACES];
		RACESET[RACE_HUMAN] = new BaseRace(RACE_HUMAN, "Человек", "res/monsters/races/human.png", "111", 10, 10, 10, 10);
		RACESET[RACE_GNOME] = new BaseRace(RACE_GNOME, "Гном", "res/monsters/races/gnome.png", "222", 12, 8, 10, 10);
		RACESET[RACE_ELF] 	= new BaseRace(RACE_ELF, "Эльф", "res/monsters/races/elf.png", "333", 8, 12, 20, 10);
	}

	public static BaseRace getRace(int id){
		return RACESET[id];
	}
	
	public static int getCurrentRaceID = RACE_HUMAN;

}