package com.rommax;

public class BaseMonster{

	private int id;
	private String name;
	private String path;
	private Stat hp;
	private Stat fovrad;
	private Stat AP;

	private Stat STR;
	private Stat AGI;
	private Stat END;
	private Stat LUCK;
	private int level;

	private Stat RFire;
	private Stat RCold;
	private Stat RElec;
	private Stat RPoison;
	private Stat RNormal;

	private Stat DFire;
	private Stat DCold;
	private Stat DElec;
	private Stat DPoison;
	private Stat DNormal;


	public BaseMonster(int level, int id, String name, String path, int maxHP,
			int STR, int AGI, int END, int LUCK,
	        int DNormal1, int DNormal2, int RNormal,
	        int DFire1, int DFire2, int RFire,
	        int DCold1, int DCold2, int RCold,
	        int DElec1, int DElec2, int RElec,
	        int DPoison1, int DPoison2, int RPoison,
	        int AP, int fovrad)
	{
		this.id = id;
		this.level = level;
		this.path = path;
		this.name = name;
		hp = new Stat(maxHP, maxHP);
		//статы
		this.STR = new Stat(STR, STR);
		this.AGI = new Stat(AGI, AGI);
		this.END = new Stat(END, END);
		this.LUCK = new Stat(LUCK, LUCK);
		//резисты
		this.RFire = new Stat(RFire, RFire);
		this.RCold = new Stat(RCold, RCold);
		this.RPoison = new Stat(RPoison, RPoison);
		this.RElec = new Stat(RElec, RElec);
		this.RNormal = new Stat(RNormal, RNormal);
		//уроны
		this.DFire = new Stat(DFire1, DFire2);
		this.DPoison = new Stat(DPoison1, DPoison2);
		this.DCold = new Stat(DCold1, DCold2);
		this.DElec = new Stat(DElec1, DElec2);
		this.DNormal = new Stat(DNormal1, DNormal2);

		this.AP = new Stat(AP, AP);
		this.fovrad = new Stat(fovrad, fovrad);
	}

	public Stat getHP(){return hp;}
	public Stat getFOVRAD(){return fovrad;}
	public String getName(){return name;}
	public int getID(){return id;}
	public String getPath(){return path;};

	public Stat getRFire(){return RFire;};
	public Stat getRCold(){return RCold;};
	public Stat getRPoison(){return RPoison;};
	public Stat getRElec(){return RElec;};
	public Stat getRNormal(){return RNormal;};

	public Stat getDFire(){return DFire;};
	public Stat getDCold(){return DCold;};
	public Stat getDPoison(){return DPoison;};
	public Stat getDElec(){return DElec;};
	public Stat getDNormal(){return DNormal;};

	public Stat getSTR(){return STR;};
	public Stat getAGI(){return AGI;};
	public Stat getEND(){return END;};
	public Stat getLUCK(){return LUCK;};
	public int getLevel(){return level;};

	public Stat getAP(){return AP;};




}
