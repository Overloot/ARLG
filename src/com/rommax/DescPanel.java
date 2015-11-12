package com.rommax;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;
import java.util.*;


class DescPanel extends JPanel
{
	public DescWindow dwindow;
	public DescKeyHandler listener;
	public int strY = 0;

	DescPanel(DescWindow dwindow){
		super();
		this.dwindow = dwindow;
		listener = new DescKeyHandler(this);
		addKeyListener(listener);
		setFocusable(true);
	}

	void drawColorString(Graphics g, String str, int lastX, int lastY){
		strY += 15;
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


	public void DrawItemDesc(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.WHITE);
		int fontsize = 15;
		g.drawImage(dwindow.game.background, 0, 0, this);
		ScriptObject so = ScriptParser.parseString(dwindow.item.getScript());
		int leftX = 10;
		strY = 15;
		String str = dwindow.item.getName() + " (предмет " + dwindow.item.getLevel() + " уровня)";
		drawColorString(g, str, leftX, strY);
		if (dwindow.item.getSlot()!= ItemSet.SLOT_ANY){
			str = ItemSet.getSlotName(dwindow.item.getSlot());
			drawColorString(g, str, leftX, strY);
		}
		str = "Этот предмет принадлежит категории: #8#" + ItemSet.getNameOfType(dwindow.item.getType()).toLowerCase() + "#^#";
		drawColorString(g, str, leftX, strY);
		str = "Вес:" + dwindow.item.getMass() + ", размер:" + dwindow.item.getSize();
		drawColorString(g, str, leftX, strY);

		if (!dwindow.item.isIdentify())
		{
			drawColorString(g, "Вам пока ничего неизвестно об этом предмете.", leftX, strY);
			return;
		}

 		if (so.STR_UP.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# вашу #8#силу.#^#", leftX, strY);
		else
		if (so.STR_UP.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# вашу #8#силу.#^#", leftX, strY);

		if (so.AGI_UP.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# вашу #8#ловкость!#^#", leftX, strY);
		else
		if (so.AGI_UP.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# вашу #8#ловкость.#^#", leftX, strY);

		if (so.END_UP.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# вашу #8#выносливость!#^#", leftX, strY);
		else
		if (so.END_UP.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# вашу #8#выносливость.#^#", leftX, strY);

		if (so.LUCK_UP.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# вашу #8#удачу!#^#", leftX, strY);
		else
		if (so.LUCK_UP.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# вашу #8#удачу.#^#", leftX, strY);

		if (so.DFIRE.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваш урон #2#огнем.#^#", leftX, strY);
		else
		if (so.DFIRE.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваш урон #2#огнем.#^#", leftX, strY);

		if (so.DCOLD.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваш урон #4#холодом.#^#", leftX, strY);
		else
		if (so.DCOLD.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваш урон #4#холодом.#^#", leftX, strY);

		if (so.DELEC.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваш урон #5#электричеством.#^#", leftX, strY);
		else
		if (so.DELEC.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваш урон #5#электричеством.#^#", leftX, strY);

		if (so.DPOISON.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваш урон #3#ядом.#^#", leftX, strY);
		else
		if (so.DPOISON.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваш урон #3#ядом.#^#", leftX, strY);

		if (so.DNORMAL.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваш #8#урон.#^#", leftX, strY);
		else
		if (so.DNORMAL.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваш #8#урон.#^#", leftX, strY);

		if (so.RFIRE.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваше сопротивление к #2#огню.#^#", leftX, strY);
		else
		if (so.RFIRE.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваше сопротивление к #2#огню.#^#", leftX, strY);

		if (so.RCOLD.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваше сопротивление к #4#холоду.#^#", leftX, strY);
		else
		if (so.RCOLD.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваше сопротивление к #4#холоду.#^#", leftX, strY);

		if (so.RPOISON.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваше сопротивление к #3#яду.#^#", leftX, strY);
		else
		if (so.RPOISON.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваше сопротивление к #3#яду.#^#", leftX, strY);

		if (so.RELEC.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваше сопротивление к #5#электричеству.#^#", leftX, strY);
		else
		if (so.RELEC.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваше сопротивление к #5#электричеству.#^#", leftX, strY);

		if (so.RNORMAL.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# ваше сопротивление к #8#урону.#^#", leftX, strY);
		else
		if (so.RNORMAL.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# ваше сопротивление к #8#урону.#^#", leftX, strY);

		if (so.SW_UP.getCurrent()>0) drawColorString(g, "Этот предмет может #3#увеличить#^# размер вашего инвентаря.", leftX, strY);
		else
		if (so.SW_UP.getCurrent()<0) drawColorString(g, "Этот предмет может #2#уменьшить#^# размер вашего инвентаря.", leftX, strY);


		if (so.BLINK) drawColorString(g, "Этот предмет может #8#телепортировать вас на короткое расстояние.#^#", leftX, strY);
		if (so.TELEPORT) drawColorString(g, "Этот предмет может #8#телепортировать вас.#^#", leftX, strY);
		if (so.IDENTIFY) drawColorString(g, "Этот предмет может #8#опознать#^# другой предмет.", leftX, strY);
		if (so.POISONCOUNT.getCurrent()>0) drawColorString(g, "Этот предмет может #3#отравить#^# вас!", leftX, strY);

		if (so.FOVRAD.getCurrent()>0) drawColorString(g, "Этот предмет может #3#улучшить#^# ваше #8#зрение!#^#", leftX, strY);
		else
		if (so.FOVRAD.getCurrent()<0) drawColorString(g, "Этот предмет может #2#ухудшить#^# ваше #8#зрение!#^#", leftX, strY);

		if (so.PARALYZECOUNT.getCurrent()>0) drawColorString(g, "Этот предмет может #5#парализовать#^# вас!", leftX, strY);

		if (so.HEALPOISON.getCurrent()>0) drawColorString(g, "Этот предмет может #3#исцелить#^# вас от #3#яда!#^#", leftX, strY);
		if (so.HEALSELF.getCurrent()>0) drawColorString(g, "Этот предмет может #3#исцелить#^# вас!#^#", leftX, strY);
	}

	public void DrawMonsterDesc(Graphics g){
		g.drawImage(dwindow.game.background,0,0,this);
		int leftX = 10;
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.YELLOW);
		g.drawString(dwindow.monster.getName(),leftX, 50);
		g2.setPaint(Color.WHITE);
		String str = "#2#ЖИЗНЬ#^# : " + Integer.toString(dwindow.monster.getHP().getCurrent()) + "/" + Integer.toString(dwindow.monster.getHP().getMax());
		drawColorString(g,str,leftX, 100);
		str = "#8#СИЛА#^# : " + Integer.toString(dwindow.monster.getSTR().getCurrent());
		drawColorString(g,str,leftX, 120);
		str = "#8#ЛОВКОСТЬ#^# : " + Integer.toString(dwindow.monster.getAGI().getCurrent());
		drawColorString(g,str,leftX, 135);
		str = "#8#ВЫНОСЛИВОСТЬ#^# : " + Integer.toString(dwindow.monster.getEND().getCurrent());
		drawColorString(g,str,leftX, 150);
		str = "#8#УДАЧА#^# :" + Integer.toString(dwindow.monster.getLUCK().getCurrent());
		drawColorString(g,str,leftX, 165);
		str = "#8#РАДИУС ЗРЕНИЯ#^# : " + dwindow.monster.getFOVRAD().getCurrent();
		drawColorString(g,str,leftX, 180);
		str = "#1#СОПРОТИВЛЕНИЕ УДАРАМ#^# : #8#  " + dwindow.monster.getRNormal().getCurrent();
		drawColorString(g,str,leftX, 195);
		str = "#1#НОРМАЛЬНЫЙ УРОН#^# : #8#  " + dwindow.monster.getDNormal().getCurrent() + " - " + dwindow.monster.getDNormal().getMax();
		drawColorString(g,str,leftX, 210);
		str = "#1#СОПРОТИВЛЕНИЕ ОГНЮ#^# : #2#  " + dwindow.monster.getRFire().getCurrent();
		drawColorString(g,str,leftX, 225);
		str = "#1#УРОН ОГНЕМ#^# : #2#  " + dwindow.monster.getDFire().getCurrent() + " - " + dwindow.monster.getDFire().getMax();
		drawColorString(g,str,leftX, 240);
		str = "#1#СОПРОТИВЛЕНИЕ ХОЛОДУ#^# : #4#  " + dwindow.monster.getRCold().getCurrent();
		drawColorString(g,str,leftX, 255);
		str = "#1#УРОН ХОЛОДОМ#^# : #4#  " + dwindow.monster.getDCold().getCurrent() + " - " + dwindow.monster.getDCold().getMax();
		drawColorString(g,str,leftX, 270);
		str = "#1#СОПРОТИВЛЕНИЕ ЯДУ#^# : #3#  " + dwindow.monster.getRPoison().getCurrent();
		drawColorString(g,str,leftX, 285);
		str = "#1#УРОН ЯДОМ#^# : #3#  " + dwindow.monster.getDPoison().getCurrent() + " - " + dwindow.monster.getDPoison().getMax();
		drawColorString(g,str,leftX, 300);
		str = "#1#СОПРОТИВЛЕНИЕ ЭЛЕКТРИЧЕСТВУ#^# : #5#  " + dwindow.monster.getRElec().getCurrent();
		drawColorString(g,str,leftX, 315);
		str = "#1#УРОН ЭЛЕКТРИЧЕСТВОМ#^# : #5#  " + dwindow.monster.getDElec().getCurrent() + " - " + dwindow.monster.getDElec().getMax();
		drawColorString(g,str,leftX, 330);
		if (dwindow.monster.getparalyzecount() > 0){
			str = "#8#ПАРАЛИЗОВАН! #^# : #5#  " + dwindow.monster.getparalyzecount();
			drawColorString(g,str,leftX, 400);
		}
		if (dwindow.monster.getPoisonCount() > 0){
			str = "#8#ОТРАВЛЕН! #^# : #3#  " + dwindow.monster.getPoisonCount();
			drawColorString(g,str,leftX, 420);
		}
		str = "#1#УРОВЕНЬ#^# : #8#  " + dwindow.monster.getLevel() + "#^#";
		drawColorString(g,str,leftX, 435);
		if (dwindow.monster == dwindow.game.player){
		str = "ОПЫТА: " + dwindow.monster.getXP() + "/" + dwindow.game.maxExperience;
			drawColorString(g,str,leftX, 450);

		}



	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (dwindow.monster != null)
			DrawMonsterDesc(g);
		else
		    DrawItemDesc(g);

	}


}
