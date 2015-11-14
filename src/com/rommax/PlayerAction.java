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
	
	// Используем скилл
	public boolean useSkill(int n){
		boolean ret = false;
		if (Skill.getAmount() >= n) {
			n--;
			ret = true;
			if (Skill.getCooldown(n) > 0) {
				map.getGame().logMessage("У вас #2#нет сил#^# использовать навык #8#" + 
					SkillSet.getSkill(Skill.skill[n]).getName() + "#^#!");
				return false;
			}else{
				// Используем навык героя
				map.getGame().logMessage("Вы #4#использовали#^# навык #8#" + 
					SkillSet.getSkill(Skill.skill[n]).getName() + ".#^#");
				Skill.setCooldown(n, SkillSet.getSkill(Skill.skill[n]).getCooldown());
				map.getGame().player.setEffectFrom(ScriptParser.parseString(SkillSet.getSkill(Skill.skill[n]).getScript()), true);
			}
		} else map.getGame().logMessage("Вы #2#не владеете#^# таким навыком!");
		return ret;
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
	
	// Включить режим осмотра местности
	public boolean startLookMode() {
		KeyHandler.ly = map.getGame().player.getY();
		KeyHandler.lx = map.getGame().player.getX();
		map.field[KeyHandler.ly][KeyHandler.lx].setCursor(true);
		KeyHandler.LOOK_MODE = true;
		this.lookTo(0, 0);
		return false;
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
	public boolean upStair() {
		if (map.field[map.getGame().player.getY()][map.getGame().player.getX()].getID()!=Tileset.TILE_STAIR_UP){
			map.getGame().logMessage("Вы #2#не можете#^# подняться #8#вверх#^# здесь!#/#");
			return false;
		} else {
			map.getGame().switchMap(-1);
			return true;
		}
	}
	
	// Вниз по лестнице
	public boolean downStair() {
		if (map.field[map.getGame().player.getY()][map.getGame().player.getX()].getID()!=Tileset.TILE_STAIR_DOWN ){
			map.getGame().logMessage("Вы #2#не можете#^# спуститься #8#вниз#^# здесь!#/#");
			return false;
		} else {
			map.getGame().switchMap(+1);
			return true;
		}
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
	
	// Поднимаем что-то с земли
	public boolean pickupIt() {
		if (map.field[map.getGame().player.getY()][map.getGame().player.getX()].getItemList().size() == 0) {
			map.getGame().logMessage("На земле пусто, нечего взять!");
			mp.repaint();
			return false;
		}
		if (map.field[map.getGame().player.getY()][map.getGame().player.getX()].getItemList().size() == 1){
			message = new ItemSelectMessage();
			map.getGame().keyHandler.keyPressed(null);
			map.getGame().tryToPickupItem(map.field[map.getGame().player.getY()][map.getGame().player.getX()].getItemList(), message.number);
			return true;
		} else {
			map.getGame().frame1.setFocusable(false);
			map.getGame().frame1.setFocusableWindowState(false);
			message = new ItemSelectMessage();
			message.command = 'g';
			ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_ANY, map.field[map.getGame().player.getY()][map.getGame().player.getX()].getItemList(), message);
			frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame2.setTitle("Что вы хотите поднять?");
			frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
			frame2.toFront();
			frame2.setVisible(true);
			return false;
		}
	}
	
	// Бросаем что-то на землю
	public boolean dropIt() {
		if (map.getGame().player.getInventory().size() == 0) {
			map.getGame().logMessage("У вас пусто в инвентаре, нечего бросить!");
			mp.repaint();
			return false;
		} else {
			map.getGame().frame1.setFocusable(false);
			map.getGame().frame1.setFocusableWindowState(false);
			message = new ItemSelectMessage();
			message.command = 'd';
			ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_ANY, map.getGame().player.getInventory(), message);
			frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame2.setTitle("Что вы хотите бросить?");
			frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
			frame2.toFront();
			frame2.setVisible(true);
			return false;
		}
	}
	
	// Информация об игроке
	public boolean info() {
		map.getGame().frame1.setFocusable(false);
		map.getGame().frame1.setFocusableWindowState(false);
		DescWindow frame2 = new DescWindow(map.getGame(), map.getGame().player);
		frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
		frame2.toFront();
		frame2.setTitle("Информация об игроке");
		frame2.setVisible(true);
		return false;
	}

	// Пьем зелья
	public boolean quaffIt() {
		if (map.getGame().player.getInventory().size() == 0) {
			map.getGame().logMessage("У вас пусто в инвентаре, нечего выпить!");
			mp.repaint();
			return false;
		} else {
			map.getGame().frame1.setFocusable(false);
			map.getGame().frame1.setFocusableWindowState(false);
			message = new ItemSelectMessage();
			message.command = 'q';
			ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_POTION, map.getGame().player.getInventory(), message);
			frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame2.setTitle("Что вы хотите выпить?");
			frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
			frame2.toFront();
			frame2.setVisible(true);
			return false;
		}
	}
	
	// Читаем свитки
	public boolean readIt() {
		if (map.getGame().player.getInventory().size() == 0) {
			map.getGame().logMessage("У вас пусто в инвентаре, нечего прочитать!");
			mp.repaint();
			return false;
		} else {
			map.getGame().frame1.setFocusable(false);
			map.getGame().frame1.setFocusableWindowState(false);
			message = new ItemSelectMessage();
			message.command = 'r';
			ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_SCROLL, map.getGame().player.getInventory(), message);
			frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame2.setTitle("Что вы хотите прочитать?");
			frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
			frame2.toFront();
			frame2.setVisible(true);
			return false;
		}
	}
	
	// Едим
	public boolean eatIt() {
		if (map.getGame().player.getInventory().size() == 0) {
			map.getGame().logMessage("У вас пусто в инвентаре, нечего скушать!");
			mp.repaint();
			return false;
		} else {
			map.getGame().frame1.setFocusable(false);
			map.getGame().frame1.setFocusableWindowState(false);
			message = new ItemSelectMessage();
			message.command = 'e';
			ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_FOOD, map.getGame().player.getInventory(), message);
			frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame2.setTitle("Что вы хотите съесть?");
			frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
			frame2.toFront();
			frame2.setVisible(true);
			return false;
		}
	}
	
	// Одеваем персонажа
	public boolean wearIt() {
		if (map.getGame().player.getInventory().size() == 0) {
			map.getGame().logMessage("У вас пусто в инвентаре, нечего надеть!");
			mp.repaint();
			return false;
		} else {
			map.getGame().frame1.setFocusable(false);
			map.getGame().frame1.setFocusableWindowState(false);
			message = new ItemSelectMessage();
			message.command = 'w';
			ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_ANY, map.getGame().player.getInventory(), message);
			frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame2.setTitle("Что вы хотите надеть?" + message.command);
			frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
			frame2.toFront();
			frame2.setVisible(true);
			return false;
		}		
	}
	
	// Экипировка
	public boolean showEq() {
		map.getGame().frame1.setFocusable(false);
		map.getGame().frame1.setFocusableWindowState(false);
	 	EqWindow frame2 = new EqWindow(map.getGame(), map.getGame().player);
		frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
		frame2.toFront();
		frame2.setTitle("Экипировка игрока");
		frame2.setVisible(true);
		return false;
	}
	
	// Открываем инвентарь
	public boolean inventory() {
		if (map.getGame().player.getInventory().size() == 0) {
			map.getGame().logMessage("У вас пусто в инвентаре, нечего осмотреть!");
			mp.repaint();
			return false;
		} else {
			map.getGame().frame1.setFocusable(false);
			map.getGame().frame1.setFocusableWindowState(false);
			message = new ItemSelectMessage();
			message.command = 'i';
			ItemSelectWindow frame2 = new ItemSelectWindow(map.getGame(), ItemSet.TYPE_ANY, map.getGame().player.getInventory(), message);
			frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame2.setTitle("Что вы хотите осмотреть?");
			frame2.setLocation(map.getGame().frame1.WINDOW_WIDTH/2 - frame2.WINDOW_WIDTH/2, map.getGame().frame1.WINDOW_HEIGHT/2 - frame2.WINDOW_HEIGHT/2);
			frame2.toFront();
			frame2.setVisible(true);
			return false;
		}
	}
	
	private boolean move(int y, int x) {
		return map.getGame().player.move(y, x);
	}
	
	private void setX(int x) {
		map.setX(map.getX() + x);
	}

	private void setY(int y) {
		map.setY(map.getY() + y);
	}

	private void setYX(int y, int x) {
		map.setY(map.getY() + y);
		map.setX(map.getX() + x);

	}

}