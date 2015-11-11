package com.rommax;


public class BaseItem extends Entity{

	private int slot;
	private int size;
	private int mass;
	private int type;
	private int chanse;
	private String script;

	public BaseItem(int chanse, int id, int level, int slot, int type, String name, String path, int size, int mass, String script)
	{
		super(id, name, path, level);
		this.size = size;
		this.mass = mass;
		this.slot = slot;
		this.type = type;
		this.script = script;
		this.chanse = chanse;
	}

	public int getMass(){return mass;};
	public int getSlot(){return slot;};
	public int getSize(){return size;};
	public int getType(){return type;};
	public String getScript(){return script;};
	public int getChanse(){return chanse;};
}
