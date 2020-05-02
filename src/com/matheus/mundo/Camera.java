package com.matheus.mundo;

public class Camera {

	public static int x = 0, y = 0;

	public static int clamp(int atual, int max, int min) {
		if (atual < min) {
			atual = min;
		}
		if (atual > max) {
			atual = max;
		}
		return atual;
	}
}
