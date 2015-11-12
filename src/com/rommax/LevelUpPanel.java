package com.rommax;

import javax.swing.*;
import java.awt.*;

class LevelUpPanel extends JPanel
{
	public LevelUpWindow lwindow;
	public LevelUpKeyHandler listener;
	public int select =  1;
	public int x1 = 0;
	public int x2 = 0;
	public int x3 = 0;



	LevelUpPanel(LevelUpWindow lwindow){
		super();
		this.lwindow = lwindow;
		listener = new LevelUpKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}





	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(lwindow.game.background, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		Image image = lwindow.game.loader.getImage("res/icons/str_icon.png");
		int y = (0);
		int x = (0) ;
		g.drawImage(image,x,y,this);
		image = lwindow.game.loader.getImage("res/icons/agi_icon.png");
		y = (Tileset.TILE_SIZE - 1);
		x = (0) ;
		g.drawImage(image,x, y,this);
		image = lwindow.game.loader.getImage("res/icons/end_icon.png");
		y = (Tileset.TILE_SIZE  * 2 - 1);
		x = (0) ;
		g.drawImage(image,x,y,this);
		y = ((select - 1) * Tileset.TILE_SIZE);
		x = (lwindow.WINDOW_WIDTH - Tileset.TILE_SIZE - 5);
		g.drawImage(lwindow.game.cursor, x, y, this);
     	g2.setPaint(Color.YELLOW);
		g.drawString("Сила: влияет на величину наносимого урона в рукопашном бою ",Tileset.TILE_SIZE + 5, (int)Tileset.TILE_SIZE/2);
		g.drawString("Ловкость: влияет на шанс уворота и попадания в бою ",Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * 1.5));
		g.drawString("Выносливость: влияет на величину здоровья ",Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * 2.5));
		g2.setPaint(Color.WHITE);
		g.drawString("Очков осталось : " + Integer.toString(lwindow.game.statsFree), 5, Tileset.TILE_SIZE * 3 + 10);

		x = (lwindow.WINDOW_WIDTH - (Tileset.TILE_SIZE*2) - 5);

		g2.setPaint(Color.GREEN);
		if (x1!=0)
		g.drawString("+ " + Integer.toString(x1), x,  (int)(Tileset.TILE_SIZE * 0.5));
		if (x2!=0)
		g.drawString("+ " + Integer.toString(x2), x,  (int)(Tileset.TILE_SIZE * 1.5));
        if (x3!=0)
		g.drawString("+ " + Integer.toString(x3), x, (int)(Tileset.TILE_SIZE * 2.5));


	}
}
