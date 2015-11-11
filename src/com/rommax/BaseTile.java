package com.rommax;

public class BaseTile extends Entity{

	private boolean isPassable;
	private boolean isTransparent;
	private boolean isOpenable;

	public BaseTile(int id, String name, String path, boolean isPassable, boolean isTransparent, boolean isOpenable){
		super(id, name, path);
		this.isPassable = isPassable;		// проходимый или нет
		this.isTransparent = isTransparent; // видно сквозь или нет
		this.isOpenable = isOpenable;		// можно открыть или нет
	}

	public boolean getPassable(){return isPassable;}
	public boolean getTransparent(){return isTransparent;}
	public boolean getOpenable(){return isOpenable;}
}
