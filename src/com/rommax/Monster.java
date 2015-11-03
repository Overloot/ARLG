package com.rommax;

import java.util.*;

public class Monster{
	private Game game;
	String name;
	private int Y;
	private int X;
	private int id;
	private Stat hp;
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
	private int level;
	private int exp;
	private int paralyzecount;
	private int poisoncount;
	public LinkedList<Item> getInventory(){
		return Inventory;
	}

	public Map map;

	public void setGame(Game game){
		this.game = game;
	}

	public Map getMap(){return map;}
	public Stat getHP(){return hp;}
	public Game getGame(){return game;}
	public Stat getFOVRAD(){return fovrad;}
	public Stat getCurrentWeight(){return currentWeight;}
	public Stat getCurrentSize(){return currentSize;}
	public int getLevel(){return level;}
	public void setLevel(int x){level = x;}
	public void setY(int Y){
		this.Y = Y;
	}

	public void setCurrentWeight(Stat currentWeight)
	{
		this.currentWeight = currentWeight;
	}

	public void setCurrentSize(Stat currentSize){
		this.currentSize = currentSize;
	}

	public void setX(int X){
		this.X = X;
	}

	public int getX(){return X;}
	public int getY(){return Y;}
	public int getID(){return id;}
	public int getPoisonCount(){return poisoncount;}
	public int getparalyzecount(){return paralyzecount;}
	public void setparalyzecount(int pc){paralyzecount = pc;}
	public void setPoisonCount(int pc){poisoncount = pc;}
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
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

	public void setSTR(int STR){
		this.STR = new Stat(STR, STR);
	}

	public void setAGI(int AGI){
		this.AGI = new Stat(AGI, AGI);
	}

	public void setEND(int END){
		this.END = new Stat(END, END);
	}

	public void setLUCK(int LUCK){
		this.LUCK = new Stat(LUCK, LUCK);
	}

	public Monster(BaseMonster bm, int y, int x, Map map, Game game){
		this.name = bm.getName();
		this.id = bm.getID();
		this.level = bm.getLevel();
		this.map = map;
		this.exp = 0;
		this.map.placeMonsterAt(y, x, this);
		this.Y = y;
		this.X = x;
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
		this.game = game;
		this.currentWeight = new Stat(0, game.CARRYING_PER_STRENGTH * STR.getCurrent());
		this.currentSize = new Stat(0, game.MIN_SIZE);

		Equipment = new Item[ItemSet.MAX_SLOTS];
		Inventory = new LinkedList<Item>();
	}

	public boolean move(int y, int x){
		if (currentWeight.getCurrent() > currentWeight.getMax()){
			game.logMessage("Вы #2#перегружены!#^#");
			return false;
		}
		if (currentSize.getCurrent() > currentSize.getMax()){
			game.logMessage("Вы несете #2#слишком много вещей!#^#");
			return false;
		}
		int ny = (this.Y + y);
		int nx = (this.X + x);
		if (map.hasTileAt(ny, nx)){
			if (map.field[ny][nx].getMonster()!=null){
				AttackMonster(map.field[ny][nx].getMonster());
				}
			else
			if (!map.field[ny][nx].getPassable() && map.field[ny][nx].getOpenable() && !map.field[ny][nx].getOpened()){
				boolean m = false;
				if (this==game.player) m = true;
				game.tryToOpenSomething(m, ny, nx, true);
			}
			else
			if (map.field[ny][nx].getPassable()){
				map.placeMonsterAt(this.Y, this.X, null);
				this.Y = ny;
				this.X = nx;
				map.placeMonsterAt(ny, nx, this);
				if (this == game.player){
				if (map.field[ny][nx].getItemList().size()>0)
					if (map.field[ny][nx].getItemList().size()>=2)
					game.logMessage("Здесь лежит #7#много вещей!#^#");
					else
					game.logMessage("Здесь лежит #7#" + map.field[ny][nx].getItemList().get(0).getName().toLowerCase() + ".#^#");
				}
				game.frame1.mainpanel.repaint();
				return true;
			}
	    }
		return false;
	}

