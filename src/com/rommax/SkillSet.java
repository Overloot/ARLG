package com.rommax;

public class SkillSet{

	public static final int SKILL_HEAL  = 0;
	public static final int SKILL_INFRA  = 1;
	public static final int SKILL_IDENTIFY = 2;
	public static final int SKILL_RFIRE = 3;
	public static final int SKILL_RCOLD = 4;
	public static final int SKILL_RELEC = 5;
	public static final int SKILL_RPOISON = 6;
	public static final int SKILL_RNORMAL = 7;
	
	public static final int MAX_SKILLS = 8;

	private static BaseSkill[] SKILLSET;

	static{
		SKILLSET = new BaseSkill[MAX_SKILLS];
		SKILLSET[SKILL_HEAL] = new BaseSkill(SKILL_HEAL, "Благословление Силанны", "res/skills/heal.png", 35, "#HEALTIME 2|10#");
		SKILLSET[SKILL_INFRA] = new BaseSkill(SKILL_INFRA, "Инфразрение", "res/skills/infra.png", 75, "#FOVRAD 10|15#");
		SKILLSET[SKILL_IDENTIFY] = new BaseSkill(SKILL_IDENTIFY, "Идентификация", "res/skills/identify.png", 100, "#IDENTIFY#");
		SKILLSET[SKILL_RFIRE] = new BaseSkill(SKILL_RFIRE, "Щит Огня", "res/skills/resfire.png", 60, "#RNORMAL 25|15#");
		SKILLSET[SKILL_RCOLD] = new BaseSkill(SKILL_RCOLD, "Северный Щит", "res/skills/rescold.png", 60, "#RCOLD 25|15#");
		SKILLSET[SKILL_RELEC] = new BaseSkill(SKILL_RELEC, "Купол Молний", "res/skills/reselec.png", 60, "#RELEC 25|15#");
		SKILLSET[SKILL_RPOISON] = new BaseSkill(SKILL_RPOISON, "Сопротивление Яду", "res/skills/respoison.png", 60, "#RPOISON 25|15#");
		SKILLSET[SKILL_RNORMAL] = new BaseSkill(SKILL_RNORMAL, "Сопротивление", "res/skills/resnormal.png", 60, "#RNORMAL 25|15#");
	}

	public static BaseSkill getSkill(int id){
		return SKILLSET[id];
	}
	
}