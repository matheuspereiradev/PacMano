package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Tile {
	public static BufferedImage TILE_FLOOR = Jogo.spritesheet.getSprite(144, 0, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA = Jogo.spritesheet.getSprite(144, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_ARVORE = Jogo.spritesheet.getSprite(144, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_WALL = Jogo.spritesheet.getSprite(192, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_2 = Jogo.spritesheet.getSprite(176, 0, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_LAVA =Jogo.spritesheet.getSprite(208, 16, 16, 16);
	public static BufferedImage TILE_LAVA_2 =Jogo.spritesheet.getSprite(224, 16, 16, 16);
	
	public static BufferedImage TILE_FLOOR_TERRA_ESQUINA_SUPERIOR_ESQUERDA = Jogo.spritesheet.getSprite(128, 96, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA_VERTICAL_ESQUERDA = Jogo.spritesheet.getSprite(128, 112, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA_ESQUINA_INFERIOR_ESQUERDA = Jogo.spritesheet.getSprite(128, 128, Jogo.tamanho, Jogo.tamanho);
	
	public static BufferedImage TILE_FLOOR_TERRA_SUPERIOR_CENTRAL = Jogo.spritesheet.getSprite(144, 96, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA_CENTRAL = Jogo.spritesheet.getSprite(144, 112, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA_INFERIOR_CENTRAL = Jogo.spritesheet.getSprite(144, 128, Jogo.tamanho, Jogo.tamanho);
	
	public static BufferedImage TILE_FLOOR_TERRA_ESQUINA_SUPERIOR_DIREITA = Jogo.spritesheet.getSprite(160, 96, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA_VERTICAL_DIREITA = Jogo.spritesheet.getSprite(160, 112, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA_ESQUINA_INFERIOR_DIREITA = Jogo.spritesheet.getSprite(160, 128, Jogo.tamanho, Jogo.tamanho);
	
	
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
