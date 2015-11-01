package com.rommax;

import javax.swing.*;
import java.awt.*;

public class RaceWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public RacePanel panel;
    public Game game;

	static{
		WINDOW_HEIGHT = (RaceSet.MAX_RACES + 4) * Tileset.TILE_SIZE;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		panel.listener = null;
		panel = null;
		game.frame1.mainpanel.repaint();
		this.dispose();
	}
	
	public RaceWindow(Game game){
		this.game = game;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new RacePanel(this);
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}


}