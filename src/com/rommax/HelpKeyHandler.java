package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class HelpKeyHandler implements KeyListener{

	HelpPanel panel;

	public HelpKeyHandler(HelpPanel panel){
		super();
		this.panel = panel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_ESCAPE:
				panel.window.stop();
				break;
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}