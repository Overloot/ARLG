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
     	add(g, "Управляйте движением персонажа с помощью стрелок, клавиш qweadzxc или цифровой клавиатуры.");
		add(g, "F1", "Справка (это окно)", 20);
     	add(g, "F4", "Включить режим добычи ресурсов", 20);
     	add(g, "F5-F8", "Использовать навыки", 40);
     	add(g, "L", "Включить режим осмотра (look mode)", 12);
     	add(g, "E", "Осмотр экипировки (T в этом окне - снять вещь)", 12);
     	add(g, "<", "Подняться на предыдущий уровень", 12);
     	add(g, ">", "Спуститься на следующий уровень", 12);
		
/*
		стрелки, qezc - ходьба
    O/С - открыть\закрыть двери
    B/G - спуститься\подняться по лестнице
    L - смотреть(ESC - выход из режима, ENTER - осмотреть объект в курсоре)
    распределять параметры - ENTER
    d, g - поднимать\бросать вещи
    E - экипировка(на экране экипировки t - снять вещь, ENTER - рассмотреть вещь)
    W - надеть предмет
    Q - пить зелья
    r - прочитать свиток
    i - осмотреть инвентарь, ENTER - рассмотреть предмет
    ESC - выход из игры*/

		
	}
}
