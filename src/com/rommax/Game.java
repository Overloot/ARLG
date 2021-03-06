package com.rommax;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.font.*;

public class Game {

    public Player player;
    private Time time;
    private Map map;
    public GameKeyHandler keyHandler;
    public Monster[] monsterList;                           // игрок по индексу [0]
    public Item[] itemList;
    public Map[] mapList;
    public GameWindow frame1;
    public int monstersQuantity;
    public int itemsQuantity;
    public int mapsQuantity;
    public int currentMapNumber = 0;
    public int turn = 0;
    public int lifePointsRegeneration = 0;
    public int manaPointsRegeneration = 0;
    public Loader loader;
    public Image background;
    public Image cursor;

    // FINAL'изированные
    final int minFovRad = 4;
    final int MOVE_COST_ActionPoints = 10;
    public static final int MAP_SIZE_Y = 30;
    public static final int MAP_SIZE_X = 30;
    final int MAX_FLOORS = 5;
    public static final int MAX_PARALYZE_COUNT = 20;
    public static final int MAX_MONSTER_PER_LEVEL = 7;
    public static final int MAX_ITEM_PER_LEVEL = 5;
    public static final int MAX_TRAP_PER_LEVEL = 25;
    public static final int MAX_CHEST_PER_LEVEL = 25;
    public final int HIT_POINTS_PER_STRENGTH = 3;
    public final int CARRYING_PER_STRENGTH = 7;
    public final int MIN_SIZE = 100;
    public final int MAX_LOOT = 3;
    public final int HIT_POINTS_PER_ENDURANCE = 9;
	public final int MANA_PER_INTELLECT = 14;
    public final int MAX_MONSTERS = MAX_FLOORS * MAX_MONSTER_PER_LEVEL + 1;
    public final int MAX_ITEMS = MAX_FLOORS * MAX_ITEM_PER_LEVEL + (MAX_CHEST_PER_LEVEL * MAX_LOOT) + 1 + (MAX_MONSTERS * MAX_LOOT);

    public Map getMap() {
        return map;
    }

    public Time getTime() {
        return time;
    }

    // инициализация вызывается перед запуском метода run из main
    public void init() {
        monstersQuantity = 0;
        itemsQuantity = 0;
        mapsQuantity = 0;
        monsterList = new Monster[MAX_MONSTERS];
        itemList = new Item[MAX_ITEMS];
        mapList = new Map[MAX_FLOORS];
        loader = new Loader();
        time = new Time(this);
        background = loader.getImage("res/icons/texture_menu.png");
        cursor = loader.getImage("res/icons/check.png");
    }

    // Попытка открыть или закрыть что-то, вызывается из класса KeyHandler
    public void tryToOpenSomething(boolean isPlayer, int ny, int nx, boolean mode) {
        if (!map.hasTileAt(ny, nx) || !map.field[ny][nx].getOpenable()) {
            if (isPlayer) {
                if (mode) {
                    this.logMessage("Там нечего открыть!#/#");
                } else {
                    this.logMessage("Там нечего закрыть!#/#");
                }
            }
            return;
        } else if (map.field[ny][nx].getOpened() == mode) {
            if (isPlayer) {
                if (mode) {
                    this.logMessage("Это уже открыто!#/#");
                } else {
                    this.logMessage("Это уже закрыто!#/#");
                }
            }
            return;
        }
        if (mode && map.field[ny][nx].getLock()) {
			if (player.howMuchItemPlayerHave(ItemSet.getKeyID()) > 0) {
				map.field[ny][nx].setLock(false);
				logMessage("Вы открыли замок ключoм.#/#");
				player.removeFromInventory(ItemSet.getKeyID(), 1);
			} else {
				this.logMessage("#2#Это не возможно открыть без ключа! #^#");
				return;
			}
        }
        if (map.field[ny][nx].getChest() > Tile.NONE) {
            map.field[ny][nx].setOpened(mode);
        } else if (mode) {
            map.setTileAt(ny, nx, Tileset.TILE_OPENED_DOOR);
        } else {
            map.setTileAt(ny, nx, Tileset.TILE_CLOSED_DOOR);
        }
        if (isPlayer) {
            if (mode) {
                this.logMessage("Вы открыли это. #/#");
            } else {
                this.logMessage("Вы закрыли это. #/#");
            }
        }
    }

