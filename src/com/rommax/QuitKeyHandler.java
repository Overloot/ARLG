package com.rommax;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class QuitKeyHandler implements KeyListener{

	QuitPanel qp;

	public QuitKeyHandler(QuitPanel qp){
		super();
		this.qp = qp;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();
		if (keycode == KeyEvent.VK_ESCAPE){
			qp.qw.stop();
			return;
		}
		else if (keycode == KeyEvent.VK_UP){
			qp.select--;
			if (qp.select == 0) qp.select = 2;
		}
		else if (keycode == KeyEvent.VK_DOWN){
			qp.select++;
			if (qp.select == 3) qp.select = 1;
		}
		else if (keycode == KeyEvent.VK_ENTER){
			qp.qw.game.statsFree--;
			switch (qp.select){
				case 1:
					qp.qw.stop();
					break;
				case 2:
					System.exit(0);
					break;
			}
			if (qp.qw.game.statsFree == 0)
			{
				qp.qw.stop();
				return;
			}
		}
		qp.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}