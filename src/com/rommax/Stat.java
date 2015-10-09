
package com.rommax;

public class Stat{
	private int current;
	private int max;
	private int timer;


	public Stat(int current, int max){
		this.current = current;
		this.max = max;
	}

	public void add(Stat s2){
		this.current += s2.current;
		if (s2.timer == 0)
			this.max += s2.current;
		else
			this.timer += (s2.timer + 1);
	}

    public void sub(Stat s2){
		this.current -= s2.current;
		if (s2.timer == 0)
			this.max -= s2.current;
		else
			this.timer = 0;
	}

	public void subTimer(){
		this.timer--;
	}

	public void setTimer(int timer){
		this.timer = timer;
	}

	public void setCurrent(int current){
		this.current = current;
	}

	public void setMax(int max){
		this.max = max;
	}

	public int getMax(){return max;}
	public int getCurrent(){return current;}
	public int getTimer(){return timer;}
}