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
	private static String raceName[], raceImagePath[];

	public static final int MAX_RACES = 3;
	
	RacePanel(RaceWindow window){
		super();
		this.window = window;
		listener = new RaceKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}
	
	static {
		raceName = new String[MAX_RACES];	raceImagePath = new String[MAX_RACES];
		raceName[0] = "Человек";			raceImagePath[0] = "res/monsters/races/human.png";
		raceName[1] = "Гном";				raceImagePath[1] = "res/monsters/races/gnome.png";
		raceName[2] = "Эльф";				raceImagePath[2] = "res/monsters/races/elf.png";
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image im =  Toolkit.getDefaultToolkit().getImage("res/icons/texture_menu.png");
		g.drawImage(im, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f1 = new Font("Serif", Font.PLAIN, 20);
		Font f2 = new Font("Serif", Font.PLAIN, 12);
		Image image;
		for(int i = 0; i <= 2; i++) {
			image = Toolkit.getDefaultToolkit().getImage(raceImagePath[i]);
			y = i * Tileset.TILE_SIZE;
			g.drawImage(image, 0, y, this);
			if (i == select - 1) g2.setPaint(Color.YELLOW); else g2.setPaint(Color.WHITE);
			g2.setFont(f1);
			g.drawString(raceName[i], Tileset.TILE_SIZE + 5, y + 24);
			if (i == select - 1) {
				g2.setFont(f2);
				g.drawString(raceName[i], Tileset.TILE_SIZE + 5, Tileset.TILE_SIZE * MAX_RACES + 25);
			}
		}
		image = Toolkit.getDefaultToolkit().getImage("res/icons/icon_plus.png");
		y = ((select - 1) * Tileset.TILE_SIZE);
		x = (window.WINDOW_WIDTH - Tileset.TILE_SIZE - 5);
		g.drawImage(image, x, y, this);
	}
}
