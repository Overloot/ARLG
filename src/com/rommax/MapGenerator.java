//КОД ПОРТИРОВАН ИЗ ПРОЕКТА BeaRLibMG(Авторы: JustHarry\Apromix)
//ПОРТ С FREE PASCAL

package com.rommax;

import java.util.*;

public class MapGenerator {

    public Map map;
    public static final int MAX_GENERATORS = 5;
    private Random rand = new Random();

    public static final int ID_FOREST_1 = 0;
    public static final int ID_MAZE_1 = 1;
    public static final int ID_FOREST_2 = 2;
    public static final int ID_MAZE_2 = 3;
    public static final int ID_TOWER = 4;

    public void generateMap(Map map, int ID) {
        this.map = map;
        if (ID == ID_FOREST_1)
            ForestCreate();
        else if (ID == ID_MAZE_1)
            MazeCreate();
        else if (ID == ID_FOREST_2)
            LakesCreate(1);
        else if (ID == ID_MAZE_2)
            RiftCreate();
        else if (ID == ID_TOWER)
            TowerCreate();
    }


    public void RiftCreate() {
        int i, j;
        map.setName("#2#Пещеры ужаса#^#");

        for (i = 0; i < (map.getHeight() * map.getWidth() / 10); i++)
            ForestPartDraw(rand.nextInt(map.getHeight()), rand.nextInt(map.getWidth()));

        for (i = 0; i < map.getHeight(); i++)
            for (j = 0; j < map.getWidth(); j++)
                if (map.field[i][j].getID() == Tileset.TILE_TREE)
                    map.setTileAt(i, j, Tileset.TILE_DUNGEON_WALL);
                else
                    map.setTileAt(i, j, Tileset.TILE_DUNGEON_FLOOR);


    }


    public int dist(int y1, int x1, int y2, int x2) {
        int d = (int) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        return d;
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
        AddModOnMap(1);
        AddModOnMap(2);
        AddModOnMap(3);


    }

    public void AddModOnMap(int modtype) {
        int i, j, x, y;
        int MapY = map.getHeight();
        int MapX = map.getWidth();
        switch (modtype) {
            case 1: {
                for (i = 0; i < MapY; i++)
                    for (j = 0; j < MapX; j++)
                        if (!map.field[i][j].getPassable())
                            if (rand.nextInt(100) <= 40)
                                map.setTileAt(i, j, Tileset.TILE_GRASS);
            }
            break;
            case 2:
                for (i = 0; i < MapX * MapY / 25; i++)
                    ForestPartDraw(rand.nextInt(MapX) + 1, rand.nextInt(MapY) + 1);
                break;
            case 3:
                for (i = 0; i < MapY; i++) {
                    for (j = 0; j < MapX; j++)
                        if (rand.nextInt(100) <= 20)
                            map.setTileAt(i, j, Tileset.TILE_WATER);//WATER
                }
                break;
        }
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

    private void ForestPartDraw(int x1, int y1) {
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
                if (map.field[i][j].getID() != Tileset.TILE_GRASS) return;
                map.setTileAt(i, j, Tileset.TILE_TREE);
            }
            if (n == 1) {
                i = i + 1;
                if (!map.hasTileAt(i, j)) return;
                if (map.field[i][j].getID() != Tileset.TILE_GRASS) return;
                map.setTileAt(i, j, Tileset.TILE_TREE);
            }
            if (n == 1) {
                j = j - 1;
                if (!map.hasTileAt(i, j)) return;
                if (map.field[i][j].getID() != Tileset.TILE_GRASS) return;
                map.setTileAt(i, j, Tileset.TILE_TREE);
            }
            if (n == 1) {
                j = j + 1;
                if (!map.hasTileAt(i, j)) return;
                if (map.field[i][j].getID() != Tileset.TILE_GRASS) return;
                map.setTileAt(i, j, Tileset.TILE_TREE);
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


        for (int i = 0; i < map.getHeight(); i++)
            for (int j = 0; j < map.getWidth(); j++)
                map.setTileAt(i, j, Tileset.TILE_DUNGEON_WALL);

        int px = map.getHeight() / 2;
        int py = map.getWidth() / 2;
        for (int i = 0; i < map.getHeight() * map.getWidth() / 100; i++) {
            px = rand.nextInt(map.getHeight());
            py = rand.nextInt(map.getWidth());
            StartWave(px, py);
        }

    }

    public void ForestCreate() {
        map.setName("#3#Лес Древних #^#");

        Random random = new Random();
        for (int i = 0; i < (map.getHeight() * map.getWidth() / 10); i++)
            ForestPartDraw(random.nextInt(map.getHeight()), random.nextInt(map.getWidth()));
    }


}