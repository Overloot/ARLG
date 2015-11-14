package com.rommax;

import java.util.*;

public class Time {
	
	private final int TURN_PER_DAY = 10;
	
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
		getGame().logMessage("Начинается новый день!");
		getGame().addRandomItem();
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