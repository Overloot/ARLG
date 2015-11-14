//КОД ПОРТИРОВАН ИЗ ПРОЕКТА BeaRLibMG(Авторы: JustHarry\Apromix)
//ПОРТ С FREE PASCAL
// В ходе рефакторинга внесены дополнения

package com.rommax;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapGenerator {

    LogWriter logWriter = new LogWriter();
    public Map map;
    public static final int MAX_GENERATORS = 8; // должно соответствовать количеству типов карт
    private Random rand = new Random();

    public static final int ID_FOREST_1 = 0;
    public static final int ID_MAZE_1 = 1;
    public static final int ID_FOREST_2 = 2;
    public static final int ID_MAZE_2 = 3;
    public static final int ID_TOWER = 4;
    public static final int ID_VILLAGE = 5;
    public static final int ID_OLD_CASTLE = 6;
    public static final int ID_FOREST_MARSH = 7;

    public void generateMap(Map map, int ID) {
        this.map = map;
        ID = 6; // для теста
		switch (ID){
			case ID_FOREST_1:
				ForestCreate();
				break;
			case ID_MAZE_1:
				MazeCreate();
				break;
			case ID_FOREST_2:
				LakesCreate(1);
				break;
			case ID_MAZE_2:
				RiftCreate();
				break;
			case ID_TOWER:
				TowerCreate();
				break;
			case ID_VILLAGE:
				VillageCreate();
				break;
			case ID_OLD_CASTLE:
				OldCastleCreate();
				break;
			case ID_FOREST_MARSH:
				forestMarshCreate();
				break;
		}
    }

    public void RiftCreate() {
        map.setName("#2#Пещеры ужаса#^#");
        for (int i = 0; i < (map.getHeight() * map.getWidth() / 10); i++)
            PartDraw(rand.nextInt(map.getHeight()), rand.nextInt(map.getWidth()),
				Tileset.TILE_GRASS, Tileset.TILE_TREE);
        for (int i = 0; i < map.getHeight(); i++)
            for (int j = 0; j < map.getWidth(); j++)
                if (map.field[i][j].getID() == Tileset.TILE_TREE)
                    map.setTileAt(i, j, Tileset.TILE_DUNGEON_WALL);
                else
                    map.setTileAt(i, j, Tileset.TILE_DUNGEON_FLOOR);
    }


    public int dist(int y1, int x1, int y2, int x2) {
        int d = (int) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        return d;
    }

	private void addTiles(int preTile, int tile){
		PartDraw(new Random().nextInt(map.getHeight()),
			new Random().nextInt(map.getWidth()), preTile, tile);
	}
	
	private void forestMarshCreate(){
		map.setName("#3#Мангровое болото#^#");
		fill(Tileset.TILE_MARSH);
		for (int i = 0; i < (int) map.getHeight() * map.getWidth() / 15; i++)
			switch (new Random().nextInt(15)){
				case 0:
					addTiles(Tileset.TILE_MARSH, Tileset.TILE_DIRT);
					break;
				case 1:
					addTiles(Tileset.TILE_MARSH, Tileset.TILE_MARSH_GRASS);
					break;
				case 2:
					addTiles(Tileset.TILE_MARSH, Tileset.TILE_MARSH_GRASS2);
					break;
				case 3:
					addTiles(Tileset.TILE_MARSH, Tileset.TILE_MARSH_GRASS3);
					break;
				case 4:
					addTiles(Tileset.TILE_MARSH, Tileset.TILE_MARSH_GRASS4);
					break;
				case 5:
					addTiles(Tileset.TILE_MARSH, Tileset.TILE_MARSH_TREE);
					break;
				default:
					addTiles(Tileset.TILE_MARSH, Tileset.TILE_MARSH_TREE2);
			}
	}
	
    public void TowerCreate() {
        int waterch = 20;
        int px, py, rad, k, i, j;
        map.setName("#7#Затонувшая башня#^#");
        int x = map.getHeight();
        int y = map.getWidth();
        px = map.getHeight() / 2;
        py = map.getWidth() / 2;
        if ((y - py) < (x - px))
            rad = y - py;
        else
            rad = x - px;
        k = rad - 5;
        while (k > 0) {
            for (i = 0; i < x; i++)
                for (j = 0; j < y; j++)
                    if (dist(i, j, px, py) == k) {
                        map.setTileAt(i, j, Tileset.TILE_TOWER_WALL);
                    }
            k -= 2;
        }
        AddModOnMap(1); // добавляет траву
        AddModOnMap(2); // добавляет лес
        AddModOnMap(3); // добавляет болото
    }

    // добавляет различные элементу на карту
    public void AddModOnMap(int modtype) {
        int i, j, x, y;
        int MapY = map.getHeight();
        int MapX = map.getWidth();
        switch (modtype) {

            case 1: { // добавляет траву
                for (i = 0; i < MapY; i++)
                    for (j = 0; j < MapX; j++)
                        if (!map.field[i][j].getPassable())
                            if (rand.nextInt(100) <= 40)
                                map.setTileAt(i, j, Tileset.TILE_GRASS);
            }
            break;

            case 2: // добавляет лес
                for (i = 0; i < MapX * MapY / 25; i++)
                    PartDraw(rand.nextInt(MapX) + 1, rand.nextInt(MapY) + 1, Tileset.TILE_GRASS, Tileset.TILE_TREE);
                break;

            case 3: // добавляет болото
                for (i = 0; i < MapY; i++) {
                    for (j = 0; j < MapX; j++)
                        if (rand.nextInt(100) <= 20)
                            map.setTileAt(i, j, Tileset.TILE_SWAMP);//SWAMP
                }
                break;
        }
    }

    public void VillageCreate() {

        // ************************************************************************
        // код полностью соответствует ForestCreate, кроме отмеченного комментариями
        map.setName("#5#Фермы у реки#^#");

        double density = 0.9F; // density переводится как плотность
        int i, j;
        int res;
        int x, y;

        x = map.getHeight();
        y = map.getWidth();
        for (i = 0; i < (int) x * y * density; i++)
            map.setTileAt(rand.nextInt(x), rand.nextInt(y), Tileset.TILE_TREE);

        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++) {
                if ((i == 0) || (j == 0) || (i == x - 1) || (j == y - 1)) {
                    map.setTileAt(i, j, Tileset.TILE_GRASS);
                    continue;
                }
                res = countnearby(i, j, Tileset.TILE_TREE);
                if (map.field[i][j].getID() == Tileset.TILE_TREE) {
                    if (res < 4)
                        map.setTileAt(i, j, Tileset.TILE_GRASS);
                    else
                        map.setTileAt(i, j, Tileset.TILE_TREE);
                }

            }

        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++)
                if (countnearby(i, j, Tileset.TILE_GRASS) < 3)
                    map.setTileAt(i, j, Tileset.TILE_TREE);

        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++)
                if ((countnearby(i, j, Tileset.TILE_GRASS) >= 7))
                    map.setTileAt(i, j, Tileset.TILE_GRASS);

        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++)
                if (countnearby(i, j, Tileset.TILE_GRASS) < 3)
                    map.setTileAt(i, j, Tileset.TILE_TREE);
        // ************************************************************************

        // ниже создаем реку
        //TODO: река должна больше гулять
        int springhead_x;
        int springhead_y;
        int farmPlace;
        int creekPlace;
        boolean bridgeAlreadyBuilt = false;
        // вертикальное расположение
        if (new Random().nextInt(2) == 0) { // 0 река будет течь по горизонтали, 1 по вертикали
            springhead_x = 0; // координаты начала истока реки
            springhead_y = new Random().nextInt(map.getHeight());
            for (int river_x = springhead_x; river_x < map.getHeight(); river_x++) {
                springhead_y = CalcOffset(springhead_y, 1, map.getHeight(), 1); // чтоб речка гуляла
                // создание фермы
                if (new Random().nextInt(100) < 30) {
                    farmPlace = CalcOffset(springhead_y, 1, map.getHeight(), 2);
                    map.setTileAt(river_x, farmPlace, Tileset.TILE_FARM);
                }
                //рукав реки
                if (new Random().nextInt(100) < 2) {
                    creekPlace = river_x;
                    for (int yy = springhead_y; yy < y; yy++) {
                        creekPlace = (CalcOffset(creekPlace, 1, map.getWidth(), 1));
                        CreateThreeTilesHorizontal(creekPlace, yy, Tileset.TILE_WATER);
                    }
                } else {
                    // главная река
                    CreateThreeTilesVertical(river_x, springhead_y, Tileset.TILE_WATER);
                }
                //если повезет - здесь будет мост
                if ((new Random().nextInt(100) <= 5) && (!bridgeAlreadyBuilt)) {
                    CreateThreeTilesVertical(river_x, springhead_y, Tileset.TILE_BRIDGE_HOR);
                    bridgeAlreadyBuilt = true;
                }
            }
        }
        // горизонтальное расположение
        else {
            springhead_x = new Random().nextInt(map.getWidth()); // координаты начала истока реки
            springhead_y = 0;
            for (int river_y = springhead_y; river_y < map.getWidth(); river_y++) {
                springhead_x = CalcOffset(springhead_x, 1, map.getWidth(), 1); // чтоб речка гуляла
                // создание фермы
                if (new Random().nextInt(100) < 30) {
                    farmPlace = CalcOffset(springhead_x, 1, map.getWidth(), 2);
                    map.setTileAt(farmPlace, river_y, Tileset.TILE_FARM);
                }
                // рукав реки
                if (new Random().nextInt(100) < 2) {
                    creekPlace = river_y;
                    for (int xx = springhead_x; xx < x; xx++) {
                        creekPlace = (CalcOffset(creekPlace, 1, map.getHeight(), 1));
                        CreateThreeTilesVertical(xx, creekPlace, Tileset.TILE_WATER);
                    }
                } else {
                    // главная река
                    CreateThreeTilesHorizontal(springhead_x, river_y, Tileset.TILE_WATER);
                }
                // если повезет - здесь будет мост
                if ((new Random().nextInt(100) <= 5) && (!bridgeAlreadyBuilt)) {
                    CreateThreeTilesHorizontal(springhead_x, river_y, Tileset.TILE_BRIDGE_VER);
                    bridgeAlreadyBuilt = true;
                }
            }
        }
    }

    private void CreateThreeTilesHorizontal(int x, int y, int tile) {
        if (x + 1 < map.getWidth()) map.setTileAt(x + 1, y, tile);
        if (x - 1 > 0) map.setTileAt(x - 1, y, tile);
        map.setTileAt(x, y, tile);
    }

    private void CreateThreeTilesVertical(int x, int y, int tile) {
        if (y + 1 < map.getHeight()) map.setTileAt(x, y + 1, tile);
        if (y - 1 > 0) map.setTileAt(x, y - 1, tile);
        map.setTileAt(x, y, tile);
    }

    // метод смещения в сторону от заданной точки
    // принимает начальную точку, и значение в приделах которого следует отступить
    // возвращается точная координата которая отличчается минимум на 1, но не меньше 1 и не больше range - 2,
    public int CalcOffset(int startPoint, int max_scatter, int range, int min_scatter) {
        if (new Random().nextInt(2) == 0) startPoint = startPoint - new Random().nextInt(max_scatter) - min_scatter;
        else startPoint = startPoint + new Random().nextInt(max_scatter) + min_scatter;
        if (startPoint < 1) startPoint = 1;
        if (startPoint >= range) startPoint = range - 2;
        return startPoint;
    }

    public void LakesCreate(int typ) {
        map.setName("#5#Старый лес#^#");

        double density = 0.9F;
        int i, j;
        int res;
        int x, y;

        x = map.getHeight();
        y = map.getWidth();
        for (i = 0; i < (int) x * y * density; i++)
            map.setTileAt(rand.nextInt(x), rand.nextInt(y), Tileset.TILE_TREE);


        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++) {
                if ((i == 0) || (j == 0) || (i == x - 1) || (j == y - 1)) {
                    map.setTileAt(i, j, Tileset.TILE_GRASS);
                    continue;
                }
                res = countnearby(i, j, Tileset.TILE_TREE);
                if (map.field[i][j].getID() == Tileset.TILE_TREE) {
                    if (res < 4)
                        map.setTileAt(i, j, Tileset.TILE_GRASS);
                    else
                        map.setTileAt(i, j, Tileset.TILE_TREE);
                }

            }

        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++)
                if (countnearby(i, j, Tileset.TILE_GRASS) < 3)
                    map.setTileAt(i, j, Tileset.TILE_TREE);

        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++)
                if ((countnearby(i, j, Tileset.TILE_GRASS) >= 7))
                    map.setTileAt(i, j, Tileset.TILE_GRASS);

        for (i = 0; i < x; i++)
            for (j = 0; j < y; j++)
                if (countnearby(i, j, Tileset.TILE_GRASS) < 3)
                    map.setTileAt(i, j, Tileset.TILE_TREE);


    }

	// tile - тайл, на который будет заменен preTile
    private void PartDraw(int x1, int y1, int preTile, int tile) {
        int i, j, e, s, w, n;
        Random random = new Random();
        i = x1;
        j = y1;
        for (int k = 1; k <= 20; k++) {
            n = random.nextInt(6);
            e = random.nextInt(6);
            s = random.nextInt(6);
            w = random.nextInt(6);
            if (!map.hasTileAt(i, j)) return;
            if (n == 1) {
                i = i - 1;
                if (!map.hasTileAt(i, j)) return;
                if (map.field[i][j].getID() != preTile) return;
                map.setTileAt(i, j, tile);
            }
            if (n == 1) {
                i = i + 1;
                if (!map.hasTileAt(i, j)) return;
                if (map.field[i][j].getID() != preTile) return;
                map.setTileAt(i, j, tile);
            }
            if (n == 1) {
                j = j - 1;
                if (!map.hasTileAt(i, j)) return;
                if (map.field[i][j].getID() != preTile) return;
                map.setTileAt(i, j, tile);
            }
            if (n == 1) {
                j = j + 1;
                if (!map.hasTileAt(i, j)) return;
                if (map.field[i][j].getID() != preTile) return;
                map.setTileAt(i, j, tile);
            }
        }
    }


    public int countnearby(int x, int y, int id) {
        int res = 0;
        if (map.hasTileAt(x - 1, y) && map.field[x - 1][y].getID() == id) res++;
        if (map.hasTileAt(x + 1, y) && map.field[x + 1][y].getID() == id) res++;
        if (map.hasTileAt(x, y + 1) && map.field[x][y + 1].getID() == id) res++;
        if (map.hasTileAt(x, y - 1) && map.field[x][y - 1].getID() == id) res++;
        if (map.hasTileAt(x - 1, y - 1) && map.field[x - 1][y - 1].getID() == id) res++;
        if (map.hasTileAt(x - 1, y + 1) && map.field[x - 1][y + 1].getID() == id) res++;
        if (map.hasTileAt(x + 1, y - 1) && map.field[x + 1][y - 1].getID() == id) res++;
        if (map.hasTileAt(x + 1, y + 1) && map.field[x + 1][y + 1].getID() == id) res++;
        return res;
    }

    public void StartWave(int px, int py) {
        int MAX_DEPTH = 5;
        int x, y, dx, dy, d, i, j;
        if ((px < 5) || (px > map.getHeight() - 5) || (py < 5) || (py > map.getWidth() - 5)) return;
        x = map.getHeight();
        y = map.getWidth();
        dx = 0;
        dy = 0;
        switch (rand.nextInt(4)) {
            case 0:
                dx = -1;
                break;
            case 1:
                dx = 1;
                break;
            case 2:
                dy = -1;
                break;
            case 3:
                dy = 1;
                break;
        }

        d = rand.nextInt(MAX_DEPTH) + 1;
        for (i = 0; i < d; i++) {
            if ((px < 5) || (px > x - 5) || (py < 5) || (py > y - 5)) return;
            if (countnearby(px, py, Tileset.TILE_WATER) > 7) return;
            map.setTileAt(px, py, Tileset.TILE_WATER);
            px += dx;
            py += dy;
        }
        StartWave(px, py);
    }


    public void MazeCreate() {
        map.setName("#7#Канализация #^#");
        fill(Tileset.TILE_DUNGEON_WALL);
        int px = map.getHeight() / 2;
        int py = map.getWidth() / 2;
        for (int i = 0; i < map.getHeight() * map.getWidth() / 100; i++) {
            px = new Random().nextInt(map.getHeight());
            py = new Random().nextInt(map.getWidth());
            StartWave(px, py);
        }
    }

    public void ForestCreate() {
        map.setName("#3#Лес Древних #^#");
        for (int i = 0; i < (map.getHeight() * map.getWidth() / 10); i++)
            PartDraw(new Random().nextInt(map.getHeight()),
				new Random().nextInt(map.getWidth()),
					Tileset.TILE_GRASS, Tileset.TILE_TREE);
    }

	// Заполняет всю карту указанным тайлом
	private void fill(int tile){
        for (int y = 0; y < map.getHeight(); y++)
            for (int x = 0; x < map.getWidth(); x++)
				map.setTileAt(y, x, tile);
	}
	
	// Заполняет область указанным тайлом (создает комнату)
	private void fill(int y, int x, int height, int width, int tile){
		for (int i = y; i <= y + height; i++)
			for (int j = x; j <= x + width; j++)
				map.setTileAt(i, j, tile);		
	}
	
	private void addDoor(){
		
	}
	
    public void OldCastleCreate() {
        final int ROOM_MAX_SIZE = 5;
        final int ROOM_MIN_SIZE = 3;
        final int ROOM_MAX_TOTAL_SIZE = ROOM_MIN_SIZE + ROOM_MAX_SIZE + 1;

        map.setName("#7#Старый замок #^#");
        int pointX = 1, pointY = 1;
        int sizeX, sizeY;
        int doorX, doorY;
        int sizeHall;
        int tempX, tempY;
		fill(Tileset.TILE_OLD_CASTLE_WALL);//заполняем карту сплошными стенами
        while (true) {
            sizeX = new Random().nextInt(ROOM_MAX_SIZE) + ROOM_MIN_SIZE;
            sizeY = new Random().nextInt(ROOM_MAX_SIZE) + ROOM_MIN_SIZE;
            // проверка на выход за границы карты
            if (pointX + sizeX > map.getWidth() -1) {
                pointY = pointY + ROOM_MAX_TOTAL_SIZE;
                pointX = 1;
                continue;
            }
            if (pointY + sizeY > map.getHeight() - 1) break;
			fill(pointY, pointX, sizeY, sizeX, Tileset.TILE_OLD_CASTLE_FLOOR);// Ставим комнату
			
			 
            // вычисляем координаты для двери и перехода
            doorY = 1 + pointY + sizeY;
            doorX = (int) pointX + sizeX / 2;
            // создаем переход
            sizeHall = ROOM_MAX_TOTAL_SIZE - sizeY;
            if (doorY + ROOM_MAX_TOTAL_SIZE < map.getHeight() -1) {
                for (int y = doorY; y <= doorY + sizeHall; y++)
                    map.setTileAt(y, doorX, Tileset.TILE_OLD_CASTLE_FLOOR);
                // ставим дверь
                map.setTileAt(doorY, doorX, Tileset.TILE_CLOSED_DOOR);
            }


            // ставим опцианальную дверь, если есть возможность
            // дверь ставится по горизонтале, справа налево.
            // в первый проход основного while двери создаваться не должны, на следующих они создадутся
            doorX = pointX;
            doorY = (int) pointY + sizeY / 2;

            if (doorY > map.getHeight() -  1) {
                pointX = pointX + ROOM_MAX_TOTAL_SIZE;
                pointY = 1;
                logWriter.myMessage("условие верно 523 строка");
                continue;
            }

            tempX = doorX;
            tempY = doorY;
            tempX--;
            while (true){
                doorX--;
                if (doorX - 1 <= 1) break; // проверка на выход за границы карты
                if (map.field[doorY][doorX].getID() == Tileset.TILE_OLD_CASTLE_FLOOR) { // если за стеной есть свободное пространство,
                    for (int xx = doorX; xx <= tempX; xx++) { // то прокладываем туда проход
                        map.setTileAt(doorY, xx, Tileset.TILE_OLD_CASTLE_FLOOR);
                    }
                    doorX++;
                    map.setTileAt(tempY, tempX, Tileset.TILE_CLOSED_DOOR); // правай дверь в проходе
                    map.setTileAt(doorY, doorX, Tileset.TILE_CLOSED_DOOR); // левая дверь в проходе
                    //System.exit(0);
                    break;
                }
            }
			
			
            // координаты будущей новой комнаты
            pointX = pointX + ROOM_MAX_TOTAL_SIZE;
        }

    }

}