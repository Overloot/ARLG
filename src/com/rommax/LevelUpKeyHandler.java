package com.rommax;

import java.awt.event.*;


public class LevelUpKeyHandler implements KeyListener{

	LevelUpPanel lp;


	public LevelUpKeyHandler(LevelUpPanel lp){
		super();
		this.lp = lp;
	};




	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();
		if (keycode == KeyEvent.VK_UP){
			lp.select--;
			if (lp.select == 0) lp.select = 3;
		}
		else
		if (keycode == KeyEvent.VK_DOWN){
			lp.select++;
			if (lp.select == 4) lp.select = 1;
		}
		else
		if (keycode == KeyEvent.VK_ENTER){
			lp.lwindow.game.statsFree--;

			switch (lp.select){
				case 1: {
					lp.lwindow.game.player.getSTR().setCurrent(lp.lwindow.game.player.getSTR().getCurrent() + 1);
					lp.lwindow.game.player.getSTR().setMax(lp.lwindow.game.player.getSTR().getMax() + 1);

					lp.x1++;
					lp.lwindow.game.player.getHP().setMax(lp.lwindow.game.player.getHP().getMax() + lp.lwindow.game.HIT_POINTS_PER_STRENGTH);
					lp.lwindow.game.player.getHP().setCurrent(lp.lwindow.game.player.getHP().getCurrent() + lp.lwindow.game.HIT_POINTS_PER_STRENGTH);
					lp.lwindow.game.player.getCW().setMax(lp.lwindow.game.player.getCW().getMax() + lp.lwindow.game.CARRYING_PER_STRENGTH);
				}
				break;
				case 2: {
					lp.lwindow.game.player.getAGI().setCurrent(lp.lwindow.game.player.getAGI().getCurrent() + 1);
					lp.lwindow.game.player.getAGI().setMax(lp.lwindow.game.player.getAGI().getMax() + 1);

					lp.x2++;

					} break;
				case 3:
				{

					lp.lwindow.game.player.getEND().setCurrent(lp.lwindow.game.player.getEND().getCurrent() + 1);
					lp.lwindow.game.player.getEND().setMax(lp.lwindow.game.player.getEND().getMax() + 1);

					lp.x3++;
					lp.lwindow.game.player.getHP().setMax(lp.lwindow.game.player.getHP().getMax() + lp.lwindow.game.HIT_POINTS_PER_ENDURANCE);
					lp.lwindow.game.player.getHP().setCurrent(lp.lwindow.game.player.getHP().getCurrent() + lp.lwindow.game.HIT_POINTS_PER_ENDURANCE);


				} break;
			}
			if (lp.lwindow.game.statsFree == 0)
			{
				lp.lwindow.stop();
				return;
			}
		}
		lp.repaint();

	}

	public void keyReleased(KeyEvent event){};

	public void keyTyped(KeyEvent event){};

}