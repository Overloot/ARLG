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
		Image im =  Toolkit.getDefaultToolkit().getImage("res/icons/texture_menu.png");
		g.drawImage(im,0,0, this);
		Graphics2D g2 = (Graphics2D)g;
		Image image = Toolkit.getDefaultToolkit().getImage("res/icons/str_icon.png");
		int y = (0);
		int x = (0) ;
		g.drawImage(image,x,y,this);
		image = Toolkit.getDefaultToolkit().getImage("res/icons/agi_icon.png");
		y = (Tileset.TILE_SIZE - 1);
		x = (0) ;
		g.drawImage(image,x, y,this);
		image = Toolkit.getDefaultToolkit().getImage("res/icons/icon_plus.png");
		y = ((select - 1) * Tileset.TILE_SIZE);
		x = (qw.WINDOW_WIDTH - Tileset.TILE_SIZE - 5);
		g.drawImage(image,x,y,this);
     	g2.setPaint(Color.YELLOW);
		g.drawString("Нет, еще поиграем!",Tileset.TILE_SIZE + 5, (int)Tileset.TILE_SIZE/2);
		g.drawString("Да, выходим!",Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * 1.5));
	}
}
