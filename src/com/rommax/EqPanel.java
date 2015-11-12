package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class EqPanel extends JPanel
{
	public EqWindow ewindow;
	public EqKeyHandler listener;
	int current = -1;

	EqPanel(EqWindow ewindow){
		super();
		this.ewindow = ewindow;
		listener = new EqKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
		for (int i=0; i< ItemSet.MAX_SLOTS; i++)
		   if (ewindow.monster.Equipment[i] != null){
			   current = i;
			   break;
		   }
	}

	void drawColorString(Graphics g, String str, int lastX, int lastY){
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f = new Font("Serif", Font.PLAIN, 12);
		g2.setFont(f);
		Rectangle2D bounds;
		StringTokenizer st = new StringTokenizer(str, "#");
		String token;
		while (st.hasMoreTokens()){
			token = st.nextToken();
			if (token.equals("^")){
				g2.setColor(Color.WHITE);
				continue;
			}
			else
			if (token.length() <= 2){
				int col = Integer.parseInt(token);
				g2.setColor(ColorSet.COLORSET[col]);
				continue;
			}
				g2.drawString(token, lastX, lastY);
				bounds = f.getStringBounds(token, context);
				lastX += (bounds.getWidth());
			}
	}




	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		Image im;
		g.drawImage(ewindow.game.background, 0, 0, this);
		for (int i=0; i< ItemSet.MAX_SLOTS; i++){
			im = ewindow.game.loader.getImage("res/icons/item_place.png");
			g.drawImage(im, 0 , i*Tileset.TILE_SIZE + 10, this);

			if (ewindow.monster.Equipment[i] != null){
					im =  ewindow.game.loader.getImage(ItemSet.getItem(ewindow.monster.Equipment[i].getID()).getPath());
				    g.drawImage(im, 0 , i*Tileset.TILE_SIZE + 10, this);
				    g2.setPaint(Color.WHITE);
					drawColorString(g,ewindow.monster.Equipment[i].getName().toLowerCase(),Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * (0.5 + (i))) + 10);
					if (i == current){
						g2.setPaint(Color.YELLOW);
						drawColorString(g, ewindow.monster.Equipment[i].getName().toLowerCase(),Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * (0.5 + (i))) + 10);
						g.drawImage(ewindow.game.cursor, ewindow.WINDOW_WIDTH - Tileset.TILE_SIZE - 5, (i)*Tileset.TILE_SIZE + 10 , this);
					}

				}
			else
					{
						im = ewindow.game.loader.getImage("res/icons/empty_item.png");
					    g.drawImage(im, 0 , i*Tileset.TILE_SIZE + 10, this);
					    g2.setPaint(Color.WHITE);
						drawColorString(g,"<<<пустой слот>>>",Tileset.TILE_SIZE + 5, (int)(Tileset.TILE_SIZE * (0.5 + (i))) + 10);
					}
			}

	}


}
