package com.rommax;

import java.util.*;

public class Tile extends BaseTile{
	final public static int BLOOD_AMOUNT = 20;
	
	private boolean isVisible;
	private boolean isSeen;
	private int blood;
	private boolean isOpened;
	public int lastseenID;
	private boolean isSelected;
	private Monster monster;
	private LinkedList<Item> itemlist;

	public void setVisible(boolean isVisible){this.isVisible = isVisible;}
	public void setCursor(boolean b){isSelected = b;}
	public boolean isSelected(){return isSelected;}
	public void setOpened(boolean op){isOpened = op;}
	public void setSeen(boolean isSeen){this.isSeen = isSeen;}
	public void setBlood(int blood){this.blood = blood;}
	public int getBlood(){return blood;}
	public boolean getOpened(){return isOpened;}
	public void setMonster(Monster monster){this.monster = monster;}
	public boolean getVisible(){return isVisible;}
	public boolean getSeen(){return isSeen;}
	public Monster getMonster(){return monster;}
	public void AddItem(Item x){itemlist.add(x);}
	public int getItemsQty(){return itemlist.size();}
	public LinkedList<Item> getItemList(){return itemlist;}

	public Tile(int id, String name, String path, boolean isPassable, boolean isTransparent, boolean isOpenable, boolean isBloodable, boolean destroyable, int maxHP, String loot, String weaknessFor){
		super(id, name, path, isPassable, isTransparent, isOpenable, isBloodable, destroyable, maxHP, loot, weaknessFor);
		this.isVisible = false;
		this.isSeen = false;
		this.blood = 0;
		if (id==Tileset.TILE_OPENED_DOOR)
			this.isOpened = true;
		else
			this.isOpened = false;
		itemlist = new LinkedList<Item>();
		monster = null;
	}
}