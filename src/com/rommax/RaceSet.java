package com.rommax;

public class RaceSet{

	public static final int RACE_HUMAN  = 0;
	public static final int RACE_HALFLING  = 1;
	public static final int RACE_GNOME  = 2;
	public static final int RACE_ELF  = 3;
	public static final int RACE_DROW  = 4;
	public static final int RACE_ORC  = 5;
	
	public static final int MAX_RACES = 6;

	private static BaseRace[] RACESET;

	/*
	Базовый показатель всех статов равен 10.
	Сумма всех статов 40.
	Забирая или добавляя балл у стата нужно учитывать его стоимость.
	Стоимость 1 балла силы, ловкости, выносливости равна 1.
	Стоимость 1 балла удачи равна 3.
	*/

	static{
		RACESET = new BaseRace[MAX_RACES];
		RACESET[RACE_HUMAN] 	= new BaseRace(RACE_HUMAN, "Человек", "res/monsters/races/human.png", "Люди - самая молодая раса. Они легко ко всему приспосабливаются. Благодаря своим", "способностям, часто правят империями, которыми другим расам управлять сложно.", "Живут мало, но у них разнообразные религии, вкусы, мораль и привычки.", 10, 10, 10, 10, SkillSet.SKILL_RELEC);
		RACESET[RACE_HALFLING] 	= new BaseRace(RACE_HALFLING, "Хафлинг", "res/monsters/races/halfling.png", "Низкорослая раса восхитительных воров и плутов. Хафлинги крепки и трудолюбивы,", "в основном тихие и мирные. В целом они предпочитают домашний уют опасностям", " приключений. Обитают во всех известных городах и селениях мира.", 8, 12, 8, 16, SkillSet.SKILL_IDENTIFY);
		RACESET[RACE_GNOME] 	= new BaseRace(RACE_GNOME, "Гном", "res/monsters/races/gnome.png", "Гномы селятся в горах, разрабатывают там месторождения полезных ископаемых и", "строят целые подземные города. Невысоки, приземисты, крепко сложены. Гномы", " честны и прямолинейны, что часто приводит их к конфликтам с другими расами.", 12, 8, 10, 10, SkillSet.SKILL_RCOLD);
		RACESET[RACE_ELF] 		= new BaseRace(RACE_ELF, "Эльф", "res/monsters/races/elf.png", "Самая древняя и самая таинственная раса. Эльфы оставили неизгладимый след в", "истории мира, большинство героев разных эпох из их числа. Быстры, ловки, а об", "эльфийской магии, которой в совершенстве владеет каждый эльф, ходят легенды.", 8, 12, 10, 10, SkillSet.SKILL_HEAL);
		RACESET[RACE_DROW] 		= new BaseRace(RACE_DROW, "Дроу", "res/monsters/races/drow.png", "Ввысокомерная темнокожая раса эльфов, обитающая в глубоких подземных городах.", "Этот народ печально известен своей жестокостью, вероломством и особенной магией.", "Дроу являются язычниками, большинство из них исповедует культ богини крови.", 9, 11, 9, 13, SkillSet.SKILL_INFRA);
		RACESET[RACE_ORC] 		= new BaseRace(RACE_ORC, "Орк", "res/monsters/races/orc.png", "Орки живут дикими варварскими племенами, скитаясь по равнинам да промышляя", "охотой на случайных путников, в надежде на поживу. Природа наградила их крепким", "телосложением и большой физической силой, что помогает им выживать.", 12, 8, 12, 4, SkillSet.SKILL_RFIRE);
	}

	public static BaseRace getRace(int id){
		return RACESET[id];
	}
	
	public static int getCurrentRaceID = RACE_HUMAN;

}