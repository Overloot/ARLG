package com.rommax;

public class ClassSet {
    
    public static final int CLASS_FIGHTER  = 0;
    public static final int CLASS_RANGER  = 1;
    public static final int CLASS_THIEF  = 2;
    
    public static final int MAX_CLASSES = 3;
    
    private static BaseClass[] CLASSSET;
    
    static{
		
		CLASSSET = new BaseClass[MAX_CLASSES];
        CLASSSET[CLASS_FIGHTER] = new BaseClass(CLASS_FIGHTER, "Боец", "fighter");
        CLASSSET[CLASS_RANGER] = new BaseClass(CLASS_RANGER, "Рейнжер", "ranger");
        CLASSSET[CLASS_THIEF] = new BaseClass(CLASS_THIEF, "Вор", "thief");
		
    }
    
    public static BaseClass getClass(int id){
		return CLASSSET[id];
    }
	
    public static int getCurrentClassID = CLASS_FIGHTER;

}
