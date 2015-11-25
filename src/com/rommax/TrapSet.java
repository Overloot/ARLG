package com.rommax;

public class TrapSet{
	
	public static final int NONE = -1;
	public static final int TRAP_POISON  = 0;
	
	public static final int MAX_TRAPS = 1;
	
	private static BaseTrap[] TRAPSET;
	
	static{
		TRAPSET = new BaseTrap[MAX_TRAPS];
		
		TRAPSET[TRAP_POISON] = new BaseTrap(TRAP_POISON, "Ядовитое Облако", "#POISONCOUNT 5=25#");
	}
	
	public static BaseTrap getTrap(int id){
		return TRAPSET[id];
	}
}