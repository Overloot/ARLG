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

		if (keycode == KeyEvent.VK_UP){
			panel.select--;
			if (panel.select == 0) panel.select = RaceSet.MAX_RACES;
		}
		else if (keycode == KeyEvent.VK_DOWN){
			panel.select++;
			if (panel.select == RaceSet.MAX_RACES + 1) panel.select = 1;
		}
		else if (keycode == KeyEvent.VK_ENTER){
			curRace = panel.select - 1;
			RaceSet.getCurrentRaceID = curRace;
			panel.window.game.player.setSTR(RaceSet.getRace(curRace).getSTR());
			panel.window.game.player.setAGI(RaceSet.getRace(curRace).getAGI());
			panel.window.game.player.setEND(RaceSet.getRace(curRace).getEND());
			panel.window.game.player.setLUCK(RaceSet.getRace(curRace).getLUCK());
			MainPanel.hasNewGame = false;					
			panel.window.stop();
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}