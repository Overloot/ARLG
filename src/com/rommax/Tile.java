package com.rommax;

import java.util.*;

public class Tile extends BaseTile{

	public static final int NONE = -1;

	final public static int BLOOD_AMOUNT = 20;
	
	private boolean isVisible;
	private boolean isSeen;
	private int blood;
	private int trap;
	private boolean isTraped;
	private boolean isOpened;
	private int chest;
	public int lastseenID;
	private boolean isSelected;
	private Monster monster;
	private LinkedList<Item> itemlist;

	public void setChest(int ch){chest = ch;}
	public int getChest(){return chest;}
	public void setTraped(boolean tr){isTraped = tr;}
	public boolean getTraped(){return isTraped;}
	public void setVisible(boolean isVisible){this.isVisible = isVisible;}
	public void setCursor(boolean b){isSelected = b;}
	public boolean isSelected(){return isSelected;}
	public void setSeen(boolean isSeen){this.isSeen = isSeen;}
	public void setBlood(int blood){this.blood = blood;}
	public int getBlood(){return blood;}
	public void setTrap(int trap){this.trap = trap;}
	public int getTrap(){return trap;}
	public void setOpened(boolean op){isOpened = op;}
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
		this.isTraped = false;		
		this.chest = NONE;		
		this.trap = NONE;
		this.blood = 0;
		if (id==Tileset.TILE_OPENED_DOOR)
			this.isOpened = true;
		else
			this.isOpened = false;
		itemlist = new LinkedList<Item>();
		monster = null;
	}
}