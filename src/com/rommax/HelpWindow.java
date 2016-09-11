package com.rommax;

import javax.swing.*;
import java.awt.*;

public class HelpWindow extends JFrame{

	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
    public HelpPanel panel;
    public Game game;

	public void stop(){
		game.frame1.setFocusable(true);
		game.frame1.setFocusableWindowState(true);
		panel.listener = null;
		panel = null;
		game.frame1.panel.repaint();
		this.dispose();
	}
	
	public HelpWindow(Game game){
		this.game = game;
		WINDOW_WIDTH = 700;
		WINDOW_HEIGHT = 500;
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		panel = new HelpPanel(this);
		Container contentPane = getContentPane();
		contentPane.add(panel);
	}


}