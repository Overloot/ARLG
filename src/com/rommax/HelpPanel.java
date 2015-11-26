package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;

class HelpPanel extends JPanel
{
	public HelpWindow window;
	public HelpKeyHandler listener;
	public int select =  1;
	private int top = 0;
	private int left = 0;

	HelpPanel(HelpWindow window){
		super();
		this.window = window;
		listener = new HelpKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}

	public void add(Graphics g, String s){
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.YELLOW);
		g.drawString(s, left, top);
		top = top + 20;
	}

	public void add(Graphics g, String k, String s, int p){
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.ORANGE);
		g.drawString(k, left, top);
		g2.setPaint(Color.YELLOW);
		g.drawString(s, left + p, top);
		top = top + 20;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(window.game.background, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		FontRenderContext context = g2.getFontRenderContext();
		Font f = new Font("Serif", Font.PLAIN, 14);
		g2.setFont(f);
		top = 20; left = 8;
		//
     	add(g, "Управляйте движением персонажа с помощью стрелок, цифровой клавиатуры или клавиш:");
		add(g, "Q / W / E", " - влево-вверх/вверх/вправо-вверх", 60);
		add(g, "A / S / D", " - влево/отдых/вправо", 60);
		add(g, "Z / X / C", " - влево-вниз/вниз/вправо-вниз", 60);
		add(g, "F1", " - Справка (это окно), ESC/ENTER - закрыть", 60);
     	add(g, "G", " - Включить режим добычи ресурсов", 60);
		add(g, "G + Shift", " - разобрать предмет из инвентаря", 60);
     	add(g, "F5-F8", " - Использовать навыки", 60);
     	add(g, "L", " - Включить режим осмотра (look mode), ESC - выход", 60);
     	add(g, "< / >", " - Подняться/спустится по лестнице", 60);
		add(g, "O / C", " - Открыть/закрыть дверь", 60);
		add(g, "D + Shift", " - Выбросить предмет", 60);
		add(g, "T", " - Поднять предмет", 60);
		add(g, "W + Shift", " - Одеть предмет", 60);
		add(g, "Q + Shift", " - Пить", 60);
		add(g, "E + Shift", " - Есть", 60);
		add(g, "R + Shift", " - Прочитать", 60);
		add(g, "I", " - Инвентарь, ENTER - осмотреть предмет", 60);
		add(g, "I + Shift", " - Осмотр экипировки (T в этом окне - снять вещь)", 60);
		add(g, "ESC", " - Выход из игры", 60);
	}
}
