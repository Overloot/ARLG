package com.rommax;

import java.util.*;

public class Util {
	
	// позволяет держать число в диапазоне
	//https://code.google.com/p/gsoc-classpath-escher-marcosroriz/source/browse/src/gnu/util/Math.java
	public static double clamp (double i, double low, double high) {
		return java.lang.Math.max (java.lang.Math.min (i, high), low);
	}

	public static float clamp (float i, float low, float high) {
		return java.lang.Math.max (java.lang.Math.min (i, high), low);
	}

	public static int clamp (int i, int low, int high) {
		return java.lang.Math.max (java.lang.Math.min (i, high), low);
	}

	public static long clamp (long i, long low, long high) {
		return java.lang.Math.max (java.lang.Math.min (i, high), low);
	}
  
    // позволяет проверить расстояние
    public static int checkDistance(int y1, int x1, int y2, int x2) {
        return (int) java.lang.Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    // позволяет определить направление движения к цели
    public static int defineDirection(int x) {
        if (x > 0) return 1;
        if (x < 0) return -1;
        return 0;
    }

	// случ. целое число из диапазона
    public static int rand(int min, int max) {
        Random random = new Random();
        return (min + random.nextInt(max - min + 1));
    }
	
	// Число от 1 до value
    public static int rand(int value) {
        Random random = new Random();
        return random.nextInt(value) + 1;
    }	

	// Бросаем кубик
    public static boolean dice(int value, int max) {
        Random random = new Random();
        int res = (random.nextInt(max) + 1);
        return (res <= value);
    }

}