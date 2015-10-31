package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class MainPanel extends JPanel
{
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
		return (y>=DrawingMap.getCurrentY() && y<SCREEN_TILE_SIZE_Y+DrawingMap.getCurrentY() && x>=DrawingMap.getCurrentX() && x<SCREEN_TILE_SIZE_X+DrawingMap.getCurrentX());
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
		Image back = Toolkit.getDefaultToolkit().getImage("res/icons/backbar.png");
		g.drawImage(back, GameWindow.WINDOW_WIDTH - 202 - 10, y, this);
		Image bar = Toolkit.getDefaultToolkit().getImage(path);
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
	
	public void paintGUI(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		left = Tileset.TILE_SIZE * SCREEN_TILE_SIZE_X + 10;
		g2.setPaint(Color.WHITE);
		top = 50;
		addLine(g, "#7#" + DrawingMap.getGame().player.getName() + "#^#");
		top = 85;
		// Level
		addLine(g, "#8#УРОВЕНЬ#^#", DrawingMap.getGame().player.getLevel(), 0);
		// Life
		addLine(g, "#2#ЖИЗНЬ#^#", DrawingMap.getGame().player.getHP().getCurrent(), DrawingMap.getGame().player.getHP().getMax());
		drawBar(DrawingMap.getGame().player.getHP().getCurrent(),
			DrawingMap.getGame().player.getHP().getMax(), "res/icons/lifebar.png", g);	
		// Experience
		addLine(g, "#3#ОПЫТ#^#", DrawingMap.getGame().player.getXP(), DrawingMap.getGame().maxExperience);
		drawBar(DrawingMap.getGame().player.getXP(),
			DrawingMap.getGame().maxExperience, "res/icons/expbar.png", g);	
		// Attributes	
		addLine(g, "#8#СИЛА#^#", DrawingMap.getGame().player.getSTR().getCurrent(), 0);
		addLine(g, "#8#ЛОВКОСТЬ#^#", DrawingMap.getGame().player.getAGI().getCurrent(), 0);
		addLine(g, "#8#ВЫНОСЛИВОСТЬ#^#", DrawingMap.getGame().player.getEND().getCurrent(), 0);
		addLine(g, "#8#УДАЧА#^#", DrawingMap.getGame().player.getLUCK().getCurrent(), 0);
		// Effects
		if (DrawingMap.getGame().player.getparalyzecount() > 0){
			top = 405;
			addLine(g, "#8#ВЫ ПАРАЛИЗОВАНЫ!!!#^# : #5#  " + DrawingMap.getGame().player.getparalyzecount());
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
		addLine(g, DrawingMap.getName());
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		Rectangle2D canvas = new Rectangle2D.Double(0, 0 , window.WINDOW_WIDTH, window.WINDOW_WIDTH);
		g2.setPaint(Color.BLACK);
		g2.fill(canvas);
		g2.draw(canvas);
		for (int i=0; i<SCREEN_TILE_SIZE_Y; i++)
		for (int j=0; j<SCREEN_TILE_SIZE_X; j++){
			if (!DrawingMap.hasTileAt(i + DrawingMap.getCurrentY(), j + DrawingMap.getCurrentX()) || (!DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getVisible() && !DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getSeen()) ){
			Image image = Toolkit.getDefaultToolkit().getImage("res/dungeons/empty.png");
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);

			}
			else if (DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getSeen() &&  !DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getVisible()){
				Image image = Toolkit.getDefaultToolkit().getImage(Tileset.getTile(DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].lastseenID).getPath());
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);
				image = Toolkit.getDefaultToolkit().getImage("res/icons/transparent.png");
				g.drawImage(image,y,x,this);
			}else{
				Image image = Toolkit.getDefaultToolkit().getImage(Tileset.getTile(DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getID()).getPath());
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);
				image = Toolkit.getDefaultToolkit().getImage("res/icons/blood.png");
				if (DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getBlood())
					g.drawImage(image,y,x,this);

				if (DrawingMap.hasTileAt(i + DrawingMap.getCurrentY(), j + DrawingMap.getCurrentX())){
					if (DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getItemsQty()!=0){
						if (DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getItemsQty() > 1)
							image = Toolkit.getDefaultToolkit().getImage("res/icons/manyitems.png");
								else{
									LinkedList<Item> itemlist = DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getItemList();
								    image = Toolkit.getDefaultToolkit().getImage(ItemSet.getItem(itemlist.getFirst().getID()).getPath());
								}
							g.drawImage(image,y,x,this);
					}



							// Рисуем монстров
							if (DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getMonster()!=null){
								image = Toolkit.getDefaultToolkit().getImage(MonsterSet.getMonster(DrawingMap.field[i + DrawingMap.getCurrentY()][j + DrawingMap.getCurrentX()].getMonster().getID()).getPath());
								int px = DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getMonster().getX();
								int py = DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getMonster().getY();
								y = (j*Tileset.TILE_SIZE);
								x = (i*Tileset.TILE_SIZE);
								double leftX = (double)y;
								double topY = (double)x;
								double curHP = (double)(DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getMonster().getHP().getCurrent());
								double maxHP = (double)(DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getMonster().getHP().getMax());
								double width = (double)(curHP/maxHP*32-1);
								double height = 2;
								Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
								Rectangle2D br = new Rectangle2D.Double(leftX, topY, 31D, height);
								g.drawImage(image,y,x,this);
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
								if (DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getMonster().getparalyzecount()!=0){
									image = Toolkit.getDefaultToolkit().getImage("res/icons/paralyzed.png");
									g.drawImage(image,y,x,this);
								}
								if (DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].getMonster().getPoisonCount()!=0){
									image = Toolkit.getDefaultToolkit().getImage("res/icons/poisoned.png");
									g.drawImage(image,y,x,this);
								}
							}
				}
			}
			if (DrawingMap.hasTileAt(i + DrawingMap.getCurrentY(), j + DrawingMap.getCurrentX()) && DrawingMap.field[i+DrawingMap.getCurrentY()][j+DrawingMap.getCurrentX()].isSelected()){
				Image image = Toolkit.getDefaultToolkit().getImage("res/icons/selected.png");
				int y = (j*Tileset.TILE_SIZE);
				int x = (i*Tileset.TILE_SIZE) ;
				g.drawImage(image,y,x,this);
			}
		}
		paintGUI(g);
		drawLog(g);
	}
	private int left = 0;
	private int top = 0;
}
