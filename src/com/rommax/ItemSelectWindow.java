package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ItemSelectWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public ItemSelectPanel ipanel;
    public ItemSelectMessage message;
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
		ipanel.listener = null;
		ipanel = null;
		game.frame1.mainpanel.listener.keyPressed(null);
		game.frame1.mainpanel.repaint();
		this.dispose();
	}


	public ItemSelectWindow(Game game, int type, LinkedList<Item> list, ItemSelectMessage message){
		if (type == ItemSet.TYPE_ANY) isChangedFilter = true;
		else
		isChangedFilter = false;
		this.game = game;
		this.list = list;
		this.message = message;
		this.type = type;
		message.number = -1;
		WINDOW_WIDTH = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		ipanel = new ItemSelectPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(ipanel);
	}


}
