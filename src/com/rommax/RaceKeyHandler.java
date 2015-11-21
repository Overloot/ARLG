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
		int curRace = 0;
		switch (keycode) {
			case KeyEvent.VK_UP:
				panel.select--;
				if (panel.select == 0)
					panel.select = RaceSet.MAX_RACES;
				break;
			case KeyEvent.VK_DOWN:
				panel.select++;
				if (panel.select == RaceSet.MAX_RACES + 1) panel.select = 1;
				break;
			case KeyEvent.VK_ENTER:
				curRace = panel.select - 1;
				RaceSet.getCurrentRaceID = curRace;
				panel.window.game.setRace(curRace);
				MainPanel.hasNewGame = false;					
				panel.window.stop();
				panel.window.game.help();
				break;
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}