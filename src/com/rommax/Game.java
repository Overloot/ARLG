package com.rommax;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;


public class Game {
    public Monster player;
    private Map map;
    public KeyHandler keyHandler;
    public Monster[] monsterList;                           // игрок по индексу [0]
    public Item[] itemList;
    public Map[] mapList;
    public GameWindow frame1;
    public int monstersQuantity;
    public int itemsQuantity;
    public int currentMapNumber = 0;
    public int turn = 0;
    public static int maxExperience = 200;
    public int statsFree = 0;
    public int hitPointsRegeneration = 0;                   //later reghp
	public Loader loader;
	public Image background;
	public Image cursor;

    // FINAL'изированные
    final int minFovRad = 4;
    final int MOVE_COST_ActionPoints = 10;
    public final int MAP_SIZE_Y = 30;
    public final int MAP_SIZE_X = 30;
    final int MAX_FLOORS = 3;
    public static final int MAX_MONSTER_PER_LEVEL = 5;
    public static final int MAX_ITEM_PER_LEVEL = 5;
    public static final int MAX_PLAYER_LEVEL = 30;
    public final int HIT_POINTS_PER_STRENGTH = 3;
    public final int CARRYING_PER_STRENGTH = 7;
    public final int MIN_SIZE = 100;
	public final int MAX_LOOT = 3;
    public final int HIT_POINTS_PER_ENDURANCE = 9;
    public final int MAX_MONSTERS = MAX_FLOORS * MAX_MONSTER_PER_LEVEL + 1;
    public final int MAX_ITEMS = MAX_FLOORS * MAX_ITEM_PER_LEVEL + 1 + (MAX_MONSTERS * MAX_LOOT);

	public Map getMap(){return map;}	

    // выводит различные сообщения в верхний-левый угол
    public void logMessage(String s) {
        frame1.mainpanel.LogMessage(s);
    }

    // инициализация вызывается перед запуском метода run из main
    public void init() {
        monstersQuantity = 0;
        itemsQuantity = 0;
        monsterList = new Monster[MAX_MONSTERS];
        itemList = new Item[MAX_ITEMS];
        mapList = new Map[MAX_FLOORS];
		loader = new Loader();
		background = loader.getImage("res/icons/texture_menu.png");
		cursor = loader.getImage("res/icons/icon_plus.png");
    }

    // попытка открыть или закрыть что-то, точно вызывается из класса KeyHandler
    public void tryToOpenSomething(boolean isPlayer, int ny, int nx, boolean mode) {
        if (!map.hasTileAt(ny, nx) || !map.field[ny][nx].getOpenable()) {
            if (isPlayer)
                if (mode)
                    map.getGame().logMessage("Там нечего открыть!#/#");
                else
                    map.getGame().logMessage("Там нечего закрыть!#/#");
            return;
        } else if (map.field[ny][nx].getOpened() == mode) {
            if (isPlayer)
                if (mode)
                    map.getGame().logMessage("Это уже открыто!#/#");
                else
                    map.getGame().logMessage("Это уже закрыто!#/#");
            return;
        }


        if (mode)
            map.setTileAt(ny, nx, Tileset.TILE_OPENED_DOOR);
        else
            map.setTileAt(ny, nx, Tileset.TILE_CLOSED_DOOR);

        if (isPlayer)
            if (mode)
                map.getGame().logMessage("Вы открыли это. #/#");
            else
                map.getGame().logMessage("Вы закрыли это. #/#");

    }

