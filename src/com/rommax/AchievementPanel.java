package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class AchievementPanel extends JPanel
{
	public AchievementWindow window;
	public AchievementKeyHandler listener;

	AchievementPanel(AchievementWindow window){
		super();
		this.window = window;
		listener = new AchievementKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image image;
		g.drawImage(window.game.background, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f1 = new Font("Serif", Font.PLAIN, 15);
		g2.setFont(f1);
		g2.setPaint(Color.YELLOW);
		int y = 0;
		int top = 4;
		int left = 4;
		for(int i = 0; i < AchievementSet.MAX_ACHIEVEMENTS; i++){
			if (Achievement.isLock(i))continue;
			image = window.game.loader.getImage(AchievementSet.getAchievement(i).getPath());
			g.drawImage(image, left, top + (y * 35), this);
			g.drawString(AchievementSet.getAchievement(i).getName(), 
				left + 40, top + (y * 35) + 10);
			g.drawString(AchievementSet.getAchievement(i).getDescr(), 
				left + 40, top + (y * 35) + 28);
			y++;
		}
	}
}
