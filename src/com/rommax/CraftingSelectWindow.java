package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CraftingSelectWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
	public CraftingSelectPanel cPanel;
	public CraftingSelectMessage message;
	public LinkedList<Item> list;
	public Game game;
	public int type;
	public boolean isChangedFilter;

	static{
		WINDOW_HEIGHT = Tileset.TILE_SIZE * 10 + 100;
	}

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		cPanel.listener = null;
		cPanel = null;
		game.frame1.panel.listener.keyPressed(null);
		game.frame1.panel.repaint();
		this.dispose();
	}


	public CraftingSelectWindow(Game game, int type, LinkedList<Item> list, CraftingSelectMessage message){
		if (type == ItemSet.TYPE_ANY) isChangedFilter = true;
		else
			isChangedFilter = false;
		this.game = game;
		this.list = list;
		this.message = message;
		this.type = type;
		message.number = -1;
		WINDOW_WIDTH = 700;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		cPanel = new CraftingSelectPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(cPanel);
	}


}