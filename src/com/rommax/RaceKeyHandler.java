package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class RaceKeyHandler implements KeyListener{

	RacePanel panel;

	public RaceKeyHandler(RacePanel panel){
		super();
		this.panel = panel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();

		if (keycode == KeyEvent.VK_UP){
			panel.select--;
			if (panel.select == 0) panel.select = 2;
		}
		else if (keycode == KeyEvent.VK_DOWN){
			panel.select++;
			if (panel.select == 3) panel.select = 1;
		}
		else if (keycode == KeyEvent.VK_ENTER){
			panel.window.game.statsFree--;
			switch (panel.select){
				case 1:
					// Человек
					
			panel.window.stop();
			MainPanel.hasNewGame = false;					
			break;
				case 2:
					// Гном
					
			panel.window.stop();
			MainPanel.hasNewGame = false;					
			break;
			}
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}