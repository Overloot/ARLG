package com.rommax;

import javax.swing.*;
import java.awt.*;

public class EqWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
	public EqPanel epanel = null;
    public Game game;
    public Monster monster;

	static{
		WINDOW_HEIGHT = 500;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		epanel.listener = null;
		epanel = null;
		game.frame1.mainpanel.listener.keyPressed(null);
    	game.frame1.mainpanel.repaint();
		this.dispose();
	}


	public EqWindow(Game game, Monster monster){
		this.game = game;
		this.monster = monster;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		epanel = new EqPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(epanel);
	}



}
