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
			if (panel.select == 0) panel.select = 3;
		}
		else if (keycode == KeyEvent.VK_DOWN){
			panel.select++;
			if (panel.select == 4) panel.select = 1;
		}
		else if (keycode == KeyEvent.VK_ENTER){
			switch (panel.select){
				case 1:
					// Человек
					Game.currentPlayerRace = "human";
				//panel.window.game.player.setName("+++");
				//panel.window.game.player.setPath("res/monsters/goblin.png");
			break;
				case 2:
					// Гном
					Game.currentPlayerRace = "gnome";
			break;
				case 3:
					// Эльф
					Game.currentPlayerRace = "elf";
			break;
			}
			MainPanel.hasNewGame = false;					
			panel.window.stop();
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}