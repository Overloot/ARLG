package com.rommax;

import javax.swing.*;
import java.awt.*;

public class QuitWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public QuitPanel qp;
    public Game game;

	static{
		WINDOW_HEIGHT = Tileset.TILE_SIZE * 3;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		qp.listener = null;
		qp = null;
		game.frame1.mainpanel.repaint();
		this.dispose();
	}
	
	public QuitWindow(Game game){
		setTitle("Вы уверены, что хотите выйти?");
		this.game = game;
		WINDOW_WIDTH = 400;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		qp = new QuitPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(qp);
	}


}