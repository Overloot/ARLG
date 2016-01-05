package com.rommax;

import java.awt.event.*;
import java.util.*;


public class CraftingSelectKeyHandler implements KeyListener{

	CraftingSelectPanel craftingSP;

	public CraftingSelectKeyHandler(CraftingSelectPanel craftingSelectPanel){
		super();
		this.craftingSP = craftingSelectPanel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();

		switch (keycode) {
			case KeyEvent.VK_1:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_MELEE_WEAPON_SWORD;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_2:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_ARMOR;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_3:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_POTION;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_4:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_SCROLL;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_5:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_CONTAINER;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_6:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_FOOD;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_7:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_MISC;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_0:
				if (craftingSP.cWindow.isChangedFilter){
					craftingSP.cWindow.type = ItemSet.TYPE_ANY;
					craftingSP.resetState();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				craftingSP.cWindow.message.command = '/';
				craftingSP.cWindow.stop();
				break;
			case KeyEvent.VK_UP:
				if (craftingSP.current > 0){
					craftingSP.current--;
					if (craftingSP.current < craftingSP.min){
						craftingSP.min--;
						craftingSP.max--;
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if (craftingSP.current < craftingSP.MAX_ITEMS - 1){
					craftingSP.current++;
					if (craftingSP.current > craftingSP.max){
						craftingSP.min++;
						craftingSP.max++;
					}
				}
				break;
			case KeyEvent.VK_ENTER:
				if (craftingSP.cWindow.type != ItemSet.TYPE_ANY)
					craftingSP.cWindow.message.number = (getItemNumber(craftingSP.current, craftingSP.cWindow.type));
				else craftingSP.cWindow.message.number = craftingSP.current;
				switch (craftingSP.cWindow.message.command) {
					case 'K': // пусть будет так
						int x = craftingSP.cWindow.game.player.getX();
						int y = craftingSP.cWindow.game.player.getY();
						int id = craftingSP.cWindow.message.number;
                        String loot = craftingSP.cWindow.list.get(id).getLoot();
                        craftingSP.cWindow.game.player.doCraft(loot, id);
						craftingSP.cWindow.stop();
						break;
					default:
						craftingSP.cWindow.stop();
						break;
				}
				break;
		}
		craftingSP.repaint();
	}

	public int getItemNumber(int number, int type){
		LinkedList<Item> x = craftingSP.cWindow.list;
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