package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Tile {
	public static BufferedImage TILE_FLOOR = Jogo.spritesheet.getSprite(192, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_WALL = Jogo.spritesheet.getSprite(176, 16, Jogo.tamanho, Jogo.tamanho);
	
	
	private BufferedImage sprite;
	private int x, y;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void renderizar(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
