package com.rommax;

import java.util.*;

public class Monster extends Entity{
	private Stat hp;
	private Stat addHP;
	private Stat AP;
	private Stat fovrad;

	private LinkedList<Item> Inventory;
	public Item[] Equipment;

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
	
	private Stat currentWeight;
	private Stat currentSize;
	private int exp;
	private int paralyzecount;
	private int poisoncount;
	public LinkedList<Item> getInventory(){return Inventory;}
	public Stat getHP(){return hp;}
	public Stat getFOVRAD(){return fovrad;}
	public Stat getCurrentWeight(){return currentWeight;}
	public Stat getCurrentSize(){return currentSize;}
	public void setCurrentWeight(Stat currentWeight){this.currentWeight = currentWeight;}
	public void setCurrentSize(Stat currentSize){this.currentSize = currentSize;}
	public void setAddHP(int HP){this.addHP = new Stat(HP, HP);}
	public Stat getAddHP(){return addHP;}
	public int getPoisonCount(){return poisoncount;}
	public int getparalyzecount(){return paralyzecount;}
	public void setparalyzecount(int pc){paralyzecount = pc;}
	public void setPoisonCount(int pc){poisoncount = pc;}
	
	public Stat getRFire(){return RFire;}
	public Stat getRCold(){return RCold;}
	public Stat getRPoison(){return RPoison;}
	public Stat getRElec(){return RElec;}
	public Stat getRNormal(){return RNormal;}
	
	public Stat getDFire(){return DFire;}
	public Stat getDCold(){return DCold;}
	public Stat getDPoison(){return DPoison;}
	public Stat getDElec(){return DElec;}
	public Stat getDNormal(){return DNormal;}
	
	public Stat getSTR(){return STR;}
	public Stat getAGI(){return AGI;}
	public Stat getEND(){return END;}
	public Stat getLUCK(){return LUCK;}
	
	public Stat getAP(){return AP;}
	public void setXP(int amount){exp = amount;}
	public int getXP(){return exp;}
	
	public void setSTR(int STR){this.STR = new Stat(STR, STR);}
	public void setAGI(int AGI){this.AGI = new Stat(AGI, AGI);}
	public void setEND(int END){this.END = new Stat(END, END);}
	public void setLUCK(int LUCK){this.LUCK = new Stat(LUCK, LUCK);}

	public Monster(BaseMonster bm, int y, int x, Map map, Game game){
		super(bm.getID(), bm.getName(), y, x, 0, 0, map, game, bm.getLevel());
		this.exp = 0;
		this.getMap().placeMonsterAt(y, x, this);
		this.addHP = new Stat(0, 0);
		this.hp = new Stat(bm.getHP().getCurrent(), bm.getHP().getMax());
		this.fovrad = new Stat(bm.getFOVRAD().getCurrent(), bm.getFOVRAD().getMax());
		
		this.STR = new Stat(bm.getSTR().getCurrent(), bm.getSTR().getMax());
		this.AGI = new Stat(bm.getAGI().getCurrent(), bm.getAGI().getMax());
		this.END = new Stat(bm.getEND().getCurrent(), bm.getEND().getMax());
		this.LUCK = new Stat(bm.getLUCK().getCurrent(), bm.getLUCK().getMax());

		this.RFire = new Stat(bm.getRFire().getCurrent(), bm.getRFire().getMax());
		this.RCold = new Stat(bm.getRCold().getCurrent(), bm.getRCold().getMax());
		this.RElec = new Stat(bm.getRElec().getCurrent(), bm.getRElec().getMax());
		this.RPoison = new Stat(bm.getRPoison().getCurrent(), bm.getRPoison().getMax());
		this.RNormal = new Stat(bm.getRNormal().getCurrent(), bm.getRNormal().getMax());

		this.DFire = new Stat(bm.getDFire().getCurrent(), bm.getDFire().getMax());
		this.DCold = new Stat(bm.getDCold().getCurrent(), bm.getDCold().getMax());
		this.DElec = new Stat(bm.getDElec().getCurrent(), bm.getDElec().getMax());
		this.DPoison = new Stat(bm.getDPoison().getCurrent(), bm.getDPoison().getMax());
		this.DNormal = new Stat(bm.getDNormal().getCurrent(), bm.getDNormal().getMax());
		
		this.AP = new Stat(bm.getAP().getCurrent(), bm.getAP().getMax());
		this.currentWeight = new Stat(0, getGame().CARRYING_PER_STRENGTH * STR.getCurrent());
		this.currentSize = new Stat(0, getGame().MIN_SIZE);

		Equipment = new Item[ItemSet.MAX_SLOTS];
		Inventory = new LinkedList<Item>();
	}

