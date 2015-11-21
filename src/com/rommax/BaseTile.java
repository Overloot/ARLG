package com.rommax;

public class BaseTile extends GameObject{

	private boolean isPassable;
	private boolean isTransparent;
	private boolean isOpenable;
	private boolean isBloodable;

	public BaseTile(int id, String name, String path, boolean isPassable, boolean isTransparent, boolean isOpenable, boolean isBloodable, boolean destroyable, int maxHP, int loot){
		super(id, name, path, destroyable, maxHP, loot);
		this.isPassable = isPassable;		// проходимый или нет
		this.isTransparent = isTransparent; // видно сквозь или нет
		this.isOpenable = isOpenable;		// можно открыть или нет
		this.isBloodable = isBloodable;		// можно поставить кровь или нет
	}

	public boolean getPassable(){return isPassable;}
	public boolean getTransparent(){return isTransparent;}
	public boolean getOpenable(){return isOpenable;}
	public boolean getBloodable(){return isBloodable;}
	public void setPassable(boolean isPassable){this.isPassable = isPassable;}
	public void setTransparent(boolean isTransparent){this.isTransparent = isTransparent;}
	public void setOpenable(boolean isOpenable){this.isOpenable = isOpenable;}
	public void setBloodable(boolean isBloodable){this.isBloodable = isBloodable;}
}
