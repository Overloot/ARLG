package com.rommax;

public class BaseHero extends Entity{

	private String descr1, descr2, descr3;
	private int STR;
	private int AGI;
	private int END;
	private int INT;
	private int LUCK;

	public BaseHero(int id, String name, String path, String descr1, 
	String descr2, String descr3, int STR, int AGI, int END, int INT, int LUCK){
		super(id, name, path);
		this.descr1 = descr1;
		this.descr2 = descr2;
		this.descr3 = descr3;
		this.STR = STR;
		this.AGI = AGI;
		this.END = END;
		this.INT = INT;
		this.LUCK = LUCK;
	}
	
	public String getDescr1(){return descr1;}
	public String getDescr2(){return descr2;}
	public String getDescr3(){return descr3;}
	public int getSTR(){return STR;}
	public int getAGI(){return AGI;}
	public int getEND(){return END;}
	public int getINT(){return INT;}
	public int getLUCK(){return LUCK;}

}
