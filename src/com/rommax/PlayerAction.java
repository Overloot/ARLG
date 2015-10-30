package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PlayerAction {
	
	private Map map;
	private MainPanel mp;
	private ItemSelectMessage message;
	
	public PlayerAction(Map map, MainPanel mp) {
		this.map = map;
		this.mp = mp;
	}
	
	// Осмотр тайла
	public void lookTo(int dx, int dy) {
		if (!map.hasTileAt(KeyHandler.ly + dy, KeyHandler.lx + dx)) return;
		if (!mp.HasTileAtScreen(KeyHandler.ly + dy, KeyHandler.lx + dx)) return;

		map.field[KeyHandler.ly][KeyHandler.lx].setCursor(false);
		KeyHandler.ly += dy;
		KeyHandler.lx += dx;
		String textLine;
		map.field[KeyHandler.ly][KeyHandler.lx].setCursor(true);
		if (map.field[KeyHandler.ly][KeyHandler.lx].getVisible())
		{
		textLine = "Здесь находится " + Tileset.getTileName(map.field[KeyHandler.ly][KeyHandler.lx].getID()).toLowerCase() + ". ";
		if (map.field[KeyHandler.ly][KeyHandler.lx].getMonster() != null)
		textLine += "#^#Здесь стоит " + map.field[KeyHandler.ly][KeyHandler.lx].getMonster().getName().toLowerCase() + ".";
		LinkedList<Item> ilist = map.field[KeyHandler.ly][KeyHandler.lx].getItemList();
		if (ilist.size() != 0){
			if (ilist.size() > 1 )
			textLine += "#^# Здесь лежит много вещей. ";
			else
			textLine += "#^#" + ilist.getFirst().getName() + " лежит здесь. ";
	  	}
		mp.descStr = textLine;
		}
		else
		mp.descStr = "#^#Вы #2#не видите#^# этого! ";
		
	}
	
	// Пропуск хода и отдых
	public void rest() {
		move(0, 0);
		map.getGame().logMessage("Вы решили #8#отдохнуть#^#!");
	}
	
	// Движение влево
	public void left() { if (move(0, -1)) setX(-1);
	}
	
	// Движение вправо
	public void right() {
		if (move(0, +1)) setX(+1);
	}
	
	// Движение вверх
	public void up() {
		if (move(-1, 0)) setY(-1);
	}
	
	// Движение вниз
	public void down() {
		if (move(+1, 0)) setY(+1);
	}
	
	// Q Движение влево и вверх
	public void moveLeftUp() {
		if (move(-1, -1)) setYX(-1, -1);
	}

	// Z Движение влево и вниз
	public void moveLeftDown() {
		if (move(+1, -1)) setYX(+1, -1);
	}

	// E Движение вправо и вверх
	public void moveRightUp() {
		if (move(-1, +1)) setYX(-1, +1);
	}

	// C Движение вправо и вниз
	public void moveRightDown() {
		if (move(+1, +1)) setYX(+1, +1);
	}
	
	// Вверх по лестнице
	public void upStair() {
		if (map.field[map.getGame().player.getY()][map.getGame().player.getX()].getID()!=Tileset.TILE_STAIR_UP )
			map.getGame().logMessage("Вы #2#не можете#^# подняться #8#вверх#^# здесь!#/#");
				else map.getGame().switchMap(-1);
	}
	
	// Вниз по лестнице
	public void downStair() {
		if (map.field[map.getGame().player.getY()][map.getGame().player.getX()].getID()!=Tileset.TILE_STAIR_DOWN )
			map.getGame().logMessage("Вы #2#не можете#^# спуститься #8#вниз#^# здесь!#/#");
			   else map.getGame().switchMap(+1);
	}
	
	// Открыть что-то
	public boolean openIt() {
		map.getGame().keyHandler.OPEN_MODE = true;
		map.getGame().logMessage("В каком направлении вы хотите #8#открыть#^# что-то?#/#");
		return false;
	}
		
	// Закрыть что-то
	public boolean closeIt() {
		map.getGame().keyHandler.CLOSE_MODE = true;
		map.getGame().logMessage("В каком направлении вы хотите #8#закрыть#^# что-то?#/#");
		return false;
	}
	
	// Идентификация чего-то
	public boolean identifyIt() {
		map.getGame().frame1.setFocusable(false);
		map.getGame().frame1.setFocusableWindowState(false);
		message = new ItemSelectMessage();
		message.command = 'b';
		ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_ANY, map.getGame().player.getInventory(), message);
		frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame2.setTitle("Что вы хотите идентифицировать?");
		frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
		frame2.toFront();
		frame2.setVisible(true);
		return false;
	}
	
	private boolean move(int y, int x) {
		return map.getGame().player.move(y, x);
	}
	
	private void setX(int x) {
		map.setCurrentX(map.getCurrentX() + x);
	}

	private void setY(int y) {
		map.setCurrentY(map.getCurrentY() + y);
	}

	private void setYX(int y, int x) {
		map.setCurrentY(map.getCurrentY() + y);
		map.setCurrentX(map.getCurrentX() + x);

	}

}