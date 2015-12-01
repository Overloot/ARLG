package com.rommax;

public class BaseRace extends BaseHero{

	private int skill; // Расовый скилл

	public BaseRace(int id, String name, String folder_name, String descr1, 
	String descr2, String descr3, int STR, int AGI, int END, int LUCK, 
	int skill){
		super(id, name, folder_name, descr1, descr2, descr3, STR, AGI, END, LUCK);
		this.skill = skill;
	}
	
	public int getSkill(){return skill;}

}