    // Попытка добыть что-то, вызывается из класса KeyHandler
    // метод прендазначен для разбора объектов типа tile - деревья, стены, камни и т.д.
    public boolean tryToGatheringSomething(int ny, int nx) {
        try {
            if (!Tileset.getTile(map.field[ny][nx].getID()).getDestroyable()) {
                this.logMessage("Это нельзя добывать");
                return false;
            }
            if (Tileset.getTile(map.field[ny][nx].getID()).gathering(map, ny, nx)) {
                this.logMessage("Ресурс добыт");
                return false;
            }
        } catch (NoSuchElementException e) {
            this.logMessage("Это нельзя добывать");
            return false;
        }
        return true;
    }

    // AI для мостра
    public void monstersAI() {
        turn++;
		boolean fmov;
		boolean flag = Skill.isShadowSkill(player.getY(), player.getX(), getMap());
        // пробегаемся по всем монстрам
        for (int index = 1; index < monstersQuantity; index++) {
            Random random = new Random();
            int dy = random.nextInt(3) - 1;
            int dx = random.nextInt(3) - 1;
            // если монстр сдох или его почему то нет в массиве, но переходит к следующему индексу
            if (monsterList[index] == null) {
                continue;
            }
            monsterList[index].getHP().setCurrent(Util.clamp(monsterList[index].getHP().getCurrent(), 0, monsterList[index].getHP().getMax()));
            if (monsterList[index].getParalyzeCount() > 0) continue;

            // если у монстра хватает ActionPoints, то он двигается
            while (monsterList[index].getAP().getCurrent() > 0) {
                // если цель (игрок) находится в зоне видимости монстра, но монстр идет к нему
                fmov = Util.checkDistance(player.getY(), player.getX(), monsterList[index].getY(), monsterList[index].getX()) <= monsterList[index].getFOVRAD().getCurrent();
				if (flag || monsterList[index].getTeam() == MonsterSet.PLAYER_TEAM) fmov = false;
				if (fmov)
					monsterList[index].move(Util.defineDirection(player.getY() - monsterList[index].getY()), Util.defineDirection(player.getX() - monsterList[index].getX()));
                    // иначе монстр просто бродит
						else monsterList[index].move(dy, dx);
				monsterList[index].getAP().setCurrent(monsterList[index].getAP().getCurrent() - MOVE_COST_ActionPoints);
            }

            // если у монстра нет AP или они меньше 0, то к нему прибавляется дефолтное значение.
            // Если AP больше дефолта, то устанавливается равному дефолту.
            // бессмыслено и беспощадно? легче же сразу к дефолту приравнять без всяких проверок.
            if (monsterList[index].getAP().getCurrent() <= 0) {
                monsterList[index].getAP().setCurrent(monsterList[index].getAP().getCurrent() + monsterList[index].getAP().getMax());
                if (monsterList[index].getAP().getCurrent() > monsterList[index].getAP().getMax()) {
                    monsterList[index].getAP().setCurrent(monsterList[index].getAP().getMax());
                }
                continue;
            }

        }
        // Проверяем, жив ли монстр
        for (int i = 1; i < monstersQuantity; i++) {
            if (monsterList[i] != null) {
                if (monsterList[i].getHP().getCurrent() <= 0) {
                    killMonster(i);
                }
            }
        }
        checkTimeEffects(); // метод проверят не пора ли поднять уровень игроку, наносит урон ядом и т.д.
        getTime().update();
    }		

