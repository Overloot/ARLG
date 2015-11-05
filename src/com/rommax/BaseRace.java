package com.rommax;

public class BaseRace{

	private int id;
	private String name;
	private String path;
	private String descr1, descr2, descr3;
	private int STR;
	private int AGI;
	private int END;
	private int LUCK;
	private int skill; // Расовый скилл

	public BaseRace(int id, String name, String path, String descr1, 
	String descr2, String descr3, int STR, int AGI, int END, int LUCK, 
	int skill){
		this.id = id;
		this.name = name;
		this.path = path;
		this.descr1 = descr1;
		this.descr2 = descr2;
		this.descr3 = descr3;
		this.STR = STR;
		this.AGI = AGI;
		this.END = END;
		this.LUCK = LUCK;
		this.skill = skill;
	}
	
	public int getID(){return id;}
	public String getName(){return name;}
	public String getPath(){return path;}
	public String getDescr1(){return descr1;}
	public String getDescr2(){return descr2;}
	public String getDescr3(){return descr3;}
	public int getSTR(){return STR;}
	public int getAGI(){return AGI;}
	public int getEND(){return END;}
	public int getLUCK(){return LUCK;}
	public int getSkill(){return skill;}

}
