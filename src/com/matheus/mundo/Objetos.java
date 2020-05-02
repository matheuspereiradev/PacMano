package com.matheus.mundo;

import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Objetos extends WallTile{
	
	public static BufferedImage bar= Jogo.spritesheet.getSprite(160, 208, 80, 64);
	public static BufferedImage casa_32X32 = Jogo.spritesheet.getSprite(208, 288, 32, 32);
	public static BufferedImage casa_64X48_1 = Jogo.spritesheet.getSprite(256, 224, 64, 48);
	public static BufferedImage casa_64X48_2 = Jogo.spritesheet.getSprite(256, 272, 64, 48);
	
	public Objetos(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

}
