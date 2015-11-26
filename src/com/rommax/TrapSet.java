package com.rommax;

public class TrapSet{
	
	public static final int TRAP_POISON  = 0;
	public static final int TRAP_PIT  = 1;
	public static final int TRAP_ICE  = 2;
	public static final int TRAP_FIRE  = 3;
	public static final int TRAP_MAGIC  = 4;
	public static final int TRAP_PHYS  = 5;
	
	public static final int MAX_TRAPS = 6;
	
	private static BaseTrap[] TRAPSET;
	
	static{
		TRAPSET = new BaseTrap[MAX_TRAPS];
		
		TRAPSET[TRAP_POISON] = new BaseTrap(TRAP_POISON, "Яма с ядовитой тиной", "#POISONCOUNT 5=25#");
		TRAPSET[TRAP_PIT] = new BaseTrap(TRAP_PIT, "Волчья яма", "#PARALYZECOUNT " + Game.MAX_PARALYZE_COUNT + "#");
		TRAPSET[TRAP_ICE] = new BaseTrap(TRAP_ICE, "Ледяная Ловушка", "#PARALYZECOUNT " + Game.MAX_PARALYZE_COUNT + "#");
		TRAPSET[TRAP_FIRE] = new BaseTrap(TRAP_FIRE, "Огненная Ловушка", "#PARALYZECOUNT " + Game.MAX_PARALYZE_COUNT + "#");
		TRAPSET[TRAP_MAGIC] = new BaseTrap(TRAP_MAGIC, "Магическая ловушка", "#PARALYZECOUNT " + Game.MAX_PARALYZE_COUNT + "#");
		TRAPSET[TRAP_PHYS] = new BaseTrap(TRAP_PHYS, "Яма с кольями", "#PARALYZECOUNT " + Game.MAX_PARALYZE_COUNT + "#");
	}
	
	public static BaseTrap getTrap(int id){
		return TRAPSET[id];
	}
}