	public boolean move(int y, int x){
		if (currentWeight.getCurrent() > currentWeight.getMax()){
			getGame().logMessage("Вы #2#перегружены!#^#");
			return false;
		}
		if (currentSize.getCurrent() > currentSize.getMax()){
			getGame().logMessage("Вы несете #2#слишком много вещей!#^#");
			return false;
		}
		if (this == getGame().player) Skill.update();
		int ny = (getY() + y);
		int nx = (getX() + x);
		if (getMap().hasTileAt(ny, nx)){
			if (getMap().field[ny][nx].getMonster()!=null){
				AttackMonster(getMap().field[ny][nx].getMonster());
				}
			else
			if (!getMap().field[ny][nx].getPassable() && getMap().field[ny][nx].getOpenable() && !getMap().field[ny][nx].getOpened()){
				boolean m = false;
				if (this==getGame().player) m = true;
				getGame().tryToOpenSomething(m, ny, nx, true);
			}
			else
			if (getMap().field[ny][nx].getPassable()){
				getMap().placeMonsterAt(getY(), getX(), null);
				setY(ny);
				setX(nx);
				getMap().placeMonsterAt(ny, nx, this);
				if (this == getGame().player){
				if (getMap().field[ny][nx].getItemList().size()>0)
					if (getMap().field[ny][nx].getItemList().size()>=2)
					getGame().logMessage("Здесь лежит #7#много вещей!#^#");
					else
					getGame().logMessage("Здесь лежит #7#" + getMap().field[ny][nx].getItemList().get(0).getName().toLowerCase() + ".#^#");
				}
				getGame().frame1.mainpanel.repaint();
				return true;
			}
	    }
		return false;
	}

	private void checkChanges(boolean b, Stat stat, String s1, String s2) {
		if (b)
			if (stat.getCurrent() < 0) getGame().logMessage(s1);
				else if (stat.getCurrent() > 0) getGame().logMessage(s2);
	}

