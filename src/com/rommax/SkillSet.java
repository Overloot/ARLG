package com.rommax;

public class SkillSet{

	public static final int SKILL_NONE  = -1;

	// Расовые навыки
	public static final int SKILL_HEAL  = 0;
	public static final int SKILL_INFRA  = 1;
	public static final int SKILL_IDENTIFY = 2;
	public static final int SKILL_RFIRE = 3;
	public static final int SKILL_RCOLD = 4;
	public static final int SKILL_RELEC = 5;
	public static final int SKILL_RPOISON = 6;
	public static final int SKILL_RNORMAL = 7;
	
	// Навыки вора
	public static final int SKILL_THIEF_1 = 8;
	public static final int SKILL_THIEF_2 = 9;
	public static final int SKILL_THIEF_3 = 10;

	// Навыки ассасина
	public static final int SKILL_ASSASSIN_1 = 11;
	public static final int SKILL_ASSASSIN_2 = 12;
	public static final int SKILL_ASSASSIN_3 = 13;
	
	// Навыки воина
	public static final int SKILL_FIGHTER_1 = 14;
	public static final int SKILL_FIGHTER_2 = 15;
	public static final int SKILL_FIGHTER_3 = 16;
	
	// Навыки паладина
	public static final int SKILL_PALADIN_1 = 17;
	public static final int SKILL_PALADIN_2 = 18;
	public static final int SKILL_PALADIN_3 = 19;
	
	// Навыки рейнжера
	public static final int SKILL_RANGER_1 = 20;
	public static final int SKILL_RANGER_2 = 21;
	public static final int SKILL_RANGER_3 = 22;
	
	public static final int MAX_SKILLS = 23;

	private static BaseSkill[] SKILLSET;
	
	static {
		SKILLSET = new BaseSkill[MAX_SKILLS];
		// Расовые навыки
		SKILLSET[SKILL_HEAL] = new BaseSkill(SKILL_HEAL, "Благословление Силанны", "+++", "res/skills/heal.png", 35, "#HEALTIME 2|10#", true);
		SKILLSET[SKILL_INFRA] = new BaseSkill(SKILL_INFRA, "Инфразрение", "+++", "res/skills/infra.png", 75, "#FOVRAD 10|15#", true);
		SKILLSET[SKILL_IDENTIFY] = new BaseSkill(SKILL_IDENTIFY, "Идентификация", "+++", "res/skills/identify.png", 100, "#IDENTIFY#", true);
		SKILLSET[SKILL_RFIRE] = new BaseSkill(SKILL_RFIRE, "Щит Огня", "+++", "res/skills/resfire.png", 60, "#RNORMAL 25|15#", true);
		SKILLSET[SKILL_RCOLD] = new BaseSkill(SKILL_RCOLD, "Северный Щит", "+++", "res/skills/rescold.png", 60, "#RCOLD 25|15#", true);
		SKILLSET[SKILL_RELEC] = new BaseSkill(SKILL_RELEC, "Купол Молний", "+++", "res/skills/reselec.png", 60, "#RELEC 25|15#", true);
		SKILLSET[SKILL_RPOISON] = new BaseSkill(SKILL_RPOISON, "Сопротивление Яду", "+++", "res/skills/respoison.png", 60, "#RPOISON 25|15#", true);
		SKILLSET[SKILL_RNORMAL] = new BaseSkill(SKILL_RNORMAL, "Сопротивление", "+++", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		// Навыки вора
		SKILLSET[SKILL_THIEF_1] = new BaseSkill(SKILL_THIEF_1, "Свидание с Тенью", "Вор скрывается в тень", "res/skills/resnormal.png", 120, "#SHADOWCOUNT 45#", true);
		SKILLSET[SKILL_THIEF_2] = new BaseSkill(SKILL_THIEF_2, "Взломщик", "Мастеру отмычек ключи не нужны", "res/skills/resnormal.png", 25, "#OPENSKEY#", true);
		SKILLSET[SKILL_THIEF_3] = new BaseSkill(SKILL_THIEF_3, "Обнаружение Угроз", "Вор видит скрытые ловушки", "res/skills/resnormal.png", 75, "#SHOWALLTRAPS#", true);
		// Навыки ассасина
		SKILLSET[SKILL_ASSASSIN_1] = new BaseSkill(SKILL_ASSASSIN_1, "Удар сзади", "Двойной удар в спину", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		SKILLSET[SKILL_ASSASSIN_2] = new BaseSkill(SKILL_ASSASSIN_2, "Ядовитое Облако", "Все цели вокруг убийцы отравлены", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		SKILLSET[SKILL_ASSASSIN_3] = new BaseSkill(SKILL_ASSASSIN_3, "Огненная Бомба", "Устанавка огненной ловушки", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		// Навыки воина
		SKILLSET[SKILL_FIGHTER_1] = new BaseSkill(SKILL_FIGHTER_1, "Неистовство", "Враги не успевают отвечать на удары", "res/skills/resnormal.png", 60, "#WARHIT 25#", true);
		SKILLSET[SKILL_FIGHTER_2] = new BaseSkill(SKILL_FIGHTER_2, "Грация Ягуара", "Воин становится очень ловким", "res/skills/resnormal.png", 60, "#BESTAGI 15#", true);
		SKILLSET[SKILL_FIGHTER_3] = new BaseSkill(SKILL_FIGHTER_3, "", "", "res/skills/resnormal.png", 60, "# 5#", true);
		// Навыки паладина
		SKILLSET[SKILL_PALADIN_1] = new BaseSkill(SKILL_PALADIN_1, "Святой Луч", "Аура света отражает удары", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		SKILLSET[SKILL_PALADIN_2] = new BaseSkill(SKILL_PALADIN_2, "Святой Луч", "Аура света отражает удары", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		SKILLSET[SKILL_PALADIN_3] = new BaseSkill(SKILL_PALADIN_3, "Святой Луч", "Аура света отражает удары", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		// Навыки рейнжера
		SKILLSET[SKILL_RANGER_1] = new BaseSkill(SKILL_RANGER_1, "Святой Луч", "Аура света отражает удары", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		SKILLSET[SKILL_RANGER_2] = new BaseSkill(SKILL_RANGER_2, "Святой Луч", "Аура света отражает удары", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
		SKILLSET[SKILL_RANGER_3] = new BaseSkill(SKILL_RANGER_3, "Святой Луч", "Аура света отражает удары", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#", true);
	}

	public static BaseSkill getSkill(int id){
		return SKILLSET[id];
	}
	
}