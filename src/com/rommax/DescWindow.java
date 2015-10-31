package com.rommax;

import javax.swing.*;
import java.awt.*;

public class DescWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
	public Item item;
	public Monster monster = null;
    public DescPanel dpanel = null;
    public Game game;

	static{
		WINDOW_HEIGHT = 500;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		dpanel.listener = null;
		dpanel = null;
		game.frame1.mainpanel.listener.keyPressed(null);
    	game.frame1.mainpanel.repaint();
		this.dispose();
	}


	public DescWindow(Game game, Item item){
		setTitle("Информация о предмете");
		this.game = game;
		this.item = item;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		dpanel = new DescPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(dpanel);
	}

	public DescWindow(Game game, Monster monster){
		this.game = game;
		this.monster = monster;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		dpanel = new DescPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(dpanel);
	}

}