	// AI для мостра
    public void monstersAI() {
        turn++;
        // пробегаемся по всем монстрам
        for (int index = 1; index < monstersQuantity; index++) {
            Random random = new Random();
            int dy = random.nextInt(3) - 1;
            int dx = random.nextInt(3) - 1;
            // если монстр сдох или его почему то нет в массиве, но переходит к следующему индексу
            if (monsterList[index] == null) continue;
			monsterList[index].getHP().setCurrent(Util.clamp(monsterList[index].getHP().getCurrent(), 0, monsterList[index].getHP().getMax()));
			if (monsterList[index].getParalyzeCount() > 0) continue;
            // если у монстра хватает ActionPoints то он двигается
            while (monsterList[index].getAP().getCurrent() > 0) {
                // если цель(игрок) находится в зоне видимости монстра, но монстр идет к нему
                if (Util.checkDistance(player.getY(), player.getX(), monsterList[index].getY(), monsterList[index].getX()) <= monsterList[index].getFOVRAD().getCurrent())
                { monsterList[index].move(Util.defineDirection(player.getY() - monsterList[index].getY()), Util.defineDirection(player.getX() - monsterList[index].getX())); }
                // иначе монстр идет по случайнно заданным(выше) dx & dy, которые могут принимать значения -1,0,1
                else { monsterList[index].move(dy, dx); }
                monsterList[index].getAP().setCurrent(monsterList[index].getAP().getCurrent() - MOVE_COST_ActionPoints);
            }

            // если у монстра нет AP или они меньше 0, то к нему прибавляется дефолтное значение.
            // Если AP больше дефолта, то устанавливается равному дефолту.
            // бессмыслено и беспощадно? легче же сразу к дефолту приравнять без всяких проверок.
            if (monsterList[index].getAP().getCurrent() <= 0) {
                monsterList[index].getAP().setCurrent(monsterList[index].getAP().getCurrent() + monsterList[index].getAP().getMax());
                if (monsterList[index].getAP().getCurrent() > monsterList[index].getAP().getMax())
                    monsterList[index].getAP().setCurrent(monsterList[index].getAP().getMax());
                continue;
            }

        }
        // Проверяем, жив ли монстр
		for (int i = 1; i < monstersQuantity; i++) {
            if (monsterList[i] != null)
                if (monsterList[i].getHP().getCurrent() <= 0)
                    killMonster(i);
        }
        checkTimeEffects(); // метод проверят не пора ли поднять уровень игроку, наносит урон ядом и т.д.

    }

	// Диалог закрытия игры
	void checkQuit() {
		frame1.setFocusable(false);
		frame1.setFocusableWindowState(false);
        QuitWindow frame1 = new QuitWindow(map.getGame());
        frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame1.setLocation(map.getGame().frame1.WINDOW_WIDTH / 2 - frame1.WINDOW_WIDTH / 2, map.getGame().frame1.WINDOW_HEIGHT / 2 - frame1.WINDOW_HEIGHT / 2);
        frame1.toFront();
        frame1.setVisible(true);		
	}
	
    // проверить изменялся ли Stat в худшую сторону, если так то вывести первую строку, иначе вторую
    void checkStatChanges(Stat s, String s1, String s2) {
        if (s.getTimer() > 0) {
            s.subTimer();
            if (s.getTimer() == 0) {
                if (s.getCurrent() < s.getMax())
                    logMessage(s1);
                else
                    logMessage(s2);
                s.setCurrent(s.getMax());
            }
        }
    }

    // временные эффекты.
    void checkTimers(Monster m) {
        checkStatChanges(m.getSTR(), "Вы снова стали #3#сильнее!#^#", "Вы снова стали #2#слабее!#^#");

        if (m.getEND().getTimer() == 1) {
            m.getHP().setMax(m.getHP().getMax() + HIT_POINTS_PER_ENDURANCE * (m.getEND().getMax() - m.getEND().getCurrent()));
            m.getHP().setCurrent(m.getHP().getCurrent() + HIT_POINTS_PER_ENDURANCE * (m.getEND().getMax() - m.getEND().getCurrent()));
        }

        if (m.getSTR().getTimer() == 1) {
            m.getHP().setMax(m.getHP().getMax() + HIT_POINTS_PER_STRENGTH * (m.getSTR().getMax() - m.getSTR().getCurrent()));
            m.getHP().setCurrent(m.getHP().getCurrent() + HIT_POINTS_PER_STRENGTH * (m.getSTR().getMax() - m.getSTR().getCurrent()));
            m.getCurrentWeight().setMax(m.getCurrentWeight().getMax() + CARRYING_PER_STRENGTH * (m.getSTR().getMax() - m.getSTR().getCurrent()));
        }
		
        checkStatChanges(m.getAGI(), "Вы снова стали более #3#ловким!#^#", "Вы снова стали менее #2#ловким!#^#");
        checkStatChanges(m.getEND(), "Вы снова стали #3#выносливее!#^#", "Вы снова стали менее #2#выносливее!#^#");
        checkStatChanges(m.getLUCK(), "Вы снова стали #3#удачливее!#^#", "Вы снова стали менее #2#удачливее!#^#");
        checkStatChanges(m.getRFire(), "Вы снова стали #3#сильнее#^# сопротивляться #2#огню!#^#", "Вы снова стали #2#слабее#^# сопротивляться #2#огню!#^#");
        checkStatChanges(m.getRCold(), "Вы снова стали #3#сильнее#^# сопротивляться #4#холоду!#^#", "Вы снова стали #2#слабее#^# сопротивляться #4#холоду!#^#");
        checkStatChanges(m.getRElec(), "Вы снова стали #3#сильнее#^# сопротивляться #5#электричеству!#^#", "Вы снова стали #2#слабее#^# сопротивляться #5#электричеству!#^#");
        checkStatChanges(m.getRNormal(), "Вы снова стали #3#сильнее#^# сопротивляться #8#ударам!#^#", "Вы снова стали #2#слабее#^# сопротивляться #8#ударам!#^#");
        checkStatChanges(m.getRPoison(), "Вы снова стали #3#сильнее#^# сопротивляться #3#яду!#^#", "Вы снова стали #2#слабее#^# сопротивляться #3#яду!#^#");
        checkStatChanges(m.getFOVRAD(), "Вы снова стали #3#лучше#^# видеть!#^#", "Вы снова стали #2#хуже#^# видеть!#^#");
        checkStatChanges(m.getAddHP(), "Вы #3#исцеляетесь#^#!#^#", "Вы #2#теряете здоровье#^#!#^#");
		
		// Медленное исцеление
		if (m.getAddHP().getCurrent() != 0) m.getHP().setCurrent(m.getHP().getCurrent() + (m.getAddHP().getCurrent() * m.getLevel()));
		// Медленное вост. маны
		//if (m.getAddMP().getCurrent() != 0) m.getMP().setCurrent(m.getMP().getCurrent() + (m.getAddMP().getCurrent() * m.getLevel()));
			
    }

