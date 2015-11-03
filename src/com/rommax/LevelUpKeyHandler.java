package com.rommax;

import java.awt.event.*;

public class LevelUpKeyHandler implements KeyListener{

	LevelUpPanel panel;

	public LevelUpKeyHandler(LevelUpPanel panel){
		super();
		this.panel = panel;
	}

	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();
		switch (keycode) {
			case KeyEvent.VK_UP:
				panel.select--;
				if (panel.select == 0) panel.select = 3;
				break;
			case KeyEvent.VK_DOWN:
				panel.select++;
				if (panel.select == 4) panel.select = 1;
				break;
			case KeyEvent.VK_ENTER:
				panel.lwindow.game.statsFree--;
				switch (panel.select){
					case 1:
						panel.lwindow.game.player.getSTR().setCurrent(panel.lwindow.game.player.getSTR().getCurrent() + 1);
						panel.lwindow.game.player.getSTR().setMax(panel.lwindow.game.player.getSTR().getMax() + 1);
						panel.x1++;
						panel.lwindow.game.player.getHP().setMax(panel.lwindow.game.player.getHP().getMax() + panel.lwindow.game.HIT_POINTS_PER_STRENGTH);
						panel.lwindow.game.player.getHP().setCurrent(panel.lwindow.game.player.getHP().getCurrent() + panel.lwindow.game.HIT_POINTS_PER_STRENGTH);
						panel.lwindow.game.player.getCurrentWeight().setMax(panel.lwindow.game.player.getCurrentWeight().getMax() + panel.lwindow.game.CARRYING_PER_STRENGTH);
						break;
					case 2:
						panel.lwindow.game.player.getAGI().setCurrent(panel.lwindow.game.player.getAGI().getCurrent() + 1);
						panel.lwindow.game.player.getAGI().setMax(panel.lwindow.game.player.getAGI().getMax() + 1);
						panel.x2++;
						break;
					case 3:
						panel.lwindow.game.player.getEND().setCurrent(panel.lwindow.game.player.getEND().getCurrent() + 1);
						panel.lwindow.game.player.getEND().setMax(panel.lwindow.game.player.getEND().getMax() + 1);
						panel.x3++;
						panel.lwindow.game.player.getHP().setMax(panel.lwindow.game.player.getHP().getMax() + panel.lwindow.game.HIT_POINTS_PER_ENDURANCE);
						panel.lwindow.game.player.getHP().setCurrent(panel.lwindow.game.player.getHP().getCurrent() + panel.lwindow.game.HIT_POINTS_PER_ENDURANCE);
						break;
				}
				if (panel.lwindow.game.statsFree == 0){
					panel.lwindow.stop();
					return;
				}
				break;
		}
		panel.repaint();
	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}