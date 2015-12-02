package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class CraftingSelectPanel extends JPanel {
	public CraftingSelectWindow cWindow;
	public CraftingSelectKeyHandler listener;
	public int min = 0;
	public int max = 0;
	public int current = 0;
	public int MAX_ITEMS = 0;
	private Image im;

	CraftingSelectPanel(CraftingSelectWindow cWindow){
		super();
		this.cWindow = cWindow;
		listener = new CraftingSelectKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
		LinkedList<Item> x = cWindow.list;
		ListIterator<Item> iter = x.listIterator();
		Item it = null;
		while (iter.hasNext()){
			it = iter.next();
			if (it.getType()==cWindow.type || cWindow.type == ItemSet.TYPE_ANY)
				MAX_ITEMS++;
		}
		min = 0;
		if (MAX_ITEMS<10) max = MAX_ITEMS-1;
			else max = 9;
	}

	public void resetState(){
		MAX_ITEMS = 0;
		LinkedList<Item> x = cWindow.list;
		ListIterator<Item> iter = x.listIterator();
		Item it = null;
		while (iter.hasNext()){
			it = iter.next();
			if (it.getType()== cWindow.type || cWindow.type == ItemSet.TYPE_ANY)
				  MAX_ITEMS++;
		}
		min = 0;
		if (MAX_ITEMS<10) max = MAX_ITEMS-1;
			else max = 9;
		current = 0;
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
			} else if (token.length() <= 2){
				int col = Integer.parseInt(token);
				g2.setColor(ColorSet.COLORSET[col]);
				continue;
			}
			g2.drawString(token, lastX, lastY);
			bounds = f.getStringBounds(token, context);
			lastX += (bounds.getWidth());
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		LinkedList<Item> x = cWindow.list;
		ListIterator<Item> iter = x.listIterator();
		int ix = 0;
		Item item = x.getFirst();
		iter.next();
		while(ix<min){
			if (item.getType()== cWindow.type || cWindow.type == ItemSet.TYPE_ANY)
			ix++;
		    item = iter.next();
		}
		g.drawImage(cWindow.game.background, 0, 0, this);
		for (int i=min; i<=max; i++){
			if (item!=null){
				if (item.getType()!= cWindow.type && cWindow.type != ItemSet.TYPE_ANY) i--;
				else {
					im =  cWindow.game.loader.getImage(ItemSet.getItem(item.getID()).getPath());
				    g.drawImage(im, 0 , (i-min)*Tileset.TILE_SIZE + 10, this);
				    g2.setPaint(Color.WHITE);
					drawColorString(g, item.getName().toLowerCase(),Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * (0.5 + (i - min))) + 10);
					if (i == current){
						g.drawString(item.craftResource(item.getLoot()), 300, (int)(Tileset.TILE_SIZE * (0.5 + (i - min))) + 10);
						g2.setPaint(Color.YELLOW);
						drawColorString(g, item.getName().toLowerCase(),Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * (0.5 + (i - min))) + 10);
						g.drawImage(cWindow.game.cursor, cWindow.WINDOW_WIDTH - Tileset.TILE_SIZE - 5, (i-min)*Tileset.TILE_SIZE + 10 , this);
					}
				}
			}
			if (iter.hasNext()) item = iter.next();	else item = null;
		}
		if (max< MAX_ITEMS-1)
			g.drawString("<...>",5, Tileset.TILE_SIZE * 10 + 15);
		if (min>0)
		    g.drawString("<...>",5,10);
		g2.setPaint(Color.WHITE);
		g.drawString("Текущий режим: " + ItemSet.getNameOfType(cWindow.type).toLowerCase(), 5, Tileset.TILE_SIZE * 10 + 25);
		if (max == -1) min = -1;
		g.drawString("Всего предметов: " + Integer.toString(MAX_ITEMS) + ", показаны предметы с " + (min + 1) + " по " + (max + 1) + ".", 5, Tileset.TILE_SIZE * 10 + 35);
		if (min == -1) min = 0;
		g.drawString("Фильтры для меню: 1 - оружие, 2 - броня, 3 - зелья, 4 - свитки, 5 - контейнеры, 6 - еда,", 5, Tileset.TILE_SIZE * 10 + 45);
		g.drawString("7 - все остальное, 0 - показывать все.", 5, Tileset.TILE_SIZE * 10 + 55);
	}

}
