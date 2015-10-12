package com.rommax;

import java.awt.event.*;


public class EqKeyHandler implements KeyListener{

	EqPanel ep;


	public EqKeyHandler(EqPanel ep){
		super();
		this.ep = ep;
	}




	public synchronized void keyPressed(KeyEvent event){
		int keycode = event.getKeyCode();
		if (keycode == KeyEvent.VK_DOWN){
			if (ep.current != -1){
				int nc = ep.current + 1;
				if (nc >=( Itemset.MAX_SLOTS - 1)) nc = 0;
				while (ep.ewindow.monster.Equipment[nc] == null){
				    nc++;
					if (nc >=( Itemset.MAX_SLOTS - 1)) nc = 0;
				}
				ep.current = nc;
			}
		}
		else
		if (keycode == KeyEvent.VK_UP){
			if (ep.current != -1){
				int nc = ep.current - 1 ;
				if (nc < 0 ) nc = Itemset.MAX_SLOTS - 1;
				while (ep.ewindow.monster.Equipment[nc] == null){
					   nc--;
						if (nc < 0 ) nc = Itemset.MAX_SLOTS - 1;
					}

				ep.current = nc;
			}
		}

		else
		if (keycode == KeyEvent.VK_ENTER && ep.current!=-1){
			ep.ewindow.game.tryToExamineItem(ep.ewindow.game.player.Equipment[ep.current]);
		}
		else
		if (keycode == KeyEvent.VK_T && ep.current!=-1){
			ep.ewindow.game.TryToTakeOffItem(ep.current);
		 	boolean b = false;
		 	for (int i=0; i<Itemset.MAX_SLOTS; i++)
		 		if (ep.ewindow.monster.Equipment[i] != null) b = true;
		 		if (!b) ep.ewindow.stop();
		}
		else
		if (keycode == KeyEvent.VK_ESCAPE){
			ep.ewindow.stop();
			return;
		}

		ep.repaint();


	}

	public void keyReleased(KeyEvent event){}

	public void keyTyped(KeyEvent event){}

}