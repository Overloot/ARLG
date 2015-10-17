package com.rommax;

import javax.swing.*;
import java.awt.*;

public class LevelUpWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public LevelUpPanel lpanel;
    public Game game;

	static{
		WINDOW_HEIGHT = Tileset.TILE_SIZE * 3 + 100;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		lpanel.listener = null;
		lpanel = null;
		game.frame1.mainpanel.repaint();
		this.dispose();


	}
	public LevelUpWindow(Game game){
		setTitle("Повышение уровня");
		this.game = game;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		lpanel = new LevelUpPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(lpanel);
	}


}
