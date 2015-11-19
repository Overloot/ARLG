package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class MainPanel extends JPanel{
	private static int SCREEN_TILE_SIZE_X;
	private static int SCREEN_TILE_SIZE_Y;
	private Map DrawingMap;
	private GameWindow window;
	public KeyHandler listener;
	private String LogString;
	private String LastString;
	private int countrepeat;
	private int linenumber;
	private int lastX;
	String laststr = "";
	public String descStr = "";

	public void setMap(Map map){
		DrawingMap = map;
		listener.map = map;
	}

	public boolean HasTileAtScreen(int y, int x){
		return (y>=DrawingMap.getY() && y<SCREEN_TILE_SIZE_Y+DrawingMap.getY() && x>=DrawingMap.getX() && x<SCREEN_TILE_SIZE_X+DrawingMap.getX());
	}

	MainPanel(GameWindow window, Map DrawingMap, int stsx, int stsy){
		super();
		this.window = window;
		LogString = "";
		linenumber = 1;
		this.DrawingMap = DrawingMap;
		this.SCREEN_TILE_SIZE_X = stsx;
		this.SCREEN_TILE_SIZE_Y = stsy;
	    listener = new KeyHandler(DrawingMap, this);
		addKeyListener(listener);
		setFocusable(true);
		lastX = 15;
	}

	public void LogMessage(String str){
		if (!str.endsWith("/#"))
		LogString += (str + "#/#");
		else
		LogString += (str);
	}
	
	public void drawSkills(Graphics g){
		Image image, fog;
		fog = window.game.loader.getImage("res/icons/transparent.png");
		int top = 200;
		int left = Tileset.TILE_SIZE * SCREEN_TILE_SIZE_X + 10;
		for(int i = 0; i < Skill.getAmount(); i++){
			String str = " (F" + (i + 5) + ")"; // Клавиши F5-F8
			if (Skill.getCooldown(i) > 0) str = " (" + Skill.getCooldown(i) + ")";
			window.game.renderIcon(g, this, top + (i * 35), left, 7, SkillSet.getSkill(Skill.skill[i]).getPath(), 
				SkillSet.getSkill(Skill.skill[i]).getName() + str, SkillSet.getSkill(Skill.skill[i]).getDescr(),
				SkillSet.getSkill(Skill.skill[i]).getLevel());
			if (Skill.getCooldown(i) > 0) g.drawImage(fog, left, top + (i * 35), this);
		}
	}
	
	private void drawMap(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		final int top = 540;
		Rectangle2D canvas = new Rectangle2D.Double(left, top, DrawingMap.getWidth() * 3, DrawingMap.getHeight() * 3);
		g2.setPaint(Color.BLACK);
		g2.fill(canvas);
		g2.draw(canvas);
		if (hasNewGame) return;
		Image player = window.game.loader.getImage("res/maps/player.png");
		Image water = window.game.loader.getImage("res/maps/water.png");
		for (int i=0; i<DrawingMap.getHeight(); i++)
			for (int j=0; j<DrawingMap.getWidth(); j++){
				//if (DrawingMap.field[i][j].getSeen()) g.drawImage(water, left + j * 3, top + i * 3, this);
				switch (DrawingMap.field[i][j].getID()){
					case Tileset.TILE_STAIR_UP:
						g.drawImage(player, left + j * 3, top + i * 3, this);
						break;
					case Tileset.TILE_STAIR_DOWN:
						g.drawImage(water, left + j * 3, top + i * 3, this);
						break;
				}
			}
		g.drawImage(player, left + DrawingMap.getGame().player.getX() * 3, top + DrawingMap.getGame().player.getY() * 3, this);
	}
	
	public void drawLog(Graphics g){
		linenumber = 1;
		int leftX = 15;
		if (LogString == null || LogString.equals("")) return;
		StringTokenizer st = new StringTokenizer(LogString, "#");
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f = new Font("Times New Roman", Font.PLAIN, 12);
		g2.setFont(f);
		Rectangle2D bounds;
		g2.setPaint(Color.WHITE);
		int topY = Tileset.TILE_SIZE*SCREEN_TILE_SIZE_Y+15;
		String str;
		while (st.hasMoreTokens()){
			str = st.nextToken();
			if (str.equals("/")){
				lastX = 15;
				linenumber++;
				continue;
			}
			else
			if (str.equals("^")){
				g2.setColor(Color.WHITE);
				continue;
			}
			else
			if (str.length() < 3){
				int col;
				boolean skip = false;
				try{
				col = Integer.parseInt(str);
				} catch (NumberFormatException ex){col = 1; skip = true;}
				g2.setColor(ColorSet.COLORSET[col]);
				if (!skip)
				continue;
			}
			g2.drawString(str, lastX, linenumber * 15);
			bounds = f.getStringBounds(str, context);
			lastX += (bounds.getWidth());
		}
			if (linenumber > 15) LogString = "";
	}

	void drawColorString(Graphics g, String str, int lastX, int lastY){
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f = new Font("Serif", Font.PLAIN, 12);
		g2.setFont(f);
		Rectangle2D bounds;
		StringTokenizer st = new StringTokenizer(str, "#");
		String token;
		while (st.hasMoreTokens()){
			token = st.nextToken();
			if (token.equals("^")){
				g2.setColor(Color.WHITE);
				continue;
			}
			else
			if (token.length() <= 2){
				int col = Integer.parseInt(token);
				g2.setColor(ColorSet.COLORSET[col]);
				continue;
			}
				g2.drawString(token, lastX, lastY);
				bounds = f.getStringBounds(token, context);
				lastX += (bounds.getWidth());
			}
	}


	private void drawBar(double c, double m, String path, Graphics g) {
		if (c > m) c = m;
		if (c < 0) c = 0;
		int y = top - 25;
		Graphics2D g2 = (Graphics2D)g;
		Image back = window.game.loader.getImage("res/icons/backbar.png");
		g.drawImage(back, GameWindow.WINDOW_WIDTH - 202 - 10, y, this);
		Image bar = window.game.loader.getImage(path);
		g.drawImage(bar, GameWindow.WINDOW_WIDTH - 201 - 10, y + 1, (int)(c / m * 200), 8, this);	
	}
	
	private void addLine(Graphics g, String s, int cur, int max) {
		String str = s + " : " + Integer.toString(cur);
		if (max > 0) str += "/" + Integer.toString(max);
		drawColorString(g, str, left, top);
		top += 15;
	}
	
	private void addLine(Graphics g, String s) {
		drawColorString(g, s, left, top);
		top += 15;		
	}

	private void drawSpriteFrame(Image source, Graphics2D g2d, int x, int y, int columns, int frame){
		int fx = (frame % columns) * Tileset.TILE_SIZE;
		int fy = (frame / columns) * Tileset.TILE_SIZE;
		g2d.drawImage(source, x, y, x + Tileset.TILE_SIZE, y + Tileset.TILE_SIZE,
		fx, fy, fx + Tileset.TILE_SIZE, fy + Tileset.TILE_SIZE, this);
	}

	public void paintGUI(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		left = Tileset.TILE_SIZE * SCREEN_TILE_SIZE_X + 10;
		g2.setPaint(Color.WHITE);
		top = 20;
		addLine(g, "#7#" + RaceSet.getRace(RaceSet.getCurrentRaceID).getName() + "#^#");
		top = 50;
		// Level
		addLine(g, "#8#УРОВЕНЬ#^#", DrawingMap.getGame().player.getLevel(), 0);
		// Life
		addLine(g, "#2#ЖИЗНЬ#^#", DrawingMap.getGame().player.getHP().getCurrent(), DrawingMap.getGame().player.getHP().getMax());
		drawBar(DrawingMap.getGame().player.getHP().getCurrent(),
			DrawingMap.getGame().player.getHP().getMax(), "res/icons/lifebar.png", g);	
		// Experience
		addLine(g, "#3#ОПЫТ#^#", DrawingMap.getGame().player.getExp(), DrawingMap.getGame().player.maxExperience);
		drawBar(DrawingMap.getGame().player.getExp(),
			DrawingMap.getGame().player.maxExperience, "res/icons/expbar.png", g);	
		// Attributes	
		addLine(g, "#8#СИЛА#^#", DrawingMap.getGame().player.getSTR().getCurrent(), 0);
		addLine(g, "#8#ЛОВКОСТЬ#^#", DrawingMap.getGame().player.getAGI().getCurrent(), 0);
		addLine(g, "#8#ВЫНОСЛИВОСТЬ#^#", DrawingMap.getGame().player.getEND().getCurrent(), 0);
		addLine(g, "#8#УДАЧА#^#", DrawingMap.getGame().player.getLUCK().getCurrent(), 0);
		addLine(g, "#2#Debug: items: " + DrawingMap.getGame().itemsQuantity + "/" + DrawingMap.getGame().MAX_ITEMS + ", monsters: " + DrawingMap.getGame().monstersQuantity + "/" + DrawingMap.getGame().MAX_MONSTERS + ", day: " + DrawingMap.getGame().getTime().getDay() + "#^#");
		// Effects
		if (DrawingMap.getGame().player.getElecCount() > 0){
			top = 390;
			addLine(g, "#8#ВЫ В ШОКЕ!!!#^# : #5#  " + DrawingMap.getGame().player.getElecCount());
		}
		if (DrawingMap.getGame().player.getParalyzeCount() > 0){
			top = 405;
			addLine(g, "#8#ВЫ ПАРАЛИЗОВАНЫ!!!#^# : #5#  " + DrawingMap.getGame().player.getParalyzeCount());
		}
		if (DrawingMap.getGame().player.getPoisonCount() > 0){
			top = 420;
			addLine(g, "#8#ВЫ ОТРАВЛЕНЫ!!!#^# : #3#  " + DrawingMap.getGame().player.getPoisonCount());
		}
		top = 435;
		addLine(g, "#8#ГЛУБИНА#^#", DrawingMap.getGame().currentMapNumber + 1,0);
		addLine(g, "#8#ХОД#^#", DrawingMap.getGame().turn, 0);
		addLine(g, "#8#НАГРУЗКА#^#", DrawingMap.getGame().player.getCurrentWeight().getCurrent(), DrawingMap.getGame().player.getCurrentWeight().getMax());
		drawBar(DrawingMap.getGame().player.getCurrentWeight().getCurrent(),
			DrawingMap.getGame().player.getCurrentWeight().getMax(), "res/icons/invbar.png", g);		
		addLine(g, "#8#ОБЪЕМ#^#", DrawingMap.getGame().player.getCurrentSize().getCurrent(), DrawingMap.getGame().player.getCurrentSize().getMax());
		drawBar(DrawingMap.getGame().player.getCurrentSize().getCurrent(),
			DrawingMap.getGame().player.getCurrentSize().getMax(), "res/icons/invbar.png", g);		
		addLine(g, "#8#КООРДИНАТЫ#^#", DrawingMap.getGame().player.getY(), DrawingMap.getGame().player.getX());

		if (!descStr.equals("")){
			drawColorString(g,descStr,15, GameWindow.WINDOW_HEIGHT - (2 * Tileset.TILE_SIZE));
		}

		// Map
		top = 520;
		addLine(g, DrawingMap.getName() + " (" + DrawingMap.getLevel() + ")");
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		Rectangle2D canvas = new Rectangle2D.Double(0, 0 , window.WINDOW_WIDTH, window.WINDOW_WIDTH);
		g2.setPaint(Color.BLACK);
		g2.fill(canvas);
		g2.draw(canvas);
		if (hasNewGame) return; // Чтобы не прорисовывалась панель, пока игрок в меню
		for (int i=0; i<SCREEN_TILE_SIZE_Y; i++)
		for (int j=0; j<SCREEN_TILE_SIZE_X; j++){
			int xx = j + DrawingMap.getX();
			int yy = i + DrawingMap.getY();
			if (!DrawingMap.hasTileAt(yy, xx) || (!DrawingMap.field[yy][xx].getVisible() && !DrawingMap.field[yy][xx].getSeen()) ){
			Image image = window.game.loader.getImage("res/dungeons/empty.png");
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);
			}
			else if (DrawingMap.field[yy][xx].getSeen() &&  !DrawingMap.field[yy][xx].getVisible()){
				Image image = window.game.loader.getImage(Tileset.getTile(DrawingMap.field[yy][xx].lastseenID).getPath());
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);
				image = window.game.loader.getImage("res/icons/transparent.png");
				g.drawImage(image,y,x,this);
			}else{
				Image image = window.game.loader.getImage(Tileset.getTile(DrawingMap.field[yy][xx].getID()).getPath());
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);
				// Кровь на тайле
				int bloodID = DrawingMap.field[yy][xx].getBlood();
				if (bloodID > 0) {
					image = window.game.loader.getImage("res/icons/bloods.png");
					drawSpriteFrame(image, g2, y, x, 10, bloodID);
				}

				if (DrawingMap.hasTileAt(yy, xx)){
					if (DrawingMap.field[yy][xx].getItemsQty()!=0){
						if (DrawingMap.field[yy][xx].getItemsQty() > 1)
							image = window.game.loader.getImage("res/icons/manyitems.png");
								else{
									LinkedList<Item> itemlist = DrawingMap.field[yy][xx].getItemList();
								    image = window.game.loader.getImage(ItemSet.getItem(itemlist.getFirst().getID()).getPath());
								}
							g.drawImage(image,y,x,this);
					}



							// Рисуем монстров
							if (DrawingMap.field[yy][xx].getMonster()!=null){
								if (DrawingMap.field[yy][xx].getMonster() == DrawingMap.getGame().player) {
									image = window.game.loader.getImage(RaceSet.getRace(RaceSet.getCurrentRaceID).getPath());
								} else image = window.game.loader.getImage(MonsterSet.getMonster(DrawingMap.field[yy][xx].getMonster().getID()).getPath());
								int px = DrawingMap.field[yy][xx].getMonster().getX();
								int py = DrawingMap.field[yy][xx].getMonster().getY();
								y = (j*Tileset.TILE_SIZE);
								x = (i*Tileset.TILE_SIZE);
								double leftX = (double)y;
								double topY = (double)x;
								double curHP = (double)(DrawingMap.field[yy][xx].getMonster().getHP().getCurrent());
								double maxHP = (double)(DrawingMap.field[yy][xx].getMonster().getHP().getMax());
								double width = (double)(curHP/maxHP*32-1);
								double height = 2;
								Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
								Rectangle2D br = new Rectangle2D.Double(leftX, topY, 31D, height);
								g.drawImage(image, y, x, this);
								g2.setPaint(Color.BLACK);
								g2.fill(br);
								g2.draw(br);
								if (curHP/maxHP>0.75)
								g2.setPaint(Color.GREEN);
								else
								if (curHP/maxHP>0.50)
								g2.setPaint(Color.YELLOW);
								else
								g2.setPaint(Color.RED);
								g2.fill(rect);
								g2.draw(rect);
								if (DrawingMap.field[yy][xx].getMonster().getParalyzeCount()!=0||DrawingMap.field[yy][xx].getMonster().getElecCount()!=0){
									image = window.game.loader.getImage("res/icons/paralyzed.png");
									g.drawImage(image,y,x,this);
								}
								if (DrawingMap.field[yy][xx].getMonster().getPoisonCount()!=0){
									image = window.game.loader.getImage("res/icons/poisoned.png");
									g.drawImage(image,y,x,this);
								}
							}
				}
			}
			if (DrawingMap.hasTileAt(yy, xx) && DrawingMap.field[yy][xx].isSelected()){
				Image image = window.game.loader.getImage("res/icons/selected.png");
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);
			}
		}
		paintGUI(g);
		drawLog(g);
		drawSkills(g);
		drawMap(g);
	}
	private int left = 0;
	private int top = 0;
	static boolean hasNewGame = true;
}