	public void setEffectFrom(ScriptObject so, boolean b){
		getHP().setMax(getHP().getMax() + getGame().HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() + getGame().HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setMax(getHP().getMax() + getGame().HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() + getGame().HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getCurrentWeight().setMax(getCurrentWeight().getMax() + getGame().CARRYING_PER_STRENGTH * so.STR_UP.getCurrent());

		if (so.IDENTIFY) getGame().frame1.mainpanel.listener.ID_MODE = true;

		checkChanges(b, so.STR_UP, "Вы почувствовали себя #3#сильнее!#^#", "Вы почувствовали себя #2#слабее!#^#");
		STR.add(so.STR_UP);

		checkChanges(b, so.AGI_UP, "Вы стали #3#более ловким!#^#", "Вы почувствовали себя #2#более неуклюже!#^#");
		AGI.add(so.AGI_UP);

		checkChanges(b, so.END_UP, "Вы почувствовали, что стали #3#более выносливым!#^#", "Вы почувствовали, что стали #2#менее выносливым!#^#");
		END.add(so.END_UP);

		checkChanges(b, so.LUCK_UP, "Вы почувствовали себя #3#удачливее!#^#", "Вы почувствовали себя #2#менее удачливее!#^#");
		LUCK.add(so.LUCK_UP);

		
		
		
		
		if (b)
		if (so.DFIRE.getCurrent()>0) getGame().logMessage("Вы почувствовали силу #2#огня!#^#");
		else
		if (so.DFIRE.getCurrent()<0) getGame().logMessage("Вы стали хуже чувствовать силу #2#огня!#^#");

		DFire.setCurrent(DFire.getCurrent() + so.DFIRE.getCurrent());
		DFire.setMax(DFire.getMax() + so.DFIRE.getMax());

		if (b)
		if (so.DCOLD.getCurrent()>0) getGame().logMessage("Вы почувствовали силу #4#холода!#^#");
		else
		if (so.DCOLD.getCurrent()<0) getGame().logMessage("Вы стали хуже чувствовать силу #4#холода!#^#");

		DCold.setCurrent(DCold.getCurrent() + so.DCOLD.getCurrent());
		DCold.setMax(DCold.getMax() + so.DCOLD.getMax());

		if (b)
		if (so.DPOISON.getCurrent()>0) getGame().logMessage("Вы почувствовали силу #3#яда!#^#");
		else
		if (so.DPOISON.getCurrent()<0) getGame().logMessage("Вы стали хуже чувствовать силу #3#яда!#^#");

		DPoison.setCurrent(DPoison.getCurrent() + so.DPOISON.getCurrent());
		DPoison.setMax(DPoison.getMax() + so.DPOISON.getMax());

		if (b)
		if (so.DELEC.getCurrent()>0) getGame().logMessage("Вы почувствовали силу #5#электричества!#^#");
		else
		if (so.DELEC.getCurrent()<0) getGame().logMessage("Вы стали хуже чувствовать силу #5#электричества!#^#");

		DElec.setCurrent(DElec.getCurrent() + so.DELEC.getCurrent());
		DElec.setMax(DElec.getMax() + so.DELEC.getMax());
		
		DNormal.setCurrent(DNormal.getCurrent() + so.DNORMAL.getCurrent());
		DNormal.setMax(DNormal.getMax() + so.DNORMAL.getMax());
		
		if (b)
		if (so.RFIRE.getCurrent()>0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #2#огню!#^#");
		else
		if (so.RFIRE.getCurrent()<0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #2#огню!#^#");

		RFire.add(so.RFIRE);

		if (b)
		if (so.RCOLD.getCurrent()>0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #4#холоду!#^#");
		else
		if (so.RCOLD.getCurrent()<0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #4#холоду!#^#");

		RCold.add(so.RCOLD);

		if (b)
		if (so.RPOISON.getCurrent()>0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #3#яду!#^#");
		else
		if (so.RPOISON.getCurrent()<0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #3#яду!#^#");

		RPoison.add(so.RPOISON);

		if (b)
		if (so.RNORMAL.getCurrent()>0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #8#ударам!#^#");
		else
		if (so.RNORMAL.getCurrent()<0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #8#ударам!#^#");

		RNormal.add(so.RNORMAL);

		if (b)
		if (so.RELEC.getCurrent()>0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #5#электричеству!#^#");
		else
		if (so.RELEC.getCurrent()<0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #5#электричеству!#^#");

		RElec.add(so.RELEC);
		
		// Медленное исцеление
		if (b) if (so.HEALTIME.getCurrent()>0) getGame().logMessage("Вы #3#исцеляетесь!#^#");
			else if (so.HEALTIME.getCurrent()<0) getGame().logMessage("Вы #2#корчитесь от боли!#^#");

		addHP.add(so.HEALTIME);

		// Молниеносное исцеление
		if (b) if (so.HEALSELF.getCurrent()>0) getGame().logMessage("Вы #3#исцеляетесь!#^#");
			else if (so.HEALSELF.getCurrent()<0) getGame().logMessage("Вы #2#корчитесь от боли!#^#");
		
		hp.setCurrent(hp.getCurrent() + so.HEALSELF.getCurrent());
		
		// Радиус обзора
		if (b) if (so.FOVRAD.getCurrent()>0) getGame().logMessage("Вы стали #8#лучше#^# видеть!");
			else if (so.FOVRAD.getCurrent()<0) getGame().logMessage("Вы стали #2#хуже#^# видеть!");
		
		fovrad.add(so.FOVRAD);

		if (b)
		if (so.SW_UP.getCurrent()>0) getGame().logMessage("Теперь вы можете нести #3#больше#^# вещей!");
		else
		if (so.SW_UP.getCurrent()<0) getGame().logMessage("Теперь вы можете нести #2#меньше#^# вещей!");
		
		currentSize.setMax(currentSize.getMax() + so.SW_UP.getCurrent());
		
		if (b)
		if (so.HEALPOISON.getCurrent()>0) getGame().logMessage("Вы исцеляетесь от #3#яда!#^#");

		if (so.TELEPORT) {
			Random random = new Random();
			getGame().logMessage("Вы внезапно телепортировались!");
			int ny = random.nextInt(getMap().getHeight());
			int nx = random.nextInt(getMap().getWidth());
			while (getMap().field[ny][nx].getMonster()!=null || !getMap().field[ny][nx].getPassable()){
			 ny = random.nextInt(getMap().getHeight());
			 nx = random.nextInt(getMap().getWidth());
			}
			getMap().field[getY()][getX()].setMonster(null);
			setY(ny);
			setX(nx);
			getMap().field[getY()][getX()].setMonster(this);
			getGame().getMap().setX(getX() - ((GameWindow.getScreenTileSizeX() * Tileset.TILE_SIZE)/ (Tileset.TILE_SIZE * 2)));
			getGame().getMap().setY(getY() - ((GameWindow.getScreenTileSizeY() * Tileset.TILE_SIZE)/ (Tileset.TILE_SIZE * 2)));
		}

		if (so.HEALPOISON.getCurrent() > 0)
			setPoisonCount(getPoisonCount() - so.HEALPOISON.getCurrent());
		if (so.POISONCOUNT.getCurrent() != 0)
		setPoisonCount(getPoisonCount() + so.POISONCOUNT.getCurrent() + 1);
		if (so.PARALYZECOUNT.getCurrent() != 0)

		setparalyzecount(getparalyzecount() + so.PARALYZECOUNT.getCurrent() + 1);
		if (getPoisonCount()<0) setPoisonCount(0);
	}

	public void deleteEffectFrom(ScriptObject so, boolean b){
		getHP().setMax(getHP().getMax() - getGame().HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() - getGame().HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setMax(getHP().getMax() - getGame().HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() - getGame().HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getCurrentWeight().setMax(getCurrentWeight().getMax() - getGame().CARRYING_PER_STRENGTH * so.STR_UP.getCurrent());

		checkChanges(b, so.STR_UP, "Вы почувствовали себя #3#сильнее!#^#", "Вы почувствовали себя #2#слабее!#^#");
		STR.sub(so.STR_UP);

		checkChanges(b, so.AGI_UP, "Вы стали #3#более ловким!#^#", "Вы почувствовали себя #2#более неуклюже!#^#");
		AGI.sub(so.AGI_UP);

		checkChanges(b, so.END_UP, "Вы почувствовали, что стали #3#более выносливым!#^#", "Вы почувствовали, что стали #2#менее выносливым!#^#");
		END.sub(so.END_UP);

		checkChanges(b, so.LUCK_UP, "Вы почувствовали себя #3#удачливее!#^#", "Вы почувствовали себя #2#менее удачливее!#^#");
		LUCK.sub(so.LUCK_UP);

		checkChanges(b, so.DFIRE, "Вы почувствовали силу #2#огня!#^#", "Вы стали хуже чувствовать силу #2#огня!#^#");
		DFire.setCurrent(DFire.getCurrent() - so.DFIRE.getCurrent());
		DFire.setMax(DFire.getMax() - so.DFIRE.getMax());
		
		if (b)
		if (so.DCOLD.getCurrent()<0) getGame().logMessage("Вы почувствовали силу #4#холода!#^#");
		else
		if (so.DCOLD.getCurrent()>0) getGame().logMessage("Вы стали хуже чувствовать силу #4#холода!#^#");

		DCold.setCurrent(DCold.getCurrent() - so.DCOLD.getCurrent());
		DCold.setMax(DCold.getMax() - so.DCOLD.getMax());
		
		if (b)
		if (so.DCOLD.getCurrent()<0) getGame().logMessage("Вы почувствовали силу #3#яда!#^#");
		else
		if (so.DCOLD.getCurrent()>0) getGame().logMessage("Вы стали хуже чувствовать силу #3#яда!#^#");

		DPoison.setCurrent(DPoison.getCurrent() - so.DPOISON.getCurrent());
		DPoison.setMax(DPoison.getMax() - so.DPOISON.getMax());
		
		if (b)
		if (so.DELEC.getCurrent()<0) getGame().logMessage("Вы почувствовали силу #5#электричества!#^#");
		else
		if (so.DELEC.getCurrent()>0) getGame().logMessage("Вы стали хуже чувствовать силу #5#электричества!#^#");

		DElec.setCurrent(DElec.getCurrent() - so.DELEC.getCurrent());
		DElec.setMax(DElec.getMax() - so.DELEC.getMax());
		
		DNormal.setCurrent(DNormal.getCurrent() - so.DNORMAL.getCurrent());
		DNormal.setMax(DNormal.getMax() - so.DNORMAL.getMax());
		
		if (b)
		if (so.RFIRE.getCurrent()<0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #2#огню!#^#");
		else
		if (so.RFIRE.getCurrent()>0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #2#огню!#^#");

		RFire.sub(so.RFIRE);
		
		if (b)
		if (so.RCOLD.getCurrent()<0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #4#холоду!#^#");
		else
		if (so.RCOLD.getCurrent()>0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #4#холоду!#^#");

		RCold.sub(so.RCOLD);
		
		if (b)
		if (so.RPOISON.getCurrent()<0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #3#яду!#^#");
		else
		if (so.RPOISON.getCurrent()>0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #3#яду!#^#");

		RPoison.sub(so.RPOISON);
		
		if (b)
		if (so.RNORMAL.getCurrent()<0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #8#ударам!#^#");
		else
		if (so.RNORMAL.getCurrent()>0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #8#ударам!#^#");

		RNormal.sub(so.RNORMAL);
		
		if (b)
		if (so.RELEC.getCurrent()<0) getGame().logMessage("Вы стали #8#менее#^# восприимчивы к #5#электричеству!#^#");
		else
		if (so.RELEC.getCurrent()>0) getGame().logMessage("Вы стали #2#более#^# восприимчивы к #5#электричеству!#^#");
		
		RElec.sub(so.RELEC);

		if (b) if (so.HEALTIME.getCurrent()>0) getGame().logMessage("Вы #3#исцеляетесь!#^#");
			else if (so.HEALTIME.getCurrent()<0) getGame().logMessage("Вы #2#корчитесь от боли!#^#");
		
		addHP.sub(so.HEALTIME);

		if (b) if (so.HEALSELF.getCurrent()<0) getGame().logMessage("Вы #3#исцеляетесь!#^#");
			else if (so.HEALSELF.getCurrent()>0) getGame().logMessage("Вы #2#корчитесь от боли!#^#");
		
		hp.setCurrent(hp.getCurrent() - so.HEALSELF.getCurrent());

		if (b) if (so.FOVRAD.getCurrent()<0) getGame().logMessage("Вы стали #8#лучше#^# видеть!");
			else if (so.FOVRAD.getCurrent()>0) getGame().logMessage("Вы стали #2#хуже#^# видеть!");
		
		fovrad.sub(so.FOVRAD);

		if (b)
		if (so.SW_UP.getCurrent()<0) getGame().logMessage("Теперь вы можете нести #3#больше#^# вещей!");
		else
		if (so.SW_UP.getCurrent()>0) getGame().logMessage("Теперь вы можете нести #2#меньше#^# вещей!");
		
		currentSize.setMax(currentSize.getMax() - so.SW_UP.getCurrent());

		if (getPoisonCount() < 0) setPoisonCount(0);
	}

	private void AttackMonster(Monster enemy){
		if (this == enemy) return;
		if (this != getGame().player && enemy != getGame().player) return;
		Random rand = new Random();

		int ndamage = ((100 - enemy.RNormal.getCurrent()) * (getGame().getValueFrom(DNormal.getCurrent(), DNormal.getMax()) + getGame().rand(STR.getCurrent())) / 100 ) ;
		
		int fdamage = ((100 - enemy.RFire.getCurrent()) * getGame().getValueFrom(DFire.getCurrent(), DFire.getMax()) / 100) ;
		int cdamage = ((100 - enemy.RCold.getCurrent()) * getGame().getValueFrom(DCold.getCurrent(), DCold.getMax()) / 100 );
		int edamage = ((100 - enemy.RElec.getCurrent()) * getGame().getValueFrom(DElec.getCurrent(), DElec.getMax()) / 100 );
		int pdamage = ((100 - enemy.RPoison.getCurrent()) * getGame().getValueFrom(DPoison.getCurrent(), DPoison.getMax()) / 100);
		
		int damage = (ndamage + fdamage + cdamage);
		int pd = enemy.hp.getCurrent();
		//String dlog = "ловкость атакующего " + AGI.getCurrent() + "| ловкость жертвы" + enemy.AGI.getCurrent();
		int min = rand.nextInt(AGI.getCurrent());
		int max = rand.nextInt(enemy.AGI.getCurrent());
		//dlog+=" выпало" + min + " против " + max;
		//getGame().logMessage(dlog);

		if (min>=max){
			//if (getGame().dice(LUCK.getCurrent() * 100,100000)){
			if (getGame().dice(LUCK.getCurrent(), 100)){
				damage += (rand.nextInt(ndamage) + 1);
				if (enemy == getGame().player)
					getGame().logMessage(this.getName().toLowerCase() + "  #8#критически#^# бьет вас!");
						else if (this == getGame().player)
							getGame().logMessage("Вы наносите #3#критический#^# удар!");
			}
					
					enemy.hp.setCurrent(pd - damage);
					if (pdamage>0) enemy.poisoncount += pdamage;
					if (edamage>0) enemy.paralyzecount += edamage + 1;
					if (pdamage<0) enemy.getHP().setCurrent(enemy.getHP().getCurrent() - pdamage);
					if (edamage<0) enemy.getHP().setCurrent(enemy.getHP().getCurrent() - edamage);

					if (enemy==getGame().player){
					    if (ndamage>0)
						getGame().logMessage("Вас #8#бьет#^# " + this.getName().toLowerCase() + "!#^# (" + Integer.toString(ndamage) + ") #/#");
						if (ndamage==0)
						getGame().logMessage("Вас #8#бьет#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (ndamage<0)
						getGame().logMessage("Вас #8#бьет#^# " + this.getName().toLowerCase() + "! Удар #3#исцеляет#^# вас! (" + Integer.toString(-ndamage) + ") #/#");
					}
					else if (this==getGame().player){
						if (ndamage>0)
						getGame().logMessage(enemy.getName() + " #8#получает удар от вас!#^# (" + Integer.toString(ndamage) + ") #/#");
						if (ndamage==0)
						getGame().logMessage(enemy.getName() + " получает удар от вас, но #8#полностью поглощает урон!#^#/#");
						if (ndamage<0)
						getGame().logMessage(enemy.getName() + " получает удар от вас и внезапно #3#исцеляется!#^# (" + Integer.toString(-ndamage) + ") #/#");
					}

				if (DFire.getMax()>0)
				if (enemy==getGame().player){
					    if (fdamage>0)
						getGame().logMessage("Вас #2#обжигает#^# " + this.getName().toLowerCase() + "! (" + Integer.toString(fdamage) + ") #/#");
						if (fdamage==0)
						getGame().logMessage("Вас #2#обжигает#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (fdamage<0)
						getGame().logMessage("Вас #2#обжигает#^# " + this.getName().toLowerCase() + "! Огонь #3#исцеляет#^# вас! (" + Integer.toString(-fdamage) + ") #/#");
					}
					else
					if (this==getGame().player){
						if (fdamage>0)
						getGame().logMessage(enemy.getName() + " #2#горит#^# в вашем огне! (" + Integer.toString(fdamage) + ") #/#");
						if (fdamage==0)
						getGame().logMessage(enemy.getName() + " #2#зажигается#^# от вашей атаки, но #8#полностью поглощает урон! #^#/#");
						if (fdamage<0)
						getGame().logMessage(enemy.getName() + " #2#получает ожог#^# от вас и внезапно #3#исцеляется!#^# (" + Integer.toString(-fdamage) + ") #/#");
					}


				if (DCold.getMax()>0)
				if (enemy==getGame().player){
					    if (cdamage>0)
						getGame().logMessage("Вас #4#замораживает#^# " + this.getName().toLowerCase() + "! (" + Integer.toString(cdamage) + ") #/#");
						if (cdamage==0)
						getGame().logMessage("Вас #4#замораживает#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона. #^#/#");
						if (cdamage<0)
						getGame().logMessage("Вас #4#замораживает#^# " + this.getName().toLowerCase() + "! Холод #3#исцеляет#^# вас! (" + Integer.toString(-cdamage) + ") #/#");
					}
					else
					if (this==getGame().player){
						if (cdamage>0)
						getGame().logMessage(enemy.getName() + " #4#дрожит от холода!#^# (" + Integer.toString(cdamage) + ") #/#");
						if (cdamage==0)
						getGame().logMessage(enemy.getName() + " #4#дрожит от холода,#^# но #8#полностью поглощает урон!#^#/#");
						if (cdamage<0)
						getGame().logMessage(enemy.getName() + " #4#мерзнет#^# и внезапно #3#исцеляется!#^# (" + Integer.toString(-cdamage) + ") #/#");
					}

			if (DElec.getMax()>0)
			if (enemy==getGame().player){
					    if (edamage>0)
						getGame().logMessage("Вас #5#бьет током#^# " + this.getName().toLowerCase() + "! Вы #5#парализованы!#^# (" + Integer.toString(edamage) + ") #/#");
						if (edamage==0)
						getGame().logMessage("Вас #5#бьет током#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (edamage<0)
						getGame().logMessage("Вас #5#бьет током#^# " + this.getName().toLowerCase() + "! Электричество #3#исцеляет#^# вас! (" + Integer.toString(-edamage) + ") #/#");
					}
					else
					if (this==getGame().player){
						if (edamage>0)
						getGame().logMessage(enemy.getName() + " #5#бьется в конвульсиях#^# и #5#не может двигаться!#^# (" + Integer.toString(edamage) + ") #/#");
						if (edamage==0)
						getGame().logMessage(enemy.getName() + " #5#получает удар током,#^# но #8#полностью поглощает урон!#^#/#");
						if (edamage<0)
						getGame().logMessage(enemy.getName() + " #5#получает удар током#^# и внезапно #3#исцеляется!#^# (" + Integer.toString(-edamage) + ") #/#");
					}
			if (DPoison.getMax()>0)
		    if (enemy==getGame().player){
					    if (pdamage>0)
						getGame().logMessage("Вас #3#отравляет#^# " + this.getName().toLowerCase() + "! (" + Integer.toString(pdamage) + ") #/#");
						if (pdamage==0)
						getGame().logMessage("Вас #3#пытается отравить#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (pdamage<0)
						getGame().logMessage("Вас #3#отравляет#^# " + this.getName().toLowerCase() + "! Яд #3#исцеляет#^# вас! (" + Integer.toString(-pdamage) + ") #/#");
					}
					else
					if (this==getGame().player){
						if (pdamage>0)
						getGame().logMessage(enemy.getName() + " #3#теряет на секунду сознание от вашего яда!#^# (" + Integer.toString(pdamage) + ") #/#");
						if (pdamage==0)
						getGame().logMessage(enemy.getName() + " #3#впитывает ваш яд,#^# но #8#полностью поглощает урон!#^#/#");
						if (pdamage<0)
						getGame().logMessage(enemy.getName() + " #3#поглощает ваш яд#^# и внезапно #3#исцеляется!#^# (" + Integer.toString(-pdamage) + ") #/#");
					}



		}
				else
				if (this==getGame().player) getGame().logMessage("Вы промахнулись! #/#");
				else
				if (enemy == getGame().player) getGame().logMessage(this.getName() + " #8#промахивается по вам!#^#/#");

	}



	}