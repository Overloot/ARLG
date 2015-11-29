package com.rommax;

public class Item extends BaseItem{
	private boolean identify;
	private String real_name;

	private static final int chance_to_have_suffixes_for_armor = 10;
	private static final int chance_to_have_resists_for_armor = 90;
	private static final int chance_to_have_stats_for_armor = 70;
	private static final int chance_to_have_suffixes_for_weapon = 10;
	private static final int chance_to_have_damages_for_weapon = 90;
	private static final int chance_to_have_resists_for_weapon = 50;
	private static final int chance_to_have_stats_for_weapon = 70;

	public void swap_names(){setName(real_name);}
	public boolean isIdentify(){return identify;};
	public void setIdentify(boolean b){identify = b;};
	public String getRealName(){return real_name;}

	public Item(BaseItem bm, int y, int x, Map map, Game game){
		super(bm.getChance(), bm.getID(), bm.getLevel(), bm.getSlot(), bm.getType(),
			bm.getName(), bm.getPath(), bm.getSize(), bm.getMass(), bm.getScript(), bm.getDestroyable(), bm.getLife(), bm.getLoot());
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
			case ItemSet.TYPE_MELEE_WEAPON_SWORD:
				generateWeapon(this);
				break;
			case ItemSet.TYPE_POTION:
				generateConsumable("Непонятное зелье");
				break;
			case ItemSet.TYPE_SCROLL:
				generateConsumable("Непонятный свиток");
				break;
			case ItemSet.TYPE_FOOD:
				if(bm.getID() >= ItemSet.MIN_MUSH && bm.getID() <= ItemSet.MAX_MUSH){
					generateConsumable("Непонятный гриб");
					return;
				}
				if(bm.getID() >= ItemSet.MIN_PLANT && bm.getID() <= ItemSet.MAX_PLANT){
					generateConsumable("Непонятное растение");
					return;
				}
				break;
		}
	}

	public Item(BaseItem bm){
		super(bm.getChance(), bm.getID(), bm.getLevel(), bm.getSlot(), bm.getType(),
				bm.getName(), bm.getPath(), bm.getSize(), bm.getMass(), bm.getScript(), bm.getDestroyable(), bm.getLife(), bm.getLoot());
		this.identify = true;
		this.real_name = getName();
	}

	private void generateConsumable(String str){
		if (ItemSet.ID_ITEMS[getID()] == 0){
			real_name = getName();
			setName(str);
			identify = false;
		}
	}
	
	private static void add(Item item, String nam, String scr){
		int d = Util.rand(item.getLevel());
		item.real_name += " " + nam;
		item.setScript(item.getScript() + "#" + scr.trim() + " " + Integer.toString(d) + "#");
	}

	private static void addDam(Item item, String nam, String scr){
		int d1 = 0;
		int d2 = 0;
		while (d1 >= d2){
			d1 = Util.rand(item.getLevel() * 5);
			d2 = Util.rand(item.getLevel() * 5);
		}
		item.real_name += " " + nam;
		item.setScript(item.getScript() + "#" + scr.trim() + " " + Integer.toString(d1) + "_" + Integer.toString(d2) + "#");
	}

	private static void addStat(Item item){
		switch (Util.rand(4)){
			case 1:
				add(item, "#8#силы#^#", "STR_UP");
				break;
			case 2:
				add(item, "#8#ловкости#^#", "AGI_UP");
				break;
			case 3:
				add(item, "#8#выносливости#^#", "END_UP");
				break;
			case 4:
				add(item, "#8#удачи#^#", "LUCK_UP");
				break;
		}
		item.identify = false;
		item.setName(item.getName() + " (?)");
	}
	
	private static void addResist(Item item){
		switch (Util.rand(4)){
			case 1:
				add(item, "#2#защиты от огня#^#", "RFIRE");
				break;
			case 2:
				add(item, "#4#защиты от холода#^#", "RCOLD");
				break;
			case 3:
				add(item, "#3#защиты от яда#^#", "RPOISON");
				break;
			case 4:
				add(item, "#5#защиты от электричества#^#", "RELEC");
				break;
		}
		item.identify = false;
		item.setName(item.getName() + " (?)");
	}
	
	private static void addDamage(Item item){
		switch (Util.rand(4)){
			case 1:
				addDam(item, "#2#пламени#^#", "DFIRE");
				break;
			case 2:
				addDam(item, "#4#холода#^#", "DCOLD");
				break;
			case 3:
				addDam(item, "#3#яда#^#", "DPOISON");
				break;
			case 4:
				addDam(item, "#3#молний#^#", "DELEC");
				break;
		}
		item.identify = false;
		item.setName(item.getName() + " (?)");
	}
	
	public static void generateArmor(Item item){
		if (!Util.dice(chance_to_have_suffixes_for_armor,100)) return;
		if (Util.dice(chance_to_have_stats_for_armor,100)) addStat(item);
		else if (Util.dice(chance_to_have_resists_for_armor,100)) addResist(item);
	}

	public static void generateWeapon(Item item){
		if (!Util.dice(chance_to_have_suffixes_for_weapon,100)) return;
		if (Util.dice(chance_to_have_damages_for_weapon,100)) addDamage(item);
		else if (Util.dice(chance_to_have_stats_for_weapon,100)) addStat(item);
		else if (Util.dice(chance_to_have_resists_for_weapon,100)) addResist(item);
	}
}