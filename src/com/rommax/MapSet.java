package com.rommax;

public class MapSet {
	
    public static final int ID_FOREST_1 = 0;
    public static final int ID_MAZE_1 = 1;
    public static final int ID_FOREST_2 = 2;
    public static final int ID_MAZE_2 = 3;
    public static final int ID_TOWER = 4;
    public static final int ID_VILLAGE = 5;
    public static final int ID_OLD_CASTLE = 6;
    public static final int ID_FOREST_MARSH = 7;
    public static final int ID_CAVE_BLACK_ABYSS = 8;
    public static final int ID_SIBERIA = 9;
    public static final int ID_OLD_CASTLE_UNDERGROUND = 10;

	/*>>>*/public static final int MAX_MAPS =11;
	
	private static int count = 0;

	private static BaseMap[] MAPSET;

	static{
		MAPSET = new BaseMap[MAX_MAPS];
		
		add(ID_FOREST_1,	3, "Старый Лес", 1);
		add(ID_FOREST_1, 	3, "Лес Энтов", 2);
		add(ID_MAZE_1, 		7, "Канализация", 2);
		
	}

	private static void add(int genID, int color, String name, int level){
		MAPSET[count] = new BaseMap(count, genID, "#" + color + "#" + name + "#^#", level);
		count++;
	}

	public static BaseMap getMap(int id){
		return MAPSET[id];
	}
	
	public static int mapsCount(){
		return count;
	}

}