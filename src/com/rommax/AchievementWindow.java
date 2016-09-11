package com.rommax;

import javax.swing.*;
import java.awt.*;

public class AchievementWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public AchievementPanel panel;
    public Game game;

	static{
		WINDOW_HEIGHT = 500;
		WINDOW_WIDTH = 500;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		panel.listener = null;
		panel = null;
		game.frame1.panel.repaint();
		this.dispose();
	}
	
	public AchievementWindow(Game game){
		setTitle("Достижения");
		this.game = game;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new AchievementPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}


}