    //метод проверят не пора ли поднять уровень игроку, наносит урон ядом и т.д.
    void checkTimeEffects() {
        boolean x = false;
        // если у игрока хватает опыта для поднятия уровня, то поднимаем
        while (player.getExp() >= Game.maxExperience) {
            x = true;
            levelUp();
        }
        // если уровень был поднят, то рисуем окошко которое позволит распределить статы
        if (x) {
            frame1.setFocusable(false);
            frame1.setFocusableWindowState(false);
            LevelUpWindow frame1 = new LevelUpWindow(map.getGame());
            frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame1.setLocation(map.getGame().frame1.WINDOW_WIDTH / 2 - frame1.WINDOW_WIDTH / 2, map.getGame().frame1.WINDOW_HEIGHT / 2 - frame1.WINDOW_HEIGHT / 2);
            frame1.toFront();
            frame1.setVisible(true);

        }

        // пробегаемся по всем монстрам....
        for (int i = 0; i < monstersQuantity; i++) {
            if (monsterList[i] != null) {
                checkTimers(monsterList[i]); // проверка нет ли на нем каких эффектов
            }
            //восстанавливаем HitPoints
            if (monsterList[i] != null)
                if (hitPointsRegeneration < monsterList[i].getEND().getCurrent())
                    monsterList[i].getHP().setCurrent(monsterList[i].getHP().getCurrent() + 1);
            // если HitPoints больше положенного - к ногтю
            if (monsterList[i] != null && monsterList[i].getHP().getCurrent() > monsterList[i].getHP().getMax())
                monsterList[i].getHP().setCurrent(monsterList[i].getHP().getMax());
            // есди есть отравление - наносим урон и снимаем единицу отравления
            if (monsterList[i] != null && monsterList[i].getPoisonCount() > 0) {
                if (i == 0) logMessage("Вы очень #3#страдаете от яда! (1)#^#/#");
                monsterList[i].setPoisonCount(monsterList[i].getPoisonCount() - 1);
                monsterList[i].getHP().setCurrent((monsterList[i].getHP().getCurrent() - 1));
            }
            // если есть парализация - снимаем одно очко парализации
            if (monsterList[i] != null && monsterList[i].getParalyzeCount() > 0) {
                monsterList[i].setParalyzeCount(monsterList[i].getParalyzeCount() - 1);
            }

        }

        //TODO: зачем так?
        hitPointsRegeneration += 10;
        if (hitPointsRegeneration > 100) hitPointsRegeneration = 0;
    }

