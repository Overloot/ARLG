package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DescKeyHandler implements KeyListener{

	DescPanel panel;

	public DescKeyHandler(DescPanel panel){
		super();
		this.panel = panel;
	}

	public synchronized void keyPressed(KeyEvent event){
		switch (event.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				panel.dwindow.stop();
				break;
		}
	}

	public void keyReleased(KeyEvent event){};

	public void keyTyped(KeyEvent event){};

}