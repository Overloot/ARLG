package com.rommax;

public class ClassSet {
    
    public static final int CLASS_FIGHTER  = 0;
    public static final int CLASS_RANGER  = 1;
    public static final int CLASS_THIEF  = 2;
    
    public static final int MAX_CLASSES = 3;
    
    private static BaseClass[] CLASSSET;
    
    static{
		
		CLASSSET = new BaseClass[MAX_CLASSES];
        CLASSSET[CLASS_FIGHTER] = new BaseClass(CLASS_FIGHTER, "Боец", "fighter", "Это воин посвятивший себя множеству изнуряющих тренировок и прошедший", "через множество поединков, став лучшим из лучших. Может быстро перемещаться по", "полю боя и наносить точные и сильные удары по противнику.", 2, 0, 2, 0, 0, SkillSet.SKILL_FIGHTER_1, SkillSet.SKILL_FIGHTER_2, SkillSet.SKILL_FIGHTER_3);
        CLASSSET[CLASS_RANGER] = new BaseClass(CLASS_RANGER, "Рейнжер", "ranger", "Одиночка с остро заточенными инстинктами самосохранения и способностью выжить", "даже в самой неблагоприятной обстановке. Хорошо обучены использованию многих", "видов оружия и способен участвовать в бою независимо от близости врага.", 1, 3, 0, 0, 0, SkillSet.SKILL_RANGER_1, SkillSet.SKILL_RANGER_2, SkillSet.SKILL_RANGER_3);
        CLASSSET[CLASS_THIEF] = new BaseClass(CLASS_THIEF, "Вор", "thief", "Вор никогда не вступает в честный поединок, говорят что хорошего вора вообще", "сложно увидеть в бою, так как сам он в драки не ввязывается, а если заметит бой", "то подождет его окончания, а потом прикончит выживших и заберет все вещи.", 0, 3, -1, 0, 6, SkillSet.SKILL_THIEF_1, SkillSet.SKILL_THIEF_2, SkillSet.SKILL_THIEF_3);
		
    }
    
    public static BaseClass getClass(int id){
		return CLASSSET[id];
    }
	
    public static int getCurrentClassID = CLASS_FIGHTER;

}