    // смена карты/переход на другой этаж, метод вызывается из класса KeyHandler
    // changeMapLevel может иметь значения -1 или 1
    public void switchMap(int changeMapLevel) {
        int newMapLevel = changeMapLevel + currentMapNumber;
        // если дальше положенного ходить нельзя
        if (newMapLevel < 0 || newMapLevel >= MAX_FLOORS) return;
        // если карты, на которую осуществляется переход, не существует, то создаем ее
        if (mapList[newMapLevel] == null) {
            mapList[newMapLevel] = new Map(MAP_SIZE_Y, MAP_SIZE_X, this);
		
			map.field[player.getY()][player.getX()].setMonster(null);
            map = mapList[newMapLevel];
            map.setGame(this);
            frame1.SwitchMap(map);
            currentMapNumber = newMapLevel;
            map.placeMonsterAt(player.getY(), player.getX(), player);
            player.setMap(map);

            while (!map.field[player.getY()][player.getX()].getPassable()) map.generate();
            fillLevelByMonsters();
            fillLevelByItems();
			finish();
        }
        // а если карта уже существует, то переносим на нее игрока
        else {

			map.field[player.getY()][player.getX()].setMonster(null);
            map = mapList[newMapLevel];
            map.setGame(this);
            frame1.SwitchMap(map);
            currentMapNumber = newMapLevel;
            map.placeMonsterAt(player.getY(), player.getX(), player);
            player.setMap(map);

		}
		if (changeMapLevel == 1)
			for (int i=0; i<map.getHeight(); i++)
				for (int j=0; j<map.getWidth(); j++){
					if (mapList[currentMapNumber - 1].field[i][j].getID() == Tileset.TILE_STAIR_DOWN) {
						if (map.field[i][j].getID() == Tileset.TILE_STAIR_UP) continue;
						if (map.field[i][j].getID() == Tileset.TILE_STAIR_DOWN) continue;
						map.setTileAt(i, j, Tileset.TILE_STAIR_UP);
					}
				}
    }

	//TODO: конец игры слишком уныл
	public void finish(){
        if (currentMapNumber == MAX_FLOORS - 1)
			logMessage("ВЫ ДОБРАЛИСЬ ДО ПОСЛЕДНЕГО УРОВНЯ И ВЫИГРАЛИ ИГРУ! СПАСИБО ЗА ТЕСТИРОВАНИЕ!");		
	}
	
    // этот метод вызывается из main вторым, сразу после Game.init()
    public void run() {
        mapList[0] = new Map(MAP_SIZE_Y, MAP_SIZE_X, this);                 // mapList класса Map
        map = mapList[0];                                                   // map это текущая карта, над ней происходят все операции
                                                                            // mapList нужен только для хранения карт
        currentMapNumber = 0;                                               // номер текущей карты в массиве
        map.generate();
	
        frame1 = new GameWindow(map, this);
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
        int y = 10;
        int x = 10;
        Random random = new Random();
        // field это массив класса Tile. По сути и есть сама карта.
        // в этом цикле while ищется подходящее место для "высадки" игрока
        while (!map.field[y][x].getPassable() || map.field[y][x].getMonster() != null) {
            y = random.nextInt(map.getHeight());
            x = random.nextInt(map.getWidth());
        }
        addMonster(MonsterSet.MONSTER_PLAYER, y, x, map);                   // "высадка" игрока
        player = monsterList[0];
		map.updatePlayer();
        fillLevelByMonsters();      // добавляет столько монстров, сколько положено на уровень ( MAX_MONSTER_PER_LEVEL )
        fillLevelByItems();         // добавляет столько итемов, сколько положено на уровень ( MAX_ITEM_PER_LEVEL )
        frame1.mainpanel.repaint();
		this.selectRace();
	}

