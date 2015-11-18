package com.rommax;

public class Entity{
	private int id;
	private int y;
	private int x;
	private int level;
	private Map map;
    private Game game;
	private String name;
    private int height;
    private int width;
	private String path;

	public int getID(){return id;}
	public void setID(int id){this.id = id;}
	public int getHeight() {return height;}
    public int getWidth() {return width;}
	public void setY(int y){this.y = y;}
	public void setX(int x){this.x = x;}
	public int getX(){return x;}
	public int getY(){return y;}	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	public String getPath(){return path;}
	public void setPath(String path){this.path = path;}
	public void setPosition(int y, int x){this.y = y;this.x = x;}
	public void setSize(int height, int width){this.height = height;this.width = width;}
    public void setMap(Map map) {this.map = map;}
	public Map getMap(){return map;}
    public void setGame(Game game) {this.game = game;}
    public Game getGame() {return game;}
	public int getLevel(){return level;}
	public void setLevel(int level){this.level = level;}

	public Entity(int id, String name, int y, int x, int height, int width, Map map, Game game, int level){
		this.id = id;
		this.name = name;
		this.setPosition(y, x);
        this.setSize(height, width);
		this.map = map;
		this.game = game;
		this.level = level;
		this.path = "";
	}
	
	public Entity(int id, String name, String path, int level){
		this.id = id;
		this.name = name;
		this.setPosition(0, 0);
        this.setSize(0, 0);
		this.map = null;
		this.game = null;
		this.level = level;
		this.path = path;
	}

	public Entity(int id, String name, String path){
		this.id = id;
		this.name = name;
		this.setPosition(0, 0);
        this.setSize(0, 0);
		this.map = null;
		this.game = null;
		this.level = 0;
		this.path = path;
	}

	public Entity() {} //дефолтовый конструктор

}