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
		g.drawImage(window.game.background, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f1 = new Font("Serif", Font.PLAIN, 20);
		g2.setFont(f1);
	}
}