	public void selectRace() {
		//frame1.setFocusable(false);
		//frame1.setFocusableWindowState(false);
		RaceWindow frame2 = new RaceWindow(this);
        frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame2.setLocation(frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
        frame2.toFront();
        frame2.setTitle("Выбор расы");
        frame2.setVisible(true);
	}
	
    public void fillLevelByMonsters() {
        for (int i = 0; i < MAX_MONSTER_PER_LEVEL; i++)
            addRandomMonster();
    }

    public void fillLevelByItems() {
        for (int i = 0; i < MAX_ITEM_PER_LEVEL; i++)
            addRandomItem();
    }

    // добавление монстра по заданным координатам. Этим методом производится "высадка" игрока
    public void addMonster(int id, int y, int x, Map map) {
        BaseMonster baseMonster = MonsterSet.getMonster(id);
        monsterList[monstersQuantity] = new Monster(baseMonster, y, x, map, this);
        monstersQuantity++;
    }

    public void addRandomMonster() {
        Random random = new Random();
        int newID;

        //определяет можно ли данному монстру появится на свет
        while (true) {
            newID = random.nextInt(MonsterSet.MAX_MONSTERS);
            // отставить разможение личности!
            if (newID == MonsterSet.MONSTER_PLAYER) continue;
            // чтоб не плодить слишком сильных для игрока монстров
            if (MonsterSet.getMonster(newID).getLevel() < currentMapNumber - 4 || MonsterSet.getMonster(newID).getLevel() > currentMapNumber + 4)
                continue;
            int chance = 90 - Math.abs(MonsterSet.getMonster(newID).getLevel() - currentMapNumber) * 20;                    // Math.abs возвращает модуль числа
            // не судьба
            if (!Util.dice(chance, 100)) continue; // см.ниже
            break;
        }
        BaseMonster baseMonster = MonsterSet.getMonster(newID);
        int y = 0;
        int x = 0;
        // ищем подходящее место для высадки монстра
        while (map.field[y][x].getPassable() == false || map.field[y][x].getMonster() != null) {
            y = random.nextInt(map.getHeight());
            x = random.nextInt(map.getWidth());
        }
        // высадка
        monsterList[monstersQuantity] = new Monster(baseMonster, y, x, map, this);
        monstersQuantity++;
    }

    // int number это message.number класса ItemSelectMessage
    // данный метод вызывается из KeyHandler
    public void tryToPickupItem(LinkedList<Item> list, int number) {
        if (number == -1) return;                                                                                               // как я понял, данная команда никогда не будет исполнена
        player.getInventory().addLast(list.get(number));                                                                        // обычно number равен нулю
        logMessage("#8#Взято!#^# (" + list.get(number).getName().toLowerCase() + "#1#)");
        player.getCurrentWeight().setCurrent(player.getCurrentWeight().getCurrent() + list.get(number).getMass());
        player.getCurrentSize().setCurrent(player.getCurrentSize().getCurrent() + list.get(number).getSize());
        list.remove(number);
    }

    // int number это message.number класса ItemSelectMessage
    // данный метод вызывается из KeyHandler
    public void tryToDropItem(LinkedList<Item> list, int number) {
        if (number == -1) return;
        list.addLast(player.getInventory().get(number));
        logMessage("#8#Выброшено!#^# (" + player.getInventory().get(number).getName().toLowerCase() + "#1#)");
        player.getCurrentWeight().setCurrent(player.getCurrentWeight().getCurrent() - player.getInventory().get(number).getMass());
        player.getCurrentSize().setCurrent(player.getCurrentSize().getCurrent() - player.getInventory().get(number).getSize());
        player.getInventory().remove(number);
    }

    // данный метод вызывается из KeyHandler, когда активен LOOK_MODE
    public void TryToLookAtMonster(int y, int x) {
        if (map.field[y][x].getMonster() == null && map.field[y][x].getItemList().size() == 0) {
            logMessage("Здесь ничего нет!");
            return;
        }
        if (map.field[y][x].getMonster() != null) {
            frame1.setFocusable(false);
            frame1.setFocusableWindowState(false);
            DescWindow frame2 = new DescWindow(this, map.field[y][x].getMonster());
            frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame2.setLocation(frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
            frame2.toFront();
            frame2.setTitle("Информация о монстре");
            frame2.setVisible(true);
        } else if (map.field[y][x].getItemList().size() != 0) {
            tryToExamineItem(map.field[y][x].getItemList(), 0);                                                             // если на клетке есть итемы, то будет показана инфа о них
        }
    }

    // метод описывает как надо пить =) quaff - пить залпом
    // вызывается из KeyHandler
    public void tryToQuaffItem(LinkedList<Item> list, int number) {
        if (number == -1) return;

        if (player.getInventory().get(number).getType() != ItemSet.TYPE_POTION) {
            logMessage("#8#" + player.getInventory().get(number).getName() + "#^# - это нельзя выпить!");
            return;
        }
        logMessage("#8#Выпито!#^# (" + player.getInventory().get(number).getName().toLowerCase() + "#^#)");
        tryToIdentifyItem(player.getInventory().get(number));                                                                   // еще не разу не питое пойло - не идентифицировано. Идентифицируем.
        player.getCurrentWeight().setCurrent(player.getCurrentWeight().getCurrent() - player.getInventory().get(number).getMass());
        player.getCurrentSize().setCurrent(player.getCurrentSize().getCurrent() - player.getInventory().get(number).getSize());
        player.setEffectFrom(ScriptParser.parseString(player.getInventory().get(number).getScript()), player.getInventory().get(number).isIdentify());
        player.getInventory().remove(number);
    }

    // вызывается из KeyHandler
    public void tryToReadItem(LinkedList<Item> list, int number) {
        if (number == -1) return;

        if (player.getInventory().get(number).getType() != ItemSet.TYPE_SCROLL) {
            logMessage(player.getInventory().get(number).getName() + "#^# - это нельзя прочитать!");
            return;
        }

        logMessage("#8#Прочитано!#^# (" + player.getInventory().get(number).getName().toLowerCase() + "#^#)");
        tryToIdentifyItem(player.getInventory().get(number));
        player.setEffectFrom(ScriptParser.parseString(player.getInventory().get(number).getScript()), player.getInventory().get(number).isIdentify());
        player.getCurrentWeight().setCurrent(player.getCurrentWeight().getCurrent() - player.getInventory().get(number).getMass());
        player.getCurrentSize().setCurrent(player.getCurrentSize().getCurrent() - player.getInventory().get(number).getSize());
        player.getInventory().remove(number);
    }

    // идентифицирует неопознанные предметы: зелья, свитки и т.д.
    // по номеру, ниже по классу есть еще метод идентификации по Item item
    public void tryToIdentifyItem(int number) {
        if (number == -1) return;

        if (player.getInventory().get(number).isIdentify()) {
            logMessage(player.getInventory().get(number).getName() + "#^# - это уже опознано!");
            return;
        }
        logMessage("#8#Идентифицировано!#^# (" + player.getInventory().get(number).getName().toLowerCase() + "#^#)");
        // пробегаем по всему инвентарю в поисках аналогичных вещей и все их идентифицируем
        for (int i = 0; i < itemsQuantity; i++) {
            if (itemList[i] != null && itemList[i] != player.getInventory().get(number)) {
                if (itemList[i].getID() == player.getInventory().get(number).getID() && !itemList[i].isIdentify()) {
                    itemList[i].swap_names();
                    itemList[i].setIdentify(true);
                }
            }
        }

        ItemSet.ID_ITEMS[player.getInventory().get(number).getID()] = 1;
        String textLine;
        textLine = "Вы поняли, что " + player.getInventory().get(number).getName().toLowerCase();
        player.getInventory().get(number).swap_names();
        textLine += " - на самом деле: " + player.getInventory().get(number).getName().toLowerCase();
        logMessage(textLine);
        player.getInventory().get(number).setIdentify(true);
    }

    // идентифицирует неопознанные предметы: зелья, свитки и т.д.
    // выше по классу есть еще один метод идентификации, но по номеру вещи
    public void tryToIdentifyItem(Item item) {
        if (item.isIdentify()) {
            return;
        }

        ItemSet.ID_ITEMS[item.getID()] = 1;
        for (int i = 0; i < itemsQuantity; i++) {
            if (itemList[i] != null) {
                if (itemList[i].getID() == item.getID() && !itemList[i].isIdentify()) {
                    itemList[i].swap_names();
                    itemList[i].setIdentify(true);
                }
            }
        }

        String textLine;
        item.swap_names();
        textLine = "Вы поняли, что это на самом деле " + new String(item.getName().toLowerCase()) + "!";
        logMessage(textLine);
        item.setIdentify(true);
    }

    // метод осмотра вещи, например из инвентаря по нажатию Enter
    // этот метод вызывается из KeyHandler
    public void tryToExamineItem(LinkedList<Item> list, int number) {
        if (number == -1) return;
        logMessage("Вы осмотрели предмет (" + list.get(number).getName().toLowerCase() + "#^#)");
        frame1.setFocusable(false);
        frame1.setFocusableWindowState(false);
        DescWindow frame2 = new DescWindow(this, list.get(number));
        frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame2.setLocation(frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
        frame2.toFront();
        frame2.setTitle("Информация об предмете");
        frame2.setVisible(true);
    }

    // метод осмотра вещи, например из инвентаря по нажатию Enter
    public void tryToExamineItem(Item item) {
        logMessage("Вы осмотрели предмет (" + item.getName().toLowerCase() + "#^#)");
        frame1.setFocusable(false);
        frame1.setFocusableWindowState(false);
        DescWindow frame2 = new DescWindow(this, item);
        frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame2.setLocation(frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
        frame2.toFront();
        frame2.setTitle("Информация об предмете");
        frame2.setVisible(true);
    }

    // метод реализует возможность одеть выбранную вещь
    public void tryToEquipItem(LinkedList<Item> list, int number) {
        if (number == -1) return;

        if (player.getInventory().get(number).getSlot() == ItemSet.SLOT_ANY) {
            logMessage("#8#" + player.getInventory().get(number).getName() + "#^# - это нельзя надеть!");
            return;
        }

        // если игрок пытается одеть вещь в тот слот, в котором уже что-то есть, то снимаем эту вещь
        // перед тем как одеть новую
        Item li = null;
        if (player.Equipment[player.getInventory().get(number).getSlot()] != null)
            li = player.Equipment[player.getInventory().get(number).getSlot()];
        if (li != null) {
            logMessage("Вы #8#сняли#^# это (" + li.getName().toLowerCase() + "#^#)");
            // снимаем эффекты старой вещи
            player.deleteEffectFrom(ScriptParser.parseString(li.getScript()), li.isIdentify());
            player.getCurrentWeight().setCurrent(player.getCurrentWeight().getCurrent() + li.getMass());
            player.getCurrentSize().setCurrent(player.getCurrentSize().getCurrent() + li.getSize());
        }
        logMessage("Вы #8#надели#^# это (" + player.getInventory().get(number).getName().toLowerCase() + "#^#)");
        // даем эффекты новой вещи
        player.setEffectFrom(ScriptParser.parseString(player.getInventory().get(number).getScript()), player.getInventory().get(number).isIdentify());
        tryToIdentifyItem(player.getInventory().get(number));
        player.getCurrentWeight().setCurrent(player.getCurrentWeight().getCurrent() - player.getInventory().get(number).getMass());
        player.getCurrentSize().setCurrent(player.getCurrentSize().getCurrent() - player.getInventory().get(number).getSize());
        if (li != null) player.getInventory().add(li); // если предварительно была снята вещь, то добавляем ее в инвентарь
        player.Equipment[player.getInventory().get(number).getSlot()] = player.getInventory().get(number);
        player.getInventory().remove(number); // удаляем надетую вещь из инвентаря
    }

    // просто снимаем надетую вещь
    public void TryToTakeOffItem(int num) {
        logMessage("Вы #8#сняли#^# это (" + player.Equipment[num].getName().toLowerCase() + "#^#)");
        player.deleteEffectFrom(ScriptParser.parseString(player.Equipment[num].getScript()), player.Equipment[num].isIdentify());
        player.getCurrentWeight().setCurrent(player.getCurrentWeight().getCurrent() + player.Equipment[num].getMass());
        player.getCurrentSize().setCurrent(player.getCurrentSize().getCurrent() + player.Equipment[num].getSize());
        player.getInventory().add(player.Equipment[num]);
        player.Equipment[num] = null;
    }

    // Добавляем предмет на карту в указанные координаты
    public void addRandomItem(int y, int x) {
		if (itemsQuantity >= MAX_ITEMS) return;
        Random random = new Random();
        int newID = 0;
		while (true) {
            newID = random.nextInt(ItemSet.MAX_ITEMS);
            // выпавший итем не должен быть уровнем выше чем
            if (ItemSet.getItem(newID).getLevel() > currentMapNumber + 4) continue;
            // шанс выпадения выбранного итема на текущем уровне карты
            int chance = 90 - Math.abs(ItemSet.getItem(newID).getLevel() - currentMapNumber) * 20;
            if (chance > 100) chance = 80;
            if (!Util.dice(chance, 100)) continue;                                                                               // метод dice описан в самом начале класса Game
            if (!Util.dice(ItemSet.getItem(newID).getChanse(), 100)) continue;
            break;
        }
		BaseItem baseItem = ItemSet.getItem(newID);
        itemList[itemsQuantity] = new Item(baseItem, y, x, map, this);
        itemsQuantity++;
    }

    // Добавляем предмет на карту в случайном месте
    public void addRandomItem() {
        Random random = new Random();
        // выбираем место сброса итема, если оно непроходимо или на нем есть монстр, то ищем другое место
        int y = random.nextInt(map.getHeight());
        int x = random.nextInt(map.getWidth());
        while (map.field[y][x].getPassable() == false || map.field[y][x].getMonster() != null) {
            y = random.nextInt(map.getHeight());
            x = random.nextInt(map.getWidth());
        }
		addRandomItem(y, x);
	}
	
	// Ставим предмет(ы) после смерти монстра (loot)
	private void loot(int index) {
        Random r = new Random();
		for(int i = 0; i < MAX_LOOT; i++)
			if (Util.dice(player.getLUCK().getCurrent(), 100))
				addRandomItem(monsterList[index].getY(), monsterList[index].getX());
	}

    // подъем уровня игрока
    public void levelUp() {
        // если игрок достиг максимального уровня, то уровень не поднимаем
        if (player.getLevel() == Game.MAX_PLAYER_LEVEL) {
            player.setExp(0);
            return;
        }
        // иначе поднимаем уровень
        player.setLevel(player.getLevel() + 1);
        player.setExp(player.getExp() - Game.maxExperience);
        Game.maxExperience *= 2;
        logMessage("Вы достигли следующего уровня! ");
        statsFree += 3;
    }

	// Обновление экрана
	public void refresh() {
		keyHandler.map.FOV(player.getY(), player.getX(), player.getFOVRAD().getCurrent());
		keyHandler.mp.repaint();
		keyHandler.keyPressed(null);
	}
	
    // метод описывает убийство монстра
    public void killMonster(int index) {
        logMessage(monsterList[index].getName() + " #2#УМИРАЕТ В МУКАХ#^#!!!#/#");
        // присваиваем map параметры той карты, на которой расположен монстр
        Map map = monsterList[index].getMap();
        // ставим кровь на месте смерти монстра
        map.placeBloodAt(monsterList[index].getY(), monsterList[index].getX());
		// Шанс выкинуть на землю предмет(ы)
		loot(index);
        // убираем монстра с карты
        map.deleteMonsterAt(monsterList[index].getY(), monsterList[index].getX());
        // модификатор получаемого количества опыта за убитого монстра
        int mod;
        // если игрок убивает равного себе монстра или монстра сильнее себя, то получает модификатор соответствующий уровню убитого монстра
        if (player.getLevel() <= monsterList[index].getLevel()) mod = monsterList[index].getLevel();
        // иначе модификатор равен единице
        else mod = 1;
        // высчитывает сколько же игрок получит опыта
        int Exp = Util.rand((int) (0.75 * (mod * mod) * 10), (int) (1.5 * (mod * mod) * 10));
        player.setExp(player.getExp() + Exp);
        logMessage("Вы получаете " + Integer.toString(Exp) + " опыта! ");
        // удаляем монстра
        monsterList[index] = null;
    }

	// Здоровье зависит от силы и выносливости
	public int calcHP(int STR, int END) {
		return (END * HIT_POINTS_PER_ENDURANCE) + (STR * HIT_POINTS_PER_STRENGTH);
	}
	
	// Нагрузка зависит от силы
	public int calcCarrying(int STR) {
		return STR * CARRYING_PER_STRENGTH;
	}
	
	// Даем игроку расу, которую он выбрал
	public void setRace(int id) {
		player.setSTR(RaceSet.getRace(id).getSTR(), RaceSet.getRace(id).getSTR());
		player.setAGI(RaceSet.getRace(id).getAGI(), RaceSet.getRace(id).getAGI());
		player.setEND(RaceSet.getRace(id).getEND(), RaceSet.getRace(id).getEND());
		player.setLUCK(RaceSet.getRace(id).getLUCK(), RaceSet.getRace(id).getLUCK());
		// Пересчитываем статы
		player.getHP().setMax(calcHP(player.getSTR().getCurrent(), player.getEND().getCurrent())); 
		player.getHP().setCurrent(calcHP(player.getSTR().getCurrent(), player.getEND().getCurrent())); 
		player.getCurrentWeight().setMax(calcCarrying(player.getSTR().getCurrent()));
		// Даем расовый навык
		Skill.add(RaceSet.getRace(id).getSkill());
	}

    // для удобства =)
    public void done() {
    }

}