package com.rommax;

public class BaseClass extends BaseHero {
    
	private int skill1, skill2, skill3;

	public BaseClass(int id, String name, String file_name, String descr1, 
	String descr2, String descr3, int STR, int AGI, int END, int INT, int LUCK, 
	int skill1, int skill2, int skill3){
		super(id, name, file_name, descr1, descr2, descr3, STR, AGI, END, INT, LUCK);
		this.skill1 = skill1;
		this.skill2 = skill2;
		this.skill3 = skill3;
	}
	
	public int getSkill(int n){
		switch(n){
			case 1:
				return skill1;
			case 2:
				return skill2;
			default:
				return skill3;
		}
	}
    
}
