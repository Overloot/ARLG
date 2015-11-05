package com.rommax;

public class BaseSkill{

	private int id;
	private String name;
	private String path;
	private int cooldown;
	private String script;

	public BaseSkill(int id, String name, String path, int cooldown, String script){
		this.id = id;
		this.name = name;
		this.path = path;
		this.cooldown = cooldown;
		this.script = script;
	}
	
	public int getID(){return id;}
	public String getName(){return name;}
	public String getPath(){return path;}
	public int getСooldown(){return cooldown;}
	public String getScript(){return script;}

}
