package com.rommax;


public class BaseItem extends GameObject{

	private int slot;
	private int size;
	private int mass;
	private int type;
	private int chance;
	private String script;

	public BaseItem(int chance, int id, int level, int slot, int type, String name, String path, int size, int mass, String script, boolean destroyable, int maxHP, String loot)
	{
		super(id, name, path, level, destroyable, maxHP, loot);
		this.size = size;
		this.mass = mass;
		this.slot = slot;
		this.type = type;
		this.script = script;
		this.chance = chance;
	}

	public void setType(int type){this.type = type;}
	public int getType(){return type;}
	public int getSlot(){return slot;}
	public void setSlot(int slot){this.slot = slot;}
	public int getSize(){return size;}
	public void setSize(int size){this.size = size;}
	public int getMass(){return mass;}
	public void setMass(int mass){this.mass = mass;}
	public void setScript(String sc){script = sc;}
	public String getScript(){return script;}
	public int getChance(){return chance;}
	public void setChance(int chance){this.chance = chance;}

}
