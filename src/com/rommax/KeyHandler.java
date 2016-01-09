package com.rommax;

import java.awt.event.*;


public class KeyHandler implements KeyListener{

	Map map;
	MainPanel mp;
	boolean OPEN_MODE = false;
	boolean CLOSE_MODE = false;
	boolean GATHERING_MODE = false;
	public static boolean LOOK_MODE = false;
	public boolean ID_MODE = false;
	boolean re;
	int Timer = -1;
	public ItemSelectMessage message = null;

	public KeyHandler(Map map, MainPanel mp){
		super();
		this.map = map;
		this.mp = mp;
		map.getGame().keyHandler = this;
		re = false;
	}

	public synchronized void keyPressed(KeyEvent event){
		
		// Пока идет создание персонажа, отключаем все клавиши
		if (MainPanel.hasNewGame) return;
		
		int keycode = 0;

		// Действия игрока
		PlayerAction playerAction = new PlayerAction(map, mp); 

		if (Timer == 0) System.exit(0);
		if (map.getGame().player.getHP().getCurrent() <=0) {
			Timer = 0;
			map.getGame().logMessage("Вы умерли!.. Нажмите любую клавишу");
			mp.repaint();
			return;
		}
		
		boolean flag = true;

		if (event != null) keycode = event.getKeyCode();
		
		if (keycode==KeyEvent.VK_ESCAPE && !LOOK_MODE) {
			map.getGame().checkQuit();			
			flag = false;
		}
		else if (keycode==KeyEvent.VK_F1) {
			map.getGame().help();			
			flag = false;
		}
		else if (map.getGame().player.getParalyzeCount()>0 && !LOOK_MODE){
			map.getGame().logMessage("ВЫ #5#ПАРАЛИЗОВАНЫ!!!#^#/#");
			if (map.getGame().player.getParalyzeCount() > Game.MAX_PARALYZE_COUNT)
				map.getGame().player.setParalyzeCount(Game.MAX_PARALYZE_COUNT);
			flag = true;
		}
		// добыча ресурсов
		else if (GATHERING_MODE) flag = gatheringMode(event, keycode);
		// Look mode
		else if (LOOK_MODE) flag = lookMode(event, keycode);
		// Поднимаем предмет(ы)
		else if (keycode == KeyEvent.VK_T && !event.isShiftDown()) flag = playerAction.pickupIt();
		// Бросаем предмет(ы)
		else if (keycode == KeyEvent.VK_D && event.isShiftDown()) flag = playerAction.dropIt();
		// Выпить зелье
		else if (keycode == KeyEvent.VK_Q && event.isShiftDown()) flag = playerAction.quaffIt();
		// Прочитать свиток
		else if (keycode == KeyEvent.VK_R && !event.isShiftDown()) flag = playerAction.readIt();
		// Съесть еду
		else if (keycode == KeyEvent.VK_E && event.isShiftDown()) flag = playerAction.eatIt();
		// Одеть экипировку
		else if (keycode == KeyEvent.VK_W && event.isShiftDown()) flag =  playerAction.wearIt();
		// Осмотреть инвентарь
		else if (keycode == KeyEvent.VK_I && !event.isShiftDown()) flag = playerAction.inventory();
		// Осмотр экипировки
		else if (keycode == KeyEvent.VK_I && event.isShiftDown()) flag = playerAction.showEq();
		// Информация об игроке
		else if (keycode == KeyEvent.VK_H && !event.isShiftDown()) flag = playerAction.info();
		// Open mode
		else if (OPEN_MODE) flag = openMode(event, keycode, true);
		// Close mode
		else if (CLOSE_MODE) flag = openMode(event, keycode, false);
		// Игрок движется по прямым
		else if (isUpKey(event, keycode)) playerAction.up();
		else if (isDownKey(event, keycode)) playerAction.down();
		else if (isLeftKey(event, keycode)) playerAction.left();
		else if (isRightKey(event, keycode)) playerAction.right();
		// Игрок движется по диагоналях
		else if (isLeftUpKey(event, keycode)) playerAction.moveLeftUp();
		else if (isRightUpKey(event, keycode)) playerAction.moveRightUp();
		else if (isLeftDownKey(event, keycode)) playerAction.moveLeftDown();
		else if (isRightDownKey(event, keycode)) playerAction.moveRightDown();
		// Игрок пропускает ход и отдыхает
		else if (isRestKey(event, keycode)) playerAction.rest();
		// Достижения игрока
		else if (keycode == KeyEvent.VK_A && event.isShiftDown()) flag = playerAction.achievements();
		// Игрок пытается что-то открыть
		else if (keycode==KeyEvent.VK_O && event.isShiftDown()) flag = playerAction.openIt();
		// Игрок пытается что-то закрыть
		else if (keycode ==KeyEvent.VK_C && event.isShiftDown()) flag = playerAction.closeIt();
		// Вниз по лестнице
		else if (keycode==KeyEvent.VK_PERIOD) flag = playerAction.downStair(); // "."
		// Вверх по лестнице
		else if (keycode==KeyEvent.VK_COMMA) flag = playerAction.upStair(); // ","
		// Look mode
		else if (keycode == KeyEvent.VK_L) flag = playerAction.startLookMode();
		// Используем навыки
		else if (keycode == KeyEvent.VK_F5) flag = playerAction.useSkill(1);
		else if (keycode == KeyEvent.VK_F6) flag = playerAction.useSkill(2);
		else if (keycode == KeyEvent.VK_F7) flag = playerAction.useSkill(3);
		else if (keycode == KeyEvent.VK_F8) flag = playerAction.useSkill(4);
		//
		else if (keycode == KeyEvent.VK_F12){
			map.getGame().player.getHP().setCurrent(map.getGame().player.getHP().getMax());
		}
		// добываем ресурсы на местности
		else if ((keycode == KeyEvent.VK_G) && !event.isShiftDown()) flag = playerAction.gatheringIt();
		// разбираем предметы
		else if (keycode == KeyEvent.VK_G && event.isShiftDown()) flag = playerAction.disassembledItem();
		// Крафтинг
		else if (keycode == KeyEvent.VK_K && !event.isShiftDown()) flag = playerAction.showCraftingWindow();
		else flag = false;

		mp.repaint();
		
		// Игрок идентифицирует что-то (прочитав свиток идентификации)
		if (ID_MODE) {
			ID_MODE = false;
			if (map.getGame().player.getInventory().size() <= 0)
				map.getGame().logMessage("У вас пусто в инвентаре, нечего идентифицировать!");
					else flag = playerAction.identifyIt();
		}
		// Действия AI и перерисовка карты
		if (flag) {
			map.getGame().monstersAI();
			map.updatePlayer();
			mp.repaint();
		}
	}

