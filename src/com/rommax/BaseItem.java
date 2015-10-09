package com.rommax;


public class BaseItem{

	private int id;
	private String name;
	private String path;
	private int level;
	private int slot;
	private int size;
	private int mass;
	private int type;
	private int chanse;
	private String script;

	public BaseItem(int chanse, int id, int level, int slot, int type, String name, String path, int size, int mass, String script)
	{
		this.id = id;
		this.level = level;
		this.path = path;
		this.name = name;
		this.size = size;
		this.mass = mass;
		this.slot = slot;
		this.type = type;
		this.script = script;
		this.chanse = chanse;
	}

	public int getID(){return id;};
	public int getLevel(){return level;};
	public int getMass(){return mass;};
	public int getSlot(){return slot;};
	public int getSize(){return size;};
	public String getPath(){return path;};
	public String getName(){return name;};
	public int getType(){return type;};
	public String getScript(){return script;};
	public int getChanse(){return chanse;};


}
