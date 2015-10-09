package com.rommax;

import java.util.*;

public class Item{
	private Game game;
	String name;
	private int Y;
	private int X;
	private int id;
	private int level;
	public Map map;
	private int slot;
	private int size;
	private int mass;
	private int type;
	private String script;
	private int chanse;
	private boolean identify;
	String real_name;
	private static final int chanse_to_have_suffixes_for_armor = 10;
	private static final int chanse_to_have_resists_for_armor = 90;
	private static final int chanse_to_have_stats_for_armor = 70;


	private static final int chanse_to_have_suffixes_for_weapon = 10;
	private static final int chanse_to_have_damages_for_weapon = 90;
	private static final int chanse_to_have_resists_for_weapon = 50;
	private static final int chanse_to_have_stats_for_weapon = 70;





	public void swap_names(){
		name = real_name;
	}

	public boolean isIdentify(){return identify;};
	public void setIdentify(boolean b){identify = b;};

	public void setGame(Game game){
		this.game = game;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public int getSlot(){return slot;};
	public void setSlot(int slot){this.slot = slot;};
	public int getSize(){return size;};
	public void setSize(int size){this.size = size;};
	public int getMass(){return mass;};
	public void setMass(int mass){this.mass = mass;};
	public int getChanse(){return chanse;};

	public Map getMap(){return map;}
	public Game getGame(){return game;}
	public int getLevel(){return level;}
	public void setLevel(int x){level = x;};
	public void setY(int Y){
		this.Y = Y;
	}

	public void setX(int X){
		this.X = X;
	}

	public String getRealName(){
		return real_name;
	}

	public int getX(){return X;}
	public int getY(){return Y;}
	public int getID(){return id;}
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	public void setScript(String sc){script = sc;};
	public String getScript(){return script;};

	public static void generateArmor(Item item){
		if (!item.game.dice(chanse_to_have_suffixes_for_armor,100)) return;

		if (item.game.dice(chanse_to_have_stats_for_armor,100)){


			Random r = new Random();
			int d = r.nextInt(4);
			switch (d){
				case 0:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#силы#^#";
					item.script += "#STR_UP " + Integer.toString(dd)+"#";
				}; break;
				case 1:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#ловкости#^#";
					item.script += "#AGI_UP " + Integer.toString(dd)+"#";
				}; break;
				case 2:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#выносливости#^#";
					item.script += "#END_UP " + Integer.toString(dd)+"#";
				}; break;
				case 3:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#удачи#^#";
					item.script += "#LUCK_UP " + Integer.toString(dd)+"#";
				}; break;
			}
			item.identify = false;
			item.name += "(?)";
		}
		else
		if (item.game.dice(chanse_to_have_resists_for_armor,100)){

			Random r = new Random();
			int d = r.nextInt(4);
			switch (d){
				case 0:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #2#защиты от огня#^#";
					item.script += "#RFIRE " + Integer.toString(dd)+"#";
				}; break;
				case 1:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #4#защиты от холода#^#";
					item.script += "#RCOLD " + Integer.toString(dd)+"#";
				}; break;
				case 2:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #3#защиты от яда#^#";
					item.script += "#RPOISON " + Integer.toString(dd)+"#";
				}; break;
				case 3:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #5#защиты от электричества#^#";
					item.script += "#RELEC " + Integer.toString(dd)+"#";
				}; break;
			}
			item.identify = false;
			item.name += "(?)";
		}
	}

