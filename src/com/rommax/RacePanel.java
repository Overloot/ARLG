package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class RacePanel extends JPanel
{
	public RaceWindow window;
	public RaceKeyHandler listener;
	public int select =  1;
	private int x, y;
	private int z = 0;
	
	RacePanel(RaceWindow window){
		super();
		this.window = window;
		listener = new RaceKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}
	
	private void addLine(Graphics g, String iconPath, String itemName, int X, int Y) {
		Image image = window.game.loader.getImage(iconPath);
		g.drawImage(image, Tileset.TILE_SIZE * (X + 1), Tileset.TILE_SIZE * (RaceSet.MAX_RACES + Y + 1) + 10, this);
		g.drawString(itemName, (Tileset.TILE_SIZE * (X + 2)) + 5, Tileset.TILE_SIZE * (RaceSet.MAX_RACES + Y + 1) + 32 + z);		
	}
	
	private void addLine(Graphics g, String itemName, int X, int Y) {
		g.drawString(itemName, (Tileset.TILE_SIZE * (X + 2)) + 5, Tileset.TILE_SIZE * (RaceSet.MAX_RACES + Y + 1) + 32);		
	}
		
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		z = 0;
		g.drawImage(window.game.background, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f1 = new Font("Serif", Font.PLAIN, 20);
		Font f2 = new Font("Serif", Font.PLAIN, 12);
		Font f3 = new Font("Serif", Font.PLAIN, 16);
		Image image;
		for(int i = 0; i < RaceSet.MAX_RACES; i++) {
			image = window.game.loader.getImage(RaceSet.getRace(i).getPath());
			y = i * Tileset.TILE_SIZE;
			g.drawImage(image, Tileset.TILE_SIZE, y, this);
			if (i == select - 1) g2.setPaint(Color.YELLOW); else g2.setPaint(Color.WHITE);
			g2.setFont(f1);
			g.drawString(RaceSet.getRace(i).getName(), (Tileset.TILE_SIZE * 2) + 5, y + 24);
			if (i == select - 1) {
				g2.setFont(f2);
				g.drawString(RaceSet.getRace(i).getDescr1(), Tileset.TILE_SIZE + 5, Tileset.TILE_SIZE * RaceSet.MAX_RACES + 25);
				g.drawString(RaceSet.getRace(i).getDescr2(), Tileset.TILE_SIZE + 5, Tileset.TILE_SIZE * RaceSet.MAX_RACES + 37);
				g.drawString(RaceSet.getRace(i).getDescr3(), Tileset.TILE_SIZE + 5, Tileset.TILE_SIZE * RaceSet.MAX_RACES + 49);
				g2.setFont(f3);
				
				addLine(g, "res/icons/str_icon.png", "Сила: " + RaceSet.getRace(i).getSTR(), 0, 1);
				addLine(g, "res/icons/agi_icon.png", "Ловкость: " + RaceSet.getRace(i).getAGI(), 0, 2);
				addLine(g, "res/icons/end_icon.png", "Выносливость: " + RaceSet.getRace(i).getEND(), 0, 3);
				addLine(g, "res/icons/icon_luck.png", "Удача: " + RaceSet.getRace(i).getLUCK(), 0, 4);
				
				y = window.game.calcHP(RaceSet.getRace(i).getSTR(), RaceSet.getRace(i).getEND());
				addLine(g, "res/icons/icon_life.png", "Жизнь: " + y + "/" + y, 7, 1);
				addLine(g, "res/items/backpack1.png", "Нагрузка: " + window.game.calcCarrying(RaceSet.getRace(i).getSTR()), 7, 2);
				
				// Расовый навык
				addLine(g, "Расовый навык:", 6, 3); z = -10;
				window.game.renderIcon(g, this, Tileset.TILE_SIZE * (RaceSet.MAX_RACES + 5) + 15,
					260, 7, SkillSet.getSkill(RaceSet.getRace(i).getSkill()).getPath(),
					SkillSet.getSkill(RaceSet.getRace(i).getSkill()).getName(),
					SkillSet.getSkill(RaceSet.getRace(i).getSkill()).getDescr(),
					SkillSet.getSkill(RaceSet.getRace(i).getSkill()).getLevel());
			}
		}
		y = ((select - 1) * Tileset.TILE_SIZE);
		x = (window.WINDOW_WIDTH - Tileset.TILE_SIZE - 5);
		g.drawImage(window.game.cursor, x, y, this);
	}
}
