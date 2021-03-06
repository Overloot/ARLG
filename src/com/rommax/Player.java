package com.rommax;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class Player extends Monster{
	
    public static final int MAX_PLAYER_LEVEL = 30;
    public static int maxExperience = 200;
    public int statsFree = 0;
	public int shadowSkill = 0;

	Player(BaseMonster player, int y, int x, Map map, Game game){
		super(player, y, x, map, game);
	}
	
	// Имя игрока, информация о расе, специализации и титуле
	public String getInfo(){
		String str = "";
		str += " "  + RaceSet.getRace(RaceSet.getCurrentRaceID).getName();
		str += " " + ClassSet.getClass(ClassSet.getCurrentClassID).getName();
		str += " " + getLevel() + " уровня";
		return str.toUpperCase();
	}
	
    // метод описывает как надо пить =) quaff - пить залпом
    public void quaffItem(LinkedList<Item> list, int number) {
        if (number == -1) return;

        if (getInventory().get(number).getType() != ItemSet.TYPE_POTION) {
            getGame().logMessage("#8#" + getInventory().get(number).getName() + "#^# - это нельзя выпить!");
            return;
        }
        getGame().logMessage("#8#Выпито!#^# (" + getInventory().get(number).getName().toLowerCase() + "#^#)");
        identifyItem(getInventory().get(number));
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() - getInventory().get(number).getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() - getInventory().get(number).getSize());
        setEffectFrom(ScriptParser.parseString(getInventory().get(number).getScript()), getInventory().get(number).isIdentify());
        getInventory().remove(number);
    }

    // данный метод вызывается из KeyHandler, когда активен LOOK_MODE
    public void lookAtMonster(int y, int x) {
        if (getMap().field[y][x].getMonster() == null && getMap().field[y][x].getItemList().size() == 0) {
            getGame().logMessage("Здесь ничего нет!");
            return;
        }
        if (getMap().field[y][x].getMonster() != null) {
            getGame().frame1.setFocusable(false);
            getGame().frame1.setFocusableWindowState(false);
            DescWindow frame2 = new DescWindow(getGame(), getMap().field[y][x].getMonster());
            frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame2.setLocation(getGame().frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, getGame().frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
            frame2.toFront();
            frame2.setTitle("Информация о монстре");
            frame2.setVisible(true);
        } else if (getMap().field[y][x].getItemList().size() != 0) {
            examineItem(getMap().field[y][x].getItemList(), 0);                                                             // если на клетке есть итемы, то будет показана инфа о них
        }
    }

    // метод осмотра вещи, например из инвентаря по нажатию Enter
    public void examineItem(LinkedList<Item> list, int number) {
        if (number == -1) return;
        getGame().logMessage("Вы осмотрели предмет (" + list.get(number).getName().toLowerCase() + "#^#)");
        getGame().frame1.setFocusable(false);
        getGame().frame1.setFocusableWindowState(false);
        DescWindow frame2 = new DescWindow(getGame(), list.get(number));
        frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame2.setLocation(getGame().frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, getGame().frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
        frame2.toFront();
        frame2.setTitle("Информация о предмете");
        frame2.setVisible(true);
    }

	// Добавляем игрока на карту
	public static void placeOnMap(Map map, Game game){
        int y = 10;
        int x = 10;
        Random random = new Random();
        while (!map.field[y][x].getPassable() || map.field[y][x].getMonster() != null) {
            y = random.nextInt(map.getHeight());
            x = random.nextInt(map.getWidth());
        }
        BaseMonster baseMonster = MonsterSet.getMonster(MonsterSet.MONSTER_PLAYER);
		game.monsterList[game.monstersQuantity] = new Player(baseMonster, y, x, map, game);
        game.monstersQuantity++;
	}
	
	// Добавляем игрока на карту и возвращает эту карту
	public Map getMap(int newMapLevel){
		Map map = getGame().getMap();
		map.field[getY()][getX()].setMonster(null);
        map = getGame().mapList[newMapLevel];
        map.setGame(getGame());
        getGame().frame1.SwitchMap(map);
        getGame().currentMapNumber = newMapLevel;
        map.placeMonsterAt(getY(), getX(), this);
        setMap(map);
		return map;
	}
	
    // Поднимаем предмет
    public void pickupItem(LinkedList<Item> list, int number) {
        if (number == -1) return;                                                                                               
		if (getMap().field[getY()][getX()].getChest() > Tile.NONE && !getMap().field[getY()][getX()].getOpened()) {
			getGame().logMessage("#2#Вы не можете взять это из закрытого сундука!#^#");
			getGame().refresh();
			return;
		}
        getInventory().addLast(list.get(number));                                                                        
        getGame().logMessage("#8#Взято!#^# (" + list.get(number).getName().toLowerCase() + "#1#)");
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() + list.get(number).getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() + list.get(number).getSize());
		// Проверка достижения на сбор предметов по типу
		switch(list.get(number).getType()){
			case ItemSet.TYPE_FOOD:
				int id = list.get(number).getID();
				// Грибы
				if(id >= ItemSet.getMinMushroomID() && id <= ItemSet.getMaxMushroomID()){
					Achievement.check(getGame(), AchievementSet.TYPE_FIND_MUSH, 1);
				}
				// Растения
				if(id >= ItemSet.getMinPlantID() && id <= ItemSet.getMaxPlantID()){
					Achievement.check(getGame(), AchievementSet.TYPE_FIND_PLANT, 1);
				}
				break;
			case ItemSet.TYPE_SCROLL:
				// Свитки
				Achievement.check(getGame(), AchievementSet.TYPE_FIND_SCROLL, 1);
				break;
			case ItemSet.TYPE_POTION:
				// Зелья
				Achievement.check(getGame(), AchievementSet.TYPE_FIND_POTION, 1);
				break;
		}
		Achievement.check(getGame(), AchievementSet.TYPE_FIND_ITEM, 1);
        list.remove(number);
    }

    // Выбрасываем предмет
    public void dropItem(LinkedList<Item> list, int number) {
        if (number == -1) return;
		if (getMap().field[getY()][getX()].getChest() > Tile.NONE && !getMap().field[getY()][getX()].getOpened()) {
			getGame().logMessage("#2#Вы не можете положить это в закрытый сундук!#^#");
			getGame().refresh();
			return;
		}
        list.addLast(getInventory().get(number));
        getGame().logMessage("#8#Выброшено!#^# (" + getInventory().get(number).getName().toLowerCase() + "#1#)");
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() - getInventory().get(number).getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() - getInventory().get(number).getSize());
        getInventory().remove(number);
    }

    // Просто снимаем надетую вещь
    public void takeOffItem(int num) {
        getGame().logMessage("Вы #8#сняли#^# это (#7#" + Equipment[num].getName().toLowerCase() + "#^#)");
        deleteEffectFrom(ScriptParser.parseString(Equipment[num].getScript()), Equipment[num].isIdentify());
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() + Equipment[num].getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() + Equipment[num].getSize());
        getInventory().add(Equipment[num]);
        Equipment[num] = null;
    }
	
    // Подъем уровня игрока
    public void levelUp() {
        // если игрок достиг максимального уровня, то уровень не поднимаем
        if (getLevel() == MAX_PLAYER_LEVEL) {setExp(0); return;}
        // иначе поднимаем уровень
		setLevel(getLevel() + 1);
        setExp(getExp() - maxExperience);
        maxExperience *= 2;
        getGame().logMessage("Вы достигли следующего уровня! ");
        statsFree += 3;
    }

	// Едим еду
    public void eatItem(LinkedList<Item> list, int number) {
        if (number == -1) return;

        if (getInventory().get(number).getType() != ItemSet.TYPE_FOOD) {
            getGame().logMessage(getInventory().get(number).getName() + "#^# - это нельзя съесть!");
            return;
        }

        getGame().logMessage("#8#Съедено!#^# (" + getInventory().get(number).getName().toLowerCase() + "#^#)");
        identifyItem(getInventory().get(number));
        setEffectFrom(ScriptParser.parseString(getInventory().get(number).getScript()), getInventory().get(number).isIdentify());
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() - getInventory().get(number).getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() - getInventory().get(number).getSize());
        getInventory().remove(number);
    }

    // идентифицирует неопознанные предметы: зелья, свитки и т.д.
    public void identifyItem(int number) {
        if (number == -1) return;

        if (getInventory().get(number).isIdentify()) {
            getGame().logMessage(getInventory().get(number).getName() + "#^# - это уже опознано!");
            return;
        }
        getGame().logMessage("#8#Идентифицировано!#^# (" + getInventory().get(number).getName().toLowerCase() + "#^#)");
        // пробегаем по всему инвентарю в поисках аналогичных вещей и все их идентифицируем
        for (int i = 0; i < getGame().itemsQuantity; i++) {
            if (getGame().itemList[i] != null && getGame().itemList[i] != getInventory().get(number)) {
                if (getGame().itemList[i].getID() == getInventory().get(number).getID() && !getGame().itemList[i].isIdentify()) {
                    getGame().itemList[i].swap_names();
                    getGame().itemList[i].setIdentify(true);
                }
            }
        }

        ItemSet.ID_ITEMS[getInventory().get(number).getID()] = 1;
        String textLine = "Вы поняли, что " + getInventory().get(number).getName().toLowerCase();
        getInventory().get(number).swap_names();
        textLine += " - на самом деле: " + getInventory().get(number).getName().toLowerCase();
        getGame().logMessage(textLine);
        getInventory().get(number).setIdentify(true);
    }

    // идентифицирует неопознанные предметы: зелья, свитки и т.д.
    // выше по классу есть еще один метод идентификации, но по номеру вещи
    public void identifyItem(Item item) {
        if (item.isIdentify()) {
            return;
        }

        ItemSet.ID_ITEMS[item.getID()] = 1;
        for (int i = 0; i < getGame().itemsQuantity; i++) {
            if (getGame().itemList[i] != null) {
                if (getGame().itemList[i].getID() == item.getID() && !getGame().itemList[i].isIdentify()) {
                    getGame().itemList[i].swap_names();
                    getGame().itemList[i].setIdentify(true);
                }
            }
        }

        String textLine;
        item.swap_names();
        textLine = "Вы поняли, что это на самом деле " + new String(item.getName().toLowerCase()) + "!";
        getGame().logMessage(textLine);
        item.setIdentify(true);
    }

    public void readItem(LinkedList<Item> list, int number) {
        if (number == -1) return;

        if (getInventory().get(number).getType() != ItemSet.TYPE_SCROLL) {
            getGame().logMessage(getInventory().get(number).getName() + "#^# - это нельзя прочитать!");
            return;
        }

        getGame().logMessage("#8#Прочитано!#^# (" + getInventory().get(number).getName().toLowerCase() + "#^#)");
        identifyItem(getInventory().get(number));
        setEffectFrom(ScriptParser.parseString(getInventory().get(number).getScript()), getInventory().get(number).isIdentify());
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() - getInventory().get(number).getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() - getInventory().get(number).getSize());
        getInventory().remove(number);
    }

    // метод реализует возможность одеть выбранную вещь
    public void equipItem(LinkedList<Item> list, int number) {
        if (number == -1) return;

        if (getInventory().get(number).getSlot() == ItemSet.SLOT_ANY) {
            getGame().logMessage("#8#" + getInventory().get(number).getName() + "#^# - это нельзя надеть!");
            return;
        }

        // если игрок пытается одеть вещь в тот слот, в котором уже что-то есть, то снимаем эту вещь
        // перед тем как одеть новую
        Item li = null;
        if (Equipment[getInventory().get(number).getSlot()] != null)
            li = Equipment[getInventory().get(number).getSlot()];
        if (li != null) {
            getGame().logMessage("Вы #8#сняли#^# это (" + li.getName().toLowerCase() + "#^#)");
            // снимаем эффекты старой вещи
            deleteEffectFrom(ScriptParser.parseString(li.getScript()), li.isIdentify());
            getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() + li.getMass());
            getCurrentSize().setCurrent(getCurrentSize().getCurrent() + li.getSize());
        }
        getGame().logMessage("Вы #8#надели#^# это (" + getInventory().get(number).getName().toLowerCase() + "#^#)");
        // даем эффекты новой вещи
        setEffectFrom(ScriptParser.parseString(getInventory().get(number).getScript()), getInventory().get(number).isIdentify());
        identifyItem(getInventory().get(number));
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() - getInventory().get(number).getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() - getInventory().get(number).getSize());
        if (li != null) getInventory().add(li); // если предварительно была снята вещь, то добавляем ее в инвентарь
        Equipment[getInventory().get(number).getSlot()] = getInventory().get(number);
        getInventory().remove(number); // удаляем надетую вещь из инвентаря
    }
	
    // метод осмотра вещи, например из инвентаря по нажатию Enter
    public void examineItem(Item item) {
        getGame().logMessage("Вы осмотрели предмет (" + item.getName().toLowerCase() + "#^#)");
        getGame().frame1.setFocusable(false);
        getGame().frame1.setFocusableWindowState(false);
        DescWindow frame2 = new DescWindow(getGame(), item);
        frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame2.setLocation(getGame().frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, getGame().frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
        frame2.toFront();
        frame2.setTitle("Информация о предмете");
        frame2.setVisible(true);
    }

    public void disassembledItem(int number)
    {
        if (number == -1) return;

        if (!getInventory().get(number).getDestroyable()) {
            getGame().logMessage(getInventory().get(number).getName() + "#^# - это нельзя разобрать!");
            return;
        }

        getGame().logMessage("#8#Разобрано!#^# (" + getInventory().get(number).getName().toLowerCase() + "#^#)");
        getCurrentWeight().setCurrent(getCurrentWeight().getCurrent() - getInventory().get(number).getMass());
        getCurrentSize().setCurrent(getCurrentSize().getCurrent() - getInventory().get(number).getSize());
        getInventory().get(number).makeLoot(getMap(), getInventory().get(number).getLoot()); // вызов метода высчитывающего какой лут упадет
        getInventory().remove(number); // удаляем разобранный предмет
        return;
    }

	// добавить предмет в инвентарь
	public void addItemToInventory(int id, int amount){
        BaseItem baseItem = ItemSet.getItem(id);
        for(int i = 0; i < amount; i++) {
            getGame().itemList[getGame().itemsQuantity] = new Item(baseItem, getMap(), getGame()); // этот конструктор сразу размещает созданный предмет в инвенторе игрока
            getGame().itemsQuantity++;
        }
	}
	
	// удалить предмет из инвентаря
	public boolean removeFromInventory(int id, int amount){
        if (howMuchItemPlayerHave(id) < amount) { // у игрока меньше итемов, чем недо
            getGame().logMessage("Недостаточно ресурсов.");
            return false;
        }
        Item item = null;
        for (int index = 0; index < getInventory().size(); index++)
        {
            item = getInventory().get(index);
            if (id == item.getID()) {
                getInventory().remove(index);
                amount--;
                if (index != 0) index--;
            }
            if (amount == 0) break;
        }
        getGame().logMessage("Предмет" + item.getRealName() + " удален");
        return true;
	}
	
    // метод считает сколько итемов с идентификатором id есть в инвентаре игрока
    public int howMuchItemPlayerHave(int id)
    {
        int haveRes = 0;
        Item item;
            for (int index = 0; index < getInventory().size(); index++)
            {
                item = getInventory().get(index);
                if (id == item.getID()) {
                    haveRes++;
                }
            }
        return haveRes;
    }

    // метод убирает необходимое кол-во итемов из инвентаря игрока
    public boolean removeItemForCraft(int needRes, int id)
    {
        if (howMuchItemPlayerHave(id) < needRes) { // у игрока меньше итемов, чем недо
            getGame().logMessage("Недостаточно ресурсов.");
            return false;
        }
        Item item;
            for (int index = 0; index < getInventory().size(); index++)
            {
                item = getInventory().get(index);
                if (id == item.getID()) {
                    getInventory().remove(index);
                    needRes--;
                    if (index != 0) index--;
                }
                if (needRes == 0) break;
            }
        getGame().logMessage("Крафт успешно завершен.");
        return true;
    }

    // добавлят итем на текущую позицию игрока
    public void addItemOnCurrentPlayerPosition(int id)
    {
        getGame().addItem(getY(), getX(), id, getMap());
    }

    // Здоровье зависит от силы и выносливости
    public int calcHP(int STR, int END) {
        return (END * getGame().HIT_POINTS_PER_ENDURANCE) + (STR * getGame().HIT_POINTS_PER_STRENGTH);
    }

    // Мана зависит от интеллекта
    public int calcMP(int INT) {
        return INT * getGame().MANA_PER_INTELLECT;
    }

    // Нагрузка зависит от силы
    public int calcCarrying(int STR) {
        return STR * getGame().CARRYING_PER_STRENGTH;
    }

    // Пересчитываем статы
	public void calcStats(){
        getHP().setMax(calcHP(getSTR().getCurrent(), getEND().getCurrent()));
        getHP().setCurrent(calcHP(getSTR().getCurrent(), getEND().getCurrent()));
        getMP().setMax(calcMP(getINT().getCurrent()));
        getMP().setCurrent(calcMP(getINT().getCurrent()));
        getCurrentWeight().setMax(calcCarrying(getSTR().getCurrent()));
	}
	
    // Даем игроку расу, которую он выбрал
    public void setRace(int id) {
        setSTR(RaceSet.getRace(id).getSTR(), RaceSet.getRace(id).getSTR());
        setAGI(RaceSet.getRace(id).getAGI(), RaceSet.getRace(id).getAGI());
        setEND(RaceSet.getRace(id).getEND(), RaceSet.getRace(id).getEND());
        setINT(RaceSet.getRace(id).getINT(), RaceSet.getRace(id).getINT());
        setLUCK(RaceSet.getRace(id).getLUCK(), RaceSet.getRace(id).getLUCK());
		calcStats();
        Skill.add(RaceSet.getRace(id).getSkill());
    }

    // Теперь игрок будет этой специализации
    public void setClass(int id) {
		int STR  = RaceSet.getRace(RaceSet.getCurrentRaceID).getSTR()  + ClassSet.getClass(id).getSTR();
		int AGI  = RaceSet.getRace(RaceSet.getCurrentRaceID).getAGI()  + ClassSet.getClass(id).getAGI();
		int END  = RaceSet.getRace(RaceSet.getCurrentRaceID).getEND()  + ClassSet.getClass(id).getEND();
		int INT  = RaceSet.getRace(RaceSet.getCurrentRaceID).getINT()  + ClassSet.getClass(id).getINT();
		int LUCK = RaceSet.getRace(RaceSet.getCurrentRaceID).getLUCK() + ClassSet.getClass(id).getLUCK();
        setSTR(STR, STR);
        setAGI(AGI, AGI);
        setEND(END, END);
        setINT(INT, INT);
        setLUCK(LUCK, LUCK);
		calcStats();
		for(int s=1;s<=3;s++)
			Skill.add(ClassSet.getClass(id).getSkill(s));
    }

	public String getPath(int raceID, int classID){
        String path = "res/heroes/" + RaceSet.getRace(raceID).getPath() + "/" + ClassSet.getClass(classID).getPath() + ".png";
        if (new File(path).exists()) { // .jar?
            return path;
        } else {
            return "res/heroes/unknown.png";
        }
	}
	
}