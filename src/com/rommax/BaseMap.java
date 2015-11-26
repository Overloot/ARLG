package com.rommax;

public class BaseMap extends Entity {
	
	private int genID;
	private int defaultTile;
	private String fauna;
	
	BaseMap(int id, int genID, String name, int level, int defaultTile, String fauna){
		super(id, name, 0, 0, Game.MAP_SIZE_Y, Game.MAP_SIZE_X, null, null, level);
		this.genID = genID;
		this.defaultTile = defaultTile;
		this.fauna = fauna;
	}

	BaseMap(String name){
		super(0, name, 0, 0, Game.MAP_SIZE_Y, Game.MAP_SIZE_X, null, null, 0);
		this.genID = 0;
	}
	
	public int getGenID(){return genID;}

	public int getDefaultTile(){return this.defaultTile;}

	public void setDefaultTile(int defaultTile) { this.defaultTile = defaultTile; }

	public String getFauna() { return this.fauna;}

	public void setFauna(String fauna) { this.fauna = fauna; }
	
}