    // Диалог закрытия игры
    void checkQuit() {
        frame1.setFocusable(false);
        frame1.setFocusableWindowState(false);
        QuitWindow frame1 = new QuitWindow(this);
        frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame1.setLocation(this.frame1.WINDOW_WIDTH / 2 - frame1.WINDOW_WIDTH / 2, this.frame1.WINDOW_HEIGHT / 2 - frame1.WINDOW_HEIGHT / 2);
        frame1.toFront();
        frame1.setVisible(true);
    }

    // Help
    void help() {
        frame1.setFocusable(false);
        frame1.setFocusableWindowState(false);
        HelpWindow frame1 = new HelpWindow(this);
        frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame1.setLocation(this.frame1.WINDOW_WIDTH / 2 - frame1.WINDOW_WIDTH / 2, this.frame1.WINDOW_HEIGHT / 2 - frame1.WINDOW_HEIGHT / 2);
        frame1.toFront();
        frame1.setVisible(true);
    }

// проверить изменялся ли Stat в худшую сторону, если так то вывести первую строку, иначе вторую
    void checkStatChanges(Stat s, String s1, String s2) {
        if (s.getTimer() > 0) {
            s.subTimer();
            if (s.getTimer() == 0) {
                if (s.getCurrent() < s.getMax()) {
                    logMessage(s1);
                } else {
                    logMessage(s2);
                }
                s.setCurrent(s.getMax());
            }
        }
    }

    // временные эффекты.
    void checkTimers(Monster m) {
		/* Есть подозрение, что что-то с этим кодом не то!
        if (m.getEND().getTimer() == 1) {
            m.getHP().setMax(m.getHP().getMax() + HIT_POINTS_PER_ENDURANCE * (m.getEND().getMax() - m.getEND().getCurrent()));
            m.getHP().setCurrent(m.getHP().getCurrent() + HIT_POINTS_PER_ENDURANCE * (m.getEND().getMax() - m.getEND().getCurrent()));
        }

        if (m.getSTR().getTimer() == 1) {
            m.getHP().setMax(m.getHP().getMax() + HIT_POINTS_PER_STRENGTH * (m.getSTR().getMax() - m.getSTR().getCurrent()));
            m.getHP().setCurrent(m.getHP().getCurrent() + HIT_POINTS_PER_STRENGTH * (m.getSTR().getMax() - m.getSTR().getCurrent()));
            m.getCurrentWeight().setMax(m.getCurrentWeight().getMax() + CARRYING_PER_STRENGTH * (m.getSTR().getMax() - m.getSTR().getCurrent()));
        }*/

        checkStatChanges(m.getSTR(), "Вы снова стали #3#сильнее!#^#", "Вы снова стали #2#слабее!#^#");
        checkStatChanges(m.getAGI(), "Вы снова стали более #3#ловким!#^#", "Вы снова стали менее #2#ловким!#^#");
        checkStatChanges(m.getEND(), "Вы снова стали #3#выносливее!#^#", "Вы снова стали менее #2#выносливее!#^#");
        checkStatChanges(m.getINT(), "Вы снова стали #3#лучше разбираться в магии!#^#", "Вы снова стали хуже #2#разбираться в магии!#^#");
        checkStatChanges(m.getLUCK(), "Вы снова стали #3#удачливее!#^#", "Вы снова стали менее #2#удачливее!#^#");
        checkStatChanges(m.getRFire(), "Вы снова стали #3#сильнее#^# сопротивляться #2#огню!#^#", "Вы снова стали #2#слабее#^# сопротивляться #2#огню!#^#");
        checkStatChanges(m.getRCold(), "Вы снова стали #3#сильнее#^# сопротивляться #4#холоду!#^#", "Вы снова стали #2#слабее#^# сопротивляться #4#холоду!#^#");
        checkStatChanges(m.getRElec(), "Вы снова стали #3#сильнее#^# сопротивляться #5#электричеству!#^#", "Вы снова стали #2#слабее#^# сопротивляться #5#электричеству!#^#");
        checkStatChanges(m.getRNormal(), "Вы снова стали #3#сильнее#^# сопротивляться #8#ударам!#^#", "Вы снова стали #2#слабее#^# сопротивляться #8#ударам!#^#");
        checkStatChanges(m.getRPoison(), "Вы снова стали #3#сильнее#^# сопротивляться #3#яду!#^#", "Вы снова стали #2#слабее#^# сопротивляться #3#яду!#^#");
        checkStatChanges(m.getFOVRAD(), "Вы снова стали #3#лучше#^# видеть!#^#", "Вы снова стали #2#хуже#^# видеть!#^#");
        checkStatChanges(m.getAddHP(), Monster.addLifeMsg(), Monster.subLifeMsg());
        checkStatChanges(m.getAddMP(), Monster.addManaMsg(), Monster.subManaMsg());
        checkStatChanges(m.getAddMP(), "Вы #3#пополняете запас маны#^#!#^#", "Вы #2#теряете ману#^#!#^#");

        // Медленное исцеление
        if (m.getAddHP().getCurrent() != 0) m.getHP().setCurrent(m.getHP().getCurrent() + (m.getAddHP().getCurrent() * m.getLevel()));
        // Медленное вост. маны
        if (m.getAddMP().getCurrent() != 0) m.getMP().setCurrent(m.getMP().getCurrent() + (m.getAddMP().getCurrent() * m.getLevel()));

    }

