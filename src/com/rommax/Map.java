package com.rommax;

import java.util.*;

public class Map extends BaseMap {
    public Tile[][] field;

    public Map(Game game) {
		super("UNKNOWN MAP!");
		setGame(game);
        field = new Tile[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++)
                field[i][j] = new Tile(Tileset.TILE_EMPTY, "", "", false, false, false, false, false, 100, ItemSet.EMPTY_JAR, Tileset.TILE_EMPTY);
    }

    public void placeMonsterAt(int y, int x, Monster monster) {
        field[y][x].setMonster(monster);
    }

    public void placeItemAt(int y, int x, Item item) {
        field[y][x].AddItem(item);
    }

	public void placeBloodAt(int y, int x){
		if (field[y][x].getBlood() == 0 && field[y][x].getBloodable())
			field[y][x].setBlood(new Random().nextInt(Tile.BLOOD_AMOUNT) + 1);
	}
	
    public void deleteMonsterAt(int y, int x) {
        field[y][x].setMonster(null);
    }

    public boolean hasTileAt(int y, int x) {
        return (y >= 0 && y < getHeight() && x >= 0 && x < getWidth());
    }

    private void setTileAs(Tile tile, BaseTile btile) {
        tile.setID(btile.getID());
        tile.setPassable(btile.getPassable());
        tile.setTransparent(btile.getTransparent());
        tile.setOpenable(btile.getOpenable());
        tile.setBloodable(btile.getBloodable());
        if (btile.getID() == Tileset.TILE_OPENED_DOOR)
            tile.setOpened(true);
        else
            tile.setOpened(false);
    }

    public void setTileAt(int y, int x, int id) {
        BaseTile bt = Tileset.getTile(id);
        setTileAs(field[y][x], bt);
    }

    //карта заполняется травой, затем запускается генератор карты, затем расставляются лесенки
    public boolean generate() {

		Random random = new Random();
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++) {
                BaseTile baseTile = Tileset.getTile(Tileset.TILE_GRASS);
                setTileAs(field[i][j], baseTile);
            }
		//random.nextInt(MapSet.MAX_MAPS));// Просто рандомная карта
		// Карта по списку из MapSet
		new MapGenerator().generateMap(this, MapSet.getMap(getGame().currentMapNumber).getGenID());
		BaseMap m = MapSet.getMap(getGame().currentMapNumber);
		setName(m.getName());
		setLevel(m.getLevel());
        AddRandomStairs();
		return true;
    }

    public void AddRandomStairs() {
		// Ограничение на кол-во карт в MapSet или общее кол-во карт для мира
		int min = Math.min(MapSet.mapsCount(), getGame().MAX_FLOORS) - 1;
        if (getGame().currentMapNumber >= min) return;		
        // Добавляем лестницы вниз в случайных местах
		Random random = new Random();
        int y = 0;
        int x = 0;
        for (int i = 0; i < 9; i++) {
            y = random.nextInt(getHeight());
            x = random.nextInt(getWidth());
            while (field[y][x].getPassable() == false || field[y][x].getMonster() != null) {
                y = random.nextInt(getHeight());
                x = random.nextInt(getWidth());
            }
            setTileAt(y, x, Tileset.TILE_STAIR_DOWN);
        }
    }

	// Игрок помещается в центр экрана
	public void updatePlayer(){
		setX(getGame().player.getX() - ((GameWindow.getScreenTileSizeX() * Tileset.TILE_SIZE) / (Tileset.TILE_SIZE * 2)));
        setY(getGame().player.getY() - ((GameWindow.getScreenTileSizeY() * Tileset.TILE_SIZE) / (Tileset.TILE_SIZE * 2)));
        FOV(getGame().player.getY(), getGame().player.getX(), getGame().player.getFOVRAD().getCurrent());
	}
	
    private void drawLine(int x1, int y1, int x2, int y2) {
        int deltaX = Math.abs(x2 - x1);
        int deltaY = Math.abs(y2 - y1);
        int signX = x1 < x2 ? 1 : -1;
        int signY = y1 < y2 ? 1 : -1;
        int error = deltaX - deltaY;
        for (; ; ) {
            if (hasTileAt(x1, y1)) {
                field[x1][y1].setVisible(true);
                field[x1][y1].lastseenID = field[x1][y1].getID();
            } else
                break;
            if (!field[x1][y1].getTransparent()) break;
            if (x1 == x2 && y1 == y2)
                break;
            int error2 = error * 2;
            if (error2 > -deltaY) {
                error -= deltaY;
                x1 += signX;
            }
            if (error2 < deltaX) {
                error += deltaX;
                y1 += signY;
            }
        }
    }

    void FOV(int x0, int y0, int radius) {
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++)
                if (field[i][j].getVisible()) {
                    field[i][j].setSeen(true);
                    field[i][j].setVisible(false);
                }
        int x = 0;
        int y = radius;
        int delta = 2 - 2 * radius;
        int error = 0;
        while (y >= 0) {
            drawLine(x0, y0, x0 + x, y0 + y);
            drawLine(x0, y0, x0 + x, y0 - y);
            drawLine(x0, y0, x0 - x, y0 + y);
            drawLine(x0, y0, x0 - x, y0 - y);
            drawLine(x0, y0, x0 + x - 1, y0 + y);
            drawLine(x0, y0, x0 + x - 1, y0 - y);
            drawLine(x0, y0, x0 - x, y0 + y - 1);
            drawLine(x0, y0, x0 - x, y0 - y - 1);
            error = 2 * (delta + y) - 1;
            if (delta < 0 && error <= 0) {
                ++x;
                delta += 2 * x + 1;
                continue;
            }
            error = 2 * (delta - x) - 1;
            if (delta > 0 && error > 0) {
                --y;
                delta += 1 - 2 * y;
                continue;
            }
            ++x;
            delta += 2 * (x - y);
            --y;
        }
    }
}