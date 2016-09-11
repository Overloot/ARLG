package com.rommax;

import javax.swing.*;
import java.awt.*;

public class DescWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
	public Item item;
	public Monster monster = null;
    public DescPanel panel = null;
    public Game game;

	static{
		WINDOW_HEIGHT = 500;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		panel.listener = null;
		panel = null;
		game.frame1.panel.listener.keyPressed(null);
    	game.frame1.panel.repaint();
		this.dispose();
	}


	public DescWindow(Game game, Item item){
		setTitle("Информация о предмете");
		this.game = game;
		this.item = item;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new DescPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}

	public DescWindow(Game game, Monster monster){
		this.game = game;
		this.monster = monster;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new DescPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}

}
