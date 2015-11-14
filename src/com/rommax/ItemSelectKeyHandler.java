package com.rommax;

import java.awt.event.*;
import java.util.*;


public class ItemSelectKeyHandler implements KeyListener{

	ItemSelectPanel ip;

	public ItemSelectKeyHandler(ItemSelectPanel ip){
		super();
		this.ip = ip;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();

		switch (keycode) {
			case KeyEvent.VK_1:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_MELEE_WEAPON;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_2:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_ARMOR;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_3:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_POTION;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_4:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_SCROLL;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_5:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_CONTAINER;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_6:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_FOOD;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_7:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_MISC;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_0:
				if (ip.iwindow.isChangedFilter){
					ip.iwindow.type = ItemSet.TYPE_ANY;
					ip.resetState();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				ip.iwindow.message.command = '/';
				ip.iwindow.stop();
				break;
			case KeyEvent.VK_UP:
				if (ip.current > 0){
					ip.current--;
					if (ip.current < ip.min){
						ip.min--;
						ip.max--;
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if (ip.current < ip.MAX_ITEMS - 1){
					ip.current++;
					if (ip.current > ip.max){
						ip.min++;
						ip.max++;
					}
				}
				break;
			case KeyEvent.VK_ENTER:
				if (ip.iwindow.type != ItemSet.TYPE_ANY)
					ip.iwindow.message.number = (getItemNumber(ip.current, ip.iwindow.type));
						else ip.iwindow.message.number = ip.current;
				switch (ip.iwindow.message.command) {
					case 'b': // Идентификация предмета свитком
						ip.iwindow.game.tryToIdentifyItem(ip.iwindow.message.number);
						ip.iwindow.stop();
						break;
					case 'i': // Осмотр предмета в инвентаре
						ip.iwindow.game.tryToExamineItem(ip.iwindow.game.player.getInventory(), ip.iwindow.message.number);
						ip.repaint();
						break;
					case 'w': // Одеть экипировку
						ip.iwindow.game.tryToEquipItem(ip.iwindow.game.player.getInventory(), ip.iwindow.message.number);
						ip.iwindow.stop();
						ip.repaint();
						break;
					case 'e': // Съесть еду
						ip.iwindow.game.tryToEatItem(ip.iwindow.game.player.getInventory(), ip.iwindow.message.number);
						ip.iwindow.stop();
						ip.repaint();
						ip.iwindow.game.refresh();
						break;
					case 'r': // Прочитать свиток
						ip.iwindow.game.tryToReadItem(ip.iwindow.game.player.getInventory(), ip.iwindow.message.number);
						ip.iwindow.stop();
						ip.repaint();
						ip.iwindow.game.refresh();
						break;
					case 'q': // Выпить зелье
						ip.iwindow.game.tryToQuaffItem(ip.iwindow.game.player.getInventory(), ip.iwindow.message.number);
						ip.iwindow.stop();
						ip.repaint();
						ip.iwindow.game.refresh();
						break;
					case 'd': // Бросить предмет
						ip.iwindow.game.tryToDropItem(ip.iwindow.game.player.getMap().field[ip.iwindow.game.player.getY()][ip.iwindow.game.player.getX()].getItemList(), ip.iwindow.message.number);
						if (ip.iwindow.list.size() == 0){
							ip.iwindow.message.command = '/';
							ip.iwindow.stop();
							return;
						}
						ip.resetState();
						ip.repaint();
						break;
					case 'g': // Поднять предмет
						ip.iwindow.game.tryToPickupItem(ip.iwindow.game.player.getMap().field[ip.iwindow.game.player.getY()][ip.iwindow.game.player.getX()].getItemList(), ip.iwindow.message.number);
						if (ip.iwindow.list.size() == 0){
							ip.iwindow.message.command = '/';
							ip.iwindow.stop();
							return;
						}
						ip.resetState();
						ip.repaint();
						break;
					default:
						ip.iwindow.stop();
						break;
				}
				break;
		}
		ip.repaint();
	}

	public int getItemNumber(int number, int type){
		LinkedList<Item> x = ip.iwindow.list;
		ListIterator<Item> iter = x.listIterator();
		int cx = -1;
		int ix = -1;
		Item item;
		while(ix<number){
			item = iter.next();
			if (ItemSet.getItem(item.getID()).getType() == type)
				ix++;
			cx++;
		}
		return cx;
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}