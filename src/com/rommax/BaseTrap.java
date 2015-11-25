package com.rommax;

public class BaseTrap extends Entity{

	private String script;

	public BaseTrap(int id, String name, String script){
		super(id, name, "");
		this.script = script;
	}

	public String getScript(){return script;}

}