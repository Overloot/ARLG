package com.rommax;

public class BaseRace{

	private int id;
	private String name;
	private String path;
	private String descr;
	private int STR;
	private int AGI;
	private int END;
	private int LUCK;

	public BaseRace(int id, String name, String path, String descr, int STR, int AGI, int END, int LUCK){
		this.id = id;
		this.name = name;
		this.path = path;
		this.descr = descr;
		this.STR = STR;
		this.AGI = AGI;
		this.END = END;
		this.LUCK = LUCK;
	}
	
	public int getID(){return id;}
	public String getName(){return name;}
	public String getPath(){return path;}
	public String getDescr(){return descr;}
	public int getSTR(){return STR;}
	public int getAGI(){return AGI;}
	public int getEND(){return END;}
	public int getLUCK(){return LUCK;}

}
