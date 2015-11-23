package com.rommax;

public class BaseMonster extends GameObject{

	private Stat STR;
	private Stat AGI;
	private Stat END;
	private Stat LUCK;

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

	private Stat life;
	private Stat rad;
	private Stat AP;
	private int exp;

	public BaseMonster(int level, int id, String name, String path, int maxHP,
			int STR, int AGI, int END, int LUCK,
	        int DNormal1, int DNormal2, int RNormal,
	        int DFire1, int DFire2, int RFire,
	        int DCold1, int DCold2, int RCold,
	        int DElec1, int DElec2, int RElec,
	        int DPoison1, int DPoison2, int RPoison,
	        int AP, int rad, String loot)
	{
		super(id, name, path, level, loot);
		
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

		this.life = new Stat(maxHP, maxHP);
		this.rad = new Stat(rad, rad);
		this.AP = new Stat(AP, AP);
		this.exp = 0;
	}

	public Stat getHP(){return life;}
	public void setHP(int cur, int max){life = new Stat(cur, max);}
	public Stat getFOVRAD(){return rad;}
	public void setFOVRAD(int cur, int max){rad = new Stat(cur, max);}
	public Stat getAP(){return AP;};
	public void setAP(int cur, int max){AP = new Stat(cur, max);}
	public void setExp(int amount){exp = amount;}
	public int getExp(){return exp;}

	public Stat getSTR(){return STR;};
	public void setSTR(int cur, int max){STR = new Stat(cur, max);}
	public Stat getAGI(){return AGI;};
	public void setAGI(int cur, int max){AGI = new Stat(cur, max);}
	public Stat getEND(){return END;};
	public void setEND(int cur, int max){END = new Stat(cur, max);}
	public Stat getLUCK(){return LUCK;};
	public void setLUCK(int cur, int max){LUCK = new Stat(cur, max);}

	public Stat getRFire(){return RFire;};
	public void setRFire(int cur, int max){RFire = new Stat(cur, max);}
	public Stat getRCold(){return RCold;};
	public void setRCold(int cur, int max){RCold = new Stat(cur, max);}
	public Stat getRPoison(){return RPoison;};
	public void setRPoison(int cur, int max){RPoison = new Stat(cur, max);}
	public Stat getRElec(){return RElec;};
	public void setRElec(int cur, int max){RElec = new Stat(cur, max);}
	public Stat getRNormal(){return RNormal;};
	public void setRNormal(int cur, int max){RNormal = new Stat(cur, max);}

	public Stat getDFire(){return DFire;};
	public void setDFire(int cur, int max){DFire = new Stat(cur, max);}
	public Stat getDCold(){return DCold;};
	public void setDCold(int cur, int max){DCold = new Stat(cur, max);}
	public Stat getDPoison(){return DPoison;};
	public void setDPoison(int cur, int max){DPoison = new Stat(cur, max);}
	public Stat getDElec(){return DElec;};
	public void setDElec(int cur, int max){DElec = new Stat(cur, max);}
	public Stat getDNormal(){return DNormal;};
	public void setDNormal(int cur, int max){DNormal = new Stat(cur, max);}
}
