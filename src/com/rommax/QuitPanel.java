package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class QuitPanel extends JPanel
{
	public QuitWindow qw;
	public QuitKeyHandler listener;
	public int select =  1;

	QuitPanel(QuitWindow qw){
		super();
		this.qw = qw;
		listener = new QuitKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(qw.game.background, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f1 = new Font("Serif", Font.PLAIN, 20);
		g2.setFont(f1);
		Image image = qw.game.loader.getImage("res/icons/flames.png");
		int y = (0);
		int x = (0) ;
		g.drawImage(image,x,y,this);
		image = qw.game.loader.getImage("res/icons/quit.png");
		y = (Tileset.TILE_SIZE - 1);
		x = (0) ;
		g.drawImage(image,x, y,this);
		y = ((select - 1) * Tileset.TILE_SIZE);
		x = (qw.WINDOW_WIDTH - Tileset.TILE_SIZE - 5);
		g.drawImage(qw.game.cursor, x, y, this);
     	g2.setPaint(Color.YELLOW);
		g.drawString("Нет, еще поиграем!",Tileset.TILE_SIZE + 5, 23);
		g.drawString("Да, выходим!",Tileset.TILE_SIZE + 5, 55);
	}
}
