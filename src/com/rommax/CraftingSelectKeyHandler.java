package com.rommax;

import java.awt.event.*;
import java.util.*;


public class CraftingSelectKeyHandler implements KeyListener{

	CraftingSelectPanel craftSP;
	int itemID;

	public CraftingSelectKeyHandler(CraftingSelectPanel craftingSelectPanel){
		super();
		this.craftSP = craftingSelectPanel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();

		switch (keycode) {
			case KeyEvent.VK_1:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_MELEE_WEAPON_SWORD;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_2:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_ARMOR;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_3:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_POTION;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_4:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_SCROLL;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_5:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_CONTAINER;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_6:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_FOOD;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_7:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_MISC;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_0:
				if (craftSP.cWindow.isChangedFilter){
					craftSP.cWindow.type = ItemSet.TYPE_ANY;
					craftSP.resetState();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				craftSP.cWindow.stop();
				break;
			case KeyEvent.VK_UP:
				if (craftSP.current > 0){
					craftSP.current--;
					if (craftSP.current < craftSP.min){
						craftSP.min--;
						craftSP.max--;
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if (craftSP.current < craftSP.MAX_ITEMS - 1){
					craftSP.current++;
					if (craftSP.current > craftSP.max){
						craftSP.min++;
						craftSP.max++;
					}
				}
				break;
			case KeyEvent.VK_ENTER:
				itemID = (getItemNumber(craftSP.current, craftSP.cWindow.type));
				Item item = getItem(itemID);
				item.doCraft(item.getLoot(), itemID);
				craftSP.cWindow.stop();
				craftSP.repaint();
				craftSP.cWindow.game.refresh();
				break;
		}
		craftSP.repaint();
	}

	public int getItemNumber(int number, int type){
		LinkedList<Item> x = craftSP.cWindow.list;
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

	private Item getItem(int itemID)
	{
		LinkedList<Item> itemList = new LinkedList<>();
		for (int item = 0; item < ItemSet.MAX_ITEMS; item++)
			itemList.add(new Item(ItemSet.getItem(item)));
		//Item item = new Item(new BaseItem(itemList.get(itemID).getChance(), itemID, itemList.get(itemID).getLevel(), itemList.get(itemID).getSlot(), itemList.get(itemID).getType(), itemList.get(itemID).getName(), itemList.get(itemID).getPath(), itemList.get(itemID).getSize(), itemList.get(itemID).getMass(), itemList.get(itemID).getScript(), itemList.get(itemID).getDestroyable(), itemList.get(itemID).getLife(), itemList.get(itemID).getLoot()));
		Item item = itemList.get(itemID);
		return item;
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}