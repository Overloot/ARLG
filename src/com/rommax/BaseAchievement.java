package com.rommax;

public class BaseAchievement extends Entity{

	private String descr;
	private int value;
	private int type;
	
	public BaseAchievement(int id, String name, String descr, String path, int type, int value, int level){
		super(id, name, path, level);
		this.descr = descr;
		this.value = value;
		this.type = type;
	}

	public String getDescr(){return descr;}
	public int getValue(){return value;}
	public int getType(){return type;}
	
}