public static void generateWeapon(Item item){
		if (!item.game.dice(chanse_to_have_suffixes_for_weapon,100)) return;

		if (item.game.dice(chanse_to_have_damages_for_weapon,100)){

			Random r = new Random();
			int d = r.nextInt(3);
			switch (d){
				case 0:
				{
					int d1 = 0;
					int d2 = 0;
					while (d1>=d2){
					d1 = r.nextInt(item.level * 5) + 1;
					d2 = r.nextInt(item.level * 5) + 1;
					}
					item.real_name += " #2#пламени#^#";
					item.script += "#DFIRE " + Integer.toString(d1)+"_" + Integer.toString(d2)+"#";
				}; break;
				case 1:
				{
					int d1 = 0;
					int d2 = 0;
					while (d1>=d2){
					d1 = r.nextInt(item.level * 5) + 1;
					d2 = r.nextInt(item.level * 5) + 1;
					}
					item.real_name += " #4#холода#^#";
					item.script += "#DCOLD " + Integer.toString(d1)+"_" + Integer.toString(d2)+"#";
				}; break;
				case 2:
				{
					int d1 = 0;
					int d2 = 0;
					while (d1>=d2){
					d1 = r.nextInt(item.level * 5) + 1;
					d2 = r.nextInt(item.level * 5) + 1;
					}
					item.real_name += " #3#яда#^#";
					item.script += "#DPOISON " + Integer.toString(d1)+"_" + Integer.toString(d2)+"#";
				}; break;

			}
			item.identify = false;
			item.name += "(?)";
		}
		else
if (item.game.dice(chanse_to_have_stats_for_weapon,100)){
	System.out.println(item.Y + "/" +item.X);


			Random r = new Random();
			int d = r.nextInt(4);
			switch (d){

				case 0:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#силы#^#";
					item.script += "#STR_UP " + Integer.toString(dd)+"#";
				}; break;
				case 1:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#ловкости#^#";
					item.script += "#AGI_UP " + Integer.toString(dd)+"#";
				}; break;
				case 2:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#выносливости#^#";
					item.script += "#END_UP " + Integer.toString(dd)+"#";
				}; break;
				case 3:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #8#удачи#^#";
					item.script += "#LUCK_UP " + Integer.toString(dd)+"#";
				}; break;
			}
			item.identify = false;
			item.name += "(?)";
		}
		else
	if (item.game.dice(chanse_to_have_resists_for_weapon,100)){

			System.out.println(item.Y + "/" +item.X);
			Random r = new Random();
			int d = r.nextInt(4);
			switch (d){
				case 0:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #2#защиты от огня#^#";
					item.script += "#RFIRE " + Integer.toString(dd)+"#";
				}; break;
				case 1:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #4#защиты от холода#^#";
					item.script += "#RCOLD " + Integer.toString(dd)+"#";
				}; break;
				case 2:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #3#защиты от яда#^#";
					item.script += "#RPOISON " + Integer.toString(dd)+"#";
				}; break;
				case 3:
				{
					int dd = r.nextInt(item.level) + 1;
					item.real_name += " #5#защиты от электричества#^#";
					item.script += "#RELEC " + Integer.toString(dd)+"#";
				}; break;
			}
			item.identify = false;
			item.name += "(?)";
		}
	}



	public Item(BaseItem bm, int y, int x, Map map, Game game){
		this.name = bm.getName();
		this.id = bm.getID();
		this.level = bm.getLevel();
		this.mass = bm.getMass();
		this.size = bm.getSize();
		this.slot = bm.getSlot();
		this.map = map;
		this.chanse = bm.getChanse();
		this.map.placeItemAt(y, x, this);
		this.Y = y;
		this.X = x;
		this.game = game;
		this.type = bm.getType();
		this.script = bm.getScript();
		this.identify = true;
		this.real_name = name;
		if (type == Itemset.TYPE_ARMOR)
		   generateArmor(this);
		if (type == Itemset.TYPE_MELEE_WEAPON)
		   generateWeapon(this);
		else
		if (type == Itemset.TYPE_POTION && Itemset.ID_ITEMS[id] == 0){
			real_name = name;
			name = "Непонятное зелье";
			identify = false;
		}
		else
		if (type == Itemset.TYPE_SCROLL && Itemset.ID_ITEMS[id] == 0){
			real_name = name;
			name = "Непонятный свиток";
			identify = false;
		}

	}



	}