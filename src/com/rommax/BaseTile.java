package com.rommax;

public class BaseTile extends GameObject{

	private boolean isPassable;
	private boolean isTransparent;
	private boolean isOpenable;
	private boolean isPlaceable;

	public BaseTile(int id, String name, String path, boolean isPassable, boolean isTransparent, boolean isOpenable, boolean isPlaceable, boolean destroyable, int maxHP, String loot, String weaknessFor){
		super(id, name, path, destroyable, maxHP, loot, weaknessFor);
		this.isPassable = isPassable;		// проходимый или нет
		this.isTransparent = isTransparent; // видно сквозь или нет
		this.isOpenable = isOpenable;		// можно открыть или нет
		this.isPlaceable = isPlaceable;		// можно поставить что-то или нет
	}

	public boolean getPassable(){return isPassable;}
	public boolean getTransparent(){return isTransparent;}
	public boolean getOpenable(){return isOpenable;}
	public boolean getPlaceable(){return isPlaceable;}
	public void setPassable(boolean isPassable){this.isPassable = isPassable;}
	public void setTransparent(boolean isTransparent){this.isTransparent = isTransparent;}
	public void setOpenable(boolean isOpenable){this.isOpenable = isOpenable;}
	public void setPlaceable(boolean isPlaceable){this.isPlaceable = isPlaceable;}
}