    //метод проверят не пора ли поднять уровень игроку, наносит урон ядом и т.д.
    void checkTimeEffects() {
        boolean x = false;
        // если у игрока хватает опыта для поднятия уровня, то поднимаем
        while (player.getExp() >= Player.maxExperience) {
            x = true;
            player.levelUp();
        }
        // если уровень был поднят, то рисуем окошко которое позволит распределить статы
        if (x) {
            frame1.setFocusable(false);
            frame1.setFocusableWindowState(false);
            LevelUpWindow frame1 = new LevelUpWindow(this);
            frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame1.setLocation(this.frame1.WINDOW_WIDTH / 2 - frame1.WINDOW_WIDTH / 2, this.frame1.WINDOW_HEIGHT / 2 - frame1.WINDOW_HEIGHT / 2);
            frame1.toFront();
            frame1.setVisible(true);
        }

        // пробегаемся по всем монстрам....
        for (int i = 0; i < monstersQuantity; i++) {
            if (monsterList[i] != null) {
                checkTimers(monsterList[i]); // проверка нет ли на нем каких эффектов
				// Восстанавливаем жизнь
                if (lifePointsRegeneration < monsterList[i].getEND().getCurrent())
                    monsterList[i].getHP().setCurrent(monsterList[i].getHP().getCurrent() + 1);
				// Восстанавливаем ману
                if (manaPointsRegeneration < monsterList[i].getINT().getCurrent())
                    monsterList[i].getMP().setCurrent(monsterList[i].getMP().getCurrent() + 1);
				//
				monsterList[i].getHP().setCurrent(Util.clamp(monsterList[i].getHP().getCurrent(), 0, monsterList[i].getHP().getMax()));
				monsterList[i].getMP().setCurrent(Util.clamp(monsterList[i].getMP().getCurrent(), 0, monsterList[i].getMP().getMax()));				
			}
			
            // есди есть отравление - наносим урон и снимаем единицу отравления
            if (monsterList[i] != null && monsterList[i].getPoisonCount() > 0) {
                if (i == 0) {
                    logMessage("Вы очень #3#страдаете от яда! (1)#^#/#");
                }
                monsterList[i].setPoisonCount(monsterList[i].getPoisonCount() - 1);
                monsterList[i].getHP().setCurrent((monsterList[i].getHP().getCurrent() - 1));
            }
            // есди есть шок - наносим урон и снимаем единицу эл. урона
            if (monsterList[i] != null && monsterList[i].getElecCount() > 0) {
                if (i == 0) {
                    logMessage("Вы очень #5#страдаете от электрических разрядов! (1)#^#/#");
                }
                monsterList[i].setElecCount(monsterList[i].getElecCount() - 1);
                monsterList[i].getHP().setCurrent((monsterList[i].getHP().getCurrent() - 1));
            }
            // если есть парализация - снимаем одно очко парализации
            if (monsterList[i] != null && monsterList[i].getParalyzeCount() > 0) {
                monsterList[i].setParalyzeCount(monsterList[i].getParalyzeCount() - 1);
            }
        }
        lifePointsRegeneration += Util.rand(5, 10);
        if (lifePointsRegeneration > 100) lifePointsRegeneration = 0;
        manaPointsRegeneration += Util.rand(10, 25);
        if (manaPointsRegeneration > 100) manaPointsRegeneration = 0;
    }

