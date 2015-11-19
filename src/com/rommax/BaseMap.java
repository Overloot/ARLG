package com.rommax;

public class BaseMap extends Entity {
	
	private int genID;
	
	BaseMap(int id, int genID, String name, int level){
		super(id, name, 0, 0, Game.MAP_SIZE_Y, Game.MAP_SIZE_X, null, null, level);
		this.genID = genID;
	}
	
	BaseMap(String name){
		super(0, name, 0, 0, Game.MAP_SIZE_Y, Game.MAP_SIZE_X, null, null, 0);
		this.genID = 0;
	}
	
	public int getGenID(){return genID;}
	
}