package com.rommax;

import java.util.*;

public class Tile extends Entity{
	private boolean isVisible;
	private boolean isPassable;
	private boolean isTransparent;
	private boolean isSeen;
	private boolean isBlood;
	private boolean isOpenable;
	private boolean isOpened;
	public int lastseenID;
	private boolean isSelected;
	private Monster monster;
	private LinkedList<Item> itemlist;

	public void setVisible(boolean isVisible){this.isVisible = isVisible;}
	public void setCursor(boolean b){isSelected = b;}
	public boolean isSelected(){return isSelected;}
	public void setOpenable(boolean op){isOpenable = op;}
	public void setOpened(boolean op){isOpened = op;}
	public void setSeen(boolean isSeen){this.isSeen = isSeen;}
	public void setBlood(boolean isBlood){this.isBlood = isBlood;}
	public boolean getBlood(){return isBlood;}
	public boolean getOpened(){return isOpened;}
	public void setPassable(boolean isPassable){this.isPassable = isPassable;}
	public void setTransparent(boolean isTransparent){this.isTransparent = isTransparent;}
	public void setMonster(Monster monster){this.monster = monster;}
	public boolean getVisible(){return isVisible;}
	public boolean getPassable(){return isPassable;}
	public boolean getOpenable(){return isOpenable;}
	public boolean getSeen(){return isSeen;}
	public Monster getMonster(){return monster;}
	public void AddItem(Item x){itemlist.add(x);}
	public int getItemsQty(){return itemlist.size();}
	public LinkedList<Item> getItemList(){return itemlist;}
	public boolean getTransparent(){return isTransparent;}

	public Tile(int id, String name, String path, boolean isPassable, boolean isTransparent, boolean isOpenable){
		super(id, name, path);
		this.isPassable = isPassable;
		this.isTransparent = isTransparent;
		this.isVisible = false;
		this.isSeen = false;
		if (id==Tileset.TILE_OPENED_DOOR)
			this.isOpened = true;
		else
			this.isOpened = false;
		itemlist = new LinkedList<Item>();
		this.isOpenable = isOpenable;
		monster = null;
	}
}