    // смена карты/переход на другой этаж, метод вызывается из класса KeyHandler
    // changeMapLevel может иметь значения -1 или 1
    public void switchMap(int changeMapLevel) {
        int newMapLevel = changeMapLevel + currentMapNumber;
        // Если дальше положенного ходить нельзя
        if (newMapLevel < 0 || newMapLevel >= MAX_FLOORS) {
            return;
        }
        // Если карты, на которую осуществляется переход, не существует, то создаем ее
        if (mapList[newMapLevel] == null) {
            mapList[newMapLevel] = new Map(this);
            mapsQuantity++;
            map = player.getMap(newMapLevel);
            while (!map.field[player.getY()][player.getX()].getPassable()) {
                if (!map.generate()) {
                    return;
                }
            }
            fillLevelByChests();
            fillLevelByMonsters();
            fillLevelByItems();
            fillLevelByTraps();
            finish();
        } // а если карта уже существует, то переносим на нее игрока
        else {
            map = player.getMap(newMapLevel);
        }
        // добавляем лестницы
        if (changeMapLevel == 1) {
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    if (mapList[currentMapNumber - 1].field[i][j].getID() == Tileset.TILE_STAIR_DOWN) {
                        if (map.field[i][j].getID() == Tileset.TILE_STAIR_UP) {
                            continue;
                        }
                        if (map.field[i][j].getID() == Tileset.TILE_STAIR_DOWN) {
                            continue;
                        }
                        map.setTileAt(i, j, Tileset.TILE_STAIR_UP);
                    }
                }
            }
        }
        // Проверка достижения
        Achievement.check(this, AchievementSet.TYPE_FIND_LEVEL, newMapLevel);
    }

    //TODO: конец игры слишком уныл
    public void finish() {
        if (currentMapNumber == MAX_FLOORS - 1) {
            logMessage("ВЫ ДОБРАЛИСЬ ДО ПОСЛЕДНЕГО УРОВНЯ И ВЫИГРАЛИ ИГРУ! СПАСИБО ЗА ТЕСТИРОВАНИЕ!");
        }
    }

    // этот метод вызывается из main вторым, сразу после Game.init()
    public void run() {
        mapList[0] = new Map(this);                 						// mapList класса Map
        map = mapList[0];                                                   // map это текущая карта, над ней происходят все операции
        // mapList нужен только для хранения карт
        currentMapNumber = 0;                                               // номер текущей карты в массиве
        if (!map.generate()) {
			logMessage("Карта сгенерирована с ошибкой!");
            return;
        }
        mapsQuantity++;

        frame1 = new GameWindow(map, this);
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Player.placeOnMap(map, this); // "высадка" игрока
        player = (Player) monsterList[0];
        map.updatePlayer();

        fillLevelByChests();
        fillLevelByMonsters();      // Добавляет столько монстров, сколько положено на уровень ( MAX_MONSTER_PER_LEVEL )
        fillLevelByItems();         // Добавляет столько итемов, сколько положено на уровень ( MAX_ITEM_PER_LEVEL )
        fillLevelByTraps(); 		// Доб. ловушки
        frame1.panel.repaint();
        this.selectRace();
    }

    public void addRandomMonster() {
        Random random = new Random();
        int newID;

        // Определяет можно ли данному монстру появится на свет
        while (true) {
            newID = random.nextInt(MonsterSet.MAX_MONSTERS);
            // Отставить разможение личности!
            if (newID == MonsterSet.MONSTER_PLAYER) continue;
            // Чтоб не плодить слишком сильных для игрока монстров
            if (MonsterSet.getMonster(newID).getLevel() < currentMapNumber - 4 || MonsterSet.getMonster(newID).getLevel() > currentMapNumber + 4) continue;
			// Это может быть только враг
			if (MonsterSet.getMonster(newID).getTeam() == MonsterSet.PLAYER_TEAM) continue;
			// Math.abs возвращает модуль числа
            int chance = 90 - Math.abs(MonsterSet.getMonster(newID).getLevel() - currentMapNumber) * 20;
            // не судьба
            if (!Util.dice(chance, 100)) continue;
            break;
        }
        BaseMonster baseMonster = MonsterSet.getMonster(newID);
        int y = 0;
        int x = 0;
        // Ищем подходящее место для высадки монстра
        while (map.field[y][x].getPassable() == false || map.field[y][x].getMonster() != null) {
            y = random.nextInt(map.getHeight());
            x = random.nextInt(map.getWidth());
        }
        // высадка
        monsterList[monstersQuantity] = new Monster(baseMonster, y, x, map, this);
        monstersQuantity++;
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

    // Специальность (Класс)
    public void selectClass() {
        frame1.setFocusable(false);
        frame1.setFocusableWindowState(false);
        ClassWindow frame2 = new ClassWindow(this);
        frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame2.setLocation(this.frame1.WINDOW_WIDTH / 2 - frame2.WINDOW_WIDTH / 2, this.frame1.WINDOW_HEIGHT / 2 - frame2.WINDOW_HEIGHT / 2);
        frame2.toFront();
        frame2.setTitle("Выбор класса");
        frame2.setVisible(true);
    }

    public void fillLevelByMonsters() {
        if (map.getFauna() != null) {
            map.makeFauna(map, map.getFauna());
        } else {
            logMessage("СЛУЧАЙНЫЕ МОБЫ");
            for (int i = 0; i < MAX_MONSTER_PER_LEVEL; i++)
                addRandomMonster();
        }
    }

    public void fillLevelByItems() {
        for (int i = 0; i < MAX_ITEM_PER_LEVEL; i++) {
            addRandomItem();
        }
    }

    // Ставим на карту ловушки
    private void fillLevelByTraps() {
        Random random = new Random();
        for (int i = 0; i < MAX_TRAP_PER_LEVEL; i++) {
            int y = random.nextInt(map.getHeight());
            int x = random.nextInt(map.getWidth());
            while (map.isEmptyAt(y, x)) {
                y = random.nextInt(map.getHeight());
                x = random.nextInt(map.getWidth());
            }
            map.placeTrapAt(y, x);
        }
    }

    // Добавляем сундуки на карту
    private void fillLevelByChests() {
        Random random = new Random();
        for (int i = 0; i < MAX_CHEST_PER_LEVEL; i++) {
            int y = random.nextInt(map.getHeight());
            int x = random.nextInt(map.getWidth());
            while (map.isEmptyAt(y, x)) {
                y = random.nextInt(map.getHeight());
                x = random.nextInt(map.getWidth());
            }
            map.placeChestAt(y, x);
        }
    }

    // Добавляем предмет на указанную карту в указанные координаты
    public void addItem(int y, int x, int id, Map map) {
        if (itemsQuantity >= MAX_ITEMS) {
            return;
        }
        BaseItem baseItem = ItemSet.getItem(id);
        itemList[itemsQuantity] = new Item(baseItem, y, x, map, this);
        itemsQuantity++;
    }

    // Добавляем случайный предмет на карту в указанные координаты
    public void addRandomItem(int y, int x) {
        if (itemsQuantity >= MAX_ITEMS) {
            return;
        }
        Random random = new Random();
        int newID = 0;
        while (true) {
            newID = random.nextInt(ItemSet.MAX_ITEMS);
            // выпавший итем должен существовать в базе
            if (ItemSet.getItem(newID) == null) continue;
            // выпавший итем не должен быть уровнем выше чем
            if (ItemSet.getItem(newID).getLevel() > currentMapNumber + 4) continue;
            // шанс выпадения выбранного итема на текущем уровне карты
            int chance = 90 - Math.abs(ItemSet.getItem(newID).getLevel() - currentMapNumber) * 20;
            if (chance > 100) chance = 80;
            if (!Util.dice(chance, 100)) continue;
            if (!Util.dice(ItemSet.getItem(newID).getChance(), 100)) continue;
            break;
        }
        addItem(y, x, newID, map);
    }

    // Добавляем предмет на карту в случайном месте
    public void addRandomItem() {
        if (itemsQuantity >= MAX_ITEMS) {
            return;
        }
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

    // Обновление экрана
    public void refresh() {
        keyHandler.map.FOV(player.getY(), player.getX(), player.getFOVRAD().getCurrent());
        keyHandler.panel.repaint();
        keyHandler.keyPressed(null);
    }

    // метод описывает убийство монстра
    public void killMonster(int index) {
        logMessage(monsterList[index].getName() + " #2#УМИРАЕТ В МУКАХ#^#!!!#/#");
        // Проверка достижения
        Achievement.check(this, AchievementSet.TYPE_KILL_ENEMY, 1);
        // Присваиваем map параметры той карты, на которой расположен монстр
        Map map = monsterList[index].getMap();
        // Cтавим кровь на месте смерти монстра
        map.placeBloodAt(monsterList[index].getY(), monsterList[index].getX());
		// Оставляем труп, если это возможно
		map.placeCorpseAt(monsterList[index].getY(), monsterList[index].getX(), monsterList[index].getCorpse());
        // Кидаем лут на землю
        monsterList[index].makeLoot(player.getMap(), monsterList[index].getLoot());
        // Yбираем монстра с карты
        map.deleteMonsterAt(monsterList[index].getY(), monsterList[index].getX());
        // Mодификатор получаемого количества опыта за убитого монстра
        int mod;
        // Eсли игрок убивает равного себе монстра или монстра сильнее себя, то получает модификатор соответствующий уровню убитого монстра
        if (player.getLevel() <= monsterList[index].getLevel()) {
            mod = monsterList[index].getLevel();
        } // иначе модификатор равен единице
        else {
            mod = 1;
        }
        // Bысчитывает сколько же игрок получит опыта
        int Exp = Util.rand((int) (0.75 * (mod * mod) * 10), (int) (1.5 * (mod * mod) * 10));
        player.setExp(player.getExp() + Exp);
        logMessage("Вы получаете " + Integer.toString(Exp) + " опыта! ");
        // Удаляем монстра
        monsterList[index] = null;
    }

    // выводит различные сообщения в верхний-левый угол
    public void logMessage(String s) {
        frame1.panel.LogMessage(s);
    }

    // Рисует иконку навыка или ачивки с названием и кратким описанием
    public void renderIcon(Graphics g, JPanel panel, int y, int x, int col,
            String imagePath, String name, String descr, int level) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(ColorSet.COLORSET[col]);
        FontRenderContext context = g2.getFontRenderContext();
        Font font = new Font("Serif", Font.PLAIN, 12);
        g2.setFont(font);
        // Иконка
        Image image = loader.getImage(imagePath);
        g.drawImage(image, x, y, panel);
        // Название
        g.drawString(name.toUpperCase(), x + 38, y + 10);
        // Краткое описание
        g.drawString(descr, x + 38, y + 25);
        // Уровень
        if (level > 0) {
            Image lev = loader.getImage("res/icons/level" + level + ".png");
            g.drawImage(lev, x, y, panel);
        }
    }

    // для удобства =)
    public void done() {
    }

}
