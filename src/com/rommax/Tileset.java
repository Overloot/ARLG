package com.rommax;

public class Tileset{

	public static final int TILE_EMPTY  = 0;
	public static final int TILE_GRASS = 1;
	public static final int TILE_TREE   = 2;
	public static final int TILE_CLOSED_DOOR   = 3;
	public static final int TILE_OPENED_DOOR = 4;
	public static final int TILE_STAIR_UP = 5;
	public static final int TILE_STAIR_DOWN = 6;
	public static final int TILE_DESTROYED_TOWER_WALL = 7;
	public static final int TILE_TOWER_WALL = 8;
	public static final int TILE_TOWER_FLOOR = 9;
	public static final int TILE_WATER = 10;
	public static final int TILE_DUNGEON_FLOOR = 11;
	public static final int TILE_DUNGEON_WALL = 12;
	public static final int TILE_SWAMP = 13;
	public static final int TILE_FARM = 14;
	public static final int TILE_BRIDGE_HOR = 15;
	public static final int TILE_BRIDGE_VER = 16;
	public static final int TILE_OLD_CASTLE_WALL = 17;
	public static final int TILE_OLD_CASTLE_FLOOR = 18;
	public static final int TILE_MARSH = 19;
	public static final int TILE_DIRT = 20;
	public static final int TILE_MARSH_TREE = 21;
	public static final int TILE_MARSH_TREE2 = 22;
	public static final int TILE_MARSH_GRASS = 23;
	public static final int TILE_MARSH_GRASS2 = 24;
	public static final int TILE_MARSH_GRASS3 = 25;
	public static final int TILE_MARSH_GRASS4 = 26;

	public static final int MAX_TILES = 27;

    public static final int TILE_SIZE = 32;

	private static BaseTile[] TILESET;

	static{
		TILESET = new BaseTile[MAX_TILES];
		TILESET[TILE_EMPTY] = new BaseTile(TILE_EMPTY, "Пустой тайл", "res/dungeons/empty.png", false, false, false);
		TILESET[TILE_GRASS] = new BaseTile(TILE_GRASS, "Трава", "res/dungeons/grass.png", true, true, false);
		TILESET[TILE_TREE] = new BaseTile(TILE_TREE, "Дерево", "res/dungeons/tree.png", false, false, false);
		TILESET[TILE_CLOSED_DOOR] = new BaseTile(TILE_CLOSED_DOOR, "Закрытая дверь", "res/dungeons/closed_door.png", false, false, true);
		TILESET[TILE_OPENED_DOOR] = new BaseTile(TILE_OPENED_DOOR, "Открытая дверь", "res/dungeons/opened_door.png", true, true, true);
		TILESET[TILE_STAIR_UP] = new BaseTile(TILE_STAIR_UP, "Лестница вверх", "res/dungeons/stair_up.png", true, true, false);
		TILESET[TILE_STAIR_DOWN] = new BaseTile(TILE_STAIR_DOWN, "Лестница вниз", "res/dungeons/stair_down.png", true, true, false);
		TILESET[TILE_DESTROYED_TOWER_WALL] = new BaseTile(TILE_DESTROYED_TOWER_WALL, "Разрушенная стена башни", "res/dungeons/destroyed_tower_wall.png", false, false, false);
		TILESET[TILE_TOWER_WALL] = new BaseTile(TILE_TOWER_WALL,"Стена башни", "res/dungeons/tower_wall.png", false, false, false);
		TILESET[TILE_WATER] = new BaseTile(TILE_WATER, "Вода", "res/dungeons/water.png", true, true, false);
		TILESET[TILE_TOWER_FLOOR] = new BaseTile(TILE_TOWER_FLOOR, "Пол башни", "res/dungeons/tower_floor.png", true, true, false);
		TILESET[TILE_DUNGEON_FLOOR] = new BaseTile(TILE_DUNGEON_FLOOR, "Пол подземелья", "res/dungeons/dungeon_floor.png", true, true, false);
		TILESET[TILE_DUNGEON_WALL] = new BaseTile(TILE_DUNGEON_WALL, "Стена подземелья", "res/dungeons/dungeon_wall.png", false, false, false);
		TILESET[TILE_SWAMP] = new BaseTile(TILE_SWAMP, "Болото", "res/dungeons/swamp.png", true, true, false);
		TILESET[TILE_FARM] = new BaseTile(TILE_FARM, "Ферма", "res/dungeons/farm.png", false, false, false);
		TILESET[TILE_BRIDGE_HOR] = new BaseTile(TILE_BRIDGE_HOR, "Мост", "res/dungeons/bridge_hor.png", true, true, false);
		TILESET[TILE_BRIDGE_VER] = new BaseTile(TILE_BRIDGE_VER, "Мост", "res/dungeons/bridge_ver.png", true, true, false);
		TILESET[TILE_OLD_CASTLE_FLOOR] = new BaseTile(TILE_OLD_CASTLE_FLOOR, "Пол старого замка", "res/dungeons/oldcastle_floor.png", true, true, false);
		TILESET[TILE_OLD_CASTLE_WALL] = new BaseTile(TILE_OLD_CASTLE_WALL, "Стена старого замка", "res/dungeons/oldcastle_wall.png", false, false, false);
		TILESET[TILE_MARSH] = new BaseTile(TILE_MARSH, "Болотный мох", "res/dungeons/marsh.png", true, true, false);
		TILESET[TILE_DIRT] = new BaseTile(TILE_DIRT, "Болотная грязь", "res/dungeons/dirt.png", true, true, false);
		TILESET[TILE_MARSH_TREE] = new BaseTile(TILE_MARSH_TREE, "Мангровое дерево", "res/dungeons/marsh_tree.png", false, false, false);
		TILESET[TILE_MARSH_TREE2] = new BaseTile(TILE_MARSH_TREE2, "Мангровое дерево", "res/dungeons/marsh_tree2.png", false, false, false);
		TILESET[TILE_MARSH_GRASS] = new BaseTile(TILE_MARSH_GRASS, "Болотник", "res/dungeons/marsh_grass.png", true, true, false);
		TILESET[TILE_MARSH_GRASS2] = new BaseTile(TILE_MARSH_GRASS2, "Остролист", "res/dungeons/marsh_grass2.png", true, true, false);
		TILESET[TILE_MARSH_GRASS3] = new BaseTile(TILE_MARSH_GRASS3, "Остролист", "res/dungeons/marsh_grass3.png", true, true, false);
		TILESET[TILE_MARSH_GRASS4] = new BaseTile(TILE_MARSH_GRASS4, "Нитевик", "res/dungeons/marsh_grass4.png", true, true, false);

	}

	public static String getTileName(int id){
		return TILESET[id].getName();
	}
	
	public static BaseTile getTile(int id){
		return TILESET[id];
	}

}