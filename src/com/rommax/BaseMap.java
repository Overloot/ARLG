package com.rommax;

public class BaseMap extends Entity {
	
	private int genID;
	private int defaultTile;
	
	BaseMap(int id, int genID, String name, int level, int defaultTile){
		super(id, name, 0, 0, Game.MAP_SIZE_Y, Game.MAP_SIZE_X, null, null, level);
		this.genID = genID;
		this.defaultTile = defaultTile;
	}
	
	BaseMap(String name){
		super(0, name, 0, 0, Game.MAP_SIZE_Y, Game.MAP_SIZE_X, null, null, 0);
		this.genID = 0;
		this.defaultTile = Tileset.TILE_GRASS;
	}
	
	public int getGenID(){return genID;}

	public int getDefaultTile(){return this.defaultTile;}
	
}