	public void setEffectFrom(ScriptObject so, boolean b){
		getHP().setMax(getHP().getMax() + game.HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() + game.HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setMax(getHP().getMax() + game.HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() + game.HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getCurrentWeight().setMax(getCurrentWeight().getMax() + game.CARRYING_PER_STRENGTH * so.STR_UP.getCurrent());

		if (so.IDENTIFY) game.frame1.mainpanel.listener.ID_MODE = true;

		STR.add(so.STR_UP);

		if (b)
		if (so.STR_UP.getCurrent()>0) game.logMessage("Вы почувствовали себя #3#сильнее!#^#");
		else
		if (so.STR_UP.getCurrent()<0) game.logMessage("Вы почувствовали себя #2#слабее!#^#");


		AGI.add(so.AGI_UP);

		if (b)
		if (so.AGI_UP.getCurrent()>0) game.logMessage("Вы стали #3#более ловким!#^#");
		else
		if (so.AGI_UP.getCurrent()<0) game.logMessage("Вы почувствовали себя #2#более неуклюже!#^#");

		END.add(so.END_UP);

		if (b)
		if (so.END_UP.getCurrent()>0) game.logMessage("Вы почувствовали, что стали #3#более выносливым!#^#");
		else
		if (so.END_UP.getCurrent()<0) game.logMessage("Вы почувствовали, что стали #2#менее выносливым!#^#");

		LUCK.add(so.LUCK_UP);
		
		if (b)
		if (so.LUCK_UP.getCurrent()>0) game.logMessage("Вы почувствовали себя #3#удачливее!#^#");
		else
		if (so.LUCK_UP.getCurrent()<0) game.logMessage("Вы почувствовали себя #2#менее удачливее!#^#");

		if (b)
		if (so.DFIRE.getCurrent()>0) game.logMessage("Вы почувствовали силу #2#огня!#^#");
		else
		if (so.DFIRE.getCurrent()<0) game.logMessage("Вы стали хуже чувствовать силу #2#огня!#^#");

		DFire.setCurrent(DFire.getCurrent() + so.DFIRE.getCurrent());
		DFire.setMax(DFire.getMax() + so.DFIRE.getMax());
		if (b)
		if (so.DCOLD.getCurrent()>0) game.logMessage("Вы почувствовали силу #4#холода!#^#");
		else
		if (so.DCOLD.getCurrent()<0) game.logMessage("Вы стали хуже чувствовать силу #4#холода!#^#");

		DCold.setCurrent(DCold.getCurrent() + so.DCOLD.getCurrent());
		DCold.setMax(DCold.getMax() + so.DCOLD.getMax());
		if (b)
		if (so.DPOISON.getCurrent()>0) game.logMessage("Вы почувствовали силу #3#яда!#^#");
		else
		if (so.DPOISON.getCurrent()<0) game.logMessage("Вы стали хуже чувствовать силу #3#яда!#^#");

		DPoison.setCurrent(DPoison.getCurrent() + so.DPOISON.getCurrent());
		DPoison.setMax(DPoison.getMax() + so.DPOISON.getMax());
		if (b)
		if (so.DELEC.getCurrent()>0) game.logMessage("Вы почувствовали силу #5#электричества!#^#");
		else
		if (so.DELEC.getCurrent()<0) game.logMessage("Вы стали хуже чувствовать силу #5#электричества!#^#");

		DElec.setCurrent(DElec.getCurrent() + so.DELEC.getCurrent());
		DElec.setMax(DElec.getMax() + so.DELEC.getMax());
		DNormal.setCurrent(DNormal.getCurrent() + so.DNORMAL.getCurrent());
		DNormal.setMax(DNormal.getMax() + so.DNORMAL.getMax());
		if (b)
		if (so.RFIRE.getCurrent()>0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #2#огню!#^#");
		else
		if (so.RFIRE.getCurrent()<0) game.logMessage("Вы стали #2#более#^# восприимчивы к #2#огню!#^#");

		RFire.add(so.RFIRE);
		if (b)
		if (so.RCOLD.getCurrent()>0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #4#холоду!#^#");
		else
		if (so.RCOLD.getCurrent()<0) game.logMessage("Вы стали #2#более#^# восприимчивы к #4#холоду!#^#");

		RCold.add(so.RCOLD);
		if (b)
		if (so.RPOISON.getCurrent()>0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #3#яду!#^#");
		else
		if (so.RPOISON.getCurrent()<0) game.logMessage("Вы стали #2#более#^# восприимчивы к #3#яду!#^#");

		RPoison.add(so.RPOISON);
		if (b)
		if (so.RNORMAL.getCurrent()>0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #8#ударам!#^#");
		else
		if (so.RNORMAL.getCurrent()<0) game.logMessage("Вы стали #2#более#^# восприимчивы к #8#ударам!#^#");

		RNormal.add(so.RNORMAL);
		if (b)
		if (so.RELEC.getCurrent()>0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #5#электричеству!#^#");
		else
		if (so.RELEC.getCurrent()<0) game.logMessage("Вы стали #2#более#^# восприимчивы к #5#электричеству!#^#");

		RElec.add(so.RELEC);
		if (b)
		if (so.HEALSELF.getCurrent()>0) game.logMessage("Вы #3#исцеляетесь!#^#");
		else
		if (so.HEALSELF.getCurrent()<0) game.logMessage("Вы #2#корчитесь от боли!#^#");

		hp.setCurrent(hp.getCurrent() + so.HEALSELF.getCurrent());
		if (b)
		if (so.FOVRAD.getCurrent()>0) game.logMessage("Вы стали #8#лучше#^# видеть!");
		else
		if (so.FOVRAD.getCurrent()<0) game.logMessage("Вы стали #2#хуже#^# видеть!");

		fovrad.add(so.FOVRAD);

		if (b)
		if (so.SW_UP.getCurrent()>0) game.logMessage("Теперь вы можете нести #3#больше#^# вещей!");
		else
		if (so.SW_UP.getCurrent()<0) game.logMessage("Теперь вы можете нести #2#меньше#^# вещей!");
		currentSize.setMax(currentSize.getMax() + so.SW_UP.getCurrent());
		if (b)
		if (so.HEALPOISON.getCurrent()>0) game.logMessage("Вы исцеляетесь от #3#яда!#^#");

		if (so.TELEPORT) {
			Random random = new Random();
			game.logMessage("Вы внезапно телепортировались!");
			int ny = random.nextInt(map.getHeight());
			int nx = random.nextInt(map.getWidth());
			while (map.field[ny][nx].getMonster()!=null || !map.field[ny][nx].getPassable()){
			 ny = random.nextInt(map.getHeight());
			 nx = random.nextInt(map.getWidth());
			}
			map.field[Y][X].setMonster(null);
			this.Y = ny;
			this.X = nx;
			map.field[Y][X].setMonster(this);
			game.map.setCurrentX(getX() - ((GameWindow.getScreenTileSizeX() * Tileset.TILE_SIZE)/ (Tileset.TILE_SIZE * 2)));
			game.map.setCurrentY(getY() - ((GameWindow.getScreenTileSizeY() * Tileset.TILE_SIZE)/ (Tileset.TILE_SIZE * 2)));

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
		getHP().setMax(getHP().getMax() - game.HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() - game.HIT_POINTS_PER_ENDURANCE * so.END_UP.getCurrent());
		getHP().setMax(getHP().getMax() - game.HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getHP().setCurrent(getHP().getCurrent() - game.HIT_POINTS_PER_STRENGTH * so.STR_UP.getCurrent());
		getCurrentWeight().setMax(getCurrentWeight().getMax() - game.CARRYING_PER_STRENGTH * so.STR_UP.getCurrent());

		STR.sub(so.STR_UP);
		if (b)
		if (so.STR_UP.getCurrent()<0) game.logMessage("Вы почувствовали себя #3#сильнее!#^#");
		else
		if (so.STR_UP.getCurrent()>0) game.logMessage("Вы почувствовали себя #2#слабее!#^#");


		AGI.sub(so.AGI_UP);
		if (b)
		if (so.AGI_UP.getCurrent()<0) game.logMessage("Вы стали #3#более ловким!#^#");
		else
		if (so.AGI_UP.getCurrent()>0) game.logMessage("Вы почувствовали себя #2#более неуклюже!#^#");

		END.sub(so.END_UP);
		if (b)
		if (so.END_UP.getCurrent()<0) game.logMessage("Вы почувствовали, что стали #3#более выносливым!#^#");
		else
		if (so.END_UP.getCurrent()>0) game.logMessage("Вы почувствовали, что стали #2#менее выносливым!#^#");

		LUCK.sub(so.LUCK_UP);
		if (b)
		if (so.LUCK_UP.getCurrent()<0) game.logMessage("Вы почувствовали себя #3#удачливее!#^#");
		else
		if (so.LUCK_UP.getCurrent()>0) game.logMessage("Вы почувствовали себя #2#менее удачливее!#^#");

		if (b)
		if (so.DFIRE.getCurrent()<0) game.logMessage("Вы почувствовали силу #2#огня!#^#");
		else
		if (so.DFIRE.getCurrent()>0) game.logMessage("Вы стали хуже чувствовать силу #2#огня!#^#");

		DFire.setCurrent(DFire.getCurrent() - so.DFIRE.getCurrent());
		DFire.setMax(DFire.getMax() - so.DFIRE.getMax());
		if (b)
		if (so.DCOLD.getCurrent()<0) game.logMessage("Вы почувствовали силу #4#холода!#^#");
		else
		if (so.DCOLD.getCurrent()>0) game.logMessage("Вы стали хуже чувствовать силу #4#холода!#^#");

		DCold.setCurrent(DCold.getCurrent() - so.DCOLD.getCurrent());
		DCold.setMax(DCold.getMax() - so.DCOLD.getMax());
		if (b)
		if (so.DCOLD.getCurrent()<0) game.logMessage("Вы почувствовали силу #3#яда!#^#");
		else
		if (so.DCOLD.getCurrent()>0) game.logMessage("Вы стали хуже чувствовать силу #3#яда!#^#");

		DPoison.setCurrent(DPoison.getCurrent() - so.DPOISON.getCurrent());
		DPoison.setMax(DPoison.getMax() - so.DPOISON.getMax());
		if (b)
		if (so.DELEC.getCurrent()<0) game.logMessage("Вы почувствовали силу #5#электричества!#^#");
		else
		if (so.DELEC.getCurrent()>0) game.logMessage("Вы стали хуже чувствовать силу #5#электричества!#^#");

		DElec.setCurrent(DElec.getCurrent() - so.DELEC.getCurrent());
		DElec.setMax(DElec.getMax() - so.DELEC.getMax());
		DNormal.setCurrent(DNormal.getCurrent() - so.DNORMAL.getCurrent());
		DNormal.setMax(DNormal.getMax() - so.DNORMAL.getMax());
		if (b)
		if (so.RFIRE.getCurrent()<0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #2#огню!#^#");
		else
		if (so.RFIRE.getCurrent()>0) game.logMessage("Вы стали #2#более#^# восприимчивы к #2#огню!#^#");

		RFire.sub(so.RFIRE);
		if (b)
		if (so.RCOLD.getCurrent()<0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #4#холоду!#^#");
		else
		if (so.RCOLD.getCurrent()>0) game.logMessage("Вы стали #2#более#^# восприимчивы к #4#холоду!#^#");

		RCold.sub(so.RCOLD);
		if (b)
		if (so.RPOISON.getCurrent()<0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #3#яду!#^#");
		else
		if (so.RPOISON.getCurrent()>0) game.logMessage("Вы стали #2#более#^# восприимчивы к #3#яду!#^#");

		RPoison.sub(so.RPOISON);
		if (b)
		if (so.RNORMAL.getCurrent()<0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #8#ударам!#^#");
		else
		if (so.RNORMAL.getCurrent()>0) game.logMessage("Вы стали #2#более#^# восприимчивы к #8#ударам!#^#");

		RNormal.sub(so.RNORMAL);
		if (b)
		if (so.RELEC.getCurrent()<0) game.logMessage("Вы стали #8#менее#^# восприимчивы к #5#электричеству!#^#");
		else
		if (so.RELEC.getCurrent()>0) game.logMessage("Вы стали #2#более#^# восприимчивы к #5#электричеству!#^#");

		RElec.sub(so.RELEC);
		if (b)
		if (so.HEALSELF.getCurrent()<0) game.logMessage("Вы #3#исцеляетесь!#^#");
		else
		if (so.HEALSELF.getCurrent()>0) game.logMessage("Вы #2#корчитесь от боли!#^#");

		hp.setCurrent(hp.getCurrent() - so.HEALSELF.getCurrent());
		if (b)
		if (so.FOVRAD.getCurrent()<0) game.logMessage("Вы стали #8#лучше#^# видеть!");
		else
		if (so.FOVRAD.getCurrent()>0) game.logMessage("Вы стали #2#хуже#^# видеть!");

		fovrad.sub(so.FOVRAD);
		if (b)
		if (so.SW_UP.getCurrent()<0) game.logMessage("Теперь вы можете нести #3#больше#^# вещей!");
		else
		if (so.SW_UP.getCurrent()>0) game.logMessage("Теперь вы можете нести #2#меньше#^# вещей!");
		currentSize.setMax(currentSize.getMax() - so.SW_UP.getCurrent());

		if (getPoisonCount()<0) setPoisonCount(0);
	}




	private void AttackMonster(Monster enemy){
		if (this==enemy) return;
		if (this!= game.player && enemy!=game.player) return;
		Random rand = new Random();

				int ndamage = ( (100 - enemy.RNormal.getCurrent()) * (game.getValueFrom(DNormal.getCurrent(), DNormal.getMax()) + game.rand(STR.getCurrent())) / 100 ) ;
				int fdamage = ((100 - enemy.RFire.getCurrent()) * game.getValueFrom(DFire.getCurrent(), DFire.getMax()) / 100) ;
				int cdamage = ((100 - enemy.RCold.getCurrent() ) * game.getValueFrom(DCold.getCurrent(), DCold.getMax()) / 100 );
				int edamage = (  (100 - enemy.RElec.getCurrent() ) * game.getValueFrom(DElec.getCurrent(), DElec.getMax())/ 100 );
				int pdamage = ((100 - enemy.RPoison.getCurrent() ) * game.getValueFrom(DPoison.getCurrent(), DPoison.getMax()) / 100);
				int damage = (ndamage + fdamage + cdamage);
				int pd = enemy.hp.getCurrent();
				//String dlog = "ловкость атакующего " + AGI.getCurrent() + "| ловкость жертвы" + enemy.AGI.getCurrent();
				int min = rand.nextInt(AGI.getCurrent());
				int max = rand.nextInt(enemy.AGI.getCurrent());
				//dlog+=" выпало" + min + " против " + max;
				//game.logMessage(dlog);

				if (min>=max){
					if (game.dice(LUCK.getCurrent() * 100,100000)){
						damage += (rand.nextInt(ndamage) + 1);
						if (enemy == game.player)
							game.logMessage(this.getName().toLowerCase() + "  #8#критически#^# бьет вас!");
								else if (this == game.player)
									game.logMessage("Вы наносите #3#критический#^# удар!");
					}
					enemy.hp.setCurrent(pd-damage);
					if (pdamage>0) enemy.poisoncount += pdamage;
					if (edamage>0) enemy.paralyzecount += edamage + 1;
					if (pdamage<0) enemy.getHP().setCurrent(enemy.getHP().getCurrent() - pdamage);
					if (edamage<0) enemy.getHP().setCurrent(enemy.getHP().getCurrent() - edamage);
					if (enemy==game.player){
					    if (ndamage>0)
						game.logMessage("Вас #8#бьет#^# " + this.getName().toLowerCase() + "!#^# (" + Integer.toString(ndamage) + ") #/#");
						if (ndamage==0)
						game.logMessage("Вас #8#бьет#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (ndamage<0)
						game.logMessage("Вас #8#бьет#^# " + this.getName().toLowerCase() + "! Удар #3#исцеляет#^# вас! (" + Integer.toString(-ndamage) + ") #/#");
					}
					else if (this==game.player){
						if (ndamage>0)
						game.logMessage(enemy.getName() + " #8#получает удар от вас!#^# (" + Integer.toString(ndamage) + ") #/#");
						if (ndamage==0)
						game.logMessage(enemy.getName() + " получает удар от вас, но #8#полностью поглощает урон!#^#/#");
						if (ndamage<0)
						game.logMessage(enemy.getName() + " получает удар от вас и внезапно #3#исцеляется!#^# (" + Integer.toString(-ndamage) + ") #/#");
					}

				if (DFire.getMax()>0)
				if (enemy==game.player){
					    if (fdamage>0)
						game.logMessage("Вас #2#обжигает#^# " + this.getName().toLowerCase() + "! (" + Integer.toString(fdamage) + ") #/#");
						if (fdamage==0)
						game.logMessage("Вас #2#обжигает#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (fdamage<0)
						game.logMessage("Вас #2#обжигает#^# " + this.getName().toLowerCase() + "! Огонь #3#исцеляет#^# вас! (" + Integer.toString(-fdamage) + ") #/#");
					}
					else
					if (this==game.player){
						if (fdamage>0)
						game.logMessage(enemy.getName() + " #2#горит#^# в вашем огне! (" + Integer.toString(fdamage) + ") #/#");
						if (fdamage==0)
						game.logMessage(enemy.getName() + " #2#зажигается#^# от вашей атаки, но #8#полностью поглощает урон! #^#/#");
						if (fdamage<0)
						game.logMessage(enemy.getName() + " #2#получает ожог#^# от вас и внезапно #3#исцеляется!#^# (" + Integer.toString(-fdamage) + ") #/#");
					}


				if (DCold.getMax()>0)
				if (enemy==game.player){
					    if (cdamage>0)
						game.logMessage("Вас #4#замораживает#^# " + this.getName().toLowerCase() + "! (" + Integer.toString(cdamage) + ") #/#");
						if (cdamage==0)
						game.logMessage("Вас #4#замораживает#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона. #^#/#");
						if (cdamage<0)
						game.logMessage("Вас #4#замораживает#^# " + this.getName().toLowerCase() + "! Холод #3#исцеляет#^# вас! (" + Integer.toString(-cdamage) + ") #/#");
					}
					else
					if (this==game.player){
						if (cdamage>0)
						game.logMessage(enemy.getName() + " #4#дрожит от холода!#^# (" + Integer.toString(cdamage) + ") #/#");
						if (cdamage==0)
						game.logMessage(enemy.getName() + " #4#дрожит от холода,#^# но #8#полностью поглощает урон!#^#/#");
						if (cdamage<0)
						game.logMessage(enemy.getName() + " #4#мерзнет#^# и внезапно #3#исцеляется!#^# (" + Integer.toString(-cdamage) + ") #/#");
					}

			if (DElec.getMax()>0)
			if (enemy==game.player){
					    if (edamage>0)
						game.logMessage("Вас #5#бьет током#^# " + this.getName().toLowerCase() + "! Вы #5#парализованы!#^# (" + Integer.toString(edamage) + ") #/#");
						if (edamage==0)
						game.logMessage("Вас #5#бьет током#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (edamage<0)
						game.logMessage("Вас #5#бьет током#^# " + this.getName().toLowerCase() + "! Электричество #3#исцеляет#^# вас! (" + Integer.toString(-edamage) + ") #/#");
					}
					else
					if (this==game.player){
						if (edamage>0)
						game.logMessage(enemy.getName() + " #5#бьется в конвульсиях#^# и #5#не может двигаться!#^# (" + Integer.toString(edamage) + ") #/#");
						if (edamage==0)
						game.logMessage(enemy.getName() + " #5#получает удар током,#^# но #8#полностью поглощает урон!#^#/#");
						if (edamage<0)
						game.logMessage(enemy.getName() + " #5#получает удар током#^# и внезапно #3#исцеляется!#^# (" + Integer.toString(-edamage) + ") #/#");
					}
			if (DPoison.getMax()>0)
		    if (enemy==game.player){
					    if (pdamage>0)
						game.logMessage("Вас #3#отравляет#^# " + this.getName().toLowerCase() + "! (" + Integer.toString(pdamage) + ") #/#");
						if (pdamage==0)
						game.logMessage("Вас #3#пытается отравить#^# " + this.getName().toLowerCase() + ", но #8#не наносит вам какого-либо урона.#^#/#");
						if (pdamage<0)
						game.logMessage("Вас #3#отравляет#^# " + this.getName().toLowerCase() + "! Яд #3#исцеляет#^# вас! (" + Integer.toString(-pdamage) + ") #/#");
					}
					else
					if (this==game.player){
						if (pdamage>0)
						game.logMessage(enemy.getName() + " #3#теряет на секунду сознание от вашего яда!#^# (" + Integer.toString(pdamage) + ") #/#");
						if (pdamage==0)
						game.logMessage(enemy.getName() + " #3#впитывает ваш яд,#^# но #8#полностью поглощает урон!#^#/#");
						if (pdamage<0)
						game.logMessage(enemy.getName() + " #3#поглощает ваш яд#^# и внезапно #3#исцеляется!#^# (" + Integer.toString(-pdamage) + ") #/#");
					}



		}
				else
				if (this==game.player) game.logMessage("Вы промахнулись! #/#");
				else
				if (enemy == game.player) game.logMessage(this.getName() + " #8#промахивается по вам!#^#/#");

	}



	}