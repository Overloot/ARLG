package com.rommax;

import java.util.*;

public class Item extends BaseItem{
	private boolean identify;
	private String real_name;

	private static final int chanse_to_have_suffixes_for_armor = 10;
	private static final int chanse_to_have_resists_for_armor = 90;
	private static final int chanse_to_have_stats_for_armor = 70;
	private static final int chanse_to_have_suffixes_for_weapon = 10;
	private static final int chanse_to_have_damages_for_weapon = 90;
	private static final int chanse_to_have_resists_for_weapon = 50;
	private static final int chanse_to_have_stats_for_weapon = 70;

	public void swap_names(){setName(real_name);}
	public boolean isIdentify(){return identify;};
	public void setIdentify(boolean b){identify = b;};
	public String getRealName(){return real_name;}

	public Item(BaseItem bm, int y, int x, Map map, Game game){
		super(bm.getChanse(), bm.getID(), bm.getLevel(), bm.getSlot(), bm.getType(),
			bm.getName(), bm.getPath(), bm.getSize(), bm.getMass(), bm.getScript());
		this.setPosition(y, x);
		this.setMap(map);
		this.setGame(game);
		this.getMap().placeItemAt(y, x, this);
		this.identify = true;
		this.real_name = getName();
		switch (getType()){
			case ItemSet.TYPE_ARMOR:
				generateArmor(this);
				break;
			case ItemSet.TYPE_MELEE_WEAPON:
				generateWeapon(this);
				break;
			case ItemSet.TYPE_POTION:
				generateConsumable("Непонятное зелье");
				break;
			case ItemSet.TYPE_SCROLL:
				generateConsumable("Непонятный свиток");
				break;
		}
	}

	private void generateConsumable(String str){
		if (ItemSet.ID_ITEMS[getID()] == 0){
			real_name = getName();
			setName(str);
			identify = false;
		}
	}
	
	private static void add(Item item, String nam, String scr){
		Random random = new Random();
		item.real_name += " " + nam;
		item.setScript(item.getScript() + "#" + scr.trim() + " " + Integer.toString(random.nextInt(item.getLevel()) + 1) + "#");
	}

	private static void addDam(Item item, String nam, String scr){
		Random random = new Random();
		int d1 = 0;
		int d2 = 0;
		while (d1 >= d2){
			d1 = random.nextInt(item.getLevel() * 5) + 1;
			d2 = random.nextInt(item.getLevel() * 5) + 1;
		}
		item.real_name += " " + nam;
		item.setScript(item.getScript() + "#" + scr.trim() + " " + Integer.toString(d1) + "_" + Integer.toString(d2) + "#");
	}

	private static void addStat(Item item){
		Random random = new Random();
		switch (random.nextInt(4)){
			case 0:
				add(item, "#8#силы#^#", "STR_UP");
				break;
			case 1:
				add(item, "#8#ловкости#^#", "AGI_UP");
				break;
			case 2:
				add(item, "#8#выносливости#^#", "END_UP");
				break;
			case 3:
				add(item, "#8#удачи#^#", "LUCK_UP");
				break;
		}
		item.identify = false;
		item.setName(item.getName() + " (?)");
	}
	
	private static void addResist(Item item){
		Random random = new Random();
		switch (random.nextInt(4)){
			case 0:
				add(item, "#2#защиты от огня#^#", "RFIRE");
				break;
			case 1:
				add(item, "#4#защиты от холода#^#", "RCOLD");
				break;
			case 2:
				add(item, "#3#защиты от яда#^#", "RPOISON");
				break;
			case 3:
				add(item, "#5#защиты от электричества#^#", "RELEC");
				break;
		}
		item.identify = false;
		item.setName(item.getName() + " (?)");
	}
	
	private static void addDamage(Item item){
		Random random = new Random();
		switch (random.nextInt(3)){
			case 0:
				addDam(item, "#2#пламени#^#", "DFIRE");
				break;
			case 1:
				addDam(item, "#4#холода#^#", "DCOLD");
				break;
			case 2:
				addDam(item, "#3#яда#^#", "DPOISON");
				break;
		}
		item.identify = false;
		item.setName(item.getName() + " (?)");
	}
	
	public static void generateArmor(Item item){
		if (!Util.dice(chanse_to_have_suffixes_for_armor,100)) return;
		if (Util.dice(chanse_to_have_stats_for_armor,100)) addStat(item);
		else if (Util.dice(chanse_to_have_resists_for_armor,100)) addResist(item);
	}

	public static void generateWeapon(Item item){
		if (!Util.dice(chanse_to_have_suffixes_for_weapon,100)) return;
		if (Util.dice(chanse_to_have_damages_for_weapon,100)) addDamage(item);
		else if (Util.dice(chanse_to_have_stats_for_weapon,100)) addStat(item);
		else if (Util.dice(chanse_to_have_resists_for_weapon,100)) addResist(item);
	}
}