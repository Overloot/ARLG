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
	public static final int TILE_CAVE_BLACK_ABYSS_FLOOR = 27;
	public static final int TILE_CAVE_BLACK_ABYSS_WALL = 28;
	public static final int TILE_CAVE_BLACK_ABYSS_BONFIRE = 29;
	public static final int TILE_CAVE_BLACK_ABYSS_SKELETON = 30;
	public static final int TILE_WINTER_FOREST_FLOOR = 31;
	public static final int TILE_WINTER_FOREST_FLOOR2 = 32;
	public static final int TILE_WINTER_FOREST_FLOOR3 = 33;
	public static final int TILE_WINTER_FOREST_TREE = 34;
	public static final int TILE_WINTER_FOREST_TREE2 = 35;
	public static final int TILE_OLD_CASTLE_UNDERGROUND_WALL = 36;
	public static final int TILE_OLD_CASTLE_UNDERGROUND_FLOOR = 37;

	public static final int MAX_TILES = 38;

    public static final int TILE_SIZE = 32;

	private static BaseTile[] TILESET;

	static{
		TILESET = new BaseTile[MAX_TILES];
		TILESET[TILE_EMPTY] = new BaseTile(TILE_EMPTY, "Пустой тайл", "res/dungeons/empty.png", false, false, false, false, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_GRASS] = new BaseTile(TILE_GRASS, "Трава", "res/dungeons/grass.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_TREE] = new BaseTile(TILE_TREE, "Дерево", "res/dungeons/tree.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_CLOSED_DOOR] = new BaseTile(TILE_CLOSED_DOOR, "Закрытая дверь", "res/dungeons/closed_door.png", false, false, true, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_OPENED_DOOR] = new BaseTile(TILE_OPENED_DOOR, "Открытая дверь", "res/dungeons/opened_door.png", true, true, true, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_STAIR_UP] = new BaseTile(TILE_STAIR_UP, "Лестница вверх", "res/dungeons/stair_up.png", true, true, false, false, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_STAIR_DOWN] = new BaseTile(TILE_STAIR_DOWN, "Лестница вниз", "res/dungeons/stair_down.png", true, true, false, false, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_DESTROYED_TOWER_WALL] = new BaseTile(TILE_DESTROYED_TOWER_WALL, "Разрушенная стена башни", "res/dungeons/destroyed_tower_wall.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_TOWER_WALL] = new BaseTile(TILE_TOWER_WALL,"Стена башни", "res/dungeons/tower_wall.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_WATER] = new BaseTile(TILE_WATER, "Вода", "res/dungeons/water.png", true, true, false, false, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_TOWER_FLOOR] = new BaseTile(TILE_TOWER_FLOOR, "Пол башни", "res/dungeons/tower_floor.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_DUNGEON_FLOOR] = new BaseTile(TILE_DUNGEON_FLOOR, "Пол подземелья", "res/dungeons/dungeon_floor.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_DUNGEON_WALL] = new BaseTile(TILE_DUNGEON_WALL, "Стена подземелья", "res/dungeons/dungeon_wall.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_SWAMP] = new BaseTile(TILE_SWAMP, "Болото", "res/dungeons/swamp.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_FARM] = new BaseTile(TILE_FARM, "Ферма", "res/dungeons/farm.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_BRIDGE_HOR] = new BaseTile(TILE_BRIDGE_HOR, "Мост", "res/dungeons/bridge_hor.png", true, true, false, true, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_BRIDGE_VER] = new BaseTile(TILE_BRIDGE_VER, "Мост", "res/dungeons/bridge_ver.png", true, true, false, true, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_OLD_CASTLE_FLOOR] = new BaseTile(TILE_OLD_CASTLE_FLOOR, "Пол старого замка", "res/dungeons/oldcastle_floor.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_OLD_CASTLE_WALL] = new BaseTile(TILE_OLD_CASTLE_WALL, "Стена старого замка", "res/dungeons/oldcastle_wall.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_MARSH] = new BaseTile(TILE_MARSH, "Болотный мох", "res/dungeons/marsh.png", true, true, false, true, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_DIRT] = new BaseTile(TILE_DIRT, "Болотная грязь", "res/dungeons/dirt.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_MARSH_TREE] = new BaseTile(TILE_MARSH_TREE, "Мангровое дерево", "res/dungeons/marsh_tree.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_MARSH_TREE2] = new BaseTile(TILE_MARSH_TREE2, "Мангровое дерево", "res/dungeons/marsh_tree2.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_MARSH_GRASS] = new BaseTile(TILE_MARSH_GRASS, "Болотник", "res/dungeons/marsh_grass.png", true, true, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_MARSH_GRASS2] = new BaseTile(TILE_MARSH_GRASS2, "Остролист", "res/dungeons/marsh_grass2.png", true, true, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_MARSH_GRASS3] = new BaseTile(TILE_MARSH_GRASS3, "Остролист", "res/dungeons/marsh_grass3.png", true, true, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_MARSH_GRASS4] = new BaseTile(TILE_MARSH_GRASS4, "Нитевик", "res/dungeons/marsh_grass4.png", true, true, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_CAVE_BLACK_ABYSS_FLOOR] = new BaseTile(TILE_CAVE_BLACK_ABYSS_FLOOR, "Пол пещеры", "res/dungeons/cave_black_abyss_floor.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_CAVE_BLACK_ABYSS_WALL] = new BaseTile(TILE_CAVE_BLACK_ABYSS_WALL, "Стена пещеры", "res/dungeons/cave_black_abyss_wall.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_CAVE_BLACK_ABYSS_BONFIRE] = new BaseTile(TILE_CAVE_BLACK_ABYSS_BONFIRE, "Костер", "res/dungeons/cave_black_abyss_bonfire.png", true, true, false, true, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_CAVE_BLACK_ABYSS_SKELETON] = new BaseTile(TILE_CAVE_BLACK_ABYSS_SKELETON, "Скелет", "res/dungeons/cave_black_abyss_skeleton.png", true, true, false, true, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_WINTER_FOREST_FLOOR] = new BaseTile(TILE_WINTER_FOREST_FLOOR, "Снег", "res/dungeons/winter_forest_floor.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_WINTER_FOREST_FLOOR2] = new BaseTile(TILE_WINTER_FOREST_FLOOR2, "Снег", "res/dungeons/winter_forest_floor2.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_WINTER_FOREST_FLOOR3] = new BaseTile(TILE_WINTER_FOREST_FLOOR3, "Снег", "res/dungeons/winter_forest_floor3.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_WINTER_FOREST_TREE] = new BaseTile(TILE_WINTER_FOREST_TREE, "Елка в снегу", "res/dungeons/winter_forest_tree.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_WINTER_FOREST_TREE2] = new BaseTile(TILE_WINTER_FOREST_TREE2, "Елка в снегу", "res/dungeons/winter_forest_tree2.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_OLD_CASTLE_UNDERGROUND_FLOOR] = new BaseTile(TILE_OLD_CASTLE_UNDERGROUND_FLOOR, "Пол подземелья старого замка", "res/dungeons/old_castle_underground_floor.png", true, true, false, true, false, 100, ItemSet.EMPTY_JAR);
		TILESET[TILE_OLD_CASTLE_UNDERGROUND_WALL] = new BaseTile(TILE_OLD_CASTLE_UNDERGROUND_WALL, "Стена стена старого замка", "res/dungeons/old_castle_underground_wall.png", false, false, false, false, true, 100, ItemSet.EMPTY_JAR);

	}

	public static String getTileName(int id){
		return TILESET[id].getName();
	}
	
	public static BaseTile getTile(int id){
		return TILESET[id];
	}

}