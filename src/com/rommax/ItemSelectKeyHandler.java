package com.rommax;

import java.awt.event.*;
import java.util.*;


public class ItemSelectKeyHandler implements KeyListener{

	ItemSelectPanel itemSP;

	public ItemSelectKeyHandler(ItemSelectPanel itemSelectPanel){
		super();
		this.itemSP = itemSelectPanel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();

		switch (keycode) {
			case KeyEvent.VK_1:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_MELEE_WEAPON_SWORD;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_2:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_ARMOR;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_3:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_POTION;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_4:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_SCROLL;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_5:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_CONTAINER;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_6:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_FOOD;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_7:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_MISC;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_0:
				if (itemSP.iwindow.isChangedFilter){
					itemSP.iwindow.type = ItemSet.TYPE_ANY;
					itemSP.resetState();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				itemSP.iwindow.message.command = '/';
				itemSP.iwindow.stop();
				break;
			case KeyEvent.VK_UP:
				if (itemSP.current > 0){
					itemSP.current--;
					if (itemSP.current < itemSP.min){
						itemSP.min--;
						itemSP.max--;
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if (itemSP.current < itemSP.MAX_ITEMS - 1){
					itemSP.current++;
					if (itemSP.current > itemSP.max){
						itemSP.min++;
						itemSP.max++;
					}
				}
				break;
			case KeyEvent.VK_ENTER:
				if (itemSP.iwindow.type != ItemSet.TYPE_ANY)
					itemSP.iwindow.message.number = (getItemNumber(itemSP.current, itemSP.iwindow.type));
						else itemSP.iwindow.message.number = itemSP.current;
				switch (itemSP.iwindow.message.command) {
					case 'b': // Идентификация предмета свитком
						itemSP.iwindow.game.player.identifyItem(itemSP.iwindow.message.number);
						itemSP.iwindow.stop();
						break;
					case 'i': // Осмотр предмета в инвентаре
						itemSP.iwindow.game.player.examineItem(itemSP.iwindow.game.player.getInventory(), itemSP.iwindow.message.number);
						itemSP.repaint();
						break;
					case 'w': // Одеть экипировку
						itemSP.iwindow.game.player.equipItem(itemSP.iwindow.game.player.getInventory(), itemSP.iwindow.message.number);
						itemSP.iwindow.stop();
						itemSP.repaint();
						break;
					case 'e': // Съесть еду
						itemSP.iwindow.game.player.eatItem(itemSP.iwindow.game.player.getInventory(), itemSP.iwindow.message.number);
						itemSP.iwindow.stop();
						itemSP.repaint();
						itemSP.iwindow.game.refresh();
						break;
					case 'r': // Прочитать свиток
						itemSP.iwindow.game.player.readItem(itemSP.iwindow.game.player.getInventory(), itemSP.iwindow.message.number);
						itemSP.iwindow.stop();
						itemSP.repaint();
						itemSP.iwindow.game.refresh();
						break;
					case 'q': // Выпить зелье
						itemSP.iwindow.game.player.quaffItem(itemSP.iwindow.game.player.getInventory(), itemSP.iwindow.message.number);
						itemSP.iwindow.stop();
						itemSP.repaint();
						itemSP.iwindow.game.refresh();
						break;
					case 'd': // Бросить предмет
						itemSP.iwindow.game.player.dropItem(itemSP.iwindow.game.player.getMap().field[itemSP.iwindow.game.player.getY()][itemSP.iwindow.game.player.getX()].getItemList(), itemSP.iwindow.message.number);
						if (itemSP.iwindow.list.size() == 0){
							itemSP.iwindow.message.command = '/';
							itemSP.iwindow.stop();
							return;
						}
						itemSP.resetState();
						itemSP.repaint();
						break;
					case 't': // Поднять предмет
						itemSP.iwindow.game.player.pickupItem(itemSP.iwindow.game.player.getMap().field[itemSP.iwindow.game.player.getY()][itemSP.iwindow.game.player.getX()].getItemList(), itemSP.iwindow.message.number);
						if (itemSP.iwindow.list.size() == 0){
							itemSP.iwindow.message.command = '/';
							itemSP.iwindow.stop();
							return;
						}
						itemSP.resetState();
						itemSP.repaint();
						break;
					case 'g': // разбираем предмет
						itemSP.iwindow.game.player.disassembledItem(itemSP.iwindow.message.number);
						itemSP.iwindow.stop();
						itemSP.repaint();
						itemSP.iwindow.game.refresh();
						break;
					default:
						itemSP.iwindow.stop();
						break;
				}
				break;
		}
		itemSP.repaint();
	}

	public int getItemNumber(int number, int type){
		LinkedList<Item> x = itemSP.iwindow.list;
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