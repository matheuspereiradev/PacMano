package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import com.matheus.entidades.*;
import com.matheus.game.Jogo;
import com.matheus.game.Sons;
import com.matheus.graficos.Spritesheet;

public class Mundo {

	public static Tile[] tiles;
	public static List<Objetos> objetos;

	public static int WIDTH_WORD, HEIGHT_WORD;

	public Mundo(String path) {
		objetos = new ArrayList<Objetos>();
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			WIDTH_WORD = mapa.getWidth();
			HEIGHT_WORD = mapa.getHeight();
			int[] pixels = new int[WIDTH_WORD * HEIGHT_WORD];

			tiles = new Tile[WIDTH_WORD * HEIGHT_WORD];

			mapa.getRGB(0, 0, WIDTH_WORD, HEIGHT_WORD, pixels, 0, WIDTH_WORD);
			for (int xx = 0; xx < WIDTH_WORD; xx++) {
				for (int yy = 0; yy < HEIGHT_WORD; yy++) {
					int atual = xx + (yy * WIDTH_WORD);

					if (tiles[atual] == null) {

						
						tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
						
						// padrão é ser grama

						if (pixels[atual] == 0xFF000000) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
							// chao
						} else if (pixels[atual] == 0xFFFFFFFF) {
							tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_WALL);
							// parede
						}else if (pixels[atual]==0xFFFF0000) {
							Fruta e = new Fruta(xx*Jogo.tamanho,yy*Jogo.tamanho,16,16,Entidade.SPRITE_FRUTA,0);
							Jogo.entidades.add(e);
							Jogo.frutas.add(e);
						} 
						else if (pixels[atual] == 0xFF2A00FF) {
							Jogo.jogador.setX(xx * Jogo.tamanho);
							Jogo.jogador.setY(yy * Jogo.tamanho);
							// Jogo.jogador.setMask(1, 1, 15, 15);
							// Jogador
						} else if (pixels[atual] == 0xFF89FFFD) {
							Inimigo i =new Inimigo(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho,Jogo.spritesheet.getSprite(64 , 32, Jogo.tamanho, Jogo.tamanho),1);
							Jogo.entidades.add(i);
							Jogo.inimigo.add(i);
						} 
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xprox, int yprox) {
		int x1 = xprox / Jogo.tamanho;
		int y1 = yprox / Jogo.tamanho;

		int x2 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y2 = yprox / Jogo.tamanho;

		int x3 = xprox / Jogo.tamanho;
		int y3 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		int x4 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y4 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		return !((tiles[x1 + (y1 * Mundo.WIDTH_WORD)] instanceof WallTile)
				|| (tiles[x2 + y2 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x3 + y3 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x4 + y4 * Mundo.WIDTH_WORD] instanceof WallTile));
	}

	public static void carregarFase(int level) {
		if (!Jogo.mute)
			Sons.proxFase.play();

		Jogo.entidades.clear();
		Jogo.inimigo.clear();

		Jogo.entidades = new ArrayList<Entidade>();
		Jogo.inimigo = new ArrayList<Inimigo>();

		Jogo.spritesheet = new Spritesheet("/Spritesheet.png");
		Jogo.jogador = new Jogador(35, 29, Jogo.tamanho, Jogo.tamanho,
				Jogo.spritesheet.getSprite(0, 0, Jogo.tamanho, Jogo.tamanho), 1);
		Jogo.entidades.add(Jogo.jogador);
	}

	public void renderizar(Graphics g) {
		// int xstart = Camera.x >> 4;Jogo.tamanho
		int xstart = Camera.x / Jogo.tamanho;
		int ystart = Camera.y / Jogo.tamanho;

		int xfinal = xstart + (Jogo.WIDITH / Jogo.tamanho) + 1;
		int yfinal = ystart + (Jogo.HEIGHT / Jogo.tamanho) + 1;

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH_WORD || yy >= HEIGHT_WORD) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH_WORD)];
				tile.renderizar(g);
			}
		}

		renderizarObjetos(g);
	}

	

	public void criarTilesEmbaixoDaCasa(int xInic, int yInic, int xTm, int yTm) {
		for (int x = xInic; x < xInic + xTm; x++) {
			for (int y = yInic; y < yInic + yTm; y++) {
				tiles[x + (y * WIDTH_WORD)] = new WallTile(x * Jogo.tamanho, y * Jogo.tamanho, Tile.TILE_FLOOR);
			}
		}
	}

	public void renderizarObjetos(Graphics g) {
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).renderizar(g);
		}
	}
}
