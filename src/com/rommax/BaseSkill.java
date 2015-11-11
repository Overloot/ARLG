package com.rommax;

public class BaseSkill extends Entity{

	private String descr;
	private int cooldown;
	private String script;
	private boolean active;

	public BaseSkill(int id, String name, String descr, String path, int cooldown, String script, boolean active){
		super(id, name, path);
		this.descr = descr;
		this.cooldown = cooldown;
		this.script = script;
		this.active = active;
	}
	
	public String getDescr(){return descr;}
	public int getСooldown(){return cooldown;}
	public String getScript(){return script;}
	public boolean getActive(){return active;}
}
