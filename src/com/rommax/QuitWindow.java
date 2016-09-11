package com.rommax;

import javax.swing.*;
import java.awt.*;

public class QuitWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public QuitPanel panel;
    public Game game;

	static{
		WINDOW_HEIGHT = Tileset.TILE_SIZE * 3;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		panel.listener = null;
		panel = null;
		game.frame1.panel.repaint();
		this.dispose();
	}
	
	public QuitWindow(Game game){
		setTitle("Вы уверены, что хотите выйти?");
		this.game = game;
		WINDOW_WIDTH = 400;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new QuitPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}


}