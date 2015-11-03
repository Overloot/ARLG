package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class QuitKeyHandler implements KeyListener{

	QuitPanel panel;

	public QuitKeyHandler(QuitPanel panel){
		super();
		this.panel = panel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_ESCAPE:
				panel.qw.stop();
				break;
			case KeyEvent.VK_UP:
				panel.select--;
				if (panel.select == 0) panel.select = 2;
				break;
			case KeyEvent.VK_DOWN:
				panel.select++;
				if (panel.select == 3) panel.select = 1;
				break;
			case KeyEvent.VK_ENTER:
				switch (panel.select) {
					case 1:
						panel.qw.stop();
						break;
					case 2:
						System.exit(0);
						break;
				}
			break;
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}