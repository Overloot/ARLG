package com.rommax;

import javax.swing.*;
import java.awt.*;

public class ClassWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public ClassPanel panel;
    public Game game;

	static{
		WINDOW_HEIGHT = (ClassSet.MAX_CLASSES + 10) * Tileset.TILE_SIZE;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		panel.listener = null;
		panel = null;
		game.frame1.mainpanel.repaint();
		this.dispose();
	}
	
	public ClassWindow(Game game){
		this.game = game;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new ClassPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}


}