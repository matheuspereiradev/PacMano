package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Fruta extends Entidade{

	public Fruta(double x, double y, int width, int height, BufferedImage sprite, int velocidade) {
		super(x, y, width, height, sprite, velocidade);
	}

	public void renderizar(Graphics g) {
		super.renderizar(g);
	}


}
