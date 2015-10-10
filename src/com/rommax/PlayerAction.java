package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PlayerAction {
	
	private Map map;
	private ItemSelectMessage message;
	
	public PlayerAction(Map map) {
		this.map = map;
	}
	
	// Пропуск хода и отдых
	public void rest() {
		move(0, 0);
		map.getGame().logMessage("Вы решили #8#отдохнуть#^#!");
	}
	
	// Движение влево
	public void left() {
		if (move(0, -1)) setX(-1);		
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
	
	// Движение влево и вверх
	public void moveLeftUp() {
		if (move(-1, -1)) setXY(-1, -1);
	}

	// Движение влево и вниз
	public void moveLeftDown() {
		if (move(-1, +1)) setXY(-1, +1);		
	}

	// Движение вправо и вверх
	public void moveRightUp() {
		if (move(+1, -1)) setXY(+1, -1);	
	}

	// Движение вправо и вниз
	public void moveRightDown() {
		if (move(+1, +1)) setXY(+1, +1);
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
		ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), Itemset.TYPE_ANY, map.getGame().player.getInventory(), message);
		frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame2.setTitle("Что вы хотите идентифицировать?");
		frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
		frame2.toFront();
		frame2.setVisible(true);
		return false;
	}
	
	private boolean move(int x, int y) {
		return map.getGame().player.move(x, y);
	}
	
	private void setX(int x) {
		map.setCurrentX(map.getCurrentX() + x);
	}

	private void setY(int y) {
		map.setCurrentY(map.getCurrentY() + y);
	}

	private void setXY(int x, int y) {
		map.setCurrentX(map.getCurrentX() + x);
		map.setCurrentY(map.getCurrentY() + y);
	}

}