	// Look mode
	private boolean lookMode(KeyEvent event, int keycode) {
		boolean flag = false;
		PlayerAction playerAction = new PlayerAction(map, mp);
		if (isRightKey(event, keycode)) playerAction.lookTo(+1, 0);
		else if (isLeftKey(event, keycode)) playerAction.lookTo(-1, 0);
		else if (isUpKey(event, keycode)) playerAction.lookTo(0, -1);
		else if (isDownKey(event, keycode)) playerAction.lookTo(0, +1);
		else if (isLeftUpKey(event, keycode)) playerAction.lookTo(-1, -1);
		else if (isLeftDownKey(event, keycode)) playerAction.lookTo(-1, +1);
		else if (isRightUpKey(event, keycode)) playerAction.lookTo(+1, -1);
		else if (isRightDownKey(event, keycode)) playerAction.lookTo(+1, +1);
		else if (keycode == KeyEvent.VK_ENTER) {
			map.getGame().player.lookAtMonster(ly, lx);
			flag = false;
		}
		else if (keycode == KeyEvent.VK_ESCAPE) {
			map.field[ly][lx].setCursor(false);
			LOOK_MODE = false;
			mp.descStr = "";
		}
		flag = false;
		return flag;
	}
	
	// Open and Close modes
	private boolean openMode(KeyEvent event, int keycode, boolean isOpen) {
		boolean flag = false;
		PlayerAction playerAction = new PlayerAction(map, mp);
		if (isRightKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() + 0, map.getGame().player.getX() + 1, isOpen);
		else if (isLeftKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() + 0, map.getGame().player.getX() - 1, isOpen);
		else if (isUpKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() - 1, map.getGame().player.getX() + 0, isOpen);
		else if (isDownKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() + 1, map.getGame().player.getX() + 0, isOpen);
		else if (isLeftUpKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() - 1, map.getGame().player.getX() - 1, isOpen);
		else if (isLeftDownKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() + 1, map.getGame().player.getX() - 1, isOpen);
		else if (isRightUpKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() - 1, map.getGame().player.getX() + 1, isOpen);
		else if (isRightDownKey(event, keycode)) map.getGame().tryToOpenSomething(true, map.getGame().player.getY() + 1, map.getGame().player.getX() + 1, isOpen);
		else {
			map.getGame().logMessage("#2#НЕВЕРНОЕ#^# НАПРАВЛЕНИЕ! #/#");
			flag = false;
		}
		if (isOpen) OPEN_MODE = false;
			else CLOSE_MODE = false;
		return flag;
	}
	
	private boolean isUpKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_UP) || (keycode==KeyEvent.VK_W && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD8));
	}
	
	private boolean isDownKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_DOWN) || (keycode==KeyEvent.VK_X && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD2));
	}

	private boolean isLeftKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_LEFT) || (keycode==KeyEvent.VK_A && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD4));
	}

	private boolean isRightKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_RIGHT) || (keycode==KeyEvent.VK_D && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD6));
	}

	private boolean isLeftUpKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_Q && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD7));
	}
	
	private boolean isRightUpKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_E && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD9));
	}
	
	private boolean isLeftDownKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_Z && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD1));
	}
	
	private boolean isRightDownKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_C && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD3));
	}
	
	private boolean isRestKey(KeyEvent event, int keycode) {
		return ((keycode==KeyEvent.VK_S && !event.isShiftDown()) || (keycode==KeyEvent.VK_NUMPAD5));
	}
	
	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}
	
	public static int lx;
	public static int ly;

	// режим добычи ресурсов
	private boolean gatheringMode(KeyEvent event, int keycode) {
		boolean flag = false;
		if (isRightKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() + 0, map.getGame().player.getX() + 1);
		else if (isLeftKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() + 0, map.getGame().player.getX() - 1);
		else if (isUpKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() - 1, map.getGame().player.getX() + 0);
		else if (isDownKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() + 1, map.getGame().player.getX() + 0);
		else if (isLeftUpKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() - 1, map.getGame().player.getX() - 1);
		else if (isLeftDownKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() + 1, map.getGame().player.getX() - 1);
		else if (isRightUpKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() - 1, map.getGame().player.getX() + 1);
		else if (isRightDownKey(event, keycode)) GATHERING_MODE = map.getGame().tryToGatheringSomething(map.getGame().player.getY() + 1, map.getGame().player.getX() + 1);
		else {
			map.getGame().logMessage("#2#НЕВЕРНОЕ#^# НАПРАВЛЕНИЕ! #/#");
		}
		flag = true;
		return flag;
	}

}