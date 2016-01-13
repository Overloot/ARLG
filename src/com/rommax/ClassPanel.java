package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class ClassPanel extends JPanel
{
	public ClassWindow window;
	public ClassKeyHandler listener;
	public int select =  1;
	private int x, y;
	private int z = 0;
	
	ClassPanel(ClassWindow window){
		super();
		this.window = window;
		listener = new ClassKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}
	
	private void addLine(Graphics g, String iconPath, String itemName, int X, int Y) {
		Image image = window.game.loader.getImage(iconPath);
		g.drawImage(image, Tileset.TILE_SIZE * (X + 1), Tileset.TILE_SIZE * (ClassSet.MAX_CLASSES + Y + 1) + 10, this);
		g.drawString(itemName, (Tileset.TILE_SIZE * (X + 2)) + 5, Tileset.TILE_SIZE * (ClassSet.MAX_CLASSES + Y + 1) + 32 + z);		
	}
	
	private void addLine(Graphics g, String itemName, int X, int Y) {
		g.drawString(itemName, (Tileset.TILE_SIZE * (X + 2)) + 5, Tileset.TILE_SIZE * (ClassSet.MAX_CLASSES + Y + 1) + 32);		
	}
		
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		z = 0;
		int STR, AGI, END, LUCK;
		g.drawImage(window.game.background, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f1 = new Font("Serif", Font.PLAIN, 20);
		Font f2 = new Font("Serif", Font.PLAIN, 12);
		Font f3 = new Font("Serif", Font.PLAIN, 16);
		Image image;
		for(int i = 0; i < ClassSet.MAX_CLASSES; i++) {
			image = window.game.loader.getImage(window.game.getPlayerPath(RaceSet.getCurrentRaceID, i));
			y = i * Tileset.TILE_SIZE;
			g.drawImage(image, Tileset.TILE_SIZE, y, this);
			if (i == select - 1) g2.setPaint(Color.YELLOW); else g2.setPaint(Color.WHITE);
			g2.setFont(f1);
			g.drawString(ClassSet.getClass(i).getName(), (Tileset.TILE_SIZE * 2) + 5, y + 24);
			if (i == select - 1) {
				g2.setFont(f2);
				g.drawString(ClassSet.getClass(i).getDescr1(), Tileset.TILE_SIZE + 5, Tileset.TILE_SIZE * ClassSet.MAX_CLASSES + 25);
				g.drawString(ClassSet.getClass(i).getDescr2(), Tileset.TILE_SIZE + 5, Tileset.TILE_SIZE * ClassSet.MAX_CLASSES + 37);
				g.drawString(ClassSet.getClass(i).getDescr3(), Tileset.TILE_SIZE + 5, Tileset.TILE_SIZE * ClassSet.MAX_CLASSES + 49);
				g2.setFont(f3);
				
				STR  = RaceSet.getRace(RaceSet.getCurrentRaceID).getSTR()  + ClassSet.getClass(i).getSTR();
				AGI  = RaceSet.getRace(RaceSet.getCurrentRaceID).getAGI()  + ClassSet.getClass(i).getAGI();
				END  = RaceSet.getRace(RaceSet.getCurrentRaceID).getEND()  + ClassSet.getClass(i).getEND();
				LUCK = RaceSet.getRace(RaceSet.getCurrentRaceID).getLUCK() + ClassSet.getClass(i).getLUCK();

				addLine(g, "res/icons/str_icon.png", "Сила: " + STR, 0, 1);
				addLine(g, "res/icons/agi_icon.png", "Ловкость: " + AGI, 0, 2);
				addLine(g, "res/icons/end_icon.png", "Выносливость: " + END, 0, 3);
				addLine(g, "res/icons/icon_luck.png", "Удача: " + LUCK, 0, 4);
								
				y = window.game.calcHP(STR, END);
				addLine(g, "res/icons/icon_life.png", "Жизнь: " + y + "/" + y, 7, 1);
				addLine(g, "res/icons/backpack.png", "Нагрузка: " + window.game.calcCarrying(STR), 7, 2);
				
				// Навыки
				addLine(g, "Навыки:", 6, 3); z = -10;
				int col, row, add;
				for(int s=1;s<=3;s++){
					col=(s==2)?32:260;
					row=(s==1)?5:6;
					add=(s==1)?0:8;
					window.game.renderIcon(g, this, Tileset.TILE_SIZE * (ClassSet.MAX_CLASSES + row) + add + 15,
						col, 7, SkillSet.getSkill(ClassSet.getClass(i).getSkill(s)).getPath(),
						SkillSet.getSkill(ClassSet.getClass(i).getSkill(s)).getName(),
						SkillSet.getSkill(ClassSet.getClass(i).getSkill(s)).getDescr(),
						SkillSet.getSkill(ClassSet.getClass(i).getSkill(s)).getLevel());
				}
			}
		}
		y = ((select - 1) * Tileset.TILE_SIZE);
		x = (window.WINDOW_WIDTH - Tileset.TILE_SIZE - 5);
		g.drawImage(window.game.cursor, x, y, this);
	}
}
