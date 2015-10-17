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

		if (keycode == KeyEvent.VK_1)
		{
		if (ip.iwindow.isChangedFilter)
		{
			ip.iwindow.type = ItemSet.TYPE_MELEE_WEAPON;
			ip.resetState();
		}
		}
		else
		if (keycode == KeyEvent.VK_2)
		{
		if (ip.iwindow.isChangedFilter)
				{
					ip.iwindow.type = ItemSet.TYPE_ARMOR;
					ip.resetState();
				}
		}
		else
		if (keycode == KeyEvent.VK_3)
				{
				if (ip.iwindow.isChangedFilter)
						{
							ip.iwindow.type = ItemSet.TYPE_POTION;
							ip.resetState();
						}
				}
		else
		if (keycode == KeyEvent.VK_4)
				{
				if (ip.iwindow.isChangedFilter)
						{
							ip.iwindow.type = ItemSet.TYPE_SCROLL;
							ip.resetState();
						}
				}
		else
		if (keycode == KeyEvent.VK_6)
				{
				if (ip.iwindow.isChangedFilter)
						{
							ip.iwindow.type = ItemSet.TYPE_MISC;
							ip.resetState();
						}
				}
		else
		if (keycode == KeyEvent.VK_5)
						{
						if (ip.iwindow.isChangedFilter)
								{
									ip.iwindow.type = ItemSet.TYPE_CONTAINER;
									ip.resetState();
								}
						}
		else
		if (keycode == KeyEvent.VK_0)
				{
				if (ip.iwindow.isChangedFilter)
						{
							ip.iwindow.type = ItemSet.TYPE_ANY;
							ip.resetState();
						}
				}
		else
		if (keycode == KeyEvent.VK_ENTER){
			if (ip.iwindow.type != ItemSet.TYPE_ANY)
			ip.iwindow.message.number = (getItemNumber(ip.current, ip.iwindow.type));
			else
			ip.iwindow.message.number = ip.current;

			if (ip.iwindow.message.command == 'd'){
				ip.iwindow.game.tryToDropItem(ip.iwindow.game.player.getMap().field[ip.iwindow.game.player.getY()][ip.iwindow.game.player.getX()].getItemList(), ip.iwindow.message.number);
				if (ip.iwindow.list.size() == 0){
					ip.iwindow.message.command = '/';
					ip.iwindow.stop();
					return;
				}
				ip.resetState();
				ip.repaint();
			}
			else
		if (ip.iwindow.message.command == 'g'){
				ip.iwindow.game.tryToPickupItem(ip.iwindow.game.player.getMap().field[ip.iwindow.game.player.getY()][ip.iwindow.game.player.getX()].getItemList(), ip.iwindow.message.number);
				if (ip.iwindow.list.size() == 0){
					ip.iwindow.message.command = '/';
					ip.iwindow.stop();
					return;
				}
				ip.resetState();
				ip.repaint();
			}

			else
			ip.iwindow.stop();
			return;
		}
		else
		if (keycode == KeyEvent.VK_ESCAPE){
			ip.iwindow.message.command = '/';
			ip.iwindow.stop();
			return;
		}
		else
		if (keycode == KeyEvent.VK_UP){
		if (ip.current > 0){
			ip.current--;
			if (ip.current < ip.min){
				ip.min--;
				ip.max--;
			}
		}


		}
		else
		if (keycode == KeyEvent.VK_DOWN){
			if (ip.current < ip.MAX_ITEMS - 1){
				ip.current++;
				if (ip.current > ip.max){
					ip.min++;
					ip.max++;
				}
			}


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