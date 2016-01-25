package com.rommax;

import java.util.*;

public class Time {
	
	private final int TURN_PER_DAY = 25;
	
	private int day;
	private int time;
	private Game game;
	
	Time(Game game){
		this.day = 0;
		this.time = 0;
		this.game = game;
	}
	
	// События начала нового дня
	private void newDay(){
		getGame().logMessage("#9#Начинается новый день!#^#");
		// Выбираем карту
		int m = new Random().nextInt(getGame().mapsQuantity);
		Map map = getGame().mapList[m];
		// Пытаемся поставить предмет (грибы)
		//for (int i = 0; i < getGame().getMap().getHeight(); i++)
			//for (int j = 0; j < getGame().getMap().getWidth(); j++)
        int y = new Random().nextInt(map.getHeight());
        int x = new Random().nextInt(map.getWidth());
		if (map.field[y][x].getPassable()&&map.field[y][x].getBloodable())
			switch(Util.rand(2)){
				case 1: // Гриб
					getGame().addItem(y, x, Util.rand(ItemSet.getMinMushroomID(), ItemSet.getMaxMushroomID()), map);
					break;
				case 2: // Растение
					getGame().addItem(y, x, Util.rand(ItemSet.getMinPlantID(), ItemSet.getMaxPlantID()), map);
					break;
			}
	}
	
	// Время бежит
	public void update(){
		time++;
		if(time > TURN_PER_DAY){
			this.day++;
			this.time = 0;
			this.newDay();
		}
	}
	
	public int getDay(){
		return day;
	}
	
	public Game getGame(){
		return game;
	}
	
}