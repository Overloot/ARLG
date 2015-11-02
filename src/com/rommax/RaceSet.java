package com.rommax;

public class RaceSet{

	public static final int RACE_HUMAN  = 0;
	public static final int RACE_GNOME  = 1;
	public static final int RACE_ELF  = 2;
	
	public static final int MAX_RACES = 3;

	private static BaseRace[] RACESET;


	static{
		RACESET = new BaseRace[MAX_RACES];
		RACESET[RACE_HUMAN] = new BaseRace(RACE_HUMAN, "Человек", "res/monsters/races/human.png", "Люди - самая молодая раса. Они легко ко всему приспосабливаются. Благодаря своим", "способностям, часто правят империями, которыми другим расам управлять сложно.", "Живут мало, но у них разнообразные религии, вкусы, мораль и привычки.", 10, 10, 10, 10);
	RACESET[RACE_GNOME] = new BaseRace(RACE_GNOME, "Гном", "res/monsters/races/gnome.png", "Гномы селятся в горах, разрабатывают там месторождения полезных ископаемых и", "строят целые подземные города. Невысоки, приземисты, крепко сложены. Гномы", " честны и прямолинейны, что часто приводит их к конфликтам с другими расами.", 12, 8, 10, 10);
		RACESET[RACE_ELF] 	= new BaseRace(RACE_ELF, "Эльф", "res/monsters/races/elf.png", "Самая древняя и самая таинственная раса. Эльфы оставили неизгладимый след в", "истории мира, большинство героев разных эпох из их числа. Быстры, ловки, а об", "эльфийской магии, которой в совершенстве владеет каждый эльф, ходят легенды.", 8, 12, 10, 10);
	}

	public static BaseRace getRace(int id){
		return RACESET[id];
	}
	
	public static int getCurrentRaceID = RACE_HUMAN;

}