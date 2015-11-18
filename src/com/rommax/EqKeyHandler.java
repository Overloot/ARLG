package com.rommax;

import java.awt.event.*;

public class EqKeyHandler implements KeyListener{

	EqPanel panel;


	public EqKeyHandler(EqPanel panel){
		super();
		this.panel = panel;
	}

	public synchronized void keyPressed(KeyEvent event){
		switch (event.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				panel.ewindow.stop();
				break;
			case KeyEvent.VK_T:
				if (panel.current!=-1) {
					panel.ewindow.game.player.takeOffItem(panel.current);
					boolean b = false;
					for (int i=0; i< ItemSet.MAX_SLOTS; i++)
						if (panel.ewindow.monster.Equipment[i] != null) b = true;
					if (!b) panel.ewindow.stop();
				}
				break;
			case KeyEvent.VK_DOWN:
				if (panel.current != -1){
					int nc = panel.current + 1;
					if (nc >=( ItemSet.MAX_SLOTS - 1)) nc = 0;
					while (panel.ewindow.monster.Equipment[nc] == null){
						nc++;
						if (nc >=( ItemSet.MAX_SLOTS - 1)) nc = 0;
					}
					panel.current = nc;
				}
				break;
			case KeyEvent.VK_UP:
				if (panel.current != -1){
					int nc = panel.current - 1 ;
					if (nc < 0 ) nc = ItemSet.MAX_SLOTS - 1;
					while (panel.ewindow.monster.Equipment[nc] == null){
						nc--;
						if (nc < 0 ) nc = ItemSet.MAX_SLOTS - 1;
					}
					panel.current = nc;
				}
				break;
			case KeyEvent.VK_ENTER:
				if (panel.current!=-1)
					panel.ewindow.game.player.examineItem(panel.ewindow.game.player.Equipment[panel.current]);
				break;
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}