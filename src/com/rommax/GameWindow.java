package com.rommax;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public MainPanel panel;
	public Game game;

	static{
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		WINDOW_HEIGHT = 768;//screenSize.height;
		WINDOW_WIDTH = 1024;//screenSize.width;
	}

	public void SwitchMap(Map map){
		panel.setMap(map);
	}

	public static int getScreenTileSizeX(){
		return (WINDOW_WIDTH/Tileset.TILE_SIZE-1)-10;
	}

    public static int getScreenTileSizeY(){
		return (WINDOW_HEIGHT/Tileset.TILE_SIZE-1);
	}

	public GameWindow(Map map, Game game){
		setTitle("ARoguelike");
		this.game = game;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new MainPanel(this, map, getScreenTileSizeX(), getScreenTileSizeY());
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}

}
