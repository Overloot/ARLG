package com.rommax;

import java.awt.*;

public class Loader{
	
	public Image getImage(String path){
		return Toolkit.getDefaultToolkit().getImage(getClass().getResource("/" + path));
	}
	
}