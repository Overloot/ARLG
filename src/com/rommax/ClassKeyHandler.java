package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ClassKeyHandler implements KeyListener{

	ClassPanel panel;

	public ClassKeyHandler(ClassPanel panel){
		super();
		this.panel = panel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();
		int curClass = 0;
		switch (keycode) {
			case KeyEvent.VK_UP:
				panel.select--;
				if (panel.select == 0)
					panel.select = ClassSet.MAX_CLASSES;
				break;
			case KeyEvent.VK_DOWN:
				panel.select++;
				if (panel.select == ClassSet.MAX_CLASSES + 1) panel.select = 1;
				break;
			case KeyEvent.VK_ENTER:
				curClass = panel.select - 1;
				ClassSet.getCurrentClassID = curClass;
				panel.window.game.player.setClass(curClass);
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